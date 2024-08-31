package com.corcentric.stepDefinitions.ui;

import com.corcentric.runner.CucumberTestRunner;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;

public class PayCRM_Modules_ImportsUIStepDefination extends CucumberTestRunner {
    @And("User creates new Master Supplier File and validate the same")
    public void userCreatesNewMasterSupplierFileAndValidateTheSame(DataTable dataTable) {
        Assert.assertTrue(payCRM_Modules_importsUIBaseStep.createAndValidateMasterSupplierFile(oBrowser, dataTable));
    }

    @Then("User edit the existing Master Supplier File and validate the same")
    public void userEditTheExistingMasterSupplierFileAndValidateTheSame(DataTable dataTable) {
        Assert.assertTrue(payCRM_Modules_importsUIBaseStep.editAndValidateMasterSupplierFile(oBrowser, dataTable));
    }

    @And("User downloads the Master Supplier File and checks download was successful")
    public void userDownloadsTheMasterSupplierFileAndChecksDowndloadWasSuccessful() {
        Assert.assertTrue(payCRM_Modules_importsUIBaseStep.downloadMasterSupplierFile(oBrowser));
    }

    @And("User creates new Match File and validate the same")
    public void userCreatesNewMatchFileAndValidateTheSame(DataTable dataTable) {
        Assert.assertTrue(payCRM_Modules_importsUIBaseStep.createAndValidateMatchFiles(oBrowser, dataTable));
    }

    @Then("User edit the existing Match File and validate the same")
    public void userEditTheExistingMatchFileAndValidateTheSame(DataTable dataTable) {
        Assert.assertTrue(payCRM_Modules_importsUIBaseStep.editAndValidateMatchFiles(oBrowser, dataTable));
    }

    @And("User creates new Payment Based Enrollments and validate the same")
    public void userCreatesNewPaymentBasedEnrollmentsAndValidateTheSame(DataTable dataTable) {
        Assert.assertTrue(payCRM_Modules_importsUIBaseStep.createAndValidatePaymentBasedEnrollments(oBrowser, dataTable));
    }

    @Then("User edit the existing Payment Based Enrollments and validate the same")
    public void userEditTheExistingPaymentBasedEnrollmentsAndValidateTheSame(DataTable dataTable) {
        Assert.assertTrue(payCRM_Modules_importsUIBaseStep.editAndValidatePaymentBasedEnrollments(oBrowser, dataTable));
    }

    @And("User validates the filter & Payment Based Enrollments details")
    public void userValidatesTheFilterPaymentBasedEnrollmentsDetails() {
        Assert.assertTrue(payCRM_Modules_importsUIBaseStep.validateFilterAndPaymentBasedEnrollmentDetails(oBrowser));
    }

    @And("User creates new report file and validate the same")
    public void userCreatesNewReportFileAndValidateTheSame(DataTable dataTable) {
        Assert.assertTrue(payCRM_Modules_importsUIBaseStep.createAndValidateTheNewReportFiles(oBrowser, dataTable));
    }

    @Then("User edit the existing Report Files and validate the same")
    public void userEditTheExistingReportFilesAndValidateTheSame(DataTable dataTable) {
        Assert.assertTrue(payCRM_Modules_importsUIBaseStep.editAndValidateReportFiles(oBrowser, dataTable));
    }

    @And("User {string} the following {string} roles for Master Supplier file")
    public void userTheFollowingRolesForMasterSupplierFile(String permissionAction, String permissionNames) {
        Assert.assertTrue(payCRM_Modules_ManageUIBaseStep.add_OR_RemovePermissionForTheSelectedRoles(oBrowser, permissionAction, permissionNames));
    }

    @Then("User validates the role OR permission for Master Supplier file {string} removing the required permissions")
    public void userValidatesTheRoleORPermissionForMasterSupplierFileRemovingTheSameByLoginToTheApplicationUsingCredentials(String permission) {
        Assert.assertTrue(payCRM_Modules_ManageUIBaseStep.validateMasterSupplierFileRoleAndPermission(oBrowser, permission));
    }
}
