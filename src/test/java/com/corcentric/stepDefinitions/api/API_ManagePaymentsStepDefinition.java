package com.corcentric.stepDefinitions.api;

import com.corcentric.runner.CucumberTestRunner;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import org.testng.Assert;

public class API_ManagePaymentsStepDefinition extends CucumberTestRunner {
    @Given("User validates Manage Payments Create, Update and Get operations")
    public void validateCompanyServicesMarketingCRUDOperations(DataTable dataTable) {
        Assert.assertTrue(api_managePaymentsBaseStep.managePaymentsCRUD(dataTable));
    }

    @Given("User validates Update Proxy Number API using PUT method")
    public void userValidatesUpdateProxyNumberAPIUsingPUTMethod(DataTable dataTable) {
        Assert.assertTrue(api_managePaymentsBaseStep.validateUpdateProxyNumberByPUTMethod(dataTable));
    }

    @Given("User validates Return Payment Status by GET method")
    public void userValidatesReturnPaymentStatusByGETMethod(DataTable dataTable) {
        Assert.assertTrue(api_managePaymentsBaseStep.validateReturnPaymentStatusByGETMethod(dataTable));
    }

    @Given("User validates Update Multi-swipe API by using PUT method")
    public void userValidatesUpdateMultiSwipeAPIByUsingPUTMethod(DataTable dataTable) {
        Assert.assertTrue(api_managePaymentsBaseStep.validateUpdateMultiSwipeByPUTMethod(dataTable));
    }

    @Given("User validates View Transaction History API by using GET method")
    public void userValidatesViewTransactionHistoryAPIByUsingGETMethod(DataTable dataTable) {
        Assert.assertTrue(api_managePaymentsBaseStep.validateViewTransactionHistoryByGETMethod(dataTable));
    }

    @Given("User validates Payments Poll and Poll By Date API by using GET method")
    public void userValidatesPaymentsPollAndPollByDateAPIByUsingGETMethod(DataTable dataTable) {
        Assert.assertTrue(api_managePaymentsBaseStep.validatePaymentsPollAndPollByDateByGETMethod(dataTable));
    }

    @Given("User validates Search payments ID & Get Payments Details API by using GET method")
    public void userValidatesSearchPaymentIDAndGetPaymentsDetailsAPIByUsingGETMethod(DataTable dataTable) {
        Assert.assertTrue(api_managePaymentsBaseStep.validateGetPaymentDetailsAndSearchPaymentIDByGETMethod(dataTable));
    }

    @Given("User validates PaymentFIle ID & Bank File ID for download scenario API by using GET method")
    public void userValidatesPaymentFIleIDBankFileIDForDownloadScenarioAPIByUsingGETMethod(DataTable dataTable) {
        Assert.assertTrue(api_managePaymentsBaseStep.validatePaymentFileIDAndBankFileIDForDownloadByGETMethod(dataTable));
    }

    @Given("User validates Payment Files Details & Payment Files List by Bank ID API by using GET method")
    public void userValidatesGetPaymentFilesDetailsAndPaymentFilesListByBankIDAPIByUsingGETMethod(DataTable dataTable) {
        Assert.assertTrue(api_managePaymentsBaseStep.validatePaymentFileDetailsAndPaymentFilesListDetailsByBankIDUsingGETMethod(dataTable));
    }

    @Given("User validates Payment - StopCheck API by using PUT method")
    public void userValidatesPaymentStopCheckAPIByUsingPUTMethod(DataTable dataTable) {
        Assert.assertTrue(api_managePaymentsBaseStep.validatePaymentStopCheckAPIUsingPUTMethod(dataTable));
    }

    @Given("User validates Managed Payment - Update Fees API by using PUT method")
    public void userValidatesManagedPaymentUpdateFeesAPIByUsingPUTMethod(DataTable dataTable) {
        Assert.assertTrue(api_managePaymentsBaseStep.validateManagedPaymentUpdateFeesAPIUsingPUTMethod(dataTable));
    }

    @Given("User validates Managed Payment - Defund API by using PUT method")
    public void userValidatesManagedPaymentDefundAPIByUsingPUTMethod(DataTable dataTable) {
        Assert.assertTrue(api_managePaymentsBaseStep.validateManagedPaymentDefundAPIUsingPUTMethod(dataTable));
    }

    @Given("User validates Bank File And Bank File Receipts API by using POST method")
    public void userValidatesBankFileAndBankFileReceiptsAPIByUsingPOSTMethod(DataTable dataTable) {
        Assert.assertTrue(api_managePaymentsBaseStep.validateManagedPaymentBankFileAndBankFileReceiptAPIByUsingPOSTMethod(dataTable));
    }

    @Given("User validates Managed Payment - Bank Files Filter by statuses API by using POST method")
    public void userValidatesManagedPaymentBankFilesFilterByStatusesAPIByUsingPOSTMethod(DataTable dataTable) {
        Assert.assertTrue(api_managePaymentsBaseStep.validateManagedPaymentFilterBankFilesByStatusesAPIByUsingPOSTMethod(dataTable));
    }

    @Given("User validates MSV Pending Authorization Date scenario API by using GET method")
    public void userValidatesMSVPendingAuthorizationDateScenarioAPIByUsingGETMethod(DataTable dataTable) {
        Assert.assertTrue(api_managePaymentsBaseStep.validateManagedPaymentPendingAuthorizationDateAPIByUsingGETMethod(dataTable));
    }

    @Given("User validates Get Bank Files - Filter By Payment File ID API by using GET method")
    public void userValidatesGetBankFilesFilterByPaymentFileIDAPIByUsingGETMethod(DataTable dataTable) {
        Assert.assertTrue(api_managePaymentsBaseStep.validateManagedPaymentGetBankFilesFilterByPaymentFileIDAPIByUsingGETMethod(dataTable));
    }

    @Given("User validates Manage Payments Reissue API by using PUT method")
    public void userValidatesManagePaymentsReissueAPIByUsingPUTMethod(DataTable dataTable) {
        Assert.assertTrue(api_managePaymentsBaseStep.validateManagedPaymentReissueAPIByUsingPUTMethod(dataTable));
    }

    @Given("User validates Manage Payments Get a list of bank files API by using POST method")
    public void userValidatesManagePaymentsGetAListOfBankFilesAPIByUsingPOSTMethod(DataTable dataTable) {
        Assert.assertTrue(api_managePaymentsBaseStep.validateManagedPaymentGetBankFileListAPIByUsingPOSTMethod(dataTable));
    }

    @Given("User validates Manage Payments Get Poll By Date Time API for BankFile Receipts API by using GET method")
    public void userValidatesManagePaymentsGetPollByDateTimeAPIForBankFileReceiptsAPIByUsingGETMethod() {
        Assert.assertTrue(api_managePaymentsBaseStep.validateManagedPaymentGetPollByDateAPIForBankFileReceiptsByUsingGETMethod());
    }

    @Given("User validates notes section using paymentId endpoint")
    public void userValidatesNotesSectionUsingPaymentIdEndpoint() {
        Assert.assertTrue(api_managePaymentsBaseStep.validateNotesSectionForPaymentIdEndpoint());
    }

    @Given("User validates except endpoint using paymentId")
    public void userValidatesExceptEndpointUsingPaymentId() {
        Assert.assertTrue(api_managePaymentsBaseStep.validateExceptAPIEndpointUsingPaymentId());
    }
}
