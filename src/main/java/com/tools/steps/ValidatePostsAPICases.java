package com.tools.steps;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.tools.config.LoggerControler;
import com.tools.utils.ApiUtils;
import com.tools.filetools.ReadTxtFile;
import com.tools.pojo.Posts;
import cucumber.api.java.en.When;
import org.json.*;
import org.testng.Assert;

import java.io.IOException;
import java.util.*;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

public class ValidatePostsAPICases {

    static LoggerControler log = LoggerControler.getLogger(ApiUtils.class);
    Response response = null;
    String pathParam;
    int expected_statusCode;
    int actual_StatusCode;
    int expected_NumberOfPosts;
    int expected_NumberOfUsers;
    String jsonPayload;

    @When("^We have (.*?) api and expect response status (.*?)$")
    public void capture_PathParam_And_Expected_StatusCode(String path, String statusCode) {
        log.info("Fetching Path param and Expected Status code from feature file");
        this.pathParam = path;
        this.expected_statusCode = Integer.parseInt(statusCode);
    }

    @When("^We check the response code returned by the response$")
    public void check_For_Expected_StatusCode() {
        this.actual_StatusCode = response.getStatusCode();
        log.info("Asserting actual status code wit expected status code");
        assertEquals(actual_StatusCode, expected_statusCode);
    }

    @When("^We check the response is (.*?)$")
    public void check_For_Content_Presence_In_Responsebody(String responseBody) {

        try{
            JSONObject json = new JSONObject(response.body().asString());
            if(responseBody.equalsIgnoreCase("empty"))
                assertTrue(json.isEmpty());
            else
                assertFalse(json.isEmpty());
        }
        catch (JSONException ignored)
        {
            JSONArray json = new JSONArray(response.body().asString());
            if(responseBody.equalsIgnoreCase("empty"))
                assertTrue(json.isEmpty());
            else
                assertFalse(json.isEmpty());
        }
    }

    @When("^We validate the schema of the response from (.*?)$")
    public void validateResponseSchema(String fileName) {
        Assert.assertTrue(ApiUtils.validateResponseSchema(fileName, response));
    }


    @When("^We check the number of posts returned in the response are (.*?)$")
    public void check_For_Number_Of_Posts_Returned(String expectedNumberOfPosts) {

        this.expected_NumberOfPosts = Integer.parseInt(expectedNumberOfPosts);
        JsonPath jsonPathEvaluator = response.jsonPath();
        List<String> allIds = jsonPathEvaluator.getList("id");
        Assert.assertEquals(allIds.size(), expected_NumberOfPosts);

        Posts[] postApiRespone = response.as(Posts[].class);
        List<Posts> obj = Arrays.asList(postApiRespone);
        Assert.assertEquals(obj.size(), expected_NumberOfPosts);
    }


    @When("^We check the number of distinct users returned in the response are (.*?)$")
    public void check_For_Number_Of_Distinct_Users_Returned(String expectedNumberOfUsers) {

        this.expected_NumberOfUsers = Integer.parseInt(expectedNumberOfUsers);
        JsonPath jsonPathEvaluator = response.jsonPath();
        List<String> allUserIds = jsonPathEvaluator.getList("userId");
        HashSet<String> distinctUserIds = new HashSet<String>(allUserIds);
        Assert.assertEquals(distinctUserIds.size(), 10);

    }
    @When("^We send a GET request to api$")
    public void getRequest() throws IOException {
        response = ApiUtils.getWithPathParameters(pathParam);
    }

    @When("^We use (.*?) to send a POST request to api$")
    public void postRequestWihtFile(String fileName) throws IOException {
        this.jsonPayload = ReadTxtFile.readTxtFile(fileName);
        response = ApiUtils.postWithParams(pathParam,jsonPayload);
    }

    @When("^We send a DELETE request to api$")
    public void deleteRequestWithParam() throws IOException {
        response = ApiUtils.deleteWithParams(pathParam);
    }


    @When("^We check the response does not contain the data passed in the payload$")
    public void check_If_Response_Contains_PayloadData() {
        JSONObject json = new JSONObject(jsonPayload);
        JsonPath jsonPathEvaluator = response.jsonPath();
        Assert.assertFalse(jsonPathEvaluator.getList("userId").contains(json.get("userId").toString()));
        Assert.assertFalse(jsonPathEvaluator.getList("id").contains(json.get("id").toString()));
    }

    @When("^We check the response data has not been Deleted$")
    public void check_If_Response_Data_Has_Not_Been_Deleted() {
        int id = Integer.parseInt(pathParam.replaceAll("[\\D]", ""));
        JsonPath jsonPathEvaluator = response.jsonPath();
        Assert.assertEquals(id, jsonPathEvaluator.get("id"));
    }

    @When("^We send a GET request to api for '(.*?)'(.*?)$")
    public void getRequest(String querryParam, String comment) throws IOException {
        HashMap<String,Object> qrParam = new HashMap<>();
        qrParam.put("userId",querryParam);
        response = ApiUtils.getWithPathAndQueryParameters(pathParam, qrParam);
    }

    @When("^I use a \"(.*?)\" file to send a POST request to \"(.*?)\"$")
    public void postRequestWihtFile(String fileName, String path) throws IOException {
        String json = ReadTxtFile.readTxtFile(fileName);
        response = ApiUtils.postWithParams(path, json);
    }

}
