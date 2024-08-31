package com.corcentric.stepDefinitions.ui;

import com.corcentric.runner.CucumberTestRunner;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;

public class AssistedPaymentServicesUIStepDefinition extends CucumberTestRunner {
    @And("User verify that the reassignment details are displayed in Assisted Payment Services activity tab")
    public void userVerifyThatTheReassignmentDetailsAreDisplyedInAssistedPaymentServicesActivityTab() {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.verifyTheReassignmentDetailsUnderAssistedPaymentServicesActivitiesTab(oBrowser));
    }

    @Then("User perform Reassignment of the Enrollment case for Assisted Payment Services")
    public void userPerformReassignmentOfTheEnrollmentCaseForAssistedPaymentServices(DataTable dataTable) {
        assistedPaymentServicesBaseSteps.reassignTheEnrollmentCaseForAssistedPaymentServices(oBrowser, dataTable);
    }

    @And("User clicks on Assisted Payment Services Logs tab")
    public void userClicksOnAssistedPaymentServicesLogsTab() {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.clickOnAssistedPaymentServicesLogsTab(oBrowser));
    }


    @Then("User verify that the reassignment details are displayed in Assisted Payment Services Logs details")
    public void user_verify_the_AssistedPaymentServices_Logs_details() {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.verifyTheReassignDetailsUnderAssistedPaymentServicesLogsTab(oBrowser));
    }

    

    @And("User clicks on Assisted Payment Services Contacts tab")
    public void userClicksOnAssistedPaymentServicesContactsTab() {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.clickOnAssistedPaymentServicesContactsTab(oBrowser));
    }


    @Then("User deletes the existing the contact & verify contact deleted successful")
    public void userDeletesTheExistingTheContactVerifyContactDeletedSuccessful() {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.deleteContact(oBrowser));
    }

    @Then("User perform the {string} supplier contact details for {string} and validates the same")
    public void userEditTheSupplierContactDetailsForAssistedPaymentServicesAndValidatesTheSame(String contactStatus, String queueName, DataTable dataTable) {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.editTheExistingContactDetails(oBrowser, contactStatus, queueName, dataTable));
    }

    @Then("User adds the {string} supplier contact details for {string} and validates the same")
    public void userAddsTheSupplierContactDetailsForAssistedPaymentServicesAndValidatesTheSame(String contactStatus, String queueName, DataTable dataTable) {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.addAndValidateTheNewContactDetails(oBrowser, contactStatus, queueName, dataTable));
    }


    @Then("User Navigate to the {string} Dashboard and Select the case {string} for the Payment")
    public void userNavigateToTheAssistedPaymentServicesDashboardAndSelectTheCaseForThePayment(String queueName, String caseID) {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.selectTheRequiredCaseIDForAssistantPaymentServices(oBrowser, queueName, caseID));
    }

    @Then("Verify Return Status is {string} before processing Bill payment for this Case")
    public void verifyReturnStatusIsBeforeProcessingBillPaymentForThisCase(String returnStatus) {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.validateThePaymentReturnStatusAsReturned(oBrowser, returnStatus));
    }

    @Then("User Log an Activity type for the case")
    public void logAnActivityTypeAndOutcome(DataTable dataTable) {
        Assert.assertNotNull(assistedPaymentServicesBaseSteps.performLogActivity(oBrowser, dataTable));
    }

    @And("User raise a payment question and validates the confirmation message for the same")
    public void userRaiseAPaymentQuestionAndValidatesTheNoticeMessageForTheSame(DataTable dataTable) {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.raisePaymentQuestion(oBrowser, dataTable));
    }

    @Then("User edit the existing voids from {string} queue and validates the same")
    public void userEditTheExistingVoidsAndValidatesTheSame(String queueName, DataTable dataTable) {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.editTheExistingVoidsAndValidatetheSame(oBrowser, queueName, dataTable));
    }

    @And("User clicks on PayNext Sync button and validate the message")
    public void userClicksOnPayNextSyncButtonAndValidateTheMessage() {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.validateTheMessageForPayNextSyncButtonClick(oBrowser));
    }

    @And("User navigates to Payment Data tab and validate the Payment and Bank Info for the case {string}")
    public void userNavigatesToPaymentDataTabAndValidateThePaymentAndBankInfo(String queryKey) {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.validatePaymentDataTab(oBrowser, queryKey));
    }

    @And("User validates the {string} Cases Grid Filter functionality")
    public void userValidatesTheFilterFunctionalityOnTheCasesGrid(String queueName) {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.validateFilterFunctionalityOnCasesGrid(oBrowser, queueName));
    }

    @Then("verify {string} case should have paymentID with link and redirect user to payment detail page")
    public void verify_case_should_have_payment_id_with_link_and_redirect_user_to_payment_detail_page(String queueName) {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.validatePaymentIDLinkOnCasePage(oBrowser, queueName));
    }

    @And("User clicks on Generate Invoice button and validates the Attachment tab for invoice details")
    public void userClicksOnGenerateInvoiceButtonAndValidatesTheAttachmentTabForInvoiceDetails() {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.clickOnGenerateInvoiceAndValidateInvoiceDetailsUnderAttachmentsTab(oBrowser));
    }

    @Then("User navigates to cases screen of the {string} queue and downloads the Excel file")
    public void userNavigatesToCasesScreenOfTheQueueAndDownloadsTheExcelFile(String queueName) {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.downloadAndValidateExcelFileOfAssistedPaymentServicesCases(oBrowser, queueName));
    }

    @And("User navigates Email Templates Payment Question dropdown Issue Overpaid Unable to Apply Credit")
    public void UserNavigatesEmailTemplatesPaymentQuestionDropdownIssueOverpaidUnableToApplyCredit(DataTable dataTable) {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.issueInvoiceUnableToApplyCredit(oBrowser, dataTable));
    }

    @And("User navigates Email Templates Payment Question dropdown Issue Overpaid Unable to Partially Process")
    public void UserNavigatesEmailTemplatesPaymentQuestionDropdownIssueOverpaidUnableToPartiallyProcess(DataTable dataTable) {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.issueInvoiceUnableToPartiallyProcess(oBrowser, dataTable));
    }

    @And("User navigates Email Templates Payment Question dropdown Issue Underpaid Pay Full Invoice Amount")
    public void UserNavigatesEmailTemplatesPaymentQuestionDropdownIssueUnderpaidPayFullInvoiceAmount(DataTable dataTable) {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.issueInvoicePayFullInvoiceAmount(oBrowser, dataTable));
    }

    @And("User navigates Email Templates Payment Question dropdown Issue Underpaid Turn Service Back On")
    public void UserNavigatesEmailTemplatesPaymentQuestionDropdownIssueUnderpaidTurnServiceBackOn(DataTable dataTable) {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.issueInvoiceTurnServiceBackOn(oBrowser, dataTable));
    }

    @And("User navigates Email Templates Payment Question dropdown Issue Unable to Locate Invoice Account")
    public void UserNavigatesEmailTemplatesPaymentQuestionDropdownIssueUnableToLocateInvoiceAccount(DataTable dataTable) {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.issueInvoiceUnableToLocate(oBrowser, dataTable));
    }

    @And("User navigates Email Templates Payment Question dropdown Issue Unable to Contact Numbers Dialed")
    public void UserNavigatesEmailTemplatesPaymentQuestionDropdownIssueUnableToContactNumbersDialed(DataTable dataTable) {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.issueInvoiceUnableToContactNumbersDialed(oBrowser, dataTable));
    }

    @And("User navigates Email Templates Payment Question dropdown Issue Unable to Contact Email Addresses Used")
    public void UserNavigatesEmailTemplatesPaymentQuestionDropdownIssueUnableToContactEmailAddressesUsed(DataTable dataTable) {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.issueInvoiceUnableToContactEmailAddressesUsed(oBrowser, dataTable));
    }

    @Then("User Navigate to the {string} cases, perform Log Activity and validates the activities log and Last touch column updation")
    public void userNavigateToTheCasesPerformLogActivityAndValidatesTheActivitiesLogAndLastTouchColumnUpdation(String queueName, DataTable dataTable) {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.validateLastTouchColumnAndActivitiesTabAfterPerformingLogActivities(oBrowser, queueName, dataTable));
    }

    @Then("User perform {string} reassign and validates the activities log and Last touch column updation")
    public void userPerformReassignAndValidatesTheActivitiesLogAndLastTouchColumnUpdation(String queueName, DataTable dataTable) {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.performReassignAndValidateTheSame(oBrowser, queueName, dataTable));
    }

    @Then("Verify Assisted Payment Services {string} options in Issue drop down -Verify Child dropdowns and Email text")
    public void verifyPaymentVoidPermissionOptionsInIssueDropDownVerifyChildDropdownsAndEmailText(String issueOption, DataTable dataTable) {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.validatePaymentVoidPermissionOptionsUnderIssueDropdown(oBrowser, issueOption, dataTable));
    }

    @Then("Verify Assisted Payment Services {string} options in Issue drop down - Verify Invoice Emails")
    public void verifyInvoideOptionsInIssueDropDownVerifyEmailText(String issueOption, DataTable dataTable) {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.validateInvoiceOptionsUnderIssueDropdown(oBrowser, issueOption, dataTable));
    }

    @And("Verify Assisted Payment Services {string} options in Issue drop down -Verify Unable To Contact Email")
    public void verifyUnableToContactOptionsInIssueDropDownVerifyEmailText(String issueOption, DataTable dataTable) {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.validateUnableToContactOptionsUnderIssueDropdown(oBrowser, issueOption, dataTable));
    }

    @And("User validates {string} Last Touch- Update and Validation: Email")
    public void userValidatesLastTouchUpdateAndValidationEmail(String queueName, DataTable dataTable) {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.userValidatesLastTouchColumnForSendEmailFunctionality(oBrowser, queueName, dataTable));
    }

    @Then("User validates the {string} file formats under {string} which should be used in uploading files")
    public void userValidatesTheSupportedFileFormatsWhichShouldBeUsedInUploading(String isUploadSupported, String queueName, DataTable dataTable) {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.verifyFileFormatsForUploadingFilesForSendMailFunctionality(oBrowser, isUploadSupported, queueName, dataTable));
    }

    @Then("Verify Pend Logic for {string} Bill Pay {string} Cases with {string} status & {string} priority-> Log Activity-> Case outcome")
    public void verifyPendLogicForBillPayCasesWithPriorityLogActivityCaseOutcome(String queueName, String caseType, String caseStatus, String casePriority, DataTable dataTable) {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.verifyBillPayCasesLogActivityCaseOutcomeFunctionality(oBrowser, queueName, caseType, caseStatus, casePriority, dataTable));
    }

    @And("Verify Assisted Payment Services {string} options in Issue drop down -Verify Process Email")
    public void verifyAssistedPaymentServicesOptionsInIssueDropDownVerifyProcessEmail(String issueOption, DataTable dataTable) {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.validateProcessOptionUnderIssueDropdown(oBrowser, issueOption, dataTable));
    }

    @Then("User navigates to cases screen of the {string} queue and validates payment question template for Credit")
    public void userNavigatesToCasesScreenOfTheQueueAndValidatesPaymentQuestionTemplateForCredit(String queueName,DataTable dataTable) {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.validatePaymentQuestionTemplateForCreditIssue(oBrowser, queueName, dataTable));
    }

    @Then("User navigates to cases screen of the {string} queue and validates moniker icon for bill pay cases")
    public void userNavigatesToCasesScreenOfTheQueueAndValidatesMonikerIconForBillPayCases(String queueName, DataTable dataTable) {
        Assert.assertTrue(assistedPaymentServicesBaseSteps.validateMonikerIconForReopenedBillPayCases(oBrowser, queueName, dataTable));
    }
}
