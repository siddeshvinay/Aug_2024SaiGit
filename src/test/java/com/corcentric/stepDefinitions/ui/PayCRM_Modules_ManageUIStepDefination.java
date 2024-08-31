package com.corcentric.stepDefinitions.ui;

import com.corcentric.runner.CucumberTestRunner;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;

public class PayCRM_Modules_ManageUIStepDefination extends CucumberTestRunner {
    @And("User creates new role and validate the same")
    public void userCreatesNewRoleAndValidateTheSame(DataTable dataTable) {
        Assert.assertTrue(payCRM_Modules_ManageUIBaseStep.createAndValidateTheRole(oBrowser, dataTable));
    }

    @Then("User edit the existing role and validate the same")
    public void userEditTheExistingRoleAndValidateTheSame() {
        Assert.assertTrue(payCRM_Modules_ManageUIBaseStep.editAndValidateTheRole(oBrowser));
    }

    @And("Create new user and assign role, groups, queue, companies")
    public void createNewUserAndAssignRoleGroupsQueueCompanies(DataTable dataTable) {
        Assert.assertTrue(payCRM_Modules_ManageUIBaseStep.createUserAndAssignRoleGroupCompanyQueueAndViews(oBrowser, dataTable));
    }

    @And("User validates the Monetization Tracking dashboard")
    public void userValidatesTheMonetizationTrackingDashboard() {
        Assert.assertTrue(payCRM_Modules_ManageUIBaseStep.navigateToMonetizationTrackingAndValidateDashboard(oBrowser));
    }

    @And("Validate search filters on Duplicate Supplier Management->Loaded page using query {string}")
    public void validateSearchFiltersOnDuplicateSupplierManagementLoadedPage(String queryKey) {
        Assert.assertTrue(payCRM_Modules_ManageUIBaseStep.validateSearchFiltersOnDuplicateSuppliersLoadedScreen(oBrowser, queryKey));
    }

    @And("Validate search filters on Duplicate Supplier Management->Completed page using query {string}")
    public void validateSearchFiltersOnDuplicateSupplierManagementCompletedPage(String queryKey) {
        Assert.assertTrue(payCRM_Modules_ManageUIBaseStep.validateSearchFiltersOnDuplicateSuppliersCompletedScreen(oBrowser, queryKey));
    }

    @And("User validates the Customized data columns display functionality for {string} section using query {string}")
    public void userValidatesTheCustomizedDataColumnsDisplayFunctionality(String duplicateSupplierTabName, String queryKey) {
        Assert.assertTrue(payCRM_Modules_ManageUIBaseStep.validateTheCustomizationForDataColumns(oBrowser, duplicateSupplierTabName, queryKey));
    }

    @Then("User validates the column data filters functionality for {string} section using query {string}")
    public void userValidatesTheColumnDataFiltersFunctionalityForSection(String duplicateSupplierTabName, String queryKey) {
        Assert.assertTrue(payCRM_Modules_ManageUIBaseStep.validateColumnDataFilterFunctionalityForDuplicateSupplier(oBrowser, duplicateSupplierTabName, queryKey));
    }

    @Then("User perform merging of duplicate suppliers")
    public void userPerformMergingOfDuplicateSuppliers() {
        Assert.assertTrue(payCRM_Modules_ManageUIBaseStep.mergingOfDuplicateSuppliers(oBrowser));
    }

    @Then("User perform donot merging of duplicate suppliers")
    public void userPerformDonotMergingOfDuplicateSuppliers() {
        Assert.assertTrue(payCRM_Modules_ManageUIBaseStep.donotMergingOfDuplicateSuppliers(oBrowser));
    }

    @And("User apply filter to {string} reports and validates the same")
    public void userApplyFilterToReportsAndValidatesTheSame(String reportType) {
        Assert.assertTrue(payCRM_Modules_ManageUIBaseStep.validateReportFilters(oBrowser, reportType));
    }

    @Then("User Navigates to Reports-->Enrollment Reports-->Verify PAF mail templates")
    public void userNavigatesToReportsEnrollmentReportsVerifyPAFMailTemplates(DataTable dataTable) {
        Assert.assertTrue(payCRM_Modules_ManageUIBaseStep.verifyGeneratePAF_SendEmail(oBrowser, dataTable));
    }

    @And("User Navigates to Reports-->Enrollment Reports-->Verify Send Mail functionality")
    public void userNavigatesToReportsEnrollmentReportsVerifySendMailFunctionality(DataTable dataTable) {
        Assert.assertTrue(payCRM_Modules_ManageUIBaseStep.verifySendEmailFunctionality(oBrowser, dataTable));
    }

    @Then("User navigates to {string} Modules and verifies presence of required fields on page")
    public void UserNavigateToDSM_VerifyRequiredElements(String module) {
        Assert.assertTrue(payCRM_Modules_ManageUIBaseStep.verifyDSMNewDesignedPage(oBrowser));
    }
}
