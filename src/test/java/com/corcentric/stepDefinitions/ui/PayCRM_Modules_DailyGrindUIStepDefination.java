package com.corcentric.stepDefinitions.ui;

import com.corcentric.baseSteps.ui.GenericUIBaseStep;
import com.corcentric.pages.PayCRM_Modules_GeneralUIPage;
import com.corcentric.runner.CucumberTestRunner;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.testng.Assert;
import java.util.List;

public class PayCRM_Modules_DailyGrindUIStepDefination extends CucumberTestRunner {

    @Then("Navigate to Information tab of the selected cases Wave & Offers section")
    public void navigateToInformationTabOfTheSelectedCasesWaveOffersSection() {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.navigateAndValidateInformationTabOfWaveAndOffer(oBrowser));
    }

    @And("click on Suppliers tab and validate the grid columns")
    public void clickOnSuppliersTabAndValidateTheGridColumns() {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.clickOnSuppliersTabAndValidateColumnGridNames(oBrowser));
    }

    @And("Navigate to offers tab of wave offer, create new wave offer and validate the same")
    public void navigateToOffersTabOfWaveOfferCreateNewWaveOfferAndValidateTheSame(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.createAndValidateTheOffers(oBrowser, dataTable));
    }

    @Then("Edit the existing wave offer and validate the same")
    public void editTheExistingWaveOfferAndValidateTheSame(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.editAndValidateTheOffers(oBrowser, dataTable));
    }

    @And("Navigate to Scripts tab of wave offer, create new scripts and validate the same")
    public void navigateToScriptsTabOfWaveOfferCreateNewScriptsAndValidateTheSame(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.createAndValidateScripts(oBrowser, dataTable));
    }

    @Then("Edit the existing scripts and validate the same")
    public void editTheExistingScriptsAndValidateTheSame(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.editAndValidateScripts(oBrowser, dataTable));
    }

    @Then("User creates the {string} wave Types and validate the same")
    public void userCreatesTheWaveTypesAndValidateTheSame(String waveTypeStatus, DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.createAndValidateTheWaveType(oBrowser, waveTypeStatus, dataTable));
    }

    @Then("User perform {string} wave Types and validate the same")
    public void userPerformWaveTypesAndValidateTheSame(String waveTypeStatus, DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.editAndValidateTheWaveType(oBrowser, waveTypeStatus, dataTable));
    }

    @Then("User perform {string} queues and validate the same")
    public void userPerformQueuesAndValidateTheSame(String queuesStatus, DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.editAndValidateTheQueues(oBrowser, queuesStatus, dataTable));
    }

    @Then("User apply the {string} filter and search the Cases data using query {string}")
    public void userApplyTheFilterAndSearchTheCasesData(String valueType, String queryKey, DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.applyFilterAndSearchCasesData(oBrowser, valueType, queryKey, dataTable));
    }


    @And("User navigates to cases tab of the selected waves type")
    public void userNavigatesToCasesTabOfTheSelectedWavesType() {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.validateCasesTabOfSelectedWavesType(oBrowser,
                By.xpath("(//a[text()='Cases'])[2]"), "divEnrollmentEnrollmentCasesContainer"));
    }

    @And("User navigates to offer tab of the selected waves type")
    public void userNavigatesToOfferTabOfTheSelectedWavesType() {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.validateCasesTabOfSelectedWavesType(oBrowser,
                PayCRM_Modules_GeneralUIPage.obj_Offers_Link, "divEnrollmentEnrollmentDetailsContainer"));
    }

    @Then("User navigates and select the Daily Grind Case using query {string}")
    public void userNavigatesAndSelectTheDailyGrindCaseUsingQuery(String queryKey) {
        Assert.assertNotNull(payCRM_modules_DailyGrindUIBaseStep.navigateAndSelectDailyGrindCase(oBrowser, queryKey));
    }

    @And("User validates Related Cases and Activities Tab fields")
    public void userValidatesRelatedCasesAndActivitiesTabFields() {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.validateRelatedCasesAndActivitiesTab(oBrowser));
    }

    @And("User verify that tab grid column heading is present")
    public void userVerifyThatTabGridColumnHeadingIsPresent(List<String> headings) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.verifyTabFromWaveDetailPage(oBrowser, "Attachments"));
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.veifyTabGridHeaders(oBrowser, headings));
    }

    @And("user apply filters on {string} page and goto Show Cases page")
    public void userApplyFiltersOnPageAndGotoShowCasesPage(String queueName , DataTable filtersTable) {
        payCRM_modules_DailyGrindUIBaseStep.applyFilterOnQueue(oBrowser, queueName, filtersTable.asMap(String.class, String.class));
    }

    @Then("User validates the columns after apply filter on the Enrollment Cases Grid")
    public void userValidatesTheColumnsAfterApplyFilterOnTheEnrollmentCasesGrid(DataTable dataTable) {
        payCRM_modules_generalUIBaseStep.verifyAppliedFilterOnGridColumn(oBrowser, dataTable.asMap(String.class, String.class));
    }

    @Then("User perform check on the StopFraud-Validations page UI components")
    public void userPerformCheckOnTheStopFraudValidationsPageUIComponents() {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.validateStopFraudValidationsPageFunctionality(oBrowser));
    }

    @Then("User validates the filter functionality on StopFraud-Validations lookup page")
    public void userValidatesTheFilterFunctionalityOnStopFraudValidationsLookupPage(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.validateSearchFunctionalitiesUnderStopFraudValidationLookup(oBrowser, dataTable));
    }

    @Then("User verify that the trashcan icon {string} visible")
    public void userVerifyThatTheTrashcanIconVisible(String visibility) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.verifyTrashIconVisibility(visibility),
                "Visibility is Trashcan is not working correctly");
    }

    @And("User perform Wave & Offers- Go to Wave")
    public void userPerformWaveOffersGoToWave() {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.validateGoToWavePage(oBrowser));
    }

    @Then("User fills out Enroll Details and validate the Case Pend Logic in Activities Tab")
    public void userFillsOutEnrollDetailsAndValidateTheCasePendLogicInActivitiesTab(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.validateCasePendLogicForEnrollData(oBrowser, dataTable));
    }

    @And("User navigates to Daily grind program management waves using the query {string}")
    public void userNavigatesToDailyGrindProgramManagementWavesUsingTheQuery(String queryKey) {
        Assert.assertNotNull(payCRM_modules_DailyGrindUIBaseStep.navigateAndSelectDailyGrindProgramManagements(oBrowser, queryKey));
    }

    @Then("User navigates to Waves grid and validates Activities tab functionality")
    public void userNavigatesToWavesGridAndValidatesActivitiesTabFunctionality() {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.validatesActivitiesTabFunctionality(oBrowser));
    }

    @Then("User navigates to Suppliers and create missing enrollment cases using query {string}")
    public void userNavigatesToSuppliersAndCreateMissingEnrollmentCases(String queryKey) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.createMissingEnrollmentCases(oBrowser, queryKey));
    }

    @And("User validates Recorded Enrollments and Additional Information Tab fields")
    public void userValidatesRecordedEnrollmentsAndAdditionalInformationTabFields() {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.validateElementsUnderRecordedEnrollmentsAndAdditionalInformationTab(oBrowser));
    }

    @And("User create, edit and validate the activities")
    public void userCreateEditAndValidateTheActivities(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.createAndEditActivity(oBrowser, dataTable));
    }

    @And("User validates Logs, Related cases, Address, Contacts and Transactions tab columns")
    public void userValidatesLogsRelatedCasesAddressContactsAndTransactionsTabColumns() {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.validateEnrollmentCasesTabsColumns(oBrowser));
    }

    @Then("User creates new supplier from supplier tab and enroll new case for {string}")
    public void user_creates_new_supplier_from_supplier_tab_and_enroll_new_case_for(String paymentType) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.createSupplier(oBrowser, paymentType));
    }

    @And("User reopen the closed cases and validate the same")
    public void userReopenTheClosedCasesAndValidateTheSame(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.reopenClosedCasesAndValidate(oBrowser, dataTable));
    }

    @Given("User apply company level setting as {string} and wave level settings as {string}")
    public void user_apply_company_level_setting_as_and_wave_level_settings_as(String companyLevelSetting, String waveLevelSetting) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.applyPAFSettings(oBrowser, companyLevelSetting, waveLevelSetting));
    }

    @Then("User perform Payment switch activities for {string} combination")
    public void userPerformPaymentSwitchActivitiesForCombination(String switchVariant, DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.performPaymentSwitchAndValidateTheSame(oBrowser, switchVariant, dataTable));
    }

    @Then("User navigate to stopFraud and mark TGBR as UnableToValidate and create Pre-Note Failure case in support case")
    public void navigateStopFraud_TGBR_UnableToValidate_PreNoteFailureCaseCreate() {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.getAccountValidationID_CreatePreNoteFailureCase(oBrowser));
    }

    @Then("User search for PayNetID as {string} then validates Hold and release functionality from StopFraud validation lookup page")
    public void verifyHoldAndReleaseFunctionality(String queryKey) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.validatePaymentHoldAndRelease(oBrowser, queryKey));
    }

    @Then("User verify support case grid")
    public void verifySupportCaseGridAndTabs() {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.verifySupportCaseGrid(oBrowser));
    }

    @Then("Verify support case link based on permission")
    public void verifySupportCasePermission(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.verifySupportCasePermission(oBrowser, dataTable));
    }

    @Then("User validates different types of support cases")
    public void userValidatesDifferentTypesOfSupportCases(DataTable supportCaseTypes) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.verifySupportCaseTypeValidations(oBrowser, supportCaseTypes));
    }

    @Then("User navigates to Daily Grind-->StopFraud and validates the {string} button functionality")
    public void userNavigatesToDailyGrindStopFraudAndValidatesTheManualValidateButtonFunctionality(String tinValidationType, DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.validateTIN_Manual_Auto_ValidateFunctionality(oBrowser, tinValidationType, dataTable));
    }

    @Then("User validate automatic support cases type information tiles based on different area name")
    public void user_validate_automatic_support_cases_type_information_tiles_based_on_different_area_name() {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.verifyAdditionalFieldsForAutomaticCases(oBrowser));
    }

    @Then("User generate multiple validations from PayNet and verify validations from support case")
    public void generateAndVerifyMultipleValidations() {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.generateAndVerifyMultipleValidations(oBrowser));
    }

    @And("Navigate to Switch Payment Type report and validate the payment switch entry")
    public void navigateToSwitchPaymentTypeReportAndValidateThePaymentSwitchEntry() {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.validateTheSwitchPaymentTypeReportForPaymentSwitchEntry(oBrowser));
    }

    @Then("User validates Support cases - Email Log Activity Fields")
    public void userValidatesSupportCasesEmailLogActivityFields() {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.validateSupportCaseEmailLogActivityFields(oBrowser));
    }

    @And("User validates Support cases - Email Log Activity Create For Different Types")
    public void userValidatesSupportCasesEmailLogActivityCreateForDifferentTypes(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.createAndValidateLogActivityForDifferentTypes_SupportCases(oBrowser, dataTable));
    }

    @And("Validate Send Email functionality from Daily Grind cases")
    public void validateSendEmailFunctionalityFromDailyGrindCases(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.validateSendMailFromDailyGrindCases(oBrowser, dataTable));
    }

    @And("Validate Generate PAF Send Email functionality from Daily Grind cases")
    public void validateGeneratePAFSendEmailFunctionalityFromDailyGrindCases(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.validateGeneratePAFSendEmailFromDailyGrindCases(oBrowser, dataTable));
    }

    @Then("Validate user can Login to PayNet from Support cases - Show page")
    public void validateUserCanLoginToPayNetFromSupportCasesShowPage() {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.validateLoginToPayNETFromSupportCaseShowPage(oBrowser));
    }

    @And("User validates Support cases {string} Internal Attachment and validate download attachment from show dialog")
    public void userValidatesSupportCasesInternalAttachment(String action, DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.validateSupportCaseInternalAttachment(oBrowser, action, dataTable));
    }

    @Then("User validates the Unable to validate case for {string} support case")
    public void userValidatesTheUnableToValidateCaseForManualSupportCase(String originationSource, DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.userPerformUnableToValidateSupportCaseScenario(oBrowser, originationSource, dataTable));
    }

    @Then("User validates the {string} support case for case type as {string} and area name as {string}")
    public void userValidateValidationCompleteFlow(String originationSource, String caseType, String areaName, io.cucumber.datatable.DataTable dataGrid) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.userPerformValidationCompleteSupportCase(oBrowser, originationSource, caseType, areaName, dataGrid));
    }

    @And("User validates Reports of Support Case")
    public void userValidatesReportsOfSupportCase() {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.validateReportsForSupportCase(oBrowser));
    }

    @Then("User validates reassign functionality for the Support Case")
    public void userValidatesReassignFunctionalityForTheSupportCase() {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.validateSupportCaseReassignFunctionality(oBrowser));
    }

    @Then("User validates Support case should be created when the case is marked {string} from stop fraud")
    public void userValidatesSupportCaseShouldBeCreatedWhenTINValidationCaseIsMarkedUnableToValidateFromStopFraud(String caseOutcomeReason, DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.validateSupportCasesAreMarkedUnableToValidateFromStopFraud(oBrowser, caseOutcomeReason, dataTable));
    }

    @Then("User validate support case grid columns with searching, sorting and filtering ActionDate and Country code column")
    public void user_validate_support_case_grid_columns_with_searching_sorting_and_filtering_action_date_and_country_code_column() {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.verifySupportCaseGridColumn(oBrowser));
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.verifySearchingSortingOperationOnColumn(oBrowser));
    }

    @Given("User validate Closed tab functionality")
    public void user_validate_closed_tab_functionality() {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.verifyClosedTabFilterFunctionality(oBrowser));
    }

    @Then("open Closed support case and verify Log notes functionality")
    public void open_closed_support_case_and_verify_log_notes_functionality() {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.verifySupportCaseLogNote(oBrowser));
    }

    @Then("User opens {string} enrollment Case from Program Management And User Enroll the cases for Offer type {string} and Was this offer accepted as {string}")
    public void userCreatesSupplierFromProgramManagementAndValidatesTheSame(String enrollmentCaseType, String offerType, String isOfferAccepted, DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.userCreatesNewEnrollmentCaseUnderProgramManagementAndEnrollWithSpecificOfferTypeAndOfferAccepted(oBrowser, enrollmentCaseType, offerType, isOfferAccepted, dataTable));
    }


    @And("User {string} the following {string} roles And perform Link OR unlink Users To selected {string} Work Queue")
    public void userChooseTheRolesAndPerformLinkORUnlinkUsersToSelectedWorkQueue(String permissionAction, String permissionNames, String workQueueNameAndActions) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.userChooseTheRolesAndLinkWorkQueueToTheSelectedUser(oBrowser, permissionAction, permissionNames, workQueueNameAndActions));
    }

    @And("Create supplier and enroll it with offer type ACH Plus")
    public void createSupplierAndEnrollWithACHPlusOffer(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.createSupplierAndEnrollWithACHPlusOffer(oBrowser, dataTable));
    }

    @And("Navigate to StopFraud and SupportCases and verify Audit Info table details")
    public void navigateToStopFraudAndSupportCasesAndVerifyAuditInfoTableDetails() {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.fetchAndValidateAuditInfoTableData(oBrowser));
    }

    @Then("User performs Clear Unable to Validate and verifies against stop fraud")
    public void userPerformsClearUnableToValidateAndVerifiesAgainstStopFraud() {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.verifyClearUnableToValidate(oBrowser));
    }

    @Then("User verifies case outcome reason using {string} case for support case")
    public void userVerifiesCaseOutcomeReasonUsingCaseForSupportCase(String caseType) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.verifyCaseOutComeReason(oBrowser, caseType));
    }

    @Then("User verifies all related cases inside Related Case tab")
    public void userVerifiesAllRelatedCasesInsideRelatedCaseTab() {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.verifyRelatedCases(oBrowser));
    }

    @Given("user login to the {string} application using {string} credentials")
    public void user_login_to_the_application_using_credentials(String string, String string2) {
        enrollmentManagersUIBaseSteps.loginToPayCRMWithAnotherUser1(string, string2);
    }

    @Then("Click on companies and edit the details")
    public void click_on_companies_and_edit_the_details(String companyStatus, DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.editAndValidateTheCompanyDetails1(oBrowser, companyStatus, dataTable));
    }
}
