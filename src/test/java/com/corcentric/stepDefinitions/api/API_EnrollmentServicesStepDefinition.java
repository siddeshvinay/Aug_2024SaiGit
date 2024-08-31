package com.corcentric.stepDefinitions.api;

import com.corcentric.runner.CucumberTestRunner;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import org.testng.Assert;

public class API_EnrollmentServicesStepDefinition extends CucumberTestRunner {
    @Given("User validates Enrollment Services API Search Global and Company Specific Suppliers functionalities")
    public void userValidatesEnrollmentServicesAPISearchGlobalAndCompanySpecificSuppliersFunctionalities(DataTable dataTable) {
        Assert.assertTrue(api_enrollmentServicesBaseStep.validateEnrollmentServicesSearchSupplierAPI(dataTable));
    }

    @Given("User validates Enrollment Services API for creating and updating new Enrollment cases for VCA & ACH")
    public void userValidatesEnrollmentServicesAPIForCreateAndUpdateNewEnrollmentCasesForVCA_ACH(DataTable dataTable) {
        Assert.assertTrue(api_enrollmentServicesBaseStep.validateEnrollmentServicesCreateAndUpdateEnrollmentCasesAPI(dataTable));
    }

    @Given("User validates Enrollment Services Notifications API by using POST method")
    public void userValidatesEnrollmentServicesNotificationsAPIByUsingPOSTMethod(DataTable dataTable) {
        Assert.assertTrue(api_enrollmentServicesBaseStep.validateEnrollmentServicesNotificationsAPIByUsingPOSTMethod(dataTable));
    }


    @Given("User validates receive additional links to existing suppliers and new clients API by using POST method")
    public void userValidatesReceiveAdditionalLinksToExistingSuppliersAndNewClientsAPIByUsingPOSTMethod(DataTable dataTable) {
        Assert.assertTrue(api_enrollmentServicesBaseStep.validateReceiveAdditionalLinksToExistingSuppliersAndNewClientsAPIByUsingPOSTMethod(dataTable));
    }
}
