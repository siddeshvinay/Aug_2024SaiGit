package com.corcentric.baseSteps.ui;

import com.corcentric.pages.*;
import com.corcentric.utiles.GridRecordAction;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PayCRM_Modules_GeneralUIBaseStep extends CommonUIBase {
    private String companyName;
    private String editCompanyName;
    private String newContactName;
    private String editContactName;
    private String queueName;
    private String caseNumber;
    private String supplierAttachmentFileName;
    private String otherAttachment;
    private String otherAttachmentName;

    /********************************************************
     * Method Name      : actionOnTheFirstRowOfTheGrid()
     * Purpose          : to verify user can click on the edit link of the first row of the Queues table grid
     * Author           : Gudi
     * Parameters       : oBrowser, gridRecordAction
     * ReturnType       : boolean
     ********************************************************/
    public boolean actionOnTheFirstRowOfTheGrid(WebDriver oBrowser, String gridRecordAction) {
        try {
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Queues_Grid_FirstRow, "Visibility", "", waitTimeOut);
            queueName = appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Queues_Grid_FirstRow, "Text");
            String action = String.format(PayCRM_Modules_GeneralUIPage.obj_Grid_First_Row_Action_Btn, gridRecordAction);
            if (gridRecordAction.equals(GridRecordAction.VIEW.getValue())) {
                int openWin = appInd.getTotalOpenWindows(oBrowser);
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Queues_Grid_FirstRow), "clickObject() was failed for the '" + PayCRM_Modules_GeneralUIPage.obj_Queues_Grid_FirstRow + "' webelement");
                appDep.threadSleep(4000);
                if (appInd.isSwitchingRequired(oBrowser, openWin)) {
                    oBrowser = appInd.switchBetweenTabsOrWindow(oBrowser);
                    String pageTitle = String.format(PayCRM_Modules_GeneralUIPage.obj_Page_Heading, queueName);
                    appDep.waitForTheElementState(oBrowser, By.xpath(pageTitle), "Visibility", "", waitTimeOut);
                }
            } else if (gridRecordAction.equals(GridRecordAction.EDIT.getValue())) {
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(action)), "clickObject() was failed for the '" + action + "' webelement");
            } else if (gridRecordAction.equals(GridRecordAction.VIEW_COMPANY_SUPPLIER.getValue())) {
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(action)), "clickObject() was failed for the '" + action + "' webelement");
            } else if (gridRecordAction.equals(GridRecordAction.OPEN_IN_NEW_TAB.getValue())) {
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(action)), "clickObject() was failed for the '" + action + "' webelement");
            } else if (gridRecordAction.equals(GridRecordAction.LOGIN_TO_PAYNET.getValue())) {
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(action)), "clickObject() was failed for the '" + action + "' webelement");
            } else {
                Assert.assertTrue(false, "Given grid operation is not valid operation.");
            }
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'actionOnTheFirstRowOfTheGrid()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'actionOnTheFirstRowOfTheGrid()' method. " + e);
            return false;
        }
    }




    /********************************************************
     * Method Name      : actionOnTheFirstRowOfTheGrid()
     * Purpose          : to verify user can click on the edit link of the first row of the Queues table grid
     * Author           : Gudi
     * Parameters       : oBrowser, objByGrid, gridRecordAction
     * ReturnType       : boolean
     ********************************************************/
    public boolean actionOnTheFirstRowOfTheGrid(WebDriver oBrowser, By objByGrid, String gridRecordAction) {
        try {
            appDep.waitForTheElementState(oBrowser, objByGrid, "Visibility", "", waitTimeOut);
            queueName = appInd.getTextOnElement(oBrowser, objByGrid, "Text");
            String action = String.format(PayCRM_Modules_GeneralUIPage.obj_Grid_First_Row_Action_Btn, gridRecordAction);
            if (gridRecordAction.equals(GridRecordAction.VIEW.getValue())) {
                int openWin = appInd.getTotalOpenWindows(oBrowser);
                Assert.assertTrue(appInd.clickObject(oBrowser, objByGrid), "clickObject() was failed for the '" + PayCRM_Modules_GeneralUIPage.obj_Queues_Grid_FirstRow + "' webelement");
                appDep.threadSleep(4000);
                if (appInd.isSwitchingRequired(oBrowser, openWin)) {
                    oBrowser = appInd.switchBetweenTabsOrWindow(oBrowser);
                    String pageTitle = String.format(PayCRM_Modules_GeneralUIPage.obj_Page_Heading, queueName);
                    appDep.waitForTheElementState(oBrowser, By.xpath(pageTitle), "Visibility", "", waitTimeOut);
                }
            } else if (gridRecordAction.equals(GridRecordAction.EDIT.getValue())) {
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(action)), "clickObject() was failed for the '" + action + "' webelement");
            } else if (gridRecordAction.equals(GridRecordAction.VIEW_COMPANY_SUPPLIER.getValue())) {
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(action)), "clickObject() was failed for the '" + action + "' webelement");
            } else if (gridRecordAction.equals(GridRecordAction.OPEN_IN_NEW_TAB.getValue())) {
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(action)), "clickObject() was failed for the '" + action + "' webelement");
            } else if (gridRecordAction.equals(GridRecordAction.LOGIN_TO_PAYNET.getValue())) {
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(action)), "clickObject() was failed for the '" + action + "' webelement");
            } else {
                Assert.assertTrue(false, "Given grid operation is not valid operation.");
            }
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'actionOnTheFirstRowOfTheGrid()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'actionOnTheFirstRowOfTheGrid()' method. " + e);
            return false;
        }
    }


    /********************************************************
     * Method Name      : createNewCompany()
     * Purpose          : to verify user can create the new company details under General modules
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : Map<String, String>
     ********************************************************/
    public Map<String, String> createNewCompany(WebDriver oBrowser, DataTable dataTable) {
        List<Map<String, String>> companyDetails = null;
        Map<String, String> companyData = null;
        String timeStamp = null;
        try {
            timeStamp = appInd.getDateTime("Shhmmss");
            companyDetails = dataTable.asMaps(String.class, String.class);
            companyData = new HashMap<String, String>();
            companyData.put("Name", companyDetails.get(0).get("Name") + "_" + timeStamp);
            companyName = companyData.get("Name");
            companyData.put("Tickler", companyDetails.get(0).get("Tickler"));
            companyData.put("Website", companyDetails.get(0).get("Website"));
            if (timeStamp.length() == 9) {
                companyData.put("PaynetCompanyID", timeStamp);
            } else {
                companyData.put("PaynetCompanyID", timeStamp + "0");
            }
            companyData.put("SalesforceID", companyDetails.get(0).get("SalesforceID"));
            companyData.put("Phone", companyDetails.get(0).get("Phone"));
            companyData.put("Fax", companyDetails.get(0).get("Fax"));
            companyData.put("ZipCode", companyDetails.get(0).get("ZipCode"));
            companyData.put("Parent", companyDetails.get(0).get("Parent"));
            companyData.put("Locale", companyDetails.get(0).get("Locale"));
            companyData.put("Language", companyDetails.get(0).get("Language"));
            companyData.put("OriginationSource", companyDetails.get(0).get("OriginationSource"));
            companyData.put("CompanyType", companyDetails.get(0).get("CompanyType"));
            companyData.put("Status", companyDetails.get(0).get("Status"));
            companyData.put("Priority", companyDetails.get(0).get("Priority"));

            Assert.assertTrue(appDep.selectGeneralModules(oBrowser, "Companies"));

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Add_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Create_Btn, "Clickable", "", waitTimeOut);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Create_EditCompany_PageTitle, "Text", "New Company"));

            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Name_Edit, companyData.get("Name")));
            Assert.assertTrue(appInd.setOptionalObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Tickler_Edit, companyData.get("Tickler")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Website_Edit, companyData.get("Website")));
            Assert.assertTrue(appInd.setOptionalObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_PayNetCompanyID_Edit, companyData.get("PaynetCompanyID")));
            Assert.assertTrue(appInd.setOptionalObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_SalesforceID_Edit, companyData.get("SalesforceID")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Phone_Edit, companyData.get("Phone")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Fax_Edit, companyData.get("Fax")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_ZipCode_Edit, companyData.get("ZipCode")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Parent_List, companyData.get("Parent")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Locale_Edit, companyData.get("Locale")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Language_Edit, companyData.get("Language")));
            appInd.createAndGetWebElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_OriginationSource_Edit).clear();
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_OriginationSource_Edit, companyData.get("OriginationSource")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_CompanyType_List, companyData.get("CompanyType")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Status_List, companyData.get("Status")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Priority_List, companyData.get("Priority")));
            reports.writeResult(oBrowser, "Screenshot", "Details are entered to creat the new companys");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Create_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CompanyEditConfirmationMessage_Label, "Visibility", "", waitTimeOut);
            if (companyData.get("CompanyType").equalsIgnoreCase("Prospect")) {
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", waitTimeOut);
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CompanyEditConfirmationMessage_Label, "Text").contains("Company and Wave successfully created"));
            } else {
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ViewActivities_Btn, "Clickable", "", waitTimeOut);
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CompanyEditConfirmationMessage_Label, "Text").contains("Company was successfully created"));
            }

            companyData.put("TestPassed", "True");
            return companyData;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createNewCompany()' method. " + e);
            companyData.put("TestPassed", "False");
            return companyData;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createNewCompany()' method. " + e);
            companyData.put("TestPassed", "False");
            return companyData;
        }finally {companyDetails = null; companyData = null; timeStamp = null;}
    }



    /********************************************************
     * Method Name      : editCompanyDetails()
     * Purpose          : to verify user can edit the company details
     * Author           : Gudi
     * Parameters       : oBrowser, companyData
     * ReturnType       : Map<String, String>
     ********************************************************/
    public Map<String, String> editCompanyDetails(WebDriver oBrowser, Map<String, String> companyData) {
        try {
            Assert.assertTrue(appDep.selectGeneralModules(oBrowser, "Companies"));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Add_Link, "Clickable", "", waitTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_TableGrid, "Visibility", "", waitTimeOut);
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CompanyName_Search_Edit, companyName), "setObject() was failed for the '" + String.valueOf(PayCRM_Modules_GeneralUIPage.obj_ContactName_Search_Edit) + "' webelement");
            appDep.threadSleep(3000);
            reports.writeResult(oBrowser, "Screenshot", "The Company was searched successful");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Edit_Link), "clickObject() was failed for '" + String.valueOf(PayCRM_Modules_GeneralUIPage.obj_Company_Edit_Link) + "' webelement");
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Create_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_EditContact_PageTitle, "Text", "Edit Company"));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Name_Edit, companyData.get("Name")), "clearAndSetObject() was failed for the '" + PayCRM_Modules_GeneralUIPage.obj_Company_Website_Edit + "' webelement");
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Tickler_Edit, companyData.get("Tickler")), "clearAndSetObject() was failed for the '" + PayCRM_Modules_GeneralUIPage.obj_Company_Tickler_Edit + "' webelement");
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Website_Edit, companyData.get("Website")), "clearAndSetObject() was failed for the '" + PayCRM_Modules_GeneralUIPage.obj_Company_Website_Edit + "' webelement");
            companyData.remove("PaynetCompanyID");
            companyData.put("PaynetCompanyID", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_PayNetCompanyID_Edit, "Value"));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_SalesforceID_Edit, companyData.get("SalesforceID")), "clearAndSetObject() was failed for the '" + PayCRM_Modules_GeneralUIPage.obj_Company_SalesforceID_Edit + "' webelement");
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Phone_Edit, companyData.get("Phone")), "clearAndSetObject() was failed for the '" + PayCRM_Modules_GeneralUIPage.obj_Company_Phone_Edit + "' webelement");
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Fax_Edit, companyData.get("Fax")), "clearAndSetObject() was failed for the '" + PayCRM_Modules_GeneralUIPage.obj_Company_Fax_Edit + "' webelement");
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_ZipCode_Edit, companyData.get("ZipCode")), "clearAndSetObject() was failed for the '" + PayCRM_Modules_GeneralUIPage.obj_Company_ZipCode_Edit + "' webelement");
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Parent_List, 1), "selectObject() was failed for the '" + PayCRM_Modules_GeneralUIPage.obj_Company_Parent_List + "' webelement");
            companyData.remove("Parent");
            companyData.put("Parent", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Parent_List, "Dropdown"));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Locale_Edit, companyData.get("Locale")), "clearAndSetObject() was failed for the '" + PayCRM_Modules_GeneralUIPage.obj_Company_Locale_Edit + "' webelement");
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Language_Edit, companyData.get("Language")), "clearAndSetObject() was failed for the '" + PayCRM_Modules_GeneralUIPage.obj_Company_Language_Edit + "' webelement");
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_OriginationSource_Edit, companyData.get("OriginationSource")), "clearAndSetObject() was failed for the '" + PayCRM_Modules_GeneralUIPage.obj_Company_OriginationSource_Edit + "' webelement");
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_CompanyType_List, companyData.get("CompanyType")), "selectObject() was failed for the '" + PayCRM_Modules_GeneralUIPage.obj_Company_CompanyType_List + "' webelement");
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Status_List, companyData.get("Status")), "selectObject() was failed for the '" + PayCRM_Modules_GeneralUIPage.obj_Company_Status_List + "' webelement");
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Priority_List, companyData.get("Priority")), "selectObject() was failed for the '" + PayCRM_Modules_GeneralUIPage.obj_Company_Priority_List + "' webelement");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Create_Btn), "clickObject() was failed for the '" + PayCRM_Modules_GeneralUIPage.obj_Company_Create_Btn + "' webelement");
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CompanyEditConfirmationMessage_Label, "Visibility", "", waitTimeOut);

            if (companyData.get("CompanyType").equalsIgnoreCase("Client")) {
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ViewActivities_Btn, "Clickable", "", waitTimeOut);
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CompanyEditConfirmationMessage_Label, "Text").contains("Company was successfully updated"));
            } else {
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", waitTimeOut);
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CompanyEditConfirmationMessage_Label, "Text").contains("Company and Wave successfully created"));
            }

            companyData.put("TestPassed", "True");
            return companyData;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'editCompanyDetails()' method. " + e);
            companyData.put("TestPassed", "False");
            return companyData;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'editCompanyDetails()' method. " + e);
            companyData.put("TestPassed", "False");
            return companyData;
        }
    }


    /********************************************************
     * Method Name      : verifyCompanyDetails()
     * Purpose          : to verify that the newly created/edited values are reflected in the companies grid for the selected company
     * Author           : Gudi
     * Parameters       : oBrowser, companyStatus, companyData
     * ReturnType       : boolean
     ********************************************************/
    public boolean verifyCompanyDetails(WebDriver oBrowser, String companyStatus, Map<String, String> companyData) {
        try {
            Assert.assertTrue(appDep.selectGeneralModules(oBrowser, "Companies"));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Add_Link, "Clickable", "", waitTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_TableGrid, "Visibility", "", waitTimeOut);
            if (companyStatus.equalsIgnoreCase("Add")) {
                Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CompanyName_Search_Edit, companyName), "setObject() was failed for the '" + String.valueOf(PayCRM_Modules_GeneralUIPage.obj_ContactName_Search_Edit) + "' webelement");
            } else {
                Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CompanyName_Search_Edit, editCompanyName), "setObject() was failed for the '" + String.valueOf(PayCRM_Modules_GeneralUIPage.obj_ContactName_Search_Edit) + "' webelement");
            }

            appDep.threadSleep(3000);
            reports.writeResult(oBrowser, "Screenshot", "The Company was searched successful");
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Companies_Grid + "//tr[contains(@class,'row-lines dx-column-lines')]/td[1]"), "Text", companyData.get("Name")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Companies_Grid + "//tr[contains(@class,'row-lines dx-column-lines')]/td[2]"), "Text", companyData.get("Website")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Companies_Grid + "//tr[contains(@class,'row-lines dx-column-lines')]/td[3]"), "Text", companyData.get("PaynetCompanyID")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Companies_Grid + "//tr[contains(@class,'row-lines dx-column-lines')]/td[4]"), "Text", companyData.get("Phone")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Companies_Grid + "//tr[contains(@class,'row-lines dx-column-lines')]/td[5]"), "Text", companyData.get("Parent")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Companies_Grid + "//tr[contains(@class,'row-lines dx-column-lines')]/td[6]"), "Text", companyData.get("OriginationSource")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Companies_Grid + "//tr[contains(@class,'row-lines dx-column-lines')]/td[7]"), "Text", companyData.get("CompanyType")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Companies_Grid + "//tr[contains(@class,'row-lines dx-column-lines')]/td[8]"), "Text", companyData.get("Status")));

            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifyCompanyDetails()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifyCompanyDetails()' method. " + e);
            return false;
        }
    }


    /********************************************************
     * Method Name      : createOREditContactsForClientCompany()
     * Purpose          : user creates/Edits the contacts from General modules-->Client companies
     * Author           : Gudi
     * Parameters       : oBrowser, contactStatus, contactDetails
     * ReturnType       : Map<String, String>
     ********************************************************/
    public Map<String, String> createOREditContactsForClientCompany(WebDriver oBrowser, String contactStatus, Map<String, String> contactDetails) {
        String headerText = null;
        try {
            if (contactStatus.equalsIgnoreCase("New")) {
                oBrowser.navigate().refresh();
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Contacts_Link, "visibility", "", waitTimeOut);
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Contacts_Link), "clickObject() was failed for the '" + PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Contacts_Link + "' webelement");
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Add_Link), "clickObject() was failed for the '" + PayCRM_Modules_GeneralUIPage.obj_Contacts_Add_Link + "' webelement");
                headerText = "New Contact";
            }else{
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Contacts_Grid +"//a[@title='Edit']")), "clickObject() was failed for the '"+String.valueOf(PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Contacts_Link)+"' webelement");
                headerText = "Edit Contact";
            }

            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Contacts_Create_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Contacts_Header + "//h4"), "Text", headerText));
            Assert.assertTrue(appInd.setOptionalObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Name_Edit, contactDetails.get("ContactName")), "setObject() was failed for '" + String.valueOf(PayCRM_Modules_GeneralUIPage.obj_Contacts_Name_Edit) + "' webelement");
            Assert.assertTrue(appInd.setOptionalObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Email_Edit, contactDetails.get("Email")), "setObject() was failed for '" + String.valueOf(PayCRM_Modules_GeneralUIPage.obj_Contacts_Email_Edit) + "' webelement");
            Assert.assertTrue(appInd.setOptionalObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Title_Edit, contactDetails.get("Title")), "setObject() was failed for '" + String.valueOf(PayCRM_Modules_GeneralUIPage.obj_Contacts_Title_Edit) + "' webelement");
            Assert.assertTrue(appInd.setOptionalObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Phone1_Edit, contactDetails.get("Phone1")), "setObject() was failed for '" + String.valueOf(PayCRM_Modules_GeneralUIPage.obj_Contacts_Phone1_Edit) + "' webelement");
            Assert.assertTrue(appInd.setOptionalObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Phone1Extn_Edit, contactDetails.get("Phone1Extn")), "selectObject() was failed for '" + String.valueOf(PayCRM_Modules_GeneralUIPage.obj_Phone1Extn_Edit) + "' webelement");
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Phone1Type_List, contactDetails.get("Phone1Type")), "selectObject() was failed for '" + String.valueOf(PayCRM_Modules_GeneralUIPage.obj_Contacts_Phone1Type_List) + "' webelement");
            Assert.assertTrue(appInd.setOptionalObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Phone2_Edit, contactDetails.get("Phone2")), "setObject() was failed for '" + String.valueOf(PayCRM_Modules_GeneralUIPage.obj_Contacts_Phone2_Edit) + "' webelement");
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Phone2Type_List, contactDetails.get("Phone2Type")), "selectObject() was failed for '" + String.valueOf(PayCRM_Modules_GeneralUIPage.obj_Contacts_Phone2Type_List) + "' webelement");
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Status_List, contactDetails.get("Status")), "selectObject() was failed for '" + String.valueOf(PayCRM_Modules_GeneralUIPage.obj_Contacts_Status_List) + "' webelement");
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Priority_List, contactDetails.get("Priority")), "selectObject() was failed for '" + String.valueOf(PayCRM_Modules_GeneralUIPage.obj_Contacts_Priority_List) + "' webelement");
            reports.writeResult(oBrowser, "Screenshot", "Contacts details entered for creating '" + contactStatus + "' Contacts");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Contacts_Create_Btn), "clickObject() was failed for '" + String.valueOf(PayCRM_Modules_GeneralUIPage.obj_NewContact_Create_Btn) + "' webelement");
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Visibility", "", 5);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Contacts_Grid + "//table//tr/td[contains(@aria-label, 'Name,')]//input"), "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Contacts_Grid + "//table//tr/td[contains(@aria-label, 'Name,')]//input"), contactDetails.get("ContactName")));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "After creating the '" + contactStatus + "' Contacts details for the client company");
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Contacts_Grid + "//table//tr/td[contains(@aria-label, 'Name, Value')])[1]"), "Text", contactDetails.get("ContactName")));
            contactDetails.put("TestPassed", "True");
            return contactDetails;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createOREditContactsForClientCompany()' method. " + e);
            contactDetails.put("TestPassed", "Exception");
            return null;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createOREditContactsForClientCompany()' method. " + e);
            contactDetails.put("TestPassed", "Exception");
            return contactDetails;
        }
    }


    /********************************************************
     * Method Name      : validateTheContactsDetails()
     * Purpose          : validate the contact details under Contacts>>Show screen
     * Author           : Gudi
     * Parameters       : oBrowser, contactStatus, contactDetails
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateTheContactsDetails(WebDriver oBrowser, String contactStatus, Map<String, String> contactDetails) {
        try {
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ShowEditDetails_Header, "Text", "Information", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "After the '" + contactStatus + "' contacts");
            if (contactStatus.equalsIgnoreCase("New")) {
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Confirmation_Panel_Message, "Text").contains("Contact: " + contactDetails.get("Name") + " - Created"));
            } else {
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Confirmation_Panel_Message, "Text").contains("Contact: " + contactDetails.get("Name") + " - Updated"));
            }

            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Confirmation_Panel_Message, "Text").contains("by " + appInd.getPropertyValueByKeyName("qaUserName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ShowContact_Information_Grid + ")[" + 1 + "]"), "Text", contactDetails.get("Name")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ShowContact_Information_Grid + ")[" + 2 + "]"), "Text").contains(contactDetails.get("Phone1")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ShowContact_Information_Grid + ")[" + 3 + "]"), "Text", contactDetails.get("Phone1Type")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ShowContact_Information_Grid + ")[" + 4 + "]"), "Text", contactDetails.get("Phone2")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ShowContact_Information_Grid + ")[" + 5 + "]"), "Text", contactDetails.get("Phone2Type")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ShowContact_Information_Grid + ")[" + 6 + "]"), "Text", contactDetails.get("Email")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ShowContact_Information_Grid + ")[" + 7 + "]"), "Text", contactDetails.get("Title")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ShowContact_Information_Grid + ")[" + 8 + "]"), "Text", contactDetails.get("Status")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ShowContact_Information_Grid + ")[" + 9 + "]"), "Text", contactDetails.get("Priority")));

            Assert.assertTrue(appDep.selectGeneralModules(oBrowser, "contacts"));
            appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_ContactsTable_Grid + "//input)[1]"), contactDetails.get("Name"));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ContactsTable_Grid + "//tr[contains(@class, 'lines dx-column-lines')][1]/td[1]"), "Text", contactDetails.get("Name")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ContactsTable_Grid + "//tr[contains(@class, 'lines dx-column-lines')][1]/td[2]"), "Text", contactDetails.get("Phone1")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ContactsTable_Grid + "//tr[contains(@class, 'lines dx-column-lines')][1]/td[3]"), "Text", contactDetails.get("Phone1Type")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ContactsTable_Grid + "//tr[contains(@class, 'lines dx-column-lines')][1]/td[4]"), "Text", contactDetails.get("Phone2")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ContactsTable_Grid + "//tr[contains(@class, 'lines dx-column-lines')][1]/td[5]"), "Text", contactDetails.get("Phone2Type")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ContactsTable_Grid + "//tr[contains(@class, 'lines dx-column-lines')][1]/td[6]"), "Text", contactDetails.get("Email")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ContactsTable_Grid + "//tr[contains(@class, 'lines dx-column-lines')][1]/td[7]"), "Text", contactDetails.get("Title")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ContactsTable_Grid + "//tr[contains(@class, 'lines dx-column-lines')][1]/td[8]"), "Text", contactDetails.get("Status")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ContactsTable_Grid + "//tr[contains(@class, 'lines dx-column-lines')][1]/td[9]"), "Text", contactDetails.get("Priority")));

            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateTheContactsDetails()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateTheContactsDetails()' method. " + e);
            return false;
        }
    }


    /********************************************************
     * Method Name      : editTheContactsForGeneralModules()
     * Purpose          : user edits the contacts from General modules
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : Map<String, String>
     ********************************************************/
    public Map<String, String> editTheContactsForGeneralModules(WebDriver oBrowser, Map<String, String> contactDetails) {
        try {
            if(!appInd.verifyOptionalElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Edit_Link)) {
                Assert.assertTrue(appDep.selectGeneralModules(oBrowser, "Contacts"));
                Assert.assertTrue(appDep.navigateAndSelectTheContact(oBrowser, "GeneralContacts"));
            }
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Edit_Link, "Clickable", "", waitTimeOut);
            Assert.assertTrue(actionOnTheFirstRowOfTheGrid(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ContactsTable_Grid +"//tr[1][contains(@class, 'row-lines dx-column-lines')]/td[1]"), GridRecordAction.EDIT.getValue()));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_NewQueues_Create_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Name_Edit, "Value", queueName));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_NewContact_Create_Btn, "Clickable", "", waitTimeOut);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_EditContact_PageTitle, "Text", "Edit Contact"));

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Name_Edit, contactDetails.get("Name")), "clearAndSetObject() was failed for '" + String.valueOf(PayCRM_Modules_GeneralUIPage.obj_Contacts_Name_Edit) + "' webelement");
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Phone1_Edit, contactDetails.get("Phone1")), "clearAndSetObject() was failed for '" + String.valueOf(PayCRM_Modules_GeneralUIPage.obj_Contacts_Phone1_Edit) + "' webelement");
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Phone1Type_List, contactDetails.get("Phone1Type")), "selectObject() was failed for '" + String.valueOf(PayCRM_Modules_GeneralUIPage.obj_Contacts_Phone1Type_List) + "' webelement");
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Phone2_Edit, contactDetails.get("Phone2")), "clearAndSetObject() was failed for '" + String.valueOf(PayCRM_Modules_GeneralUIPage.obj_Contacts_Phone2_Edit) + "' webelement");
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Phone2Type_List, contactDetails.get("Phone2Type")), "selectObject() was failed for '" + String.valueOf(PayCRM_Modules_GeneralUIPage.obj_Contacts_Phone2Type_List) + "' webelement");
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Email_Edit, contactDetails.get("Email")), "clearAndSetObject() was failed for '" + String.valueOf(PayCRM_Modules_GeneralUIPage.obj_Contacts_Email_Edit) + "' webelement");
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Title_Edit, contactDetails.get("Title")), "clearAndSetObject() was failed for '" + String.valueOf(PayCRM_Modules_GeneralUIPage.obj_Contacts_Title_Edit) + "' webelement");
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Status_List, contactDetails.get("Status")), "selectObject() was failed for '" + String.valueOf(PayCRM_Modules_GeneralUIPage.obj_Contacts_Status_List) + "' webelement");
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Priority_List, contactDetails.get("Priority")), "selectObject() was failed for '" + String.valueOf(PayCRM_Modules_GeneralUIPage.obj_Contacts_Priority_List) + "' webelement");
            reports.writeResult(oBrowser, "Screenshot", "Contacts details entered for editing the existing Contacts");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_NewContact_Create_Btn), "clickObject() was failed for '" + String.valueOf(PayCRM_Modules_GeneralUIPage.obj_NewContact_Create_Btn) + "' webelement");
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Show_EditContacts_Link, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CompanyEditConfirmationMessage_Label, "Text").contains("Contact was successfully updated."));
            contactDetails.put("TestPassed", "True");
            return contactDetails;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'editTheContactsForGeneralModules()' method. " + e);
            contactDetails.put("TestPassed", "Exception");
            return null;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'editTheContactsForGeneralModules()' method. " + e);
            contactDetails.put("TestPassed", "Exception");
            return contactDetails;
        }
    }


    /********************************************************
     * Method Name      : deleteContactsForGeneralModules()
     * Purpose          : user deletes the contacts from General modules
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean deleteContactsForGeneralModules(WebDriver oBrowser) {
        try {
            Assert.assertTrue(appDep.selectGeneralModules(oBrowser, "Contacts"));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Add_Link, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ContactName_Search_Edit, editContactName), "setObject() was failed for the '" + String.valueOf(PayCRM_Modules_GeneralUIPage.obj_ContactName_Search_Edit) + "' webelement");
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ContactsGrid_Pagination_Label, "Text", "Displaying Page 1 of 1 (1 records)", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "The contact was searched successful");

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Delete_Link), "clickObject() was failed for '" + String.valueOf(PayCRM_Modules_GeneralUIPage.obj_Contacts_Delete_Link) + "' webelement");
            appDep.threadSleep(2000);
            if (appInd.isAlertPresent(oBrowser)) {
                oBrowser.switchTo().alert().accept();
                appDep.threadSleep(2000);
            } else {
                reports.writeResult(oBrowser, "Fail", "Unable to display the delete contacts confirmation alert.");
                Assert.assertTrue(false, "Failed to get the delete contacts confirmation alert.");
            }
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CompanyEditConfirmationMessage_Label, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CompanyEditConfirmationMessage_Label, "Text").contains("Contact was successfully destroyed"));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ContactName_Search_Edit, "Clickable", "", waitTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ContactsGrid_WithData, "Invisibility", "", waitTimeOut);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ContactName_Search_Edit, editContactName), "setObject() was failed for the '" + String.valueOf(PayCRM_Modules_GeneralUIPage.obj_ContactName_Search_Edit) + "' webelement");
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ContactsGrid_Pagination_NoData_Label, "Text", "Displaying Page 1 of 1 (0 records)", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "The contact was searched successful");
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'deleteContactsForGeneralModules()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'deleteContactsForGeneralModules()' method. " + e);
            return false;
        }
    }


    /********************************************************
     * Method Name      : validateTheClientContactsDetails()
     * Purpose          : user deletes the contacts from General modules
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateTheClientContactsDetails(WebDriver oBrowser, String contactStatus, Map<String, String> contactDetails) {
        try {
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Contacts_Grid + "//table//tr[contains(@class, 'lines dx-column-lines')]/td[1])[1]"), "Text", contactDetails.get("ContactName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Contacts_Grid + "//table//tr[contains(@class, 'lines dx-column-lines')]/td[2])[1]"), "Text", contactDetails.get("Email")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Contacts_Grid + "//table//tr[contains(@class, 'lines dx-column-lines')]/td[3])[1]"), "Text", contactDetails.get("Phone1")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Contacts_Grid + "//table//tr[contains(@class, 'lines dx-column-lines')]/td[4])[1]"), "Text", contactDetails.get("Phone1Type")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Contacts_Grid + "//table//tr[contains(@class, 'lines dx-column-lines')]/td[5])[1]"), "Text", contactDetails.get("Phone2")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Contacts_Grid + "//table//tr[contains(@class, 'lines dx-column-lines')]/td[6])[1]"), "Text", contactDetails.get("Phone2Type")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Contacts_Grid + "//table//tr[contains(@class, 'lines dx-column-lines')]/td[7])[1]"), "Text", contactDetails.get("Status")));

            //Go to Show page
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Queues_Show_Link));

            reports.writeResult(oBrowser, "Screenshot", "Show page of '" + contactStatus + "' contacts");
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientContacts_Show_Grid + "//h4"), "Text", "Contact", minTimeOut);

            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientContacts_Show_Grid + "//dl/dd[1]"), "Text", contactDetails.get("ContactName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientContacts_Show_Grid + "//dl/dd[2]"), "Text", contactDetails.get("Phone1") + " ext. " + contactDetails.get("Phone1Extn")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientContacts_Show_Grid + "//dl/dd[3]"), "Text", contactDetails.get("Phone1Type")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientContacts_Show_Grid + "//dl/dd[4]"), "Text", contactDetails.get("Phone2")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientContacts_Show_Grid + "//dl/dd[5]"), "Text", contactDetails.get("Phone2Type")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientContacts_Show_Grid + "//dl/dd[6]"), "Text", contactDetails.get("Email")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientContacts_Show_Grid + "//dl/dd[7]"), "Text", contactDetails.get("Title")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientContacts_Show_Grid + "//dl/dd[8]"), "Text", contactDetails.get("Status")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientContacts_Show_Grid + "//dl/dd[9]"), "Text", contactDetails.get("Priority")));

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientContacts_Show_Grid + "//button[@class='close']")));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateTheClientContactsDetails()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateTheClientContactsDetails()' method. " + e);
            return false;
        }
    }


    /*************************************************************************************************************
     * Method Name      : createOREditContactsForClientCompaniesAndValidate()
     * Purpose          : user creates/Edits the contacts from General modules--> Client companies and validates the same
     * Author           : Gudi
     * Parameters       : oBrowser, contactStatus, dataTable
     * ReturnType       : boolean
     ************************************************************************************************************/
    public boolean createOREditContactsForClientCompaniesAndValidate(WebDriver oBrowser, String contactStatus, DataTable dataTable) {
        Map<String, String> contactDetails;
        List<Map<String, String>> contactData = null;
        String timeStamp;
        try {
            timeStamp = appInd.getDateTime("Shhmmss");
            contactData = dataTable.asMaps(String.class, String.class);
            contactDetails = new HashMap<String, String>();
            contactDetails.put("ContactName", contactData.get(0).get("ContactName") + timeStamp);
            newContactName = contactDetails.get("ContactName");
            contactDetails.put("Email", contactData.get(0).get("Email"));
            contactDetails.put("Title", contactData.get(0).get("Title"));
            contactDetails.put("Phone1", contactData.get(0).get("Phone1"));
            contactDetails.put("Phone1Extn", contactData.get(0).get("Phone1Extn"));
            contactDetails.put("Phone1Type", contactData.get(0).get("Phone1Type"));
            contactDetails.put("Phone2", contactData.get(0).get("Phone2"));
            contactDetails.put("Phone2Type", contactData.get(0).get("Phone2Type"));
            contactDetails.put("Status", contactData.get(0).get("Status"));
            contactDetails.put("Priority", contactData.get(0).get("Priority"));

            contactDetails = createOREditContactsForClientCompany(oBrowser, contactStatus, contactDetails);
            Assert.assertTrue(contactDetails.get("TestPassed").equalsIgnoreCase("True"), "The createOREditContactsForClientCompany() method was failed");
            Assert.assertTrue(validateTheClientContactsDetails(oBrowser, contactStatus, contactDetails));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createOREditContactsForClientCompaniesAndValidate()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createOREditContactsForClientCompaniesAndValidate()' method. " + e);
            return false;
        } finally {contactDetails = null; contactData = null; timeStamp = null;}
    }


    /********************************************************
     * Method Name      : editAndValidateNewContactsGeneralModules()
     * Purpose          : user edits the contacts from General modules and validates the same
     * Author           : Gudi
     * Parameters       : oBrowser, contactStatus, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean editAndValidateNewContactsGeneralModules(WebDriver oBrowser, String contactStatus, DataTable dataTable) {
        Map<String, String> contactDetails;
        List<Map<String, String>> contactData = null;
        String timeStamp;
        List<Map<String, String>> db_GeneralContactsData = null;
        try {
            timeStamp = appInd.getDateTime("Shhmmss");
            contactData = dataTable.asMaps(String.class, String.class);
            contactDetails = new HashMap<String, String>();
            contactDetails.put("Name", contactData.get(0).get("Name") + timeStamp);
            editContactName = contactDetails.get("Name");
            contactDetails.put("Phone1", contactData.get(0).get("Phone1"));
            contactDetails.put("Phone1Type", contactData.get(0).get("Phone1Type"));
            contactDetails.put("Phone2", contactData.get(0).get("Phone2"));
            contactDetails.put("Phone2Type", contactData.get(0).get("Phone2Type"));
            contactDetails.put("Email", contactData.get(0).get("Email"));
            contactDetails.put("Title", contactData.get(0).get("Title"));
            contactDetails.put("Status", contactData.get(0).get("Status"));
            contactDetails.put("Priority", contactData.get(0).get("Priority"));

            Assert.assertTrue(appDep.navigateAndSelectTheContact(oBrowser, "GeneralContacts"));

            contactDetails = editTheContactsForGeneralModules(oBrowser, contactDetails);
            Assert.assertTrue(contactDetails.get("TestPassed").equalsIgnoreCase("True"), "The editTheContactsForGeneralModules() method was failed");
            Assert.assertTrue(validateTheContactsDetails(oBrowser, contactStatus, contactDetails));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'editAndValidateNewContactsGeneralModules()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'editAndValidateNewContactsGeneralModules()' method. " + e);
            return false;
        } finally {contactDetails = null; contactData = null; timeStamp = null;}
    }


    /********************************************************
     * Method Name      : createAndValidateTheCompanyDetails()
     * Purpose          : user creates the company from General modules and validates the same
     * Author           : Gudi
     * Parameters       : oBrowser, companyStatus, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean createAndValidateTheCompanyDetails(WebDriver oBrowser, String companyStatus, DataTable dataTable) {
        Map<String, String> companyData = null;
        try {
            companyData = createNewCompany(oBrowser, dataTable);
            Assert.assertTrue(companyData.get("TestPassed").equalsIgnoreCase("True"), "The createNewCompany() method was failed");
            Assert.assertTrue(validateCompanyDetails_ShowPage(oBrowser, companyStatus, companyData));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createAndValidateTheCompanyDetails()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createAndValidateTheCompanyDetails()' method. " + e);
            return false;
        } finally {companyData = null;}
    }


    /********************************************************
     * Method Name      : editAndValidateTheCompanyDetails()
     * Purpose          : user edits the company from General modules and validates the same
     * Author           : Gudi
     * Parameters       : oBrowser, companyStatus, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean editAndValidateTheCompanyDetails(WebDriver oBrowser, String companyStatus, DataTable dataTable) {
        List<Map<String, String>> companyDetails = null;
        Map<String, String> companyData = null;
        String timeStamp = null;
        try {
            timeStamp = appInd.getDateTime("Shhmmss");
            companyDetails = dataTable.asMaps(String.class, String.class);
            companyData = new HashMap<String, String>();
            companyData.put("Name", companyDetails.get(0).get("Name") + "_" + timeStamp);
            editCompanyName = companyData.get("Name");
            companyData.put("Tickler", companyDetails.get(0).get("Tickler"));
            companyData.put("Website", companyDetails.get(0).get("Website"));
            if (timeStamp.length() == 9) {
                companyData.put("PaynetCompanyID", timeStamp);
            } else {
                companyData.put("PaynetCompanyID", timeStamp + "0");
            }
            companyData.put("SalesforceID", companyDetails.get(0).get("SalesforceID"));
            companyData.put("Phone", companyDetails.get(0).get("Phone"));
            companyData.put("Fax", companyDetails.get(0).get("Fax"));
            companyData.put("ZipCode", companyDetails.get(0).get("ZipCode"));
            companyData.put("Parent", companyDetails.get(0).get("Parent"));
            companyData.put("Locale", companyDetails.get(0).get("Locale"));
            companyData.put("Language", companyDetails.get(0).get("Language"));
            companyData.put("OriginationSource", companyDetails.get(0).get("OriginationSource"));
            companyData.put("CompanyType", companyDetails.get(0).get("CompanyType"));
            companyData.put("Status", companyDetails.get(0).get("Status"));
            companyData.put("Priority", companyDetails.get(0).get("Priority"));

            companyData = editCompanyDetails(oBrowser, companyData);
            Assert.assertTrue(companyData.get("TestPassed").equalsIgnoreCase("True"), "The editCompanyDetails() method was failed");
            Assert.assertTrue(verifyCompanyDetails(oBrowser, companyStatus, companyData));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'editAndValidateTheCompanyDetails()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'editAndValidateTheCompanyDetails()' method. " + e);
            return false;
        } finally {
            companyDetails = null; companyData = null; timeStamp = null;
        }
    }


    /********************************************************
     * Method Name      : validateCompanyDetails_ShowPage()
     * Purpose          : validate the company details under Company>>Show screen
     * Author           : Gudi
     * Parameters       : oBrowser, contactStatus, contactDetails
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateCompanyDetails_ShowPage(WebDriver oBrowser, String companyStatus, Map<String, String> contactDetails) {
        try {
            reports.writeResult(oBrowser, "Screenshot", "After the '" + companyStatus + "' Company is saved");
            if (contactDetails.get("CompanyType").equalsIgnoreCase("Client")) {
                if (companyStatus.equalsIgnoreCase("New")) {
                    Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Confirmation_Panel_Message, "Text").contains("Company: " + contactDetails.get("Name") + " - Created"));
                } else {
                    Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Confirmation_Panel_Message, "Text").contains("Company: " + contactDetails.get("Name") + " - Updated"));
                }

                Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Confirmation_Panel_Message, "Text").contains(appInd.getPropertyValueByKeyName("qaUserName")));
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Company_Show_Grid + "/dl/dd)[1]"), "Text").trim().contains(contactDetails.get("Name")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Company_Show_Grid + "/dl/dd)[2]"), "Text", contactDetails.get("PaynetCompanyID")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Company_Show_Grid + "/dl/dd)[3]"), "Text", contactDetails.get("Website")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Company_Show_Grid + "/dl/dd)[4]"), "Text", contactDetails.get("Phone")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Company_Show_Grid + "/dl/dd)[5]"), "Text", contactDetails.get("Fax")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Company_Show_Grid + "/dl/dd)[6]"), "Text", contactDetails.get("ZipCode")));
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Company_Show_Grid + "/dl/dd/a)[2]"), "Text").equalsIgnoreCase(contactDetails.get("Parent")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Company_Show_Grid + "/dl/dd)[8]"), "Text", contactDetails.get("OriginationSource")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Company_Show_Grid + "/dl/dd)[9]"), "Text", contactDetails.get("Status")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Company_Show_Grid + "/dl/dd)[10]"), "Text", contactDetails.get("Priority")));
            } else {
                reports.writeResult(oBrowser, "Info", "The Prospect companies will not have Show page");
            }
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateCompanyDetails_ShowPage()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateCompanyDetails_ShowPage()' method. " + e);
            return false;
        }
    }



    /********************************************************
     * Method Name      : applyFilterAndSearchSuppliersData()
     * Purpose          : to verify that the Filter functionality works for Suppliers data under under modules->Suppliers section
     * Author           : Gudi
     * Parameters       : oBrowser, valueType, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean applyFilterAndSearchSuppliersData(WebDriver oBrowser, String valueType, String queryKey, DataTable dataTable) {
        List<Map<String, String>> suppliersDetails = null;
        List<WebElement> rows;
        int rowNum = 0;
        Map<String, String> supplierData = null;
        JSONArray caseDetails = null;
        try {
            suppliersDetails = dataTable.asMaps(String.class, String.class);
            supplierData = new HashMap<>();
            supplierData.put("SupplierName", suppliersDetails.get(0).get("SupplierName"));
            supplierData.put("Type", suppliersDetails.get(0).get("Type"));
            supplierData.put("Status", suppliersDetails.get(0).get("Status"));
            supplierData.put("Priority", suppliersDetails.get(0).get("Priority"));
            supplierData.put("PaynetCompanyID", suppliersDetails.get(0).get("PaynetCompanyID"));
            supplierData.put("RecordLimit", suppliersDetails.get(0).get("RecordLimit"));
            supplierData.put("HasPaynetCompanyID", suppliersDetails.get(0).get("HasPaynetCompanyID"));
            supplierData.put("InProgramOnly", suppliersDetails.get(0).get("InProgramOnly"));

            if(!queryKey.isEmpty()) {
                caseDetails = apiDataProvider.getAPIDataProviderResponse(queryKey, new Object[] {});
            }

            Assert.assertTrue(appDep.selectGeneralModules(oBrowser, "suppliers"));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Suppliers_Filter_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Suppliers_Clear_Btn));
            Assert.assertTrue(payCRM_modules_generalUIBaseStep.validateClearButtonInSuppliersPage(oBrowser));
            reports.writeResult(oBrowser, "Screenshot", "The 'Suppliers' filter was cleared successful");

            if (supplierData.get("SupplierName") != null) {
                if(valueType.equalsIgnoreCase("Valid")){
                    Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Suppliers_Name_Edit, ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("name").toString()));
                    supplierData.remove("SupplierName");
                    supplierData.put("SupplierName", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("name").toString());
                }else{
                    supplierData.remove("SupplierName");
                    supplierData.put("SupplierName", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("name").toString()+"!@#$%");
                    Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Suppliers_Name_Edit, supplierData.get("SupplierName")));
                }
            }

            if (supplierData.get("Type") != null) {
                String type = new String(new StringBuilder(((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("supplier_type_name").toString()).insert(0, "(").insert(((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("supplier_type_name").toString().indexOf('-'), ")")).replace("- ", "");
                appInd.clickObject(oBrowser, By.xpath("//div[@id='supplier_type_filter_chosen']//input[@class='chosen-search-input default']"));
//                appInd.setObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierTypes_Edit +"//input"), type);
                Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Suppliers_Types_SearchResults + "/ul/li[text()='" + type + "']")));
                supplierData.remove("Type");
                supplierData.put("Type", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("supplier_type_name").toString());
            }

            if (supplierData.get("Status") != null) {
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Suppliers_Status_Label));
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Suppliers_Status_SearchResults + "/ul/li[text()='" + ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("status_name").toString() + "']")));
                supplierData.remove("Status");
                supplierData.put("Status", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("status_name").toString());
            }

            if (supplierData.get("Priority") != null) {
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Suppliers_Priority_Label));
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Suppliers_Priority_SearchResults + "/ul/li[text()='" + ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("priority_name").toString() + "']")));
                supplierData.remove("Priority");
                supplierData.put("Priority", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("priority_name").toString());
            }

            if (supplierData.get("PaynetCompanyID") != null) {
                Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Suppliers_PaynetCompanyID_Edit, suppliersDetails.get(0).get("PaynetCompanyID")));
            }

            if (supplierData.get("RecordLimit") != null) {
                Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Suppliers_RecordLimits_Edit, suppliersDetails.get(0).get("RecordLimit")));
                oBrowser.findElement(PayCRM_Modules_GeneralUIPage.obj_Suppliers_RecordLimits_Edit).sendKeys(Keys.TAB);
            }

            if (supplierData.get("HasPaynetCompanyID") != null && suppliersDetails.get(0).get("HasPaynetCompanyID").equalsIgnoreCase("Yes")) {
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Suppliers_HasPaynetCompanyID_Btn));
            }

            if (supplierData.get("InProgramOnly") != null && suppliersDetails.get(0).get("InProgramOnly").equalsIgnoreCase("Yes")) {
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Suppliers_InProgramOnly_Btn));
            }

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Suppliers_Filter_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Visibility", "", minTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);

            if (valueType.equalsIgnoreCase("Valid")) {
                rows = oBrowser.findElements(By.xpath(PayCRM_Modules_GeneralUIPage.obj_Suppliers_SearchResultGrid_rows + "/table[@style]/tbody/tr[not(@style)]"));
                if (supplierData.get("RecordLimit") != null && rows.size() == Integer.parseInt(supplierData.get("RecordLimit"))) {
                    rowNum = Integer.parseInt(supplierData.get("RecordLimit"));
                } else {
                    rowNum = rows.size();
                }

                for (int i = 0; i < rowNum; i++) {
                    if (supplierData.get("SupplierName") != null)
                        Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Suppliers_SearchResultGrid_rows + "/table[@style]/tbody/tr[" + (i + 1) + "][not(@style)]/td[1]"), "Text", supplierData.get("SupplierName")));
                    if (supplierData.get("Type") != null)
                        Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Suppliers_SearchResultGrid_rows + "/table[@style]/tbody/tr[" + (i + 1) + "][not(@style)]/td[7]"), "Text", supplierData.get("Type")));
                    if (supplierData.get("Status") != null)
                        Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Suppliers_SearchResultGrid_rows + "/table[@style]/tbody/tr[" + (i + 1) + "][not(@style)]/td[8]"), "Text", supplierData.get("Status")));
                    if (supplierData.get("Priority") != null)
                        Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Suppliers_SearchResultGrid_rows + "/table[@style]/tbody/tr[" + (i + 1) + "][not(@style)]/td[9]"), "Text", supplierData.get("Priority")));
                    if (supplierData.get("PaynetCompanyID") != null)
                        Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Suppliers_SearchResultGrid_rows + "/table[@style]/tbody/tr[" + (i + 1) + "][not(@style)]/td[4]"), "Text", supplierData.get("PaynetCompanyID")));
                    if (supplierData.get("HasPaynetCompanyID") != null && suppliersDetails.get(0).get("HasPaynetCompanyID").equalsIgnoreCase("Yes"))
                        Assert.assertTrue(!appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Suppliers_SearchResultGrid_rows + "/table[@style]/tbody/tr[" + (i + 1) + "][not(@style)]/td[4]"), "Text").isEmpty());
                }
            } else {
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Suppliers_Grid_WithNoData, "Visibility", "", waitTimeOut);
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Suppliers_Grid_WithNoData));
            }
            reports.writeResult(oBrowser, "Screenshot", "The 'Suppliers' filter functionality was working successful");
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'applyFilterAndSearchSuppliersData()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'applyFilterAndSearchSuppliersData()' method. " + e);
            return false;
        } finally {
            suppliersDetails = null; rows = null; supplierData = null; caseDetails = null;
        }
    }




    /********************************************************
     * Method Name      : validateClearButtonInSuppliersPage()
     * Purpose          : to verify the clear button functionalities under modules->Suppliers section
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateClearButtonInSuppliersPage(WebDriver oBrowser) {
        try {
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Suppliers_Name_Edit, "placeholder", "Search by Supplier Name"));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Suppliers_Types_Label, "value", "Select Type(s)"));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Suppliers_Status_Label, "value", "Select Status(s)"));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Suppliers_Priority_Label, "value", "Select Priority(s)"));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Suppliers_RecordLimits_Edit, "value", "1000"));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Suppliers_PaynetCompanyID_Edit, "value", ""));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateClearButtonInSuppliersPage()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateClearButtonInSuppliersPage()' method. " + e);
            return false;
        }
    }


    /********************************************************
     * Method Name      : validateClearButtonInCasesPage()
     * Purpose          : to verify the clear button functionalities under modules->Cases section
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateClearButtonInCasesPage(WebDriver oBrowser) {
        try {
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Cases_SupplierName_Edit, "placeholder", "Enter Supplier Name"));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Cases_CompanyName_Edit, "value", "Select One or More"));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Cases_CaseType_Edit, "value", "Select One or More"));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Cases_status_Edit, "value", "Select One or More"));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Cases_Priority_Edit, "value", "Select One or More"));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Cases_RecordLimit_Edit, "value", "1000"));
            //Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Cases_Amount_Edit, "placeholder", "Amount"));

            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateClearButtonInCasesPage()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateClearButtonInCasesPage()' method. " + e);
            return false;
        }
    }




    /********************************************************
     * Method Name      : clickAndValidateTheSelectedCase()
     * Purpose          : user clicks on the first case number from the Cases Grid and validates that the Cases page has opened successful
     *                    Suppoered QueueName values are 'Daily Grind - Cases', 'Enrollments Manager' & 'Assisted Payment Services'
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : String
     ********************************************************/
    public String clickAndValidateTheSelectedCase(WebDriver oBrowser, String queueName) {
        String caseNum = null;
        try {
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Cases_Grid_FirstRow, "Clickable", "", waitTimeOut);
            caseNum = appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Cases_Grid_FirstRow, "Text");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Cases_Grid_FirstRow));
            if (queueName.equalsIgnoreCase("Enrollments Manager")) {
                oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_WaveAndOffers_Header, "Text", "Wave & Offers", waitTimeOut);
            } else if (queueName.equalsIgnoreCase("Assisted Payment Services")) {
                oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
                appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentHelp_Label, "Text", "Payment Help", waitTimeOut);
            } else if (queueName.equalsIgnoreCase("Daily Grind - Cases")) {
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_WaveAndOffers_Header, "Text", "Wave & Offers", waitTimeOut);
            } else {
                reports.writeResult(oBrowser, "Fail", "Invalid queue Name '" + queueName + "'");
                Assert.assertTrue(false, "Invalid queue Name '" + queueName + "'");
            }
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CaseNumber_Label, "Text").contains(caseNum));
            return caseNum;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'clickAndValidateTheSelectedCase()' method. " + e);
            return null;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'clickAndValidateTheSelectedCase()' method. " + e);
            return null;
        } finally {
            caseNum = null;
        }
    }





    /********************************************************
     * Method Name      : linkUpdateForCasesAndValidate()
     * Purpose          : user perform Link Update of cases for Modules->Daily Grind->Cases
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean linkUpdateForCasesAndValidate(WebDriver oBrowser, DataTable dataTable) {
        List<Map<String, String>> caseNum = null;
        try {
            caseNum = dataTable.asMaps(String.class, String.class);
            caseNumber = caseNum.get(0).get("CaseNumber");
            /*appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Cases_Grid_Pagination, "Clickable", "", waitTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Cases_Grid_CaseNumber_Search_Edit, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Cases_Grid_CaseNumber_Search_Edit, caseNum.get(0).get("CaseNumber")));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CasesGrid_Pagination_Label, "Visibility", "", waitTimeOut);
            caseNumber = appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Cases_Grid_FirstRow, "Text");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Cases_Grid_FirstRow));
            caseNumber = clickAndValidateTheSelectedCase(oBrowser, "Daily Grind - Cases");*/
            oBrowser.navigate().to(appInd.getPropertyValueByKeyName("linkUpdateCaseURL"));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Cases_LinkUpdate_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_EnrollmentCaseNumber_Label, "Text").contains("» Case #" + caseNumber));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Cases_LinkUpdate_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_LinkUpdateConfirmationMessage_Label, "Visibility", "", waitTimeOut);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_LinkUpdateConfirmationMessage_Label, "Text").contains("Link has been successfully updated"));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Globe_PayNetLogin_Link));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'linkUpdateForCasesAndValidate()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'linkUpdateForCasesAndValidate()' method. " + e);
            return false;
        } finally {caseNum = null;}
    }





    /********************************************************
     * Method Name      : validateSuppliersValitionsTabElements()
     * Purpose          : user validates the UI elements under Validations tab of Suppliers page
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateSuppliersValitionsTabElements(WebDriver oBrowser) {
        String company_OuterColumns[];
        String company_InnerColumns[];
        String contact_OuterColumns[];
        String contact_InnerColumns[];
        String payment_OuterColumns[];
        String payment_InnerColumns[];
        String audioInfo_Columns[];
        int rowCount = 0;
        String paymentType = null;
        boolean blnExist = false;
        try {
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Validations_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Visibility", "", minTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Validations_Tabs + "/ul/li/a)[1]"), "Text", "Company"));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Validations_Tabs + "/ul/li/a)[2]"), "Text").contains("Contact"));
            String Payment = appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Validations_Tabs + "/ul/li/a)[3]"), "Text").trim();
            if(Payment.equalsIgnoreCase("Payment (Commercial Cards)")){
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Validations_Tabs + "/ul/li/a)[3]"), "Text", "Payment (Commercial Cards)"));
                paymentType = "Payment (Commercial Cards)";
            }else{
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Validations_Tabs + "/ul/li/a)[3]"), "Text", "Payment (Electronic)"));
                paymentType = "Payment (Electronic)";
            }

            String auditInfo = appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Validations_Tabs + "/ul/li/a)[4]"), "Text").trim();
            if(auditInfo.equalsIgnoreCase("Audit Info")){
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Validations_Tabs + "/ul/li/a)[4]"), "Text", "Audit Info"));
            }else{
                blnExist = true;
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Validations_Tabs + "/ul/li/a)[4]"), "Text", "Payment (Electronic)"));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Validations_Tabs + "/ul/li/a)[5]"), "Text", "Audit Info"));
            }


            //Company
            reports.writeResult(oBrowser, "Screenshot", "Validating 'Company' Tab elements");
            company_OuterColumns = "Supplier Name#PayNet Supplier ID#TIN#Address 1#Address 2#City#State#Country#DUNS#Phone#Phone Ext#Fax".split("#");
            for (int i = 0; i < company_OuterColumns.length; i++) {
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Company_OuterGrid + "//table//tr[1]/td[" + (i + 2) + "]/div[contains(@class,'text-content')]"), "Text", company_OuterColumns[i]));
            }


            company_InnerColumns = "Validation Type#Audit Date#Action Date#Area#Email#Status".split("#");
            for (int i = 0; i < company_InnerColumns.length; i++) {
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Company_InnerGrid + "//tr/th[" + (i + 1) + "]"), "Text", company_InnerColumns[i]));
            }


            //Contact
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Validations_Tabs + "/ul/li/a)[2]")));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Validating 'Contact' Tab elements");
            contact_OuterColumns = "First Name#Last Name#Job Title#Contact Class#Contact Type#DFA Phone#DFA Ext#DFA Type#Activation Contact#DFA Selected".split("#");
            for (int i = 0; i < contact_OuterColumns.length; i++) {
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Contact_OuterGrid + "//table//tr[1]/td[" + (i + 2) + "]/div[contains(@class, 'text-content')]"), "Text", contact_OuterColumns[i]));
            }


            contact_InnerColumns = "Validation Type#Audit Date#Action Date#Area#Email#Status".split("#");
            for (int i = 0; i < contact_InnerColumns.length; i++) {
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Contact_InnerGrid + "//tr/th[" + (i + 1) + "]"), "Text", contact_InnerColumns[i]));
            }


            if(paymentType.contains("Commercial Cards")){
                //Payment (Commercial Cards)
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Validations_Tabs + "/ul/li/a)[3]")));
                appDep.threadSleep(2000);
                reports.writeResult(oBrowser, "Screenshot", "Validating 'Payment (Commercial Cards)' Tab elements");
                payment_OuterColumns = "Provider#Card Type#Contact Email#Merchant ID#PIC ID#Remit. Delivery Address#Remit. Delivery Option#Remit. Format#Separate Remit. Email".split("#");
                for (int i = 0; i < payment_OuterColumns.length; i++) {
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Payment_OuterGrid + "//table//tr[1]/td[" + (i + 2) + "]/div[contains(@class, 'text-content')]"), "Text", payment_OuterColumns[i]));
                }
            }else{
                //Payment (Electronics)
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Validations_Tabs + "/ul/li/a)[3]")));
                appDep.threadSleep(2000);
                reports.writeResult(oBrowser, "Screenshot", "Validating 'Payment (Electronics)' Tab elements");
                payment_OuterColumns = "Account,Account #,Bank,Local Routing Code,Remit. Delivery Address,Remit. Delivery Option,Remit. Format,Enable Dual Approval,Has Dual Approval".split(",");
                for (int i = 0; i < payment_OuterColumns.length; i++) {
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Payment_Grid + "//table//tr[1]/td["+(i+2)+"]/div[contains(@class, 'text-content')]"), "Text", payment_OuterColumns[i]));
                }
            }



            payment_InnerColumns = "Validation Type#Audit Date#Action Date#Area#Email#Status".split("#");
            for (int i = 0; i < payment_InnerColumns.length; i++) {
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Payment_InnerGrid + "//tr/th[" + (i + 1) + "]"), "Text", payment_InnerColumns[i]));
            }


            if(blnExist == true){
                //Payment (Electronics)
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Validations_Tabs + "/ul/li/a)[4]")));
                appDep.threadSleep(2000);
                reports.writeResult(oBrowser, "Screenshot", "Validating 'Payment (Electronics)' Tab elements");
                payment_OuterColumns = "Account,Account #,Bank,Local Routing Code,Remit. Delivery Address,Remit. Delivery Option,Remit. Format,Enable Dual Approval,Has Dual Approval".split(",");
                for (int i = 0; i < payment_OuterColumns.length; i++) {
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Payment_Grid + "//table//tr[1]/td["+(i+2)+"]/div[contains(@class, 'text-content')]"), "Text", payment_OuterColumns[i]));
                }
            }


            //Audit Info
            if(blnExist == true){
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Validations_Tabs + "/ul/li/a)[5]")));
            }else{
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Validations_Tabs + "/ul/li/a)[4]")));
            }

            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Validating 'Audit Info' Tab elements");
            audioInfo_Columns = "Timestamp#Username#Action Type#Area#Effected To#Data Details#Validation Details".split("#");
            for (int i = 0; i < audioInfo_Columns.length; i++) {
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_AuditInfo_Grid + "//table//tr[1]/td[" + (i + 1) + "]/div[contains(@class,'text-content')]"), "Text", audioInfo_Columns[i]));
            }


            //Data Details under Audit Info section
            rowCount = oBrowser.findElements(By.xpath(PayCRM_Modules_GeneralUIPage.obj_AuditInfo_Grid +"//table//tr[contains(@class, 'dx-row-lines dx-column-lines')]")).size();
            for(int i=0; i<rowCount; i++){
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_AuditInfo_Grid +"//table//tr[contains(@class, 'dx-row-lines dx-column-lines')]/td[6]/a)["+(i+1)+"]")));
                appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_AudioInfo_DataDetails_Grid + "//table[contains(@id, 'DataTables_Table_')]"), "Visibility", "", waitTimeOut);
                appDep.threadSleep(2000);
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_AudioInfo_DataDetails_Grid + "//button[@class='close']")));
            }

            //Validation details under Audit Info section
            if(appInd.verifyOptionalElementPresent(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_AuditInfo_Grid +"//table//tr[contains(@class, 'dx-row-lines dx-column-lines')]/td[7]/a)[1]"))){
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_AuditInfo_Grid +"//table//tr[contains(@class, 'dx-row-lines dx-column-lines')]/td[7]/a)[1]")));
                appDep.waitForTheElementState(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_AudioInfo_DataDetails_Grid + "//div[@class='modal-body'])"), "Visibility", "", waitTimeOut);
                appDep.threadSleep(2000);
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_AudioInfo_DataDetails_Grid + "//button[@class='close']")));
            }else{
                reports.writeResult(oBrowser, "Warning", "The 'View' hyperlink is not displayed under 'Validations Details column of the 'Audit Info' tab");
                reports.writeResult(oBrowser, "Screenshot", "The 'View' hyperlink is not displayed under 'Validations Details column of the 'Audit Info' tab");
            }

            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateSuppliersValitionsTabElements()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateSuppliersValitionsTabElements()' method. " + e);
            return false;
        } finally {
            company_OuterColumns = null; company_InnerColumns = null; contact_OuterColumns = null; contact_InnerColumns = null; payment_OuterColumns = null; payment_InnerColumns = null; audioInfo_Columns = null;
        }
    }


    /********************************************************
     * Method Name      : validateOtherProviderApproachUIElementsUnderSolutionDesign()
     * Purpose          : user validates the UI elements of Other Provider Approach under Solution Design of the prospect Company
     * Author           : Gudi
     * Parameters       : oBrowser, queryKey
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateOtherProviderApproachUIElementsUnderSolutionDesign(WebDriver oBrowser, String queryKey) {
        int rowCount = 0;
        boolean blnRes = false;
        Actions oAction = null;
        WebElement objEle = null;
        String riskScore[];
        String paymentMixComparision[];
        String companyName = null;
        String otherProvider = null;
        String otherProviderPercentage = null;
        String companyProvider = null;
        String companyPercentage = null;
        String finalPaymentScore = null;
        String finalRiskScore = null;
        String initialTotalTransactionCount = null;
        String initialTotalSpendCount = null;
        String totalTransactionCount = null;
        String totalSpendCount = null;
        String finalTotalTransactionCount = null;
        String finalTotalSpendCount = null;
        String initialPaymentScore = null;
        String initialRiskScore = null;
        JSONArray caseDetails = null;
        try {
            caseDetails = navigateAndSelectGeneralCompanies(oBrowser, "Prospect", queryKey);
            companyName = appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "value");

            //set FALSE to "Display Payment and Risk Score" dropdown
            appDep.navigateToInternalSetupPage(oBrowser, "Account Setup");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Preferences_Link));
            appDep.threadSleep(2000);
            appInd.scrollToThePage(oBrowser, "Bottom");
            Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_DisplayScore_List), "True"));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Preferences_Update_Btn));
            appDep.threadSleep(4000);
            reports.writeResult(oBrowser, "Screenshot", "The 'Display Score' value was set to 'FALSE'");
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_DisplayScore_List + "/parent::td/preceding-sibling::td"), "Text", "Display Payment and Risk Score"));
            oBrowser.navigate().refresh();
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", maxWaitTimeOut);


            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SolutionDesigns_Link));
            appDep.threadSleep(4000);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_OtherProvidersApproach_Link));
            appDep.threadSleep(4000);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_OtherProvider_Label), "Text", "Other Providers", minTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_OtherProvidersEffort_Label, "Text", "Other Providers Effort", waitTimeOut);
            oAction = new Actions(oBrowser);
            Assert.assertTrue(appDep.mouseOverAndValidateTheToolTip(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CardNetworkMatchConfidence_Grid + "//span[contains(text(), 'High')]"), "Match on Supplier name, full address, and other data provided", "High"));
            Assert.assertTrue(appDep.mouseOverAndValidateTheToolTip(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CardNetworkMatchConfidence_Grid + "//span[contains(text(), 'Medium')]"), "Match on Supplier name and partial address, or other data provided", "Medium"));
            Assert.assertTrue(appDep.mouseOverAndValidateTheToolTip(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CardNetworkMatchConfidence_Grid + "//span[contains(text(), 'Low')]"), "Match on Supplier name only", "Low"));
            Assert.assertTrue(appDep.mouseOverAndValidateTheToolTip(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CardNetworkMatchConfidence_Grid + "//span[contains(text(), 'No Match')]"), "No match with Visa", "No Match"));

            //Payment Score
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_PaymentScore_Legend_Label + "/div/div/div[1])[2]"), "Text").contains("Below Average"));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_PaymentScore_Legend_Label + "/div/div/div[1])[3]"), "Text").contains("Average"));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_PaymentScore_Legend_Label + "/div/div/div[1])[4]"), "Text").contains("Above Average"));

            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Payment Score section with different levels of payment scores");
            appDep.threadSleep(2000);


            //Risk Score
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_RiskScore_Toggle_Btn)));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "After clicking the red toggle button on 'Risk Score'");
            riskScore = "Minimal#Low#Slight#Enhanced#High#Critical#Catastrophic".split("#");
            for (int i = 0; i < riskScore.length; i++) {
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_RiskScore_Levels_Label + "//ul/li[" + (i + 1) + "]"), "Text").contains(riskScore[i]));
            }


            //Payment Mix comparision
            //Check, Electronic & Virtual card
            paymentMixComparision = "Check#Electronic#Virtual Card".split("#");
            for (int i = 0; i < paymentMixComparision.length; i++) {
                blnRes = false;
                rowCount = oBrowser.findElements(By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentMixComparison_grid + "//*[@class='dxc-labels-group']//*[text()='" + paymentMixComparision[i] + "']")).size();
                for (int j = 0; j < rowCount; j++) {
                    blnRes = true;
                    objEle = appInd.createAndGetWebElement(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_PaymentMixComparison_grid + "//*[@class='dxc-labels-group']//*[text()='" + paymentMixComparision[i] + "'])[" + (j + 1) + "]"));
                    oAction.moveToElement(objEle).click().build().perform();
                    reports.writeResult(oBrowser, "Screenshot", "Hovering on '" + paymentMixComparision[i] + "' option of the 'Payment Mix Comparison'. The value is: " + objEle.getText());
                }

                if (blnRes == false) {
                    reports.writeResult(oBrowser, "Info", "The '" + paymentMixComparision[i] + "' option was not found");
                }
            }


            //Other Providers Effort
            otherProvider = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_OtherProvider_Label), "Text");
            otherProviderPercentage = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_OtherProvider_Label + "/ancestor::*[@fill]/following-sibling::*[@fill]"), "Text").replace(".0", "");
            oAction.moveToElement(appInd.createAndGetWebElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_OtherProvider_Label))).build().perform();
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Other Providers parcentage: " + otherProvider + ": " + otherProviderPercentage);


//            appDep.threadSleep(2000);
//            companyProvider = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_otherProviderEfforts_Grid + "//*[@overflow]/*[contains(text(), '"+companyName+"')]"), "Text");
//            companyPercentage = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_otherProviderEfforts_Grid + "//*[@overflow]/*[contains(text(), '"+companyName+"')]/ancestor::*[@fill]/following-sibling::*[@fill]"), "Text").replace(".0", "");
//            oAction.moveToElement(appInd.createAndGetWebElement(oBrowser, By.xpath("(//*[contains(text(),'" + companyName + "')])[2]"))).build().perform();
//            appDep.threadSleep(2000);
//            reports.writeResult(oBrowser, "Screenshot", "The company '" + companyName + "' Providers parcentage: " + companyProvider + ": " + companyPercentage);

            //payment score & Risk score should change based on the checkbox selected under 'Financial institution'
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_RiskScore_Toggle_Btn)));
            appDep.threadSleep(2000);

            initialPaymentScore = appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentScoreUtilization_Label, "Text");
            initialRiskScore = appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_RiskScoreUtilization_Label, "Text");
            for (int i = 0; i <= 2; i++) {
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_FinancialInstitutions_Grid + "//tr/td[@class='text-center']/input")));
            }
            appDep.threadSleep(2000);
            finalPaymentScore = appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentScoreUtilization_Label, "Text");
            finalRiskScore = appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_RiskScoreUtilization_Label, "Text");
            Assert.assertTrue((!initialPaymentScore.equals(finalPaymentScore)), "Initial and final payment scores are still same");
            Assert.assertTrue((!initialRiskScore.equals(finalRiskScore)), "Initial and final risk scores are still same");

            //Top 15 High Network Matches
            appInd.scrollToThePage(oBrowser, "Bottom");
            initialTotalTransactionCount = appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_totalTransactionCount_Label, "Text");
            initialTotalSpendCount = appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_totalSpendCount_Label, "Text");
            int transactionCount = 0;
            double totalSpend = 0.0;
            for (int i = 0; i < 2; i++) {
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_HighNetworkMatches_Grid + "//tr[" + (i + 1) + "]/td[1]")));
                appDep.threadSleep(1000);
                transactionCount += Double.parseDouble(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_HighNetworkMatches_Grid + "//tr[" + (i + 1) + "]/td[5]"), "Text"));
                double total = Math.round(Double.parseDouble(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_HighNetworkMatches_Grid + "//tr[" + (i + 1) + "]/td[6]"), "Text").replace("$", "").replace(",", "")) * 100.0) / 100.0;
                totalSpend += total;
                totalSpend = (double) Math.round(totalSpend * 100) / 100;
            }
            totalTransactionCount = appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_totalTransactionCount_Label, "Text");
            totalSpendCount = appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_totalSpendCount_Label, "Text").replace("$", "").replace(",", "");

            Assert.assertTrue((transactionCount == Integer.parseInt(totalTransactionCount)), "The 'Total Transaction' Count is not same as the number of 'High Network Matches' selected'");
            Assert.assertTrue((totalSpend == Double.parseDouble(totalSpendCount)), "The 'Total Spend' Count is not same as the number of 'High Network Matches' selected'");

            //verify 'Clear' button is visible
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Clear_Btn, "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Clear_Btn));
            Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Clear_Btn));
            appDep.threadSleep(2000);
            finalTotalTransactionCount = appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_totalTransactionCount_Label, "Text");
            finalTotalSpendCount = appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_totalSpendCount_Label, "Text");

            Assert.assertEquals(initialTotalTransactionCount, finalTotalTransactionCount, "Both initial '" + initialTotalTransactionCount + "' & final '" + finalTotalTransactionCount + "' Transaction Counts are not matching");
            Assert.assertEquals(initialTotalSpendCount, finalTotalSpendCount, "Both initial '" + initialTotalSpendCount + "' & final '" + finalTotalSpendCount + "' Total Spend are not matching");


            //Effective Rebate
            objEle = appInd.createAndGetWebElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_EffectiveRebate_Label));
            oAction.click(objEle).click().perform();
            String strText = oBrowser.findElement(By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_EffectiveRebate_Mouse+")[2]")).getText();
            reports.writeResult(oBrowser, "Screenshot", "Mouse over on 'Effective Rebate'");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_OtherProvidersEffort_Label));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "After clicking the toggle button on 'Effective Rebate'");

            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateOtherProviderApproachUIElementsUnderSolutionDesign()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateOtherProviderApproachUIElementsUnderSolutionDesign()' method. " + e);
            return false;
        } finally {
            oAction = null;objEle = null;riskScore = null;paymentMixComparision = null;companyName = null;otherProvider = null;otherProviderPercentage = null;companyProvider = null;companyPercentage = null;finalPaymentScore = null;finalRiskScore = null;initialTotalTransactionCount = null;initialTotalSpendCount = null;totalTransactionCount = null;totalSpendCount = null;finalTotalTransactionCount = null;finalTotalSpendCount = null;initialPaymentScore = null;initialRiskScore = null;
        }
    }

    
    /********************************************************
     * Method Name      : updateDuplicateSupplierFromParent()
     * Purpose          : user perform 'Update Duplicates from Parent' from Solution Design--> Suppliers
     * Author           : Gudi
     * Parameters       : oBrowser, queryKey
     * ReturnType       : boolean
     ********************************************************/
    public boolean updateDuplicateSupplierFromParent(WebDriver oBrowser, String queryKey) {
        List<String> objSupplierName = null;
        Response response = null;
        JSONArray caseDetails = null;
        try {
            caseDetails = navigateAndSelectGeneralCompanies(oBrowser,"Prospect", queryKey);
            objSupplierName = new ArrayList<String>();
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SolutionDesigns_Link));
            appDep.threadSleep(4000);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SuppliersTab_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_UpdateDuplicatesFromParent_Btn, "Clickable", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Prospect Company Suppliers Page");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_UpdateDuplicatesFromParent_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_UpdateDuplicates_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_UpdateDuplicates_No_Btn+"//div[@class='slider slider-big round']/span[text()='NO']")));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Prospect Company Update Duplicates dialog");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_UpdateDuplicates_Btn));
            appDep.threadSleep(4000);

            for (int i = 0; i <= 4; i++) {
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//tr/td/div//span)[" + (i + 1) + "]")));
                appDep.threadSleep(1000);
                objSupplierName.add(appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//tr/td[2])[" + (i + 1) + "]"), "Text"));
            }
            reports.writeResult(oBrowser, "Screenshot", "Prospect Company: Selected suppliers for Mass Update");

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MassUpdate_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MassUpdate_Update_Btn, "Clickable", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Prospect Company: Mass Update dialog");
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SupplierType_List, "General"));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Supplier_Notes_Textarea, "Automation Notes"));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MassUpdate_Update_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CompanyEditConfirmationMessage_Label, "Visibility", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CompanyEditConfirmationMessage_Label, "Text").contains("Suppliers has been successfully Updated."));
            reports.writeResult(oBrowser, "Screenshot", "Prospect Company: Status change for all the selected suppliers for Mass update");
            for (int i = 0; i < objSupplierName.size(); i++) {
                appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ProspectCompanySupplier_Grid + "//td[contains(@aria-label, 'Supplier,')]//input"), "Clickable", "", minTimeOut);
                Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ProspectCompanySupplier_Grid + "//td[contains(@aria-label, 'Supplier,')]//input"), objSupplierName.get(i)));
                appDep.threadSleep(2000);
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//tr/td[5])[1]"), "Text", "General"));
            }
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'updateDuplicateSupplierFromParent()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'updateDuplicateSupplierFromParent()' method. " + e);
            return false;
        } finally {objSupplierName = null; caseDetails = null; response = null;}
    }



    /********************************************************
     * Method Name      : createNewSupplierAttachment()
     * Purpose          : user creates new supplier attachment under Modules-->General-->Supplier
     * Author           : Gudi
     * Parameters       : oBrowser, pageName, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public Map<String, String> createNewSupplierAttachment(WebDriver oBrowser, String pageName, Map<String, String> attachmentData) {
        String pageObject = null;
        try {
            if (pageName.equalsIgnoreCase("Supplier")) {
                //Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_FirstRow));
                //oBrowser = enrollmentMgrBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ViewActivities_Btn, "Clickable", "", waitTimeOut);
                pageObject = PayCRM_Modules_GeneralUIPage.obj_SupplierAttachments_Grid;
            } else {
                pageObject = PayCRM_Modules_GeneralUIPage.obj_CompanyAttachments_Grid;
            }

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Attachments_Link));
            appInd.scrollToThePage(oBrowser, "bottom");
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Add_Link, "Clickable", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "User in '" + pageName + "' 'Attachments' tab");
            appInd.scrollToThePage(oBrowser, "bottom");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Add_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_supplierAttachment_Create_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_NewAttachment_Dialog + "//h4"), "Text", "New Attachment"));

            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SupplierAttachment_Name_Edit, attachmentData.get("Name")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SupplierAttachment_Description_Textarea, attachmentData.get("Description")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AttachmentType_List, attachmentData.get("AttachmentType")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SupplierAttachment_Status_List, attachmentData.get("Status")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SupplierAttachment_Upload_Edit, System.getProperty("user.dir").replace("\\", "/") + "/TestData/" + attachmentData.get("FileToUpload") + ".pdf"));

            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierAttachment_FileUpload_Link + "/ancestor::span[1]/following-sibling::input"), "Value", attachmentData.get("FileToUpload") + ".pdf", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Creating the attachment in 'Attachments' tab");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_supplierAttachment_Create_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Visibility", "", minTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);

            appDep.waitForTheElementState(oBrowser, By.xpath(pageObject + "//td[contains(@aria-label, 'Attachment,')]//input"), "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CompanyEditConfirmationMessage_Label, "Text").contains("Attachment has been successfully created."));
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath(pageObject + "//td[contains(@aria-label, 'Attachment,')]//input"), attachmentData.get("Name")));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "The attachment was created in 'Attachments' tab");
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(pageObject + "//td[contains(@aria-label, 'Attachment, Value')]"), "Text", attachmentData.get("Name")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(pageObject + "//td[contains(@aria-label, 'Description, Value')]"), "Text", attachmentData.get("Description")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(pageObject + "//td[contains(@aria-label, 'Type, Value')]"), "Text", attachmentData.get("AttachmentType")));
            attachmentData.put("TestPassed", "True");
            return attachmentData;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createNewSupplierAttachment()' method. " + e);
            attachmentData.put("TestPassed", "False");
            return attachmentData;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createNewSupplierAttachment()' method. " + e);
            attachmentData.put("TestPassed", "False");
            return attachmentData;
        } finally {pageObject = null;}
    }



    /********************************************************
     * Method Name      : verifySuppliersAttachmentData()
     * Purpose          : user verifies created/edited supplier attachment data under Modules-->General-->Supplier
     * Author           : Gudi
     * Parameters       : oBrowser, attachmentStatus, pageName, supplierAttachment
     * ReturnType       : boolean
     ********************************************************/
    public boolean verifySuppliersAttachmentData(WebDriver oBrowser, String attachmentStatus, String pageName, Map<String, String> supplierAttachment) {
        String fileName = null;
        try {
            if (attachmentStatus.equalsIgnoreCase("New")) {
                fileName = supplierAttachment.get("FileToUpload");
            } else {
                fileName = supplierAttachmentFileName;
            }

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Queues_Show_Link));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Attachment_Show_Grid + "//h4"), "Text", "Attachment", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Attachment_Show_Grid + "//dl/dd)[1]"), "Text", supplierAttachment.get("Name")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Attachment_Show_Grid + "//dl/dd)[2]"), "Text", supplierAttachment.get("Description")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Attachment_Show_Grid + "//dl/dd)[3]"), "Text", supplierAttachment.get("Status")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Attachment_Show_Grid + "//dl/dd[4]/a)[1]"), "Text").contains(fileName));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Attachment_Show_Grid + "//dl/dd)[5]"), "Text", supplierAttachment.get("AttachmentType")));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Attachment_Show_Grid + "//button[@class='close']")));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifySuppliersAttachmentData()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifySuppliersAttachmentData()' method. " + e);
            return false;
        } finally {fileName = null;}
    }


    /********************************************************
     * Method Name      : createNewSupplierAttachmentAndValidateTheSame()
     * Purpose          : user adds new supplier attachment under Modules-->General-->Supplier
     * Author           : Gudi
     * Parameters       : oBrowser, pageName, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean createNewSupplierAttachmentAndValidateTheSame(WebDriver oBrowser, String pageName, DataTable dataTable) {
        List<Map<String, String>> supplierData = null;
        Map<String, String> supplier = null;
        String timeStamp = null;
        try {
            timeStamp = appInd.getDateTime("Shhmmss");
            supplierData = dataTable.asMaps(String.class, String.class);
            supplier = new HashMap<String, String>();
            supplier.put("Name", supplierData.get(0).get("Name") + timeStamp);
            supplier.put("Description", supplierData.get(0).get("Description"));
            supplier.put("AttachmentType", supplierData.get(0).get("AttachmentType"));
            supplier.put("Status", supplierData.get(0).get("Status"));
            supplier.put("FileToUpload", supplierData.get(0).get("FileToUpload"));
            supplierAttachmentFileName = supplier.get("FileToUpload");

            supplier = createNewSupplierAttachment(oBrowser, pageName, supplier);
            Assert.assertTrue(supplier.get("TestPassed").equalsIgnoreCase("True"), "The 'createNewSupplierAttachment()' method was failed");
            Assert.assertTrue(verifySuppliersAttachmentData(oBrowser, "New", pageName, supplier));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createNewSupplierAttachmentAndValidateTheSame()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createNewSupplierAttachmentAndValidateTheSame()' method. " + e);
            return false;
        } finally {
            supplierData = null; supplier = null; timeStamp = null;
        }
    }


    /********************************************************
     * Method Name      : editTheSupplierAttachment()
     * Purpose          : user edit supplier attachment under Modules-->General-->Supplier
     * Author           : Gudi
     * Parameters       : oBrowser, pageName, attachmentData
     * ReturnType       : Map<String, String>
     ********************************************************/
    public Map<String, String> editTheSupplierAttachment(WebDriver oBrowser, String pageName, Map<String, String> attachmentData) {
        String pageObject = null;
        By objEditLink = null;
        try {
            if (pageName.equalsIgnoreCase("Supplier")) {
                objEditLink = PayCRM_Modules_GeneralUIPage.obj_Contacts_Edit_Link;
            } else {
                objEditLink = PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Attachment_Edit_Link;
            }
            Assert.assertTrue(appInd.clickObject(oBrowser, objEditLink));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_supplierAttachment_Create_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_NewAttachment_Dialog + "//h4"), "Text", "Edit Attachment"));
            reports.writeResult(oBrowser, "Screenshot", "Before editing the '" + pageName + "' attachment in 'Attachments' tab");
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SupplierAttachment_Name_Edit, attachmentData.get("Name")));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SupplierAttachment_Description_Textarea, attachmentData.get("Description")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AttachmentType_List, attachmentData.get("AttachmentType")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SupplierAttachment_Status_List, attachmentData.get("Status")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_FileName_Edit, "Value").contains(supplierAttachmentFileName));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SupplierAttachment_FileAttached_Link, "Text").contains(supplierAttachmentFileName));
            reports.writeResult(oBrowser, "Screenshot", "editing the '" + pageName + "' attachment in 'Attachments' tab");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_supplierAttachment_Create_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Visibility", "", minTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);

            if (pageName.equalsIgnoreCase("Supplier")) {
                pageObject = PayCRM_Modules_GeneralUIPage.obj_SupplierAttachments_Grid;
            } else {
                pageObject = PayCRM_Modules_GeneralUIPage.obj_CompanyAttachments_Grid;
            }

            appDep.waitForTheElementState(oBrowser, By.xpath(pageObject + "//td[contains(@aria-label, 'Attachment,')]//input"), "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CompanyEditConfirmationMessage_Label, "Text").contains("Attachment has been successfully updated."));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(pageObject + "//td[contains(@aria-label, 'Attachment,')]//input"), attachmentData.get("Name")));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "After editing the '" + pageName + "' attachment in 'Attachments' tab");
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(pageObject + "//td[contains(@aria-label, 'Attachment, Value')]"), "Text", attachmentData.get("Name")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(pageObject + "//td[contains(@aria-label, 'Description, Value')]"), "Text", attachmentData.get("Description")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(pageObject + "//td[contains(@aria-label, 'Type, Value')]"), "Text", attachmentData.get("AttachmentType")));
            attachmentData.put("TestPassed", "True");
            return attachmentData;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'editTheSupplierAttachment()' method. " + e);
            attachmentData.put("TestPassed", "False");
            return attachmentData;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'editTheSupplierAttachment()' method. " + e);
            attachmentData.put("TestPassed", "False");
            return attachmentData;
        } finally {pageObject = null; objEditLink = null;}
    }




    /********************************************************
     * Method Name      : downloadAndDeleteTheSupplierAttachment()
     * Purpose          : User download and delets the Suppliers attachments
     * Author           : Gudi
     * Parameters       : oBrowser, pageName, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean downloadAndDeleteTheSupplierAttachment(WebDriver oBrowser, String pageName){
        try{
            Assert.assertTrue(downloadAndDeleteTheAttachment(oBrowser, pageName, PayCRM_Modules_GeneralUIPage.obj_DownloadAttachment_Link, PayCRM_Modules_GeneralUIPage.obj_Contacts_Delete_Link));
            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'downloadAndDeleteTheSupplierAttachment()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'downloadAndDeleteTheSupplierAttachment()' method. " + e);
            return false;
        }
    }





    /********************************************************
     * Method Name      : editSupplierAttachmentAndValidateTheSame()
     * Purpose          : user edits new supplier attachment under Modules-->General-->Supplier
     * Author           : Gudi
     * Parameters       : oBrowser, pageName, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean editSupplierAttachmentAndValidateTheSame(WebDriver oBrowser, String pageName, DataTable dataTable) {
        List<Map<String, String>> supplierData = null;
        Map<String, String> supplier = null;
        String timeStamp = null;
        try {
            timeStamp = appInd.getDateTime("Shhmmss");
            supplierData = dataTable.asMaps(String.class, String.class);
            supplier = new HashMap<String, String>();
            supplier.put("Name", supplierData.get(0).get("Name") + timeStamp);
            supplier.put("Description", supplierData.get(0).get("Description"));
            supplier.put("AttachmentType", supplierData.get(0).get("AttachmentType"));
            supplier.put("Status", supplierData.get(0).get("Status"));
            supplier.put("FileToUpload", supplierData.get(0).get("FileToUpload"));

            supplier = editTheSupplierAttachment(oBrowser, pageName, supplier);
            Assert.assertTrue(supplier.get("TestPassed").equalsIgnoreCase("True"), "The 'editTheSupplierAttachment()' method was failed");
            Assert.assertTrue(verifySuppliersAttachmentData(oBrowser, "Edit", pageName, supplier));
            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'editSupplierAttachmentAndValidateTheSame()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'editSupplierAttachmentAndValidateTheSame()' method. " + e);
            return false;
        }finally {supplierData = null; supplier = null; timeStamp = null;}
    }



    /********************************************************
     * Method Name      : downloadAndDeleteTheAttachment()
     * Purpose          : user downloads and deletes the supplier/Companies/Transmission Files attachment under Modules-->General-->Supplier/Companies
     * Author           : Gudi
     * Parameters       : oBrowser, pageName, objEle_Download, objEle_Delete
     * ReturnType       : boolean
     ********************************************************/
    public boolean downloadAndDeleteTheAttachment(WebDriver oBrowser, String pageName, By objEle_Download, By objEle_Delete) {
        boolean blnRes = false;
        Alert oAlert = null;
        String confirmationMsg = null;
        String pageObject = null;
        String fileName = null;
        try {
            //download
            appInd.moveToElement(oBrowser, objEle_Download);
            appDep.threadSleep(1000);
            Assert.assertTrue(appInd.clickObject(oBrowser, objEle_Download));
            appDep.threadSleep(2000);

            if(pageName.equalsIgnoreCase("Transmission Files")){
                oAlert = oBrowser.switchTo().alert();
                fileName = oAlert.getText();
                appDep.threadSleep(3000);
                oAlert.accept();
                appDep.threadSleep(5000);
                blnRes = appDep.validateDownloadedFileAndDelete(oBrowser, fileName, ".xlsx", "", "Yes");
                if (blnRes) {
                    reports.writeResult(oBrowser, "Pass", "The '" + pageName + " Attachment' .xlsx file was Exported successful");
                } else {
                    reports.writeResult(oBrowser, "Fail", "Failed to Exported the '" + pageName + " Attachment' .xlsx file");
                    Assert.assertTrue(false, "Failed to Export the '" + pageName + " Attachment' .xlsx file");
                }
            }else{
                appDep.threadSleep(5000);
                blnRes = appDep.verifyLatestDownloadFile(oBrowser, "NA", ".pdf", "Dummy PDF file", true);
                if (blnRes) {
                    reports.writeResult(oBrowser, "Pass", "The '" + pageName + " Attachment' pdf file was downloaded successful");
                } else {
                    reports.writeResult(oBrowser, "Fail", "Failed to download the '" + pageName + " Attachment' pdf file");
                    Assert.assertTrue(false, "Failed to download the '" + pageName + " Attachment' pdf file");
                }
            }

            if (pageName.equalsIgnoreCase("Supplier")) {
                confirmationMsg = "Are you sure? If this attachment is not linked to a company or case, this will also delete the entire attachment.";
                pageObject = PayCRM_Modules_GeneralUIPage.obj_SupplierAttachments_Grid;
            }else if(pageName.equalsIgnoreCase("Transmission Files")){
                confirmationMsg = "Are you sure?";
                pageObject = PayCRM_Modules_ManagedPaymentsUIPage.obj_Attachments_Grid;
            }
            else {
                confirmationMsg = "Are you sure? This will delete the attachment relationship to this company. If this attachment is not linked to a supplier or case, this will also delete the entire attachment.";
                pageObject = PayCRM_Modules_GeneralUIPage.obj_CompanyAttachments_Grid;
            }
            //delete
            appDep.threadSleep(1000);
            Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, objEle_Delete));
            appDep.threadSleep(2000);
            oAlert = oBrowser.switchTo().alert();
            Assert.assertTrue(appInd.compareValues(oBrowser, oAlert.getText(), confirmationMsg));
            oAlert.accept();
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appDep.threadSleep(5000);
            if(pageName.equalsIgnoreCase("Transmission Files")){
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(pageObject + "//tr[contains(@class, 'lines dx-column-lines')]/td[1]")));
            }else{
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(pageObject + "//td[contains(@aria-label, 'Attachment, Value')]")));
            }
            reports.writeResult(oBrowser, "Screenshot", "After deleting the '" + pageName + "' attachment in 'Attachments' tab");
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'downloadAndDeleteTheAttachment()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'downloadAndDeleteTheAttachment()' method. " + e);
            return false;
        } finally {oAlert = null; confirmationMsg = null; pageObject = null;}
    }




    /********************************************************
     * Method Name      : exportClientCompanyCasesORAttachmentsAndValidateTheSame()
     * Purpose          : user exports the client company cases/Attachments & validates the same under Modules-->General-->Companies
     * Author           : Gudi
     * Parameters       : oBrowser, pageName
     * ReturnType       : boolean
     ********************************************************/
    public boolean exportClientCompanyCasesORAttachmentsAndValidateTheSame(WebDriver oBrowser, String pageName) {
        Alert oAlert = null;
        String fileName = null;
        boolean blnRes = false;
        File objExportedCasesExcelFile = null;
        By objByExportBtn = null;
        String tableGrid = null;
        try {
            if (pageName.equalsIgnoreCase("Cases")) {
                companyName = appDep.selectTheCompanyBasedOnType(oBrowser, "Client");
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCases_Link));
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Cases_RecordLimitFilter_Edit, "Clickable", "", minTimeOut);
                Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Cases_RecordLimitFilter_Edit, "10"));
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCases_Link));
                appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Cases_RecordLimits_Grid + "//div[text()='Displaying Page 1 of 1 (10 records)']"), "Text", "Displaying Page 1 of 1 (10 records)", minTimeOut);
                objByExportBtn = PayCRM_Modules_GeneralUIPage.obj_Cases_Export_Btn;
                tableGrid = PayCRM_Modules_GeneralUIPage.obj_Cases_RecordLimits_Grid;
            } else if (pageName.contains("Company")) {
                objByExportBtn = PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Address_Export_Btn;
                tableGrid = PayCRM_Modules_GeneralUIPage.obj_Company_Address_Grid;
            } else {
                objByExportBtn = PayCRM_Modules_GeneralUIPage.obj_Attachments_Export_Btn;
                tableGrid = PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Attachments_Grid;
            }


            appDep.waitForTheElementState(oBrowser, objByExportBtn, "Clickable", "", minTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Client Company '" + pageName + "' grid data");
            appInd.scrollToElement(oBrowser, objByExportBtn);
            appInd.moveToElement(oBrowser, objByExportBtn);
            fileName = appDep.exportTheFile(oBrowser, objByExportBtn);

            blnRes = appDep.validateDownloadedFileAndDelete(oBrowser, fileName, ".xlsx", "", "No");
            if (blnRes)
                reports.writeResult(oBrowser, "Pass", "The Cases excel file '" + fileName + ".xlsx' was exported successful");
            else {
                reports.writeResult(oBrowser, "Fail", "Failed to export the Cases excel file '" + fileName + ".xlsx'");
                Assert.fail("Failed to export the Cases excel file '" + fileName + "'");
            }

            //Compare the downloaded excel file data with cases grid data
            blnRes = appDep.exportAndCompareTheExportedDataWithTableGridData(oBrowser, tableGrid, fileName);

            if (blnRes)
                reports.writeResult(oBrowser, "Pass", "Exported '" + pageName + "' excel file cell data and '" + pageName + "' grid cell datas are matching");
            else {
                reports.writeResult(oBrowser, "Fail", "Mis-match in Exported '" + pageName + "' excel file cell data and '" + pageName + "' grid cell data values");
                Assert.fail("Mis-match in Exported '" + pageName + "' excel file cell data and '" + pageName + "' grid cell data values");
            }

            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'exportClientCompanyCasesORAttachmentsAndValidateTheSame()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'exportClientCompanyCasesORAttachmentsAndValidateTheSame()' method. " + e);
            return false;
        } finally {
            objExportedCasesExcelFile = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Downloads\\" + fileName + ".xlsx");
            objExportedCasesExcelFile.delete();
            try {
                oAlert = null; fileName = null; objExportedCasesExcelFile = null; objByExportBtn = null; tableGrid = null;
            } catch (Exception e) {
            }
        }
    }


    /********************************************************
     * Method Name      : exportClientCompanyContactsAndValidateTheSame()
     * Purpose          : user exports the client company contacts & validates the same under Modules-->General-->Companies
     * Author           : Gudi
     * Parameters       : oBrowser, pageName
     * ReturnType       : boolean
     ********************************************************/
    public boolean exportClientCompanyContactsAndValidateTheSame(WebDriver oBrowser) {
        Alert oAlert = null;
        String fileName = null;
        boolean blnRes = false;
        File objExportedCasesExcelFile = null;
        try {
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Contacts_Grid + "//table//tr/td[contains(@aria-label, 'Name,')]//input"), ""));
            appDep.threadSleep(2000);
            /*Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Contacts_Export_Btn));
            appDep.threadSleep(2000);
            oAlert = oBrowser.switchTo().alert();
            fileName = oAlert.getText();
            oAlert.accept();
            appDep.threadSleep(5000);*/

            fileName = appDep.exportTheFile(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Contacts_Export_Btn);
            blnRes = appDep.exportAndCompareTheExportedDataWithTableGridData(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Contacts_Grid, fileName);

            if (blnRes)
                reports.writeResult(oBrowser, "Pass", "Exported 'Contacts' excel file cell data and 'Contacts' grid cell datas are matching");
            else {
                reports.writeResult(oBrowser, "Fail", "Mis-match in Exported 'Contacts' excel file cell data and 'Contacts' grid cell data values");
                Assert.fail("Mis-match in Exported 'Contacts' excel file cell data and 'Contacts' grid cell data values");
            }

            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'exportClientCompanyContactsAndValidateTheSame()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'exportClientCompanyContactsAndValidateTheSame()' method. " + e);
            return false;
        } finally {
            objExportedCasesExcelFile = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Downloads\\" + fileName + ".xlsx");
            objExportedCasesExcelFile.delete();
            oAlert = null; fileName = null; objExportedCasesExcelFile = null;
        }
    }


    /********************************************************
     * Method Name      : importSFDCAndDeleteTheContacts()
     * Purpose          : user exports the client company contacts & validates the same under Modules-->General-->Companies
     * Author           : Gudi
     * Parameters       : oBrowser, pageName
     * ReturnType       : boolean
     ********************************************************/
    public boolean importSFDCAndDeleteTheContacts(WebDriver oBrowser) {
        Alert oAlert = null;
        try {
            appInd.scrollToElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCompany_ImportSFDCContacts_Btn);
            appInd.moveToElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCompany_ImportSFDCContacts_Btn);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCompany_ImportSFDCContacts_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CompanyEditConfirmationMessage_Label, "Visibility", "", minTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CompanyEditConfirmationMessage_Label, "Text").contains("contacts were successfully imported from Salesforce."));

            //delete the contacts
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Contacts_Grid + "//table//tr/td[contains(@aria-label, 'Name,')]//input"), newContactName));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Delete_Link));
            appDep.threadSleep(2000);
            oAlert = oBrowser.switchTo().alert();
            Assert.assertTrue(appInd.compareValues(oBrowser, oAlert.getText(), "Are you sure?"));
            oAlert.accept();
            appDep.threadSleep(5000);
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Contacts_Grid + "//table//tr/td[contains(@aria-label, 'Name,')]//input"), newContactName));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Contacts_Grid + "//table//tr/td[contains(@aria-label, 'Name, Value')])[1]")));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'importSFDCAndDeleteTheContacts()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'importSFDCAndDeleteTheContacts()' method. " + e);
            return false;
        } finally {oAlert = null;}
    }


    /********************************************************
     * Method Name      : exportSupplierAttachmentsAndValidateTheSame()
     * Purpose          : user exports the supplier attachments & validates the same under Modules-->General-->Suppliers-->attachments
     * Author           : Gudi
     * Parameters       : oBrowser, pageName
     * ReturnType       : boolean
     ********************************************************/
    public boolean exportSupplierAttachmentsAndValidateTheSame(WebDriver oBrowser, String pageName) {
        Alert oAlert = null;
        String fileName = null;
        boolean blnRes = false;
        File objExportedCasesExcelFile = null;
        try {
            fileName = appDep.exportTheFile(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Suppliers_Attachments_Export_Btn);
            blnRes = appDep.exportAndCompareTheExportedDataWithTableGridData(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SupplierAttachments_Grid, fileName);

            if (blnRes)
                reports.writeResult(oBrowser, "Pass", "Exported 'Supplier Attachment' excel file cell data and 'Supplier Attachment' grid cell datas are matching");
            else {
                reports.writeResult(oBrowser, "Fail", "Mis-match in Exported 'Supplier Attachment' excel file cell data and 'Supplier Attachment' grid cell data values");
                Assert.fail("Mis-match in Exported 'Supplier Attachment' excel file cell data and 'Supplier Attachment' grid cell data values");
            }
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'exportSupplierAttachmentsAndValidateTheSame()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'exportSupplierAttachmentsAndValidateTheSame()' method. " + e);
            return false;
        } finally {
            objExportedCasesExcelFile = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Downloads\\" + fileName + ".xlsx");
            objExportedCasesExcelFile.delete();
            oAlert = null; fileName = null; objExportedCasesExcelFile = null;
        }
    }


    /********************************************************
     * Method Name      : exportAndOpenInNewTabForTheClientCompanyWaves()
     * Purpose          : user exports the supplier attachments & validates the same under Modules-->General-->Suppliers-->attachments
     * Author           : Gudi
     * Parameters       : oBrowser, pageName
     * ReturnType       : boolean
     ********************************************************/
    public boolean exportAndOpenInNewTabForTheClientCompanyWaves(WebDriver oBrowser) {
        Alert oAlert = null;
        String fileName = null;
        boolean blnRes = false;
        File objExportedCasesExcelFile = null;
        Map<String, String> waveDetails = null;
        try {
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_clientCompanies_Wave_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            fileName = appDep.exportTheFile(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Waves_Export_Btn);
            blnRes = appDep.exportAndCompareTheExportedDataWithTableGridData(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Wave_Grid, fileName);

            if (blnRes)
                reports.writeResult(oBrowser, "Pass", "Exported 'Wave' excel file cell data and 'Wave' grid cell datas are matching");
            else {
                reports.writeResult(oBrowser, "Fail", "Mis-match in Exported 'Wave' excel file cell data and 'Wave' grid cell data values");
                Assert.fail("Mis-match in Exported 'Wave' excel file cell data and 'Wave' grid cell data values");
            }

            waveDetails = new HashMap<String, String>();
            waveDetails.put("companyName", appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CompanyName_Label + "//span"), "Text"));
            waveDetails.put("waveName", appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Wave_Grid + "//table//tr[contains(@class, 'lines dx-column-lines')])[1]/td[1]"), "Text"));
            waveDetails.put("startDate", appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Wave_Grid + "//table//tr[contains(@class, 'lines dx-column-lines')])[1]/td[2]"), "Text"));
            waveDetails.put("endDate", appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Wave_Grid + "//table//tr[contains(@class, 'lines dx-column-lines')])[1]/td[3]"), "Text"));
            waveDetails.put("waveType", appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Wave_Grid + "//table//tr[contains(@class, 'lines dx-column-lines')])[1]/td[4]"), "Text"));
            waveDetails.put("status", appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Wave_Grid + "//table//tr[contains(@class, 'lines dx-column-lines')])[1]/td[5]"), "Text"));
            waveDetails.put("priority", appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Wave_Grid + "//table//tr[contains(@class, 'lines dx-column-lines')])[1]/td[6]"), "Text"));

            //open in new tab
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Waves_OpenInNewTab_Link));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_GoToWave_Information_Section + "//dl/dd[4]/a"), "Text", waveDetails.get("companyName"), minTimeOut);

            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_GoToWave_Information_Section + "//dl/dd[1]"), "Text").contains(waveDetails.get("waveName")));
            //Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_GoToWave_Information_Section + "//dl/dd[2]"), "Text").contains(appInd.formatDateFromOnetoAnother(waveDetails.get("startDate"), "MM/dd/yyyy", "yyyy-MM-dd")));
            //Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_GoToWave_Information_Section + "//dl/dd[3]"), "Text").contains(appInd.formatDateFromOnetoAnother(waveDetails.get("endDate"), "MM/dd/yyyy", "yyyy-MM-dd")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_GoToWave_Information_Section + "//dl/dd[4]/a"), "Text").contains(waveDetails.get("companyName")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_GoToWave_Information_Section + "//dl/dd[5]"), "Text").contains(waveDetails.get("waveType")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_GoToWave_Information_Section + "//dl/dd[6]"), "Text").contains(waveDetails.get("status")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_GoToWave_Information_Section + "//dl/dd[7]"), "Text").contains(waveDetails.get("priority")));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'exportAndOpenInNewTabForTheClientCompanyWaves()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'exportAndOpenInNewTabForTheClientCompanyWaves()' method. " + e);
            return false;
        } finally {
            objExportedCasesExcelFile = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Downloads\\" + fileName + ".xlsx");
            objExportedCasesExcelFile.delete();
            oAlert = null; fileName = null; objExportedCasesExcelFile = null;
        }
    }


    /********************************************************
     * Method Name      : editClientCompanySuppliers()
     * Purpose          : user edits the client company supplier under Modules-->General-->Companies-->Suppliers
     * Author           : Gudi
     * Parameters       : oBrowser, pageName, supplierData
     * ReturnType       : Map<String, String>
     ********************************************************/
    public Map<String, String> editClientCompanySuppliers(WebDriver oBrowser, String pageName, Map<String, String> supplierData) {
        List<WebElement> oEles = null;
        Actions oAction = null;
        try {
            if (pageName.equalsIgnoreCase("Suppliers")) {
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SuppliersTab_Link));
            } else {
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_HighPriority_Link));
            }
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Edit_Link));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_EditSuppliers_DialogGrid + "//h4"), "Visibility", "", minTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "'Edit supplier' page");
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_EditSuppliers_DialogGrid + "//h4"), "Text").contains("Edit Supplier"));
            oEles = oBrowser.findElements(By.xpath(PayCRM_Modules_GeneralUIPage.obj_VendorID_Field + "//a[@class='search-choice-close']"));
            for (int i = 0; i < oEles.size(); i++) {
                Assert.assertTrue(appInd.clickObject(oBrowser, oEles.get(i)));
                appDep.threadSleep(1000);
            }

            oAction = new Actions(oBrowser);
            oAction.sendKeys(oBrowser.findElement(By.xpath(PayCRM_Modules_GeneralUIPage.obj_VendorID_Field + "//input")), supplierData.get("VendorID")).sendKeys(Keys.ENTER).build().perform();
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCompany_SupplierType_List, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCompany_SupplierType_List, "List", supplierData.get("SupplierType"))));
            supplierData.remove("SupplierType");
            supplierData.put("SupplierType", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCompany_SupplierType_List, "Dropdown"));

            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Suppliers_Status_List, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Suppliers_Status_List, "List", supplierData.get("Status"))));
            supplierData.remove("Status");
            supplierData.put("Status", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Suppliers_Status_List, "Dropdown"));

            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Suppliers_Priority_List, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Suppliers_Priority_List, "List", supplierData.get("Priority"))));
            supplierData.remove("Priority");
            supplierData.put("Priority", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Suppliers_Priority_List, "Dropdown"));

            if (pageName.equalsIgnoreCase("High Priority")) {
                Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_NumberChecks_Edit, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_NumberChecks_Edit, "Value", supplierData.get("CheckNum"))));
                supplierData.remove("CheckNum");
                supplierData.put("CheckNum", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_NumberChecks_Edit, "Value"));
                Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_TotalSpend_Edit, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_TotalSpend_Edit, "Value", supplierData.get("TotalSpend"))));
                supplierData.remove("TotalSpend");
                supplierData.put("TotalSpend", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_TotalSpend_Edit, "Value"));
            }
            reports.writeResult(oBrowser, "Screenshot", "Before submitting the Edit details");

            appInd.scrollToElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_EditSuppliers_DialogGrid + "//button[@name='button']"));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_EditSuppliers_DialogGrid + "//button[@name='button']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_clientCompanies_Wave_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            if (pageName.equalsIgnoreCase("Suppliers")) {
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SuppliersTab_Link));
            } else {
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_HighPriority_Link));
            }
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            supplierData.put("TestPassed", "True");
            return supplierData;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'editClientCompanySuppliers()' method. " + e);
            supplierData.put("TestPassed", "False");
            return supplierData;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'editClientCompanySuppliers()' method. " + e);
            supplierData.put("TestPassed", "False");
            return supplierData;
        } finally {oEles = null; oAction = null;}
    }


    /********************************************************
     * Method Name      : validateClientCompanySupplierDetails()
     * Purpose          : user exports the supplier attachments & validates the same under Modules-->General-->Suppliers-->attachments
     * Author           : Gudi
     * Parameters       : oBrowser, pageName
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateClientCompanySupplierDetails(WebDriver oBrowser, String pageName, Map<String, String> supplierData) {
        String gridLocator = null;
        By objShowLink = null;
        try {
            if (pageName.equalsIgnoreCase("Suppliers")) {
                gridLocator = PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Suppliers_Grid;
                objShowLink = PayCRM_Modules_GeneralUIPage.obj_Queues_Show_Link;
            } else {
                gridLocator = PayCRM_Modules_GeneralUIPage.obj_HighPriority_Grid;
                objShowLink = By.xpath(PayCRM_Modules_GeneralUIPage.obj_HighPriority_Grid + "//a[@title='Show']");
            }
            appDep.waitForTheElementState(oBrowser, By.xpath(gridLocator + "//td[contains(@aria-label, 'Vendor ID, ')]//input"), "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(gridLocator + "//td[contains(@aria-label, 'Vendor ID, ')]//input"), supplierData.get("VendorID")));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "After Editing the '" + pageName + "' details");
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(gridLocator + "//table//tr[1][contains(@class, 'lines dx-column-lines')]/td[2]"), "Text", supplierData.get("VendorID")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(gridLocator + "//table//tr[1][contains(@class, 'lines dx-column-lines')]/td[5]"), "Text").contains(supplierData.get("SupplierType")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(gridLocator + "//table//tr[1][contains(@class, 'lines dx-column-lines')]/td[6]"), "Text", supplierData.get("Status")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(gridLocator + "//table//tr[1][contains(@class, 'lines dx-column-lines')]/td[7]"), "Text", supplierData.get("Priority")));
            if (pageName.equalsIgnoreCase("High Priority")) {
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(gridLocator + "//table//tr[1][contains(@class, 'lines dx-column-lines')]/td[8]"), "Text", supplierData.get("CheckNum")));
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(gridLocator + "//table//tr[1][contains(@class, 'lines dx-column-lines')]/td[9]"), "Text").contains(supplierData.get("TotalSpend")));
            }

            supplierData.put("SupplierName", appInd.getTextOnElement(oBrowser, By.xpath(gridLocator + "//table//tr[1][contains(@class, 'lines dx-column-lines')]/td[1]"), "Text"));
            supplierData.put("TaxID", appInd.getTextOnElement(oBrowser, By.xpath(gridLocator + "//table//tr[1][contains(@class, 'lines dx-column-lines')]/td[3]"), "Text"));

            //Show section
            Assert.assertTrue(appInd.clickObject(oBrowser, objShowLink));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientSupplier_Show_Grid + "//h4"), "Text", "Company Supplier", minTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "After Editing the '" + pageName + "' displaying 'Show' page");
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientSupplier_Show_Grid + "//h4"), "Text", "Company Supplier"));

            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientSupplier_Show_Grid + "//dl/dd[1]"), "Text", supplierData.get("SupplierName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientSupplier_Show_Grid + "//dl/dd[2]"), "Text", supplierData.get("VendorID")));
            if (pageName.equalsIgnoreCase("Suppliers"))
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientSupplier_Show_Grid + "//dl/dd[3]"), "Text", supplierData.get("TaxID")));

            if (pageName.equalsIgnoreCase("High Priority")) {
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientSupplier_Show_Grid + "//dl/dd[11]"), "Text", supplierData.get("CheckNum")));
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientSupplier_Show_Grid + "//dl/dd[12]"), "Text").contains(supplierData.get("TotalSpend")));
            }

            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientSuppier_Show_GeneralInfo + "/following-sibling::div/dl/dd[3]"), "Text", supplierData.get("Status")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientSuppier_Show_GeneralInfo + "/following-sibling::div/dl/dd[4]"), "Text", supplierData.get("Priority")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientSuppier_Show_GeneralInfo + "/following-sibling::div/dl/dd[5]"), "Text").contains(supplierData.get("SupplierType")));

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientSupplier_Show_Grid + "//button[@class='close']")));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateClientCompanySupplierDetails()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateClientCompanySupplierDetails()' method. " + e);
            return false;
        }finally{gridLocator = null; objShowLink = null;}
    }


    /********************************************************
     * Method Name      : editClientCompnaySuppliersAndValidateTheSame()
     * Purpose          : user exports the supplier attachments & validates the same under Modules-->General-->Suppliers-->attachments
     * Author           : Gudi
     * Parameters       : oBrowser, pageName, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean editClientCompnaySuppliersAndValidateTheSame(WebDriver oBrowser, String pageName, DataTable dataTable) {
        List<Map<String, String>> supplierData = null;
        Map<String, String> supplier = null;
        String timeStamp = null;
        try {
            timeStamp = appInd.getDateTime("Shhmmss");
            supplierData = dataTable.asMaps(String.class, String.class);
            supplier = new HashMap<String, String>();
            supplier.put("VendorID", timeStamp);
            supplier.put("SupplierType", supplierData.get(0).get("SupplierType"));
            supplier.put("Status", supplierData.get(0).get("Status"));
            supplier.put("Priority", supplierData.get(0).get("Priority"));

            if (pageName.equalsIgnoreCase("High Priority")) {
                supplier.put("CheckNum", supplierData.get(0).get("CheckNum"));
                supplier.put("TotalSpend", supplierData.get(0).get("TotalSpend"));
            }

            supplier = editClientCompanySuppliers(oBrowser, pageName, supplier);
            Assert.assertTrue(supplier.get("TestPassed").equalsIgnoreCase("True"), "The 'editClientCompanySuppliers()' method was failed");
            Assert.assertTrue(validateClientCompanySupplierDetails(oBrowser, pageName, supplier));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'editClientCompnaySuppliersAndValidateTheSame()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'editClientCompnaySuppliersAndValidateTheSame()' method. " + e);
            return false;
        } finally {
            supplierData = null; supplier = null; timeStamp = null;
        }
    }


    /********************************************************
     * Method Name      : exportAndOpenInNewTabForTheClientCompanySuppliers()
     * Purpose          : user exports the client company Suppliers data & validates the same under Modules-->General-->Companies-->Suppliers
     * Author           : Gudi
     * Parameters       : oBrowser, pageName
     * ReturnType       : boolean
     ********************************************************/
    public boolean exportAndOpenInNewTabForTheClientCompanySuppliers(WebDriver oBrowser, String pageName) {
        Alert oAlert = null;
        String fileName = null;
        boolean blnRes = false;
        File objExportedCasesExcelFile = null;
        Map<String, String> supplierDetails = null;
        By objExportBtn = null;
        String tableGrid = null;
        try {
            if (pageName.equalsIgnoreCase("Suppliers")) {
                objExportBtn = PayCRM_Modules_GeneralUIPage.obj_Suppliers_Exports_Btn;
                tableGrid = PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Suppliers_Grid;
            } else {
                objExportBtn = PayCRM_Modules_GeneralUIPage.obj_HighPriority_Export_Btn;
                tableGrid = PayCRM_Modules_GeneralUIPage.obj_HighPriority_Grid;
            }

            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            fileName = appDep.exportTheFile(oBrowser, objExportBtn);
            blnRes = appDep.exportAndCompareTheExportedDataWithTableGridData(oBrowser, tableGrid, fileName);

            if (blnRes)
                reports.writeResult(oBrowser, "Pass", "Exported 'Company Supplier' excel file cell data and 'Company Supplier' grid cell datas are matching");
            else {
                reports.writeResult(oBrowser, "Fail", "Mis-match in Exported 'Company Supplier' excel file cell data and 'Company Supplier' grid cell data values");
                Assert.fail("Mis-match in Exported 'Company Supplier' excel file cell data and 'Company Supplier' grid cell data values");
            }

            supplierDetails = new HashMap<String, String>();
            supplierDetails.put("SupplierName", appInd.getTextOnElement(oBrowser, By.xpath(tableGrid + "//table//tr[1][contains(@class, 'lines dx-column-lines')]/td[1]"), "Text"));
            supplierDetails.put("VendorID", appInd.getTextOnElement(oBrowser, By.xpath(tableGrid + "//table//tr[1][contains(@class, 'lines dx-column-lines')]/td[2]"), "Text"));
            supplierDetails.put("SupplierType", appInd.getTextOnElement(oBrowser, By.xpath(tableGrid + "//table//tr[1][contains(@class, 'lines dx-column-lines')]/td[5]"), "Text"));
            supplierDetails.put("Status", appInd.getTextOnElement(oBrowser, By.xpath(tableGrid + "//table//tr[1][contains(@class, 'lines dx-column-lines')]/td[6]"), "Text"));
            supplierDetails.put("Priority", appInd.getTextOnElement(oBrowser, By.xpath(tableGrid + "//table//tr[1][contains(@class, 'lines dx-column-lines')]/td[7]"), "Text"));

            //open in new tab
            appInd.scrollToElement(oBrowser, By.xpath(tableGrid + "//a[@title='Open in New Tab']"));
            appInd.moveToElement(oBrowser, By.xpath(tableGrid + "//a[@title='Open in New Tab']"));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(tableGrid + "//a[@title='Open in New Tab']")));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            appDep.waitForTheElementState(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_Suppliers_Show_SuppliersName_Label +"//h3)[1]"), "Text", supplierDetails.get("SupplierName"), minTimeOut);

            reports.writeResult(oBrowser, "Screenshot", "'" + pageName + "': Open in new tab");
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_Suppliers_Show_SuppliersName_Label+"//h3)[1]"), "Text", supplierDetails.get("SupplierName")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientSupplier_Case_Grid + "//dl/dd[1]"), "Text").contains(supplierDetails.get("SupplierName")));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'exportAndOpenInNewTabForTheClientCompanySuppliers()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'exportAndOpenInNewTabForTheClientCompanySuppliers()' method. " + e);
            return false;
        } finally {
            objExportedCasesExcelFile = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Downloads\\" + fileName + ".xlsx");
            objExportedCasesExcelFile.delete();
            oAlert = null; fileName = null; objExportedCasesExcelFile = null;
        }
    }


    /********************************************************
     * Method Name      : validateExportIconWasRemovedFromClientCompanyCaseActivities()
     * Purpose          : user exports the client company Suppliers data & validates the same under Modules-->General-->Companies-->Suppliers
     * Author           : Gudi
     * Parameters       : oBrowser, pageName
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateExportIconWasRemovedFromClientCompanyCaseActivities(WebDriver oBrowser) {
        Alert oAlert = null;
        String fileName = null;
        boolean blnRes = false;
        File objExportedCasesExcelFile = null;
        try {
            Assert.assertTrue(appDep.searchCaseActivitiesByDateRange(oBrowser, "07/24/2022 - 07/31/2022", By.xpath(PayCRM_Modules_GeneralUIPage.obj_CaseActivities_Grid +"//tr[contains(@class, 'row-lines dx-column-lines')]/td[1]")));

            //Export icon was removed from the 'Case Activities'
            blnRes = appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CaseActivities_Export_Btn);

            if (blnRes)
                reports.writeResult(oBrowser, "Pass", "The Export icon was removed from the 'Case Activities' grid");
            else {
                reports.writeResult(oBrowser, "Fail", "The Export icon still exist in the 'Case Activities' grid");
                Assert.fail("The Export icon still exist in the 'Case Activities' grid");
            }
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateExportIconWasRemovedFromClientCompanyCaseActivities()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateExportIconWasRemovedFromClientCompanyCaseActivities()' method. " + e);
            return false;
        } finally {
            objExportedCasesExcelFile = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Downloads\\" + fileName + ".xlsx");
            objExportedCasesExcelFile.delete();
            oAlert = null; fileName = null;
        }
    }


    /********************************************************
     * Method Name      : updateGlobalVariableFromInternalSetup()
     * Purpose          : user updates the Global variable from internal setup
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean updateGlobalVariableFromInternalSetup(WebDriver oBrowser) {
        String companyName = null;
        String timeStamp = null;
        String companyNameUpdated = null;
        try {
            timeStamp = appInd.getDateTime("Shhmmss");
            companyName = appDep.navigateToInternalSetupPage(oBrowser, "HomePage");
            companyNameUpdated = companyName + "_" + timeStamp;
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GlobalVariable_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CompanyName_Edit, "Clickable", "", minTimeOut);

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CompanyName_Edit, companyNameUpdated));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GlobalVariable_Update_Btn));
            appDep.threadSleep(4000);
            oBrowser.navigate().refresh();
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", maxWaitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CompanyName_Header, "Text").contains(companyNameUpdated));
            //Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Value", companyNameUpdated));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'updateGlobalVariableFromInternalSetup()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'updateGlobalVariableFromInternalSetup()' method. " + e);
            return false;
        } finally {
            companyName = null; timeStamp = null; companyNameUpdated = null;
        }
    }


    /********************************************************
     * Method Name      : updatePreferencesFromInternalSetup()
     * Purpose          : user updates the Preferences from internal setup
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean updatePreferencesFromInternalSetup(WebDriver oBrowser, DataTable dataTable) {
        List<Map<String, String>> preferencesData = null;
        Map<String, String> preferences = null;
        String timeStamp = null;
        String companyName = null;
        try {
            timeStamp = appInd.getDateTime("Shhmmss");
            preferencesData = dataTable.asMaps(String.class, String.class);
            preferences = new HashMap<String, String>();
            preferences.put("TINValidation", preferencesData.get(0).get("TINValidation"));
            preferences.put("BypassValidation", preferencesData.get(0).get("BypassValidation"));
            preferences.put("AutoEnroll", preferencesData.get(0).get("AutoEnroll"));
            preferences.put("ProgramName", preferencesData.get(0).get("ProgramName") + timeStamp);
            preferences.put("ProgramEmail", preferencesData.get(0).get("ProgramEmail"));
            preferences.put("ClientMicrosite", preferencesData.get(0).get("ClientMicrosite"));

            companyName = appDep.navigateToInternalSetupPage(oBrowser, "HomePage");
            reports.writeResult(oBrowser, "Screenshot", "Account setup page");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Preferences_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientMicrosite_Edit, "Clickable", "", minTimeOut);
            appInd.scrollToThePage(oBrowser, "Bottom");
            reports.writeResult(oBrowser, "Screenshot", "Before editing the 'Preferences' values");
            Assert.assertTrue(appInd.selectObjectByValue(oBrowser, PayCRM_Modules_GeneralUIPage.obj_TINRequired_List, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_TINRequired_List, "List", preferences.get("TINValidation"))));
            preferences.remove("TINValidation");
            preferences.put("TINValidation", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_TINRequired_List, "Dropdown"));

            Assert.assertTrue(appInd.selectObjectByValue(oBrowser, PayCRM_Modules_GeneralUIPage.obj_BypassValidation_List, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_BypassValidation_List, "List", preferences.get("BypassValidation"))));
            preferences.remove("BypassValidation");
            preferences.put("BypassValidation", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_BypassValidation_List, "Dropdown"));

            Assert.assertTrue(appInd.selectObjectByValue(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AutoEnroll_List, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AutoEnroll_List, "List", preferences.get("AutoEnroll"))));
            preferences.remove("AutoEnroll");
            preferences.put("AutoEnroll", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AutoEnroll_List, "Dropdown"));

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ProgramName_Edit, preferences.get("ProgramName")));

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ProgramEmail_Edit, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ProgramEmail_Edit, "Value", preferences.get("ProgramEmail"))));
            preferences.remove("ProgramEmail");
            preferences.put("ProgramEmail", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ProgramEmail_Edit, "Value"));

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientMicrosite_Edit, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientMicrosite_Edit, "Value", preferences.get("ClientMicrosite"))));
            preferences.remove("ClientMicrosite");
            preferences.put("ClientMicrosite", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientMicrosite_Edit, "Value"));
            reports.writeResult(oBrowser, "Screenshot", "After editing the 'Preferences' values");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Preferences_Update_Btn));
            appDep.threadSleep(4000);
            oBrowser.navigate().refresh();
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", maxWaitTimeOut);
            appDep.navigateToInternalSetupPage(oBrowser, "AccountSetup");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Preferences_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientMicrosite_Edit, "Clickable", "", minTimeOut);

            //Validate preferences values are saved
            appInd.scrollToThePage(oBrowser, "Bottom");
            reports.writeResult(oBrowser, "Screenshot", "The updated 'Preferences' values after updating");
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_TINRequired_List, "Dropdown").contains(preferences.get("TINValidation")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_BypassValidation_List, "Dropdown").contains(preferences.get("BypassValidation")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AutoEnroll_List, "Dropdown").contains(preferences.get("AutoEnroll")));

            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ProgramName_Edit, "Value").contains(preferences.get("ProgramName")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ProgramEmail_Edit, "Value").contains(preferences.get("ProgramEmail")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientMicrosite_Edit, "Value").contains(preferences.get("ClientMicrosite")));
            oBrowser.navigate().refresh();
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", maxWaitTimeOut);
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'updatePreferencesFromInternalSetup()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'updatePreferencesFromInternalSetup()' method. " + e);
            return false;
        } finally {
            preferencesData = null; preferences = null; timeStamp = null; companyName = null;
        }
    }


    /********************************************************
     * Method Name      : updateExpectedPercentagesFromInternalSetup()
     * Purpose          : user updates the expected percentages from internal setup
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean updateExpectedPercentagesFromInternalSetup(WebDriver oBrowser, DataTable dataTable) {
        List<Map<String, String>> expectedData = null;
        Map<String, String> expected = null;
        try {
            expectedData = dataTable.asMaps(String.class, String.class);
            expected = new HashMap<String, String>();
            expected.put("ClientRebatePercentage", expectedData.get(0).get("ClientRebatePercentage"));
            expected.put("ClientKeepPercentage", expectedData.get(0).get("ClientKeepPercentage"));
            expected.put("AchPlusDiscountPercentage", expectedData.get(0).get("AchPlusDiscountPercentage"));
            expected.put("AverageCheckCost", expectedData.get(0).get("AverageCheckCost"));

            appDep.navigateToInternalSetupPage(oBrowser, "AccountSetup");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Expected_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientRebatePercentage_Edit, "Clickable", "", minTimeOut);

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientRebatePercentage_Edit, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ProgramEmail_Edit, "Value", expected.get("ClientRebatePercentage"))));
            expected.remove("ClientRebatePercentage");
            expected.put("ClientRebatePercentage", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientRebatePercentage_Edit, "Value"));

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientKeepPercentage_Edit, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientKeepPercentage_Edit, "Value", expected.get("ClientKeepPercentage"))));
            expected.remove("ClientKeepPercentage");
            expected.put("ClientKeepPercentage", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientKeepPercentage_Edit, "Value"));

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AchPlusDiscountPercentage_Edit, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AchPlusDiscountPercentage_Edit, "Value", expected.get("AchPlusDiscountPercentage"))));
            expected.remove("AchPlusDiscountPercentage");
            expected.put("AchPlusDiscountPercentage", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AchPlusDiscountPercentage_Edit, "Value"));

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AverageCheckCost_Edit, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AverageCheckCost_Edit, "Value", expected.get("AverageCheckCost"))));
            expected.remove("AverageCheckCost");
            expected.put("AverageCheckCost", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AverageCheckCost_Edit, "Value"));

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Expected_Update_Btn));
            appDep.threadSleep(4000);
            oBrowser.navigate().refresh();
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", maxWaitTimeOut);

            appInd.scrollToThePage(oBrowser, "Top");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SolutionDesigns_Link));
            appDep.threadSleep(4000);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_Link));
            appDep.threadSleep(4000);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_VirtualCardRebatePercentage_Label, "Text", expected.get("ClientRebatePercentage") + "%"));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ACHPlusDiscountOnly_Label, "Text", expected.get("AchPlusDiscountPercentage") + "%"));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AverageCheckCost_Label, "Text", "$" + expected.get("AverageCheckCost")));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ACHPlusKeepPercentage_Label, "Text", expected.get("ClientKeepPercentage") + "%"));
            oBrowser.navigate().refresh();
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", maxWaitTimeOut);
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'updateExpectedPercentagesFromInternalSetup()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'updateExpectedPercentagesFromInternalSetup()' method. " + e);
            return false;
        } finally {expectedData = null; expected = null;}
    }


    /********************************************************
     * Method Name      : updateClientRebatePercentageFromInternalSetup()
     * Purpose          : user updates the expected percentages from internal setup
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean updateClientRebatePercentageFromInternalSetup(WebDriver oBrowser, DataTable dataTable) {
        List<Map<String, String>> rebateData = null;
        Map<String, String> rebate = null;
        try {
            rebateData = dataTable.asMaps(String.class, String.class);
            rebate = new HashMap<String, String>();
            rebate.put("TypicalClientRebatePercentage", rebateData.get(0).get("TypicalClientRebatePercentage"));
            appDep.navigateToInternalSetupPage(oBrowser, "AccountSetup");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_TypicalClientRebatePercentage_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_TypicalClientRebatePercentage_Edit, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_TypicalClientRebatePercentage_Edit, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_TypicalClientRebatePercentage_Edit, "Value", rebate.get("TypicalClientRebatePercentage"))));
            rebate.remove("TypicalClientRebatePercentage");
            rebate.put("TypicalClientRebatePercentage", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_TypicalClientRebatePercentage_Edit, "Value"));
            reports.writeResult(oBrowser, "Screenshot", "modifying Rebate Percentage value");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_TypicalClientRebatePercentage_Update_Btn));
            appDep.threadSleep(4000);

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SolutionDesigns_Link));
            appDep.threadSleep(4000);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_OtherProvidersApproach_Link));
            appDep.threadSleep(4000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_TypicalClientRebatePercentage_Label, "Text", rebate.get("TypicalClientRebatePercentage") + "%", minTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Validating Rebate Percentage value under 'Other Provider' tab ");
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_TypicalClientRebatePercentage_Label, "Text", rebate.get("TypicalClientRebatePercentage") + "%"));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'updateClientRebatePercentageFromInternalSetup()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'updateClientRebatePercentageFromInternalSetup()' method. " + e);
            return false;
        } finally {rebateData = null; rebate = null;}
    }


    /********************************************************
     * Method Name      : updateCorcentricPaymentMixFromInternalSetup()
     * Purpose          : user updates the Corcentric Payment Mix values from internal setup
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean updateCorcentricPaymentMixFromInternalSetup(WebDriver oBrowser, DataTable dataTable) {
        List<Map<String, String>> paymentMixData = null;
        Map<String, String> paymentMix = null;
        String achBasicRevenue = null;
        String achPlusRevenue = null;
        String checkRevenue = null;
        String virtualCardRevenue = null;
        try {
            paymentMixData = dataTable.asMaps(String.class, String.class);
            paymentMix = new HashMap<String, String>();
            paymentMix.put("AchBasic", paymentMixData.get(0).get("AchBasic"));
            paymentMix.put("AchBasicRevenue", paymentMixData.get(0).get("AchBasicRevenue"));
            paymentMix.put("AchPlus", paymentMixData.get(0).get("AchPlus"));
            paymentMix.put("AchPlusRevenue", paymentMixData.get(0).get("AchPlusRevenue"));
            paymentMix.put("Check", paymentMixData.get(0).get("Check"));
            paymentMix.put("CheckRevenue", paymentMixData.get(0).get("CheckRevenue"));
            paymentMix.put("VirtualCard", paymentMixData.get(0).get("VirtualCard"));
            paymentMix.put("VirualCardRevenue", paymentMixData.get(0).get("VirualCardRevenue"));

            appDep.navigateToInternalSetupPage(oBrowser, "HomePage");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CorcentricPaymentMix_Link));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CorcentricPaymentMix_Grid + "//td[contains(text(), 'Virtual Card')]/following-sibling::td/input[@id='value']"), "Clickable", "", waitTimeOut);

            reports.writeResult(oBrowser, "Screenshot", "Before modifying the 'Corcentric Payment Mix' data");
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CorcentricPaymentMix_Grid + "//td[contains(text(), 'Ach Basic')]/following-sibling::td/input[@id='value']"), appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CorcentricPaymentMix_Grid + "//td[contains(text(), 'Ach Basic')]/following-sibling::td/input[@id='value']"), "Value", paymentMix.get("AchBasic"))));
            paymentMix.remove("AchBasic");
            paymentMix.put("AchBasic", appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CorcentricPaymentMix_Grid + "//td[contains(text(), 'Ach Basic')]/following-sibling::td/input[@id='value']"), "Value"));

            achBasicRevenue = appInd.clickCheckboxBasedOnPreviousValue(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CorcentricPaymentMix_Grid + "//td[contains(text(), 'Ach Basic')]/following-sibling::td//input[@id='revenue_generating']"), paymentMix.get("AchBasicRevenue"));
            paymentMix.remove("AchBasicRevenue");
            paymentMix.put("AchBasicRevenue", achBasicRevenue);

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CorcentricPaymentMix_Grid + "//td[contains(text(), 'Ach Plus')]/following-sibling::td/input[@id='value']"), appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CorcentricPaymentMix_Grid + "//td[contains(text(), 'Ach Plus')]/following-sibling::td/input[@id='value']"), "Value", paymentMix.get("AchPlus"))));
            paymentMix.remove("AchPlus");
            paymentMix.put("AchPlus", appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CorcentricPaymentMix_Grid + "//td[contains(text(), 'Ach Plus')]/following-sibling::td/input[@id='value']"), "Value"));

            achPlusRevenue = appInd.clickCheckboxBasedOnPreviousValue(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CorcentricPaymentMix_Grid + "//td[contains(text(), 'Ach Plus')]/following-sibling::td//input[@id='revenue_generating']"), paymentMix.get("AchPlusRevenue"));
            paymentMix.remove("AchPlusRevenue");
            paymentMix.put("AchPlusRevenue", achPlusRevenue);

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CorcentricPaymentMix_Grid + "//td[contains(text(), 'Check')]/following-sibling::td/input[@id='value']"), appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CorcentricPaymentMix_Grid + "//td[contains(text(), 'Check')]/following-sibling::td/input[@id='value']"), "Value", paymentMix.get("Check"))));
            paymentMix.remove("Check");
            paymentMix.put("Check", appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CorcentricPaymentMix_Grid + "//td[contains(text(), 'Check')]/following-sibling::td/input[@id='value']"), "Value"));

            checkRevenue = appInd.clickCheckboxBasedOnPreviousValue(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CorcentricPaymentMix_Grid + "//td[contains(text(), 'Check')]/following-sibling::td//input[@id='revenue_generating']"), paymentMix.get("CheckRevenue"));
            paymentMix.remove("CheckRevenue");
            paymentMix.put("CheckRevenue", checkRevenue);

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CorcentricPaymentMix_Grid + "//td[contains(text(), 'Virtual Card')]/following-sibling::td/input[@id='value']"), appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CorcentricPaymentMix_Grid + "//td[contains(text(), 'Virtual Card')]/following-sibling::td/input[@id='value']"), "Value", paymentMix.get("VirtualCard"))));
            paymentMix.remove("VirtualCard");
            paymentMix.put("VirtualCard", appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CorcentricPaymentMix_Grid + "//td[contains(text(), 'Virtual Card')]/following-sibling::td/input[@id='value']"), "Value"));

            virtualCardRevenue = appInd.clickCheckboxBasedOnPreviousValue(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CorcentricPaymentMix_Grid + "//td[contains(text(), 'Virtual Card')]/following-sibling::td//input[@id='revenue_generating']"), paymentMix.get("VirualCardRevenue"));
            paymentMix.remove("VirualCardRevenue");
            paymentMix.put("VirualCardRevenue", virtualCardRevenue);

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CorcentricPaymentMix_Update_Btn));
            appDep.threadSleep(4000);
            reports.writeResult(oBrowser, "Screenshot", "After modifying the 'Corcentric Payment Mix' data");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SolutionDesigns_Link));
            appDep.threadSleep(4000);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_Link));
            appDep.threadSleep(4000);
            appInd.scrollToThePage(oBrowser, "Bottom");
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PostCentricOptimization_Expand_Btn, "Clickable", "", minTimeOut);
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PostCorcentricOptimization_Grid + "//table//tr//span[@data-original-title='Check']"), "Visibility", "", waitTimeOut);
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Validating modified 'Corcentric Payment Mix' data under 'Results With Corcentric' tab");
            Assert.assertTrue(appDep.validateCorcentricPaymentMixDataForPaymentTypes(oBrowser, "Virtual Card", paymentMix.get("VirtualCard") + "%", paymentMix.get("VirualCardRevenue")));
            Assert.assertTrue(appDep.validateCorcentricPaymentMixDataForPaymentTypes(oBrowser, "ACH Plus", paymentMix.get("AchPlus") + "%", paymentMix.get("AchPlusRevenue")));
            Assert.assertTrue(appDep.validateCorcentricPaymentMixDataForPaymentTypes(oBrowser, "ACH Basic", paymentMix.get("AchBasic") + "%", paymentMix.get("AchBasicRevenue")));
            Assert.assertTrue(appDep.validateCorcentricPaymentMixDataForPaymentTypes(oBrowser, "Check", paymentMix.get("Check") + "%", paymentMix.get("CheckRevenue")));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'updateCorcentricPaymentMixFromInternalSetup()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'updateCorcentricPaymentMixFromInternalSetup()' method. " + e);
            return false;
        } finally {
            paymentMixData = null; paymentMix = null; achBasicRevenue = null; achPlusRevenue = null; checkRevenue = null; virtualCardRevenue = null;
        }
    }


    /********************************************************
     * Method Name      : updateCurrentPaymentMixFromInternalSetup()
     * Purpose          : user updates the Current Payment Mix values from internal setup
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean updateCurrentPaymentMixFromInternalSetup(WebDriver oBrowser, DataTable dataTable) {
        List<Map<String, String>> paymentMixData = null;
        Map<String, String> paymentMix = null;
        WebElement objEle = null;
        String check = null;
        String ach = null;
        String mixed = null;
        try {
            paymentMixData = dataTable.asMaps(String.class, String.class);
            paymentMix = new HashMap<String, String>();
            paymentMix.put("Check", paymentMixData.get(0).get("Check"));
            paymentMix.put("Wire", paymentMixData.get(0).get("Wire"));
            paymentMix.put("Ach", paymentMixData.get(0).get("Ach"));
            paymentMix.put("Mixed", paymentMixData.get(0).get("Mixed"));
            paymentMix.put("ForeignExchange", paymentMixData.get(0).get("ForeignExchange"));
            paymentMix.put("VirtualCard", paymentMixData.get(0).get("VirtualCard"));
            paymentMix.put("EnhancedAch", paymentMixData.get(0).get("EnhancedAch"));

            appDep.navigateToInternalSetupPage(oBrowser, "HomePage");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CurrentPaymentMix_Link));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Before modifying the 'Current Payment Mix' values");
            check = appInd.clickCheckboxBasedOnPreviousValue(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CurrentPaymentMix_Grid + "//td[contains(text(), 'Check')]/following-sibling::td//input[@id='revenue_generating']"), paymentMix.get("Check"));
            paymentMix.remove("Check");
            paymentMix.put("Check", check);

            ach = appInd.clickCheckboxBasedOnPreviousValue(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CurrentPaymentMix_Grid + "//td[contains(text(), 'Ach')]/following-sibling::td//input[@id='revenue_generating']"), paymentMix.get("Ach"));
            paymentMix.remove("Ach");
            paymentMix.put("Ach", ach);

            mixed = appInd.clickCheckboxBasedOnPreviousValue(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CurrentPaymentMix_Grid + "//td[contains(text(), 'Mixed')]/following-sibling::td//input[@id='revenue_generating']"), paymentMix.get("Mixed"));
            paymentMix.remove("mixed");
            paymentMix.put("mixed", mixed);

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CurrentPaymentMix_Btn));
            appDep.threadSleep(4000);
            reports.writeResult(oBrowser, "Screenshot", "After modifying the 'Current Payment Mix' values");
            oBrowser.navigate().refresh();
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", maxWaitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SolutionDesigns_Link));
            appDep.threadSleep(4000);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CurrentState_Link));
            appDep.threadSleep(4000);

            Assert.assertTrue(appDep.validateCurrentPaymentMixDataForPaymentTypes(oBrowser, "Mixed", paymentMix.get("mixed")));
            Assert.assertTrue(appDep.validateCurrentPaymentMixDataForPaymentTypes(oBrowser, "Check", paymentMix.get("Check")));
            Assert.assertTrue(appDep.validateCurrentPaymentMixDataForPaymentTypes(oBrowser, "ACH", paymentMix.get("Ach")));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'updateCurrentPaymentMixFromInternalSetup()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'updateCurrentPaymentMixFromInternalSetup()' method. " + e);
            return false;
        } finally {
            paymentMixData = null; paymentMix = null; objEle = null; check = null; ach = null; mixed = null;
        }
    }


    /********************************************************
     * Method Name      : updateCardNetworkMatchConfidenceFromInternalSetup()
     * Purpose          : user updates the Card Network Match Confidence values from internal setup
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean updateCardNetworkMatchConfidenceFromInternalSetup(WebDriver oBrowser, DataTable dataTable) {
        List<Map<String, String>> cardNetworkData = null;
        Map<String, String> cardNetwork = null;
        try {
            cardNetworkData = dataTable.asMaps(String.class, String.class);
            cardNetwork = new HashMap<String, String>();
            cardNetwork.put("High", cardNetworkData.get(0).get("High"));
            cardNetwork.put("Medium", cardNetworkData.get(0).get("Medium"));
            cardNetwork.put("Low", cardNetworkData.get(0).get("Low"));
            cardNetwork.put("Blank", cardNetworkData.get(0).get("Blank"));

            appDep.navigateToInternalSetupPage(oBrowser, "HomePage");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_cardNetworkMatchConfidence_Link));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_InternalSetup_CardNetworkMatchConfidence_Grid + "//label[@title='High']/following-sibling::div/input"), appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_InternalSetup_CardNetworkMatchConfidence_Grid + "//label[@title='High']/following-sibling::div/input"), "Value", cardNetwork.get("High"))));
            cardNetwork.remove("High");
            cardNetwork.put("High", appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_InternalSetup_CardNetworkMatchConfidence_Grid + "//label[@title='High']/following-sibling::div/input"), "Value"));

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_InternalSetup_CardNetworkMatchConfidence_Grid + "//label[@title='Medium']/following-sibling::div/input"), appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_InternalSetup_CardNetworkMatchConfidence_Grid + "//label[@title='Medium']/following-sibling::div/input"), "Value", cardNetwork.get("Medium"))));
            cardNetwork.remove("Medium");
            cardNetwork.put("Medium", appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_InternalSetup_CardNetworkMatchConfidence_Grid + "//label[@title='Medium']/following-sibling::div/input"), "Value"));

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_InternalSetup_CardNetworkMatchConfidence_Grid + "//label[@title='Low']/following-sibling::div/input"), appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_InternalSetup_CardNetworkMatchConfidence_Grid + "//label[@title='Low']/following-sibling::div/input"), "Value", cardNetwork.get("Low"))));
            cardNetwork.remove("Low");
            cardNetwork.put("Low", appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_InternalSetup_CardNetworkMatchConfidence_Grid + "//label[@title='Low']/following-sibling::div/input"), "Value"));

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_InternalSetup_CardNetworkMatchConfidence_Grid + "//label[@title='(BLANK)']/following-sibling::div/input"), cardNetwork.get("Blank")));

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CardNetworkMatchConfidence_Update_Btn));
            appDep.threadSleep(4000);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SolutionDesigns_Link));
            appDep.threadSleep(4000);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_OtherProvidersApproach_Link));
            appDep.threadSleep(4000);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CardNetworkMatchConfidence_Grid + "//td/span[contains(text(), 'High')]/../following-sibling::td[2]"), "Text", cardNetwork.get("High") + "%"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CardNetworkMatchConfidence_Grid + "//td/span[contains(text(), 'Medium')]/../following-sibling::td[2]"), "Text", cardNetwork.get("Medium") + "%"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CardNetworkMatchConfidence_Grid + "//td/span[contains(text(), 'Low')]/../following-sibling::td[2]"), "Text", cardNetwork.get("Low") + "%"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CardNetworkMatchConfidence_Grid + "//td/span[contains(text(), 'No Match')]/../following-sibling::td[2]"), "Text", cardNetwork.get("Blank") + "%"));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'updateCardNetworkMatchConfidenceFromInternalSetup()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'updateCardNetworkMatchConfidenceFromInternalSetup()' method. " + e);
            return false;
        } finally {cardNetworkData = null; cardNetwork = null;}
    }


    /********************************************************
     * Method Name      : validateTheTestFunctionalityOfPropsectCompany()
     * Purpose          : user validates the prospect company test functionality
     * Author           : Gudi
     * Parameters       : oBrowser, pageName
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateTheTestFunctionalityOfPropsectCompany(WebDriver oBrowser, String pageName) {
        String boxNames[];
        int rowCount = 0;
        Actions oAction = null;
        String totalCostItems = null;
        WebElement objEle = null;
        String riskScore[];
        String initialPaymentScore = null;
        String initialRiskScore = null;
        String finalPaymentScore = null;
        String finalRiskScore = null;
        String initialTotalTransactionCount = null;
        String initialTotalSupplierCount = null;
        String initialTotalTotalSpend = null;
        String initialEstimatedVirtualCard = null;
        String initialACHPlus = null;
        String initialACHBasic = null;
        String initialRemainOnCheck = null;
        String finalTotalTransactionCount = null;
        String finalTotalSupplierCount = null;
        String finalTotalTotalSpend = null;
        String finalEstimatedVirtualCard = null;
        String finalACHPlus = null;
        String finalACHBasic = null;
        String finalRemainOnCheck = null;
        String totalTransactionCount = null;
        String totalSupplierCount = null;
        String totalTotalSpend = null;
        String totalEstimatedVirtualCard = null;
        String totalACHPlus = null;
        String totalACHBasic = null;
        String totalRemainOnCheck = null;
        String corcentric = null;
        String prospectCompany = null;
        By objToggleOnBtn = null;
        By objToggleOffBtn = null;
        String objPaymentScoreToggle = null;
        String obj_EffectiveRebate = null;
        By objEffectiveRebateFormula = null;
        String objRiskScoreGrid = null;
        String objRiskScoreLegend = null;
        String objCorcentricEffortsGrid = null;
        By objScoreCardLink = null;
        String objScorecardGrid = null;
        By objPaymentScoreLabel = null;
        String objPaymentScoreLegend = null;
        By objRiskScoreLabel = null;
        String objCorcentricFeatureGrid = null;
        By objSupplierClearBtn = null;
        By objCardMatchSupplierLink = null;
        By objCardMatchSupplierClearBtn = null;
        String totalSpend = null;
        String estimatedVirtualCard = null;
        String achPlus = null;
        String achBasic = null;
        String remainOnCheck = null;
        List<String> strTotalSpend = null;
        List<String> strEstdVirtualCard = null;
        List<String> strAchPlus = null;
        List<String> strAchBasic = null;
        List<String> strRemainOnCheck = null;
        WebDriver driver = null;
        By objPaymentScoreHeader = null;
        try {
            boolean blnRes = appInd.verifyOptionalElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SolutionDesigns_Link);
            if (!blnRes)
                companyName = appDep.selectTheCompanyBasedOnType(oBrowser, "Prospect");

            companyName = "";
            appDep.threadSleep(5000);
            if (pageName.equalsIgnoreCase("Solution")) {
                Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Solution_Link));
            } else {
                appInd.scrollToThePage(oBrowser, "Top");
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SolutionDesigns_Link));
                appDep.threadSleep(5000);
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_Link));
            }

            appDep.threadSleep(5000);
            if (pageName.equalsIgnoreCase("Solution")) {
                boxNames = "Total Spend#Transaction Count#Checks Eliminated#In Program Spend#Out of Program Spend#Network Virtual Card Matches#Network ACH Plus Matches".split("#");
                for (int i = 0; i < boxNames.length; i++) {
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_DarkBlue_Boxes + "//div[@class='callout callout-dark-purple flex-grow w-auto']//span)[" + (i + 1) + "]"), "Text", boxNames[i]));
                }
            }

            objPaymentScoreLegend = "(//div[@id='solution_payment_gauge']//div[@class='text--body-small font--normal'])";

            if (pageName.equalsIgnoreCase("Solution")) {
                objToggleOnBtn = PayCRM_Modules_GeneralUIPage.obj_Itemized_Toggle_Btn;
                objToggleOffBtn = PayCRM_Modules_GeneralUIPage.obj_Itemized_Toggle_Red_Btn;
                objPaymentScoreToggle = PayCRM_Modules_GeneralUIPage.obj_PaymentScore_ToggleOff_Grid;
                obj_EffectiveRebate = PayCRM_Modules_GeneralUIPage.obj_EffectiveRebate_Section;
                objEffectiveRebateFormula = PayCRM_Modules_GeneralUIPage.obj_Solution_EffectiveRebate_TotalRebate_Label;
                objRiskScoreGrid = PayCRM_Modules_GeneralUIPage.obj_RiskScore_Grid;
                objRiskScoreLegend = PayCRM_Modules_GeneralUIPage.obj_RiskScore_Legend_Grid;
                objCorcentricEffortsGrid = PayCRM_Modules_GeneralUIPage.obj_Solution_CorcentricEfforts_Grid;
                objScoreCardLink = PayCRM_Modules_GeneralUIPage.obj_PaymentScodeCard_Link;
                objScorecardGrid = PayCRM_Modules_GeneralUIPage.obj_PaymentScorecard_Grid;
                objPaymentScoreLabel = PayCRM_Modules_GeneralUIPage.obj_PaymentScorePercentage_Label;
                objRiskScoreLabel = PayCRM_Modules_GeneralUIPage.obj_RiskScorePercentage_Label;
                objCorcentricFeatureGrid = PayCRM_Modules_GeneralUIPage.obj_CorcentricFeatures_Grid;
                objSupplierClearBtn = PayCRM_Modules_GeneralUIPage.obj_SupplierType_clear_Btn;
                objCardMatchSupplierLink = PayCRM_Modules_GeneralUIPage.obj_Top15CardMatchSupplier_Link;
                objCardMatchSupplierClearBtn = PayCRM_Modules_GeneralUIPage.obj_Top15CardMatchSuppliers_Clear_Btn;
                objPaymentScoreHeader = PayCRM_Modules_GeneralUIPage.obj_Solution_PaymentScore_Label;
            } else {
                objToggleOnBtn = PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_Itemized_Toggle;
                objToggleOffBtn = PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_Itemized_Toggle_Red_Btn;
                objPaymentScoreToggle = PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_PaymentScore_ToggleOff_Grid;
                obj_EffectiveRebate = PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_EffectiveRebate_Section;
                objEffectiveRebateFormula = PayCRM_Modules_GeneralUIPage.ResultsWithCorcentric_EffectiveRebate_Label;
                objRiskScoreGrid = PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_RiskScore_Grid;
                objRiskScoreLegend = PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_RiskScore_Legend_Grid;
                objCorcentricEffortsGrid = PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_CorcentricEfforts_Grid;
                objScoreCardLink = PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_PaymentScodeCard_Link;
                objScorecardGrid = PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_PaymentScoreCard_Grid;
                objPaymentScoreLabel = PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_PaymentScorePercentage_Label;
                objRiskScoreLabel = PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_RiskScorePercentage_Label;
                objCorcentricFeatureGrid = PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_CorcentricFeatures_Grid;
                objSupplierClearBtn = PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_SupplierType_clear_Btn;
                objCardMatchSupplierLink = PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_Top15CardMatchSupplier_Link;
                objCardMatchSupplierClearBtn = PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_Top15CardMatchSuppliers_Clear_Btn;
                objPaymentScoreHeader = PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_Solution_PaymentScore_Label;
            }

            Assert.assertTrue(appInd.clickObject(oBrowser, objToggleOnBtn));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ItemizedProgramCosts_Grid + "/following-sibling::table//th[text()='Total Costs']"), "Text", "Total Costs", minTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ItemizedProgramCosts_Grid + "/following-sibling::table//th[text()='Total Costs']"), "Text", "Total Costs"));

            oAction = new Actions(oBrowser);
            rowCount = oBrowser.findElements(By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ItemizedProgramCosts_Grid + "/following-sibling::table)[1]/tbody/tr")).size();
            for (int i = 0; i < rowCount - 1; i++) {
                totalCostItems = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ItemizedProgramCosts_Grid + "/following-sibling::table/tbody/tr[" + (i + 1) + "]/td[1]"), "Text");
                if (totalCostItems.equalsIgnoreCase("vpaxAR") || totalCostItems.equalsIgnoreCase("PrePaid Debit Card") || totalCostItems.equalsIgnoreCase("Supplier Management")) {
                    objEle = appInd.createAndGetWebElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ItemizedProgramCosts_Grid + "/following-sibling::table/tbody/tr[" + (i + 1) + "]/td[1]"));
                } else {
                    objEle = appInd.createAndGetWebElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ItemizedProgramCosts_Grid + "/following-sibling::table/tbody/tr[" + (i + 1) + "]/td[1]/span"));
                }

                oAction.moveToElement(objEle).build().perform();
                appDep.threadSleep(2000);
                totalCostItems = objEle.getText();
                if (totalCostItems.equalsIgnoreCase("vpaxAR") || totalCostItems.equalsIgnoreCase("PrePaid Debit Card") || totalCostItems.equalsIgnoreCase("Supplier Management")) {
                    Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ItemizedProgramCosts_Grid + "/following-sibling::table/tbody/tr[" + (i + 1) + "]/td[1]/span")));
                } else {
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ItemizedProgramCosts_Grid + "/following-sibling::table/tbody/tr[" + (i + 1) + "]/td[1]/span")));
                }
                reports.writeResult(oBrowser, "Screenshot", "Hover over blue labels in itemized program costs table for the row : '" + (i + 1) + "'\n" + ": " + totalCostItems);
            }
            appInd.scrollToThePage(oBrowser, "Top");
            Assert.assertTrue(appInd.clickObject(oBrowser, objToggleOffBtn));
            appDep.threadSleep(2000);


            //Payment Score
            appDep.threadSleep(2000);
            appInd.compareValues(oBrowser, appInd.getTextOnElement(oBrowser, By.xpath(objPaymentScoreLegend + "[1]"), "Text"), "Below Average");
            appInd.compareValues(oBrowser, appInd.getTextOnElement(oBrowser, By.xpath(objPaymentScoreLegend + "[2]"), "Text"), "Average");
            appInd.compareValues(oBrowser, appInd.getTextOnElement(oBrowser, By.xpath(objPaymentScoreLegend + "[3]"), "Text"), "Above Average");
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Payment Score section with different levels of payment scores");
            appDep.threadSleep(2000);


            //Effective Rebate Card Title
            objEle = appInd.createAndGetWebElement(oBrowser, By.xpath(obj_EffectiveRebate + "//label/span"));
            oAction.moveToElement(objEle).perform();
            String strText = oBrowser.findElement(By.xpath(obj_EffectiveRebate + "//label/span")).getAttribute("data-content");
            Assert.assertTrue(strText.contains("Virtual Card Rebate + (ACH Plus Rebate * ACH Plus Keep Percentage)"));
            Assert.assertTrue(strText.contains("÷"));
            Assert.assertTrue(strText.contains("Total Spend"));
            Assert.assertTrue(strText.contains("= Effective Rebate"));
            reports.writeResult(oBrowser, "Screenshot", "Mouse over on 'Effective Rebate'");
            Assert.assertTrue(appInd.clickObject(oBrowser, objPaymentScoreHeader));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(obj_EffectiveRebate+"//label/span[@class='switch-sm-label']")));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, objEffectiveRebateFormula, "Text", "Total Rebate", minTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "After clicking the toggle button on 'Effective Rebate'");


            //Risk Score
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(objRiskScoreGrid + "//span[@class='switch-sm-label']")));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "After clicking the red toggle button on 'Risk Score'");
            riskScore = "Minimal#Low#Slight#Enhanced#High#Critical#Catastrophic".split("#");
            for (int i = 0; i < riskScore.length; i++) {
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(objRiskScoreLegend + "//ul/li[" + (i + 1) + "]"), "Text").contains(riskScore[i]));
            }
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(objRiskScoreGrid + "//span[@class='switch-sm-handle']")));
            appDep.threadSleep(2000);


            //Corcentric Effort
            objEle = appInd.createAndGetWebElement(oBrowser, By.xpath(objCorcentricEffortsGrid + "//*[text()='Corcentric']/../../../parent::*[@transform]"));
            corcentric = objEle.getText();
            oAction.moveToElement(objEle).build().perform();
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Corcentric Efforts graph");

            objEle = appInd.createAndGetWebElement(oBrowser, By.xpath(objCorcentricEffortsGrid + "//*[contains(text(), '" + companyName + "')]/../../../parent::*[@transform]"));
            prospectCompany = objEle.getText();
            oAction.moveToElement(objEle).build().perform();
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Corcentric Efforts graph");

            Assert.assertTrue(appInd.clickObject(oBrowser, objScoreCardLink));
            appDep.waitForTheElementState(oBrowser, By.xpath(objScorecardGrid + "//label[text()='Other']"), "Visibility", "", minTimeOut);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(objScorecardGrid + "//label[text()='Corcentric']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(objScorecardGrid + "//label[text()='Current']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(objScorecardGrid + "//label[text()='Other']")));


            //In Post Corcentric Optimization, hover over tooltip headers
            Assert.assertTrue(appDep.mouseOverAndValidateTheToolTip(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_PostCorcentricOptimization_Grid + "//table//th/span)[1]"), "Total annual dollar amount of Vendors provided", "Spend"));
            Assert.assertTrue(appDep.mouseOverAndValidateTheToolTip(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_PostCorcentricOptimization_Grid + "//table//th/span)[2]"), "Total annual check count of Vendors", "Transaction Count"));
            Assert.assertTrue(appDep.mouseOverAndValidateTheToolTip(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_PostCorcentricOptimization_Grid + "//table//th/span)[3]"), "Total number of Suppliers", "Supplier Count"));
            Assert.assertTrue(appDep.mouseOverAndValidateTheToolTip(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_PostCorcentricOptimization_Grid + "//table//th/span)[4]"), "Percentage of suppliers accepting payment type", "% of Suppliers"));
            Assert.assertTrue(appDep.mouseOverAndValidateTheToolTip(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_PostCorcentricOptimization_Grid + "//table//th/span)[5]"), "Percentage of spend that generates revenue", "% of Rev. Generating Spend"));
            Assert.assertTrue(appDep.mouseOverAndValidateTheToolTip(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_PostCorcentricOptimization_Grid + "//table//th/span)[6]"), "Percentage of transactions that generates revenue", "% of Rev. Generating Transactions"));


            //In checkbox table next to Post Corcentric Optimization, select and deselect items of your choosing.
            initialPaymentScore = appInd.getTextOnElement(oBrowser, objPaymentScoreLabel, "Text");
            initialRiskScore = appInd.getTextOnElement(oBrowser, objRiskScoreLabel, "Text");
            for (int i = 0; i <= 2; i++) {
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("(" + objCorcentricFeatureGrid + "//tr/td[@class='text-center']/input)[" + (i + 1) + "]")));
            }
            finalPaymentScore = appInd.getTextOnElement(oBrowser, objPaymentScoreLabel, "Text");
            finalRiskScore = appInd.getTextOnElement(oBrowser, objRiskScoreLabel, "Text");
            Assert.assertTrue((!initialPaymentScore.equals(finalPaymentScore)), "Initial and final payment scores are still same");
            Assert.assertTrue((!initialRiskScore.equals(finalRiskScore)), "Initial and final risk scores are still same");


            //In Supplier Types table, click several rows.
            appInd.scrollToThePage(oBrowser, "Bottom");
            initialTotalTransactionCount = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierTypes_Statistics_Grid + "/td[@id='total_transaction_count']"), "Text");
            initialTotalSupplierCount = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierTypes_Statistics_Grid + "/td[@id='total_supplier_count']"), "Text");
            initialTotalTotalSpend = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierTypes_Statistics_Grid + "/td[@id='total_total_spend']"), "Text");
            initialEstimatedVirtualCard = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierTypes_Statistics_Grid + "/td[@id='total_estimated_card']"), "Text");
            initialACHPlus = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierTypes_Statistics_Grid + "/td[@id='total_vpax_plus']"), "Text");
            initialACHBasic = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierTypes_Statistics_Grid + "/td[@id='total_vpax_lite']"), "Text");
            initialRemainOnCheck = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierTypes_Statistics_Grid + "/td[@id='total_remain_on_check']"), "Text");
            int transactionCount = 0;
            int supplierCount = 0;
            reports.writeResult(oBrowser, "Screenshot", "'Supplier Type' Table Grid");
            strTotalSpend = new ArrayList<String>();
            strEstdVirtualCard = new ArrayList<String>();
            strAchPlus = new ArrayList<String>();
            strAchBasic = new ArrayList<String>();
            strRemainOnCheck = new ArrayList<String>();
            for (int i = 0; i < 2; i++) {
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierType_Grid + "//tr[" + (i + 1) + "]/td[1]")));
                appDep.threadSleep(1000);
                transactionCount += Double.parseDouble(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierType_Grid + "//tr[" + (i + 1) + "]/td[3]"), "Text").replace(",", ""));

                supplierCount += Double.parseDouble(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierType_Grid + "//tr[" + (i + 1) + "]/td[4]"), "Text").replace(",", ""));

                strTotalSpend.add(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierType_Grid + "//tr[" + (i + 1) + "]/td[5]"), "Text").replace("$", "").replace(",", ""));

                strEstdVirtualCard.add(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierType_Grid + "//tr[" + (i + 1) + "]/td[6]"), "Text").replace("$", "").replace(",", ""));

                strAchPlus.add(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierType_Grid + "//tr[" + (i + 1) + "]/td[7]"), "Text").replace("$", "").replace(",", ""));

                strAchBasic.add(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierType_Grid + "//tr[" + (i + 1) + "]/td[8]"), "Text").replace("$", "").replace(",", ""));

                strRemainOnCheck.add(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierType_Grid + "//tr[" + (i + 1) + "]/td[9]"), "Text").replace("$", "").replace(",", ""));
            }

            totalTransactionCount = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierTypes_Statistics_Grid + "/td[@id='total_transaction_count']"), "Text").replace(",", "");
            totalSupplierCount = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierTypes_Statistics_Grid + "/td[@id='total_supplier_count']"), "Text").replace(",", "");
            totalTotalSpend = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierTypes_Statistics_Grid + "/td[@id='total_total_spend']"), "Text").replace("$", "").replace(",", "");
            totalEstimatedVirtualCard = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierTypes_Statistics_Grid + "/td[@id='total_estimated_card']"), "Text").replace("$", "").replace(",", "");
            totalACHPlus = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierTypes_Statistics_Grid + "/td[@id='total_vpax_plus']"), "Text").replace("$", "").replace(",", "");
            totalACHBasic = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierTypes_Statistics_Grid + "/td[@id='total_vpax_lite']"), "Text").replace("$", "").replace(",", "");
            totalRemainOnCheck = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierTypes_Statistics_Grid + "/td[@id='total_remain_on_check']"), "Text").replace("$", "").replace(",", "");


            oBrowser.switchTo().newWindow(WindowType.TAB);
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            oBrowser.get("https://www.rapidtables.com/calc/math/Add_Calculator.html");
            appDep.handleCaptcha(oBrowser);
            totalSpend = appDep.calculatorOnline(oBrowser, strTotalSpend);
            estimatedVirtualCard = appDep.calculatorOnline(oBrowser, strEstdVirtualCard);
            achPlus = appDep.calculatorOnline(oBrowser, strAchPlus);
            achBasic = appDep.calculatorOnline(oBrowser, strAchBasic);
            remainOnCheck = appDep.calculatorOnline(oBrowser, strRemainOnCheck);
            appDep.threadSleep(2000);
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);

            Assert.assertTrue((transactionCount == Integer.parseInt(totalTransactionCount)), "Mis-match in the total 'Transaction Count' values for the selected rows");
            Assert.assertTrue((supplierCount == Integer.parseInt(totalSupplierCount)), "Mis-match in the total 'Supplier Count' values for the selected rows");
            Assert.assertTrue(Double.parseDouble(totalTotalSpend) == Double.parseDouble(totalSpend), "Mis-match in the total 'Total Spend' values for the selected rows");
            Assert.assertTrue(Double.parseDouble(totalEstimatedVirtualCard) == Double.parseDouble(estimatedVirtualCard), "Mis-match in the total 'Estimated Virtual Card' values for the selected rows");
            Assert.assertTrue(Double.parseDouble(totalACHPlus) == Double.parseDouble(achPlus), "Mis-match in the total 'ACH Plus' values for the selected rows");
            Assert.assertTrue(Double.parseDouble(totalACHBasic) == Double.parseDouble(achBasic), "Mis-match in the total 'ACH Basic' values for the selected rows");
            Assert.assertTrue(Double.parseDouble(totalRemainOnCheck) == Double.parseDouble(remainOnCheck), "Mis-match in the total 'Remain On Check' values for the selected rows");


            //verify 'Clear' button is visible
            appDep.waitForTheElementState(oBrowser, objSupplierClearBtn, "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, objSupplierClearBtn));
            Assert.assertTrue(appInd.clickObject(oBrowser, objSupplierClearBtn));
            appDep.threadSleep(2000);

            finalTotalTransactionCount = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierTypes_Statistics_Grid + "/td[@id='total_transaction_count']"), "Text");
            finalTotalSupplierCount = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierTypes_Statistics_Grid + "/td[@id='total_supplier_count']"), "Text");
            finalTotalTotalSpend = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierTypes_Statistics_Grid + "/td[@id='total_total_spend']"), "Text");
            finalEstimatedVirtualCard = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierTypes_Statistics_Grid + "/td[@id='total_estimated_card']"), "Text");
            finalACHPlus = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierTypes_Statistics_Grid + "/td[@id='total_vpax_plus']"), "Text");
            finalACHBasic = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierTypes_Statistics_Grid + "/td[@id='total_vpax_lite']"), "Text");
            finalRemainOnCheck = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierTypes_Statistics_Grid + "/td[@id='total_remain_on_check']"), "Text");

            Assert.assertEquals(initialTotalTransactionCount, finalTotalTransactionCount, "Both initial '" + initialTotalTransactionCount + "' & final '" + finalTotalTransactionCount + "' Transaction Counts are not matching");
            Assert.assertEquals(initialTotalSupplierCount, finalTotalSupplierCount, "Both initial '" + initialTotalSupplierCount + "' & final '" + finalTotalSupplierCount + "' Supplier Counts are not matching");
            Assert.assertEquals(initialTotalTotalSpend, finalTotalTotalSpend, "Both initial '" + initialTotalTotalSpend + "' & final '" + finalTotalTotalSpend + "' Total Spend Counts are not matching");
            Assert.assertEquals(initialEstimatedVirtualCard, finalEstimatedVirtualCard, "Both initial '" + initialEstimatedVirtualCard + "' & final '" + finalEstimatedVirtualCard + "' Estimated Virtual Card Counts are not matching");
            Assert.assertEquals(initialACHPlus, finalACHPlus, "Both initial '" + initialACHPlus + "' & final '" + finalACHPlus + "' ACH Plus Counts are not matching");
            Assert.assertEquals(initialACHBasic, finalACHBasic, "Both initial '" + initialACHBasic + "' & final '" + finalACHBasic + "' ACH Basic Counts are not matching");
            Assert.assertEquals(initialRemainOnCheck, finalRemainOnCheck, "Both initial '" + initialRemainOnCheck + "' & final '" + finalRemainOnCheck + "' Remain On Check Counts are not matching");


            //In Top 15 Card Match Suppliers table, click several rows.
            Assert.assertTrue(appInd.clickObject(oBrowser, objCardMatchSupplierLink));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "'Top 15 Card Match Supplier' Table Grid");
            initialTotalTransactionCount = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Top15CardMatchSupplier_Grid + "/td[@id='top_card_match_total_count']"), "Text");
            initialTotalSupplierCount = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Top15CardMatchSupplier_Grid + "/td[@id='top_card_match_total_spend']"), "Text");

            transactionCount = 0;
            totalSpend = null;
            strTotalSpend = new ArrayList<String>();
            for (int i = 0; i < 2; i++) {
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Top15CardMatchSupplier_Rows + "//tr[" + (i + 1) + "]/td[1]")));
                appDep.threadSleep(1000);
                transactionCount += Double.parseDouble(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Top15CardMatchSupplier_Rows + "//tr[" + (i + 1) + "]/td[5]"), "Text").replace(",", ""));

                strTotalSpend.add(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Top15CardMatchSupplier_Rows + "//tr[" + (i + 1) + "]/td[6]"), "Text").replace("$", "").replace(",", ""));
            }

            totalTransactionCount = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Top15CardMatchSupplier_Grid + "/td[@id='top_card_match_total_count']"), "Text").replace(",", "");
            totalTotalSpend = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Top15CardMatchSupplier_Grid + "/td[@id='top_card_match_total_spend']"), "Text").replace("$", "").replace(",", "");

            oBrowser.switchTo().newWindow(WindowType.TAB);
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            oBrowser.get("https://www.rapidtables.com/calc/math/Add_Calculator.html");
            appDep.handleCaptcha(oBrowser);
            totalSpend = appDep.calculatorOnline(oBrowser, strTotalSpend);
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);

            Assert.assertTrue((transactionCount == Integer.parseInt(totalTransactionCount)), "Mis-match in the total 'Transaction Count' values for the selected rows");
            Assert.assertTrue(Double.parseDouble(totalTotalSpend) == Double.parseDouble(totalSpend), "Mis-match in the total 'Total Spend' values for the selected rows");

            //verify 'Clear' button is visible
            appDep.waitForTheElementState(oBrowser, objCardMatchSupplierClearBtn, "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, objCardMatchSupplierClearBtn));
            Assert.assertTrue(appInd.clickObject(oBrowser, objCardMatchSupplierClearBtn));
            appDep.threadSleep(2000);

            finalTotalTransactionCount = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Top15CardMatchSupplier_Grid + "/td[@id='top_card_match_total_count']"), "Text");
            finalTotalSupplierCount = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Top15CardMatchSupplier_Grid + "/td[@id='top_card_match_total_spend']"), "Text");

            Assert.assertEquals(initialTotalTransactionCount, finalTotalTransactionCount, "Both initial '" + initialTotalTransactionCount + "' & final '" + finalTotalTransactionCount + "' Transaction Counts are not matching");
            Assert.assertEquals(initialTotalSupplierCount, finalTotalSupplierCount, "Both initial '" + initialTotalSupplierCount + "' & final '" + finalTotalSupplierCount + "' Supplier Counts are not matching");
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateTheTestFunctionalityOfPropsectCompany()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateTheTestFunctionalityOfPropsectCompany()' method. " + e);
            return false;
        } finally {
            boxNames = null;oAction = null;totalCostItems = null;objEle = null;riskScore = null;initialPaymentScore = null;initialRiskScore = null;finalPaymentScore = null;finalRiskScore = null;initialTotalTransactionCount = null;initialTotalSupplierCount = null;initialTotalTotalSpend = null;initialEstimatedVirtualCard = null;initialACHPlus = null;initialACHBasic = null;initialRemainOnCheck = null;finalTotalTransactionCount = null;finalTotalSupplierCount = null;finalTotalTotalSpend = null;finalEstimatedVirtualCard = null;finalACHPlus = null;finalACHBasic = null;finalRemainOnCheck = null;totalTransactionCount = null;objToggleOffBtn = null;objPaymentScoreToggle = null;
            obj_EffectiveRebate = null;objEffectiveRebateFormula = null;objRiskScoreGrid = null;objRiskScoreLegend = null;objCorcentricEffortsGrid = null;objScoreCardLink = null;objScorecardGrid = null;objPaymentScoreLabel = null;objRiskScoreLabel = null;objCorcentricFeatureGrid = null;objSupplierClearBtn = null;objCardMatchSupplierLink = null;objCardMatchSupplierClearBtn = null;totalRemainOnCheck = null;corcentric = null;prospectCompany = null;objToggleOnBtn = null;totalTotalSpend = null;totalEstimatedVirtualCard = null;totalACHPlus = null;totalACHBasic = null;totalSupplierCount = null;totalSpend = null;estimatedVirtualCard = null;achPlus = null;achBasic = null;remainOnCheck = null;strTotalSpend = null;strEstdVirtualCard = null;strAchPlus = null;strAchBasic = null;strRemainOnCheck = null;}
    }


    /********************************************************
     * Method Name      : hoverOverTotalCostColumnValues()
     * Purpose          : user validates mouse over on Total Cost column values
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean hoverOverTotalCostColumnValues(WebDriver oBrowser) {
        Actions oAction = null;
        int rowCount = 0;
        WebElement objEle = null;
        String totalCostItems = null;
        try {
            appInd.scrollToThePage(oBrowser, "Top");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Pricing_Link));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Pricing_ItemizedProgramCosts_Grid + "//table//td[text()='Total Costs Per Year']"), "Visibility", "", minTimeOut);
            appDep.threadSleep(2000);
            oAction = new Actions(oBrowser);
            rowCount = oBrowser.findElements(By.xpath(PayCRM_Modules_GeneralUIPage.obj_Pricing_ItemizedProgramCosts_Grid + "//table/tbody/tr")).size();
            for (int i = 0; i < rowCount - 1; i++) {
                totalCostItems = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Pricing_ItemizedProgramCosts_Grid + "//table//tr[" + (i + 1) + "]/td[1]"), "Text");
                if (totalCostItems.equalsIgnoreCase("vpaxAR") || totalCostItems.equalsIgnoreCase("PrePaid Debit Card") || totalCostItems.equalsIgnoreCase("Supplier Management")) {
                    objEle = appInd.createAndGetWebElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Pricing_ItemizedProgramCosts_Grid + "//table//tr[" + (i + 1) + "]/td[1]"));
                } else {
                    objEle = appInd.createAndGetWebElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Pricing_ItemizedProgramCosts_Grid + "//table//tr[" + (i + 1) + "]/td[1]/span"));
                }

                oAction.moveToElement(objEle).build().perform();
                appDep.threadSleep(2000);
                totalCostItems = objEle.getText();
                if (totalCostItems.equalsIgnoreCase("vpaxAR") || totalCostItems.equalsIgnoreCase("PrePaid Debit Card") || totalCostItems.equalsIgnoreCase("Supplier Management")) {
                    Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Pricing_ItemizedProgramCosts_Grid + "//table//tr[" + (i + 1) + "]/td[1]/span")));
                } else {
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Pricing_ItemizedProgramCosts_Grid + "//table//tr[" + (i + 1) + "]/td[1]/span")));
                }
                reports.writeResult(oBrowser, "Screenshot", "Hover over blue labels in itemized program costs table for the row : '" + (i + 1) + "'\n" + ": " + totalCostItems);
            }
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'hoverOverTotalCostColumnValues()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'hoverOverTotalCostColumnValues()' method. " + e);
            return false;
        } finally {
            oAction = null; objEle = null; totalCostItems = null;
        }
    }


    /********************************************************
     * Method Name      : editClientLinkInformation()
     * Purpose          : user edits the Client Link Information data for the selected Supplier
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public Map<String, String> editClientLinkInformation(WebDriver oBrowser, Map<String, String> clientLinkData) {
        Actions oAction = null;
        List<WebElement> objEle = null;
        try {
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Edit_Link));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_EditSuppliers_DialogGrid + "//h4"), "Visibility", "", minTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Edit 'Client Link Information' page opened");
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_EditSuppliers_DialogGrid + "//h4"), "Text").contains("Edit Client Link Information"));
            objEle = oBrowser.findElements(By.xpath(PayCRM_Modules_GeneralUIPage.obj_VendorID_Field + "//a[@class='search-choice-close']"));
            for (int i = 0; i < objEle.size(); i++) {
                Assert.assertTrue(appInd.clickObject(oBrowser, objEle.get(i)));
                appDep.threadSleep(1000);
            }

            oAction = new Actions(oBrowser);
            oAction.sendKeys(oBrowser.findElement(By.xpath(PayCRM_Modules_GeneralUIPage.obj_VendorID_Field + "//input")), clientLinkData.get("VendorID")).sendKeys(Keys.ENTER).build().perform();
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCompany_SupplierType_List, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCompany_SupplierType_List, "List", clientLinkData.get("SupplierType"))));
            clientLinkData.remove("SupplierType");
            clientLinkData.put("SupplierType", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCompany_SupplierType_List, "Dropdown"));

            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Suppliers_Status_List, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Suppliers_Status_List, "List", clientLinkData.get("Status"))));
            clientLinkData.remove("Status");
            clientLinkData.put("Status", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Suppliers_Status_List, "Dropdown"));

            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Suppliers_Priority_List, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Suppliers_Priority_List, "List", clientLinkData.get("Priority"))));
            clientLinkData.remove("Priority");
            clientLinkData.put("Priority", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Suppliers_Priority_List, "Dropdown"));

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_TotalSpend_Edit, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_TotalSpend_Edit, "Value", clientLinkData.get("TotalSpend"))));
            clientLinkData.remove("TotalSpend");
            clientLinkData.put("TotalSpend", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_TotalSpend_Edit, "Value"));

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_NumberChecks_Edit, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_NumberChecks_Edit, "Value", clientLinkData.get("NumberChecks"))));
            clientLinkData.remove("NumberChecks");
            clientLinkData.put("NumberChecks", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_NumberChecks_Edit, "Value"));

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientLink_Link_Edit, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientLink_Link_Edit, "Value", clientLinkData.get("Link"))));
            clientLinkData.remove("Link");
            clientLinkData.put("Link", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientLink_Link_Edit, "Value"));

            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Paymentype_List, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Paymentype_List, "List", clientLinkData.get("PaymentType"))));
            clientLinkData.remove("PaymentType");
            clientLinkData.put("PaymentType", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Paymentype_List, "DropDown"));
            reports.writeResult(oBrowser, "Screenshot", "The details required to Edit the 'Client Link Information' was entered successful");
            appInd.scrollToElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_EditSuppliers_DialogGrid + "//button[@name='button']"));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_EditSuppliers_DialogGrid + "//button[@name='button']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CompanyEditConfirmationMessage_Label, "Text").contains("Company Supplier has been successfully updated."));
            clientLinkData.put("TestPassed", "True");
            return clientLinkData;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'editClientLinkInformation()' method. " + e);
            clientLinkData.put("TestPassed", "False");
            return clientLinkData;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'editClientLinkInformation()' method. " + e);
            clientLinkData.put("TestPassed", "False");
            return clientLinkData;
        } finally {oAction = null; objEle = null;}
    }


    /********************************************************
     * Method Name      : validateClientLinkInformationGridDetails()
     * Purpose          : user validates the Client Link Information Grid & Show page values
     * Author           : Gudi
     * Parameters       : oBrowser, clientLinkData
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateClientLinkInformationGridDetails(WebDriver oBrowser, Map<String, String> clientLinkData) {
        try {
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Suppliers_Grid + "//td[contains(@aria-label, 'Vendor ID, ')]//input"), "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Suppliers_Grid + "//td[contains(@aria-label, 'Vendor ID, ')]//input"), clientLinkData.get("VendorID")));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Suppliers_Grid + "//table//tr[1][contains(@class, 'lines dx-column-lines')]/td[3]"), "Text", clientLinkData.get("VendorID")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Suppliers_Grid + "//table//tr[1][contains(@class, 'lines dx-column-lines')]/td[4]"), "Text").contains(clientLinkData.get("Link")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Suppliers_Grid + "//table//tr[1][contains(@class, 'lines dx-column-lines')]/td[5]"), "Text", clientLinkData.get("Status")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Suppliers_Grid + "//table//tr[1][contains(@class, 'lines dx-column-lines')]/td[6]"), "Text", clientLinkData.get("Priority")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Suppliers_Grid + "//table//tr[1][contains(@class, 'lines dx-column-lines')]/td[7]"), "Text").contains(clientLinkData.get("SupplierType")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Suppliers_Grid + "//table//tr[1][contains(@class, 'lines dx-column-lines')]/td[8]"), "Text").contains(clientLinkData.get("Wave")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Suppliers_Grid + "//table//tr[1][contains(@class, 'lines dx-column-lines')]/td[9]"), "Text").contains(clientLinkData.get("TotalSpend")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Suppliers_Grid + "//table//tr[1][contains(@class, 'lines dx-column-lines')]/td[10]"), "Text").contains(clientLinkData.get("PaymentType")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Suppliers_Grid + "//table//tr[1][contains(@class, 'lines dx-column-lines')]/td[11]"), "Text").contains(clientLinkData.get("NumberChecks")));
            clientLinkData.put("SupplierName", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SupplierName_Header, "Text"));
            clientLinkData.put("CompanyName", appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Suppliers_Grid + "//table//tr[1][contains(@class, 'lines dx-column-lines')]/td[2]"), "Text"));

            //Show section
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Queues_Show_Link));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientSupplier_Show_Grid + "//h4"), "Text", "Company Supplier", minTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientSupplier_Show_Grid + "//h4"), "Text", "Company Supplier"));

            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientSupplier_Show_Grid + "//dl/dd[1]"), "Text", clientLinkData.get("SupplierName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientSupplier_Show_Grid + "//dl/dd[2]"), "Text", clientLinkData.get("VendorID")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientSupplier_Show_Grid + "//dl/dd[11]"), "Text", clientLinkData.get("NumberChecks")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientSupplier_Show_Grid + "//dl/dd[12]"), "Text").contains(String.valueOf(Integer.parseInt(clientLinkData.get("TotalSpend")))));

            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientSuppier_Show_GeneralInfo + "/following-sibling::div/dl/dd[1]"), "Text", clientLinkData.get("CompanyName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientSuppier_Show_GeneralInfo + "/following-sibling::div/dl/dd[3]"), "Text", clientLinkData.get("Status")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientSuppier_Show_GeneralInfo + "/following-sibling::div/dl/dd[4]"), "Text", clientLinkData.get("Priority")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientSuppier_Show_GeneralInfo + "/following-sibling::div/dl/dd[5]"), "Text").contains(clientLinkData.get("SupplierType")));

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientSupplier_Show_Grid + "//button[@class='close']")));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateClientLinkInformationGridDetails()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateClientLinkInformationGridDetails()' method. " + e);
            return false;
        }
    }


    /********************************************************
     * Method Name      : validateClientLinkInformationData()
     * Purpose          : user validates the Client Link Information data for the selected Supplier
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateClientLinkInformationData(WebDriver oBrowser, DataTable dataTable) {
        List<Map<String, String>> clientLinkData = null;
        Map<String, String> clientLink = null;
        String timeStamp = null;
        try {
            timeStamp = appInd.getDateTime("Shhmmss");
            clientLinkData = dataTable.asMaps(String.class, String.class);
            clientLink = new HashMap<String, String>();
            clientLink.put("VendorID", timeStamp);
            clientLink.put("SupplierType", clientLinkData.get(0).get("SupplierType"));
            clientLink.put("Status", clientLinkData.get(0).get("Status"));
            clientLink.put("Priority", clientLinkData.get(0).get("Priority"));
            clientLink.put("TotalSpend", clientLinkData.get(0).get("TotalSpend"));
            clientLink.put("NumberChecks", clientLinkData.get(0).get("NumberChecks"));
            clientLink.put("Link", clientLinkData.get(0).get("Link"));
            clientLink.put("PaymentType", clientLinkData.get(0).get("PaymentType"));

            /*appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_FirstRow, "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_FirstRow));
            oBrowser = enrollmentMgrBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ViewActivities_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientLinkInformation_Link));*/
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Queues_Show_Link));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Edit_Link));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ViewPayNetLink_Link));
            reports.writeResult(oBrowser, "Screenshot", "The 'Client Link Information' tab was clicked");
            clientLink = editClientLinkInformation(oBrowser, clientLink);
            Assert.assertTrue(clientLink.get("TestPassed").equalsIgnoreCase("True"), "The 'editClientLinkInformation()' method was failed.");
            Assert.assertTrue(validateClientLinkInformationGridDetails(oBrowser, clientLink));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateClientLinkInformationData()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateClientLinkInformationData()' method. " + e);
            return false;
        } finally {
            clientLinkData = null; clientLink = null; timeStamp = null;
        }
    }


    /********************************************************
     * Method Name      : clickOnViewPayNetLinkAndValidateTheSame()
     * Purpose          : user clicks on 'View PayNet Link' and validate the same
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean clickOnViewPayNetLinkAndValidateTheSame(WebDriver oBrowser) {
        String companyName = null;
        String vendorId = null;
        String id = null;
        try {
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ViewActivities_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientLinkInformation_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);

            id = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Suppliers_Grid + "//table//tr[1][contains(@class, 'lines dx-column-lines')]/td[1]"), "Text");
            companyName = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Suppliers_Grid + "//table//tr[1][contains(@class, 'lines dx-column-lines')]/td[2]"), "Text");
            vendorId = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientCompany_Suppliers_Grid + "//table//tr[1][contains(@class, 'lines dx-column-lines')]/td[3]"), "Text");

            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ViewPayNetLink_Link, "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ViewPayNetLink_Link));
            appDep.waitForTheElementState(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_PayNetLink_Properties_Dialog + "//dt[text()='EntityIdentifier']/following-sibling::dd)[1]"), "Text", vendorId, waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PayNetLink_Properties_Dialog + "//h4"), "Text").contains("PayNet Link Properties (" + companyName));

            if(vendorId.contains(",")){
                String arrVendorID[] = vendorId.split(",");
                for(int i=0; i<arrVendorID.length; i++){
                    Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_PayNetLink_Properties_Dialog + "//dt[text()='EntityIdentifier']/following-sibling::dd)[1]"), "Text").contains(arrVendorID[i].trim()));
                }
            }else
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_PayNetLink_Properties_Dialog + "//dt[text()='EntityIdentifier']/following-sibling::dd)[1]"), "Text", vendorId));

            //Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_PayNetLink_Properties_Dialog + "//dt[text()='VenNumber']/following-sibling::dd)[1]"), "Text", id));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PayNetLink_Properties_Dialog + "//button[@class='close']"), "Clickable", "", minTimeOut);
            appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PayNetLink_Properties_Dialog + "//button[@class='close']"));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'clickOnViewPayNetLinkAndValidateTheSame()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'clickOnViewPayNetLinkAndValidateTheSame()' method. " + e);
            return false;
        } finally {companyName = null; vendorId = null;}
    }


    /********************************************************
     * Method Name      : createAccessCodeAndValidatesTheSame()
     * Purpose          : user creates new Access Code for the selected client company and validates the same
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public Map<String, String> createEditAccessCode(WebDriver oBrowser, String accessCodeStatus, Map<String, String> accessCodeData) {
        String dialogHeader = null;
        String confirmationMsg = null;
        WebElement objEle = null;
        try {
            if (accessCodeStatus.equalsIgnoreCase("Add")) {
                appInd.scrollToElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AccessCode_Link);
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Add_Link));
                dialogHeader = "New Access Code";
                confirmationMsg = "Access code has been successfully created.";
            } else {
                appInd.scrollToElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Edit_Link);
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Company_Edit_Link));
                dialogHeader = "Edit Access Code";
                confirmationMsg = "Access code has been successfully updated.";
            }

            reports.writeResult(oBrowser, "Screenshot", "Before '" + accessCodeStatus + "' Access Code");
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_AccessCode_Dialog + "//h4"), "Text", dialogHeader, minTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_AccessCode_Dialog + "//h4"), "Text", dialogHeader));
            if (accessCodeStatus.equalsIgnoreCase("Add")) {
                Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AccessCode_Edit, accessCodeData.get("ActivationCode")));
                Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AssignedTo_Edit, accessCodeData.get("AssignedTo")));
            } else {
                Assert.assertTrue(appInd.compareValues(oBrowser, appInd.createAndGetWebElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AccessCode_Edit).getAttribute("readonly"), "true"));
                accessCodeData.remove("ActivationCode");
                accessCodeData.put("ActivationCode", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AccessCode_Edit, "Value"));
                Assert.assertTrue(appInd.compareValues(oBrowser, appInd.createAndGetWebElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_HitCount_Edit).getAttribute("readonly"), "true"));
                Assert.assertTrue(appInd.compareValues(oBrowser, appInd.createAndGetWebElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_LastAccessedAt_Edit).getAttribute("readonly"), "true"));
                /*Assert.assertTrue(appInd.compareValues(oBrowser, appInd.createAndGetWebElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AssignedTo_Edit).getAttribute("readonly"), "true"));
                accessCodeData.remove("AssignedTo");
                accessCodeData.put("AssignedTo", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AssignedTo_Edit, "Value"));*/
            }

            if(accessCodeStatus.equalsIgnoreCase("Add")){
                Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AssignedTo_Edit, accessCodeData.get("AssignedTo")));
            }else{
                if(oBrowser.findElement(PayCRM_Modules_GeneralUIPage.obj_AssignedTo_Edit).getAttribute("readonly")==null)
                    Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AssignedTo_Edit, accessCodeData.get("AssignedTo")));
                else{
                    accessCodeData.remove("AssignedTo");
                    accessCodeData.put("AssignedTo", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AssignedTo_Edit, "Value"));
                }
            }

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AdditionalInfo_Edit, accessCodeData.get("AdditionalInfo")));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ExpiresAt_Edit));
            String day = (accessCodeData.get("ExpiresAt").split("/"))[1];
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ExpiresAt_Calendar + "//a[text()='" + Integer.parseInt(day) + "']")));

            if (accessCodeData.get("IsActivationCode").equalsIgnoreCase("true")) {
                if(appInd.verifyOptionalElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_IsActivationCode_ChkBox)){
                    objEle = oBrowser.findElement(PayCRM_Modules_GeneralUIPage.obj_IsActivationCode_ChkBox);
                    if (!objEle.isSelected())
                        Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_IsActivationCode_ChkBox));
                    //accessCodeData.remove("AssignedTo");
                    //accessCodeData.put("AssignedTo", "1");
                }
            }

            reports.writeResult(oBrowser, "Screenshot", "After entering the data for '" + accessCodeStatus + "' Access Code");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_AccessCode_Dialog + "//button[@type='submit']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CompanyEditConfirmationMessage_Label, "Visibility", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CompanyEditConfirmationMessage_Label, "Text").contains(confirmationMsg));
            oBrowser.navigate().refresh();
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AccessCode_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);

            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AccessCode_Search_Edit, "Visibility", "", minTimeOut);
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AccessCode_Search_Edit, accessCodeData.get("ActivationCode")));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_AccessCode_Grid + "/tbody/tr/td[2]"), "Text", accessCodeData.get("ActivationCode")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_AccessCode_Grid + "/tbody/tr/td[3]"), "Text", "0"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_AccessCode_Grid + "/tbody/tr/td[4]"), "Text", ""));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_AccessCode_Grid + "/tbody/tr/td[6]"), "Text", appInd.getPropertyValueByKeyName("qaUserName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_AccessCode_Grid + "/tbody/tr/td[8]"), "Text", accessCodeData.get("AdditionalInfo")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_AccessCode_Grid + "/tbody/tr/td[9]"), "Text", accessCodeData.get("ExpiresAt")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_AccessCode_Grid + "/tbody/tr/td[10]//*[@class='fa-solid fa-check miller-replace-check']")));
            if (accessCodeStatus.equalsIgnoreCase("Add")) {
                if (accessCodeData.get("IsActivationCode").equalsIgnoreCase("True")) {
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_AccessCode_Grid + "/tbody/tr/td[@id='is_active_cell']/*[@class='glyph-icon icon-check']")));
                } else {
                    Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_AccessCode_Grid + "/tbody/tr/td[@id='is_active_cell']/*[@class='glyph-icon icon-check']")));
                }
            } else {
                if (accessCodeData.get("IsActivationCode").equalsIgnoreCase("True")) {
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_AccessCode_Grid + "/tbody/tr/td[@id='is_active_cell']/*[@class='glyph-icon icon-check']")));
                } else {
                    Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_AccessCode_Grid + "/tbody/tr/td[@id='is_active_cell']/*[@class='glyph-icon icon-check']")));
                }
            }

            accessCodeData.put("TestPassed", "True");
            return accessCodeData;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createEditAccessCode()' method. " + e);
            accessCodeData.put("TestPassed", "False");
            return accessCodeData;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createEditAccessCode()' method. " + e);
            accessCodeData.put("TestPassed", "False");
            return accessCodeData;
        }finally{dialogHeader = null; confirmationMsg = null; objEle = null;}
    }


    /********************************************************
     * Method Name      : createEditAccessCodeAndValidatesTheSame()
     * Purpose          : user creates/Edits Access Code for the selected client company and validates the same
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean createEditAccessCodeAndValidatesTheSame(WebDriver oBrowser, String accessCodeStatus, DataTable dataTable) {
        List<Map<String, String>> accessCodeData = null;
        Map<String, String> accessCode = null;
        String timeStamp = null;
        try {
            timeStamp = appInd.getDateTime("ShhmmssddMM");
            accessCodeData = dataTable.asMaps(String.class, String.class);
            accessCode = new HashMap<String, String>();
            accessCode.put("ActivationCode", accessCodeData.get(0).get("ActivationCode") + "_" + timeStamp);
            accessCode.put("AssignedTo", accessCodeData.get(0).get("AssignedTo"));
            accessCode.put("AdditionalInfo", accessCodeData.get(0).get("AdditionalInfo") + "_" + timeStamp);
            accessCode.put("ExpiresAt", appInd.getDateTimeByTimezone("MM/dd/yyyy", "CST"));
            accessCode.put("IsActivationCode", accessCodeData.get(0).get("IsActivationCode"));

            if (accessCodeStatus.equalsIgnoreCase("Add")) {
                companyName = appDep.selectTheCompanyBasedOnType(oBrowser, "Client");
                Assert.assertNotNull(companyName, "The 'selectTheCompanyBasedOnType()' method returned null value");
                appDep.threadSleep(2000);
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AccessCode_Link));
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AccessCode_Search_Edit, "Clickable", "", minTimeOut);
            }

            accessCode = createEditAccessCode(oBrowser, accessCodeStatus, accessCode);
            Assert.assertTrue(accessCode.get("TestPassed").equalsIgnoreCase("True"), "The 'createEditAccessCode' method was failed.");
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createEditAccessCodeAndValidatesTheSame()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createEditAccessCodeAndValidatesTheSame()' method. " + e);
            return false;
        } finally {
            accessCodeData = null; accessCode = null; timeStamp = null;
        }
    }


    /********************************************************
     * Method Name      : assignWorkQueues()
     * Purpose          : user perform Assign work queue
     * Author           : Gudi
     * Parameters       : oBrowser, userNames
     * ReturnType       : boolean
     ********************************************************/
    public boolean assignWorkQueues(WebDriver oBrowser, Map<String, String> usersData) {
        List<String> objUsersToAssign = null;
        try {
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Users_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_NewUserSetup_Settings_Update_Btn, "Clickable", "", waitTimeOut);

            objUsersToAssign = new ArrayList<>();
            objUsersToAssign.add(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_Users_WorkQueue_Grid + "//tr[contains(@class, 'row-lines dx-column-lines')]/td[2])[1]"), "Text"));
            objUsersToAssign.add(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_Users_WorkQueue_Grid + "//tr[contains(@class, 'row-lines dx-column-lines')]/td[2])[2]"), "Text"));

            for (int i = 0; i < objUsersToAssign.size(); i++) {
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Users_WorkQueue_Grid + "//td[text()='" + objUsersToAssign.get(i) + "']/preceding-sibling::td//div[@role='checkbox']")));
            }
            reports.writeResult(oBrowser, "Screenshot", "Before adding to the work queue");

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_NewUserSetup_Settings_Update_Btn));

            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Queue_Elelement + "//span"), "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_AddWorkQueue_Dialog + "//h4"), "Text", "Add Work Queue"));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Queue_Elelement + "//span")));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Queue_Elelement + "//input"), "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Queue_Elelement + "//input"), usersData.get("QueueName")));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Queue_Elelement + "//ul/li/em[text()='" + usersData.get("QueueName") + "']")));
            appDep.threadSleep(4000);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Users_List), "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_AddWorkQueue_Dialog + "//button[@type='submit']")));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "After adding to the work queue");
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'assignWorkQueues()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'assignWorkQueues()' method. " + e);
            return false;
        } finally {objUsersToAssign = null;}
    }


    /********************************************************
     * Method Name      : removeSelectedUsersFromWorkQueue()
     * Purpose          : user removes the users from the work queue
     * Author           : Gudi
     * Parameters       : oBrowser, userNames
     * ReturnType       : boolean
     ********************************************************/
    public boolean removeSelectedUsersFromWorkQueue(WebDriver oBrowser, Map<String, String> usersData) {
        String arrAddUsers[];
        String arrRemoveUsers[];
        Alert oAlert = null;
        List<String> objUsersToDelete = null;
        try {
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Users_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_NewUserSetup_Settings_Update_Btn, "Clickable", "", waitTimeOut);

            //select users and delete
            objUsersToDelete = new ArrayList<>();
            objUsersToDelete.add(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Users_WorkQueue_Grid + "//tr[1][contains(@class, 'row-lines dx-column-lines')]/td[2]"), "Text"));
            objUsersToDelete.add(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Users_WorkQueue_Grid + "//tr[2][contains(@class, 'row-lines dx-column-lines')]/td[2]"), "Text"));
            appDep.threadSleep(2000);
            for (int i = 0; i < objUsersToDelete.size(); i++) {
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Users_WorkQueue_Grid + "//td[text()='" + objUsersToDelete.get(i) + "']/preceding-sibling::td//div[@role='checkbox']")));
            }

            reports.writeResult(oBrowser, "Screenshot", "Before removing the users from the work queue");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_RemoveSelected_Btn));
            appDep.threadSleep(2000);
            oAlert = oBrowser.switchTo().alert();

            Assert.assertTrue(oAlert.getText().equalsIgnoreCase("Are you sure you want to remove the selected users?"));
            oAlert.accept();
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", minTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CompanyEditConfirmationMessage_Label, "Visibility", "", minTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CompanyEditConfirmationMessage_Label, "Text").contains("Users successfully removed."));
            appDep.threadSleep(2000);
            for (int i = 0; i < objUsersToDelete.size(); i++) {
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Users_WorkQueue_Grid + "//td[2][text()='" + objUsersToDelete.get(i) + "']")));
            }
            reports.writeResult(oBrowser, "Screenshot", "After removing the users from the work queue");
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'removeSelectedUsersFromWorkQueue()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'removeSelectedUsersFromWorkQueue()' method. " + e);
            return false;
        } finally {
            arrAddUsers = null; arrRemoveUsers = null; oAlert = null; objUsersToDelete = null;
        }
    }


    /********************************************************
     * Method Name      : assignAndRemoveQueues()
     * Purpose          : user Assign and removes the users from the work queue
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean assignAndRemoveQueues(WebDriver oBrowser, DataTable dataTable) {
        List<Map<String, String>> usersData = null;
        Map<String, String> users = null;
        try {
            usersData = dataTable.asMaps(String.class, String.class);
            users = new HashMap<String, String>();
            users.put("QueueName", usersData.get(0).get("QueueName"));
            users.put("AddUsers", usersData.get(0).get("AddUsers"));
            users.put("removeUsers", usersData.get(0).get("removeUsers"));

            Assert.assertTrue(removeSelectedUsersFromWorkQueue(oBrowser, users));
            Assert.assertTrue(assignWorkQueues(oBrowser, users));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'assignAndRemoveQueues()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'assignAndRemoveQueues()' method. " + e);
            return false;
        } finally {usersData = null; users = null;}
    }


    /********************************************************
     * Method Name      : updateTabVisibilityFromInternalSetup()
     * Purpose          : user updates Tab Visibility from internal setup
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean updateTabVisibilityFromInternalSetup(WebDriver oBrowser, DataTable dataTable) {
        String arrPrimaryTabs[];
        String arrSolutionDesignTabs[];
        List<Map<String, String>> primaryTabsData = null;
        Map<String, String> primaryTabs = null;
        try {
            primaryTabsData = dataTable.asMaps(String.class, String.class);
            primaryTabs = new HashMap<String, String>();
            primaryTabs.put("PrimaryTabs", primaryTabsData.get(0).get("PrimaryTabs"));
            primaryTabs.put("SolutionDesignTabs", primaryTabsData.get(0).get("SolutionDesignTabs"));

            appDep.navigateToInternalSetupPage(oBrowser, "HomePage");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_TabVisibility_Link));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SolutionDesingTabs), "Text", "Solution Design Tabs", minTimeOut);

            arrPrimaryTabs = primaryTabs.get("PrimaryTabs").split("#");
            for (int i = 0; i < arrPrimaryTabs.length; i++) {
                Assert.assertTrue(appInd.validateAndSelectTheCheckbox(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PrimaryTabs + "//ul/li[" + (i + 1) + "]/input[1]"), "false"));
            }

            arrSolutionDesignTabs = primaryTabs.get("SolutionDesignTabs").split("#");
            for (int i = 0; i < arrSolutionDesignTabs.length; i++) {
                Assert.assertTrue(appInd.validateAndSelectTheCheckbox(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SolutionDesingTabs + "//ul/li[" + (i + 1) + "]/input[1]"), "false"));
            }

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Update_TabVisibility_Btn));
            appDep.threadSleep(4000);
            oBrowser.navigate().refresh();
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", waitTimeOut);

            //Validate all the primary tabs are removed
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AccountSetupForm_Link));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SolutionDesigns_Link));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Pricing_Link));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Solution_Link));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PreviewAndGoLive_Link));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CurrentState_Link));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_OtherProvidersApproach_Link));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SuppliersTab_Link));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MccMatches_Link));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SupplierTypeMatches_Link));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_Link));


            //select Primary Tabs and unselected Solution design tabs
            appDep.navigateToInternalSetupPage(oBrowser, "Account Setup");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_TabVisibility_Link));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SolutionDesingTabs + "//h4"), "Text", "Solution Design Tabs", minTimeOut);

            arrPrimaryTabs = primaryTabs.get("PrimaryTabs").split("#");
            for (int i = 0; i < arrPrimaryTabs.length; i++) {
                Assert.assertTrue(appInd.validateAndSelectTheCheckbox(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PrimaryTabs + "//ul/li[" + (i + 1) + "]/input[1]"), "true"));
            }

            arrSolutionDesignTabs = primaryTabs.get("SolutionDesignTabs").split("#");
            for (int i = 0; i < arrSolutionDesignTabs.length; i++) {
                Assert.assertTrue(appInd.validateAndSelectTheCheckbox(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SolutionDesingTabs + "//ul/li[" + (i + 1) + "]/input[1]"), "true"));
            }
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Update_TabVisibility_Btn));
            appDep.threadSleep(4000);
            oBrowser.navigate().refresh();
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", waitTimeOut);

            //Validate all the Solutions Design tabs are removed
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AccountSetupForm_Link));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SolutionDesigns_Link));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Pricing_Link));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Solution_Link));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PreviewAndGoLive_Link));

            //go insdie the 'Solution Desing' tab
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SolutionDesigns_Link));
            appDep.threadSleep(5000);

            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CurrentState_Link));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_OtherProvidersApproach_Link));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SuppliersTab_Link));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MccMatches_Link));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SupplierTypeMatches_Link));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_Link));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'updateTabVisibilityFromInternalSetup()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'updateTabVisibilityFromInternalSetup()' method. " + e);
            return false;
        } finally {
            arrPrimaryTabs = null; arrSolutionDesignTabs = null; primaryTabsData = null; primaryTabs = null;
        }
    }


    /********************************************************
     * Method Name      : updateCurrentStateFromInternalSetup()
     * Purpose          : user updates Current State from internal setup
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean updateCurrentStateFromInternalSetup(WebDriver oBrowser, DataTable dataTable) {
        List<Map<String, String>> currentStateData = null;
        Map<String, String> currentState = null;
        try {
            currentStateData = dataTable.asMaps(String.class, String.class);
            currentState = new HashMap<String, String>();
            currentState.put("UtilizationPercentage", currentStateData.get(0).get("UtilizationPercentage"));
            currentState.put("EffectiveRebateOffset", currentStateData.get(0).get("EffectiveRebateOffset"));
            currentState.put("RiskScorePercentage", currentStateData.get(0).get("RiskScorePercentage"));
            currentState.put("CurrentClientRebatePercentage", currentStateData.get(0).get("CurrentClientRebatePercentage"));
            currentState.put("CurrentEffortPercentage", currentStateData.get(0).get("CurrentEffortPercentage"));
            currentState.put("CurrentEnhancedACHDiscountPercentage", currentStateData.get(0).get("CurrentEnhancedACHDiscountPercentage"));
            currentState.put("CurrentEnhancedACHKeepPercentage", currentStateData.get(0).get("CurrentEnhancedACHKeepPercentage"));

            appDep.navigateToInternalSetupPage(oBrowser, "HomePage");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_InternalSetup_CurrentState_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CurrentEnhancedACHKeepPercentage_Edit, "Clickable", "", minTimeOut);

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_UtilizationPercentage_Edit, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_UtilizationPercentage_Edit, "Value", currentState.get("UtilizationPercentage"))));
            currentState.remove("UtilizationPercentage");
            currentState.put("UtilizationPercentage", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_UtilizationPercentage_Edit, "Value"));

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_EffectiveRebateOffset_Edit, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_EffectiveRebateOffset_Edit, "Value", currentState.get("EffectiveRebateOffset"))));
            currentState.remove("EffectiveRebateOffset");
            currentState.put("EffectiveRebateOffset", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_EffectiveRebateOffset_Edit, "Value"));

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_RiskScorePercentage_Edit, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_RiskScorePercentage_Edit, "Value", currentState.get("RiskScorePercentage"))));
            currentState.remove("RiskScorePercentage");
            currentState.put("RiskScorePercentage", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_RiskScorePercentage_Edit, "Value"));

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CurrentClientRebatePercentage_Edit, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CurrentClientRebatePercentage_Edit, "Value", currentState.get("CurrentClientRebatePercentage"))));
            currentState.remove("CurrentClientRebatePercentage");
            currentState.put("CurrentClientRebatePercentage", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CurrentClientRebatePercentage_Edit, "Value"));

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CurrentEffortPercentage_Edit, currentState.get("CurrentEffortPercentage")));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CurrentEnhancedACHDiscountPercentage_Edit, currentState.get("CurrentEnhancedACHDiscountPercentage")));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CurrentEnhancedACHKeepPercentage_Edit, currentState.get("CurrentEnhancedACHKeepPercentage")));

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CurrentState_Update_Btn));
            appDep.threadSleep(4000);
            oBrowser.navigate().refresh();
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SolutionDesigns_Link));
            appDep.threadSleep(5000);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CurrentState_Link));
            appDep.threadSleep(4000);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CurrentState_PaymentScore_Percentage_Label), "Text", currentState.get("UtilizationPercentage") + "%"));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CurrentState_EffectiveRebate_Percentage_Label, "Text", currentState.get("EffectiveRebateOffset") + ".00%"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CurrentState_riskScore_Percentage_Label), "Text", currentState.get("RiskScorePercentage") + "%"));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'updateTabVisibilityFromInternalSetup()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'updateTabVisibilityFromInternalSetup()' method. " + e);
            return false;
        } finally {currentStateData = null; currentState = null;}
    }


    /********************************************************
     * Method Name      : validateSupplierTypeMatches()
     * Purpose          : user validates the Supplier Type matches functionality under 'Solution Design--> Supplier Type Matches' section of Account setup page
     * Author           : Gudi
     * Parameters       : oBrowser, queryKey
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateSupplierTypeMatches(WebDriver oBrowser, String queryKey) {
        String supplierName = null;
        String supplierType = null;
        String matchedSupplierName = null;
        String matchedSupplierTIN = null;
        Response response = null;
        JSONArray caseDetails = null;
        try {
            caseDetails = navigateAndSelectGeneralCompanies(oBrowser, "Prospect", queryKey);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SolutionDesigns_Link));
            appDep.threadSleep(4000);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SupplierTypeMatches_Link));
            appDep.threadSleep(4000);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierTypeMatches_Grid + "//table//tr[contains(@class, 'lines dx-column-lines')]/td[1]"), "Clickable", "", minTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "'Supplier Type Matches' page");
            supplierName = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierTypeMatches_Grid + "//table//tr[contains(@class, 'lines dx-column-lines')]/td[1]"), "Text");
            supplierType = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierTypeMatches_Grid + "//table//tr[contains(@class, 'lines dx-column-lines')]/td[4]"), "Text");

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierTypeMatches_Grid + "//table//tr[contains(@class, 'lines dx-column-lines')]/td[1]")));
            appDep.waitForTheElementState(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_SupplierMatches_Dialog + "//h4/span)[3]"), "Text", "Matched Suppliers", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Supplier Type Matches dialog for '" + supplierName + "': Before changing the supplier type");
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_SupplierMatches_Dialog + "//h4/span)[2]"), "Text", "Current Supplier"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_SupplierMatches_Dialog + "//h4/span)[3]"), "Text", "Matched Suppliers"));

            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierMatches_Dialog + "//table[1]//td[1]"), "Text", supplierName));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierMatches_Dialog + "//table[1]//td[3]"), "Text", supplierType));

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierMatches_Dialog + "//table[1]//td[3]")));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierMatches_Dialog + "//select[@class='input-xs']"), "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierMatches_Dialog + "//select[@class='input-xs']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierMatches_Dialog + "//a[text()='Confirm']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierMatches_Dialog + "//a[text()='Cancel']")));

            Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierMatches_Dialog + "//select[@class='input-xs']"), appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierMatches_Dialog + "//select[@class='input-xs']"), "List", "General#Strategic")));
            supplierType = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierMatches_Dialog + "//select[@class='input-xs']"), "Dropdown");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierMatches_Dialog + "//a[text()='Confirm']")));
            reports.writeResult(oBrowser, "Screenshot", "Supplier Type Matches dialog for '" + supplierName + "': After changing the supplier type");
            matchedSupplierName = appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_SupplierMatches_Dialog + "//table)[2]//td[2]"), "Text");
            matchedSupplierTIN = appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_SupplierMatches_Dialog + "//table)[2]//td[3]"), "Text");

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_OpenSupplierInNewTab_Link));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ViewActivities_Btn, "Clickable", "", minTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "The supplier '" + matchedSupplierName + "' was opened in the new tab");
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SupplierName_Header, "Text", matchedSupplierName));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Company_Show_Grid + "//dl/dd[1]"), "Text").contains(matchedSupplierName));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Company_Show_Grid + "//dl/dd[3]"), "Text", matchedSupplierTIN));
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierMatches_Dialog + "//button[@class='close']")));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SupplierTypeMatches_Refresh_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", minTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierTypeMatches_Grid + "//table//tr[contains(@class, 'lines dx-column-lines')]/td[4]"), "Text", supplierType));
            reports.writeResult(oBrowser, "Screenshot", "The new supplier '" + supplierName + "' was updated in the 'Supplier Type Matches' grid");
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateSupplierTypeMatches()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateSupplierTypeMatches()' method. " + e);
            return false;
        } finally {
            supplierName = null; supplierType = null; matchedSupplierName = null; matchedSupplierTIN = null; caseDetails = null; response = null;
        }
    }


    /********************************************************
     * Method Name      : validateMCCMatches()
     * Purpose          : user validates the MCC matches functionality under 'Solution Design--> MCC Matches' section of Account setup page
     * Author           : Gudi
     * Parameters       : oBrowser, queryKey
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateMCCMatches(WebDriver oBrowser, String queryKey) {
        String supplierName = null;
        String supplierType = null;
        String matchedSupplierName = null;
        String matchedSupplierTIN = null;
        Response response = null;
        JSONArray caseDetails = null;
        try {
            caseDetails = navigateAndSelectGeneralCompanies(oBrowser, "Prospect", queryKey);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SolutionDesigns_Link));
            appDep.threadSleep(4000);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MccMatches_Link));
            appDep.threadSleep(4000);
            reports.writeResult(oBrowser, "Screenshot", "'MCC Matches' page");
            supplierName = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_MCCMatches_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td[1]"), "Text");
            supplierType = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_MCCMatches_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td[5]"), "Text");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_MCCMatches_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td[1]")));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_MCCMatches_Dialog + "//input[@id='matched_search']"), "Clickable", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "MCC Matches dialog for '" + supplierName + "': Before changing the supplier type");
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_MCCMatches_Dialog + "//h4/span)[2]"), "Text", "Current Supplier"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_MCCMatches_Dialog + "//h4/span)[3]"), "Text", "Matched Suppliers"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_MCCMatches_Dialog + "//table)[1]//td[1]"), "Text", supplierName));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_MCCMatches_Dialog + "//table)[1]//td[3]"), "Text", supplierType));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_MCCMatches_Dialog + "//table)[1]//td[3]")));
            appDep.waitForTheElementState(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_MCCMatches_Dialog + "//table)[1]//td/select[@class='input-xs']"), "Clickable", "", minTimeOut);

            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_MCCMatches_Dialog + "//table)[1]//td/select[@class='input-xs']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_MCCMatches_Dialog + "//table)[1]//td/a[text()='Confirm']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_MCCMatches_Dialog + "//table)[1]//td/a[text()='Cancel']")));

            Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_MCCMatches_Dialog + "//table)[1]//td/select[@class='input-xs']"), appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_MCCMatches_Dialog + "//table)[1]//td/select[@class='input-xs']"), "List", "General#Strategic")));
            supplierType = appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_MCCMatches_Dialog + "//table)[1]//td/select[@class='input-xs']"), "Dropdown");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_MCCMatches_Dialog + "//table)[1]//td/a[text()='Confirm']")));
            reports.writeResult(oBrowser, "Screenshot", "MCC Matches dialog for '" + supplierName + "': After changing the supplier type");
            matchedSupplierName = appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_MCCMatches_Dialog + "//table)[2]//td[1]"), "Text");
            matchedSupplierTIN = appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_MCCMatches_Dialog + "//table)[2]//td[2]"), "Text");

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_MCCMatches_Dialog+"//a[@title='Open Supplier in New Tab']")));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ViewActivities_Btn, "Clickable", "", minTimeOut);

            reports.writeResult(oBrowser, "Screenshot", "The supplier '" + matchedSupplierName + "' was opened in the new tab");
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SupplierName_Header, "Text", matchedSupplierName));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Company_Show_Grid + "//dl/dd[1]"), "Text").contains(matchedSupplierName));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Company_Show_Grid + "//dl/dd[3]"), "Text", matchedSupplierTIN));
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_MCCMatches_Dialog + "//button[@class='close']")));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SupplierTypeMatches_Refresh_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", minTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_MCCMatches_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td[5]"), "Text", supplierType));
            reports.writeResult(oBrowser, "Screenshot", "The new supplier '" + supplierName + "' was updated in the 'MCC Matches' grid");
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateMCCMatches()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateMCCMatches()' method. " + e);
            return false;
        } finally {
            supplierName = null; supplierType = null; matchedSupplierName = null; matchedSupplierTIN = null; caseDetails = null; response = null;
        }
    }


    public boolean createNewRecordForPaymentProcessTab(WebDriver oBrowser, DataTable dataTable){
        Actions oAction = null;
        List<Map<String, String>> paymentData;
        Map<String, String> payments;

        try {
            paymentData = dataTable.asMaps(String.class, String.class);
            payments = new HashMap<>();
            payments.put("Universal", paymentData.get(0).get("Universal"));
            payments.put("Client-VendorID", payments.get("Client-VendorID"));
            payments.put("Account_Number", paymentData.get(0).get("Account_Number"));
            payments.put("Method", paymentData.get(0).get("Method"));
            payments.put("Address", paymentData.get(0).get("Address"));
            payments.put("ServiceName", paymentData.get(0).get("ServiceName"));
            payments.put("ServiceValue", paymentData.get(0).get("ServiceValue"));
            payments.put("AdditionalValue", paymentData.get(0).get("AdditionalValue"));
            payments.put("AdditionalStep", paymentData.get(0).get("AdditionalStep"));
            payments.put("AdditionalNotes", paymentData.get(0).get("AdditionalNotes"));



            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentProcess_Link));
            Assert.assertTrue(appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_WaveType_Add_Link, "Clickable", "", waitTimeOut));
            appInd.scrollToThePage(oBrowser, "bottom");
            Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_WaveType_Add_Link));

            if(payments.get("Universal").equalsIgnoreCase("yes")){
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Payment_Submit_Btn)));
                reports.writeResult(oBrowser, "Screenshot", "After toggle the Supplier Payment Process");
            }

            oAction = new Actions(oBrowser);
            oAction.sendKeys(oBrowser.findElement(PayCRM_Modules_GeneralUIPage.obj_GenralAccountNumbers_New), payments.get("Account_Number")).sendKeys(Keys.ENTER).build().perform();

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_GenralServiceInformation_List)));
            appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_GenralServiceInformation_List), payments.get("ServiceValue"));

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_GenralAdditionalInformation_Name)));
            appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_GenralAdditionalInformation_Name), payments.get("AdditionalNotes"));

            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Payment_create_Btn), "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Payment_create_Btn)));

            return true;

        }catch (Exception e){
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createNewRecordForPaymentProcessTab()' method. " + e);
            return false;
        }finally{
            oAction=null; paymentData=null; payments=null;
        }
    }

    public boolean verifyTabGridRecord(WebDriver oBrowser, String tabName, Map<String, String> paymentProcessDataObject) {
        String attribute = "Column %s, Value %s";
        List<String> mappingList = new ArrayList<>();
        payCRM_modules_DailyGrindUIBaseStep.verifyTabFromWaveDetailPage(oBrowser, tabName);
        paymentProcessDataObject.forEach((key, value) -> {
            value = value != null ? value : "";
            mappingList.add(String.format(attribute, key, value).trim());
        });
        List<WebElement> rows = appInd.getWebElements(oBrowser, PayCRM_Modules_GeneralUIPage.obj_grid_data_verification);
        boolean isDataFound = false;
        for(WebElement row : rows) {
            List<WebElement> columns = row.findElements(By.xpath("td"));
            columns.remove(columns.size() - 1);
            for (int i = 0; i < columns.size(); i++) {
                if(columns.get(i).getAttribute("aria-label").trim().equalsIgnoreCase(mappingList.get(i))){
                    reports.writeResult(oBrowser, "pass", "Universal Payment Process Grid Cell Value Match : "+mappingList.get(i));
                    Assert.assertTrue(true);
                    isDataFound=true;
                }
                else{
                    isDataFound=false;
                    break;
                }
            }
            if(isDataFound)
                break;
        }
        if(!isDataFound){
            reports.writeResult(oBrowser, "screenshot", "Universal Payment Process record is not found");
            reports.writeResult(oBrowser, "fail", "Universal Payment Process record is not found");
        }
        return isDataFound;
    }

    public boolean verifyAvailableLinksOnPopup(WebDriver oBrowser, List<String> listOfLinks) {
        listOfLinks.forEach(link -> Assert.assertTrue(appDep.waitForTheElementState(oBrowser, By.linkText(link), "visibility", "", waitTimeOut)
        , "Link " + link + " is not visible on the page"));
        return true;
    }

    public boolean veifyTabGridHeaders(WebDriver oBrowser, List<String> headers){
        appInd.getWebElements(oBrowser, PayCRM_Modules_GeneralUIPage.obj_grid_column_header)
                .forEach( webElement -> Assert.assertTrue(headers.contains(webElement.getText().trim())));
        return true;
    }



    public boolean verifyAppliedFilterOnGridColumn(WebDriver oBrowser, Map<String, String> mapping){
        appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_CaseNumber_Grid_Pagination, "Visibility", "", waitTimeOut);
        appDep.threadSleep(2000);
        List<WebElement> columnsList = appInd.getWebElements(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Grid_Heading);
        Set<String> columnSet = mapping.keySet();
        columnSet.forEach(column -> {
            AtomicInteger index= new AtomicInteger();
            columnsList.forEach(webElement -> {
                if(webElement.getText().trim().equalsIgnoreCase(column))
                  index.set(columnsList.indexOf(webElement));
            });
            String columnData = String.format(PayCRM_Modules_GeneralUIPage.obj_Grid_Column_Heading_Text,column);
            appDep.waitForTheElementState(oBrowser, By.xpath(columnData),"Clickable","",waitTimeOut);
            String cellData = String.format(PayCRM_Modules_GeneralUIPage.obj_Grid_Column_Cell_Data,index.incrementAndGet());
            List<WebElement> columData = appInd.getWebElements(oBrowser, By.xpath(cellData));
            for(int i=1;i<=15;i++){
                Assert.assertTrue(columData.get(i).getText().trim().startsWith(mapping.get(column)));
            }
        });
        return true;
    }


    /********************************************************
     * Method Name      : updateCorcentricAnalysisFromInternalSetup()
     * Purpose          : user updates Corcentric Analysis from Internal Setup and validates the same
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean updateCorcentricAnalysisFromInternalSetup(WebDriver oBrowser, DataTable dataTable) {
        List<Map<String, String>> corcentricAnalysisData = null;
        Map<String, String> corcentricAnalysis = null;
        int rowCount = 0;
        List<String> objServices = null;
        String arrServices[];
        String arrCorcentric[];
        try {
            objServices = new ArrayList<String>();
            corcentricAnalysisData = dataTable.asMaps(String.class, String.class);
            corcentricAnalysis = new HashMap<String, String>();
            corcentricAnalysis.put("CorcentricEffortPercentage", corcentricAnalysisData.get(0).get("CorcentricEffortPercentage"));
            corcentricAnalysis.put("Other", corcentricAnalysisData.get(0).get("Other"));
            corcentricAnalysis.put("Corcentric", corcentricAnalysisData.get(0).get("Corcentric"));

            appDep.navigateToInternalSetupPage(oBrowser, "AccountSetup");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CorcentricAnalysis_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CorcentricEffortPercentage_Edit, "Clickable", "", minTimeOut);

            reports.writeResult(oBrowser, "Screenshot", "'Corcentric Analysis' internal setup Before");
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CorcentricEffortPercentage_Edit, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CorcentricEffortPercentage_Edit, "Value", corcentricAnalysis.get("CorcentricEffortPercentage"))));
            corcentricAnalysis.remove("CorcentricEffortPercentage");
            corcentricAnalysis.put("CorcentricEffortPercentage", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CorcentricEffortPercentage_Edit, "Value"));

            rowCount = oBrowser.findElements(By.xpath(PayCRM_Modules_GeneralUIPage.obj_CorcentricAnalysis_Grid + "/tbody/tr")).size();
            for (int i = 0; i < rowCount; i++) {
                Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_CorcentricAnalysis_Grid + "/tbody/tr/td[6]/input)[" + (i + 1) + "]"), String.valueOf(i + 1)));
            }

            arrServices = corcentricAnalysis.get("Other").split("#");
            arrCorcentric = corcentricAnalysis.get("Corcentric").split("#");
            for (int i = 0; i < arrServices.length; i++) {
                objServices.add(appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_CorcentricAnalysis_Grid + "/tbody/tr/td[1]/input)[" + (i + 1) + "]"), "Value"));
                Assert.assertTrue(appInd.validateAndSelectTheCheckbox(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_CorcentricAnalysis_Grid + "/tbody/tr/td[4]/input)[" + (i + 1) + "]"), arrServices[i]));
                Assert.assertTrue(appInd.validateAndSelectTheCheckbox(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_CorcentricAnalysis_Grid + "/tbody/tr/td[5]/input)[" + (i + 1) + "]"), arrCorcentric[i]));
            }

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CorcentricAnalysis_Update_Btn));
            appDep.threadSleep(4000);
            reports.writeResult(oBrowser, "Screenshot", "'Corcentric Analysis' internal setup After");
            oBrowser.navigate().refresh();
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", maxWaitTimeOut);
            appDep.navigateToInternalSetupPage(oBrowser, "AccountSetup");
            appInd.scrollToThePage(oBrowser, "Top");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SolutionDesigns_Link));
            appDep.threadSleep(4000);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_Link));
            appDep.threadSleep(4000);
            reports.writeResult(oBrowser, "Screenshot", "Validate 'Corcentric Analysis' internal setup values under 'Results with Corcentric' section");
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CorcentricEffortPercentage_Label, "Text", corcentricAnalysis.get("CorcentricEffortPercentage") + ".0%"));

            for (int i = 0; i < objServices.size(); i++) {
                if (arrServices[i].equalsIgnoreCase("True")) {
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_CorcentricFeatures_Grid + "//table//td[text()='" + objServices.get(i) + "']/following-sibling::td/*[@class='glyph-icon icon-check green'])[1]")));
                } else {
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_CorcentricFeatures_Grid + "//table//td[text()='" + objServices.get(i) + "']/following-sibling::td/*[@class='glyph-icon icon-close red'])[1]")));
                }

                if (arrCorcentric[i].equalsIgnoreCase("True")) {
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_CorcentricFeatures_Grid + "//table//td[text()='" + objServices.get(i) + "']/following-sibling::td[2]/input[@checked])[1]")));
                } else {
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_CorcentricFeatures_Grid + "//table//td[text()='" + objServices.get(i) + "']/following-sibling::td[2]/input[not(@checked)])[1]")));
                }
            }
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'updateCorcentricAnalysisFromInternalSetup()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'updateCorcentricAnalysisFromInternalSetup()' method. " + e);
            return false;
        } finally {
            corcentricAnalysisData = null; corcentricAnalysis = null; objServices = null; arrServices = null; arrCorcentric = null;
        }
    }



    /********************************************************
     * Method Name      : validateTheSupplierTabs()
     * Purpose          : user validates the Activities, Cases, Address, Contacts, Payment List, Payment notes & Additional Information tabs functionality for the selected Wave
     * Author           : Gudi
     * Parameters       : oBrowser, queryKey
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateTheSupplierTabs(WebDriver oBrowser, String queryKey){
        String expectedRecordLimits = "1000";
        String actualRecordLimits = null;
        Response response = null;
        JSONArray caseDetails = null;
        try{
            caseDetails = navigateAndSelectGeneralSupplier(oBrowser, queryKey);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ViewActivities_Btn, "Clickable", "", minTimeOut);

            //Case Activities tab
            Assert.assertTrue(appDep.searchCaseActivitiesByDateRange(oBrowser, ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("case_activity_date_range_start").toString()+" - "+appInd.getDateTime("MM/dd/yyyy"), By.xpath(PayCRM_Modules_GeneralUIPage.obj_Suppliers_CaseActivities_Grid +"//tr[contains(@class, 'row-lines dx-column-lines')]/td[1]")));

            appInd.scrollToThePage(oBrowser, "Bottom");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Suppliers_CaseActivities_Grid + "//tr[contains(@class, 'row-lines dx-column-lines')]")));
            reports.writeResult(oBrowser, "Screenshot", "validating 'Case Activities' Tab");

            //Cases Tab
            appInd.scrollToThePage(oBrowser, "Top");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCases_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Cases_RecordLimitFilter_Edit, expectedRecordLimits));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientCases_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appDep.threadSleep(2000);
            actualRecordLimits = (appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Suppliers_Cases_Grid + "//div[contains(text(), 'Displaying Page 1 of ')]"), "Text").split(" "))[5].replace("(", "");
            Assert.assertTrue((Integer.parseInt(actualRecordLimits) <= Integer.parseInt(expectedRecordLimits)), "Mis-match in the search results count by appying records limit value");
            reports.writeResult(oBrowser, "Screenshot", "validating 'Cases' Tab");

            //Address Tab
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Cases_Address_Tab));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Suppliers_Address_Grid + "//tr[contains(@class, 'row-lines dx-column-lines')]/td[9][text()='Primary']")));
            reports.writeResult(oBrowser, "Screenshot", "validating 'Addresses' Tab");


            //Contacts Tab
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Cases_Contacts_Tab));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Suppliers_Contacts_Grid + "//tr[contains(@class, 'row-lines dx-column-lines')]/td[9][text()='Primary']")));
            reports.writeResult(oBrowser, "Screenshot", "validating 'Contacts' Tab");

            //Payment Notes
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentNotes_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentNotes_Grid + "//div[@class='panel-heading']"), "Text", "Salesforce Payment Notes"));
            reports.writeResult(oBrowser, "Screenshot", "validating 'Payment Notes' Tab");

            //Additional Information
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AdditionalInformation_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            int count = oBrowser.findElements(By.xpath(PayCRM_Modules_GeneralUIPage.obj_Suppliers_AdditionalInformation_Grid + "//div[@class='panel-heading']")).size();
            if(count == 5){
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_Suppliers_AdditionalInformation_Grid +"//div[@class='panel-heading'])[1]"), "Text", "Legacy Data"));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_Suppliers_AdditionalInformation_Grid +"//div[@class='panel-heading'])[2]"), "Text", "System Xref"));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_Suppliers_AdditionalInformation_Grid +"//div[@class='panel-heading'])[3]"), "Text", "Preferences"));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_Suppliers_AdditionalInformation_Grid +"//div[@class='panel-heading'])[4]"), "Text", "Profile"));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_Suppliers_AdditionalInformation_Grid +"//div[@class='panel-heading'])[5]"), "Text", "Settings"));
            }else{
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_Suppliers_AdditionalInformation_Grid +"//div[@class='panel-heading'])[1]"), "Text", "System Xref"));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_Suppliers_AdditionalInformation_Grid +"//div[@class='panel-heading'])[2]"), "Text", "Preferences"));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_Suppliers_AdditionalInformation_Grid +"//div[@class='panel-heading'])[3]"), "Text", "Profile"));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_Suppliers_AdditionalInformation_Grid +"//div[@class='panel-heading'])[4]"), "Text", "Settings"));
            }

            reports.writeResult(oBrowser, "Screenshot", "validating 'Additional Information' Tab");
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateTheSupplierTabs()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateTheSupplierTabs()' method. " + e);
            return false;
        }finally {expectedRecordLimits = null; actualRecordLimits = null; response = null; caseDetails = null;}
    }




    /********************************************************
     * Method Name      : createNewUniversalPaymentProcess()
     * Purpose          : user creates universal payment process and validates the same from Modules-->General-->Suppliers
     * Author           : Gudi
     * Parameters       : oBrowser, paymentStatus, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean createNewUniversalPaymentProcess(WebDriver oBrowser, String paymentStatus, DataTable dataTable){
        List<Map<String, String>> paymentData = null;
        Map<String, String> payment = null;
        String timeStamp = null;
        try{
            timeStamp = appInd.getDateTime("Shhmmss");
            paymentData = dataTable.asMaps(String.class, String.class);
            payment = new HashMap<String, String>();
            payment.put("Supplier", paymentData.get(0).get("Supplier"));
            payment.put("AccountNumber", timeStamp);
            payment.put("Method", paymentData.get(0).get("Method"));
            payment.put("Address", paymentData.get(0).get("Address"));
            payment.put("ServiceInformation", paymentData.get(0).get("ServiceInformation"));
            payment.put("AdditionalInformation", paymentData.get(0).get("AdditionalInformation"));
            payment.put("Steps", paymentData.get(0).get("Steps"));
            payment.put("Notes", paymentData.get(0).get("Notes"));
            payment.put("SecurityQuestions", paymentData.get(0).get("SecurityQuestions"));
            payment.put("Notes", paymentData.get(0).get("Notes"));

            reports.writeResult(oBrowser, "Screenshot", "Suppliers-->Show page opened");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentProcess_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Add_Link));

            Assert.assertTrue(appDep.paymentProcess(oBrowser, paymentStatus, payment));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_PaymentProcess_Grid +"//tr//input)[4]"), payment.get("AccountNumber")));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "After creating the 'Universal payment process'");
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentProcess_Grid +"//tr[1][contains(@class, 'row-lines dx-column-lines')]/td[1]"), "Text", "Universal Payment Process"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentProcess_Grid +"//tr[1][contains(@class, 'row-lines dx-column-lines')]/td[3]"), "Text", payment.get("Method")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentProcess_Grid +"//tr[1][contains(@class, 'row-lines dx-column-lines')]/td[4]"), "Text", payment.get("AccountNumber")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentProcess_Grid +"//tr[1][contains(@class, 'row-lines dx-column-lines')]/td[5]"), "Text", payment.get("Address")));

            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentProcess_Grid +"//tr[1][contains(@class, 'row-lines dx-column-lines')]/td[1]/*[@title='Universal Payment Process']")));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentProcess_Grid +"//tr[1][contains(@class, 'row-lines dx-column-lines')]/td[1]/*[@title='Universal Payment Process']")));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentProcess_Dialog +"//div[contains(@class, 'text-header')]"), "Visibility", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "'Universal payment process' dialog");
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentProcess_Dialog +"//table[1]//tr[1]/td[1]"), "Text", payment.get("AccountNumber")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentProcess_Dialog +"//table[1]//tr[2]/td[1]"), "Text", payment.get("Method")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentProcess_Dialog +"//table[1]//tr[3]/td[1]"), "Text", payment.get("Address")));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_PaymentProcess_Dialog +"//button/*)[1]")));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createNewUniversalPaymentProcess()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createNewUniversalPaymentProcess()' method. " + e);
            return false;
        }finally{paymentData = null; payment = null; timeStamp = null;}
    }





    /********************************************************
     * Method Name      : generateProviderFile()
     * Purpose          : user generates Provider file from Modules-->General-->Suppliers
     * Author           : Gudi
     * Parameters       : oBrowser, paymentStatus, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean generateProviderFile(WebDriver oBrowser){
        boolean blnRes = false;
        String timeStamp = null;
        try{
            timeStamp = appInd.getDateTimeByTimezone("yyyy_MM_dd", "CST");
            appDep.navigateToProspectCompanyAttachmentScreen(oBrowser, "HomePage");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ImportWaves_Btn));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Attachment_Section +"//span[@id='generate_provider_li']/a")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appDep.threadSleep(8000);
            blnRes = appDep.validateDownloadedFileAndDelete(oBrowser, "Provider_Match_"+timeStamp, ".csv", "", "Yes");
            if (blnRes) {
                reports.writeResult(oBrowser, "Pass", "The 'Generate Provider File' excel file was generated successful");
            } else {
                reports.writeResult(oBrowser, "Fail", "Failed to generate the 'Generate Provider File' excel file");
                Assert.assertTrue(false, "Failed to generate the 'Generate Provider File' csv file");
            }
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'generateProviderFile()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'generateProviderFile()' method. " + e);
            return false;
        }finally{timeStamp = null;}
    }





    /********************************************************
     * Method Name      : importSupplierFile()
     * Purpose          : user imports supplier file
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : Map<String, String>
     ********************************************************/
    public Map<String, String> importSupplierFile(WebDriver oBrowser, DataTable dataTable){
        List<Map<String, String>> supplierTestData = null;
        Map<String, String> supplierData = null;
        String timeStamp = null;
        try{
            timeStamp = appInd.getDateTime("Shhmmss");
            supplierTestData = dataTable.asMaps(String.class, String.class);
            supplierData = new HashMap<>();
            supplierData.put("Name", supplierTestData.get(0).get("Name")+timeStamp);
            supplierData.put("Wave", supplierTestData.get(0).get("Wave"));
            supplierData.put("Version", supplierTestData.get(0).get("Version"));
            supplierData.put("Status", supplierTestData.get(0).get("Status"));
            supplierData.put("Priority", supplierTestData.get(0).get("Priority"));
            supplierData.put("FileToUpload", supplierTestData.get(0).get("FileToUpload"));

            reports.writeResult(oBrowser, "Screenshot", "Prospect company--> Attachments screen");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Attachment_Section +"//span[@id='import_suppliers_li']")));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_NewSupplierImport_Dialog +"//button[@name='button']"), "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_NewSupplierImport_Dialog +"//h4"), "Text", "New Supplier Import"));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFile_Name_Edit, supplierData.get("Name")));

            supplierData.remove("Wave");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Wave_List)));
            supplierData.put("Wave", appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFile_Wave_List + "/parent::div//div[@class='chosen-drop']/ul/li[2]"), "Text"));
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Wave_List +"//input"), supplierData.get("Wave")));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Wave_List +"//ul/li/em[text()='"+supplierData.get("Wave")+"']")));

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFile_Version_Edit, supplierData.get("Version")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFile_Status_List, supplierData.get("Status")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFile_Priority_List, supplierData.get("Priority")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ImportSuppliers_Upload_Edit, System.getProperty("user.dir").replace("\\", "/") + "/TestData/" + supplierData.get("FileToUpload")));

            reports.writeResult(oBrowser, "Screenshot", "Entered all the details in the 'Import Suppliers' dialog before clicking 'Create' button");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_NewSupplierImport_Dialog +"//button[@name='button']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierFileImport_Grid +"//*[@class='dx-icon dx-icon-refresh']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_SupplierFileImport_Grid+"//input)[2]"), supplierData.get("Name")));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "The 'Import Suppliers' was created successful");
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierFileImport_Grid +"//tr[contains(@class, 'row-lines dx-column-lines')][1]/td[1]"), "Text", "MSF File"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierFileImport_Grid +"//tr[contains(@class, 'row-lines dx-column-lines')][1]/td[2]"), "Text", supplierData.get("Name")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierFileImport_Grid +"//tr[contains(@class, 'row-lines dx-column-lines')][1]/td[3]"), "Text", supplierData.get("FileToUpload")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierFileImport_Grid +"//tr[contains(@class, 'row-lines dx-column-lines')][1]/td[4]"), "Text", supplierData.get("Wave")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierFileImport_Grid +"//tr[contains(@class, 'row-lines dx-column-lines')][1]/td[5]"), "Text", "Imported"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierFileImport_Grid +"//tr[contains(@class, 'row-lines dx-column-lines')][1]/td[6]"), "Text", supplierData.get("Status")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierFileImport_Grid +"//tr[contains(@class, 'row-lines dx-column-lines')][1]/td[7]"), "Text", supplierData.get("Priority")));
            supplierData.put("TestPassed", "True");
            return supplierData;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'importSupplierFile()' method. " + e);
            supplierData.put("TestPassed", "False");
            return supplierData;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'importSupplierFile()' method. " + e);
            supplierData.put("TestPassed", "False");
            return supplierData;
        }
    }





    /********************************************************
     * Method Name      : userImportsSupplierFileAndValidateTheSame()
     * Purpose          : user imports supplier and validates the same from Modules-->General-->prospect Companies->Attachment
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean userImportsSupplierFileAndValidateTheSame(WebDriver oBrowser, DataTable dataTable){
        Map<String, String> supplier = null;
        try{
            supplier = importSupplierFile(oBrowser, dataTable);
            Assert.assertTrue(supplier.get("TestPassed").equalsIgnoreCase("True"), "The method 'importSupplierFile()' was failed");
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'userImportsSupplierFileAndValidateTheSame()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'userImportsSupplierFileAndValidateTheSame()' method. " + e);
            return false;
        }finally {supplier = null;}
    }





    /********************************************************
     * Method Name      : importMatchesFile()
     * Purpose          : user imports Matches file
     * Author           : Gudi
     * Parameters       : oBrowser, matchesData
     * ReturnType       : boolean
     ********************************************************/
    public Map<String, String> importMatchesFile(WebDriver oBrowser, Map<String, String> matchesData){
        try{
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Attachment_Section +"//span[@id='import_matches_li']")));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Matches_Dialog +"//button[@name='button']"), "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Matches_Dialog +"//h4"), "Text", "New Match Import"));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MatchFiles_Name_Edit, matchesData.get("Name")));

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MatchFiles_Version_Edit, matchesData.get("Version")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MatchFiles_Status_List, matchesData.get("Status")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MatchFiles_Priority_List, matchesData.get("Priority")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ImportMatches_Upload_Edit, System.getProperty("user.dir").replace("\\", "/") + "/TestData/" + matchesData.get("FileToUpload")));

            reports.writeResult(oBrowser, "Screenshot", "Entered all the details in the 'Import Matches' dialog before clicking 'Create' button");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Matches_Dialog +"//button[@name='button']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierFileImport_Grid +"//*[@class='dx-icon dx-icon-refresh']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_SupplierFileImport_Grid+"//input)[2]"), matchesData.get("Name")));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "The 'Import Matches' was created successful");
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierFileImport_Grid +"//tr[contains(@class, 'row-lines dx-column-lines')][1]/td[1]"), "Text", "Match File"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierFileImport_Grid +"//tr[contains(@class, 'row-lines dx-column-lines')][1]/td[2]"), "Text", matchesData.get("Name")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierFileImport_Grid +"//tr[contains(@class, 'row-lines dx-column-lines')][1]/td[3]"), "Text", matchesData.get("FileToUpload")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierFileImport_Grid +"//tr[contains(@class, 'row-lines dx-column-lines')][1]/td[5]"), "Text", "Imported"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierFileImport_Grid +"//tr[contains(@class, 'row-lines dx-column-lines')][1]/td[6]"), "Text", matchesData.get("Status")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierFileImport_Grid +"//tr[contains(@class, 'row-lines dx-column-lines')][1]/td[7]"), "Text", matchesData.get("Priority")));
            matchesData.put("TestPassed", "True");
            return matchesData;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'importMatchesFile()' method. " + e);
            matchesData.put("TestPassed", "False");
            return matchesData;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'importMatchesFile()' method. " + e);
            matchesData.put("TestPassed", "False");
            return matchesData;
        }
    }





    /********************************************************
     * Method Name      : userImportsMatchesFileAndValidateTheSame()
     * Purpose          : user imports Matches file and validates the same from Modules-->General-->prospect Companies->Attachment
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean userImportsMatchesFileAndValidateTheSame(WebDriver oBrowser, DataTable dataTable){
        List<Map<String, String>> matchesData = null;
        Map<String, String> matches = null;
        String timeStamp = null;
        try{
            timeStamp = appInd.getDateTime("Shhmmss");
            matchesData = dataTable.asMaps(String.class, String.class);
            matches = new HashMap<String, String>();
            matches.put("Name", matchesData.get(0).get("Name")+timeStamp);
            matches.put("Version", matchesData.get(0).get("Version"));
            matches.put("Status", matchesData.get(0).get("Status"));
            matches.put("Priority", matchesData.get(0).get("Priority"));
            matches.put("FileToUpload", matchesData.get(0).get("FileToUpload"));

            matches = importMatchesFile(oBrowser, matches);
            Assert.assertTrue(matches.get("TestPassed").equalsIgnoreCase("True"), "The method 'importMatchesFile()' was failed");

            //Click on the Munch icon
            Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierFileImport_Grid +"//tr[contains(@class, 'row-lines dx-column-lines')]//a[@title='Munch']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            oBrowser.navigate().refresh();
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", waitTimeOut);
            appDep.navigateToProspectCompanyAttachmentScreen(oBrowser, "Account SetUp");
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_SupplierFileImport_Grid+"//input)[2]"), matches.get("Name")));
            appDep.threadSleep(2000);

//            while(true){
//                matchFileImportStatus = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierFileImport_Grid +"//tr[contains(@class, 'row-lines dx-column-lines')][1]/td[5]"), "Text");
//                if(!matchFileImportStatus.equalsIgnoreCase("Completed")){
//                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierFileImport_Grid +"//*[@class='dx-icon dx-icon-refresh']")));
//                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
//                }else{
//                    break;
//                }
//                count++;
//                if(count==10){
//                    reports.writeResult(oBrowser, "Fail", "Failed to get the MatchFile imported file status to COMPLETED after the munch");
//                    Assert.fail("Failed to get the MatchFile imported file status to COMPLETED after the munch");
//                    break;
//                }
//            }
//
//            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierFileImport_Grid +"//tr[contains(@class, 'row-lines dx-column-lines')][1]/td[5]"), "Text", "Completed"));

            Assert.assertTrue(appDep.waitForMunchStatusToCompleted(oBrowser, "Match", 15));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'userImportsMatchesFileAndValidateTheSame()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'userImportsMatchesFileAndValidateTheSame()' method. " + e);
            return false;
        }finally {matchesData = null; matches = null; timeStamp = null;}
    }





    /********************************************************
     * Method Name      : createOrEditAttachment()
     * Purpose          : user creates/edits the other attachments from Modules-->General-->prospect Companies->Attachment-->Other Attachments
     * Author           : Gudi
     * Parameters       : oBrowser, attachmentStatus, attachmentData
     * ReturnType       : boolean
     ********************************************************/
    public Map<String, String> createOrEditAttachment(WebDriver oBrowser, String attachmentStatus, Map<String, String> attachmentData){
        String dialogHeader = null;
        String confirmationMsg = null;
        try{
            if(attachmentStatus.equalsIgnoreCase("Add")){
                dialogHeader = "New Attachment";
                confirmationMsg = "Attachment has been successfully created.";
                appInd.scrollToElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Add_Link);
                appInd.moveToElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Add_Link);
                Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Add_Link));
            }else{
                dialogHeader = "Edit Attachment";
                confirmationMsg = "Attachment has been successfully updated.";
                appInd.scrollToElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Edit_Link);
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Edit_Link));
            }

            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_NewAttachment_Dialog + "//button[@name='button']"), "Clickable", "", minTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Before "+attachmentStatus + " other attachments");
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_NewAttachment_Dialog + "//h4"), "Text", dialogHeader));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SupplierAttachment_Name_Edit, attachmentData.get("Name")));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SupplierAttachment_Description_Textarea, attachmentData.get("Description")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AttachmentType_List, attachmentData.get("AttachmentType")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SupplierAttachment_Status_List, attachmentData.get("Status")));

            if(attachmentStatus.equalsIgnoreCase("Edit")){
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_FileName_Edit, "Value").contains(otherAttachment));
            }


            if(attachmentStatus.equalsIgnoreCase("Add")){
                //Assert.assertTrue(appDep.uploadFileUsingRobotClass(System.getProperty("user.dir") + "\\TestData\\" + attachmentData.get("FileToUpload") + ".xlsx"));
                Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_OtherAttachments_Upload_Edit, System.getProperty("user.dir").replace("\\", "/") + "/TestData/" + attachmentData.get("FileToUpload")));
                appDep.threadSleep(2000);
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_NewAttachment_Dialog + "//input[@class='form-control ']"), "Value", attachmentData.get("FileToUpload")));
            }else{
                //Assert.assertTrue(appDep.uploadFileUsingRobotClass(System.getProperty("user.dir") + "\\TestData\\" + attachmentData.get("FileToUpload") + ".png"));
                Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_OtherAttachments_Upload_Edit, System.getProperty("user.dir").replace("\\", "/") + "/TestData/" + attachmentData.get("FileToUpload")));
                appDep.threadSleep(2000);
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_NewAttachment_Dialog + "//input[@class='form-control ']"), "Value", attachmentData.get("FileToUpload")));
            }
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "After entering all the details for "+attachmentStatus + " other attachments");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_NewAttachment_Dialog + "//button[@name='button']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", minTimeOut);
            appInd.scrollToThePage(oBrowser, "Top");
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CompanyEditConfirmationMessage_Label, "Text").contains(confirmationMsg));
            attachmentData.put("TestPassed", "True");
            return attachmentData;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createOrEditAttachment()' method. " + e);
            attachmentData.put("TestPassed", "False");
            return attachmentData;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createOrEditAttachment()' method. " + e);
            attachmentData.put("TestPassed", "False");
            return attachmentData;
        }finally {dialogHeader = null; confirmationMsg = null;}
    }





    /********************************************************
     * Method Name      : validateOtherAttachmentDetails()
     * Purpose          : user validates the data after creates/edits the other attachments
     * Author           : Gudi
     * Parameters       : oBrowser, attachmentStatus, attachmentData
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateOtherAttachmentDetails(WebDriver oBrowser, String attachmentStatus, Map<String, String> attachmentData){
        try{
            appInd.scrollToThePage(oBrowser, "Bottom");
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_OtherAttachments_Grid +"//input)[1]"), attachmentData.get("Name")));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_OtherAttachments_Grid + "//tr[contains(@class, 'row-lines dx-column-lines')]/td[1]"), "Text", attachmentData.get("Name")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_OtherAttachments_Grid + "//tr[contains(@class, 'row-lines dx-column-lines')]/td[2]"), "Text", attachmentData.get("Description")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_OtherAttachments_Grid + "//tr[contains(@class, 'row-lines dx-column-lines')]/td[3]"), "Text").contains(attachmentData.get("AttachmentType")));

            //Show page
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Queues_Show_Link));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Attachment_Show_Grid + "//dl/dd/a/*[contains(@class, 'icon-download')]"), "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Attachment_Show_Grid + "//h4"), "Text", "Attachment"));

            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Attachment_Show_Grid + "//dl/dd[1]"), "Text", attachmentData.get("Name")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Attachment_Show_Grid + "//dl/dd[2]"), "Text", attachmentData.get("Description")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Attachment_Show_Grid + "//dl/dd[3]"), "Text", attachmentData.get("Status")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Attachment_Show_Grid + "//dl/dd[4]/a"), "Text").contains(attachmentData.get("FileToUpload")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Attachment_Show_Grid + "//dl/dd[5]"), "Text", attachmentData.get("AttachmentType")));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Attachment_Show_Grid + "//button[@class='close']")));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createAttachmentUnderOtherAttachments()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createAttachmentUnderOtherAttachments()' method. " + e);
            return false;
        }
    }

    /********************************************************
     * Method Name      : createAttachmentUnderOtherAttachments()
     * Purpose          : user create/edit attachments from Modules-->General-->prospect Companies->Attachment-->Other attachments
     * Author           : Gudi
     * Parameters       : oBrowser, attachmentStatus, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean createAttachmentUnderOtherAttachments(WebDriver oBrowser, String attachmentStatus, DataTable dataTable){
        List<Map<String, String>> attachmentData = null;
        Map<String, String> attachment = null;
        String timeStamp = null;
        try{
            timeStamp = appInd.getDateTime("Shhmmss");
            attachment = new HashMap<String, String>();
            attachmentData = dataTable.asMaps(String.class, String.class);
            attachment.put("Name", attachmentData.get(0).get("Name")+timeStamp);
            attachment.put("Description", attachmentData.get(0).get("Description"));
            attachment.put("AttachmentType", attachmentData.get(0).get("AttachmentType"));
            attachment.put("Status", attachmentData.get(0).get("Status"));
            attachment.put("FileToUpload", attachmentData.get(0).get("FileToUpload"));
            if(attachmentStatus.equalsIgnoreCase("Add")){
                otherAttachment = attachment.get("FileToUpload");
            }else{
                otherAttachmentName = attachment.get("Name");
            }

            attachment = createOrEditAttachment(oBrowser, attachmentStatus, attachment);
            Assert.assertTrue(attachment.get("TestPassed").equalsIgnoreCase("True"), "The createOrEditAttachment() method was failed");
            Assert.assertTrue(validateOtherAttachmentDetails(oBrowser, attachmentStatus, attachment));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createAttachmentUnderOtherAttachments()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createAttachmentUnderOtherAttachments()' method. " + e);
            return false;
        }finally {
            attachmentData = null; attachment = null; timeStamp = null;
        }
    }

    /********************************************************
     * Method Name      : deleteAttachmentFromOtherAttachments()
     * Purpose          : user create/edit attachments from Modules-->General-->prospect Companies->Attachment-->Other attachments
     * Author           : Gudi
     * Parameters       : oBrowser, attachmentStatus, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean deleteAttachmentFromOtherAttachments(WebDriver oBrowser){
        Alert oAlert = null;
        try{
            Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_Delete_Link));
            appDep.threadSleep(2000);
            oAlert = oBrowser.switchTo().alert();
            Assert.assertTrue(oAlert.getText().equals("Are you sure? This will delete the attachment relationship to this company. If this attachment is not linked to a supplier or case, this will also delete the entire attachment."));
            oAlert.accept();
            appDep.threadSleep(4000);
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_OtherAttachments_Grid +"//input)[1]"), otherAttachmentName));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_OtherAttachments_Grid + "//tr[contains(@class, 'row-lines dx-column-lines')]/td[1]")));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'deleteAttachmentFromOtherAttachments()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'deleteAttachmentFromOtherAttachments()' method. " + e);
            return false;
        }finally{oAlert = null;}
    }

    /********************************************************
     * Method Name      : navigateToProspectCompanyGoLive()
     * Purpose          : user navigate to the Companies > Prospect > on any given ribbon name from random record
     * Author           : Deepak
     * Parameters       : oBrowser, columnName, filterRecord, ribbonName
     * ReturnType       : boolean
     ********************************************************/
    public boolean navigateToProspectCompanyGoLive(WebDriver oBrowser, String columnName, String filterRecord, String ribbonName){
        ribbonName = ribbonName.replace(" ","_").toLowerCase();
        String ribbonXpath = String.format(PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_RibbonPath, "preview_go_live");
        try{
            navigateToGeneralModuleRecord(oBrowser, "Companies", "divCompaniesContainer", columnName, filterRecord);
            navigateToRecord(oBrowser, "divCompaniesContainer", "Name",  appInd.getPropertyValueByKeyName("GoLiveProspectCompany"));
            appDep.threadSleep(3000);
            clickOnFirstRecord(oBrowser, "divCompaniesContainer");
            reports.writeResult(oBrowser, "screenshot","General Module Navigation is done");
            appDep.waitForTheElementState(oBrowser, By.xpath(ribbonXpath),"visibility","",waitTimeOut);
            appDep.threadSleep(1000);
            appInd.clickObject(oBrowser, By.xpath(ribbonXpath));
            reports.writeResult(oBrowser, "screenshot","click on "+ribbonXpath);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "invisibility","", waitTimeOut);
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'navigateToProspectCompanyGoLive()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'navigateToProspectCompanyGoLive()' method. " + e);
            return false;
        }
    }

    /********************************************************
     * Method Name      : verifyGoLiveShowPreviewAndCardFunctionality()
     * Purpose          : user verify GoLive Wave, Show Preview History table, Cards with supplier data
     * Author           : Deepak
     * Parameters       : oBrowser, columnName, filterRecord, ribbonName
     * ReturnType       : boolean
     ********************************************************/
    public boolean verifyGoLiveShowPreviewAndCardFunctionality(WebDriver oBrowser){
        try{
            String ribbonXpath = "//ul[@id='prospect_company_tabs']//a[@href='#preview_go_live']";
            String solutionDesign = "//ul[@id='prospect_company_tabs']//a[@href='#solution_design']";
            String supplier = String.format(PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_SolutionDesign_SubMenu, "Suppliers");
            String totalCount, totalTransection, totalSpent;
            //----------------------- Navigate to solution design > supplier to collect data ---------------------------
            appInd.clickObject(oBrowser, By.xpath(solutionDesign));
            appDep.threadSleep(2000);
            appInd.clickObject(oBrowser, By.xpath(supplier));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "invisibility","", waitTimeOut);
            appInd.scrollToThePage(oBrowser, "down");
            appDep.threadSleep(2000);
            totalCount = appInd.getTextOnElement(oBrowser, By.xpath("(//div[@class='dx-datagrid-total-footer dx-datagrid-nowrap']//tr//div)[1]"), "text");
            totalTransection = appInd.getTextOnElement(oBrowser, By.xpath("(//div[@class='dx-datagrid-total-footer dx-datagrid-nowrap']//tr//div)[2]"), "text");
            totalSpent = appInd.getTextOnElement(oBrowser, By.xpath("(//div[@class='dx-datagrid-total-footer dx-datagrid-nowrap']//tr//div)[3]"), "text");

            totalCount = totalCount.replaceAll("[A-Z a-z :]", "");
            totalTransection = totalTransection.replaceAll("[A-Z a-z () :]", "");
            totalSpent = totalSpent.replaceAll("[A-Z a-z () : $]","");
            totalSpent = totalSpent.replaceAll(",","");
            //----------------------- Navigate to Go-Live Preview for validation  ---------------------------
            appInd.scrollToThePage(oBrowser, "Top");


            appDep.threadSleep(1000);
            appInd.clickObject(oBrowser, By.xpath(ribbonXpath));
            reports.writeResult(oBrowser, "screenshot","click on "+ribbonXpath);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "invisibility","", waitTimeOut);
            appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_WaveDropDownID, 1);
            reports.writeResult(oBrowser, "screenshot","select wave from dropdown");
            appDep.threadSleep(2000);
            appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Generate_PreviewID);
            appDep.threadSleep(1000);
            Assert.assertTrue(appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_PreviewSuccessXpath,"visibility","",waitTimeOut));
            reports.writeResult(oBrowser, "screenshot", "generate preview is click");
            appDep.threadSleep(1000);
            //----------------------------------- verify history table and cards --------------------------------------
            Assert.assertTrue(appDep.waitForTheElementState(oBrowser, By.xpath("//input[@id='current_snapshot_company_id']/../label[text()='Preview History']"), "visibility","", waitTimeOut));
            appDep.threadSleep(2000);
            Assert.assertTrue(appDep.waitForTheElementState(oBrowser, By.xpath(String.format(PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_PaymentHistory_Table,"1","Completed")), "visibility", "", waitTimeOut));
            Assert.assertTrue(appDep.waitForTheElementState(oBrowser, By.xpath(String.format(PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_PaymentHistory_Table,"1", appInd.getDateTime("MM/dd/yyyy"))), "visibility", "", waitTimeOut));
            Assert.assertTrue(appDep.waitForTheElementState(oBrowser, By.xpath(String.format(PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_PaymentHistory_Table,"1", appInd.dateAddAndReturn("MM/dd/yyyy",2))), "visibility", "", waitTimeOut));
            Assert.assertTrue(appDep.waitForTheElementState(oBrowser, By.xpath(String.format(PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_PaymentHistory_Table,"2", "Expired")), "visibility", "", waitTimeOut));
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath("(//div[@id='divPreviewHistoryGridContainer']//table)[2]//tr[2]/td//div"),"text"),"Show Preview");
            appInd.scrollToThePage(oBrowser, "Top");
            String cardSupplierCount = appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_SupplierCount,"text");
            String cardCheckCount = appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_CheckCount,"text");
            String cardSpendVolume = appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_SpendVolume,"text");
            cardSpendVolume = cardSpendVolume.replaceAll("[A-Z a-z () : $]","");
            cardSpendVolume = cardSpendVolume.replaceAll(",","");
            Assert.assertEquals(cardSupplierCount, totalCount, "Both supplier count is not match");
            Assert.assertEquals(cardCheckCount, totalTransection, "Both total transection counts are not match");
            Assert.assertEquals(Float.parseFloat(totalSpent), Float.parseFloat(cardSpendVolume), "Both total spend counts are not matched");
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'navigateToProspectCompanyGoLive()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'navigateToProspectCompanyGoLive()' method. " + e);
            return false;
        }
        return true;
    }

    /********************************************************
     * Method Name      : verifyGoLiveCardsData()
     * Purpose          : user verify GoLive cards and it's related data
     * Author           : Deepak
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean verifyGoLiveCardsData(WebDriver oBrowser){
        try{
            //-------------------------------- generate preview ------------------------------------
            String ribbonXpath = "//ul[@id='prospect_company_tabs']//a[@href='#preview_go_live']";
            appInd.clickObject(oBrowser, By.xpath(ribbonXpath));
            reports.writeResult(oBrowser, "screenshot","click on "+ribbonXpath);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "invisibility","", waitTimeOut);
            appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_WaveDropDownID, 1);
            appDep.threadSleep(1000);
            reports.writeResult(oBrowser, "screenshot","select wave from dropdown");
            appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Generate_PreviewID);
            appDep.threadSleep(1000);
            Assert.assertTrue(appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_PreviewSuccessXpath,"visibility","",waitTimeOut));
            reports.writeResult(oBrowser, "screenshot", "generate preview is click");
            appDep.threadSleep(1000);
            //-------------------------------- Goto Card and verify ------------------------------------
            appInd.scrollToThePage(oBrowser, "Top");
            Assert.assertTrue(appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_CasesCreation, "visibility","",minTimeOut));
            Assert.assertTrue(appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_AutoEnrollment, "visibility","",minTimeOut));
            Assert.assertTrue(appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_LiveCase, "visibility","",minTimeOut));
            Assert.assertTrue(appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_DuplicateSupplierCount, "visibility","",minTimeOut));
            Assert.assertTrue(appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_OutOfProgramSupplier, "visibility","",minTimeOut));
            reports.writeResult(oBrowser, "screenshot", "Verify all cards is available on page");
            //----------------------------- verification of Cases Anticipated by Creation -----------------------------
            appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_CasesCreation);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_Model), "visibility","",minTimeOut);
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "screenshot", "verification of Cases Anticipated by Creation card");
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_Model+"//h4"), "text"), "Number of Cases Anticipated by Creation");
            Assert.assertTrue(verifyGoLiveCardGrid(oBrowser));
            //----------------------------- verification of Anticipated Auto Enrollments -----------------------------
            appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_AutoEnrollment);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_Model), "visibility","",minTimeOut);
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "screenshot", "verification of Anticipated Auto Enrollments card");
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_Model+"//h4"), "text"), "Number of Anticipated Auto Enrollments");
            Assert.assertTrue(verifyGoLiveCardGrid(oBrowser));
            //----------------------------- verification of Live Cases Agents will need to reach out on -----------------------------
            appInd.scrollToElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_LiveCase);
            appDep.threadSleep(1000);
            appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_LiveCase);
            reports.writeResult(oBrowser, "screenshot", "verification of Live Cases Agents will need to reach out on card");
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_Model), "visibility","",minTimeOut);
            appDep.threadSleep(2000);
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_Model+"//h4"), "text"), "Number of Live Cases Agents will need to reach out on");
            Assert.assertTrue(verifyGoLiveCardGrid(oBrowser));
            //----------------------------- verification of Anticipated Out of Program Suppliers -----------------------------
            appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_OutOfProgramSupplier);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_Model), "visibility","",minTimeOut);
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "screenshot", "verification of Anticipated Out of Program Suppliers card");
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_Model+"//h4"), "text"), "Anticipated Out of Program Suppliers");
            Assert.assertTrue(verifyGoLiveCardGrid(oBrowser));
            //----------------------------- verification of Duplicate Suppliers from Data -----------------------------
            appInd.scrollToElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_DuplicateSupplierCount);
            appDep.threadSleep(1000);
            appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_DuplicateSupplierCount);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_Model), "visibility","",minTimeOut);
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "screenshot", "verification of Duplicate Suppliers from Data card");
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_Model+"//h4"), "text"), "Duplicate Suppliers from Data");
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_Model_DuplicateSupplier_GridHeader,"1")), "text"),"Supplier name");
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_Model_DuplicateSupplier_GridHeader,"2")), "text"),"Match Criteria");
            Assert.assertTrue(appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_Model_Grid_DownloadBtn, "visibility", "", minTimeOut));
            appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_Model_Grid_DownloadBtn);
            appDep.threadSleep(1500);
            reports.writeResult(oBrowser, "screenshot", "Export functionality is working");
            if(appInd.isAlertPresent(oBrowser))
                oBrowser.switchTo().alert().accept();
            appDep.threadSleep(3000);
            Assert.assertTrue(appDep.validateDownloadedFileAndDelete(oBrowser, "Export-" + appInd.getDateTime("yyyy-MM-dd"), ".xlsx", "", "Yes"));
            appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_Model+"//button"));
            appDep.threadSleep(2000);
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'navigateToProspectCompanyGoLive()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'navigateToProspectCompanyGoLive()' method. " + e);
            return false;
        }
    }

    /********************************************************
     * Method Name      : navigateToGeneralModuleRecord()
     * Purpose          : User will filter the record based on column Name and collect the row record
     * Author           : Deepak
     * Parameters       : oBrowser, generalModuleName, tableID, columnName, filterRecord
     * ReturnType       : boolean
     ********************************************************/
    public List<String> navigateToGeneralModuleRecord(WebDriver oBrowser, String generalModuleName, String tableID, String columnName, String filterRecord){
        Assert.assertTrue(appDep.selectGeneralModules(oBrowser, generalModuleName));
        Assert.assertTrue(navigateToRecord(oBrowser, tableID, columnName, filterRecord));
        return payCRM_ManagedPaymentsUIBaseStep.getVoidRequestRandomRecord(oBrowser, tableID);
    }



    /********************************************************
     * Method Name      : verifyGoLiveCardGrid()
     * Purpose          : Supportive private method for verifying GoLive Cards Model.
     * Author           : Deepak
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    private boolean verifyGoLiveCardGrid(WebDriver oBrowser){
        Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_Model_Grid_Headers,"1")), "text"),"Supplier Name");
        Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_Model_Grid_Headers,"2")), "text"),"Vendor ID");
        Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_Model_Grid_Headers,"3")), "text"),"Tax ID");
        Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_Model_Grid_Headers,"4")), "text"),"Supplier Type");
        Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_Model_Grid_Headers,"5")), "text"),"Transaction Count");
        Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_Model_Grid_Headers,"6")), "text"),"Total Spend");
        Assert.assertTrue(appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_Model_Grid_DownloadBtn, "visibility", "", minTimeOut));
        appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_Model_Grid_DownloadBtn);
        appDep.threadSleep(1500);
        reports.writeResult(oBrowser, "screenshot", "Verify export functionality is working");
        if(appInd.isAlertPresent(oBrowser))
            oBrowser.switchTo().alert().accept();
        appDep.threadSleep(3000);
        Assert.assertTrue(appDep.validateDownloadedFileAndDelete(oBrowser, "Export-" + appInd.getDateTime("yyyy-MM-dd"), ".xlsx", "", "Yes"));
        appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Companies_Prospect_GoLive_Card_Model+"//button"));
        appDep.threadSleep(2000);
        return true;
    }



    public boolean verifyPAFSetting(WebDriver oBrowser, DataTable dataTable){
        Map<String, String> validationMap = dataTable.asMap(String.class, String.class);
        try{
            int size = appInd.getWebElements(oBrowser, By.xpath("//div[@id='DataTables_Table_0_wrapper']//tbody/tr")).size();
            for(int i=1;i<=size;i++){
                String validationType = appInd.getTextOnElement(oBrowser, By.xpath("//div[@id='DataTables_Table_0_wrapper']//tbody/tr["+i+"]/td[1]"), "text");
                String status = appInd.getTextOnElement(oBrowser, By.xpath("//div[@id='DataTables_Table_0_wrapper']//tbody/tr["+i+"]/td[6]/span"), "text");
                if(validationMap.get(validationType).equalsIgnoreCase(status)==false){
                    return false;
                }
            }
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifyPAFSetting()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifyPAFSetting()' method. " + e);
            return false;
        }
        return true;
    }

    /********************************************************
     * Method Name      : enterDetailsForTestAccountSetupForm()
     * Purpose          : user enters details for General Information section from Modules-->General-->prospect Companies
     * Author           : Gudi
     * Parameters       : oBrowser, accountSection, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean enterDetailsForTestAccountSetupForm(WebDriver oBrowser, String accountSection, DataTable dataTable){
        try{
            switch(accountSection.toLowerCase()){
                case "general":
                    Assert.assertTrue(appDep.createGeneralInformation(oBrowser, dataTable));
                    break;
                case "contact":
                    Assert.assertTrue(appDep.createContactInformation(oBrowser, dataTable));
                    break;
                case "current payment landscape":
                    Assert.assertTrue(appDep.createCurrentPaymentLandscape(oBrowser, dataTable));
                    break;
                case "additional details":
                    Assert.assertTrue(appDep.createAdditionalDetails(oBrowser, dataTable));
                    break;
                default:
                    reports.writeResult(oBrowser, "Fail", "Invalid status '"+accountSection+"' for the account information");
            }

            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'enterDetailsForTestAccountSetupForm()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'enterDetailsForTestAccountSetupForm()' method. " + e);
            return false;
        }
    }

    /********************************************************
     * Method Name      : updateProgramCostsFromInternalSetup()
     * Purpose          : user updates Program Costs from Modules-->General-->prospect Companies-->Program Costs
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean updateProgramCostsFromInternalSetup(WebDriver oBrowser, DataTable dataTable){
        List<Map<String, String>> programCostData = null;
        Map<String, String> programCosts = null;
        List<Map<String, String>> dataToVerify = null;
        Map<String, String> data = null;
        String arrTotalCosts[];
        String arrPriceReduceDetails[];
        String arrPriceReduce[];
        String arrWaiveOff[];
        String strPropValue = null;
        try{
            programCostData = dataTable.asMaps(String.class, String.class);
            programCosts = new HashMap<String, String>();
            for(int i=0; i<programCostData.size(); i++){
                programCosts.put("YearRange", programCostData.get(i).get("YearRange"));
                programCosts.put("TotalCosts", programCostData.get(i).get("TotalCosts"));
                programCosts.put("PriceReduce", programCostData.get(i).get("PriceReduce"));
                programCosts.put("WaiveOff", programCostData.get(i).get("WaiveOff"));

                appDep.threadSleep(5000);
                appDep.navigateToInternalSetupPage(oBrowser, "AccountSetUp");
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ProgramCosts_Link));
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_YearRange_List, "Clickable", "", minTimeOut);
                Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_YearRange_List, programCosts.get("YearRange")));
                appDep.threadSleep(2000);
                reports.writeResult(oBrowser, "Screenshot", "Before modifying the Total Costs price & Reduce amount");

                dataToVerify = new ArrayList<Map<String, String>>();
                arrTotalCosts = programCosts.get("TotalCosts").split("#");
                arrPriceReduceDetails = programCosts.get("PriceReduce").split("\\$");
                for(int j=0; j<arrTotalCosts.length; j++){
                    data = new HashMap<String, String>();
                    data.put("TotalCostName"+(j+1), arrTotalCosts[j]);
                    arrPriceReduce = arrPriceReduceDetails[j].split(",");
                    for(int k=0; k<arrPriceReduce.length; k++){
                        data.put("Price"+(k+1), (arrPriceReduce[k].split("#"))[0]);
                        data.put("Reduce"+(k+1), (arrPriceReduce[k].split("#"))[1]);
                        Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_ProgramCosts_Grid + "//td/span[contains(text(),'"+arrTotalCosts[j]+"')]/parent::td/following-sibling::td/input[@*='Price'])["+(k+1)+"]"), data.get("Price"+(k+1))));
                        Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_ProgramCosts_Grid + "//td/span[contains(text(),'"+arrTotalCosts[j]+"')]/parent::td/following-sibling::td/input[@*='Reduced'])["+(k+1)+"]"), data.get("Reduce"+(k+1))));

                    }
                    dataToVerify.add(j, data);
                    data = null;
                }

                reports.writeResult(oBrowser, "Screenshot", "After modifying the Total Costs price & Reduce amount");

                arrWaiveOff = programCosts.get("WaiveOff")  .split("#");
                strPropValue = oBrowser.findElement(By.xpath(PayCRM_Modules_GeneralUIPage.obj_ProgramCosts_Grid + "//td/span[contains(text(),'"+arrWaiveOff[0]+"')]/parent::td/following-sibling::td[1]/div[contains(@class, 'danger')]")).getAttribute("style");
                if(strPropValue.equalsIgnoreCase("display: none;") && arrWaiveOff[1].equalsIgnoreCase("On")){
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ProgramCosts_Grid + "//td/span[contains(text(),'"+arrWaiveOff[0]+"')]/parent::td/following-sibling::td[1]/div[contains(@class, 'success')]")));
                    appDep.threadSleep(2000);
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ProgramCosts_Grid + "//td/span[contains(text(),'"+arrWaiveOff[0]+"')]/parent::td/following-sibling::td[1]/div[contains(@class, 'danger')]")));
                }else if(strPropValue.equalsIgnoreCase("") && arrWaiveOff[1].equalsIgnoreCase("Off")){
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ProgramCosts_Grid + "//td/span[contains(text(),'"+arrWaiveOff[0]+"')]/parent::td/following-sibling::td[1]/div[contains(@class, 'danger')]")));
                    appDep.threadSleep(2000);
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ProgramCosts_Grid + "//td/span[contains(text(),'"+arrWaiveOff[0]+"')]/parent::td/following-sibling::td[1]/div[contains(@class, 'success')]")));
                }
                reports.writeResult(oBrowser, "Screenshot", "After making Waive off "+ arrWaiveOff[1] +" for the Total Cost '"+arrWaiveOff[0]);
                appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ProgramCosts_Grid));
                appDep.threadSleep(2000);
                appInd.scrollToThePage(oBrowser, "Top");
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ProgramCosts_Update_Btn));
                appDep.threadSleep(5000);
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CompanyEditConfirmationMessage_Label, "Text").contains("Program costs has been successfully updated."));
                oBrowser.navigate().refresh();
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", waitTimeOut);
                appDep.threadSleep(2000);
                appInd.scrollToThePage(oBrowser, "Top");
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SolutionDesigns_Link));
                appDep.threadSleep(4000);
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_Link));
                appDep.threadSleep(4000);
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_Itemized_Toggle));
                appDep.threadSleep(2000);
                reports.writeResult(oBrowser, "Screenshot", "Validating the Program Cost changes under 'Solution Design->Results With Corcentic page");
                arrTotalCosts = programCosts.get("TotalCosts").split("#");
                arrPriceReduceDetails = programCosts.get("PriceReduce").split("\\$");
                for(int j=0; j<arrTotalCosts.length; j++) {
                    arrPriceReduce = arrPriceReduceDetails[j].split(",");
                    for (int k = 0; k < arrPriceReduce.length; k++) {
                        Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ItemizedProgramizedCosts_Grid +"//td/span[contains(text(), '"+arrTotalCosts[j]+"')]/parent::td/following-sibling::td["+(k+1)+"]/s"), "Text").replace(",", "").equals("$"+dataToVerify.get(j).get("Price"+(k+1))+".00"));
                        Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ItemizedProgramizedCosts_Grid +"//td/span[contains(text(), '"+arrTotalCosts[j]+"')]/parent::td/following-sibling::td["+(k+1)+"]/span"), "Text").replace(",", "").equals("$"+dataToVerify.get(j).get("Reduce"+(k+1))+".00"));
                    }
                }

                if(arrWaiveOff[1].equalsIgnoreCase("On")){
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ItemizedProgramizedCosts_Grid +"//td/span[contains(text(), '"+arrWaiveOff[0]+"')]/parent::td/following-sibling::td[1]/span[text()='Waived']")));
                }else{
                    Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ItemizedProgramizedCosts_Grid +"//td/span[contains(text(), '"+arrWaiveOff[0]+"')]/parent::td/following-sibling::td[1]/span[text()='Waived']")));
                }
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SolutionDesign_ResultsWithCorcentric_Post_Corcentric_optimization)));
            }
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'updateProgramCostsFromInternalSetup()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'updateProgramCostsFromInternalSetup()' method. " + e);
            return false;
        }finally {
            programCostData = null; programCosts = null; dataToVerify = null; data = null; arrTotalCosts = null; arrPriceReduceDetails = null; arrPriceReduce = null; arrWaiveOff = null; strPropValue = null;
        }
    }




    /********************************************************
     * Method Name      : navigateAndSelectGeneralSupplier
     * Purpose          : user navigates to General-->Suppliers and select the Wave using query
     * Author           : Gudi
     * Parameters       : oBrowser, queryKey
     * ReturnType       : JSONArray
     ********************************************************/
    public JSONArray navigateAndSelectGeneralSupplier(WebDriver oBrowser, String queryKey){
        String strURL = null;
        String supplierID = null;
        JSONArray caseDetails = null;
        try{
            caseDetails = apiDataProvider.getAPIDataProviderResponse(queryKey, new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) caseDetails.get(0)).size(),1);
            supplierID = ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(Integer.parseInt(randomNum)-1)).get("cases_id").toString();

            strURL = appInd.getPropertyValueByKeyName("qaURL")+"/suppliers/" + supplierID;
            oBrowser.navigate().to(strURL);
            reports.writeResult(oBrowser, "Screenshot", "The constructed URL: " + strURL);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ViewActivities_Btn, "Clickable", "", waitTimeOut);
            return caseDetails;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'navigateAndSelectGeneralSupplier()' method. " + e);
            return null;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'navigateAndSelectGeneralSupplier()' method. " + e);
            return null;
        }finally {caseDetails = null; strURL = null; supplierID = null;}
    }




    /********************************************************
     * Method Name      : navigateAndSelectGeneralCompanies
     * Purpose          : user navigates to General-->Companies and select the Client company
     * Author           : Gudi
     * Parameters       : oBrowser, companyType, queryKey
     * ReturnType       : JSONArray
     ********************************************************/
    public JSONArray navigateAndSelectGeneralCompanies(WebDriver oBrowser, String companyType, String queryKey){
        String strURL = null;
        String companyID = null;
        JSONArray caseDetails = null;
        try{
            caseDetails = apiDataProvider.getAPIDataProviderResponse(queryKey, new Object[] {});
            companyID = ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("cases_id").toString();

            switch(companyType.toLowerCase()){
                case "client":
                    strURL = appInd.getPropertyValueByKeyName("qaURL")+"/companies/" + companyID;
                    break;
                case "prospect":
                    strURL = appInd.getPropertyValueByKeyName("qaURL")+"/prospect_companies/" + companyID;
                    break;
                default:
                    reports.writeResult(oBrowser, "Fail", "Invalid company type '"+companyType+"' was specified.");
                    return null;
            }
            oBrowser.navigate().to(strURL);
            reports.writeResult(oBrowser, "Screenshot", "The constructed URL: " + strURL);

            if(companyType.equalsIgnoreCase("Client")) appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ViewActivities_Btn, "Clickable", "", waitTimeOut);
            else appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", waitTimeOut);
            return caseDetails;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'navigateAndSelectGeneralCompanies()' method. " + e);
            return null;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'navigateAndSelectGeneralCompanies()' method. " + e);
            return null;
        }finally {strURL = null; companyID = null; caseDetails = null;}
    }




    /********************************************************
     * Method Name      : updateAndValidateClientCompanyPreferences
     * Purpose          : user updates the preferences section of the client company and validates the same
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean updateAndValidateClientCompanyPreferences(WebDriver oBrowser, DataTable dataTable){
        String companyName = null;
        List<Map<String, String>> preferencesData = null;
        Map<String, String> preferences = null;
        String timeStamp = null;
        try{
            timeStamp = appInd.getDateTime("Shhmmss");
            preferencesData = dataTable.asMaps(String.class, String.class);
            preferences = new HashMap<String, String>();
            preferences.put("RequiresTINValidation", preferencesData.get(0).get("RequiresTINValidation"));
            preferences.put("BypassVirtualCard", preferencesData.get(0).get("BypassVirtualCard"));
            preferences.put("AutoEnrollVirtualCard", preferencesData.get(0).get("AutoEnrollVirtualCard"));
            preferences.put("ProgramName", preferencesData.get(0).get("ProgramName") + timeStamp);
            preferences.put("ProgramEmail", preferencesData.get(0).get("ProgramEmail"));
            preferences.put("ClientMicrosite", preferencesData.get(0).get("ClientMicrosite"));
            preferences.put("EmailTemplate", preferencesData.get(0).get("EmailTemplate"));
            preferences.put("PaymentVoidContactEmail", preferencesData.get(0).get("PaymentVoidContactEmail"));
            preferences.put("DefaultPaymentQuestionEmails", preferencesData.get(0).get("DefaultPaymentQuestionEmails"));

            preferences = updateClientCompanyPreferences(oBrowser, preferences);
            Assert.assertTrue(preferences.get("TestPassed").equalsIgnoreCase("True"), "The updateClientCompanyPreferences() method was failed");

            //Validate that the Preferences values are saved succesful
            appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Users_Link);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_NewUserSetup_Settings_Update_Btn, "Clickable", "", waitTimeOut);
            appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Preferences_Link);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Preferences_Update_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_TINRequired_List, "Dropdown", preferences.get("RequiresTINValidation")));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_BypassValidation_List, "Dropdown", preferences.get("BypassVirtualCard")));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AutoEnroll_List, "Dropdown", preferences.get("AutoEnrollVirtualCard")));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ProgramName_Edit, "Value", preferences.get("ProgramName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ProgramEmail_Edit, "Value", preferences.get("ProgramEmail")));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientMicrosite_Edit, "Value", preferences.get("ClientMicrosite")));
            if(preferences.get("EmailTemplate")!=null)
                Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_EmailTemplate_Edit, "Value", preferences.get("EmailTemplate")));

            if(preferences.get("PaymentVoidContactEmail")!=null)
                Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentVoidContactEmail_Edit, "Value", preferences.get("PaymentVoidContactEmail")));

            if(preferences.get("PaymentVoidContactEmail")!=null)
                Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_DefaultPaymentQuestionEmail_Edit, "Value", preferences.get("DefaultPaymentQuestionEmails")));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'updateAndValidateClientCompanyPreferences()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'updateAndValidateClientCompanyPreferences()' method. " + e);
            return false;
        }finally {companyName = null; preferencesData = null; preferences = null; timeStamp = null;}
    }



    /********************************************************
     * Method Name      : updateClientCompanyPreferences
     * Purpose          : user updates the preferences section of the client company
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : Map<String, String>
     ********************************************************/
    public Map<String, String> updateClientCompanyPreferences(WebDriver oBrowser, Map<String, String> preferencesData){
        try{
            companyName = appDep.selectTheCompanyBasedOnType(oBrowser, "Client");
            appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Preferences_Link);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Preferences_Update_Btn, "Clickable", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Before updating the 'Preferences' options");

            appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_TINRequired_List, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_TINRequired_List, "List", preferencesData.get("RequiresTINValidation")));
            preferencesData.remove("RequiresTINValidation");
            preferencesData.put("RequiresTINValidation", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_TINRequired_List, "Dropdown"));

            appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_BypassValidation_List, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_BypassValidation_List, "List", preferencesData.get("BypassVirtualCard")));
            preferencesData.remove("BypassVirtualCard");
            preferencesData.put("BypassVirtualCard", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_BypassValidation_List, "Dropdown"));

            appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AutoEnroll_List, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AutoEnroll_List, "List", preferencesData.get("AutoEnrollVirtualCard")));
            preferencesData.remove("AutoEnrollVirtualCard");
            preferencesData.put("AutoEnrollVirtualCard", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AutoEnroll_List, "Dropdown"));

            appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ProgramName_Edit, preferencesData.get("ProgramName"));
            appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ProgramEmail_Edit, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AutoEnroll_List, "Text", preferencesData.get("ProgramEmail")));
            preferencesData.remove("ProgramEmail");
            preferencesData.put("ProgramEmail", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ProgramEmail_Edit, "Value"));

            appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientMicrosite_Edit, preferencesData.get("ClientMicrosite"));

            appInd.setOptionalObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_EmailTemplate_Edit, preferencesData.get("EmailTemplate"));
            appInd.setOptionalObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentVoidContactEmail_Edit, preferencesData.get("PaymentVoidContactEmail"));
            appInd.setOptionalObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_DefaultPaymentQuestionEmail_Edit, preferencesData.get("DefaultPaymentQuestionEmails"));
            appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Preferences_Update_Btn);
            appDep.threadSleep(5000);
            reports.writeResult(oBrowser, "Screenshot", "After updating the 'Preferences' options");
            preferencesData.put("TestPassed", "True");
            return preferencesData;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'updateClientCompanyPreferences()' method. " + e);
            preferencesData.put("TestPassed", "False");
            return preferencesData;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'updateClientCompanyPreferences()' method. " + e);
            preferencesData.put("TestPassed", "False");
            return preferencesData;
        }finally{preferencesData = null;}
    }




    /********************************************************
     * Method Name      : validateGoliveUIAndPostResults
     * Purpose          : user perform the Go-Live UI and post result validations
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateGoliveUIAndPostResults(WebDriver oBrowser){
        String companyName = null;
        String supplierCount = null;
        String casesAnticipated = null;
        String checkCount = null;
        String anticipatedAutoEnrollments = null;
        String spendVolume = null;
        String casesAgentsNeedsToReachOut = null;
        String anticipatedOutOfPrgmSuppliers = null;
        String duplicateSuppliers = null;
        boolean blnRes = false;
        String offerNumber = appInd.generateRandomNumbers(1,5,1);
        int records = 0;
        Alert oAlert = null;
        try{
            companyName = appDep.selectTheCompanyBasedOnType(oBrowser, "Prospect");
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AccountSetupForm_Link));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AccountSetup_Img));

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PreviewAndGolive_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);

            //Before Generate PReview
            supplierCount = appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_GoLive_Cards + ")[1]/div/div"), "Text");
            casesAnticipated = appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_GoLive_Cards + ")[2]/div/div"), "Text");
            checkCount = appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_GoLive_Cards + ")[3]/div/div"), "Text");
            anticipatedAutoEnrollments = appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_GoLive_Cards + ")[4]/div/div"), "Text");
            spendVolume = appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_GoLive_Cards + ")[5]/div/div"), "Text");
            casesAgentsNeedsToReachOut = appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_GoLive_Cards + ")[6]/div/div"), "Text");
            anticipatedOutOfPrgmSuppliers = appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_GoLive_Cards + ")[7]/div/div"), "Text");
            duplicateSuppliers = appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_GoLive_Cards + ")[8]/div/div"), "Text");

            //Check Waveis present
            int waveCount = new Select(oBrowser.findElement(PayCRM_Modules_GeneralUIPage.obj_GoLive_Wave_List)).getOptions().size();
            if(waveCount > 1){
                reports.writeResult(oBrowser, "Screenshot", "The Wave was present for the selected prospect company: '" + companyName + "'");
                blnRes = true;
            }else{
                reports.writeResult(oBrowser, "Warning", "The selected prospect company: '" + companyName + "' does not have Wave");
                blnRes = false;
            }

            if(blnRes){
                Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GoLive_Wave_List, 1));
                appDep.threadSleep(2000);
                if(appInd.verifyOptionalElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PreviewWaveInfo_Label+"[@style='display:none;']"))){
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_WaveInformation_Link));
                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_WaveInformation_Link));
                    oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Offers_Link, "Clickable", "", minTimeOut);
                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Offers_Link));
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_WaveType_Add_Link));
                    Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_NewWaveOffer_Dialog, "Text", "New Wave Offer"));
                    Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_OfferNumber_Edit, offerNumber));
                    Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_OfferType_List, "VCA"));
                    Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_CardPlatform_List, "HP"));
                    Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_CardNetwork_List, "Visa"));
                    Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_CardCampaignType_List, "Card First"));
                    Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_VirtualCardDeliveryMethod_List, "Email"));

                    appInd.scrollToElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_Create_Btn);
                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_Create_Btn));
                    appDep.threadSleep(2000);
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_ConfirmationMessage, "Visibility", "", waitTimeOut);
                    Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_ConfirmationMessage, "Text").contains("Enrollment Detail has been successfully created"));
                    oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
                    appDep.threadSleep(2000); //change the tab and reviste "Preview & Go Live" again
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PreviewWaveInfo_Label+"//tr[2]/td[1]"), "Text", "Offer "+offerNumber));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PreviewWaveInfo_Label+"//tr[2]/td[2]"), "Text", "VCA"));
                }

                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PreviewWaveInfo_Label+"//tr[2]")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PreviewWaveInfo_Label+"//tr[1]/th[1]"), "Text", "Offer Number"));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PreviewWaveInfo_Label+"//tr[1]/th[2]"), "Text", "Offer Type"));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PreviewWaveInfo_Label+"//tr[1]/th[3]"), "Text", "VND%"));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PreviewWaveInfo_Label+"//tr[1]/th[4]"), "Text", "Terms"));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PreviewWaveInfo_Label+"//tr[1]/th[5]"), "Text", "Offer Enabled"));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PreviewWaveInfo_Label+"//tr[1]/th[6]"), "Text", "Custom Payment Terms"));
            }

            //Generate preview
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneratePreview_Btn));
//            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneratePreviewInProgress_Message, "Visibility", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneratePreviewInProgress_Message), "Failed to generate the preview for the selected wave");
            for(int i=1; i<=10; i++){
                oBrowser.navigate().refresh();
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", waitTimeOut);
                appDep.threadSleep(2000);
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PreviewAndGolive_Link));
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GoLive_Wave_List, 1));
                if(appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneratePreviewInProgress_Message, "Invisibility", "", waitTimeOut))
                    break;
            }


            //After Generate PReview
            //Verify Generate Preview is completed
            reports.writeResult(oBrowser, "Screenshot", "After Generate preview");
//            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneratePreviewInProgress_Message), "Failed to generate the preview for the selected wave");
            appDep.threadSleep(2000);
            Assert.assertTrue(Integer.parseInt(supplierCount) <= Integer.parseInt(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_GoLive_Cards + ")[1]/div/div"), "Text")));
            Assert.assertTrue(Integer.parseInt(casesAnticipated) <= Integer.parseInt(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_GoLive_Cards + ")[2]/div/div"), "Text")));
            Assert.assertTrue(Integer.parseInt(checkCount) <= Integer.parseInt(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_GoLive_Cards + ")[3]/div/div"), "Text")));
            Assert.assertTrue(Integer.parseInt(anticipatedAutoEnrollments) <= Integer.parseInt(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_GoLive_Cards + ")[4]/div/div"), "Text")));
            Assert.assertTrue(spendVolume.equalsIgnoreCase(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_GoLive_Cards + ")[5]/div/div"), "Text")));
            Assert.assertTrue(Integer.parseInt(casesAgentsNeedsToReachOut) <= Integer.parseInt(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_GoLive_Cards + ")[6]/div/div"), "Text")));
            Assert.assertTrue(Integer.parseInt(anticipatedOutOfPrgmSuppliers) <= Integer.parseInt(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_GoLive_Cards + ")[7]/div/div"), "Text")));
            Assert.assertTrue(Integer.parseInt(duplicateSuppliers) <= Integer.parseInt(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_GoLive_Cards + ")[8]/div/div"), "Text")));


            //Click on Each Card
            //Number of Cases Anticipated by Creation
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_GoLive_Cards + ")[2]/div")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Cards_Grid + "//h4"), "Text", "Number of Cases Anticipated by Creation"));
            records = Integer.parseInt((((appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Cards_Grid + "//div[@class='dx-info']"), "Text").split("\\("))[1]).split(" "))[0].trim());
            Assert.assertTrue(records == Integer.parseInt(casesAnticipated), "Mis-match '("+String.valueOf(records)+" <> "+casesAnticipated+")' in the record count for the 'Number of Cases Anticipated by Creation' card");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Cards_Grid + "//button[@class='close']")));

            //Number of Anticipated Auto Enrollments
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_GoLive_Cards + ")[4]/div")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Cards_Grid + "//h4"), "Text", "Number of Anticipated Auto Enrollments"));
            records = Integer.parseInt((((appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Cards_Grid + "//div[@class='dx-info']"), "Text").split("\\("))[1]).split(" "))[0].trim());
            Assert.assertTrue(records == Integer.parseInt(anticipatedAutoEnrollments), "Mis-match '("+String.valueOf(records)+" <> "+anticipatedAutoEnrollments+")' in the record count for the 'Number of Anticipated Auto Enrollments' card");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Cards_Grid + "//button[@class='close']")));


            //Number of Live Cases Agents will need to reach out on
            // This feature is not in development code hence this assertion is not required anymore, please refer - https://determine.atlassian.net/browse/CPAY-5135
//            appDep.threadSleep(2000);
//            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_GoLive_Cards + ")[6]/div")));
//            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
//            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Cards_Grid + "//h4"), "Text", "Number of Live Cases Agents will need to reach out on"));
//            records = Integer.parseInt((((appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Cards_Grid + "//div[@class='dx-info']"), "Text").split("\\("))[1]).split(" "))[0].trim());
//            Assert.assertTrue(records == Integer.parseInt(casesAgentsNeedsToReachOut), "Mis-match '("+String.valueOf(records)+" <> "+casesAgentsNeedsToReachOut+")' in the record count for the 'Number of Live Cases Agents will need to reach out on' card");
//            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Cards_Grid + "//button[@class='close']")));


            //Anticipated Out of Program Suppliers
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_GoLive_Cards + ")[7]/div")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Cards_Grid + "//h4"), "Text", "Anticipated Out of Program Suppliers"));
            records = Integer.parseInt((((appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Cards_Grid + "//div[@class='dx-info']"), "Text").split("\\("))[1]).split(" "))[0].trim());
            Assert.assertTrue(records == Integer.parseInt(anticipatedOutOfPrgmSuppliers), "Mis-match '("+String.valueOf(records)+" <> "+anticipatedOutOfPrgmSuppliers+")' in the record count for the 'Anticipated Out of Program Suppliers' card");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Cards_Grid + "//button[@class='close']")));


            //Duplicate Suppliers from Data
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_GoLive_Cards + ")[8]/div")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Cards_Grid + "//h4"), "Text", "Duplicate Suppliers from Data"));
            records = Integer.parseInt((((appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Cards_Grid + "//div[@class='dx-info']"), "Text").split("\\("))[1]).split(" "))[0].trim());
            Assert.assertTrue(records == Integer.parseInt(duplicateSuppliers), "Mis-match '("+String.valueOf(records)+" <> "+duplicateSuppliers+")' in the record count for the 'Duplicate Suppliers from Data' card");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Cards_Grid + "//button[@class='close']")));

            //Validate Preview History grid
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PreviewHistory_Grid + "//tr[contains(@class, 'lines dx-column-lines')][1]/td[1]"), "Text", companyName + " - Preview - " + appInd.getDateTime("yyyy-MM-dd")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PreviewHistory_Grid + "//tr[contains(@class, 'lines dx-column-lines')][1]/td[2]"), "Text", appInd.getPropertyValueByKeyName("qaUserName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PreviewHistory_Grid + "//tr[contains(@class, 'lines dx-column-lines')][1]/td[3]"), "Text", appInd.getDateTime("MM/dd/yyyy")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PreviewHistory_Grid + "//tr[contains(@class, 'lines dx-column-lines')][1]/td[5]"), "Text", "Completed"));


            //Click on 'Completed' status record which is ready for Go-Live
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_GoLive_Btn + "[@class='btn btn-success disable-submit']")), "The 'Go-Live' button should be DISABLED before selecting the 'Completed' status record");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PreviewHistory_Grid + "//tr[contains(@class, 'lines dx-column-lines')][1]/td[1]")));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_GoLive_Btn + "[@class='btn btn-success']")), "The 'Go-Live' button should be ENABLED before selecting the 'Completed' status record");
            reports.writeResult(oBrowser, "Screenshot", "Before Go-Live");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_GoLive_Btn)));
            appDep.threadSleep(2000);
            oAlert = oBrowser.switchTo().alert();
            Assert.assertTrue(oAlert.getText().equalsIgnoreCase("Go-Live with preview "+companyName+" - Preview - " + appInd.getDateTime("yyyy-MM-dd")));
            oAlert.accept();
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ViewActivities_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ClientSupplier_Case_Grid + "//dt[text()='Name']/following-sibling::dd[1]"), "Text").contains(companyName));
            reports.writeResult(oBrowser, "Screenshot", "After Go-Live");


            //Verify that the Propsect Compnay has changed to Client company
            Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "Program Management"));
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_ProgramManagement_Grid + "//input)[1]"), companyName));
            appDep.threadSleep(2000);
            Assert.assertTrue(!appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ProgramManagement_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[5]"), "Text").equalsIgnoreCase("Prospect"));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateGoliveUIAndPostResults()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateGoliveUIAndPostResults()' method. " + e);
            return false;
        }finally {companyName = null; supplierCount = null; casesAnticipated = null; checkCount = null; anticipatedAutoEnrollments = null; spendVolume = null; casesAgentsNeedsToReachOut = null; anticipatedOutOfPrgmSuppliers = null; duplicateSuppliers = null; offerNumber = null; oAlert = null;}
    }


    /********************************************************
     * Method Name      : createProspectCompanyAndValidateMergedSuppliersTab
     * Purpose          : user create the new Prospect company and validates the "Merged Suppliers" Tab under "Solution Design"
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean createProspectCompanyAndValidateMergedSuppliersTab(WebDriver oBrowser, DataTable dataTable){
        Map<String, String> companyData = null;
        String arrColumns[];
        String existingPropsectCompany = null;
        try {
            if(System.getProperty("environment").equalsIgnoreCase("TEST")){
                existingPropsectCompany = "TEST_ProspectCompany_DoNotTouch_Automation";
            }else{
                existingPropsectCompany = "STAGING_ProspectCompany_DoNotTouch_Automation";
            }

            Assert.assertTrue(appDep.selectGeneralModules(oBrowser, "Companies"));
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_Companies_Grid + "//input)[1]"), existingPropsectCompany));
            appDep.threadSleep(2000);
            if(!appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Companies_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[7]"), "Text").equalsIgnoreCase("Prospect")){
                reports.writeResult(oBrowser, "Warning", "The existing prospect company '"+existingPropsectCompany+"' was not found");
            }else{
                reports.writeResult(oBrowser, "Pass", "The existing prospect company '"+existingPropsectCompany+"' was found");
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Companies_Grid +"//tr[contains(@class, 'lines dx-column-lines')]/td[1]")));
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", waitTimeOut);
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SolutionDesigns_Link));
                appDep.threadSleep(5000);
                reports.writeResult(oBrowser, "Pass", "The 'Merged Suppliers' tab was not found for the existing prospect companies");
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MergedSuppliers_Link));
                appDep.threadSleep(2000);
            }


            companyData = createNewCompany(oBrowser, dataTable);
            Assert.assertTrue(companyData.get("TestPassed").equalsIgnoreCase("True"), "The createNewCompany() method was failed");
            reports.writeResult(oBrowser, "Pass", "The new Prospect company was created successful");

            //Validate "Merge Supplier" tab under Solution Design tab
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SolutionDesigns_Link));
            appDep.threadSleep(5000);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MergedSuppliers_Link));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MergedSuppliers_Link));
            appDep.threadSleep(2000);

            arrColumns = "Vendor ID#Supplier#Tax ID#Transaction Count#Total Spend".split("#");
            for(int i=0; i<arrColumns.length; i++){
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_MergedSuppliers_Grid + "//div[contains(@class, 'text-content')])["+(i+1)+"]"), "Text", arrColumns[i]));
            }
            reports.writeResult(oBrowser, "Pass", "The 'Merged Suppliers' tab was found successful");

            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createProspectCompanyAndValidateMergedSuppliersTab()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createProspectCompanyAndValidateMergedSuppliersTab()' method. " + e);
            return false;
        }finally {companyData = null; arrColumns = null; existingPropsectCompany = null;}
    }




    /********************************************************
     * Method Name      : validateShowORHideMergedSupplierTabViaInternalSetup
     * Purpose          : user validates Show OR hide "Merged Suppliers" Tab under "Solution Design" from Internal setup tab
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateShowORHideMergedSupplierTabViaInternalSetup(WebDriver oBrowser){
        try{
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SecretSauce_Asterisk_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_InternalSetup_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_InternalSetup_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_TabVisibility_Link, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_TabVisibility_Link));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SolutionDesingTabs), "Text", "Solution Design Tabs", minTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MergedSuppliers_ChkBox));
            Assert.assertTrue(!oBrowser.findElement(PayCRM_Modules_GeneralUIPage.obj_MergedSuppliers_ChkBox).isSelected(), "Failed to uncheck the 'Merged Suppliers' Checkbox under internal setup");
            reports.writeResult(oBrowser, "Pass", "The 'Merged Suppliers' checkbox was unchecked successful");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Update_TabVisibility_Btn));
            appDep.threadSleep(4000);
            oBrowser.navigate().refresh();
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", waitTimeOut);

            //The "Merged Suppliers" Tab should not be displayed
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SolutionDesigns_Link));
            appDep.threadSleep(5000);
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MergedSuppliers_Link));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Pass", "The 'Merged Suppliers' tab was hidden successful");


            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SecretSauce_Asterisk_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_InternalSetup_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_InternalSetup_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_TabVisibility_Link, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_TabVisibility_Link));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SolutionDesingTabs), "Text", "Solution Design Tabs", minTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MergedSuppliers_ChkBox));
            Assert.assertTrue(oBrowser.findElement(PayCRM_Modules_GeneralUIPage.obj_MergedSuppliers_ChkBox).isSelected(), "Failed to Check the 'Merged Suppliers' Checkbox under internal setup");
            reports.writeResult(oBrowser, "Pass", "The 'Merged Suppliers' checkbox was checked successful");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Update_TabVisibility_Btn));
            appDep.threadSleep(4000);
            oBrowser.navigate().refresh();
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", waitTimeOut);


            //The "Merged Suppliers" Tab should be displayed
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SolutionDesigns_Link));
            appDep.threadSleep(5000);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MergedSuppliers_Link));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Pass", "The 'Merged Suppliers' tab was visible successful");
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateShowORHideMergedSupplierTabViaInternalSetup()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateShowORHideMergedSupplierTabViaInternalSetup()' method. " + e);
            return false;
        }
    }




    /********************************************************
     * Method Name      : createProspectCompanyForMergeSuppliers
     * Purpose          : user creates new Prospect company to validate the Merged Suppliers functionality
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean createProspectCompanyForMergeSuppliers(WebDriver oBrowser, DataTable dataTable){
        Map<String, String> companyData = null;
        try{
            companyData = createNewCompany(oBrowser, dataTable);
            Assert.assertTrue(companyData.get("TestPassed").equalsIgnoreCase("True"), "The createNewCompany() method was failed");
            reports.writeResult(oBrowser, "Pass", "The new Prospect company was created successful");

            appDep.navigateToProspectCompanyAttachmentScreen(oBrowser, "Account SetUp");
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createProspectCompanyForMergeSuppliers()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createProspectCompanyForMergeSuppliers()' method. " + e);
            return false;
        }finally{companyData = null;}
    }




    /********************************************************
     * Method Name      : userPerformMunchForTheImportedSuppliers
     * Purpose          : user perform Munch for the imported suppliers
     * Author           : Gudi
     * Parameters       : oBrowser, supplierData
     * ReturnType       : boolean
     ********************************************************/
    public boolean userPerformMunchForTheImportedSuppliers(WebDriver oBrowser, Map<String, String> supplierData){
        try{
            //perform munch for the imported suppliers after the 'File Import Status' changes to "Imported"
            Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierFileImport_Grid +"//tr[contains(@class, 'row-lines dx-column-lines')]//a[@title='Munch']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            oBrowser.navigate().refresh();
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", waitTimeOut);
            appDep.navigateToProspectCompanyAttachmentScreen(oBrowser, "Account SetUp");
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_SupplierFileImport_Grid+"//input)[2]"), supplierData.get("Name")));
            appDep.threadSleep(2000);

//            while(true){
//                supplierFileImportStatus = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierFileImport_Grid +"//tr[contains(@class, 'row-lines dx-column-lines')][1]/td[5]"), "Text");
//                if(!supplierFileImportStatus.equalsIgnoreCase("Completed")){
//                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierFileImport_Grid +"//*[@class='dx-icon dx-icon-refresh']")));
//                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
//                }else{
//                    break;
//                }
//                count++;
//                if(count==10){
//                    reports.writeResult(oBrowser, "Fail", "Failed to get the suppler imported file status to COMPLETED after the munch");
//                    Assert.fail("Failed to get the suppler imported file status to COMPLETED after the munch");
//                    break;
//                }
//            }
//            reports.writeResult(oBrowser, "Screenshot", "After Munch, the supplier status got changed to 'Completed'");
//            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierFileImport_Grid +"//tr[contains(@class, 'row-lines dx-column-lines')][1]/td[5]"), "Text", "Completed"));

            Assert.assertTrue(appDep.waitForMunchStatusToCompleted(oBrowser, "Suppler", 15));

            oBrowser.navigate().refresh();
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", waitTimeOut);
            appDep.navigateToProspectCompanyAttachmentScreen(oBrowser, "Account SetUp");
            oBrowser.navigate().refresh();
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", waitTimeOut);
            appDep.navigateToProspectCompanyAttachmentScreen(oBrowser, "Account SetUp");
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'userPerformMunchForTheImportedSuppliers()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'userPerformMunchForTheImportedSuppliers()' method. " + e);
            return false;
        }
    }




    /********************************************************
     * Method Name      : userImportsSuppliersAndValidateAutomaticMergeAndUndoMergeSupplierFunctionality
     * Purpose          : user imports suppliers and validates automatic merge details & Undo any merged supplier functionality
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean userImportsSuppliersAndValidateAutomaticMergeAndUndoMergeSupplierFunctionality(WebDriver oBrowser, DataTable dataTable){
        Map<String, String> supplier = null;
        try{
            supplier = importSupplierFile(oBrowser, dataTable);
            Assert.assertTrue(supplier.get("TestPassed").equalsIgnoreCase("True"), "The method 'importSupplierFile()' was failed");
            Assert.assertTrue(userPerformMunchForTheImportedSuppliers(oBrowser, supplier));

            //Navigate to "Supplier" tab and validate the supplier details
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SuppliersTab_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Navigate to 'Suppliers' tab and validate the suppliers records");
            if(!appInd.verifyOptionalElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ProspectCompanySupplier_Grid + "//table[contains(@class, 'table-fixed')]//td[2]//span[@class='dx-sort dx-sort-up']"))){
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ProspectCompanySupplier_Grid + "//table[contains(@class, 'table-fixed')]//span[@class='dx-sort dx-sort-none']/../..")));
                appDep.threadSleep(2000);
            }

            Map<Integer, String> data = new HashMap<Integer, String>();
            for(int r=1; r<=15; r++){
                String rowData = "";
                for(int c=2; c<=7; c++){
                    rowData+=appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ProspectCompanySupplier_Grid + "//tr["+r+"][contains(@class, 'dx-row-lines')]/td["+c+"]"), "Text").trim()+"#";
                }
                data.put(r, rowData.substring(0, rowData.length()-1));
            }

            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(1), "BIDEASE INC#85865-APALONEU DUB-MSEU-1Z5, 85865-NYNEW01-MO-MS-161##14#$94,619#General"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(2), "BIDEASE INC#85865-APALONEU DUB-MSEU-1Z5, 85865-NYNEW01-MO-MS-161##0#$0#Duplicate"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(3), "BOB HARPER ENTERPRISES#61660-BEVERLY HILLS-MS-161#6813997483#2#$59,503#General"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(4), "BRANDFUSE INC#93503-CAP-CALAR01-MS-161#6813997483#0#$0#Duplicate"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(5), "BRIGHTCOVE INC#104755-MAWOB01-DB-MS-161##2#$11,266#Network Name Match"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(6), "BRIGHTCOVE INC#104755-MAWOB01-DB-MS-161#6813995579#1#$976#Network Name Match"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(7), "BRIKENA SELA QERKINI#92834-ITRANSLATE-I-MSEU-1Z5##1#$75#International"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(8), "CLICKBOOTH HOLDINGS INC#88331-APALONEU DUB-MSEU-1Z5, 88331-CODEN01-MO-MS-161#6813982952#41#$980,997#General"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(9), "CLICKBOOTH HOLDINGS INC#88331-APALONEU DUB-MSEU-1Z5, 88331-CODEN01-MO-MS-161#6813982952#0#$0#Duplicate"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(10), "CLOUDFLARE#69130-APALONEU DUB-MSEU-1Z5##10#$70,000#General"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(11), "DELOITTE#72793-APALONEU DUB-MSEU-1Z5, 72793-MS DUBLIN-MSEU-143##4#$40,226#International"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(12), "DELOITTE#72793-APALONEU DUB-MSEU-1Z5, 72793-MS DUBLIN-MSEU-143##0#$0#Duplicate"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(13), "DELOITTE#72793-APALONEU DUB-MSEU-1Z5, 72793-MS DUBLIN-MSEU-143##0#$0#Duplicate"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(14), "KPMG TAX CORPORATION#72189-APALONEU DUB-MSEU-1Z5, 72189-ITRANSLATE-I-ITRN-1Z5#6813995581#1#$230,000#Exempt"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(15), "KPMG TAX CORPORATION#72189-APALONEU DUB-MSEU-1Z5, 72189-ITRANSLATE-I-ITRN-1Z5#6813995580#1#$30,000#Exempt"));

            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(//div[contains(@class, '-summary-item')])[1]"), "Text", "Count: 15"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(//div[contains(@class, '-summary-item')])[2]"), "Text", "(T): 77"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(//div[contains(@class, '-summary-item')])[3]"), "Text", "(T): $1,517,661.73"));



            //Navigate to "Merged Suppliers" tab and validate the duplicate suppliers
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MergedSuppliers_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", minTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "'Merged Suppliers' page Opened successful");
            data = new HashMap<Integer, String>();
            for(int r=1; r<=4; r++){
                String rowData = "";
                for(int c=1; c<=5; c++){
                    rowData+=appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_MergedSuppliers_Grid + "//tr["+r+"][contains(@class, 'lines dx-column-lines')]/td["+c+"]"), "Text").trim()+"#";
                }
                data.put(r, rowData.substring(0, rowData.length()-1));
            }

            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(1), "88331-APALONEU DUB-MSEU-1Z5, 88331-CODEN01-MO-MS-161#CLICKBOOTH HOLDINGS INC#6813982952#41#$980,997"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(2), "85865-APALONEU DUB-MSEU-1Z5, 85865-NYNEW01-MO-MS-161#BIDEASE INC##14#$94,619"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(3), "61660-BEVERLY HILLS-MS-161#BOB HARPER ENTERPRISES#6813997483#2#$59,503"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(4), "72793-APALONEU DUB-MSEU-1Z5, 72793-MS DUBLIN-MSEU-143#DELOITTE##4#$40,226"));

            //Click on any duplicate record and validate the entries in it
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_MergedSuppliers_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[text()='DELOITTE']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_UndoMerge_Btn, "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_MergedSuppliers_Dialog + "//div[@class='text-header-miller']"), "Text", "Merged Suppliers"));
            reports.writeResult(oBrowser, "Screenshot", "'Merged Suppliers' dialog Opened successful");

            data = new HashMap<Integer, String>();
            for(int r=1; r<=2; r++){
                String rowData = "";
                for(int c=2; c<=6; c++){
                    rowData+=appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_MergedSuppliers_Dialog_Grid + "//tr["+(r)+"]/td["+c+"]"), "Text").trim()+"#";
                }
                data.put(r, rowData.substring(0, rowData.length()-1));
            }

            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(1), "72793-APALONEU DUB-MSEU-1Z5, 72793-MS DUBLIN-MSEU-143#DELOITTE##2#$30,750.00"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(2), "72793-APALONEU DUB-MSEU-1Z5, 72793-MS DUBLIN-MSEU-143#DELOITTE##1#$2,342.00"));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_MergedSuppliers_Dialog + "//button[@*='Close']")));
            appDep.threadSleep(1000);

            //Undo merge any specific merged suppliers
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_MergedSuppliers_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[text()='DELOITTE']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_UndoMerge_Btn, "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_MergedSuppliers_Dialog + "//div[@class='text-header-miller']"), "Text", "Merged Suppliers"));

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_MergedSuppliers_Dialog_Grid + "//tr[1]/td[1]/input")));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_MergedSuppliers_Dialog_Grid + "//tr[2]/td[1]/input")));
            reports.writeResult(oBrowser, "Screenshot", "perform undo merge from the 'Merged Suppliers' dialog");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_UndoMerge_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ConfirmationMessage_Label, "Text", "Undo Merge Supplier action successfully completed."));
            reports.writeResult(oBrowser, "Screenshot", "Undo merge confirmation message was displayed");

            //After performing the Undo merge suppliers
            data = new HashMap<Integer, String>();
            for(int r=1; r<=3; r++){
                String rowData = "";
                for(int c=1; c<=5; c++){
                    rowData+=appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_MergedSuppliers_Grid + "//tr["+r+"][contains(@class, 'lines dx-column-lines')]/td["+c+"]"), "Text").trim()+"#";
                }
                data.put(r, rowData.substring(0, rowData.length()-1));
            }

            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(1), "88331-APALONEU DUB-MSEU-1Z5, 88331-CODEN01-MO-MS-161#CLICKBOOTH HOLDINGS INC#6813982952#41#$980,997"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(2), "85865-APALONEU DUB-MSEU-1Z5, 85865-NYNEW01-MO-MS-161#BIDEASE INC##14#$94,619"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(3), "61660-BEVERLY HILLS-MS-161#BOB HARPER ENTERPRISES#6813997483#2#$59,503"));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_MergedSuppliers_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[text()='DELOITTE']")));

            //Verify Merged Supplier tab and verify Duplicated supplier under "Suppliers" Tab
            oBrowser.navigate().refresh();
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", waitTimeOut);
            appDep.navigateToProspectCompanyAttachmentScreen(oBrowser, "Account SetUp");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SuppliersTab_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Suppliers page after performing the Undo Merge of the selected entries");
            data = new HashMap<Integer, String>();
            for(int r=1; r<=15; r++){
                String rowData = "";
                for(int c=2; c<=7; c++){
                    rowData+=appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ProspectCompanySupplier_Grid + "//tr["+r+"][contains(@class, 'dx-row-lines')]/td["+c+"]"), "Text").trim()+"#";
                }
                data.put(r, rowData.substring(0, rowData.length()-1));
            }

            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(1), "BIDEASE INC#85865-APALONEU DUB-MSEU-1Z5, 85865-NYNEW01-MO-MS-161##14#$94,619#General"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(2), "BIDEASE INC#85865-APALONEU DUB-MSEU-1Z5, 85865-NYNEW01-MO-MS-161##0#$0#Duplicate"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(3), "BOB HARPER ENTERPRISES#61660-BEVERLY HILLS-MS-161#6813997483#2#$59,503#General"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(4), "BRANDFUSE INC#93503-CAP-CALAR01-MS-161#6813997483#0#$0#Duplicate"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(5), "BRIGHTCOVE INC#104755-MAWOB01-DB-MS-161##2#$11,266#Network Name Match"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(6), "BRIGHTCOVE INC#104755-MAWOB01-DB-MS-161#6813995579#1#$976#Network Name Match"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(7), "BRIKENA SELA QERKINI#92834-ITRANSLATE-I-MSEU-1Z5##1#$75#International"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(8), "CLICKBOOTH HOLDINGS INC#88331-APALONEU DUB-MSEU-1Z5, 88331-CODEN01-MO-MS-161#6813982952#41#$980,997#General"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(9), "CLICKBOOTH HOLDINGS INC#88331-APALONEU DUB-MSEU-1Z5, 88331-CODEN01-MO-MS-161#6813982952#0#$0#Duplicate"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(10), "CLOUDFLARE#69130-APALONEU DUB-MSEU-1Z5##10#$70,000#General"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(11), "DELOITTE#72793-APALONEU DUB-MSEU-1Z5, 72793-MS DUBLIN-MSEU-143##2#$30,750#International"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(12), "DELOITTE#72793-APALONEU DUB-MSEU-1Z5, 72793-MS DUBLIN-MSEU-143##1#$7,134#International"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(13), "DELOITTE#72793-APALONEU DUB-MSEU-1Z5, 72793-MS DUBLIN-MSEU-143##1#$2,342#Low Volume"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(14), "KPMG TAX CORPORATION#72189-APALONEU DUB-MSEU-1Z5, 72189-ITRANSLATE-I-ITRN-1Z5#6813995581#1#$230,000#Exempt"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(15), "KPMG TAX CORPORATION#72189-APALONEU DUB-MSEU-1Z5, 72189-ITRANSLATE-I-ITRN-1Z5#6813995580#1#$30,000#Exempt"));

            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'userImportsSuppliersAndValidateAutomaticMergeAndUndoMergeSupplierFunctionality()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'userImportsSuppliersAndValidateAutomaticMergeAndUndoMergeSupplierFunctionality()' method. " + e);
            return false;
        }finally {supplier = null;}
    }




    /********************************************************
     * Method Name      : userImportsUniqueSuppliersAndValidatesAutomaticMergingOfSuppliersShouldNotHappen
     * Purpose          : user imports unique suppliers and validates automatic merge should not happen
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean userImportsUniqueSuppliersAndValidatesAutomaticMergingOfSuppliersShouldNotHappen(WebDriver oBrowser, DataTable dataTable){
        Map<String, String> supplier = null;
        try{
            supplier = importSupplierFile(oBrowser, dataTable);
            Assert.assertTrue(supplier.get("TestPassed").equalsIgnoreCase("True"), "The method 'importSupplierFile()' was failed");
            Assert.assertTrue(userPerformMunchForTheImportedSuppliers(oBrowser, supplier));

            //Navigate to "Supplier" tab and validate the supplier details
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SuppliersTab_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Navigate to 'Suppliers' tab and validate the suppliers records");
            if(!appInd.verifyOptionalElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ProspectCompanySupplier_Grid + "//table[contains(@class, 'table-fixed')]//td[2]//span[@class='dx-sort dx-sort-up']"))){
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ProspectCompanySupplier_Grid + "//table[contains(@class, 'table-fixed')]//span[@class='dx-sort dx-sort-none']/../..")));
                appDep.threadSleep(1000);
            }

            Map<Integer, String> data = new HashMap<Integer, String>();
            for(int r=1; r<=9; r++){
                String rowData = "";
                for(int c=2; c<=7; c++){
                    rowData+=appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ProspectCompanySupplier_Grid + "//tr["+r+"][contains(@class, 'dx-row-lines')]/td["+c+"]"), "Text").trim()+"#";
                }
                data.put(r, rowData.substring(0, rowData.length()-1));
            }

            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(1), "BIDEASE INC#85865-APALONEU DUB-MSEU-1Z5, 85865-NYNEW01-MO-MS-161##11#$80,980#General"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(2), "BOB HARPER ENTERPRISES#61660-BEVERLY HILLS-MS-161#6813997483#1#$40,000#General"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(3), "BRIGHTCOVE INC#104755-MAWOB01-DB-MS-161#6813995579#1#$976#Network Name Match"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(4), "BRIKENA SELA QERKINI#92834-ITRANSLATE-I-MSEU-1Z5##1#$75#International"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(5), "CLICKBOOTH HOLDINGS INC#88331-APALONEU DUB-MSEU-1Z5, 88331-CODEN01-MO-MS-161#6813982952#24#$529,272#General"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(6), "CLIMACELL INC#90451-MABOS01-AP-MS-161##2#$4,178#Low Volume"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(7), "CLOUDFLARE#69130-APALONEU DUB-MSEU-1Z5##10#$70,000#General"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(8), "DELOITTE#72793-APALONEU DUB-MSEU-1Z5, 72793-MS DUBLIN-MSEU-143##1#$7,134#International"));
            Assert.assertTrue(compareSuppliersGridMultiValues(oBrowser, data.get(9), "KPMG TAX CORPORATION#72189-APALONEU DUB-MSEU-1Z5, 72189-ITRANSLATE-I-ITRN-1Z5#6813995581#1#$230,000#Exempt"));

            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(//div[contains(@class, '-summary-item')])[1]"), "Text", "Count: 9"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(//div[contains(@class, '-summary-item')])[2]"), "Text", "(T): 52"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(//div[contains(@class, '-summary-item')])[3]"), "Text", "(T): $962,614.55"));


            //Go to 'Merged Suppliers' Tab and validate there should not be any rows exist
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_MergedSuppliers_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", minTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "The 'Merged Suppliers' tab doesnot have any records");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_MergedSuppliers_Grid + "//span[text()='No data']")), "The 'Merged Suppliers' grid still contains some data when the unique suppliers are imported");
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'userImportsUniqueSuppliersAndValidatesAutomaticMergingOfSuppliersShouldNotHappen()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'userImportsUniqueSuppliersAndValidatesAutomaticMergingOfSuppliersShouldNotHappen()' method. " + e);
            return false;
        }finally {supplier = null;}
    }




    /********************************************************
     * Method Name      : compareSuppliersGridMultiValues
     * Purpose          : compare the values from the Suppliers grid with expected data
     * Author           : Gudi
     * Parameters       : oBrowser, actualData, expectedData
     * ReturnType       : boolean
     ********************************************************/
    public boolean compareSuppliersGridMultiValues(WebDriver oBrowser, String actualData, String expectedData){
        String arrExpectedValues[];
        String arrActualValues[];
        boolean blnStatus = false;
        try{
            if(actualData.equalsIgnoreCase(expectedData)){
                reports.writeResult(oBrowser, "Pass", "The actual data '"+actualData+"' and the expected data: '"+expectedData + " are matched");
                return true;
            }else{
                arrExpectedValues = expectedData.split("#", -1);
                arrActualValues = actualData.split("#", -1);

                for(int i=0; i<arrActualValues.length; i++){
                    blnStatus = false;
                    int j;
                    for(j=0; j<arrExpectedValues.length; j++){
                        if(arrActualValues[i].equalsIgnoreCase(arrExpectedValues[j])){
                            blnStatus = true;
                            break;
                        }
                    }
                    if(!blnStatus) {
                        reports.writeResult(oBrowser, "Fail", "Mis-match in actual data '" + arrActualValues[i] + "' and the expected values: '" + arrExpectedValues[j]);
                        Assert.fail("Mis-match in actual data '" + arrActualValues[i] + "' and the expected values: '" + arrExpectedValues[j]);
                    }
                }
                if(blnStatus) reports.writeResult(oBrowser, "Pass", "Both actual '"+actualData+"' and the expected values: '"+ expectedData +" are matching");
            }
            return blnStatus;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'compareSuppliersGridMultiValues()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'compareSuppliersGridMultiValues()' method. " + e);
            return false;
        }finally{arrExpectedValues = null; arrActualValues = null;}
    }



    public boolean createSupportCasesForSuppliers(WebDriver oBrowser){
        String supportCasePath = "(//div[@id='divSupportCasesContainer']//table)[1]//tr[1]/td/div[2][text()='%s']";
        String statusInput = "(//div[@id='divSupportCasesContainer']//table)[1]//tr/td[8]//input";
        try{
            Assert.assertTrue(appDep.selectGeneralModules(oBrowser, "Suppliers"));
            appInd.clickObject(oBrowser, By.xpath("//label[text()='Has PayNet Company ID']/..//div"));
            appInd.clickObject(oBrowser, By.xpath("//label[text()='In Program Only']/..//div"));
            appInd.clickObject(oBrowser, By.id("btn_filter"));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", 30);
            //------------------------- supplier-1 validation -------------------------------
            appInd.clickObject(oBrowser, By.xpath("(//div[@id='divSuppliersContainer']//table)[2]/tbody/tr[1]"));
            appDep.threadSleep(1000);
            appInd.switchToLastWindow(oBrowser);
            String payNetID = "";
            appInd.clickObject(oBrowser, By.xpath("//div[@id='exTab3']//a[text()='Support Cases']"));
            reports.writeResult(oBrowser, "Screenshot", "Supplier->Support Case tab visibility");
            appInd.scrollTo(oBrowser,0,400);
            //------------------ verify support case grid headers from supplier-1 ----------------------
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.id("sc_grid_filter_limit")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.id("sc_grid_filter_limit_button")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", 30);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(supportCasePath, "Case #"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(supportCasePath, "PayNet Supplier ID"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(supportCasePath, "Supplier Name"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(supportCasePath, "Case Category"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(supportCasePath, "Area Name"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(supportCasePath, "Case Type"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(supportCasePath, "Action Date"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(supportCasePath, "Status"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(supportCasePath, "Country Code"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(supportCasePath, "Priority"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(supportCasePath, "Assigned To"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(supportCasePath, "Origination Source"))));
            //------------------- verify export feature -------------------
            appInd.clickObject(oBrowser, By.xpath("(//div[@id='divSupportCasesContainer']//table)[1]//tr[1]/td//span[@title='Export']/i"));
            appDep.threadSleep(1000);
            oBrowser.switchTo().alert().accept();
            //----------------- collect support case record with status and verify into actual support case --------------
            appInd.clearAndSetObject(oBrowser, By.xpath(statusInput), "Open");
            appDep.threadSleep(1000);
            String totalOpenCases = getRowCount(appInd.getTextOnElement(oBrowser, By.xpath("//div[@class='dx-pages']/div[1]"), "text"));
            reports.writeResult(oBrowser, "Screenshot", "Supplier->Support Case tab > Open Status");
            appInd.clearAndSetObject(oBrowser, By.xpath(statusInput), "Hold");
            appDep.threadSleep(1000);
            String totalHoldCases = getRowCount(appInd.getTextOnElement(oBrowser, By.xpath("//div[@class='dx-pages']/div[1]"), "text"));
            reports.writeResult(oBrowser, "Screenshot", "Supplier->Support Case tab > Hold Status");
            appInd.clearAndSetObject(oBrowser, By.xpath(statusInput), "Closed");
            appDep.threadSleep(1000);
            String totalClosedCases = getRowCount(appInd.getTextOnElement(oBrowser, By.xpath("//div[@class='dx-pages']/div[1]"), "text"));
            reports.writeResult(oBrowser, "Screenshot", "Supplier->Support Case tab > Closed Status");
            appInd.clearAndSetObject(oBrowser, By.xpath(statusInput), "");
            appDep.threadSleep(1000);
            payNetID = appInd.getTextOnElement(oBrowser, By.xpath("(//div[@id='divSupportCasesContainer']//table)[2]//tr/td[2]"), "text");
            Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "Support Cases"));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", 30);
            appInd.clearAndSetObject(oBrowser, By.xpath("(//div[@id='divActiveSupportCasesContainer']//table)[1]//tr/td[2]//input"), payNetID);
            appDep.threadSleep(1000);
            String supportCases = getRowCount(appInd.getTextOnElement(oBrowser, By.xpath("//div[@id='divActiveSupportCasesContainer']//div[@class='dx-pages']/div[1]"), "text"));
            Assert.assertEquals(supportCases, totalOpenCases);
            //---- click to Pending tab -------
            appInd.clickObject(oBrowser, By.xpath("//div[@id='exTab3']//a[text()='Pending']"));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", 30);
            appInd.clearAndSetObject(oBrowser, By.xpath("(//div[@id='divPendingSupportCasesContainer']//table)[1]//tr/td[2]//input"), payNetID);
            appDep.threadSleep(1000);
            supportCases = getRowCount(appInd.getTextOnElement(oBrowser, By.xpath("//div[@id='divPendingSupportCasesContainer']//div[@class='dx-pages']/div[1]"), "text"));
            Assert.assertEquals(supportCases, totalHoldCases);
            //---- click to Closed tab -------
            appInd.clickObject(oBrowser, By.xpath("//div[@id='exTab3']//a[text()='Closed']"));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", 30);
            appInd.clearAndSetObject(oBrowser, By.xpath("(//div[@id='divClosedSupportCasesContainer']//table)[1]//tr/td[3]//input"), payNetID);
            appDep.threadSleep(1000);
            supportCases = getRowCount(appInd.getTextOnElement(oBrowser, By.xpath("//div[@id='divClosedSupportCasesContainer']//div[@class='dx-pages']/div[1]"), "text"));
            Assert.assertEquals(supportCases, totalClosedCases);
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createSupportCasesForSuppliers()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createSupportCasesForSuppliers()' method. " + e);
            return false;
        }
    }



    private String getRowCount(String footerMsg){
        String num = null;
        StringTokenizer st = new StringTokenizer(footerMsg, "()");
        while(st.hasMoreTokens()){
            st.nextToken();
            num = st.nextToken();
            System.out.println(num);
        }
        System.out.println(num.split(" ")[0]);
        return num.split(" ")[0];
    }




    /*****************************************************************************************************************
     * Method Name      : verifyProspectModuleShowHidePayment_RiskScoreAndCorcentricFeaturesTable
     * Purpose          : To validate hide/show Prospect company Show/Hide “Payment Score”, “Risk Score”, and “Corcentric Features” table for all tabs/globally in the Prospect Module
     * Author           : Gudi
     * Parameters       : oBrowser, isDisplayScoreEnabled
     * ReturnType       : boolean
     ****************************************************************************************************************/
    public boolean verifyProspectModuleShowHidePayment_RiskScoreAndCorcentricFeaturesTable(WebDriver oBrowser, String isDisplayScoreEnabled){
        try{
            appInd.scrollToThePage(oBrowser, "Top");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SolutionDesigns_Link));
            appDep.threadSleep(4000);

            if(isDisplayScoreEnabled.equalsIgnoreCase("False")){
                //Current State section
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CurrentState_PaymentScore_Tile + "[not(@style='display: none;')]")));
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CurrentState_RiskScore_Tile + "[not(@style='display: none;')]")));
                reports.writeResult(oBrowser, "Screenshot", "The elements are hidden under 'Solution Design-->Current State' screen");

                //Other Providers Approach
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_OtherProviderApproach_Link));
                appDep.threadSleep(4000);
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_OtherProviderApproach_PaymentScore_Label + "[not(@style='display: none;')]")));
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_OtherProviderApproach_RiskScore_Tile + "[not(@style='display: none;')]")));
                reports.writeResult(oBrowser, "Screenshot", "The elements are hidden under 'Solution Design-->Other Providers Approach' screen");

                //Results With Corcentric
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_Link));
                appDep.threadSleep(4000);
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_PaymentScore_ToggleOff_Grid + "[not(@style='display: none;')]")));
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_RiskScore_Grid + "[not(@style='display: none;')]")));
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_CorcentricFeatures_Table + "[not(@style='display: none;')]")));
                reports.writeResult(oBrowser, "Screenshot", "The elements are hidden under 'Solution Design-->Results With Corcentric' screen");

                //Solution tab
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Solution_Link));
                appDep.threadSleep(4000);
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentScore_ToggleOff_Grid + "[not(@style='display: none;')]")));
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_RiskScore_Grid + "[not(@style='display: none;')]")));
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CorcentricFeatures_Grid + "[not(@style='display: none;')]")));
                reports.writeResult(oBrowser, "Screenshot", "The elements are hidden under 'Solution' screen");
            }else{
                //Current State section
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CurrentState_PaymentScore_Tile + "[not(@style='display: none;')]")));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CurrentState_RiskScore_Tile + "[not(@style='display: none;')]")));
                reports.writeResult(oBrowser, "Screenshot", "The elements are shown under 'Solution Design-->Current State' screen");

                //Other Providers Approach
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_OtherProviderApproach_Link));
                appDep.threadSleep(4000);
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_OtherProviderApproach_PaymentScore_Label + "[not(@style='display: none;')]")));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_OtherProviderApproach_RiskScore_Tile + "[not(@style='display: none;')]")));
                reports.writeResult(oBrowser, "Screenshot", "The elements are Shown under 'Solution Design-->Other Providers Approach' screen");


                //Results With Corcentric
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_Link));
                appDep.threadSleep(4000);
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_PaymentScore_ToggleOff_Grid + "[not(@style='display: none;')]")));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_RiskScore_Grid + "[not(@style='display: none;')]")));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_CorcentricFeatures_Table + "[not(@style='display: none;')]")));
                reports.writeResult(oBrowser, "Screenshot", "The elements are Shown under 'Solution Design-->Results With Corcentric' screen");

                //Solution tab
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Solution_Link));
                appDep.threadSleep(4000);
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentScore_ToggleOff_Grid + "[not(@style='display: none;')]")));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_RiskScore_Grid + "[not(@style='display: none;')]")));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_CorcentricFeatures_Grid + "[not(@style='display: none;')]")));
                reports.writeResult(oBrowser, "Screenshot", "The elements are Shown under 'Solution' screen");
            }

            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifyProspectModuleShowHidePayment_RiskScoreAndCorcentricFeaturesTable()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifyProspectModuleShowHidePayment_RiskScoreAndCorcentricFeaturesTable()' method. " + e);
            return false;
        }
    }



    /**********************************************************************************************************
     * Method Name      : userOpensProspectCompanyAndValidateShowHide_PaymentScore_RiskScoreTilesAndCorcentricFeaturesTablesOfProspectModules
     * Purpose          : User validates Show/Hide “Payment Score”, “Risk Score” tiles and “Corcentric Features” table for all tabs/globally in the Prospect Module
     * Author           : Gudi
     * Parameters       : oBrowser, companyStatus, dataTable
     * ReturnType       : boolean
     **********************************************************************************************************/
    public boolean userOpensProspectCompanyAndValidateShowHide_PaymentScore_RiskScoreTilesAndCorcentricFeaturesTablesOfProspectModules(WebDriver oBrowser, String companyStatus, DataTable dataTable){
        Map<String, String> companyData = null;
        String companyName = null;
        Select oSelect = null;
        try{
            if(companyStatus.equalsIgnoreCase("New")){
                companyData = createNewCompany(oBrowser, dataTable);
                Assert.assertTrue(companyData.get("TestPassed").equalsIgnoreCase("True"), "The createNewCompany() method was failed");
                reports.writeResult(oBrowser, "Pass", "The new Prospect company was created & Opened successful");
            }else{
                companyName = appDep.selectTheCompanyBasedOnType(oBrowser, "Prospect");
                reports.writeResult(oBrowser, "Pass", "The existing Prospect company was opened successful");
            }

            appDep.navigateToInternalSetupPage(oBrowser, "Account Setup");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Preferences_Link));
            appDep.threadSleep(2000);
            appInd.scrollToThePage(oBrowser, "Bottom");
            if(companyStatus.equalsIgnoreCase("New")){
                //Validate the value TRUE to default to 'Display Score' dropdown
                oSelect = new Select(oBrowser.findElement(By.xpath(PayCRM_Modules_GeneralUIPage.obj_DisplayScore_List)));
                Assert.assertTrue(appInd.compareValues(oBrowser, oSelect.getFirstSelectedOption().getText(), "False"));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_DisplayScore_List + "/parent::td/preceding-sibling::td"), "Text", "Display Payment and Risk Score"));
                reports.writeResult(oBrowser, "Screenshot", "The 'Display Score' value must set to 'FALSE' by default");
            }else{
                //Set the value FALSE to 'Display Score' dropdown
                Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_DisplayScore_List), "False"));
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Preferences_Update_Btn));
                appDep.threadSleep(4000);
                reports.writeResult(oBrowser, "Screenshot", "The 'Display Score' value was set to 'FALSE'");
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_DisplayScore_List + "/parent::td/preceding-sibling::td"), "Text", "Display Payment and Risk Score"));
                oBrowser.navigate().refresh();
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", maxWaitTimeOut);
            }

            //Validate “Payment Score”, “Risk Score” tiles and “Corcentric Features” table for all tabs/globally in the Prospect Module are hidden
            Assert.assertTrue(verifyProspectModuleShowHidePayment_RiskScoreAndCorcentricFeaturesTable(oBrowser, "False"));

            //Set the value TRUE to 'Display Score' dropdown
            appDep.navigateToInternalSetupPage(oBrowser, "Prospect Page");

            if(!appInd.verifyOptionalElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Preferences_Panel + "[contains(@class, 'show')]"))){
                Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Preferences_Link));
                appDep.threadSleep(2000);
            }
            appInd.scrollToThePage(oBrowser, "Bottom");
            Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_DisplayScore_List), "True"));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Preferences_Update_Btn));
            appDep.threadSleep(4000);
            reports.writeResult(oBrowser, "Screenshot", "The 'Display Score' value was set to 'TRUE'");
            oBrowser.navigate().refresh();
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", maxWaitTimeOut);

            //Validate “Payment Score”, “Risk Score” tiles and “Corcentric Features” table for all tabs/globally in the Prospect Module are shown/displayed
            Assert.assertTrue(verifyProspectModuleShowHidePayment_RiskScoreAndCorcentricFeaturesTable(oBrowser, "True"));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'userOpensProspectCompanyAndValidateShowHide_PaymentScore_RiskScoreTilesAndCorcentricFeaturesTablesOfProspectModules()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'userOpensProspectCompanyAndValidateShowHide_PaymentScore_RiskScoreTilesAndCorcentricFeaturesTablesOfProspectModules()' method. " + e);
            return false;
        }finally {companyData = null; companyName = null; oSelect = null;}
    }





    /**********************************************************************************************************
     * Method Name      : userValidatesDefaultBenchMarkValuesUnderInternalSetUp_ExpectedSectionForProspectCompany
     * Purpose          : User validates default Benchmark values under internal setup-->Expected section for the prospect comapny
     * Author           : Gudi
     * Parameters       : oBrowser, companyStatus, dataTable
     * ReturnType       : boolean
     **********************************************************************************************************/
    public boolean validateExpectedTableDefaultValues(WebDriver oBrowser, int rowIndex, String rowData){
        String arrColumnData[];
        try{
            arrColumnData = rowData.split("#");
            for(int c=0; c<6; c++){
                if(c==0)
                    Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_Expected_Grid + "//tr["+rowIndex+"]/td)["+(c+1)+"]"), "Text").contains(arrColumnData[c]));
                if((c>=1) && (c<=3))
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_Expected_Grid + "//tr["+rowIndex+"]/td)["+(c+1)+"]/input"), "Value", arrColumnData[c]));

                if(c==5){
                    switch(arrColumnData[5].toLowerCase()){
                        case "yes":
                            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_Expected_Grid + "//tr["+rowIndex+"]/td)["+(c+1)+"]/input[@checked]")));
                            break;
                        case "no":
                            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_Expected_Grid + "//tr["+rowIndex+"]/td)["+(c+1)+"]/input[not(@checked)]")));
                    }
                }
            }
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'userOpensProspectCompanyAndValidateShowHideTiles_TablesOfProspectModules()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'userOpensProspectCompanyAndValidateShowHideTiles_TablesOfProspectModules()' method. " + e);
            return false;
        }finally{arrColumnData = null;}
    }






    /**********************************************************************************************************
     * Method Name      : userValidatesDefaultBenchMarkValuesUnderInternalSetUp_ExpectedSectionForProspectCompany
     * Purpose          : User validates default Benchmark values under internal setup-->Expected section for the prospect comapny
     * Author           : Gudi
     * Parameters       : oBrowser, companyStatus, dataTable
     * ReturnType       : boolean
     **********************************************************************************************************/
    public boolean userValidatesDefaultBenchMarkValuesUnderInternalSetUp_ExpectedSectionForProspectCompany(WebDriver oBrowser, DataTable dataTable){
        Map<String, String> companyData = null;
        String columnName[];
        try{
            companyData = createNewCompany(oBrowser, dataTable);
            Assert.assertTrue(companyData.get("TestPassed").equalsIgnoreCase("True"), "The createNewCompany() method was failed");
            reports.writeResult(oBrowser, "Pass", "The new Prospect company was created & Opened successful");

            appDep.navigateToInternalSetupPage(oBrowser, "Prospect Page");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Expected_Link));
            appDep.threadSleep(2000);
            appInd.scrollToThePage(oBrowser, "Bottom");

            //Validate the default values under 'Net Benefits'
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Expected_Section + "//div[text()='Net Benefits']")));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientRebatePercentage_Edit, "Value", "1"));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ClientKeepPercentage_Edit, "Value", "50"));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AchPlusDiscountPercentage_Edit, "Value", "2"));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AverageCheckCost_Edit, "Value", "7"));

            reports.writeResult(oBrowser, "Screenshot", "The 'Expected' grid default values");

            //Validate the default values under 'Supplier Categories'
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Expected_Section + "//div[text()='Supplier Categories']")));
            columnName = "Name#Virtual Card#ACH Plus#ACH Basic#Check#In Program".split("#");
            for(int i=0; i<columnName.length; i++){
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_Expected_Grid + "//th)["+(i+1)+"]"), "Text", columnName[i]));
            }

            Assert.assertTrue(validateExpectedTableDefaultValues(oBrowser, 1, "General#10.0#5.0#80.0#-10.0#Yes"));
            Assert.assertTrue(validateExpectedTableDefaultValues(oBrowser, 2, "Strategic#5.0#5.0#80.0#-10.0#Yes"));
            Assert.assertTrue(validateExpectedTableDefaultValues(oBrowser, 3, "Network TIN Match#50.0#5.0#80.0#-10.0#Yes"));
            Assert.assertTrue(validateExpectedTableDefaultValues(oBrowser, 4, "Network Name Match#30.0#5.0#80.0#-10.0#Yes"));
            Assert.assertTrue(validateExpectedTableDefaultValues(oBrowser, 5, "Exempt#0.0#0.0#80.0#-10.0#No"));
            Assert.assertTrue(validateExpectedTableDefaultValues(oBrowser, 6, "Client Request Removal#0.0#0.0#80.0#-10.0#No"));
            Assert.assertTrue(validateExpectedTableDefaultValues(oBrowser, 7, "International#0.0#0.0#80.0#-10.0#No"));
            Assert.assertTrue(validateExpectedTableDefaultValues(oBrowser, 8, "Individual#0.0#0.0#80.0#-10.0#No"));
            Assert.assertTrue(validateExpectedTableDefaultValues(oBrowser, 9, "Low Volume#0.0#0.0#50.0#-10.0#Yes"));
            Assert.assertTrue(validateExpectedTableDefaultValues(oBrowser, 10, "Network Name Match Virtual Card#50.0#5.0#80.0#-10.0#Yes"));
            Assert.assertTrue(validateExpectedTableDefaultValues(oBrowser, 11, "Network Tin Match Virtual Card#60.0#5.0#80.0#-10.0#Yes"));
            Assert.assertTrue(validateExpectedTableDefaultValues(oBrowser, 12, "Network Name Match Ach Plus#5.0#40.0#80.0#-10.0#Yes"));
            Assert.assertTrue(validateExpectedTableDefaultValues(oBrowser, 13, "Network Name Match Ach Basic#7.0#5.0#80.0#-10.0#Yes"));
            Assert.assertTrue(validateExpectedTableDefaultValues(oBrowser, 14, "Network Tin Match Ach Plus#5.0#50.0#80.0#-10.0#Yes"));
            Assert.assertTrue(validateExpectedTableDefaultValues(oBrowser, 15, "Network Tin Match Ach Basic#7.0#5.0#80.0#-10.0#Yes"));


            //Navigate to Solution tab and verify Net Benefits values
            //Solution tab
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Solution_Link));
            appDep.threadSleep(4000);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Solution_NetBenefit_Grid+"//div[text()='Virtual Card Rebate Percentage']/following-sibling::div"), "Text", "1%"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Solution_NetBenefit_Grid+"//div[text()='ACH Plus Discount Only']/following-sibling::div"), "Text", "2%"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Solution_NetBenefit_Grid+"//div[text()='Average Check Cost']/following-sibling::div"), "Text", "$7"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Solution_NetBenefit_Grid+"//div[text()='ACH Plus Keep Percentage']/following-sibling::div"), "Text", "50%"));
            reports.writeResult(oBrowser, "Screenshot", "'Solution-->Optimized Gross Benefit' table with New Benefit details");

            //Results With Corcentric
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SolutionDesigns_Link));
            appDep.threadSleep(4000);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_Link));
            appDep.threadSleep(4000);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SolutionDesign_ResultsWithCorcentric_NetBenefits_Grid+"//div[text()='Virtual Card Rebate Percentage']/following-sibling::div"), "Text", "1%"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SolutionDesign_ResultsWithCorcentric_NetBenefits_Grid+"//div[text()='ACH Plus Discount Only']/following-sibling::div"), "Text", "2%"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SolutionDesign_ResultsWithCorcentric_NetBenefits_Grid+"//div[text()='Average Check Cost']/following-sibling::div"), "Text", "$7"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SolutionDesign_ResultsWithCorcentric_NetBenefits_Grid+"//div[text()='ACH Plus Keep Percentage']/following-sibling::div"), "Text", "50%"));
            reports.writeResult(oBrowser, "Screenshot", "'Solution Desing-->Results With Corcentric-->Optimized Gross Benefit' table with New Benefit details");
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'userOpensProspectCompanyAndValidateShowHideTiles_TablesOfProspectModules()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'userOpensProspectCompanyAndValidateShowHideTiles_TablesOfProspectModules()' method. " + e);
            return false;
        }finally {companyData = null; columnName = null;}
    }




    /**********************************************************************************************************
     * Method Name      : userOpensProspectCompanyAndValidateShowHide_TheTotalCostNetBenefitsFromTheOptimizedProgramCostTableOfProspectModules
     * Purpose          : User validates Show/Hide “TotalCost”, “NetBenefits” options for Optimized Program Cost Table in the Prospect Module
     * Author           : Gudi
     * Parameters       : oBrowser, companyStatus, dataTable
     * ReturnType       : boolean
     **********************************************************************************************************/
    public boolean userOpensProspectCompanyAndValidateShowHide_TheTotalCostNetBenefitsFromTheOptimizedProgramCostTableOfProspectModules(WebDriver oBrowser, String companyStatus, DataTable dataTable){
        Map<String, String> companyData = null;
        String companyName = null;
        Select oSelect = null;
        try{
            if(companyStatus.equalsIgnoreCase("New")){
                companyData = createNewCompany(oBrowser, dataTable);
                Assert.assertTrue(companyData.get("TestPassed").equalsIgnoreCase("True"), "The createNewCompany() method was failed");
                reports.writeResult(oBrowser, "Pass", "The new Prospect company was created & Opened successful");
            }else{
                companyName = appDep.selectTheCompanyBasedOnType(oBrowser, "Prospect");
                reports.writeResult(oBrowser, "Pass", "The existing Prospect company was opened successful");
            }

            appDep.navigateToInternalSetupPage(oBrowser, "ProspectPage");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Preferences_Link));
            appDep.threadSleep(2000);
            appInd.scrollToThePage(oBrowser, "Bottom");
            if(companyStatus.equalsIgnoreCase("New")){
                //Validate the value TRUE to default to Display Total Cost and Net Benefits' dropdown
                oSelect = new Select(oBrowser.findElement(By.xpath(PayCRM_Modules_GeneralUIPage.obj_DisplayCostAndBenefits_List)));
                Assert.assertTrue(appInd.compareValues(oBrowser, oSelect.getFirstSelectedOption().getText(), "False"));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_DisplayCostAndBenefits_List + "/parent::td/preceding-sibling::td"), "Text", "Display Total Cost and Net Benefits"));
                reports.writeResult(oBrowser, "Screenshot", "The 'Display Cost and Benefit' value must set to 'FALSE' by default");
            }else{
                //Set the value FALSE to 'Display Total Cost and Net Benefits' dropdown
                Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_DisplayCostAndBenefits_List), "False"));
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Preferences_Update_Btn));
                appDep.threadSleep(4000);
                reports.writeResult(oBrowser, "Screenshot", "The 'Display Cost and Benefit' value was set to 'FALSE'");
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_DisplayCostAndBenefits_List + "/parent::td/preceding-sibling::td"), "Text", "Display Total Cost and Net Benefits"));
                oBrowser.navigate().refresh();
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", maxWaitTimeOut);
            }

            //Validate "Total Cost" & "Net benefits" from the Optimized Program Cost table in the Prospect Module are hidden
            Assert.assertTrue(verifyProspectModuleShowHideTotalCost_NetBenefitsFromOptimizedGrossBenefitsTable(oBrowser, "False"));

            //Set the value TRUE to 'Display Cost and Benefit' dropdown
            appDep.navigateToInternalSetupPage(oBrowser, "Prospect Page");

            if(!appInd.verifyOptionalElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Preferences_Panel + "[contains(@class, 'show')]"))){
                Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Preferences_Link));
                appDep.threadSleep(2000);
            }
            appInd.scrollToThePage(oBrowser, "Bottom");
            Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_DisplayCostAndBenefits_List), "True"));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Preferences_Update_Btn));
            appDep.threadSleep(4000);
            reports.writeResult(oBrowser, "Screenshot", "The 'Display Cost and Benefit' value was set to 'TRUE'");
            oBrowser.navigate().refresh();
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", maxWaitTimeOut);

            //Validate "Total Cost" & "Net benefits" from the Optimized Program Cost table in the Prospect Module are shown/displayed
            Assert.assertTrue(verifyProspectModuleShowHideTotalCost_NetBenefitsFromOptimizedGrossBenefitsTable(oBrowser, "True"));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'userOpensProspectCompanyAndValidateShowHide_TheTotalCostNetBenefitsFromTheOptimizedProgramCostTableOfProspectModules()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'userOpensProspectCompanyAndValidateShowHide_TheTotalCostNetBenefitsFromTheOptimizedProgramCostTableOfProspectModules()' method. " + e);
            return false;
        }finally {companyData = null; companyName = null; oSelect = null;}
    }




    /*****************************************************************************************************************
     * Method Name      : verifyProspectModuleShowHideTotalCost_NetBenefitsFromOptimizedGrossBenefitsTable
     * Purpose          : To validate hide/show Prospect company Show/Hide “Payment Score”, “Risk Score”, and “Corcentric Features” table for all tabs/globally in the Prospect Module
     * Author           : Gudi
     * Parameters       : oBrowser, isDisplayScoreEnabled
     * ReturnType       : boolean
     ****************************************************************************************************************/
    public boolean verifyProspectModuleShowHideTotalCost_NetBenefitsFromOptimizedGrossBenefitsTable(WebDriver oBrowser, String isDisplayScoreEnabled){
        try{
            appInd.scrollToThePage(oBrowser, "Top");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SolutionDesigns_Link));
            appDep.threadSleep(4000);

            if(isDisplayScoreEnabled.equalsIgnoreCase("False")){
                //Results With Corcentric
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_Link));
                appDep.threadSleep(4000);
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_TotolCardProgramCost_Label + "/preceding-sibling::div")));
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_NetBenefit_Label + "/preceding-sibling::span")));
                reports.writeResult(oBrowser, "Screenshot", "The elements are hidden under 'Solution Design-->Results With Corcentric-->Optimized Net Benefit' table");

                //Solution tab
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Solution_Link));
                appDep.threadSleep(4000);
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Solution_NetBenefit_Grid + PayCRM_Modules_GeneralUIPage.obj_TotolCardProgramCost_Label+"/preceding-sibling::div")));
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Solution_NetBenefit_Grid + PayCRM_Modules_GeneralUIPage.obj_NetBenefit_Label+"/preceding-sibling::div")));
                reports.writeResult(oBrowser, "Screenshot", "The elements are hidden under 'Solution tab-->Optimized Net Benefit' Table");
            }else{
                //Results With Corcentric
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ResultsWithCorcentric_Link));
                appDep.threadSleep(4000);
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_TotolCardProgramCost_Label + "/preceding-sibling::div")));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_NetBenefit_Label + "/preceding-sibling::span")));
                reports.writeResult(oBrowser, "Screenshot", "The elements are visible under 'Solution Design-->Results With Corcentric-->Optimized Net Benefit' table");

                //Solution tab
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Solution_Link));
                appDep.threadSleep(4000);
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Solution_NetBenefit_Grid + PayCRM_Modules_GeneralUIPage.obj_TotolCardProgramCost_Label+"/preceding-sibling::div")));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Solution_NetBenefit_Grid + PayCRM_Modules_GeneralUIPage.obj_NetBenefit_Label+"/preceding-sibling::span")));
                reports.writeResult(oBrowser, "Screenshot", "The elements are visible under 'Solution tab-->Optimized Net Benefit' Table");
            }
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifyProspectModuleShowHideTotalCost_NetBenefitsFromOptimizedGrossBenefitsTable()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifyProspectModuleShowHideTotalCost_NetBenefitsFromOptimizedGrossBenefitsTable()' method. " + e);
            return false;
        }
    }





    /*****************************************************************************************************************
     * Method Name      : userCreatesProspectCompanyAndPreparesMVFFile
     * Purpose          : User creates Prospect company and prepares MVF file for performing import suppliers
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ****************************************************************************************************************/
    public boolean userCreatesProspectCompanyAndPreparesMVFFile(WebDriver oBrowser, DataTable dataTable){
        Map<String, String> companyData = null;
        JSONArray dbResponse = null;
        String supplierName = null;
        String tinNumber = null;
        String filePath = null;
        try{
            companyData = createNewCompany(oBrowser, dataTable);
            Assert.assertTrue(companyData.get("TestPassed").equalsIgnoreCase("True"), "The createNewCompany() method was failed");
            reports.writeResult(oBrowser, "Pass", "The new Prospect company was created & Opened successful");

            //Execute the query, get the suppler name & TIN & update in the MVF excel file
            dbResponse = apiDataProvider.getAPIDataProviderResponse("PayCRM_NetworkName_TINMatchCalculation_Input", new Object[] {});
            supplierName = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("name"));
            tinNumber = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(1)).get("tin"));

            filePath = System.getProperty("user.dir").replace("\\", "/") + "/TestData/Test_4055_NetworkName_TINMatch.xlsx";
            excelDatatable.setCellData(filePath, "Sheet1", "Name", "Test-4055_vendRe_new", supplierName);
            excelDatatable.setCellData(filePath, "Sheet1", "TIN", "Test-4055_vend", tinNumber);
            excelDatatable.setCellData(filePath, "Sheet1", "Name", "Test-4055_vend", "Auto_Supplier"+appInd.getDateTime("hhmmss"));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'userCreatesProspectCompanyAndPreparesMVFFile()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'userCreatesProspectCompanyAndPreparesMVFFile()' method. " + e);
            return false;
        }finally {companyData = null; dbResponse = null; supplierName = null; tinNumber = null; filePath = null;}
    }



    /*****************************************************************************************************************
     * Method Name      : userImportSuppliersAndValidateUpdate_ReplaceNetworkNameAndTINMatchCalculations
     * Purpose          : To validate update/replace Network Name & TIN Match Calculations
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ****************************************************************************************************************/
    public boolean userImportSuppliersAndValidateUpdate_ReplaceNetworkNameAndTINMatchCalculations(WebDriver oBrowser, DataTable dataTable){
        Map<String, String> supplier = null;
        String supplierName1 = null;
        String supplierName2 = null;
        String tinNumber = null;
        String filePath = null;
        try{
            //Read the supplier name and TIN Number from the excel
            filePath = System.getProperty("user.dir").replace("\\", "/") + "/TestData/Test_4055_NetworkName_TINMatch.xlsx";
            supplierName1 = excelDatatable.getCellData(filePath, "Sheet1", "Name", 1);
            tinNumber = excelDatatable.getCellData(filePath, "Sheet1", "TIN", 2);
            supplierName2 = excelDatatable.getCellData(filePath, "Sheet1", "Name", 2);

            appDep.navigateToProspectCompanyAttachmentScreen(oBrowser, "Account SetUp");
            supplier = importSupplierFile(oBrowser, dataTable);
            Assert.assertTrue(supplier.get("TestPassed").equalsIgnoreCase("True"), "The method 'importSupplierFile()' was failed");
            Assert.assertTrue(userPerformMunchForTheImportedSuppliers(oBrowser, supplier));

            //Validate the Network Name and TIN Match calculations under Prospect company-->Suppliers grid
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SuppliersTab_Link));
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "The Prospect Company-->Solution Design-->Suppliers page");
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ProspectCompanySupplier_Grid + "//td[contains(@aria-label, 'Supplier,')]//input"), "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ProspectCompanySupplier_Grid + "//td[contains(@aria-label, 'Supplier,')]//input"), supplierName1));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//tr/td[2])[1]"), "Text", supplierName1));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//tr/td[5])[1]"), "Text", "Network Name Match Virtual Card"));

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ProspectCompanySupplier_Grid + "//td[contains(@aria-label, 'Supplier,')]//input"), ""));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ProspectCompanySupplier_Grid + "//td[contains(@aria-label, 'Tax ID,')]//input"), tinNumber));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//tr/td[2])[1]"), "Text", supplierName2));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//tr/td[4])[1]"), "Text", tinNumber));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_GeneralUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//tr/td[5])[1]"), "Text", "Network TIN Match Virtual Card"));


            //Validate the Network Name and TIN Match calculations under Module-->General-->Supplier--> Search the supplier by name and TinNumber
            Assert.assertTrue(appDep.selectGeneralModules(oBrowser, "suppliers"));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Suppliers_Filter_Btn));
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_Suppliers_Grid + "//input)[1]"), supplierName1));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Modules-->General-->Suppliers page with supplier name");
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Suppliers_Grid + "//tr[1][contains(@class, 'dx-row-lines')]/td[1]"), "Text", supplierName1));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Suppliers_Grid + "//tr[1][contains(@class, 'dx-row-lines')]/td[7]"), "Text", "In Program - Network Name Match Virtual Card"));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_Suppliers_Grid + "//input)[1]"), ""));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_Suppliers_Grid + "//input)[3]"), tinNumber));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Modules-->General-->Suppliers page with TIN Number");
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Suppliers_Grid + "//tr[1][contains(@class, 'dx-row-lines')]/td[1]"), "Text", supplierName2));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Suppliers_Grid + "//tr[1][contains(@class, 'dx-row-lines')]/td[3]"), "Text", tinNumber));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Suppliers_Grid + "//tr[1][contains(@class, 'dx-row-lines')]/td[7]"), "Text", "In Program - Network TIN Match Virtual Card"));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'userImportSuppliersAndValidateUpdate_ReplaceNetworkNameAndTINMatchCalculations()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'userImportSuppliersAndValidateUpdate_ReplaceNetworkNameAndTINMatchCalculations()' method. " + e);
            return false;
        }finally {supplier = null; supplierName1 = null; supplierName2 = null; tinNumber = null; filePath = null;}
    }



    /********************************************************
     * Method Name      : editAndValidateTheCompanyDetails()
     * Purpose          : user edits the company from General modules and validates the same
     * Author           : Gudi
     * Parameters       : oBrowser, companyStatus, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean editAndValidateTheCompanyDetails1(WebDriver oBrowser, String companyStatus, DataTable dataTable) {
        List<Map<String, String>> companyDetails = null;
        Map<String, String> companyData = null;
        String timeStamp = null;
        try {
            timeStamp = appInd.getDateTime("Shhmmss");
            companyDetails = dataTable.asMaps(String.class, String.class);
            companyData = new HashMap<String, String>();
            companyData.put("Name", companyDetails.get(0).get("Name") + "_" + timeStamp);
            editCompanyName = companyData.get("Name");
            companyData.put("Tickler", companyDetails.get(0).get("Tickler"));
            companyData.put("Website", companyDetails.get(0).get("Website"));
            if (timeStamp.length() == 9) {
                companyData.put("PaynetCompanyID", timeStamp);
            } else {
                companyData.put("PaynetCompanyID", timeStamp + "0");
            }
            companyData.put("SalesforceID", companyDetails.get(0).get("SalesforceID"));
            companyData.put("Phone", companyDetails.get(0).get("Phone"));
            companyData.put("Fax", companyDetails.get(0).get("Fax"));
            companyData.put("ZipCode", companyDetails.get(0).get("ZipCode"));
            companyData.put("Parent", companyDetails.get(0).get("Parent"));
            companyData.put("Locale", companyDetails.get(0).get("Locale"));
            companyData.put("Language", companyDetails.get(0).get("Language"));
            companyData.put("OriginationSource", companyDetails.get(0).get("OriginationSource"));
            companyData.put("CompanyType", companyDetails.get(0).get("CompanyType"));
            companyData.put("Status", companyDetails.get(0).get("Status"));
            companyData.put("Priority", companyDetails.get(0).get("Priority"));

            companyData = editCompanyDetails(oBrowser, companyData);
            Assert.assertTrue(companyData.get("TestPassed").equalsIgnoreCase("True"), "The editCompanyDetails() method was failed");
            Assert.assertTrue(verifyCompanyDetails(oBrowser, companyStatus, companyData));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'editAndValidateTheCompanyDetails()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'editAndValidateTheCompanyDetails()' method. " + e);
            return false;
        } finally {
            companyDetails = null; companyData = null; timeStamp = null;
        }
    }
}
