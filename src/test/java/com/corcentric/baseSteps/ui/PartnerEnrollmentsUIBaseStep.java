package com.corcentric.baseSteps.ui;

import com.corcentric.pages.AssistedPaymentServicesUIPage;
import com.corcentric.pages.EnrollmentsManagerUIPage;
import com.corcentric.pages.PartnerEnrollmentsUIPage;
import com.corcentric.runner.CucumberTestRunner;
import io.cucumber.datatable.DataTable;
import net.minidev.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.*;

public class PartnerEnrollmentsUIBaseStep extends CucumberTestRunner {
    private String assignedUser;
    private String assignTo;

    /********************************************************
     * Method Name      : reassignTheEnrollmentCaseForEnrollmentManager()
     * Purpose          : to login to the required application
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean reassignTheEnrollmentCaseForPartnerEnrollments(WebDriver oBrowser, DataTable dataTable){
        List<Map<String, String>> reassignmentDetails = null;
        try{
            reassignmentDetails = dataTable.asMaps(String.class, String.class);
            Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Reassign_Btn), "clickObject() was failed for '"+String.valueOf(EnrollmentsManagerUIPage.obj_Reassign_Btn)+"' webelement");
            appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_ReassignCase_Dialog, "Text", "Reassign Case", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "The 'Reassign Case' dialog opened successful");

            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PartnerEnrollmentsUIPage.obj_ReassignCase_UserName_Label), "verifyElementPresent() was failed for '"+String.valueOf(PartnerEnrollmentsUIPage.obj_ReassignCase_UserName_Label)+"' webelement");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PartnerEnrollmentsUIPage.obj_Assign_Btn), "verifyElementPresent() was failed for '"+String.valueOf(PartnerEnrollmentsUIPage.obj_Assign_Btn)+"' webelement");

            assignedUser = appInd.createAndGetWebElement(oBrowser, PartnerEnrollmentsUIPage.obj_ReassignCase_UserName_Label).getText();
            if(assignedUser.equalsIgnoreCase(reassignmentDetails.get(0).get("AssignedToUser1"))){
                assignTo = reassignmentDetails.get(0).get("AssignedToUser2");
            }else{
                assignTo = reassignmentDetails.get(0).get("AssignedToUser1");
            }
            Assert.assertTrue(appInd.clickObject(oBrowser, PartnerEnrollmentsUIPage.obj_ReassignCase_UserName_Label), "clickObject() was failed for '"+String.valueOf(EnrollmentsManagerUIPage.obj_ReassignCase_UserName_Label)+"' webelement");
            appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_ReassignCase_UserName_EditList, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.setObject(oBrowser, EnrollmentsManagerUIPage.obj_ReassignCase_UserName_EditList, assignTo), "setObject() was failed for '"+String.valueOf(EnrollmentsManagerUIPage.obj_ReassignCase_UserName_EditList)+"' webelement");
            appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_ReassignCase_UserName_SearchResult_Label, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PartnerEnrollmentsUIPage.obj_ReassignCase_UserName_SearchResult_Label), "clickObject() was failed for '"+String.valueOf(PartnerEnrollmentsUIPage.obj_ReassignCase_UserName_SearchResult_Label)+"' webelement");
            reports.writeResult(oBrowser, "Screenshot", "The Partner Enrollment 'Reassign Case' details entered successful");
            Assert.assertTrue(appInd.clickObject(oBrowser, PartnerEnrollmentsUIPage.obj_Assign_Btn), "clickObject() was failed for '"+String.valueOf(EnrollmentsManagerUIPage.obj_Assign_Btn)+"' webelement");
            appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_Suppliers_search_Loading, "Invisibility", "", waitTimeOut);
            //Assert.assertTrue(appInd.getTextOnElement(oBrowser, EnrollmentsManagerUIPage.obj_ReassignConfirmationMessage_Label).contains("Case has been successfully reassigned"), "Mis-match in actual & expected values for the '"+String.valueOf(EnrollmentsManagerUIPage.obj_ReassignConfirmationMessage_Label)+"' webelement");
            reports.writeResult(oBrowser, "Screenshot", "The post 'Reassign Case' details for Partner Enrollment");
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, PartnerEnrollmentsUIPage.obj_AssignedTo_Label, "Text").trim(), assignTo, "Mis-match in actual & expected values for the '"+String.valueOf(EnrollmentsManagerUIPage.obj_AssignedTo_Label)+"' webelement");
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'reassignTheEnrollmentCaseForPartnerEnrollments()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'reassignTheEnrollmentCaseForPartnerEnrollments()' method. " + e);
            return false;
        }finally{reassignmentDetails = null;}
    }




    /********************************************************
     * Method Name      : verifyTheReassignmentDetailsUnderPartnerEnrollmentsActivitiesTab()
     * Purpose          : to verify the reassignment details under Activities tab
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean verifyTheReassignmentDetailsUnderPartnerEnrollmentsActivitiesTab(WebDriver oBrowser) {
        Calendar cal;
        try {
            reports.writeResult(oBrowser, "Screenshot", "The Partner Enrollments 'Activities' tab details");
            appDep.waitForTheElementState(oBrowser, PartnerEnrollmentsUIPage.obj_EnrollmentActivities_Outcome_Column, "Clickable", "", waitTimeOut);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyText(oBrowser, PartnerEnrollmentsUIPage.obj_EnrollmentActivities_Outcome_Column, "Text", "Manual Entry - Re-assigned"), "Mis-match in actual & expected values for the '"+String.valueOf(PartnerEnrollmentsUIPage.obj_EnrollmentActivities_Outcome_Column)+"' webelement");
            Assert.assertTrue(appInd.verifyText(oBrowser, PartnerEnrollmentsUIPage.obj_EnrollmentActivities_CreatedBy_Column, "Text", appInd.getPropertyValueByKeyName("qaUserName")), "Mis-match in actual & expected values for the '"+String.valueOf(PartnerEnrollmentsUIPage.obj_EnrollmentActivities_CreatedBy_Column)+"' webelement");

            cal = Calendar.getInstance();
            String dateCreated = String.valueOf((cal.get(Calendar.MONTH)+1))+"/"+String.valueOf(cal.get(Calendar.DAY_OF_MONTH))+"/"+String.valueOf(cal.get(Calendar.YEAR));
            Assert.assertTrue(appInd.verifyText(oBrowser, PartnerEnrollmentsUIPage.obj_EnrollmentActivities_DateCreated_Column, "Text", dateCreated), "Mis-match in actual & expected values for the '"+String.valueOf(PartnerEnrollmentsUIPage.obj_EnrollmentActivities_DateCreated_Column)+"' webelement");

            Assert.assertTrue(appInd.verifyText(oBrowser, PartnerEnrollmentsUIPage.obj_EnrollmentActivities_Notes_Column, "Text", appInd.getPropertyValueByKeyName("qaUserName")+ " reassigned this case to " + assignTo), "Mis-match in actual & expected values for the '"+String.valueOf(PartnerEnrollmentsUIPage.obj_EnrollmentActivities_Notes_Column)+"' webelement");
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in ' verifyTheReassignmentDetailsUnderPartnerEnrollmentsActivitiesTab()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifyTheReassignmentDetailsUnderPartnerEnrollmentsActivitiesTab()' method. " + e);
            return false;
        }
    }


    public boolean verifyLogActivityEmailPhoneFields(WebDriver oBrowser){
        int randomData = appInd.getRandomNumber(6);
        try{
            appDep.waitForTheElementState(oBrowser, PartnerEnrollmentsUIPage.obj_LogActivity_Btn, "visibility", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PartnerEnrollmentsUIPage.obj_LogActivity_Btn));
            appDep.threadSleep(2000);
            appInd.switchToLastWindow(oBrowser);
            //------------ Verification of Activity Information ------------
            appDep.waitForTheElementState(oBrowser, PartnerEnrollmentsUIPage.obj_LogActivity_Information, "visibility", "", waitTimeOut);
            Assert.assertTrue(appInd.selectObject(oBrowser, PartnerEnrollmentsUIPage.obj_LogActivity_Information, "Email - Inbound"));

            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PartnerEnrollmentsUIPage.obj_LogActivity_Information_Email_ContactName));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PartnerEnrollmentsUIPage.obj_LogActivity_Information_Email_ContactEmail));
            appInd.selectObject(oBrowser, PartnerEnrollmentsUIPage.obj_LogActivity_Information, "Email - Outbound");

            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PartnerEnrollmentsUIPage.obj_LogActivity_Information_Email_ContactName));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PartnerEnrollmentsUIPage.obj_LogActivity_Information_Email_ContactEmail));
            Assert.assertTrue(appInd.selectObject(oBrowser, PartnerEnrollmentsUIPage.obj_LogActivity_Information, "Phone - Inbound"));

            Assert.assertTrue(appInd.verifyElementPresent(oBrowser,PartnerEnrollmentsUIPage.obj_LogActivity_Information_Email_ContactName));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser,PartnerEnrollmentsUIPage.obj_LogActivity_Information_Email_ContactNumber));
            Assert.assertTrue(appInd.selectObject(oBrowser, PartnerEnrollmentsUIPage.obj_LogActivity_Information, "Phone - Outbound"));

            Assert.assertTrue(appInd.verifyElementPresent(oBrowser,PartnerEnrollmentsUIPage.obj_LogActivity_Information_Email_ContactName));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser,PartnerEnrollmentsUIPage.obj_LogActivity_Information_Email_ContactNumber));
            //------------ Verification of Case Outcome Information ----------
            //Enrollment Follow-up
            Assert.assertTrue(appInd.selectObject(oBrowser, PartnerEnrollmentsUIPage.obj_LogActivity_CaseOutcome, "Enrollment Follow-up"));

            Assert.assertTrue(appInd.verifyElementPresent(oBrowser,PartnerEnrollmentsUIPage.obj_LogActivity_CaseOutcome_AlarmDate));
            String alarmDate = appInd.getTextOnElement(oBrowser, PartnerEnrollmentsUIPage.obj_LogActivity_CaseOutcome_AlarmDate, "value");
            String description = appInd.getTextOnElement(oBrowser, PartnerEnrollmentsUIPage.obj_LogActivity_CaseOutcome_Description, "text");
            Assert.assertTrue(StringUtils.isNotBlank(alarmDate));
            Assert.assertEquals(description, "Agent followed up with supplier via email. Case is set to Hold.");
            //Information Provided
            Assert.assertTrue(appInd.selectObject(oBrowser, PartnerEnrollmentsUIPage.obj_LogActivity_CaseOutcome, "Information Provided"));

            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PartnerEnrollmentsUIPage.obj_LogActivity_CaseOutcome_AlarmDate));
            description = appInd.getTextOnElement(oBrowser, PartnerEnrollmentsUIPage.obj_LogActivity_CaseOutcome_Description, "text");
            Assert.assertEquals(description, "Invoice copy, account number, or contact information has been provided. Case is set to In Progress.");
            //No Response
            Assert.assertTrue(appInd.selectObject(oBrowser, PartnerEnrollmentsUIPage.obj_LogActivity_CaseOutcome, "No Response"));

            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PartnerEnrollmentsUIPage.obj_LogActivity_CaseOutcome_AlarmDate));
            appInd.getTextOnElement(oBrowser, PartnerEnrollmentsUIPage.obj_LogActivity_CaseOutcome_AlarmDate, "value");
            alarmDate = appInd.getTextOnElement(oBrowser, PartnerEnrollmentsUIPage.obj_LogActivity_CaseOutcome_AlarmDate, "value");
            description = appInd.getTextOnElement(oBrowser, PartnerEnrollmentsUIPage.obj_LogActivity_CaseOutcome_Description, "text");
            Assert.assertEquals(description, "Supplier did not respond when an attempt was made to contact, via phone or email. Case is set to Hold.");
            Assert.assertTrue(StringUtils.isNotBlank(alarmDate));
            //Refused to Participate
            appInd.selectObject(oBrowser, PartnerEnrollmentsUIPage.obj_LogActivity_CaseOutcome, "Refused to Participate");

            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PartnerEnrollmentsUIPage.obj_LogActivity_CaseOutcome_AlarmDate));
            description = appInd.getTextOnElement(oBrowser, PartnerEnrollmentsUIPage.obj_LogActivity_CaseOutcome_Description, "text");
            Assert.assertEquals(description, "Supplier has refused to provide information or is unwilling to participate in Enrollment. Case is set to Pending Case Closure.");
            //------- Enter details and create Activity log --------
            Assert.assertTrue(appInd.setObject(oBrowser, PartnerEnrollmentsUIPage.obj_LogActivity_Information_Email_ContactName,"Automation Execution"));
            Assert.assertTrue(appInd.setObject(oBrowser, PartnerEnrollmentsUIPage.obj_LogActivity_Information_Email_ContactNumber,"9898"+randomData));
            Assert.assertTrue(appInd.setObject(oBrowser, PartnerEnrollmentsUIPage.obj_LogActivity_Notes,"Notes-"+randomData));
            Assert.assertTrue(appInd.clickObject(oBrowser, PartnerEnrollmentsUIPage.obj_LogActivity_Submit_Btn));
            final String caseID = oBrowser.getCurrentUrl().split("/")[5];
            String caseUpdate = appInd.getTextOnElement(oBrowser, PartnerEnrollmentsUIPage.obj_LogActivity_CaseUpdate_Msg,"text");
            Assert.assertTrue(caseUpdate.trim().startsWith("Case "+caseID+" has been successfully"));
            oBrowser.close();
            appInd.switchToLastWindow(oBrowser);
            appDep.waitForTheElementState(oBrowser, PartnerEnrollmentsUIPage.obj_ActivitiesTab_SearchBoxes,"visibility", "", waitTimeOut);
            appInd.getWebElements(oBrowser, PartnerEnrollmentsUIPage.obj_ActivitiesTab_SearchBoxes).get(6).sendKeys("Notes-"+randomData);

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(String.format(PartnerEnrollmentsUIPage.activitiesGrid_Record_Open,"Notes-"+randomData))));
            appDep.waitForTheElementState(oBrowser, By.xpath("//div[@id='view_notes' and contains(@style,'block;')]"),"visibility", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser,
                    By.xpath(String.format(PartnerEnrollmentsUIPage.activityModelVerification,"Phone (Outbound) - Refused to Participate"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser,
                    By.xpath(String.format(PartnerEnrollmentsUIPage.activityModelVerification,"Automation Execution"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser,
                    By.xpath(String.format(PartnerEnrollmentsUIPage.activityModelVerification,"Notes-"+randomData))));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PartnerEnrollmentsUIPage.obj_Activity_ViewNotes_Dialog + "//button[@class='close']")));
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in ' verifyLogActivityEmailPhoneFields()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifyLogActivityEmailPhoneFields()' method. " + e);
            return false;
        }
    }

    private boolean isVisible(By... by){
        for(By element : by){
            if(!appInd.verifyElementPresent(oBrowser, element))
                return false;
        }
        return true;
    }

    /********************************************************
     * Method Name      : validateFilterFunctionalityForTheColumnData()
     * Purpose          : User validates the filter functionality for the column data
     * Author           : Gudi
     * Parameters       : oBrowser, queueName
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateFilterFunctionalityForTheColumnData(WebDriver oBrowser, String queueName){
        JSONArray caseDetails = null;
        try{
            Assert.assertTrue(enrollmentManagersUIBaseSteps.selectRequiredQueue(oBrowser, queueName));
            appDep.threadSleep(2000);
            Assert.assertTrue(enrollmentManagersUIBaseSteps.applyFiltersOnQueuesDashboard(oBrowser, queueName));
            Assert.assertTrue(enrollmentManagersUIBaseSteps.clickOnShowCaseButton(oBrowser, queueName));

            caseDetails = apiDataProvider.getAPIDataProviderResponse("PartnerEnrollmentsCases", new Object[] {});

            Assert.assertTrue(assistedPaymentServicesBaseSteps.applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Case #", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("partner_case_id").toString(),1));
            Assert.assertTrue(assistedPaymentServicesBaseSteps.applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Community Name", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("community_name").toString().replaceAll("\"", ""),2));
            Assert.assertTrue(assistedPaymentServicesBaseSteps.applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Partner Type", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("pt_id").toString().replaceAll("\"", ""),3));
            Assert.assertTrue(assistedPaymentServicesBaseSteps.applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Partner Name", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("partner_name").toString().replaceAll("\"", ""),4));
            Assert.assertTrue(assistedPaymentServicesBaseSteps.applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Status", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("partner_case_status").toString(),5));
            Assert.assertTrue(assistedPaymentServicesBaseSteps.applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Outcome", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("partner_case_outcome_name").toString(),6));
            Assert.assertTrue(assistedPaymentServicesBaseSteps.applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Created At", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("partner_case_created_at").toString(),7));
            Assert.assertTrue(assistedPaymentServicesBaseSteps.applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Created By", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("partner_case_created_by").toString().replaceAll("\"", ""),8));
            Assert.assertTrue(assistedPaymentServicesBaseSteps.applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Assigned To", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("partner_case_assigned_to").toString(),9));

            //Back button should be enabled like other dashboards
            oBrowser.navigate().back();
            appDep.waitForTheElementState(oBrowser, By.xpath(PartnerEnrollmentsUIPage.obj_HomePage_Page + "//p"), "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PartnerEnrollmentsUIPage.obj_HomePage_Page + "//h2"), "Text", "Getting Started with PayCRM"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PartnerEnrollmentsUIPage.obj_HomePage_Page + "//p"), "Text", "Please select your Queue to start working."));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in ' validateFilterFunctionalityForTheColumnData()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateFilterFunctionalityForTheColumnData()' method. " + e);
            return false;
        }finally{caseDetails = null;}
    }




    /********************************************************
     * Method Name      : verifyLogActivityEmailPhoneFields()
     * Purpose          : user validate "Log Activity" fields on UI page
     * Author           : Deepak
     * Parameters       : oBrowser, queueName
     * ReturnType       : boolean
     ********************************************************/
    public boolean verifyLogActivityEmailPhoneFields(WebDriver oBrowser, String queueName){
        try{
            Assert.assertTrue(enrollmentManagersUIBaseSteps.selectRequiredQueue(oBrowser, queueName));
            Assert.assertTrue(enrollmentManagersUIBaseSteps.clickOnShowCaseButton(oBrowser, queueName));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PartnerEnrollmentsUIPage.obj_PartnerEnrollmentCases_Grid + "//input)[6]"), "New"));
            appDep.threadSleep(2000);
            Assert.assertNotNull(enrollmentManagersUIBaseSteps.readAndClickResultFirstCaseNumber(oBrowser, queueName));
            if(queueName.equalsIgnoreCase("Partner Enrollments")){
                return partnerEnrollmentsUIBaseStep.verifyLogActivityEmailPhoneFields(oBrowser);
            }
            else if(queueName.equalsIgnoreCase("Assisted Payment Services")){
                return true;
            }
            else{
                reports.writeResult(oBrowser, "FAIL", "Log Activity functionality is not applicable for given queue : "+queueName);
                Assert.assertTrue(false);
            }
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in ' verifyLogActivityEmailPhoneFields()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifyLogActivityEmailPhoneFields()' method. " + e);
            return false;
        }
    }




    /********************************************************
     * Method Name      : validateCommunitiesMultiSelect_AssignedToFilterFunctionality()
     * Purpose          : user validate Multiselect functionalities for the Communities grid & also 'Assogiend To' filter functionalities
     * Author           : Gudi
     * Parameters       : oBrowser, queueName
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateCommunitiesMultiSelect_AssignedToFilterFunctionality(WebDriver oBrowser, String queueName){
        int rowCount = 0;
        String communitiesName = "";
        String openCases = "";
        int caseCount = 0;
        String communitiesNotSelected = "";
        String arrCommunitiesName[];
        String arrOpenCases[];
        String arrCommunitiesNotSelected[];
        String assignedToEmail = "";
        String assignedToEmailNotSelected = "";
        String arrAssignedToEmailNotSelected[];
        String arrAssignedToEmail[];
        String assignedToCaseCount = "";
        String arrAssignedToCases[];
        int assignedCounts = 0;
        try{
            Assert.assertTrue(enrollmentManagersUIBaseSteps.selectRequiredQueue(oBrowser, queueName));
            Assert.assertTrue(enrollmentManagersUIBaseSteps.applyFiltersOnQueuesDashboard(oBrowser, queueName));
            Assert.assertTrue(enrollmentManagersUIBaseSteps.clickOnShowCaseButton(oBrowser, queueName));

            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PartnerEnrollmentsUIPage.obj_PartnerEnrollments_Link));
            Assert.assertTrue(appInd.clickObject(oBrowser, PartnerEnrollmentsUIPage.obj_PartnerEnrollments_Link));

            //Closed Case status toggle should be set off
            String closedCases = appInd.getTextOnElement(oBrowser, By.xpath(PartnerEnrollmentsUIPage.obj_CasesStatuses_Panel + "//li[contains(text(),'Closed')]/following-sibling::li[1]"), "Text");
            if(Integer.parseInt(closedCases)>0){
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PartnerEnrollmentsUIPage.obj_CasesStatuses_Panel + "//li[contains(text(),'Closed')]/following-sibling::li[2]/label")));
            }

            if(!oBrowser.findElement(EnrollmentsManagerUIPage.obj_Companies_Grid_Checkbox).isSelected())
                Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Companies_Grid_Checkbox));

            Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Companies_Grid_Checkbox));

            int max = 6;
            rowCount = oBrowser.findElements(By.xpath(PartnerEnrollmentsUIPage.obj_CommunitiesTable_Grid + "//tr")).size();
            if(rowCount > 6) rowCount = max;
            for(int i=1; i<rowCount-1; i++){
                String name = appInd.getTextOnElement(oBrowser, By.xpath(PartnerEnrollmentsUIPage.obj_CommunitiesTable_Grid + "//tr["+(i+1)+"]/td[2]"), "Text");
                String cases = appInd.getTextOnElement(oBrowser, By.xpath(PartnerEnrollmentsUIPage.obj_CommunitiesTable_Grid + "//tr["+(i+1)+"]/td[3]"), "Text");
                if(!name.isEmpty() && (Integer.parseInt(cases) > 0 && Integer.parseInt(cases) < 10)){
                    communitiesName+=name + "#";
                    openCases+=cases + "#";
                    caseCount += Integer.parseInt(cases);
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PartnerEnrollmentsUIPage.obj_CommunitiesTable_Grid + "//tr["+(i+1)+"]/td[1]")));
                }else{
                    communitiesNotSelected+=name + "#";
                }
            }
            reports.writeResult(oBrowser, "Screenshot", "Filter was applied for the Communities grid");

            //Click on the "Show Cases" button and validate the Cases grid for the selected Communities name and count
            communitiesName = communitiesName.substring(0, communitiesName.length()-1);
            openCases = openCases.substring(0, openCases.length()-1);
            Assert.assertTrue(appInd.clickObject(oBrowser, PartnerEnrollmentsUIPage.obj_ShowCases_btn));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_CaseNumber_Grid_Pagination, "Visibility", "", waitTimeOut);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_CaseNumber_Grid_Pagination));

            int records = Integer.parseInt((((appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_CaseNumber_Grid_Pagination, "Text").split("\\("))[1]).split(" "))[0].trim());
            Assert.assertTrue(appInd.compareValues(oBrowser, String.valueOf(records), String.valueOf(caseCount)), "Mis-match in No. of Actual '"+String.valueOf(records)+"' & expected '"+String.valueOf(caseCount)+"' record counts in the Cases grid for total Communities selected");

            arrCommunitiesName = communitiesName.split("#");
            arrOpenCases = openCases.split("#");
            arrCommunitiesNotSelected = communitiesNotSelected.split("#");

            //Verify that the selected Communities name must be displayed in the Cases grid
            for(int i=0; i<arrCommunitiesName.length; i++){
                Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PartnerEnrollmentsUIPage.obj_PartnerEnrollmentCases_Grid + "//input)[3]"), arrCommunitiesName[i]));
                appDep.threadSleep(2000);
                int rows = oBrowser.findElements(By.xpath(PartnerEnrollmentsUIPage.obj_PartnerEnrollmentCases_Grid + "//tr[contains(@class, 'lines dx-column-lines')]")).size();
                for(int j=0; j<rows; j++){
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PartnerEnrollmentsUIPage.obj_PartnerEnrollmentCases_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[2])["+(j+1)+"]"), "Text", arrCommunitiesName[i]));
                }
                records = Integer.parseInt((((appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_CaseNumber_Grid_Pagination, "Text").split("\\("))[1]).split(" "))[0].trim());
                Assert.assertTrue(appInd.compareValues(oBrowser, String.valueOf(records), String.valueOf(arrOpenCases[i])), "Mis-match in No. of Actual '"+String.valueOf(records)+"' & expected '"+String.valueOf(arrOpenCases[i])+"' record counts for the Community '"+arrCommunitiesName[i]+"'");
                reports.writeResult(oBrowser, "Screenshot", "Partner Enrollments Cases grid for the selected Community '"+arrCommunitiesName[i]+"' filter result values");
            }


            //The communities which are not selected should not be displayed in the Partners Enrollment cases grid
            for(int i=0; i<arrCommunitiesNotSelected.length; i++){
                Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PartnerEnrollmentsUIPage.obj_PartnerEnrollmentCases_Grid + "//input)[3]"), arrCommunitiesNotSelected[i]));
                appDep.threadSleep(2000);
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PartnerEnrollmentsUIPage.obj_PartnerEnrollmentCases_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[1]")), "The community which is not selected '"+arrCommunitiesNotSelected[i]+"' was also displayed");
                records = Integer.parseInt((((appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_CaseNumber_Grid_Pagination, "Text").split("\\("))[1]).split(" "))[0].trim());
                Assert.assertTrue(appInd.compareValues(oBrowser, String.valueOf(records), "0"), "Mis-match in No. of Actual '"+String.valueOf(records)+"' & expected '0' record counts for the not selected Community '"+arrCommunitiesName[i]+"'");
                reports.writeResult(oBrowser, "Screenshot", "Partner Enrollments Cases grid for the NON-selected Community '"+arrCommunitiesNotSelected[i]+"' filter result values");
            }



            //Validate "Assigned To" toggle filter
            //Reset all the filters back to normal
            Assert.assertTrue(appInd.clickObject(oBrowser, PartnerEnrollmentsUIPage.obj_PartnerEnrollments_Link));

            //Closed Case status toggle should be set on now
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PartnerEnrollmentsUIPage.obj_CasesStatuses_Panel + "//li[contains(text(),'Closed')]/following-sibling::li[2]/label")));

            if(!oBrowser.findElement(EnrollmentsManagerUIPage.obj_Companies_Grid_Checkbox).isSelected())
                Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Companies_Grid_Checkbox));

            int assignedToRecordsCount = oBrowser.findElements(By.xpath(PartnerEnrollmentsUIPage.obj_AssignedTo_Panel + "/div")).size();
            for(int i=0; i<assignedToRecordsCount; i++){
                String email = appInd.getTextOnElement(oBrowser, By.xpath("("+PartnerEnrollmentsUIPage.obj_AssignedTo_Panel + "/div)["+(i+1)+"]//ul/li[2]"), "Text");
                String cases = appInd.getTextOnElement(oBrowser, By.xpath("("+PartnerEnrollmentsUIPage.obj_AssignedTo_Panel + "/div)["+(i+1)+"]//ul/li[3]"), "Text");

                if(Integer.parseInt(cases) > 0 && Integer.parseInt(cases) < 10){
                    assignedToEmail+= email + "#";
                    assignedToCaseCount+= cases + "#";
                    assignedCounts+=Integer.parseInt(cases);
                }else{
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+PartnerEnrollmentsUIPage.obj_AssignedTo_Panel + "/div)["+(i+1)+"]//ul/li[4]/label")));
                    assignedToEmailNotSelected+=email + "#";
                }
            }
            reports.writeResult(oBrowser, "Screenshot", "Filter was applied for the 'Assigned To' panel");
            assignedToEmail = assignedToEmail.substring(0, assignedToEmail.length()-1);
            assignedToCaseCount = assignedToCaseCount.substring(0, assignedToCaseCount.length()-1);

            Assert.assertTrue(appInd.clickObject(oBrowser, PartnerEnrollmentsUIPage.obj_ShowCases_btn));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_CaseNumber_Grid_Pagination, "Visibility", "", waitTimeOut);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_CaseNumber_Grid_Pagination));

            //Verify the Partner Enrollment grid for the applied filters for the "Assigned To" column
            records = Integer.parseInt((((appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_CaseNumber_Grid_Pagination, "Text").split("\\("))[1]).split(" "))[0].trim());
            Assert.assertTrue(appInd.compareValues(oBrowser, String.valueOf(records), String.valueOf(assignedCounts)), "Mis-match in No. of Actual '"+String.valueOf(records)+"' & expected '"+String.valueOf(assignedCounts)+"' record counts in the Cases grid for the 'Assigned To' column");

            arrAssignedToEmail = assignedToEmail.split("#");
            arrAssignedToCases = assignedToCaseCount.split("#");
            for(int i=0; i<arrAssignedToEmail.length; i++){
                Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PartnerEnrollmentsUIPage.obj_PartnerEnrollmentCases_Grid + "//input)[11]"), arrAssignedToEmail[i]));
                appDep.threadSleep(2000);
                int rows = oBrowser.findElements(By.xpath(PartnerEnrollmentsUIPage.obj_PartnerEnrollmentCases_Grid + "//tr[contains(@class, 'lines dx-column-lines')]")).size();
                for(int j=0; j<rows; j++){
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PartnerEnrollmentsUIPage.obj_PartnerEnrollmentCases_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[9])["+(j+1)+"]"), "Text", arrAssignedToEmail[i]));
                }
                records = Integer.parseInt((((appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_CaseNumber_Grid_Pagination, "Text").split("\\("))[1]).split(" "))[0].trim());
                Assert.assertTrue(appInd.compareValues(oBrowser, String.valueOf(records), String.valueOf(arrAssignedToCases[i])), "Mis-match in No. of Actual '"+String.valueOf(records)+"' & expected '"+String.valueOf(arrAssignedToCases[i])+"' record counts for the 'Assigned To' column '"+arrAssignedToCases[i]+"'");
                reports.writeResult(oBrowser, "Screenshot", "Partner Enrollments Cases grid for the selected 'Assigned To' filter result values");
            }

            //Unselected 'Assigned To' records should not be displayed in the Partner Enrollment case grid
            arrAssignedToEmailNotSelected = assignedToEmailNotSelected.split("#");
            for(int i=0; i<arrAssignedToEmailNotSelected.length; i++){
                Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PartnerEnrollmentsUIPage.obj_PartnerEnrollmentCases_Grid + "//input)[11]"), arrAssignedToEmailNotSelected[i]));
                appDep.threadSleep(2000);
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PartnerEnrollmentsUIPage.obj_PartnerEnrollmentCases_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[1]")), "The 'Assigned To' which is not selected '"+arrAssignedToEmailNotSelected[i]+"' was also displayed");
                records = Integer.parseInt((((appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_CaseNumber_Grid_Pagination, "Text").split("\\("))[1]).split(" "))[0].trim());
                Assert.assertTrue(appInd.compareValues(oBrowser, String.valueOf(records), "0"), "Mis-match in No. of Actual '"+String.valueOf(records)+"' & expected '0' record counts for the not selected 'Assigned To' '"+arrAssignedToEmailNotSelected[i]+"'");
                reports.writeResult(oBrowser, "Screenshot", "Partner Enrollments Cases grid for the NON-selected 'Assigned To' '"+arrAssignedToEmailNotSelected[i]+"' filter result values");
            }
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in ' validateCommunitiesMultiSelect_AssignedToFilterFunctionality()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateCommunitiesMultiSelect_AssignedToFilterFunctionality()' method. " + e);
            return false;
        }finally {
            communitiesName = null; openCases = null; communitiesNotSelected = null; arrCommunitiesName = null; arrOpenCases = null; arrCommunitiesNotSelected = null; assignedToEmail = null;
            assignedToEmailNotSelected = null; arrAssignedToEmailNotSelected = null; arrAssignedToEmail = null; assignedToCaseCount = null; arrAssignedToCases = null;
        }
    }





    /********************************************************
     * Method Name      : validateCaseTypeAndPartnerTypesFilterFunctionality()
     * Purpose          : user validate 'Case Type' and 'Partner Type' filter functionalities
     * Author           : Gudi
     * Parameters       : oBrowser, queueName
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateCaseTypeAndPartnerTypesFilterFunctionality(WebDriver oBrowser, String queueName){
        String partnerTypeCount = "";
        String arrPartnerTypeCount[];
        String partnerTypeName = "";
        String arrPartnerTypeName[];
        int partnersCount = 0;
        int records = 0;
        String caseStatus = "";
        String arrCaseStatus[];
        String caseStatusCount = "";
        String arrCaseCount[];
        int casesCount = 0;
        int RowCount = 0;
        try{
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PartnerEnrollmentsUIPage.obj_PartnerEnrollments_Link));
            Assert.assertTrue(appInd.clickObject(oBrowser, PartnerEnrollmentsUIPage.obj_PartnerEnrollments_Link));
            Assert.assertTrue(enrollmentManagersUIBaseSteps.applyFiltersOnQueuesDashboard(oBrowser, queueName));

            //Apply filter to the 'Partner type'
            RowCount = oBrowser.findElements(By.xpath(PartnerEnrollmentsUIPage.obj_PartnerType_Items + "/div//li[@id]")).size();
            for(int i=0; i<RowCount; i++){
                if(i==0) {
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+PartnerEnrollmentsUIPage.obj_PartnerType_Items + "/div//li[@id]/following-sibling::li/label)["+(i+1)+"]")));
                }
                else{
                    String name = appInd.getTextOnElement(oBrowser, By.xpath("("+PartnerEnrollmentsUIPage.obj_PartnerType_Items + "/div//ul//li[2])["+(i+1)+"]"), "Text");
                    String count = appInd.getTextOnElement(oBrowser, By.xpath("("+PartnerEnrollmentsUIPage.obj_PartnerType_Items + "/div//ul//li[3])["+(i+1)+"]"), "Text");
                    partnerTypeName+= name+ "#";
                    partnerTypeCount+= count+ "#";
                    partnersCount+=Integer.parseInt(count);
                }
            }

            if(!oBrowser.findElement(EnrollmentsManagerUIPage.obj_Companies_Grid_Checkbox).isSelected())
                Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Companies_Grid_Checkbox));



            //click on "Show Cases" button
            Assert.assertTrue(appInd.clickObject(oBrowser, PartnerEnrollmentsUIPage.obj_ShowCases_btn));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_CaseNumber_Grid_Pagination, "Visibility", "", waitTimeOut);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_CaseNumber_Grid_Pagination));


            //Validate the 'Partner Type' filter result
            arrPartnerTypeName = partnerTypeName.split("#");
            arrPartnerTypeCount = partnerTypeCount.split("#");

            records = Integer.parseInt((((appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_CaseNumber_Grid_Pagination, "Text").split("\\("))[1]).split(" "))[0].trim());
            Assert.assertTrue(appInd.compareValues(oBrowser, String.valueOf(records), String.valueOf(partnersCount)), "Mis-match in No. of Actual '"+String.valueOf(records)+"' & expected '"+partnersCount+"' record counts for the Partner Type overall count");
            reports.writeResult(oBrowser, "Screenshot", "Partner Enrollments Cases grid for the Partner Type filter total count '"+partnersCount+"'");

            for(int i=0; i<arrPartnerTypeCount.length; i++){
                String partialText = null;
                if(arrPartnerTypeName[i].contains(" ")){
                    partialText = (arrPartnerTypeName[i].split(" "))[1];
                    Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PartnerEnrollmentsUIPage.obj_PartnerEnrollmentCases_Grid + "//input)[4]"), partialText));
                    appDep.threadSleep(2000);
                    records = Integer.parseInt((((appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_CaseNumber_Grid_Pagination, "Text").split("\\("))[1]).split(" "))[0].trim());
                    Assert.assertTrue(appInd.compareValues(oBrowser, String.valueOf(records), arrPartnerTypeCount[i]), "Mis-match in No. of Actual '"+String.valueOf(records)+"' & expected '"+arrPartnerTypeCount[i]+"' record counts for the Partner Type '"+arrPartnerTypeName[i]+"'");
                    reports.writeResult(oBrowser, "Screenshot", "Partner Enrollments Cases grid for the Partner Type '"+arrPartnerTypeName[i]+"' filter result values");
                }
            }

            if(!oBrowser.findElement(EnrollmentsManagerUIPage.obj_Companies_Grid_Checkbox).isSelected())
                Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Companies_Grid_Checkbox));

            //Apply filter for the 'Case Statuses' section
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PartnerEnrollmentsUIPage.obj_PartnerEnrollments_Link));
            Assert.assertTrue(appInd.clickObject(oBrowser, PartnerEnrollmentsUIPage.obj_PartnerEnrollments_Link));
            Assert.assertTrue(enrollmentManagersUIBaseSteps.applyFiltersOnQueuesDashboard(oBrowser, queueName));

            RowCount = oBrowser.findElements(By.xpath(PartnerEnrollmentsUIPage.obj_CasesStatuses_Panel + "/div//li[@id]")).size();
            for(int i=0; i<RowCount; i++){
                if((i%2)!=0) {
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+PartnerEnrollmentsUIPage.obj_CasesStatuses_Panel + "/div//li[@id]/following-sibling::li/label)["+(i+1)+"]")));
                }
                else{
                    String name = appInd.getTextOnElement(oBrowser, By.xpath("("+PartnerEnrollmentsUIPage.obj_CasesStatuses_Panel + "/div//ul//li[2])["+(i+1)+"]"), "Text");
                    String count = appInd.getTextOnElement(oBrowser, By.xpath("("+PartnerEnrollmentsUIPage.obj_CasesStatuses_Panel + "/div//ul//li[3])["+(i+1)+"]"), "Text");
                    caseStatus+= name+ "#";
                    caseStatusCount+= count+ "#";
                    casesCount+=Integer.parseInt(count);
                }
            }

            //click on "Show Cases" button
            Assert.assertTrue(appInd.clickObject(oBrowser, PartnerEnrollmentsUIPage.obj_ShowCases_btn));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_CaseNumber_Grid_Pagination, "Visibility", "", waitTimeOut);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_CaseNumber_Grid_Pagination));

            //Validate the 'Case Statuses' filter result
            arrCaseStatus = caseStatus.split("#");
            arrCaseCount = caseStatusCount.split("#");
            records = Integer.parseInt((((appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_CaseNumber_Grid_Pagination, "Text").split("\\("))[1]).split(" "))[0].trim());
            Assert.assertTrue(appInd.compareValues(oBrowser, String.valueOf(records), String.valueOf(casesCount)), "Mis-match in No. of Actual '"+String.valueOf(records)+"' & expected '"+casesCount+"' record counts for the 'Cases Statuses' overall count");
            reports.writeResult(oBrowser, "Screenshot", "Partner Enrollments Cases grid for the 'Cases Statuses' filter total count '"+casesCount+"'");

            for(int i=0; i<arrCaseStatus.length; i++){
                Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PartnerEnrollmentsUIPage.obj_PartnerEnrollmentCases_Grid + "//input)[6]"), arrCaseStatus[i]));
                appDep.threadSleep(2000);
                records = Integer.parseInt((((appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_CaseNumber_Grid_Pagination, "Text").split("\\("))[1]).split(" "))[0].trim());
                Assert.assertTrue(appInd.compareValues(oBrowser, String.valueOf(records), arrCaseCount[i]), "Mis-match in No. of Actual '"+String.valueOf(records)+"' & expected '"+arrCaseCount[i]+"' record counts for the 'Cases Statuses' '"+arrCaseStatus[i]+"'");
                reports.writeResult(oBrowser, "Screenshot", "Partner Enrollments Cases grid for the 'Cases Statuses' '"+arrCaseStatus[i]+"' filter result values");
            }
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in ' validateCaseTypeAndPartnerTypesFilterFunctionality()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateCaseTypeAndPartnerTypesFilterFunctionality()' method. " + e);
            return false;
        }finally {partnerTypeCount = null; arrPartnerTypeCount = null; partnerTypeName = null; arrPartnerTypeName = null; caseStatus = null; arrCaseStatus = null; caseStatusCount = null; arrCaseCount = null;
        }
    }




    /********************************************************
     * Method Name      : validatePartnerEnrollmentRoleAndPermission()
     * Purpose          : user validates the Roles/PErmission for Partner Enrollments
     * Author           : Gudi
     * Parameters       : oBrowser, permissionStatus
     * ReturnType       : boolean
     ********************************************************/
    public boolean validatePartnerEnrollmentRoleAndPermission(WebDriver oBrowser, String permissionStatus){
        try{
            Assert.assertTrue(appInd.clickObject(oBrowser, PartnerEnrollmentsUIPage.obj_payCRM_NavigateBar_Link));
            appDep.waitForTheElementState(oBrowser, PartnerEnrollmentsUIPage.obj_AssistedPaymentServices_Link, "Clickable", "", minTimeOut);
            oBrowser.navigate().refresh();
            appDep.waitForTheElementState(oBrowser, PartnerEnrollmentsUIPage.obj_AssistedPaymentServices_Link, "Clickable", "", minTimeOut);
            appDep.threadSleep(5000);
            oBrowser.navigate().refresh();
            appDep.waitForTheElementState(oBrowser, PartnerEnrollmentsUIPage.obj_AssistedPaymentServices_Link, "Clickable", "", minTimeOut);
            if(permissionStatus.equalsIgnoreCase("Before")){
                reports.writeResult(oBrowser, "Screenshot", "Before disabling the permission for 'Partner Enrollments'");
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PartnerEnrollmentsUIPage.obj_PartnerEnrollments_Label));
                Assert.assertTrue(appDep.selectManageModules(oBrowser, "Reports"));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PartnerEnrollmentsUIPage.obj_PartnerEnrollments_Report_Link));
                Assert.assertTrue(payCRM_Modules_ManageUIBaseStep.validatePartnerEnrollmentsReportFilters(oBrowser));
            }else{
                reports.writeResult(oBrowser, "Screenshot", "After disabling the permission for 'Partner Enrollments'");
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PartnerEnrollmentsUIPage.obj_PartnerEnrollments_Label));
                Assert.assertTrue(appDep.selectManageModules(oBrowser, "Reports"));
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PartnerEnrollmentsUIPage.obj_PartnerEnrollments_Report_Link));
            }
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in ' validatePartnerEnrollmentRoleAndPermission()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validatePartnerEnrollmentRoleAndPermission()' method. " + e);
            return false;
        }finally {
            Assert.assertTrue(enrollmentManagersUIBaseSteps.logoutFromTheApplication(oBrowser, "PayCRM"));
        }
    }


}
