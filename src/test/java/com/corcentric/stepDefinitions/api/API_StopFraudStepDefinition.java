package com.corcentric.stepDefinitions.api;

import com.corcentric.runner.CucumberTestRunner;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import org.testng.Assert;

import javax.xml.crypto.Data;

public class API_StopFraudStepDefinition extends CucumberTestRunner {
    @Given("User validates StopFraud GET Company View API")
    public void userValidatesStopFraudGETCompanyViewAPI(DataTable dataTable) {
        Assert.assertTrue(api_stopFraudBaseStep.validateStopFraudGETCompanyViewAPI(dataTable));
    }

    @Given("User validates StopFraud Poll and poll by date GET API")
    public void userValidatesStopFraudPollAndPollByDateGETAPI(DataTable dataTable) {
        Assert.assertTrue(api_stopFraudBaseStep.validateStopFraudPollAndPollByDateGETAPI(dataTable));
    }

    @Given("User validates Attach File API using POST method")
    public void userValidatesAttachFileAPIUsingPOSTMethod(DataTable dataTable) {
        Assert.assertTrue(api_stopFraudBaseStep.validateStopFraudAttachFileByUsingPOSTMethod(dataTable));
    }

    @Given("User validates Stop Fraud get Details By Section API using GET method")
    public void userValidatesStopFraudGetDetailsBySectionAPIUsingGETMethod(DataTable dataTable) {
        Assert.assertTrue(api_stopFraudBaseStep.validateStopFraudGetDetailsBySectionAPIByUsingGETMethod(dataTable));
    }

    @Given("User validates Stop Fraud auto validate and OFAC auto validate API using PUT method")
    public void userValidatesStopFraudAutoValidateAndOFACAutoValidateAPIUsingPUTMethod(DataTable dataTable) {
        Assert.assertTrue(api_stopFraudBaseStep.validateStopFraudAutoValidateAndOFACAutoValidateAPIByUsingPUTMethod(dataTable));
    }

    @Given("User validates Stop Fraud validate-tin-manual API using PUT method")
    public void userValidatesStopFraudValidateTinManualAPIUsingPUTMethod(DataTable dataTable) {
        Assert.assertTrue(api_stopFraudBaseStep.validateStopFraudManualTINValidationAPIByUsingPUTMethod(dataTable));
    }

    @Given("User validates Stop Fraud Auto Validate Address API using PUT method")
    public void userValidatesStopFraudAutoValidateAddressAPIUsingPUTMethod(DataTable dataTable) {
        Assert.assertTrue(api_stopFraudBaseStep.validateStopFraudAddressAutoValidateAPIByUsingPUTMethod(dataTable));
    }

    @Given("User validates Stop Fraud Payment Info Hold & Release API using PUT method")
    public void userValidatesStopFraudPaymentInfoHoldAndReleaseAPIUsingPUTMethod(DataTable dataTable) {
        Assert.assertTrue(api_stopFraudBaseStep.validateStopFraudPaymentInfoHoldAndReleaseAPIByUsingPUTMethod(dataTable));
    }

    @Given("User validates Stop Fraud validate-tin-auto API using PUT method")
    public void userValidatesStopFraudValidateTinAutoAPIUsingPUTMethod(DataTable dataTable) {
        Assert.assertTrue(api_stopFraudBaseStep.validateStopFraudValidateTinAutoAPIByUsingPUTMethod(dataTable));
    }

    @Given("User validates Stop Fraud Get Audio Info API using GET method")
    public void userValidatesStopFraudGetAudioInfoAPIUsingGETMethod(DataTable dataTable) {
        Assert.assertTrue(api_stopFraudBaseStep.validateStopFraudGetAuditInfoAPIByUsingGETMethod(dataTable));
    }

    @Given("User validates Stop Fraud polling endpoints with notes added API using GET method")
    public void userValidatesStopFraudPollingEndpointsWithNotesAddedAPIUsingGETMethod(DataTable dataTable) {
        Assert.assertTrue(api_stopFraudBaseStep.validateStopFraudPollingEndpointsWithNotesAddedAPIByUsingGETMethod(dataTable));
    }

    @Given("User validates Stop Fraud Clear Unable To Validate API using PUT method")
    public void userValidatesStopFraudClearUnableToValidateAPIUsingPUTMethod(DataTable dataTable) {
        Assert.assertTrue(api_stopFraudBaseStep.validateStopFraudClearUnableToValidateAPIByUsingPUTMethod(dataTable));
    }
}
