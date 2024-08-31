package com.Fifty50.seventh7day;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class iMPquestions {
    public WebDriver driver;


    public  void takeScreenshots(String path) throws IOException {
        try{
            TakesScreenshot takesScreenshot= (TakesScreenshot) driver;
            File src= takesScreenshot.getScreenshotAs(OutputType.FILE);
            File traget= new File(path);
            FileUtils.copyFile(src,traget);
        }catch (Exception e){
            Reporter.log("take the screenshots");
        }
    }

    //Another approch
    @Test
    public void anto(){
        try{
            File screeshot=((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screeshot, new File("path"+".png"));
        }catch (Exception e){
            Reporter.log("get the screen shots");
        }
    }

    @BeforeMethod
    public void testlogs(){
        Reporter.log("Starting of the methods and method details");
    }
}
