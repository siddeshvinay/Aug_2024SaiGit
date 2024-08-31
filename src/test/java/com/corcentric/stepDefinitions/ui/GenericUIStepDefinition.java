package com.corcentric.stepDefinitions.ui;

import com.corcentric.runner.CucumberTestRunner;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import io.cucumber.java.en.Given;

public class GenericUIStepDefinition extends CucumberTestRunner {
    @Given("User want to navigate the URL {string} in the {string} application")
    public void userWantToNavigateThePayCRMURL(String urlPayCRM, String appName) {
        Assert.assertTrue(appDep.navigateURL(oBrowser, urlPayCRM, appName), "Failed to navigate the '"+appName+"' url");
    }

    @And("User login to the {string} application with valid {string} and {string}")
    public void userLoginToTheApplicationWithValidCredentials(String appName, String userName, String password) {
        switch(appName.toLowerCase()){
            case "paycrm":
                Assert.assertTrue(appDep.loginToApplication(oBrowser, userName, password, appName));
                reports.writeResult(oBrowser, "Screenshot", "Login to the '" + appName + "' application was successful");
               break;
            default:
            	reports.writeResult(null, "Fail", "Invalid application name '"+appName+"' was provided");
        }
    }

    @Then("User perform Reassignment of the Enrollment case for Enrollments Manager")
    public void userPerformReassignmentOfTheEnrollmentCaseForEnrollmentsManager(DataTable dataTable) {
        Assert.assertTrue(enrollmentManagersUIBaseSteps.reassignTheEnrollmentCaseForEnrollmentsManager(oBrowser, dataTable));
    }

    @And("User verify that the reassignment details are displayed in Enrollments Manager activity tab")
    public void userVerifyThatTheReassignmentDetailsAreDisplyedInEnrollmentsManagerActivityTab() {
        Assert.assertTrue(enrollmentManagersUIBaseSteps.verifyTheReassignmentDetailsUnderEnrollmentsManagerActivitiesTab(oBrowser));
    }

    @And("User clicks on Activities tab for {string} queue")
    public void userClicksOnActivitiesTab(String queueName) {
        Assert.assertTrue(enrollmentManagersUIBaseSteps.clickOnActivitiesTab(oBrowser, queueName));
    }

    @Then("User clicks on Logs tab")
    public void user_clicks_on_Logs_tab() {
        Assert.assertTrue(enrollmentManagersUIBaseSteps.clickOnLogsTab(oBrowser));
    }

    @Then("User verify that the reassignment details are displayed in Logs details")
    public void user_verify_the_Logs_details() {
        Assert.assertTrue(enrollmentManagersUIBaseSteps.verifyTheEnrollmentDetailsUnderLogsTab(oBrowser));
    }

    @And("User closes Child window and switch back to Parent browser window")
    public void userClosesChildWindowAndSwitchBackToParentBrowserWindow() {
        enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
    }

    @And("User logs out from the {string} application")
    public void userLogsOutFromTheApplication(String appName) {
        Assert.assertTrue(enrollmentManagersUIBaseSteps.logoutFromTheApplication(oBrowser, appName));
    }

    @And("User adds the attachment for {string} queue")
    public void userClicksOnAttachmentsTabForQueue(String queueName, DataTable dataTable) {
        Assert.assertTrue(enrollmentManagersUIBaseSteps.addAttachments(oBrowser, queueName, dataTable));
    }

    @And("User verify that the {string} attachment details for {string} queue")
    public void userVerifyThatTheAttachmentDetailsForQueue(String attachmentAction, String queueName) {
        Assert.assertTrue(enrollmentManagersUIBaseSteps.verifyTheAttachmentDetails(oBrowser, attachmentAction, queueName));
    }

    @And("User click on Show button to verify the {string} attachments details for {string} queue")
    public void userClickOnShowButtonToVerifyTheAttachmentsDetails(String attachmentAction, String queueName) {
        Assert.assertTrue(enrollmentManagersUIBaseSteps.verifyAttachmentDetailsUnderShowSection(oBrowser, attachmentAction, queueName));
    }

