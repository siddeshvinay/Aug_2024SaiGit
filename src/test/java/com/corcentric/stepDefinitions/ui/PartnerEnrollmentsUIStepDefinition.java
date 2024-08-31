package com.corcentric.stepDefinitions.ui;

import com.corcentric.runner.CucumberTestRunner;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;

public class PartnerEnrollmentsUIStepDefinition extends CucumberTestRunner {
    @And("User verify that the reassignment details are displayed in Partner Enrollments activity tab")
    public void userVerifyThatTheReassignmentDetailsAreDisplyedInPartnerEnrollmentsActivityTab() {
        Assert.assertTrue(partnerEnrollmentsUIBaseStep.verifyTheReassignmentDetailsUnderPartnerEnrollmentsActivitiesTab(oBrowser));
    }

    @And("User validates the Filter functionality on the {string} Cases Grid")
    public void userValidatesTheFilterFunctionalityOnTheCasesGrid(String queueName) {
        Assert.assertTrue(partnerEnrollmentsUIBaseStep.validateFilterFunctionalityForTheColumnData(oBrowser, queueName));
    }

    @Given("User validates Email and phone fields on the {string} Log Activity page")
    public void userValidateEmailPhoneFieldsOnLogActivity(String queueName) {
        Assert.assertTrue(partnerEnrollmentsUIBaseStep.verifyLogActivityEmailPhoneFields(oBrowser, queueName));
    }

    @Then("Verify Communities Table multiselect and Assigned To filter functionalities for {string} queue")
    public void verifyCommunitiesTableMultiselectAndAssignedToFilterFunctionality(String queueName) {
        Assert.assertTrue(partnerEnrollmentsUIBaseStep.validateCommunitiesMultiSelect_AssignedToFilterFunctionality(oBrowser, queueName));
    }

    @And("User validates Case Status and Partner Types filter functionality for {string} queue")
    public void userValidatesCaseStatusAndPartnerTypesFilterFunctionalityForQueue(String queueName) {
        Assert.assertTrue(partnerEnrollmentsUIBaseStep.validateCaseTypeAndPartnerTypesFilterFunctionality(oBrowser, queueName));
    }

    @Then("User validates the role OR permission for Partner Enrollments {string} removing the required permissions")
    public void userValidatesTheRolePermissionForPartnerEnrollmentsBeforeRemovingTheSame(String permission) {
        Assert.assertTrue(partnerEnrollmentsUIBaseStep.validatePartnerEnrollmentRoleAndPermission(oBrowser, permission));
    }

    @And("User {string} the following {string} roles for Partner Enrollment")
    public void userTheFollowingRolesForPartnerEnrollment(String permissionAction, String permissionNames) {
        Assert.assertTrue(payCRM_Modules_ManageUIBaseStep.add_OR_RemovePermissionForTheSelectedRoles(oBrowser, permissionAction, permissionNames));
    }
}
