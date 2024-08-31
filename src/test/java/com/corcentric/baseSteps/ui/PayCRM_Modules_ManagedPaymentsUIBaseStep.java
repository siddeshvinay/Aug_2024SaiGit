package com.corcentric.baseSteps.ui;

import com.corcentric.pages.*;
import com.corcentric.runner.CucumberTestRunner;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.testng.Assert;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class PayCRM_Modules_ManagedPaymentsUIBaseStep extends CucumberTestRunner {
    private String transmissionFileName;

    /********************************************************
     * Method Name      : navigateToManagedPaymentAndValidateBankFileDetails()
     * Purpose          : Navigate to Managed Payments and validate the Bank File details page
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean navigateToManagedPaymentAndValidateBankFileDetails(WebDriver oBrowser){
        Map<String, String> objPendingBankFileDetails = null;
        String arrColumnName[];
        int rowCount = 0;
        String randomNumbers[];
        String status = null;
        String paymentFileApprovedDate = null;
        String gridColumnNames[];
        try{
            appDep.selectManagePaymentModules(oBrowser, "Pending Bank Files");

            //Verify the Pending Bank File column names
            gridColumnNames = "PayNet Client Name#PayNet Client ID#Bank File ID#Bank Route#VC Account#Payment File ID#Payment File Status#Payment File Control Total#Payment File Total with Discounts Applied#Bank File Total#Payment Type#Payment File Approved".split("#");
            for(int i=0; i<gridColumnNames.length; i++){
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_PendingBankFile_Grid + "//tr[1]//div[2])["+(i+1)+"]"), "Text", gridColumnNames[i]));
            }

            objPendingBankFileDetails = new HashMap<String, String>();
            rowCount = oBrowser.findElements(By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PendingBankFile_Grid + "//tr[contains(@class, 'lines dx-column-lines')]")).size();
            int maxRow = 10;
            if(rowCount > maxRow) rowCount = maxRow;

            randomNumbers = appInd.generateRandomNumbers(1, maxRow, 2).split("#");
            arrColumnName = "PayNetClientName#PayNetClientID#BankFileID#BankRoute#VCAccount#PaymentFileID#PaymentFileStatus#PaymentFileControlTotal#PaymentFileTotalwithDiscountsApplied#BankFileTotal#PaymentType#PaymentFileApproved".split("#", -1);

            for(int l=0; l<2; l++){
                reports.writeResult(oBrowser, "Info", "The random number generated for the 'Bank File details' Row number :" + randomNumbers[l]);
                for(int i=0; i<arrColumnName.length; i++){
                    objPendingBankFileDetails.put(arrColumnName[i], appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_PendingBankFile_Grid + "//tr[contains(@class, 'lines dx-column-lines')])["+randomNumbers[l]+"]/td["+(i+1)+"]"), "Text"));
                }

                appInd.clickObject(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_PendingBankFile_Grid + "//tr[contains(@class, 'lines dx-column-lines')])["+randomNumbers[l]+"]/td[3]//a"));
                oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Link, "Clickable", "", waitTimeOut);
                reports.writeResult(oBrowser, "Screenshot", "'Managed Payment » Bank File Detail' page");

                //Validate Bank File Details
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_BankFileDetails_Grid + "//th[text()='Company Name']/following-sibling::td[1]"), "Text", objPendingBankFileDetails.get("PayNetClientName")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_BankFileDetails_Grid + "//th[text()='Company ID']/following-sibling::td[1]"), "Text", objPendingBankFileDetails.get("PayNetClientID")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_BankFileDetails_Grid + "//th[text()='Bank File ID']/following-sibling::td[1]"), "Text", objPendingBankFileDetails.get("BankFileID")));
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_BankFileDetails_Grid + "//th[text()='Filename']/following-sibling::td[1]"), "Text").contains(objPendingBankFileDetails.get("BankFileID")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_BankFileDetails_Grid + "//th[text()='Route Name']/following-sibling::td[1]"), "Text", objPendingBankFileDetails.get("BankRoute")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_BankFileDetails_Grid + "//th[text()='Payment File ID']/following-sibling::td[1]"), "Text", objPendingBankFileDetails.get("PaymentFileID")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_BankFileDetails_Grid + "//th[text()='Payment File Status Name']/following-sibling::td[1]"), "Text", objPendingBankFileDetails.get("PaymentFileStatus")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_BankFileDetails_Grid + "//th[text()='Payment File Control Total']/following-sibling::td[1]"), "Text", objPendingBankFileDetails.get("PaymentFileControlTotal")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_BankFileDetails_Grid + "//th[text()='Payment File Total Discounted']/following-sibling::td[1]"), "Text", objPendingBankFileDetails.get("PaymentFileTotalwithDiscountsApplied")));


                /*switch(objPendingBankFileDetails.get("PaymentType")){
                    case "CHK":
                        status = "Check";
                        break;
                    case "ACH":
                        status = "ACH (NACHA)";
                        break;
                    default:
                        status = "CASS All";
                }
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_BankFileDetails_Grid + "//th[text()='Payment Type']/following-sibling::td[1]"), "Text", status));*/

                paymentFileApprovedDate = (objPendingBankFileDetails.get("PaymentFileApproved").split(","))[0].trim();
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_BankFileDetails_Grid + "//th[text()='Payment File Approved At']/following-sibling::td[1]"), "Text").contains(appInd.formatDateFromOnetoAnother(paymentFileApprovedDate, "MM/dd/yyyy", "yyyy-MM-dd")));

                //Validate Payment File Details
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentFileDetails_Grid + "//th[text()='Company Name']/following-sibling::td[1]"), "Text", objPendingBankFileDetails.get("PayNetClientName")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentFileDetails_Grid + "//th[text()='Company ID']/following-sibling::td[1]"), "Text", objPendingBankFileDetails.get("PayNetClientID")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentFileDetails_Grid + "//th[text()='Payment File ID']/following-sibling::td[1]"), "Text", objPendingBankFileDetails.get("PaymentFileID")));

                oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
            }

            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'navigateToManagedPaymentAndValidateBankFileDetails()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'navigateToManagedPaymentAndValidateBankFileDetails()' method. " + e);
            return false;
        }finally {
            objPendingBankFileDetails = null; arrColumnName = null; randomNumbers = null; status = null; paymentFileApprovedDate = null; gridColumnNames = null;
        }
    }



    /********************************************************
     * Method Name      : validateVoidsPageDataGridActionIcons()
     * Purpose          : user navigates to Modules->Managed Payments->Voids and validates the data grid action icons
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateVoidsPageDataGridActionIcons(WebDriver oBrowser){
        Map<String, String> paymentVoidRequests = null;
        Map<String, String> defundedCancelledPayments = null;
        Map<String, String> voidedPayments = null;
        Map<String, String> dismissed = null;
        Alert oAlert = null;
        String paymentID = null;
        try{
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentVoidRequests_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentVoidRequests_Grid +"//tr[1][contains(@class, 'row-lines dx-column-lines')]/td[1]")));

            //'Payment Void Requests' grid Action icons - 'Payment Details'
            paymentVoidRequests = appDep.readManagedPaymentsGridDataAndReturnAsMap(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentVoidRequests_Grid+"//tr[1][contains(@class, 'row-lines dx-column-lines')]/td", "PaymentID#SupplierName#SupplierPayNetID#RemitToID#ClientName#PayNetClientID#PaymentAmount#PaymentType#RequestCreatedBy#VoidRequestedDate#AmounttoVoid#ActiontoPerformAfterVoid", "Payment Void Requests");

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentVoidRequests_Grid +"//a[@title='Payment Details']")));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Link, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appDep.validatePaymentDetailsGridData(oBrowser, paymentVoidRequests, "PaymentID#SupplierName#SupplierPayNetID#RemitToID#ClientName#PayNetClientID#PaymentAmount#PaymentType", "Payment Void Requests->Payment Details"));
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);

            //'Payment Void Requests' grid Action icons - 'Payment Void Case'
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentVoidRequests_Grid +"//a[@title='Payment Void Case']")));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Link, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appDep.validatePaymentVoidCaseDetailsGridData(oBrowser, paymentVoidRequests, "PaymentID#SupplierName#SupplierPayNetID#RemitToID#ClientName#PayNetClientID#PaymentAmount#PaymentType#RequestCreatedBy#VoidRequestedDate#AmounttoVoid#ActiontoPerformAfterVoid", "Payment Void Requests->Payment Void Case"));
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);


            //'Payment Void Requests' grid Action icons - 'Dismiss'
            paymentID = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentVoidRequests_Grid + "//tr[contains(@class, 'lines dx-column-lines')]//td[1]"), "Text");
            Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentVoidRequests_Grid +"//a[@title='Dismiss']")));
            appDep.threadSleep(2000);
            oAlert = oBrowser.switchTo().alert();
            Assert.assertTrue(oAlert.getText().equalsIgnoreCase("Are you sure?"));
            oAlert.accept();
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentVoidRequests_Grid + "//input)[1]"), paymentID));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentVoidRequests_Grid +"//tr[1][contains(@class, 'row-lines dx-column-lines')]/td[1][text()='"+paymentVoidRequests.get("PaymentID")+"']")));


            //'Defunded/Cancelled Payments' grid Action icons - 'Payment Details'
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_DefundedCancelledPayments_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DefundedCancelledPayments_Grid +"//tr[1][contains(@class, 'row-lines dx-column-lines')]/td[1]")));

            defundedCancelledPayments = appDep.readManagedPaymentsGridDataAndReturnAsMap(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_DefundedCancelledPayments_Grid+"//tr[1][contains(@class, 'row-lines dx-column-lines')]/td", "PaymentID#SupplierName#SupplierPayNetID#RemitToID#ClientName#PayNetClientID#PaymentAmount#PaymentType", "Defunded/Cancelled Payments");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DefundedCancelledPayments_Grid +"//a[@title='Payment Details']")));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Link, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appDep.validatePaymentDetailsGridData(oBrowser, defundedCancelledPayments, "PaymentID#SupplierName#SupplierPayNetID#RemitToID#ClientName#PayNetClientID#PaymentAmount#PaymentType", "Defunded/Cancelled Payments->Payment Details"));
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);

            //'Defunded/Cancelled Payments' grid Action icons - 'Payment Void Case'
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DefundedCancelledPayments_Grid +"//a[@title='Payment Void Case']")));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Link, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appDep.validatePaymentVoidCaseDetailsGridData(oBrowser, defundedCancelledPayments, "PaymentID#SupplierName#SupplierPayNetID#RemitToID#ClientName#PayNetClientID#PaymentAmount#PaymentType", "Defunded/Cancelled Payments->Payment Void Case"));
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);



            //'Voided Payments' grid Action icons - 'Payment Details'
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidedPayments_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidedPayments_Grid +"//tr[1][contains(@class, 'row-lines dx-column-lines')]/td[1]")));

            voidedPayments = appDep.readManagedPaymentsGridDataAndReturnAsMap(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidedPayments_Grid +"//tr[1][contains(@class, 'row-lines dx-column-lines')]/td", "PaymentID#SupplierName#SupplierPayNetID#RemitToID#ClientName#PayNetClientID#PaymentAmount#PaymentType", "Voided Payments");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidedPayments_Grid +"//a[@title='Payment Details']")));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Link, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appDep.validatePaymentDetailsGridData(oBrowser, voidedPayments, "PaymentID#SupplierName#SupplierPayNetID#RemitToID#ClientName#PayNetClientID#PaymentAmount#PaymentType", "Voided Payments->Payment Details"));
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);

            //'Voided Payments' grid Action icons - 'Payment Void Case'
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidedPayments_Grid +"//a[@title='Payment Void Case']")));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Link, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appDep.validatePaymentVoidCaseDetailsGridData(oBrowser, voidedPayments, "PaymentID#SupplierName#SupplierPayNetID#RemitToID#ClientName#PayNetClientID#PaymentAmount#PaymentType", "Voided Payments->Payment Void Case"));
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);



            //'Dismissed' grid Action icons - 'Payment Details'
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Dismissed_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Dismissed_Grid +"//tr[1][contains(@class, 'row-lines dx-column-lines')]/td[1]")));

            dismissed = appDep.readManagedPaymentsGridDataAndReturnAsMap(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Dismissed_Grid +"//tr[1][contains(@class, 'row-lines dx-column-lines')]/td", "PaymentID#SupplierName#SupplierPayNetID#RemitToID#ClientName#PayNetClientID#PaymentAmount#PaymentType", "Dismissed");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Dismissed_Grid +"//a[@title='Payment Details']")));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Link, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appDep.validatePaymentDetailsGridData(oBrowser, dismissed, "PaymentID#SupplierName#SupplierPayNetID#RemitToID#ClientName#PayNetClientID#PaymentAmount#PaymentType", "Dismissed->Payment Details"));
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);

            //'Dismissed' grid Action icons - 'Payment Void Case'
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Dismissed_Grid +"//a[@title='Payment Void Case']")));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Link, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appDep.validatePaymentVoidCaseDetailsGridData(oBrowser, dismissed, "PaymentID#SupplierName#SupplierPayNetID#RemitToID#ClientName#PayNetClientID#PaymentAmount#PaymentType", "Dismissed->Payment Void Case"));
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateVoidsPageDataGridActionIcons()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateVoidsPageDataGridActionIcons()' method. " + e);
            return false;
        }finally {
            paymentVoidRequests = null; defundedCancelledPayments = null; voidedPayments = null; dismissed = null; oAlert = null;
        }
    }



    /********************************************************
     * Method Name      : navigateToManagedPaymentAndValidatePaymentFileIDDetails()
     * Purpose          : Navigate to Managed Payments and validate the Payment File ID details page
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean navigateToManagedPaymentAndValidatePaymentFileIDDetails(WebDriver oBrowser){
        Map<String, String> objPaymentFileDetails = null;
        String arrColumnName[];
        int rowCount = 0;
        String randomNumbers[];
        String status = null;
        String paymentFileApprovedDate = null;
        try{
            objPaymentFileDetails = new HashMap<String, String>();
            rowCount = oBrowser.findElements(By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PendingBankFile_Grid + "//tr[contains(@class, 'lines dx-column-lines')]")).size();
            int maxRow = 10;
            if(rowCount > maxRow) rowCount = maxRow;

            randomNumbers = appInd.generateRandomNumbers(1, maxRow, 2).split("#");
            arrColumnName = "PayNetClientName#PayNetClientID#BankFileID#BankRoute#VCAccount#PaymentFileID#PaymentFileStatus#PaymentFileControlTotal#PaymentFileTotalwithDiscountsApplied#BankFileTotal#PaymentType#PaymentFileApproved".split("#", -1);

            for(int l=0; l<2; l++){
                reports.writeResult(oBrowser, "Info", "The random number generated for the 'Payment File ID details' row number: " + randomNumbers[l]);
                for(int i=0; i<arrColumnName.length; i++){
                    objPaymentFileDetails.put(arrColumnName[i], appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_PendingBankFile_Grid + "//tr[contains(@class, 'lines dx-column-lines')])["+randomNumbers[l]+"]/td["+(i+1)+"]"), "Text"));
                }

                appInd.clickObject(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_PendingBankFile_Grid + "//tr[contains(@class, 'lines dx-column-lines')])["+randomNumbers[l]+"]/td[6]//a"));
                oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Link, "Clickable", "", waitTimeOut);
                reports.writeResult(oBrowser, "Screenshot", "'Managed Payment » Payment File Detail' page");

                //Validate Payment File Details
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentFileDetails_Grid + "//th[text()='Company Name']/following-sibling::td[1]"), "Text", objPaymentFileDetails.get("PayNetClientName")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentFileDetails_Grid + "//th[text()='Company ID']/following-sibling::td[1]"), "Text", objPaymentFileDetails.get("PayNetClientID")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentFile_BankFileDetails_Grid + "//td[text()="+objPaymentFileDetails.get("BankFileID")+"]"), "Text", objPaymentFileDetails.get("BankFileID")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentFileDetails_Grid + "//th[text()='Payment File ID']/following-sibling::td[1]"), "Text", objPaymentFileDetails.get("PaymentFileID")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentFileDetails_Grid + "//th[text()='Payment File Status Name']/following-sibling::td[1]"), "Text", objPaymentFileDetails.get("PaymentFileStatus")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentFileDetails_Grid + "//th[text()='Payment File Control Total']/following-sibling::td[1]"), "Text", objPaymentFileDetails.get("PaymentFileControlTotal")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentFileDetails_Grid + "//th[text()='Payment File Control Total Discounted']/following-sibling::td[1]"), "Text", objPaymentFileDetails.get("PaymentFileTotalwithDiscountsApplied")));

                /*switch(objPaymentFileDetails.get("PaymentType")){
                    case "CHK":
                        status = "Check";
                        break;
                    case "ACH":
                        status = "ACH (NACHA)";
                        break;
                    default:
                        status = "CASS All";
                }
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentFileDetails_Grid + "//th[text()='Payment Type']/following-sibling::td[1]"), "Text", status));*/

                paymentFileApprovedDate = (objPaymentFileDetails.get("PaymentFileApproved").split(","))[0].trim();
                String actualDate = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentFileDetails_Grid + "//th[text()='Payment File Approved At']/following-sibling::td[1]"), "Text");
                String expectedDate = appInd.formatDateFromOnetoAnother(paymentFileApprovedDate, "MM/dd/yyyy", "MMM dd yyyy");
                String arrDate[] = expectedDate.split(" ");
                expectedDate = arrDate[0] +" "+ Integer.parseInt(arrDate[1]) +" "+ arrDate[2];
                Assert.assertTrue(actualDate.contains(expectedDate), "Both Actual '"+actualDate+"' & expected '"+expectedDate+"' dates are not matching");

                //Validate Payment File Bank file Details
                List<WebElement> bankFileDetails = oBrowser.findElements(By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentFile_BankFileDetails_Grid+"//tbody/tr"));
                boolean blnExist = false;
                for(int i=0; i<bankFileDetails.size(); i++){
                    String bankFileID = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentFile_BankFileDetails_Grid+"//tbody/tr["+(i+1)+"]/td[1]"), "Text");
                    if(bankFileID.equalsIgnoreCase(objPaymentFileDetails.get("BankFileID"))){
                        blnExist = true;
                        break;
                    }
                }
                Assert.assertTrue(blnExist, "Bank File ID: " + objPaymentFileDetails.get("BankFileID")+ " doesnot exist");
                oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
            }

            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'navigateToManagedPaymentAndValidatePaymentFileIDDetails()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'navigateToManagedPaymentAndValidatePaymentFileIDDetails()' method. " + e);
            return false;
        }finally {objPaymentFileDetails = null; arrColumnName = null; randomNumbers = null; status = null; paymentFileApprovedDate = null;}
    }



    /********************************************************
     * Method Name      : validateVoidsDataGridsFromTheManagedPayments()
     * Purpose          : user navigates to Modules->Managed Payments->Voids and validates the data grid & UI functionalities
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateVoidsDataGridsFromTheManagedPayments(WebDriver oBrowser){
        String paymentVoidRequests = null;
        String defundedCancelledPayments = null;
        String voidedPayments = null;
        String dismissed = null;
        try{
            Assert.assertTrue(appDep.selectManagePaymentModules(oBrowser, "Voids"));
            appDep.threadSleep(2000);

            //Validate tabs under Voids
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentVoidRequests_Link));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_DefundedCancelledPayments_Link));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidedPayments_Link));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Dismissed_Link));


            //Verify 'Payment Void Requests' table columns
            paymentVoidRequests = "Payment ID#Supplier Name#Supplier PayNet ID#Remit To ID#Client Name#PayNet Client ID#Payment Amount#Payment Type#Request Created By#Void Requested Date#Amount to Void#Action to Perform After Void";
            Assert.assertTrue(appDep.validateGridColumns(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentVoidRequests_Grid + "//tr[contains(@class, 'dx-header-row')]/td/div[contains(@class,'content')]", paymentVoidRequests, "Payment Void Requests"));


            //Verify 'Defunded/Cancelled Payments' table columns
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_DefundedCancelledPayments_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            defundedCancelledPayments = "Payment ID#Supplier Name#Supplier PayNet ID#Remit To ID#Client Name#PayNet Client ID#Payment Amount#Payment Type";
            Assert.assertTrue(appDep.validateGridColumns(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_DefundedCancelledPayments_Grid + "//tr[contains(@class, 'dx-header-row')]/td/div[contains(@class,'content')]", defundedCancelledPayments, "Defunded/Cancelled Payments"));


            //Verify 'Voided Payments' table columns
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidedPayments_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            voidedPayments = "Payment ID#Supplier Name#Supplier PayNet ID#Remit To ID#Client Name#PayNet Client ID#Payment Amount#Payment Type";
            Assert.assertTrue(appDep.validateGridColumns(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidedPayments_Grid + "//tr[contains(@class, 'dx-header-row')]/td/div[contains(@class,'content')]", voidedPayments, "Voided Payments"));


            //Verify 'Dismissed' table columns
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Dismissed_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            dismissed = "Payment ID#Supplier Name#Supplier PayNet ID#Remit To ID#Client Name#PayNet Client ID#Payment Amount#Payment Type";
            Assert.assertTrue(appDep.validateGridColumns(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Dismissed_Grid + "//tr[contains(@class, 'dx-header-row')]/td/div[contains(@class,'content')]", dismissed, "Dismissed"));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateVoidsDataGridsFromTheManagedPayments()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateVoidsDataGridsFromTheManagedPayments()' method. " + e);
            return false;
        }finally {
            paymentVoidRequests = null; defundedCancelledPayments = null; voidedPayments = null; dismissed = null;
        }
    }



    /********************************************************
     * Method Name      : verifyBankFileIDPage()
     * Purpose          : to verifyBankFileIDPage details page fields, attachment tab, with download functionality
     * Author           : Deepak
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean verifyBankFileIDPage(WebDriver oBrowser){
        String id = appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_BankFileId_Link, "Text");
        appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_BankFileId_Link);
        oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
        reports.writeResult(oBrowser,"screenshot","Bank File ID click success");
        appDep.waitForTheElementState(oBrowser,By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_queues_heading,"Bank File Details")),"visibility","",waitTimeOut);
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser,By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_queues_heading,"Bank File Details"))));
        reports.writeResult(oBrowser,"screenshot","Bank File ID click success");
        appInd.clickObject(oBrowser, By.xpath("(//*[@id='btn_download_payment_file'])[1]"));
        appDep.threadSleep(5000);
        File file = new File(System.getProperty("user.home")+"/Downloads/"+id+".csv");
        Assert.assertTrue(file.exists());
        file.delete();
        reports.writeResult(oBrowser,"screenshot","Bank File ID download verified success");
        //Verification for labels and its values inside table
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Bank File ID"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Company ID"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Bank File Status ID"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Payment File Status ID"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Route ID"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"File Format ID"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Payment Type"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Funding Method"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"VC Company"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Payment File Status ID"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Payment File Received At"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Payment File Control Total"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Payment File ID"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Company Name"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Route Name"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Payment Count"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Filename"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Debit Account"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Payment File Status Name"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Funding Confirmation Required"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Payment File Total Discounted"))));
        reports.writeResult(oBrowser,"screenshot","Bank file page HTML Table verify success");
        //verify Log Activitiy Model and functionality
        verifyLogActivity(oBrowser);
        reports.writeResult(oBrowser,"screenshot","Bank file Activity Load Verify success");
        //verify attachment functionality
        appDep.threadSleep(1000);
        Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_tab_names,"Attachments"))));
        appDep.threadSleep(2000);
        Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentBasedEnrollments_Add_Link));
        String timeStemp = appInd.getDateTime("Shhmmss");
        appDep.waitForTheElementState(oBrowser,By.xpath("//h4[text()='New Attachment']"),"visibility","", waitTimeOut);
        appInd.setObject(oBrowser,By.id("managed_payment_attachment_name"),"New Automation Attachment : "+timeStemp);
        appInd.setObject(oBrowser, By.id("FileUploaderAttachment"), new File("TestData/CompanyAttachment_dummy.pdf").getAbsolutePath());
        appInd.clickObject(oBrowser, By.id("attachment-submit"));
        reports.writeResult(oBrowser,"screenshot","Bank file ID attachment success");
        appDep.waitForTheElementState(oBrowser,By.xpath("//h4[text()='New Attachment']"),"invisibility","", waitTimeOut);
        appDep.threadSleep(2000);
        appInd.setObject(oBrowser, By.xpath("(//div[@id='divCaseAttachmentDashboardContainer']//input)[1]"),"New Automation Attachment : "+timeStemp);
        appDep.threadSleep(2000);
        appInd.clickObject(oBrowser, By.xpath("//div[@id='divCaseAttachmentDashboardContainer']//a[@title='Show']"));
        appDep.threadSleep(2000);
        appDep.waitForTheElementState(oBrowser,By.xpath("//div[@id='modal_show_attachment' and contains(@style,'display: block;')]"),"visibility","", waitTimeOut);
        appInd.clickObject(oBrowser, By.partialLinkText("CompanyAttachment_dummy"));
        appDep.threadSleep(5000);
        boolean blnRes = appDep.verifyLatestDownloadFile(oBrowser, "NA", ".pdf", "Dummy PDF file", true);
        if (blnRes) {
            reports.writeResult(oBrowser, "Pass", "The ' Attachment' pdf file was downloaded successful");
        } else {
            reports.writeResult(oBrowser, "Fail", "Failed to download the Attachment' pdf file");
            Assert.assertTrue(false, "Failed to download the Attachment' pdf file");
        }
        appDep.threadSleep(2000);
        appInd.clickObject(oBrowser, By.xpath("//div[@id='modal_show_attachment']//button[@class='close']"));
        appDep.threadSleep(1000);
        appInd.clickObject(oBrowser, By.xpath("//div[@id='divCaseAttachmentDashboardContainer']//a[@title='Delete']"));
        appDep.threadSleep(2000);
        oBrowser.switchTo().alert().accept();
        appDep.threadSleep(1000);
        return true;
    }



    /********************************************************
     * Method Name      : verifyVoidRequest()
     * Purpose          : to verify void request display valid data
     * Author           : Deepak
     * Parameters       : oBrowser
     * Parameters       : voidRequest List of expected records
     * ReturnType       : boolean
     ********************************************************/
    public boolean verifyVoidRequest(WebDriver oBrowser, List<String> voidRequest){
        try{
            appInd.setObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_TextBox, voidRequest.get(0));
            appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_SubmitBtn);
            appDep.threadSleep(3000);
            appInd.switchToLastWindow(oBrowser);
            appDep.waitForTheElementState(oBrowser, By.id("btn_void_request"), "visibility","",waitTimeOut);
            appInd.clickObject(oBrowser, By.id("btn_void_request"));
            reports.writeResult(oBrowser, "screenshot","void page open and ready click on void button");
            appDep.threadSleep(2500);
            if(appInd.verifyElementPresent(oBrowser, By.id("void_request_form"))) {
                appInd.setObject(oBrowser, By.id("void_request_amount"),"0.123");
                appInd.selectObject(oBrowser, By.id("void_request_after_action"),"Re-issue as ACH");
                appInd.selectObject(oBrowser, By.id("void_request_link_action"),"Yes");
                appInd.setObject(oBrowser, By.id("void_request_notes"),"Automation Regression Run");
                appInd.clickObject(oBrowser, By.id("btn_void_request_submit"));
                appDep.threadSleep(1000);
            }
            else{
                String message = appInd.getTextOnElement(oBrowser, By.xpath("//span[@id='success_message_on_case']"),"text");
                Assert.assertTrue(message.startsWith("There is already a void request for this payment."));
                appInd.clickObject(oBrowser, By.xpath("//span[@id='success_message_on_case']/a"));
                appDep.threadSleep(2000);
                appInd.switchToLastWindow(oBrowser);
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_ActivityTab,"visibility","", waitTimeOut);
            }
            reports.writeResult(oBrowser, "screenshot","Void detail page ready after click on void button");
            List<WebElement> records = appInd.getWebElements(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_PaymentDetail_Grid_th);
            Assert.assertEquals(records.get(0).getText().trim(),"Payment ID","Actual and expected not match of Payment Id");
            Assert.assertEquals(records.get(1).getText().trim(),"Client company ID","Actual and expected not match of Client company Id");
            Assert.assertEquals(records.get(2).getText().trim(),"Client company name","Actual and expected not match of Client company name");
            Assert.assertEquals(records.get(3).getText().trim(),"Payment reference","Actual and expected not match of Payment reference");
            Assert.assertEquals(records.get(4).getText().trim(),"Supplier pay net ID","Actual and expected not match of Supplier pay net Id");
            Assert.assertEquals(records.get(5).getText().trim(),"Supplier name","Actual and expected not match of Supplier name");
            Assert.assertEquals(records.get(6).getText().trim(),"Remit to ID","Actual and expected not match of Remit to Id");
            Assert.assertEquals(records.get(7).getText().trim(),"Payment amount","Actual and expected not match of Payment amount");
            Assert.assertEquals(records.get(8).getText().trim(),"Payment currency","Actual and expected not match of Payment currency");
            Assert.assertEquals(records.get(9).getText().trim(),"Payment type","Actual and expected not match of Payment type");
            Assert.assertEquals(records.get(10).getText().trim(),"Status","Actual and expected not match of Status");

            records = appInd.getWebElements(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_PaymentDetail_Grid_td);
            Assert.assertEquals(records.get(0).findElement(By.tagName("a")).getText().trim(),voidRequest.get(0),"Actual and expected not match of Payment Id");
            Assert.assertEquals(records.get(1).getText().trim(),voidRequest.get(5),"Actual and expected not match of Client company Id");
            Assert.assertEquals(records.get(2).getText().trim(),voidRequest.get(4),"Actual and expected not match of Client company name");
            Assert.assertEquals(records.get(4).getText().trim(),voidRequest.get(2),"Actual and expected not match of Supplier pay net Id");
            Assert.assertEquals(records.get(5).getText().trim(),voidRequest.get(1),"Actual and expected not match of Supplier name");
            Assert.assertEquals(records.get(6).getText().trim(),voidRequest.get(3),"Actual and expected not match of Remit to Id");
            Assert.assertEquals(records.get(9).getText().trim(),voidRequest.get(7),"Actual and expected not match of Payment type");
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifyVoidRequest()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifyVoidRequest()' method. " + e);
            return false;
        }
        return true;
    }



    /********************************************************
     * Method Name      : verifyPendingBankFilesGrid()
     * Purpose          : to verify that the Filter functionality works for Modules > Manage Payments
     * Author           : Deepak
     * Parameters       : oBrowser, valueType
     * ReturnType       : boolean
     ********************************************************/
    public boolean gotoAndVerifyManagePaymentModule(WebDriver oBrowser, String subModule, String tabName) {
        Assert.assertTrue(appDep.selectManagePaymentModules(oBrowser, subModule));
        subModule = subModule.toLowerCase();
        switch (subModule){
            case "pending bank files":
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Grid_Heading,"visibility","",waitTimeOut);
                if(tabName!=null)
                    appInd.clickObject(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_tab_names,tabName)));
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                List<WebElement> columnHeading = appInd.getWebElements(oBrowser,PayCRM_Modules_ManagedPaymentsUIPage.obj_Grid_Heading);
                Assert.assertTrue(columnHeading.get(0).getText().equalsIgnoreCase("PayNet Client Name"));
                Assert.assertTrue(columnHeading.get(1).getText().equalsIgnoreCase("PayNet Client ID"));
                Assert.assertTrue(columnHeading.get(2).getText().equalsIgnoreCase("Bank File ID"));
                Assert.assertTrue(columnHeading.get(3).getText().equalsIgnoreCase("Bank Route"));
                Assert.assertTrue(columnHeading.get(4).getText().equalsIgnoreCase("VC Account"));
                Assert.assertTrue(columnHeading.get(5).getText().equalsIgnoreCase("Payment File ID"));
                Assert.assertTrue(columnHeading.get(6).getText().equalsIgnoreCase("Payment File Status"));
                Assert.assertTrue(columnHeading.get(7).getText().equalsIgnoreCase("Payment File Control Total"));
                Assert.assertTrue(columnHeading.get(8).getText().equalsIgnoreCase("Payment File Total with Discounts Applied"));
                Assert.assertTrue(columnHeading.get(9).getText().equalsIgnoreCase("Bank File Total"));
                Assert.assertTrue(columnHeading.get(10).getText().equalsIgnoreCase("Payment Type"));
                Assert.assertTrue(columnHeading.get(11).getText().equalsIgnoreCase("Payment File Approved"));
                appDep.waitForTheElementState(oBrowser,PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_Pending_Bank_Files_Grid_Heading_Export_Icon,"visibility","",waitTimeOut);
                break;
            case "bank file receipts":
                break;
            case "transmission files":
                break;
            case "voids":
                appDep.waitForTheElementState(oBrowser,PayCRM_Modules_ManagedPaymentsUIPage.obj_Grid_Heading,"visibility","",waitTimeOut);
                if(tabName!=null)
                    appInd.clickObject(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_tab_names, tabName)));
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                break;
            case "payment search":
                appDep.waitForTheElementState(oBrowser,PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_TextBox,"visibility","",waitTimeOut);
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_ClearBtn));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_SubmitBtn));
                break;
        }
        return true;
    }



    public List<String> getVoidRequestRandomRecord(WebDriver oBrowser, String gridID){
        List<String> record = new ArrayList<>();
        try{
            String table = String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_table_row, gridID);
            Random random = new Random();
            int size = appInd.getWebElements(oBrowser, By.xpath(table)).size()-1;
            int row = random.nextInt(size>15?15:size);
            String recordXpath = "("+table+"["+row+"])[1]/td";
            reports.writeResult(oBrowser, "screenshot","From void page we collecting random record");
            appInd.getWebElements(oBrowser, By.xpath(recordXpath)).forEach( td -> {
                if(StringUtils.isNotBlank(td.getText().trim())){
                    record.add(td.getText().trim());
                }
            });
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'getVoidRequestRandomRecord()' method. " + e);
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'getVoidRequestRandomRecord()' method. " + e);
        }
        return record;
    }



    /********************************************************
     * Method Name      : gotoBankFileID()
     * Purpose          : to verify BankFileID details page fields
     * Author           : Deepak
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean gotoBankFileID(WebDriver oBrowser){
        String id = appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_BankFileId_Link, "Text");
        appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_BankFileId_Link);
        oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
        reports.writeResult(oBrowser,"screenshot","Bank File ID click success");
        String heading = String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_queues_heading,"Bank File Details");
        appDep.waitForTheElementState(oBrowser,By.xpath(heading),"visibility","",waitTimeOut);
        reports.writeResult(oBrowser,"screenshot","bank file details verified success");
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser,By.xpath(heading)));
        String fileIDText = String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_BankFileDetail_FileID_Text, id);
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser,By.xpath(fileIDText)));
        oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
        appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_DownloadBankFile_Link);
        appDep.threadSleep(5000);
        File file = new File(System.getProperty("user.home")+"/Downloads/" + id + ".csv");
        Assert.assertTrue(file.exists());
        file.delete();
        reports.writeResult(oBrowser,"screenshot","payment File download verified success");
        return true;
    }


    private boolean verifyLogActivity(WebDriver oBrowser){
        String timeStemp = appInd.getDateTime("Shhmmss");
        Assert.assertTrue(appInd.clickObject(oBrowser, By.id("btn_log_activity")));
        appDep.waitForTheElementState(oBrowser,By.xpath("//h4[text()='Log Activity']"),"visibility","", waitTimeOut);
        appInd.setObject(oBrowser, By.id("managed_payment_case_entry_notes"),"Testing Log Activity : "+timeStemp);
        Assert.assertTrue(appInd.clickObject(oBrowser, By.id("btn_submit_log_activity")));
        appDep.waitForTheElementState(oBrowser,By.xpath("//h4[text()='Log Activity']"),"invisibility","", waitTimeOut);
        appInd.scrollToThePage(oBrowser, "bottom");
        appDep.threadSleep(1000);
        appInd.setObject(oBrowser,By.xpath("(//div[@id='divCaseActivitiesDashboardContainer']//input)[5]"),"Testing Log Activity : "+timeStemp);
        appDep.threadSleep(1000);
        appInd.clickObject(oBrowser,By.xpath("//a[@title='Show']"));
        appDep.waitForTheElementState(oBrowser,By.xpath("//div[@id='view_notes' and contains(@style,'display: block;')]"),"visibility","", waitTimeOut);
        appDep.threadSleep(2000);
        appDep.waitForTheElementState(oBrowser, By.xpath("//div[@id='view_notes' and contains(@style,'display: block;')]//div[text()='Testing Log Activity : "+timeStemp+"']"),"Visibility","",waitTimeOut);
        appDep.threadSleep(2000);
        Assert.assertTrue(appInd.clickObject(oBrowser,By.xpath("//div[@id='view_notes']//button[@class='close']")));
        appDep.threadSleep(2000);
        appInd.clickObject(oBrowser,By.xpath("//a[@title='Delete']"));
        appDep.threadSleep(2000);
        oBrowser.switchTo().alert().accept();
        return true;
    }


    private boolean verifyFileIDTableLable(WebDriver oBrowser){
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Company ID"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Payment File ID"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Payment File Received At"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Payment File Approved By"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Payment File Control Total Discounted"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Num Payments"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Route ID"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Funding Confirmation Required"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Debit Account"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"VC Account"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Payment File ID"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Company Name"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Payment File Status Name"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Payment File Approved At"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Payment File Control Total"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Payment File Name"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Payment Type"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Route Name"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"Funding Method"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagePayment_FileID_Table_Label,"VC Company"))));
        return  true;
    }


    /********************************************************
     * Method Name      : gotoPaymentFileID()
     * Purpose          : to verify PaymentFileID details page fields
     * Author           : Deepak
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean gotoPaymentFileID(WebDriver oBrowser){
        String id = appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentFileDetails_Link, "Text");
        appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentFileDetails_Link);
        oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
        reports.writeResult(oBrowser,"screenshot","payment File ID click success");
        String heading = String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_queues_heading,"Payment File Details");
        appDep.waitForTheElementState(oBrowser,By.xpath(heading),"visibility","",waitTimeOut);
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser,By.xpath(heading)));
        reports.writeResult(oBrowser,"screenshot","payment File ID page heading verify success");
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser,By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentFileDetails_Grid + "//td[contains(text(), '"+id+"')]")));
        String fileName = appInd.getTextOnElement(oBrowser, By.xpath("(//table[@class='table table-condensed']//th[text()='Payment File Name']/following-sibling::td)[1]"),"text");
        oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
        appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_DownloadPaymentFile_Link);
        appDep.threadSleep(5000);
        File file = new File(System.getProperty("user.home")+"/Downloads/"+fileName);
        Assert.assertTrue(file.exists());
        file.delete();
        reports.writeResult(oBrowser,"screenshot","payment File download and verify success");
        return true;
    }


    /********************************************************
     * Method Name      : verifyPaymentFileIDPage()
     * Purpose          : to verifyPaymentFileIDPage details page fields with download functionality
     * Author           : Deepak
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean verifyPaymentFileIDPage(WebDriver oBrowser){
        appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentFileDetails_Link);
        enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
        reports.writeResult(oBrowser,"screenshot","payment File ID click success");
        String heading = String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_queues_heading,"Payment File Details");
        appDep.waitForTheElementState(oBrowser,By.xpath(heading),"visibility","",waitTimeOut);
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser,By.xpath(heading)));
        reports.writeResult(oBrowser,"screenshot","payment File ID Detail page is open");
        String fileName = appInd.getTextOnElement(oBrowser, By.xpath("//table[@class='table table-condensed']//td[14]"),"text");
        appInd.clickObject(oBrowser, By.id("btn_download_payment_file"));
        appDep.threadSleep(5000);
        File file = new File(System.getProperty("user.home")+"/Downloads/"+fileName);
        Assert.assertTrue(file.exists());
        file.delete();
        reports.writeResult(oBrowser,"screenshot","payment file download success");
        //Verification for labels and its values inside table
        verifyFileIDTableLable(oBrowser);
        reports.writeResult(oBrowser,"screenshot","payment File table verification success");
        //verify Log Activitiy Model and functionality
        verifyLogActivity(oBrowser);
        reports.writeResult(oBrowser,"screenshot","payment File ID Log verification success");
        return true;
    }




    /********************************************************
     * Method Name      : userPerformLogActivityForManagedPaymentAndValidateTheSame()
     * Purpose          : user perform Log Activity for Managed Payment and validate the same under Activities tab
     * Author           : Deepak
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean userPerformLogActivityForManagedPaymentAndValidateTheSame(WebDriver oBrowser, DataTable dataTable){
        List<Map<String, String>> logActivity = null;
        String notes = null;
        String timeStamp = null;
        String dateCreated1[];
        String dateCreated2[];
        try{
            timeStamp = appInd.getDateTime("hhmmss");
            logActivity = dataTable.asMaps(String.class, String.class);
            notes = logActivity.get(0).get("Notes") +"_"+ timeStamp;
            appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_LogActivity_Btn);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_LogActivity_Dialog + "//button[@class='btn btn-info']"), "Clickable", "", minTimeOut);
            appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_LogActivity_Dialog + "//h4"), "Text", "Log Activity");
            appInd.setObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_LogActivity_TextArea, notes);
            reports.writeResult(oBrowser, "Screenshot", "Before creating 'Log Activity'");
            appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_LogActivity_Dialog + "//button[@class='btn btn-info']"));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_LogActivity_Dialog + "//h4"), "Invisibility", "", minTimeOut);
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "After creating 'Log Activity'");

            //Validate the log activity entry under Activities tab
            appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Link);
            appDep.waitForTheElementState(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//input)[5]"), "Clickable", "", minTimeOut);
            appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//input)[5]"), notes);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[1]"), "Text", "Manual Entry - Note Added"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[2]"), "Text", appInd.getPropertyValueByKeyName("qaUserName")));
            dateCreated1 = appInd.getDateTimeByTimezone("MM/dd/yyyy", "CST").split("/");
            dateCreated2 = appInd.getDateTime("MM/dd/yyyy").split("/");
            String createdDate1 = String.valueOf(Integer.parseInt(dateCreated1[0])) + "/" + String.valueOf(Integer.parseInt(dateCreated1[1])) + "/" + String.valueOf(Integer.parseInt(dateCreated1[2]));
            String createdDate2 = String.valueOf(Integer.parseInt(dateCreated2[0])) + "/" + String.valueOf(Integer.parseInt(dateCreated2[1])) + "/" + String.valueOf(Integer.parseInt(dateCreated2[2]));
            String actualDateCreated = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[3]"), "Text");
            if(actualDateCreated.equalsIgnoreCase(createdDate1) || actualDateCreated.equalsIgnoreCase(createdDate2)){
                reports.writeResult(oBrowser, "Pass", "Log Activity 'Date Created' was matched");
            }else{
                reports.writeResult(oBrowser, "Fail", "Mis-match in the Log Activity 'Date Created': Actual- is: " + actualDateCreated +" & expected were: " + createdDate1 + " OR " + createdDate2);
                Assert.fail("Mis-match in the Log Activity 'Date Created': Actual- is: " + actualDateCreated +" & expected were: " + createdDate1 + " OR " + createdDate2);
            }
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[4]"), "Text", notes));
            reports.writeResult(oBrowser, "Screenshot", "Validated activities tab for 'Log Activity' entry");
            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'userPerformLogActivityForManagedPaymentAndValidateTheSame()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'userPerformLogActivityForManagedPaymentAndValidateTheSame()' method. " + e);
            return false;
        }finally {logActivity = null; notes = null; timeStamp = null; dateCreated1 = null; dateCreated2 = null;}
    }





    /********************************************************
     * Method Name      : validateActivitiesTabFunctionalities()
     * Purpose          : user navigates to Managed Payments->Payment Search page and validates Activities tab functionalities
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateActivitiesTabFunctionalities(WebDriver oBrowser, DataTable dataTable){
        JSONArray paymentIDDetails = null;
        String arrColumnNames[];
        try{
            Assert.assertTrue(appDep.selectManagePaymentModules(oBrowser, "Payment Search"));
            appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//p"), "Text", "Managed Payments");
            appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//h3"), "Text", "Search by Payment ID");
            appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EnterPaymentID_Edit);
            appInd.verifyText(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EnterPaymentID_Edit, "placeholder", "Enter Payment ID (only numbers and dashes allowed) ... ");
            appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_ClearBtn);
            appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_SubmitBtn);
            Assert.assertTrue(appInd.createAndGetWebElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_SubmitBtn).getAttribute("class").contains("disable-submit"), "The 'Search' button is not disabled before entering the Payment ID");


            //API call
            paymentIDDetails = apiDataProvider.getAPIDataProviderResponse("ManagedPayment_PaymentID", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) paymentIDDetails.get(0)).size(), 1);
            String payment_ID = ((LinkedHashMap) ((JSONArray) paymentIDDetails.get(0)).get(Integer.parseInt(randomNum)-1)).get("paymentid").toString();

            appInd.setObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EnterPaymentID_Edit, payment_ID);
            appDep.threadSleep(1000);
            Assert.assertTrue(!appInd.createAndGetWebElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_SubmitBtn).getAttribute("class").contains("disable-submit"), "The 'Search' button is disabled after entering the Payment ID");

            //click on payment search and validate the details page
            appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_SubmitBtn);
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Link, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentDetails_Grid + "/div[@class='page-header']"), "Text").contains("» Managed Payment » Payment Detail"));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_LogActivity_Btn));
            Assert.assertTrue(appInd.compareValues(oBrowser, oBrowser.getTitle(), "Managed Payment » Payment Details - PayCRM"));

            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Link));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachments_Link));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentVoidCases_Link));


            //Validate the "Managed Payment » Payment Detail" grid column values
            arrColumnNames = "Payment file ID#Payment file name#Payment file approved at#Bank file ID#Bank file route desc#Bank file released at#Payment ID#Client company ID#Client company name#Parent client company ID#Parent client company name#Payment created at#Value date#Payment reference#Supplier pay net ID#Supplier name#Payee entity identifier#Remit to ID#Payment amount#Discount amount#Amount requested#Amount posted#Amount voided#Payment currency#Payment type#Payment type ID#Status#Status ID#Status updated at#Check number provided#Check number generated#Ach trace number#Cardholder reference number#Funding method#Funding account number#Funding account identifier#Payment processor#Check number#Route description#Funding account code".split("#");
            for(int i=0; i<arrColumnNames.length; i++){
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_MngdPayments_PaymentDetails_Grid + "//th)["+(i+1)+"]"), "Text", arrColumnNames[i]));
            }

            //Perform Log activity and validate the same under Activities tab
            Assert.assertTrue(userPerformLogActivityForManagedPaymentAndValidateTheSame(oBrowser, dataTable));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateActivitiesTabFunctionalities()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateActivitiesTabFunctionalities()' method. " + e);
            return false;
        }finally{paymentIDDetails = null;}
    }





    /********************************************************
     * Method Name      : addPaymentDetailsAttachment()
     * Purpose          : user navigates to Managed Payments->Payment Search page and validates Attachment tab add attachment functionalities
     * Author           : Gudi
     * Parameters       : oBrowser, datatable
     * ReturnType       : boolean
     ********************************************************/
    public Map<String, String> addPaymentDetailsAttachment(WebDriver oBrowser, Map<String, String> attachmentData){
        try{
            appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachments_Link);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagedPayments_Add_Link, "Clickable", "", minTimeOut);
            appInd.scrollToElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagedPayments_Add_Link);
            appInd.moveToElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagedPayments_Add_Link);
            appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagedPayments_Add_Link);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_NewAttachment_Grid + "//button[@id='attachment-submit']"), "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_NewAttachment_Grid + "//h4"), "Text", "New Attachment"));
            appInd.setObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_NewAttchment_Name_Edit, attachmentData.get("Name"));
            appInd.setObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_NewAttchment_Description_Textarea, attachmentData.get("Description"));
            appInd.selectObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_NewAttachment_Attachmentype_List, attachmentData.get("AttachmentType"));
            appInd.setObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UploadNewFile_Edit, System.getProperty("user.dir").replace("\\", "/") + "/TestData/" + attachmentData.get("FileToUpload"));
            reports.writeResult(oBrowser, "Screenshot", "Adding the Attachment for the Managed Payment");
            appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_NewAttachment_Grid + "//button[@id='attachment-submit']"));
            appDep.threadSleep(5000);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ConfirmationMsg_Label, "Text").contains("Attachment has been successfully created."));


            //Verify the new attachment created successful
            appDep.waitForTheElementState(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachments_Grid + "//input)[1]"), "Clickable", "", waitTimeOut);
            appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachments_Grid + "//input)[1]"), attachmentData.get("Name"));
            appDep.threadSleep(2000);
            appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachments_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[1]"), "Text", attachmentData.get("Name"));
            appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachments_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[2]"), "Text", attachmentData.get("Description"));
            appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachments_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[3]"), "Text", attachmentData.get("AttachmentType"));
            reports.writeResult(oBrowser, "Screenshot", "Validating the Attachment details under Attachment grid");
            attachmentData.put("TestPassed", "True");
            return attachmentData;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'addPaymentDetailsAttachment()' method. " + e);
            attachmentData.put("TestPassed", "False");
            return attachmentData;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'addPaymentDetailsAttachment()' method. " + e);
            attachmentData.put("TestPassed", "False");
            return attachmentData;
        }
    }





    /********************************************************
     * Method Name      : userPerformCreateAndDeleteAttachmentForManagedPayments()
     * Purpose          : user navigates to Managed Payments->Payment Search page and validates Attachment tab Create and Delete attachment functionalities
     * Author           : Gudi
     * Parameters       : oBrowser, datatable
     * ReturnType       : boolean
     ********************************************************/
    public boolean userPerformCreateAndDeleteAttachmentForManagedPayments(WebDriver oBrowser, DataTable dataTable){
        List<Map<String, String>> attachmentData = null;
        String timeStamp = null;
        Map<String, String> attachment = null;
        Alert oAlert = null;
        try{
            timeStamp = appInd.getDateTime("hhmmss");
            attachment = new HashMap<String, String>();
            attachmentData = dataTable.asMaps(String.class, String.class);
            attachment.put("Name", attachmentData.get(0).get("Name") + timeStamp);
            attachment.put("Description", attachmentData.get(0).get("Description"));
            attachment.put("AttachmentType", attachmentData.get(0).get("AttachmentType"));
            attachment.put("FileToUpload", attachmentData.get(0).get("FileToUpload"));

            attachment = addPaymentDetailsAttachment(oBrowser, attachment);
            Assert.assertTrue(attachment.get("TestPassed").equalsIgnoreCase("True"), "The addPaymentDetailsAttachment() method was failed");

            //delete the attachment
            appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachments_Grid + "//a[@title='Delete']"));
            appDep.threadSleep(2000);
            oAlert = oBrowser.switchTo().alert();
            Assert.assertTrue(oAlert.getText().equals("Are you sure?"));
            oAlert.dismiss();
            appDep.threadSleep(2000);
            appInd.clearAndSetObject(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachments_Grid + "//input)[1]"), attachment.get("Name"));
            appDep.threadSleep(2000);
            appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachments_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[1]"), "Text", attachment.get("Name"));

            reports.writeResult(oBrowser, "Screenshot", "Before deleting the attachment");
            appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachments_Grid + "//a[@title='Delete']"));
            appDep.threadSleep(2000);
            oAlert = oBrowser.switchTo().alert();
            Assert.assertTrue(oAlert.getText().equals("Are you sure?"));
            oAlert.accept();
            appDep.threadSleep(2000);
            appInd.clearAndSetObject(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachments_Grid + "//input)[1]"), attachment.get("Name"));
            appDep.threadSleep(2000);
            appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachments_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[1]"));
            reports.writeResult(oBrowser, "Screenshot", "After deleting the attachment");
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'userPerformCreateAndDeleteAttachmentForManagedPayments()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'userPerformCreateAndDeleteAttachmentForManagedPayments()' method. " + e);
            return false;
        }finally {
            attachmentData = null; timeStamp = null; attachment = null; oAlert = null;
        }
    }




    /********************************************************
     * Method Name      : validatePaymentVoidCasesDetailsOnPaymentDetailsPage()
     * Purpose          : user navigates to Managed Payments->Payment Details page, Perform Void Request and validate the 'Payment Void Cases tab details
     * Author           : Gudi
     * Parameters       : oBrowser, datatable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validatePaymentVoidCasesDetailsOnPaymentDetailsPage(WebDriver oBrowser, DataTable dataTable){
        List<Map<String, String>> voidRequestDetails = null;
        String timeStamp = null;
        Map<String, String> voidRequest = null;
        String arrColumnNames[];
        String paymentID = null;
        try{
            timeStamp = appInd.getDateTime("Shhmmss");
            voidRequestDetails = dataTable.asMaps(String.class, String.class);
            voidRequest = new HashMap<String, String>();
            voidRequest.put("paymentID", voidRequestDetails.get(0).get("paymentID"));
            voidRequest.put("AmountToVoid", voidRequestDetails.get(0).get("AmountToVoid"));
            voidRequest.put("ActionAfterVoid", voidRequestDetails.get(0).get("ActionAfterVoid"));
            voidRequest.put("LinkRevoked", voidRequestDetails.get(0).get("LinkRevoked"));
            voidRequest.put("Notes", voidRequestDetails.get(0).get("Notes") + timeStamp);

            Assert.assertTrue(appDep.selectManagePaymentModules(oBrowser, "Payment Search"));

            paymentID = searchAndOpenThePaymentIDUsingAPIDataprovider(oBrowser, "PaymentID_VoidRequest", new Object[] {}, "PaymentID");
            voidRequest.remove("paymentID");
            voidRequest.put("paymentID", paymentID);

            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidRequest_Btn));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidRequest_Btn));
            boolean blnRes = appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentVoidCase_Link, "Clickable", "", minTimeOut);
            if(blnRes)
                appInd.scrollToThePage(oBrowser, "Top");
            blnRes = appInd.verifyOptionalElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentVoidCase_Link);
            if(!blnRes){
                //Perform Void Request for the selected payment
                appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidRequest_Dialog + "//input[@value='Submit']"), "Clickable", "", waitTimeOut);
                Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_AmountToVoid_Edit, voidRequest.get("AmountToVoid")));
                Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ActionToPerformAfterVoid_List, voidRequest.get("ActionAfterVoid")));
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("//input[@name='void_request_link_action' and @value='"+voidRequest.get("LinkRevoked")+"']")));
                Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidRequest_Notes_Textarea, voidRequest.get("Notes")));
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidRequest_Dialog + "//input[@value='Submit']")));
                appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//h3[contains(text(), 'Void Request Details')]"), "Clickable", "", waitTimeOut);
            }else{
                Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidRequestConfirmation_Msg, "Text", "There is already a void request for this payment. Please click here to view."));
                reports.writeResult(oBrowser, "Pass", "There is already a Void Request for this payment");
                reports.writeResult(oBrowser, "Screenshot", "There is already a Void Request for this payment");
                appInd.scrollToThePage(oBrowser, "Top");
                Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentVoidCase_Link));
                oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            }

            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//th[text()='Payment ID']/following-sibling::td/a"), "Text", voidRequest.get("paymentID")));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//th[text()='Payment ID']/following-sibling::td/a")));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            oBrowser.navigate().refresh();
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//th[text()='Payment ID']/following-sibling::td[1]"), "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//th[text()='Payment ID']/following-sibling::td[1]"), "Text", voidRequest.get("paymentID")));

            //Verify the 'Payment Void Case' tab columns after the Void Request for the payment is completed
            appInd.scrollToElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentVoidCases_Link);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentVoidCases_Link));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentVoidCases_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "The 'Payment Voide Cases' grid");
            arrColumnNames = "Request Created By#Void Requested Date#Amount to Void#Payment Void Case Status#Action to Perform After Void#Link Revoked#Notes".split("#");
            for(int i=0; i<arrColumnNames.length; i++){
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentVoidCases_Grid + "//tr[contains(@class, 'lines dx-header-row')]/td["+(i+1)+"]"), "Text", arrColumnNames[i]), "Invalid column name '"+arrColumnNames[i]+"' from the 'Payment Void Cases' Grid");
            }
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validatePaymentVoidCasesDetailsOnPaymentDetailsPage()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validatePaymentVoidCasesDetailsOnPaymentDetailsPage()' method. " + e);
            return false;
        }finally{voidRequestDetails = null; timeStamp = null; voidRequest = null; arrColumnNames = null;}
    }





    /********************************************************
     * Method Name      : validateEditPaymentVoidCasesDetails()
     * Purpose          : user navigates to Managed Payments->Payment Void, edit the Void Request Details and validates the same
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateEditPaymentVoidCasesDetails(WebDriver oBrowser){
        String paymentID = null;
        String status = null;
        try{
            //Hover over Modules > Voids
            Assert.assertTrue(appDep.selectManagePaymentModules(oBrowser, "Voids"));
            reports.writeResult(oBrowser, "Screenshot", "Managed Pyment-->Void Page opened");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentVoidRequests_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentVoidRequests_Grid +"//tr[1][contains(@class, 'row-lines dx-column-lines')]/td[1]")), "'Payment Void Request' grid doesnot have any records");
            paymentID = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentVoidRequests_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[1]"), "Text");

            //On the Payment Void Requests grid click the eye icon to take you to a case
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentVoidCase_Link));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//th[text()='Payment ID']/following-sibling::td/a"), "Text", paymentID));
            reports.writeResult(oBrowser, "Screenshot", "'Managed Payment » Payment Void' Page opened");

            //On a Payment Void Case, click the pencil icon next to Void Request Details card title
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EditCaseDetails_Link));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentVoidCase_Dialog + "//button[@type='submit']"), "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentVoidCase_Dialog + "//h4"), "Text").contains("Edit Payment Void Case - "));
            reports.writeResult(oBrowser, "Screenshot", "'Edit Payment Void Case ' Page opened");

            //If Case Status is New, should see status options of Dismissed and In Progress.
            //If Case Status is Completed, should see status options of Closed.
            //If Case Status is In Progress, should see status options of Dismissed.
            status = appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentVoid_Status_List, "Dropdown");

            switch(status.toLowerCase()){
                case "new":
                    Assert.assertTrue(appDep.validateTheListValues(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentVoid_Status_List, "New#In Progress#Dismissed"));
                    break;
                case "completed":
                    Assert.assertTrue(appDep.validateTheListValues(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentVoid_Status_List, "Completed#Closed"));
                    break;
                case "in progress":
                    Assert.assertTrue(appDep.validateTheListValues(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentVoid_Status_List, "In Progress#Dismissed"));
                    break;
                default:
                    reports.writeResult(oBrowser, "Fail", "Invalid status '"+status+"' under Edit Payment Void Case status dropdown");
                    Assert.fail("Invalid status '"+status+"' under Edit Payment Void Case status dropdown");
            }

            reports.writeResult(oBrowser, "Screenshot", "After editing the 'Edit Payment Void Case ' Page");
            String oldStatus = status;
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentVoid_Status_List, 1));
            String updatedStatus = appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentVoid_Status_List, "Dropdown");
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentVoid_Notes_Textarea, "Edit Payment Void Notes_" + appInd.getDateTime("Shhmmss")));
            String updatedNote = appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentVoid_Notes_Textarea, "Value");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentVoidCase_Dialog + "//button[@type='submit']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ConfirmationMsg_Label, "Visibility", "", waitTimeOut);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ConfirmationMsg_Label, "Text").contains("Case was successfully updated"));

            //Validate under Activities tab
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Link));
            oBrowser.navigate().refresh();
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appDep.waitForTheElementState(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//input)[5]"), "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//input)[5]"), updatedNote));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "'Edit Payment Void Case' Activities Page");
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[1]"), "Text", "User Action - Case Updated"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[2]"), "Text", appInd.getPropertyValueByKeyName("qaUserName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[4]"), "Text", updatedNote));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateEditPaymentVoidCasesDetails()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateEditPaymentVoidCasesDetails()' method. " + e);
            return false;
        }finally {paymentID = null; status = null;}
    }


    public boolean verifyProxyNumberVisibility(WebDriver oBrowser, String isVisible){
        String paymentID;
        String proxyNum = String.valueOf(appInd.getRandomNumber(10));
        appDep.selectManagePaymentModules(oBrowser, "Payment Search");
        if(isVisible.equalsIgnoreCase("not be")){
            paymentID = searchAndOpenThePaymentIDUsingAPIDataprovider(oBrowser, "ManagedPayment_UpdateProxy_Hide", new Object[] {}, "PaymentID");
        }else {
            paymentID = searchAndOpenThePaymentIDUsingAPIDataprovider(oBrowser, "ManagedPayment_UpdateProxy_Visible", new Object[] {}, "PaymentID");
        }
        try{
            if(paymentID.equalsIgnoreCase("Data not found for the API")) {
                reports.writeResult(oBrowser, "Warning", "The data was not found in the DB for the dataProvider API 'ManagedPayments_Defund' & typeID");
            }
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidRequest_Btn, "visibility","",waitTimeOut);
            appDep.threadSleep(1000);
            if(isVisible.equalsIgnoreCase("be")){
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UPDATE_PROXY_NUMBER));
                appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UPDATE_PROXY_NUMBER);
                appDep.threadSleep(2000);
                Assert.assertTrue(appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UPDATE_PROXY_MODEL, "visibility", "", waitTimeOut));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UPDATE_PROXY_MODEL_HEADING));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UPDATE_PROXY_MODEL_COMPANY_ID));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UPDATE_PROXY_MODEL_OLD_PROXY_NUM));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UPDATE_PROXY_MODEL_NEW_PROXY_ENABLE));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UPDATE_PROXY_MODEL_NOTES));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UPDATE_PROXY_MODEL_SUBMIT));
                Assert.assertFalse(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UPDATE_PROXY_MODEL_NOTES_READONLY));
                Assert.assertFalse(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UPDATE_PROXY_MODEL_NEW_PROXY_READONLY));
                appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UPDATE_PROXY_MODEL_SUBMIT);
                appDep.threadSleep(1000);
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UPDATE_PROXY_MODEL_ERROR_MSG, "text").equalsIgnoreCase("This field is required."));
                String oldProxy = appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UPDATE_PROXY_MODEL_OLD_PROXY_NUM_READONLY, "value");
                //Validation: Enter old proxy number as new proxy number
                appInd.setObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UPDATE_PROXY_MODEL_NEW_PROXY_ENABLE, oldProxy);
                appDep.threadSleep(1000);
                Assert.assertEquals(appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UPDATE_PROXY_MODEL_ERROR_MSG, "text"),"New Proxy number cannot be same as Old Proxy Number");
                //Validation: Enter special chars in new proxy number (~@#$%^&*()_{}|':;/><,)
                appInd.clearAndSetObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UPDATE_PROXY_MODEL_NEW_PROXY_ENABLE, "~@#$%^&*()_{}|':;/><,");
                appDep.threadSleep(1000);
                Assert.assertEquals(appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UPDATE_PROXY_MODEL_ERROR_MSG, "text"),"This field is required.");
                //Validation: Enter special chars in new proxy number (+.)
                appInd.clearAndSetObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UPDATE_PROXY_MODEL_NEW_PROXY_ENABLE, "+");
                appDep.threadSleep(1000);
                Assert.assertEquals(appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UPDATE_PROXY_MODEL_ERROR_MSG, "text"),"Please enter a valid number.");
                //Validation: Enter special chars in new proxy number (abcdefg)
                appInd.clearAndSetObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UPDATE_PROXY_MODEL_NEW_PROXY_ENABLE, "abcdefg");
                appDep.threadSleep(1000);
                Assert.assertEquals(appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UPDATE_PROXY_MODEL_ERROR_MSG, "text"),"Please enter a valid number.");
                //Validation: Enter numbers more than 20 digits
                appInd.clearAndSetObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UPDATE_PROXY_MODEL_NEW_PROXY_ENABLE, proxyNum+"12345678901234");
                appDep.threadSleep(1000);
                Assert.assertEquals(appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UPDATE_PROXY_MODEL_ERROR_MSG, "text"),"Please enter no more than 20 characters.");
                //Validation: Enter negative numbers more than 20 digits
                appInd.clearAndSetObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UPDATE_PROXY_MODEL_NEW_PROXY_ENABLE, "-"+proxyNum);
                appDep.threadSleep(1000);
                Assert.assertEquals(appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UPDATE_PROXY_MODEL_ERROR_MSG, "text"),"Please enter a value greater than or equal to 1.");
                //Enter valid proxy number and save
                appInd.clearAndSetObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UPDATE_PROXY_MODEL_NEW_PROXY_ENABLE, proxyNum);
                appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UPDATE_PROXY_MODEL_SUBMIT);
                appDep.threadSleep(1000);
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UPDATE_PROXY_MODEL, "invisibility", "", waitTimeOut);
                Assert.assertEquals(appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidRequestConfirmation_Msg, "text"),"Proxy Number updated Successfully");
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//div[@id='payment_details_partial']//td[28 and text()='"+proxyNum+"']")));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//div[@id='payment_details_partial']//th[28 and text()='Cardholder reference number']")));
            }else {
                Assert.assertFalse(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UPDATE_PROXY_NUMBER));
                oBrowser.close();
                appInd.switchToLastWindow(oBrowser);
            }
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifyProxyNumberVisibility()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifyProxyNumberVisibility()' method. " + e);
            return false;
        }
    }




    /********************************************************
     * Method Name      : validateTransmissionFilesGridDetails()
     * Purpose          : Managed Payment - Verify Transmission Files grid details
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateTransmissionFilesGridDetails(WebDriver oBrowser){
        String arrColumnNames[];
        try{
            Assert.assertTrue(appDep.selectManagePaymentModules(oBrowser, "Transmission Files"));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_TransmissionFiles_Link));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Generated_Link));

            arrColumnNames = "Template Name#Template Description#Type#Client Name#Client PayNet ID#Drawdown Method#Disbursement Method#Site Name".split("#");
            for(int i=0; i<arrColumnNames.length; i++){
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_TransmissionFiles_Grid + "//td/div[contains(@class, 'text-content')])["+(i+1)+"]"), "Text", arrColumnNames[i]), "Invalid column name '"+arrColumnNames[i]+"' was found in the 'Transmission Files' grid");
            }
            reports.writeResult(oBrowser, "Screenshot", "'Transmission Files' grid column names");
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateTransmissionFilesGridDetails()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateTransmissionFilesGridDetails()' method. " + e);
            return false;
        }finally{arrColumnNames = null;}
    }




    /********************************************************
     * Method Name      : userPerformUIValidationsForTheTransmissionFormPage()
     * Purpose          : Validate Transmission File Page UI components
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean userPerformUIValidationsForTheTransmissionFormPage(WebDriver oBrowser, DataTable dataTable){
        List<Map<String, String>> TransmissionFileDetails = null;
        Map<String, String> TransmissionDetails = null;
        String timeStamp = null;
        try{
            timeStamp = appInd.getDateTime("Shhmmss");
            TransmissionFileDetails = dataTable.asMaps(String.class, String.class);
            TransmissionDetails = new HashMap<String, String>();
            TransmissionDetails.put("ChooseProvider", TransmissionFileDetails.get(0).get("ChooseProvider"));
            TransmissionDetails.put("TransmissionTemplate", TransmissionFileDetails.get(0).get("TransmissionTemplate"));
            TransmissionDetails.put("TransmissionType", TransmissionFileDetails.get(0).get("TransmissionType"));
            TransmissionDetails.put("AssociateWithClient", TransmissionFileDetails.get(0).get("AssociateWithClient"));
            TransmissionDetails.put("TemplateName", TransmissionFileDetails.get(0).get("TemplateName")+timeStamp);
            TransmissionDetails.put("ChooseDrawdownAccount", TransmissionFileDetails.get(0).get("ChooseDrawdownAccount"));
            TransmissionDetails.put("DA_Account_BuyerID", TransmissionFileDetails.get(0).get("DA_Account_BuyerID"));
            TransmissionDetails.put("DA_Account_ClientCode", TransmissionFileDetails.get(0).get("DA_Account_ClientCode"));
            TransmissionDetails.put("DrawdownMethod", TransmissionFileDetails.get(0).get("DrawdownMethod"));
            TransmissionDetails.put("BuyerAccountID", TransmissionFileDetails.get(0).get("BuyerAccountID"));
            TransmissionDetails.put("PayeeAccountID", TransmissionFileDetails.get(0).get("PayeeAccountID"));
            TransmissionDetails.put("ChooseDisbursementAccount", TransmissionFileDetails.get(0).get("ChooseDisbursementAccount"));
            TransmissionDetails.put("DI_Account_BuyerID", TransmissionFileDetails.get(0).get("DI_Account_BuyerID"));
            TransmissionDetails.put("DI_Account_ClientCode", TransmissionFileDetails.get(0).get("DI_Account_ClientCode"));
            TransmissionDetails.put("DisbursementMethod", TransmissionFileDetails.get(0).get("DisbursementMethod"));

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagedPayments_Add_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_TransmissionFile_Submit_Btn, "Clickable", "", waitTimeOut);

            //Verify Grid columns for the 4 sections
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//*[text()='Template Information']"), "Text", "Template Information"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//*[text()='Drawdown Account']"), "Text", "Drawdown Account"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//*[text()='Disbursement Information']"), "Text", "Disbursement Information"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//*[text()='Delivery Details']"), "Text", "Delivery Details"));


            //Click 'Subit' button without entering the data
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_TransmissionFile_Submit_Btn));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Mandatory field error message when no data is entered");

            //Verify that the mandatory fields (with no default data) should throw a error message
            //Template Information section
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_TemplateInformation_Grid + "//span[text()='Please choose a Provider' and not(@style)]")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_TemplateInformation_Grid + "//span[text()='Please choose a Transmission Template' and not(@style)]")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_TemplateInformation_Grid + "//span[text()='Please choose a Client' and not(@style)]")));

            //Drawdown Account section
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DrawdownAccount_Grid + "//span[text()='Please choose a Drawdown Account' and not(@style)]")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DrawdownAccount_Grid + "//span[text()='Please enter an Account Name'][not(@style)]")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DrawdownAccount_Grid + "//span[text()='Please enter a Recipient Name'][not(@style)]")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DrawdownAccount_Grid + "//span[text()='Please enter a Currency'][not(@style)]")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DrawdownAccount_Grid + "//span[text()='Please enter a Country'][not(@style)]")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DrawdownAccount_Grid + "//span[text()='Please enter an Account ID / Buyer ID'][not(@style)]")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DrawdownAccount_Grid + "//span[text()='Please Enter an Account Code / Client Code'][not(@style)]")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DrawdownAccount_Grid + "//span[text()='Pleases choose a Drawdown Method' and not(@style)]")));

            //Disbursement Information section
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DisbursementInformation_grid + "//span[text()='Please choose a Disbursement Account' and not(@style)]")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DisbursementInformation_grid + "//span[text()='Please enter an Account Name'][not(@style)]")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DisbursementInformation_grid + "//span[text()='Please enter a Recipient Name'][not(@style)]")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DisbursementInformation_grid + "//span[text()='Please enter a Currency'][not(@style)]")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DisbursementInformation_grid + "//span[text()='Please enter a Country'][not(@style)]")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DisbursementInformation_grid + "//span[text()='Please enter an Account ID / Buyer ID'][not(@style)]")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DisbursementInformation_grid + "//span[text()='Please Enter an Account Code / Client Code'][not(@style)]")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DisbursementInformation_grid + "//span[text()='Please choose a Disbursement Method' and not(@style)]")));


            //Enter the data into the mandatory fields and validate that the error message should be removed
            //Template Information
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_TemplateInformation_Grid + "//span[contains(@aria-labelledby, '-provider_id-')]")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchOption_Edit, "Clickable", "", minTimeOut);
            appInd.setMultipleValues(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchOption_Edit, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchResult_Options, TransmissionDetails.get("ChooseProvider"));

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_TemplateInformation_Grid + "//span[contains(@aria-labelledby,'-transmission_file_id-')]")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchOption_Edit, "Clickable", "", minTimeOut);
            appInd.setMultipleValues(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchOption_Edit, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchResult_Options, TransmissionDetails.get("TransmissionTemplate"));

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_TemplateInformation_Grid + "//span[contains(@aria-labelledby,'-type_id-')]")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchOption_Edit, "Clickable", "", minTimeOut);
            appInd.setMultipleValues(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchOption_Edit, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchResult_Options, TransmissionDetails.get("TransmissionType"));

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_TemplateInformation_Grid + "//span[contains(@aria-labelledby, '-client_id-')]")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchOption_Edit, "Clickable", "", minTimeOut);
            appInd.setMultipleValues(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchOption_Edit, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchResult_Options, TransmissionDetails.get("AssociateWithClient"));

            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_TemplateName_Edit, TransmissionDetails.get("TemplateName")));


            //Drawdown Account
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DrawdownAccount_Grid + "//span[@id='select2-drawdown_account_id-container']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchOption_Edit, "Clickable", "", minTimeOut);
            appInd.setMultipleValues(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchOption_Edit, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchResult_Options, TransmissionDetails.get("ChooseDrawdownAccount"));

            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Drawdown_Account_BuyerID_Edit, TransmissionDetails.get("DA_Account_BuyerID")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Drawdown_Account_BuyerCode_Edit, TransmissionDetails.get("DA_Account_ClientCode")));

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DrawdownAccount_Grid + "//span[@id='select2-drawdown_method-container']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchOption_Edit, "Clickable", "", minTimeOut);
            appInd.setMultipleValues(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchOption_Edit, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchResultsMethods_List, TransmissionDetails.get("DrawdownMethod"));


            //Disbursement Information
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DisbursementInformation_grid + "//span[@id='select2-disbursement_account_id-container']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchOption_Edit, "Clickable", "", minTimeOut);
            appInd.setMultipleValues(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchOption_Edit, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchResult_Options, TransmissionDetails.get("ChooseDisbursementAccount"));

            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Disbursement_Account_BuyerID_Edit, TransmissionDetails.get("DI_Account_BuyerID")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Disbursement_Account_BuyerCode_Edit, TransmissionDetails.get("DI_Account_ClientCode")));

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DisbursementInformation_grid + "//span[@id='select2-disbursement_method-container']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchOption_Edit, "Clickable", "", minTimeOut);
            appInd.setMultipleValues(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchOption_Edit, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchResultsMethods_List, TransmissionDetails.get("DisbursementMethod"));

            reports.writeResult(oBrowser, "Screenshot", "Mandatory field error messages are removed when data is entered");
            //Enter the data into the mandatory fields and verify that the error message was removed
            //Template Information section
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_TemplateInformation_Grid + "//span[text()='Please choose a Provider'][not(contains(@style, 'none'))]")));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_TemplateInformation_Grid + "//span[text()='Please choose a Transmission Template'][not(contains(@style, 'none'))]")));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_TemplateInformation_Grid + "//span[text()='Please choose a Client'][not(contains(@style, 'none'))]")));

            //Drawdown Account section
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DrawdownAccount_Grid + "//span[text()='Please choose a Drawdown Account'][not(contains(@style, 'none'))]")));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DrawdownAccount_Grid + "//span[text()='Please enter an Account Name'][not(@style)]")));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DrawdownAccount_Grid + "//span[text()='Please enter a Recipient Name'][not(@style)]")));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DrawdownAccount_Grid + "//span[text()='Please enter a Currency'][not(@style)]")));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DrawdownAccount_Grid + "//span[text()='Please enter a Country'][not(@style)]")));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DrawdownAccount_Grid + "//span[text()='Please enter an Account ID / Buyer ID'][not(@style)]")));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DrawdownAccount_Grid + "//span[text()='Please Enter an Account Code / Client Code'][not(@style)]")));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DrawdownAccount_Grid + "//span[text()='Pleases choose a Drawdown Method'][not(contains(@style, 'none'))]")));

            //Disbursement Information section
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DisbursementInformation_grid + "//span[text()='Please choose a Disbursement Account'][not(contains(@style, 'none'))]")));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DisbursementInformation_grid + "//span[text()='Please enter an Account Name'][not(@style)]")));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DisbursementInformation_grid + "//span[text()='Please enter a Recipient Name'][not(@style)]")));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DisbursementInformation_grid + "//span[text()='Please enter a Currency'][not(@style)]")));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DisbursementInformation_grid + "//span[text()='Please enter a Country'][not(@style)]")));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DisbursementInformation_grid + "//span[text()='Please enter an Account ID / Buyer ID'][not(@style)]")));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DisbursementInformation_grid + "//span[text()='Please Enter an Account Code / Client Code'][not(@style)]")));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DisbursementInformation_grid + "//span[text()='Please choose a Disbursement Method'][not(contains(@style, 'none'))]")));

            //Enter Buyer and Payee Account ID details more than 50 characters
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_BuyerAccountID_Edit, "a1b1c1d1e1f1g1h1i1j1k1l1m1n1o1p1q1r1s1t1u1v1w1x1y1z"));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeAccountID_Edit));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DrawdownAccount_Grid + "//span[text()='Please enter no more than 50 characters.']")));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_BuyerAccountID_Edit, "a1b1c1d1e1f1g1h1i1j1k1l1m1n1o1p1q"));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeAccountID_Edit));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DrawdownAccount_Grid + "//span[text()='Please enter no more than 50 characters.']")));

            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeAccountID_Edit, "a1b1c1d1e1f1g1h1i1j1k1l1m1n1o1p1q1r1s1t1u1v1w1x1y1z"));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_BuyerAccountID_Edit));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DrawdownAccount_Grid + "//span[text()='Please enter no more than 50 characters.']")));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeAccountID_Edit, "a1b1c1d1e1f1g1h1i1j1k1l1m1n1o1p1q"));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_BuyerAccountID_Edit));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DrawdownAccount_Grid + "//span[text()='Please enter no more than 50 characters.']")));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'userPerformUIValidationsForTheTransmissionFormPage()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'userPerformUIValidationsForTheTransmissionFormPage()' method. " + e);
            return false;
        }finally{TransmissionFileDetails = null; TransmissionDetails = null; timeStamp = null;}
    }





    /********************************************************
     * Method Name      : createTransmissionFileDetails()
     * Purpose          : Add the Transmission File details
     * Author           : Gudi
     * Parameters       : oBrowser, buyerAccountDetails, payeeAccountDetails, dataTable
     * ReturnType       : Map<String, String>
     ********************************************************/
    public boolean createTransmissionFileDetailsAndValidateTheSame(WebDriver oBrowser, String buyerAccountDetails, String payeeAccountDetails, DataTable dataTable){
        List<Map<String, String>> TransmissionFileDetails = null;
        Map<String, String> TransmissionDetails = null;
        String timeStamp = null;
        try{
            timeStamp = appInd.getDateTime("Shhmmss");
            TransmissionFileDetails = dataTable.asMaps(String.class, String.class);
            TransmissionDetails = new HashMap<>();
            TransmissionDetails.put("ChooseProvider", TransmissionFileDetails.get(0).get("ChooseProvider"));
            TransmissionDetails.put("TransmissionTemplate", TransmissionFileDetails.get(0).get("TransmissionTemplate"));
            TransmissionDetails.put("TransmissionType", TransmissionFileDetails.get(0).get("TransmissionType"));
            TransmissionDetails.put("AssociateWithClient", TransmissionFileDetails.get(0).get("AssociateWithClient"));
            TransmissionDetails.put("TemplateName", TransmissionFileDetails.get(0).get("TemplateName")+timeStamp);
            transmissionFileName = TransmissionDetails.get("TemplateName");
            TransmissionDetails.put("ChooseDrawdownAccount", TransmissionFileDetails.get(0).get("ChooseDrawdownAccount"));
            TransmissionDetails.put("DA_Account_BuyerID", TransmissionFileDetails.get(0).get("DA_Account_BuyerID"));
            TransmissionDetails.put("DA_Account_ClientCode", TransmissionFileDetails.get(0).get("DA_Account_ClientCode"));
            TransmissionDetails.put("DrawdownMethod", TransmissionFileDetails.get(0).get("DrawdownMethod"));
            TransmissionDetails.put("BuyerAccountID", TransmissionFileDetails.get(0).get("BuyerAccountID"));
            TransmissionDetails.put("PayeeAccountID", TransmissionFileDetails.get(0).get("PayeeAccountID"));
            TransmissionDetails.put("ChooseDisbursementAccount", TransmissionFileDetails.get(0).get("ChooseDisbursementAccount"));
            TransmissionDetails.put("DI_Account_BuyerID", TransmissionFileDetails.get(0).get("DI_Account_BuyerID"));
            TransmissionDetails.put("DI_Account_ClientCode", TransmissionFileDetails.get(0).get("DI_Account_ClientCode"));
            TransmissionDetails.put("DisbursementMethod", TransmissionFileDetails.get(0).get("DisbursementMethod"));

            TransmissionDetails = createTransmissionFileDetails(oBrowser, buyerAccountDetails, payeeAccountDetails, TransmissionDetails);
            Assert.assertTrue(TransmissionDetails.get("TestPassed").equalsIgnoreCase("True"), "The createTransmissionFileDetails() method was failed");
            TransmissionDetails = transferAmountAndValidateTheDetails(oBrowser, TransmissionDetails);
            Assert.assertTrue(TransmissionDetails.get("TestPassed").equalsIgnoreCase("True"), "The transferAmountAndValidateTheDetails() method was failed");
            Assert.assertTrue(validateTransferPageDetails(oBrowser, TransmissionDetails));
            Assert.assertTrue(downloadAndValidateTheCSVFileUnderAttachmentsTabOfTransferPage(oBrowser, buyerAccountDetails, payeeAccountDetails, TransmissionDetails));
            Assert.assertTrue(validateTheHistoryOfTheGeneratedTransmissionFile(oBrowser, TransmissionDetails));
            //-------------------- Template Deactivated Functionality verification --------------------
            Assert.assertTrue(appDep.selectManagePaymentModules(oBrowser, "Transmission Files"));
            appDep.waitForTheElementState(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_TransmissionFiles_Grid + "//input)[1]"), "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_TransmissionFiles_Grid + "//input)[1]"), TransmissionDetails.get("TemplateName")));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Search Template and start verification of grid data");
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_TransmissionFiles_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td[1]"), "Text", TransmissionDetails.get("TemplateName")));
            appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_TransmissionFiles_Grid + "//a[@title='Deactivate']"));
            appDep.threadSleep(1000);
            Alert deactiveAlert = oBrowser.switchTo().alert();
            Assert.assertTrue(deactiveAlert.getText().equalsIgnoreCase("Are you sure you want to deactivate this?"));
            deactiveAlert.accept();
            appDep.threadSleep(1000);
            appDep.waitForTheElementState(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_TransmissionFiles_Grid + "//input)[1]"), "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_TransmissionFiles_Grid + "//input)[1]"), TransmissionDetails.get("TemplateName")));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "After deactivated, template should not be present in grid");
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_TransmissionFiles_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td[1]")));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'userPerformUIValidationsForTheTransmissionFormPage()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'userPerformUIValidationsForTheTransmissionFormPage()' method. " + e);
            return false;
        }finally {TransmissionFileDetails = null; TransmissionDetails = null; timeStamp = null;}
    }



    private Map<String, String> verifyTransmissionFileData(Map<String, String> transmissionDataCompare){
        Map<String, String> transmissionData = new HashMap<>();
        String dropDownSelectedVal = "//select[@id='%s']/option[@selected]";
        String value = appInd.getAttribute(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_TemplateName_Edit, "value");
        //------------ Template info collect -------------
        transmissionData.put("TransmissionType", appInd.getTextOnElement(oBrowser, By.xpath(String.format(dropDownSelectedVal, "type_id")), "text"));
        transmissionData.put("AssociateWithClient", appInd.getTextOnElement(oBrowser, By.xpath(String.format(dropDownSelectedVal, "client_id")), "text"));
        transmissionData.put("TemplateName", value);
        //------------ Drawdown Account info collect -------------
        transmissionData.put("DrawdownAccount", appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DrawdownAccount_Grid + "//span[@id='select2-drawdown_account_id-container']"), "text"));
        transmissionData.put("DrawdownAccountName", appInd.getAttribute(oBrowser, By.id("drawdown_account_name"), "value"));
        transmissionData.put("DrawdownReceipientName", appInd.getAttribute(oBrowser, By.id("drawdown_recipient_name"), "value"));
        transmissionData.put("DrawdownRoutingNumber", appInd.getAttribute(oBrowser, By.id("drawdown_routing_number"), "value"));
        transmissionData.put("DrawdownAccountNumber", appInd.getAttribute(oBrowser, By.id("drawdown_account_number"), "value"));
        transmissionData.put("DrawdownCurrency", appInd.getAttribute(oBrowser, By.id("drawdown_account_currency"), "value"));
        transmissionData.put("DrawdownCountry", appInd.getAttribute(oBrowser, By.id("drawdown_account_country"), "value"));
        transmissionData.put("DrawdownAccountID", appInd.getAttribute(oBrowser, By.id("drawdown_account_reference_account_id"), "value"));
        transmissionData.put("DrawdownAccountCode", appInd.getAttribute(oBrowser, By.id("drawdown_account_reference_account_code"), "value"));
        transmissionData.put("DrawdownMethod", appInd.getAttribute(oBrowser, By.id("select2-drawdown_method-container"), "title"));
        transmissionData.put("DrawdownNotes", appInd.getAttribute(oBrowser, By.id("managed_payment_client_transmission_file_drawdown_transmission_notes"), "value"));
        transmissionData.put("BuyerAccountID", appInd.getAttribute(oBrowser, By.id("drawdown_account_drawdown_buyer_account_id"), "class"));
        transmissionData.put("PayeeAccountID", appInd.getAttribute(oBrowser, By.id("drawdown_account_drawdown_payee_account_id"), "class"));
        //------------ Disbursement info collect -------------
        transmissionData.put("DisbursementAccount", appInd.getAttribute(oBrowser, By.id("select2-disbursement_account_id-container"), "title"));
        transmissionData.put("DisbursementAccountName", appInd.getAttribute(oBrowser, By.id("disbursement_account_name"), "title"));
        transmissionData.put("DisbursementRecipientName", appInd.getAttribute(oBrowser, By.id("disbursement_recipient_name"), "value"));
        transmissionData.put("DisbursementRoutingNumber", appInd.getAttribute(oBrowser, By.id("disbursement_routing_number"), "value"));
        transmissionData.put("DisbursementAccountNumber", appInd.getAttribute(oBrowser, By.id("disbursement_account_number"), "value"));
        transmissionData.put("DisbursementCurrency", appInd.getAttribute(oBrowser, By.id("disbursement_currency"), "value"));
        transmissionData.put("DisbursementCountry", appInd.getAttribute(oBrowser, By.id("disbursement_country"), "value"));
        transmissionData.put("DisbursementAccountID", appInd.getAttribute(oBrowser, By.id("disbursement_account_reference_account_id"), "value"));
        transmissionData.put("DisbursementAccountCode", appInd.getAttribute(oBrowser, By.id("disbursement_account_reference_account_code"), "value"));
        transmissionData.put("DisbursementMethod", appInd.getAttribute(oBrowser, By.id("select2-disbursement_method-container"), "title"));
        transmissionData.put("DisbursementNotes", appInd.getAttribute(oBrowser, By.id("managed_payment_client_transmission_file_disbursement_transmission_notes"), "value"));
        //------------ Delivery info collect -------------
        transmissionData.put("DevliverySite", appInd.getAttribute(oBrowser, By.id("select2-site_id-container"), "title"));
        transmissionData.put("DevliveryHostName", appInd.getAttribute(oBrowser, By.id("site_host_name"), "value"));
        transmissionData.put("DevliveryUsername", appInd.getAttribute(oBrowser, By.id("site_username"), "value"));
        transmissionData.put("DevliveryDirectory", appInd.getAttribute(oBrowser, By.id("site_directory"), "value"));

        if(!transmissionDataCompare.isEmpty()){
            Assert.assertTrue(transmissionData.equals(transmissionDataCompare));
        }
        return transmissionData;
    }

    public boolean verifyEditCloneFeatureOnTransmissionFile(WebDriver oBrowser){
        String tr = "(//div[@id='divClientTransmissionFilesDashboardContainer']//table)[2]/tbody/tr";
        String firstRowCol = "((//div[@id='divClientTransmissionFilesDashboardContainer']//table)[2]/tbody/tr[1])[1]/td";
        String randRowCol = "((//div[@id='divClientTransmissionFilesDashboardContainer']//table)[2]/tbody/tr[%s])[1]/td";
        String templateNameInput = "((//div[@id='divClientTransmissionFilesDashboardContainer']//table)[1]/tbody/tr[2])[1]/td[1]//input";
        String editHeader = "//h1[text()='Edit Transmission Template']";
        String updateSuccessMsg = "//div[@id='flash_container']//div[contains(@class,'alert alert-info')]";
        Map<String, String> transmissionData = new HashMap<>();
        try {
            int totalRows = appInd.getWebElements(oBrowser, By.xpath(tr)).size() > 15 ? 15 : appInd.getWebElements(oBrowser, By.xpath(tr)).size() ;
            randRowCol = String.format(randRowCol, new Random().nextInt(totalRows));
            //---------------------------------------- Edit of data verification --------------------------------
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(randRowCol+"[9]/a[@title='Edit Transmission File']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(randRowCol+"[9]/a[@title='Clone']")));
            appInd.clickObject(oBrowser, By.xpath(randRowCol+"[9]/a[@title='Edit Transmission File']"));
            appDep.threadSleep(1000);
            reports.writeResult(oBrowser, "Screenshot", "Click on Edit the template");
            appInd.switchToLastWindow(oBrowser);
            appDep.waitForTheElementState(oBrowser, By.xpath(editHeader), "visibility", "",30);
            String value = appInd.getAttribute(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_TemplateName_Edit, "value");
            appInd.clearAndSetObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_TemplateName_Edit, value+" Update");
            transmissionData = verifyTransmissionFileData(transmissionData);
            reports.writeResult(oBrowser, "Screenshot", "Update template name and collect all data for comparision");
            appInd.scrollTo(oBrowser, 0, 400);
            appInd.clickObject(oBrowser, By.id("btn_submit"));
            appDep.threadSleep(1000);
            appDep.waitForTheElementState(oBrowser, By.xpath(updateSuccessMsg), "visibility", "", 30);
            reports.writeResult(oBrowser, "Screenshot", "successfully update transmission template");
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(updateSuccessMsg), "text").contains("Client transmission file was successfully updated."));
            oBrowser.close();
            appInd.switchToLastWindow(oBrowser);
            oBrowser.navigate().refresh();
            appDep.threadSleep(2000);
            appInd.clearAndSetObject(oBrowser, By.xpath(templateNameInput), transmissionData.get("TemplateName"));
            appDep.threadSleep(2000);
            List<WebElement> templateCols = appInd.getWebElements(oBrowser, By.xpath(firstRowCol)); //collect 1st row data
            reports.writeResult(oBrowser, "Screenshot", "Transmission Template File > Grid > Search updated record ");
            Assert.assertTrue(templateCols.get(0).getText().trim().equalsIgnoreCase(transmissionData.get("TemplateName")));
            Assert.assertTrue(templateCols.get(2).getText().trim().equalsIgnoreCase(transmissionData.get("TransmissionType")));
            Assert.assertTrue(templateCols.get(3).getText().trim().equalsIgnoreCase(transmissionData.get("AssociateWithClient")));
            Assert.assertTrue(templateCols.get(5).getText().trim().equalsIgnoreCase(transmissionData.get("DrawdownMethod")));
            Assert.assertTrue(templateCols.get(6).getText().trim().equalsIgnoreCase(transmissionData.get("DisbursementMethod")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(firstRowCol+"[9]/a[@title='Edit Transmission File']")));
            reports.writeResult(oBrowser, "Screenshot", "Updated record verification completed");
            appInd.clickObject(oBrowser, By.xpath(firstRowCol+"[9]/a[@title='Edit Transmission File']"));
            appDep.threadSleep(1000);
            appInd.switchToLastWindow(oBrowser);
            appDep.waitForTheElementState(oBrowser, By.xpath(editHeader), "visibility", "",30);
            reports.writeResult(oBrowser, "Screenshot", "open edited record and verify it should load with updated data");
            verifyTransmissionFileData(transmissionData);
            //---------------------------------------- clone of data verification --------------------------------
            oBrowser.close();
            appInd.switchToLastWindow(oBrowser);
            appInd.clearAndSetObject(oBrowser, By.xpath(templateNameInput), transmissionData.get("TemplateName"));
            appDep.threadSleep(1000);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(firstRowCol+"[9]/a[@title='Clone']")));
            appDep.threadSleep(1000);
            appInd.switchToLastWindow(oBrowser);
            appDep.waitForTheElementState(oBrowser, By.xpath("//h1[text()='Clone Transmission Template']"), "visibility", "",30);
            reports.writeResult(oBrowser, "Screenshot", "Transmission Template File > Grid > Search updated record > click on Clone button");
            transmissionData.put("TemplateName", transmissionData.get("TemplateName").concat(" Clone"));
            verifyTransmissionFileData(transmissionData);
            appInd.scrollTo(oBrowser, 0, 400);
            appInd.clickObject(oBrowser, By.id("btn_submit"));
            appDep.threadSleep(1000);
            appDep.waitForTheElementState(oBrowser, By.xpath(updateSuccessMsg), "visibility", "", 30);
            reports.writeResult(oBrowser, "Screenshot", "successfully update transmission template");
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(updateSuccessMsg), "text").contains("Client transmission file was successfully cloned."));

            reports.writeResult(oBrowser, "Screenshot", "Clone data verification completed");
            oBrowser.close();
            appInd.switchToLastWindow(oBrowser);
            oBrowser.navigate().refresh();
            appDep.threadSleep(2000);
            appInd.clearAndSetObject(oBrowser, By.xpath(templateNameInput), transmissionData.get("TemplateName"));
            appDep.threadSleep(2000);
            templateCols = appInd.getWebElements(oBrowser, By.xpath(firstRowCol)); //collect 1st row data
            reports.writeResult(oBrowser, "Screenshot", "Transmission Template File > Grid > Search updated record ");
            Assert.assertTrue(templateCols.get(0).getText().trim().equalsIgnoreCase(transmissionData.get("TemplateName")));
            Assert.assertTrue(templateCols.get(2).getText().trim().equalsIgnoreCase(transmissionData.get("TransmissionType")));
            Assert.assertTrue(templateCols.get(3).getText().trim().equalsIgnoreCase(transmissionData.get("AssociateWithClient")));
            Assert.assertTrue(templateCols.get(5).getText().trim().equalsIgnoreCase(transmissionData.get("DrawdownMethod")));
            Assert.assertTrue(templateCols.get(6).getText().trim().equalsIgnoreCase(transmissionData.get("DisbursementMethod")));
            reports.writeResult(oBrowser, "Screenshot", "Transmission Template File > Grid > Clone data comparison successfully completed");
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifyEditCloneFeatureOnTransmissionFile()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifyEditCloneFeatureOnTransmissionFile()' method. " + e);
            return false;
        }
    }


    /********************************************************
     * Method Name      : validateTheHistoryOfTheGeneratedTransmissionFile()
     * Purpose          : Validate the history of the generated Transmission File
     * Author           : Gudi
     * Parameters       : oBrowser, TransmissionDetails
     * ReturnType       : boolean
     ********************************************************/
    private boolean validateTheHistoryOfTheGeneratedTransmissionFile(WebDriver oBrowser, Map<String, String> TransmissionDetails){
        try{
            Assert.assertTrue(navigateToTransmissionFile_Generate_OpenCases(oBrowser, TransmissionDetails.get("TemplateName")));

            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//h3)[1]"), "Text", "Template Information"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//h3)[2]"), "Text", "Delivery Details"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//h3)[3]"), "Text", "Drawdown Account"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//h3)[4]"), "Text", "Disbursement Account"));

            //Validate the Transfer PAge details
            //Template Information
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Provider']/following-sibling::dd[1]"), "Text", TransmissionDetails.get("ChooseProvider")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Transmission Template']/following-sibling::dd[1]"), "Text", TransmissionDetails.get("TransmissionTemplate")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Transfer Type']/following-sibling::dd[1]"), "Text", TransmissionDetails.get("TransmissionType")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Associated with Client']/following-sibling::dd[1]"), "Text", TransmissionDetails.get("AssociateWithClient")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Notes']/following-sibling::dd[1]"), "Text", TransmissionDetails.get("TemplateName")));

            //Drawdown Account
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Account Name'])[1]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DA_AccountName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Recipient Name'])[1]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DA_RecipientName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Routing Number'])[1]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DA_RoutingNumber")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Account Number'])[1]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DA_AccountNumber")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Currency'])[1]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DA_Currency")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Country'])[1]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DA_Country")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Account ID / Buyer ID'])[1]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DA_Account_BuyerID")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Account Code / Client Code'])[1]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DA_Account_ClientCode")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Drawdown Method'])[1]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DrawdownMethod")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Drawdown Notes'])[1]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DA_DrawnNotes")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Buyer Account ID'])[1]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DA_BuyerAccountID")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Payee Account ID'])[1]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DA_PayeeAccountID")));

            //Disbursement Account
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Account Name'])[2]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DI_AccountName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Recipient Name'])[2]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DI_RecipientName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Routing Number'])[2]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DI_RoutingNumber")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Account Number'])[2]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DI_AccountNumber")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Currency'])[2]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DI_Currency")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Country'])[2]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DI_Country")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Account ID / Buyer ID'])[2]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DI_Account_BuyerID")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Account Code / Client Code'])[2]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DI_Account_ClientCode")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Disbursement Method'])[1]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DisbursementMethod")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Disbursement Notes'])[1]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DI_DisbursementNotes")));

            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateTheHistoryOfTheGeneratedTransmissionFile()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateTheHistoryOfTheGeneratedTransmissionFile()' method. " + e);
            return false;
        }
    }





    /********************************************************
     * Method Name      : downloadAndValidateTheCSVFileUnderAttachmentsTabOfTransferPage()
     * Purpose          : Navigate to attachments tab of Transfer Page, download and validate the .csv file
     * Author           : Gudi
     * Parameters       : oBrowser, TransmissionDetails
     * ReturnType       : boolean
     ********************************************************/
    public boolean downloadAndValidateTheCSVFileUnderAttachmentsTabOfTransferPage(WebDriver oBrowser, String buyerAccountDetails, String payeeAccountDetails, Map<String, String> TransmissionDetails){
        String name = null;
        String description = null;
        String attachmentType = null;
        String arrName[];
        File objFile = null;
        File objArrFiles[];
        String filePath = null;
        BufferedReader br = null;
        String sLine = "";
        String arrCSVFileContent[];
        String csvData = "";
        String arrAutoGenerate[];
        try{
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachments_Link));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachments_Grid + "//a[@title='Show']"), "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachments_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[1]"), "Text").contains("Transfer File:"));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachments_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[1]"), "Text").contains(".csv"));
            name = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachments_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[1]"), "Text");
            arrName = name.split(":");
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachments_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[2]"), "Text").contains("Transfer File generated by " + appInd.getPropertyValueByKeyName("qaUserName") + " on "));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachments_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[2]"), "Text").contains("for $" + TransmissionDetails.get("TransferAmount")));
            description = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachments_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[2]"), "Text");
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachments_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[3]"), "Text").contains("Other"));
            attachmentType = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachments_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[3]"), "Text");

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachments_Grid + "//a[@title='Show']")));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachment_Show_Dialog + "//a"), "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachment_Show_Dialog + "//dt[text()='Name']/following-sibling::dd[1]"), "Text", name));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachment_Show_Dialog + "//dt[text()='Description']/following-sibling::dd[1]"), "Text", description));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachment_Show_Dialog + "//dt[text()='File Name']/following-sibling::dd[1]/a"), "Text").contains(arrName[1].trim()));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachment_Show_Dialog + "//dt[text()='Attachment Type']/following-sibling::dd[1]"), "Text", attachmentType));


            //Download the .csv file and validat the same
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachment_Show_Dialog + "//a")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appDep.threadSleep(8000);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachment_Show_Dialog + "//button")));

            boolean blnRes = false;
            objFile = new File("C:/Users/"+System.getProperty("user.name")+"/Downloads");
            objArrFiles = objFile.listFiles();
            for(int i=0; i < objArrFiles.length; i++){
                if(objArrFiles[i].isFile()){
                    filePath = objArrFiles[i].getPath();
                    if(filePath.contains(".csv")){
                        br = new BufferedReader(new FileReader(filePath));
                        sLine = br.readLine();
                        if(sLine.contains(arrName[1].trim())){
                            blnRes = true;
                            break;
                        }
                    }
                }
            }

            br.close();
            br = null;
            sLine = "";

            if(blnRes){
                br = new BufferedReader(new FileReader(filePath));
                while(true){
                    sLine=br.readLine();
                    if(sLine==null) {
                        break;
                    }
                    csvData+=sLine+"#";
                }

                arrCSVFileContent = csvData.split("#");
                Assert.assertTrue(arrCSVFileContent[0].equalsIgnoreCase("0000|" + arrName[1].trim()));

                if(TransmissionDetails.get("BuyerAccountID").equalsIgnoreCase("Off#NA") && TransmissionDetails.get("PayeeAccountID").equalsIgnoreCase("Off#NA"))
                    Assert.assertTrue(arrCSVFileContent[1].endsWith("|||||"), "The .csf file have the records form 'Buyer Account ID' & 'Payee Account ID' eventhough no data is entered into it while creating the 'Transmission Files'");
                else if(TransmissionDetails.get("BuyerAccountID").trim().equalsIgnoreCase("On#NA") && TransmissionDetails.get("PayeeAccountID").trim().equalsIgnoreCase("On#NA")){
                    arrAutoGenerate = arrCSVFileContent[1].split("\\|");
                    Assert.assertTrue(!arrAutoGenerate[arrAutoGenerate.length-2].isEmpty(), "Failed to auto-generate the 'Buyer Account ID'");
                    Assert.assertTrue(!arrAutoGenerate[arrAutoGenerate.length-1].isEmpty(), "Failed to auto-generate the 'Payee Account ID'");
                    reports.writeResult(oBrowser, "Screenshot", "The auto-generated Buyer Account ID: '"+arrAutoGenerate[arrAutoGenerate.length-2]+"' & Payee Account ID: '"+arrAutoGenerate[arrAutoGenerate.length-1]+"'");
                }
                else if(TransmissionDetails.get("BuyerAccountID").trim().equalsIgnoreCase("Off#Buyer123") && TransmissionDetails.get("PayeeAccountID").trim().equalsIgnoreCase("Off#Payee123"))
                    Assert.assertTrue(arrCSVFileContent[1].endsWith("|||"+TransmissionDetails.get("DA_BuyerAccountID")+"|"+TransmissionDetails.get("DA_PayeeAccountID")), "The .csf file doesnot have the records form 'Buyer Account ID' & 'Payee Account ID' eventhough data is entered into it while creating the 'Transmission Files'");
                else {
                    reports.writeResult(oBrowser, "Fail", "Invalid selection for the 'Buyer Account ID' & 'Payee Account ID' toggle values");
                    Assert.fail("Invalid selection for the 'Buyer Account ID' & 'Payee Account ID' toggle values");
                }

                int disbursementRowNum = 2;
                if(TransmissionDetails.get("DrawdownMethod").equalsIgnoreCase("02 – Reverse Wire")){
                    disbursementRowNum = 3;
                    Assert.assertTrue(arrCSVFileContent[2].equalsIgnoreCase("2100|"+TransmissionDetails.get("DA_RecipientName")+"|"+TransmissionDetails.get("DA_RoutingNumber")+"|"+TransmissionDetails.get("DA_AccountNumber")), "The 'Drawdown method' output value is not matching with the .csv file");
                }else if(TransmissionDetails.get("DrawdownMethod").equalsIgnoreCase("04 – ACH Debit (CIS to debit buyer’s account)")){
                    disbursementRowNum = 3;
                    Assert.assertTrue(arrCSVFileContent[2].equalsIgnoreCase("2400|"+TransmissionDetails.get("DA_RecipientName")+"|"+TransmissionDetails.get("DA_RoutingNumber")+"|"+TransmissionDetails.get("DA_AccountNumber")+"|||||||||||"), "The 'Drawdown method' output value is not matching with the .csv file");
                }

                if(TransmissionDetails.get("DisbursementMethod").equalsIgnoreCase("01 – Wire (initiated by CIS)")){
                    Assert.assertTrue(arrCSVFileContent[disbursementRowNum].equalsIgnoreCase("2201|"+TransmissionDetails.get("DA_RecipientName")+"|"+TransmissionDetails.get("DA_RoutingNumber")+"|"+TransmissionDetails.get("DA_AccountNumber")+"|||||||||||"), "The 'Drawdown method' output value is not matching with the .csv file");
                }else if(TransmissionDetails.get("DisbursementMethod").equalsIgnoreCase("03 – ACH Credit using CCD/CCD+ (Initiated by CIS)")){
                    Assert.assertTrue(arrCSVFileContent[disbursementRowNum].equalsIgnoreCase("2300|"+TransmissionDetails.get("DA_RecipientName")+"|"+TransmissionDetails.get("DA_RoutingNumber")+"|"+TransmissionDetails.get("DA_AccountNumber")+"|||||||||||"), "The 'Drawdown method' output value is not matching with the .csv file");
                }

            }else{
                reports.writeResult(oBrowser, "Fail", "Fail to download the .csv file from the Tranfer Funds page");
                Assert.fail("Fail to download the .csv file from the Tranfer Funds page");
            }

            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'downloadAndValidateTheCSVFileUnderAttachmentsTabOfTransferPage()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'downloadAndValidateTheCSVFileUnderAttachmentsTabOfTransferPage()' method. " + e);
            return false;
        }finally{
            try{
                name = null; description = null; attachmentType = null; arrName = null; arrCSVFileContent = null;
                br.close();
                br = null;
                sLine = null;
                new File(filePath).delete();
                new File(filePath).deleteOnExit();
                csvData = null;
                arrAutoGenerate = null;
            }catch(Exception e){}
        }
    }





    /********************************************************
     * Method Name      : transferAmountAndValidateTheDetails()
     * Purpose          : Validate the Transmission File created details and Transfer Amount
     * Author           : Gudi
     * Parameters       : oBrowser, TransmissionDetails
     * ReturnType       : Map<String, String>
     ********************************************************/
    public Map<String, String> transferAmountAndValidateTheDetails(WebDriver oBrowser, Map<String, String> TransmissionDetails){
        try{
            TransmissionDetails.remove("TestPassed");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_TransferFunds_Btn));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_TransferFunds_Dialog + "//button[@id='funds_submit']"), "Clickable", "", waitTimeOut);
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "'Transfer Funds' page details");
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_TransferFunds_Dialog + "//h4"), "Text", "Transfer Funds"));
            //Template Information
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//label[text()='Provider']/following-sibling::input"), "Value", TransmissionDetails.get("ChooseProvider")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//label[text()='Transmission Template']/following-sibling::input"), "Value", TransmissionDetails.get("TransmissionTemplate")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//label[text()='Transfer Type']/following-sibling::input"), "Value", TransmissionDetails.get("TransmissionType")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//label[text()='Associated with Client']/following-sibling::input"), "Value", TransmissionDetails.get("AssociateWithClient")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//label[text()='Template Name']/following-sibling::input"), "Value", TransmissionDetails.get("TemplateName")));

            //Drawdown Account
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//label[text()='Account Name']/following-sibling::input)[1]"), "Value", TransmissionDetails.get("DA_AccountName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//label[text()='Recipient Name']/following-sibling::input)[1]"), "Value", TransmissionDetails.get("DA_RecipientName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//label[text()='Routing Number']/following-sibling::input)[1]"), "Value", TransmissionDetails.get("DA_RoutingNumber")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//label[text()='Account Number']/following-sibling::input)[1]"), "Value", TransmissionDetails.get("DA_AccountNumber")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//label[text()='Currency']/following-sibling::input)[1]"), "Value", TransmissionDetails.get("DA_Currency")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//label[text()='Country']/following-sibling::input)[1]"), "Value", TransmissionDetails.get("DA_Country")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//label[text()='Account ID / Buyer ID']/following-sibling::input)[1]"), "Value", TransmissionDetails.get("DA_Account_BuyerID")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//label[text()='Account Code / Client Code']/following-sibling::input)[1]"), "Value", TransmissionDetails.get("DA_Account_ClientCode")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//label[text()='Drawdown Method']/following-sibling::input)[1]"), "Value", TransmissionDetails.get("DrawdownMethod")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//label[text()='Drawdown Notes']/following-sibling::input)[1]"), "Value", TransmissionDetails.get("DA_DrawnNotes")));

            if(!TransmissionDetails.get("DA_BuyerAccountID").equalsIgnoreCase("Auto-Generate"))
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//label[text()='Buyer Account ID']/following-sibling::input)[1]"), "Value", TransmissionDetails.get("DA_BuyerAccountID")));
            else {
                Assert.assertTrue(!appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//label[text()='Buyer Account ID']/following-sibling::input)[1]"), "Value").isEmpty(), "The 'Buyer Account ID' should not be blank and must have auto-generated value'");
                TransmissionDetails.remove("DA_BuyerAccountID");
                TransmissionDetails.put("DA_BuyerAccountID", appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//label[text()='Buyer Account ID']/following-sibling::input)[1]"), "Value"));
            }

            if(!TransmissionDetails.get("DA_PayeeAccountID").equalsIgnoreCase("Auto-Generate"))
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//label[text()='Payee Account ID']/following-sibling::input)[1]"), "Value", TransmissionDetails.get("DA_PayeeAccountID")));
            else{
                Assert.assertTrue(!appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//label[text()='Payee Account ID']/following-sibling::input)[1]"), "Value").isEmpty(), "The 'Payee Account ID' should not be blank and must have auto-generated value'");
                TransmissionDetails.remove("DA_PayeeAccountID");
                TransmissionDetails.put("DA_PayeeAccountID", appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//label[text()='Payee Account ID']/following-sibling::input)[1]"), "Value"));
            }

            //Disbursement Information
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//label[text()='Account Name']/following-sibling::input)[2]"), "Value", TransmissionDetails.get("DI_AccountName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//label[text()='Recipient Name']/following-sibling::input)[2]"), "Value", TransmissionDetails.get("DI_RecipientName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//label[text()='Routing Number']/following-sibling::input)[2]"), "Value", TransmissionDetails.get("DI_RoutingNumber")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//label[text()='Account Number']/following-sibling::input)[2]"), "Value", TransmissionDetails.get("DI_AccountNumber")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//label[text()='Currency']/following-sibling::input)[2]"), "Value", TransmissionDetails.get("DI_Currency")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//label[text()='Country']/following-sibling::input)[2]"), "Value", TransmissionDetails.get("DI_Country")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//label[text()='Account ID / Buyer ID']/following-sibling::input)[2]"), "Value", TransmissionDetails.get("DI_Account_BuyerID")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//label[text()='Account Code / Client Code']/following-sibling::input)[2]"), "Value", TransmissionDetails.get("DI_Account_ClientCode")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//label[text()='Disbursement Method']/following-sibling::input)[1]"), "Value", TransmissionDetails.get("DisbursementMethod")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_Section + "//label[text()='Disbursement Notes']/following-sibling::input)[1]"), "Value", TransmissionDetails.get("DI_DisbursementNotes")));

            //Enter amount
            appInd.scrollToElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EnterAmount_Edit);
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EnterAmount_Edit, "1"));
            TransmissionDetails.put("TransferAmount", appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EnterAmount_Edit, "Value"));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_TransferFunds_Dialog + "//button[@id='funds_submit']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_LogActivity_Btn, "Clickable", "", waitTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachments_Link, "Clickable", "", waitTimeOut);
            TransmissionDetails.put("TestPassed", "True");
            return TransmissionDetails;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'transferAmountAndValidateTheDetails()' method. " + e);
            TransmissionDetails.put("TestPassed", "False");
            return TransmissionDetails;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'transferAmountAndValidateTheDetails()' method. " + e);
            TransmissionDetails.put("TestPassed", "False");
            return TransmissionDetails;
        }
    }





    /********************************************************
     * Method Name      : validateTransferPageDetails()
     * Purpose          : After Transfer Amount validate the Transfer page details
     * Author           : Gudi
     * Parameters       : oBrowser, TransmissionDetails
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateTransferPageDetails(WebDriver oBrowser, Map<String, String> TransmissionDetails){
        try{
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Transfer Page details");
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Transfer_Header, "Text").contains("» Managed Payment » Transfer Template » Transfer"));

            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//h3)[1]"), "Text", "Template Information"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//h3)[2]"), "Text", "Delivery Details"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//h3)[3]"), "Text", "Drawdown Account"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//h3)[4]"), "Text", "Disbursement Account"));

            //Validate the Transfer PAge details
            //Transfer fund information
            String actualFundInfo = appInd.getTextOnElement(oBrowser, By.xpath("//div[@class='blurb-success status']"), "text");
            String expectedFuncInfo = "Funds transfer initiated on "+appInd.dateAddAndReturn("MM/dd/yy",0)+" for $"+TransmissionDetails.get("TransferAmount")+".00";
            Assert.assertTrue(actualFundInfo.trim().equalsIgnoreCase(expectedFuncInfo.trim()), "Transfer fund details are not visible as expected");

            //Template Information
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Provider']/following-sibling::dd[1]"), "Text", TransmissionDetails.get("ChooseProvider")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Transmission Template']/following-sibling::dd[1]"), "Text", TransmissionDetails.get("TransmissionTemplate")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Transfer Type']/following-sibling::dd[1]"), "Text", TransmissionDetails.get("TransmissionType")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Associated with Client']/following-sibling::dd[1]"), "Text", TransmissionDetails.get("AssociateWithClient")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Notes']/following-sibling::dd[1]"), "Text", TransmissionDetails.get("TemplateName")));

            //Drawdown Account
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Account Name'])[1]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DA_AccountName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Recipient Name'])[1]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DA_RecipientName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Routing Number'])[1]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DA_RoutingNumber")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Account Number'])[1]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DA_AccountNumber")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Currency'])[1]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DA_Currency")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Country'])[1]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DA_Country")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Account ID / Buyer ID'])[1]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DA_Account_BuyerID")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Account Code / Client Code'])[1]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DA_Account_ClientCode")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Drawdown Method'])[1]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DrawdownMethod")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Drawdown Notes'])[1]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DA_DrawnNotes")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Buyer Account ID'])[1]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DA_BuyerAccountID")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Payee Account ID'])[1]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DA_PayeeAccountID")));

            //Disbursement Account
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Account Name'])[2]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DI_AccountName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Recipient Name'])[2]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DI_RecipientName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Routing Number'])[2]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DI_RoutingNumber")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Account Number'])[2]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DI_AccountNumber")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Currency'])[2]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DI_Currency")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Country'])[2]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DI_Country")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Account ID / Buyer ID'])[2]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DI_Account_BuyerID")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Account Code / Client Code'])[2]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DI_Account_ClientCode")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Disbursement Method'])[1]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DisbursementMethod")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//dt[text()='Disbursement Notes'])[1]/following-sibling::dd[1]"), "Text", TransmissionDetails.get("DI_DisbursementNotes")));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateTransferPageDetails()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateTransferPageDetails()' method. " + e);
            return false;
        }
    }




    /********************************************************
     * Method Name      : createTransmissionFileDetails()
     * Purpose          : Add the Transmission File details
     * Author           : Gudi
     * Parameters       : oBrowser, buyerAccountDetails, payeeAccountDetails, dataTable
     * ReturnType       : Map<String, String>
     ********************************************************/
    public Map<String, String> createTransmissionFileDetails(WebDriver oBrowser, String buyerAccountDetails, String payeeAccountDetails, Map<String, String> TransmissionDetails){
        String arrBuyerDetails[];
        String arrPayeeDetails[];
        try{
            Assert.assertTrue(appDep.selectManagePaymentModules(oBrowser, "Transmission Files"));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ManagedPayments_Add_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_TransmissionFile_Submit_Btn, "Clickable", "", waitTimeOut);
            appDep.threadSleep(2000);

            //Enter the data to create the Transmission File
            //Template Information
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_TemplateInformation_Grid + "//span[contains(@aria-labelledby, '-provider_id-')]")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchOption_Edit, "Clickable", "", minTimeOut);
            appInd.setMultipleValues(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchOption_Edit, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchResult_Options, TransmissionDetails.get("ChooseProvider"));

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_TemplateInformation_Grid + "//span[contains(@aria-labelledby,'-transmission_file_id-')]")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchOption_Edit, "Clickable", "", minTimeOut);
            appInd.setMultipleValues(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchOption_Edit, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchResult_Options, TransmissionDetails.get("TransmissionTemplate"));

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_TemplateInformation_Grid + "//span[contains(@aria-labelledby,'-type_id-')]")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchOption_Edit, "Clickable", "", minTimeOut);
            appInd.setMultipleValues(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchOption_Edit, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchResult_Options, TransmissionDetails.get("TransmissionType"));

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_TemplateInformation_Grid + "//span[contains(@aria-labelledby, '-client_id-')]")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchOption_Edit, "Clickable", "", minTimeOut);
            appInd.setMultipleValues(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchOption_Edit, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchResult_Options, TransmissionDetails.get("AssociateWithClient"));

            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_TemplateName_Edit, TransmissionDetails.get("TemplateName")));


            //Drawdown Account
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DrawdownAccount_Grid + "//span[@id='select2-drawdown_account_id-container']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchOption_Edit, "Clickable", "", minTimeOut);
            appInd.setMultipleValues(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchOption_Edit, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchResult_Options, TransmissionDetails.get("ChooseDrawdownAccount"));
            appDep.threadSleep(2000);
            TransmissionDetails.put("DA_AccountName", appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_DA_AccountName_Edit, "Value"));
            TransmissionDetails.put("DA_RecipientName", appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_DA_RecipientName_Edit, "Value"));
            TransmissionDetails.put("DA_RoutingNumber", appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_DA_RoutingNumber_Edit, "Value"));
            TransmissionDetails.put("DA_AccountNumber", appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_DA_AccountNumber_Edit, "Value"));
            TransmissionDetails.put("DA_Currency", appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_DA_Currency_Edit, "Value"));
            TransmissionDetails.put("DA_Country", appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_DA_Country_Edit, "Value"));

            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Drawdown_Account_BuyerID_Edit, TransmissionDetails.get("DA_Account_BuyerID")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Drawdown_Account_BuyerCode_Edit, TransmissionDetails.get("DA_Account_ClientCode")));

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DrawdownAccount_Grid + "//span[@id='select2-drawdown_method-container']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchOption_Edit, "Clickable", "", minTimeOut);
            appInd.setMultipleValues(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchOption_Edit, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchResultsMethods_List, TransmissionDetails.get("DrawdownMethod"));

            arrBuyerDetails = TransmissionDetails.get("BuyerAccountID").split("#", -1);
            if(arrBuyerDetails[0].equalsIgnoreCase("On")){
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_BuyerAccountID_Toggle_Btn));
                Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_BuyerAccountID_Edit, "Value", "Auto-Generate"));
            }else{
                if(!arrBuyerDetails[1].equalsIgnoreCase("NA"))
                    Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_BuyerAccountID_Edit, arrBuyerDetails[1]));
            }


            arrPayeeDetails = TransmissionDetails.get("PayeeAccountID").split("#", -1);
            if(arrPayeeDetails[0].equalsIgnoreCase("On")){
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeAccountID_Toggle_Btn));
                Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeAccountID_Edit, "Value", "Auto-Generate"));
            }else{
                if(!arrPayeeDetails[1].equalsIgnoreCase("NA"))
                    Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeAccountID_Edit, arrPayeeDetails[1]));
            }

            TransmissionDetails.put("DA_DrawnNotes", appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_DA_DrawdownNotes_Edit, "Value"));
            TransmissionDetails.put("DA_BuyerAccountID", appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_BuyerAccountID_Edit, "Value"));
            TransmissionDetails.put("DA_PayeeAccountID", appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeAccountID_Edit, "Value"));

            //Disbursement Information
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DisbursementInformation_grid + "//span[@id='select2-disbursement_account_id-container']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchOption_Edit, "Clickable", "", minTimeOut);
            appInd.setMultipleValues(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchOption_Edit, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchResult_Options, TransmissionDetails.get("ChooseDisbursementAccount"));
            appDep.threadSleep(2000);
            TransmissionDetails.put("DI_AccountName", appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_DI_AccountName_Edit, "Value"));
            TransmissionDetails.put("DI_RecipientName", appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_DI_RecipientName_Edit, "Value"));
            TransmissionDetails.put("DI_RoutingNumber", appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_DI_RoutingNumber_Edit, "Value"));
            TransmissionDetails.put("DI_AccountNumber", appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_DI_AccountNumber_Edit, "Value"));
            TransmissionDetails.put("DI_Currency", appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_DI_Currency_Edit, "Value"));
            TransmissionDetails.put("DI_Country", appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_DI_Country_Edit, "Value"));

            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Disbursement_Account_BuyerID_Edit, TransmissionDetails.get("DI_Account_BuyerID")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Disbursement_Account_BuyerCode_Edit, TransmissionDetails.get("DI_Account_ClientCode")));

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_DisbursementInformation_grid + "//span[@id='select2-disbursement_method-container']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchOption_Edit, "Clickable", "", minTimeOut);
            appInd.setMultipleValues(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchOption_Edit, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchResultsMethods_List, TransmissionDetails.get("DisbursementMethod"));

            TransmissionDetails.put("DI_DisbursementNotes", appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_DI_DisbursementNotes_Edit, "Value"));
            reports.writeResult(oBrowser, "Screenshot", "All the required details are entered while adding the new Transmission File");

            //Click 'Submit' button after entering the data
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_TransmissionFile_Submit_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ConfirmationMsg_Label, "Visibility", "", waitTimeOut);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ConfirmationMsg_Label, "Text").contains("Client transmission file was successfully created."));
            appDep.waitForTheElementState(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_TransmissionFiles_Grid + "//input)[1]"), "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_TransmissionFiles_Grid + "//input)[1]"), TransmissionDetails.get("TemplateName")));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Search Template and start verification of grid data");
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_TransmissionFiles_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td[1]"), "Text", TransmissionDetails.get("TemplateName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_TransmissionFiles_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td[2]"), "Text", TransmissionDetails.get("TransmissionTemplate")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_TransmissionFiles_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td[3]"), "Text", TransmissionDetails.get("TransmissionType")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_TransmissionFiles_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td[4]"), "Text", TransmissionDetails.get("AssociateWithClient")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_TransmissionFiles_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td[6]"), "Text", TransmissionDetails.get("DrawdownMethod")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_TransmissionFiles_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td[7]"), "Text", TransmissionDetails.get("DisbursementMethod")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_TransmissionFiles_Grid + "//a[@title='Deactivate']")));
            reports.writeResult(oBrowser, "Screenshot", "The new Transmission File was successfully created");
            TransmissionDetails.put("TestPassed", "True");
            return TransmissionDetails;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createTransmissionFileDetails()' method. " + e);
            TransmissionDetails.put("TestPassed", "False");
            return TransmissionDetails;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createTransmissionFileDetails()' method. " + e);
            TransmissionDetails.put("TestPassed", "False");
            return TransmissionDetails;
        }finally{arrBuyerDetails = null; arrPayeeDetails = null;}
    }




    /********************************************************
     * Method Name      : navigateToTransmissionFile_Generate_OpenCases()
     * Purpose          : User navigates to "Managed Payment » Transfer Template » Transfer" page
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean navigateToTransmissionFile_Generate_OpenCases(WebDriver oBrowser, String transmissionTemplateName){
        try{
            Assert.assertTrue(appDep.selectManagePaymentModules(oBrowser, "Transmission Files"));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Generated_Link));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Generated_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td[1]"), "Visibility", "", waitTimeOut);
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_Generated_Grid + "//input)[7]"), transmissionTemplateName));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_OpenCases_Link));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);

            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Transfer Page details");
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Transfer_Header, "Text").contains("» Managed Payment » Transfer Template » Transfer"));
            appDep.threadSleep(2000);
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'navigateToTransmissionFile_Generate_OpenCases()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'navigateToTransmissionFile_Generate_OpenCases()' method. " + e);
            return false;
        }
    }




    /********************************************************
     * Method Name      : createDownloadAndDeleteTheAttachment_TransmissionFile()
     * Purpose          : Create, download and delete the attachment under "Managed Payment » Transfer Template » Transfer"
     * Author           : Gudi
     * Parameters       : oBrowser, pageName, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean createDownloadAndDeleteTheAttachment_TransmissionFile(WebDriver oBrowser, String pageName, DataTable dataTable){
        List<Map<String, String>> attachmentData = null;
        String timeStamp = null;
        Map<String, String> attachment = null;
        try{
            timeStamp = appInd.getDateTime("Shhmmss");
            attachmentData = dataTable.asMaps(String.class, String.class);
            attachment = new HashMap<String, String>();
            attachment.put("Name", attachmentData.get(0).get("Name")+timeStamp);
            attachment.put("Description", attachmentData.get(0).get("Description"));
            attachment.put("AttachmentType", attachmentData.get(0).get("AttachmentType"));
            attachment.put("FileToUpload", attachmentData.get(0).get("FileToUpload"));

            Assert.assertTrue(navigateToTransmissionFile_Generate_OpenCases(oBrowser, transmissionFileName));
            attachment = addPaymentDetailsAttachment(oBrowser, attachment);
            Assert.assertTrue(attachment.get("TestPassed").equalsIgnoreCase("True"), "The addPaymentDetailsAttachment() method was failed");

            //Download the file
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachments_Grid + "//a[@title='Show']")));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachment_Show_Dialog + "//a"), "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachment_Show_Dialog + "//a")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appDep.threadSleep(8000);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachment_Show_Dialog + "//button")));

            //Validate that the file downloaded successful
            boolean blnRes = appDep.verifyLatestDownloadFile(oBrowser, "NA", ".pdf", "Dummy PDF file", true);
            if (blnRes) {
                reports.writeResult(oBrowser, "Pass", "The '" + pageName + " Attachment' pdf file was downloaded successful");
            } else {
                reports.writeResult(oBrowser, "Fail", "Failed to download the '" + pageName + " Attachment' pdf file");
                Assert.assertTrue(false, "Failed to download the '" + pageName + " Attachment' pdf file");
            }

            Assert.assertTrue(payCRM_modules_generalUIBaseStep.downloadAndDeleteTheAttachment(oBrowser, pageName, PayCRM_Modules_ManagedPaymentsUIPage.obj_TransferFile_Attachment_Export_Btn, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachments_Grid + "//a[@title='Delete']")));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createDownloadAndDeleteTheAttachment_TransmissionFile()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createDownloadAndDeleteTheAttachment_TransmissionFile()' method. " + e);
            return false;
        }finally {attachmentData = null; timeStamp = null; attachment = null;}
    }





    /********************************************************
     * Method Name      : verifyUserArchivesOpenBankFileReceipts()
     * Purpose          : Verify user archives the Open Bank file Receipts and validates the same
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean verifyUserArchivesOpenBankFileReceipts(WebDriver oBrowser){
        Map<String, String> openBankFileRowData = null;
        Alert oAlert = null;
        String arrColumnNames[];
        try{
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_OpenBankFileReceipts_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);

            //Click on last column for any data which will show "Archive Bank FIle Receipts" if hover on image.
            /*Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_OpenBankFileReceipts_Grid + "//tr//li//div[@aria-haspopup])[4]")));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_OpenBankFileReceipts_FilterOptions + "//ul/li//span[text()='Does not equal']"), "Clickable", "", waitTimeOut);
            appDep.threadSleep(1000);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_OpenBankFileReceipts_FilterOptions + "//ul/li//span[text()='Does not equal']")));
            appDep.threadSleep(1000);
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_OpenBankFileReceipts_Grid + "//input)[4]"), "0"));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appDep.threadSleep(2000);*/

            //'Open Bank File Receipts' Grid First row cell data and store in the Map object
            openBankFileRowData = appDep.readManagedPaymentsGridDataAndReturnAsMap(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_OpenBankFileReceipts_Grid+"//tr[1][contains(@class, 'row-lines dx-column-lines')]/td", "PaynetClientName#PaynetClientID#BankFileID#TransactionFileID#FileName#ReturnFileID#FileReceiptName#Status#FileTotal", "Open Bank File Receipts");
            reports.writeResult(oBrowser, "Screenshot", "'Open Bank File Receipts' table grid after filter");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_OpenBankFileReceipts_Grid + "//a[@title='Archive Bank File Receipt'])[1]")));
            appDep.threadSleep(2000);
            oAlert = oBrowser.switchTo().alert();
            Assert.assertTrue(oAlert.getText().equalsIgnoreCase("Are you sure?"), "Failed to display the Alert while Archieving the Open Bank File Receipt");
            oAlert.accept();
            appDep.threadSleep(2000);


            //Data should get remove from "Open Bank File Receipts"
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_OpenBankFileReceipts_Grid + "//input)[5]"), openBankFileRowData.get("FileName")));
            appDep.threadSleep(2000);
            /*Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_OpenBankFileReceipts_Grid + "//tr//li//div[@aria-haspopup])[4]")));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_OpenBankFileReceipts_FilterOptions + "//ul/li//span[text()='Reset']"), "Clickable", "", waitTimeOut);
            appDep.threadSleep(1000);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_OpenBankFileReceipts_FilterOptions + "//ul/li//span[text()='Reset']")));
            appDep.threadSleep(1000);*/
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_OpenBankFileReceipts_Grid + "//input)[6]"), openBankFileRowData.get("ReturnFileID")));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_OpenBankFileReceipts_Grid+"//tr[1][contains(@class, 'row-lines dx-column-lines')]/td)[1]")));
            reports.writeResult(oBrowser, "Screenshot", "'Open Bank File Receipts' table grid data was removed after the 'Archiving the Bank File Receipts'");

            //"Open Bank File Receipts" entry should show in "Archived Bank File Receipts"
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ArchievedBankFileReceipts_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_ArchievedBankFileReceipts_Grid + "//input)[5]"), openBankFileRowData.get("FileName")));
            appDep.threadSleep(2000);
