package com.corcentric.stepDefinitions.api;

import com.corcentric.runner.CucumberTestRunner;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import org.testng.Assert;

public class API_PayNetAPIStepDefinition extends CucumberTestRunner {
    @Given("User validates Update offer for {string} Links form {string} to {string} using POST Method")
    public void userValidatesUpdateOfferWithDifferentPaymentTypesUsingPOSTMethod(String linkStatus, String fromPaymentType, String toPaymentType, DataTable dataTable) {
        Assert.assertTrue(api_payNetAPIBaseStep.validateUpdatesOfferWithDifferentPaymentTypesAPIUsingPOSTMethod(linkStatus, fromPaymentType, toPaymentType, dataTable));
    }
}
