package com.corcentric.stepDefinitions.api;

import com.corcentric.runner.CucumberTestRunner;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import org.testng.Assert;

public class API_DataDimensionStepDefinition extends CucumberTestRunner {
    @Given("User validates MSV - Data Dimension - Block & unblock Payment API by using PUT method and also validate Poll & Poll By Date API for the same using GET method")
    public void userValidatesMSVDataDimensionBlockUnblockPaymentEndpointUsingPUTMethods(DataTable dataTable) {
        Assert.assertTrue(api_dataDimensionBaseStep.validateDataDimension_BlockAndUnblockPaymentAPIUsingPUTMethodAndValidatePollAndPollByDateAPIForTheSameUsingGETMethod(dataTable));
    }

    @Given("User validates Data Dimension - Get Transaction History API by using GET methods")
    public void userValidatesDataDimensionGetTransactionHistoryByUsingGETMethods(DataTable dataTable) {
        Assert.assertTrue(api_dataDimensionBaseStep.validateDataDimension_GetTransactionHistoryAPIUsingGETMethod(dataTable));
    }

    @Given("User validates Data Dimension - Block-Refund Payment API by using PUT methods")
    public void userValidatesDataDimensionBlockRefundPaymentAPIByUsingPUTMethods(DataTable dataTable) {
        Assert.assertTrue(api_dataDimensionBaseStep.validateDataDimension_BlockRefundPaymentAPIUsingPUTMethod(dataTable));
    }

    @Given("User validates Data Dimension - Enable multi-swipe API by using PUT methods")
    public void userValidatesDataDimensionEnableMultiSwipeAPIByUsingPUTMethods(DataTable dataTable) {
        Assert.assertTrue(api_dataDimensionBaseStep.validateDataDimension_EnableMultiSwipeAPIUsingPUTMethod(dataTable));
    }

    @Given("User validates Data Dimension - CardInfo API by using PUT methods")
    public void userValidatesDataDimensionCardInfoAPIByUsingPUTMethods(DataTable dataTable) {
        Assert.assertTrue(api_dataDimensionBaseStep.validateDataDimension_CardInfoAPIUsingGETMethod(dataTable));
    }
}
