package com.corcentric.baseSteps.ui;

import com.corcentric.pages.PayCRM_Modules_GeneralUIPage;
import com.corcentric.runner.CucumberTestRunner;
import io.cucumber.datatable.DataTable;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class PayCRM_Modules_ImportsUIBaseStep extends CucumberTestRunner {
    private String newMasterSupplierFile;
    private String masterSupplierFileName;
    private String updateMasterSupplierFile;
    private String newMatchFileName;
    private String matchFileName;
    private String fileUploaded;
    private String paymentBasedEnrollmentName;
    private String reportFileName;
    private String newReportFileName;

    /********************************************************
     * Method Name      : createMasterSupplierFile()
     * Purpose          : user creates the new Master Supplier Files from Modules-> Import-> Master Supplier Files
     * Author           : Gudi
     * Parameters       : oBrowser, masterSupplierFileData
     * ReturnType       : Map<String, String>
     ********************************************************/
    public Map<String, String> createMasterSupplierFile(WebDriver oBrowser, Map<String, String> masterSupplierFileData) {
        Select oSelect = null;
        try {
            Assert.assertTrue(appDep.selectImportsModules(oBrowser, "Master Supplier Files"));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MasterSupplierFiles_Add_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MasterSupplierFiles_Create_Btn, "Clickable", "", waitTimeOut);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_EditContact_PageTitle, "Text", "New Master Supplier File"));
            reports.writeResult(oBrowser, "Screenshot", "'New Master Supplier File' page was opened successful");
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFile_Name_Edit, masterSupplierFileData.get("Name")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFile_Company_List, masterSupplierFileData.get("Company")));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFile_Wave_List), "Clickable", "", waitTimeOut);
            //Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFile_Wave_List), 1));
            appDep.threadSleep(1000);
            oSelect = new Select(appInd.createAndGetWebElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFile_Wave_List)));
            masterSupplierFileData.put("Wave", oSelect.getFirstSelectedOption().getText());
            Assert.assertTrue(appInd.setOptionalObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFile_Version_Edit, masterSupplierFileData.get("Version")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFile_Status_List, masterSupplierFileData.get("Status")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFile_Priority_List, masterSupplierFileData.get("Priority")));

            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MasterSupplierFile_Upload_Edit, System.getProperty("user.dir").replace("\\", "/") + "/TestData/" + masterSupplierFileData.get("FileToUpload") + ".xlsx"));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MasterSupplierFiles_Create_Btn, "Clickable", "", waitTimeOut);
            appDep.threadSleep(4000);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_BrowseFile_Edit, "Value").contains(masterSupplierFileData.get("FileToUpload")));
            if (masterSupplierFileData.get("OverrideHistoricalValues") != null) {
                boolean blnRes = false;
                if (masterSupplierFileData.get("OverrideHistoricalValues").equalsIgnoreCase("Yes")) {
                    blnRes = appInd.verifyOptionalElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFile_OverrideHistoricalValues_No_Btn);
                    if (blnRes)
                        Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFile_OverrideHistoricalValues_No_Btn));
                    else Assert.assertTrue(false, "Failed to find the NO button on 'Override Historical Values''");
                }/*else{
                    blnRes = appInd.verifyOptionalElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFile_OverrideHistoricalValues_Yes_Btn);
                    if(blnRes)
                        Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFile_OverrideHistoricalValues_Yes_Btn));
                    else Assert.assertTrue(false, "Failed to find the YES button on 'Override Historical Values''");
                }*/
            }
            reports.writeResult(oBrowser, "Screenshot", "Before creating 'New Master Supplier File'");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MasterSupplierFiles_Create_Btn));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ShowEditDetails_Header, "Text", "Information", waitTimeOut);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFiles_Show_Grid + "//dl/dd[1]"), "Text", masterSupplierFileData.get("Name"), waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "After creating 'New Master Supplier File'");
            masterSupplierFileData.put("TestPassed", "True");
            return masterSupplierFileData;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createMasterSupplierFile()' method. " + e);
            masterSupplierFileData.put("TestPassed", "False");
            return masterSupplierFileData;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createMasterSupplierFile()' method. " + e);
            masterSupplierFileData.put("TestPassed", "False");
            return masterSupplierFileData;
        } finally {oSelect = null;}
    }



    /********************************************************
     * Method Name      : validateMasterSupplierFilesData()
     * Purpose          : user validate the Master Supplier Files details from Modules-> Import-> Master Supplier Files
     * Author           : Gudi
     * Parameters       : oBrowser, masterSupplierFileStatus, masterSupplierFileData
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateMasterSupplierFilesData(WebDriver oBrowser, String masterSupplierFileStatus, Map<String, String> masterSupplierFileData) {
        String fileName = null;
        String fileImportStatus = null;
        try {
            if (masterSupplierFileStatus.equalsIgnoreCase("New")) {
                fileName = masterSupplierFileData.get("FileToUpload");
            } else {
                if (masterSupplierFileData.get("FileToUpload") != null) {
                    fileName = masterSupplierFileData.get("FileToUpload");
                } else {
                    fileName = masterSupplierFileName;
                }
            }

            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFiles_Show_Grid + "//dl/dd[5]/a"), "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFiles_Show_Grid + "//dl/dd[1]"), "Text", masterSupplierFileData.get("Name")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFiles_Show_Grid + "//dl/dd[2]"), "Text").contains(appInd.getDateTime("yyyy-MM-dd")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFiles_Show_Grid + "//dl/dd[3]/a"), "Text", masterSupplierFileData.get("Company")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFiles_Show_Grid + "//dl/dd[4]/a"), "Text", masterSupplierFileData.get("Wave")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFiles_Show_Grid + "//dl/dd[5]/a"), "Text").contains(fileName));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFiles_Show_Grid + "//dl/dd[6]"), "Text", masterSupplierFileData.get("Version")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFiles_Show_Grid + "//dl/dd[7]"), "Text", masterSupplierFileData.get("Status")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFiles_Show_Grid + "//dl/dd[8]"), "Text", masterSupplierFileData.get("Priority")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFiles_Show_Grid + "//dl/dd[9]"), "Text", "staging"));
            fileImportStatus = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFiles_Show_Grid + "//dl/dd[10]"), "Text");
            Assert.assertTrue(fileImportStatus.contains("Imported") || fileImportStatus.contains("Uploaded"));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateMasterSupplierFilesData()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateMasterSupplierFilesData()' method. " + e);
            return false;
        } finally {fileName = null;}
    }



    /********************************************************
     * Method Name      : createAndValidateMasterSupplierFile()
     * Purpose          : user creates the new Master Supplier Files from Modules-> Import-> Master Supplier Files
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean createAndValidateMasterSupplierFile(WebDriver oBrowser, DataTable dataTable) {
        List<Map<String, String>> masterSupplierFileData = null;
        Map<String, String> masterSupplierFile = null;
        String timeStamp;
        try {
            masterSupplierFileData = dataTable.asMaps(String.class, String.class);
            masterSupplierFile = new HashMap<>();
            timeStamp = appInd.getDateTime("Shhmmss");
            masterSupplierFile.put("Name", masterSupplierFileData.get(0).get("Name") + timeStamp);
            newMasterSupplierFile = masterSupplierFile.get("Name");
            masterSupplierFile.put("Company", masterSupplierFileData.get(0).get("Company"));
            masterSupplierFile.put("Wave", masterSupplierFileData.get(0).get("Wave"));
            masterSupplierFile.put("Version", masterSupplierFileData.get(0).get("Version"));
            masterSupplierFile.put("Status", masterSupplierFileData.get(0).get("Status"));
            masterSupplierFile.put("Priority", masterSupplierFileData.get(0).get("Priority"));
            masterSupplierFile.put("FileToUpload", masterSupplierFileData.get(0).get("FileToUpload"));
            masterSupplierFileName = masterSupplierFile.get("FileToUpload");
            masterSupplierFile.put("OverrideHistoricalValues", masterSupplierFileData.get(0).get("OverrideHistoricalValues"));

            masterSupplierFile = createMasterSupplierFile(oBrowser, masterSupplierFile);
            Assert.assertTrue(masterSupplierFile.get("TestPassed").equalsIgnoreCase("True"), "The 'createMasterSupplierFile()' method was failed.");
            Assert.assertTrue(validateMasterSupplierFilesData(oBrowser, "New", masterSupplierFile));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createAndValidateMasterSupplierFile()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createAndValidateMasterSupplierFile()' method. " + e);
            return false;
        } finally {
            masterSupplierFileData = null; masterSupplierFile = null; timeStamp = null;
        }
    }



    /********************************************************
     * Method Name      : editMasterSupplierFile()
     * Purpose          : user edits the existing Master Supplier Files from Modules-> Import-> Master Supplier Files
     * Author           : Gudi
     * Parameters       : oBrowser, masterSupplierFileData
     * ReturnType       : Map<String, String>
     ********************************************************/
    public Map<String, String> editMasterSupplierFile(WebDriver oBrowser, Map<String, String> masterSupplierFileData) {
        Select oSelect = null;
        try {
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFiles_Edit_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MasterSupplierFiles_Create_Btn, "Clickable", "", waitTimeOut);
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Before Editing 'Master Supplier File'");
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_EditContact_PageTitle, "Text", "Edit Master Supplier File"));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFile_Name_Edit, masterSupplierFileData.get("Name")));

            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFile_Company_List, masterSupplierFileData.get("Company")));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFile_Wave_List), "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFile_Wave_List), 1));
            oSelect = new Select(appInd.createAndGetWebElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFile_Wave_List)));
            masterSupplierFileData.put("Wave", oSelect.getFirstSelectedOption().getText());
            Assert.assertTrue(appInd.setOptionalObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFile_Version_Edit, masterSupplierFileData.get("Version")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFile_Status_List, masterSupplierFileData.get("Status")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFile_Priority_List, masterSupplierFileData.get("Priority")));

            if (masterSupplierFileData.get("OverrideHistoricalValues") != null) {
                if (masterSupplierFileData.get("OverrideHistoricalValues").equalsIgnoreCase("Yes")) {
                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFile_OverrideHistoricalValues_No_Btn));
                }
            }
            reports.writeResult(oBrowser, "Screenshot", "Data was entered for the 'Edit Master Supplier File' before clicking the Submit button");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MasterSupplierFiles_Create_Btn));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ShowEditDetails_Header, "Text", "Information", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MasterSupplierFiles_EditConfirmationMessage_Label, "Text").contains("Master supplier file was successfully updated"));
            reports.writeResult(oBrowser, "Screenshot", "After updating the 'Edit Master Supplier File'");
            masterSupplierFileData.put("TestPassed", "True");
            return masterSupplierFileData;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'editMasterSupplierFile()' method. " + e);
            masterSupplierFileData.put("TestPassed", "False");
            return masterSupplierFileData;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'editMasterSupplierFile()' method. " + e);
            masterSupplierFileData.put("TestPassed", "False");
            return masterSupplierFileData;
        } finally {oSelect = null;}
    }



    /********************************************************
     * Method Name      : validatePaymentBasedEnrollmentData()
     * Purpose          : user validates the payment Based Enrollments data from Modules-> Import-> payment Based Enrollment-> Shows
     * Author           : Gudi
     * Parameters       : oBrowser, paymentBasedEnrollmentStatus, paymentBasedEnrollmentData
     * ReturnType       : boolean
     ********************************************************/
    public boolean validatePaymentBasedEnrollmentData(WebDriver oBrowser, String paymentBasedEnrollmentStatus, Map<String, String> paymentBasedEnrollmentData) {
        String uploadedFileName = null;
        try {
            if (paymentBasedEnrollmentStatus.equalsIgnoreCase("New")) {
                uploadedFileName = paymentBasedEnrollmentData.get("FileToUpload");
            } else {
                if (paymentBasedEnrollmentData.get("FileToUpload") == null)
                    uploadedFileName = fileUploaded;
            }
            reports.writeResult(oBrowser, "Screenshot", "Validating the '" + paymentBasedEnrollmentStatus + "' Payment Based Enrollment data - Show View");
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Show_Grid + "//dl/dd[4]/a[1]"), "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Edit_WaveType_PageTitle, "Text").trim().contains(paymentBasedEnrollmentData.get("Name")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Show_Grid + "//dl/dd[1]"), "Text").trim().equals(appInd.getDateTimeByTimezone("MM/dd/yyyy", "CST")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Show_Grid + "//dl/dd[2]/a[1]"), "Text").trim().equals(paymentBasedEnrollmentData.get("Company")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Show_Grid + "//dl/dd[3]/a[1]"), "Text").trim().equals(paymentBasedEnrollmentData.get("Wave")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Show_Grid + "//dl/dd[4]"), "Text").trim().contains(uploadedFileName));
            String fileImportStatus = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Show_Grid + "//dl/dd[5]"), "Text");
            Assert.assertTrue(fileImportStatus.equals("Imported") || fileImportStatus.contains("In Progress") || fileImportStatus.contains("Aged"));

            Assert.assertTrue(appDep.selectImportsModules(oBrowser, "Payment Based Enrollments"));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Grid + "//td[contains(@aria-label, 'Column Name')]//input"), "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Grid + "//td[contains(@aria-label, 'Column Name')]//input"), paymentBasedEnrollmentData.get("Name")));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CasesGrid_Pagination_Label, "Visibility", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Validating the '" + paymentBasedEnrollmentStatus + "' Payment Based Enrollment data - Grid View");
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Grid + "//td[contains(@aria-label, 'Column Name, Value')]"), "text", paymentBasedEnrollmentData.get("Name")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Grid + "//td[contains(@aria-label, 'Column Company, Value')]"), "text", paymentBasedEnrollmentData.get("Company")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Grid + "//td[contains(@aria-label, 'Column File Name, Value')]"), "text").contains(uploadedFileName));
            Calendar cal = Calendar.getInstance();
            String dateImported = String.valueOf(cal.get(Calendar.MONTH) + 1) + "/" + String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(cal.get(Calendar.YEAR));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Grid + "//td[contains(@aria-label, 'Column Date Imported, Value')]"), "text", dateImported));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Grid + "//td[contains(@aria-label, 'Column Status, Value')]"), "text", paymentBasedEnrollmentData.get("Status")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Grid + "//td[contains(@aria-label, 'Column Priority, Value')]"), "text", paymentBasedEnrollmentData.get("Priority")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Grid + "//td[contains(@aria-label, 'Column File Import Status, Value')]"), "text", "Aged"));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validatePaymentBasedEnrollmentData()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validatePaymentBasedEnrollmentData()' method. " + e);
            return false;
        } finally {uploadedFileName = null;}
    }



    /********************************************************
     * Method Name      : createPaymentBasedEnrollments()
     * Purpose          : user creates new payment Based Enrollment from Modules-> Import-> payment Based Enrollment
     * Author           : Gudi
     * Parameters       : oBrowser, matchFileData
     * ReturnType       : Map<String, String>
     ********************************************************/
    public Map<String, String> createPaymentBasedEnrollments(WebDriver oBrowser, Map<String, String> paymentBasedEnrollmentData) {
        try {
            Assert.assertTrue(appDep.selectImportsModules(oBrowser, "Payment Based Enrollments"));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ProgramManagement_Grid_Pagination, "Visibility", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Add_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Create_Btn, "Clickable", "", waitTimeOut);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_EditContact_PageTitle, "Text", "New Payment Based Enrollment"));


            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Name_Edit, paymentBasedEnrollmentData.get("Name")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Company_List, paymentBasedEnrollmentData.get("Company")));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Wave_List, "Clickable", "", waitTimeOut);
            if (paymentBasedEnrollmentData.get("Wave") != null) {
                Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Wave_List, paymentBasedEnrollmentData.get("Wave")));
            } else {
                Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Wave_List,0));
                appDep.threadSleep(1000);
                paymentBasedEnrollmentData.put("Wave", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Wave_List, "Dropdown"));
            }

            Assert.assertTrue(appInd.setOptionalObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Version_Edit, paymentBasedEnrollmentData.get("Version")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Status_List, paymentBasedEnrollmentData.get("Status")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Priority_List, paymentBasedEnrollmentData.get("Priority")));
            Assert.assertTrue(appInd.setOptionalObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_EmailMunchResultTo_Edit, paymentBasedEnrollmentData.get("EmailMunchResultsTo")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Upload_Edit, System.getProperty("user.dir").replace("\\", "/") + "/TestData/" + paymentBasedEnrollmentData.get("FileToUpload") + ".xlsx"));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Create_Btn, "Clickable", "", waitTimeOut);
            appDep.threadSleep(4000);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_BrowseFile_Edit, "Value").contains(paymentBasedEnrollmentData.get("FileToUpload")));
            reports.writeResult(oBrowser, "Screenshot", "Creating new Payment Based Enrollment");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Create_Btn));
            appDep.threadSleep(4000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Munch_Link, "clickable", "", waitTimeOut);
            appDep.threadSleep(2000);
            paymentBasedEnrollmentData.put("TestPassed", "True");
            return paymentBasedEnrollmentData;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createPaymentBasedEnrollments()' method. " + e);
            paymentBasedEnrollmentData.put("TestPassed", "False");
            return paymentBasedEnrollmentData;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createPaymentBasedEnrollments()' method. " + e);
            paymentBasedEnrollmentData.put("TestPassed", "False");
            return paymentBasedEnrollmentData;
        }
    }



    /********************************************************
     * Method Name      : editPaymentBasedEnrollments()
     * Purpose          : user edits existing payment Based Enrollment from Modules-> Import-> payment Based Enrollment
     * Author           : Gudi
     * Parameters       : oBrowser, matchFileData
     * ReturnType       : Map<String, String>
     ********************************************************/
    public Map<String, String> editPaymentBasedEnrollments(WebDriver oBrowser, Map<String, String> paymentBasedEnrollmentData) {
        try {
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Edit_Link));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_EditContact_PageTitle, "Text", "Edit Payment Based Enrollment"));

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_EditName_Edit, paymentBasedEnrollmentData.get("Name")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_EditCompany_List, paymentBasedEnrollmentData.get("Company")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_EditWave_List, "Clickable", "", waitTimeOut);
            if (paymentBasedEnrollmentData.get("Wave") != null) {
                Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_EditWave_List, paymentBasedEnrollmentData.get("Wave")));
            } else {
                Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_EditWave_List, 1));
                appDep.threadSleep(2000);
                paymentBasedEnrollmentData.put("Wave", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_EditWave_List, "Dropdown"));
            }

            Assert.assertTrue(appInd.setOptionalObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_EditVersion_Edit, paymentBasedEnrollmentData.get("Version")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_EditStatus_List, paymentBasedEnrollmentData.get("Status")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_EditPriority_List, paymentBasedEnrollmentData.get("Priority")));
            Assert.assertTrue(appInd.setOptionalObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_EditEmailMunchResultTo_Edit, paymentBasedEnrollmentData.get("EmailMunchResultsTo")));

            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Create_Btn, "Clickable", "", waitTimeOut);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Show_File, "Text").contains(fileUploaded));
            reports.writeResult(oBrowser, "Screenshot", "Editing the existing Payment Based Enrollment");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Create_Btn));
            appDep.threadSleep(4000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Results_Link, "clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_ConfirmationMessage, "Text").contains("Payment based enrollment was successfully updated"));
            appDep.threadSleep(2000);
            paymentBasedEnrollmentData.put("TestPassed", "True");
            return paymentBasedEnrollmentData;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'editPaymentBasedEnrollments()' method. " + e);
            paymentBasedEnrollmentData.put("TestPassed", "False");
            return paymentBasedEnrollmentData;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'editPaymentBasedEnrollments()' method. " + e);
            paymentBasedEnrollmentData.put("TestPassed", "False");
            return paymentBasedEnrollmentData;
        }
    }



    /********************************************************
     * Method Name      : createReportFile()
     * Purpose          : user creates new Report Files from Modules-> Import-> Report Files
     * Author           : Gudi
     * Parameters       : oBrowser, reportFilesData
     * ReturnType       : Map<String, String>
     ********************************************************/
    public Map<String, String> createReportFile(WebDriver oBrowser, Map<String, String> reportFilesData) {
        try {
            Assert.assertTrue(appDep.selectImportsModules(oBrowser, "Report Files"));
            appDep.threadSleep(2000);
            if(!appInd.verifyOptionalElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ProgramManagement_Grid_Pagination))
                Assert.assertTrue(appDep.selectImportsModules(oBrowser, "Report Files"));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ProgramManagement_Grid_Pagination, "Visibility", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MatchFiles_Add_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MatchFiles_Create_Btn, "Clickable", "", waitTimeOut);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_EditContact_PageTitle, "Text", "New Report File"));

            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ReportFiles_Name_Edit, reportFilesData.get("Name")));

            if (reportFilesData.get("Company") != null) {
                Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ReportFiles_Company_List, reportFilesData.get("Company")));
            } else {
                Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ReportFiles_Company_List, 1));
                appDep.threadSleep(1000);
                reportFilesData.put("Company", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ReportFiles_Company_List, "Dropdown"));
            }

            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ReportFiles_Version_Edit, reportFilesData.get("Version")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ReportFiles_ReportType_List, reportFilesData.get("ReportType")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ReportFiles_Status_List, reportFilesData.get("Status")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ReportFiles_Priority_List, reportFilesData.get("Priority")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ReportFiles_Upload_Edit, System.getProperty("user.dir").replace("\\", "/") + "/TestData/" + reportFilesData.get("FileToUpload") + ".xlsx"));
            reports.writeResult(oBrowser, "Screenshot", "Entered required details for creating Report Files");
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MatchFiles_Create_Btn, "Clickable", "", waitTimeOut);
            appDep.threadSleep(4000);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ReportFiles_FileUploadedName_Label, "Value").contains(reportFilesData.get("FileToUpload")));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MatchFiles_Create_Btn));
            appDep.threadSleep(4000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ShowEditDetails_Header, "Text", "Information", waitTimeOut);
            appDep.waitForTheElementState(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Import_MatchFile_Show_Grid + "/parent::div//dl/dd)[1]"), "Text", reportFilesData.get("Name"), waitTimeOut);
            reportFilesData.put("TestPassed", "True");
            return reportFilesData;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createReportFile()' method. " + e);
            reportFilesData.put("TestPassed", "False");
            return reportFilesData;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createReportFile()' method. " + e);
            reportFilesData.put("TestPassed", "False");
            return reportFilesData;
        }
    }



    /********************************************************
     * Method Name      : validateTheReportFileDetails()
     * Purpose          : user validate the Report Files details from Modules-> Import-> Report Files
     * Author           : Gudi
     * Parameters       : oBrowser, matchFileStatus, matchFileData
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateTheReportFileDetails(WebDriver oBrowser, String reportFileStatus, Map<String, String> reportFileData) {
        String fileName = null;
        String fileImportStatus = null;
        try {
            if (reportFileStatus.equalsIgnoreCase("New")) {
                fileName = reportFileData.get("FileToUpload");
            } else {
                if (reportFileData.get("FileToUpload") != null) {
                    fileName = reportFileData.get("FileToUpload");
                } else {
                    fileName = reportFileName;
                }
            }
            reports.writeResult(oBrowser, "Screenshot", reportFileStatus + " Report Files");
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_ReportFiles_Show_Grid + "//dl/dd[1]"), "Text", reportFileData.get("Name"), waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_ReportFiles_Show_Grid + "//dl/dd[1]"), "Text", reportFileData.get("Name")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_ReportFiles_Show_Grid + "//dl/dd[2]"), "Text").contains(appInd.getDateTimeByTimezone("yyyy-MM-dd", "CST")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_ReportFiles_Show_Grid + "//dl/dd[3]/a[1]"), "Text", reportFileData.get("Company")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_ReportFiles_Show_Grid + "//dl/dd[4]/a[1]"), "Text").contains(fileName));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_ReportFiles_Show_Grid + "//dl/dd[5]"), "Text", reportFileData.get("Version")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_ReportFiles_Show_Grid + "//dl/dd[6]"), "Text", reportFileData.get("ReportType")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_ReportFiles_Show_Grid + "//dl/dd[7]"), "Text", reportFileData.get("Status")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_ReportFiles_Show_Grid + "//dl/dd[8]"), "Text", reportFileData.get("Priority")));
            fileImportStatus = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_ReportFiles_Show_Grid + "//dl/dd[9]"), "Text");
            Assert.assertTrue(fileImportStatus.contains("Imported") || fileImportStatus.contains("Uploaded"));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateTheReportFileDetails()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateTheReportFileDetails()' method. " + e);
            return false;
        } finally {fileName = null;}
    }



    /********************************************************
     * Method Name      : editReportFiles()
     * Purpose          : user edits the Report Files from Modules-> Import-> Report Files
     * Author           : Gudi
     * Parameters       : oBrowser, reportFileData
     * ReturnType       : Map<String, String>
     ********************************************************/
    public Map<String, String> editReportFiles(WebDriver oBrowser, Map<String, String> reportFileData) {
        try {
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MatchFiles_Edit_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MatchFiles_Create_Btn, "Clickable", "", waitTimeOut);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_EditContact_PageTitle, "Text", "Edit Report File"));

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ReportFiles_Name_Edit, reportFileData.get("Name")));

            if (reportFileData.get("Company") != null) {
                Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ReportFiles_Company_List, reportFileData.get("Company")));
            } else {
                Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ReportFiles_Company_List, 1));
                appDep.threadSleep(1000);
                reportFileData.put("Company", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ReportFiles_Company_List, "Dropdown"));
            }

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ReportFiles_Version_Edit, reportFileData.get("Version")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ReportFiles_ReportType_List, reportFileData.get("ReportType")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ReportFiles_Status_List, reportFileData.get("Status")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ReportFiles_Priority_List, reportFileData.get("Priority")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MatchFiles_Create_Btn, "Clickable", "", waitTimeOut);
            appDep.threadSleep(4000);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_FileReports_UploadedFile_Name_Label, "Text").contains(reportFileName));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MatchFiles_Create_Btn));
            appDep.threadSleep(4000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ShowEditDetails_Header, "Text", "Information", waitTimeOut);
            appDep.waitForTheElementState(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Import_MatchFile_Show_Grid + "/parent::div//dl/dd)[1]"), "Text", reportFileData.get("Name"), waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CompanyEditConfirmationMessage_Label, "Text").contains("Report file was successfully updated."));
            reportFileData.put("TestPassed", "True");
            return reportFileData;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'editReportFiles()' method. " + e);
            reportFileData.put("TestPassed", "False");
            return reportFileData;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'editReportFiles()' method. " + e);
            reportFileData.put("TestPassed", "False");
            return reportFileData;
        }
    }



    /********************************************************
     * Method Name      : editAndValidateReportFiles()
     * Purpose          : user edits the Report Files from Modules-> Import-> Report Files
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean editAndValidateReportFiles(WebDriver oBrowser, DataTable dataTable) {
        List<Map<String, String>> reportFileData = null;
        Map<String, String> reportFile = null;
        String timeStamp;
        Calendar cal = null;
        String fileImportStatus = null;
        try {
            reportFileData = dataTable.asMaps(String.class, String.class);
            reportFile = new HashMap<String, String>();
            timeStamp = appInd.getDateTime("Shhmmss");
            reportFile.put("Name", reportFileData.get(0).get("Name") + timeStamp);
            newReportFileName = reportFile.get("Name");
            reportFile.put("Company", reportFileData.get(0).get("Company"));
            reportFile.put("Version", reportFileData.get(0).get("Version"));
            reportFile.put("ReportType", reportFileData.get(0).get("ReportType"));
            reportFile.put("Status", reportFileData.get(0).get("Status"));
            reportFile.put("Priority", reportFileData.get(0).get("Priority"));
            reportFile.put("FileToUpload", reportFileData.get(0).get("FileToUpload"));

            reportFile = editReportFiles(oBrowser, reportFile);
            Assert.assertTrue(reportFile.get("TestPassed").equalsIgnoreCase("True"), "The 'editReportFiles()' method was failed.");
            Assert.assertTrue(validateTheReportFileDetails(oBrowser, "Edit", reportFile));
            Assert.assertTrue(appDep.selectImportsModules(oBrowser, "Report Files"));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ReportFiles_Name_Search_Edit, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ReportFiles_Name_Search_Edit, reportFile.get("Name")));
            appDep.threadSleep(2000);

            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ReportFiles_Grid + "/td)[1]"), "Text", reportFile.get("Name")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ReportFiles_Grid + "/td)[2]"), "Text", reportFile.get("Company")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ReportFiles_Grid + "/td)[3]"), "Text", reportFileName + ".xlsx"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ReportFiles_Grid + "/td)[4]"), "Text", reportFile.get("ReportType")));
            cal = Calendar.getInstance();
            String date[] = appInd.getDateTimeByTimezone("MM/dd/yyyy", "CST").split("/");
            String dateImported = Integer.parseInt(date[0]) + "/" + Integer.parseInt(date[1]) + "/" + Integer.parseInt(date[2]);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ReportFiles_Grid + "/td)[5]"), "Text", dateImported));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ReportFiles_Grid + "/td)[6]"), "Text", reportFile.get("Status")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ReportFiles_Grid + "/td)[7]"), "Text", reportFile.get("Priority")));
            fileImportStatus = appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ReportFiles_Grid + "/td)[8]"), "Text");
            Assert.assertTrue(fileImportStatus.contains("Imported") || fileImportStatus.contains("Uploaded"));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'editAndValidateMatchFiles()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'editAndValidateMatchFiles()' method. " + e);
            return false;
        } finally {
            reportFileData = null; reportFile = null; timeStamp = null; cal = null;
        }
    }



    /********************************************************
     * Method Name      : createAndValidateTheNewReportFiles()
     * Purpose          : user creates new Report files and validates the same from Modules-->Imports-->Report Files
     * Author           : Gudi
     * Parameters       : oBrowser, duplicateSupplierTabName, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean createAndValidateTheNewReportFiles(WebDriver oBrowser, DataTable dataTable) {
        List<Map<String, String>> reportFileData = null;
        String timeStamp = null;
        Map<String, String> reportFile = null;
        try {
            reportFileData = dataTable.asMaps(String.class, String.class);
            timeStamp = appInd.getDateTime("Shhmmss");
            reportFile = new HashMap<String, String>();
            reportFile.put("Name", reportFileData.get(0).get("Name") + timeStamp);
            reportFile.put("Company", reportFileData.get(0).get("Company"));
            reportFile.put("Version", reportFileData.get(0).get("Version"));
            reportFile.put("ReportType", reportFileData.get(0).get("ReportType"));
            reportFile.put("Status", reportFileData.get(0).get("Status"));
            reportFile.put("Priority", reportFileData.get(0).get("Priority"));
            reportFile.put("FileToUpload", reportFileData.get(0).get("FileToUpload"));
            reportFileName = reportFile.get("FileToUpload");
            reportFile = createReportFile(oBrowser, reportFile);
            Assert.assertTrue(reportFile.get("TestPassed").equalsIgnoreCase("True"), "The createReportFile() method was failed");
            Assert.assertTrue(validateTheReportFileDetails(oBrowser, "New", reportFile));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createAndValidateTheNewReportFiles()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createAndValidateTheNewReportFiles()' method. " + e);
            return false;
        } finally {
            reportFileData = null; timeStamp = null; reportFile = null;
        }
    }



    /********************************************************
     * Method Name      : paymentBasedEnrollmentsFilterFunctionality()
     * Purpose          : user validate filter functionality for Payment Based Enrollment grid data
     * Author           : Gudi
     * Parameters       : oBrowser, columnName
     * ReturnType       : boolean
     ********************************************************/
    public boolean paymentBasedEnrollmentsFilterFunctionality(WebDriver oBrowser, String columnName) {
        String companyName = null;
        String status = null;
        String priority = null;
        String importStatus = null;
        String dateImported = null;
        String dataToValidate = null;
        try {
            Assert.assertTrue(appDep.selectImportsModules(oBrowser, "Payment Based Enrollments"));
            appDep.threadSleep(2000);
            switch (columnName.toLowerCase()) {
                case "company":
                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_CompanyFilter_Btn));
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_FilterOptions_Label, "Clickable", "", waitTimeOut);
                    companyName = appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_FilterOptions_Label, "Text");
                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_FilterOptions_Label));
                    reports.writeResult(oBrowser, "Screenshot", "Applying the filter for 'Company' column");
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Filter_OK_Btn)));
                    appDep.threadSleep(2000);
                    break;
                case "status":
                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_StatusFilter_Btn));
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_FilterOptions_Label, "Clickable", "", waitTimeOut);
                    status = appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_FilterOptions_Label, "Text");
                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_FilterOptions_Label));
                    reports.writeResult(oBrowser, "Screenshot", "Applying the filter for 'Status' column");
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Filter_OK_Btn)));
                    appDep.threadSleep(2000);
                    break;
                case "priority":
                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_PriorityFilter_Btn));
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_FilterOptions_Label, "Clickable", "", waitTimeOut);
                    priority = appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_FilterOptions_Label, "Text");
                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_FilterOptions_Label));
                    reports.writeResult(oBrowser, "Screenshot", "Applying the filter for 'Priority' column");
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Filter_OK_Btn)));
                    appDep.threadSleep(2000);
                    break;
                case "date imported":
                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_DateImportFilter_Btn));
                    appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Filter_OK_Btn), "Clickable", "", waitTimeOut);
                    String year = appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_FilterOptions_DateImported_List + "//div[contains(@class, 'dx-treeview-item-content')])[1]"), "Text");
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_FilterOptions_DateImported_List + "//div[contains(@class, 'dx-treeview-item-content')]/parent::div/following-sibling::div")));
                    String month = appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_FilterOptions_DateImported_List + "//div[contains(@class, 'dx-treeview-item-content')])[2]"), "Text");
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_FilterOptions_DateImported_List + "//div[contains(@class, 'dx-treeview-item-content')])[2]/parent::div/following-sibling::div")));
                    String days = appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_FilterOptions_DateImported_List + "//div[contains(@class, 'dx-treeview-item-content')])[3]"), "Text");
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_FilterOptions_DateImported_List + "//div[contains(@class, 'dx-treeview-item-content')])[3]/parent::div/preceding-sibling::div")));
                    reports.writeResult(oBrowser, "Screenshot", "Applying the filter for 'Date Imported' column");
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Filter_OK_Btn)));
                    appDep.threadSleep(2000);
                    dateImported = appInd.getMonthNumber(month) + "/" + days + "/" + year;
                    break;
                case "import status":
                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_FileImportStatusFilter_Btn));
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_FilterOptions_Label, "Clickable", "", waitTimeOut);
                    importStatus = appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_FilterOptions_Label, "Text");
                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_FilterOptions_Label));
                    reports.writeResult(oBrowser, "Screenshot", "Applying the filter for 'Import Status' column");
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Filter_OK_Btn)));
                    appDep.threadSleep(2000);
                    break;
                default:
                    reports.writeResult(oBrowser, "Fail", "Invalid column name '" + columnName + "' for filter");
                    return false;
            }
            int index = 0;
            switch (columnName.toLowerCase()) {
                case "company":
                    dataToValidate = companyName;
                    index = 2;
                    break;
                case "status":
                    dataToValidate = status;
                    index = 6;
                    break;
                case "priority":
                    dataToValidate = priority;
                    index = 7;
                    break;
                case "date imported":
                    dataToValidate = dateImported;
                    index = 5;
                    break;
                case "import status":
                    dataToValidate = importStatus;
                    index = 8;
            }
            int maxRowNum = 18;
            int rowNum = oBrowser.findElements(By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Grid + "//div[@class='dx-datagrid-content']/table//tr")).size() - 1;
            if (rowNum > maxRowNum) rowNum = maxRowNum;

            for (int i = 0; i < rowNum; i++) {
                String cellData = appInd.createAndGetWebElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Grid + "//div[@class='dx-datagrid-content']/table//tr[" + (i + 1) + "]/td[" + index + "]")).getText();
                Assert.assertTrue(appInd.compareValues(oBrowser, cellData, dataToValidate), "Mis-match in actual +'" + cellData + "' & expected value + '" + dataToValidate + "' in Paymnet Based Enrollment Grid filtered values");
            }
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'paymentBasedEnrollmentsFilterFunctionality()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'paymentBasedEnrollmentsFilterFunctionality()' method. " + e);
            return false;
        } finally {
            companyName = null; status = null; priority = null; importStatus = null; dateImported = null; dataToValidate = null;
        }
    }



    /********************************************************
     * Method Name      : validateFilterAndPaymentBasedEnrollmentDetails()
     * Purpose          : user perform filter & PaymentBasedEnrollments data validation from Modules-> Import-> Payment Based Enrollments & validates the same
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateFilterAndPaymentBasedEnrollmentDetails(WebDriver oBrowser) {
        File objFile = null;
        File objArrFiles[];
        boolean blnRes = false;
        FileInputStream fin = null;
        Workbook wb = null;
        Sheet sh = null;
        try {
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Show_Link));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Results_Link, "clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Edit_WaveType_PageTitle, "Text").trim().contains(paymentBasedEnrollmentName));
            String companyName = appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Show_CompanyLink, "Text");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Show_CompanyLink));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_Show_Page, "Visibility", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Validate the data in the Company Details page");
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_Show_HeaderText, "Text").trim().equals(companyName));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_CompanyName_Label, "Text").trim().contains(companyName));
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Show_WaveLink, "Clickable", "", waitTimeOut);
            String waveName = appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Show_WaveLink, "Text");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Show_WaveLink));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_WaveAndOffers_Information), "Text", "Information", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Validate the data in the Wave Details page");
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GoToWave_PageTitle, "Text").contains(waveName));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_CompanyName_Label, "Text").trim().contains(waveName));
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
            appDep.waitForTheElementState(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Show_FileNameDownloadLink + "/following-sibling::dd/a)[1]"), "Clickable", "", waitTimeOut);
            String fileName = appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Show_FileNameDownloadLink + "/following-sibling::dd)[1]"), "Text");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Show_FileNameDownloadLink + "/following-sibling::dd/a)[1]")));
            appDep.threadSleep(5000);
            objFile = new File("C:/Users/" + System.getProperty("user.name") + "/Downloads");
            objArrFiles = objFile.listFiles();
            blnRes = false;
            for (int i = 0; i < objArrFiles.length; i++) {
                if (objArrFiles[i].isFile()) {
                    String filePath = objArrFiles[i].getPath();
                    if (filePath.contains(".xlsx")) {
                        fin = new FileInputStream(filePath);
                        wb = new XSSFWorkbook(fin);
                        sh = wb.getSheetAt(0);
                        String sheetName = sh.getSheetName();
                        fin.close();
                        fin = null;
                        sh = null;
                        wb.close();
                        wb = null;
                        if (sheetName.equalsIgnoreCase("PaymentBasedEnrollments")) {
                            blnRes = true;
                            boolean res = objArrFiles[i].delete();
                            break;
                        }
                    }
                }
            }
            objFile = null;
            objArrFiles = null;


            if (blnRes) {
                reports.writeResult(oBrowser, "Pass", "The 'Payment Based Enrollment' excel file was downloded successful");
            } else {
                reports.writeResult(oBrowser, "Fail", "Failed to download the 'Payment Based Enrollment' excel file");
                Assert.assertTrue(false, "Failed to download the 'Payment Based Enrollment' xlsx file");
            }

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_ExportAllData_Btn));
            appDep.threadSleep(5000);

            blnRes = appDep.validateDownloadedFileAndDelete(oBrowser, companyName + "-" + "PaymentBasedInrollment", ".xlsx", "", "Yes");

            if (blnRes) {
                reports.writeResult(oBrowser, "Pass", "The 'Export All Data' excel file was downloded successful");
            } else {
                reports.writeResult(oBrowser, "Fail", "Failed to download the 'Export All Data' excel file");
                Assert.assertTrue(false, "Failed to download the 'Export All Data' xlsx file");
            }


            Assert.assertTrue(paymentBasedEnrollmentsFilterFunctionality(oBrowser, "Company"));
            Assert.assertTrue(paymentBasedEnrollmentsFilterFunctionality(oBrowser, "Status"));
            Assert.assertTrue(paymentBasedEnrollmentsFilterFunctionality(oBrowser, "Priority"));
            Assert.assertTrue(paymentBasedEnrollmentsFilterFunctionality(oBrowser, "Date Imported"));
            Assert.assertTrue(paymentBasedEnrollmentsFilterFunctionality(oBrowser, "Import Status"));

            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateFilterAndPaymentBasedEnrollmentDetails()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateFilterAndPaymentBasedEnrollmentDetails()' method. " + e);
            return false;
        } finally {objArrFiles = null; objFile = null;}
    }



    /********************************************************
     * Method Name      : editAndValidatePaymentBasedEnrollments()
     * Purpose          : user edit the existing PaymentBasedEnrollments from Modules-> Import-> Payment Based Enrollments & validates the same
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean editAndValidatePaymentBasedEnrollments(WebDriver oBrowser, DataTable dataTable) {
        List<Map<String, String>> paymentBasedEnrollmentData = null;
        Map<String, String> editPaymentBasedEnrollment = null;
        String timeStamp;
        try {
            paymentBasedEnrollmentData = dataTable.asMaps(String.class, String.class);
            editPaymentBasedEnrollment = new HashMap<>();
            timeStamp = appInd.getDateTime("Shhmmss");
            editPaymentBasedEnrollment.put("Name", paymentBasedEnrollmentData.get(0).get("Name") + timeStamp);
            paymentBasedEnrollmentName = editPaymentBasedEnrollment.get("Name");
            editPaymentBasedEnrollment.put("Company", paymentBasedEnrollmentData.get(0).get("Company"));
            editPaymentBasedEnrollment.put("Wave", paymentBasedEnrollmentData.get(0).get("Wave"));
            editPaymentBasedEnrollment.put("Version", paymentBasedEnrollmentData.get(0).get("Version"));
            editPaymentBasedEnrollment.put("Status", paymentBasedEnrollmentData.get(0).get("Status"));
            editPaymentBasedEnrollment.put("Priority", paymentBasedEnrollmentData.get(0).get("Priority"));
            editPaymentBasedEnrollment.put("EmailMunchResultsTo", paymentBasedEnrollmentData.get(0).get("EmailMunchResultsTo"));
            editPaymentBasedEnrollment.put("FileToUpload", paymentBasedEnrollmentData.get(0).get("FileToUpload"));

            editPaymentBasedEnrollment = editPaymentBasedEnrollments(oBrowser, editPaymentBasedEnrollment);
            Assert.assertTrue(editPaymentBasedEnrollment.get("TestPassed").equalsIgnoreCase("True"), "The 'editPaymentBasedEnrollments()' method was failed.");
            Assert.assertTrue(validatePaymentBasedEnrollmentData(oBrowser, "Edit", editPaymentBasedEnrollment));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'editAndValidatePaymentBasedEnrollments()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'editAndValidatePaymentBasedEnrollments()' method. " + e);
            return false;
        } finally {
            paymentBasedEnrollmentData = null; editPaymentBasedEnrollment = null; timeStamp = null;
        }
    }



    /********************************************************
     * Method Name      : createAndValidatePaymentBasedEnrollments()
     * Purpose          : user creates the new PaymentBasedEnrollments from Modules-> Import-> Payment Based Enrollments & validates the same
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean createAndValidatePaymentBasedEnrollments(WebDriver oBrowser, DataTable dataTable) {
        List<Map<String, String>> paymentBasedEnrollmentData = null;
        Map<String, String> paymentBasedEnrollment = null;
        String timeStamp;
        try {
            paymentBasedEnrollmentData = dataTable.asMaps(String.class, String.class);
            paymentBasedEnrollment = new HashMap<String, String>();
            timeStamp = appInd.getDateTime("Shhmmss");
            paymentBasedEnrollment.put("Name", paymentBasedEnrollmentData.get(0).get("Name") + timeStamp);
            paymentBasedEnrollment.put("Company", paymentBasedEnrollmentData.get(0).get("Company"));
            paymentBasedEnrollment.put("Wave", paymentBasedEnrollmentData.get(0).get("Wave"));
            paymentBasedEnrollment.put("Version", paymentBasedEnrollmentData.get(0).get("Version"));
            paymentBasedEnrollment.put("Status", paymentBasedEnrollmentData.get(0).get("Status"));
            paymentBasedEnrollment.put("Priority", paymentBasedEnrollmentData.get(0).get("Priority"));
            paymentBasedEnrollment.put("EmailMunchResultsTo", paymentBasedEnrollmentData.get(0).get("EmailMunchResultsTo"));
            paymentBasedEnrollment.put("FileToUpload", paymentBasedEnrollmentData.get(0).get("FileToUpload"));
            fileUploaded = paymentBasedEnrollment.get("FileToUpload");

            paymentBasedEnrollment = createPaymentBasedEnrollments(oBrowser, paymentBasedEnrollment);
            Assert.assertTrue(paymentBasedEnrollment.get("TestPassed").equalsIgnoreCase("True"), "The 'createPaymentBasedEnrollment()' method was failed.");

            //click on the Munch button
            reports.writeResult(oBrowser, "Screenshot", "Before clicking 'Munch' button");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Munch_Btn), "Failed to find the 'Munch' button");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Munch_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", minTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "After clicking 'Munch' button");
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Suppliers_Show_SuppliersName_Label +"//div[@class='alert alert-success custom-alert-info']"), "Text").contains("File Munch Complete!"));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Show_Grid +"//label)[1]"), "Text").contains("Cases Updated"));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Show_Grid +"//label)[2]"), "Text").contains("Cases Created"));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Show_Grid +"//label)[3]"), "Text").contains("Statuses"));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Show_Grid +"//label)[4]"), "Text").contains("EXCLUDED - Due Date before"));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Show_Grid +"//label)[5]"), "Text").contains("Amount less than"));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Show_Grid +"//label)[6]"), "Text").contains("Case Ignored:"));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Show_Grid +"//label)[7]"), "Text").contains("Supplier Inrolled:"));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Show_Grid +"//label)[8]"), "Text").contains("Errors"));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Show_Grid +"//label)[9]"), "Text").contains("Company Suppliers Inactive:"));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Show_Grid +"//label)[10]"), "Text").contains("Multiple Company Suppliers Found:"));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_PaymentBasedEnrollments_Show_Grid +"//label)[11]"), "Text").contains("Placeholder Supplier:"));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Results_Link));

            Assert.assertTrue(validatePaymentBasedEnrollmentData(oBrowser, "New", paymentBasedEnrollment));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createAndValidatePaymentBasedEnrollments()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createAndValidatePaymentBasedEnrollments()' method. " + e);
            return false;
        } finally {
            paymentBasedEnrollmentData = null; paymentBasedEnrollment = null; timeStamp = null;
        }
    }



    /********************************************************
     * Method Name      : editAndValidateMasterSupplierFile()
     * Purpose          : user edits the new Master Supplier Files from Modules-> Import-> Master Supplier Files
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean editAndValidateMasterSupplierFile(WebDriver oBrowser, DataTable dataTable) {
        List<Map<String, String>> masterSupplierFileData = null;
        Map<String, String> masterSupplierFile = null;
        String timeStamp;
        try {
            masterSupplierFileData = dataTable.asMaps(String.class, String.class);
            masterSupplierFile = new HashMap<String, String>();
            timeStamp = appInd.getDateTime("Shhmmss");
            masterSupplierFile.put("Name", masterSupplierFileData.get(0).get("Name") + timeStamp);
            updateMasterSupplierFile = masterSupplierFile.get("Name");
            masterSupplierFile.put("Company", masterSupplierFileData.get(0).get("Company"));
            masterSupplierFile.put("Wave", masterSupplierFileData.get(0).get("Wave"));
            masterSupplierFile.put("Version", masterSupplierFileData.get(0).get("Version"));
            masterSupplierFile.put("Status", masterSupplierFileData.get(0).get("Status"));
            masterSupplierFile.put("Priority", masterSupplierFileData.get(0).get("Priority"));
            masterSupplierFile.put("FileToUpload", masterSupplierFileData.get(0).get("FileToUpload"));
            masterSupplierFile.put("OverrideHistoricalValues", masterSupplierFileData.get(0).get("OverrideHistoricalValues"));

            masterSupplierFile = editMasterSupplierFile(oBrowser, masterSupplierFile);
            Assert.assertTrue(masterSupplierFile.get("TestPassed").equalsIgnoreCase("True"), "The 'editMasterSupplierFile()' method was failed.");
            Assert.assertTrue(validateMasterSupplierFilesData(oBrowser, "Edit", masterSupplierFile));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'editAndValidateMasterSupplierFile()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'editAndValidateMasterSupplierFile()' method. " + e);
            return false;
        } finally {
            masterSupplierFileData = null; masterSupplierFile = null; timeStamp = null;
        }
    }



    /********************************************************
     * Method Name      : createMatchFile()
     * Purpose          : user creates new Match Files from Modules-> Import-> Match Files
     * Author           : Gudi
     * Parameters       : oBrowser, matchFileData
     * ReturnType       : Map<String, String>
     ********************************************************/
    public Map<String, String> createMatchFile(WebDriver oBrowser, Map<String, String> matchFileData) {
        try {
            Assert.assertTrue(appDep.selectImportsModules(oBrowser, "Match Files"));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ProgramManagement_Grid_Pagination, "Visibility", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MatchFiles_Add_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MatchFiles_Create_Btn, "Clickable", "", waitTimeOut);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_EditContact_PageTitle, "Text", "New Match File"));

            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MatchFiles_Name_Edit, matchFileData.get("Name")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MatchFiles_Company_List, matchFileData.get("Company")));
            if (matchFileData.get("MasterSupplierFile") != null) {
                Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MatchFiles_MasterSupplierFiles_Edit, matchFileData.get("MasterSupplierFile")));
            } else {
                Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MatchFiles_MasterSupplierFiles_Edit, 1));
                appDep.threadSleep(1000);
                matchFileData.put("MasterSupplierFile", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MatchFiles_MasterSupplierFiles_Edit, "Dropdown"));
            }

            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MatchFiles_Version_Edit, matchFileData.get("Version")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MatchFiles_Status_List, matchFileData.get("Status")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MatchFiles_Priority_List, matchFileData.get("Priority")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MatchFile_Upload_Edit, System.getProperty("user.dir").replace("\\", "/") + "/TestData/" + matchFileData.get("FileToUpload") + ".xlsx"));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MatchFiles_Create_Btn, "Clickable", "", waitTimeOut);
            appDep.threadSleep(4000);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MatchFiles_BrowseFile_Edit, "Value").contains(matchFileData.get("FileToUpload")));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MatchFiles_Create_Btn));
            appDep.threadSleep(4000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ShowEditDetails_Header, "Text", "Information", waitTimeOut);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Import_MatchFile_Show_Grid + "//dl/dd[1]"), "Text", matchFileData.get("Name"), waitTimeOut);
            matchFileData.put("TestPassed", "True");
            return matchFileData;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createMatchFile()' method. " + e);
            matchFileData.put("TestPassed", "False");
            return matchFileData;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createMatchFile()' method. " + e);
            matchFileData.put("TestPassed", "False");
            return matchFileData;
        }
    }



    /********************************************************
     * Method Name      : createAndValidateMatchFiles()
     * Purpose          : user creates new Match Files from Modules-> Import-> Match Files
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean createAndValidateMatchFiles(WebDriver oBrowser, DataTable dataTable) {
        List<Map<String, String>> matchFileData = null;
        Map<String, String> matchFile = null;
        String timeStamp = null;
        try {
            matchFileData = dataTable.asMaps(String.class, String.class);
            matchFile = new HashMap<String, String>();
            timeStamp = appInd.getDateTime("Shhmmss");
            matchFile.put("Name", matchFileData.get(0).get("Name") + timeStamp);
            newMatchFileName = matchFile.get("Name");
            matchFile.put("Company", matchFileData.get(0).get("Company"));
            matchFile.put("Wave", matchFileData.get(0).get("MasterSupplierFile"));
            matchFile.put("Version", matchFileData.get(0).get("Version"));
            matchFile.put("Status", matchFileData.get(0).get("Status"));
            matchFile.put("Priority", matchFileData.get(0).get("Priority"));
            matchFile.put("FileToUpload", matchFileData.get(0).get("FileToUpload"));
            matchFileName = matchFile.get("FileToUpload");

            matchFile = createMatchFile(oBrowser, matchFile);
            Assert.assertTrue(matchFile.get("TestPassed").equalsIgnoreCase("True"), "The 'createMatchFile()' method was failed.");
            Assert.assertTrue(validateMatchFileData(oBrowser, "New", matchFile));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createAndValidateMatchFiles()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createAndValidateMatchFiles()' method. " + e);
            return false;
        } finally {matchFileData = null; matchFile = null; timeStamp = null;}
    }



    /********************************************************
     * Method Name      : validateMatchFileData()
     * Purpose          : user validate the Match Files details from Modules-> Import-> Match Files
     * Author           : Gudi
     * Parameters       : oBrowser, matchFileStatus, matchFileData
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateMatchFileData(WebDriver oBrowser, String matchFileStatus, Map<String, String> matchFileData) {
        String fileName = null;
        String fileImportStatus = null;
        try {
            if (matchFileStatus.equalsIgnoreCase("New")) {
                fileName = matchFileData.get("FileToUpload");
            } else {
                if (matchFileData.get("FileToUpload") != null) {
                    fileName = matchFileData.get("FileToUpload");
                } else {
                    fileName = matchFileName;
                }
            }

            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFiles_Show_Grid + "//dl/dd[1]"), "Text", matchFileData.get("Name")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFiles_Show_Grid + "//dl/dd[2]"), "Text").contains(appInd.getDateTimeByTimezone("yyyy-MM-dd", "CST")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFiles_Show_Grid + "//dl/dd[3]/a"), "Text", matchFileData.get("Company")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFiles_Show_Grid + "//dl/dd[4]/a"), "Text").contains(fileName));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFiles_Show_Grid + "//dl/dd[5]"), "Text", matchFileData.get("Version")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFiles_Show_Grid + "//dl/dd[6]"), "Text", matchFileData.get("Status")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFiles_Show_Grid + "//dl/dd[7]"), "Text", matchFileData.get("Priority")));
            fileImportStatus = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFiles_Show_Grid + "//dl/dd[8]"), "Text");
            Assert.assertTrue(fileImportStatus.contains("Imported") || fileImportStatus.contains("Uploaded"));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateMatchFileData()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateMatchFileData()' method. " + e);
            return false;
        } finally {fileName = null;}
    }



    /********************************************************
     * Method Name      : editMatchFile()
     * Purpose          : user edits the Match Files from Modules-> Import-> Match Files
     * Author           : Gudi
     * Parameters       : oBrowser, matchFileData
     * ReturnType       : Map<String, String>
     ********************************************************/
    public Map<String, String> editMatchFile(WebDriver oBrowser, Map<String, String> matchFileData) {
        try {
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MatchFiles_Edit_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MatchFiles_Create_Btn, "Clickable", "", waitTimeOut);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_EditContact_PageTitle, "Text", "Edit Match File"));

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MatchFiles_Name_Edit, matchFileData.get("Name")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MatchFiles_Company_List, matchFileData.get("Company")));
            if (matchFileData.get("MasterSupplierFile") != null) {
                Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MatchFiles_MasterSupplierFiles_Edit, matchFileData.get("MasterSupplierFile")));
            } else {
                Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MatchFiles_MasterSupplierFiles_Edit, 1));
                appDep.threadSleep(1000);
                matchFileData.put("MasterSupplierFile", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MatchFiles_MasterSupplierFiles_Edit, "Dropdown"));
            }

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MatchFiles_Version_Edit, matchFileData.get("Version")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MatchFiles_Status_List, matchFileData.get("Status")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MatchFiles_Priority_List, matchFileData.get("Priority")));

            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MatchFiles_File_Label, "Text").contains(matchFileName));

            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MatchFiles_Create_Btn, "Clickable", "", waitTimeOut);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MasterSupplierFiles_Create_Btn));
            appDep.threadSleep(4000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ShowEditDetails_Header, "Text", "Information", waitTimeOut);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Import_MatchFile_Show_Grid + "//dl/dd[1]"), "Text", matchFileData.get("Name"), waitTimeOut);
            matchFileData.put("TestPassed", "True");
            return matchFileData;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'editMatchFile()' method. " + e);
            matchFileData.put("TestPassed", "False");
            return matchFileData;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'editMatchFile()' method. " + e);
            matchFileData.put("TestPassed", "False");
            return matchFileData;
        }
    }



    /********************************************************
     * Method Name      : editAndValidateMatchFiles()
     * Purpose          : user edits the new Master Supplier Files from Modules-> Import-> Master Supplier Files
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean editAndValidateMatchFiles(WebDriver oBrowser, DataTable dataTable) {
        List<Map<String, String>> matchFileData = null;
        Map<String, String> matchFile = null;
        String timeStamp;
        try {
            matchFileData = dataTable.asMaps(String.class, String.class);
            matchFile = new HashMap<String, String>();
            timeStamp = appInd.getDateTime("Shhmmss");
            matchFile.put("Name", matchFileData.get(0).get("Name") + timeStamp);
            matchFile.put("Company", matchFileData.get(0).get("Company"));
            matchFile.put("Wave", matchFileData.get(0).get("MasterSupplierFile"));
            matchFile.put("Version", matchFileData.get(0).get("Version"));
            matchFile.put("Status", matchFileData.get(0).get("Status"));
            matchFile.put("Priority", matchFileData.get(0).get("Priority"));
            matchFile.put("FileToUpload", matchFileData.get(0).get("FileToUpload"));

            matchFile = editMatchFile(oBrowser, matchFile);
            Assert.assertTrue(matchFile.get("TestPassed").equalsIgnoreCase("True"), "The 'editMatchFile()' method was failed.");
            Assert.assertTrue(validateMatchFileData(oBrowser, "Edit", matchFile));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'editAndValidateMatchFiles()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'editAndValidateMatchFiles()' method. " + e);
            return false;
        } finally {matchFileData = null; matchFile = null; timeStamp = null;}
    }



    /********************************************************
     * Method Name      : downloadMasterSupplierFile()
     * Purpose          : user downloads the Master Supplier Files from Modules-> Import-> Master Supplier Files
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean downloadMasterSupplierFile(WebDriver oBrowser) {
        Workbook wb = null;
        Sheet sh = null;
        FileInputStream fin = null;
        File objFile = null;
        File objArrFiles[];
        try {
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_FileName_Hyperlink));
            appDep.threadSleep(5000);
            objFile = new File("C:/Users/" + System.getProperty("user.name") + "/Downloads");
            objArrFiles = objFile.listFiles();
            boolean blnRes = false;
            for (int i = 0; i < objArrFiles.length; i++) {
                if (objArrFiles[i].isFile()) {
                    String filePath = objArrFiles[i].getPath();
                    if (filePath.contains(".xlsx")) {
                        fin = new FileInputStream(filePath);
                        wb = new XSSFWorkbook(fin);
                        sh = wb.getSheetAt(0);
                        String sheetName = sh.getSheetName();
                        fin.close();
                        fin = null;
                        sh = null;
                        wb.close();
                        wb = null;
                        if (sheetName.equalsIgnoreCase("MasterSupplierFileOne")) {
                            blnRes = true;
                            boolean res = objArrFiles[i].delete();
                            break;
                        }
                    }
                }
            }

            if (blnRes) {
                reports.writeResult(oBrowser, "Pass", "The 'Master Supplier file' was downloaded successful");
            } else {
                reports.writeResult(oBrowser, "Fail", "Failed to download the 'Master Supplier file'");
                Assert.assertTrue(false, "Failed to download the 'Master Supplier file'");
            }
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'downloadMasterSupplierFile()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'downloadMasterSupplierFile()' method. " + e);
            return false;
        } finally {
            try {
                objFile = null;
                objArrFiles = null;
            } catch (Exception e) {
            }
        }
    }
}
