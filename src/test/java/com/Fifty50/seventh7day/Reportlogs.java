package com.Fifty50.seventh7day;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class Reportlogs implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result){
        Reporter.log("Test is failed"+ result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result){
        Reporter.log("Testcase is passed"+ result.getName());
    }

}
