package com.tools.utils;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.tools.config.LoggerControler;
import io.restassured.module.jsv.JsonSchemaValidator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static com.jayway.restassured.RestAssured.given;

public class ApiUtils {
    static LoggerControler log = LoggerControler.getLogger(ApiUtils.class);

    static String baseURI;
    static RequestSpecBuilder builder = new RequestSpecBuilder();
    static RequestSpecification requestSpec;
    static Response response;

    public static void requestSpecBuilder() throws IOException {
        log.info("Building API Request Specifications");
        builder
                .setBaseUri(getGlobalValue("baseURI"))
                .setContentType(ContentType.JSON)
                .build()
                .log().all();
        requestSpec = builder.build();
    }

    public static String getGlobalValue(String key) throws IOException
    {
        log.info("Fetching Base URI, from Global Variables");
        Properties prop = new Properties();
        String path = System.getProperty("user.dir") + File.separator + "src/test/resources/global.properties";
        FileInputStream fis = new FileInputStream(path);
        prop.load(fis);
        baseURI = prop.getProperty(key);
        return baseURI;
    }
    public static Response postWithParams(String pathParam, String json) throws IOException {
        log.info("Making POST request using Parameters");
        requestSpecBuilder();
        response = given().spec(requestSpec)
                .body(json)
                .when().log().all().post("/"+pathParam);
        log.info(response.statusCode());
        log.info("reponse:");
        response.getBody().prettyPrint();
        return response;
    }

    public static Response deleteWithParams(String pathParam) throws IOException {
        log.info("Making DELETE request using Parameters");
        requestSpecBuilder();
        response = given().spec(requestSpec)
                .when().log().all().delete("/"+pathParam);
        log.info(response.statusCode());
        log.info("reponse:");
        response.getBody().prettyPrint();
        return response;
    }

    public static Boolean validateResponseSchema(String fileName, Response response) {
        log.info("Validating Response SCHEMA using Schema Validator");
        String path = System.getProperty("user.dir") + File.separator + "src/test/resources/schema" + File.separator;
        try {
             response.then().assertThat().contentType(ContentType.JSON).and()
                    .body(JsonSchemaValidator.
                            matchesJsonSchema(new File(path + fileName)));
             return true;
        }
        catch (Exception e)
        {
            log.error("Exception occurred : " + e);

            return false;
        }
    }

    public static Response getWithPathParameters(String uriPathParam) throws IOException {
        log.info("Making GET request using Path Parameters");
        requestSpecBuilder();
        response = given()
                .spec(requestSpec)
                .when()
                .get("/"+uriPathParam)
                .then().extract().response();
        log.info("Requesting URL: " +baseURI+uriPathParam);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(response.getTime());
        log.info("Response time in milliSeconds : " +response.getTime());
        log.info("Response time in seconds : " +seconds);
        log.info("Response : " +response.asString());
        return response;
    }

    public static Response getWithPathAndQueryParameters(String uriPathParam, HashMap<String,Object> querryParam)
            throws IOException {
        log.info("Making GET request using Path And Query Parameters");
        requestSpecBuilder();
        response = given()
                .spec(requestSpec)
                .queryParameters(querryParam)
                .when()
                .get("/"+uriPathParam)
                .then().extract().response();
        log.info("Requesting URL: " +baseURI+uriPathParam);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(response.getTime());
        log.info("Response time in milliSeconds : " +response.getTime());
        log.info("Response time in seconds : " +seconds);
        log.info("Response : " +response.asString());
        return response;
    }

}
