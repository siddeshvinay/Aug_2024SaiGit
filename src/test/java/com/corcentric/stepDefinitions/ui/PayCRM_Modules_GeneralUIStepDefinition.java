package com.corcentric.stepDefinitions.ui;

import com.corcentric.runner.CucumberTestRunner;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import java.util.List;

public class PayCRM_Modules_GeneralUIStepDefinition extends CucumberTestRunner {
    @And("User navigates to {string} Modules and verifies presence of all the column names in the Companies Grid")
    public void userNavigatesToModulesAndVerifiesPresenceOfAllTheColumnNamesInTheCompaniesGrid(String generalTypes, DataTable dataTable) {
        Assert.assertTrue(appDep.validateTheGeneralModulesTableColumnNames(oBrowser, generalTypes, dataTable));
    }

    /*@Then("User creates the {string} contacts from General Modules")
    public void userCreatesTheContactsFromGeneralModules(String contactStatus, DataTable dataTable) {
       Assert.assertTrue(payCRM_modules_generalUIBaseStep.createAndValidateNewContactsGeneralModules(oBrowser, contactStatus, dataTable));
    }*/

    @Then("User perform the {string} contacts from General Modules")
    public void userPerformTheContactsFromGeneralModules(String generalType, DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.editAndValidateNewContactsGeneralModules(oBrowser, generalType, dataTable));
    }

    @Then("User deletes the contacts from General Modules")
    public void userDeletesTheContactsFromGeneralModules() {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.deleteContactsForGeneralModules(oBrowser));
    }

    @Then("User create the {string} company and validate the same successful")
    public void userCreateTheCompanyAndValidateTheSameSuccessful(String companyStatus, DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.createAndValidateTheCompanyDetails(oBrowser, companyStatus, dataTable));
    }

    @Then("User {string} the company details and validate the same successful")
    public void userEditTheCompanyDetailsAndValidateTheSameSuccessful(String companyStatus, DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.editAndValidateTheCompanyDetails(oBrowser, companyStatus, dataTable));
    }

    @Then("User apply the {string} filter and search the Suppliers data using query {string}")
    public void userApplyTheFilterAndSeachTheSuppliersData(String valueType, String queryKey, DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.applyFilterAndSearchSuppliersData(oBrowser, valueType, queryKey, dataTable));
    }

    @And("User perform Link Update cases and validate the same")
    public void userPerformLinkUpdateCasesAndValidateTheSame(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.linkUpdateForCasesAndValidate(oBrowser, dataTable));
    }

    @Then("User navigates to Suppliers page and validate the Validations tab UI elements")
    public void userNavigatesToSuppliersPageAndValidateTheValidationsTabUIElements() {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.validateSuppliersValitionsTabElements(oBrowser));
    }

    @Then("User navigates to prospect companies & validates the Other provider approach section under Solution design tab using the query {string}")
    public void userNavigatesToProspectCompaniesValidatesTheOtherProviderApproachSectionUnderSolutionDesignTab(String queryKey) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.validateOtherProviderApproachUIElementsUnderSolutionDesign(oBrowser, queryKey));
    }

    @And("User validates the Update Duplicates From Parent under Solution design->Suppliers tab using the query {string}")
    public void userValidatesTheUpdateDuplicatesFromParentUnderSolutionDesignSuppliersTab(String queryKey) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.updateDuplicateSupplierFromParent(oBrowser, queryKey));
    }

    @Then("User creates new {string} attachment and validate the same")
    public void userCreatesNewSupplierAttachmentAndValidateTheSame(String pageName, DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.createNewSupplierAttachmentAndValidateTheSame(oBrowser, pageName, dataTable));
    }

    @Then("User edit the {string} attachment and validate the same")
    public void userEditTheSupplierAttachmentAndValidateTheSame(String pageName, DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.editSupplierAttachmentAndValidateTheSame(oBrowser, pageName, dataTable));
    }

    @And("User downloads and deletes the {string} attachment")
    public void userDownloadsAndDeletesTheSupplierAttachment(String pageName) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.downloadAndDeleteTheSupplierAttachment(oBrowser, pageName));
    }

    @Then("User navigates to client companies, Export {string} and validate the same")
    public void userNavigatesToClientCompaniesExportCasesAndValidateTheSame(String pageName) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.exportClientCompanyCasesORAttachmentsAndValidateTheSame(oBrowser, pageName));
    }


    @And("User add the {string} Contacts for the client Company and validates the same")
    public void userAddTheForTheClientCompanyAndValidatesTheSame(String contactStatus, DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.createOREditContactsForClientCompaniesAndValidate(oBrowser, contactStatus, dataTable));
    }

    @Then("User perform the {string} Contacts for client Company and validates the same")
    public void userPerformTheContactsForClientCompanyAndValidatesTheSame(String contactStatus, DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.createOREditContactsForClientCompaniesAndValidate(oBrowser, contactStatus, dataTable));
    }

    @Then("User exports the client company Contacts and validate the same")
    public void userExportsTheClientCompanyContactsAndValidateTheSame() {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.exportClientCompanyContactsAndValidateTheSame(oBrowser));
    }


    @And("User Imports the SFDC Contacts & deletes the client company Contacts and validates the same")
    public void userImportsTheSFDCContactsDeletesTheClientCompanyContactsAndValidatesTheSame() {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.importSFDCAndDeleteTheContacts(oBrowser));
    }

    @And("User exports the {string} Attachment and validates the same")
    public void userExportsTheAttachmentAndValidatesTheSame(String pageName) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.exportSupplierAttachmentsAndValidateTheSame(oBrowser, pageName));
    }

    @Then("User exports client company Waves and Open in New Tab")
    public void userExportsClientCompanyWavesAndOpenInNewTab() {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.exportAndOpenInNewTabForTheClientCompanyWaves(oBrowser));
    }

    @Then("User Edits the client company {string} detail & validates the same")
    public void userEditsTheClientCompanySuppliersDetailValidatesTheSame(String pageName, DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.editClientCompnaySuppliersAndValidateTheSame(oBrowser, pageName, dataTable));
    }

    @Then("User exports client company {string} and Open in New Tab")
    public void userExportsClientCompanySuppliersAndOpenInNewTab(String pageName) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.exportAndOpenInNewTabForTheClientCompanySuppliers(oBrowser, pageName));
    }

    @Then("User validates exports icon was removed from the client companies Case Activities")
    public void userExportsTheClientCompaniesCaseActivitiesAndValidatesTheSame() {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.validateExportIconWasRemovedFromClientCompanyCaseActivities(oBrowser));
    }

    @Then("User navigates to internal setup and updates the global variable")
    public void userNavigatesToInternalSetupAndUpdatesTheGlobalVariable() {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.updateGlobalVariableFromInternalSetup(oBrowser));
    }

    @Then("User navigates to internal setup and updates the Preferences")
    public void userNavigatesToInternalSetupAndUpdatesThePreferences(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.updatePreferencesFromInternalSetup(oBrowser, dataTable));
    }

    @Then("User navigates to internal setup and updates the Expected section")
    public void userNavigatesToInternalSetupAndUpDatesTheExpectedSection(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.updateExpectedPercentagesFromInternalSetup(oBrowser, dataTable));
    }

    @Then("User navigates to internal setup and updates the Client Rebate Percentage")
    public void userNavigatesToInternalSetupAndUpdatesTheClientRebatePercentage(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.updateClientRebatePercentageFromInternalSetup(oBrowser, dataTable));
    }

    @Then("User navigates to internal setup and updates the Corcentric Payment Mix")
    public void userNavigatesToInternalSetupAndUpdatesTheCorcentricPaymentMix(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.updateCorcentricPaymentMixFromInternalSetup(oBrowser, dataTable));
    }

    @Then("User navigates to internal setup and updates the Current Payment Mix")
    public void userNavigatesToInternalSetupAndUpdatesTheCurrentPaymentMix(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.updateCurrentPaymentMixFromInternalSetup(oBrowser, dataTable));
    }

    @Then("User navigates to internal setup and updates the Card Network Match Confidence")
    public void userNavigatesToInternalSetupAndUpdatesTheCardNetworkMatchConfidence(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.updateCardNetworkMatchConfidenceFromInternalSetup(oBrowser, dataTable));
    }

    @Then("User validates the {string} page Test functionality of Prospect company")
    public void userValidatesTheTestFunctionalityOfProspectCompany(String pageName) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.validateTheTestFunctionalityOfPropsectCompany(oBrowser, pageName));
    }

    @Then("User validates the hover over total cost column values")
    public void userValidatesTheHoverOverTotalCostColumnValues() {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.hoverOverTotalCostColumnValues(oBrowser));
    }

    @And("User validates Client Link Information data")
    public void userValidatesClientLinkInformationData(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.validateClientLinkInformationData(oBrowser, dataTable));
    }

    @Then("Click on View PayNet Link and verify data pulled from PayNet")
    public void clickOnViewPayNetLinkAndVerifyDataPulledFromPayNet() {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.clickOnViewPayNetLinkAndValidateTheSame(oBrowser));
    }

    @And("User {string} new access code and validates the same")
    public void userCreatesNewAccessCodeAndValidatesTheSame(String accessCodeStatus, DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.createEditAccessCodeAndValidatesTheSame(oBrowser, accessCodeStatus, dataTable));
    }

    @Then("User perform assign and remove the work queue")
    public void userPerformAssignAndRemoveTheWorkQueue(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.assignAndRemoveQueues(oBrowser, dataTable));
    }

    @Then("User navigates to internal setup and updates the Tab Visibility")
    public void userNavigatesToInternalSetupAndUpdatesTheTabVisibility(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.updateTabVisibilityFromInternalSetup(oBrowser, dataTable));
    }

    @Then("User navigates to internal setup and updates the Current State")
    public void userNavigatesToInternalSetupAndUpdatesTheCurrentState(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.updateCurrentStateFromInternalSetup(oBrowser, dataTable));
    }

    @Then("User validates Supplier Type Matches under Solution Design section of prospect company using the query {string}")
    public void userValidatesSupplierTypeMatchesUnderSolutionDesignSectionOfProspectCompany(String queryKey) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.validateSupplierTypeMatches(oBrowser, queryKey));
    }

    @Then("User validates MCC Matches under Solution Design Section of the prospect company using the query {string}")
    public void userValidatesMCCMatchesUnderSolutionDesignSectionOfTheProspectCompany(String queryKey) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.validateMCCMatches(oBrowser, queryKey));
    }

    @Then("User navigates to internal setup and updates the Corcentric Analysis")
    public void userNavigatesToInternalSetupAndUpdatesTheCorcentricAnalysis(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.updateCorcentricAnalysisFromInternalSetup(oBrowser, dataTable));
    }

    @And("User validates tabs under selected suppliers by using query {string}")
    public void userValidatesTabsUnderSelectedSuppliers(String queryKey) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.validateTheSupplierTabs(oBrowser, queryKey));
    }

    @Then("User creates {string} Universal Payment Process and validates the same")
    public void userCreatesUniversalPaymentProcessAndValidatesTheSame(String paymentStatus, DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.createNewUniversalPaymentProcess(oBrowser, paymentStatus, dataTable));
    }

    @Then("User Validates Generate Provider Files, Import Suppliers and Import Munches functionalities")
    public void userValidatesGenerateProviderFilesImportSuppliersAndImportMunchesFunctionalities() {
        //Assert.assertTrue(payCRM_modules_generalUIBaseStep.validateAttachmentFunctionalityForProspectCompany(oBrowser));
    }

    @And("User perform {string} action on grid first record and navigates to {string} tab")
    public void userPerformActionOnGridFirstRecordAndNavigatesToTab(String action, String tabName) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.actionOnTheFirstRowOfTheGrid(oBrowser, action));
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.verifyTabFromWaveDetailPage(oBrowser, tabName));
    }

    @Then("User creates new payment process record from payment process tab")
    public void userCreatesNewPaymentProcessRecordFromPaymentProcessTab(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.createNewRecordForPaymentProcessTab(oBrowser, dataTable));
    }

    @And("User verify that {string} tab grid first record is present")
    public void userVerifyThatTabGridFirstRecordIsPresent(String tabName, DataTable dataTable) {
        dataTable.asMaps().stream().forEach( object ->
            Assert.assertTrue(payCRM_modules_generalUIBaseStep.verifyTabGridRecord(oBrowser, tabName, object)));
    }

    @Then("User validates Related Cases links on popup")
    public void userValidatesRelatedCasesLinksOnPopup(List<String> listOfLinks) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.verifyAvailableLinksOnPopup(oBrowser, listOfLinks));
    }

    @Then("User Validates Generate Provider Files")
    public void userValidatesGenerateProviderFiles() {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.generateProviderFile(oBrowser));
    }

    @And("User perform Import Suppliers and validates the same")
    public void userPerformImportSuppliersAndValidatesTheSame(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.userImportsSupplierFileAndValidateTheSame(oBrowser, dataTable));
    }

    @And("User perform Import Matches and validates the same")
    public void userPerformImportMatchesAndValidatesTheSame(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.userImportsMatchesFileAndValidateTheSame(oBrowser, dataTable));
    }

    @Then("User perform {string} attachment under other attachments section")
    public void userPerformAttachmentUnderOtherAttachmentsSection(String attachmentStatus, DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.createAttachmentUnderOtherAttachments(oBrowser, attachmentStatus, dataTable));
    }

    @And("User deletes the attachment from Other attachment section")
    public void userDeletesTheAttachmentFromOtherAttachmentSection() {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.deleteAttachmentFromOtherAttachments(oBrowser));
    }

    @Then("User enters {string} Information details")
    public void userEntersGeneralInfromationDetails(String accountSection, DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.enterDetailsForTestAccountSetupForm(oBrowser, accountSection, dataTable));
    }

    @Then("User enters {string} information details")
    public void userEnterInformationDetails(String accountSection, DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.enterDetailsForTestAccountSetupForm(oBrowser, accountSection, dataTable));
    }

    @Then("User enters {string} details")
    public void userEntersDetails(String accountSection, DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.enterDetailsForTestAccountSetupForm(oBrowser, accountSection, dataTable));
    }

    @And("User navigates to {string} tab from waves page and perform {string} action on grid record")
    public void userNavigatesToTabFromWavesPageAndPerformActionOnGridRecord(String action, String tabName) {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.verifyTabFromWaveDetailPage(oBrowser, action));
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.actionOnTheFirstRowOfTheGrid(oBrowser, tabName));
    }
    @And("User navigates to internal setup and updates Program Costs")
    public void userNavigatesToInternalSetupAndUpdatesProgramCosts(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.updateProgramCostsFromInternalSetup(oBrowser, dataTable));
    }

    @And("User navigate to {string} ribbon from prospect companies")
    public void userNavigateToRibbonFromProspectCompanies(String ribbonName) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.navigateToProspectCompanyGoLive(oBrowser, "Type","Prospect", ribbonName));
    }

    @Then("User click on generate and show preview and verify cards data")
    public void userClickOnGenerateAndShowPreviewAndVerifyCardsData() {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.verifyGoLiveShowPreviewAndCardFunctionality(oBrowser));
    }

    @Then("User click on each cards and verify each cards")
    public void userClickOnEachCardsAndVerifyEachCards() {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.verifyGoLiveCardsData(oBrowser));
    }

    @Then("User verify PAF validation as")
    public void user_verify_PAF_validation_as(io.cucumber.datatable.DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.verifyPAFSetting(oBrowser, dataTable));
    }

    @And("User verify Re-Open functionality of enroll case")
    public void user_verify_re_open_functionality_of_enroll_case() {
        Assert.assertTrue(payCRM_modules_DailyGrindUIBaseStep.verifyEnrollCaseReOpenFlow(oBrowser));
    }

    @And("User navigates and select the general suppliers using the query {string}")
    public void userNavigatesAndSelectTheGeneralSuppliersUsingTheQuery(String queryKey) {
        Assert.assertNotNull(payCRM_modules_generalUIBaseStep.navigateAndSelectGeneralSupplier(oBrowser, queryKey));
    }

    @And("User navigates and select the General {string} Company by using the query {string}")
    public void userNavigatesAndSelectTheGeneralCompanyByUsingTheQuery(String companyType, String queryKey) {
        Assert.assertNotNull(payCRM_modules_generalUIBaseStep.navigateAndSelectGeneralCompanies(oBrowser, companyType, queryKey));
    }


    @And("User updates the Preferences tab values for the client company")
    public void userUpdatesThePreferencesTabValuesForTheClientCompany(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.updateAndValidateClientCompanyPreferences(oBrowser, dataTable));
    }

    @Then("User validates the GoLive UI Functionality and post result validations")
    public void userValidatesTheGoLiveUIFunctionalityAndPostResultValidations() {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.validateGoliveUIAndPostResults(oBrowser));
    }

    @Then("User create the New Prospect company and validate that Merged Suppliers tab is available under Solution Design")
    public void userCreateTheProspectCompanyAndValidateThatMergedSuppliersTabIsAvailableUnderSolutionDesign(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.createProspectCompanyAndValidateMergedSuppliersTab(oBrowser, dataTable));
    }

    @And("User validates Merged Suppliers tab can be shown or hidden via the internal setup tab")
    public void userValidatesMergedSuppliersTabCanBeShownOrHiddenViaTheInternalSetupTab() {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.validateShowORHideMergedSupplierTabViaInternalSetup(oBrowser));
    }

    @Then("User create the New Prospect company to validate the automatic Merge Suppliers functionality")
    public void userCreateTheNewProspectCompanyToValidateTheAutomaticMergeSuppliersFunctionality(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.createProspectCompanyForMergeSuppliers(oBrowser, dataTable));
    }

    @And("User imports the suppliers which are having duplicate supplier details, validate the automatic merge supplier and Undo merged suppliers functionality")
    public void userImportsTheSuppliersWhichAreHavingDuplicateSupplierDetailsValidateTheAutomaticMergeAndUndoMergeSupplierFunctionality(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.userImportsSuppliersAndValidateAutomaticMergeAndUndoMergeSupplierFunctionality(oBrowser, dataTable));
    }

    @And("User imports the suppliers which are having unique supplier details, validate that the automatic merging of suppliers should not happen")
    public void userImportsTheSuppliersWhichAreHavingUniqueSupplierDetailsValidateThatTheAutomaticMergingOfSuppliersShouldNotHappen(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.userImportsUniqueSuppliersAndValidatesAutomaticMergingOfSuppliersShouldNotHappen(oBrowser, dataTable));
    }

    @Then("User verify support case grid and related data from supplier detail page")
    public void userVerifySupportCaseGridAndDataFromSupplierPage() {
       Assert.assertTrue(payCRM_modules_generalUIBaseStep.createSupportCasesForSuppliers(oBrowser));
    }

    @Then("User opens {string} prospect company and Validates the preferences option to show or hide Payment Score, Risk Score tiles & Corcentric Features tiles and tables")
    public void userOpensProspectCompanyAndValidatesThePreferencesOptionToShowHide_PaymentScore_RiskScoreTilesAndCorcentricFeaturesTables(String companyStatus, DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.userOpensProspectCompanyAndValidateShowHide_PaymentScore_RiskScoreTilesAndCorcentricFeaturesTablesOfProspectModules(oBrowser, companyStatus, dataTable));
    }

    @Then("User creates new prospect company and validates the default benchmark values under Internal setup-->Expected section of Prospect company")
    public void userCreatesNewProspectCompanyAndValidatesTheDefaultBenchmarkValuesUnderInternalSetupExpectedSectionOfProspectCompany(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.userValidatesDefaultBenchMarkValuesUnderInternalSetUp_ExpectedSectionForProspectCompany(oBrowser, dataTable));
    }

    @Then("User opens {string} prospect company and Validates the preferences option to hide or un-hide the Total Cost & Net benefits from the Optimized Program Cost table")
    public void userOpensProspectCompanyAndValidatesThePreferencesOptionToHideUnHideTheTotalCostNetBenefitsFromTheOptimizedProgramCostTable(String companyStatus, DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.userOpensProspectCompanyAndValidateShowHide_TheTotalCostNetBenefitsFromTheOptimizedProgramCostTableOfProspectModules(oBrowser, companyStatus, dataTable));
    }

    @Then("User create new prospect company and prepare the MVF file as required")
    public void userCreatesProspectCompanyAndPrepareTheMVFFileAsRequired(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.userCreatesProspectCompanyAndPreparesMVFFile(oBrowser, dataTable));
    }

    @And("User imports the suppliers and Validates the update or replace the Network Name & TIN Match Calculations")
    public void userImportsTheSuppliersAndValidatesTheUpdateOrReplaceTheNetworkNameTINMatchCalculations(DataTable dataTable) {
        Assert.assertTrue(payCRM_modules_generalUIBaseStep.userImportSuppliersAndValidateUpdate_ReplaceNetworkNameAndTINMatchCalculations(oBrowser, dataTable));
    }
}
