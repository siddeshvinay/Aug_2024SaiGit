package com.corcentric.stepDefinitions;

import com.corcentric.common.ExtentReportUtils;
import com.corcentric.runner.CucumberTestRunner;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;

public class Hooks extends CucumberTestRunner {
    public String scenarioName;
    @Before
    public void setUp(Scenario scenario){
        try{
            scenarioName = scenario.getName();
            test = extent.startTest(scenarioName);
            if(!scenarioName.startsWith("API Test")){
                oBrowser = appInd.launchBrowser();
                tlDriver.set(oBrowser);
                Assert.assertNotNull(tlDriver.get(), "The browser object was null");
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Exception in setUp() method. " + e);
        }
    }


    @After
    public void tearDown(Scenario scenario){
        try{
            String screenShotName = scenario.getName().replaceAll(" ", "_");
            if(scenario.isFailed()){
                scenario.log("The scenario: '" + scenario.getName() + "' was failed");
                TakesScreenshot ts = (TakesScreenshot) tlDriver.get();
                byte[] screen = ts.getScreenshotAs(OutputType.BYTES);
                scenario.attach(screen, "image/png", screenShotName);
            }
            if(!scenario.getName().startsWith("API Test")){
                oBrowser.quit();
                if(oBrowserTwo!=null)
                    oBrowserTwo.quit();
            }
        }catch(Exception e){
            System.out.println("Exception in tearDown() method. " + e);
        }finally {
            ExtentReportUtils.endExtentReport(test);
        }
    }
}
