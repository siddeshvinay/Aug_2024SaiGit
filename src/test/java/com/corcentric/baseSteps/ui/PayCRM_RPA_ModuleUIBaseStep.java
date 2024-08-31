package com.corcentric.baseSteps.ui;

import com.corcentric.pages.AssistedPaymentServicesUIPage;
import com.corcentric.pages.PartnerEnrollmentsUIPage;
import com.corcentric.pages.PayCRMMainUIPage;
import com.corcentric.pages.PayCRM_Modules_GeneralUIPage;
import com.corcentric.pages.PayCRM_RPA_ModuleUIPage;
import io.cucumber.datatable.DataTable;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.testng.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PayCRM_RPA_ModuleUIBaseStep extends CommonUIBase {
    /********************************************************
     * Method Name      : navigateToRPACaseGrid()
     * Purpose          : Navigate to RPA Case, verify grid and select 1st record
     * Author           : Deepak
     * Parameters       : oBrowser, tableID
     * ReturnType       : boolean
     ********************************************************/
    public boolean navigateToRPACaseGrid(WebDriver oBrowser, String headerID) {
        List<String> firstRecord = new ArrayList<>();
        List<String> userEmailList = new ArrayList<>();
        try {
            appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_payCRM_NavigateBar_Link);
            appDep.waitForTheElementState(oBrowser, By.xpath(String.format(PayCRM_RPA_ModuleUIPage.RPA_TILE_PATH, "RPA")), "visibility", "", waitTimeOut);
            if (appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_RPA_ModuleUIPage.RPA_TILE_PATH, "RPA")))) {
                reports.writeResult(oBrowser, "screenshot", "RPA tile is visible on page");
                Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "Queues"));
                appInd.clickObject(oBrowser, By.xpath("//div[@id='divWorkQueuesContainer']//tbody//td[contains(text(),'CMVP')]"));
                appDep.threadSleep(1000);
                reports.writeResult(oBrowser, "screenshot", "CMVP option is visible in grid");
                appInd.switchToLastWindow(oBrowser);
                appDep.waitForTheElementState(oBrowser, By.xpath("//li/a[text()='Users']"), "visibility", "", waitTimeOut);
                appInd.clickObject(oBrowser, By.xpath("//li/a[text()='Users']"));
                appDep.threadSleep(1000);
                reports.writeResult(oBrowser, "screenshot", "CMVP > Users list is visible in grid");
                appInd.getWebElements(oBrowser, By.xpath("(//table)[2]//tr/td[2]")).forEach(element -> {
                    if (!element.getText().equals(""))
                        userEmailList.add(element.getText().trim());
                });
                oBrowser.close();
                appInd.switchToLastWindow(oBrowser);
                appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_payCRM_NavigateBar_Link);
                appInd.clickObject(oBrowser, By.xpath(String.format(PayCRM_RPA_ModuleUIPage.RPA_TILE_PATH, "RPA").concat("/../..")));
                Assert.assertTrue(verifyRPAGrid(headerID));
                //----------------- Collect record data and click 1st record ------------------
                appInd.getWebElements(oBrowser, By.xpath(String.format(PayCRM_Modules_GeneralUIPage.obj_Grid_Column_FirstRecord_Data, headerID)))
                        .forEach(webElement -> firstRecord.add(webElement.getText().trim()));
                reports.writeResult(oBrowser, "screenshot", "We successfully collect 1st record from RPA Grid");
                appInd.clickObject(oBrowser, By.xpath(String.format(PayCRM_Modules_GeneralUIPage.obj_Grid_Column_FirstRecord, headerID)));
                appDep.threadSleep(2000);
                appInd.switchToLastWindow(oBrowser);
                //----------------- Click on 1st record from RPA grid and verify data ------------------
                appDep.waitForTheElementState(oBrowser, PayCRM_RPA_ModuleUIPage.RPA_REASSIGN_BTN, "visibility", "", waitTimeOut);
                reports.writeResult(oBrowser, "screenshot", "RPA Detail page is visible");
                reports.writeResult(oBrowser, "pass", "RPA Case Detail page open");
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_RPA_ModuleUIPage.RPA_REASSIGN_BTN));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_RPA_ModuleUIPage.RPA_TILE_PATH, "Payment Help"))));
                //Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_RPA_ModuleUIPage.RPA_TILE_PATH, "Invoices"))));
                //Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_GeneralUIPage.obj_tab_names, "Payment Data"))));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_GeneralUIPage.obj_tab_names, "Activities"))));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_GeneralUIPage.obj_tab_names, "Logs"))));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_RPA_ModuleUIPage.RPA_CASE_CARD_DATA, firstRecord.get(0)))));
                Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath("(//div[@class='card']/dl/dd/a[@title])[1]"), "text"), firstRecord.get(1));
                Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath("(//div[@class='card']/dl/dd/a[@title])[3]"), "text"), firstRecord.get(2));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//div[@class='card']/dl/dd[@class='col-md-9 show-read-more' and contains(text(),'"+firstRecord.get(3)+"')]")));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_RPA_ModuleUIPage.RPA_CASE_CARD_DATA, "RPA"))));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_RPA_ModuleUIPage.RPA_SUCCESS_BTN));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_RPA_ModuleUIPage.RPA_FAIL_BTN));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_RPA_ModuleUIPage.RPA_REASSIGN_BTN));
                reports.writeResult(oBrowser, "pass", "RPA Case Detail Page Basic Verification Done");
                reports.writeResult(oBrowser, "screenshot", "RPA Detail page verification done");
                //----------------- Click on Reassign button and verify model ------------------
                appInd.clickObject(oBrowser, PayCRM_RPA_ModuleUIPage.RPA_REASSIGN_BTN);
                appDep.threadSleep(2000);
                reports.writeResult(oBrowser, "screenshot", "RPA Reassign model is visible");
                Assert.assertTrue(appDep.waitForTheElementState(oBrowser, PayCRM_RPA_ModuleUIPage.RPA_REASSIGN_MODEL, "visibility", "", waitTimeOut));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_RPA_ModuleUIPage.RPA_REASSIGN_MODEL_NOTES));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_RPA_ModuleUIPage.RPA_REASSIGN_MODEL_USER_ASSIGN_DROPDOWN));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_RPA_ModuleUIPage.RPA_REASSIGN_MODEL_SUBMIT));
                List<String> userDropdownEmail = new ArrayList<>();
                appInd.getWebElements(oBrowser, By.xpath("//select[@id='rpa_case_reassign_user']/option")).forEach(element -> {
                    if (!element.getText().equals(""))
                        userDropdownEmail.add(element.getText().trim());
                });
                reports.writeResult(oBrowser, "pass", "compare users email ids with CMVP > users email ids list");
                reports.writeResult(oBrowser, "screenshot", "compare users email ids with CMVP > users email ids list");
                Assert.assertTrue(userDropdownEmail.containsAll(userEmailList));
                appInd.clickObject(oBrowser, By.xpath("//div[@id='modal_reassign' and contains(@style,'block')]//button[@class='close']"));
                appDep.threadSleep(1000);
            } else {
                return false;
            }
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'navigateToRPACaseGrid()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'navigateToRPACaseGrid()' method. " + e);
            return false;
        }
        return true;
    }

    /********************************************************
     * Method Name      : verifyRPACaseReassignFunctionality()
     * Purpose          : Verify Reassign functionality
     * Author           : Deepak
     * Parameters       : 'oBrowser' as browser object, 'headerId' as grid div id
     * ReturnType       : boolean
     ********************************************************/
    public boolean verifyRPACaseReassignFunctionality(WebDriver oBrowser, String headerID) {
        //-------------------------- Verify Model validation ---------------------
        try {
            String caseID = appInd.getTextOnElement(oBrowser, By.xpath("(//div[@class='card']/dl/dd)[1]"), "text");
            reports.writeResult(oBrowser, "screenshot", "Collect RPA caseID for further use");
            appInd.clickObject(oBrowser, PayCRM_RPA_ModuleUIPage.RPA_REASSIGN_BTN);
            appDep.threadSleep(2000);
            Assert.assertTrue(appDep.waitForTheElementState(oBrowser, PayCRM_RPA_ModuleUIPage.RPA_REASSIGN_MODEL, "visibility", "", waitTimeOut));
            appInd.clickObject(oBrowser, PayCRM_RPA_ModuleUIPage.RPA_REASSIGN_MODEL_SUBMIT);
            appDep.threadSleep(2000);
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.id("reassign_notes-error"), "text"), "Please enter a note.");
            reports.writeResult(oBrowser, "pass", "RPA Reassign Model Validation trigger");
            reports.writeResult(oBrowser, "screenshot", "RPA reassign model validation verified");
            appInd.setObject(oBrowser, By.id("reassign_notes"), "Automation Regression Reassign note");
            appInd.clickObject(oBrowser, PayCRM_RPA_ModuleUIPage.RPA_REASSIGN_MODEL_SUBMIT);
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CompanyEditConfirmationMessage_Label, "Visibility", "", waitTimeOut);
            reports.writeResult(oBrowser, "screenshot", "reassign is successfully applied");
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CompanyEditConfirmationMessage_Label, "Text").contains("Successfully reassign the case to user"));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Activities_Activity_Btn));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.id("btn_reassign")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.id("btn_update_case_from_vnet_payment")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.id("btn_generate_invoices")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.id("btn_void_request")));
            reports.writeResult(oBrowser, "pass", "RPA Reassign successful");
            oBrowser.close();
            appInd.switchToLastWindow(oBrowser);
            appDep.threadSleep(1000);
            // record should not visible in grid, etc
            Assert.assertTrue(navigateToRecord(oBrowser, headerID, "Case #", caseID));
            reports.writeResult(oBrowser, "pass", "RPA Case Grid Should not visible reassign record in grid");
            reports.writeResult(oBrowser, "screenshot", "RPA Case Grid Should not visible reassign record in grid");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_GeneralUIPage.obj_Grid_Column_FirstRecord, headerID))));
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifyRPACaseReassignFunctionality()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifyRPACaseReassignFunctionality()' method. " + e);
            return false;
        }
        return true;
    }

    /********************************************************
     * Method Name      : RPAWillValidateTheCaseStatusToPerformTheSuitableActivities()
     * Purpose          : User uses RPA to validate the Case status and will decide the suitable activities viz., Success OR Fail
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean RPAWillValidateTheCaseStatusToPerformTheSuitableActivities(WebDriver oBrowser) {
        String caseNumber = null;
        List<WebElement> objEles = null;
        String paymentMethods = "";
        String invoiceID = null;
        String amount = null;
        try {
            enrollmentManagersUIBaseSteps.selectRequiredQueue(oBrowser, "RPA");
            for (int l = 0; l < 2; l++) {
                boolean isPaymentMethodPresent = false;
                boolean isInvoicePresent = false;
                boolean isPaymentAmountPresent = false;
                String statusMessage = "";
                caseNumber = appDep.navigateAndGetRPACaseNumber(oBrowser);
                appInd.verifyElementPresent(oBrowser, PayCRM_RPA_ModuleUIPage.obj_Success_Btn);
                appInd.verifyElementPresent(oBrowser, PayCRM_RPA_ModuleUIPage.obj_Fail_Btn);
                appInd.verifyElementPresent(oBrowser, PayCRM_RPA_ModuleUIPage.obj_Reassign_Btn);

                //Verify the case status to decide the respective activities
                if (appInd.verifyOptionalElementPresent(oBrowser, By.xpath(PayCRM_RPA_ModuleUIPage.obj_PaymentMethod_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[1]"))) {
                    isPaymentMethodPresent = true;
                    objEles = oBrowser.findElements(By.xpath(PayCRM_RPA_ModuleUIPage.obj_PaymentMethod_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[1]"));
                    for (int i = 0; i < objEles.size(); i++) {
                        paymentMethods += objEles.get(i).getText() + ",";
                    }
                    reports.writeResult(oBrowser, "Screenshot", "The Payment Methods are: " + paymentMethods);
                    statusMessage += "The Payment methods Exist: " + paymentMethods + "\n";
                } else {
                    reports.writeResult(oBrowser, "Screenshot", "There are no payment methods associated with the Case number: " + caseNumber);
                    statusMessage += "The Payment methods doesnot exist. \n";
                }

                if (appInd.verifyOptionalElementPresent(oBrowser, By.xpath(PayCRM_RPA_ModuleUIPage.obj_Invoice_Grid + "/table//tr[@data-toggle]/td[2]"))) {
                    isInvoicePresent = true;
                    invoiceID = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_RPA_ModuleUIPage.obj_Invoice_Grid + "/table//tr[@data-toggle]/td[2]"), "Text");
                    reports.writeResult(oBrowser, "Screenshot", "The Invoice ID is: " + invoiceID);
                    statusMessage += "The Invoice Exist: " + invoiceID + "\n";
                } else {
                    reports.writeResult(oBrowser, "Screenshot", "There are no Invoice Id's associated with the Case number: " + caseNumber);
                    statusMessage += "The Invoice doesnot Exist: \n";
                }

                appInd.scrollToElement(oBrowser, PayCRM_RPA_ModuleUIPage.obj_PaymentData_Link);
                if (appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_RPA_ModuleUIPage.obj_PaymentData_Grid + "//table//tr/td[text()='Amount']/following-sibling::td"), "Text").length() > 0) {
                    isPaymentAmountPresent = true;
                    amount = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_RPA_ModuleUIPage.obj_PaymentData_Grid + "//table//tr/td[text()='Amount']/following-sibling::td"), "Text");
                    reports.writeResult(oBrowser, "Screenshot", "The Amount is: " + amount);
                    statusMessage += "The Amount Exist: " + amount + "\n";
                } else {
                    reports.writeResult(oBrowser, "Screenshot", "There are no Amount associated with the Case number: " + caseNumber);
                    statusMessage += "The Amount doesnot Exist";
                }


                appInd.scrollToThePage(oBrowser, "Top");
                if (isPaymentMethodPresent == true && isInvoicePresent == true && isPaymentAmountPresent == true) {
                    reports.writeResult(oBrowser, "Info", "Performing 'Success' activity");
                    appInd.clickObject(oBrowser, PayCRM_RPA_ModuleUIPage.obj_Success_Btn);
                    appDep.waitForTheElementState(oBrowser, PayCRM_RPA_ModuleUIPage.obj_Success_Submit_Btn, "Clickable", "", minTimeOut);
                    appInd.verifyText(oBrowser, By.xpath(PayCRM_RPA_ModuleUIPage.obj_ModelSuccess_Dialog + "//h4[@class='modal-title']"), "Text", "Success");
                    appInd.setObject(oBrowser, PayCRM_RPA_ModuleUIPage.obj_SuccessNotes_TextArea, statusMessage);
                    reports.writeResult(oBrowser, "Screenshot", "After entering the details for the 'Success' action");
                    appInd.clickObject(oBrowser, PayCRM_RPA_ModuleUIPage.obj_Success_Submit_Btn);
                    oBrowser = enrollmentManagersUIBaseSteps.switchBackToParentWindowWithoutClosingChildWindow(oBrowser);
                    appDep.waitForTheElementState(oBrowser, PayCRM_RPA_ModuleUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                    appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_RPA_ModuleUIPage.obj_RPA_Cases_Grid + "//tr[contains(@class, 'lines dx-column-lines')][1]/td[text()='" + caseNumber + "']"));
                } else {
                    reports.writeResult(oBrowser, "Info", "Performing 'Fail' activity");
                    appInd.clickObject(oBrowser, PayCRM_RPA_ModuleUIPage.obj_Fail_Btn);
                    appDep.waitForTheElementState(oBrowser, PayCRM_RPA_ModuleUIPage.obj_Fail_Submit_Btn, "Clickable", "", minTimeOut);
                    appInd.verifyText(oBrowser, By.xpath(PayCRM_RPA_ModuleUIPage.obj_ModelFail_Dialog + "//h4[@class='modal-title']"), "Text", "Fail");
                    appInd.setObject(oBrowser, PayCRM_RPA_ModuleUIPage.obj_FailNotes_TextArea, statusMessage);
                    reports.writeResult(oBrowser, "Screenshot", "After entering the details for the 'Fail' action");
                    appInd.clickObject(oBrowser, PayCRM_RPA_ModuleUIPage.obj_Fail_Submit_Btn);
                    oBrowser = enrollmentManagersUIBaseSteps.switchBackToParentWindowWithoutClosingChildWindow(oBrowser);
                    appDep.waitForTheElementState(oBrowser, PayCRM_RPA_ModuleUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                    appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_RPA_ModuleUIPage.obj_RPA_Cases_Grid + "//tr[contains(@class, 'lines dx-column-lines')][1]/td[text()='" + caseNumber + "']"));
                    break;
                }
            }
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in ' RPAWillValidateTheCaseStatusToPerformTheSuitableActivities()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'RPAWillValidateTheCaseStatusToPerformTheSuitableActivities()' method. " + e);
            return false;
        } finally {
            caseNumber = null;
            objEles = null;
            paymentMethods = null;
            invoiceID = null;
            amount = null;
        }
    }

    /********************************************************
     * Method Name      : validateTheFailButtonStructureAndFunctionality()
     * Purpose          : User validates the Fail button structure and functionality under RPA case number
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateTheFailButtonStructureAndFunctionality(WebDriver oBrowser) {
        String caseNumber = null;
        String caseStatus = null;
        String strURL = null;
        String strFailNoteMessage = null;
        try {
            enrollmentManagersUIBaseSteps.selectRequiredQueue(oBrowser, "RPA");
            caseNumber = appDep.navigateAndGetRPACaseNumber(oBrowser);
            strURL = oBrowser.getCurrentUrl();
            caseStatus = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_RPA_ModuleUIPage.obj_RPA_CaseInfo_Grid + "//div[@class='open']"), "Text");

            if (caseStatus.equalsIgnoreCase("New") || caseStatus.equalsIgnoreCase("Open") || caseStatus.equalsIgnoreCase("In Progress")) {
                appInd.verifyElementPresent(oBrowser, PayCRM_RPA_ModuleUIPage.obj_Fail_Btn);
            }

            appInd.clickObject(oBrowser, PayCRM_RPA_ModuleUIPage.obj_Fail_Btn);
            appDep.waitForTheElementState(oBrowser, PayCRM_RPA_ModuleUIPage.obj_Fail_Submit_Btn, "Clickable", "", minTimeOut);
            appInd.verifyText(oBrowser, By.xpath(PayCRM_RPA_ModuleUIPage.obj_ModelFail_Dialog + "//h4[@class='modal-title']"), "Text", "Fail");
            appInd.verifyText(oBrowser, PayCRM_RPA_ModuleUIPage.obj_Fail_Notes_Textarea_Label, "Text", "Please enter notes *");
            appInd.clickObject(oBrowser, PayCRM_RPA_ModuleUIPage.obj_Fail_Submit_Btn);
            appDep.waitForTheElementState(oBrowser, PayCRM_RPA_ModuleUIPage.obj_Fail_Notes_Error_Label, "Text", "Please enter a note for fail.", minTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Click 'Submit' button without entering the details for the 'Fail' action");
            appInd.verifyText(oBrowser, PayCRM_RPA_ModuleUIPage.obj_Fail_Notes_Error_Label, "Text", "Please enter a note for fail.");
            appInd.setObject(oBrowser, PayCRM_RPA_ModuleUIPage.obj_FailNotes_TextArea, "Sample Notes for Fail: " + appInd.getDateTime("hhmmss"));
            reports.writeResult(oBrowser, "Screenshot", "After entering the details for the 'Fail' action");
            strFailNoteMessage = appInd.getTextOnElement(oBrowser, PayCRM_RPA_ModuleUIPage.obj_FailNotes_TextArea, "Value");
            appInd.clickObject(oBrowser, PayCRM_RPA_ModuleUIPage.obj_Fail_Submit_Btn);
            appDep.threadSleep(1000);
            //oBrowser = enrollmentManagersUIBaseSteps.switchBackToParentWindowWithoutClosingChildWindow(oBrowser);
            oBrowser.switchTo().window(sParent);
            appDep.waitForTheElementState(oBrowser, PartnerEnrollmentsUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_RPA_ModuleUIPage.obj_RPA_Cases_Grid + "//tr[contains(@class, 'lines dx-column-lines')][1]/td[text()='" + caseNumber + "']"));

            oBrowser.navigate().to(strURL);
            appDep.waitForTheElementState(oBrowser, PartnerEnrollmentsUIPage.obj_PaymentHelp_Header, "Text", "Payment Help", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "RPA cases-->>Show page has opened");
            appInd.verifyText(oBrowser, By.xpath(PayCRM_RPA_ModuleUIPage.obj_RPA_CaseInfo_Grid + "//dt[text()='Case Type']/following::dd[1]"), "Text", "CMVP");
            appInd.verifyText(oBrowser, By.xpath(PayCRM_RPA_ModuleUIPage.obj_RPA_CaseInfo_Grid + "//div[@class='open']"), "Text", "Open");
            appInd.verifyText(oBrowser, By.xpath(PayCRM_RPA_ModuleUIPage.obj_RPA_CaseInfo_Grid + "//dt[text()='Assigned to']/following::dd[1]/span"), "Text", "Unassigned");
            appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_RPA_ModuleUIPage.obj_RPA_CaseInfo_Grid + "//dt[text()='Alarm']/following::dd[1]"));

            //Activities tab
            appInd.scrollToElement(oBrowser, PayCRM_RPA_ModuleUIPage.obj_SplitterHorizonal_Line);
            appInd.clickObject(oBrowser, PayCRM_RPA_ModuleUIPage.obj_Activities_Link);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_RPA_ModuleUIPage.obj_CaseActivity_Grid + "//td[text()='CMVP Activities']"), "Text", "CMVP Activities", minTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "RPA cases-->>Activities page has opened");
            appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_RPA_ModuleUIPage.obj_CaseActivity_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[4])[1]"), "Text", "RPA");
            appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_RPA_ModuleUIPage.obj_CaseActivity_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[7])[1]"), "Text", strFailNoteMessage);
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in ' validateTheFailButtonStructureAndFunctionality()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateTheFailButtonStructureAndFunctionality()' method. " + e);
            return false;
        } finally {
            caseNumber = null;
            caseStatus = null;
            strURL = null;
            strFailNoteMessage = null;
        }
    }

    /********************************************************
     * Method Name      : validateReportsAndPermissionsAroundTheReport()
     * Purpose          : User navigates to Reports and validates the permissions around the report
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateReportsAndPermissionsAroundTheReport(WebDriver oBrowser) {
        List<String> objRolesSelected_Names = null;
        int rowCount = 0;
        String supplierName = null;
        try {
            objRolesSelected_Names = appDep.getTheUserRolesSelected(oBrowser);
            Assert.assertTrue(appDep.selectManageModules(oBrowser, "Reports"));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Reports page");

            if (objRolesSelected_Names.contains("RPA Admin") && objRolesSelected_Names.contains("Internal Admin")) {
                reports.writeResult(oBrowser, "Pass", "The user '" + appInd.getPropertyValueByKeyName("qaUserName") + "' having both 'RPA Admin' & 'Internal Admin' roles. Hence we can see 'RPA Enabled Suppliers' report");
                appInd.moveToElement(oBrowser, PayCRM_RPA_ModuleUIPage.obj_RPAEnabledSuppliers_Link);
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_RPA_ModuleUIPage.obj_RPAEnabledSuppliers_Link));
                appDep.waitForTheElementState(oBrowser, PayCRM_RPA_ModuleUIPage.obj_RunReport_Btn, "Clickable", "", waitTimeOut);
                reports.writeResult(oBrowser, "Screenshot", "'RPA Enabled Suppliers' report");
                appInd.clickObject(oBrowser, PayCRM_RPA_ModuleUIPage.obj_RunReport_Btn);
                appDep.waitForTheElementState(oBrowser, PayCRM_RPA_ModuleUIPage.obj_search_Loading, "Visibility", "", waitTimeOut);
                appDep.waitForTheElementState(oBrowser, PayCRM_RPA_ModuleUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                appDep.waitForTheElementState(oBrowser, PayCRM_RPA_ModuleUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                reports.writeResult(oBrowser, "Screenshot", "'RPA Enabled Suppliers' report data");
                rowCount = oBrowser.findElements(By.xpath(PayCRM_RPA_ModuleUIPage.obj_RPAEnabledSuppliers_Grid + "//tr[contains(@class, 'lines dx-column-lines')]")).size();
                Assert.assertTrue(rowCount > 0, "The 'RPA Enabled Suppliers' grid doesnot have any records'");

                supplierName = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_RPA_ModuleUIPage.obj_RPAEnabledSuppliers_Grid + "//tr[contains(@class, 'lines dx-column-lines')][1]/td[1]"), "Text");
                appInd.clickObject(oBrowser, By.xpath(PayCRM_RPA_ModuleUIPage.obj_RPAEnabledSuppliers_Grid + "//tr[contains(@class, 'lines dx-column-lines')][1]/td[1]"));
                oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
                appDep.waitForTheElementState(oBrowser, PayCRM_RPA_ModuleUIPage.obj_ViewActivities_Btn, "Clickable", "", waitTimeOut);

                appInd.verifyText(oBrowser, By.xpath(PayCRM_RPA_ModuleUIPage.obj_SupplierDetails_Grid + "//dt[text()='Name']/following-sibling::dd[1]"), "Text", supplierName);
                reports.writeResult(oBrowser, "Screenshot", "'RPA Enabled Suppliers' suppliers detail");
            } else {
                appInd.verifyElementNotPresent(oBrowser, PayCRM_RPA_ModuleUIPage.obj_RPAEnabledSuppliers_Link);
                reports.writeResult(oBrowser, "Fail", "As the user '" + appInd.getPropertyValueByKeyName("qaUserName") + "' doesnot have both 'RPA Admin' & 'Internal Admin' roles. Hence we can't see 'RPA Enabled Suppliers' report");
            }
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in ' validateReportsAndPermissionsAroundTheReport()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateReportsAndPermissionsAroundTheReport()' method. " + e);
            return false;
        } finally {
            objRolesSelected_Names = null;
            supplierName = null;
        }
    }

    /********************************************************
     * Method Name      : userExportTheRPAEnabledSupplierReportsAndValidateTheSame()
     * Purpose          : User exports the RPA enabled reporta nd validate sthe same
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean userExportTheRPAEnabledSupplierReportsAndValidateTheSame(WebDriver oBrowser) {
        String fileName = null;
        boolean blnRes = false;
        File objExportedCasesExcelFile = null;
        try {
            fileName = appDep.exportTheFile(oBrowser, PayCRM_RPA_ModuleUIPage.obj_Export_RPAEnabledSuppliers_Btn);
            blnRes = appDep.exportAndCompareTheExportedDataWithTableGridData(oBrowser, PayCRM_RPA_ModuleUIPage.obj_RPAEnabledSuppliers_Grid, fileName);
            if (blnRes)
                reports.writeResult(oBrowser, "Pass", "Exported 'RPA Enabled Suppliers' excel file cell data and 'RPA Enabled Suppliers' grid cell datas are matching");
            else {
                reports.writeResult(oBrowser, "Fail", "Mis-match in Exported 'RPA Enabled Suppliers' excel file cell data and 'RPA Enabled Suppliers' grid cell data values");
                Assert.fail("Mis-match in Exported 'RPA Enabled Suppliers' excel file cell data and 'RPA Enabled Suppliers' grid cell data values");
            }
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in ' userExportTheRPAEnabledSupplierReportsAndValidateTheSame()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'userExportTheRPAEnabledSupplierReportsAndValidateTheSame()' method. " + e);
            return false;
        } finally {
            objExportedCasesExcelFile = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Downloads\\" + fileName + ".xlsx");
            objExportedCasesExcelFile.delete();
            fileName = null;
            objExportedCasesExcelFile = null;
        }
    }

    /********************************************************
     * Method Name      : updateUserPermissionForInternalAdmin()
     * Purpose          : Update user permission and Navigate to RPA Case, verify RPA Tile & it's grid
     * Author           : Deepak
     * Parameters       : oBrowser, headerID
     * ReturnType       : boolean
     ********************************************************/
    public boolean updateUserPermissionForInternalAdmin(DataTable dataTable, String headerID) {
        Map<String, String> mapping = null;
        try {
            mapping = setPermissionAndOpenNewTab(oBrowser, dataTable);
            if (appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_RPA_ModuleUIPage.RPA_TILE_PATH, "RPA")))) {
                appInd.clickObject(oBrowser, By.xpath(String.format(PayCRM_RPA_ModuleUIPage.RPA_TILE_PATH, "RPA").concat("/../..")));
                Assert.assertTrue(verifyRPAGrid(headerID));
            } else {
                return false;
            }
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'updateUserPermissionForInternalAdmin()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'updateUserPermissionForInternalAdmin()' method. " + e);
            return false;
        } finally {
            resetPermission(oBrowser, mapping);
        }
        return true;
    }

    /********************************************************
     * Method Name      : updateUserPermission()
     * Purpose          : Update user permission and Navigate to RPA Case, verify RPA Tile & it's grid
     * Author           : Deepak
     * Parameters       : oBrowser, headerID
     * ReturnType       : boolean
     ********************************************************/
    public boolean updateUserPermissionForSuperAdmin(DataTable dataTable, String headerID) {
        try {
            setPermissionAndOpenNewTab(oBrowser, dataTable);
            appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_payCRM_NavigateBar_Link);
            Assert.assertTrue(verifyRPAGrid(headerID));
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'updateUserPermission()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'updateUserPermission()' method. " + e);
            return false;
        } finally {
            oBrowser.close();
            appDep.threadSleep(1000);
            appInd.switchToLastWindow(oBrowser);
            appDep.threadSleep(1000);
            appInd.clickObject(oBrowser, By.xpath("//li/label[text()='Super Admin']/../input/.."));
            appInd.clickObject(oBrowser, By.xpath("//li/label[text()='Internal Admin']/../input/.."));
            appInd.clickObject(oBrowser, By.xpath("//li/label[text()='Normal User']/../input/.."));
            appInd.clickObject(oBrowser, PayCRMMainUIPage.obj_Profile_Update_Bt);
            appDep.threadSleep(1000);
            reports.writeResult(oBrowser, "screenshot", "user reset the updated permission");
            appDep.waitForTheElementState(oBrowser, By.xpath("//div[@class='alert alert-info custom-alert-info']"), "visibility", "", waitTimeOut);
        }
        return true;
    }

    public boolean setUserPermissionAndNavigateToSupplier(String isVisible, DataTable dataTable){
        List<Map<String, String>> db_GeneralSupplier;
        try {
            setPermissionAndOpenNewTab(oBrowser, dataTable);
            db_GeneralSupplier = dbUtils.getQueryResultsInListOfMap("CPAY794_797_GeneralSupplierID", new Object[] {});
            Assert.assertTrue((db_GeneralSupplier.size() > 0), "No records were retrieved from the '" + System.getProperty("environment") + "' DB");
           String strURL = appInd.getPropertyValueByKeyName("qaURL")+"/suppliers/" + db_GeneralSupplier.get(0).get("cases_id");
            oBrowser.navigate().to(strURL);
            reports.writeResult(oBrowser, "Screenshot", "The constructed URL: " + strURL);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ViewActivities_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientLinkInformation_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(verifySupplierClientLinkInformationRPAEnable(isVisible));
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'setUserPermissionAndNavigateToSupplier()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'setUserPermissionAndNavigateToSupplier()' method. " + e);
            return false;
        }finally {
            if(isVisible.equalsIgnoreCase("not be"))
                resetPermission(oBrowser, dataTable.asMap(String.class, String.class));
        }
        return true;
    }

    public boolean verifySupplierClientLinkInformationRPAEnable(String isEnabled){
        try{
           List<WebElement> columns = getRpaEnableRowCells();
           Assert.assertNotNull(columns,"Unable to find 'Client Link Information' valid record which might missing some info such as VendorID, PayNetID, Status and SupplierType");Assert.assertTrue(isEnabled.equals("be")?appInd.verifyElementPresent(oBrowser, By.id("btn_update_rpa_enabled")):appInd.verifyElementNotPresent(oBrowser, By.id("btn_update_rpa_enabled")));
           Assert.assertTrue(isEnabled.equals("be")?appInd.verifyElementPresent(oBrowser, By.xpath("//label[text()='RPA Enabled']")):appInd.verifyElementNotPresent(oBrowser, By.xpath("//label[text()='RPA Enabled']")));
           Assert.assertTrue(isEnabled.equals("be")?appInd.verifyElementPresent(oBrowser, By.xpath("//input[@id='company_supplier_rpa_enabled']/..")):appInd.verifyElementNotPresent(oBrowser, By.xpath("//input[@id='company_supplier_rpa_enabled']/..")));
           appInd.clickObject(oBrowser, By.xpath("//div[@id='modal_form_company_supplier']//button[@data-dismiss='modal']"));
           appDep.threadSleep(2000);
           return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifySupplierClientLinkInformationRPAEnable()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifySupplierClientLinkInformationRPAEnable()' method. " + e);
            return false;
        }
    }

    public boolean verifySupplierClientLinkInformationRPASaveCorrectly(){
        List<WebElement> columns = getRpaEnableRowCells();
        Assert.assertNotNull(columns,"Unable to find 'Client Link Information' valid record which might missing some info such as VendorID, PayNetID, Status and SupplierType");
        try {
            String color1 = appInd.getWebElements(oBrowser, By.xpath("//input[@id='company_supplier_rpa_enabled']/../div")).get(0).getCssValue("background-color");
            appInd.clickObject(oBrowser, By.xpath("//input[@id='company_supplier_rpa_enabled']/.."));
            appDep.threadSleep(1000);
            String color2 = appInd.getWebElements(oBrowser, By.xpath("//input[@id='company_supplier_rpa_enabled']/../div")).get(0).getCssValue("background-color");

            Assert.assertNotEquals(color1, color2, "RPA Enable section is not change after click on it");
            appInd.clickObject(oBrowser, By.id("btn_update_rpa_enabled"));
            appDep.threadSleep(1000);
            appInd.clickObject(oBrowser, By.xpath("//div[@id='modal_form_company_supplier']//button[@data-dismiss='modal']"));
            appDep.threadSleep(2000);
            columns.get(10).findElement(By.xpath("//a[@title='Edit']")).click();
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_EditSuppliers_DialogGrid + "//h4"), "Visibility", "", minTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Edit 'Client Link Information' page opened");
            color1 = appInd.getWebElements(oBrowser, By.xpath("//input[@id='company_supplier_rpa_enabled']/../div")).get(0).getCssValue("background-color");
            Assert.assertEquals(color1, color2,"RPA Enable section is not preserved save changes");
            appInd.clickObject(oBrowser, By.xpath("//div[@id='modal_form_company_supplier']//button[@data-dismiss='modal']"));
            appDep.threadSleep(2000);
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifySupplierClientLinkInformationRPASaveCorrectly()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifySupplierClientLinkInformationRPASaveCorrectly()' method. " + e);
            return false;
        }
        return true;
    }

    public boolean changeClientLinkInformationDataAndVerifyRPR(String rpaSetting){
        getRpaEnableRowCells();
        try {
            String[] setting = rpaSetting.split("=");
            if(setting[0].equalsIgnoreCase("VendorID")){
                List<String> vendorIDs = appInd.getWebElements(oBrowser, By.xpath("//ul[@class='chosen-choices']/li[@class='search-choice']/span")).stream().map(id -> id.getText()).collect(Collectors.toList());
                appInd.getWebElements(oBrowser, By.xpath("//ul[@class='chosen-choices']/li[@class='search-choice']/a"))
                        .forEach(id -> { appInd.highlightElement(oBrowser, id); id.click();});
                appInd.clickObject(oBrowser, By.xpath("//form[starts-with(@id,'edit_company_supplier')]//button[@type='submit']"));
                appDep.threadSleep(2000);
                Assert.assertTrue(verifySupplierClientLinkInformationRPAEnable("not be"));
                getRpaEnableRowCells();
                WebElement input = oBrowser.findElement(By.xpath("//input[@value='Enter Vendor ID(s)']"));
                input.clear();
                vendorIDs.forEach(id -> { appDep.threadSleep(500);input.sendKeys(id, Keys.ENTER); });
            }else if(setting[0].equalsIgnoreCase("PayNetLinkID")){
                String paynetID = appInd.getTextOnElement(oBrowser, By.id("company_supplier_enrollment_company_supplier_link_id"), "text");
                appInd.clearAndSetObject(oBrowser, By.id("company_supplier_enrollment_company_supplier_link_id"), "");
                appInd.clickObject(oBrowser, By.xpath("//form[starts-with(@id,'edit_company_supplier')]//button[@type='submit']"));
                appDep.threadSleep(2000);
                Assert.assertTrue(verifySupplierClientLinkInformationRPAEnable("not be"));
                getRpaEnableRowCells();
                appInd.clearAndSetObject(oBrowser, By.id("company_supplier_enrollment_company_supplier_link_id"), paynetID);
            }
            else if(setting[0].equalsIgnoreCase("Status") || (setting[0].equalsIgnoreCase("SupplierType"))) {
                String id = setting[0].equalsIgnoreCase("Status")?"company_supplier_status_id":"company_supplier_supplier_type_id";
                String status = appInd.getTextOnElement(oBrowser, By.id(id), "dropdown");
                appInd.selectObject(oBrowser, By.id(id), setting[1]);
                appInd.clickObject(oBrowser, By.xpath("//form[starts-with(@id,'edit_company_supplier')]//button[@type='submit']"));
                appDep.threadSleep(2000);
                Assert.assertTrue(verifySupplierClientLinkInformationRPAEnable("not be"));
                getRpaEnableRowCells();
                appInd.selectObject(oBrowser, By.id(id), status);
            }
            appInd.clickObject(oBrowser, By.xpath("//form[starts-with(@id,'edit_company_supplier')]//button[@type='submit']"));
            appDep.threadSleep(2000);
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    private List<WebElement> getRpaEnableRowCells(){
        try {
            List<WebElement> rows = appInd.getWebElements(oBrowser, By.xpath("(//div[@id='divCompanySuppliersContainer']//table)[2]/tbody/tr[not(contains(@class,'freespace'))]"));
            for (WebElement row : rows) {
                List<WebElement> columns = row.findElements(By.tagName("td"));
                if (StringUtils.isNotBlank(columns.get(2).getText().trim()) && StringUtils.isNotBlank(columns.get(3).getText().trim())
                        && columns.get(4).getText().trim().equals("Active") && columns.get(6).getText().trim().startsWith("In Program")) {
                    columns.get(10).findElement(By.xpath("//a[@title='Edit']")).click();
                    appDep.threadSleep(2000);
                    appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_EditSuppliers_DialogGrid + "//h4"), "Visibility", "", minTimeOut);
                    reports.writeResult(oBrowser, "Screenshot", "Edit 'Client Link Information' page opened");
                    return  columns;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public boolean verifyRPAGrid(String headerID) {
        List<String> listOfHeaders = getMainGridTableHeaders(oBrowser, headerID);
        reports.writeResult(oBrowser, "pass", "RPA Case Grid headers are visible");
        //----------------- Verify RPA Grid columns ------------------
        Assert.assertEquals(listOfHeaders.get(0), "Case #", listOfHeaders.get(0) + " Header is not matched");
        Assert.assertEquals(listOfHeaders.get(1), "Company Name", listOfHeaders.get(1) + " Header is not matched");
        Assert.assertEquals(listOfHeaders.get(2), "Supplier Name", listOfHeaders.get(2) + " Header is not matched");
        Assert.assertEquals(listOfHeaders.get(3), "Remit to ID / Vendor ID", listOfHeaders.get(3) + " Header is not matched");
        Assert.assertEquals(listOfHeaders.get(4), "Due date", listOfHeaders.get(4) + " Header is not matched");
        Assert.assertEquals(listOfHeaders.get(5), "Status", listOfHeaders.get(5) + " Header is not matched");
        Assert.assertEquals(listOfHeaders.get(6), "Payment ID", listOfHeaders.get(6) + " Header is not matched");
        Assert.assertEquals(listOfHeaders.get(7), "Return Status", listOfHeaders.get(7) + " Header is not matched");
        Assert.assertEquals(listOfHeaders.get(8), "Amount", listOfHeaders.get(8) + " Header is not matched");
        reports.writeResult(oBrowser, "pass", "RPA Case Grid headers verification successful");
        reports.writeResult(oBrowser, "screenshot", "RPA Case Grid headers verification successful");
        return true;
    }

    public boolean verifySuccessFlowAndReassignValidation(WebDriver oBrowser, String headerID) {
        try {
            //----------------- Click on 1st record from RPA grid and verify data ------------------
            appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_payCRM_NavigateBar_Link);
            appDep.waitForTheElementState(oBrowser, By.xpath(String.format(PayCRM_RPA_ModuleUIPage.RPA_TILE_PATH, "RPA")), "visibility", "", waitTimeOut);
            appInd.clickObject(oBrowser, By.xpath(String.format(PayCRM_RPA_ModuleUIPage.RPA_TILE_PATH, "RPA").concat("/../..")));
            reports.writeResult(oBrowser, "screenshot", "We successfully collect 1st record from RPA Grid");
            appInd.clickObject(oBrowser, By.xpath(String.format(PayCRM_Modules_GeneralUIPage.obj_Grid_Column_FirstRecord, headerID)));
            appDep.threadSleep(2000);
            appInd.switchToLastWindow(oBrowser);
            appDep.waitForTheElementState(oBrowser, By.id("btn_rpa_reassign"), "visibility", "", waitTimeOut);
            //--------------------- Perform success flow -----------------------
            String caseID = appInd.getTextOnElement(oBrowser, By.xpath("(//div[@class='card']/dl/dd)[1]"), "text");
            reports.writeResult(oBrowser, "screenshot", "Collect RPA caseID for further use");
            appInd.clickObject(oBrowser, PayCRM_RPA_ModuleUIPage.RPA_SUCCESS_BTN);
            appDep.threadSleep(1000);
            Assert.assertTrue(appDep.waitForTheElementState(oBrowser, PayCRM_RPA_ModuleUIPage.RPA_SUCCESS_MODEL, "visibility", "", waitTimeOut));
            appInd.clickObject(oBrowser, PayCRM_RPA_ModuleUIPage.RPA_SUCCESS_MODEL_SUBMIT);
            appDep.threadSleep(2000);
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.id("success_notes-error"), "text"), "Please enter a note for success.");
            reports.writeResult(oBrowser, "pass", "RPA Success Model Validation trigger");
            reports.writeResult(oBrowser, "screenshot", "RPA Success model validation verified");
            appInd.setObject(oBrowser, By.id("success_notes"), "Automation Regression Success note");
            appInd.clickObject(oBrowser, PayCRM_RPA_ModuleUIPage.RPA_SUCCESS_MODEL_SUBMIT);
            appDep.threadSleep(2000);
            appInd.switchToLastWindow(oBrowser);
            // verify record should visible in RPA grid and reopen again
            //-----------------------Open record after success flow and post verification -------------------------
            oBrowser.navigate().refresh();
            appDep.threadSleep(2000);
            Assert.assertTrue(navigateToRecord(oBrowser, headerID, "Case #", caseID));
            appDep.threadSleep(1000);
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_GeneralUIPage.obj_Grid_Column_FirstRecord, headerID)),"text"), "");
            reports.writeResult(oBrowser, "screenshot", "After success flow record should not visible in grid");
            oBrowser.switchTo().newWindow(WindowType.TAB);
            appDep.threadSleep(1000);
            appInd.switchToLastWindow(oBrowser);
            oBrowser.navigate().to(appInd.getPropertyValueByKeyName("qaURL")+"/rpa/cases/"+caseID);
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, By.id("btn_rpa_reassign"), "visibility", "", waitTimeOut);
            reports.writeResult(oBrowser, "screenshot", "RPA Detail page is visible");
            reports.writeResult(oBrowser, "pass", "RPA Case Detail page open");
            Assert.assertFalse(appInd.verifyElementPresent(oBrowser, PayCRM_RPA_ModuleUIPage.RPA_SUCCESS_BTN));
            Assert.assertFalse(appInd.verifyElementPresent(oBrowser, PayCRM_RPA_ModuleUIPage.RPA_FAIL_BTN));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_RPA_ModuleUIPage.RPA_REASSIGN_BTN));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_RPA_ModuleUIPage.RPA_CASE_CARD_DATA, "RPA"))));
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath("//div[@id='section_case_information']/div/div[1]/div"), "text"), "Hold");
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath("(//div[@class='card']/dl/dd)[10]"), "text"), "Promised to Process");
            String alarmDate = appInd.getTextOnElement(oBrowser, By.xpath("(//div[@class='card']/dl/dd)[7]"), "text").split(" ")[0];
            Assert.assertEquals(alarmDate, appInd.dateAddAndReturn("MM/dd/yyyy",5));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_RPA_ModuleUIPage.RPA_TILE_PATH, "Payment Help"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_RPA_ModuleUIPage.RPA_TILE_PATH, "Invoices"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_GeneralUIPage.obj_tab_names, "Payment Data"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_GeneralUIPage.obj_tab_names, "Activities"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_GeneralUIPage.obj_tab_names, "Logs"))));
            //-------------- Activity tab verification ----------------
            appInd.clickObject(oBrowser, By.xpath(String.format(PayCRM_Modules_GeneralUIPage.obj_tab_names, "Activities")));
            appDep.threadSleep(1000);
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath("(//div[@id='divCaseActivitiesDashboardContainer']//table)[2]//tr[1]/td[1]"), "text"), "Hold: Promised to Process");
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath("(//div[@id='divCaseActivitiesDashboardContainer']//table)[2]//tr[1]/td[2]"), "text"), "RPA");
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath("(//div[@id='divCaseActivitiesDashboardContainer']//table)[2]//tr[1]/td[3]"), "text"), appInd.dateAddAndReturn("MM/d/yyyy",0));
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath("(//div[@id='divCaseActivitiesDashboardContainer']//table)[2]//tr[1]/td[4]"), "text"), "Automation Regression Success note");
            reports.writeResult(oBrowser, "pass", "RPA Case Activity tab Verification Done");
            //------------------- Logs verification -------------------------
            appInd.clickObject(oBrowser, By.xpath(String.format(PayCRM_Modules_GeneralUIPage.obj_tab_names, "Logs")));
            appDep.threadSleep(1000);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_logs']//tbody/tr[1]/td[1]"), "text")
                    .contains(appInd.dateAddAndReturn("MM/dd/yyyy",0)));
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_logs']//tbody/tr[1]/td[3]/strong[2]"), "text"), "Promised to Process");
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_logs']//tbody/tr[1]/td[3]/strong[4]"), "text"), appInd.dateAddAndReturn("MM/dd/yyyy",5));
            reports.writeResult(oBrowser, "pass", "RPA Case Logs tab Verification Done");
            reports.writeResult(oBrowser, "screenshot", "RPA Logs tab verification done");
            //------------ Reassigned functionality verify -----------------
            appInd.scrollToThePage(oBrowser, "Top");
            appDep.threadSleep(1000);
            appInd.clickObject(oBrowser, PayCRM_RPA_ModuleUIPage.RPA_REASSIGN_BTN);
            appDep.threadSleep(2000);
            Assert.assertTrue(appDep.waitForTheElementState(oBrowser, PayCRM_RPA_ModuleUIPage.RPA_REASSIGN_MODEL, "visibility", "", waitTimeOut));
            appInd.setObject(oBrowser, By.id("reassign_notes"), "Automation Regression Success note");
            appInd.clickObject(oBrowser, PayCRM_RPA_ModuleUIPage.RPA_REASSIGN_MODEL_SUBMIT);
            appDep.threadSleep(2000);
            oBrowser.close();
            appInd.switchToLastWindow(oBrowser);
            appDep.threadSleep(1000);
            // record should not visible in grid, etc
            Assert.assertTrue(navigateToRecord(oBrowser, headerID, "Case #", caseID));
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_GeneralUIPage.obj_Grid_Column_FirstRecord, headerID)),"text"), "");
            reports.writeResult(oBrowser, "screenshot", "After success flow record should not visible in grid");
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifySuccessFlowAndReassignValidation()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifySuccessFlowAndReassignValidation()' method. " + e);
            return false;
        }
        return true;
    }

    public Map<String, String> setPermissionAndOpenNewTab(WebDriver oBrowser, DataTable dataTable) {
        Map<String, String> mapping = null;
        try {
            appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_payCRM_NavigateBar_Link);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRMMainUIPage.obj_Logout_Navbar_List), "clickObject() was failed for the '" + PayCRMMainUIPage.obj_Logout_Navbar_List + "' webelement");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(String.format(PayCRMMainUIPage.obj_Logout_Link, "Edit Profile"))), "clickObject() was failed for the '" + PayCRMMainUIPage.obj_Logout_Link + "' webelement");
            appDep.threadSleep(6000);
            reports.writeResult(oBrowser, "screenshot", "user navigate to profile page");
            mapping = dataTable.asMap(String.class, String.class);
            mapping.forEach((roleName, permission) -> {
                if (permission.equalsIgnoreCase("false") && appInd.verifyElementPresent(oBrowser, By.xpath("//li/label[text()='" + roleName + "']/../input[@checked]"))) {
                    appInd.clickObject(oBrowser, By.xpath("//li/label[text()='" + roleName + "']/../input/.."));
                } else if (permission.equalsIgnoreCase("true") && !appInd.verifyElementPresent(oBrowser, By.xpath("//li/label[text()='" + roleName + "']/../input[@checked]"))) {
                    appInd.clickObject(oBrowser, By.xpath("//li/label[text()='" + roleName + "']/../input/.."));
                }
            });
            appInd.scrollToThePage(oBrowser, "Down");
            appInd.clickObject(oBrowser, PayCRMMainUIPage.obj_Profile_Update_Bt);
            appDep.threadSleep(4000);
            reports.writeResult(oBrowser, "screenshot", "user update the permissions");
            appInd.scrollToThePage(oBrowser, "Top");
            appDep.waitForTheElementState(oBrowser, By.xpath("//div[@class='alert alert-info custom-alert-info']"), "visibility", "", waitTimeOut);
            oBrowser.switchTo().newWindow(WindowType.TAB);
            appDep.threadSleep(1000);
            appInd.switchToLastWindow(oBrowser);
            oBrowser.navigate().to(appInd.getPropertyValueByKeyName("qaURL"));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "screenshot", "user navigate to QA URL");
            return mapping;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'setPermissionAndOpenNewTab()' method. " + e);
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'setPermissionAndOpenNewTab()' method. " + e);
        }
        return mapping;
    }

    public void resetPermission(WebDriver oBrowser, Map<String, String> mapping) {
        oBrowser.close();
        appDep.threadSleep(1000);
        appInd.switchToLastWindow(oBrowser);
        appDep.threadSleep(1000);
        oBrowser.navigate().refresh();
        appDep.threadSleep(6000);
        mapping.forEach((roleName, permission) -> {
            if (!oBrowser.findElement(By.xpath("//li/label[text()='" + roleName + "']/../input")).isSelected()) {
                appInd.clickObject(oBrowser, By.xpath("//li/label[text()='" + roleName + "']/../input/.."));
            }
        });
        appInd.scrollToThePage(oBrowser, "Down");
        appInd.clickObject(oBrowser, PayCRMMainUIPage.obj_Profile_Update_Bt);
        reports.writeResult(oBrowser, "screenshot", "user reset the updated permissions");
        appInd.scrollToThePage(oBrowser, "Top");
        appDep.threadSleep(4000);
        appDep.waitForTheElementState(oBrowser, By.xpath("//div[@class='alert alert-info custom-alert-info']"), "visibility", "", waitTimeOut);
    }
}
