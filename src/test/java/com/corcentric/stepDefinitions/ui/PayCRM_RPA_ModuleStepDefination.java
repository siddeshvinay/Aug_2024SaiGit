package com.corcentric.stepDefinitions.ui;

import com.corcentric.runner.CucumberTestRunner;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import io.cucumber.datatable.DataTable;

public class PayCRM_RPA_ModuleStepDefination extends CucumberTestRunner {

    @Then("User navigates to RPA Case grid")
    public void userNavigateRPAGridAndVerify() {
        Assert.assertTrue(payCRM_rpa_moduleUIBaseStep.navigateToRPACaseGrid(oBrowser,"divRpaCasesContainer"));
    }

    @Then("User verify Reassign functionality")
    public void userPerformReassignActivity() {
        Assert.assertTrue(payCRM_rpa_moduleUIBaseStep.verifyRPACaseReassignFunctionality(oBrowser,"divRpaCasesContainer"));
    }


    @And("RPA will validate the case status and based on the same it perform Success or Fail activities")
    public void rpaWillValidateTheCaseStatusAndBasedOnTheSameItPerformSuccessFailActivities() {
        Assert.assertTrue(payCRM_rpa_moduleUIBaseStep.RPAWillValidateTheCaseStatusToPerformTheSuitableActivities(oBrowser));
    }

    @Then("Navigate to RPA case number and validate the Fail button structure and functionality")
    public void navigateToRPACaseNumberAndValidateTheFailButtonStructureAndFunctionality() {
        Assert.assertTrue(payCRM_rpa_moduleUIBaseStep.validateTheFailButtonStructureAndFunctionality(oBrowser));
    }

    @Then("User navigates to reports and validates the permissions around the report")
    public void userNavigatesToReportsAndValidatesThePermissionsAroundTheReport() {
        Assert.assertTrue(payCRM_rpa_moduleUIBaseStep.validateReportsAndPermissionsAroundTheReport(oBrowser));
    }

    @And("User exports the RPA enabled Suppliers report and validate the same")
    public void userExportsTheRPAEnabledSuppliersReportAndValidateTheSame() {
        Assert.assertTrue(payCRM_rpa_moduleUIBaseStep.userExportTheRPAEnabledSupplierReportsAndValidateTheSame(oBrowser));
    }

    @Then("User navigates to edit profile page then update permission and verify grid should {string} visible")
    public void editProfileAndVerifyTileAndGrid(String visibility, DataTable dataTable) {
        if(visibility.toLowerCase().contains("not")){
            Assert.assertFalse(payCRM_rpa_moduleUIBaseStep.updateUserPermissionForInternalAdmin(dataTable, "divRpaCasesContainer"));
        }else{
            Assert.assertTrue(payCRM_rpa_moduleUIBaseStep.updateUserPermissionForInternalAdmin(dataTable, "divRpaCasesContainer"));
        }
    }

    @Then("User navigates to RPA Case grid and perform success flow with reassign validation")
    public void userVerifySuccessAndReassignFlow() {
        Assert.assertTrue(payCRM_rpa_moduleUIBaseStep.verifySuccessFlowAndReassignValidation(oBrowser,"divRpaCasesContainer"));
    }

    @Then("User navigates to edit profile page then update permission and verify only RPA grid should visible")
    public void editProfileAndVerifyGrid(DataTable dataTable) {
        Assert.assertTrue(payCRM_rpa_moduleUIBaseStep.updateUserPermissionForSuperAdmin(dataTable, "divRpaCasesContainer"));
    }

    @Then("User update rpa permission for clientLinkInformation and verify RPA enable section should {string} visible")
    public void updateRPAPermissionAndNavigateToSupplierPage(String visible, io.cucumber.datatable.DataTable dataTable) {
        Assert.assertTrue(payCRM_rpa_moduleUIBaseStep.setUserPermissionAndNavigateToSupplier(visible, dataTable));
    }

    @Then("User verify RPA Enable section change should saved properly")
    public void verifyRPAEnableChanges() {
        Assert.assertTrue(payCRM_rpa_moduleUIBaseStep.verifySupplierClientLinkInformationRPASaveCorrectly());
    }

    @Then("User edit {string} to the existing Client Link Information")
    public void editExistingClientLinkInfomrationRecord(String recordDetail) {
        Assert.assertTrue(payCRM_rpa_moduleUIBaseStep.changeClientLinkInformationDataAndVerifyRPR(recordDetail));
        Assert.assertTrue(payCRM_rpa_moduleUIBaseStep.verifySupplierClientLinkInformationRPAEnable("be"));
    }
}
