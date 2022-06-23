package com.run;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        strict = true,
        monochrome = true,
        plugin = {
                "pretty",
                "html:target/html-reports/report.html",
                "html:target/html/cucumber-html.html"},
        features="src/test/resources/feature",
        glue = "com.tools.steps",
        tags = {"@Positive,@Negative,@SchemaValidation"})
public class TestRunner {
}
