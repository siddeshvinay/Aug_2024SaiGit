package com.corcentric.stepDefinitions.ui;

import com.corcentric.runner.CucumberTestRunner;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import java.util.List;

public class PayCRM_Modules_ManagedPaymentsUIStepDefination extends CucumberTestRunner {
    @And("User navigates to Managed Payments->pending Bank Files and validate the Bank File Details")
    public void userNavigatesToManagedPaymentAndValidatesBankFileDetails() {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.navigateToManagedPaymentAndValidateBankFileDetails(oBrowser));
    }

    @And("User navigates to Managed Payments->pending Bank Files and validate the Payment File ID Details")
    public void userNavigatesToManagedPaymentsPendingBankFilesAndValidateThePaymentFileIDDetails() {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.navigateToManagedPaymentAndValidatePaymentFileIDDetails(oBrowser));
    }

    @And("User navigates to Manage Payment Pending Bank Files Page from module")
    public void userNavigatesToManagePaymentPendingBankFilesPageFromModule() {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.gotoAndVerifyManagePaymentModule(oBrowser,"Pending Bank Files", "Pending"));
    }

    @And("User navigates to Bank File ID and verify new tab open with file download")
    public void userNavigatesToBankFileIDAndVerifyNewTabOpenWithFileDownload() {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.gotoBankFileID(oBrowser));
    }

    @And("User navigates to Payment File ID and verify new tab open with file download")
    public void userNavigatesToPaymentFileIDAndVerifyNewTabOpenWithFileDownload() {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.gotoPaymentFileID(oBrowser));
    }

    @Then("User navigates to Payment File ID and verify new tab with required field and download functionality")
    public void userNavigatesToPaymentFileIDAndVerifyNewTabWithRequiredFieldAndDownloadFunctionality() {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.verifyPaymentFileIDPage(oBrowser));
    }

    @Then("User navigates to Bank File ID and verify new tab with required field, download and attachment functionality")
    public void userNavigatesToBankFileIDAndVerifyNewTabWithRequiredFieldDownloadAndAttachmentFunctionality() {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.verifyBankFileIDPage(oBrowser));
    }

    @Then("User validates the void page data grid")
    public void userValidatesTheVoidPageDataGrid() {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.validateVoidsDataGridsFromTheManagedPayments(oBrowser));
    }

    @And("User validates the Voids page data grid action icons")
    public void userValidatesTheVoidsPageDataGridActionIcons() {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.validateVoidsPageDataGridActionIcons(oBrowser));
    }

    @And("User search for PaymentID and verify payment detail section")
    public void userSearchForPaymentIDAndVerifyPaymentDetailSection() {
        payCRM_ManagedPaymentsUIBaseStep.gotoAndVerifyManagePaymentModule(oBrowser,"Voids","Payment Void Requests");
        List<String> voidRecord = payCRM_ManagedPaymentsUIBaseStep.getVoidRequestRandomRecord(oBrowser, "divPaymentVoidRequestsContainer");
        payCRM_ManagedPaymentsUIBaseStep.gotoAndVerifyManagePaymentModule(oBrowser,"Payment Search","");
        payCRM_ManagedPaymentsUIBaseStep.verifyVoidRequest(oBrowser, voidRecord);
    }

    @And("User navigates to Managed Payments->Payment Search page, Create Log Activity & validate it under Activities tab")
    public void userNavigatesToManagedPaymentsPaymentSearchPageAndValidatesActivitiesTabFunctionalities(DataTable dataTable) {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.validateActivitiesTabFunctionalities(oBrowser, dataTable));
    }

    @And("User navigates to Managed Payments->Payment Search page, create & Delete the attachments under Attachments tab")
    public void userNavigatesToManagedPaymentsPaymentSearchPageCreateDeleteTheAttachmentsUnderAttachmentsTab(DataTable dataTable) {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.userPerformCreateAndDeleteAttachmentForManagedPayments(oBrowser, dataTable));
    }

    @Then("User validates Payment Void Cases on Payment Details")
    public void userValidatesPaymentVoidCasesOnPaymentDetails(DataTable dataTable) {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.validatePaymentVoidCasesDetailsOnPaymentDetailsPage(oBrowser, dataTable));
    }

    @And("Validate the Payment Void Case - Edit Case Details")
    public void validateThePaymentVoidCaseEditCaseDetails() {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.validateEditPaymentVoidCasesDetails(oBrowser));
    }


    @Then("User search for PaymentID and verify Proxy Button should {string} visible")
    public void userSearchPayNetIDForProxyVerification(String visibility) {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.verifyProxyNumberVisibility(oBrowser,visibility));
    }

    @And("Managed Payment - Verify Transmission Files grid details")
    public void managedPaymentVerifyTransmissionFilesGridDetails() {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.validateTransmissionFilesGridDetails(oBrowser));
    }

    @Then("User perform filed level validations for the mandatory fields under the Transmission form")
    public void userPerformFiledLevelValidationsForTheMandatoryFieldsUnderTheTransmissionForm(DataTable dataTable) {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.userPerformUIValidationsForTheTransmissionFormPage(oBrowser, dataTable));
    }

    @And("AKF-Add a New Transmission record-Buyer Account ID and Payee Account ID Toggle {string} and {string} entered")
    public void akfAddANewTransmissionRecordBuyerAccountIDAndPayeeAccountIDToggleAndEntered(String buyerAccountDetails, String payeeAccountDetails, DataTable dataTable) {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.createTransmissionFileDetailsAndValidateTheSame(oBrowser, buyerAccountDetails, payeeAccountDetails, dataTable));
    }

    @Then("User perform Create, Download and Delete the {string} attachment under General tab")
    public void userPerformCreateDownloadAndDeleteAttachmentsUnderGeneralTab(String pageName, DataTable dataTable) {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.createDownloadAndDeleteTheAttachment_TransmissionFile(oBrowser, pageName, dataTable));
    }

    @And("User Archives the data from Open Bank FIle Receipts and verify in Archived Bank File receipts")
    public void userArchivesTheDataFromOpenBankFIleReceiptsAndVerifyInArchivedBankFileReceipts() {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.verifyUserArchivesOpenBankFileReceipts(oBrowser));
    }

    @Then("Verify Bank File Receipts menu option on treasury dashboard card and details of the grid")
    public void verifyBankFileReceiptsMenuOptionOnTreasuryDashboardCardAndDetailsOfTheGrid() {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.verifyBankFileReceiptsColumnNames(oBrowser));
    }

    @Then("User validates the Void Request Modal functionality for {string} page")
    public void userValidatesTheVoidRequestModalFunctionality(String pageName, DataTable dataTable) {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.validateVoidRequestModalFunctionality(oBrowser, pageName, dataTable));
    }

    @Then("User perform the actionable validation for Defund Button")
    public void userPerformTheActionableValidationForDefundButton() {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.performActionableValidationsForDefundButton(oBrowser));
    }

    @Then("User validates columns and payment status for Transaction History tab")
    public void userValidatesColumnsAndPaymentStatusForTransactionHistoryTab() {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.transactionHistoryTabValidations(oBrowser));
    }

    @Then("User validates Stop Check model validation in PayCRM")
    public void userValidatesStopCheckModelValidationInPayCRM() {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.userValidatesStopCheckFunctionality(oBrowser));
    }

    @And("Managed Payment - Verify Payment Search with both valid and invalid payment ID")
    public void managedPaymentVerifyPaymentSearchWithBothValidAndInvalidPaymentID() {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.validatePaymentSearchWithValidAndInvalidPaymentID(oBrowser));
    }

    @And("User validates the conditions to show specific payment status options for Payment Type: Check")
    public void userValidatesTheConditionsToShowSpecificPaymentStatusOptionsForPaymentTypeCheck() {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.validatePaymentStatusOptionsForPaymentTypeCheck(oBrowser));
    }

    @And("User validates the conditions to show specific payment status options for Payment Type: ACH or ACH Plus")
    public void userValidatesTheConditionsToShowSpecificPaymentStatusOptionsForPaymentTypeACHOrACHPlus() {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.validatePaymentStatusOptionsForPaymentTypeACH_ACHPlus(oBrowser));
    }

    @And("User validates the conditions to show specific payment status options for Payment Type: Virtual Card")
    public void userValidatesTheConditionsToShowSpecificPaymentStatusOptionsForPaymentTypeVirtualCard() {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.validatePaymentStatusOptionsForPaymentTypeVirtualCard(oBrowser));
    }

    @Then("User validates the conditions to show specific payment status options for Payment Type: Prepaid or Debit Cards")
    public void userValidatesTheConditionsToShowSpecificPaymentStatusOptionsForPaymentTypePrepaidOrDebitCards() {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.validatePaymentStatusOptionsForPaymentTypePrepaidOrDebitCard(oBrowser));
    }

    @And("User validates the fields based on the Payment Type selected in Re-Issue")
    public void userValidatesTheFieldsBasedOnThePaymentTypeSelectedInReIssue() {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.validateTheMandatoryAndOptionalFiledsInReIssueWindowBasedOnThePaymentTypeData(oBrowser));
    }

    @And("User Validates Re-Issue Actionable Button in PayCRM")
    public void userValidatesReIssueActionableButtonInPayCRM(DataTable dataTable) {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.performReIssueAndValidateTheSame(oBrowser, dataTable));
    }

    @Then("User validates Negative scenarios for Re-Issue Actionable Button in PayCRM")
    public void userValidatesNegativeScenariosForReIssueActionableButtonInPayCRM() {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.verifyNonAvailabilityOfReIssueButton(oBrowser));
    }

    @And("User validates when the Multi-Swipe button should be show or Hide based on the payment Status")
    public void userValidatesWhenTheMultiSwipeButtonShouldBeShowOrHideBasedOnThePaymentStatus() {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.validateShowAndHideFunctionalityForMultiSwipeButton(oBrowser));
    }

    @Then("User validates Enable Multi Swipe functionality and validates the same under Activities tab")
    public void userValidatesEnableMultiSwipeFunctionalityAndValidatesTheSameUnderActivitiesTab() {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.validateEnableMultiSwipeFunctionalityAndValidateTheSame(oBrowser));
    }

    @And("User {string} the Managed Payments permission for the PayCRM user and {string} logout")
    public void userTheManagedPaymentsPermissionForThePayCRMUser(String enablePermission, String logoutRequired) {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.add_OR_RemovePermissionForManagedPayments(oBrowser, enablePermission, logoutRequired));
    }

    @Then("User validates the permission for Managed Payments {string} removing the required permissions")
    public void userValidatesTheRoleORPermissionForManagedPaymentsBeforeRemovingTheSame(String permission) {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.validateManagedPaymentsRoleAndPermission(oBrowser, permission));
    }

    @Then("User validates {string} status for Update Fee button by using query {string}")
    public void userValidatesShowORHideStatusForUpdateButton(String showORHide, String queryKey) {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.userValidatesShowORHideFunctionalityForUpdateFeeButton(oBrowser, showORHide, queryKey));
    }

    @And("User validates Update fee button - Modal fields and new Fees validation")
    public void userValidatesUpdateFeeButtonModalFieldsAndNewFeesValidation() {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.validateUpdateFeeFunctionality(oBrowser));
    }

    @And("User perform Resend Void Notification and validate the Email notification")
    public void userPerformResendVoidNotificationAndValidateTheEmailNotification() {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.performResendVoidNotificationAndValidateTheSame(oBrowser));
    }

    @And("User validates {string} Defund button functionality using queries {string}")
    public void userValidatesDefundButtonFunctionality(String defundBtnStatus, String queryKeys) {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.validateShowORHideDefundButtonFunctionalityBasedOnPaymentTypeAndStatus(oBrowser, defundBtnStatus, queryKeys));
    }

    @And("User validates negative scenario for not showing Edit Payment Status button")
    public void userValidatesNegativeScenarioForNotShowingEditPaymentStatusButton() {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.validateNegativeScenarioForNotShowingEditPaymentStatusButton(oBrowser));
    }

    @Then("User validates Authorization Pending Date functionality")
    public void userValidatesAuthorizationPendingDateFunctinality() {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.validatePendingAuthFunctionality(oBrowser));
    }

    @Then("Verify edit and Clone functionality on transmission file grid")
    public void verifyEditFuncOnTransmissionFile() {
        Assert.assertTrue(payCRM_ManagedPaymentsUIBaseStep.verifyEditCloneFeatureOnTransmissionFile(oBrowser));
    }

}
