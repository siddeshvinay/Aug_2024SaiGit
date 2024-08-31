package com.corcentric.stepDefinitions.api;

import com.corcentric.runner.CucumberTestRunner;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import org.testng.Assert;

public class API_AKFStepDefinition extends CucumberTestRunner {
    @Given("User validates AKF MSV scenario for create supplier, update payment info and cancel the payment using GET, POST & PUT methods")
    public void userValidatesAKFMSVScenarioForCreateSupplierUpdatePaymentInfoAndCancelThePaymentUingGETPOSTPUTMethods(DataTable dataTable) {
        Assert.assertTrue(api_akfBaseStep.validateAKFScenario_CreateSupplier_UpdatePaymentInfo_CancelPayment_GET_POST_PUTRequest(dataTable));
    }
}
