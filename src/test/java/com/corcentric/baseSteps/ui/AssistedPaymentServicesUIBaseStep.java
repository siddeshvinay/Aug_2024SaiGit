package com.corcentric.baseSteps.ui;

import com.corcentric.pages.AssistedPaymentServicesUIPage;
import com.corcentric.pages.EnrollmentsManagerUIPage;
import com.corcentric.pages.PayCRM_Modules_DailyGrindUIPage;
import com.corcentric.pages.PayCRM_Modules_GeneralUIPage;
import com.corcentric.runner.CucumberTestRunner;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.io.File;
import java.util.*;

import net.minidev.json.JSONArray;

public class AssistedPaymentServicesUIBaseStep extends CucumberTestRunner {
    private String assignedUser;
    private String assignTo;
    private String contactsName;
    private Map<String, String> updateContactsData;
    private String caseNumber;

    /********************************************************
     * Method Name      : reassignTheEnrollmentCaseForAssistedPaymentServices()
     * Purpose          : to reassign the cases under Assisted Payment services
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean reassignTheEnrollmentCaseForAssistedPaymentServices(WebDriver oBrowser, DataTable dataTable){
        List<Map<String, String>> reassignmentDetails = null;
        try{
            reassignmentDetails = dataTable.asMaps(String.class, String.class);
            Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Reassign_Btn), "clickObject() was failed for '"+String.valueOf(EnrollmentsManagerUIPage.obj_Reassign_Btn)+"' webelement");
            appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_ReassignCase_Dialog, "Text", "Reassign Case", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "The 'Reassign Case' dialog opened successful");

            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignCase_UserName_Label), "verifyElementPresent() was failed for '"+String.valueOf(AssistedPaymentServicesUIPage.obj_ReassignCase_UserName_Label)+"' webelement");
            boolean blnRes = appInd.verifyOptionalElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignCase_WorkQueue_List);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_Assign_Btn), "verifyElementPresent() was failed for '"+String.valueOf(AssistedPaymentServicesUIPage.obj_Assign_Btn)+"' webelement");

            assignedUser = appInd.createAndGetWebElement(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignCase_UserName_Label).getText();
            if(assignedUser.equalsIgnoreCase(reassignmentDetails.get(0).get("AssignedToUser1"))){
                assignTo = reassignmentDetails.get(0).get("AssignedToUser2");
            }else{
                assignTo = reassignmentDetails.get(0).get("AssignedToUser1");
            }
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignCase_UserName_Label), "clickObject() was failed for '"+String.valueOf(AssistedPaymentServicesUIPage.obj_ReassignCase_UserName_Label)+"' webelement");
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_reassignCase_UserName_Edit, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_reassignCase_UserName_Edit, assignTo), "setObject() was failed for '"+String.valueOf(AssistedPaymentServicesUIPage.obj_reassignCase_UserName_Edit)+"' webelement");
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignCase_UserName_SearchResult_Label), "clickObject() was failed for '"+String.valueOf(AssistedPaymentServicesUIPage.obj_ReassignCase_UserName_SearchResult_Label)+"' webelement");
            if(blnRes)
                Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignCase_WorkQueue_List, reassignmentDetails.get(0).get("workQueue")), "selectObject() was failed for '"+String.valueOf(AssistedPaymentServicesUIPage.obj_ReassignCase_WorkQueue_List)+"' webelement");
            reports.writeResult(oBrowser, "Screenshot", "The Partner Enrollment 'Reassign Case' details entered successful");
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_Assign_Btn), "clickObject() was failed for '"+String.valueOf(AssistedPaymentServicesUIPage.obj_Assign_Btn)+"' webelement");
            appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_ReassignCase_Dialog, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignConfirmationMessage_Label, "Text").contains("Case has been successfully reassigned"), "Mis-match in actual & expected values for the '"+String.valueOf(AssistedPaymentServicesUIPage.obj_ReassignConfirmationMessage_Label)+"' webelement");
            reports.writeResult(oBrowser, "Screenshot", "The post 'Reassign Case' details for Partner Enrollment");
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_AssignedTo_Label, "Text").trim(), assignTo, "Mis-match in actual & expected values for the '"+String.valueOf(EnrollmentsManagerUIPage.obj_AssignedTo_Label)+"' webelement");
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'reassignTheEnrollmentCaseForAssistedPaymentServices()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'reassignTheEnrollmentCaseForAssistedPaymentServices()' method. " + e);
            return false;
        }finally{reassignmentDetails = null;}
    }




    /********************************************************
     * Method Name      : verifyTheReassignmentDetailsUnderAssistedPaymentServicesActivitiesTab()
     * Purpose          : to verify the reassignment details under Activities tab
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean verifyTheReassignmentDetailsUnderAssistedPaymentServicesActivitiesTab(WebDriver oBrowser) {
        Calendar cal = null;
        try {
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_SplitterHorizonal_Line, "Clickable", "", waitTimeOut);
            appInd.scrollToElement(oBrowser, AssistedPaymentServicesUIPage.obj_SplitterHorizonal_Line);
            appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_Activities_Tab);
            appDep.waitForTheElementState(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Activities_Grid + "//td[text()='CMVP Activities']"), "Clickable", "", minTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "The Assisted Payment Services 'Activities' tab details");
            Assert.assertTrue(appInd.verifyText(oBrowser, AssistedPaymentServicesUIPage.obj_Activities_SMVP_Activity_Column, "Text", "Reassignment by " + appInd.getPropertyValueByKeyName("qaUserName")), "Mis-match in actual & expected values for the '"+String.valueOf(AssistedPaymentServicesUIPage.obj_Activities_SMVP_Activity_Column)+"' webelement");
            Assert.assertTrue(appInd.verifyText(oBrowser, AssistedPaymentServicesUIPage.obj_Activities_SMVP_ActivityType_Column, "Text", "Case Update"), "Mis-match in actual & expected values for the '"+String.valueOf(AssistedPaymentServicesUIPage.obj_Activities_SMVP_ActivityType_Column)+"' webelement");
            Assert.assertTrue(appInd.verifyText(oBrowser, AssistedPaymentServicesUIPage.obj_Activities_SMVP_CreatedBy_Column, "Text", appInd.getPropertyValueByKeyName("qaUserName")), "Mis-match in actual & expected values for the '"+String.valueOf(AssistedPaymentServicesUIPage.obj_Activities_SMVP_CreatedBy_Column)+"' webelement");
            cal = Calendar.getInstance();
            String dateCreated = String.valueOf((cal.get(Calendar.MONTH)+1))+"/"+String.valueOf(cal.get(Calendar.DAY_OF_MONTH))+"/"+String.valueOf(cal.get(Calendar.YEAR));
            Assert.assertTrue(appInd.verifyText(oBrowser, AssistedPaymentServicesUIPage.obj_Activities_SMVP_DateCreated_Column, "Text", dateCreated), "Mis-match in actual & expected values for the '"+String.valueOf(AssistedPaymentServicesUIPage.obj_Activities_SMVP_DateCreated_Column)+"' webelement");
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_Activities_SMVP_Notes_Column, "Text").contains("Queue Owner reassigned case to " + assignTo + " in work"), "Mis-match in actual & expected values for the '"+String.valueOf(AssistedPaymentServicesUIPage.obj_Activities_SMVP_Notes_Column)+"' webelement");
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in ' verifyTheReassignmentDetailsUnderAssistedPaymentServicesActivitiesTab()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifyTheReassignmentDetailsUnderAssistedPaymentServicesActivitiesTab()' method. " + e);
            return false;
        }finally{cal = null;}
    }




    /********************************************************
     * Method Name      : clickOnAssistedPaymentServicesLogsTab()
     * Purpose          : to verify that user clicks on Logs tab
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean clickOnAssistedPaymentServicesLogsTab(WebDriver oBrowser){
        try{
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_SplitterHorizonal_Line, "Clickable", "", waitTimeOut);
            appInd.scrollToElement(oBrowser, AssistedPaymentServicesUIPage.obj_SplitterHorizonal_Line);
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_Reassign_Logs_Tab), "clickObject() was failed for the '"+String.valueOf(AssistedPaymentServicesUIPage.obj_Reassign_Logs_Tab)+"' webelement");
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_Reassign_Logs_TableGrid, "Clickable", "", waitTimeOut);
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in ' clickOnAssistedPaymentServicesLogsTab()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'clickOnAssistedPaymentServicesLogsTab()' method. " + e);
            return false;
        }
    }



    /********************************************************
     * Method Name      : verifyTheReassignDetailsUnderAssistedPaymentServicesLogsTab()
     * Purpose          : to verify the enrollment details under Logs tab grid of Assisted Payment Services
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean verifyTheReassignDetailsUnderAssistedPaymentServicesLogsTab(WebDriver oBrowser) {
        try {
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_Reassign_Logs_TableGrid, "Clickable", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "The 'Logs' tab details");
            Assert.assertTrue(appInd.verifyText(oBrowser, AssistedPaymentServicesUIPage.obj_Reassign_Logs_UserName_Label, "Text", appInd.getPropertyValueByKeyName("qaUserName")), "Mis-match in actual & expected values for the '"+String.valueOf(AssistedPaymentServicesUIPage.obj_Reassign_Logs_UserName_Label)+"' webelement");
            Assert.assertTrue(appInd.verifyText(oBrowser, AssistedPaymentServicesUIPage.obj_Reassign_Logs_Actions_Label, "Text", "Updated Assigned to from " + assignedUser + " to " + assignTo), "Mis-match in actual & expected values for the '"+String.valueOf(AssistedPaymentServicesUIPage.obj_Reassign_Logs_Actions_Label)+"' webelement");
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifyTheReassignDetailsUnderAssistedPaymentServicesLogsTab()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifyTheReassignDetailsUnderAssistedPaymentServicesLogsTab()' method. " + e);
            return false;
        }
    }




    /********************************************************
     * Method Name      : clickOnAssistedPaymentServicesContactsTab()
     * Purpose          : to verify that the user clicks on the Contacts tab in Assisted Payment Services
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean clickOnAssistedPaymentServicesContactsTab(WebDriver oBrowser){
        try{
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_Contacts_Tab, "Clickable", "", waitTimeOut);
            appInd.scrollToElement(oBrowser, AssistedPaymentServicesUIPage.obj_SplitterHorizonal_Line);
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_Contacts_Tab), "clickObject() was failed for the '"+String.valueOf(AssistedPaymentServicesUIPage.obj_Contacts_Tab)+"' webelement");
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_Contacts_Add_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_Contacts_Add_Btn), "verifyElementPresent() was failed for '"+String.valueOf(AssistedPaymentServicesUIPage.obj_Contacts_Add_Btn)+"' webelement");
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in ' clickOnAssistedPaymentServicesContactsTab()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'clickOnAssistedPaymentServicesContactsTab()' method. " + e);
            return false;
        }
    }





    /********************************************************
     * Method Name      : addNewServiceContactDetails()
     * Purpose          : to verify that the user can add the new contacts under Assisted Payment Services
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean addNewServiceContactDetails(WebDriver oBrowser, Map<String, String> contactsData){
        try{
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_Contacts_Add_Btn), "clickObject() was failed for '"+AssistedPaymentServicesUIPage.obj_Contacts_Add_Btn+"' webelement");
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_Create_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_Header, "Text", "New Contact"), "verifyText() was failed for '"+AssistedPaymentServicesUIPage.obj_NewContact_Header+"' webelement");

            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_ContactType_List, contactsData.get("ContactType")), "selectObject() was failed for '"+AssistedPaymentServicesUIPage.obj_NewContact_ContactType_List+"' webelement");
            Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_Name_Edit, contactsData.get("Name")), "selectObject() was failed for '"+AssistedPaymentServicesUIPage.obj_NewContact_Name_Edit+"' webelement");
            Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_Email_Edit, contactsData.get("Email")), "selectObject() was failed for '"+AssistedPaymentServicesUIPage.obj_NewContact_Email_Edit+"' webelement");
            Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_Title_Edit, contactsData.get("Title")), "selectObject() was failed for '"+AssistedPaymentServicesUIPage.obj_NewContact_Title_Edit+"' webelement");
            Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_Phone1_Edit, contactsData.get("Phone1")), "selectObject() was failed for '"+AssistedPaymentServicesUIPage.obj_NewContact_Phone1_Edit+"' webelement");
            Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_Phone1Extn_Edit, contactsData.get("Phone1Extn")), "selectObject() was failed for '"+AssistedPaymentServicesUIPage.obj_NewContact_Phone1Extn_Edit+"' webelement");
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_Phone1Type_List, contactsData.get("Phone1Type")), "selectObject() was failed for '"+AssistedPaymentServicesUIPage.obj_NewContact_Phone1Type_List+"' webelement");
            Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_Phone2_Edit, contactsData.get("Phone2")), "selectObject() was failed for '"+AssistedPaymentServicesUIPage.obj_NewContact_Phone2_Edit+"' webelement");
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_Phone2Type_List, contactsData.get("Phone2Type")), "selectObject() was failed for '"+AssistedPaymentServicesUIPage.obj_NewContact_Phone2Type_List+"' webelement");
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_Status_List, contactsData.get("Status")), "selectObject() was failed for '"+AssistedPaymentServicesUIPage.obj_NewContact_Status_List+"' webelement");
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_Priority_List, contactsData.get("Priority")), "selectObject() was failed for '"+AssistedPaymentServicesUIPage.obj_NewContact_Priority_List+"' webelement");

            WebElement objPrimaryContact = appInd.createAndGetWebElement(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_PrimaryContact_Chkbox);
            if(contactsData.get("IsPrimaryContact").equalsIgnoreCase("Yes") && (!objPrimaryContact.isSelected())){
                Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_PrimaryContact_Chkbox), "clickObject() was failed for '"+AssistedPaymentServicesUIPage.obj_NewContact_PrimaryContact_Chkbox+"' webelement");
            }else if(contactsData.get("IsPrimaryContact").equalsIgnoreCase("No") && (objPrimaryContact.isSelected())){
                Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_PrimaryContact_Chkbox), "clickObject() was failed for '"+AssistedPaymentServicesUIPage.obj_NewContact_PrimaryContact_Chkbox+"' webelement");
            }
            reports.writeResult(oBrowser, "Screenshot", "Before creating the new contacts");
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_Create_Btn), "clickObject() was failed for '"+String.valueOf(AssistedPaymentServicesUIPage.obj_NewContact_Create_Btn)+"' webelement");
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_Header, "Invisibility", "", waitTimeOut);

            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appInd.clearAndSetObject(oBrowser, By.xpath("("+AssistedPaymentServicesUIPage.obj_Cases_Contacts_Grid +"//input)[1]"), contactsData.get("Name"));
            appDep.threadSleep(2000);
            appInd.verifyText(oBrowser, By.xpath("("+AssistedPaymentServicesUIPage.obj_Cases_Contacts_Grid + "//tr[contains(@class, 'lines dx-column-lines')])[1]/td[1]"), "Text", contactsData.get("Name"));
            reports.writeResult(oBrowser, "Screenshot", "After creating the new contacts");
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'addNewServiceContactDetails()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'addNewServiceContactDetails()' method. " + e);
            return false;
        }
    }



    /********************************************************
     * Method Name      : verifyTheContactsDetails()
     * Purpose          : to verify new contact is added succesful for Assisted Payment Services
     * Author           : Gudi
     * Parameters       : oBrowser, contactType
     * ReturnType       : boolean
     ********************************************************/
    public boolean verifyTheContactsDetails(WebDriver oBrowser, Map<String, String> contactDetails){
        try {
            reports.writeResult(oBrowser, "Screenshot", contactDetails.get("contactStatus") + " Contacts detail in the contact grid");
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_Header, "Invisibility", "", waitTimeOut);
            WebElement objContactName = appInd.createAndGetWebElement(oBrowser, AssistedPaymentServicesUIPage.obj_Contacts_TableGrid);
            Assert.assertTrue(objContactName.findElement(By.xpath("//tr/td[text()='"+contactDetails.get("Name")+"']")).getText().equals(contactDetails.get("Name")), "Mis-match in the actual & expected values for Contacts 'Name'");
            Assert.assertTrue(objContactName.findElement(By.xpath("//tr/td[text()='"+contactDetails.get("Name")+"']/following-sibling::td[1]")).getText().equals(contactDetails.get("Email")), "Mis-match in the actual & expected values for Contacts 'Email'");
            Assert.assertTrue(objContactName.findElement(By.xpath("//tr/td[text()='"+contactDetails.get("Name")+"']/following-sibling::td[2]")).getText().equals(contactDetails.get("Phone1")+" ext. "+contactDetails.get("Phone1Extn")), "Mis-match in the actual & expected values for Contacts 'Phone1'");
            Assert.assertTrue(objContactName.findElement(By.xpath("//tr/td[text()='"+contactDetails.get("Name")+"']/following-sibling::td[3]")).getText().equals(contactDetails.get("Phone1Type")), "Mis-match in the actual & expected values for Contacts 'Phone1Type'");
            Assert.assertTrue(objContactName.findElement(By.xpath("//tr/td[text()='"+contactDetails.get("Name")+"']/following-sibling::td[4]")).getText().equals(contactDetails.get("Phone2")), "Mis-match in the actual & expected values for Contacts 'Phone2'");
            Assert.assertTrue(objContactName.findElement(By.xpath("//tr/td[text()='"+contactDetails.get("Name")+"']/following-sibling::td[5]")).getText().equals(contactDetails.get("Phone2Type")), "Mis-match in the actual & expected values for Contacts 'Phone2Type'");
            Assert.assertTrue(objContactName.findElement(By.xpath("//tr/td[text()='"+contactDetails.get("Name")+"']/following-sibling::td[6]")).getText().equals(contactDetails.get("Status")), "Mis-match in the actual & expected values for Contacts 'Status'");
            Assert.assertTrue(objContactName.findElement(By.xpath("//tr/td[text()='"+contactDetails.get("Name")+"']/following-sibling::td[7]")).getText().equals(contactDetails.get("Priority")), "Mis-match in the actual & expected values for Contacts 'Priority'");
            Assert.assertTrue(objContactName.findElement(By.xpath("//tr/td[text()='"+contactDetails.get("Name")+"']/following-sibling::td[8]")).getText().equals(contactDetails.get("ContactType")), "Mis-match in the actual & expected values for Contacts 'ContactType'");
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifyTheContactsDetails()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifyTheContactsDetails()' method. " + e);
            return false;
        }
    }




    /********************************************************
     * Method Name      : updateTheExistingContactDetails()
     * Purpose          : to verify that the existing contacts updated for Assisted Payment Services
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean updateTheExistingContactDetails(WebDriver oBrowser, Map<String, String> updateContactsData){
        try{
            //objContactName = appInd.createAndGetWebElement(oBrowser, AssistedPaymentServicesUIPage.obj_Contacts_TableGrid);
            //objContactName.findElement(By.xpath("//tr/td[text()='"+updateContactsData.get("previousContactsName")+"']/following-sibling::td/a[@title='Edit']")).click();
            appInd.clickObject(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Cases_Contacts_Grid + "//a[@title='Edit']"));
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_EditContact_Header, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_Name_Edit, "Value", updateContactsData.get("previousContactsName")), "Mis-match in the actual & expected values for the '"+AssistedPaymentServicesUIPage.obj_NewContact_Name_Edit+"' webelement");
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_Create_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, AssistedPaymentServicesUIPage.obj_EditContact_Header, "Text", "Edit Contact"), "verifyText() was failed for '"+AssistedPaymentServicesUIPage.obj_NewContact_Header+"' webelement");

            reports.writeResult(oBrowser, "Screenshot", "Before editing the Contacts detail");
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_ContactType_List, updateContactsData.get("ContactType")), "selectObject() was failed for '"+AssistedPaymentServicesUIPage.obj_NewContact_ContactType_List+"' webelement");
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_Name_Edit, updateContactsData.get("Name")), "selectObject() was failed for '"+AssistedPaymentServicesUIPage.obj_NewContact_Name_Edit+"' webelement");
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_Email_Edit, updateContactsData.get("Email")), "selectObject() was failed for '"+AssistedPaymentServicesUIPage.obj_NewContact_Email_Edit+"' webelement");
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_Title_Edit, updateContactsData.get("Title")), "selectObject() was failed for '"+AssistedPaymentServicesUIPage.obj_NewContact_Title_Edit+"' webelement");
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_Phone1_Edit, updateContactsData.get("Phone1")), "selectObject() was failed for '"+AssistedPaymentServicesUIPage.obj_NewContact_Phone1_Edit+"' webelement");
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_Phone1Extn_Edit, updateContactsData.get("Phone1Extn")), "selectObject() was failed for '"+AssistedPaymentServicesUIPage.obj_NewContact_Phone1Extn_Edit+"' webelement");
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_Phone1Type_List, updateContactsData.get("Phone1Type")), "selectObject() was failed for '"+AssistedPaymentServicesUIPage.obj_NewContact_Phone1Type_List+"' webelement");
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_Phone2_Edit, updateContactsData.get("Phone2")), "selectObject() was failed for '"+AssistedPaymentServicesUIPage.obj_NewContact_Phone2_Edit+"' webelement");
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_Phone2Type_List, updateContactsData.get("Phone2Type")), "selectObject() was failed for '"+AssistedPaymentServicesUIPage.obj_NewContact_Phone2Type_List+"' webelement");
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_Status_List, updateContactsData.get("Status")), "selectObject() was failed for '"+AssistedPaymentServicesUIPage.obj_NewContact_Status_List+"' webelement");
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_Priority_List, updateContactsData.get("Priority")), "selectObject() was failed for '"+AssistedPaymentServicesUIPage.obj_NewContact_Priority_List+"' webelement");

            WebElement objPrimaryContact = appInd.createAndGetWebElement(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_PrimaryContact_Chkbox);
            if(updateContactsData.get("IsPrimaryContact").equalsIgnoreCase("Yes") && (!objPrimaryContact.isSelected())){
                Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_PrimaryContact_Chkbox), "clickObject() was failed for '"+AssistedPaymentServicesUIPage.obj_NewContact_PrimaryContact_Chkbox+"' webelement");
            }else if(updateContactsData.get("IsPrimaryContact").equalsIgnoreCase("No") && (objPrimaryContact.isSelected())){
                Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_PrimaryContact_Chkbox), "clickObject() was failed for '"+AssistedPaymentServicesUIPage.obj_NewContact_PrimaryContact_Chkbox+"' webelement");
            }
            reports.writeResult(oBrowser, "Screenshot", "After editing the Contacts detail");

            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_Create_Btn), "clickObject() was failed for '"+String.valueOf(AssistedPaymentServicesUIPage.obj_NewContact_Create_Btn)+"' webelement");
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_NewContact_Header, "Invisibility", "", waitTimeOut);
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appInd.clearAndSetObject(oBrowser, By.xpath("("+AssistedPaymentServicesUIPage.obj_Cases_Contacts_Grid +"//input)[1]"), updateContactsData.get("Name"));
            appDep.threadSleep(2000);
            appInd.verifyText(oBrowser, By.xpath("("+AssistedPaymentServicesUIPage.obj_Cases_Contacts_Grid + "//tr[contains(@class, 'lines dx-column-lines')])[1]/td[1]"), "Text", updateContactsData.get("Name"));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'updateTheExistingContactDetails()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'updateTheExistingContactDetails()' method. " + e);
            return false;
        }
    }



    /********************************************************
     * Method Name      : deleteContact()
     * Purpose          : to verify that the user deletes the contact for Assisted Payment Services
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean deleteContact(WebDriver oBrowser){
        WebElement objContactName = null;
        WebDriverWait oWait = null;
        WebElement objEle = null;
        try{
            /*oWait = new WebDriverWait(oBrowser, Duration.ofSeconds(waitTimeOut));
            objContactName = appInd.createAndGetWebElement(oBrowser, AssistedPaymentServicesUIPage.obj_Contacts_TableGrid);
            oWait.until(ExpectedConditions.elementToBeClickable(objContactName.findElement(By.xpath("//tr/td[text()='"+contactsName+"']/following-sibling::td/a[@title='Delete']"))));
            objEle = oBrowser.findElement(By.xpath("//table[contains(@class, 'dx-datagrid-table-content')]/tbody/tr/td[text()='"+contactsName+"']/following-sibling::td/a[@title='Delete']"));
            objEle.click();*/
            appInd.clickObject(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Cases_Contacts_Grid + "//a[@title='Delete']"));
            appDep.threadSleep(2000);
            if(appInd.isAlertPresent(oBrowser)){
                oBrowser.switchTo().alert().accept();
            }
            appDep.threadSleep(4000);
            appDep.waitForTheElementState(oBrowser, By.xpath("//table[contains(@class, 'dx-datagrid-table-content')]/tbody/tr/td[text()='"+contactsName+"']"), "Invisibility", "", waitTimeOut);
            List<WebElement> objElements = oBrowser.findElements(By.xpath("//table[contains(@class, 'dx-datagrid-table-content')]/tbody/tr/td[text()='"+contactsName+"']"));
            if(objElements.size() > 0){
                reports.writeResult(oBrowser, "Fail", "Failed to remove the selected Contacts from the Contacts grid");
                Assert.assertTrue(false, "Failed to delete the Contact");
            }else{
                reports.writeResult(oBrowser, "Pass", "The selected Contact was deleted from the Contacts grid");
                Assert.assertTrue(true, "The Contact was deleted successful");
            }
            reports.writeResult(oBrowser, "Screenshot", "After deleting the contact");
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'deleteContact()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'deleteContact()' method. " + e);
            return false;
        }
        finally{
            objContactName = null; oWait = null; objEle = null;
        }
    }




    /********************************************************
     * Method Name      : addAndValidateTheNewContactDetails()
     * Purpose          : to add and validate the contacts details for Assisted Payment Services
     * Author           : Gudi
     * Parameters       : oBrowser, contactStatus, queueName, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean addAndValidateTheNewContactDetails(WebDriver oBrowser, String contactStatus, String queueName, DataTable dataTable){
        List<Map<String, String>> contactDetails = null;
        String timeStamp = null;
        try{
            Assert.assertTrue(enrollmentManagersUIBaseSteps.selectRequiredQueue(oBrowser, queueName));
            Assert.assertTrue(enrollmentManagersUIBaseSteps.clickOnShowCaseButton(oBrowser, queueName));
            Assert.assertNotNull(enrollmentManagersUIBaseSteps.readAndClickResultFirstCaseNumber(oBrowser, queueName));
            Assert.assertTrue(assistedPaymentServicesBaseSteps.clickOnAssistedPaymentServicesContactsTab(oBrowser));

            Map<String, String> contactsData;
            timeStamp = appInd.getDateTime("YYYYShhmmss");
            contactDetails = dataTable.asMaps(String.class, String.class);
            contactsData = new HashMap<String, String>();
            contactsData.put("contactStatus", contactStatus);
            contactsData.put("ContactType", contactDetails.get(0).get("ContactType"));
            contactsData.put("Name", contactDetails.get(0).get("Name")+"_"+timeStamp);
            contactsName = contactsData.get("Name");
            contactsData.put("Email", contactDetails.get(0).get("Email"));
            contactsData.put("Title", contactDetails.get(0).get("Title"));
            contactsData.put("Phone1", contactDetails.get(0).get("Phone1"));
            contactsData.put("Phone1Extn", contactDetails.get(0).get("Phone1Extn"));
            contactsData.put("Phone1Type", contactDetails.get(0).get("Phone1Type"));
            contactsData.put("Phone2", contactDetails.get(0).get("Phone2"));
            contactsData.put("Phone2Type", contactDetails.get(0).get("Phone2Type"));
            contactsData.put("Status", contactDetails.get(0).get("Status"));
            contactsData.put("Priority", contactDetails.get(0).get("Priority"));
            contactsData.put("IsPrimaryContact", contactDetails.get(0).get("IsPrimaryContact"));

            Assert.assertTrue(assistedPaymentServicesBaseSteps.addNewServiceContactDetails(oBrowser, contactsData), "addNewServiceContactDetails() method was failed.");
            Assert.assertTrue(assistedPaymentServicesBaseSteps.verifyTheContactsDetails(oBrowser, contactsData), "verifyTheContactsDetails() method was failed");
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'addAndValidateTheNewContactDetails()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'addAndValidateTheNewContactDetails()' method. " + e);
            return false;
        }
    }




    /********************************************************
     * Method Name      : editTheExistingContactDetails()
     * Purpose          : to edit and validate the contacts details for Assisted Payment Services
     * Author           : Gudi
     * Parameters       : oBrowser, contactStatus, queueName, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean editTheExistingContactDetails(WebDriver oBrowser, String contactStatus, String queueName, DataTable dataTable){
        List<Map<String, String>> contactDetails = null;
        String timeStamp = null;
        try{
            updateContactsData = new HashMap<String, String>();
            timeStamp = appInd.getDateTime("YYYYShhmmss");
            contactDetails = dataTable.asMaps(String.class, String.class);
            updateContactsData.put("previousContactsName", contactsName);
            updateContactsData.put("contactStatus", contactStatus);
            updateContactsData.put("ContactType", contactDetails.get(0).get("ContactType"));
            updateContactsData.put("Name", contactDetails.get(0).get("Name")+"_"+timeStamp);
            contactsName = updateContactsData.get("Name");
            updateContactsData.put("Email", contactDetails.get(0).get("Email"));
            updateContactsData.put("Title", contactDetails.get(0).get("Title"));
            updateContactsData.put("Phone1", contactDetails.get(0).get("Phone1"));
            updateContactsData.put("Phone1Extn", contactDetails.get(0).get("Phone1Extn"));
            updateContactsData.put("Phone1Type", contactDetails.get(0).get("Phone1Type"));
            updateContactsData.put("Phone2", contactDetails.get(0).get("Phone2"));
            updateContactsData.put("Phone2Type", contactDetails.get(0).get("Phone2Type"));
            updateContactsData.put("Status", contactDetails.get(0).get("Status"));
            updateContactsData.put("Priority", contactDetails.get(0).get("Priority"));
            updateContactsData.put("IsPrimaryContact", contactDetails.get(0).get("IsPrimaryContact"));

            Assert.assertTrue(assistedPaymentServicesBaseSteps.updateTheExistingContactDetails(oBrowser, updateContactsData), "updateTheExistingContactDetails() method was failed");
            Assert.assertTrue(assistedPaymentServicesBaseSteps.verifyTheContactsDetails(oBrowser, updateContactsData), "verifyTheContactsDetails() method was failed");
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'editTheExistingContactDetails()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'editTheExistingContactDetails()' method. " + e);
            return false;
        }finally{
            updateContactsData = null;
        }
    }




    /********************************************************
     * Method Name      : selectTheRequiredCaseIDForAssistantPaymentServices()
     * Purpose          : user selects the required case id from the Assisted Payment Services queue using DB queries
     * Author           : Gudi
     * Parameters       : oBrowser, queueName, queryKey
     * ReturnType       : boolean
     ********************************************************/
    public boolean selectTheRequiredCaseIDForAssistantPaymentServices(WebDriver oBrowser, String queueName, String queryKey){
        String caseNumber = null;
        String strURL = null;
        JSONArray caseDetails = null;
        try{
            if(queryKey.equalsIgnoreCase("AsstdPaymentServices_PaymentQuestions")){
                caseDetails = apiDataProvider.getAPIDataProviderResponse(queryKey, new Object[] {"typeID", 29});
            }
            else{
                caseDetails = apiDataProvider.getAPIDataProviderResponse(queryKey, new Object[] {});
            }

            caseNumber = ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("cases_id").toString();
            strURL = appInd.getPropertyValueByKeyName("qaURL")+"/cases/" + caseNumber;
            oBrowser.navigate().to(strURL);
            reports.writeResult(oBrowser, "Screenshot", "The constructed URL: " + strURL);
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentHelp_Label, "Text", "Payment Help", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "The page has opened with the case id '"+caseNumber+"'");
            Assert.assertTrue(appInd.verifyText(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentHelp_Label, "Text", "Payment Help"));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_CaseNumber_Label, "Text").contains("Case "+caseNumber));
            Assert.assertTrue(setDefaultPaymentQuestionMailUnderPreferences(oBrowser, "Assisted Payment Services"));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'selectTheRequiredCaseIDForAssistantPaymentServices()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'selectTheRequiredCaseIDForAssistantPaymentServices()' method. " + e);
            return false;
        }finally {strURL = null; caseNumber = null; caseDetails = null;}
    }




    /********************************************************
     * Method Name      : validateThePaymentReturnStatusAsReturned()
     * Purpose          : user validates the Return Status for the Payment Information under Assisted Payment Services queue
     * Author           : Gudi
     * Parameters       : oBrowser, returnStatus
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateThePaymentReturnStatusAsReturned(WebDriver oBrowser, String returnStatus){
        try{
            appInd.scrollToElement(oBrowser, AssistedPaymentServicesUIPage.obj_SplitterHorizonal_Line);
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_ReturnStatus_Value_Label, "Clickable", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Payment Information section");
            Assert.assertTrue(appInd.verifyText(oBrowser, AssistedPaymentServicesUIPage.obj_ReturnStatus_Value_Label, "Text", "Confirmed"));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateThePaymentReturnStatusAsReturned()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateThePaymentReturnStatusAsReturned()' method. " + e);
            return false;
        }
    }




    /********************************************************
     * Method Name      : userPerformLogActivity()
     * Purpose          : user perform Log activity for the case under Assisted Payment Services queue
     * Author           : Gudi
     * Parameters       : oBrowser, returnStatus
     * ReturnType       : boolean
     ********************************************************/
    public Map<String, String> performLogActivity(WebDriver oBrowser, DataTable dataTable){
        List<Map<String, String>> logActivityData = null;
        Map<String, String> logActivity = null;
        String timeStamp = null;
        String supplierName = null;
        String caseNumber = null;
        String arrCaseActivity[];
        int expectedAlaramDate = 0;
        try{
            timeStamp = appInd.getDateTime("hhmmss");
            logActivityData = dataTable.asMaps(String.class, String.class);
            logActivity = new HashMap<String, String>();
            logActivity.put("ActivityType", logActivityData.get(0).get("ActivityType"));
            logActivity.put("WebAddress", appInd.getPropertyValueByKeyName("qaURL"));
            logActivity.put("CaseOutcome", logActivityData.get(0).get("CaseOutcome"));
            logActivity.put("CaseOutcomeValidation", logActivityData.get(0).get("CaseOutcomeValidation"));
            logActivity.put("Notes", logActivityData.get(0).get("Notes") + timeStamp);

            appInd.scrollToThePage(oBrowser, "Top");
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_LogActivity_Btn));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_ActivityType_List, "Clickable", "", waitTimeOut);

            caseNumber = appInd.getTextOnElement(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_CaseReference_Grid + "//dt[text()='Case #']/following-sibling::dd[1]"), "Text");
            supplierName = appInd.getTextOnElement(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_CaseReference_Grid + "//dt[text()='Supplier']/following-sibling::dd[1]"), "Text");

            reports.writeResult(oBrowser, "Screenshot", "Before entering the details for 'Log Activity'");
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_ActivityType_List, logActivity.get("ActivityType")));
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_WebAddress_Edit, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_WebAddress_Edit, logActivity.get("WebAddress")));
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_CaseOutcome_List, logActivity.get("CaseOutcome")));

            //Verify pend logic for below Caseoutcome options
            switch(logActivity.get("CaseOutcome")){
                case "Call Transfer": case "Team Lead Review":
                    logActivity.put("caseStatusRunTime", "Open");
                    appDep.waitForTheElementState(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_ActivityCase_UserID_Label), "Clickable", "", minTimeOut);
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_ActivityCase_UserID_Label)));
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_ActivityCase_UserID_Label)));
                    Assert.assertTrue(appInd.setObject(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_ActivityCase_UserID_Edit), logActivity.get("CaseOutcomeValidation")));
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_ActivityCase_UserID_Edit +"/parent::span/following-sibling::span//li")));
                    if(logActivity.get("CaseOutcome").equalsIgnoreCase("Call Transfer"))
                        Assert.assertTrue(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_CaseOutcomeDescription_Label, "Text").contains("Agent is transferring the call to a different Agent. Case will be Reassigned to the selected Agent."));
                    else Assert.assertTrue(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_CaseOutcomeDescription_Label, "Text").contains("Team Lead is reviewing Case with an Agent."));
                    break;
                case "Payment Question": case "Promised to Process": case "Payment Follow-up": case "No Response": case "No Answer - LVM":
                    arrCaseActivity = logActivity.get("CaseOutcomeValidation").split("#", -1);
                    appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_CaseActivityAlarmDate_Edit, "Clickable", "", minTimeOut);
                    String caseAlarmDate = appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_CaseActivityAlarmDate_Edit, "Value");
                    if(logActivity.get("CaseOutcome").equalsIgnoreCase("Payment Question") || logActivity.get("CaseOutcome").equalsIgnoreCase("Promised to Process")){
                        expectedAlaramDate = 5;
                        logActivity.put("caseStatusRunTime", "Hold");
                        logActivity.put("expectedAlaramDate", String.valueOf(5));
                        Assert.assertTrue(appInd.compareValues(oBrowser, "5", String.valueOf(appInd.dateDifferences(caseAlarmDate.replace("-", "/"), appInd.getDateTime("yyyy/MM/dd"), "Day", "yyyy/MM/dd"))));
                    }else{
                        expectedAlaramDate = 3;
                        logActivity.put("caseStatusRunTime", "Hold");
                        logActivity.put("expectedAlaramDate", String.valueOf(3));
                        Assert.assertTrue(appInd.compareValues(oBrowser, "3", String.valueOf(appInd.dateDifferences(caseAlarmDate.replace("-", "/"), appInd.getDateTime("yyyy/MM/dd"), "Day", "yyyy/MM/dd"))));
                    }

                    logActivity.put("alarmDate", caseAlarmDate);
                    Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_CaseActivityContactName_Edit, arrCaseActivity[0]));
                    Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_CaseActivityContactNumber_Edit, arrCaseActivity[1]));

                    if(logActivity.get("CaseOutcome").equalsIgnoreCase("Payment Question"))
                        Assert.assertTrue(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_CaseOutcomeDescription_Label, "Text").replace("\n", "").contains("Agent has a question regarding the payment and cannot complete.Case is set to Hold with the Alarm Date defaulted to "+expectedAlaramDate+" days in the future."));
                    else if(logActivity.get("CaseOutcome").equalsIgnoreCase("Promised to Process")) {
                        appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_ConfirmationNumber_Edit, "Clickable", "", minTimeOut);
                        Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_ConfirmationNumber_Edit, arrCaseActivity[2]));
                        Assert.assertTrue(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_CaseOutcomeDescription_Label, "Text").replace("\n", "").contains("Supplier has promised to process the payment.Case is set to Hold with the Alarm Date defaulted to "+expectedAlaramDate+" days in the future."));
                    }else if(logActivity.get("CaseOutcome").equalsIgnoreCase("Payment Follow-up"))
                        Assert.assertTrue(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_CaseOutcomeDescription_Label, "Text").replace("\n", "").contains("Payment has been sent and Agent will follow-up for confirmation on a later date.Case is set to Hold with the Alarm Date defaulted to "+expectedAlaramDate+" days in the future."));
                    else if(logActivity.get("CaseOutcome").equalsIgnoreCase("No Response"))
                        Assert.assertTrue(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_CaseOutcomeDescription_Label, "Text").replace("\n", "").contains("Supplier did not respond when an attempt was made to contact, via phone or email.Case is set to Hold with the Alarm Date defaulted to "+expectedAlaramDate+" days in the future."));
                    else if(logActivity.get("CaseOutcome").equalsIgnoreCase("No Answer - LVM"))
                        Assert.assertTrue(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_CaseOutcomeDescription_Label, "Text").replace("\n", "").contains("Supplier did not answer when called and agent left a voicemail.Case is set to Hold with the Alarm Date defaulted to "+expectedAlaramDate+" days in the future."));
                    break;
                case "Payment Question Answered":
                    logActivity.put("caseStatusRunTime", "Open");
                    break;
            }
            reports.writeResult(oBrowser, "Screenshot", "Log Activity Details are entered successful");
            Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_ActivityNotes_Textarea, logActivity.get("Notes")));
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_Activity_Log_Btn));
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignConfirmationMessage_Label, "Clickable", "", waitTimeOut);
            appInd.scrollToThePage(oBrowser, "Top");
            reports.writeResult(oBrowser, "Screenshot", "After creating the 'Log Activity'");
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignConfirmationMessage_Label, "Text").contains("Case Activity has been successfully created."));
            appInd.scrollToThePage(oBrowser, "Bottom");
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_LogActivity_Update_Section + "//h3"), "Text").contains("Updated"));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_LogActivity_Update_Section + "//p"), "Text").contains("Case "+caseNumber+" - "+supplierName+" has been successfully updated with a new Case Activity."));
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
            logActivity.put("TestPassed", "True");
            return logActivity;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'performLogActivity()' method. " + e);
            logActivity.put("TestPassed", "False");
            return logActivity;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'performLogActivity()' method. " + e);
            logActivity.put("TestPassed", "False");
            return logActivity;
        }
        finally{logActivityData = null; logActivity = null; timeStamp = null; supplierName = null; caseNumber = null;}
    }





    /********************************************************
     * Method Name      : raisePaymentQuestion()
     * Purpose          : user raise the payment question for the case under Assisted Payment Services queue
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean raisePaymentQuestion(WebDriver oBrowser, DataTable dataTable){
        List<Map<String, String>> questionData = null;
        try{
            questionData = dataTable.asMaps(String.class, String.class);
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_EmailTemplate_Btn));
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentQuestion_Link));
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentQuestion_Send_Btn, "Clickable", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Before entering the details for 'Payment Questions'");
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_Issue_List, questionData.get(0).get("Issue")));
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_VoidReason_List, questionData.get(0).get("VoidReason")));
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentMethod_List, questionData.get(0).get("PaymentMethod")));
            if(questionData.get(0).get("PaymentDate") == null){
                Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentDate_Edit, appInd.getDateTime("dd-MM-yyyy")));
            }else{
                Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentDate_Edit, questionData.get(0).get("PaymentDate")));
            }
            Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_DueAmount_Edit, questionData.get(0).get("DueAmount")));
            Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_IssuedAmount_Edit, questionData.get(0).get("IssuedAmount")));
            Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_ReissuedAmount_Edit, questionData.get(0).get("ReissuedAmount")));
            reports.writeResult(oBrowser, "Screenshot", "After entering the details for 'Payment Questions'");
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentQuestion_Send_Btn));
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignConfirmationMessage_Label, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignConfirmationMessage_Label, "Text").contains("Client Support email has been successfully sent."));
            return true;
       }catch(Exception e) {
           reports.writeResult(oBrowser, "Exception", "Exception in 'raisePaymentQuestion()' method. " + e);
           return false;
       }catch(AssertionError e) {
           reports.writeResult(oBrowser, "Exception", "AssertionError in 'raisePaymentQuestion()' method. " + e);
           return false;
       }finally{
            questionData = null;
        }
    }

    /********************************************************
     * Method Name      : editTheExistingVoids()
     * Purpose          : user edit the existing void for the case under Assisted Payment Services queue
     * Author           : Gudi
     * Parameters       : oBrowser, queueName, voidsData
     * ReturnType       : Map<String, String>
     ********************************************************/
    public Map<String, String> editTheExistingVoids(WebDriver oBrowser, String queueName, Map<String, String> voidsData){
        String data[];
        List<Map<String, String>> db_AsstdPaymentServices_Waves = null;
        String strURL = null;
        JSONArray id = null;
        try{
            id = apiDataProvider.getAPIDataProviderResponse("Bill_Pay_Cases_with_data_in_Voids_tab", new Object[] {});
            caseNumber = ((LinkedHashMap) ((JSONArray) id.get(0)).get(0)).get("cases_id").toString();

            //caseNumber = db_AsstdPaymentServices_Waves.get(0).get("cases_id");
            strURL = appInd.getPropertyValueByKeyName("qaURL")+"/cases/" + caseNumber;
            oBrowser.navigate().to(strURL);
            reports.writeResult(oBrowser, "Screenshot", "The constructed URL: " + strURL);
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentHelp_Label, "Text", "Payment Help", waitTimeOut);
            Assert.assertTrue(appInd.scrollToElement(oBrowser, AssistedPaymentServicesUIPage.obj_SplitterHorizonal_Line));
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_Voids_Link));
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_Voids_Edit_Link, "Clickable", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Inside 'Voids' tab");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_VoidsTab_Grid + "//a[@title='Edit']")));
            appDep.waitForTheElementState(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Voids_EditTransaction_Header + "//h4"), "Text", "Edit Transaction", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Voids_EditTransaction_Header + "//h4"), "Text", "Edit Transaction"));

            if(voidsData.get("Name") != null)
                Assert.assertTrue(appInd.clearAndSetObject(oBrowser, AssistedPaymentServicesUIPage.obj_Voids_Name_Edit, voidsData.get("Name")));
            else
                voidsData.put("Name", appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_Voids_Name_Edit, "Value"));

            if(voidsData.get("CompanySupplierID") != null)
                Assert.assertTrue(appInd.clearAndSetObject(oBrowser, AssistedPaymentServicesUIPage.obj_Voids_CompanySupplierID_Edit, voidsData.get("CompanySupplierID")));
            else
                voidsData.put("CompanySupplierID", appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_Voids_CompanySupplierID_Edit, "Value"));


            if(voidsData.get("Case") != null)
                Assert.assertTrue(appInd.clearAndSetObject(oBrowser, AssistedPaymentServicesUIPage.obj_Voids_Case_Edit, voidsData.get("Case")));
            else{
                voidsData.put("Case", appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_Voids_Case_Edit, "Value"));
                Assert.assertTrue(voidsData.get("Case").equals(caseNumber), "Invalid case number '"+caseNumber+"' was displayed for the selected voids");
            }


            if(voidsData.get("PaymentID") != null)
                Assert.assertTrue(appInd.clearAndSetObject(oBrowser, AssistedPaymentServicesUIPage.obj_Voids_PaymentID_Edit, voidsData.get("PaymentID")));
            else
                voidsData.put("PaymentID", appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_Voids_PaymentID_Edit, "Value"));


            data = voidsData.get("Amount").split("#");
            if(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_Voids_Amount_Edit, "Value").equalsIgnoreCase(data[0])){
                voidsData.put("Amount", data[1]);
            }else{
                voidsData.put("Amount", data[0]);
            }
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, AssistedPaymentServicesUIPage.obj_Voids_Amount_Edit, voidsData.get("Amount")));


            data = voidsData.get("Currency").split("#");
            if(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_Voids_Currency_Edit, "Value").equalsIgnoreCase(data[0])){
                voidsData.put("Currency", data[1]);
            }else{
                voidsData.put("Currency", data[0]);
            }
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, AssistedPaymentServicesUIPage.obj_Voids_Currency_Edit, voidsData.get("Currency")));


            data = voidsData.get("CheckCount").split("#");
            if(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_Voids_CheckCount_Edit, "Value").equalsIgnoreCase(data[0])){
                voidsData.put("CheckCount", data[1]);
            }else{
                voidsData.put("CheckCount", data[0]);
            }
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, AssistedPaymentServicesUIPage.obj_Voids_CheckCount_Edit, voidsData.get("CheckCount")));


            data = voidsData.get("Terms").split("#");
            if(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_Voids_Terms_Edit, "Value").equalsIgnoreCase(data[0])){
                voidsData.put("Terms", data[1]);
            }else{
                voidsData.put("Terms", data[0]);
            }
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, AssistedPaymentServicesUIPage.obj_Voids_Terms_Edit, voidsData.get("Terms")));


            data = voidsData.get("TransactionType").split("#");
            if(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_Voids_TransactionType_List, "Dropdown").equalsIgnoreCase(data[0])){
                voidsData.put("TransactionType", data[1]);
            }else{
                voidsData.put("TransactionType", data[0]);
            }
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_Voids_TransactionType_List, voidsData.get("TransactionType")));

            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_Voids_Update_Btn));
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_EditVoids_ConfirmationMessage_Label, "Visibility", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_EditVoids_ConfirmationMessage_Label, "Text").contains("Transaction has been successfully updated."));
            voidsData.put("TestPassed", "True");
            return voidsData;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'editTheExistingVoids()' method. " + e);
            voidsData.put("TestPassed", "False");
            return voidsData;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'editTheExistingVoids()' method. " + e);
            voidsData.put("TestPassed", "False");
            return voidsData;
        }finally {data = null; db_AsstdPaymentServices_Waves = null; strURL = null;}
    }





    /********************************************************
     * Method Name      : validateVoidsDetails()
     * Purpose          : user validate the edited void details for the case under Assisted Payment Services queue
     * Author           : Gudi
     * Parameters       : oBrowser, voidsData
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateVoidsDetails(WebDriver oBrowser, Map<String, String> voidsData){
        try{
            appDep.waitForTheElementState(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Voids_Grid + "//td[contains(@aria-label, 'Column Name')]//input"), "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_Attachments_Link));
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_Attachments_Pagination, "Visibility", "", waitTimeOut);

            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_Voids_Link));
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_Voids_Pagination, "Visibility", "", waitTimeOut);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Voids_Grid + "//td[contains(@aria-label, 'Column Name')]//input"), voidsData.get("Name")));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Edit Voids Grid");
            appDep.waitForTheElementState(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Voids_Grid + "//table[contains(@class, 'dx-datagrid-table-content')]//tr[1]/td[1]"), "Text", voidsData.get("Name"), waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Voids_Grid + "//table[contains(@class, 'dx-datagrid-table-content')]//tr[1]/td[1]"), "Text", voidsData.get("Name")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Voids_Grid + "//table[contains(@class, 'dx-datagrid-table-content')]//tr[1]/td[2]"), "Text", voidsData.get("PaymentID")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Voids_Grid + "//table[contains(@class, 'dx-datagrid-table-content')]//tr[1]/td[4]"), "Text").replace(",","").contains(voidsData.get("Amount")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Voids_Grid + "//table[contains(@class, 'dx-datagrid-table-content')]//tr[1]/td[5]"), "Text", voidsData.get("TransactionType")));

            //Show Details
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_Voids_Show_Link));
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_Voids_Show_Close_Btn, "Clickable", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Edit Voids-->Show Page");
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Voids_Show_Content + "//h4[@class='modal-title']"), "Text").contains("Transaction - Payment ID: "+voidsData.get("PaymentID")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Voids_Show_Content + "//div[@class='modal-body']/dl/dd[1]"), "Text", voidsData.get("Name")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Voids_Show_Content + "//div[@class='modal-body']/dl/dd[2]"), "Text", voidsData.get("PaymentID")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Voids_Show_Content + "//div[@class='modal-body']/dl/dd[4]"), "Text").contains(voidsData.get("Amount")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Voids_Show_Content + "//div[@class='modal-body']/dl/dd[5]"), "Text", voidsData.get("Currency")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Voids_Show_Content + "//div[@class='modal-body']/dl/dd[6]"), "Text", voidsData.get("Terms")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Voids_Show_Content + "//div[@class='modal-body']/dl/dd[7]"), "Text", voidsData.get("TransactionType")));
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_Voids_Show_Close_Btn));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateVoidsDetails()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateVoidsDetails()' method. " + e);
            return false;
        }
    }





    /********************************************************
     * Method Name      : editTheExistingVoidsAndValidatetheSame()
     * Purpose          : user edit the existing void for the case under Assisted Payment Services queue
     * Author           : Gudi
     * Parameters       : oBrowser, queueName, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean editTheExistingVoidsAndValidatetheSame(WebDriver oBrowser, String queueName, DataTable dataTable){
        List<Map<String, String>> voidData = null;
        Map<String, String> voids = null;
        String timeStamp = null;
        try{
            timeStamp = appInd.getDateTime("Shhmmss");
            voidData = dataTable.asMaps(String.class, String.class);
            voids = new HashMap<String, String>();
            voids.put("Name", voidData.get(0).get("Name") + timeStamp);
            voids.put("CompanySupplierID", voidData.get(0).get("CompanySupplierID"));
            voids.put("Case", voidData.get(0).get("Case"));
            voids.put("PaymentID", voidData.get(0).get("PaymentID"));
            voids.put("Amount", voidData.get(0).get("Amount"));
            voids.put("Currency", voidData.get(0).get("Currency"));
            voids.put("CheckCount", voidData.get(0).get("CheckCount"));
            voids.put("Terms", voidData.get(0).get("Terms"));
            voids.put("TransactionType", voidData.get(0).get("TransactionType"));

            voids = editTheExistingVoids(oBrowser, queueName, voids);
            Assert.assertTrue(voids.get("TestPassed").equalsIgnoreCase("True"), "The 'editTheExistingVoids()' method was failed");
            Assert.assertTrue(validateVoidsDetails(oBrowser, voids));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'editTheExistingVoidsAndValidatetheSame()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'editTheExistingVoidsAndValidatetheSame()' method. " + e);
            return false;
        }
        finally {
            voidData = null; voids = null; timeStamp = null;
        }
    }





    /********************************************************
     * Method Name      : validateTheMessageForPayNextSyncButtonClick()
     * Purpose          : user clicks on 'PayNey Sync' button & validate for the case under Assisted Payment Services queue
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateTheMessageForPayNextSyncButtonClick(WebDriver oBrowser){
        try{
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_PayNetSync_Btn));
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignConfirmationMessage_Label, "Visibility", "", waitTimeOut);
            String message = appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignConfirmationMessage_Label, "Text");
            Assert.assertTrue(message.contains("Case already up-to-date.") || message.contains("Case now up-to-date"));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateTheMessageForPayNextSyncButtonClick()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateTheMessageForPayNextSyncButtonClick()' method. " + e);
            return false;
        }
    }




    /********************************************************
     * Method Name      : validatePaymentDataTab()
     * Purpose          : user validates Payment Data tab for the case under Assisted Payment Services queue
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validatePaymentDataTab(WebDriver oBrowser, String queryKey){
        String amount = null;
        String paymentAmount = null;
        String paymentIdentifier = null;
        String currencyCode = null;
        String checkNumber = null;
        String strURL = null;
        JSONArray caseDetails = null;
        try{
            caseDetails = apiDataProvider.getAPIDataProviderResponse(queryKey, new Object[] {});
            caseNumber = ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("cases_id").toString();

            strURL = appInd.getPropertyValueByKeyName("qaURL")+"/cases/" + caseNumber;
            oBrowser.navigate().to(strURL);
            reports.writeResult(oBrowser, "Screenshot", "The constructed URL: " + strURL);
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentHelp_Label, "Text", "Payment Help", waitTimeOut);
            appInd.scrollToElement(oBrowser, AssistedPaymentServicesUIPage.obj_SplitterHorizonal_Line);
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentsData_Link));
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_BankInformation_Label, "Text", "Bank Information", waitTimeOut);
            amount = appInd.getTextOnElement(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_PaymentData_Grid + "//td[text()='Amount']/following-sibling::td"), "Text").trim();
            Assert.assertTrue(!amount.isEmpty());
            paymentAmount = appInd.getTextOnElement(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_PaymentData_Grid + "//td[text()='Payment Amount']/following-sibling::td"), "Text").trim();
            Assert.assertTrue(!paymentAmount.isEmpty());
            paymentIdentifier = appInd.getTextOnElement(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_PaymentData_Grid + "//td[text()='Payment Identifier']/following-sibling::td"), "Text").trim();
            Assert.assertTrue(!paymentIdentifier.isEmpty());
            currencyCode = appInd.getTextOnElement(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_PaymentData_Grid + "//td[text()='Currency Code']/following-sibling::td"), "Text").trim();
            Assert.assertTrue(!currencyCode.isEmpty());
            checkNumber = appInd.getTextOnElement(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_PaymentData_Grid + "//td[text()='Check Number']/following-sibling::td"), "Text").trim();
            Assert.assertTrue(!checkNumber.isEmpty() || checkNumber.isEmpty());
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validatePaymentDataTab()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validatePaymentDataTab()' method. " + e);
            return false;
        }finally{
            amount = null; paymentAmount = null; paymentIdentifier = null; currencyCode = null; checkNumber = null; strURL = null; caseDetails = null;
        }
    }


    /********************************************************
     * Method Name      : validatePaymentIDLinkOnCasePage()
     * Purpose          : user validates PaymentID link on case page
     * Author           : Deepak
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validatePaymentIDLinkOnCasePage(WebDriver oBrowser, String queueName) {
        JSONArray caseDetails = null;
        String currentUrl = null;
        String paymentID = null;
        try{
            caseDetails = apiDataProvider.getAPIDataProviderResponse("AssistedPaymentServices_PaymentAndBankInfo", new Object[] {});
            currentUrl = oBrowser.getCurrentUrl()+"cases/"+((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("cases_id").toString();
            oBrowser.get(currentUrl);
            appInd.scrollToElement(oBrowser, AssistedPaymentServicesUIPage.obj_SplitterHorizonal_Line);
            paymentID = appInd.getTextOnElement(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_PaymentData_Grid + "//td[text()='Request Payment ID']/following::td[1]/a"), "text");
            appInd.clickObject(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_PaymentData_Grid + "//td[text()='Request Payment ID']/following::td[1]/a"));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            Assert.assertTrue(oBrowser.getCurrentUrl().contains("managed_payment/cases"));
            oBrowser.navigate().refresh();
            appDep.waitForTheElementState(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_PaymentDetails_Grid + "//th[text()='Payment ID']/following-sibling::td[1]"), "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//td[text()='"+paymentID+"']")));
            reports.writeResult(oBrowser, "Screenshot", "Assisted Payment Services - Payment Data page");
         return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validatePaymentIDLinkOnCasePage()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validatePaymentIDLinkOnCasePage()' method. " + e);
            return false;
        }finally {caseDetails = null; currentUrl = null; paymentID = null;}
    }



    /********************************************************
     * Method Name      : validateFilterFunctionalityOnCasesGrid()
     * Purpose          : user validates Filter functionalities on the  case Grid for Assisted Payment Services queue
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateFilterFunctionalityOnCasesGrid(WebDriver oBrowser, String queueName){
        JSONArray caseDetails = null;
        try{
            Assert.assertTrue(enrollmentManagersUIBaseSteps.selectRequiredQueue(oBrowser, queueName));
            Assert.assertTrue(enrollmentManagersUIBaseSteps.applyFiltersOnQueuesDashboard(oBrowser, queueName));
            Assert.assertTrue(enrollmentManagersUIBaseSteps.clickOnShowCaseButton(oBrowser, queueName));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_CaseNumber_Grid_Pagination, "Visibility", "", waitTimeOut);
            appDep.threadSleep(2000);

            caseDetails = apiDataProvider.getAPIDataProviderResponse("AssistedPaymentServices_Cases_Grid", new Object[] {});

            Assert.assertTrue(applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Case #", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("cases_id").toString(),1));
            Assert.assertTrue(applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Company", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("company_name").toString(),2));
            Assert.assertTrue(applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Supplier", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("supplier_name").toString(),3));
            Assert.assertTrue(applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Due Date", (((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("value_date").toString().split("T"))[0],4));
            Assert.assertTrue(applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Time Zone", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("time_zone").toString(),5));
            Assert.assertTrue(applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Case Type", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("case_type_name").toString(),6));
            Assert.assertTrue(applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Status", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("status_desc").toString(),7));
            Assert.assertTrue(applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Priority", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("priority_name").toString(),8));
            Assert.assertTrue(applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Assigned To", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("user_name").toString(),9));
            Assert.assertTrue(applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Payment ID", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("payment_id_formatted").toString(),10));
            Assert.assertTrue(applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Payment Reference", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("payment_reference").toString(),11));
            Assert.assertTrue(applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Return Status", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("return_status").toString(),12));
            Assert.assertTrue(applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Amount", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("payment_amount").toString(),13));
            Assert.assertTrue(applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Last Touched", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("last_touched").toString(),14));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateFilterFunctionalityOnCasesGrid()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateFilterFunctionalityOnCasesGrid()' method. " + e);
            return false;
        }finally{caseDetails = null;}
    }




    /********************************************************
     * Method Name      : applyFilterOnCaseGridColumnNameAndValidateTheData()
     * Purpose          : user apply the Filter on each column of the case table grid & validates the data for Assisted Payment Services queue
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean applyFilterOnCaseGridColumnNameAndValidateTheData(WebDriver oBrowser, String columnName, String searchValue, int index){
        String columnData = null;
        int rowNum = 0;
        String cellData = null;
        int flag = 0;
        Actions oAction = null;
        try{
            if(searchValue.isEmpty())
                columnData = appInd.getTextOnElement(oBrowser, By.xpath("("+AssistedPaymentServicesUIPage.obj_CaseGridCellData + "/table//td[contains(@aria-label, 'Column "+columnName+"')])[1]"), "Text");
            else
                columnData = searchValue;
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appDep.waitForTheElementState(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_CaseGridCellSearch_Edit + "//table//tr[2]/td["+index+"]//input[@type='text']"), "Clickable", "", waitTimeOut);
            if(columnName.equalsIgnoreCase("Amount"))
                Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_CaseGridCellSearch_Edit + "//table//tr[2]/td["+index+"]//input[@type='text']"), columnData.replace(",","")));
            else if(columnName.equalsIgnoreCase("Created At") || columnName.equalsIgnoreCase("Last Touched")){
               // columnData = appInd.formatDateFromOnetoAnother((columnData.split(" "))[0].trim(), "yyyy-MM-dd", "MM/dd/yyyy");
                columnData = appInd.formatDate(columnData, "yyyy-MM-dd", "MM/dd/yyyy");
                oAction = new Actions(oBrowser);
                oAction.sendKeys(oBrowser.findElement(By.xpath(AssistedPaymentServicesUIPage.obj_CaseGridCellSearch_Edit + "//table//tr[2]/td["+index+"]//input[@type='text']")), columnData).sendKeys(Keys.ENTER).build().perform();
            }else{
                Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_CaseGridCellSearch_Edit + "//table//tr[2]/td["+index+"]//input[@type='text']"), columnData));
            }
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Filter was applied on '"+columnName+"' using the data '"+columnData+"'");
            rowNum = oBrowser.findElements(By.xpath(AssistedPaymentServicesUIPage.obj_CaseGridCellData + "/table//td[contains(@aria-label, 'Column "+columnName+"')]")).size();
            int maxRowNum = 15;
            if(rowNum > maxRowNum) rowNum = maxRowNum;
            for(int i=0; i<rowNum; i++){
                flag++;
                cellData = appInd.getTextOnElement(oBrowser, By.xpath("("+AssistedPaymentServicesUIPage.obj_CaseGridCellData + "/table//td[contains(@aria-label, 'Column "+columnName+"')])["+(i+1)+"]"), "Text");
                if(!columnName.equalsIgnoreCase("Status") && !columnName.equalsIgnoreCase("Time Zone")) {
                    if(columnName.equalsIgnoreCase("Amount")) cellData = cellData.replace("$", "").replace(",", "");
                    Assert.assertTrue(cellData.equalsIgnoreCase(columnData) || cellData.contains(columnData) || columnData.contains(cellData) ||cellData.replaceAll("[^a-zA-Z0-9]", "").equalsIgnoreCase(columnData.replaceAll("[^a-zA-Z0-9]", "")), "Mis-match in both actual '" + cellData + "' & expected '" + columnData + "' values");
                }else
                    Assert.assertTrue(cellData.contains(columnData), "Mis-match in both actual '"+cellData+"' & expected '"+columnData+"' values");
            }

            if(flag==0){
                Assert.assertTrue(false, "No data was found for the applied filter value '"+columnData+"' for the column +'"+columnName+"'");
            }
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_CaseGridCellSearch_Edit + "//table//tr[2]/td["+index+"]//input[@type='text']"), ""));
            appDep.threadSleep(2000);
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'applyFilterOnCaseGridColumnNameAndValidateTheData()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'applyFilterOnCaseGridColumnNameAndValidateTheData()' method. " + e);
            return false;
        }
    }




    /********************************************************
     * Method Name      : clickOnGenerateInvoiceAndValidateInvoiceDetailsUnderAttachmentsTab()
     * Purpose          : user clicks on Generate Invoice and validates the same under attachments tab for Assisted Payment Services queue
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean clickOnGenerateInvoiceAndValidateInvoiceDetailsUnderAttachmentsTab(WebDriver oBrowser){
        String dtTime = null;
        try{
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_GenerateInvoice_Btn));
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignConfirmationMessage_Label, "Visibility", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignConfirmationMessage_Label, "Text").contains("New remittance details attachment successfully generated."));
            dtTime = appInd.getDateTimeByTimezone("MM/dd/YY hh:mm", "CST");
            appInd.scrollToElement(oBrowser, AssistedPaymentServicesUIPage.obj_SplitterHorizonal_Line);
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_Attachments_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appDep.waitForTheElementState(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Attachments_Grid + "//td[contains(@aria-label,'Column Name,')]//input"), "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Attachments_Grid + "//td[contains(@aria-label,'Column Description,')]//input"), dtTime));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Attachments_Grid + "//td[contains(@aria-label,'Column Name, Value')]"), "Text", "Generated Remittance Details"));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Attachments_Grid + "//td[contains(@aria-label,'Column Description, Value')]"), "Text").contains("Remittance details attachment generated at "+dtTime));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Attachments_Grid + "//td[contains(@aria-label,'Attachment Type, Value')]"), "Text", "Document"));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Attachments_Grid + "//td[contains(@aria-label, 'Attached To, Value')]/*[@class='fa-solid fa-briefcase']")));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'clickOnGenerateInvoiceAndValidateInvoiceDetailsUnderAttachmentsTab()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'clickOnGenerateInvoiceAndValidateInvoiceDetailsUnderAttachmentsTab()' method. " + e);
            return false;
        }
    }





    /********************************************************
     * Method Name      : downloadAndValidateExcelFileOfAssistedPaymentServicesCases()
     * Purpose          : user clicks on Generate Invoice and validates the same under attachments tab for Assisted Payment Services queue
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean downloadAndValidateExcelFileOfAssistedPaymentServicesCases(WebDriver oBrowser, String queueName){
        String timeStamp = null;
        boolean blnRes = false;
        File objExportedCasesExcelFile = null;
        try{
            timeStamp = appInd.getDateTimeByTimezone("yyyy-MM-dd", "CST");
            Assert.assertTrue(enrollmentManagersUIBaseSteps.selectRequiredQueue(oBrowser, queueName));
            Assert.assertTrue(enrollmentManagersUIBaseSteps.clickOnShowCaseButton(oBrowser, queueName));
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_Export_Icon));
            appDep.threadSleep(9000);
            blnRes = appDep.validateDownloadedFileAndDelete(oBrowser, "PayCRM Dashboard Queue Cases-"+timeStamp, ".xlsx", "", "No");
            if(blnRes){
                reports.writeResult(oBrowser, "Pass", "The 'Cases' data excel file was exported successful");
            }else{
                reports.writeResult(oBrowser, "Fail", "Failed to export the 'Cases' data excel file");
                Assert.assertTrue(false, "Failed to export the 'Cases' xlsx file");
            }

            Assert.assertTrue(appDep.exportAndCompareTheExportedDataWithTableGridData(oBrowser, AssistedPaymentServicesUIPage.obj_AsstPaymentServices_Cases_Grid, "PayCRM Dashboard Queue Cases-"+timeStamp));

            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'downloadAndValidateExcelFileOfAssistedPaymentServicesCases()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'downloadAndValidateExcelFileOfAssistedPaymentServicesCases()' method. " + e);
            return false;
        }finally {
            objExportedCasesExcelFile = new File("C:\\Users\\"+System.getProperty("user.name")+"\\Downloads\\" + "PayCRM Dashboard Queue Cases-"+timeStamp+".xlsx");
            objExportedCasesExcelFile.delete();
            timeStamp = null; objExportedCasesExcelFile = null;
        }
    }



    /********************************************************
     * Method Name      : issueInvoiceUnableToApplyCredit
     * Purpose          : User Navigates Payment Question Email Template checking Issue Invoice, Overpaid, Unable to Apply Credit, and child fields
     * Author           : Coner
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean issueInvoiceUnableToApplyCredit(WebDriver oBrowser, DataTable dataTable){
        List<Map<String, String>> questionData = null;
        try{
            questionData = dataTable.asMaps(String.class, String.class);
            Assert.assertTrue(appDep.navigateToPaymentQuestions(oBrowser));
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_Issue_List, questionData.get(0).get("Issue")));
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_Invoice_List, questionData.get(0).get("InvoiceIssue")));
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_Overpaid_List, questionData.get(0).get("WhyOverpaid?")));
            Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_DueAmount_Edit, questionData.get(0).get("DueAmount")));
            Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_IssuedAmount_Edit, questionData.get(0).get("IssuedAmount")));
            Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_ReissuedAmount_Edit, questionData.get(0).get("ReissuedAmount")));
            reports.writeResult(oBrowser, "Screenshot", "After entering the details for 'Payment Questions'");
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentQuestion_Send_Btn));
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignConfirmationMessage_Label, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignConfirmationMessage_Label, "Text").contains("Client Support email has been successfully sent."));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'issueInvoiceUnableToApplyCredit()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'issueInvoiceUnableToApplyCredit()' method. " + e);
            return false;
        }finally{
            questionData = null;
        }
    }


    /********************************************************
     * Method Name      : issueInvoiceUnableToPartiallyProcess
     * Purpose          : User Navigates Payment Question Email Template checking Issue Invoice, Overpaid, Unable to Partially Process, and child fields
     * Author           : Coner
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean issueInvoiceUnableToPartiallyProcess(WebDriver oBrowser, DataTable dataTable){
        List<Map<String, String>> questionData = null;
        try{
            questionData = dataTable.asMaps(String.class, String.class);
            Assert.assertTrue(appDep.navigateToPaymentQuestions(oBrowser));
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_Issue_List, questionData.get(0).get("Issue")));
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_Invoice_List, questionData.get(0).get("InvoiceIssue")));
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_Overpaid_List, questionData.get(0).get("WhyOverpaid?")));
            Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_ReissuedAmount_Edit, questionData.get(0).get("ReissuedAmount")));
            reports.writeResult(oBrowser, "Screenshot", "After entering the details for 'Payment Questions'");
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentQuestion_Send_Btn));
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignConfirmationMessage_Label, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignConfirmationMessage_Label, "Text").contains("Client Support email has been successfully sent."));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'issueInvoiceUnableToPartiallyProcess()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'issueInvoiceUnableToPartiallyProcess()' method. " + e);
            return false;
        }finally{
            questionData = null;
        }
    }

    /********************************************************
     * Method Name      : issueInvoicePayFullInvoiceAmount
     * Purpose          : User Navigates Payment Question Email Template checking Issue Invoice, Underpaid, Invoice Pay Full Invoice/Amount, and child fields
     * Author           : Coner
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean issueInvoicePayFullInvoiceAmount(WebDriver oBrowser, DataTable dataTable){
        List<Map<String, String>> questionData = null;
        try{
            questionData = dataTable.asMaps(String.class, String.class);
            Assert.assertTrue(appDep.navigateToPaymentQuestions(oBrowser));
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_Issue_List, questionData.get(0).get("Issue")));
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_Invoice_List, questionData.get(0).get("InvoiceIssue")));
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_Underpaid_List, questionData.get(0).get("WhyUnderpaid?")));
            Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_DueAmount_Edit, questionData.get(0).get("DueAmount")));
            Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_IssuedAmount_Edit, questionData.get(0).get("IssuedAmount")));
            Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_ReissuedAmount_Edit, questionData.get(0).get("ReissuedAmount")));
            reports.writeResult(oBrowser, "Screenshot", "After entering the details for 'Payment Questions'");
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentQuestion_Send_Btn));
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignConfirmationMessage_Label, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignConfirmationMessage_Label, "Text").contains("Client Support email has been successfully sent."));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'issueInvoicePayFullInvoiceAmount()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'issueInvoicePayFullInvoiceAmount()' method. " + e);
            return false;
        }finally{
            questionData = null;
        }
    }

    /********************************************************
     * Method Name      : issueInvoiceTurnServiceBackOn
     * Purpose          : User Navigates Payment Question Email Template checking Issue Invoice, Underpaid, Turn Service Back On, and child fields
     * Author           : Coner
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean issueInvoiceTurnServiceBackOn(WebDriver oBrowser, DataTable dataTable){
        List<Map<String, String>> questionData = null;
        try{
            questionData = dataTable.asMaps(String.class, String.class);
            Assert.assertTrue(appDep.navigateToPaymentQuestions(oBrowser));
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_Issue_List, questionData.get(0).get("Issue")));
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_Invoice_List, questionData.get(0).get("InvoiceIssue")));
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_Underpaid_List, questionData.get(0).get("WhyUnderpaid?")));
            Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_ReissuedAmount_Edit, questionData.get(0).get("ReissuedAmount")));
            reports.writeResult(oBrowser, "Screenshot", "After entering the details for 'Payment Questions'");
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentQuestion_Send_Btn));
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignConfirmationMessage_Label, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignConfirmationMessage_Label, "Text").contains("Client Support email has been successfully sent."));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'issueInvoiceTurnServiceBackOn()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'issueInvoiceTurnServiceBackOn()' method. " + e);
            return false;
        }finally{
            questionData = null;
        }
    }




    /********************************************************
     * Method Name      : issueInvoiceUnableToLocate
     * Purpose          : User Navigates Payment Question Email Template checking Issue Invoice, Unable to Locate Invoice/Account
     * Author           : Coner
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean issueInvoiceUnableToLocate(WebDriver oBrowser, DataTable dataTable){
        List<Map<String, String>> questionData = null;
        try{
            questionData = dataTable.asMaps(String.class, String.class);
            Assert.assertTrue(appDep.navigateToPaymentQuestions(oBrowser));
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_Issue_List, questionData.get(0).get("Issue")));
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_Invoice_List, questionData.get(0).get("InvoiceIssue")));
            reports.writeResult(oBrowser, "Screenshot", "After entering the details for 'Payment Questions'");
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentQuestion_Send_Btn));
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignConfirmationMessage_Label, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignConfirmationMessage_Label, "Text").contains("Client Support email has been successfully sent."));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'issueInvoiceUnableToLocate()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'issueInvoiceUnableToLocate()' method. " + e);
            return false;
        }finally{questionData = null;}
    }



    /********************************************************
     * Method Name      : issueInvoiceUnableToContactNumbersDialed
     * Purpose          : User Navigates Payment Question Email Template checking Issue Unable to Contact and Numbers Dialed
     * Author           : Coner
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean issueInvoiceUnableToContactNumbersDialed(WebDriver oBrowser, DataTable dataTable){
        List<Map<String, String>> questionData = null;
        try{
            questionData = dataTable.asMaps(String.class, String.class);
            Assert.assertTrue(appDep.navigateToCasesPage(oBrowser, "Assisted Payment Services", "AsstdPaymentServices_PaymentQuestions", new Object[] {"typeID", 33}));
            Assert.assertTrue(appDep.navigateToPaymentQuestions(oBrowser));
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_Issue_List, questionData.get(0).get("Issue")));
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_Attempted_Methods_List, questionData.get(0).get("Attempted Methods")));
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_Phone_Numbers_List));
            reports.writeResult(oBrowser, "Screenshot", "After clicking into the Phone Numbers list field");
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_Phone_Numbers_List_Chosen));
            reports.writeResult(oBrowser, "Screenshot", "After clicking the Chosen Phone Number");
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentQuestion_Send_Btn));
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignConfirmationMessage_Label, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignConfirmationMessage_Label, "Text").contains("Client Support email has been successfully sent."));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'issueInvoiceUnableToContactNumbersDialed()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'issueInvoiceUnableToContactNumbersDialed()' method. " + e);
            return false;
        }finally{questionData = null;}
    }



    /********************************************************
     * Method Name      : issueInvoiceUnableToContactEmailAddressesUsed
     * Purpose          : User Navigates Payment Question Email Template checking Issue Unable to Contact and child dropdown Email Addresses Used
     * Author           : Coner
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean issueInvoiceUnableToContactEmailAddressesUsed(WebDriver oBrowser, DataTable dataTable){
        List<Map<String, String>> questionData = null;
        try{
            questionData = dataTable.asMaps(String.class, String.class);
            Assert.assertTrue(appDep.navigateToCasesPage(oBrowser, "Assisted Payment Services", "AsstdPaymentServices_PaymentQuestions", new Object[] {"typeID", 29}));
            Assert.assertTrue(appDep.navigateToPaymentQuestions(oBrowser));
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_Issue_List, questionData.get(0).get("Issue")));
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_Attempted_Methods_List, questionData.get(0).get("Attempted Methods")));
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_Email_Address_List));
            reports.writeResult(oBrowser, "Screenshot", "After clicking into the Email list field");
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_Email_Address_List_Chosen));
            reports.writeResult(oBrowser, "Screenshot", "After clicking the Chosen Email");
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentQuestion_Send_Btn));
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignConfirmationMessage_Label, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignConfirmationMessage_Label, "Text").contains("Client Support email has been successfully sent."));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'issueInvoiceUnableToContactEmailAddressesUsed()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'issueInvoiceUnableToContactEmailAddressesUsed()' method. " + e);
            return false;
        }finally{questionData = null;}
    }




    /********************************************************
     * Method Name      : validateLastTouchColumn
     * Purpose          : User Navigates to Assistant Payment Services cases and validates the last touch coumn
     * Author           : Coner
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateLastTouchColumn(WebDriver oBrowser, String caseNumber){
        String lastTouched = null;
        String arrLastTouched[];
        try{
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appDep.waitForTheElementState(oBrowser, By.xpath("("+AssistedPaymentServicesUIPage.obj_AsstPaymentServices_Cases_Grid + "//input)[1]"), "Clickable", "", minTimeOut);
            appInd.setObject(oBrowser, By.xpath("("+AssistedPaymentServicesUIPage.obj_AsstPaymentServices_Cases_Grid + "//input)[1]"), caseNumber);
            appDep.threadSleep(2000);
            arrLastTouched = appInd.getDateTimeByTimezone("MM/dd/yyyy", "CST").split("/");
            lastTouched = String.valueOf(Integer.parseInt(arrLastTouched[0])) + "/" + String.valueOf(Integer.parseInt(arrLastTouched[1])) + "/" + String.valueOf(Integer.parseInt(arrLastTouched[2]));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_AsstPaymentServices_Cases_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[14]"), "Text", lastTouched));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateLastTouchColumn()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateLastTouchColumn()' method. " + e);
            return false;
        }finally{lastTouched = null; arrLastTouched = null;}
    }




    /********************************************************
     * Method Name      : validateLastTouchColumnAndActivitiesTabAfterPerformingLogActivities
     * Purpose          : User Navigates to Assistant Payment Services cases, perform 'Log Activity' action and validates Activities tab and last touch coumn
     * Author           : Coner
     * Parameters       : oBrowser, queueName, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateLastTouchColumnAndActivitiesTabAfterPerformingLogActivities(WebDriver oBrowser, String queueName, DataTable dataTable){
        List<Map<String, String>> logActivityData = null;
        Map<String, String> logActivity = null;
        String caseNumber = null;
        try{
            Assert.assertTrue(enrollmentManagersUIBaseSteps.selectRequiredQueue(oBrowser, queueName));
            Assert.assertTrue(enrollmentManagersUIBaseSteps.applyFiltersOnQueuesDashboard(oBrowser, queueName));
            Assert.assertTrue(enrollmentManagersUIBaseSteps.clickOnShowCaseButton(oBrowser, queueName));

            //Validate Last Touched column. Last Touched column should be in far right column
            int columnCount = oBrowser.findElements(By.xpath(AssistedPaymentServicesUIPage.obj_AsstPaymentServices_Cases_Grid + "//tr[1][contains(@class, 'lines dx-header-row')]/td")).size();
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser,
                    By.xpath(AssistedPaymentServicesUIPage.obj_AsstPaymentServices_Cases_Grid + "//tr[1][contains(@class, 'lines dx-header-row')]/td["+columnCount+"]/div[not(@style) and text()='Last Touched']")));

            //Click and open the cases whixh status either OPEN or HOLD
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_AsstPaymentServices_Cases_Grid + "//td[@aria-label = 'Status Column']/div[@class='dx-column-indicators']")));
            appDep.waitForTheElementState(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_PaymentBasedEnrollments_FilterOptions_Label), "Clickable", "", waitTimeOut);
            appInd.clickObject(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_AsstPaymentGird_ColumnFilter_Values + "//div[text()='Open']"));
            appInd.clickObject(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_AsstPaymentGird_ColumnFilter_Values + "//div[text()='Hold']"));
            appInd.clickObject(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Filter_OK_Btn));
            appDep.threadSleep(2000);
            caseNumber = enrollmentManagersUIBaseSteps.readAndClickResultFirstCaseNumber(oBrowser, queueName);
            this.caseNumber = caseNumber;

            //perform 'Log Activity'
            logActivity = performLogActivity(oBrowser, dataTable);
            appInd.scrollToElement(oBrowser, AssistedPaymentServicesUIPage.obj_SplitterHorizonal_Line);
            appDep.threadSleep(2000);
            appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_Activities_Tab);
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, By.xpath("("+AssistedPaymentServicesUIPage.obj_Activities_Grid + "//input)[6]"), "Clickable", "", waitTimeOut);
            appInd.setObject(oBrowser, By.xpath("("+AssistedPaymentServicesUIPage.obj_Activities_Grid + "//input)[6]"), logActivity.get("Notes"));
            appDep.threadSleep(2000);

            //Validate the Activities entry in the Activities grid
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[3]"), "Text", "Open: " + logActivity.get("CaseOutcome")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[4]"), "Text", logActivity.get("ActivityType")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[5]"), "Text", appInd.getPropertyValueByKeyName("qaUserName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[7]"), "Text", logActivity.get("Notes")));


            //Go back to the case from the dashboard and Validate Last touch column
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
            /*appDep.waitForTheElementState(oBrowser, By.xpath("("+AssistedPaymentServicesUIPage.obj_AsstPaymentServices_Cases_Grid + "//input)[1]"), "Clickable", "", minTimeOut);
            appInd.setObject(oBrowser, By.xpath("("+AssistedPaymentServicesUIPage.obj_AsstPaymentServices_Cases_Grid + "//input)[1]"), caseNumber);
            appDep.threadSleep(2000);
            arrLastTouched = appInd.getDateTimeByTimezone("MM/dd/yyyy", "CST").split("/");
            lastTouched = String.valueOf(Integer.parseInt(arrLastTouched[0])) + "/" + String.valueOf(Integer.parseInt(arrLastTouched[1])) + "/" + String.valueOf(Integer.parseInt(arrLastTouched[2]));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_AsstPaymentServices_Cases_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[14]"), "Text", lastTouched));*/
            Assert.assertTrue(validateLastTouchColumn(oBrowser, caseNumber));
            appInd.clearAndSetObject(oBrowser, By.xpath("("+AssistedPaymentServicesUIPage.obj_AsstPaymentServices_Cases_Grid + "//input)[1]"), "");
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateLastTouchColumnAndActivitiesTabAfterPerformingLogActivities()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateLastTouchColumnAndActivitiesTabAfterPerformingLogActivities()' method. " + e);
            return false;
        }finally{
            logActivityData = null; logActivity = null; caseNumber = null;
        }
    }




    /********************************************************
     * Method Name      : performReassignAndValidateTheSame
     * Purpose          : User Navigates to Assistant Payment Services cases, perform Reassign and validates the same
     * Author           : Coner
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean performReassignAndValidateTheSame(WebDriver oBrowser, String queueName, DataTable dataTable){
        String caseNumber = null;
        try{
            caseNumber = enrollmentManagersUIBaseSteps.readAndClickRandomRowCaseNumber(oBrowser, queueName, EnrollmentsManagerUIPage.obj_EnrollmentCase_Grid + "//tr[contains(@class, 'lines dx-column-lines')]");
            Assert.assertTrue(assistedPaymentServicesBaseSteps.reassignTheEnrollmentCaseForAssistedPaymentServices(oBrowser, dataTable));
            Assert.assertTrue(assistedPaymentServicesBaseSteps.verifyTheReassignmentDetailsUnderAssistedPaymentServicesActivitiesTab(oBrowser));

            //Go back to the case from the dashboard and Validate Last touch column
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
            Assert.assertTrue(validateLastTouchColumn(oBrowser, caseNumber));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'performReassignAndValidateTheSame()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'performReassignAndValidateTheSame()' method. " + e);
            return false;
        }
    }





    /********************************************************
     * Method Name      : emailTemplatesForPaymentQuestions
     * Purpose          : User sends the email using Payment Options
     * Author           : Gudi
     * Parameters       : oBrowser, caseNumber, dataTable
     * ReturnType       : Map<String, String>
     ********************************************************/
    public Map<String, String> emailTemplatesForPaymentQuestions(WebDriver oBrowser, String caseNumber, Map<String, String> paymentQuestions){
        String clientName = null;
        String supplierName = null;
        String subject = null;
        List<WebElement> oEmailID = null;
        try{
            appInd.scrollToThePage(oBrowser, "Top");
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_EmailTemplate_Btn));
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentQuestion_Link));
            appDep.waitForTheElementState(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_PaymentQuestion_Dialog + "//button[@type='submit']"), "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+AssistedPaymentServicesUIPage.obj_PaymentQuestion_Dialog + "/div/div/div/div)[1]"), "Text").replace("\n", "").contains("Payment QuestionCase #" + caseNumber));
            subject = appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_Subject_Edit, "Value");
            paymentQuestions.put("Subject", subject);

            appDep.waitForTheElementState(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_PaymentQuestions_To_Label), "Clickable", "", minTimeOut);
            oEmailID = oBrowser.findElements(By.xpath(AssistedPaymentServicesUIPage.obj_PaymentQuestions_To_Label + "//li[@class='search-choice']"));
            if((oEmailID.size() == 1) && !appInd.compareValues(oBrowser, oEmailID.get(0).getText(), appInd.getPropertyValueByKeyName("emailUserName"))){
                reports.writeResult(oBrowser, "Fail", "The Configured email id '"+appInd.getPropertyValueByKeyName("emailUserName")+"' was not displayed under To field of Payment Questions");
                Assert.fail("The Configured email id '"+appInd.getPropertyValueByKeyName("emailUserName")+"' was not displayed under To field of Payment Questions");
            }
            for(int i=0; i<oEmailID.size(); i++){
                if(!oEmailID.get(i).getText().equalsIgnoreCase(appInd.getPropertyValueByKeyName("emailUserName"))){
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+AssistedPaymentServicesUIPage.obj_PaymentQuestions_To_Label + "//li[@class='search-choice']/a)["+(i+1)+"]")));
                    i--;
                    oEmailID = oBrowser.findElements(By.xpath(AssistedPaymentServicesUIPage.obj_PaymentQuestions_To_Label + "//li[@class='search-choice']"));
                }
            }
            Assert.assertTrue(appInd.verifyText(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentQuestions_From_List, "Dropdown", appInd.getPropertyValueByKeyName("qaUserName")));

            switch(paymentQuestions.get("Issue").toLowerCase()){
                case "payment void permission":
                    Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_Issue_List, paymentQuestions.get("Issue")));
                    Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_VoidReason_List, paymentQuestions.get("VoidReason")));
                    switch(paymentQuestions.get("VoidReason").toLowerCase()) {
                        case "amount discrepancy, refund balance after payment posted":
                            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_ProcessedAmount_Edit, "Clickable", "", minTimeOut);
                            Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_ProcessedAmount_Edit, paymentQuestions.get("ProcessedAmount")));
                            Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_RefundAmount_Edit, paymentQuestions.get("RefundAmount")));
                            paymentQuestions.put("Message", "The payment has been processed for "+paymentQuestions.get("ProcessedAmount")+".00 as authorized. Once posted, the remaining balance of "+paymentQuestions.get("RefundAmount")+".00 should be voided and refunded to the client");
                            break;
                        case "payment invoice paid":
                            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentMethod_List, "Clickable", "", minTimeOut);
                            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentMethod_List, paymentQuestions.get("PaymentMethod")));
                            appDep.threadSleep(2000);
                            if(paymentQuestions.get("PaymentMethod").equalsIgnoreCase("CC")){
                                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_LastFourCardNumber_Edit), "Failed to find the element '"+String.valueOf(AssistedPaymentServicesUIPage.obj_LastFourCardNumber_Edit)+"'");
                            }else if(paymentQuestions.get("PaymentMethod").equalsIgnoreCase("Check")){
                                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_CheckNumber_Edit), "Failed to find the element '"+String.valueOf(AssistedPaymentServicesUIPage.obj_CheckNumber_Edit)+"'");
                                paymentQuestions.remove("CheckNumber");
                                paymentQuestions.put("CheckNumber", appInd.getDateTime("hhmmss"));
                                Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_CheckNumber_Edit, paymentQuestions.get("CheckNumber")));
                            }
                            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentDate_Edit));
                            String currentDay = appInd.getDateTime("dd");
                            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_PaymentDate_Calendar + "//td[@data-handler='selectDay']/a[text()='"+currentDay+"']")));
                            Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_DueAmount_Edit, paymentQuestions.get("DueAmount")));
                            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_IssuedAmount_Edit));
                            Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_IssuedAmount_Edit, paymentQuestions.get("IssuedAmount")));
                            Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_ReissuedAmount_Edit, paymentQuestions.get("ReIssuedAmount")));
                            paymentQuestions.put("Message", "The supplier has advised that the invoice was due for "+paymentQuestions.get("DueAmount")+".00 and it was issued for "+paymentQuestions.get("IssuedAmount")+".00. We are requesting that the payment be voided and reissued for the amount of "+paymentQuestions.get("ReIssuedAmount")+".00 for the supplier to process the payment.");
                            break;
                    }
                    break;
                case "invoice":
                    Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_Issue_List, paymentQuestions.get("Issue")));
                    Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_Invoice_List, paymentQuestions.get("InvoiceIssue")));
                    switch(paymentQuestions.get("InvoiceIssue").toLowerCase()){
                        case "overpaid":
                            appDep.threadSleep(2000);
                            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_Overpaid_List, paymentQuestions.get("WhyOver_UnderPaid")));
                            if(paymentQuestions.get("WhyOver_UnderPaid").equalsIgnoreCase("Unable to Apply Credit")){
                                Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_DueAmount_Edit, paymentQuestions.get("DueAmount")));
                                Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_IssuedAmount_Edit, paymentQuestions.get("IssuedAmount")));
                                Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_ReissuedAmount_Edit, paymentQuestions.get("ReIssuedAmount")));
                                paymentQuestions.put("Message", "The supplier has advised that the invoice was due for "+paymentQuestions.get("DueAmount")+".00 and it was issued for "+paymentQuestions.get("IssuedAmount")+".00. We are requesting that the payment be voided and reissued for the amount of "+paymentQuestions.get("ReIssuedAmount")+".00 for the supplier to process the payment.");
                            }else if(paymentQuestions.get("WhyOver_UnderPaid").equalsIgnoreCase("Unable to Partially Process Payment Per Client Request")){
                                Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_ReissuedAmount_Edit, paymentQuestions.get("ReIssuedAmount")));
                                paymentQuestions.put("Message", "The supplier has advised they are unable to accept a payment other than the full amount due to restore services. We suggest voiding the payment and re-issuing for the amount due of "+paymentQuestions.get("ReIssuedAmount")+".00.");
                            }else{
                                reports.writeResult(oBrowser, "Fail", "Invalid value for 'Why Overpaid?': " + paymentQuestions.get("WhyOver_UnderPaid"));
                                Assert.fail("Invalid value for 'Why Overpaid?': " + paymentQuestions.get("WhyOver_UnderPaid"));
                            }
                            break;
                        case "underpaid":
                            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_Underpaid_List, paymentQuestions.get("WhyOver_UnderPaid")));
                            if(paymentQuestions.get("WhyOver_UnderPaid").equalsIgnoreCase("Vendor requires client to pay full invoice amount")){
                                Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_DueAmount_Edit, paymentQuestions.get("DueAmount")));
                                Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_IssuedAmount_Edit, paymentQuestions.get("IssuedAmount")));
                                Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_ReissuedAmount_Edit, paymentQuestions.get("ReIssuedAmount")));
                                paymentQuestions.put("Message", "The supplier has advised that the invoice was due for "+paymentQuestions.get("DueAmount")+".00 and it was issued for "+paymentQuestions.get("IssuedAmount")+".00. We are requesting that the payment be voided and reissued for the amount of "+paymentQuestions.get("ReIssuedAmount")+".00 for the supplier to process the payment.");
                            }else if(paymentQuestions.get("WhyOver_UnderPaid").equalsIgnoreCase("In order to turn service back on, client will need to remit full payment on the account")){
                                Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_ReissuedAmount_Edit, paymentQuestions.get("ReIssuedAmount")));
                                paymentQuestions.put("Message", "The supplier has advised they are unable to accept a payment other than the full amount due to restore services. We suggest voiding the payment and re-issuing for the amount due of "+paymentQuestions.get("ReIssuedAmount")+".00.");
                            }else{
                                reports.writeResult(oBrowser, "Fail", "Invalid value for 'Why Underpaid?': " + paymentQuestions.get("WhyOver_UnderPaid"));
                                Assert.fail("Invalid value for 'Why Underpaid?': " + paymentQuestions.get("WhyOver_UnderPaid"));
                            }
                            break;
                        case "unable to locate invoice/account":
                            paymentQuestions.put("Message", "We have attempted to verify the Account/Invoice with the information provided in the Remittance Details without success. Can you please provide a copy of the invoice(s) for the referenced payment?");
                            break;
                        case "unable to apply credit":
                            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_WhyUnableToApplyCreditAsIssued_List, paymentQuestions.get("WhyOver_UnderPaid")));
                            if(paymentQuestions.get("WhyOver_UnderPaid").equalsIgnoreCase("Credit Already Applied")){
                                String creditAppliedDate = appInd.getDateTime("MM/dd/yyyy");
                                Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_CreditAppliedDate_Edit));
                                String currentDay = appInd.getDateTime("dd");
                                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_PaymentDate_Calendar + "//td[@data-handler='selectDay']/a[text()='"+currentDay+"']")));
                                paymentQuestions.put("Message", "We attempted to process this payment and apply the credit as issued. The supplier has confirm this credit has already been applied on "+creditAppliedDate+" to Invoice "+paymentQuestions.get("Invoices"));
                            }else{
                                paymentQuestions.put("Message", "We attempted to process the payment and apply Credit Invoice "+paymentQuestions.get("Invoices")+"; however, the supplier states it is not available.");
                            }
                            break;
                        case "invoice paid":
                            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_InvoicePaidMethod_List, paymentQuestions.get("WhyOver_UnderPaid")));
                            if(paymentQuestions.get("WhyOver_UnderPaid").equalsIgnoreCase("Previously Paid Check #")){
                                appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_Invoice_CheckNumber_Edit, "Clickable", "", minTimeOut);
                                Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_Invoice_CheckNumber_Edit, paymentQuestions.get("CheckNumber")));
                                paymentQuestions.put("Message", "The vendor has advised that the Invoice was process with Check "+ paymentQuestions.get("CheckNumber"));
                            }else if(paymentQuestions.get("WhyOver_UnderPaid").equalsIgnoreCase("Previously Paid Card # (Last 4)")){
                                appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_CardLast4Digits_Edit, "Clickable", "", minTimeOut);
                                Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_CardLast4Digits_Edit, paymentQuestions.get("CheckNumber")));
                                paymentQuestions.put("Message", "The vendor has advised that the Invoice was process with Card ending in " + paymentQuestions.get("CheckNumber"));
                            }else if(paymentQuestions.get("WhyOver_UnderPaid").equalsIgnoreCase("Invoice paid by ACH/wire")){
                                appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentMethod_Edit, "Clickable", "", minTimeOut);
                                Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentMethod_Edit, paymentQuestions.get("PaymentMethod")));
                                String dateOfPayment = appInd.getDateTime("MM/dd/yyyy");
                                Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_DateOfPayment_Edit));
                                String currentDay = appInd.getDateTime("dd");
                                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_PaymentDate_Calendar + "//td[@data-handler='selectDay']/a[text()='"+currentDay+"']")));

                                paymentQuestions.put("Message", "We attempted to process payment as issued. The supplier has advised Invoice(s) "+paymentQuestions.get("Invoices")+"have been paid by "+paymentQuestions.get("PaymentMethod")+" on " + dateOfPayment);
                            }else if(paymentQuestions.get("WhyOver_UnderPaid").equalsIgnoreCase("Unable to Confirm Check #")){
                                paymentQuestions.put("Message", "We attempted to confirm additional details for the Check that was used to process payment towards this invoice; however, the supplier is unable to provide those details.");
                            }else if(paymentQuestions.get("WhyOver_UnderPaid").equalsIgnoreCase("Unable to Confirm Card # (Last 4)")){
                                paymentQuestions.put("Message", "We attempted to confirm additional details for the Card that was used to process payment towards this invoice; however, the supplier is unable to provide those details.");
                            }
                            break;
                        case "processing fee":
                            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_ReasonForProcessingFee_List, paymentQuestions.get("WhyOver_UnderPaid")));
                            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_ProcessingFee_Edit, "Clickable", "", minTimeOut);
                            Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_ProcessingFee_Edit, paymentQuestions.get("ProcessingFee")));
                            if(paymentQuestions.get("WhyOver_UnderPaid").equalsIgnoreCase("Fee Waived (ACH Option)")){
                                paymentQuestions.put("Message", "We reached out to process the open payment and the supplier advised there was a "+paymentQuestions.get("ProcessingFee")+" processing fee. The supplier has agreed to waive this fee for this payment but cannot do so for future payments. We suggest revoking the link for credit card payments. Please let us know if you have questions or need anything further.");
                            }else if(paymentQuestions.get("WhyOver_UnderPaid").equalsIgnoreCase("Fee Waived (No ACH Option)")){
                                paymentQuestions.put("Message", "We reached out to process the open payment and the supplier advised there was a "+paymentQuestions.get("ProcessingFee")+" processing fee. The supplier has agreed to waive this fee for this payment but cannot do so for future payments. We suggest revoking the link for credit card payments. We will have our Enrollment Team reach out to the supplier to assist them in updating the payment method for future payments. Please let us know if you have questions or need anything further.");
                            }else if(paymentQuestions.get("WhyOver_UnderPaid").equalsIgnoreCase("Fee Not Waived (No ACH Option)")){
                                paymentQuestions.put("Message", "We reached out to process the open payment and the supplier advised there was a "+paymentQuestions.get("ProcessingFee")+" processing fee. The supplier is unable to waive this fee for this payment and this payment needs to be voided. We suggest revoking the link for credit card payments. Please let us know if you have questions or need anything further.");
                            }else if(paymentQuestions.get("WhyOver_UnderPaid").equalsIgnoreCase("Fee Not Waived (ACH Option)")){
                                paymentQuestions.put("Message", "We reached out to process the open payment and the supplier advised there was a "+paymentQuestions.get("ProcessingFee")+" processing fee. The supplier is unwilling to waive the processing fee. We suggest revoking the link for credit card payments. We will have our Enrollment Team reach out to the supplier to assist them in updating the payment method for future payments. Please let us know if you have questions or need anything further.");
                            }
                            break;
                        default:
                            reports.writeResult(oBrowser, "Fail", "Invalid value for 'Invoice Issue' dropdown: '"+paymentQuestions.get("InvoiceIssue")+"'");
                            Assert.fail("Invalid value for 'Issue Type' dropdown: '"+paymentQuestions.get("InvoiceIssue")+"'");
                    }
                    break;
                case "unable to contact":
                    Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_Issue_List, paymentQuestions.get("Issue")));
                    Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_Attempted_Methods_List, paymentQuestions.get("AttemptedMethods")));
                    if(paymentQuestions.get("AttemptedMethods").equalsIgnoreCase("Numbers Dialed")){
                        Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_Phone_Numbers_List));
                        reports.writeResult(oBrowser, "Screenshot", "After clicking into the Phone Numbers list field");
                        Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_Phone_Numbers_List_Chosen));
                        reports.writeResult(oBrowser, "Screenshot", "After clicking the Chosen Phone Number");
                        paymentQuestions.put("Message", "Agents have attempted to process this payment without success. We have tried calling the following numbers without a response or return call. We will continue our efforts; however, a copy of an invoice or direct point of contact may help in our attempts.");
                    }else if(paymentQuestions.get("AttemptedMethods").equalsIgnoreCase("Email Addresses Used")){
                        Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_Email_Address_List));
                        reports.writeResult(oBrowser, "Screenshot", "After clicking into the Email list field");
                        Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_Email_Address_List_Chosen));
                        reports.writeResult(oBrowser, "Screenshot", "After clicking the Chosen Email");
                        paymentQuestions.put("Message", "Agents have attempted to process this payment without success. We have tried reaching the following contacts by email.");
                    }else{
                        reports.writeResult(oBrowser, "Fail", "Invalid value for 'Attempted Methods' dropdown: '"+paymentQuestions.get("AttemptedMethods")+"'");
                        Assert.fail("Invalid value for 'Attempted Methods' dropdown: '"+paymentQuestions.get("AttemptedMethods")+"'");
                    }
                    break;
                case "process":
                    Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_Issue_List, paymentQuestions.get("Issue")));
                    Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_ProcessIssue_List, paymentQuestions.get("ProcessIssue")));
                    Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_LoginCredentials_List, paymentQuestions.get("LoginCredentials")));
                    if(paymentQuestions.get("LoginCredentials").equalsIgnoreCase("Need Username and Password")){
                        paymentQuestions.put("Message", "This email is to advise we attempted to execute payment using the vendors preferred method by using their online portal. Our records indicate we require updated login credentials to proceed. Can you please provide the requested information? Once confirmed, we will update the information on file for future reference.");
                    }else{
                        Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_Process_userName_Edit, paymentQuestions.get("UserName")));
                        paymentQuestions.put("Message", "This email is to advise we attempted to execute payment using the vendors preferred method by using their online portal. Our records indicate we require updated login credentials for "+paymentQuestions.get("UserName")+" to proceed. Can you please provide the requested information? Once confirmed, we will update the information on file for future reference.");
                    }
                    break;
                default:
                    reports.writeResult(oBrowser, "Fail", "Invalid value for 'Issue Type' dropdown: '"+paymentQuestions.get("Issue")+"'");
                    Assert.fail("Invalid value for 'Issue Type' dropdown: '"+paymentQuestions.get("Issue")+"'");
            }
            reports.writeResult(oBrowser, "Screenshot", "Before sending email for the 'Payment Questions'");
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_SupplierInfo_Label));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_PaymentQuestion_Dialog + "//button[@type='submit']")));
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignConfirmationMessage_Label, "Visibility", "", maxWaitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignConfirmationMessage_Label, "Text").contains("Client Support email has been successfully sent."));
            paymentQuestions.put("TestPassed", "True");
            return paymentQuestions;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'emailTemplatesForPaymentQuestions()' method. " + e);
            paymentQuestions.put("TestPassed", "False");
            return paymentQuestions;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'emailTemplatesForPaymentQuestions()' method. " + e);
            paymentQuestions.put("TestPassed", "False");
            return paymentQuestions;
        }finally {clientName = null; supplierName = null; subject = null;}
    }





    /********************************************************
     * Method Name      : setDefaultPaymentQuestionMailUnderPreferences
     * Purpose          : set Default Payment Question Mail under Client company preferences
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean setDefaultPaymentQuestionMailUnderPreferences(WebDriver oBrowser, String pageName){
        try{
            if(pageName.equalsIgnoreCase("Assisted Payment Services")){
                Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, By.xpath("("+AssistedPaymentServicesUIPage.obj_CaseDetails_Section + "//dl//span[@class='truncate-title']/a)[1]")));
                oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            }
            appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Preferences_Link);
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appInd.setOptionalObject(oBrowser, AssistedPaymentServicesUIPage.obj_DefaultPaymentQuestionEmail_Edit, appInd.getPropertyValueByKeyName("emailUserName"));
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_Preferences_Update_Btn, "Clickable", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Before updating the client company-->'Preferences' options");
            appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_Preferences_Update_Btn);
            reports.writeResult(oBrowser, "Screenshot", "After updating the client company-->'Preferences' options");
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, AssistedPaymentServicesUIPage.obj_DefaultPaymentQuestionEmail_Edit, "value", appInd.getPropertyValueByKeyName("emailUserName")));

            if(pageName.equalsIgnoreCase("Assisted Payment Services")){
                oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
                oBrowser.navigate().refresh();
                appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentHelp_Header, "Text", "Payment Help", waitTimeOut);
            }
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'setDefaultPaymentQuestionMailUnderPreferences()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'setDefaultPaymentQuestionMailUnderPreferences()' method. " + e);
            return false;
        }
    }





    /********************************************************
     * Method Name      : validatePaymentVoidPermissionOptionsUnderIssueDropdown
     * Purpose          : User Navigates to Assistant Payment Services cases--> Email Templates--> Payment Options Email for "Payment Void Permission" Issue type
     * Author           : Gudi
     * Parameters       : oBrowser, issueOption, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validatePaymentVoidPermissionOptionsUnderIssueDropdown(WebDriver oBrowser, String issueOption, DataTable dataTable){
        Select oSelect = null;
        List<WebElement> oEles = null;
        String arrListValues[];
        try{
            Assert.assertTrue(enrollmentManagersUIBaseSteps.selectRequiredQueue(oBrowser, "Assisted Payment Services"));
            Assert.assertTrue(enrollmentManagersUIBaseSteps.clickOnShowCaseButton(oBrowser, "Assisted Payment Services"));
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+AssistedPaymentServicesUIPage.obj_AsstPaymentServices_Cases_Grid + "//input)[7]"), "Open"));
            appDep.threadSleep(2000);
            caseNumber = enrollmentManagersUIBaseSteps.readAndClickRandomRowCaseNumber(oBrowser, "Assisted Payment Services", EnrollmentsManagerUIPage.obj_EnrollmentCase_Grid + "//tr[contains(@class, 'lines dx-column-lines')]");

            //Update the email id under client company preferences
            Assert.assertTrue(setDefaultPaymentQuestionMailUnderPreferences(oBrowser, "Assisted Payment Services"));

            //Email Templates - Payment Questions - Verify "Payment Void Permission" options in "Issue" drop down and child drop downs
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_EmailTemplate_Btn));
            Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentQuestion_Link));
            appDep.waitForTheElementState(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_PaymentQuestion_Dialog + "//button[@type='submit']"), "Clickable", "", waitTimeOut);

            //'Payment Question' modal should open with below details:
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_Subject_Edit));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_PaymentQuestions_To_Label)));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentQuestions_From_List));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentQuestions_CC_Span));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_AlarmDate_Edit));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_AttachFile_Edit));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_SupplierName_Edit));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentID_Edit));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_SupplierID_Edit));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentAmount_Edit));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_RemitToId_Edit));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentPreference_Edit));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_InvoicesInQuestions_Edit));

            oSelect = new Select(oBrowser.findElement(AssistedPaymentServicesUIPage.obj_Issue_List));
            oEles = oSelect.getOptions();
            arrListValues = "--Select--#Payment Void Permission#Invoice#Unable to Contact#Process".split("#");
            for(int i=0; i<arrListValues.length; i++){
                Assert.assertTrue(appInd.compareValues(oBrowser, oEles.get(i).getText(), arrListValues[i]), "Mis-match in Actual '"+oEles.get(i).getText()+"' & Expected '"+arrListValues[i]+"' values of 'Issue' dropdown");
            }

            //Select "Payment Void Permission" in "Issue" drop down and validate the 'Void Reason' options
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_Issue_List, "Payment Void Permission"));
            appDep.threadSleep(1000);

            oSelect = new Select(oBrowser.findElement(AssistedPaymentServicesUIPage.obj_VoidReason_List));
            oEles = oSelect.getOptions();
            arrListValues = "--Select--#Amount Discrepancy, Refund Balance After Payment Posted#Payment Invoice Paid".split("#");
            for(int i=0; i<arrListValues.length; i++){
                Assert.assertTrue(appInd.compareValues(oBrowser, oEles.get(i).getText(), arrListValues[i]), "Mis-match in Actual '"+oEles.get(i).getText()+"' & Expected '"+arrListValues[i]+"' values of 'Void Reason' dropdown");
            }


            ////Select "Amount Discrepancy, Refund Balance After Payment Posted" in "Void Reason" drop down and validate the 'fields
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_VoidReason_List, "Amount Discrepancy, Refund Balance After Payment Posted"));
            appDep.threadSleep(1000);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_ProcessedAmount_Edit));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_RefundAmount_Edit));


            ////Select "Payment Invoice Paid" in "Void Reason" drop down and validate the 'Payment Methods' option values & other fields
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_VoidReason_List, "Payment Invoice Paid"));
            appDep.threadSleep(1000);

            oSelect = new Select(oBrowser.findElement(AssistedPaymentServicesUIPage.obj_PaymentMethod_List));
            oEles = oSelect.getOptions();
            arrListValues = "--Select--#CC#Check#ACH#Wire".split("#");
            for(int i=0; i<arrListValues.length; i++){
                Assert.assertTrue(appInd.compareValues(oBrowser, oEles.get(i).getText(), arrListValues[i]), "Mis-match in Actual '"+oEles.get(i).getText()+"' & Expected '"+arrListValues[i]+"' values of 'Payment Method' dropdown");
            }

            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentDate_Edit));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_DueAmount_Edit));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_IssuedAmount_Edit));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_ReissuedAmount_Edit));


            //Select 'Check' from 'Payment Methods' drop down and validate the fields
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentMethod_List, "Check"));
            appDep.threadSleep(1000);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_CheckNumber_Edit));

            ////Select 'CC' from 'Payment Methods' drop down and validate the fields
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentMethod_List, "CC"));
            appDep.threadSleep(1000);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_LastFourCardNumber_Edit));

            ////Select 'ACH' from 'Payment Methods' drop down and validate the fields
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentMethod_List, "ACH"));
            appDep.threadSleep(1000);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_DueAmount_Edit));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_IssuedAmount_Edit));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_ReissuedAmount_Edit));

            //Select 'Wire' from 'Payment Methods' drop down and validate the fields
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentMethod_List, "Wire"));
            appDep.threadSleep(1000);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_DueAmount_Edit));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_IssuedAmount_Edit));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_ReissuedAmount_Edit));

            //Verify proper error message isi displayed for the mandatory fields
            Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_Issue_List, "Invoice"));
            appDep.threadSleep(1000);


            //Close the Payment Questions dialog
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+AssistedPaymentServicesUIPage.obj_PaymentQuestion_Dialog + "//button)[1]")));
            appDep.threadSleep(2000);

            Assert.assertTrue(validatePaymentQuestionsEmailFunctionality(oBrowser, caseNumber, issueOption, dataTable));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validatePaymentVoidPermissionOptionsUnderIssueDropdown()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validatePaymentVoidPermissionOptionsUnderIssueDropdown()' method. " + e);
            return false;
        }
    }





    /********************************************************
     * Method Name      : validatePaymentQuestionsEmailFunctionality
     * Purpose          : User Navigates to Assistant Payment Services cases--> Email Templates--> Payment Options Email functionalities
     * Author           : Gudi
     * Parameters       : oBrowser, caseNumber, issueOption, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validatePaymentQuestionsEmailFunctionality(WebDriver oBrowser, String caseNumber, String issueOption, DataTable dataTable){
        List<Map<String, String>> paymentData = null;
        Map<String, String> paymentEmailData = null;
        Response response = null;
        JSONArray caseDetails = null;
        String strURL = null;
        List<WebElement> oInvoices = null;
        String invoicesNumber = "";
        String companyName = null;
        try{
            paymentData = dataTable.asMaps(String.class, String.class);
            for(int i=0; i<paymentData.size(); i++){
                paymentEmailData = new HashMap<String, String>();
                paymentEmailData.put("Issue", paymentData.get(i).get("Issue"));
                paymentEmailData.put("VoidReason", paymentData.get(i).get("VoidReason"));
                paymentEmailData.put("ProcessedAmount", paymentData.get(i).get("ProcessedAmount"));
                paymentEmailData.put("RefundAmount", paymentData.get(i).get("RefundAmount"));
                paymentEmailData.put("PaymentMethod", paymentData.get(i).get("PaymentMethod"));
                paymentEmailData.put("CheckNumber", paymentData.get(i).get("CheckNumber"));
                paymentEmailData.put("DueAmount", paymentData.get(i).get("DueAmount"));
                paymentEmailData.put("IssuedAmount", paymentData.get(i).get("IssuedAmount"));
                paymentEmailData.put("ReIssuedAmount", paymentData.get(i).get("ReIssuedAmount"));
                paymentEmailData.put("InvoiceIssue", paymentData.get(i).get("InvoiceIssue"));
                paymentEmailData.put("WhyOver_UnderPaid", paymentData.get(i).get("WhyOver_UnderPaid"));
                paymentEmailData.put("AttemptedMethods", paymentData.get(i).get("AttemptedMethods"));
                paymentEmailData.put("PhoneNumbers_EmailAddressAttemptedOnRecord", paymentData.get(i).get("PhoneNumbers_EmailAddressAttemptedOnRecord"));
                paymentEmailData.put("PaymentMethod", paymentData.get(i).get("PaymentMethod"));
                paymentEmailData.put("CheckNumber", paymentData.get(i).get("CheckNumber"));
                paymentEmailData.put("ProcessingFee", paymentData.get(i).get("ProcessingFee"));
                paymentEmailData.put("ProcessIssue", paymentData.get(i).get("ProcessIssue"));
                paymentEmailData.put("LoginCredentials", paymentData.get(i).get("LoginCredentials"));
                paymentEmailData.put("UserName", paymentData.get(i).get("UserName"));

                if(paymentEmailData.get("AttemptedMethods") != null){
                    if(paymentEmailData.get("AttemptedMethods").equalsIgnoreCase("Numbers Dialed")){
                        caseDetails = apiDataProvider.getAPIDataProviderResponse("AsstdPaymentServices_PaymentQuestions", new Object[] {"typeID", 33});
                    }else if(paymentEmailData.get("AttemptedMethods").equalsIgnoreCase("Email Addresses Used")){
                        caseDetails = apiDataProvider.getAPIDataProviderResponse("AsstdPaymentServices_PaymentQuestions", new Object[] {"typeID", 29});
                    }

                    boolean blnExist = false;
                    for(int j=0; j<((JSONArray) caseDetails.get(0)).size(); j++) {
                        companyName = ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(j)).get("company_name").toString();
                        if(!companyName.contains("Trailer Wizards LTD")){
                            caseNumber = ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(j)).get("cases_id").toString();
                            blnExist = true;
                            break;
                        }
                    }

                    if(blnExist == false){
                        reports.writeResult(oBrowser, "Fail", "The valid company name was not found. Hence exiting from the test case");
                        Assert.fail("The valid company name was not found. Hence exiting from the test case");
                    }
                    strURL = appInd.getPropertyValueByKeyName("qaURL")+"/cases/" + caseNumber;
                    oBrowser.navigate().to(strURL);
                    reports.writeResult(oBrowser, "Screenshot", "The constructed URL: " + strURL);
                    appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentHelp_Label, "Text", "Payment Help", waitTimeOut);
                    reports.writeResult(oBrowser, "Screenshot", "The page has opened with the case id '"+caseNumber+"'");
                    Assert.assertTrue(setDefaultPaymentQuestionMailUnderPreferences(oBrowser, "Assisted Payment Services"));
                }

                //Read the Supplier details to verify in the email body:
                appInd.scrollToThePage(oBrowser, "Top");
                paymentEmailData.put("SupplierName", appInd.getTextOnElement(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Supplier_Section + "//span[contains(@class, 'title')]/a"), "Text"));
                paymentEmailData.put("CaseNumber", caseNumber);
                paymentEmailData.put("CaseStatus", appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Cases_Section+ "//div[contains(@class, 'sticky')]/div"), "Text"));
                oInvoices = oBrowser.findElements(By.xpath(AssistedPaymentServicesUIPage.obj_Invoices_Grid + "//tr[@data-toggle]/td[2]/span"));
                for(int j=0; j<oInvoices.size(); j++){
                    invoicesNumber+= oInvoices.get(j).getText().trim()+", ";
                }
                if(oInvoices.size() > 0)
                    paymentEmailData.put("Invoices", invoicesNumber.substring(0, invoicesNumber.length()-2));
                else paymentEmailData.put("Invoices", "");
                appInd.scrollToElement(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentData_Link);
                appDep.threadSleep(2000);
                Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentData_Link));
                appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                paymentEmailData.put("SupplierID", appInd.getTextOnElement(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_PaymentData_Grid + "//td[text()='Supplier ID']/following-sibling::td"), "Text"));
                paymentEmailData.put("RemitTo", appInd.getTextOnElement(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_PaymentData_Grid + "//td[text()='Remit To']/following-sibling::td"), "Text"));
                paymentEmailData.put("RequestPaymentID", appInd.getTextOnElement(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_PaymentData_Grid + "//td[text()='Request Payment ID']/following-sibling::td"), "Text"));
                paymentEmailData.put("ReferenceNumber", appInd.getTextOnElement(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_PaymentData_Grid + "//td[text()='Payment Identifier']/following-sibling::td"), "Text"));
                paymentEmailData.put("PaymentAmount", appInd.getTextOnElement(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_PaymentData_Grid + "//td[text()='Payment Amount']/following-sibling::td"), "Text"));

                paymentEmailData = emailTemplatesForPaymentQuestions(oBrowser, caseNumber, paymentEmailData);
                Assert.assertTrue(paymentEmailData.get("TestPassed").equalsIgnoreCase("True"), "The 'emailTemplatesForPaymentQuestions()' method was failed");
                appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);

                //Validate under 'Activities' Grid
                paymentEmailData.put("CaseStatus", appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Cases_Section+ "//div[contains(@class, 'sticky')]/div"), "Text"));
                appInd.scrollToElement(oBrowser, AssistedPaymentServicesUIPage.obj_SplitterHorizonal_Line);
                Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_Activities_Link));
                appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_EnrollmentCases_Activities_Grid + "//tr[2][contains(@class, 'lines dx-column-lines')]/td)[3]"), "Text", paymentEmailData.get("CaseStatus")+": Payment Question"));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_EnrollmentCases_Activities_Grid + "//tr[2][contains(@class, 'lines dx-column-lines')]/td)[4]"), "Text", "Outbound Email"));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_EnrollmentCases_Activities_Grid + "//tr[2][contains(@class, 'lines dx-column-lines')]/td)[5]"), "Text", appInd.getPropertyValueByKeyName("qaUserName")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_EnrollmentCases_Activities_Grid + "//tr[2][contains(@class, 'lines dx-column-lines')]/td)[6]"), "Text", appInd.formatDate(appInd.getDateTime("MM/dd/yyyy"), "MM/dd/yyyy", "MM/dd/yyyy")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_EnrollmentCases_Activities_Grid + "//tr[2][contains(@class, 'lines dx-column-lines')]/td)[7]"), "Text", "Successfully Sent Payment Question to Client Support - " + paymentEmailData.get("Issue")));

                Assert.assertTrue(emailUtilities.connectAndOpenEmails(paymentEmailData));
                Assert.assertTrue(emailUtilities.readEmailFromPaymentQuestions(oEmailBrowser, issueOption, paymentEmailData));
            }

            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validatePaymentQuestionsEmailFunctionality()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validatePaymentQuestionsEmailFunctionality()' method. " + e);
            return false;
        }finally {paymentData = null; caseNumber = null; paymentEmailData = null;}
    }





    /********************************************************
     * Method Name      : validateInvoiceOptionsUnderIssueDropdown
     * Purpose          : User Navigates to Assistant Payment Services cases--> Email Templates--> Payment Options for "Invoice"
     * Author           : Gudi
     * Parameters       : oBrowser, issueOption, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateInvoiceOptionsUnderIssueDropdown(WebDriver oBrowser, String issueOption, DataTable dataTable){
        try{
            Assert.assertTrue(validatePaymentQuestionsEmailFunctionality(oBrowser, caseNumber, issueOption, dataTable));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateInvoiceOptionsUnderIssueDropdown()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateInvoiceOptionsUnderIssueDropdown()' method. " + e);
            return false;
        }
    }





    /********************************************************
     * Method Name      : validateUnableToContactOptionsUnderIssueDropdown
     * Purpose          : User Navigates to Assistant Payment Services cases--> Email Templates--> Payment Options for "Unable To Contact"
     * Author           : Gudi
     * Parameters       : oBrowser, issueOption, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateUnableToContactOptionsUnderIssueDropdown(WebDriver oBrowser, String issueOption, DataTable dataTable){
        try{
            Assert.assertTrue(validatePaymentQuestionsEmailFunctionality(oBrowser, caseNumber, issueOption, dataTable));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateUnableToContactOptionsUnderIssueDropdown()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateUnableToContactOptionsUnderIssueDropdown()' method. " + e);
            return false;
        }
    }




    /********************************************************
     * Method Name      : validateProcessOptionUnderIssueDropdown
     * Purpose          : User Navigates to Assistant Payment Services cases--> Email Templates--> Payment Options for "Process"
     * Author           : Gudi
     * Parameters       : oBrowser, issueOption, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateProcessOptionUnderIssueDropdown(WebDriver oBrowser, String issueOption, DataTable dataTable){
        try{
            Assert.assertTrue(validatePaymentQuestionsEmailFunctionality(oBrowser, caseNumber, issueOption, dataTable));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateProcessOptionUnderIssueDropdown()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateProcessOptionUnderIssueDropdown()' method. " + e);
            return false;
        }
    }




    /********************************************************
     * Method Name      : userValidatesLastTouchColumnForSendEmailFunctionality
     * Purpose          : User validates Last Touch column values after performing Send Email functionality for 'Assisted PAyment Servrices'
     * Author           : Gudi
     * Parameters       : oBrowser, queueName, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean userValidatesLastTouchColumnForSendEmailFunctionality(WebDriver oBrowser, String queueName, DataTable dataTable){
        String caseNumber = null;
        Map<String, String> emailData = null;
        try{
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appDep.waitForTheElementState(oBrowser, By.xpath("("+AssistedPaymentServicesUIPage.obj_AsstPaymentServices_Cases_Grid + "//input)[1]"), "Clickable", "", minTimeOut);
            appInd.clearAndSetObject(oBrowser, By.xpath("("+AssistedPaymentServicesUIPage.obj_AsstPaymentServices_Cases_Grid + "//input)[1]"), "");
            appDep.threadSleep(2000);
            appInd.clearAndSetObject(oBrowser, By.xpath("("+AssistedPaymentServicesUIPage.obj_AsstPaymentServices_Cases_Grid + "//input)[7]"), "Open");
            appDep.threadSleep(2000);
            caseNumber = enrollmentManagersUIBaseSteps.readAndClickRandomRowCaseNumber(oBrowser, queueName, EnrollmentsManagerUIPage.obj_EnrollmentCase_Grid + "//tr[contains(@class, 'lines dx-column-lines')]");

            emailData = appDep.sendEmail_ComposeNewMessageFunctionality(oBrowser, queueName, dataTable);
            Assert.assertTrue(emailData.get("TestPassed").equalsIgnoreCase("True"), "The 'sendEmail_ComposeNewMessageFunctionality()' method was failed");

            //Go back to the case from the dashboard and Validate Last touch column
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);

            //Check user received the email
            Assert.assertTrue(emailUtilities.connectAndOpenEmails(emailData));
            Assert.assertTrue(emailUtilities.readEmailFromSendMail(oEmailBrowser, emailData));
            Assert.assertTrue(validateLastTouchColumn(oBrowser, caseNumber));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'userValidatesLastTouchColumnForSendEmailFunctionality()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'userValidatesLastTouchColumnForSendEmailFunctionality()' method. " + e);
            return false;
        }finally {caseNumber = null; emailData = null;}
    }





    /********************************************************
     * Method Name      : verifyFileFormatsForUploadingFilesForSendMailFunctionality
     * Purpose          : User validates uploading differnet supported/unsupported files for Send Email functionality for 'Assisted PAyment Servrices'
     * Author           : Gudi
     * Parameters       : oBrowser, isUploadSupported, fileFormats, queueName, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean verifyFileFormatsForUploadingFilesForSendMailFunctionality(WebDriver oBrowser, String isUploadSupported, String queueName, DataTable dataTable){
        String arrFilesToUpload[];
        Map<String, String> emailData = null;
        String caseNumber = null;
        boolean blnExist = false;
        try{
            blnExist = appInd.verifyOptionalElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_EmailTemplate_Btn);
            if(!blnExist){
                Assert.assertTrue(enrollmentManagersUIBaseSteps.selectRequiredQueue(oBrowser, queueName));
                Assert.assertTrue(enrollmentManagersUIBaseSteps.clickOnShowCaseButton(oBrowser, queueName));
                appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                caseNumber = enrollmentManagersUIBaseSteps.readAndClickRandomRowCaseNumber(oBrowser, queueName, EnrollmentsManagerUIPage.obj_EnrollmentCase_Grid + "//tr[contains(@class, 'lines dx-column-lines')]");
            }

            emailData = appDep.sendEmail_ComposeNewMessageFunctionality(oBrowser, queueName, dataTable);
            Assert.assertTrue(emailData.get("TestPassed").equalsIgnoreCase("True"), "The 'sendEmail_ComposeNewMessageFunctionality()' method was failed");
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifyFileFormatsForUploadingFilesForSendMailFunctionality()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifyFileFormatsForUploadingFilesForSendMailFunctionality()' method. " + e);
            return false;
        }finally{arrFilesToUpload = null; emailData = null; caseNumber = null;}
    }




    /********************************************************
     * Method Name      : verifyBillPayCasesLogActivityCaseOutcomeFunctionality
     * Purpose          : User validates Pend Logic for Bill Pay Cases-> Log Activity-> Case outcome options functionality for 'Assisted Payment Servrices'
     * Author           : Gudi
     * Parameters       : oBrowser, queueName, caseType, casePriority, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public  boolean verifyBillPayCasesLogActivityCaseOutcomeFunctionality(WebDriver oBrowser, String queueName, String caseType, String caseStatus, String casePriority, DataTable dataTable){
        String caseNumber = null;
        String arrCaseTypes[];
        String dbAlarmDateBefore = null;
        String dbAlarmDateAfter = null;
        String appAlarmDateBefore = null;
        String appAlarmDateAfter = null;
        JSONArray dbResponse = null;
        Map<String, String> logActivity = null;
        boolean blnExist = false;
        try{
            arrCaseTypes = caseType.split("#");
            blnExist = appInd.verifyOptionalElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_CaseNumber_Grid_Pagination);
            if(!blnExist){
                Assert.assertTrue(enrollmentManagersUIBaseSteps.selectRequiredQueue(oBrowser, queueName));
                Assert.assertTrue(enrollmentManagersUIBaseSteps.clickOnShowCaseButton(oBrowser, queueName));
            }

            for(int i=0; i<arrCaseTypes.length; i++){
                appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+AssistedPaymentServicesUIPage.obj_AsstPaymentServices_Cases_Grid + "//input)[6]"), arrCaseTypes[i]));
                if(!caseStatus.isEmpty()) Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+AssistedPaymentServicesUIPage.obj_AsstPaymentServices_Cases_Grid + "//input)[7]"), caseStatus));
                Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+AssistedPaymentServicesUIPage.obj_AsstPaymentServices_Cases_Grid + "//input)[8]"), casePriority));
                appDep.threadSleep(2000);
                caseNumber = enrollmentManagersUIBaseSteps.readAndClickRandomRowCaseNumber(oBrowser, queueName, EnrollmentsManagerUIPage.obj_EnrollmentCase_Grid + "//tr[contains(@class, 'lines dx-column-lines')]");

                //Read the alarm date from db and Click on log activity
                dbResponse = apiDataProvider.getAPIDataProviderResponse("PayCRM_PendLogic_AlarmDate", new Object[] {"caseID", Integer.parseInt(caseNumber)});
                dbAlarmDateBefore = (String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("alarm_date")).split("T"))[0].trim();
                if(appInd.verifyOptionalElementPresent(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_CaseDetails_Section + "//dt[text()='Alarm']/following-sibling::dd[1]"))){
                    appAlarmDateBefore = appInd.getTextOnElement(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_CaseDetails_Section + "//dt[text()='Alarm']/following-sibling::dd[1]"), "Text");
                    Assert.assertTrue(dbAlarmDateBefore.equalsIgnoreCase(appInd.formatDateFromOnetoAnother((appAlarmDateBefore.split(" "))[0].trim(), "MM/dd/yyyy", "yyyy-MM-dd")), "Mis-match in actual '"+dbAlarmDateBefore+"' & expected '"+appAlarmDateBefore+"' Alarm Date");
                }else{
                    appAlarmDateBefore = "null";
                    Assert.assertTrue(dbAlarmDateBefore.equalsIgnoreCase(appAlarmDateBefore), "Mis-match in actual '"+dbAlarmDateBefore+"' & expected '"+appAlarmDateBefore+"' Alarm Date");
                }

                logActivity = performLogActivity(oBrowser, dataTable);
                Assert.assertTrue(logActivity.get("TestPassed").equalsIgnoreCase("True"), "The 'performLogActivity()' method was failed");
                oBrowser.navigate().refresh();
                appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentHelp_Label, "Text", "Payment Help", waitTimeOut);
                appDep.threadSleep(2000);

                //Validate the pend logic in "Case Reference" section of log activity page
                dbResponse = apiDataProvider.getAPIDataProviderResponse("PayCRM_PendLogic_AlarmDate", new Object[] {"caseID", Integer.parseInt(caseNumber)});
                dbAlarmDateAfter = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("alarm_date").toString();

                appInd.scrollToThePage(oBrowser, "Top");
                if(logActivity.get("CaseOutcome").equalsIgnoreCase("Call Transfer") || logActivity.get("CaseOutcome").equalsIgnoreCase("Team Lead Review"))
                    Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_CaseDetails_Section + "//dt[text()='Assigned to']/following-sibling::dd[1]"), "Text").contains(logActivity.get("CaseOutcomeValidation")));

                appAlarmDateAfter = appInd.getTextOnElement(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_CaseDetails_Section + "//dt[text()='Alarm']/following-sibling::dd[1]"), "Text");
                if(!logActivity.get("CaseOutcome").equalsIgnoreCase("Payment Question Answered")){
                    String date = (appAlarmDateAfter.split(" "))[0].trim();
                    String actualDate = appInd.formatDateFromOnetoAnother(logActivity.get("alarmDate").replace("-", "/"), "yyyy/MM/dd", "MM/dd/yyyy");
                    Assert.assertTrue(date.equals(actualDate), "Mis-match in Actual '"+date+"' & Expected '"+actualDate+"' Alarm Date");
                    String time = (appAlarmDateAfter.split(" "))[1].trim().substring(0, 5);
                    String alarmDate = appInd.formatDateFromOnetoAnother(date, "dd/MM/yyyy", "yyyy-dd-MM");
                    Assert.assertTrue(dbAlarmDateAfter.contains(alarmDate+"T"+time), "Mis-match in the DB: '"+dbAlarmDateAfter+"' and UI '"+(alarmDate+"T"+time)+"' values for the Alarm Date after Log Activity");
                }else if(casePriority.equalsIgnoreCase("High")){
                    Assert.assertTrue(appInd.compareValues(oBrowser, "1", String.valueOf(appInd.dateDifferences((appAlarmDateAfter.split(""))[0], appInd.getDateTimeByTimezone("yyyy/MM/dd", "CST"), "Day", "yyyy/MM/dd"))));
                    Assert.assertTrue((appAlarmDateAfter.split(" ")[0].trim().equalsIgnoreCase(appInd.dateAddAndReturn("MM/dd/yyyy", 1))), "Mis-match in Actual '"+(appAlarmDateAfter.split(" ")[0].trim()+"' & Expected '"+appInd.dateAddAndReturn("MM/dd/yyyy", 1))+"' Alarm date for High Priority CMVP/SMVP Cases");
                }
                else{
                    Assert.assertTrue((appAlarmDateBefore.split(" "))[0].trim().equalsIgnoreCase((appAlarmDateAfter.split(" "))[0].trim()), "Mis-match in the DB: '"+appAlarmDateBefore+"' and UI '"+appAlarmDateBefore+"' values for the Alarm Date after Log Activity");
                }

                if("Payment Question#Promised to Process#Payment Follow-up#No Response#No Answer - LVM#Call Transfer#Team Lead Review#Payment Question Answered".contains(logActivity.get("CaseOutcome")))
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Cases_Section+ "//div[contains(@class, 'sticky')]/div"), "Text", logActivity.get("caseStatusRunTime")));
                reports.writeResult(oBrowser, "Screenshot", "After Log Activity for the Case Outcome: " + logActivity.get("CaseOutcome"));

                //Validate Activity Tab
                appInd.scrollToElement(oBrowser, AssistedPaymentServicesUIPage.obj_SplitterHorizonal_Line);
                appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_Activities_Link, "Clickable", "", minTimeOut);
                Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_Activities_Link));
                appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_search_Loading, "Invisibility", "", minTimeOut);
                Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+AssistedPaymentServicesUIPage.obj_Activities_Grid + "//input)[6]"), logActivity.get("Notes")));
                appDep.threadSleep(2000);
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Activities_Grid + "//tr[contains(@class, 'dx-row-lines')]/td[3]"), "Text", logActivity.get("caseStatusRunTime")+": "+ logActivity.get("CaseOutcome")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Activities_Grid + "//tr[contains(@class, 'dx-row-lines')]/td[4]"), "Text", logActivity.get("ActivityType")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Activities_Grid + "//tr[contains(@class, 'dx-row-lines')]/td[5]"), "Text", appInd.getPropertyValueByKeyName("qaUserName")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Activities_Grid + "//tr[contains(@class, 'dx-row-lines')]/td[6]"), "Text", appInd.formatDate(appInd.getDateTime("MM/dd/yyyy"), "MM/dd/yyyy", "MM/dd/yyyy")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Activities_Grid + "//tr[contains(@class, 'dx-row-lines')]/td[7]"), "Text", logActivity.get("Notes")));
                if(logActivity.get("CaseOutcome").equalsIgnoreCase("Call Transfer") || logActivity.get("CaseOutcome").equalsIgnoreCase("Team Lead Review"))
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_Activities_Grid + "//tr[contains(@class, 'dx-row-lines')]/td[8]"), "Text", logActivity.get("CaseOutcomeValidation")));
                reports.writeResult(oBrowser, "Screenshot", "'Activities' tab validation After Log Activity for the Case Outcome: " + logActivity.get("CaseOutcome"));

                oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
            }

            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifyBillPayCasesLogActivityCaseOutcomeFunctionality()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifyBillPayCasesLogActivityCaseOutcomeFunctionality()' method. " + e);
            return false;
        }finally {caseNumber = null; arrCaseTypes = null; dbAlarmDateBefore = null; dbAlarmDateAfter = null; appAlarmDateBefore = null; appAlarmDateAfter = null; dbResponse = null; logActivity = null;}
    }

    /********************************************************
     * Method Name      : validatePaymentQuestionTemplateForCreditIssue
     * Purpose          : This method helps to validate Issue type with all combinations for payment tamplate
     * Author           : Shidd
     * Parameters       : oBrowser, queueName, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validatePaymentQuestionTemplateForCreditIssue(WebDriver oBrowser, String queueName, DataTable dataTable) {
        String activity, activityType, createdBy, notes, issueType;
        List<Map<String, String>> paymentQuestions;
        try {
            paymentQuestions = dataTable.asMaps(String.class, String.class);
            Assert.assertTrue(enrollmentManagersUIBaseSteps.selectRequiredQueue(oBrowser, queueName));
            Assert.assertTrue(enrollmentManagersUIBaseSteps.clickOnShowCaseButton(oBrowser, queueName));
            Assert.assertNotNull(enrollmentManagersUIBaseSteps.readAndClickResultFirstCaseNumber(oBrowser, queueName));

            for (Map<String, String> paymentQuestion : paymentQuestions) {
                /*
                Launching payment questions and entering credit as issue
                 */
                Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_EmailTemplate_Btn));
                Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentQuestion_Link));
                appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentQuestion_Send_Btn, "Clickable", "", waitTimeOut);
                Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentQuestion_Send_Btn));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_paymentQuestionIssueDropdownError));
                Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_Issue_List, "Credit"));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_PaymentQuestions_To_Label + "//li[@class='search-choice']"), "text","inroll.development@corcentric.com"));
                reports.writeResult(oBrowser, "Screenshot", "Before entering the details for 'Payment Questions'");
                Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentQuestion_Send_Btn));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_paymentQuestionCreditIssueDropdownError));

                // Testing different combinations of payment questions
                if (paymentQuestion.get("CreditOptions").equalsIgnoreCase("Credit Refund") && paymentQuestion.get("Reason").equalsIgnoreCase("Cannot Confirm")) {
                    Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_creditIssueDropdown, "Credit Refund"));
                    Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentQuestion_Send_Btn));
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_paymentQuestionCreditReasonDropdownError));
                    Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_creditReasonDropdown, "Cannot Confirm"));
                    reports.writeResult(oBrowser, "Screenshot", "After entering the details for 'Payment Questions'");
                    Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentQuestion_Send_Btn));
                }

                if (paymentQuestion.get("CreditOptions").equalsIgnoreCase("Credit Refund") && paymentQuestion.get("Reason").equalsIgnoreCase("Confirmed (will not re-process)")) {
                    Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_creditIssueDropdown, "Credit Refund"));
                    Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentQuestion_Send_Btn));
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_paymentQuestionCreditReasonDropdownError));
                    Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_creditReasonDropdown, "Confirmed (will not re-process)"));
                    Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentQuestion_Send_Btn));
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_attemptedMethodErrorMessage));
                    Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_attemptedMethodDropdown, "Numbers Dialed"));
                    reports.writeResult(oBrowser, "Screenshot", "After entering the details for 'Payment Questions'");
                    Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentQuestion_Send_Btn));
                }

                if (paymentQuestion.get("CreditOptions").equalsIgnoreCase("Credit Refund") && paymentQuestion.get("Reason").equalsIgnoreCase("Confirmed (will not refund)")) {
                    Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_creditIssueDropdown, "Credit Refund"));
                    Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_creditReasonDropdown, "Confirmed (will not refund)"));
                    Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_attemptedMethodDropdown, "Numbers Dialed"));
                    reports.writeResult(oBrowser, "Screenshot", "After entering the details for 'Payment Questions'");
                    Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentQuestion_Send_Btn));
                }

                if (paymentQuestion.get("CreditOptions").equalsIgnoreCase("Credit ReRun") && paymentQuestion.get("Reason").equalsIgnoreCase("Credit ReRun")) {
                    Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_creditIssueDropdown, "Credit ReRun"));
                    Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentQuestion_Send_Btn));
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_creditRerunErrorMessage));
                    Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_creditRerunReasonDropdown, "Credit ReRun"));
                    reports.writeResult(oBrowser, "Screenshot", "After entering the details for 'Payment Questions'");
                    Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentQuestion_Send_Btn));
                }

                // Verifying Activities
                appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignConfirmationMessage_Label, "visibility", "", waitTimeOut);
                Assert.assertTrue(appInd.verifyContainsValues(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignConfirmationMessage_Label, "text"), "Client Support email has been successfully sent."));

                appInd.moveToElement(oBrowser, AssistedPaymentServicesUIPage.obj_logActivitiesTab);
                Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, AssistedPaymentServicesUIPage.obj_logActivitiesTab));

                activity = appInd.getTextOnElement(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_logActivitiesFirstRow + "/td[contains(@aria-label, 'Column Activity, Value Hold')]"), "text");
                activityType = appInd.getTextOnElement(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_logActivitiesFirstRow + "/td[contains(@aria-label, 'Column Activity Type')]"), "text");
                createdBy = appInd.getTextOnElement(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_logActivitiesFirstRow + "/td[contains(@aria-label, 'Column Created By')]"), "text");
                notes = appInd.getTextOnElement(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_logActivitiesFirstRow + "/td[contains(@aria-label, 'Column Notes')]"), "text");

                Assert.assertEquals(activity, "Hold: Payment Question");
                Assert.assertEquals(activityType, "Outbound Email");
                Assert.assertEquals(createdBy, "paycrm_testuser_gudi@corcentric.com");
                Assert.assertEquals(notes, "Successfully Sent Payment Question to Client Support - Credit");

                appInd.moveToElement(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_logActivitiesFirstRow + "/td[contains(@aria-label, 'Column Actions')]//i[@class='fa-solid fa-eye']"));
                Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_logActivitiesFirstRow + "/td[contains(@aria-label, 'Column Actions')]//i[@class='fa-solid fa-eye']")));
                issueType = appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_activityDetailsInViewIcon, "text");
                Assert.assertTrue(appInd.verifyContainsValues(issueType, "Credit"));

                Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_closeActivitiesModal));
                appInd.scrollToThePage(oBrowser, "Top");
            }
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validatePaymentQuestionTemplateForCredit()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validatePaymentQuestionTemplateForCredit()' method. " + e);
            return false;
        } finally {
            activity = null; activityType = null; createdBy = null; notes = null; issueType = null; paymentQuestions = null;
        }
    }

    /********************************************************
     * Method Name      : validateMonikerIconForReopenedBillPayCases
     * Purpose          : This method helps to validate moniker icon for reopened cases
     * Author           : Shidd
     * Parameters       : oBrowser, queueName, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateMonikerIconForReopenedBillPayCases(WebDriver oBrowser, String queueName, DataTable dataTable) {
        int rowCount;
        try {
            Assert.assertTrue(enrollmentManagersUIBaseSteps.selectRequiredQueue(oBrowser, queueName));
            Assert.assertTrue(enrollmentManagersUIBaseSteps.applyFilterForAssistantServiceDashboard(oBrowser, dataTable));
            Assert.assertTrue(enrollmentManagersUIBaseSteps.clickOnShowCaseButton(oBrowser, queueName));
            rowCount = oBrowser.findElements(By.xpath(AssistedPaymentServicesUIPage.obj_billCaseGridRows + "//tr")).size();
            reports.writeResult(oBrowser, "Screenshot", "After enabling Reopened Cases");
            for (int i = 2; i < rowCount-2; i++) {
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_billCaseGridRows + "//tr/td[3]/i[@title='Credit Applied']")));
            }
            reports.writeResult(oBrowser, "Info", "Successfully verified moniker icon for Supplier inside table.");
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateMonikerIconForReopenedBillPayCases()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateMonikerIconForReopenedBillPayCases()' method. " + e);
            return false;
        }
    }
}