    @Then("User edit the attachment for the {string} queue")
    public void userClickOnEditButtonToEditTheAttachementForTheQueue(String queueName, DataTable dataTable) {
        Assert.assertTrue(enrollmentManagersUIBaseSteps.editTheAttachment(oBrowser, queueName, dataTable));
    }

    @Given("User login to the {string} application using {string} credentials")
    public void userLoginToTheApplicationUsingCredentials(String appName, String qaDetails) {
        Assert.assertTrue(enrollmentManagersUIBaseSteps.userLoginToApplication(oBrowser, appName, qaDetails));
    }

    @And("User login to the another {string} application using {string} credentials")
    public void userLoginToTheApplicationUsingAnotherCredentials(String appName, String qaDetails) {
        Assert.assertNotNull(enrollmentManagersUIBaseSteps.loginToPayCRMWithAnotherUser(appName, qaDetails));
    }

    @When("User perform Reassignment of the Enrollment case for {string} queue")
    public void userPerformReassignmentOfTheEnrollmentCaseForQueue(String queueName, DataTable dataTable) {
        Assert.assertTrue(enrollmentManagersUIBaseSteps.reassignTheEnrollmentCase(oBrowser, queueName, dataTable));
    }

    @And("User verify that the reassignment details are displayed in {string} activity tab")
    public void userVerifyThatTheReassignmentDetailsAreDisplayedInActivityTab(String queueName) {
        Assert.assertTrue(enrollmentManagersUIBaseSteps.validateReassignDetailsInActivitiesTab(oBrowser, queueName));
    }

    @And("User verify that the reassignment details are displayed in {string} Logs details")
    public void userVerifyThatTheReassignmentDetailsAreDisplayedInLogsDetails(String queueName) {
        Assert.assertTrue(enrollmentManagersUIBaseSteps.validateReassignDetailsInLogsTab(oBrowser, queueName));
    }

    @Then("User removes the {string} attachment for {string} queue")
    public void userRemovesTheAttachmentForQueue(String attachmentAction, String queueName) {
        Assert.assertTrue(enrollmentManagersUIBaseSteps.removeTheAttachment(oBrowser, attachmentAction, queueName));
    }

    @Then("User adds the {string} Address details for {string} and validates the same")
    public void userAddsTheAddressDetailsForAndValidatesTheSame(String addressStatus, String queueName, DataTable dataTable) {
        Assert.assertTrue(enrollmentManagersUIBaseSteps.createAddressAndValidateTheSame(oBrowser, addressStatus, queueName, dataTable));
    }

    @Then("User perform the {string} Address details for {string} and validates the same")
    public void userPerformTheAddressDetailsForAndValidatesTheSame(String addressStatus, String queueName, DataTable dataTable) {
        Assert.assertTrue(enrollmentManagersUIBaseSteps.editAddressAndValidateTheSame(oBrowser, addressStatus, queueName, dataTable));
    }

    @Then("User deletes the existing {string} & verify address deleted successful")
    public void userDeletesTheExistingTheAddressVerifyAddressDeletedSuccessful(String pageName) {
        Assert.assertTrue(enrollmentManagersUIBaseSteps.deleteTheAddressAndValidate(oBrowser, pageName));
    }

    @And("User validates the Column search functionality on the {string} Cases Grid")
    public void userValidatesTheColumnSearchFunctionalityOnTheCasesGrid(String queueName) {
        Assert.assertTrue(enrollmentManagersUIBaseSteps.validateColumnDataSearchFunctionality(oBrowser, queueName));
    }

    @And("User validates the column filter functionality on the Enrollment Cases Grid")
    public void userValidatesTheColumnFilterFunctionaliyOnTheCasesGrid() {
        Assert.assertTrue(enrollmentManagersUIBaseSteps.ValidateColumnFilterFunctionality(oBrowser));
    }

    @And("User Re-run Single Payment Poll")
    public void userReRunSinglePaymentPoll(DataTable dataTable) {
        Assert.assertTrue(enrollmentManagersUIBaseSteps.rerunSinglePaymentPoll(oBrowser, dataTable));
    }

