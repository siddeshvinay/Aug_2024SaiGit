package com.corcentric.common;

import com.corcentric.runner.CucumberTestRunner;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import java.io.File;

public class ExtentReportUtils extends CucumberTestRunner {

    private static Logger logger = Logger.getLogger(ExtentReportUtils.class);

    /********************************************************
     * Method Name      : startExtentReport()
     * Purpose          : to begin the extentReport
     * Author           : Gudi
     * Parameters       : fileName
     * ReturnType       : ExtentReport
     ********************************************************/
    public static ExtentReports startExtentReport(String fileName)
    {
        String resultPath = null;
        File objResFilePath = null;
        File objScreenshotPath = null;
        try {
            resultPath = System.getProperty("user.dir") + "/target/extent-reports";
            logger.info("System set Extent report path as "+resultPath);
            objResFilePath = new File(resultPath);
            if(!objResFilePath.exists()) {
                objResFilePath.mkdirs();
            }

            screenShotLocation = objResFilePath + "/screenshot";
            logger.info("System set Extent report screenshot path as "+screenShotLocation);
            objScreenshotPath = new File(screenShotLocation);
            if(!objScreenshotPath.exists()) {
                objScreenshotPath.mkdirs();
            }

            extent = new ExtentReports(resultPath + "/" + fileName+".html", false);
            extent.addSystemInfo("Host Name", System.getProperty("os.name"));
            extent.addSystemInfo("Environment", System.getProperty("environment"));
            extent.addSystemInfo("User Name", System.getProperty("user.name"));
            extent.loadConfig(new File(System.getProperty("user.dir")+"/extent-config.xml"));
            logger.info("Extent report all properties successfully set.");
            return extent;
        }catch(Exception e)
        {
            logger.error("Exception in startExtentReport() method. " + e);
            return null;
        }
        finally {resultPath = null; objResFilePath = null; objScreenshotPath = null;}
    }



    /********************************************************
     * Method Name : endExtentReport()
     * Purpose : to end the extent Report
     *
     *
     ********************************************************/
    public static void endExtentReport(ExtentTest test)
    {
        try {
            extent.endTest(test);
            extent.flush();
            logger.info("Extent report formation successfully complete");
        }catch(Exception e)
        {
            logger.error("Exception in endExtentReport() method. " + e);
        }
    }


    /********************************************************
     * Method Name : writeResult()
     * Purpose : To write the report
     *
     *
     ********************************************************/
    public void writeResult(WebDriver oBrowser, String status, String strDescription)
    {
        try {
            switch(status.toLowerCase())
            {
                case "pass":
                    test.log(LogStatus.PASS, strDescription);
                    logger.info("Executed step is success with : "+strDescription);
                    break;
                case "fail":
                    if(oBrowser!=null) {
                        test.log(LogStatus.FAIL, strDescription + " : " + test.addScreenCapture(captureScreenshot(oBrowser)));
                        logger.error("Executed step is fail with : "+strDescription);
                    }
                    else {
                        test.log(LogStatus.FAIL, "BROWSER OBJECT IS NULL : " + strDescription);
                        logger.error("BROWSER OBJECT IS NULL :  "+strDescription);
                    }
                    break;
                case "warning":
                    test.log(LogStatus.WARNING, strDescription);
                    logger.warn("Executed step having warning as "+strDescription);
                    break;
                case "info":
                    test.log(LogStatus.INFO, strDescription);
                    logger.info("Executed step is success with : "+strDescription);
                    break;
                case "exception":
                    if(oBrowser!=null) {
                        test.log(LogStatus.FATAL, strDescription + " : " + test.addScreenCapture(captureScreenshot(oBrowser)));
                        logger.fatal("Executed step has exception : "+strDescription);
                    }
                    else {
                        test.log(LogStatus.FATAL, strDescription);
                        logger.fatal("Executed step has exception : "+strDescription);
                    }
                    break;
                case "screenshot":
                    test.log(LogStatus.PASS, strDescription + " : " + test.addScreenCapture(captureScreenshot(oBrowser)));
                    break;
                default:
                    logger.error("Invalid status '"+status+"' for the result. please provide the appropriate status");
            }
        }catch(Exception e)
        {
            logger.error("Exception in writeResult() method. " + e);
        }
    }


    /********************************************************
     * Method Name  : captureScreenshot()
     * Purpose 		:
     * Author		: Gudi
     *
     ********************************************************/
    public String captureScreenshot(WebDriver driver)
    {
        File objSource = null;
        String strDestination = null;
        File objDestination = null;
        try {
        	if(driver!=null) {
        		strDestination = screenShotLocation +"/" + "ScreenShot_" + appInd.getDateTime("ddMMYYYY_hhmmss")+".png";
                TakesScreenshot ts = (TakesScreenshot) driver;
                objSource = ts.getScreenshotAs(OutputType.FILE);
                objDestination = new File(strDestination);
                FileHandler.copy(objSource, objDestination);
        	}
            logger.info("Current screen has been captured to : "+strDestination);
        	return strDestination;
        }catch(Exception e)
        {
            logger.info("Exception in captureScreenshot() method. " + e);
            return null;
        }
        finally
        {
            objSource = null;
            objDestination = null;
        }
    }
}
