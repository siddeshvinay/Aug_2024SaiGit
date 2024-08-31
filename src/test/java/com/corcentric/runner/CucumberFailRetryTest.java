package com.corcentric.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;


@CucumberOptions
        (
                features = "@target/rerun.txt",
                glue = {"com.corcentric.stepDefinitions"},
                plugin = {
                        "pretty",
                        "html:target/cucumber-report-retry.html",
                        "json:target/cucumberRetry.json"
                },
                monochrome = true
        )

public class CucumberFailRetryTest extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider()
    public Object[][] scenarios() {
        return super.scenarios();
    }


}