//            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_ArchievedBankFileReceipts_Grid + "//input)[4]"), openBankFileRowData.get("TransactionFileID")));
//            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "'Open Bank File Receipts' table grid data was now present in the 'Archived Bank File Receipts' grid");

            arrColumnNames = "PaynetClientName#PaynetClientID#BankFileID#TransactionFileID#FileName#ReturnFileID#FileReceiptName#Status#FileTotal".split("#");
            for(int i=0; i<arrColumnNames.length; i++){
                if(!arrColumnNames[i].equalsIgnoreCase("ReturnFileID")){
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_ArchievedBankFileReceipts_Grid + "//tr[1][contains(@class, 'row-lines dx-column-lines')]/td)["+(i+1)+"]"), "Text", openBankFileRowData.get(arrColumnNames[i])));
                }else i++;
            }
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifyUserArchivesOpenBankFileReceipts()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifyUserArchivesOpenBankFileReceipts()' method. " + e);
            return false;
        }finally {openBankFileRowData = null; oAlert = null; arrColumnNames = null;}
    }




    /********************************************************
     * Method Name      : verifyBankFileReceiptsColumnNames()
     * Purpose          : User validates the column names from the Bank File Receipts menu (Open Bank File Receipts & Archived Bank File Receipts grids)
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean verifyBankFileReceiptsColumnNames(WebDriver oBrowser){
        String arrOpenBankFileColumns[];
        String arrArchivedBankFileColumns[];
        try{
            Assert.assertTrue(appDep.selectManagePaymentModules(oBrowser, "Bank File Receipts"));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_OpenBankFileReceipts_Link));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ArchievedBankFileReceipts_Link));
            reports.writeResult(oBrowser, "Screenshot", "'Open Bank File Receipts' table grid before filter");

            //Open Bank File Receipt column name validation
            arrOpenBankFileColumns = "PayNet Client Name#PayNet Client ID#Bank File ID#Transaction File ID#File Name#Return File ID#File Receipt Name#Status#File Total".split("#");
            for(int i=0; i<arrOpenBankFileColumns.length; i++){
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_OpenBankFileReceipts_Grid + "//tr[contains(@class, 'dx-header-row')]/td/div[contains(@class, 'text-content')])["+(i+1)+"]"), "Text", arrOpenBankFileColumns[i]));
            }


            //Archived Bank File Receipt column nmaes validation
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ArchievedBankFileReceipts_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            arrArchivedBankFileColumns = "PayNet Client Name#PayNet Client ID#Bank File ID#Transaction File ID#File Name#Return File ID#File Receipt Name#Status#File Total".split("#");
            for(int i=0; i<arrOpenBankFileColumns.length; i++){
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_ArchievedBankFileReceipts_Grid + "//tr[contains(@class, 'dx-header-row')]/td/div[contains(@class, 'text-content')])["+(i+1)+"]"), "Text", arrArchivedBankFileColumns[i]));
            }
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifyBankFileReceiptsColumnNames()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifyBankFileReceiptsColumnNames()' method. " + e);
            return false;
        }finally{arrOpenBankFileColumns = null; arrArchivedBankFileColumns = null;}
    }




    /********************************************************
     * Method Name      : validateVoidRequestModalFunctionality()
     * Purpose          : User validates the Void Request modal functionalities
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateVoidRequestModalFunctionality(WebDriver oBrowser, String pageName, DataTable dataTable){
        List<Map<String, String>> voidRequestDetails = null;
        String timeStamp = null;
        Map<String, String> voidRequest = null;
        String paymentID = null;
        JSONArray dbResponse = null;
        String strURL = null;
        String paymentAmount = null;
        String postedAmount = null;
        String discountAmount = null;
        double remainingAmount = 0.0;
        try{
            timeStamp = appInd.getDateTime("Shhmmss");
            voidRequestDetails = dataTable.asMaps(String.class, String.class);
            voidRequest = new HashMap<>();
            voidRequest.put("paymentID", voidRequestDetails.get(0).get("paymentID"));
            voidRequest.put("AmountToVoid", voidRequestDetails.get(0).get("AmountToVoid"));
            voidRequest.put("ActionAfterVoid", voidRequestDetails.get(0).get("ActionAfterVoid"));
            voidRequest.put("LinkRevoked", voidRequestDetails.get(0).get("LinkRevoked"));
            voidRequest.put("Notes", voidRequestDetails.get(0).get("Notes") + timeStamp);

            if(pageName.equalsIgnoreCase("Managed Payments")){
                Assert.assertTrue(appDep.selectManagePaymentModules(oBrowser, "Payment Search"));
                paymentID = searchAndOpenThePaymentIDUsingAPIDataprovider(oBrowser, "PaymentID_VoidRequest", new Object[] {}, "PaymentID");

                paymentAmount = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_PaymentDetails_Grid + "//th[text()='Payment amount']/following-sibling::td[1]"), "Text").replace("$", "");
                postedAmount = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_PaymentDetails_Grid + "//th[text()='Amount posted']/following-sibling::td[1]"), "Text").replace("$", "");
                discountAmount = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_PaymentDetails_Grid + "//th[text()='Discount amount']/following-sibling::td[1]"), "Text").replace("$", "");

                if(paymentAmount.isEmpty()) paymentAmount = "0.0";
                if(postedAmount.isEmpty()) postedAmount = "0.0";
                if(discountAmount.isEmpty()) discountAmount = "0.0";

                if (paymentAmount.contains(",")) {
                    paymentAmount = paymentAmount.replace(",","");
                }
                if (postedAmount.contains(",")) {
                    postedAmount = postedAmount.replace(",","");
                }
                if (discountAmount.contains(",")) {
                    discountAmount = discountAmount.replace(",","");
                }
                remainingAmount = (Double.parseDouble(paymentAmount) - Double.parseDouble(postedAmount) - Double.parseDouble(discountAmount));
            }else if(pageName.equalsIgnoreCase("Assisted Payment Services")){
                dbResponse = apiDataProvider.getAPIDataProviderResponse("AssistedPaymentServices_PaymentAndBankInfo", new Object[] {});
                String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
                paymentID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("cases_id").toString();
                strURL = appInd.getPropertyValueByKeyName("qaURL")+"/cases/" + paymentID;
                oBrowser.navigate().to(strURL);
                reports.writeResult(oBrowser, "Screenshot", "The constructed URL: " + strURL);
                appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentHelp_Label, "Text", "Payment Help", waitTimeOut);
                reports.writeResult(oBrowser, "Screenshot", "The page has opened with the case id '"+paymentID+"'");

                appInd.scrollToElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentInfo_Label);
                paymentAmount = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentInformation_Grid + "//td[text()='Payment Amount']/following-sibling::td"), "Text").replace("$", "");
                postedAmount = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentInformation_Grid + "//td[text()='Amount Posted']/following-sibling::td"), "Text").replace("$", "");
                discountAmount = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentInformation_Grid + "//td[text()='Adjustment Amount']/following-sibling::td"), "Text").replace("$", "");
                if(paymentAmount.isEmpty()) paymentAmount = "0.0";
                if(postedAmount.isEmpty()) postedAmount = "0.0";
                if(discountAmount.isEmpty()) discountAmount = "0.0";

                if (paymentAmount.contains(",")) {
                    paymentAmount = paymentAmount.replace(",","");
                }
                if (postedAmount.contains(",")) {
                    postedAmount = postedAmount.replace(",","");
                }
                if (discountAmount.contains(",")) {
                    discountAmount = discountAmount.replace(",","");
                }

                remainingAmount = (Double.parseDouble(paymentAmount) - Double.parseDouble(postedAmount) - Double.parseDouble(discountAmount));
            }else{
                reports.writeResult(oBrowser, "Fail", "Invalid page name '"+pageName+"' was mentioned for 'Void Request' button UI validations");
                Assert.fail("Invalid page name '"+pageName+"' was mentioned for 'Void Request' button UI validations");
            }


            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidRequest_Btn));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidRequest_Btn));

            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidRequest_Dialog + "//input[@value='Submit']"), "Clickable", "", waitTimeOut);

            boolean blnExist = appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidRequest_Dialog + "//input[@value='Submit']"));
            if(blnExist){
                Assert.assertTrue(appDep.validate_VoidRequest_Dialog_FunctionalityAndElements(oBrowser, String.valueOf("$"+remainingAmount), voidRequest));

                if(pageName.equalsIgnoreCase("Managed Payments")){
                    //Verify the entry in the Activities tab of "Payment Details" Page for Managed Payments
                    oBrowser.navigate().back();
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Link, "Clickable", "", waitTimeOut);
                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachments_Link));
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "InVisibility", "", minTimeOut);
                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Link));
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "InVisibility", "", minTimeOut);
                    Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//input)[5]"), voidRequest.get("Notes")));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'dx-row-lines dx-column-lines')]/td[1]"), "Text", "User Action - Void Requested"));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'dx-row-lines dx-column-lines')]/td[2]"), "Text", appInd.getPropertyValueByKeyName("qaUserName")));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'dx-row-lines dx-column-lines')]/td[3]"), "Text", appInd.formatDate(appInd.getDateTime("MM/dd/yyyy"), "MM/dd/yyyy", "MM/dd/yyyy")));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'dx-row-lines dx-column-lines')]/td[4]"), "Text", voidRequest.get("Notes")));
                }else{
                    //Verify the entry in the Activities tab of "Cases>>Show" Page for Assisted Payment Services
                    oBrowser.navigate().back();
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Link, "Clickable", "", waitTimeOut);
                    appInd.scrollToElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SplitterHorizonal_Line);
                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachments_Link));
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "InVisibility", "", minTimeOut);
                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Link));
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "InVisibility", "", minTimeOut);
                    Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//input)[6]"), voidRequest.get("Notes")));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'dx-row-lines dx-column-lines')]/td[3]"), "Text", "Open: Void Requested"));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'dx-row-lines dx-column-lines')]/td[4]"), "Text", "Void Requested"));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'dx-row-lines dx-column-lines')]/td[5]"), "Text", appInd.getPropertyValueByKeyName("qaUserName")));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'dx-row-lines dx-column-lines')]/td[6]"), "Text", appInd.formatDate(appInd.getDateTime("MM/dd/yyyy"), "MM/dd/yyyy", "MM/dd/yyyy")));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'dx-row-lines dx-column-lines')]/td[7]"), "Text", voidRequest.get("Notes")));
                }

            }else{
                reports.writeResult(oBrowser, "Fail", "The Case Number/PaymentID: '"+paymentID+"' which is already having void request completed for the page '" + pageName + "'");
                Assert.fail("The Case Number/PaymentID: '"+paymentID+"' which is already having void request completed for the page '" + pageName + "'");
            }

            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateVoidRequestModalFunctionality()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateVoidRequestModalFunctionality()' method. " + e);
            return false;
        }finally{voidRequestDetails = null; timeStamp = null; voidRequest = null; paymentID = null; dbResponse = null; strURL = null;}
    }





    /********************************************************
     * Method Name      : searchAndOpenThePaymentIDUsingAPIDataprovider()
     * Purpose          : User perform the actionable validations for Defund button functionalities
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public String searchAndOpenThePaymentIDUsingAPIDataprovider(WebDriver oBrowser, String strAPIKey, Object param[], String strResponseKey){
        JSONArray paymentIDDetails = null;
        String payment_ID = null;
        try{
            paymentIDDetails = apiDataProvider.getAPIDataProviderResponse(strAPIKey, param);
            if(((JSONArray) paymentIDDetails.get(0)).size() == 0){
                return "Data not found for the API '"+strAPIKey+"' with the parameter '"+Arrays.toString(param)+"'";
            }
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) paymentIDDetails.get(0)).size(), 1);
            payment_ID = ((LinkedHashMap) ((JSONArray) paymentIDDetails.get(0)).get(Integer.parseInt(randomNum)-1)).get(strResponseKey).toString();

            appInd.clearAndSetObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EnterPaymentID_Edit, payment_ID);
            appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_SubmitBtn);
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            boolean blnRes = appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Link, "Clickable", "", waitTimeOut);

            if(blnRes) {
                reports.writeResult(oBrowser, "Screenshot", "The payment id: '" + payment_ID + "' was opened successful using the API Dataprovider: '" + strAPIKey + "'");
                return payment_ID;
            }else {
                reports.writeResult(oBrowser, "Screenshot", "Failed to search the payment id: '" + payment_ID + "' using the API Dataprovider: '" + strAPIKey + "'");
                return null;
            }
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'searchAndOpenThePaymentIDUsingAPIDataprovider()' method. " + e);
            return null;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'searchAndOpenThePaymentIDUsingAPIDataprovider()' method. " + e);
            return null;
        }finally {paymentIDDetails = null; payment_ID = null;}
    }




    /********************************************************
     * Method Name      : validateShowORHideDefundButtonFunctionalityBasedOnPaymentTypeAndStatus()
     * Purpose          : User validates Show/Hide Defund button functionalities based on Payment type and status
     * Author           : Gudi
     * Parameters       : oBrowser, defundBtnStatus, queryKeys
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateShowORHideDefundButtonFunctionalityBasedOnPaymentTypeAndStatus(WebDriver oBrowser, String defundBtnStatus, String queryKeys){
        String paymentID = null;
        String paymentType = null;
        String paymentProcessor = null;
        String paymentStatus = null;
        JSONArray paymentIDDetails = null;
        String arrQueryKeys[];
        try{
            arrQueryKeys = queryKeys.split("#");
            for(int h=0; h<arrQueryKeys.length; h++){
                paymentIDDetails = apiDataProvider.getAPIDataProviderResponse(arrQueryKeys[h], new Object[] {});

                for(int i=0; i<((JSONArray) paymentIDDetails.get(0)).size(); i++) {
                    paymentID = ((LinkedHashMap) ((JSONArray) paymentIDDetails.get(0)).get(i)).get("PaymentID").toString();
                    Assert.assertTrue(appDep.searchPaymentID(oBrowser, paymentID));
                    paymentType = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_MngdPayments_PaymentDetails_Grid + "//th[text()='Payment type']/following-sibling::td[1]"), "Text");
                    paymentProcessor = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_MngdPayments_PaymentDetails_Grid + "//th[text()='Payment processor']/following-sibling::td[1]"), "Text");
                    paymentStatus = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_MngdPayments_PaymentDetails_Grid + "//th[text()='Status']/following-sibling::td[1]"), "Text");

                    if(defundBtnStatus.equalsIgnoreCase("Show")){
                        switch (paymentType.toLowerCase()){
                            case "virtual card": case "vca":
                                reports.writeResult(oBrowser, "Info", "The Payment Type is '"+paymentType+"'");
                                switch(paymentProcessor.toLowerCase()){
                                    case "dxc":
                                        switch(paymentStatus.toLowerCase()){
                                            case "confirmed": case "partially processed": case "partially voided": case "partially expired": case "voided": case "expired":
                                                boolean blnExist = false;
                                                blnExist = appInd.verifyOptionalElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Defund_Btn);
                                                if(blnExist){
                                                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Defund_Btn), "The 'Defund' button was NOT present when the Payment Type='"+paymentType+"', Payment Processor='"+paymentProcessor+"' & Payment Status='"+paymentStatus+"'");
                                                }else{
                                                    reports.writeResult(oBrowser, "Fail", "Failed to get the 'Defund' button for the Payment Type='"+paymentType+"', Payment Processor='"+paymentProcessor+"' & Payment Status='"+paymentStatus+"'");
                                                    Assert.fail("Failed to get the 'Defund' button for the Payment Type='"+paymentType+"', Payment Processor='"+paymentProcessor+"' & Payment Status='"+paymentStatus+"'");
                                                }
                                                break;
                                        }
                                        break;
                                    case "tsys":
                                        switch(paymentStatus.toLowerCase()){
                                            case "authorized": case "partially processed": case "partially expired": case "expired":
                                                boolean blnExist = false;
                                                blnExist = appInd.verifyOptionalElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Defund_Btn);
                                                if(blnExist){
                                                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Defund_Btn), "The 'Defund' button was NOT present when the Payment Type='"+paymentType+"', Payment Processor='"+paymentProcessor+"' & Payment Status='"+paymentStatus+"'");
                                                }else{
                                                    reports.writeResult(oBrowser, "Fail", "Failed to get the 'Defund' button for the Payment Type='"+paymentType+"', Payment Processor='"+paymentProcessor+"' & Payment Status='"+paymentStatus+"'");
                                                    Assert.fail("Failed to get the 'Defund' button for the Payment Type='"+paymentType+"', Payment Processor='"+paymentProcessor+"' & Payment Status='"+paymentStatus+"'");
                                                }
                                                break;
                                        }
                                        break;
                                }
                                break;
                            default:
                                reports.writeResult(oBrowser, "Info", "The 'Payment Type' is neither 'Virtual Card' OR 'VCA'. Hence can't validate the 'Defund' button functionality");
                        }
                    }else{
                        switch (paymentType.toLowerCase()){
                            case "virtual card": case "vca":
                                reports.writeResult(oBrowser, "Info", "The Payment Type is '"+paymentType+"'");
                                switch(paymentProcessor.toLowerCase()){
                                    case "dxc":
                                        switch(paymentStatus.toLowerCase()){
                                            case "authorized": case "virtual card processed": case "cancelled":
                                                boolean blnExist = false;
                                                blnExist = appInd.verifyOptionalElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Defund_Btn);
                                                if(!blnExist){
                                                    Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Defund_Btn), "The 'Defund' button was still present when the Payment Type='"+paymentType+"', Payment Processor='"+paymentProcessor+"' & Payment Status='"+paymentStatus+"'");
                                                }else{
                                                    reports.writeResult(oBrowser, "Fail", "The 'Defund' button was still exist for the Payment Type='"+paymentType+"', Payment Processor='"+paymentProcessor+"' & Payment Status='"+paymentStatus+"'");
                                                    Assert.fail("The 'Defund' button was still exist for the Payment Type='"+paymentType+"', Payment Processor='"+paymentProcessor+"' & Payment Status='"+paymentStatus+"'");
                                                }
                                                break;
                                        }
                                        break;
                                    case "tsys":
                                        switch(paymentStatus.toLowerCase()){
                                            case "exception": case "virtual card processed": case "pending": case "submitted": case "confirmed": case "cancelled": case "partially voided": case "voided":
                                                boolean blnExist = false;
                                                blnExist = appInd.verifyOptionalElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Defund_Btn);
                                                if(!blnExist){
                                                    Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Defund_Btn), "The 'Defund' button was still present when the Payment Type='"+paymentType+"', Payment Processor='"+paymentProcessor+"' & Payment Status='"+paymentStatus+"'");
                                                }else{
                                                    reports.writeResult(oBrowser, "Fail", "The 'Defund' button was still exist for the Payment Type='"+paymentType+"', Payment Processor='"+paymentProcessor+"' & Payment Status='"+paymentStatus+"'");
                                                    Assert.fail("The 'Defund' button was still exist for the Payment Type='"+paymentType+"', Payment Processor='"+paymentProcessor+"' & Payment Status='"+paymentStatus+"'");
                                                }
                                                break;
                                        }
                                        break;
                                }
                                break;
                            default:
                                reports.writeResult(oBrowser, "Info", "The 'Payment Type' is neither 'Virtual Card' OR 'VCA'. Hence can't validate the 'Defund' button functionality");
                        }
                    }
                    oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
                }
            }
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateShowORHideDefundButtonFunctionalityBasedOnPaymentTypeAndStatus()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateShowORHideDefundButtonFunctionalityBasedOnPaymentTypeAndStatus()' method. " + e);
            return false;
        }finally {paymentID = null; paymentType = null; paymentProcessor = null; paymentStatus = null; paymentIDDetails = null;}
    }





    /********************************************************
     * Method Name      : performActionableValidationsForDefundButton()
     * Purpose          : User perform the actionable validations for Defund button functionalities
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean performActionableValidationsForDefundButton(WebDriver oBrowser){
        String paymentID = null;
        try{
            Assert.assertTrue(appDep.selectManagePaymentModules(oBrowser, "Payment Search"));
            paymentID = searchAndOpenThePaymentIDUsingAPIDataprovider(oBrowser, "ManagedPayment_Defund_Visible", new Object[] {}, "PaymentID");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Defund_Btn), "The 'Defund' button doesnot exist");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Defund_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_DefundDialog_Defund_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Defund_Dialog + "//h4"),"Text", "Defund"));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Defund_AmountRemaining_Edit));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Defund_Notes_Textarea));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_DefundDialog_Defund_Btn));

            //Amount Remaining field is not be editable
            Assert.assertTrue(oBrowser.findElement(PayCRM_Modules_ManagedPaymentsUIPage.obj_Defund_AmountRemaining_Edit).getAttribute("readonly").equalsIgnoreCase("true"), "The 'Amount Remaining' textfield should be disabled");

            //Notes field is editable
            Assert.assertTrue(oBrowser.findElement(PayCRM_Modules_ManagedPaymentsUIPage.obj_Defund_Notes_Textarea).isEnabled(), "The 'Amount Remaining' textfield should be enabled");
            String notes = "Defund Notes - " + appInd.getDateTime("Shhmmss");
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Defund_Notes_Textarea, notes));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_DefundDialog_Defund_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidRequestConfirmation_Msg, "Text", "Defund Request Submitted Successfully", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidRequestConfirmation_Msg, "Text", "Defund Request Submitted Successfully"));

            //Defund button should dis-appear
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Defund_Btn), "The 'Defund' button was not removed from the UI");

            //Verify the defund in the 'Activities' tab
            appInd.moveToElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Link);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Link));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//a[@title='Show']"), "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//input)[5]"), notes));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td[1]"), "Text", "User Action - Defund Initiated"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td[2]"), "Text", appInd.getPropertyValueByKeyName("qaUserName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td[4]"), "Text", notes));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'performActionableValidationsForDefundButton()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'performActionableValidationsForDefundButton()' method. " + e);
            return false;
        }finally{paymentID = null;}
    }




    /********************************************************
     * Method Name      : transactionHistoryTabValidations()
     * Purpose          : User validates the Transaction History functionality for payment status
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean transactionHistoryTabValidations(WebDriver oBrowser){
        String arrPaymentStatus[];
        String paymentID = null;
        String paymentProcessor = null;
        String paymentStatus = null;
        String arrCoumnNames[];
        String paymentType = null;
        try{
            Assert.assertTrue(appDep.selectManagePaymentModules(oBrowser, "Payment Search"));
            arrPaymentStatus = "5#19#17#18#15#14".split("#");
            for(int i=0; i<arrPaymentStatus.length; i++){
                paymentID = searchAndOpenThePaymentIDUsingAPIDataprovider(oBrowser, "ManagedPayments_TransactionHistory", new Object[] {"typeID", arrPaymentStatus[i]}, "PaymentID");

                if(paymentID.equalsIgnoreCase("Data not found for the API")) {
                    reports.writeResult(oBrowser, "Warning", "The data was not found in the DB for the dataProvider API 'ManagedPayments_TransactionHistory' & typeID '" + arrPaymentStatus[i] + "'");
                }else{
                    //Verify for below Payment Status Transaction History tab is visible-
                    //Confirmed, Authorized, Partially Processed, Virtual Card Processed, Cancelled, Partially Voided, Partially Expired, Voided, Expired

                    paymentProcessor = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_MngdPayments_PaymentDetails_Grid + "//th[text()='Payment processor']/following-sibling::td[1]"), "Text");
                    paymentStatus = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_MngdPayments_PaymentDetails_Grid + "//th[text()='Status']/following-sibling::td[1]"), "Text");
                    paymentType = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_MngdPayments_PaymentDetails_Grid + "//th[text()='Payment type']/following-sibling::td[1]"), "Text");

                    if(paymentType.equalsIgnoreCase("VCA")){
                        switch(paymentProcessor.toLowerCase()){
                            case "dxc":
                                switch(paymentStatus.toLowerCase()){
                                    case "confirmed": case "authorized": case "partially processed": case "virtual card processed": case "cancelled": case "partially voided": case "partially expired": case "voided": case "expired":
                                        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_TransactionHistory_Link), "Failed to display the 'Transaction History' tab for the Payment status: '"+paymentStatus+"'");
                                }
                        }
                        oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
                    }
                }
            }

            paymentID = searchAndOpenThePaymentIDUsingAPIDataprovider(oBrowser, "ManagedPayments_TransactionHistory", new Object[] {"typeID", "5"}, "PaymentID");
            Assert.assertTrue(!paymentID.equalsIgnoreCase("Data not found for the API"), "The Payment ID was not found in the DB for the dataProvider API 'ManagedPayments_TransactionHistory' & typeID '5'");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_TransactionHistory_Link), "The 'Transaction History' tab doesnot exist");
            paymentProcessor = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_MngdPayments_PaymentDetails_Grid + "//th[text()='Payment processor']/following-sibling::td[1]"), "Text");
            paymentType = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_MngdPayments_PaymentDetails_Grid + "//th[text()='Payment type']/following-sibling::td[1]"), "Text");
            if(paymentProcessor.equalsIgnoreCase("DXC") && paymentType.equalsIgnoreCase("VCA")){
                appInd.scrollToThePage(oBrowser, "Bottom");
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_TransactionHistory_Link));
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                arrCoumnNames = "Company Number#Authorization Date#Posted Date#Transaction Status#Token Number#Central Bill#Card Balance#Expiration Date#MCC#MCC Description#MCC Classification#Merchant Name#Merchant Street#Merchant City#Merchant State#Merchant Postal Code#Authorization Amount#Posted Amount#Authorization Unique Id#Approval Code#Reverse Flag#Ea Credit Limit#Ea Credit Exp Date#Ea Invoice#Interchange Fee Amt#Authorization Response Set Pointer Desc".split("#");
                for(int i=0; i<arrCoumnNames.length; i++){
                    if(i > 10)
                        appInd.moveToElement(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_TransactionHistory_Grid + "//tr[1]/td/div[contains(@class, 'text-content')])["+(i+1)+"]"));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_TransactionHistory_Grid + "//tr[1]/td/div[contains(@class, 'text-content')])["+(i+1)+"]"), "Text", arrCoumnNames[i]));
                }
            }
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'transactionHistoryTabValidations()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'transactionHistoryTabValidations()' method. " + e);
            return false;
        }finally {arrPaymentStatus = null; paymentID = null; paymentProcessor = null; paymentStatus = null; arrCoumnNames = null;}
    }




    /********************************************************
     * Method Name      : userValidatesStopCheckFunctionality()
     * Purpose          : User validates the Stop Check functionality for payment status
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean userValidatesStopCheckFunctionality(WebDriver oBrowser){
        String paymentID = null;
        String paymentType = null;
        String paymentStatus = null;
        String checkNumber = null;
        try{
            Assert.assertTrue(appDep.selectManagePaymentModules(oBrowser, "Payment Search"));
            paymentID = searchAndOpenThePaymentIDUsingAPIDataprovider(oBrowser, "ManagedPayments_StopCheck", new Object[] {}, "PaymentID");
            Assert.assertTrue(!paymentID.equalsIgnoreCase("Data not found for the API"), "The Payment ID was not found in the DB for the dataProvider API 'ManagedPayments_StopCheck'");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_StopCheck_Btn), "The 'Stop Check' button doesnot exist");

            paymentType = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_MngdPayments_PaymentDetails_Grid + "//th[text()='Payment type']/following-sibling::td[1]"), "Text");
            paymentStatus = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_MngdPayments_PaymentDetails_Grid + "//th[text()='Status']/following-sibling::td[1]"), "Text");

            if(paymentType.equalsIgnoreCase("CHK") && paymentStatus.equalsIgnoreCase("Shipped")){
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_StopCheck_Btn), "The 'Stop Check' button doesnot exist");
                reports.writeResult(oBrowser, "Screenshot", "The 'Stop Check' button exist successful when the Payment Type = '"+paymentType+"' & Payment Status = '"+paymentStatus+"'");
            }else{
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_StopCheck_Btn), "The 'Stop Check' button still exist");
                reports.writeResult(oBrowser, "Screenshot", "The 'Stop Check' button should not be present when the Payment Type = '"+paymentType+"' & Payment Status = '"+paymentStatus+"'");
            }

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_StopCheck_Btn));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_StopCheck_Dialog + "//button[@id='btn_submit_stop_check']"), "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_StopCheck_Dialog + "//h4"), "Text", "Stop Check"));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_CheckNumber_Edit), "'Check Number' edit field doesnot exist");
            checkNumber = appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_CheckNumber_Edit, "Value");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_WhyStoppingThisCheck_Textarea), "'Why are you stopping this check?' textarea field doesnot exist");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_StopCheck_Dialog + "//button[@id='btn_submit_stop_check']")), "'Submit' button field doesnot exist");
            String notes = "The reason to stop check - " + appInd.getDateTime("Shhmmss");
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_WhyStoppingThisCheck_Textarea, notes));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_StopCheck_Dialog + "//button[@id='btn_submit_stop_check']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidRequestConfirmation_Msg, "Clickable", "", waitTimeOut);
            //Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidRequestConfirmation_Msg, "Text", "XXXXXXXXXXXXXXXXXXXXXXXXXX"));
            appInd.moveToElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Link);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//input)[5]"), notes));
            appDep.threadSleep(2000);

            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')][1]/td[1]"), "Text", "User Action - Stop Check"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')][1]/td[2]"), "Text", appInd.getPropertyValueByKeyName("qaUserName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')][1]/td[4]"), "Text", "Stop Check initialized by: "+appInd.getPropertyValueByKeyName("qaUserName")+" User notes: "+notes));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'userValidatesStopCheckFunctionality()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'userValidatesStopCheckFunctionality()' method. " + e);
            return false;
        }finally{paymentID = null; paymentType = null; paymentStatus = null; checkNumber = null;}
    }




    /********************************************************
     * Method Name      : validatePaymentSearchWithValidAndInvalidPaymentID()
     * Purpose          : User validates the Stop Check functionality for payment status
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validatePaymentSearchWithValidAndInvalidPaymentID(WebDriver oBrowser){
        String paymentID = null;
        JSONArray paymentIDDetails = null;
        try{
            Assert.assertTrue(appDep.selectManagePaymentModules(oBrowser, "Payment Search"));
            paymentIDDetails = apiDataProvider.getAPIDataProviderResponse("PaymentID_VoidRequest", new Object[] {});
            if(((JSONArray) paymentIDDetails.get(0)).size() == 0){
                reports.writeResult(oBrowser, "Fail", "Data not found for the API 'PaymentID_VoidRequest' with the parameter '0'");
                Assert.fail("Data not found for the API 'PaymentID_VoidRequest' with the parameter '0'");
            }
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) paymentIDDetails.get(0)).size(), 1);
            paymentID = ((LinkedHashMap) ((JSONArray) paymentIDDetails.get(0)).get(Integer.parseInt(randomNum)-1)).get("PaymentID").toString();


            //Enter Payment search id in search box and click on red clear button
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EnterPaymentID_Edit, paymentID));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EnterPaymentID_Edit, "Value", paymentID));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_ClearBtn));
            appDep.threadSleep(1000);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EnterPaymentID_Edit, "Placeholder", "Enter Payment ID (only numbers and dashes allowed) ... "));

            //Enter Invalid Payment search id in search box and click on green Search button
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EnterPaymentID_Edit, "123456789-10x"));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_SubmitBtn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchPaymentID_Error_Msg, "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchPaymentID_Error_Msg, "Text").contains("This payment ID does not exist. Please try searching for another payment"));


            //Enter valid Payment search id in search box and click on green Search button
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EnterPaymentID_Edit, paymentID));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_SubmitBtn));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Link, "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Transfer_Header, "Text", "» Managed Payment » Payment Detail"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_PaymentDetails_Grid + "//th[text()='Payment ID']/following-sibling::td[1]"), "Text", paymentID));
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validatePaymentSearchWithValidAndInvalidPaymentID()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validatePaymentSearchWithValidAndInvalidPaymentID()' method. " + e);
            return false;
        }finally{paymentID = null; paymentIDDetails = null;}
    }




    /********************************************************
     * Method Name      : validatePaymentStatusOptionsForPaymentTypeCheck()
     * Purpose          : User validates the Differnet Payment Status Options for Payment Type - CHECK
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validatePaymentStatusOptionsForPaymentTypeCheck(WebDriver oBrowser){
        JSONArray paymentIDDetails = null;
        String paymentID = null;
        String paymentType = null;
        String paymentStatus = null;
        String statusValidated = "";
        try{
            Assert.assertTrue(appDep.selectManagePaymentModules(oBrowser, "Payment Search"));
            paymentIDDetails = apiDataProvider.getAPIDataProviderResponse("PaymentStatus_PaymentType_CHECK", new Object[] {});

            for(int i=0; i<((JSONArray) paymentIDDetails.get(0)).size(); i++){
                paymentID = ((LinkedHashMap) ((JSONArray) paymentIDDetails.get(0)).get(i)).get("PaymentID").toString();
                paymentType = ((LinkedHashMap) ((JSONArray) paymentIDDetails.get(0)).get(i)).get("PaymentType").toString();
                paymentStatus = ((LinkedHashMap) ((JSONArray) paymentIDDetails.get(0)).get(i)).get("PaymentStatus").toString();

                if(!statusValidated.contains(paymentStatus)){
                    if(paymentType.equalsIgnoreCase("Check")) paymentType = "CHK";
                    switch (paymentType.toLowerCase()){
                        case "chk":
                            reports.writeResult(oBrowser, "Info", "The Payment Type is '"+paymentType+"'");
                            switch(paymentStatus.toLowerCase()){
                                case "processed":
                                    Assert.assertTrue(appDep.searchPaymentID(oBrowser, paymentID));
                                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Btn));
                                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Btn));
                                    appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Dialog + "//button[@id='btn_submit_payment_status']"), "Clickable", "", waitTimeOut);
                                    reports.writeResult(oBrowser, "Screenshot", "The 'Edit Payment Status' button was displyed when Payment Type is '"+paymentType+"' & Payment Status is '"+paymentStatus+"'");

                                    //Click on Update Payment Status & validate the fields
                                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Dialog + "//h4"), "Text", "Edit Payment Status"));
                                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Dialog + "//label[contains(text(),'ID:')]")));
                                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Dialog + "//label[contains(text(),'Amount:')]")));
                                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_CurrentPaymentStatus_Edit));
                                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_NewPaymentStatus_List));
                                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Notes_Textarea));
                                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Dialog + "//button[@id='btn_submit_payment_status']")));

                                    Assert.assertTrue(appDep.validateTheListValues(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_NewPaymentStatus_List, "--Select--#Voided#Expired#Cleared#Shipped"));
                                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Dialog + "//button[@class='close']")));
                                    appDep.threadSleep(2000);
                                    oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
                                    break;
                                case "shipped":
                                    Assert.assertTrue(appDep.searchPaymentID(oBrowser, paymentID));
                                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Btn));
                                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Btn));
                                    appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Dialog + "//button[@id='btn_submit_payment_status']"), "Clickable", "", waitTimeOut);
                                    reports.writeResult(oBrowser, "Screenshot", "The 'Edit Payment Status' button was displyed when Payment Type is '"+paymentType+"' & Payment Status is '"+paymentStatus+"'");
                                    Assert.assertTrue(appDep.validateTheListValues(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_NewPaymentStatus_List, "--Select--#Voided#Expired#Cleared"));
                                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Dialog + "//button[@class='close']")));
                                    appDep.threadSleep(2000);
                                    oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
                                    break;
                            }
                            break;
                        default:
                            reports.writeResult(oBrowser, "Info", "Invalid 'Payment Type' '"+paymentType+"' was displyed. The required payment type was 'CHECK'");
                            Assert.fail("Invalid 'Payment Type' '"+paymentType+"' was displyed. The required payment type was 'CHECK'");
                    }
                    statusValidated+=paymentStatus+"#";
                }
            }
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validatePaymentStatusOptionsForPaymentTypeCheck()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validatePaymentStatusOptionsForPaymentTypeCheck()' method. " + e);
            return false;
        }finally{paymentIDDetails = null; paymentID = null; paymentType = null; paymentStatus = null; statusValidated = "";}
    }




    /********************************************************
     * Method Name      : validatePaymentStatusOptionsForPaymentTypeACH_ACHPlus()
     * Purpose          : User validates the Differnet Payment Status Options for Payment Type - ACH or ACH Plus
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validatePaymentStatusOptionsForPaymentTypeACH_ACHPlus(WebDriver oBrowser){
        JSONArray paymentIDDetails = null;
        String paymentID = null;
        String paymentType = null;
        String paymentStatus = null;
        String statusValidated = "";
        try{
            boolean blnRes = appInd.verifyOptionalElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EnterPaymentID_Edit);
            if(!blnRes)
                Assert.assertTrue(appDep.selectManagePaymentModules(oBrowser, "Payment Search"));

            paymentIDDetails = apiDataProvider.getAPIDataProviderResponse("PaymentStatus_PaymentType_ACH_ACHPlus", new Object[] {});

            for(int i=0; i<((JSONArray) paymentIDDetails.get(0)).size(); i++){
                paymentID = ((LinkedHashMap) ((JSONArray) paymentIDDetails.get(0)).get(i)).get("PaymentID").toString();
                paymentType = ((LinkedHashMap) ((JSONArray) paymentIDDetails.get(0)).get(i)).get("PaymentType").toString();
                paymentStatus = ((LinkedHashMap) ((JSONArray) paymentIDDetails.get(0)).get(i)).get("PaymentStatus").toString();

                if(!statusValidated.contains(paymentStatus)){
                    if(paymentType.equalsIgnoreCase("ACH Plus")) paymentType = "ACP";
                    switch (paymentType.toLowerCase()){
                        case "ach": case "acp":
                            reports.writeResult(oBrowser, "Info", "The Payment Type is '"+paymentType+"'");
                            switch(paymentStatus.toLowerCase()){
                                case "processed": case "confirmed":
                                    Assert.assertTrue(appDep.searchPaymentID(oBrowser, paymentID));
                                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Btn));
                                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Btn));
                                    appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Dialog + "//button[@id='btn_submit_payment_status']"), "Clickable", "", waitTimeOut);
                                    reports.writeResult(oBrowser, "Screenshot", "The 'Edit Payment Status' button was displyed when Payment Type is '"+paymentType+"' & Payment Status is '"+paymentStatus+"'");
                                    Assert.assertTrue(appDep.validateTheListValues(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_NewPaymentStatus_List, "--Select--#Returned"));
                                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Dialog + "//button[@class='close']")));
                                    appDep.threadSleep(2000);
                                    oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
                                    break;
                                case "returned":
                                    Assert.assertTrue(appDep.searchPaymentID(oBrowser, paymentID));
                                    Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Btn));
                                    reports.writeResult(oBrowser, "Screenshot", "The 'Edit Payment Status' button was not displyed when Payment Type is '"+paymentType+"' & Payment Status is '"+paymentStatus+"'");
                                    oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
                                    break;
                            }
                            break;
                        default:
                            reports.writeResult(oBrowser, "Info", "Invalid 'Payment Type' '"+paymentType+"' was displyed. The required payment type was 'ACH or ACH Plus'");
                            Assert.fail("Invalid 'Payment Type' '"+paymentType+"' was displyed. The required payment type was 'ACH or ACH Plus'");
                    }
                    statusValidated+=paymentStatus+"#";
                }
            }
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validatePaymentStatusOptionsForPaymentTypeACH_ACHPlus()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validatePaymentStatusOptionsForPaymentTypeACH_ACHPlus()' method. " + e);
            return false;
        }finally{paymentIDDetails = null; paymentID = null; paymentType = null; paymentStatus = null; statusValidated = null;}
    }




    /********************************************************
     * Method Name      : validatePaymentStatusOptionsForPaymentTypeVirtualCard()
     * Purpose          : User validates the Differnet Payment Status Options for Payment Type - Virtual Card
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validatePaymentStatusOptionsForPaymentTypeVirtualCard(WebDriver oBrowser){
        JSONArray paymentIDDetails = null;
        String paymentID = null;
        String paymentType = null;
        String paymentStatus = null;
        String statusValidated = "";
        String arrOptionValues[];
        try{
            boolean blnRes = appInd.verifyOptionalElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EnterPaymentID_Edit);
            if(!blnRes)
                Assert.assertTrue(appDep.selectManagePaymentModules(oBrowser, "Payment Search"));

            paymentIDDetails = apiDataProvider.getAPIDataProviderResponse("PaymentStatus_PaymentType_VirtualCard", new Object[] {});

            for(int i=0; i<((JSONArray) paymentIDDetails.get(0)).size(); i++){
                paymentID = ((LinkedHashMap) ((JSONArray) paymentIDDetails.get(0)).get(i)).get("PaymentID").toString();
                paymentType = ((LinkedHashMap) ((JSONArray) paymentIDDetails.get(0)).get(i)).get("PaymentType").toString();
                paymentStatus = ((LinkedHashMap) ((JSONArray) paymentIDDetails.get(0)).get(i)).get("PaymentStatus").toString();

                if(!statusValidated.contains(paymentStatus)){
                    if(paymentType.equalsIgnoreCase("Virtual Card")) paymentType = "VCA";

                    switch (paymentType.toLowerCase()){
                        case "vca":
                            reports.writeResult(oBrowser, "Info", "The Payment Type is '"+paymentType+"'");
                            switch(paymentStatus.toLowerCase()){
                                case "confirmed":
                                    Assert.assertTrue(appDep.searchPaymentID(oBrowser, paymentID));
                                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Btn));
                                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Btn));
                                    appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Dialog + "//button[@id='btn_submit_payment_status']"), "Clickable", "", waitTimeOut);
                                    reports.writeResult(oBrowser, "Screenshot", "The 'Edit Payment Status' button was displyed when Payment Type is '"+paymentType+"' & Payment Status is '"+paymentStatus+"'");
                                    Assert.assertTrue(appDep.validateTheListValues(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_NewPaymentStatus_List, "--Select--#Virtual Card Processed#Authorized#Voided#Expired#Partially Voided#Partially Expired#Partially Processed"));

                                    arrOptionValues = "Virtual Card Processed#Authorized#Voided#Expired#Partially Voided#Partially Expired#Partially Processed".split("#");
                                    for(int j=0; j<arrOptionValues.length; j++){
                                        Assert.assertTrue(validateElementsBasedOnTheOptionSelectedFromTheNewPaymentStatusDropdown(oBrowser, arrOptionValues[j]));
                                    }
                                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Dialog + "//button[@class='close']")));
                                    appDep.threadSleep(2000);
                                    oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
                                    break;
                                case "authorized":
                                    Assert.assertTrue(appDep.searchPaymentID(oBrowser, paymentID));
                                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Btn));
                                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Btn));
                                    appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Dialog + "//button[@id='btn_submit_payment_status']"), "Clickable", "", waitTimeOut);
                                    reports.writeResult(oBrowser, "Screenshot", "The 'Edit Payment Status' button was displyed when Payment Type is '"+paymentType+"' & Payment Status is '"+paymentStatus+"'");
                                    Assert.assertTrue(appDep.validateTheListValues(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_NewPaymentStatus_List, "--Select--#Virtual Card Processed#Voided#Expired#Partially Voided#Partially Expired#Partially Processed"));

                                    arrOptionValues = "Virtual Card Processed#Voided#Expired#Partially Voided#Partially Expired#Partially Processed".split("#");
                                    for(int j=0; j<arrOptionValues.length; j++){
                                        Assert.assertTrue(validateElementsBasedOnTheOptionSelectedFromTheNewPaymentStatusDropdown(oBrowser, arrOptionValues[j]));
                                    }

                                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Dialog + "//button[@class='close']")));
                                    appDep.threadSleep(2000);
                                    oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
                                    break;
                                case "partially processed":
                                    Assert.assertTrue(appDep.searchPaymentID(oBrowser, paymentID));
                                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Btn));
                                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Btn));
                                    appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Dialog + "//button[@id='btn_submit_payment_status']"), "Clickable", "", waitTimeOut);
                                    reports.writeResult(oBrowser, "Screenshot", "The 'Edit Payment Status' button was displyed when Payment Type is '"+paymentType+"' & Payment Status is '"+paymentStatus+"'");
                                    Assert.assertTrue(appDep.validateTheListValues(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_NewPaymentStatus_List, "--Select--#Virtual Card Processed#Partially Voided#Partially Expired#Partially Processed"));

                                    arrOptionValues = "Virtual Card Processed#Partially Voided#Partially Expired#Partially Processed".split("#");
                                    for(int j=0; j<arrOptionValues.length; j++){
                                        Assert.assertTrue(validateElementsBasedOnTheOptionSelectedFromTheNewPaymentStatusDropdown(oBrowser, arrOptionValues[j]));
                                    }

                                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Dialog + "//button[@class='close']")));
                                    appDep.threadSleep(2000);
                                    oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
                                    break;
                            }
                            break;
                        default:
                            reports.writeResult(oBrowser, "Info", "Invalid 'Payment Type' '"+paymentType+"' was displyed. The required payment type was 'Virtual Card'");
                            Assert.fail("Invalid 'Payment Type' '"+paymentType+"' was displyed. The required payment type was 'Virtual Card'");
                    }
                    statusValidated+=paymentStatus+"#";
                }
            }
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validatePaymentStatusOptionsForPaymentTypeVirtualCard()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validatePaymentStatusOptionsForPaymentTypeVirtualCard()' method. " + e);
            return false;
        }finally {paymentIDDetails = null; paymentID = null; paymentType = null; paymentStatus = null; statusValidated = null; arrOptionValues = null;}
    }




    /********************************************************
     * Method Name      : validateElementsBasedOnTheOptionSelectedFromTheNewPaymentStatusDropdown()
     * Purpose          : User validates that based on the Option value selected from the 'New Payment Status *' dropdown the different fields should automatically popup
     * Author           : Gudi
     * Parameters       : oBrowser, optionValue
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateElementsBasedOnTheOptionSelectedFromTheNewPaymentStatusDropdown(WebDriver oBrowser, String optionValue){
        try{
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_NewPaymentStatus_List, optionValue));
            appDep.threadSleep(1000);
            switch(optionValue.toLowerCase()){
                case "virtual card processed": case "prepaid card processed":
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_CardProcessedDate_Edit), "The element was NOT displayed when '"+optionValue+"' option was selected from the 'New Payment Status *' dropdown");
                    break;
                case "authorized": case "voided": case "expired":
                    break;
                case "partially voided": case "partially expired": case "partially processed":
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_OriginalAmountPosted_Edit), "The element was NOT displayed when '"+optionValue+"' option was selected from the 'New Payment Status *' dropdown");
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_RemainingAmount_Edit), "The element was NOT displayed when '"+optionValue+"' option was selected from the 'New Payment Status *' dropdown");
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_NewAmountPosted_Edit), "The element was NOT displayed when '"+optionValue+"' option was selected from the 'New Payment Status *' dropdown");
                    if(!optionValue.equalsIgnoreCase("Partially Processed")){
                        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_OriginalAmountVoided_Edit), "The element was NOT displayed when '"+optionValue+"' option was selected from the 'New Payment Status *' dropdown");
                        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_NewAmountVoided_Edit), "The element was NOT displayed when '"+optionValue+"' option was selected from the 'New Payment Status *' dropdown");
                    }
                    break;
                default:
                    reports.writeResult(oBrowser, "Fail", "Invalid option '"+optionValue+"' was mentioned for the 'New Payment Status *' dropdown");
                    Assert.fail("Invalid option '"+optionValue+"' was mentioned for the 'New Payment Status *' dropdown");
            }
            reports.writeResult(oBrowser, "Screenshot", "When '"+optionValue+"' option was selected from the 'New Payment Status *' dropdown");
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validatePaymentStatusOptionsForPaymentTypeVirtualCard()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validatePaymentStatusOptionsForPaymentTypeVirtualCard()' method. " + e);
            return false;
        }
    }



    /********************************************************
     * Method Name      : validatePaymentStatusOptionsForPaymentTypePrepaidOrDebitCard()
     * Purpose          : User validates the Differnet Payment Status Options for Payment Type - Prepaid / Debit card
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validatePaymentStatusOptionsForPaymentTypePrepaidOrDebitCard(WebDriver oBrowser){
        JSONArray paymentIDDetails = null;
        String paymentID = null;
        String paymentType = null;
        String paymentStatus = null;
        String statusValidated = "";
        String arrOptionValues[];
        try{
            boolean blnRes = appInd.verifyOptionalElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EnterPaymentID_Edit);
            if(!blnRes)
                Assert.assertTrue(appDep.selectManagePaymentModules(oBrowser, "Payment Search"));

            paymentIDDetails = apiDataProvider.getAPIDataProviderResponse("PaymentStatus_PaymentType_Prepaid_DebitCard", new Object[] {});

            for(int i=0; i<((JSONArray) paymentIDDetails.get(0)).size(); i++){
                paymentID = ((LinkedHashMap) ((JSONArray) paymentIDDetails.get(0)).get(i)).get("PaymentID").toString();
                paymentType = ((LinkedHashMap) ((JSONArray) paymentIDDetails.get(0)).get(i)).get("PaymentType").toString();
                paymentStatus = ((LinkedHashMap) ((JSONArray) paymentIDDetails.get(0)).get(i)).get("PaymentStatus").toString();

                if(!statusValidated.contains(paymentStatus)){
                    if(paymentType.equalsIgnoreCase("Prepaid Card")) paymentType = "VPC";

                    switch (paymentType.toLowerCase()){
                        case "vpc":
                            reports.writeResult(oBrowser, "Info", "The Payment Type is '"+paymentType+"'");
                            switch(paymentStatus.toLowerCase()){
                                case "confirmed":
                                    Assert.assertTrue(appDep.searchPaymentID(oBrowser, paymentID));
                                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Btn));
                                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Btn));
                                    appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Dialog + "//button[@id='btn_submit_payment_status']"), "Clickable", "", waitTimeOut);
                                    reports.writeResult(oBrowser, "Screenshot", "The 'Edit Payment Status' button was displyed when Payment Type is '"+paymentType+"' & Payment Status is '"+paymentStatus+"'");
                                    Assert.assertTrue(appDep.validateTheListValues(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_NewPaymentStatus_List, "--Select--#Voided#Expired#Partially Voided#Partially Expired#Partially Processed#Prepaid Card Processed"));

                                    arrOptionValues = "Voided#Expired#Partially Voided#Partially Expired#Partially Processed#Prepaid Card Processed".split("#");
                                    for(int j=0; j<arrOptionValues.length; j++){
                                        Assert.assertTrue(validateElementsBasedOnTheOptionSelectedFromTheNewPaymentStatusDropdown(oBrowser, arrOptionValues[j]));
                                    }
                                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Dialog + "//button[@class='close']")));
                                    appDep.threadSleep(2000);
                                    oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
                                    break;
                                case "partially processed":
                                    Assert.assertTrue(appDep.searchPaymentID(oBrowser, paymentID));
                                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Btn));
                                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Btn));
                                    appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Dialog + "//button[@id='btn_submit_payment_status']"), "Clickable", "", waitTimeOut);
                                    reports.writeResult(oBrowser, "Screenshot", "The 'Edit Payment Status' button was displyed when Payment Type is '"+paymentType+"' & Payment Status is '"+paymentStatus+"'");
                                    Assert.assertTrue(appDep.validateTheListValues(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_NewPaymentStatus_List, "--Select--#Partially Voided#Partially Expired#Partially Processed#Prepaid Card Processed"));

                                    arrOptionValues = "Partially Voided#Partially Expired#Partially Processed#Prepaid Card Processed".split("#");
                                    for(int j=0; j<arrOptionValues.length; j++){
                                        Assert.assertTrue(validateElementsBasedOnTheOptionSelectedFromTheNewPaymentStatusDropdown(oBrowser, arrOptionValues[j]));
                                    }

                                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Dialog + "//button[@class='close']")));
                                    appDep.threadSleep(2000);
                                    oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
                                    break;
                            }
                            break;
                        default:
                            reports.writeResult(oBrowser, "Info", "Invalid 'Payment Type' '"+paymentType+"' was displyed. The required payment type was 'Prepaid / Debit card'");
                            Assert.fail("Invalid 'Payment Type' '"+paymentType+"' was displyed. The required payment type was 'Prepaid / Debit card'");
                    }

                    statusValidated+=paymentStatus+"#";
                }
            }
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validatePaymentStatusOptionsForPaymentTypePrepaidOrDebitCard()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validatePaymentStatusOptionsForPaymentTypePrepaidOrDebitCard()' method. " + e);
            return false;
        }finally {paymentIDDetails = null; paymentID = null; paymentType = null; paymentStatus = null; statusValidated = null; arrOptionValues = null;}
    }




    /********************************************************
     * Method Name      : validateNegativeScenarioForNotShowingEditPaymentStatusButton()
     * Purpose          : User validates the conditions not to show "Edit Payment Status" button
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateNegativeScenarioForNotShowingEditPaymentStatusButton(WebDriver oBrowser){
        JSONArray paymentIDDetails = null;
        String paymentID = null;
        String paymentType = null;
        String paymentStatus = null;
        try{
            boolean blnRes = appInd.verifyOptionalElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EnterPaymentID_Edit);
            if(!blnRes)
                Assert.assertTrue(appDep.selectManagePaymentModules(oBrowser, "Payment Search"));

            paymentIDDetails = apiDataProvider.getAPIDataProviderResponse("LogicToHide_EditPaymentStatus", new Object[] {});

            for(int i=0; i<((JSONArray) paymentIDDetails.get(0)).size(); i++) {
                paymentID = ((LinkedHashMap) ((JSONArray) paymentIDDetails.get(0)).get(i)).get("PaymentID").toString();
                paymentType = ((LinkedHashMap) ((JSONArray) paymentIDDetails.get(0)).get(i)).get("PaymentType").toString();
                paymentStatus = ((LinkedHashMap) ((JSONArray) paymentIDDetails.get(0)).get(i)).get("PaymentStatus").toString();
                if(paymentType.equalsIgnoreCase("ACH Plus")) paymentType = "ACP";
                if(paymentType.equalsIgnoreCase("Virtual Card")) paymentType = "VCA";
                if(paymentType.equalsIgnoreCase("Check")) paymentType = "CHK";
                if(paymentType.equalsIgnoreCase("Prepaid Card")) paymentType = "VPC";

                switch(paymentType.toLowerCase()){
                    case "chk":
                        switch(paymentStatus.toLowerCase()){
                            case "cleared": case "voided": case "expired":
                                Assert.assertTrue(appDep.searchPaymentID(oBrowser, paymentID));
                                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Btn), "The 'Edit Payment Status' button was STILL displyed when Payment Type is '"+paymentType+"' & Payment Status is '"+paymentStatus+"'");
                                oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
                        }
                        break;
                    case "ach": case "acp":
                        switch(paymentStatus.toLowerCase()){
                            case "returned":
                                Assert.assertTrue(appDep.searchPaymentID(oBrowser, paymentID));
                                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Btn), "The 'Edit Payment Status' button was STILL displyed when Payment Type is '"+paymentType+"' & Payment Status is '"+paymentStatus+"'");
                                oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
                        }
                        break;
                    case "vca":
                        switch(paymentStatus.toLowerCase()){
                            case "virtual card processed": case "partially voided": case "partially expired": case "voided": case "expired":
                                Assert.assertTrue(appDep.searchPaymentID(oBrowser, paymentID));
                                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Btn), "The 'Edit Payment Status' button was STILL displyed when Payment Type is '"+paymentType+"' & Payment Status is '"+paymentStatus+"'");
                                oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
                        }
                        break;
                    case "vpc":
                        switch(paymentStatus.toLowerCase()){
                            case "prepaid card processed": case "partially voided": case "partially expired": case "voided": case "expired":
                                Assert.assertTrue(appDep.searchPaymentID(oBrowser, paymentID));
                                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EditPaymentStatus_Btn), "The 'Edit Payment Status' button was STILL displyed when Payment Type is '"+paymentType+"' & Payment Status is '"+paymentStatus+"'");
                                oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
                        }
                }
            }
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateNegativeScenarioForNotShowingEditPaymentStatusButton()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateNegativeScenarioForNotShowingEditPaymentStatusButton()' method. " + e);
            return false;
        }finally {paymentIDDetails = null; paymentID = null; paymentType = null; paymentStatus = null;}
    }




    /********************************************************
     * Method Name      : validateTheMandatoryAndOptionalFiledsInReIssueWindowBasedOnThePaymentTypeData()
     * Purpose          : User validates the mandatoty and non-mandatory fields in Re-Issue dialog based on the PAyment type selected
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateTheMandatoryAndOptionalFiledsInReIssueWindowBasedOnThePaymentTypeData(WebDriver oBrowser){
        String[] arrPaymentTypes;
        String paymentAmount;
        try{
            // https://determine.atlassian.net/browse/CPAY-3966 Fetching reissue amount is no more valid, hence applicable assertions have been removed. please refer https://determine.atlassian.net/browse/CPAY-3966

            Assert.assertTrue(appDep.selectManagePaymentModules(oBrowser, "Payment Search"));
            searchAndOpenThePaymentIDUsingAPIDataprovider(oBrowser, "ManagedPayment_ReIssue_Btn_Visible", new Object[] {}, "PaymentID");

            //Verify Re-Issue button exist
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ReIssue_Btn));

            //Click on Re_Issue Button & validate the mandatory and optional fields based on the payment types
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ReIssue_Btn));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_ReIssue_Dialog + "//input[@id='btn_pay_action_reissue_submit']"), "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_ReIssue_Dialog + "//h4"), "Text", "Re-Issue"));

            arrPaymentTypes = "Check#ACH#ACH Plus#Virtual Card".split("#");
            for (String arrPaymentType : arrPaymentTypes) {
                Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ReIssue_PaymentType_List, arrPaymentType));
                appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_ReIssue_Dialog + "//input[@id='btn_pay_action_reissue_submit']"), "Clickable", "", minTimeOut);
                Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_ReIssue_Dialog + "//input[@id='btn_pay_action_reissue_submit']")));
                appDep.threadSleep(1000);
                reports.writeResult(oBrowser, "Screenshot", "The payment type '" + arrPaymentType + "' was selected");
                if (arrPaymentType.equalsIgnoreCase("Check")) {
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeName_Edit + "/following-sibling::span[text()='Please enter PayeeName']")));
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeAddress1_Edit + "/following-sibling::span[text()='Please enter PayeeAddress1']")));
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeAddress2_Edit)));
                    Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeAddress2_Edit + "/following-sibling::span")));
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeCity_Edit + "/following-sibling::span[text()='Please enter PayeeCity']")));
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeState_Edit + "/following-sibling::span[text()='Please enter PayeeState']")));
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeZIP_Edit + "/following-sibling::span[text()='Please enter PayeeZip']")));
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeCountry_Edit + "/following-sibling::span[text()='Please enter PayeeCountry']")));
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_ReIssueNotes_Textarea)));
                    Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_ReIssueNotes_Textarea + "/following-sibling::span")));
                } else {
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeName_Edit + "/following-sibling::span[text()='Please enter PayeeName']")));
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeAddress1_Edit)));
                    Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeAddress1_Edit + "/following-sibling::span[text()='Please enter PayeeAddress1']")));
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeAddress2_Edit)));
                    Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeAddress2_Edit + "/following-sibling::span")));
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeCity_Edit)));
                    Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeCity_Edit + "/following-sibling::span[text()='Please enter PayeeCity']")));
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeState_Edit)));
                    Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeState_Edit + "/following-sibling::span[text()='Please enter PayeeState']")));
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeZIP_Edit)));
                    Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeZIP_Edit + "/following-sibling::span[text()='Please enter PayeeZip']")));
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeCountry_Edit)));
                    Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeCountry_Edit + "/following-sibling::span[text()='Please enter PayeeCountry']")));
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_ReIssueNotes_Textarea)));
                    Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_ReIssueNotes_Textarea + "/following-sibling::span")));
                }
            }

            //Validate Re-Issue Amount field by giving Value over remaining amount OR more than the remaining amount. Appropriate error message "Please enter value less than or equal Amount Remaining" should display
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ReIssue_PaymentType_List, "ACH"));
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeName_Edit), "PayeeName"));
            paymentAmount = appInd.getAttribute(oBrowser,  By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeReissueAmount_Edit), "value");
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeReissueAmount_Edit), String.valueOf(Double.parseDouble(paymentAmount) + 1)));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_ReIssue_Dialog + "//input[@id='btn_pay_action_reissue_submit']")));
            appDep.threadSleep(1000);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeReissueAmount_Edit + "/following-sibling::span"), "Text", "Please enter value less than or equal Amount Remaining"));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_ReIssue_Dialog + "//button[@class='close']")));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateTheMandatoryAndOptionalFiledsInReIssueWindowBasedOnThePaymentTypeData()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateTheMandatoryAndOptionalFiledsInReIssueWindowBasedOnThePaymentTypeData()' method. " + e);
            return false;
        }finally {arrPaymentTypes = null; paymentAmount = null;}
    }

    /********************************************************
     * Method Name      : performReIssueFunctionality()
     * Purpose          : User perform Re-issue functionality
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public Map<String, String> performReIssueFunctionality(WebDriver oBrowser, DataTable dataTable){
        List<Map<String, String>> reIssueData = null;
        Map<String, String> reIssue = null;
        String timeStamp = null;
        String paymentAmount = null;
        try{
            timeStamp = appInd.getDateTime("Shhmmss");
            reIssueData = dataTable.asMaps(String.class, String.class);
            reIssue = new HashMap<String, String>();
            reIssue.put("PaymentType", reIssueData.get(0).get("PaymentType"));
            reIssue.put("PayeeName", reIssueData.get(0).get("PayeeName") + timeStamp);
            reIssue.put("PayeeAddress1", reIssueData.get(0).get("PayeeAddress1"));
            reIssue.put("PayeeAddress2", reIssueData.get(0).get("PayeeAddress2"));
            reIssue.put("PayeeCity", reIssueData.get(0).get("PayeeCity"));
            reIssue.put("PayeeState", reIssueData.get(0).get("PayeeState"));
            reIssue.put("PayeeZip", reIssueData.get(0).get("PayeeZip"));
            reIssue.put("PayeeCountry", reIssueData.get(0).get("PayeeCountry"));
            reIssue.put("Notes", reIssueData.get(0).get("Notes") + timeStamp);

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ReIssue_Btn));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_ReIssue_Dialog + "//input[@id='btn_pay_action_reissue_submit']"), "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_ReIssue_Dialog + "//h4"), "Text", "Re-Issue"));

            paymentAmount = appInd.getAttribute(oBrowser,  By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeReissueAmount_Edit), "value");
            reIssue.put("PaymentAmount", paymentAmount);

            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ReIssue_PaymentType_List, reIssue.get("PaymentType")));
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeName_Edit), reIssue.get("PayeeName")));
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeAddress1_Edit),reIssue.get("PayeeAddress1")));
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeAddress2_Edit),reIssue.get("PayeeAddress2")));
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeCity_Edit),reIssue.get("PayeeCity")));
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeState_Edit),reIssue.get("PayeeState")));
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeZIP_Edit),reIssue.get("PayeeZip")));
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeCountry_Edit),reIssue.get("PayeeCountry")));
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PayeeReissueAmount_Edit),reIssue.get("PaymentAmount")));
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_ReIssueNotes_Textarea),reIssue.get("Notes")));
            Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_ReIssue_Dialog + "//input[@id='btn_pay_action_reissue_submit']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidRequestConfirmation_Msg, "Visibility", "", minTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidRequestConfirmation_Msg, "Text", "Re-issue Request Submitted Successfully"));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            reIssue.put("TestPassed", "True");
            return reIssue;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'performReIssueFunctionality()' method. " + e);
            reIssue.put("TestPassed", "False");
            return reIssue;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'performReIssueFunctionality()' method. " + e);
            reIssue.put("TestPassed", "False");
            return reIssue;
        }finally {reIssueData = null; reIssue = null; timeStamp = null; paymentAmount = null;}
    }





    /********************************************************
     * Method Name      : performReIssueAndValidateTheSame()
     * Purpose          : User perform Re-issue and validates the same under Activities tab
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean performReIssueAndValidateTheSame(WebDriver oBrowser, DataTable dataTable){
        Map<String, String> reIssueData = null;
        try{
            reIssueData = performReIssueFunctionality(oBrowser, dataTable);
            Assert.assertTrue(reIssueData.get("TestPassed").equalsIgnoreCase("True"), "The 'performReIssueFunctionality()' method was failed.");

            //Validate the same in 'Activitie' tab
            appInd.scrollToElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Link);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//input)[5]"), reIssueData.get("Notes")));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[1]"), "Text", "User Action - Re-issue Initiated"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[2]"), "Text", appInd.getPropertyValueByKeyName("qaUserName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[3]"), "Text", appInd.formatDate(appInd.getDateTimeByTimezone("dd-MM-yyyy", "CST"), "dd-MM-yyyy", "MM/dd/yyyy")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[4]"), "Text", reIssueData.get("Notes")));

            //Verify Re-issue button is not visible once Reissue is completed successfully
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ReIssue_Btn), "Failed to remove the 'Re-Issue' button once the re-issue action is completed");
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'performReIssueAndValidateTheSame()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'performReIssueAndValidateTheSame()' method. " + e);
            return false;
        }finally{reIssueData = null;}
    }




    /********************************************************
     * Method Name      : verifyNonAvailabilityOfReIssueButton()
     * Purpose          : User perform Re-issue and validates the same under Activities tab
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean verifyNonAvailabilityOfReIssueButton(WebDriver oBrowser){
        String paymentID = null;
        JSONArray paymentIDDetails = null;
        String paymentStatus = null;
        String validationCompleted = "";
        try{
            paymentIDDetails = apiDataProvider.getAPIDataProviderResponse("ManagedPayment_ReIssue_Btn_Hide", new Object[] {});
            if(((JSONArray) paymentIDDetails.get(0)).isEmpty()){
                reports.writeResult(oBrowser, "Fail", "Data not found for the API 'ManagedPayment_ReIssue_Btn_Hide' with the parameter 'NA'");
                Assert.fail("Data not found for the API 'ManagedPayment_ReIssue_Btn_Hide' with the parameter 'NA'");
            }

            for(int i=0; i<((JSONArray) paymentIDDetails.get(0)).size(); i++){
                paymentID = ((LinkedHashMap) ((JSONArray) paymentIDDetails.get(0)).get(i)).get("PaymentID").toString();
                paymentStatus = ((LinkedHashMap) ((JSONArray) paymentIDDetails.get(0)).get(i)).get("PaymentStatus").toString();

                if(!validationCompleted.contains(paymentStatus)){
                    Assert.assertTrue(appDep.searchPaymentID(oBrowser, paymentID));
                    Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ReIssue_Btn));
                    oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
                    validationCompleted+=paymentStatus+"#";
                }
            }
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifyNonAvailabilityOfReIssueButton()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifyNonAvailabilityOfReIssueButton()' method. " + e);
            return false;
        }finally {paymentID = null; paymentIDDetails = null; paymentStatus = null;validationCompleted = null;}
    }




    /********************************************************
     * Method Name      : validateShowAndHideFunctionalityForMultiSwipeButton()
     * Purpose          : User perform Re-issue and validates the same under Activities tab
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateShowAndHideFunctionalityForMultiSwipeButton(WebDriver oBrowser){
        JSONArray paymentIDDetails = null;
        String paymentID = null;
        String paymentStatus = null;
        String paymentType = null;
        try{
            Assert.assertTrue(appDep.selectManagePaymentModules(oBrowser, "Payment Search"));

            paymentIDDetails = apiDataProvider.getAPIDataProviderResponse("ManagedPayment_MultiSwipe_Visible", new Object[] {});
            if(((JSONArray) paymentIDDetails.get(0)).isEmpty()){
                reports.writeResult(oBrowser, "Fail", "Data not found for the API 'ManagedPayment_MultiSwipe_Visible' with the parameter 'NA'");
                Assert.fail("Data not found for the API 'ManagedPayment_MultiSwipe_Visible' with the parameter 'NA'");
            }

            for(int i=0; i<((JSONArray) paymentIDDetails.get(0)).size(); i++){
                paymentID = ((LinkedHashMap) ((JSONArray) paymentIDDetails.get(0)).get(i)).get("PaymentID").toString();
                paymentType = ((LinkedHashMap) ((JSONArray) paymentIDDetails.get(0)).get(i)).get("PaymentType").toString();
                paymentStatus = ((LinkedHashMap) ((JSONArray) paymentIDDetails.get(0)).get(i)).get("PaymentStatus").toString();

                if(paymentType.equalsIgnoreCase("Virtual Card") || paymentType.equalsIgnoreCase("VCA")){
                    switch(paymentStatus.toLowerCase()){
                        case "confirmed": case "authorized": case "partially processed":
                            Assert.assertTrue(appDep.searchPaymentID(oBrowser, paymentID));
                            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EnableMultiSwipe_Btn), "The 'Enable Multi-Swipe' button was not displayed");

//                            int multiSwipeButtonSize = oBrowser.findElements(PayCRM_Modules_ManagedPaymentsUIPage.obj_EnableMultiSwipe_Btn).size();
//                            if (multiSwipeButtonSize == 0) {
//                                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_multiSwipeBtnEnabled), "The 'Enabled Multi-Swipe' icon is not displayed.");
//                            }

                            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
                            break;
                    }
                }
            }

            //The "Enable Multi-Swipe" button should be disabled
            paymentIDDetails = apiDataProvider.getAPIDataProviderResponse("ManagedPayment_MultiSwipe_Hide", new Object[] {});
            if(((JSONArray) paymentIDDetails.get(0)).isEmpty()){
                reports.writeResult(oBrowser, "Fail", "Data not found for the API 'ManagedPayment_MultiSwipe_Hide' with the parameter 'NA'");
                Assert.fail("Data not found for the API 'ManagedPayment_MultiSwipe_Hide' with the parameter 'NA'");
            }

            for(int i=0; i<((JSONArray) paymentIDDetails.get(0)).size(); i++){
                paymentID = ((LinkedHashMap) ((JSONArray) paymentIDDetails.get(0)).get(i)).get("PaymentID").toString();
                paymentType = ((LinkedHashMap) ((JSONArray) paymentIDDetails.get(0)).get(i)).get("PaymentType").toString();
                paymentStatus = ((LinkedHashMap) ((JSONArray) paymentIDDetails.get(0)).get(i)).get("PaymentStatus").toString();

                if(paymentType.equalsIgnoreCase("Virtual Card") || paymentType.equalsIgnoreCase("VCA")){
                    switch(paymentStatus.toLowerCase()){
                        case "cancelled": case "partially voided": case "voided ": case "expired": case "virtual card processed":
                            Assert.assertTrue(appDep.searchPaymentID(oBrowser, paymentID));
                            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EnableMultiSwipe_Btn), "The 'Enable Multi-Swipe' button was not displayed");
                            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
                            break;
                    }
                }
            }
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateShowAndHideFunctionalityForMultiSwipeButton()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateShowAndHideFunctionalityForMultiSwipeButton()' method. " + e);
            return false;
        }finally {paymentIDDetails = null; paymentID = null; paymentStatus = null; paymentType = null;}
    }




    /********************************************************
     * Method Name      : validateEnableMultiSwipeFunctionalityAndValidateTheSame()
     * Purpose          : User perform 'Enable Multi-Swipe' and validates the same under Activities tab
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateEnableMultiSwipeFunctionalityAndValidateTheSame(WebDriver oBrowser){
        Response response = null;
        JSONArray paymentIDDetails = null;
        String paymentID = null;
        Alert oAlert = null;
        try{
            paymentID = searchAndOpenThePaymentIDUsingAPIDataprovider(oBrowser, "ManagedPayment_MultiSwipe_Visible", new Object[] {}, "PaymentID");

            //Validate 'Enable Multi-Swipe' button should be displayed & Click on Enable Multi-Swipe Button
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EnableMultiSwipe_Btn), "Failed to display 'Enable Multi-Swipe' button");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_MultiSwipeDisabled_Btn), "Failed to display 'Mukti-Swipe Disabled' button");

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EnableMultiSwipe_Btn));
            appDep.threadSleep(2000);
            oAlert = oBrowser.switchTo().alert();
            Assert.assertTrue(oAlert.getText().equalsIgnoreCase("Are you sure you want to enable Multiswipe?"));
            oAlert.accept();
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidRequestConfirmation_Msg, "Visibility", "", minTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidRequestConfirmation_Msg, "Text", "Multi-Swipe Request Submitted Successfully"));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_search_Loading, "Invisibility", "", minTimeOut);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_MultiSwipeEnabled_Img));

            //Validate the entry in the Activities tab
            String dateCreated = appInd.formatDate(appInd.getDateTimeByTimezone("dd-MM-yyyy", "CST"), "dd-MM-yyyy", "MM/dd/yyyy");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Link));
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//input)[2]"), appInd.getPropertyValueByKeyName("qaUserName")));
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//input)[4]"), dateCreated));
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//input)[5]"), "Multi-Swipe initialized by: " + appInd.getPropertyValueByKeyName("qaUserName")));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[1]"), "Text", "User Action - Enabled Multi-Swipe"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[2]"), "Text", appInd.getPropertyValueByKeyName("qaUserName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[3]"), "Text", dateCreated));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[4]"), "Text", "Multi-Swipe initialized by: " + appInd.getPropertyValueByKeyName("qaUserName")));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateEnableMultiSwipeFunctionalityAndValidateTheSame()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateEnableMultiSwipeFunctionalityAndValidateTheSame()' method. " + e);
            return false;
        }finally {response = null; paymentIDDetails = null; paymentID = null; oAlert = null;}
    }




    /********************************************************
     * Method Name      : add_OR_RemovePermissionForManagedPayments()
     * Purpose          : user add/removes the Roles/Permission for Managed Payments
     * Author           : Gudi
     * Parameters       : oBrowser, permissionAction, appName, loginDetails
     * ReturnType       : boolean
     ********************************************************/
    public boolean add_OR_RemovePermissionForManagedPayments(WebDriver oBrowser, String permissionAction, String logoutRequired){
        String arrPermissions[];
        try{
            Assert.assertTrue(appDep.selectManageModules(oBrowser, "User Console"));

            //Search the user and add/remove the permission for 'Managed Payments'
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+ PayCRM_Modules_ManagedPaymentsUIPage.obj_UserConsole_Grid + "//input)[1]"), appInd.getPropertyValueByKeyName("qaPermissionUser")));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_UserConsole_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[1]")));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_UserRoles_Section + "//ul[@id='user_roles']/li"), "Clickable", "", minTimeOut);

            arrPermissions = "Super Admin#Managed Payment Admin".split("#");
            for(int i=0; i<arrPermissions.length; i++){
                if(permissionAction.equalsIgnoreCase("Enable")){
                    if(!oBrowser.findElement(By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_UserRoles_Section + "//ul[@id='user_roles']/li/input[@value='"+arrPermissions[i]+"']")).isSelected())
                        Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("//input[@value='"+arrPermissions[i]+"']/following-sibling::label")));
                }else{
                    if(oBrowser.findElement(By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_UserRoles_Section + "//ul[@id='user_roles']/li/input[@value='"+arrPermissions[i]+"']")).isSelected())
                        Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("//input[@value='"+arrPermissions[i]+"']/following-sibling::label")));
                }
            }
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Roles_Update_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ConfirmationMessage_Label, "Visibility", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ConfirmationMessage_Label, "Text").contains("User was successfully updated."));
            reports.writeResult(oBrowser, "Screenshot", "The permission was '"+permissionAction+"d' for 'Managed Payments'");

            if(logoutRequired.equalsIgnoreCase("Yes")){
                Assert.assertTrue(enrollmentManagersUIBaseSteps.logoutFromTheApplication(oBrowser, "PayCRM"));
            }
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in ' add_OR_RemovePermissionForManagedPayments()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'add_OR_RemovePermissionForManagedPayments()' method. " + e);
            return false;
        }finally{arrPermissions = null;}
    }




    /********************************************************
     * Method Name      : validateManagedPaymentsRoleAndPermission()
     * Purpose          : user validates the Roles/PErmission for Managed Payments
     * Author           : Gudi
     * Parameters       : oBrowser, permissionStatus
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateManagedPaymentsRoleAndPermission(WebDriver oBrowser, String permissionStatus){
        try{
            Assert.assertTrue(appInd.clickObject(oBrowser, PartnerEnrollmentsUIPage.obj_payCRM_NavigateBar_Link));
            appDep.waitForTheElementState(oBrowser, PartnerEnrollmentsUIPage.obj_AssistedPaymentServices_Link, "Clickable", "", minTimeOut);
            oBrowser.navigate().refresh();
            appDep.waitForTheElementState(oBrowser, PartnerEnrollmentsUIPage.obj_AssistedPaymentServices_Link, "Clickable", "", minTimeOut);
            appDep.threadSleep(5000);
            oBrowser.navigate().refresh();
            appDep.waitForTheElementState(oBrowser, PartnerEnrollmentsUIPage.obj_AssistedPaymentServices_Link, "Clickable", "", minTimeOut);

            if(permissionStatus.equalsIgnoreCase("Before")){
                reports.writeResult(oBrowser, "Screenshot", "Before disabling the permission for 'Managed Payments'");
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Modules_Menu));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Manage_Payment_Pending_Bank_Files_Link));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Manage_Payment_Bank_File_Receipts_Link));

                //'Pending Bank File' page should display
                oBrowser.navigate().to(appInd.getPropertyValueByKeyName("qaURL") + "/managed_payment/pending_bank_files");
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Pending_Tab, "Clickable", "", minTimeOut);
                reports.writeResult(oBrowser, "Screenshot", "Before disabling the permission for 'Managed Payments-->Pending Bank File'");
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Pending_Tab));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Unresolved_Tab));
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Permission_Error + "[contains(normalize-space(), 'Access denied')]")));


                //'Bank File Receipts' should display
                oBrowser.navigate().to(appInd.getPropertyValueByKeyName("qaURL") + "/managed_payment/bank_file_receipts");
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_OpenBankFileReceipts_Link, "Clickable", "", minTimeOut);
                reports.writeResult(oBrowser, "Screenshot", "Before disabling the permission for 'Managed Payments-->Bank File Receipts'");
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_OpenBankFileReceipts_Link));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ArchievedBankFileReceipts_Link));
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Permission_Error + "[contains(normalize-space(), 'Access denied')]")));

            }else{
                reports.writeResult(oBrowser, "Screenshot", "After disabling the permission for 'Managed Payments'");
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Modules_Menu));
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Manage_Payment_Pending_Bank_Files_Link));
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Manage_Payment_Bank_File_Receipts_Link));

                //'Pending Bank File' page should display
                oBrowser.navigate().to(appInd.getPropertyValueByKeyName("qaURL") + "/managed_payment/pending_bank_files");
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Pending_Tab, "Clickable", "", 10);
                reports.writeResult(oBrowser, "Screenshot", "After disabling the permission for 'Managed Payments-->Pending Bank File'");
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Permission_Error + "[contains(normalize-space(), 'Access denied')]")));
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Pending_Tab));
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Unresolved_Tab));


                //'Bank File Receipts' should display
                oBrowser.navigate().to(appInd.getPropertyValueByKeyName("qaURL") + "/managed_payment/bank_file_receipts");
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_OpenBankFileReceipts_Link, "Clickable", "", 10);
                reports.writeResult(oBrowser, "Screenshot", "After disabling the permission for 'Managed Payments-->Bank File Receipts'");
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Permission_Error + "[contains(normalize-space(), 'Access denied')]")));
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_OpenBankFileReceipts_Link));
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ArchievedBankFileReceipts_Link));
            }
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in ' validateManagedPaymentsRoleAndPermission()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateManagedPaymentsRoleAndPermission()' method. " + e);
            return false;
        }finally {
            Assert.assertTrue(enrollmentManagersUIBaseSteps.logoutFromTheApplication(oBrowser, "PayCRM"));
        }
    }





    /********************************************************
     * Method Name      : userValidatesShowORHideFunctionalityForUpdateFeeButton()
     * Purpose          : user validates the Show OR Hide payment statuses for 'Update Fee' button
     * Author           : Gudi
     * Parameters       : oBrowser, showORHide, queryKey
     * ReturnType       : boolean
     ********************************************************/
    public boolean userValidatesShowORHideFunctionalityForUpdateFeeButton(WebDriver oBrowser, String showORHide, String queryKey){
        JSONArray paymentIDDetails = null;
        String paymentID = null;
        String paymentType = null;
        String paymentStatus = null;
        String statusValidated = "";
        try{
            boolean blnRes = appInd.verifyOptionalElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EnterPaymentID_Edit);
            if(!blnRes)
                Assert.assertTrue(appDep.selectManagePaymentModules(oBrowser, "Payment Search"));

            paymentIDDetails = apiDataProvider.getAPIDataProviderResponse(queryKey, new Object[] {});

            for(int i=0; i<((JSONArray) paymentIDDetails.get(0)).size(); i++){
                paymentID = ((LinkedHashMap) ((JSONArray) paymentIDDetails.get(0)).get(i)).get("PaymentID").toString();
                paymentType = ((LinkedHashMap) ((JSONArray) paymentIDDetails.get(0)).get(i)).get("PaymentType").toString();
                paymentStatus = ((LinkedHashMap) ((JSONArray) paymentIDDetails.get(0)).get(i)).get("PaymentStatus").toString();

                if(!statusValidated.contains(paymentStatus)){
                    if(paymentType.equalsIgnoreCase("Virtual Card")) paymentType = "VCA";
                    Assert.assertTrue(appDep.searchPaymentID(oBrowser, paymentID));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_MngdPayments_PaymentDetails_Grid + "//th[text()='Payment type']/following-sibling::td[1]"), "Text", paymentType));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_MngdPayments_PaymentDetails_Grid + "//th[text()='Status']/following-sibling::td[1]"), "Text", paymentStatus));

                    if(showORHide.equalsIgnoreCase("Show")){
                        switch (paymentType.toLowerCase()){
                            case "vca":
                                reports.writeResult(oBrowser, "Info", "The Payment Type is '"+paymentType+"' for '"+showORHide+"' Update Fee button");
                                switch(paymentStatus.toLowerCase()){
                                    case "confirmed": case "authorized": case "partially processed": case "partially expired": case "expired":
                                        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UpdateFee_Btn), "Failed to find the 'Update Fee' button");
                                        break;
                                }
                                break;
                            default:
                                reports.writeResult(oBrowser, "Fail", "Invalid Payment type: '"+paymentType+"' for the 'Update Fee' button");
                                Assert.fail("Invalid Payment type: '"+paymentType+"' for the 'Update Fee' button");
                        }
                    }else{
                        switch (paymentType.toLowerCase()){
                            case "vca":
                                reports.writeResult(oBrowser, "Info", "The Payment Type is '"+paymentType+"' for '"+showORHide+"' Update Fee button");
                                switch(paymentStatus.toLowerCase()){
                                    case "cancelled": case "partially voided": case "voided": case "virtual card processed":
                                        Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UpdateFee_Btn), "The 'Update Fee' button was present in the UI");
                                }
                                break;
                            default:
                                reports.writeResult(oBrowser, "Fail", "Invalid Payment type: '"+paymentType+"' for the 'Update Fee' button");
                                Assert.fail("Invalid Payment type: '"+paymentType+"' for the 'Update Fee' button");
                        }
                    }

                    oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
                    statusValidated+=paymentStatus+"#";
                }
            }
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'userValidatesShowORHideFunctionalityForUpdateFeeButton()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'userValidatesShowORHideFunctionalityForUpdateFeeButton()' method. " + e);
            return false;
        }finally{paymentIDDetails = null; paymentID = null; paymentType = null; paymentStatus = null; statusValidated = null;}
    }





    /********************************************************
     * Method Name      : validateUpdateFeeFunctionality()
     * Purpose          : user validates the functionality of 'Update Fee' button
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateUpdateFeeFunctionality(WebDriver oBrowser){
        String paymentID = null;
        String currentFee = null;
        String str;
        try{
            paymentID = searchAndOpenThePaymentIDUsingAPIDataprovider(oBrowser, "ManagedPayment_MultiSwipe_Visible", new Object[] {}, "PaymentID");
            if(paymentID.equalsIgnoreCase("Data not found for the API")) {
                reports.writeResult(oBrowser, "Fail", "The data was not found in the DB for the dataProvider API 'ManagedPayment_MultiSwipe_Visible'");
                Assert.fail("The data was not found in the DB for the dataProvider API 'ManagedPayment_MultiSwipe_Visible'");
            }else{
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UpdateFee_Btn), "Failed to find the 'Update Fee' button in the UI");
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UpdateFee_Btn));
                appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_UpdateFee_Dialog + "//button[@id='btn_submit_update_fee']"), "Clickable", "", minTimeOut);
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_UpdateFee_Dialog + "//h4"), "Text", "Update Fee"));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UpdateFee_CurrentFees_Edit));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_UpdateFee_NewFee_Edit)));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UpdateFee_Notes_Textarea));

                //New fee amount should be less than or equal to payment amount and if this exceeds we should get error on modal.
                currentFee = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_MngdPayments_PaymentDetails_Grid + "//th[text()='Payment amount']/following-sibling::td[1]"), "Text");
                if (currentFee.contains(",")) {
                    currentFee = currentFee.replace(",","");
                }
                Assert.assertTrue(appInd.setObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_UpdateFee_NewFee_Edit), String.valueOf(Double.parseDouble(currentFee.replace("$","")) + 10.0)));
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_UpdateFee_Dialog + "//button[@id='btn_submit_update_fee']")));
                appDep.threadSleep(1000);
                str = oBrowser.findElement(By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_UpdateFee_NewFee_Edit + "/following-sibling::span")).getText().split(" ")[9];
                str = str.replace(str.substring(str.length()-1), "");
                Assert.assertEquals(Double.parseDouble(currentFee.replace("$", "")), Double.parseDouble(str));
                String notes = "Update Fee_" + appInd.getDateTime("Shhmmss");
                Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_UpdateFee_NewFee_Edit), "0"));
                Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_UpdateFee_Notes_Textarea, notes));
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_UpdateFee_Dialog + "//button[@id='btn_submit_update_fee']")));
                appDep.waitForTheElementState(oBrowserTwo, PayCRM_Modules_ManagedPaymentsUIPage.obj_ConfirmationMessage_Label, "Visibility", "", waitTimeOut);
                //Assert.assertTrue(appInd.getTextOnElement(oBrowserTwo, PayCRM_Modules_ManagedPaymentsUIPage.obj_ConfirmationMessage_Label, "Text").contains("User was successfully updated."));
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);

                //Validate the 'Update Fee' entry under activities tab
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Link));
                Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//input)[5]"), notes));
                appDep.threadSleep(2000);
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[1]"), "Text", "User Action - Fee Updated"));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[2]"), "Text", appInd.getPropertyValueByKeyName("qaUserName")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[3]"), "Text", appInd.formatDate(appInd.getDateTimeByTimezone("dd-MM-yyyy", "CST"), "dd-MM-yyyy", "MM/dd/yyyy")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[4]"), "Text", "Fee amount updated by: "+appInd.getPropertyValueByKeyName("qaUserName")+" User notes: " + notes));
            }
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateUpdateFeeFunctionality()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateUpdateFeeFunctionality()' method. " + e);
            return false;
        }finally {paymentID = null; currentFee = null; str = null;}
    }




    /********************************************************
     * Method Name      : configureEmailIDForResendVoidNotification()
     * Purpose          : user configures email ID for Resend Void Notifications
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public String configureEmailIDForResendVoidNotification(WebDriver oBrowser){
        String paymentID = null;
        JSONArray paymentIDDetails = null;
        try{
            paymentIDDetails = apiDataProvider.getAPIDataProviderResponse("ManagedPayment_ReSendVoidNotification", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) paymentIDDetails.get(0)).size(), 1);
            paymentID = ((LinkedHashMap) ((JSONArray) paymentIDDetails.get(0)).get(Integer.parseInt(randomNum)-1)).get("PaymentID").toString();

            Assert.assertTrue(appDep.selectGeneralModules(oBrowser, "Companies"));
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_Companies_Grid + "//input)[8]"), "Client"));
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_Companies_Grid + "//input)[4]"), (paymentID.split("-"))[0]));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Companies_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[1]")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ViewActivities_Btn, "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Preferences_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentVoidContactEmails_Edit, appInd.getPropertyValueByKeyName("emailUserName")));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Preferences_Update_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            return paymentID;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'configureEmailIDForResendVoidNotification()' method. " + e);
            return null;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'configureEmailIDForResendVoidNotification()' method. " + e);
            return null;
        }finally {paymentID = null; paymentIDDetails = null;}
    }





    /********************************************************
     * Method Name      : performResendVoidNotificationAndValidateTheSame()
     * Purpose          : user configures email ID for Resend Void Notifications & sends notification for 'Resend Void Notifications'
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean performResendVoidNotificationAndValidateTheSame(WebDriver oBrowser){
        String paymentID = null;
        Map<String, String> resendNotification = null;
        try{
            paymentID = configureEmailIDForResendVoidNotification(oBrowser);

            resendNotification = new HashMap<>();
            Assert.assertTrue(appDep.selectManagePaymentModules(oBrowser, "Payment Search"));
            Assert.assertTrue(appDep.searchPaymentID(oBrowser, paymentID));

            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ResendVoidNotification_Btn), "The 'Resend Void Notification' button doesn't exist");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ResendVoidNotification_Btn));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_ResendVoidNotification_Dialog + "//button[@id='btn_submit_resend_void']"), "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_ResendVoidNotification_Dialog +"//h4"), "Text", "Re-send Void Notification"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_ResendVoidNotification_Dialog +"//div/input)[1]"), "Value", appInd.getPropertyValueByKeyName("emailUserName")));

            resendNotification.put("emailID", appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_ResendVoidNotification_Dialog +"//div/input)[1]"), "Value"));
            resendNotification.put("vendorName", appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_ResendVoidNotification_Dialog +"//div/input)[2]"), "Value"));
            resendNotification.put("remitID", appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_ResendVoidNotification_Dialog +"//div/input)[3]"), "Value"));
            resendNotification.put("paymentAmount", appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_ResendVoidNotification_Dialog +"//div/input)[4]"), "Value"));
            resendNotification.put("paymentReference", appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_ResendVoidNotification_Dialog +"//div/input)[5]"), "Value"));
            resendNotification.put("Subject", "Voided Card Payment for " + resendNotification.get("vendorName"));
            resendNotification.put("Message", "Please accept this as notification that the following listed card payment has been Voided");
            resendNotification.put("paymentID", paymentID);

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_ResendVoidNotification_Dialog + "//button[@id='btn_submit_resend_void']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidRequestConfirmation_Msg, "Text", "Re-send Void Notification sent Successfully", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidRequestConfirmation_Msg, "Text", "Re-send Void Notification sent Successfully"));
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);

            // ----------------------- BEFORE CLOSE CHILD TAB OR HERE ACTIVITY VERIFICATION WILL IN PLACE ----------------------
            appInd.scrollToThePage(oBrowser, "Down");
            appDep.threadSleep(1000);
            appInd.clickObject(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_Generic_Text,"Attachments")));
            appDep.threadSleep(1000);
            appInd.clickObject(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_Generic_Text,"Activities")));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("(//div[@id='divCaseActivitiesDashboardContainer']//table)[2]//tr[1]/td[1]"), "text").equalsIgnoreCase("Internal Action - Void Payment Email Sent"));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("(//div[@id='divCaseActivitiesDashboardContainer']//table)[2]//tr[1]/td[3]"), "text").equalsIgnoreCase(appInd.formatDate((appInd.dateAddAndReturn("MM/dd/yyyy",0)), "MM/dd/yyyy","MM/dd/yyyy")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("(//div[@id='divCaseActivitiesDashboardContainer']//table)[2]//tr[1]/td[4]"), "text").equalsIgnoreCase("Email sent to [\""+resendNotification.get("emailID")+"\"] to void payment"));

            appInd.clickObject(oBrowser, By.xpath("(//div[@id='divCaseActivitiesDashboardContainer']//table)[2]//tr[1]//a[@title='Show']"));
            appDep.threadSleep(1000);
            appDep.waitForTheElementState(oBrowser, By.xpath("//div[@id='view_notes' and contains(@style,'block')]//img[@alt='Gravatar']"), "visibility", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//div[@id='view_notes' and contains(@style,'block')]//div[text()='Internal Action - Void Payment Email Sent']")));
            appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_viewNotesCloseButton);
            //--------------------------------------- EMAIL VERIFICATION ------------------------------
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
            Assert.assertTrue(emailUtilities.connectAndOpenEmails(resendNotification));
            Assert.assertTrue(emailUtilities.readEmailFromResendVoidNotification(oEmailBrowser, resendNotification));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'performResendVoidNotificationAndValidateTheSame()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'performResendVoidNotificationAndValidateTheSame()' method. " + e);
            return false;
        }finally {paymentID = null; resendNotification = null;}
    }

    public boolean validatePendingAuthFunctionality(WebDriver oBrowser){
        JSONArray dbResponse = null;
        String paymentID = null;
        String authorizationDate = null;
        try{
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagePayments_AuthorizationDate_GETInput", new Object[]{});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            paymentID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("PaymentID").toString();
            authorizationDate = appInd.formatDateFromOnetoAnother(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("AuthorizationDate").toString(), "yyyy-MM-dd", "MM/dd/yy");
            Assert.assertTrue(appDep.selectManagePaymentModules(oBrowser, "Payment Search"));
            Assert.assertTrue(appDep.searchPaymentID(oBrowser, paymentID));
            appDep.waitForTheElementState(oBrowser, By.id("btn_log_activity"), "visibility", "", minTimeOut);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//div[@class='blurb-warning']")));
            String authMsg = appInd.getTextOnElement(oBrowser, By.xpath("//div[@class='blurb-warning']"), "text").trim();
            Assert.assertTrue(authMsg.startsWith("Authorization pending as of"));
            Assert.assertTrue(authMsg.endsWith(authorizationDate+"."));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validatePendingAuthFunctionality()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validatePendingAuthFunctionality()' method. " + e);
            return false;
        }
    }
}
