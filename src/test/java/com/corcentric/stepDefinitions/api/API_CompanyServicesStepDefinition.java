package com.corcentric.stepDefinitions.api;

import com.corcentric.runner.CucumberTestRunner;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import org.testng.Assert;

public class API_CompanyServicesStepDefinition extends CucumberTestRunner {
    @Given("User validates Company Services Marketing Create, Update and Get operations")
    public void validateCompanyServicesMarketingCRUDOperations(DataTable dataTable) {
        Assert.assertTrue(api_companyServicesBaseStep.companyServicesMarketingCRUD(dataTable));
    }

   @Given("User validates Company Services contact Get operations")
    public void userValidatesCompanyServicesContactGetOperation(DataTable dataTable) {
        Assert.assertTrue(api_companyServicesBaseStep.companyServicesContactGETRequest(dataTable));
    }

    @Given("User validates Company Services Support-Case Get operations")
    public void userValidatesCompanyServicesSupportCaseGetOperations(DataTable dataTable) {
        Assert.assertTrue(api_companyServicesBaseStep.companyServicesSupportCaseGETRequest(dataTable));
    }

    @Given("User validates Company Management Services SupplierLinks Get and PUT operations")
    public void userValidatesCompanyServicesSupplierLinksGetAndPUTOperations(DataTable dataTable) {
        Assert.assertTrue(api_companyServicesBaseStep.companyServicesSupplierLinksGETAndPUTRequests(dataTable));
    }

    @Given("User validates Supplier Search API using the Get operations")
    public void userValidatesSupplierSearchAPIUsingTheGetOperations(DataTable dataTable) {
        Assert.assertTrue(api_companyServicesBaseStep.searchSupplierAPIUsingGETRequest(dataTable));
    }


    @Given("User validates GET Campaign By ID API")
    public void userValidatesGETCampaignByIDAPI(DataTable dataTable) {
        Assert.assertTrue(api_companyServicesBaseStep.validateGETCampaignAPIByID(dataTable));
    }

    @Given("User validates Get PayNet user information based on valid PayNetID using GET method")
    public void userValidatesGetPayNetUserInformationBasedOnValidPayNetIDUsingGETMethod(DataTable dataTable) {
        Assert.assertTrue(api_companyServicesBaseStep.validateGetPayNetUserInfoWithValidPayNetIdAPIByUsingGETMethod(dataTable));
    }

    @Given("User validates Company services-Supplier Link Clone API by using POST method")
    public void userValidatesCompanyServicesSupplierLinkCloneAPIByUsingPOSTMethod(DataTable dataTable) {
        Assert.assertTrue(api_companyServicesBaseStep.validateCompanyServiceSupplierLinkCloneAPIByUsingPOSTMethod(dataTable));
    }
}
