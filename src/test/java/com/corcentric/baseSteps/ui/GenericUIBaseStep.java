package com.corcentric.baseSteps.ui;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.List;
import com.corcentric.pages.*;
import com.corcentric.runner.CucumberTestRunner;
import net.minidev.json.JSONArray;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import io.cucumber.datatable.DataTable;

public class GenericUIBaseStep extends CucumberTestRunner {
	String firstResultCaseNumber;
	private String assignTo;
	private String assignedUser;
	private WebDriver driver;
	private String strParentWndID;
	private String strChildWndID;
	private Map<String, String> attachmentData;
	private Map<String, String> editAttachmentData;
	private String newAddressOne;
	private String editAddressOne;
	private String paymentAccountNumber;
	private Map<String, String> objEnrollmentSiteDetails = null;
	
	
	
	/********************************************************
	 * Method Name      : reassignTheEnrollmentCaseForEnrollmentManager()
	 * Purpose          : to login to the required application
	 * Author           : Gudi
	 * Parameters       : oBrowser, dataTable
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean reassignTheEnrollmentCaseForEnrollmentsManager(WebDriver oBrowser, DataTable dataTable){
		List<Map<String, String>> reassignmentDetails = null;
		try{
			reassignmentDetails = dataTable.asMaps(String.class, String.class);
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_Reassign_Btn,"visibility","",waitTimeOut);
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Reassign_Btn), "clickObject() was failed for '"+String.valueOf(EnrollmentsManagerUIPage.obj_Reassign_Btn)+"' webelement");
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_ReassignCase_Dialog, "Text", "Reassign Case", waitTimeOut);
			reports.writeResult(oBrowser, "Screenshot", "The Enrollments Manager 'Reassign Case' dialog opened successful");

			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, EnrollmentsManagerUIPage.obj_ReassignCase_UserName_Label), "verifyElementPresent() was failed for '"+String.valueOf(EnrollmentsManagerUIPage.obj_ReassignCase_UserName_Label)+"' webelement");
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_ReassignCase_User)), "verifyElementPresent() was failed for '"+String.valueOf(By.xpath(EnrollmentsManagerUIPage.obj_ReassignCase_User))+"' webelement");
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, EnrollmentsManagerUIPage.obj_Assign_Btn), "verifyElementPresent() was failed for '"+String.valueOf(EnrollmentsManagerUIPage.obj_Assign_Btn)+"' webelement");

			assignedUser = appInd.createAndGetWebElement(oBrowser, EnrollmentsManagerUIPage.obj_ReassignCase_UserName_Label).getText();
			if(assignedUser.equalsIgnoreCase(reassignmentDetails.get(0).get("AssignedToUser1"))){
				assignTo = reassignmentDetails.get(0).get("AssignedToUser2");
			}else{
				assignTo = reassignmentDetails.get(0).get("AssignedToUser1");
			}
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_ReassignCase_UserName_Label), "clickObject() was failed for '"+ EnrollmentsManagerUIPage.obj_ReassignCase_UserName_Label +"' webelement");
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_ReassignCase_UserName_EditList, "Clickable", "", waitTimeOut);
			Assert.assertTrue(appInd.setObject(oBrowser, EnrollmentsManagerUIPage.obj_ReassignCase_UserName_EditList, assignTo), "setObject() was failed for '"+ EnrollmentsManagerUIPage.obj_ReassignCase_UserName_EditList +"' webelement");
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_ReassignCase_UserName_SearchResult_Label), "clickObject() was failed for '"+ EnrollmentsManagerUIPage.obj_ReassignCase_UserName_SearchResult_Label +"' webelement");
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_ReassignCase_User)));
			appDep.waitForTheElementState(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_ReassignCase_User + "//input"), "Clickable", "", minTimeOut);
			Assert.assertTrue(appInd.setObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_ReassignCase_User + "//input"), assignTo));
			appDep.threadSleep(1000);
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_ReassignCase_User + "//li")));
			reports.writeResult(oBrowser, "Screenshot", "The Enrollments Manager 'Reassign Case' details entered successful");
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Assign_Btn), "clickObject() was failed for '"+ EnrollmentsManagerUIPage.obj_Assign_Btn +"' webelement");
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_ReassignCase_Dialog, "Invisibility", "", waitTimeOut);
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, EnrollmentsManagerUIPage.obj_ReassignConfirmationMessage_Label, "Text").contains("Case has been successfully reassigned"), "Mis-match in actual & expected values for the '"+String.valueOf(EnrollmentsManagerUIPage.obj_ReassignConfirmationMessage_Label)+"' webelement");
			reports.writeResult(oBrowser, "Screenshot", "The post 'Reassign Case' details for Enrollments Manager");
			Assert.assertEquals(appInd.getTextOnElement(oBrowser, EnrollmentsManagerUIPage.obj_AssignedTo_Label, "Text").trim(), assignTo, "Mis-match in actual & expected values for the '"+String.valueOf(EnrollmentsManagerUIPage.obj_AssignedTo_Label)+"' webelement");
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'reassignTheEnrollmentCaseForEnrollmentsManager()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'reassignTheEnrollmentCaseForEnrollmentsManager()' method. " + e);
			return false;
		}finally{reassignmentDetails = null;}
	}


	
	
	/*****************************************************************
	 * Method Name      : selectRequiredQueue()
	 * Purpose          : to login to the required application
	 * Author           : Gudi
	 * Parameters       : oBrowser, queueName
	 * ReturnType       : boolean
	 *****************************************************************/
	public boolean selectRequiredQueue(WebDriver oBrowser, String queueName) {
		try {
			appDep.waitForTheElementState(oBrowser, PartnerEnrollmentsUIPage.obj_payCRM_NavigateBar_Link, "Clickable", "", minTimeOut);
			boolean blnRes = appInd.verifyOptionalElementPresent(oBrowser, PayCRMMainUIPage.obj_EnrollmentsManager_Label);
			if(!blnRes)
				Assert.assertTrue(appInd.clickObject(oBrowser, PartnerEnrollmentsUIPage.obj_payCRM_NavigateBar_Link));
			appDep.waitForTheElementState(oBrowser, PayCRMMainUIPage.obj_EnrollmentsManager_Label, "Clickable", "", waitTimeOut);
			switch (queueName.toLowerCase()){
				case "enrollments manager":
					Assert.assertTrue(appInd.clickObject(oBrowser, PayCRMMainUIPage.obj_EnrollmentsManager_Label), "clickObject() was failed for '"+String.valueOf(PayCRMMainUIPage.obj_EnrollmentsManager_Label)+"' webelement");
					appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_Companies_Table_Loading, "InVisibility", "", waitTimeOut);
					appDep.threadSleep(2000);
					Assert.assertTrue(appInd.verifyElementPresent(oBrowser, EnrollmentsManagerUIPage.obj_ShowCases_btn), "verifyElementPresent() was failed for '"+String.valueOf(EnrollmentsManagerUIPage.obj_ShowCases_btn)+"' webelement");
					Assert.assertTrue(appInd.verifyElementPresent(oBrowser, EnrollmentsManagerUIPage.obj_Enrollments_Clicked_tab), "verifyElementPresent() was failed for '"+String.valueOf(EnrollmentsManagerUIPage.obj_Enrollments_Clicked_tab)+"' webelement");
					Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_Payments_tab), "verifyElementPresent() was failed for '"+String.valueOf(AssistedPaymentServicesUIPage.obj_Payments_tab)+"' webelement");
					reports.writeResult(oBrowser, "Screenshot", "The 'Enrollments Manager' queue was clicked successful");
					break;
				case "partner enrollments":
					Assert.assertTrue(appInd.clickObject(oBrowser, PayCRMMainUIPage.obj_PartnerEnrollments_Label), "clickObject() was failed for '"+String.valueOf(PayCRMMainUIPage.obj_PartnerEnrollments_Label)+"' webelement");
					appDep.waitForTheElementState(oBrowser, PartnerEnrollmentsUIPage.obj_Communities_Grid, "Clickable", "", waitTimeOut);
					appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_ShowCases_btn, "Clickable", "", waitTimeOut);
					appDep.threadSleep(2000);
					Assert.assertTrue(appInd.verifyElementPresent(oBrowser, EnrollmentsManagerUIPage.obj_ShowCases_btn), "verifyElementPresent() was failed for '"+String.valueOf(EnrollmentsManagerUIPage.obj_ShowCases_btn)+"' webelement");
					reports.writeResult(oBrowser, "Screenshot", "The 'Partner Enrollments' queue was clicked successful");
					break;
				case "assisted payment services":
					Assert.assertTrue(appInd.clickObject(oBrowser, PayCRMMainUIPage.obj_AssistedPaymentServices_Label), "clickObject() was failed for '"+String.valueOf(PayCRMMainUIPage.obj_AssistedPaymentServices_Label)+"' webelement");
					appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_Companies_Table_Loading, "Invisibility", "", waitTimeOut);
					appDep.threadSleep(2000);
					Assert.assertTrue(appInd.verifyElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_Payments_Clicked_tab), "verifyElementPresent() was failed for '"+String.valueOf(AssistedPaymentServicesUIPage.obj_Payments_Clicked_tab)+"' webelement");
					Assert.assertTrue(appInd.verifyElementPresent(oBrowser, EnrollmentsManagerUIPage.obj_Enrollments_tab), "verifyElementPresent() was failed for '"+String.valueOf(EnrollmentsManagerUIPage.obj_Enrollments_tab)+"' webelement");
					Assert.assertTrue(appInd.verifyElementPresent(oBrowser, EnrollmentsManagerUIPage.obj_ShowCases_btn), "verifyElementPresent() was failed for '"+String.valueOf(EnrollmentsManagerUIPage.obj_ShowCases_btn)+"' webelement");
					reports.writeResult(oBrowser, "Screenshot", "The 'Assisted Payment Service' queue was clicked successful");
					break;
				case "rpa":
					Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_RPA_ModuleUIPage.obj_RPA_Label), "clickObject() was failed for '"+String.valueOf(PayCRM_RPA_ModuleUIPage.obj_RPA_Label)+"' webelement");
					appDep.waitForTheElementState(oBrowser, PartnerEnrollmentsUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
					appDep.threadSleep(2000);
					appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_RPA_ModuleUIPage.obj_RPA_Cases_Grid + "//tr[contains(@class, 'lines dx-column-lines')][1]/td[1]"));
					reports.writeResult(oBrowser, "Screenshot", "The 'RPA' queue was clicked successful");
					break;
				default:
					reports.writeResult(oBrowser, "Fail", "Invalid Queue '"+queueName+"' was provided.");
			}
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'selectRequiredQueue()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'selectRequiredQueue()' method. " + e);
			return false;
		}
    }


	
	
	/********************************************************
	 * Method Name      : clickOnShowCaseButton()
	 * Purpose          : to click on 'ShowCase' button
	 * Author           : Gudi
	 * Parameters       : oBrowser
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean clickOnShowCaseButton(WebDriver oBrowser, String queueName) {
		boolean blnSuccess = false;
		int count = 0;
		try {
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_ShowCases_btn, "Clickable", "", waitTimeOut);
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_CompaniesTable_Loading, "Invisibility", "", maxWaitTimeOut);
			if(queueName.equalsIgnoreCase("partner enrollments")){
				appDep.waitForTheElementState(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Communities_Grid + "//tr/td[1]"), "Clickable", "", waitTimeOut);
				String disabled = appInd.createAndGetWebElement(oBrowser, EnrollmentsManagerUIPage.obj_ShowCases_btn).getAttribute("class");
				if(disabled.contains("disable-submit")){
					Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Companies_Grid_Checkbox));
				}
			}else{
				appDep.waitForTheElementState(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Companies_Grid + "//tr/td[1]"), "Clickable", "", waitTimeOut);
				appDep.threadSleep(1000);
				if(!oBrowser.findElement(EnrollmentsManagerUIPage.obj_Companies_Grid_Checkbox).isSelected())
					Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, EnrollmentsManagerUIPage.obj_Companies_Grid_Checkbox));
			}

			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_ShowCases_btn), "clickObject() was failed for '"+String.valueOf(EnrollmentsManagerUIPage.obj_ShowCases_btn)+"' webelement");
			appDep.threadSleep(1000);

			appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_CaseNumber_Grid_Pagination, "Visibility", "", waitTimeOut);
			blnSuccess = appInd.verifyOptionalElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_CaseNumber_Grid_Pagination);
			if(!blnSuccess && count==0){
				reports.writeResult(oBrowser, "Screenshot", "Re try logic is invoked as the '"+queueName+"' queue cases page was not loaded properly");
				count++;
				oBrowser.navigate().refresh();
				appDep.threadSleep(2000);
				Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_payCRM_NavigateBar_Link));
				appDep.waitForTheElementState(oBrowser, PayCRMMainUIPage.obj_AssistedPaymentServices_Label, "Clickable", "", waitTimeOut);
				Assert.assertTrue(selectRequiredQueue(oBrowser, queueName));
				if(!queueName.equalsIgnoreCase("partner enrollments"))
					Assert.assertTrue(enrollmentManagersUIBaseSteps.applyFiltersOnQueuesDashboard(oBrowser, queueName));
				appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_ShowCases_btn, "Clickable", "", waitTimeOut);
				appDep.threadSleep(2000);
				if(queueName.equalsIgnoreCase("partner enrollments")){
					String disabled = appInd.createAndGetWebElement(oBrowser, EnrollmentsManagerUIPage.obj_ShowCases_btn).getAttribute("class");
					if(disabled.contains("disable-submit")){
						Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Companies_Grid_Checkbox));
					}
				}
				appDep.threadSleep(2000);
				Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_ShowCases_btn), "clickObject() was failed for '"+String.valueOf(EnrollmentsManagerUIPage.obj_ShowCases_btn)+"' webelement");
				appDep.threadSleep(2000);
			}

			appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
			appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_CaseNumber_Grid_Pagination, "Clickable", "", maxWaitTimeOut);
			appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_CaseNumber_Grid_Pagination, "Visibility", "", maxWaitTimeOut);
			blnSuccess = appInd.verifyOptionalElementPresent(oBrowser, AssistedPaymentServicesUIPage.obj_CaseNumber_Grid_Pagination);
			if(!blnSuccess){
				reports.writeResult(oBrowser, "Fail", "The '"+queueName+"' Case Grid was not loaded properly");
				Assert.assertTrue(false, "The '"+queueName+"' Case Grid was not loaded properly");
			}
			reports.writeResult(oBrowser, "Screenshot", "The 'Show Cases' button was clicked successful");
	        return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'clickOnShowCaseButton()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'clickOnShowCaseButton()' method. " + e);
			return false;
		}
	}
	
	
	
	
	
	/********************************************************
	 * Method Name      : readAndClickResultFirstCaseNumber()
	 * Purpose          : to read the first case number and click on it
	 * Author           : Gudi
	 * Parameters       : oBrowser
	 * ReturnType       : String
	 ********************************************************/
	public String readAndClickResultFirstCaseNumber(WebDriver oBrowser, String queueName) {
		String firstResultCaseNumber = null;
		try {
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_ResultCaseContainer_FirstCaseNumber, "Clickable", "", waitTimeOut);
			firstResultCaseNumber = appInd.createAndGetWebElement(oBrowser, EnrollmentsManagerUIPage.obj_ResultCaseContainer_FirstCaseNumber).getText();
	        Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_ResultCaseContainer_FirstCaseNumber), "clickObject() was failed for '"+String.valueOf(EnrollmentsManagerUIPage.obj_ResultCaseContainer_FirstCaseNumber)+"' webelement");
			oBrowser = switchToChildWindowAndReturnDriverObject(oBrowser);
			switch(queueName.toLowerCase()){
				case "enrollments manager":
					appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_WaveAndOffers_Header, "Text", "Wave & Offers", waitTimeOut);
					break;
				case "partner enrollments":
					appDep.waitForTheElementState(oBrowser, PartnerEnrollmentsUIPage.obj_EnrollmentActivities_Outcome_Column, "Visibility", "", waitTimeOut);
					break;
				case "assisted payment services":
					appDep.waitForTheElementState(oBrowser, PartnerEnrollmentsUIPage.obj_PaymentHelp_Header, "Text", "Payment Help", waitTimeOut);
			}

			reports.writeResult(oBrowser, "Screenshot", "The First 'Result Case' number row was clicked successful");
	        WebElement objCaseNumber = appInd.createAndGetWebElement(oBrowser, EnrollmentsManagerUIPage.obj_SectionCaseInfo_Label);
	        Assert.assertTrue(objCaseNumber.getText().trim().contains("Case "+firstResultCaseNumber), "Mis-match in actual & expected values for the '"+EnrollmentsManagerUIPage.obj_SectionCaseInfo_Label+"' webelement");
	        return firstResultCaseNumber;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'readAndClickResultFirstCaseNumber()' method. " + e);
			return null;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'readAndClickResultFirstCaseNumber()' method. " + e);
			return null;
		}
	}




	/********************************************************
	 * Method Name      : readAndClickRandomRowCaseNumber()
	 * Purpose          : to read the random row case number and click on it
	 * Author           : Gudi
	 * Parameters       : oBrowser
	 * ReturnType       : String, String queueName
	 ********************************************************/
	public String readAndClickRandomRowCaseNumber(WebDriver oBrowser, String queueName, String strObjGridLocator) {
		String firstResultCaseNumber = null;
		int randomNumbers = 0;
		int rowCount = 0;
		try {
			int maxRowNum = 5;
			int rowNum = oBrowser.findElements(By.xpath(strObjGridLocator)).size() -1;
			if(rowNum > maxRowNum) rowNum = maxRowNum;

			randomNumbers = Integer.parseInt(appInd.generateRandomNumbers(1, rowNum, 1));

			appDep.waitForTheElementState(oBrowser, By.xpath(strObjGridLocator + "[1]/td[1]"), "Clickable", "", waitTimeOut);
			firstResultCaseNumber = appInd.createAndGetWebElement(oBrowser, By.xpath(strObjGridLocator + "["+randomNumbers+"]/td[1]")).getText();
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(strObjGridLocator + "["+randomNumbers+"]/td[1]")), "clickObject() was failed for '"+String.valueOf(By.xpath(strObjGridLocator + "["+randomNumbers+"]/td[1]"))+"' webelement");
			oBrowser = switchToChildWindowAndReturnDriverObject(oBrowser);
			switch(queueName.toLowerCase()){
				case "enrollments manager":
					appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_WaveAndOffers_Header, "Text", "Wave & Offers", waitTimeOut);
					break;
				case "partner enrollments":
					appDep.waitForTheElementState(oBrowser, PartnerEnrollmentsUIPage.obj_EnrollmentActivities_Outcome_Column, "Visibility", "", waitTimeOut);
					break;
				case "assisted payment services":
					appDep.waitForTheElementState(oBrowser, PartnerEnrollmentsUIPage.obj_PaymentHelp_Header, "Text", "Payment Help", waitTimeOut);
			}

			reports.writeResult(oBrowser, "Screenshot", "The random 'Result Case' number row '"+randomNumbers+"' was clicked successful");
			WebElement objCaseNumber = appInd.createAndGetWebElement(oBrowser, EnrollmentsManagerUIPage.obj_SectionCaseInfo_Label);
			Assert.assertTrue(objCaseNumber.getText().trim().contains("Case "+firstResultCaseNumber), "Mis-match in actual & expected values for the '"+EnrollmentsManagerUIPage.obj_SectionCaseInfo_Label+"' webelement");
			return firstResultCaseNumber;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'readAndClickRandomRowCaseNumber()' method. " + e);
			return null;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'readAndClickRandomRowCaseNumber()' method. " + e);
			return null;
		}
	}



	/********************************************************
	 * Method Name : switchToChildWindowAndReturnDriverObject()
	 * Purpose :
	 * Author : Gudi
	 * Parameters : oBrowser
	 * ReturnType : boolean
	 ********************************************************/
	public WebDriver switchToChildWindowAndReturnDriverObject(WebDriver oBrowser){
		Object objWnds[];
		try{
			Thread.sleep(5000);
			objWnds = oBrowser.getWindowHandles().toArray();
			driver = oBrowser.switchTo().window(objWnds[objWnds.length -1].toString());
			return driver;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'switchToChildWindowAndReturnDriverObject()' method. " + e);
			return null;
		}finally{objWnds = null;}
	}




	/********************************************************
	 * Method Name : closeChildWindowAndSwitchBackToParentWindow()
	 * Purpose :
	 * Author : Gudi
	 * Parameters : oBrowser
	 * ReturnType : boolean
	 ********************************************************/
	public WebDriver closeChildWindowAndSwitchBackToParentWindow(WebDriver oBrowser){
		Object objWnds[];
		try{
			oBrowser.close();
			objWnds = oBrowser.getWindowHandles().toArray();
			driver = oBrowser.switchTo().window(objWnds[objWnds.length -1].toString());
			return driver;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'closeChildWindowAndSwitchBackToParentWindow()' method. " + e);
			return null;
		}
	}




	/********************************************************
	 * Method Name : switchBackToParentWindowWithoutClosingChildWindow()
	 * Purpose :
	 * Author : Gudi
	 * Parameters : oBrowser
	 * ReturnType : boolean
	 ********************************************************/
	public WebDriver switchBackToParentWindowWithoutClosingChildWindow(WebDriver oBrowser){
		Object objWnds[];
		try{
			objWnds = oBrowser.getWindowHandles().toArray();
			driver = oBrowser.switchTo().window(objWnds[objWnds.length -2].toString());
			return driver;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'switchBackToParentWindowWithoutClosingChildWindow()' method. " + e);
			return null;
		}
	}


	/********************************************************
	 * Method Name : switchBackToParentWindowWithoutClosingChildWindow()
	 * Purpose : In cases where multiple browser sessions are open and without closing any child browsers we need to switch back to required parent (among many) window
	 * Author : Gudi
	 * Parameters : oBrowser, childBrowserIndex
	 * ReturnType : boolean
	 ********************************************************/
	public WebDriver switchBackToParentWindowWithoutClosingChildWindow(WebDriver oBrowser, int childBrowserIndex){
		Object objWnds[];
		try{
			objWnds = oBrowser.getWindowHandles().toArray();
			driver = oBrowser.switchTo().window(objWnds[childBrowserIndex-1].toString());
			return driver;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'switchBackToParentWindowWithoutClosingChildWindow()' method. " + e);
			return null;
		}
	}



	
	/********************************************************
	 * Method Name      : verifyTheReassignmentDetailsUnderEnrollmentsManagerActivitiesTab()
	 * Purpose          : to verify the reassignment details under Activities tab
	 * Author           : Gudi
	 * Parameters       : oBrowser
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean verifyTheReassignmentDetailsUnderEnrollmentsManagerActivitiesTab(WebDriver oBrowser) {
		Calendar cal = null;
		try {
			//appInd.scrollToElement(oBrowser, PartnerEnrollmentsUIPage.obj_EnrollmentActivities_Pagination_Label);
			appInd.scrollToThePage(oBrowser, "Bottom");
			reports.writeResult(oBrowser, "Screenshot", "The Enrollments Manager 'Activities' tab details");
	        Assert.assertTrue(appInd.verifyText(oBrowser, EnrollmentsManagerUIPage.obj_EnrollmentActivities_Activity_Column, "Text", "Reassignment by "+appInd.getPropertyValueByKeyName("qaUserName")), "Mis-match in actual & expected values for the '"+String.valueOf(EnrollmentsManagerUIPage.obj_EnrollmentActivities_Activity_Column)+"' webelement");
	        Assert.assertTrue(appInd.verifyText(oBrowser, EnrollmentsManagerUIPage.obj_EnrollmentActivities_ActivityType_Column, "Text", "Case Update"), "Mis-match in actual & expected values for the '"+String.valueOf(EnrollmentsManagerUIPage.obj_EnrollmentActivities_ActivityType_Column)+"' webelement");
	        Assert.assertTrue(appInd.verifyText(oBrowser, EnrollmentsManagerUIPage.obj_EnrollmentActivities_CreatedBy_Column, "Text", appInd.getPropertyValueByKeyName("qaUserName")), "Mis-match in actual & expected values for the '"+String.valueOf(EnrollmentsManagerUIPage.obj_EnrollmentActivities_CreatedBy_Column)+"' webelement");

			cal = Calendar.getInstance();
			String dateCreated = String.valueOf((cal.get(Calendar.MONTH)+1))+"/"+String.valueOf(cal.get(Calendar.DAY_OF_MONTH))+"/"+String.valueOf(cal.get(Calendar.YEAR));
			Assert.assertTrue(appInd.verifyText(oBrowser, EnrollmentsManagerUIPage.obj_EnrollmentActivities_DateCreated_Column, "Text", dateCreated), "Mis-match in actual & expected values for the '"+String.valueOf(EnrollmentsManagerUIPage.obj_EnrollmentActivities_DateCreated_Column)+"' webelement");
	        Assert.assertTrue(appInd.verifyText(oBrowser, EnrollmentsManagerUIPage.obj_EnrollmentActivities_Notes_Column, "Text", "Queue Owner reassigned case to "+ assignTo +" in work queue Enrollments."), "Mis-match in actual & expected values for the '"+String.valueOf(EnrollmentsManagerUIPage.obj_EnrollmentActivities_Notes_Column)+"' webelement");
	        return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'verifyTheReassignmentDetailsUnderEnrollmentsManagerActivitiesTab()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifyTheReassignmentDetailsUnderEnrollmentsManagerActivitiesTab()' method. " + e);
			return false;
		}finally{cal = null;}
	}
	
	
	
	
	/********************************************************
	 * Method Name      : clickOnActivitiesTab()
	 * Purpose          : to click on Activities tab
	 * Author           : Gudi
	 * Parameters       : oBrowser
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean clickOnActivitiesTab(WebDriver oBrowser, String queueName) {
		try {
			if(queueName.equalsIgnoreCase("Assisted Payment Services")){
				appInd.scrollToElement(oBrowser, AssistedPaymentServicesUIPage.obj_SplitterHorizonal_Line);
			}
			//appInd.scrollToElement(oBrowser, AssistedPaymentServicesUIPage.obj_Activities_Tab);
			Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_Activities_Tab), "clickObject() was failed for '"+String.valueOf(EnrollmentsManagerUIPage.obj_Activities_Tab)+"' webelement");

			switch(queueName.toLowerCase()){
				case "enrollments manager":
					appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_EnrollmentActivities_Grid, "Clickable", "", waitTimeOut);
					break;
				case "partner enrollments":
					appDep.waitForTheElementState(oBrowser, PartnerEnrollmentsUIPage.obj_EnrollmentActivities_Outcome_Column, "Visibility", "", waitTimeOut);
					break;
				case "assisted payment services":
					appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_Activities_Grid_Header, "Visibility", "", waitTimeOut);
			}

	        return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'clickOnActivitiesTab()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'clickOnActivitiesTab()' method. " + e);
			return false;
		}
	}



	/********************************************************
	 * Method Name      : clickOnLogsTab()
	 * Purpose          : to click on Logs tab
	 * Author           : Gudi
	 * Parameters       : oBrowser
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean clickOnLogsTab(WebDriver oBrowser) {
		try {
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Logs_Tab), "clickObject() was failed for '"+String.valueOf(EnrollmentsManagerUIPage.obj_Logs_Tab)+"' webelement");
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_EnrollmentLogs_UserColumn, "Clickable", "", waitTimeOut);
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'clickOnLogsTab()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'clickOnLogsTab()' method. " + e);
			return false;
		}
	}




	/********************************************************
	 * Method Name      : verifyTheEnrollmentDetailsUnderLogsTab()
	 * Purpose          : to verify the enrollment details under Logs tab grid
	 * Author           : Gudi
	 * Parameters       : oBrowser
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean verifyTheEnrollmentDetailsUnderLogsTab(WebDriver oBrowser) {
		try {
			reports.writeResult(oBrowser, "Screenshot", "The 'Logs' tab details");
			Assert.assertTrue(appInd.verifyText(oBrowser, EnrollmentsManagerUIPage.obj_EnrollmentLogs_UserColumn, "Text", appInd.getPropertyValueByKeyName("qaUserName")), "Mis-match in actual & expected values for the '"+String.valueOf(EnrollmentsManagerUIPage.obj_EnrollmentLogs_UserColumn)+"' webelement");
			Assert.assertTrue(appInd.verifyText(oBrowser, EnrollmentsManagerUIPage.obj_EnrollmentLogs_ActionColumn, "Text", "Updated Assigned to from " + assignedUser + " to " + assignTo), "Mis-match in actual & expected values for the '"+String.valueOf(EnrollmentsManagerUIPage.obj_EnrollmentLogs_ActionColumn)+"' webelement");
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'verifyTheEnrollmentDetailsUnderLogsTab()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifyTheEnrollmentDetailsUnderLogsTab()' method. " + e);
			return false;
		}
	}




	/********************************************************
	 * Method Name      : logoutFromTheApplication()
	 * Purpose          : to logoff from the given (PayCRM/PayNet) application
	 * Author           : Gudi
	 * Parameters       : oBrowser
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean logoutFromTheApplication(WebDriver oBrowser, String appName){
		try{
			switch (appName.toLowerCase()){
				case "paycrm":
					Assert.assertTrue(appInd.clickObject(oBrowser, PayCRMMainUIPage.obj_Logout_Navbar_List), "clickObject() was failed for the '"+ PayCRMMainUIPage.obj_Logout_Navbar_List +"' webelement");
					Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(String.format(PayCRMMainUIPage.obj_Logout_Link,"Logout"))), "clickObject() was failed for the '"+ PayCRMMainUIPage.obj_Logout_Link +"' webelement");
					appDep.waitForTheElementState(oBrowser, PayCRMMainUIPage.obj_LOGIN_btn, "Clickable", "", waitTimeOut);
					break;
				case "paynet":
					break;
				default:
					reports.writeResult(oBrowser, "Fail", "Invalid application name '"+appName+"' was provided.");
					return false;
			}
			reports.writeResult(oBrowser, "Screenshot", "The logout from the application '" + appName + "' successful");
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'logoutFromTheApplication()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'logoutFromTheApplication()' method. " + e);
			return false;
		}
	}




	/********************************************************
	 * Method Name      : addAttachments()
	 * Purpose          : User adds the attachment to the Enrollment cases for the selected queue
	 * Author           : Gudi
	 * Parameters       : oBrowser, queueName
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean addAttachments(WebDriver oBrowser, String queueName, DataTable dataTable){
		List<Map<String, String>> attachmentDetails = null;
		String timeStamp = null;
		try{
			Assert.assertTrue(selectRequiredQueue(oBrowser, queueName));
			Assert.assertTrue(clickOnShowCaseButton(oBrowser, queueName));
			this.firstResultCaseNumber = readAndClickResultFirstCaseNumber(oBrowser, queueName);
			Assert.assertNotNull(this.firstResultCaseNumber);

			timeStamp = appInd.getDateTime("YYYYShhmmss");
			attachmentDetails = dataTable.asMaps(String.class, String.class);

			attachmentData = new HashMap<>();
			attachmentData.put("Name", attachmentDetails.get(0).get("Name")+"_"+timeStamp);
			attachmentData.put("Description", attachmentDetails.get(0).get("Description"));
			attachmentData.put("AttachmentType", attachmentDetails.get(0).get("AttachmentType"));
			attachmentData.put("Status", attachmentDetails.get(0).get("Status"));
			attachmentData.put("UploadFileName", attachmentDetails.get(0).get("UploadFileName"));

			appInd.scrollToElement(oBrowser, EnrollmentsManagerUIPage.obj_SplitterHorizonal_Line);
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Attachments_Tab), "clickObject() was failed for the '"+EnrollmentsManagerUIPage.obj_Attachments_Tab+"' webelement");
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.Obj_Attachments_Add_Btn, "Clickable", "", waitTimeOut);
			appDep.threadSleep(2000);
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.Obj_Attachments_Add_Btn), "clickObject() was failed for the '"+EnrollmentsManagerUIPage.Obj_Attachments_Add_Btn+"' webelement");
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.Obj_Attachments_Header, "Clickable", "", waitTimeOut);
			reports.writeResult(oBrowser, "Screenshot", "Before entering the Attachment details");
			Assert.assertTrue(appInd.verifyText(oBrowser, EnrollmentsManagerUIPage.Obj_Attachments_Header, "Text", "New Attachment"));
			Assert.assertTrue(appInd.setObject(oBrowser, EnrollmentsManagerUIPage.Obj_Attachments_Name_Edit, attachmentData.get("Name")), "setObject() was failed for '"+EnrollmentsManagerUIPage.Obj_Attachments_Name_Edit+"' webelement");
			Assert.assertTrue(appInd.setObject(oBrowser, EnrollmentsManagerUIPage.Obj_Attachments_Description_Textarea, attachmentData.get("Description")), "setObject() was failed for '"+EnrollmentsManagerUIPage.Obj_Attachments_Description_Textarea+"' webelement");
			Assert.assertTrue(appInd.selectObject(oBrowser, EnrollmentsManagerUIPage.obj_attachments_AttachmentType_List, attachmentData.get("AttachmentType")), "selectObject() was failed for '"+EnrollmentsManagerUIPage.obj_attachments_AttachmentType_List+"' webelement");
			Assert.assertTrue(appInd.selectObject(oBrowser, EnrollmentsManagerUIPage.obj_Attachments_Status_List, attachmentData.get("Status")), "selectObject() was failed for '"+EnrollmentsManagerUIPage.obj_Attachments_Status_List+"' webelement");
			appInd.setObject(oBrowser, EnrollmentsManagerUIPage.obj_FileUpload_Edit,System.getProperty("user.dir").replace("\\", "/") + "/TestData/" + attachmentData.get("UploadFileName") + ".docx");
			reports.writeResult(oBrowser, "Screenshot", "After entering the Attachment details");
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.Obj_Attachments_Create_Btn, "Clickable", "", waitTimeOut);
			appDep.threadSleep(4000);
			Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, EnrollmentsManagerUIPage.Obj_Attachments_Create_Btn), "javaScriptClickObject() was failed for the '"+EnrollmentsManagerUIPage.Obj_Attachments_Create_Btn+"' webelement");
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_ReassignConfirmationMessage_Label, "Visibility", "", waitTimeOut);
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, EnrollmentsManagerUIPage.obj_ReassignConfirmationMessage_Label, "Text").contains("Attachment has been successfully created"), "getTextOnElement() was failed for the '"+EnrollmentsManagerUIPage.obj_ReassignConfirmationMessage_Label+"' webelement");
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'addAttachments()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'addAttachments()' method. " + e);
			return false;
		}finally{attachmentDetails = null; timeStamp = null;}
	}




	/********************************************************
	 * Method Name      : verifyTheAttachmentDetails()
	 * Purpose          : user verifies the attachment details for the selected queue
	 * Author           : Gudi
	 * Parameters       : oBrowser, queueName
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean verifyTheAttachmentDetails(WebDriver oBrowser, String attachmentAction, String queueName){
		WebElement objEle = null;
		Map<String, String> validateAttachment = null;
		try{
			appDep.threadSleep(2000);
			validateAttachment = new HashMap<String, String>();
			switch(attachmentAction.toLowerCase()){
				case "add":
					validateAttachment = attachmentData;
					break;
				case "edit":
					validateAttachment = editAttachmentData;
					break;
				default:
					reports.writeResult(oBrowser, "Fail", "Invalid attachment Action '"+attachmentAction+"' was specified. The supported one are 'Add' & 'Edit'");
					return false;
			}


			//Search The attachment name
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, EnrollmentsManagerUIPage.obj_Attachments_Name_Edit, validateAttachment.get("Name")));
			appDep.threadSleep(4000);
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_Attachments_Pagination, "Visibility", "", waitTimeOut);
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.Obj_Attachments_Grid, "Visibility", "", waitTimeOut);
			reports.writeResult(oBrowser, "Screenshot", "After "+attachmentAction+"ing Attachment");
			objEle = appInd.createAndGetWebElement(oBrowser, EnrollmentsManagerUIPage.Obj_Attachments_Grid);
			Assert.assertTrue(objEle.findElement(By.xpath("//tr/td[text()='"+validateAttachment.get("Name")+"']")).getText().equals(validateAttachment.get("Name")), "Mis-match in actual & expected values for Attachments 'Name' column");
			Assert.assertTrue(objEle.findElement(By.xpath("//tr/td[text()='"+validateAttachment.get("Name")+"']/following-sibling::td[1]")).getText().equals(validateAttachment.get("Description")), "Mis-match in actual & expected values for Attachments 'Description' column");
			Assert.assertTrue(objEle.findElement(By.xpath("//tr/td[text()='"+validateAttachment.get("Name")+"']/following-sibling::td[2]")).getText().equals(validateAttachment.get("AttachmentType")), "Mis-match in actual & expected values for Attachments 'Attachment Type' column");

			if(attachmentAction.equalsIgnoreCase("Edit") && validateAttachment.get("AttachToSupplier").equalsIgnoreCase("Yes")){
				objEle = appInd.createAndGetWebElement(oBrowser, EnrollmentsManagerUIPage.Obj_Attachments_Grid);
				Assert.assertTrue(appInd.verifyElementPresent(oBrowser, objEle.findElement(By.xpath("//tr/td[text()='"+validateAttachment.get("Name")+"']/following-sibling::td/i[@title='Supplier Attachment']"))), "verifyElementPresent() was failed for 'Supplier Attachment' icon");
			}

			Assert.assertTrue(verifyAttachmentDetailsUnderShowSection(oBrowser, attachmentAction, queueName));
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'verifyTheAttachmentDetails()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifyTheAttachmentDetails()' method. " + e);
			return false;
		}finally{objEle = null; validateAttachment = null;}
	}




	/********************************************************
	 * Method Name      : verifyAttachmentDetailsUnderShowSection()
	 * Purpose          : user verifies the attachment details under Show section for the selected queue
	 * Author           : Gudi
	 * Parameters       : oBrowser, attachmentAction, queueName
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean verifyAttachmentDetailsUnderShowSection(WebDriver oBrowser, String attachmentAction, String queueName){
		WebElement objAttachmentsGrid;
		WebDriverWait oWait;
		Map<String, String> validateAttachment = null;
		try{
			switch(attachmentAction.toLowerCase()){
				case "add":
					validateAttachment = attachmentData;
					break;
				case "edit":
					validateAttachment = editAttachmentData;
					break;
				default:
					reports.writeResult(oBrowser, "Fail", "Invalid attachment Action '"+attachmentAction+"' was specified. The supported one are 'Add' & 'Edit'");
					return false;
			}
			oWait = new WebDriverWait(oBrowser, Duration.ofSeconds(waitTimeOut));
			objAttachmentsGrid = appInd.createAndGetWebElement(oBrowser, EnrollmentsManagerUIPage.Obj_Attachments_Grid);
			oWait.until(ExpectedConditions.elementToBeClickable(objAttachmentsGrid.findElement(By.xpath("//tr/td[text()='"+validateAttachment.get("Name")+"']/following-sibling::td/a[@title='Show']"))));
			Assert.assertTrue(appInd.clickObject(oBrowser, objAttachmentsGrid.findElement(By.xpath("//tr/td[text()='"+validateAttachment.get("Name")+"']/following-sibling::td/a[@title='Show']"))), "clickObject() was failed for the 'Show' Attachments");
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_Attachments_ShowAttachment_Header, "Visibility", "", waitTimeOut);

			//objShowAttachmentGrid = appInd.createAndGetWebElement(oBrowser, EnrollmentsManagerUIPage.obj_ShowAttachment_Grid);
			Assert.assertTrue(appInd.verifyText(oBrowser, EnrollmentsManagerUIPage.obj_ShowAttachment_Name_Label, "Text", validateAttachment.get("Name")), "Mis-match in actual & expected values for attachment 'Name' in show grid");
			Assert.assertTrue(appInd.verifyText(oBrowser, EnrollmentsManagerUIPage.obj_ShowAttachment_Description_Label, "Text", validateAttachment.get("Description")), "Mis-match in actual & expected values for attachment 'Description' in show grid");
			Assert.assertTrue(appInd.verifyText(oBrowser, EnrollmentsManagerUIPage.obj_ShowAttachment_Status_Label, "Text", validateAttachment.get("Status")), "Mis-match in actual & expected values for attachment 'Status' in show grid");
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, EnrollmentsManagerUIPage.obj_ShowAttachment_FileName_Label, "Text").contains(validateAttachment.get("UploadFileName")), "Mis-match in actual & expected values for attachment 'UploadFileName' in show grid");
			Assert.assertTrue(appInd.verifyText(oBrowser, EnrollmentsManagerUIPage.obj_ShowAttachment_AttachmentType_Label, "Text", validateAttachment.get("AttachmentType")), "Mis-match in actual & expected values for attachment 'AttachmentType' in show grid");
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, EnrollmentsManagerUIPage.obj_ShowAttachment_AttachedTo_Label, "Text").contains("Case #"+firstResultCaseNumber), "Mis-match in actual & expected values for attachment 'Case #' in show grid");
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_ShowAttachments_Close_Btn), "clickObject() was failed for the '"+EnrollmentsManagerUIPage.obj_ShowAttachments_Close_Btn+"' webelement");
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_Attachments_ShowAttachment_Header, "Invisibility", "", waitTimeOut);
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'verifyAttachmentDetailsUnderShowSection()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifyAttachmentDetailsUnderShowSection()' method. " + e);
			return false;
		}finally{
			objAttachmentsGrid = null; oWait = null;
		}
	}




	/********************************************************
	 * Method Name      : editTheAttachment()
	 * Purpose          : user edits the attachment details for the selected queue
	 * Author           : Gudi
	 * Parameters       : oBrowser, queueName, dataTable
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean editTheAttachment(WebDriver oBrowser, String queueName, DataTable dataTable){
		WebElement objAttachmentsGrid = null;
		WebDriverWait oWait = null;
		List<Map<String, String>> editAttachmentDetails = null;
		String timeStamp = null;
		try{
			timeStamp = appInd.getDateTime("YYYYShhmmss");
			editAttachmentDetails = dataTable.asMaps(String.class, String.class);
			editAttachmentData = new HashMap<String, String>();
			editAttachmentData.put("Name", editAttachmentDetails.get(0).get("Name")+"_"+timeStamp);
			editAttachmentData.put("Description", editAttachmentDetails.get(0).get("Description"));
			editAttachmentData.put("AttachmentType", editAttachmentDetails.get(0).get("AttachmentType"));
			editAttachmentData.put("Status", editAttachmentDetails.get(0).get("Status"));
			editAttachmentData.put("UploadFileName", editAttachmentDetails.get(0).get("UploadFileName"));
			editAttachmentData.put("AttachToSupplier", editAttachmentDetails.get(0).get("AttachToSupplier"));

			oWait = new WebDriverWait(oBrowser, Duration.ofSeconds(waitTimeOut));
			objAttachmentsGrid = appInd.createAndGetWebElement(oBrowser, EnrollmentsManagerUIPage.Obj_Attachments_Grid);
			oWait.until(ExpectedConditions.elementToBeClickable(objAttachmentsGrid.findElement(By.xpath("//tr/td[text()='"+attachmentData.get("Name")+"']/following-sibling::td/a[@title='Edit']"))));
			Assert.assertTrue(appInd.clickObject(oBrowser, objAttachmentsGrid.findElement(By.xpath("//tr/td[text()='"+attachmentData.get("Name")+"']/following-sibling::td/a[@title='Edit']"))), "clickObject() was failed for the 'Show' Attachments");
			appDep.threadSleep(2000);
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_Attachments_EditAttachment_Header, "Visibility", "", waitTimeOut);
			Assert.assertTrue(appInd.verifyText(oBrowser, EnrollmentsManagerUIPage.obj_Attachments_EditAttachment_Header, "Text", "Edit Attachment"));
			reports.writeResult(oBrowser, "Screenshot", "Before editing the Attachment details");
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, EnrollmentsManagerUIPage.Obj_Attachments_Name_Edit, editAttachmentData.get("Name")), "setObject() was failed for '"+EnrollmentsManagerUIPage.Obj_Attachments_Name_Edit+"' webelement");
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, EnrollmentsManagerUIPage.Obj_Attachments_Description_Textarea, editAttachmentData.get("Description")), "setObject() was failed for '"+EnrollmentsManagerUIPage.Obj_Attachments_Description_Textarea+"' webelement");
			Assert.assertTrue(appInd.selectObject(oBrowser, EnrollmentsManagerUIPage.obj_attachments_AttachmentType_List, editAttachmentData.get("AttachmentType")), "selectObject() was failed for '"+EnrollmentsManagerUIPage.obj_attachments_AttachmentType_List+"' webelement");
			Assert.assertTrue(appInd.selectObject(oBrowser, EnrollmentsManagerUIPage.obj_Attachments_Status_List, editAttachmentData.get("Status")), "selectObject() was failed for '"+EnrollmentsManagerUIPage.obj_Attachments_Status_List+"' webelement");
			appInd.setObject(oBrowser, EnrollmentsManagerUIPage.obj_FileUpload_Edit,System.getProperty("user.dir").replace("\\", "/") + "/TestData/" + editAttachmentData.get("UploadFileName") + ".docx");
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.Obj_Attachments_Create_Btn, "Clickable", "", waitTimeOut);
			appDep.threadSleep(4000);
			appDep.waitForTheElementState(oBrowser, By.xpath("//div/a[text()='"+attachmentData.get("UploadFileName") + ".docx"+"']"), "Clickable", "", waitTimeOut);
			Assert.assertTrue(appInd.verifyText(oBrowser, EnrollmentsManagerUIPage.obj_EditAttachments_FileName_Edit, "Value", editAttachmentData.get("UploadFileName") + ".docx"), "verifyText() was failed for the '"+EnrollmentsManagerUIPage.obj_EditAttachments_FileName_Edit+"' webelement");

			if(editAttachmentData.get("AttachToSupplier").equalsIgnoreCase("Yes") && appInd.createAndGetWebElement(oBrowser, EnrollmentsManagerUIPage.obj_Attachments_AttachToSupplierNo_Btn).isDisplayed()){
				Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Attachments_AttachToSupplierYes_Btn), "clickObject() was failed for the '"+EnrollmentsManagerUIPage.obj_Attachments_AttachToSupplierYes_Btn+"' webelement");
			}
			reports.writeResult(oBrowser, "Screenshot", "After editing the Attachment details");
			appDep.threadSleep(4000);
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.Obj_Attachments_Create_Btn));
			appDep.threadSleep(4000);
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_ReassignConfirmationMessage_Label, "Visibility", "", waitTimeOut);
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_ConfirmationMessage_close_Btn, "Clickable", "", waitTimeOut);
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, EnrollmentsManagerUIPage.obj_ReassignConfirmationMessage_Label, "Text").contains("Attachment has been successfully updated"), "getTextOnElement() was failed for the '"+EnrollmentsManagerUIPage.obj_ReassignConfirmationMessage_Label+"' webelement");
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'editTheAttachment()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'editTheAttachment()' method. " + e);
			return false;
		}finally{
			objAttachmentsGrid = null; oWait = null; editAttachmentDetails = null; timeStamp = null;
		}
	}




	/********************************************************
	 * Method Name      : userLoginToApplication()
	 * Purpose          : Login to the application
	 * Author           : Gudi
	 * Parameters       : oBrowser, queueName
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean userLoginToApplication(WebDriver oBrowser, String appName, String qaDetails){
		String arrDetails[];
		try{
			arrDetails = appInd.getPropertyValueByKeyName(qaDetails).split("#", -1);
			if(oBrowser!=null){
				Assert.assertTrue(appDep.navigateURL(oBrowser, arrDetails[0], appName), "URL navigation is failed for '"+arrDetails[0]+"' URL");
				Assert.assertTrue(appDep.loginToApplication(oBrowser, arrDetails[1], arrDetails[2], appName), "Login was failed for the userName '"+arrDetails[1]+"'");
				reports.writeResult(oBrowser, "Screenshot", "Login to the '"+appName+"' was successful");
				return true;
			}else{
				Assert.assertTrue(false, "Failed to launch the '"+ appInd.browserName+"' browser");
				return false;
			}
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'userLoginToApplication()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'userLoginToApplication()' method. " + e);
			return false;
		}finally {arrDetails = null;}
	}




	/********************************************************
	 * Method Name      : loginToPayCRMWithAnotherUser()
	 * Purpose          : Login to the application
	 * Author           : Gudi
	 * Parameters       : oBrowser, queueName
	 * ReturnType       : boolean
	 ********************************************************/
	public WebDriver loginToPayCRMWithAnotherUser(String appName, String qaDetails){
		String arrDetails[];
		WebDriver oDriver = null;
		try{
			oDriver = appInd.launchBrowser();
			arrDetails = appInd.getPropertyValueByKeyName(qaDetails).split("#", -1);
			if(oDriver!=null){
				Assert.assertTrue(appDep.navigateURL(oDriver, arrDetails[0], appName), "URL navigation is failed for '"+arrDetails[0]+"' URL");
				Assert.assertTrue(appDep.loginToApplication(oDriver, arrDetails[1], arrDetails[2], appName), "Login was failed for the userName '"+arrDetails[1]+"'");
				reports.writeResult(oDriver, "Screenshot", "Login to the '"+appName+"' was successful");
				return oDriver;
			}else{
				Assert.assertTrue(false, "Failed to launch the '"+ appInd.browserName+"' browser");
				return null;
			}
		}catch(Exception e) {
			reports.writeResult(oDriver, "Exception", "Exception in 'loginToPayCRMWithAnotherUser()' method. " + e);
			return null;
		}catch(AssertionError e) {
			reports.writeResult(oDriver, "Exception", "AssertionError in 'loginToPayCRMWithAnotherUser()' method. " + e);
			return null;
		}finally {arrDetails = null;}
	}




	/********************************************************
	 * Method Name      : reassignTheEnrollmentCase()
	 * Purpose          : user reassign the user to the enrollment case
	 * Author           : Gudi
	 * Parameters       : oBrowser, queueName, dataTable
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean reassignTheEnrollmentCase(WebDriver oBrowser, String queueName, DataTable dataTable){
		try{
			Assert.assertTrue(selectRequiredQueue(oBrowser, queueName));
			Assert.assertTrue(clickOnShowCaseButton(oBrowser, queueName));
			Assert.assertNotNull(readAndClickResultFirstCaseNumber(oBrowser, queueName));
			if(queueName.equalsIgnoreCase("Enrollments Manager")){
				Assert.assertTrue(reassignTheEnrollmentCaseForEnrollmentsManager(oBrowser, dataTable));
			}else if(queueName.equalsIgnoreCase("Assisted Payment Services")){
				Assert.assertTrue(assistedPaymentServicesBaseSteps.reassignTheEnrollmentCaseForAssistedPaymentServices(oBrowser, dataTable));
			}else if(queueName.equalsIgnoreCase("Partner Enrollments")){
				Assert.assertTrue(partnerEnrollmentsUIBaseStep.reassignTheEnrollmentCaseForPartnerEnrollments(oBrowser, dataTable));
			}else{
				reports.writeResult(oBrowser, "Fail", "Invalid queue name '"+queueName+"' was provided. Provide appropriate queue name");
			}
			appDep.threadSleep(2000);
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'reassignTheEnrollmentCase()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'reassignTheEnrollmentCase()' method. " + e);
			return false;
		}
	}



	/********************************************************
	 * Method Name      : validateReassignDetailsInActivitiesTab()
	 * Purpose          : user valiadtes Reassign details under Activities tab
	 * Author           : Gudi
	 * Parameters       : oBrowser, queueName
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean validateReassignDetailsInActivitiesTab(WebDriver oBrowser, String queueName){
		try{
			Assert.assertTrue(clickOnActivitiesTab(oBrowser, queueName));
			appDep.threadSleep(4000);
			switch(queueName.toLowerCase()){
				case "enrollments manager":
					Assert.assertTrue(verifyTheReassignmentDetailsUnderEnrollmentsManagerActivitiesTab(oBrowser));
					break;
				case "assisted payment services":
					Assert.assertTrue(assistedPaymentServicesBaseSteps.verifyTheReassignmentDetailsUnderAssistedPaymentServicesActivitiesTab(oBrowser));
					break;
				case "partner enrollments":
					Assert.assertTrue(partnerEnrollmentsUIBaseStep.verifyTheReassignmentDetailsUnderPartnerEnrollmentsActivitiesTab(oBrowser));
					break;
				default:
					reports.writeResult(oBrowser, "Fail", "Invalid queue name '"+queueName+"' was specified. Please specify the valid queue name");
					return false;
			}
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'validateReassignDetailsInActivitiesTab()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateReassignDetailsInActivitiesTab()' method. " + e);
			return false;
		}
	}



	/********************************************************
	 * Method Name      : validateReassignDetailsInLogsTab()
	 * Purpose          : user valiadtes Reassign details under Logs tab
	 * Author           : Gudi
	 * Parameters       : oBrowser, queueName
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean validateReassignDetailsInLogsTab(WebDriver oBrowser, String queueName){
		try{
			switch(queueName.toLowerCase()){
				case "enrollments manager":
					Assert.assertTrue(clickOnLogsTab(oBrowser));
					Assert.assertTrue(verifyTheEnrollmentDetailsUnderLogsTab(oBrowser));
					break;
				case "assisted payment services":
					Assert.assertTrue(assistedPaymentServicesBaseSteps.clickOnAssistedPaymentServicesLogsTab(oBrowser));
					Assert.assertTrue(assistedPaymentServicesBaseSteps.verifyTheReassignDetailsUnderAssistedPaymentServicesLogsTab(oBrowser));
					break;
				case "partner enrollments":
					//No logs tab for 'Partner Enrollment' queue
					break;
				default:
					reports.writeResult(oBrowser, "Fail", "Invalid queue name '"+queueName+"' was specified. Please specify the valid queue name");
					return false;
			}

			Assert.assertTrue(assistedPaymentServicesBaseSteps.clickOnAssistedPaymentServicesLogsTab(oBrowser));
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'validateReassignDetailsInLogsTab()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateReassignDetailsInLogsTab()' method. " + e);
			return false;
		}
	}




	/********************************************************
	 * Method Name      : removeTheAttachment()
	 * Purpose          : user removes the attachment details for the selected queue
	 * Author           : Gudi
	 * Parameters       : oBrowser, attachmentAction, queueName
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean removeTheAttachment(WebDriver oBrowser, String attachmentAction, String queueName){
		WebElement objAttachmentsGrid = null;
		WebDriverWait oWait = null;
		String attachmentName = null;
		try{
			switch(attachmentAction.toLowerCase()){
				case "add":
					attachmentName = attachmentData.get("Name");
					break;
				case "edit":
					attachmentName = editAttachmentData.get("Name");
					break;
				default:
					reports.writeResult(oBrowser, "Fail", "Invalid attachment Action '"+attachmentAction+"' was specified. The supported one are 'Add' & 'Edit'");
					return false;
			}
			oWait = new WebDriverWait(oBrowser, Duration.ofSeconds(waitTimeOut));

			//Search if the grid contains more data as selenium will not find the data in the grid if it is hidden
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, EnrollmentsManagerUIPage.obj_Attachments_Name_Edit, attachmentName));
			appDep.threadSleep(2000);
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_Attachments_Pagination, "Visibility", "", waitTimeOut);

			objAttachmentsGrid = appInd.createAndGetWebElement(oBrowser, EnrollmentsManagerUIPage.Obj_Attachments_Grid);
			oWait.until(ExpectedConditions.elementToBeClickable(objAttachmentsGrid.findElement(By.xpath("//tr/td[text()='"+attachmentName+"']/following-sibling::td/a[@title='Edit']"))));
			Assert.assertTrue(appInd.clickObject(oBrowser, objAttachmentsGrid.findElement(By.xpath("//tr/td[text()='"+attachmentName+"']/following-sibling::td/a[@title='Edit']"))), "clickObject() was failed for the 'Show' Attachments");
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_Attachments_EditAttachment_Header, "Visibility", "", waitTimeOut);
			Assert.assertTrue(appInd.verifyText(oBrowser, EnrollmentsManagerUIPage.obj_Attachments_EditAttachment_Header, "Text", "Edit Attachment"));

			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_RemoveAttachment_No_Btn), "clickObject() was failed for the '"+EnrollmentsManagerUIPage.obj_RemoveAttachment_Yes_Btn+"' webelement");
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_DeleteAttachmentSection_Message, "Visibility", "", waitTimeOut);
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, EnrollmentsManagerUIPage.obj_DeleteAttachmentSection_Message, "Text").contains("Choosing this action will delete the entire attachment, relationships included"));
			appDep.threadSleep(4000);
			reports.writeResult(oBrowser, "Screenshot", "Removing the attachment");
			Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, EnrollmentsManagerUIPage.Obj_Attachments_Create_Btn), "javaScriptClickObject() was failed for the '"+EnrollmentsManagerUIPage.Obj_Attachments_Create_Btn+"' webelement");
			appDep.threadSleep(4000);
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_ReassignConfirmationMessage_Label, "Visibility", "", waitTimeOut);
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, EnrollmentsManagerUIPage.obj_ReassignConfirmationMessage_Label, "Text").contains("Attachment has been successfully deleted"), "getTextOnElement() was failed for the '"+EnrollmentsManagerUIPage.obj_ReassignConfirmationMessage_Label+"' webelement");
			appDep.threadSleep(2000);
			appDep.waitForTheElementState(oBrowser, By.xpath("//td[text()='"+attachmentName+"']"), "Invisibility", "", waitTimeOut);
			Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath("//td[text()='"+attachmentName+"']")), "verifyElementNotPresent() was failed for the delete attachment '"+attachmentName+"'");
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'removeTheAttachment()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'removeTheAttachment()' method. " + e);
			return false;
		}finally {
			objAttachmentsGrid = null; oWait = null; attachmentName = null;
		}
	}




	/********************************************************
	 * Method Name      : clickOnAddressTab()
	 * Purpose          : to verify that the user clicks on the Address tab
	 * Author           : Gudi
	 * Parameters       : oBrowser
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean clickOnAddressTab(WebDriver oBrowser){
		try{
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_Address_Tab, "Clickable", "", waitTimeOut);
			appInd.scrollToElement(oBrowser, EnrollmentsManagerUIPage.obj_SplitterHorizonal_Line);
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Address_Tab));
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_Address_Add_Btn, "Clickable", "", waitTimeOut);
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, EnrollmentsManagerUIPage.obj_Address_Add_Btn));
			appDep.threadSleep(2000);
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in ' clickOnAddressTab()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'clickOnAddressTab()' method. " + e);
			return false;
		}
	}





	/********************************************************
	 * Method Name      : createAddress()
	 * Purpose          : to add and validate the address details for Enrollments Manager
	 * Author           : Gudi
	 * Parameters       : oBrowser, queueName, dataTable
	 * ReturnType       : Map<String, String>
	 ********************************************************/
	public Map<String, String> createAddress(WebDriver oBrowser, String queueName, Map<String, String> addressData){
		String stateProvince = null;
		By objAddressCreateBtn = null;
		By objCreateAddressDialog = null;
		String headerTitle = null;
		String confirmationMsg = null;
		try{
			/*Assert.assertTrue(enrollmentMgrBaseSteps.selectRequiredQueue(oBrowser, queueName));
			Assert.assertTrue(enrollmentMgrBaseSteps.clickOnShowCaseButton(oBrowser, queueName));
			Assert.assertTrue(enrollmentMgrBaseSteps.readAndClickResultFirstCaseNumber(oBrowser, queueName));
			Assert.assertTrue(enrollmentMgrBaseSteps.clickOnAddressTab(oBrowser));*/

			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Address_Tab));
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Address_Add_Btn));

			if(queueName.equalsIgnoreCase("Company")) {
				objAddressCreateBtn = PayCRM_Modules_GeneralUIPage.obj_Company_Address_Create_Btn;
				objCreateAddressDialog = EnrollmentsManagerUIPage.obj_address_NewCompanyAddress_dialog;
				headerTitle = "New Company Address";
				confirmationMsg = "Company Address has been successfully created.";
			}
			else {
				objAddressCreateBtn = EnrollmentsManagerUIPage.obj_NewAddress_Create_Btn;
				objCreateAddressDialog = EnrollmentsManagerUIPage.obj_address_NewSupplierAddress_dialog;
				headerTitle = "New Supplier Address";
				confirmationMsg = "Supplier Address has been successfully created";
			}

			appDep.waitForTheElementState(oBrowser, objAddressCreateBtn, "Clickable", "", waitTimeOut);
			appInd.verifyText(oBrowser, objCreateAddressDialog, "Text", headerTitle);
			Assert.assertTrue(appInd.selectObject(oBrowser, EnrollmentsManagerUIPage.obj_Address_AddressType_List, addressData.get("AddressType")));

			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Address_Country_Label));
			Assert.assertTrue(appInd.setObject(oBrowser, EnrollmentsManagerUIPage.obj_Address_Country_Search_Edit, addressData.get("Country")));
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Address_Country_SearchResult_Label));

			Assert.assertTrue(appInd.setObject(oBrowser, EnrollmentsManagerUIPage.obj_Address_Address1_Edit, addressData.get("Address1")));
			if(addressData.get("Address2") != null)
				Assert.assertTrue(appInd.setObject(oBrowser, EnrollmentsManagerUIPage.obj_Address_Address2_Edit, addressData.get("Address2")));
			if(addressData.get("Address3") != null)
				Assert.assertTrue(appInd.setObject(oBrowser, EnrollmentsManagerUIPage.obj_Address_Address3_Edit, addressData.get("Address3")));

			Assert.assertTrue(appInd.setObject(oBrowser, EnrollmentsManagerUIPage.obj_Address_City_Edit, addressData.get("City")));

			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Address_StateProvince_Label));
			Assert.assertTrue(appInd.setObject(oBrowser, EnrollmentsManagerUIPage.obj_Address_StateProvince_Search_Edit, addressData.get("StateProvince")));
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Address_StateProvince_SearchResult_Label));
			stateProvince = addressData.get("StateProvince");
			addressData.remove("StateProvince");
			appDep.threadSleep(2000);
			addressData.put("StateProvince", appInd.getTextOnElement(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_StateProvince_List +"/option[text()='" + stateProvince + "']"), "value"));
			Assert.assertTrue(appInd.setObject(oBrowser, EnrollmentsManagerUIPage.obj_Address_PostalCode_Edit, addressData.get("PostalCode")));

			if(addressData.get("Language") != null)
				Assert.assertTrue(appInd.setObject(oBrowser, EnrollmentsManagerUIPage.obj_Address_Language_Edit, addressData.get("Language")));

			appDep.waitForTheElementState(oBrowser, objAddressCreateBtn, "Clickable", "", waitTimeOut);
			reports.writeResult(oBrowser, "Screenshot", "The new address details are entered for '"+queueName+"' successful");
			Assert.assertTrue(appInd.clickObject(oBrowser, objAddressCreateBtn));
			appDep.threadSleep(2000);
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_ReassignConfirmationMessage_Label, "Visibility", "", waitTimeOut);
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, EnrollmentsManagerUIPage.obj_ReassignConfirmationMessage_Label, "Text").contains(confirmationMsg));
			appDep.threadSleep(2000);
			addressData.put("TestPassed", "True");
			return addressData;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'createAddress()' method. " + e);
			addressData.put("TestPassed", "false");
			return addressData;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'createAddress()' method. " + e);
			addressData.put("TestPassed", "false");
			return addressData;
		}finally {
			stateProvince = null; objAddressCreateBtn = null; objCreateAddressDialog = null; headerTitle = null; confirmationMsg = null;
		}
	}





	/********************************************************
	 * Method Name      : editAddress()
	 * Purpose          : to edit and validate the address details for Enrollments Manager
	 * Author           : Gudi
	 * Parameters       : oBrowser, queueName, dataTable
	 * ReturnType       : Map<String, String>
	 ********************************************************/
	public Map<String, String> editAddress(WebDriver oBrowser, String queueName, Map<String, String> addressData){
		String stateProvince = null;
		By objAddressCreateBtn = null;
		By objEditAddressDialog = null;
		String headerTitle = null;
		String confirmationMsg = null;
		try{
			//appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_Address_Address1_Search_Edit, "Clickable", "", waitTimeOut);
			//Assert.assertTrue(appInd.clearAndSetObject(oBrowser, EnrollmentsManagerUIPage.obj_Address_Address1_Search_Edit, newAddressOne));

			if(queueName.equalsIgnoreCase("Company")) {
				objAddressCreateBtn = PayCRM_Modules_GeneralUIPage.obj_Company_Address_Create_Btn;
				objEditAddressDialog = EnrollmentsManagerUIPage.obj_address_EditCompanyAddress_dialog;
				headerTitle = "Edit Company Address";
				confirmationMsg = "Company Address has been successfully updated.";
			}
			else {
				objAddressCreateBtn = EnrollmentsManagerUIPage.obj_NewAddress_Create_Btn;
				objEditAddressDialog = EnrollmentsManagerUIPage.obj_address_EditSupplierAddress_dialog;
				headerTitle = "Edit Supplier Address";
				confirmationMsg = "Supplier Address has been successfully updated";
			}

			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Address_Table_Grid +"/tr/td[text()='" + newAddressOne + "']/following-sibling::td/a[@title='Edit']")));
			appDep.waitForTheElementState(oBrowser, objAddressCreateBtn, "Clickable", "", waitTimeOut);
			appInd.verifyText(oBrowser, objEditAddressDialog, "Text", headerTitle);

			Assert.assertTrue(appInd.selectObject(oBrowser, EnrollmentsManagerUIPage.obj_Address_AddressType_List, addressData.get("AddressType")));
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Address_Country_Label));
			Assert.assertTrue(appInd.setObject(oBrowser, EnrollmentsManagerUIPage.obj_Address_Country_Search_Edit, addressData.get("Country")));
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Address_StateProvince_SearchResult_Label));

			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, EnrollmentsManagerUIPage.obj_Address_Address1_Edit, addressData.get("Address1")));
			if(addressData.get("Address2") != null)
				Assert.assertTrue(appInd.clearAndSetObject(oBrowser, EnrollmentsManagerUIPage.obj_Address_Address2_Edit, addressData.get("Address2")));
			else
				Assert.assertTrue(appInd.clearAndSetObject(oBrowser, EnrollmentsManagerUIPage.obj_Address_Address2_Edit, ""));

			if(addressData.get("Address3") != null)
				Assert.assertTrue(appInd.clearAndSetObject(oBrowser, EnrollmentsManagerUIPage.obj_Address_Address3_Edit, addressData.get("Address3")));
			else
				Assert.assertTrue(appInd.clearAndSetObject(oBrowser, EnrollmentsManagerUIPage.obj_Address_Address3_Edit, ""));

			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, EnrollmentsManagerUIPage.obj_Address_City_Edit, addressData.get("City")));
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Address_StateProvince_Label));
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, EnrollmentsManagerUIPage.obj_Address_StateProvince_Search_Edit, addressData.get("StateProvince")));
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Address_StateProvince_SearchResult_Label));
			stateProvince = addressData.get("StateProvince");
			addressData.remove("StateProvince");
			appDep.threadSleep(2000);
			addressData.put("StateProvince", appInd.getTextOnElement(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_StateProvince_List +"/option[text()='" + stateProvince + "']"), "value"));

			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, EnrollmentsManagerUIPage.obj_Address_PostalCode_Edit, addressData.get("PostalCode")));
			if(addressData.get("Language") != null)
				Assert.assertTrue(appInd.clearAndSetObject(oBrowser, EnrollmentsManagerUIPage.obj_Address_Language_Edit, addressData.get("Language")));
			else
				Assert.assertTrue(appInd.clearAndSetObject(oBrowser, EnrollmentsManagerUIPage.obj_Address_Language_Edit, ""));

			appDep.waitForTheElementState(oBrowser, objAddressCreateBtn, "Clickable", "", waitTimeOut);
			reports.writeResult(oBrowser, "Screenshot", "The '"+queueName+"' Edit address details are entered successful");
			Assert.assertTrue(appInd.clickObject(oBrowser, objAddressCreateBtn));
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_ReassignConfirmationMessage_Label, "Visibility", "", waitTimeOut);
			appDep.threadSleep(2000);
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, EnrollmentsManagerUIPage.obj_ReassignConfirmationMessage_Label, "Text").contains(confirmationMsg));
			appDep.threadSleep(2000);
			addressData.put("TestPassed", "True");
			return addressData;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'editAddress()' method. " + e);
			addressData.put("TestPassed", "false");
			return addressData;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'editAddress()' method. " + e);
			addressData.put("TestPassed", "false");
			return addressData;
		}finally {
			stateProvince = null; objAddressCreateBtn = null; objEditAddressDialog = null; headerTitle = null; confirmationMsg = null;
		}
	}






	/********************************************************
	 * Method Name      : validateAddressDetails()
	 * Purpose          : validate the Address details under Address Table Grid & Show Grid
	 * Author           : Gudi
	 * Parameters       : oBrowser, contactStatus, queueName, contactDetails
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean validateAddressDetails(WebDriver oBrowser, String addressStatus, String queueName, Map<String, String> addressData){
		String addressOne = null;
		By objAddress1SearchEdit = null;
		String headerText = null;
		By objAddressShowCloseBtn = null;
		try{
			if(queueName.equalsIgnoreCase("Company")){
				objAddress1SearchEdit = EnrollmentsManagerUIPage.obj_ClientAddress_Address1_Search_Edit;
				headerText = "Company Address";
				objAddressShowCloseBtn = EnrollmentsManagerUIPage.obj_ClientAddress_Show_Close_Btn;
			}else{
				objAddress1SearchEdit = EnrollmentsManagerUIPage.obj_Address_Address1_Search_Edit;
				headerText = "Supplier Address";
				objAddressShowCloseBtn = EnrollmentsManagerUIPage.obj_Address_ShowAddress_close_Btn;
			}
			appDep.waitForTheElementState(oBrowser, objAddress1SearchEdit, "Visibility", "", waitTimeOut);

			reports.writeResult(oBrowser, "Screenshot", "After the '"+addressStatus+"' Address is saved");
			if(addressStatus.equalsIgnoreCase("New")){
				addressOne = newAddressOne;
			}else{
				addressOne = editAddressOne;
			}

			appDep.waitForTheElementState(oBrowser, objAddress1SearchEdit, "Clickable", "", waitTimeOut);
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, objAddress1SearchEdit, addressOne));
			appDep.threadSleep(2000);
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Address_Table_Grid +"/tr/td[text()='" + addressOne + "']"), "Text", addressData.get("Address1")));
			if(addressData.get("Address2") != null )
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Address_Table_Grid +"/tr/td[text()='" + addressOne + "']/following-sibling::td[1]"), "Text", addressData.get("Address2")));
			else
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Address_Table_Grid +"/tr/td[text()='" + addressOne + "']/following-sibling::td[1]"), "Text", ""));

			if(addressData.get("Address3") != null)
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Address_Table_Grid +"/tr/td[text()='" + addressOne + "']/following-sibling::td[2]"), "Text", addressData.get("Address3")));
			else
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Address_Table_Grid +"/tr/td[text()='" + addressOne + "']/following-sibling::td[2]"), "Text", ""));

			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Address_Table_Grid +"/tr/td[text()='" + addressOne + "']/following-sibling::td[3]"), "Text", addressData.get("City")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Address_Table_Grid +"/tr/td[text()='" + addressOne + "']/following-sibling::td[4]"), "Text", addressData.get("StateProvince")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Address_Table_Grid +"/tr/td[text()='" + addressOne + "']/following-sibling::td[5]"), "Text", addressData.get("PostalCode")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Address_Table_Grid +"/tr/td[text()='" + addressOne + "']/following-sibling::td[6]"), "Text", addressData.get("Country")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Address_Table_Grid +"/tr/td[text()='" + addressOne + "']/following-sibling::td[7]"), "Text", addressData.get("AddressType")));

			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Address_Table_Grid +"/tr/td[text()='" + addressOne + "']/following-sibling::td/a[@title='Show']")));
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_Address_ShowSupplierAddress_Dialog, "Text", headerText, waitTimeOut);

			Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+ EnrollmentsManagerUIPage.obj_Address_ShowAddress_Grid +"/dl/dd)[1]"), "Text").trim().contains(addressData.get("Address1")));
			if(addressData.get("Address2") != null )
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+ EnrollmentsManagerUIPage.obj_Address_ShowAddress_Grid +"/dl/dd)[2]"), "Text", addressData.get("Address2")));
			else
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+ EnrollmentsManagerUIPage.obj_Address_ShowAddress_Grid +"/dl/dd)[2]"), "Text", ""));

			if(addressData.get("Address3") != null )
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+ EnrollmentsManagerUIPage.obj_Address_ShowAddress_Grid +"/dl/dd)[3]"), "Text", addressData.get("Address3")));
			else
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+ EnrollmentsManagerUIPage.obj_Address_ShowAddress_Grid +"/dl/dd)[3]"), "Text", ""));

			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+ EnrollmentsManagerUIPage.obj_Address_ShowAddress_Grid +"/dl/dd)[4]"), "Text", addressData.get("City")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+ EnrollmentsManagerUIPage.obj_Address_ShowAddress_Grid +"/dl/dd)[5]"), "Text", addressData.get("StateProvince")));

			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+ EnrollmentsManagerUIPage.obj_Address_ShowAddress_Grid +"/dl/dd)[6]"), "Text", addressData.get("PostalCode")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+ EnrollmentsManagerUIPage.obj_Address_ShowAddress_Grid +"/dl/dd)[7]"), "Text", addressData.get("Country")));

			if(addressData.get("Language") != null)
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+ EnrollmentsManagerUIPage.obj_Address_ShowAddress_Grid +"/dl/dd)[8]"), "Text", addressData.get("Language")));
			else
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+ EnrollmentsManagerUIPage.obj_Address_ShowAddress_Grid +"/dl/dd)[8]"), "Text", ""));

			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+ EnrollmentsManagerUIPage.obj_Address_ShowAddress_Grid +"/dl/dd)[9]"), "Text", addressData.get("AddressType")));
			Assert.assertTrue(appInd.clickObject(oBrowser, objAddressShowCloseBtn));
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'validateAddressDetails()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateAddressDetails()' method. " + e);
			return false;
		}finally {
			addressOne = null; objAddress1SearchEdit = null; headerText = null; objAddressShowCloseBtn = null;
		}
	}





	/********************************************************
	 * Method Name      : createAddressAndValidateTheSame()
	 * Purpose          : user creates the new Address and validates the same
	 * Author           : Gudi
	 * Parameters       : oBrowser, addressStatus, queueName, dataTable
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean createAddressAndValidateTheSame(WebDriver oBrowser, String addressStatus, String queueName, DataTable dataTable){
		List<Map<String, String>> addressDetails = null;
		String timeStamp = null;
		Map<String, String> addressData;
		try{
			timeStamp = appInd.getDateTime("YYYYShhmmss");
			addressDetails = dataTable.asMaps(String.class, String.class);
			addressData = new HashMap<String, String>();
			addressData.put("AddressStatus", addressStatus);
			addressData.put("AddressType", addressDetails.get(0).get("AddressType"));
			addressData.put("Country", addressDetails.get(0).get("Country"));
			addressData.put("Address1", addressDetails.get(0).get("Address1")+"_"+timeStamp);
			newAddressOne = addressData.get("Address1");
			addressData.put("Address2", addressDetails.get(0).get("Address2"));
			addressData.put("Address3", addressDetails.get(0).get("Address3"));
			addressData.put("City", addressDetails.get(0).get("City"));
			addressData.put("StateProvince", addressDetails.get(0).get("StateProvince"));
			addressData.put("PostalCode", addressDetails.get(0).get("PostalCode"));
			addressData.put("Language", addressDetails.get(0).get("Language"));

			switch(queueName.toLowerCase()){
				case "company":
					String companyName = appDep.selectTheCompanyBasedOnType(oBrowser, "Client");
					Assert.assertNotNull(companyName, "The 'selectTheCompanyBasedOnType()' method returned null value");
					break;
				default:
					Assert.assertTrue(appDep.navigateToCasesGridOfTheSelectedQueue(oBrowser, queueName));
					Assert.assertTrue(enrollmentManagersUIBaseSteps.clickOnAddressTab(oBrowser));
			}

			addressData = createAddress(oBrowser, queueName, addressData);
			Assert.assertTrue(addressData.get("TestPassed").equalsIgnoreCase("True"), "The 'createAddress()' method was failed");
			Assert.assertTrue(validateAddressDetails(oBrowser, addressStatus, queueName, addressData));
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'createAddressAndValidateTheSame()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'createAddressAndValidateTheSame()' method. " + e);
			return false;
		}
		finally{
			addressDetails = null; timeStamp = null; addressData = null;
		}
	}





	/********************************************************
	 * Method Name      : editAddressAndValidateTheSame()
	 * Purpose          : user edit the new Address and validates the same
	 * Author           : Gudi
	 * Parameters       : oBrowser, addressStatus, queueName, dataTable
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean editAddressAndValidateTheSame(WebDriver oBrowser, String addressStatus, String queueName, DataTable dataTable){
		List<Map<String, String>> addressDetails = null;
		String timeStamp;
		Map<String, String> addressData;
		try{
			timeStamp = appInd.getDateTime("YYYYShhmmss");
			addressDetails = dataTable.asMaps(String.class, String.class);
			addressData = new HashMap<String, String>();
			addressData.put("AddressStatus", addressStatus);
			addressData.put("AddressType", addressDetails.get(0).get("AddressType"));
			addressData.put("Country", addressDetails.get(0).get("Country"));
			addressData.put("Address1", addressDetails.get(0).get("Address1")+"_"+timeStamp);
			editAddressOne = addressData.get("Address1");
			addressData.put("Address2", addressDetails.get(0).get("Address2"));
			addressData.put("Address3", addressDetails.get(0).get("Address3"));
			addressData.put("City", addressDetails.get(0).get("City"));
			addressData.put("StateProvince", addressDetails.get(0).get("StateProvince"));
			addressData.put("PostalCode", addressDetails.get(0).get("PostalCode"));
			addressData.put("Language", addressDetails.get(0).get("Language"));

			addressData = editAddress(oBrowser, queueName, addressData);
			Assert.assertTrue(addressData.get("TestPassed").equalsIgnoreCase("True"), "The 'editAddress()' method was failed");
			Assert.assertTrue(validateAddressDetails(oBrowser, addressStatus, queueName, addressData));
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'editAddressAndValidateTheSame()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'editAddressAndValidateTheSame()' method. " + e);
			return false;
		}
		finally{
			addressDetails = null; timeStamp = null; addressData = null;
		}
	}




	/********************************************************
	 * Method Name      : deleteTheAddressAndValidate()
	 * Purpose          : user deletes the Address and validates the same
	 * Author           : Gudi
	 * Parameters       : oBrowser
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean deleteTheAddressAndValidate(WebDriver oBrowser, String pageName){
		String confirmationMsg = null;
		By objAddress1SearchEdit = null;
		try{
			if(pageName.contains("Company")){
				objAddress1SearchEdit = EnrollmentsManagerUIPage.obj_ClientAddress_Address1_Search_Edit;
				confirmationMsg = "Company Address has been successfully removed.";
			}else{
				objAddress1SearchEdit = EnrollmentsManagerUIPage.obj_Address_Address1_Search_Edit;
				confirmationMsg = "Supplier Address has been successfully removed.";
			}
			appDep.waitForTheElementState(oBrowser, objAddress1SearchEdit, "Clickable", "", waitTimeOut);
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, objAddress1SearchEdit, editAddressOne));
			appDep.threadSleep(2000);
			reports.writeResult(oBrowser, "Screenshot", "Before deleting the Address");
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_AddressTable_Grid +"//a[@title='Delete']")));	//Address delete button xpath modified
			appDep.threadSleep(2000);
			if(appInd.isAlertPresent(oBrowser)){
				oBrowser.switchTo().alert().accept();
			}
			appDep.threadSleep(2000);
			appDep.waitForTheElementState(oBrowser, objAddress1SearchEdit, "Clickable", "", waitTimeOut);
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, objAddress1SearchEdit, editAddressOne));
			appDep.threadSleep(2000);
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_Address_Grid_WithNoData, "Visibility", "", waitTimeOut);
			reports.writeResult(oBrowser, "Screenshot", "After deleting the Address");
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, EnrollmentsManagerUIPage.obj_Address_Grid_WithNoData));
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_AddressGrid_Pagination_NoData_Label, "Visibility", "", waitTimeOut);
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, EnrollmentsManagerUIPage.obj_AddressGrid_Pagination_NoData_Label));
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'deleteTheAddressAndValidate()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'deleteTheAddressAndValidate()' method. " + e);
			return false;
		}finally {
			confirmationMsg = null; objAddress1SearchEdit = null;
		}
	}





	/********************************************************
	 * Method Name      : validateColumnDataSearchFunctionality()
	 * Purpose          : user search the column data values from the Enrollment Case grid
	 * Author           : Gudi
	 * Parameters       : oBrowser, queueName
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean validateColumnDataSearchFunctionality(WebDriver oBrowser, String queueName){
		JSONArray caseDetails = null;
		String date;
		String actualDate;
		Date date1;
		Date date2;
		Calendar cal1;
		Calendar cal2;
		try{
			Assert.assertTrue(enrollmentManagersUIBaseSteps.selectRequiredQueue(oBrowser, queueName));
			Assert.assertTrue(enrollmentManagersUIBaseSteps.applyFiltersOnQueuesDashboard(oBrowser, queueName));
			Assert.assertTrue(enrollmentManagersUIBaseSteps.clickOnShowCaseButton(oBrowser, queueName));
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_CaseNumber_Grid_Pagination, "Visibility", "", maxWaitTimeOut);
			appDep.threadSleep(2000);

			caseDetails = apiDataProvider.getAPIDataProviderResponse("EnrollmentsManager_Cases_Grid", new Object[] {});

			Assert.assertTrue(assistedPaymentServicesBaseSteps.applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Case #", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("id").toString(),1));
			Assert.assertTrue(assistedPaymentServicesBaseSteps.applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Company", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("company_name").toString(),2));
			Assert.assertTrue(assistedPaymentServicesBaseSteps.applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Supplier", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("supplier_name").toString(),3));
			Assert.assertTrue(assistedPaymentServicesBaseSteps.applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Address", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("address").toString(),4));
			Assert.assertTrue(assistedPaymentServicesBaseSteps.applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Time Zone", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("time_zone").toString(),5));
			Assert.assertTrue(assistedPaymentServicesBaseSteps.applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Case Outcome", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("case_outcome").toString(),6));
			Assert.assertTrue(assistedPaymentServicesBaseSteps.applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Status", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("status_desc").toString(),7));
			Assert.assertTrue(assistedPaymentServicesBaseSteps.applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Assigned To", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("user_name").toString(),8));
			Assert.assertTrue(assistedPaymentServicesBaseSteps.applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Priority", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("priority_name").toString(),9));
			Assert.assertTrue(assistedPaymentServicesBaseSteps.applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Amount", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("total_spend").toString(),10));
			Assert.assertTrue(assistedPaymentServicesBaseSteps.applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "# Checks", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("check_count").toString(),11));
			Assert.assertTrue(assistedPaymentServicesBaseSteps.applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Debit Card Acceptor", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("debit_card_candidate").toString(),12));
			Assert.assertTrue(assistedPaymentServicesBaseSteps.applyFilterOnCaseGridColumnNameAndValidateTheData(oBrowser, "Offer", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("offer").toString(),13));
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_CaseGridCellSearch_Edit + "//table[@class='dx-datagrid-table dx-datagrid-table-fixed']//td[@aria-label='Last Touched Column']")));

			appInd.scrollToThePage(oBrowser, "Top");
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_EnrollmentCase_Grid + "//tr[contains(@class, 'dx-row-lines')]/td[1]")));
			oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
			reports.writeResult(oBrowser, "screenshot", "Enrollment Case : We click on 1st record from grid");
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_WaveAndOffers_Header, "Text", "Wave & Offers", waitTimeOut);
			Assert.assertTrue(appDep.validateGoogleIconPresence(oBrowser, "EnrollmentCase"));
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_setOfferButton));
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_setOfferButtonInPopup, "clickable", "", waitTimeOut);
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_setOfferButtonInPopup));
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_ReassignConfirmationMessage_Label, "visibility", "", waitTimeOut);
			oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_refreshButton));
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_Suppliers_search_Loading, "Invisibility", "", maxWaitTimeOut);
			date = appInd.dateAddAndReturn("MM/dd/yyyy", 0);
			actualDate = appInd.getTextOnElement(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_EnrollmentCase_Grid + "//tr[contains(@class, 'dx-row-lines')]/td[14]"), "text");
			date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
			date2 = new SimpleDateFormat("dd/MM/yyyy").parse(actualDate);
			cal1 = Calendar.getInstance();
			cal2 = Calendar.getInstance();
			cal1.setTime(date1);
			cal2.setTime(date2);
            Assert.assertEquals(cal2, cal1);
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'validateColumnDataSearchFunctionality()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateColumnDataSearchFunctionality()' method. " + e);
			return false;
		}finally{caseDetails = null; date = null; actualDate = null; date1 = null; date2 = null; cal1 = null; cal2 = null;}
	}




	/********************************************************
	 * Method Name      : EnrollmentCaseGridColumnFilterFunctionality()
	 * Purpose          : user validate filter functionality for Payment Based Enrollment grid data
	 * Author           : Gudi
	 * Parameters       : oBrowser, columnName
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean EnrollmentCaseGridColumnFilterFunctionality(WebDriver oBrowser, String columnName, String dateValidate, int index){
		String dataToValidate = "";
		int flag = 0;
		String value[];
		boolean isMultipleValue = false;
		String arrData[];
		try{
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_CaseNumber_Grid_Pagination, "Visibility", "", waitTimeOut);
			appDep.threadSleep(2000);
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_EnrollmentCase_Grid + "//td[@aria-label = '"+columnName+" Column']/div[@class='dx-column-indicators']")));
			appDep.waitForTheElementState(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_PaymentBasedEnrollments_FilterOptions_Label), "Clickable", "", waitTimeOut);

			if(dateValidate.isEmpty()){
				for(int x=1; x<=3; x++){
					String filterData = appInd.getTextOnElement(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_PaymentBasedEnrollments_FilterOptions_Label+")["+(x)+"]"), "Text");
					if(!filterData.contains("(Blanks)") && !filterData.contains("'")){
						Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_PaymentBasedEnrollments_FilterOptions_Label +")["+(x)+"]")));
						dataToValidate+= filterData+"#";
						if(columnName.equalsIgnoreCase("Amount") || columnName.equalsIgnoreCase("# checks"))
							break;
					}
				}
				isMultipleValue = true;
			}else{
				dataToValidate = dateValidate;
				Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Enrollment_Case_FilterValue +"/div[contains(text(), '"+dataToValidate+"')]")));
			}

			dataToValidate = dataToValidate.substring(0, dataToValidate.length()-1);
			reports.writeResult(oBrowser, "Screenshot", "Applying the filter for '"+columnName+"' column");
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Filter_OK_Btn)));
			appDep.threadSleep(2000);

			int maxRowNum = 15;
			int rowNum = oBrowser.findElements(By.xpath(EnrollmentsManagerUIPage.obj_Enrollment_Case_Grid + "//div[@class='dx-datagrid-content']/table//tr")).size() - 1;
			if(rowNum > maxRowNum) rowNum = maxRowNum;

			for(int i=0; i<rowNum; i++){
				flag++;
				String cellData = appInd.createAndGetWebElement(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Enrollment_Case_Grid + "//div[@class='dx-datagrid-content']/table//tr["+(i+1)+"]/td["+index+"]")).getText();
				switch(columnName.toLowerCase()){
					case "company": case "assigned to": case "priority": case "debit card acceptor": case "offer": case "case outcome":
						if(isMultipleValue) Assert.assertTrue(dataToValidate.contains(cellData), "Mis-match in actual +'"+cellData+"' & expected value + '"+dataToValidate+"' in Paymnet Based Enrollment Grid filtered values");
						else
							Assert.assertTrue(appInd.compareValues(oBrowser, cellData, dataToValidate), "Mis-match in actual +'"+cellData+"' & expected value + '"+dataToValidate+"' in Paymnet Based Enrollment Grid filtered values");
						break;
					case "time zone": case "status":
						if(isMultipleValue) {
							arrData = dataToValidate.split("#");
							int j; boolean count = false;
							for(j = 0; j<arrData.length; j++){
								if(cellData.contains(arrData[j])){
									count = true;
									break;
								}
							}
							Assert.assertTrue((count==true), "Mis-match in actual +'"+cellData+"' & expected value + '"+dataToValidate+"' in Paymnet Based Enrollment Grid filtered values");
						}
						else
							Assert.assertTrue(cellData.contains(dataToValidate), "Mis-match in actual +'"+cellData+"' & expected value + '"+dataToValidate+"' in Paymnet Based Enrollment Grid filtered values");
						break;
					case "amount": case "# checks":
						value = dataToValidate.split(" - ");
						Assert.assertTrue(Double.parseDouble(cellData.replace("$", "").replace(",", "")) <= Double.parseDouble(value[1].replace(",", "").replace("$", "")), "Mis-match in actual +'"+cellData+"' & expected value + '"+dataToValidate+"' in Paymnet Based Enrollment Grid filtered values");
						break;
					default:
						reports.writeResult(oBrowser, "Fail", "Invalid column name '"+columnName+"' was mentioned.");
						Assert.assertTrue(false, "Invalid column name '"+columnName+"' was mentioned.");
				}
			}
			if(flag==0){
				Assert.assertTrue(false, "No data was found for the applied filter value '"+dataToValidate+"' for the column +'"+columnName+"'");
			}

			//remove the filter
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_EnrollmentCase_Grid + "//td[@aria-label = '"+columnName+" Column']/div[@class='dx-column-indicators']")));
			appDep.threadSleep(2000);
			if(isMultipleValue){
				arrData = dataToValidate.split("#");
				for(int i=0; i<arrData.length; i++){
					Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Enrollment_Case_FilterValue +"/div[contains(text(), '"+arrData[i]+"')]")));
				}
			}else
				Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Enrollment_Case_FilterValue +"/div[contains(text(), '"+dataToValidate+"')]")));
			appDep.threadSleep(2000);
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Filter_OK_Btn)));
			appDep.threadSleep(2000);
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'EnrollmentCaseGridColumnFilterFunctionality()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'EnrollmentCaseGridColumnFilterFunctionality()' method. " + e);
			return false;
		}finally {
			dataToValidate = null; value = null;
		}
	}




	/********************************************************
	 * Method Name      : ValidateColumnFilterFunctionality()
	 * Purpose          : user filter the column data values from the Enrollment Case grid
	 * Author           : Gudi
	 * Parameters       : oBrowser
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean ValidateColumnFilterFunctionality(WebDriver oBrowser){
		try{
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut + 300);
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_CaseNumber_Grid_Pagination, "Visibility", "", waitTimeOut);
			appDep.threadSleep(2000);
			Assert.assertTrue(EnrollmentCaseGridColumnFilterFunctionality(oBrowser, "Company", "", 2));
			Assert.assertTrue(EnrollmentCaseGridColumnFilterFunctionality(oBrowser, "Time Zone", "", 5));
			Assert.assertTrue(EnrollmentCaseGridColumnFilterFunctionality(oBrowser, "Case Outcome", "", 6));
			Assert.assertTrue(EnrollmentCaseGridColumnFilterFunctionality(oBrowser, "Status", "", 7));
			Assert.assertTrue(EnrollmentCaseGridColumnFilterFunctionality(oBrowser, "Assigned To", "", 8));
			Assert.assertTrue(EnrollmentCaseGridColumnFilterFunctionality(oBrowser, "Priority", "", 9));
			Assert.assertTrue(EnrollmentCaseGridColumnFilterFunctionality(oBrowser, "Amount", "", 10));
			Assert.assertTrue(EnrollmentCaseGridColumnFilterFunctionality(oBrowser, "# Checks", "",11));
			Assert.assertTrue(EnrollmentCaseGridColumnFilterFunctionality(oBrowser, "Debit Card Acceptor", "",12));
			Assert.assertTrue(EnrollmentCaseGridColumnFilterFunctionality(oBrowser, "Offer", "", 13));
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'ValidateColumnFilterFunctionality()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'ValidateColumnFilterFunctionality()' method. " + e);
			return false;
		}
	}




	/********************************************************
	 * Method Name      : rerunSinglePaymentPoll()
	 * Purpose          : user rerun the single payment poll
	 * Author           : Gudi
	 * Parameters       : oBrowser, dataTable
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean rerunSinglePaymentPoll(WebDriver oBrowser, DataTable dataTable){
		List<Map<String, String>> rerunSinglePaymentPoll = null;
		try{
			rerunSinglePaymentPoll = dataTable.asMaps(String.class, String.class);
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_CloudWatchErrorLogs_Link));
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_RerunSinglePaymentPoll_Btn, "Clickable", "", waitTimeOut);
			appDep.threadSleep(2000);
			reports.writeResult(oBrowser, "Screenshot", "The 'Cloud Watch Error Logs' tab was clicked");
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_CloudWatchErrorLogs_Header + ")[1]"), "Text", "CloudWatch Error Logs"));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_CloudWatchErrorLogs_Header + ")[2]"), "Text", "Nothing Selected"));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_CloudWatchErrorLog_Grid + "/table//tr/th[1]"), "Text", "Log Stream Name"));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_CloudWatchErrorLog_Grid + "/table//tr/th[2]"), "Text", "Last Event Time"));
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_RerunSinglePaymentPoll_Btn));
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_RerunSinglePaymentPoll_Run_Btn, "Clickable", "", waitTimeOut);
			reports.writeResult(oBrowser, "Screenshot", "The 'Rerun Single Payment Poll' dialog was opened'");
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_RerunSinglePaymentPoll_Dialog + "//div[@class='modal-header']/h4"), "Text", "Rerun Single Payment Poll"));
			Assert.assertTrue(appInd.setObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_RerunSinglePaymentPoll_Dialog + "//textarea[@id='payment_hash']"), rerunSinglePaymentPoll.get(0).get("RerunSinglePaymentPoll_Hash")));
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_RerunSinglePaymentPoll_Run_Btn));
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_RerunSinglePaymentPoll_Status, "Text", "Success", CucumberTestRunner.waitTimeOut);
			reports.writeResult(oBrowser, "Screenshot", "The status message");
			Assert.assertTrue(appInd.verifyText(oBrowser, EnrollmentsManagerUIPage.obj_RerunSinglePaymentPoll_Status, "Text", "Success"));
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, EnrollmentsManagerUIPage.obj_AdditionalAction_ViewCase_Link));
			Assert.assertTrue(appInd.verifyText(oBrowser, EnrollmentsManagerUIPage.obj_AdditionalAction_ViewCase_Link, "Text", "View Case"));
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_RerunSinglePaymentPoll_Close_Btn));
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'rerunSinglePaymentPoll()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'rerunSinglePaymentPoll()' method. " + e);
			return false;
		}finally{rerunSinglePaymentPoll = null;}
	}




	/********************************************************
	 * Method Name      : selectOptionsFromDevMenu()
	 * Purpose          : user selects different values from Dev dropdown
	 * Author           : Gudi
	 * Parameters       : oBrowser, menuValues
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean selectOptionsFromDevMenu(WebDriver oBrowser, String menuValues){
		try{
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Dev_List));
			switch(menuValues.toLowerCase()){
				case "manual api polling":
					Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_ManualAPIPolling_Link));
					appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_RunAPIPoll_Btn, "Clickable", "", waitTimeOut);
					break;
				case "settings":
					Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Settings_Link));
					appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_Suppliers_search_Loading, "Invisibility", "", waitTimeOut);
					break;
				case "link management":
					Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_LinkManagement_Link));
					appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_MoveLink_Btn, "Clickable", "", waitTimeOut);
					break;
				case "support case polling":
					Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_SupportCasePolling_Link));
					appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_RunAPIPoll_Btn, "Clickable", "", waitTimeOut);
					break;
				default:
					reports.writeResult(oBrowser, "Fail", "Invalid data for the menu values '"+menuValues+"'");
					return false;
			}
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'selectOptionsFromDevMenu()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'selectOptionsFromDevMenu()' method. " + e);
			return false;
		}
	}




	/********************************************************
	 * Method Name      : createApplicationSetting()
	 * Purpose          : user creates a new application setting
	 * Author           : Gudi
	 * Parameters       : oBrowser, appSettingData
	 * ReturnType       : Map<String, String>
	 ********************************************************/
	public Map<String, String> createApplicationSetting(WebDriver oBrowser, Map<String, String> appSettingData){
		try{
			reports.writeResult(oBrowser, "Screenshot", "Before creating the application setting");
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Add_Link));
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_NewAddress_Create_Btn, "Clickable", "", waitTimeOut);
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_NewApplicationSetting_Dialog + "//h4"), "Text", "New Application Setting"));
			Assert.assertTrue(appInd.setObject(oBrowser, EnrollmentsManagerUIPage.obj_ApplicationSetting_Name_Edit, appSettingData.get("Name")));
			Assert.assertTrue(appInd.setObject(oBrowser, EnrollmentsManagerUIPage.obj_ApplicationSetting_Value_Edit, appSettingData.get("Value")));
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_NewAddress_Create_Btn));
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_Suppliers_search_Loading, "Invisibility", "", waitTimeOut);
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_ApplicationSetting_ConfirmationMessage_Label, "Visibility", "", waitTimeOut);
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, EnrollmentsManagerUIPage.obj_ApplicationSetting_ConfirmationMessage_Label, "Text").contains("Application setting has been successfully created."));
			appDep.waitForTheElementState(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_ApplicationSetting_Grid + "//input)[1]"), "Clickable", "", minTimeOut);
			Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_ApplicationSetting_Grid + "//input)[1]"), appSettingData.get("Name")));
			appDep.threadSleep(2000);
			reports.writeResult(oBrowser, "Screenshot", "After creating the application setting");
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_ApplicationSetting_Grid + "//table[contains(@class, 'dx-datagrid-table-content')]//td)[1]"), "Text", appSettingData.get("Name")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_ApplicationSetting_Grid + "//table[contains(@class, 'dx-datagrid-table-content')]//td)[2]"), "Text", appSettingData.get("Value")));
			appSettingData.put("TestPassed", "True");
			return appSettingData;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'createApplicationSetting()' method. " + e);
			appSettingData.put("TestPassed", "False");
			return appSettingData;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'createApplicationSetting()' method. " + e);
			appSettingData.put("TestPassed", "False");
			return appSettingData;
		}
	}





	/********************************************************
	 * Method Name      : editApplicationSetting()
	 * Purpose          : user edit the new application setting
	 * Author           : Gudi
	 * Parameters       : oBrowser, appSettingData
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean editApplicationSetting(WebDriver oBrowser, Map<String, String> appSettingData){
		String timeStamp = null;
		try{
			timeStamp = appInd.getDateTime("Shhmmss");
			reports.writeResult(oBrowser, "Screenshot", "Before Editing the application setting");
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_ApplicationSetting_Edit_Btn));
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_NewAddress_Create_Btn, "Clickable", "", waitTimeOut);
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_NewApplicationSetting_Dialog + "//h4"), "Text", "Edit Application Setting"));

			Assert.assertTrue(appInd.verifyText(oBrowser, EnrollmentsManagerUIPage.obj_ApplicationSetting_Name_Edit, "Value", appSettingData.get("Name")));
			Assert.assertTrue(appInd.verifyText(oBrowser, EnrollmentsManagerUIPage.obj_ApplicationSetting_Value_Edit, "Value", appSettingData.get("Value")));

			//Edit the Map values to update the application settings
			appSettingData.remove("Name");
			appSettingData.remove("Value");
			appSettingData.put("Name", "AutoEditAppSetting" + timeStamp);
			appSettingData.put("Value", "Automation Test");

			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, EnrollmentsManagerUIPage.obj_ApplicationSetting_Name_Edit, appSettingData.get("Name")));
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, EnrollmentsManagerUIPage.obj_ApplicationSetting_Value_Edit, appSettingData.get("Value")));

			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_NewAddress_Create_Btn));
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_Suppliers_search_Loading, "Invisibility", "", waitTimeOut);
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_ApplicationSetting_Grid + "//input"), appSettingData.get("Name")));
			appDep.threadSleep(2000);
			reports.writeResult(oBrowser, "Screenshot", "After Editing the application setting");
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_ApplicationSetting_Grid + "//table[contains(@class, 'dx-datagrid-table-content')]//td)[1]"), "Text", appSettingData.get("Name")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_ApplicationSetting_Grid + "//table[contains(@class, 'dx-datagrid-table-content')]//td)[2]"), "Text", appSettingData.get("Value")));
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'editApplicationSetting()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'editApplicationSetting()' method. " + e);
			return false;
		}finally {timeStamp = null;}
	}




	/********************************************************
	 * Method Name      : createandDeleteTheApplicationSettingAndValidateTheSame()
	 * Purpose          : user creates a new application setting & edits the same
	 * Author           : Gudi
	 * Parameters       : oBrowser, dataTable
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean createandDeleteTheApplicationSettingAndValidateTheSame(WebDriver oBrowser, DataTable dataTable){
		List<Map<String, String>> appSettingData = null;
		Map<String, String> appSetting = null;
		String timeStamp = null;
		try{
			timeStamp = appInd.getDateTime("Shhmmss");
			appSettingData = dataTable.asMaps(String.class, String.class);
			appSetting = new HashMap<String, String>();
			appSetting.put("Name", appSettingData.get(0).get("Name") + timeStamp);
			appSetting.put("Value", appSettingData.get(0).get("Value"));
			Assert.assertTrue(selectOptionsFromDevMenu(oBrowser, "Settings"));
			appSetting = createApplicationSetting(oBrowser, appSetting);
			Assert.assertTrue(appSetting.get("TestPassed").equalsIgnoreCase("True"), "The 'createApplicationSetting()' method was failed");
			Assert.assertTrue(editApplicationSetting(oBrowser, appSetting));
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'createandDeleteTheApplicationSettingAndValidateTheSame()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'createandDeleteTheApplicationSettingAndValidateTheSame()' method. " + e);
			return false;
		}finally {
			appSettingData = null; appSetting = null; timeStamp = null;
		}
	}





	/********************************************************
	 * Method Name      : createOREditPaymentProcess()
	 * Purpose          : user creates OR Edits Payment process and validate the same for Enrollment Manager queue
	 * Author           : Gudi
	 * Parameters       : oBrowser, paymentStatus, paymentData
	 * ReturnType       : Map<String, String>
	 ********************************************************/
	public Map<String, String> createOREditPaymentProcess(WebDriver oBrowser, String paymentStatus, Map<String, String> paymentData){
		try{
			if(paymentStatus.equalsIgnoreCase("New")){
				appInd.scrollToElement(oBrowser, EnrollmentsManagerUIPage.obj_SplitterHorizonal_Line);
				Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_PaymentProcess_Link));
				appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_Companies_Table_Loading, "InVisibility", "", waitTimeOut);
				Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_PaymentProcess_Grid + "//a[@title='Add']")));
			}else{
				Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_PaymentProcess_Grid + "//a[@title='Edit']")));
			}

			Assert.assertTrue(appDep.paymentProcess(oBrowser, paymentStatus, paymentData));
			paymentData.put("TestPassed", "True");
			return paymentData;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'createOREditPaymentProcess()' method. " + e);
			paymentData.put("TestPassed", "false");
			return paymentData;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'createOREditPaymentProcess()' method. " + e);
			paymentData.put("TestPassed", "false");
			return paymentData;
		}
	}




	/********************************************************
	 * Method Name      : validatePaymentProcessDetails()
	 * Purpose          : user validates the Payment process grid values & show payment process values after create/edit activities
	 * Author           : Gudi
	 * Parameters       : oBrowser, queueName, dataTable
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean validatePaymentProcessDetails(WebDriver oBrowser, String paymentStatus, Map<String, String> paymentData){
		String arrServiceInfoDatas[];
		String arrServiceInfo[];
		String arrAdditionalInfoDatas[];
		String arrAdditionalInfo[];
		String arrSteps[];
		try{
			appDep.waitForTheElementState(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_PaymentProcess_Grid +"//td[contains(@aria-label, 'Account Numbers,')]//input"), "Clickable", "", minTimeOut);
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_PaymentProcess_Grid +"//td[contains(@aria-label, 'Account Numbers,')]//input"), paymentData.get("AccountNumber")));
			appDep.threadSleep(2000);
			reports.writeResult(oBrowser, "Screenshot", "After creating the '"+paymentStatus+"' payment Process");
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_PaymentProcess_Grid +"//div[@class='dx-datagrid-content']//table//tr[contains(@class, 'lines dx-column-lines')])[1]/td[1]"), "Text").contains(paymentData.get("Method")));
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_PaymentProcess_Grid +"//div[@class='dx-datagrid-content']//table//tr[contains(@class, 'lines dx-column-lines')])[1]/td[2]"), "Text").contains(paymentData.get("AccountNumber")));
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_PaymentProcess_Grid +"//div[@class='dx-datagrid-content']//table//tr[contains(@class, 'lines dx-column-lines')])[1]/td[3]"), "Text").contains(paymentData.get("Address")));

			//Show Payment details
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_PaymentProcess_Grid +"//a[@title='Show']")));
			appDep.waitForTheElementState(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_PaymentProcess_Section), "Visibility", "", minTimeOut);
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_PaymentProcess_Section), "Text").contains("Payment Process"));

			appDep.waitForTheElementState(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_PaymentProcess_Section), "Visibility", "", minTimeOut);
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_PaymentProcess_Section), "Text").contains("Payment Process"));
			reports.writeResult(oBrowser, "Screenshot", "The Show Screen for '"+paymentStatus+"' payment Process");

			//Account Info
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_PaymentProcess_Show_Dialog +"//table/tbody/tr/td)[1]"), "Text").contains(paymentData.get("AccountNumber")));
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_PaymentProcess_Show_Dialog +"//table/tbody/tr/td)[2]"), "Text").contains(paymentData.get("Method")));
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_PaymentProcess_Show_Dialog +"//table/tbody/tr/td)[3]"), "Text").contains(paymentData.get("Address")));


			//Service Info
			arrServiceInfoDatas = paymentData.get("ServiceInformation").split(",");
			for(int i=0; i<arrServiceInfoDatas.length; i++){
				arrServiceInfo = arrServiceInfoDatas[i].split("#");
				Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_PaymentProcess_Show_Dialog+"//table)[2]//tr["+(i+1)+"]/td[1]"),"Text").contains(arrServiceInfo[0]));
				Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_PaymentProcess_Show_Dialog+"//table)[2]//tr["+(i+1)+"]/td[2]"),"Text").contains(arrServiceInfo[1]));
			}

			appInd.scrollToElement(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_PaymentProcess_Show_Dialog + "//div[text()='Notes']"));

			//Additional Info
			arrAdditionalInfoDatas = paymentData.get("AdditionalInformation").split(",");
			for(int i=0; i<arrAdditionalInfoDatas.length; i++){
				arrAdditionalInfo = arrAdditionalInfoDatas[i].split("#");
				Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_PaymentProcess_Show_Dialog+"//table)[5]//tr["+(i+1)+"]/td[1]"),"Text").contains(arrAdditionalInfo[0]));
				Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_PaymentProcess_Show_Dialog+"//table)[5]//tr["+(i+1)+"]/td[2]"),"Text").contains(arrAdditionalInfo[1]));
			}

			//Steps
			arrSteps = paymentData.get("Steps").split("#");
			for(int i=0; i<arrSteps.length; i++){
				Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_PaymentProcess_Show_Dialog+"//table)[4]//tr["+(i+1)+"]/td[1]"), "Text").contains(arrSteps[i]));
				Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_PaymentProcess_Show_Dialog+"//table)[4]//tr["+(i+1)+"]/td[1]"), "Text").contains(arrSteps[i]));
			}

			//Notes
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_PaymentProcess_Show_Dialog+"//div/p"), "Text").contains(paymentData.get("Notes")));

			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_PaymentProcess_Show_Dialog+"//button[1]")));
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'validatePaymentProcessDetails()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'validatePaymentProcessDetails()' method. " + e);
			return false;
		}finally {
			arrServiceInfoDatas = null; arrServiceInfo = null; arrAdditionalInfoDatas = null; arrAdditionalInfo = null; arrSteps = null;
		}
	}


	/********************************************************
	 * Method Name      : createOrEditPaymentProcessAndValidateTheSame()
	 * Purpose          : user creates/Edits a new Payment process and validate the same for Enrollment Manager queue
	 * Author           : Gudi
	 * Parameters       : oBrowser, paymentStatus, queueName, dataTable
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean createOrEditPaymentProcessAndValidateTheSame(WebDriver oBrowser, String paymentStatus, String queueName, DataTable dataTable){
		List<Map<String, String>> paymentData;
		Map<String, String> payments;
		String timeStamp;
		try{
			timeStamp = appInd.getDateTime("Shhmmss");
			paymentData = dataTable.asMaps(String.class, String.class);
			payments = new HashMap<>();
			payments.put("Supplier", paymentData.get(0).get("Supplier"));

			if(paymentData.get(0).get("AccountNumber")==null){
				payments.put("AccountNumber", timeStamp);
			}else{
				payments.put("AccountNumber", paymentData.get(0).get("AccountNumber"));
			}
			paymentAccountNumber = payments.get("AccountNumber");
			payments.put("Method", paymentData.get(0).get("Method"));
			payments.put("Address", paymentData.get(0).get("Address"));
			payments.put("ServiceInformation", paymentData.get(0).get("ServiceInformation"));
			payments.put("AdditionalInformation", paymentData.get(0).get("AdditionalInformation"));
			payments.put("Steps", paymentData.get(0).get("Steps"));
			payments.put("Notes", paymentData.get(0).get("Notes"));
			payments.put("SecurityQuestions", paymentData.get(0).get("SecurityQuestions"));

			if(paymentStatus.equalsIgnoreCase("New"))
				Assert.assertTrue(appDep.navigateToCasesGridOfTheSelectedQueue(oBrowser, queueName));

			payments = createOREditPaymentProcess(oBrowser, paymentStatus, payments);
			Assert.assertTrue(payments.get("TestPassed").equalsIgnoreCase("True"), "The method 'createOREditPaymentProcess()' was failed");
			Assert.assertTrue(validatePaymentProcessDetails(oBrowser, paymentStatus, payments));
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'createOrEditPaymentProcessAndValidateTheSame()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'createOrEditPaymentProcessAndValidateTheSame()' method. " + e);
			return false;
		}finally {
			paymentData = null; payments = null; timeStamp = null;
		}
	}




	/********************************************************
	 * Method Name      : deletePaymentProcessAndValidateTheSame()
	 * Purpose          : user deletes Payment process and validate the same for Enrollment Manager queue
	 * Author           : Gudi
	 * Parameters       : oBrowser, queueName
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean deletePaymentProcessAndValidateTheSame(WebDriver oBrowser, String queueName){
		Alert oAlert = null;
		String confirmMsg = null;
		try{
			reports.writeResult(oBrowser, "Screenshot", "Before deleting the '"+paymentAccountNumber+"' Payment process");
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_PaymentProcess_Grid + "//a[@title='Delete']")));
			appDep.threadSleep(2000);
			oAlert = oBrowser.switchTo().alert();
			confirmMsg = oAlert.getText();
			Assert.assertTrue(appInd.compareValues(oBrowser, confirmMsg, "Are you sure?"));
			oAlert.accept();
			appDep.threadSleep(4000);
			appDep.waitForTheElementState(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_PaymentProcess_Grid +"//td[contains(@aria-label, 'Account Numbers,')]//input"), "Clickable", "", minTimeOut);
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_PaymentProcess_Grid +"//td[contains(@aria-label, 'Account Numbers,')]//input"), paymentAccountNumber));
			appDep.threadSleep(2000);
			Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_PaymentProcess_Grid +"//div[@class='dx-datagrid-content']//table//tr[contains(@class, 'lines dx-column-lines')])[1]/td[2]")));
			reports.writeResult(oBrowser, "Screenshot", "After deleting the '"+paymentAccountNumber+"' Payment process");
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'deletePaymentProcessAndValidateTheSame()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'deletePaymentProcessAndValidateTheSame()' method. " + e);
			return false;
		}finally {
			oAlert = null; confirmMsg = null;
		}
	}




	/********************************************************
	 * Method Name      : searchAndValidateTheSearchOutput()
	 * Purpose          : user deletes Payment process and validate the same for Enrollment Manager queue
	 * Author           : Gudi
	 * Parameters       : oBrowser, queueName
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean searchAndValidateTheSearchOutput(WebDriver oBrowser, DataTable dataTable){
		String searchInput = null;
		List<Map<String, String>> searchData = null;
		Map<String, String> search = null;
		int rowCount = 0;
		String searchResult = null;
		int index = 0;
		try{
			searchData = dataTable.asMaps(String.class, String.class);
			search = new HashMap<String, String>();
			search.put("searchValue", searchData.get(0).get("searchValue"));
			searchInput = search.get("searchValue");
			Assert.assertTrue(appDep.searchEnrolledCases(oBrowser, search.get("searchValue")));
			rowCount = oBrowser.findElements(By.xpath("//td/span[contains(text(), '"+appInd.stringInitCap(searchInput, " ")+"') or contains(text(), '"+searchInput.toUpperCase()+"') or contains(text(), '"+searchInput.toLowerCase()+"')]")).size();
			for(int i=0; i<rowCount; i++){
				index++;
				searchResult = appInd.getTextOnElement(oBrowser, By.xpath("//td/span[contains(text(), '"+appInd.stringInitCap(searchInput, " ")+"') or contains(text(), '"+searchInput.toUpperCase()+"') or contains(text(), '"+searchInput.toLowerCase()+"')]"), "Text");
				Assert.assertTrue(searchResult.toLowerCase().contains(searchInput.toLowerCase()), "The actual '"+searchResult+"' doesnot contains expected '"+searchInput+"' value");
			}

			if(index > 0){
				reports.writeResult(oBrowser, "Pass", "The search was found for the string '"+searchInput+"'");
			}else{
				reports.writeResult(oBrowser, "Fail", "No result was found for the search string '"+searchInput+"'");
				Assert.fail("No result was found for the search string '"+searchInput+"'");
			}
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'searchAndValidateTheSearchOutput()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'searchAndValidateTheSearchOutput()' method. " + e);
			return false;
		}finally {
			searchInput = null; searchData = null; search = null; searchResult = null;
		}
	}





	/********************************************************
	 * Method Name      : registerToExternalEnrollmentSite()
	 * Purpose          : user perform [External Enrollment Site] Test Duplicate Creation
	 * Author           : Gudi
	 * Parameters       : oBrowser, pageName, enrollmentURL, dataTable
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean registerToExternalEnrollmentSite(WebDriver oBrowser, String pageName, String enrollmentURL, DataTable dataTable){
		List<Map<String, String>> enrollmentData = null;
		Map<String, String> enrollment = null;
		String state = null;
		try{
			objEnrollmentSiteDetails = new HashMap<String, String>();
			enrollmentData = dataTable.asMaps(String.class, String.class);
			enrollment = new HashMap<String, String>();
			enrollment.put("ActivationCode", enrollmentData.get(0).get("ActivationCode"));
			enrollment.put("Company_IndividualName", enrollmentData.get(0).get("Company_IndividualName"));
			enrollment.put("TaxID_SSN", enrollmentData.get(0).get("TaxID_SSN"));
			enrollment.put("PhysicalAddress", enrollmentData.get(0).get("PhysicalAddress"));
			enrollment.put("City", enrollmentData.get(0).get("City"));
			enrollment.put("State", enrollmentData.get(0).get("State"));
			enrollment.put("ZipCode", enrollmentData.get(0).get("ZipCode"));
			enrollment.put("TaxClarification", enrollmentData.get(0).get("TaxClarification"));
			enrollment.put("FirstName", enrollmentData.get(0).get("FirstName"));
			enrollment.put("LastName", enrollmentData.get(0).get("LastName"));
			enrollment.put("EmailAddress", enrollmentData.get(0).get("EmailAddress"));
			enrollment.put("PhoneNumber", enrollmentData.get(0).get("PhoneNumber"));
			enrollment.put("Extn", enrollmentData.get(0).get("Extn"));
			enrollment.put("PaymentNotificationEmail", enrollmentData.get(0).get("PaymentNotificationEmail"));
			enrollment.put("PaymentTerm", enrollmentData.get(0).get("PaymentTerm"));
			enrollment.put("EmailForVisaCreditCard", enrollmentData.get(0).get("EmailForVisaCreditCard"));

			Assert.assertTrue(appDep.navigateURL(oBrowser, enrollmentURL, pageName));
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_GetStarted_Btn, "Clickable", "", waitTimeOut);
			Assert.assertTrue(appInd.setObject(oBrowser, EnrollmentsManagerUIPage.obj_ActivationCode_Edit, enrollment.get("ActivationCode")));
			reports.writeResult(oBrowser, "Screenshot", "'External Enrollment site' has opened");
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_GetStarted_Btn));
			appDep.waitForTheElementState(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Company_IndividualName_Edit), "Clickable", "", waitTimeOut);
			appInd.scrollToElement(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Company_IndividualName_Edit));
			Assert.assertTrue(appInd.setObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Company_IndividualName_Edit), enrollment.get("Company_IndividualName")));
			Assert.assertTrue(appInd.setObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_TaxID_SSN_Edit), enrollment.get("TaxID_SSN")));
			Assert.assertTrue(appInd.setObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_PhysicalAddress_Edit), enrollment.get("PhysicalAddress")));
			Assert.assertTrue(appInd.setObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_City_Edit), enrollment.get("City")));
			Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_State_List), enrollment.get("State")));
			state = enrollment.get("State");
			enrollment.remove("State");
			enrollment.put("State", appInd.getTextOnElement(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_State_List +"/option[text()='" + state + "']"), "value"));
			Assert.assertTrue(appInd.setObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_ZipCode_Edit), enrollment.get("ZipCode")));
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_TaxClarification_Grid + "//li/label[text()='"+enrollment.get("TaxClarification")+"']/preceding-sibling::input")));
			Assert.assertTrue(appInd.setObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_FirstName_Edit), enrollment.get("FirstName")));
			Assert.assertTrue(appInd.setObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_LastName_Edit), enrollment.get("LastName")));
			Assert.assertTrue(appInd.setObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_EmailAddress_Edit), enrollment.get("EmailAddress")));
			Assert.assertTrue(appInd.setObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_PhoneNumber_Edit), enrollment.get("PhoneNumber")));
			Assert.assertTrue(appInd.setOptionalObject(oBrowser, EnrollmentsManagerUIPage.obj_PhoneExtn_Edit, enrollment.get("Extn")));
			Assert.assertTrue(appInd.setOptionalObject(oBrowser, EnrollmentsManagerUIPage.obj_PaymentNotificationEmail_Edit, enrollment.get("PaymentNotificationEmail")));
			reports.writeResult(oBrowser, "Screenshot", "Company & Payment Processing Contact Information entered in 'External Enrollment site'");
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Continue_Btn));
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_SelectPaymentTerm_List, "Clickable", "", waitTimeOut);
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, EnrollmentsManagerUIPage.obj_ElectronicPaymentPage_Header, "Text").contains("How fast do"));
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, EnrollmentsManagerUIPage.obj_ElectronicPaymentPage_Header, "Text").contains("you want to get paid?"));
			Assert.assertTrue(appInd.selectObject(oBrowser, EnrollmentsManagerUIPage.obj_SelectPaymentTerm_List, enrollment.get("PaymentTerm")));
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_EmailForVisaCreditDetails_Edit, "Clickable", "", minTimeOut);
			Assert.assertTrue(appInd.setObject(oBrowser, EnrollmentsManagerUIPage.obj_EmailForVisaCreditDetails_Edit, enrollment.get("EmailForVisaCreditCard")));
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_ClarifyEmpCompany_ChkBox));
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_TermsAndCondition_ChkBox));
			reports.writeResult(oBrowser, "Screenshot", "Electronic payment details entered in the 'External Enrollment site'");
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Submit_Btn));
			appDep.waitForTheElementState(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_DetailsCaptured_Grid + "/../preceding-sibling::div[1]//p"), "Text", "Here's what we captured:", waitTimeOut);

			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_DetailsCaptured_Grid + "//ul)[1]/li[1]/span"), "Text", enrollment.get("Company_IndividualName")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_DetailsCaptured_Grid + "//ul)[1]/li[2]/span"), "Text", enrollment.get("TaxID_SSN")));
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_DetailsCaptured_Grid + "//ul)[1]/li[3]"), "Text").contains(enrollment.get("PhysicalAddress")+", "+enrollment.get("City")+" "+enrollment.get("State")+", "+enrollment.get("ZipCode")));
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_DetailsCaptured_Grid + "//ul)[1]/li[4]"), "Text").contains(enrollment.get("PhysicalAddress")+", "+enrollment.get("City")+" "+enrollment.get("State")+", "+enrollment.get("ZipCode")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_DetailsCaptured_Grid + "//ul)[1]/li[5]/span"), "Text", enrollment.get("TaxClarification")));

			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_DetailsCaptured_Grid + "//ul)[2]/li[1]"), "Text", "Full Name: " + enrollment.get("FirstName")+" "+enrollment.get("LastName")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_DetailsCaptured_Grid + "//ul)[2]/li[2]/span"), "Text", enrollment.get("EmailAddress")));
			if(enrollment.get("Extn") == null){
				enrollment.remove("Extn");
				enrollment.put("Extn", "");
			}
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_DetailsCaptured_Grid + "//ul)[2]/li[3]"), "Text", "Phone Number: "+enrollment.get("PhoneNumber")+" ext."+enrollment.get("Extn")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_DetailsCaptured_Grid + "//ul)[3]/li[1]/span"), "Text", enrollment.get("PaymentTerm")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_DetailsCaptured_Grid + "//ul)[3]/li[2]/span"), "Text", enrollment.get("EmailForVisaCreditCard")));
			reports.writeResult(oBrowser, "Screenshot", "After registering the details in the 'External Enrollment site'");
			objEnrollmentSiteDetails = enrollment;
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'registerToExternalEnrollmentSite()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'registerToExternalEnrollmentSite()' method. " + e);
			return false;
		}finally {
			enrollmentData = null; enrollment = null; state = null;
		}
	}




	/********************************************************
	 * Method Name      : validateEnrollmentSiteRegistrationDetails()
	 * Purpose          : user validates the details entered in External Enrollment Site are reflected in Duplicate Supplier Management
	 * Author           : Gudi
	 * Parameters       : oBrowser
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean validateEnrollmentSiteRegistrationDetails(WebDriver oBrowser){
		try{
			appDep.selectManageModules(oBrowser, "Duplicate Supplier Management");
			reports.writeResult(oBrowser, "Screenshot", "Enrolled details in the 'Duplicate Supplier Management'");
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_DuplicateSupplierManagement_Loaded_Grid + "//table//tr[contains(@class, 'row-lines dx-column-lines')])[1]/td[2]"), "Text", objEnrollmentSiteDetails.get("Company_IndividualName")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_DuplicateSupplierManagement_Loaded_Grid + "//table//tr[contains(@class, 'row-lines dx-column-lines')])[1]/td[4]"), "Text", objEnrollmentSiteDetails.get("TaxID_SSN")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_DuplicateSupplierManagement_Loaded_Grid + "//table//tr[contains(@class, 'row-lines dx-column-lines')])[1]/td[5]"), "Text", objEnrollmentSiteDetails.get("PhoneNumber")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_DuplicateSupplierManagement_Loaded_Grid + "//table//tr[contains(@class, 'row-lines dx-column-lines')])[1]/td[7]"), "Text", objEnrollmentSiteDetails.get("PhysicalAddress")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_DuplicateSupplierManagement_Loaded_Grid + "//table//tr[contains(@class, 'row-lines dx-column-lines')])[1]/td[8]"), "Text", objEnrollmentSiteDetails.get("City")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_DuplicateSupplierManagement_Loaded_Grid + "//table//tr[contains(@class, 'row-lines dx-column-lines')])[1]/td[9]"), "Text", objEnrollmentSiteDetails.get("State")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_DuplicateSupplierManagement_Loaded_Grid + "//table//tr[contains(@class, 'row-lines dx-column-lines')])[1]/td[10]"), "Text", objEnrollmentSiteDetails.get("ZipCode")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_DuplicateSupplierManagement_Loaded_Grid + "//table//tr[contains(@class, 'row-lines dx-column-lines')])[1]/td[12]"), "Text", "Enrollment Microservice"));

			//search the enrolled records
			Assert.assertTrue(appDep.searchEnrolledCases(oBrowser, objEnrollmentSiteDetails.get("Company_IndividualName")));
			reports.writeResult(oBrowser, "Screenshot", "The enrolled case was searched successful");
			appDep.waitForTheElementState(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Enrollment_Search_Case_Grid +"/tbody/tr/td[6]/span[text()='Enrollment']"), "Clickable", "", waitTimeOut);
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Enrollment_Search_Case_Grid +"/tbody/tr/td[6]/span[text()='Enrollment']")));
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Enrollment_Search_Case_Grid +"/tbody/tr/td[6]/span[text()='Enrollment']")));
			oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_WaveAndOffers_Header, "Text", "Wave & Offers", waitTimeOut);
			reports.writeResult(oBrowser, "Screenshot", "The enrolled case was opened successful");
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, EnrollmentsManagerUIPage.obj_AlertWarning_Msg, "Text").contains("This case was generated"));
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, EnrollmentsManagerUIPage.obj_AlertWarning_Msg, "Text").contains("from an"));
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, EnrollmentsManagerUIPage.obj_AlertWarning_Msg, "Text").contains("External Enrollment site."));
			appInd.scrollToElement(oBrowser, EnrollmentsManagerUIPage.obj_SplitterHorizonal_Line);
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Activities_Tab));
			appDep.threadSleep(2000);
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_EnrollmentActivities_Grid, "Visibility", "", minTimeOut);
			reports.writeResult(oBrowser, "Screenshot", "The enrolled case-->Activity tab was opened successful");
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Activities_Grid +"//tr[contains(@class, 'row-lines dx-column-lines')]/td[contains(text(), 'Enrolled')]")));
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Activities_Grid +"//tr[contains(@class, 'row-lines dx-column-lines')]/td[text()='Enrollment Complete']")));
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Activities_Grid +"//tr[contains(@class, 'row-lines dx-column-lines')]/td[text()='Enrollment Microservice']")));
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Activities_Grid +"//tr[contains(@class, 'row-lines dx-column-lines')]/td[text()='Case created by Enrollment Microservice']")));
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'validateEnrollmentSiteRegistrationDetails()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateEnrollmentSiteRegistrationDetails()' method. " + e);
			return false;
		}
	}




	/********************************************************
	 * Method Name      : userPullsCompanyAndSupplierLinkDataFromPayNetToPayCRM()
	 * Purpose          : user pulls the Company and Suppliers Link data from PayNet to PayCRM
	 * Author           : Gudi
	 * Parameters       : oBrowser
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean userPullsCompanyAndSupplierLinkDataFromPayNetToPayCRM(WebDriver oBrowser){
		String columnNames[];
		String startDate = null;
		String endDate = null;
		JSONArray dateRange = null;
		try{
			Assert.assertTrue(selectOptionsFromDevMenu(oBrowser, "Manual API Polling"));
			reports.writeResult(oBrowser, "Screenshot", "Manual API Polling page opened successful");

			dateRange = apiDataProvider.getAPIDataProviderResponse("ManualAPIPolling_Links", new Object[] {});
			startDate = (((LinkedHashMap) ((JSONArray) dateRange.get(0)).get(0)).get("Start Date").toString().split("T"))[0];
			endDate = (((LinkedHashMap) ((JSONArray) dateRange.get(0)).get(0)).get("End Date").toString().split("T"))[0];

			startDate = appInd.formatDateFromOnetoAnother(startDate, "yyyy-MM-dd", "MM/d/yyyy");
			endDate = appInd.formatDateFromOnetoAnother(endDate, "yyyy-MM-dd", "MM/dd/yyyy");
			appInd.setObject(oBrowser, EnrollmentsManagerUIPage.obj_ManualAPIPoling_StartDate_Edit, startDate);
			appInd.setObject(oBrowser, EnrollmentsManagerUIPage.obj_ManualAPIPoling_EndDate_Edit, endDate);

			//Type LINK
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_ManualAPIPolling_BtnType + "/button[@value='link']")));
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_RunAPIPoll_Btn));
			appDep.waitForTheElementState(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_LinkPollingResult_Grid + "/following-sibling::div[contains(text(), 'Showing 1 to ')]"), "Visibility", "", maxWaitTimeOut);
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_LinkPollingResult_Grid + "/following-sibling::div[contains(text(), 'Showing 1 to ')]")), "The Manual API Polling result for 'Link' was not displayed");
			appDep.waitForTheElementState(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_LinkPollingResult_Grid + "/tbody/tr/td[1]"), "Visibility", "", maxWaitTimeOut);
			reports.writeResult(oBrowser, "Screenshot", "'Link Polling Result' was displayed successful for the type 'LINK'");
			Assert.assertTrue(appInd.verifyText(oBrowser, EnrollmentsManagerUIPage.obj_LinkPollingResult_Header, "Text", "Link Polling Result"));
			columnNames = "Supplier ID#Supplier Name#Supplier Status#Supplier PayNet ID#Company Name#TIN#Link IDs#Vendor IDs#Outcome".split("#");
			for(int i=0; i<columnNames.length; i++){
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_LinkPollingResult_Grid +"/thead//th["+(i+1)+"]"), "Text", columnNames[i]));
			}


			//Payments:
			dateRange = apiDataProvider.getAPIDataProviderResponse("ManualAPIPolling_Payments", new Object[] {});
			startDate = (((LinkedHashMap) ((JSONArray) dateRange.get(0)).get(0)).get("Start Date").toString().split("T"))[0];
			endDate = (((LinkedHashMap) ((JSONArray) dateRange.get(0)).get(0)).get("End Date").toString().split("T"))[0];

			Assert.assertTrue(selectOptionsFromDevMenu(oBrowser, "Manual API Polling"));
			reports.writeResult(oBrowser, "Screenshot", "Manual API Polling page opened successful");
			startDate = appInd.formatDateFromOnetoAnother(startDate, "yyyy-MM-dd", "MM/d/yyyy");
			endDate = appInd.formatDateFromOnetoAnother(endDate, "yyyy-MM-dd", "MM/dd/yyyy");
			appInd.setObject(oBrowser, EnrollmentsManagerUIPage.obj_ManualAPIPoling_StartDate_Edit, startDate);
			appInd.setObject(oBrowser, EnrollmentsManagerUIPage.obj_ManualAPIPoling_EndDate_Edit, endDate);

			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_ManualAPIPolling_BtnType + "/button[@value='payment']")));
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_RunAPIPoll_Btn));
			appDep.waitForTheElementState(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_LinkPollingPaymentResult_Grid + "/following-sibling::div[contains(text(), 'Showing 1 to ')]"), "Visibility", "", maxWaitTimeOut);
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_LinkPollingPaymentResult_Grid + "/following-sibling::div[contains(text(), 'Showing 1 to ')]")), "The Manual API Polling result for 'Payment' was not displayed");
			appDep.waitForTheElementState(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_LinkPollingPaymentResult_Grid + "/tbody/tr/td[1]"), "Visibility", "", maxWaitTimeOut);
			reports.writeResult(oBrowser, "Screenshot", "'Link Polling Result' was displayed successful for the type 'PAYMENT'");
			Assert.assertTrue(appInd.verifyText(oBrowser, EnrollmentsManagerUIPage.obj_PaymentPollingResults_Header, "Text", "Payment Polling Result"));
			columnNames = "Case ID#Case Status#Link ID#Payment ID Formatted#Return Status#Is CMVP#Outcome".split("#");
			for(int i=0; i<columnNames.length; i++){
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_LinkPollingPaymentResult_Grid +"/thead//th["+(i+1)+"]"), "Text", columnNames[i]));
			}
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'userPullsCompanyAndSupplierLinkDataFromPayNetToPayCRM()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'userPullsCompanyAndSupplierLinkDataFromPayNetToPayCRM()' method. " + e);
			return false;
		}finally{columnNames = null; startDate = null; endDate = null; dateRange = null;}
	}




	/*****************************************************************
	 * Method Name      : applyFiltersOnQueuesDashboard()
	 * Purpose          : to apply the filters on the queues dashboard before clicking "Show Cases" button
	 * Author           : Gudi
	 * Parameters       : oBrowser, queueName
	 * ReturnType       : boolean
	 *****************************************************************/
	public boolean applyFiltersOnQueuesDashboard(WebDriver oBrowser, String queueName){
		try{
			if(queueName.equalsIgnoreCase("Enrollments Manager")){
				appInd.selectObject(oBrowser, EnrollmentsManagerUIPage.obj_AppliedFilter_List, "AutomationFilter_DO Not Modify");
				if(!appInd.getTextOnElement(oBrowser, EnrollmentsManagerUIPage.obj_AppliedFilter_List, "Dropdown").equalsIgnoreCase("AutomationFilter_DO Not Modify")){
					Assert.assertTrue(appDep.selectToggleIcon(oBrowser, EnrollmentsManagerUIPage.obj_AgedOnly_Toggle, "Single", "No"));
					Assert.assertTrue(appDep.selectToggleIcon(oBrowser, EnrollmentsManagerUIPage.obj_IncludeHold_Enrollment_Toggle, "Single", "Yes"));
					appInd.clearAndSetObjectByJavaScript(oBrowser, EnrollmentsManagerUIPage.obj_NumberOfChecks_Edit, "-1");
					appInd.clearAndSetObjectByJavaScript(oBrowser, EnrollmentsManagerUIPage.obj_TotalSpend_Edit, "-1");
					Assert.assertTrue(appDep.selectToggleIcon(oBrowser, EnrollmentsManagerUIPage.obj_OfferType_Toggle, "Multiple", ""));
					Assert.assertTrue(appDep.selectToggleIcon(oBrowser, EnrollmentsManagerUIPage.obj_CaseStatuses_Toggle, "Multiple", ""));
					Assert.assertTrue(appDep.selectToggleIcon(oBrowser, EnrollmentsManagerUIPage.obj_CasePriorities_Toggle, "Multiple", ""));
					Assert.assertTrue(appDep.selectToggleIcon(oBrowser, EnrollmentsManagerUIPage.obj_AssignedTo_Toggle, "Multiple", ""));
				}
			}else if(queueName.equalsIgnoreCase("Assisted Payment Services")){
				appInd.selectObject(oBrowser, EnrollmentsManagerUIPage.obj_AppliedFilter_List, "AutomationFilter_DO Not Modify");
				if(!appInd.getTextOnElement(oBrowser, EnrollmentsManagerUIPage.obj_AppliedFilter_List, "Dropdown").equalsIgnoreCase("AutomationFilter_DO Not Modify")){
					Assert.assertTrue(appDep.selectToggleIcon(oBrowser, EnrollmentsManagerUIPage.obj_SpendOnly_Toggle, "Single", "No"));
					Assert.assertTrue(appDep.selectToggleIcon(oBrowser, EnrollmentsManagerUIPage.getObj_IncludeHold_Payment_Toggle, "Single", "Yes"));
					Assert.assertTrue(appDep.selectToggleIcon(oBrowser, EnrollmentsManagerUIPage.obj_FirstPaymentOnly_Toggle, "Single", "No"));
					appInd.clearAndSetObjectByJavaScript(oBrowser, EnrollmentsManagerUIPage.obj_DueDateDays_Edit, "-5");
					appInd.clearAndSetObjectByJavaScript(oBrowser, EnrollmentsManagerUIPage.obj_PaymentAmount_Edit, "0");
					Assert.assertTrue(appDep.selectToggleIcon(oBrowser, EnrollmentsManagerUIPage.obj_CaseType_Toggle, "Multiple", ""));
					Assert.assertTrue(appDep.selectToggleIcon(oBrowser, EnrollmentsManagerUIPage.obj_CaseStatuses_Toggle, "Multiple", ""));
					Assert.assertTrue(appDep.selectToggleIcon(oBrowser, EnrollmentsManagerUIPage.obj_SupplierPriorities_Toggle, "Multiple", ""));
					Assert.assertTrue(appDep.selectToggleIcon(oBrowser, EnrollmentsManagerUIPage.obj_DueDateRange_Toggle, "Multiple", ""));
				}
			}else if(queueName.equalsIgnoreCase("Partner Enrollments")){
				Assert.assertTrue(appDep.selectToggleIcon(oBrowser, EnrollmentsManagerUIPage.obj_PartnerType_Toggle, "Multiple", ""));
				Assert.assertTrue(appDep.selectToggleIcon(oBrowser, EnrollmentsManagerUIPage.obj_Partner_CaseStatuses_Toggle, "Multiple", ""));
				Assert.assertTrue(appDep.selectToggleIcon(oBrowser, EnrollmentsManagerUIPage.obj_Partner_AssignedTo_Toggle, "Multiple", ""));
			}
			else{
				reports.writeResult(oBrowser, "Fail", "Invalid queue name '"+queueName+"'");
				return false;
			}

			if(!queueName.equalsIgnoreCase("Partner Enrollments")){
				appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Filter_Btn);
				appDep.waitForTheElementState(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Companies_Dashboard_Grid + "//tr[1]/td[@class='dataTables_empty'][text()='Loading...']"), "InVisibility", "", waitTimeOut);
			}

			appDep.threadSleep(4000);
			appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Companies_SelectAll_CheckBox);
			appDep.threadSleep(2000);
			if(!oBrowser.findElement(EnrollmentsManagerUIPage.obj_Companies_SelectAll_CheckBox).isSelected())
				appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Companies_SelectAll_CheckBox);
			appDep.threadSleep(2000);
			reports.writeResult(oBrowser, "Screenshot", "'"+queueName+"' Queue Dashboard filter settings was done ");
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'applyFiltersOnQueuesDashboard()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'applyFiltersOnQueuesDashboard()' method. " + e);
			return false;
		}
	}




	/*****************************************************************
	 * Method Name      : validateCompaniesDashboardFromQueuePages()
	 * Purpose          : user navigates to queues (Assistant Payment Service/Enrollment Managers) and validates the Companies dashboard columns and values
	 * Author           : Gudi
	 * Parameters       : oBrowser, queueName
	 * ReturnType       : boolean
	 *****************************************************************/
	public boolean validateCompaniesDashboardFromQueuePages(WebDriver oBrowser, String queueName){
		List<String> columnNames = null;
		String parentCompany = null;
		String openCases = null;
		Robot robot = null;
		try{

			appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_payCRM_NavigateBar_Link);
			appDep.waitForTheElementState(oBrowser, PayCRMMainUIPage.obj_AssistedPaymentServices_Label, "Clickable", "", waitTimeOut);

			Assert.assertTrue(enrollmentManagersUIBaseSteps.selectRequiredQueue(oBrowser, queueName));
			appDep.waitForTheElementState(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Companies_Dashboard_Grid + "//tr[1]/td[@class='dataTables_empty'][text()='Loading...']"), "InVisibility", "", waitTimeOut);
			appDep.threadSleep(4000);

			//Parent Column should added and data should be present
			if(queueName.equalsIgnoreCase("Enrollments Manager"))
				columnNames = Arrays.asList("Name#Parent#Total Spend#Number of Checks#Open Cases".split("#"));
			else if(queueName.equalsIgnoreCase("Assisted Payment Services"))
				columnNames = Arrays.asList("Name#Parent#Total Payment Amount#First Time Payments#Open Cases".split("#"));
			else {
				reports.writeResult(oBrowser, "Fail", "Invalid queue name '" + queueName + "'");
				Assert.fail("Invalid queue name '" + queueName + "'");
			}

			for(int i=0; i<columnNames.size(); i++){
				appInd.moveToElement(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_Companies_Header_Grid + "//table)[1]//th["+(i+2)+"]"));
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_Companies_Header_Grid + "//table)[1]//th["+(i+2)+"]"), "Text", columnNames.get(i)));
			}


			boolean blnRes = false;
			int rowCount = oBrowser.findElements(By.xpath(EnrollmentsManagerUIPage.obj_Companies_Data_Grid + "//tr")).size();
			for(int i=0; i<rowCount; i++){
				parentCompany = appInd.getTextOnElement(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Companies_Data_Grid + "//tr["+(i+1)+"]/td[3]"), "Text");
				if(!parentCompany.equalsIgnoreCase("")){
					blnRes = true;
					break;
				}
			}

			if(blnRes) reports.writeResult(oBrowser, "Pass", "The 'Parent' column under '"+queueName+"' has the data");
			else {
				reports.writeResult(oBrowser, "Fail", "The 'Parent' column under '"+queueName+"' doesnot has the data");
				Assert.fail("The 'Parent' column under '"+queueName+"' doesnot has the data");
			}


			//Companies with 0 open cases should also be visible
			blnRes = false;
			rowCount = oBrowser.findElements(By.xpath(EnrollmentsManagerUIPage.obj_Companies_Data_Grid + "//tr")).size();
			for(int i=0; i<rowCount; i++){
				openCases = appInd.getTextOnElement(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Companies_Data_Grid + "//tr["+(i+1)+"]/td[6]"), "Text");
				if(openCases.equalsIgnoreCase("0")){
					blnRes = true;
					break;
				}
			}

			if(blnRes) reports.writeResult(oBrowser, "Pass", "The 'Open Cases' column under '"+queueName+"' is having zero cases");
			else {
				reports.writeResult(oBrowser, "Fail", "The 'Open Cases' column under '"+queueName+"' doesnot has the Zero cases");
				Assert.fail("The 'Open Cases' column under '"+queueName+"' doesnot has the Zero cases");
			}
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'validateCompaniesDashboardFromQueuePages()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateCompaniesDashboardFromQueuePages()' method. " + e);
			return false;
		}finally{columnNames = null;}
	}





	/*****************************************************************
	 * Method Name      : verifyDuplicateSupplierFromMicrosite()
	 * Purpose          : user navigates to queues (Assistant Payment Service/Enrollment Managers) and validates the Companies dashboard columns and values
	 * Author           : Gudi
	 * Parameters       : oBrowser, queueName
	 * ReturnType       : Map<String, String>
	 *****************************************************************/
	public Map<String, String> verifyDuplicateSupplierFromMicrosite(WebDriver oBrowser, String companyName, String taxID_SSN, String phoneNumber, DataTable dataTable){
		List<Map<String, String>> enrollmentData = null;
		Map<String, String> enrollment = null;
		String timeStamp = null;
		try{
			timeStamp = appInd.getDateTime("Shhmmss");
			enrollmentData = dataTable.asMaps(String.class, String.class);
			enrollment = new HashMap<String, String>();
			enrollment.put("Company_IndividualName", enrollmentData.get(0).get("Company_IndividualName")+timeStamp);
			enrollment.put("TaxID_SSN", appInd.generateTaxID("Shhmmss"));
			enrollment.put("PhysicalAddress", enrollmentData.get(0).get("PhysicalAddress"));
			enrollment.put("City", enrollmentData.get(0).get("City"));
			enrollment.put("State", enrollmentData.get(0).get("State"));
			enrollment.put("ZipCode", enrollmentData.get(0).get("ZipCode"));
			enrollment.put("TaxClarification", enrollmentData.get(0).get("TaxClarification"));
			enrollment.put("FirstName", enrollmentData.get(0).get("FirstName"));
			enrollment.put("LastName", enrollmentData.get(0).get("LastName")+timeStamp);
			enrollment.put("EmailAddress", enrollmentData.get(0).get("EmailAddress"));
			enrollment.put("PhoneNumber", appInd.generatePhoneNumber("9538", "hhmmss"));
			enrollment.put("From", enrollmentData.get(0).get("From"));

			appInd.scrollToElement(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Company_IndividualName_Edit));
			if(!companyName.isEmpty()) {
				Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Company_IndividualName_Edit), companyName));
				enrollment.remove("Company_IndividualName");
				enrollment.put("Company_IndividualName", companyName);
			}else Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Company_IndividualName_Edit), enrollment.get("Company_IndividualName")));

			if(!taxID_SSN.isEmpty()) {
				Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_TaxID_SSN_Edit), taxID_SSN));
				enrollment.remove("TaxID_SSN");
				enrollment.put("TaxID_SSN", taxID_SSN);
			}else Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_TaxID_SSN_Edit), enrollment.get("TaxID_SSN")));

			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_PhysicalAddress_Edit), enrollment.get("PhysicalAddress")));
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_City_Edit), enrollment.get("City")));
			Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_State_List), enrollment.get("State")));
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_ZipCode_Edit), enrollment.get("ZipCode")));
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_TaxClarification_Grid + "//li/label[text()='"+enrollment.get("TaxClarification")+"']/preceding-sibling::input")));
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_FirstName_Edit), enrollment.get("FirstName")));
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_LastName_Edit), enrollment.get("LastName")));
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_EmailAddress_Edit), enrollment.get("EmailAddress")));

			if(!phoneNumber.isEmpty()) {
				Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_PhoneNumber_Edit), phoneNumber));
				enrollment.remove("PhoneNumber");
				enrollment.put("PhoneNumber", phoneNumber);
			}else Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_PhoneNumber_Edit), enrollment.get("PhoneNumber")));

			reports.writeResult(oBrowser, "Screenshot", "Company & Payment Processing Contact Information entered in 'External Enrollment site'");
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Continue_Btn));
			enrollment.put("TestPassed", "True");
			return enrollment;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'verifyDuplicateSupplierFromMicrosite()' method. " + e);
			enrollment.put("TestPassed", "False");
			return enrollment;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifyDuplicateSupplierFromMicrosite()' method. " + e);
			enrollment.put("TestPassed", "False");
			return enrollment;
		}finally {enrollmentData = null; enrollment = null; timeStamp = null;}
	}





	/*****************************************************************
	 * Method Name      : validateDuplicateSuppliersWithUniqueDetails()
	 * Purpose          : user navigates to microsite and validate "Duplicate Supplier Found" should not be displayed with unique records
	 * Author           : Gudi
	 * Parameters       : oBrowser, pageName, micrositeURL, dataTable
	 * ReturnType       : boolean
	 *****************************************************************/
	public boolean validateDuplicateSuppliersWithUniqueDetails(WebDriver oBrowser, String pageName, String micrositeURL, DataTable dataTable){
		List<Map<String, String>> micrositeDetails = null;
		Map<String, String> supplierData = null;
		try{
			micrositeDetails = dataTable.asMaps(String.class, String.class);
			Assert.assertTrue(appDep.navigateURL(oBrowser, micrositeURL, pageName));
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_GetStarted_Btn, "Clickable", "", waitTimeOut);
			reports.writeResult(oBrowser, "Screenshot", "'External Enrollment site' has opened");
			Assert.assertTrue(appInd.setObject(oBrowser, EnrollmentsManagerUIPage.obj_ActivationCode_Edit, micrositeDetails.get(0).get("ActivationCode")));
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_GetStarted_Btn));
			appDep.waitForTheElementState(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Company_IndividualName_Edit), "Clickable", "", waitTimeOut);

			//Enter unique data and validate that there are no error indicating duplicate supplier found
			supplierData = verifyDuplicateSupplierFromMicrosite(oBrowser, "", "", "", dataTable);
			Assert.assertTrue(supplierData.get("TestPassed").equalsIgnoreCase("True"), "The method 'verifyDuplicateSupplierFromMicrosite()' has failed");

			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_SelectPaymentTerm_List, "Clickable", "", waitTimeOut);
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_PaymentTab_Link + "/a[contains(@class, 'active')][text()='Payment']")));

			oBrowser.navigate().back();
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_ActivationCode_Edit, "Clickable", "", waitTimeOut);
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'validateDuplicateSuppliersWithUniqueDetails()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateDuplicateSuppliersWithUniqueDetails()' method. " + e);
			return false;
		}finally {micrositeDetails = null; supplierData = null;}
	}





	/*****************************************************************
	 * Method Name      : validateComponentsAndItsFunctionalityUnderDuplicateSupplierFoundDialog()
	 * Purpose          : user validates components and its elements functionality under "Duplicate Supplier Found" Dialog
	 * Author           : Gudi
	 * Parameters       : oBrowser, tileName
	 * ReturnType       : boolean
	 *****************************************************************/
	public boolean validateComponentsAndItsFunctionalityUnderDuplicateSupplierFoundDialog(WebDriver oBrowser, String tileName, Map<String, String> supplierData){
		try{
			switch(tileName.toLowerCase()){
				case "update company information":
					Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_DuplicateSupplierFound_Dialog + "//label[@for='radio-card-1']")));
					break;
				case "update contact information":
					Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_DuplicateSupplierFound_Dialog + "//label[@for='radio-card-2']")));
					break;
				case "update payment information":
					Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_DuplicateSupplierFound_Dialog + "//label[@for='radio-card-3']")));
					break;
				case "other":
					Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_DuplicateSupplierFound_Dialog + "//label[@for='radio-card-4']")));
			}

			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_AdditionalInformation_Textarea, "Clickable", "", waitTimeOut);
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, EnrollmentsManagerUIPage.obj_AdditionalInformation_Textarea));
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, EnrollmentsManagerUIPage.obj_UseExistingContact_Btn));
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, EnrollmentsManagerUIPage.obj_UseNewContact_Btn));

			//Verify data under "Use  Existing Contact" section
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_UseExistingContact_Pane + "//div[@id='existing_name']"), "Text", supplierData.get("FirstName") +" "+ supplierData.get("LastName")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_UseExistingContact_Pane + "//div[@id='existing_number']"), "Text", supplierData.get("PhoneNumber")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_UseExistingContact_Pane + "//div[@id='existing_email']"), "Text", supplierData.get("EmailAddress")));


			//Click on 'User New Contact"
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_UseNewContact_Btn));
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_FullName_Edit)));
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Email_Edit)));
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Phone_Edit)));
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, EnrollmentsManagerUIPage.obj_Extension_Edit));

			//Click on the "Submit" button without entering the data into "Use New Contacts" section
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_DuplicateSupplierFound_Dialog + "//button[@id='btn_modal_submit']")));
			appDep.threadSleep(2000);
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_FullName_Edit + "/following-sibling::span[1]"), "Text", "Please enter a Full Contact Name"));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Email_Edit + "/following-sibling::span[1]"), "Text", "Please enter a valid Email Address"));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Phone_Edit + "/following-sibling::span[1]"), "Text", "Please enter a valid Phone Number"));
			appDep.threadSleep(2000);
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'validateComponentsAndItsFunctionalityUnderDuplicateSupplierFoundDialog()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateComponentsAndItsFunctionalityUnderDuplicateSupplierFoundDialog()' method. " + e);
			return false;
		}
	}





	/*****************************************************************
	 * Method Name      : validateDuplicateSuppliersWithDuplicateDetails()
	 * Purpose          : user navigates to microsite and validate "Duplicate Supplier Found" should not be displayed with unique records
	 * Author           : Gudi
	 * Parameters       : oBrowser, pageName, micrositeURL, dataTable
	 * ReturnType       : boolean
	 *****************************************************************/
	public boolean validateDuplicateSuppliersWithDuplicateDetails(WebDriver oBrowser, String pageName, String micrositeURL, DataTable dataTable){
		List<Map<String, String>> micrositeDetails = null;
		String companyName = null;
		String taxID = null;
		String phoneNumber = null;
		Map<String, String> supplierData = null;
		String fullName_NewContact = null;
		String email_NewContact = null;
		String phone_NewContact = null;
		try{
			if(System.getProperty("environment").equalsIgnoreCase("TEST")){
				companyName = "DELOITTE";
				taxID = "832176539";
				phoneNumber = "9173667712";
			}else{
				companyName = "Chocolate";
				taxID = "989898981";
				phoneNumber = "9988998899";
			}
			micrositeDetails = dataTable.asMaps(String.class, String.class);
			Assert.assertTrue(appDep.navigateURL(oBrowser, micrositeURL, pageName));
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_GetStarted_Btn, "Clickable", "", waitTimeOut);
			reports.writeResult(oBrowser, "Screenshot", "'External Enrollment site' has opened");

			//Enter invalid activation code and validate the appropriate error message displayed
			Assert.assertTrue(appInd.setObject(oBrowser, EnrollmentsManagerUIPage.obj_ActivationCode_Edit, "AbCdEfGhIjKlMnOp"));
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_GetStarted_Btn));
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_InvalidActivationCode_Label, "Clickable", "", waitTimeOut);
			Assert.assertTrue(appInd.verifyText(oBrowser, EnrollmentsManagerUIPage.obj_InvalidActivationCode_Label, "Text", "Invalid Activation Code"));

			//Enter valid Activation code
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, EnrollmentsManagerUIPage.obj_ActivationCode_Edit, micrositeDetails.get(0).get("ActivationCode")));
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_GetStarted_Btn));
			appDep.waitForTheElementState(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Company_IndividualName_Edit), "Clickable", "", waitTimeOut);


			//Without entering any data in the "Company Information" Page, click on "Continue" button and validate the appropriate error messages for the mandatory fields
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Continue_Btn));
			appDep.threadSleep(2000);
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Company_IndividualName_Edit + "/following-sibling::span[1]"), "Text", "Please enter a Company Legal Name"));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_TaxID_SSN_Edit + "/following-sibling::span[1]"), "Text", "Please enter a valid EIN / TIN"));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_PhysicalAddress_Edit + "/following-sibling::span[1]"), "Text", "Please enter a Street Address"));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_City_Edit + "/following-sibling::span[1]"), "Text", "Please enter a City"));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_State_List + "/following-sibling::span[1]"), "Text", "Please select a State"));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_ZipCode_Edit + "/following-sibling::span[1]"), "Text", "Please enter a valid Zip Code"));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_FirstName_Edit + "/following-sibling::span[1]"), "Text", "Please enter a First Name"));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_LastName_Edit + "/following-sibling::span[1]"), "Text", "Please enter a Last Name"));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_EmailAddress_Edit + "/following-sibling::span[1]"), "Text", "Please enter a valid Email Address"));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_PhoneNumber_Edit + "/following-sibling::span[1]"), "Text", "Please enter a valid Phone Number"));



			//Enter duplicate value for Company Name only and validate "Duplicate Supplier Found" message displayed and trigger the email
			supplierData = verifyDuplicateSupplierFromMicrosite(oBrowser, companyName, "", "", dataTable);
			Assert.assertTrue(supplierData.get("TestPassed").equalsIgnoreCase("True"), "The method 'verifyDuplicateSupplierFromMicrosite()' has failed");

			appDep.waitForTheElementState(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_DuplicateSupplierFound_Dialog + "//button[@id='btn_modal_submit']"), "Clickable", "", waitTimeOut);
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_DuplicateSupplierFound_Dialog + "//h2"), "Text", "Duplicate Supplier Found"));

			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_DuplicateSupplierFound_Dialog + "//label[@for='radio-card-1']")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_DuplicateSupplierFound_Dialog + "//label[@for='radio-card-1']"), "Text", "Update company information"));

			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_DuplicateSupplierFound_Dialog + "//label[@for='radio-card-2']")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_DuplicateSupplierFound_Dialog + "//label[@for='radio-card-2']"), "Text", "Update contact information"));

			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_DuplicateSupplierFound_Dialog + "//label[@for='radio-card-3']")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_DuplicateSupplierFound_Dialog + "//label[@for='radio-card-3']"), "Text", "Update payment information"));

			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_DuplicateSupplierFound_Dialog + "//label[@for='radio-card-4']")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_DuplicateSupplierFound_Dialog + "//label[@for='radio-card-4']"), "Text", "Other"));

			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_DuplicateSupplierFound_Dialog + "//button[@id='btn_modal_submit']")));
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_DuplicateSupplierFound_Dialog + "//div[text()='Cancel']")));

			//Click on "Update Company Information" tile and validate the elements and its functionality
			Assert.assertTrue(validateComponentsAndItsFunctionalityUnderDuplicateSupplierFoundDialog(oBrowser, "Update company information", supplierData));
			oBrowser.navigate().refresh();
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_Continue_Btn, "Clickable", "", waitTimeOut);


			//Enter duplicate value for taxID only and validate "Duplicate Supplier Found" message displayed
			supplierData = verifyDuplicateSupplierFromMicrosite(oBrowser, "", taxID, "", dataTable);
			Assert.assertTrue(supplierData.get("TestPassed").equalsIgnoreCase("True"), "The method 'verifyDuplicateSupplierFromMicrosite()' has failed");

			//Click on "Update Payment Information" tile and validate the elements and its functionality
			Assert.assertTrue(validateComponentsAndItsFunctionalityUnderDuplicateSupplierFoundDialog(oBrowser, "Update Payment information", supplierData));
			oBrowser.navigate().refresh();
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_Continue_Btn, "Clickable", "", waitTimeOut);


			//Enter duplicate value for PhoneNumber only and validate "Duplicate Supplier Found" message displayed
			supplierData = verifyDuplicateSupplierFromMicrosite(oBrowser, "", "", phoneNumber, dataTable);
			Assert.assertTrue(supplierData.get("TestPassed").equalsIgnoreCase("True"), "The method 'verifyDuplicateSupplierFromMicrosite()' has failed");

			//Click on "Update Payment Information" tile and validate the elements and its functionality
			Assert.assertTrue(validateComponentsAndItsFunctionalityUnderDuplicateSupplierFoundDialog(oBrowser, "Other", supplierData));
			oBrowser.navigate().refresh();
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_Continue_Btn, "Clickable", "", waitTimeOut);


			//Enter duplicate value for Company Name, TaxID and phone number and validate "Duplicate Supplier Found" message displayed & also validate the email functionality
			supplierData = verifyDuplicateSupplierFromMicrosite(oBrowser, companyName, taxID, phoneNumber, dataTable);
			Assert.assertTrue(supplierData.get("TestPassed").equalsIgnoreCase("True"), "The method 'verifyDuplicateSupplierFromMicrosite()' has failed");

			//Click on "Update Contact Information" tile and validate the elements and its functionality
			Assert.assertTrue(validateComponentsAndItsFunctionalityUnderDuplicateSupplierFoundDialog(oBrowser, "Update Contact information", supplierData));
			fullName_NewContact = "regression"+appInd.getDateTime("Shhmmss");
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_FullName_Edit), fullName_NewContact));
			email_NewContact = "ggudi@corcentric.com";
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Email_Edit), email_NewContact));
			phone_NewContact = supplierData.get("PhoneNumber");
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Phone_Edit), phone_NewContact));

			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_DuplicateSupplierFound_Dialog + "//button[@id='btn_modal_submit']")));
			appDep.threadSleep(5000);
			if(System.getProperty("environment").equalsIgnoreCase("Test")){
				supplierData.put("Subject", "[STAGING] Duplicate Supplier Enrollment Info.");
			}else{
				supplierData.put("Subject", "[STAGING] Duplicate Supplier Enrollment Info.");
			}

			supplierData.put("RequestSupport", "Update Contact Information");
			supplierData.put("ContactFullName", fullName_NewContact);
			supplierData.put("ContactEmail", email_NewContact);
			supplierData.put("ContactPhone", phone_NewContact);


			//Validate the iResult Screen from microsite
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_EmailResults_Section + "//p[1]"), "Text", "Thank you for submitting your information. Someone from our team will be reaching out to you soon to complete your request! In the meantime, here is the information we captured."));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_EmailResults_Section + "//div[text()='Company of Information Name']/following-sibling::div[1]/span"), "Text", supplierData.get("Company_IndividualName")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_EmailResults_Section + "//div[text()='Tax ID or SSN']/following-sibling::div[1]/span"), "Text", supplierData.get("TaxID_SSN")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_EmailResults_Section + "//div[text()='Requested Support']/following-sibling::div[1]/span"), "Text", supplierData.get("RequestSupport")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_EmailResults_Section + "//div[text()='Contact']/following-sibling::div[1]/span[1]"), "Text", supplierData.get("ContactFullName")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_EmailResults_Section + "//div[text()='Contact']/following-sibling::div[1]/span[2]"), "Text", supplierData.get("ContactEmail")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_EmailResults_Section + "//div[text()='Contact']/following-sibling::div[1]/span[3]"), "Text", supplierData.get("ContactPhone")+" ext."));

			//Validate the email body
			Assert.assertTrue(emailUtilities.connectAndOpenEmails(supplierData));
			Assert.assertTrue(emailUtilities.readEmailFromDuplicateSupplierNotification(oEmailBrowser, supplierData));
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'validateDuplicateSuppliersWithDuplicateDetails()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateDuplicateSuppliersWithDuplicateDetails()' method. " + e);
			return false;
		}finally {micrositeDetails = null; companyName = null; taxID = null; phoneNumber = null; supplierData = null; fullName_NewContact = null; email_NewContact = null; phone_NewContact = null;}
	}

	/*****************************************************************
	 * Method Name      : applyFilterForAssistantServiceDashboard()
	 * Purpose          : This method helps user to set filter based on requirement for bill pay dashboard.
	 * Author           : Shidd
	 * Parameters       : oBrowser, dataTable
	 * ReturnType       : boolean
	 *****************************************************************/
	public boolean applyFilterForAssistantServiceDashboard(WebDriver oBrowser, DataTable dataTable) {
		List<Map<String, String>> filterData;
		String[] paymentRange, dueDateDays;
		try {
			filterData = dataTable.asMaps(String.class, String.class);

			if (filterData.get(0).get("Queue Name") != null) {
				Assert.assertTrue(appInd.selectObject(oBrowser, AssistedPaymentServicesUIPage.obj_queueSelect, filterData.get(0).get("Queue Name")));
			}

			if (filterData.get(0).get("Supplier Name") != null)
				Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_supplierNameFilter, filterData.get(0).get("Supplier Name")));

			if (filterData.get(0).get("Payment Range") != null) {
				paymentRange = filterData.get(0).get("Payment Range").split("-");
				Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_lowPaymentAmountRange, paymentRange[0]));
				Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_highPaymentAmountRange, paymentRange[1]));
			}

			if (filterData.get(0).get("Due Date Days") != null) {
				dueDateDays = filterData.get(0).get("Due Date Days").split("-");

				if (dueDateDays[0].equalsIgnoreCase("LessThan"))
					Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_lessThanDueDateDays));
				else if (dueDateDays[0].equalsIgnoreCase("GreaterThan"))
					Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_greaterThanDueDateDays));
				else
					Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_equalToDueDateDays));

				Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_dueDateDays, dueDateDays[1]));
			}

			if (filterData.get(0).get("Spend Only") != null) {
				if (filterData.get(0).get("Spend Only").equalsIgnoreCase("True"))
					Assert.assertTrue(appInd.switchToggleButton(oBrowser, AssistedPaymentServicesUIPage.obj_spendOnlyFilter, "True"));
				else
					Assert.assertTrue(appInd.switchToggleButton(oBrowser, AssistedPaymentServicesUIPage.obj_spendOnlyFilter, "False"));
			}

			if (filterData.get(0).get("Reopened Case") != null) {
				if (filterData.get(0).get("Reopened Case").equalsIgnoreCase("True"))
					Assert.assertTrue(appInd.switchToggleButton(oBrowser, AssistedPaymentServicesUIPage.obj_reopenedCasesFilter, "True"));
				else
					Assert.assertTrue(appInd.switchToggleButton(oBrowser, AssistedPaymentServicesUIPage.obj_reopenedCasesFilter, "False"));
			}

			if (filterData.get(0).get("Include Hold") != null) {
				if (filterData.get(0).get("Include Hold").equalsIgnoreCase("True"))
					Assert.assertTrue(appInd.switchToggleButton(oBrowser, AssistedPaymentServicesUIPage.obj_includeHoldFilter, "True"));
				else
					Assert.assertTrue(appInd.switchToggleButton(oBrowser, AssistedPaymentServicesUIPage.obj_includeHoldFilter, "False"));
			}

			if (filterData.get(0).get("First Time Payment") != null) {
				if (filterData.get(0).get("First Time Payment").equalsIgnoreCase("True"))
					Assert.assertTrue(appInd.switchToggleButton(oBrowser, AssistedPaymentServicesUIPage.obj_firstTimePaymentFilter, "True"));
				else
					Assert.assertTrue(appInd.switchToggleButton(oBrowser, AssistedPaymentServicesUIPage.obj_firstTimePaymentFilter, "False"));
			}

			Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_saveFilter));
			return true;
		} catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'applyFilterForAssistantServiceDashboard()' method. " + e);
			return false;
		} catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'applyFilterForAssistantServiceDashboard()' method. " + e);
			return false;
		} finally {
			filterData = null; paymentRange = null; dueDateDays = null;
		}
	}

	/********************************************************
	 * Method Name      : loginToPayCRMWithAnotherUser()
	 * Purpose          : Login to the application
	 * Author           : Gudi
	 * Parameters       : oBrowser, queueName
	 * ReturnType       : boolean
	 ********************************************************/
	public WebDriver loginToPayCRMWithAnotherUser1(String appName, String qaDetails){
		String arrDetails[];
		WebDriver oDriver = null;
		try{
			oDriver = appInd.launchBrowser();
			arrDetails = appInd.getPropertyValueByKeyName(qaDetails).split("#", -1);
			if(oDriver!=null){
				Assert.assertTrue(appDep.navigateURL(oDriver, arrDetails[0], appName), "URL navigation is failed for '"+arrDetails[0]+"' URL");
				Assert.assertTrue(appDep.loginToApplication(oDriver, arrDetails[1], arrDetails[2], appName), "Login was failed for the userName '"+arrDetails[1]+"'");
				reports.writeResult(oDriver, "Screenshot", "Login to the '"+appName+"' was successful");
				return oDriver;
			}else{
				Assert.assertTrue(false, "Failed to launch the '"+ appInd.browserName+"' browser");
				return null;
			}
		}catch(Exception e) {
			reports.writeResult(oDriver, "Exception", "Exception in 'loginToPayCRMWithAnotherUser()' method. " + e);
			return null;
		}catch(AssertionError e) {
			reports.writeResult(oDriver, "Exception", "AssertionError in 'loginToPayCRMWithAnotherUser()' method. " + e);
			return null;
		}finally {arrDetails = null;}
	}


}