    @And("User creates the new application setting & edits the same")
    public void userCreatesTheNewApplicationSetting(DataTable dataTable) {
        Assert.assertTrue(enrollmentManagersUIBaseSteps.createandDeleteTheApplicationSettingAndValidateTheSame(oBrowser, dataTable));
    }

    @And("User creates the {string} payment process for {string} and validate the same")
    public void userCreatesThePaymentProcessAndValidateTheSame(String paymentStatus, String queueName, DataTable dataTable) {
        Assert.assertTrue(enrollmentManagersUIBaseSteps.createOrEditPaymentProcessAndValidateTheSame(oBrowser, paymentStatus, queueName, dataTable));
    }

    @And("User {string} the payment process for {string} and validate the same")
    public void userEditsThePaymentProcessForAndValidateTheSame(String paymentStatus, String queueName, DataTable dataTable) {
        Assert.assertTrue(enrollmentManagersUIBaseSteps.createOrEditPaymentProcessAndValidateTheSame(oBrowser, paymentStatus, queueName, dataTable));
    }

    @Then("User deletes the Payment process for {string} queue")
    public void userDeletesThePaymentProcessForQueue(String queueName) {
        Assert.assertTrue(enrollmentManagersUIBaseSteps.deletePaymentProcessAndValidateTheSame(oBrowser, queueName));
    }

    @Then("Enter a search value and validate the search output")
    public void enterASearchValueAndValidateTheSearchOutput(DataTable dataTable) {
        Assert.assertTrue(enrollmentManagersUIBaseSteps.searchAndValidateTheSearchOutput(oBrowser, dataTable));
    }

    @Given("User navigates to the {string} application using {string} URL and perform Test Duplicate creation")
    public void userNavigatesToTheApplicationUsingURL(String pageName, String externalEnrollmentSite, DataTable dataTable) {
        Assert.assertTrue(enrollmentManagersUIBaseSteps.registerToExternalEnrollmentSite(oBrowser, pageName, externalEnrollmentSite, dataTable));
    }

    @Then("Validate the Enrollment Site Registration details under Duplicate Supplier Management and search enrolled records")
    public void validateThatTheEnrollmentSiteRegistrationDetailsUnderDuplicateSupplierManagement() {
        Assert.assertTrue(enrollmentManagersUIBaseSteps.validateEnrollmentSiteRegistrationDetails(oBrowser));
    }

    @Then("User pulls the latest Company and Supplier Link data from PayNet to PayCRM")
    public void userPullsTheLatestCompanyAndSupplierLinkDataFromPayNetToPayCRM() {
        Assert.assertTrue(enrollmentManagersUIBaseSteps.userPullsCompanyAndSupplierLinkDataFromPayNetToPayCRM(oBrowser));
    }

    @Then("User navigates to {string} queue and validates the companies dashboard")
    public void userNavigatesToQueueAndValidatesTheCompaniesDashboard(String queueName) {
        Assert.assertTrue(enrollmentManagersUIBaseSteps.validateCompaniesDashboardFromQueuePages(oBrowser, queueName));
    }

    @Given("User navigates to {string} application using the {string} URL and validate Duplicate suppliers with Unique records")
    public void userNavigatesToApplicationUsingTheURLAndValidateDuplicateSuppliersWithUniqueRecords(String pageName, String micrositeURL, DataTable dataTable) {
        Assert.assertTrue(enrollmentManagersUIBaseSteps.validateDuplicateSuppliersWithUniqueDetails(oBrowser, pageName, micrositeURL, dataTable));
    }

    @Given("User navigates to {string} application using the {string} URL and validate Duplicate suppliers with duplicate records")
    public void userNavigatesToApplicationUsingTheURLAndValidateDuplicateSuppliersWithDuplicateRecords(String pageName, String micrositeURL, DataTable dataTable) {
        Assert.assertTrue(enrollmentManagersUIBaseSteps.validateDuplicateSuppliersWithDuplicateDetails(oBrowser, pageName, micrositeURL, dataTable));
    }

    @Given("User launch the browser")
    public void userLaunchTheBrowser(String appName, String qaDetails) {
        Assert.assertTrue(enrollmentManagersUIBaseSteps.userLoginToApplication(oBrowser, appName, qaDetails));
    }

}
