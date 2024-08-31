package com.corcentric.common;

import com.corcentric.pages.*;
import com.corcentric.runner.CucumberTestRunner;
import io.cucumber.datatable.DataTable;
import net.minidev.json.JSONArray;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.List;

public class AppDependentMethods extends CucumberTestRunner {
    /********************************************************
     * Method Name      : navigateURL()
     * Purpose          : to navigate the URL
     * Author           : Gudi
     * Parameters       : oBrowser, appURL
     * ReturnType       : boolean
     ********************************************************/
    public boolean navigateURL(WebDriver oBrowser, String appURL, String appName){
        try{
        	oBrowser.navigate().to(appInd.getPropertyValueByKeyName(appURL));
        	switch(appName.toLowerCase()) {
        		case "paycrm": case "awsexternal": case "enrollment cases": case "suppliers->show": case "companies->show": case "companies->prospect": case "activities":
        			waitForTheElementState(oBrowser, PayCRMMainUIPage.obj_LOGIN_btn, "clickable", "", maxWaitTimeOut);
        			Assert.assertEquals(oBrowser.getTitle(), "PayCRM", "Invalid title was displayed for the '"+appName+"' URL navigation");
        			break;
				case "paynet":
					//waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_LOGIN_btn, "clickable", "", 20);
				 	//Assert.assertEquals(oBrowser.getTitle(), "Sign-In", "Invalid title was displayed for the '"+appName+"' URL navigation");
				case "enrollmentsite":
					waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_GetStarted_Btn, "clickable", "", maxWaitTimeOut);
					Assert.assertTrue(appInd.verifyElementPresent(oBrowser, EnrollmentsManagerUIPage.obj_GetStarted_Btn));
					break;
				case "outlook":
					waitForTheElementState(oBrowser, EmailUtilities.EmailPhone_Edit, "clickable", "", maxWaitTimeOut);
					Assert.assertTrue(appInd.verifyElementPresent(oBrowser, EmailUtilities.obj_EmailPhone_Next_btn));
					break;
				case "na":
					reports.writeResult(oBrowser, "info", "Navigated to " + oBrowser.getTitle() + " page.");
					break;
        		default:
        			reports.writeResult(oBrowser, "Fail", "Invalid application name '"+appName+"' was specified");
        			return false;
        	}
			return true;
        }catch(Exception e){
            reports.writeResult(oBrowser, "Exception", "Exception in 'navigateURL()' method. " + e);
			return false;
        }catch(AssertionError e){
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'navigateURL()' method. " + e);
			return false;
		}
    }
    
    
    
    
    
    /********************************************************
     * Method Name      : loginToApplication()
     * Purpose          : to login to the required application 
     * Author           : Gudi
     * Parameters       : oBrowser, userName, password
     * ReturnType       : boolean
     ********************************************************/
    public boolean loginToApplication(WebDriver oBrowser, String userName, String password, String appName) {
    	try {
			appDep.waitForTheElementState(oBrowser, PayCRMMainUIPage.obj_UserName_Edit, "Clickable", "", waitTimeOut);
    		Assert.assertTrue(appInd.setObject(oBrowser, PayCRMMainUIPage.obj_UserName_Edit, appInd.getPropertyValueByKeyName(userName)), "setObject() was failed for '"+String.valueOf(PayCRMMainUIPage.obj_UserName_Edit)+"' webelement");
    		Assert.assertTrue(appInd.setObject(oBrowser, PayCRMMainUIPage.obj_Password_Edit, appInd.getPropertyValueByKeyName(password)), "setObject() was failed for '"+String.valueOf(PayCRMMainUIPage.obj_Password_Edit)+"' webelement");
    		Assert.assertTrue(appInd.clickObject(oBrowser, PayCRMMainUIPage.obj_LOGIN_btn), "clickObject() was failed for '"+String.valueOf(PayCRMMainUIPage.obj_LOGIN_btn)+"' webelement");
			switch(appName.toLowerCase()){
				case "paycrm":
					appDep.waitForTheElementState(oBrowser, PayCRMMainUIPage.obj_EnrollmentsManager_Label, "Clickable", "", maxWaitTimeOut);
					Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRMMainUIPage.obj_EnrollmentsManager_Label), "verifyElementPresent() was failed for '"+String.valueOf(PayCRMMainUIPage.obj_EnrollmentsManager_Label)+"' webelement");
					break;
				case "awsexternal":
					appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_WhiteListGroup_IPAddress_Grid_Header, "Visibility", "", maxWaitTimeOut);
					Assert.assertTrue(appInd.verifyText(oBrowser, EnrollmentsManagerUIPage.obj_WhiteListGroup_IPAddress_Grid_Header, "Text", "IP match conditions"));
					break;
				case "enrollment cases":
					appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_WaveAndOffers_Header, "Text", "Wave & Offers", maxWaitTimeOut);
					Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_WaveAndOffers_Header, "Text", "Wave & Offers"));
					break;
				case "suppliers->show": case "companies->show":
					appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ViewActivities_Btn, "Clickable", "", maxWaitTimeOut);
					Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ViewActivities_Btn));
					break;
				case "companies->prospect":
					appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", maxWaitTimeOut);
					Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit));
					break;
				case "activities":
					waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ShowEditDetails_Header, "Text", "Information", waitTimeOut);
					Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ShowEditDetails_Header, "Text", "Information"));
					break;
				default:
					reports.writeResult(oBrowser, "Fail", "Invalid application name '"+appName+"' was specified.");
					return false;
			}
    		return true;
    	}catch(Exception e) {
    		reports.writeResult(oBrowser, "Exception", "Exception in 'loginToApplication()' method. " + e);
    		return false;
    	}catch(AssertionError e){
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'loginToApplication()' method. " + e);
			return false;
		}
    }
    
    
    
    
    /********************************************************
     * Method Name      : waitForTheElementState()
     * Purpose          : to sync between the script speed and the application rendering speed
     * Author           : Gudi
     * Parameters       : oBrowser, objBy, strWaitFor, textValue, timeOut
     * ReturnType       : boolean
     ********************************************************/
    public boolean waitForTheElementState(WebDriver oBrowser, By objBy, String waitCondition, String textValue, long waitTimeOut) {
    	WebDriverWait oWait = null;
    	try {
    		oWait = new WebDriverWait(oBrowser, Duration.ofSeconds(waitTimeOut));
    		switch (waitCondition.toLowerCase()) {
				case "clickable":
					oWait.until(ExpectedConditions.elementToBeClickable(objBy));
					break;
				case "text":
					oWait.until(ExpectedConditions.textToBePresentInElementLocated(objBy, textValue));
					break;
				case "value":
					oWait.until(ExpectedConditions.textToBePresentInElementValue(objBy, textValue));
					break;
				case "visibility":
					oWait.until(ExpectedConditions.visibilityOfElementLocated(objBy));
					break;
				case "presence":
					oWait.until(ExpectedConditions.presenceOfElementLocated(objBy));
					break;
				case "refreshed":
					oWait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(objBy)));
					break;
				case "invisibility":
					oWait.until(ExpectedConditions.invisibilityOfElementLocated(objBy));
					break;
				default:
					reports.writeResult(oBrowser, "Fail", "Invalid wait condition '"+waitCondition+"' was provided. Provide the appropriate");
			}
    		return true;
    	}catch(Exception e) {
    		System.out.println("Exception in 'waitForTheElementState()' method. " + String.valueOf(objBy));
    		return false;
    	}
    }




	/********************************************************
	 * Method Name      : waitForTheElementState()
	 * Purpose          : to sync between the script speed and the application rendering speed
	 * Author           : Gudi
	 * Parameters       : oBrowser, objBy, strWaitFor, textValue, timeOut
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean waitForTheElementState(WebDriver oBrowser, WebElement objElement, String waitCondition, String textValue, long waitTimeOut) {
		WebDriverWait oWait = null;
		try {
			oWait = new WebDriverWait(oBrowser, Duration.ofSeconds(waitTimeOut));
			switch (waitCondition.toLowerCase()) {
				case "clickable":
					oWait.until(ExpectedConditions.elementToBeClickable(objElement));
					break;
				case "text":
					oWait.until(ExpectedConditions.textToBePresentInElement(objElement, textValue));
					break;
				case "value":
					oWait.until(ExpectedConditions.textToBePresentInElementValue(objElement, textValue));
					break;
				case "visibility":
					oWait.until(ExpectedConditions.visibilityOf(objElement));
					break;
				case "refreshed":
					oWait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(objElement)));
					break;
				case "invisibility":
					oWait.until(ExpectedConditions.invisibilityOf(objElement));
					break;
				default:
					reports.writeResult(oBrowser, "Fail", "Invalid wait condition '"+waitCondition+"' was provided. Provide the appropriate");
			}
			return true;
		}catch(Exception e) {
			System.out.println("Exception in 'waitForTheElementState()' method. " + String.valueOf(objElement));
			return false;
		}
	}



	/********************************************************
	 * Method Name      : compareDateRangeWithActualDate()
	 * Purpose          : compare actual date with range of expected dates
	 * Author           : Gudi
	 * Parameters       : startDate, endDate, actualDate
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean compareDateRangeWithActualDate(String startDate, String endDate, String actualDate){
		SimpleDateFormat sdf = null;
		Date dtStartDate = null;
		Date dtEndDate = null;
		Date dtDateEnrolled = null;
		try{
			sdf = new SimpleDateFormat("dd-MM-yyyy");
			dtStartDate = sdf.parse(startDate);
			dtEndDate = sdf.parse(endDate);
			dtDateEnrolled = sdf.parse(actualDate);

			if((dtStartDate.compareTo(dtDateEnrolled) <= 0) || (dtEndDate.compareTo(dtDateEnrolled) >= 0)){
				return true;
			}else{
				return false;
			}
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'compareDateRangeWithActualDate()' method. " + e);
			return false;
		}
	}

	/********************************************************
	 * Method Name      : verifyLatestDownloadFile()
	 * Purpose          : validate last download the file downloaded/exported successful and delete the file
	 * Author           : Gudi
	 * Parameters       : startDate, fileName, fileExtension
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean verifyLatestDownloadFile(WebDriver oBrowser, String fileName, String extension, String content, boolean delete) {
		String filePath;
		boolean blnRes = false;
		try{
			Path dir = Paths.get("C:/Users/"+System.getProperty("user.name")+"/Downloads");
			Optional<Path> lastFilePath = Files.list(dir)
					.filter(f -> !Files.isDirectory(f))
					.max(Comparator.comparingLong(f -> f.toFile().lastModified()));
			filePath = lastFilePath.get().toString();
			if ( lastFilePath.isPresent() )
			{
				if((fileName.equals("NA") || filePath.equals(fileName)) && filePath.contains(extension)){
					Assert.assertTrue(appInd.readFromPdfFile(filePath).contains(content), "The .pdf file content is not matching with the expected value '"+content+"'");
					blnRes=true;
				}
			}
			if(blnRes && delete){
					lastFilePath.get().toFile().delete();
					lastFilePath.get().toFile().deleteOnExit();
			}
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'validateDownloadedFileAndDelete()' method. " + e);
			return false;
		}
		return true;
	}

	/********************************************************
	 * Method Name      : validateDownloadedFileAndDelete()
	 * Purpose          : validate the file downloaded/exported successful and delete the file
	 * Author           : Gudi
	 * Parameters       : startDate, fileName, fileExtension
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean validateDownloadedFileAndDelete(WebDriver oBrowser, String fileName, String fileExtension, String fileContent, String deleteFile){
		File objFile;
		File objArrFiles[];
		String filePath;
		String strText = null;
		long lastModifiedTime = Long.MIN_VALUE;
		try{
			boolean blnRes = false;
			objFile = new File("C:/Users/"+System.getProperty("user.name")+"/Downloads");
			objArrFiles = objFile.listFiles(File::isFile);
			for(int i=0; i < objArrFiles.length; i++){
					filePath = objArrFiles[i].getPath();
					if(objArrFiles[i].lastModified() > lastModifiedTime){
						if(fileName.equals("NA") && filePath.contains(fileExtension)){
							Assert.assertTrue(appInd.readFromPdfFile(filePath).contains(fileContent), "The .pdf file content '"+strText+"' was not matching with the expected value '"+fileContent+"'");
						}else if(filePath.contains(fileName) && filePath.contains(fileExtension)){
							blnRes = true;
						}
					}
				if(blnRes){
					if(deleteFile.equalsIgnoreCase("Yes")){
						objArrFiles[i].delete();
						objArrFiles[i].deleteOnExit();
					}
					break;
				}
			}
			return blnRes;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'validateDownloadedFileAndDelete()' method. " + e);
			return false;
		}
		finally {
			objFile = null; objArrFiles = null; filePath = null; strText = null;
		}
	}




	/********************************************************
	 * Method Name      : mouseOverAndValidateTheToolTip()
	 * Purpose          : validate the tooltip message for the elements by hovering on them
	 * Author           : Gudi
	 * Parameters       : startDate, fileName, fileExtension
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean mouseOverAndValidateTheToolTip(WebDriver oBrowser, By objBy, String expectedToolTip, String columnName){
		Actions oActions = null;
		WebElement objEle = null;
		try{
			oActions = new Actions(oBrowser);
			objEle = appInd.createAndGetWebElement(oBrowser, objBy);
			oActions.moveToElement(objEle).build().perform();
			appDep.threadSleep(2000);
			Assert.assertTrue(objEle.getAttribute("data-original-title").equalsIgnoreCase(expectedToolTip));
			reports.writeResult(oBrowser, "Screenshot", "Mouse hover on the column name '"+columnName+"' of the grid 'Card Network Match Confidence'");
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'mouseOverAndValidateTheToolTip()' method. " + e);
			return false;
		}finally{
			oActions = null; objEle = null;
		}
	}





	/********************************************************
	 * Method Name      : uploadFileUsingRobotClass()
	 * Purpose          : Upload file using robot class
	 * Author           : Gudi
	 * Parameters       : filePath
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean uploadFileUsingRobotClass(String filePath){
		StringSelection strPath = null;
		Clipboard clipboard = null;
		Robot robot = null;
		try{
			strPath = new StringSelection(filePath);
			clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(strPath, null);

			robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			appDep.threadSleep(2000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			appDep.threadSleep(5000);
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'uploadFileUsingRobotClass()' method. " + e);
			return false;
		}finally{
			strPath = null; clipboard = null; robot = null;
		}
	}



	/********************************************************
	 * Method Name      : selectTheCompanyBasedOnType()
	 * Purpose          : User navigates to Modules-->General-->Companies & select the company name based on the type specified
	 * Author           : Gudi
	 * Parameters       : oBrowser, companyType
	 * ReturnType       : String
	 ********************************************************/
	public String selectTheCompanyBasedOnType(WebDriver oBrowser, String companyType){
		int rowCount = 0;
		String cellData = null;
		String companyName = null;
		boolean blnRes = false;
		try{
			Assert.assertTrue(selectGeneralModules(oBrowser, "Companies"));
			appDep.threadSleep(2000);
			reports.writeResult(oBrowser, "Screenshot", "General-->Companies page opened");
			Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_Companies_Grid + "//input)[8]"), companyType));
			appDep.threadSleep(2000);
			rowCount = oBrowser.findElements(By.xpath(PayCRM_Modules_GeneralUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//tr")).size();
			for(int i=0; i<rowCount; i++){
				cellData = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//tr["+(i+1)+"]/td[7]"), "Text");
				if(cellData.equalsIgnoreCase(companyType)){
					blnRes = true;
					companyName = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Companies_Grid + "//tr[contains(@class, 'lines dx-column-lines')]["+(i+1)+"]/td[1]"), "Text").trim();
					appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//tr["+(i+1)+"]/td[1]"), "Clickable", "", waitTimeOut);
					reports.writeResult(oBrowser, "Screenshot", "The company type '"+companyType+"' was found in the companiy grid");
					Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//tr["+(i+1)+"]/td[7]")));
					if(companyType.equalsIgnoreCase("Prospect")){
						appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", waitTimeOut);
					}else{
						appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ViewActivities_Btn, "Clickable", "", waitTimeOut);
					}
					break;
				}
			}

			if(blnRes == false){
				reports.writeResult(oBrowser, "Fail", "No Company Name was found with type as '"+companyType+"'");
				Assert.fail("No Company Name was found with type as 'Prospect'");
			}else{
				reports.writeResult(oBrowser, "Pass", "The Company '"+companyName+"' with type as '"+companyType+"' was selected successful");
				reports.writeResult(oBrowser, "Screenshot", "The Company '"+companyName+"' with type as '"+companyType+"' was selected successful");
			}
			return companyName;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'selectTheCompanyBasedOnType()' method. " + e);
			return null;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'selectTheCompanyBasedOnType()' method. " + e);
			return null;
		}finally{cellData = null; companyName = null;}
	}



	/********************************************************
	 * Method Name      : selectGeneralModules()
	 * Purpose          : to verify that the use can navigate to different modules in the General section viz., Companies, Suppliers, Contacts
	 * Author           : Gudi
	 * Parameters       : oBrowser, generalType
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean selectGeneralModules(WebDriver oBrowser, String generalType){
		try{
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Modules_Menu, "Clickable", "", waitTimeOut);
			Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Modules_Menu), "clickObject() was failed for '"+PayCRM_Modules_GeneralUIPage.obj_Modules_Menu+"' webelement");
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Modules_General_Companies_Link, "Clickable", "", waitTimeOut);
			switch(generalType.toLowerCase()){
				case "companies":
					Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Modules_General_Companies_Link), "clickObject() was failed for the '"+PayCRM_Modules_GeneralUIPage.obj_Modules_General_Companies_Link+"' webelement");
					appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Companies_FirstRow, "Visibility", "", waitTimeOut);
					break;
				case "suppliers":
					Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Modules_General_Suppliers_Link), "clickObject() was failed for the '"+PayCRM_Modules_GeneralUIPage.obj_Modules_General_Suppliers_Link+"' webelement");
					appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Suppliers_PaynetCompanyID_Edit, "Clickable", "", waitTimeOut);
					break;
				case "contacts":
					Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Modules_General_Contacts_Link), "clickObject() was failed for the '"+PayCRM_Modules_GeneralUIPage.obj_Modules_General_Contacts_Link+"' webelement");
					appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Contacts_tableGrid, "Visibility", "", waitTimeOut);
					break;
				default:
					reports.writeResult(oBrowser, "Fail", "Invalid general type value '"+generalType+"' was specified. The supported ones are: 'Companies, Suppliers, Contacts'");
					return false;
			}
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'selectGeneralModules()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'selectGeneralModules()' method. " + e);
			return false;
		}
	}




	/********************************************************
	 * Method Name      : selectDailyGrindModules()
	 * Purpose          : to verify that the use can navigate to different modules in the Daily Grind section viz., Program Management, Cases, Queues, StopFraud & Partner Enrollments
	 * Author           : Gudi
	 * Parameters       : oBrowser, dailyGrindType
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean selectDailyGrindModules(WebDriver oBrowser, String dailyGrindType){
		try{
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Modules_Menu, "Clickable", "", waitTimeOut);
			Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Modules_Menu), "clickObject() was failed for '"+PayCRM_Modules_GeneralUIPage.obj_Modules_Menu+"' webelement");
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Modules_General_Companies_Link, "Clickable", "", waitTimeOut);
			switch(dailyGrindType.toLowerCase()){
				case "program management":
					Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_DailyGrind_ProgramManagement_Link), "clickObject() was failed for the '"+PayCRM_Modules_GeneralUIPage.obj_DailyGrind_ProgramManagement_Link+"' webelement");
					appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ProgramManagement_Grid_Pagination, "Visibility", "", waitTimeOut);
					break;
				case "cases":
					Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_DailyGrind_Cases_Link), "clickObject() was failed for the '"+PayCRM_Modules_GeneralUIPage.obj_DailyGrind_Cases_Link+"' webelement");
					appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_RecordLimits_Edit, "Clickable", "", waitTimeOut);
					break;
				case "queues":
					Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_DailyGrind_Queues_Link), "clickObject() was failed for the '"+PayCRM_Modules_GeneralUIPage.obj_DailyGrind_Queues_Link+"' webelement");
					appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ProgramManagement_Grid_Pagination, "Visibility", "", waitTimeOut);
					break;
				case "stopfraud":
					Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_DailyGrind_StopFraud_Link), "clickObject() was failed for the '"+PayCRM_Modules_GeneralUIPage.obj_DailyGrind_StopFraud_Link+"' webelement");
					appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_StopFraud_ActionType_Grid, "Visibility", "", waitTimeOut);
					break;
				case "partner enrollments":
					Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_DailyGrind_PartnerEnrollments_Link), "clickObject() was failed for the '"+PayCRM_Modules_GeneralUIPage.obj_DailyGrind_PartnerEnrollments_Link+"' webelement");
					appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PartnerEnrollment_ShowCases_btn, "Clickable", "", waitTimeOut);
					break;
				case "support cases":
					Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_DailyGrind_SupportCases_Link), "clickObject() was failed for the '"+PayCRM_Modules_GeneralUIPage.obj_DailyGrind_PartnerEnrollments_Link+"' webelement");
					appDep.waitForTheElementState(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_row, "Active", "1")), "Visibility", "", waitTimeOut);
					break;
				default:
					reports.writeResult(oBrowser, "Fail", "Invalid Daily Grinds type value '"+dailyGrindType+"' was specified. The supported ones are: 'Program Management, Cases, Queues, StopFraud & Partner Enrollments'");
					return false;
			}
			reports.writeResult(oBrowser, "Pass", "The module '"+dailyGrindType+"' was selected form the 'Daily Grind' section");
			reports.writeResult(oBrowser, "Screenshot", "The module '"+dailyGrindType+"' was selected form the 'Daily Grind' section");
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'selectDailyGrindModules()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'selectDailyGrindModules()' method. " + e);
			return false;
		}
	}




	/********************************************************
	 * Method Name      : selectImportsModules()
	 * Purpose          : to verify that the use can navigate to different modules in the Imports section viz., Master Supplier Files, Match Files, Payment Based Enrollments & Report Files
	 * Author           : Gudi
	 * Parameters       : oBrowser, importsType
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean selectImportsModules(WebDriver oBrowser, String importsType){
		try{
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Modules_Menu, "Clickable", "", waitTimeOut);
			Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Modules_Menu), "clickObject() was failed for '"+PayCRM_Modules_GeneralUIPage.obj_Modules_Menu+"' webelement");
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFiles_Link, "Clickable", "", waitTimeOut);
			switch(importsType.toLowerCase()){
				case "master supplier files":
					Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MasterSupplierFiles_Link));
					appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Cases_Grid_Pagination, "Visibility", "", waitTimeOut);
					break;
				case "match files":
					Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_MatchFiles_Link));
					appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Cases_Grid_Pagination, "Clickable", "", waitTimeOut);
					break;
				case "payment based enrollments":
					Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_PaymentBasedEnrollments_Link));
					appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Cases_Grid_Pagination, "Visibility", "", waitTimeOut);
					break;
				case "report files":
					Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Imports_ReportFiles_Link));
					appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Cases_Grid_Pagination, "Visibility", "", waitTimeOut);
					break;
				default:
					reports.writeResult(oBrowser, "Fail", "Invalid Imports type value '" + importsType + "' was specified. The supported ones are: 'Master Supplier Files, Match Files, Payment Based Enrollments & Report Files'");
					return false;
			}
			reports.writeResult(oBrowser, "Screenshot", "'"+importsType+"' page was opened from the Modules-->Imports section");
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'selectImportsModules()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'selectImportsModules()' method. " + e);
			return false;
		}
	}






	/********************************************************
	 * Method Name      : selectManageModules()
	 * Purpose          : to verify that the use can navigate to different modules in the Manage section viz., User console, Reports, Monetization Tracking & Duplicate Supplier Management
	 * Author           : Gudi
	 * Parameters       : oBrowser, manageType
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean selectManageModules(WebDriver oBrowser, String manageType){
		try{
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Modules_Menu, "Clickable", "", waitTimeOut);
			Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Modules_Menu), "clickObject() was failed for '"+PayCRM_Modules_GeneralUIPage.obj_Modules_Menu+"' webelement");
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Manage_UserConsole_Link, "Clickable", "", waitTimeOut);
			switch(manageType.toLowerCase()){
				case "user console":
					Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Manage_UserConsole_Link));
					appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Cases_Grid_Pagination, "Visibility", "", waitTimeOut);
					break;
				case "reports":
					Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Manage_Reports_Link));
					appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_paymentsParentSection, "Clickable", "", waitTimeOut);
					break;
				case "monetization tracking":
					Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Manage_MonetizationTracking_Link));
					appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.Obj_Manage_MonetizationTracking_SpendEfficiency_Label, "Text", "Spend Efficiency", waitTimeOut);
					break;
				case "duplicate supplier management":
					Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Manage_DuplicateSupplierManagement_Link));
					appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Manage_DuplicateSupplierManagement_GridPagination_Label, "Visibility", "", waitTimeOut);
					break;
				default:
					reports.writeResult(oBrowser, "Fail", "Invalid Imports type value '" + manageType + "' was specified. The supported ones are: 'User console, Reports, Monetization Tracking & Duplicate Supplier Management'");
					return false;
			}
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'selectManageModules()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'selectManageModules()' method. " + e);
			return false;
		}
	}


	/********************************************************
	 * Method Name      : selectManagePaymentModules()
	 * Purpose          : to verify that the use can navigate to different modules in the Manage section viz.
	 * Author           : Gudi
	 * Parameters       : oBrowser, manageType
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean selectManagePaymentModules(WebDriver oBrowser, String manageType){
		try{
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Modules_Menu, "Clickable", "", waitTimeOut);
			Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Modules_Menu), "clickObject() was failed for '"+PayCRM_Modules_GeneralUIPage.obj_Modules_Menu+"' webelement");
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Manage_Payment_Pending_Bank_Files_Link, "Clickable", "", waitTimeOut);
			switch(manageType){
				case "Pending Bank Files":
					Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Manage_Payment_Pending_Bank_Files_Link));
					appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Cases_Grid_Pagination, "Visibility", "", waitTimeOut);
					break;
				case "Bank File Receipts":
					Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Manage_Payment_Bank_File_Receipts_Link));
					appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Cases_Grid_Pagination, "Clickable", "", waitTimeOut);
					break;
				case "Transmission Files":
					Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Manage_Payment_Transmission_Files_Link));
					appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Cases_Grid_Pagination, "Text", "", waitTimeOut);
					break;
				case "Payment Search":
					Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Manage_Payment_Payment_Search_Link));
					appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentSearch_TextBox, "Visibility", "", waitTimeOut);
					break;
				case "Voids":
					Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Manage_Payment_Voids_Link));
					String tab = String.format(PayCRM_Modules_GeneralUIPage.obj_tab_names,"Payment Void Requests");
					appDep.waitForTheElementState(oBrowser, By.xpath(tab), "Visibility", "", waitTimeOut);
					break;
				default:
					reports.writeResult(oBrowser, "Fail", "Invalid Imports type value '" + manageType + "' was specified.'");
					return false;
			}
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
			reports.writeResult(oBrowser, "Screenshot", "The '"+manageType+"' page has opened form the 'Managed Payments' section");
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'selectManagePaymentModules()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'selectManagePaymentModules()' method. " + e);
			return false;
		}
	}



	/********************************************************
	 * Method Name      : verifyColumnNamesForGeneralModulesGrid()
	 * Purpose          : to verify the all the column names from the General modules table grid
	 * Author           : Gudi
	 * Parameters       : oBrowser
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean verifyColumnNamesForGeneralModulesGrid(WebDriver oBrowser, DataTable dataTable){
		List<Map<String, String>> companiesColumnNames = null;
		String columnNames[];
		WebElement oEle = null;
		WebElement objColName = null;
		try{
			companiesColumnNames = dataTable.asMaps(String.class, String.class);
			columnNames = companiesColumnNames.get(0).get("columnNames").split("#");
			oEle = appInd.createAndGetWebElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Companies_Table_ColumnNames));
			objColName = null;
			for(int i=0; i<columnNames.length; i++){
				objColName = oEle.findElement(By.xpath("//tbody/tr[1]/td[@role]["+(i+1)+"]"));
				if(columnNames[i].equals("Actions")){
					Assert.assertTrue(objColName.getAttribute("aria-label").contains(columnNames[i]), "Mis-match in the actual & expected values for the column names in the '"+String.valueOf(objColName)+"' webelement");
				}else{
					Assert.assertTrue(objColName.getText().equals(columnNames[i]), "Mis-match in the actual & expected values for the column names in the '"+String.valueOf(objColName)+"' webelement");
				}
			}
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'verifyColumnNamesForGeneralModulesGrid()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifyColumnNamesForGeneralModulesGrid()' method. " + e);
			return false;
		}
		finally {
			companiesColumnNames = null; columnNames = null; oEle = null; objColName = null;
		}
	}




	/********************************************************
	 * Method Name      : navigateToCasesGridOfTheSelectedQueue()
	 * Purpose          : user navigates to the Case grid of the selected Queue name viz., Enrollment Manager & Assisted Payment Services
	 * Author           : Gudi
	 * Parameters       : oBrowser, queueName
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean navigateToCasesGridOfTheSelectedQueue(WebDriver oBrowser, String queueName){
		try{
			Assert.assertTrue(enrollmentManagersUIBaseSteps.selectRequiredQueue(oBrowser, queueName));
			Assert.assertTrue(enrollmentManagersUIBaseSteps.clickOnShowCaseButton(oBrowser, queueName));
			Assert.assertNotNull(enrollmentManagersUIBaseSteps.readAndClickResultFirstCaseNumber(oBrowser, queueName));
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'navigateToCasesGridOfTheSelectedQueue()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'navigateToCasesGridOfTheSelectedQueue()' method. " + e);
			return false;
		}
	}




	/********************************************************
	 * Method Name      : validateTheGeneralModulesTableColumnNames()
	 * Purpose          : to verify the column names of the General modules (Companies/Suppliers/Contacts) table grid
	 * Author           : Gudi
	 * Parameters       : oBrowser, generalTypes, dataTable
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean validateTheGeneralModulesTableColumnNames(WebDriver oBrowser, String generalTypes, DataTable dataTable){
		try{
			Assert.assertTrue(appDep.selectGeneralModules(oBrowser, generalTypes), "selectGeneralModules() method was failed");
			switch(generalTypes.toLowerCase()){
				case "companies":
					Assert.assertTrue(appDep.verifyColumnNamesForGeneralModulesGrid(oBrowser, dataTable), "verifyColumnNamesForGeneralModulesGrid() method was failed for '"+generalTypes+"'");
					break;
				case "contacts":
					Assert.assertTrue(appDep.verifyColumnNamesForGeneralModulesGrid(oBrowser, dataTable), "verifyColumnNamesForGeneralModulesGrid() method was failed for '"+generalTypes+"'");
					break;
				case "suppliers":
					Assert.assertTrue(appDep.verifyColumnNamesForGeneralModulesGrid(oBrowser, dataTable), "verifyColumnNamesForGeneralModulesGrid() method was failed for '"+generalTypes+"'");
					break;
				default:
					reports.writeResult(oBrowser, "Fail", "Invalid value for general module name '"+generalTypes+"'. The supported ones are: 'Companies', 'Suppliers' & 'Contacts'");
					return false;
			}
			reports.writeResult(oBrowser, "Screenshot", "Companies table grid");
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'validateTheGeneralModulesTableColumnNames()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateTheGeneralModulesTableColumnNames()' method. " + e);
			return false;
		}
	}




	/********************************************************
	 * Method Name      : exportAndCompareTheExportedDataWithTableGridData()
	 * Purpose          : Export and validate the exported file data with the table grid data
	 * Author           : Gudi
	 * Parameters       : oBrowser, objGridLocator, fileName
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean exportAndCompareTheExportedDataWithTableGridData(WebDriver oBrowser, String objGridLocator, String fileName){
		boolean blnRes = false;
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row = null;
		Cell cell = null;
		List<WebElement> oRows = null;
		List<WebElement> oColumns = null;
		String gridCellData = null;
		String excelCellData = null;
		try{
			blnRes = true;
			fin = new FileInputStream("C:/Users/"+System.getProperty("user.name")+"/Downloads/" + fileName + ".xlsx");
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet("Sheet");
			oRows = oBrowser.findElements(By.xpath(objGridLocator + "//div[@class='dx-datagrid-content']//table//tr[contains(@class, 'lines dx-column-lines')]"));
			for(int r=0; r<oRows.size(); r++){
				row = sh.getRow(r+1);
				oColumns = oBrowser.findElements(By.xpath("("+objGridLocator + "//div[@class='dx-datagrid-content']//table//tr[contains(@class, 'lines dx-column-lines')])["+(r+1)+"]/td"));
				for(int c=0; c<oColumns.size()-1; c++){
					cell = row.getCell(c);
					gridCellData = oBrowser.findElement(By.xpath("("+objGridLocator + "//div[@class='dx-datagrid-content']//table//tr[contains(@class, 'lines dx-column-lines')])["+(r+1)+"]/td["+(c+1)+"]")).getText();
					if(gridCellData.contains("$")) gridCellData = gridCellData.replace(",", "");
					excelCellData = appInd.getExcelCellData(cell);
					if((!gridCellData.trim().equalsIgnoreCase(excelCellData.replace("$", "").trim())) && (!gridCellData.trim().contains(excelCellData.replace("$", "").trim())) && (!excelCellData.trim().contains(gridCellData.replace("$", "").trim()))){
						blnRes = false;
						reports.writeResult(oBrowser, "Fail", "Mis-match in actual: "+excelCellData+" & Expected: "+ gridCellData+" values");
						break;
					}
				}
				if(blnRes==false) break;
			}
			return blnRes;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'exportAndCompareTheExportedDataWithTableGridData()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'exportAndCompareTheExportedDataWithTableGridData()' method. " + e);
			return false;
		}finally {
			try{
				fin.close(); fin = null; wb = null; sh = null; row = null; cell = null; oRows = null; oColumns = null; gridCellData = null; excelCellData = null;
				new File("C:/Users/"+System.getProperty("user.name")+"/Downloads/" + fileName + ".xlsx").delete();
			}catch(Exception e){}
		}
	}




	/********************************************************
	 * Method Name      : navigateToInternalSetupPage()
	 * Purpose          : User navigates to Internal setup page either from Home page/Accoutn setup page
	 * Author           : Gudi
	 * Parameters       : oBrowser, fromPage
	 * ReturnType       : boolean
	 ********************************************************/
	public String navigateToInternalSetupPage(WebDriver oBrowser, String fromPage){
		String companyName = null;
		try{
			if(fromPage.equalsIgnoreCase("HomePage"))
				companyName = appDep.selectTheCompanyBasedOnType(oBrowser, "Prospect");

			appInd.scrollToThePage(oBrowser, "Top");
			appDep.threadSleep(4000);
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SecretSauce_Asterisk_Btn, "Clickable", "", waitTimeOut);
			Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SecretSauce_Asterisk_Btn));
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_InternalSetup_Btn, "Clickable", "", waitTimeOut);
			if(!appInd.verifyOptionalElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_InternalSetup_Button + "[contains(@style, 'display: block')]")))
				Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SecretSauce_Asterisk_Btn));
			Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_InternalSetup_Btn));
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GlobalVariable_Link, "Clickable", "", waitTimeOut);
			if(!appInd.verifyOptionalElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GlobalVariable_Link))
				Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_InternalSetup_Btn));
			appDep.threadSleep(2000);
			reports.writeResult(oBrowser, "Screenshot", "The 'Internal Setup' page has opened successful");
			return companyName;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'navigateToInternalSetupPage()' method. " + e);
			return null;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'navigateToInternalSetupPage()' method. " + e);
			return null;
		}finally {companyName = null;}
	}





	/********************************************************
	 * Method Name      : navigateToProspectCompanyAttachmentScreen()
	 * Purpose          : User navigates to Attachmnet screen of the prospect company from Home page/Account setup page
	 * Author           : Gudi
	 * Parameters       : oBrowser, fromPage
	 * ReturnType       : boolean
	 ********************************************************/
	public String navigateToProspectCompanyAttachmentScreen(WebDriver oBrowser, String fromPage){
		String companyName = null;
		try{
			if(fromPage.equalsIgnoreCase("HomePage"))
				companyName = appDep.selectTheCompanyBasedOnType(oBrowser, "Prospect");

			appInd.scrollToThePage(oBrowser, "Top");
			appDep.threadSleep(4000);
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SecretSauce_Asterisk_Btn, "Clickable", "", waitTimeOut);
			Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SecretSauce_Asterisk_Btn));
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_InternalSetup_Btn, "Clickable", "", waitTimeOut);
			if(!appInd.verifyOptionalElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_InternalSetup_Btn))
				Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SecretSauce_Asterisk_Btn));
			Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SolutionDesigns_Link));
			appDep.threadSleep(4000);
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Attachments_Link, "Clickable", "", waitTimeOut);
			Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Attachments_Link));
			appDep.threadSleep(4000);
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_OtherAttachments_Label));
			return companyName;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'navigateToProspectCompanyAttachmentScreen()' method. " + e);
			return null;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'navigateToProspectCompanyAttachmentScreen()' method. " + e);
			return null;
		}finally {companyName = null;}
	}





	/********************************************************
	 * Method Name      : validateCorcentricPaymentMixDataForPaymentTypes()
	 * Purpose          : User validates the corcentric payment mix data against differnet payment types under Account Setup-->Solution Desing-->Corcentric Reuslts-->Post Corcentric Optimization grid
	 * Author           : Gudi
	 * Parameters       : oBrowser, paymentType, paymentData, revenueGenerating
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean validateCorcentricPaymentMixDataForPaymentTypes(WebDriver oBrowser, String paymentType, String paymentData, String revenueGenerating){
		try{
			if(revenueGenerating.equalsIgnoreCase("True")){
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PostCorcentricOptimization_Grid +"//table//tr//span[@data-original-title='"+paymentType+"']/parent::td/following-sibling::td[4]"), "Text", paymentData));
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PostCorcentricOptimization_Grid +"//table//tr//span[@data-original-title='"+paymentType+"']/parent::td/following-sibling::td[5]"), "Text", paymentData));
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PostCorcentricOptimization_Grid +"//table//tr//span[@data-original-title='"+paymentType+"']/parent::td/following-sibling::td[6]"), "Text", paymentData));
			}else{
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PostCorcentricOptimization_Grid +"//table//tr//span[@data-original-title='"+paymentType+"']/parent::td/following-sibling::td[4]"), "Text", paymentData));
				Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PostCorcentricOptimization_Grid +"//table//tr//span[@data-original-title='"+paymentType+"']/parent::td/following-sibling::td[5]/img[contains(@src, 'no-revenue')]")));
				Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PostCorcentricOptimization_Grid +"//table//tr//span[@data-original-title='"+paymentType+"']/parent::td/following-sibling::td[6]/img[contains(@src, 'no-revenue')]")));
			}
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'validateCorcentricPaymentMixDataForPaymentTypes()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateCorcentricPaymentMixDataForPaymentTypes()' method. " + e);
			return false;
		}
	}




	/********************************************************
	 * Method Name      : validateCurrentPaymentMixDataForPaymentTypes()
	 * Purpose          : User validates the Current payment mix data against differnet payment types under Account Setup-->Solution Design-->Current State-->Pre Corcentric Optimization grid
	 * Author           : Gudi
	 * Parameters       : oBrowser, paymentType, revenueGenerating
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean validateCurrentPaymentMixDataForPaymentTypes(WebDriver oBrowser, String paymentType, String revenueGenerating){
		try{
			if(revenueGenerating.equalsIgnoreCase("False")){
				if(paymentType.equalsIgnoreCase("Mixed")){
					Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PreCorcentricOptimization_Grid +"//tr//td[contains(text(),'"+paymentType+"')]/following-sibling::td[6]/img")));
					Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PreCorcentricOptimization_Grid +"//tr//td[contains(text(),'"+paymentType+"')]/following-sibling::td[7]/img")));
				}else{
					Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PreCorcentricOptimization_Grid +"//tr//span[@data-original-title='"+paymentType+"']/parent::td/following-sibling::td[6]/img")));
					Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PreCorcentricOptimization_Grid +"//tr//span[@data-original-title='"+paymentType+"']/parent::td/following-sibling::td[7]/img")));
				}
			}else{
				if(paymentType.equalsIgnoreCase("Mixed")){
					Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PreCorcentricOptimization_Grid +"//tr//td[contains(text(),'"+paymentType+"')]/following-sibling::td[6]/img")));
					Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PreCorcentricOptimization_Grid +"//tr//td[contains(text(),'"+paymentType+"')]/following-sibling::td[7]/img")));
				}else{
					Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PreCorcentricOptimization_Grid +"//tr//span[@data-original-title='"+paymentType+"']/parent::td/following-sibling::td[6]/img")));
					Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PreCorcentricOptimization_Grid +"//tr//span[@data-original-title='"+paymentType+"']/parent::td/following-sibling::td[7]/img")));
				}
			}
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'validateCurrentPaymentMixDataForPaymentTypes()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateCurrentPaymentMixDataForPaymentTypes()' method. " + e);
			return false;
		}
	}



	/********************************************************
	 * Method Name      : calculatorOnline()
	 * Purpose          : calculate the big double numbers
	 * Author           : Gudi
	 * Parameters       : numbers
	 * ReturnType       : String
	 ********************************************************/
	public String calculatorOnline(WebDriver driver, List<String> numbers){
		try{
			Assert.assertTrue(appInd.clearAndSetObject(driver, By.xpath("//input[@name='x']"), numbers.get(0)));
			Assert.assertTrue(appInd.clearAndSetObject(driver, By.xpath("//input[@name='x2']"), numbers.get(1)));
			Assert.assertTrue(appInd.clickObject(driver, By.xpath("//input[@value='Calculate']")));
			appDep.threadSleep(1000);
			return appInd.getTextOnElement(driver, By.xpath("//input[@id='y']"), "Value");
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'calculatorOnline()' method. " + e);
			return null;
		}
	}


	public void threadSleep(int miliSecond){
		try {
			Thread.sleep(miliSecond);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}


	/********************************************************
	 * Method Name      : handleCaptcha()
	 * Purpose          : close the captcha image
	 * Author           : Gudi
	 * Parameters       : driver
	 * ReturnType       : void
	 ********************************************************/
	public void handleCaptcha(WebDriver driver) throws InterruptedException{
		appDep.waitForTheElementState(driver, By.xpath("//span[text()='×']"), "Clickable", "", 10);
		if(appInd.verifyOptionalElementPresent(driver, By.xpath("//span[text()='×']"))){
			appDep.threadSleep(4000);
			driver.findElement(By.xpath("//span[text()='×']")).click();
		}
	}



	/********************************************************
	 * Method Name      : searchEnrolledCases()
	 * Purpose          : search the enrolled cases
	 * Author           : Gudi
	 * Parameters       : oBrowser, searchEnrollment
	 * ReturnType       : String
	 ********************************************************/
	public boolean searchEnrolledCases(WebDriver oBrowser, String enrollmentName){
		try{
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_Search_Edit, "Clickable", "", minTimeOut);
			Assert.assertTrue(appInd.setObject(oBrowser, EnrollmentsManagerUIPage.obj_Search_Edit, enrollmentName));
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_SearchIcon_Btn));
			oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
			appDep.waitForTheElementState(oBrowser, By.xpath("//td/span[contains(text(), '"+appInd.stringInitCap(enrollmentName, " ")+"') or contains(text(), '"+enrollmentName.toUpperCase()+"') or contains(text(), '"+enrollmentName.toLowerCase()+"')]"), "Visibility", "", waitTimeOut);
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//td/span[contains(text(), '"+appInd.stringInitCap(enrollmentName, " ")+"') or contains(text(), '"+enrollmentName.toUpperCase()+"') or contains(text(), '"+enrollmentName.toLowerCase()+"')]")));
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'searchEnrolledCases()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'searchEnrolledCases()' method. " + e);
			return false;
		}
	}





	/********************************************************
	 * Method Name      : searchCaseActivitiesByDateRange()
	 * Purpose          : search the case activities by providing the date range
	 * Author           : Gudi
	 * Parameters       : oBrowser, searchEnrollment
	 * ReturnType       : String
	 ********************************************************/
	public boolean searchCaseActivitiesByDateRange(WebDriver oBrowser, String dateRangeValue, By objBy){
		try{
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CaseActivities_Link, "Clickable", "", minTimeOut);
			Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CaseActivities_Link));
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ViewActivities_Btn, "Clickable", "", minTimeOut);
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_DateRange_Edit, dateRangeValue));
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_DateRange_Apply_Btn, "Clickable", "", minTimeOut);
			Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_DateRange_Apply_Btn));
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, objBy));
			reports.writeResult(oBrowser, "Screenshot", "The Case activities data was searched by '"+dateRangeValue+"' date range");
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'searchCaseActivitiesByDateRange()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'searchCaseActivitiesByDateRange()' method. " + e);
			return false;
		}
	}






	/********************************************************
	 * Method Name      : paymentProcess()
	 * Purpose          : user creates/Edits the Payment process
	 * Author           : Gudi
	 * Parameters       : oBrowser, paymentStatus, paymentData
	 * ReturnType       : String
	 ********************************************************/
	public boolean paymentProcess(WebDriver oBrowser, String paymentStatus, Map<String, String> paymentData){
		WebElement objEle = null;
		Actions oAction = null;
		int eleCount = 0;
		int index = 0;
		String arrServiceInfoDatas[];
		String arrServiceInfo[];
		String arrAdditionalInfoDatas[];
		String arrAdditionalInfo[];
		String arrSteps[];
		String dialogHeader = null;
		String confirmationMsg = null;
		String arrServiceInfoValues[];
		String arrSecurityQuestionsData[];
		String arrSecurityQuestions[];
		try{
			if(paymentStatus.equalsIgnoreCase("New")) {
				dialogHeader = "Create Payment Process";
				confirmationMsg = "Payment process has been successfully created.";
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_PaymentProcess_CreatePaymentProcess), "Text", dialogHeader));
			}else{
				dialogHeader = "Edit Payment Process";
				confirmationMsg = "Payment Process has been successfully updated.";
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_PaymentProcess_EditPaymentProcess), "Text", dialogHeader));
			}
			appDep.waitForTheElementState(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_PaymentProcess_dialog + "//button[@name='button']"), "Clickable", "", waitTimeOut);

			if(paymentStatus.equalsIgnoreCase("New")){
				objEle = appInd.createAndGetWebElement(oBrowser, EnrollmentsManagerUIPage.obj_SupplierToggle_Btn);
				if(objEle.getAttribute("action").contains("company_supplier_payment_processes")){
					Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, EnrollmentsManagerUIPage.obj_Supplier_Toggle_Green_Btn));
				}
			}

			reports.writeResult(oBrowser, "Screenshot", "Adding/Editing the account number details");
			if(paymentStatus.equalsIgnoreCase("Edit")){
				eleCount = oBrowser.findElements(By.xpath(EnrollmentsManagerUIPage.obj_RemoveAccountNumbers_Btn)).size();
				for(int i=0; i<eleCount; i++){
					Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_RemoveAccountNumbers_Btn +")["+(i+1)+"]")));
				}
				appDep.threadSleep(1000);
			}
			oAction = new Actions(oBrowser);
			oAction.sendKeys(oBrowser.findElement(EnrollmentsManagerUIPage.obj_AccountNumbers_Edit), paymentData.get("AccountNumber")).sendKeys(Keys.ENTER).build().perform();

			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Payment_MethodChooser_Label + "/..")));
			Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Payment_MethodChooser_Label), paymentData.get("Method")));
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, EnrollmentsManagerUIPage.obj_Payment_Address_Edit, paymentData.get("Address")));


			reports.writeResult(oBrowser, "Screenshot", "Adding/Editing the Service Information details");
			if(paymentStatus.equalsIgnoreCase("Edit")){
				eleCount = oBrowser.findElements(By.xpath(EnrollmentsManagerUIPage.obj_Remove_ServiceInformation_Btn)).size();
				for (int i=0; i<eleCount - 1; i++){
					Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("(" + EnrollmentsManagerUIPage.obj_Remove_ServiceInformation_Btn + ")[" + (i + 1) + "]")));
				}
				appDep.threadSleep(1000);
			}

			index = 0;
			arrServiceInfoDatas = paymentData.get("ServiceInformation").split(",");
			for (int i=0; i<arrServiceInfoDatas.length; i++){
				index++;
				arrServiceInfo = arrServiceInfoDatas[i].split("#");
				Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_ServiceInformation_List)));
				Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("(" + EnrollmentsManagerUIPage.obj_ServiceInformation_List + ")[" + (i + 1) + "]"), arrServiceInfo[0]));
				Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("(" + EnrollmentsManagerUIPage.obj_ServiceInformation_vlaue + ")[" + (i + 1) + "]"), arrServiceInfo[1]));
				appDep.threadSleep(1000);
				if (arrServiceInfoDatas.length > 0 && index < arrServiceInfoDatas.length) {
					Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_ServiceInformation_Addvlaue_btn)));
					appDep.threadSleep(1000);
				}
			}


			//Additional Information
			reports.writeResult(oBrowser, "Screenshot", "Adding/Editing the Additional Information details");
			if(paymentStatus.equalsIgnoreCase("Edit")){
				eleCount = oBrowser.findElements(By.xpath(EnrollmentsManagerUIPage.obj_Remove_AdditionalInformation_Btn)).size();
				for (int i=0; i<eleCount-1; i++){
					Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("(" + EnrollmentsManagerUIPage.obj_Remove_AdditionalInformation_Btn + ")[" + (i + 1) + "]")));
				}
				appDep.threadSleep(1000);
			}
			index = 0;
			arrAdditionalInfoDatas = paymentData.get("AdditionalInformation").split(",");
			for (int i = 0; i < arrAdditionalInfoDatas.length; i++) {
				index++;
				arrAdditionalInfo = arrAdditionalInfoDatas[i].split("#");
				Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_AdditionalInformation_Name_Edit + ")["+(i+1)+"]"), arrAdditionalInfo[0]));
				Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_AdditionalInformation_Value_Edit + ")["+(i+1)+"]"), arrAdditionalInfo[1]));
				if (arrAdditionalInfoDatas.length > 0 && index < arrAdditionalInfoDatas.length) {
					Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_AdditionalInformation_Addvlaue_btn)));
					appDep.threadSleep(1000);
				}
			}


			//Steps
			reports.writeResult(oBrowser, "Screenshot", "Adding/Editing the Process Steps details");
			if (paymentStatus.equalsIgnoreCase("Edit")) {
				eleCount = oBrowser.findElements(By.xpath(EnrollmentsManagerUIPage.obj_Remove_Steps_Btn)).size();
				for (int i=0; i<eleCount - 1; i++){
					Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_Remove_Steps_Btn +")["+(i+1)+"]")));
				}
				appDep.threadSleep(1000);
			}

			index = 0;
			arrSteps = paymentData.get("Steps").split("#");
			for (int i=0; i<arrSteps.length; i++){
				index++;
				Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_PaymentService_Steps_Edit + ")["+(i+1)+"]"), arrSteps[i]));
				if (arrSteps.length > 0 && index < arrSteps.length) {
					Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Payment_Steps_Add_Btn));
					appDep.threadSleep(1000);
				}
			}


			//Security Questions
			reports.writeResult(oBrowser, "Screenshot", "Adding/Editing the Security Questions details");
			if (paymentStatus.equalsIgnoreCase("Edit")) {
				eleCount = oBrowser.findElements(By.xpath(EnrollmentsManagerUIPage.obj_Remove_SecurityQuestions_Btn)).size();
				for (int i=0; i<eleCount - 1; i++){
					Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_Remove_SecurityQuestions_Btn +")["+(i+1)+"]")));
				}
				appDep.threadSleep(1000);
			}

			index = 0;
			arrSecurityQuestionsData = paymentData.get("SecurityQuestions").split(",");
			for (int i = 0; i < arrSecurityQuestionsData.length; i++) {
				index++;
				arrSecurityQuestions = arrSecurityQuestionsData[i].split("#");
				Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_SecurityQuestions_Edit + ")["+(i+1)+"]"), arrSecurityQuestions[0]));
				Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+EnrollmentsManagerUIPage.obj_SecurityAnswers_Edit + ")["+(i+1)+"]"), arrSecurityQuestions[1]));
				if (arrSecurityQuestionsData.length > 0 && index < arrSecurityQuestionsData.length) {
					Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_AddQuestions_Btn));
					appDep.threadSleep(1000);
				}
			}

			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, EnrollmentsManagerUIPage.obj_Payment_Notes_Textarea, paymentData.get("Notes")));
			reports.writeResult(oBrowser, "Screenshot", "The details are entered for '" + paymentStatus + "' Payment Process");
			appDep.waitForTheElementState(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Payment_create_Btn), "Clickable", "", minTimeOut);
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Payment_create_Btn)));
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_Suppliers_search_Loading, "Invisibility", "", waitTimeOut);
			appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_ReassignConfirmationMessage_Label, "Visibility", "", minTimeOut);
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, EnrollmentsManagerUIPage.obj_ReassignConfirmationMessage_Label, "Text").contains(confirmationMsg));
			return true;
		}catch(Exception e){
			reports.writeResult(oBrowser, "Exception", "Exception in 'paymentProcess()' method. " + e);
			return false;
		}catch(AssertionError e){
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'paymentProcess()' method. " + e);
			return false;
		}finally{objEle = null; oAction = null; arrServiceInfoDatas = null; arrServiceInfo = null; arrAdditionalInfoDatas = null; arrAdditionalInfo = null; arrSteps = null; dialogHeader = null; confirmationMsg = null; arrServiceInfoValues = null; arrSecurityQuestionsData = null; arrSecurityQuestions = null;}
	}





	/********************************************************
	 * Method Name      : createGeneralInformation()
	 * Purpose          : user enters Contact information details for test account setup
	 * Author           : Gudi
	 * Parameters       : oBrowser, dataTable
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean createGeneralInformation(WebDriver oBrowser, DataTable dataTable){
		List<Map<String, String>> generalInfoData = null;
		String companyName = null;
		Map<String, String> generalInfo = null;
		String date = null;
		String dateFormatted = null;
		try{
			date = appInd.getDateTimeByTimezone("yyyy-MM-dd", "CST");
			generalInfo = new HashMap<String, String>();
			generalInfoData = dataTable.asMaps(String.class, String.class);
			generalInfo.put("CompanyName", companyName);
			generalInfo.put("Industry", generalInfoData.get(0).get("Industry"));
			generalInfo.put("NumberOfLocations", generalInfoData.get(0).get("NumberOfLocations"));
			generalInfo.put("BriefHistory", generalInfoData.get(0).get("BriefHistory"));
			generalInfo.put("CurrentStage", generalInfoData.get(0).get("CurrentStage"));
			generalInfo.put("Date", date);

			companyName = appDep.selectTheCompanyBasedOnType(oBrowser, "Prospect");
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, companyName));
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Industry_Edit, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Industry_Edit, "Value", generalInfo.get("Industry"))));
			generalInfo.remove("Industry");
			generalInfo.put("Industry", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Industry_Edit, "Value"));

			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_NumberOfLocations_Edit, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_NumberOfLocations_Edit, "Value", generalInfo.get("NumberOfLocations"))));
			generalInfo.remove("NumberOfLocations");
			generalInfo.put("NumberOfLocations", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_NumberOfLocations_Edit, "Value"));

			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_BriefHistory_textarea, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_BriefHistory_textarea, "Value", generalInfo.get("BriefHistory"))));
			generalInfo.remove("BriefHistory");
			generalInfo.put("BriefHistory", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_BriefHistory_textarea, "Value"));

			Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CurrentStage_List, appInd.chooseAnotherValueOtherThanTheCurrent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CurrentStage_List, "List", generalInfo.get("CurrentStage"))));
			generalInfo.remove("CurrentStage");
			generalInfo.put("CurrentStage", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CurrentStage_List, "Dropdown"));

			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_Date_Edit, generalInfo.get("Date")));
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_DatePicker_Dialog +"//td/a[contains(@class, 'ui-state-active')]")));
			Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SaveAnswers_Btn));
			appDep.threadSleep(5000);
			oBrowser.navigate().refresh();
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", waitTimeOut);

			//Verify that General info values dave successful
			Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Value", companyName));
			Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Industry_Edit, "Value", generalInfo.get("Industry")));
			Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_NumberOfLocations_Edit, "Value", generalInfo.get("NumberOfLocations")));
			Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage. obj_BriefHistory_textarea, "Value", generalInfo.get("BriefHistory")));
			Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage. obj_CurrentStage_List, "Dropdown", generalInfo.get("CurrentStage")));

			//Click on Show History and verify History grid displayed successful
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ShowHistory_Btn)));
			appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ShoHistory_Grid + "//table//th[1]"), "Text", "Stage", minTimeOut);
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ShoHistory_Grid + "//table//th[1]"), "Text", "Stage"));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ShoHistory_Grid + "//table//th[2]"), "Text", "Date"));
			dateFormatted = appInd.formatDateFromOnetoAnother(date, "yyyy-MM-dd", "MM/dd/yyyy");
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ShoHistory_Grid + "//table//td[text()='"+generalInfo.get("CurrentStage")+"']/following-sibling::td[text()='"+dateFormatted+"']"), "Text", dateFormatted));

			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ShowHistory_Btn)));
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'createGeneralInformation()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'createGeneralInformation()' method. " + e);
			return false;
		}finally{
			generalInfoData = null; companyName = null; generalInfo = null; date = null;
		}
	}




	/********************************************************
	 * Method Name      : createContactInformation()
	 * Purpose          : user enters contact information details for test account setup
	 * Author           : Gudi
	 * Parameters       : oBrowser, dataTable
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean createContactInformation(WebDriver oBrowser, DataTable dataTable){
		List<Map<String, String>> contactInfoData = null;
		Map<String, String> contactInfo = null;
		String timeStamp = null;
		int index = 0;
		WebElement objEle = null;
		try{
			timeStamp = appInd.getDateTime("Shhmmss");
			contactInfo = new HashMap<String, String>();
			contactInfoData = dataTable.asMaps(String.class, String.class);
			contactInfo.put("ContactName", contactInfoData.get(0).get("ContactName")+timeStamp);
			contactInfo.put("Title", contactInfoData.get(0).get("Title"));
			contactInfo.put("ContactSigner", contactInfoData.get(0).get("ContactSigner"));
			contactInfo.put("DecisionMaker", contactInfoData.get(0).get("DecisionMaker"));

			index = oBrowser.findElements(PayCRM_Modules_GeneralUIPage.obj_ContactInformation_Details).size();
			Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AddContact_Btn));
			Assert.assertTrue(appInd.setObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ContactInformation_Grid +"//input[@id='contact_information_"+index+"_name']"), contactInfo.get("ContactName")));
			Assert.assertTrue(appInd.setObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ContactInformation_Grid +"//input[@id='contact_information_"+index+"_title']"), contactInfo.get("Title")));
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ContactInformation_Grid +"//input[@id='contract_signer_"+index+"']/following-sibling::label")));
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ContactInformation_Grid +"//input[@id='decision_maker_"+index+"']/following-sibling::label")));

			appInd.scrollToThePage(oBrowser, "Top");
			Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SaveAnswers_Btn));
			appDep.threadSleep(5000);
			oBrowser.navigate().refresh();
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", waitTimeOut);

			//validate contact information saved successful
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ContactInformation_Grid +"//input[@id='contact_information_"+index+"_name']"), "Value", contactInfo.get("ContactName")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ContactInformation_Grid +"//input[@id='contact_information_"+index+"_title']"), "Value", contactInfo.get("Title")));
			appInd.scrollToElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ContactInformation_Grid +"//input[@id='contact_information_"+index+"_title']"));
			objEle = oBrowser.findElement(By.xpath(PayCRM_Modules_GeneralUIPage.obj_ContactInformation_Grid +"//input[@id='contract_signer_"+index+"']"));
			Assert.assertTrue(objEle.isSelected());
			objEle = oBrowser.findElement(By.xpath(PayCRM_Modules_GeneralUIPage.obj_ContactInformation_Grid +"//input[@id='decision_maker_"+index+"']"));
			Assert.assertTrue(objEle.isSelected());
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'createContactInformation()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'createContactInformation()' method. " + e);
			return false;
		}finally{
			contactInfoData = null; contactInfo = null; timeStamp = null;
		}
	}





	/********************************************************
	 * Method Name      : createCurrentPaymentLandscape()
	 * Purpose          : user enters Current Payment Landscape details for test account setup
	 * Author           : Gudi
	 * Parameters       : oBrowser, dataTable
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean createCurrentPaymentLandscape(WebDriver oBrowser, DataTable dataTable){
		List<Map<String, String>> paymentLandscape = null;
		Map<String, String> payment = null;
		String timeStamp = null;
		int index = 0;
		String financialInstitution[];
		try{
			timeStamp = appInd.getDateTime("Shhmmss");
			payment = new HashMap<String, String>();
			paymentLandscape = dataTable.asMaps(String.class, String.class);
			payment.put("PayablesVolume", timeStamp);
			payment.put("SpendFrequency", paymentLandscape.get(0).get("SpendFrequency"));
			payment.put("VirtualCardProgram", paymentLandscape.get(0).get("VirtualCardProgram"));
			payment.put("FinancialInstitution", paymentLandscape.get(0).get("FinancialInstitution"));
			payment.put("CurrentRebate", paymentLandscape.get(0).get("CurrentRebate"));
			payment.put("RebateFrequency", paymentLandscape.get(0).get("RebateFrequency"));
			payment.put("CheckPercentage", paymentLandscape.get(0).get("CheckPercentage"));
			payment.put("ACHPercentage", paymentLandscape.get(0).get("ACHPercentage"));
			payment.put("EnhancedACHPercentage", paymentLandscape.get(0).get("EnhancedACHPercentage"));
			payment.put("WirePercentage", paymentLandscape.get(0).get("WirePercentage"));
			payment.put("PaymentRunFrequency", paymentLandscape.get(0).get("PaymentRunFrequency"));
			payment.put("PaymentRunDays", paymentLandscape.get(0).get("PaymentRunDays"));
			payment.put("FullTimeEmployeeAccountsPayable", paymentLandscape.get(0).get("FullTimeEmployeeAccountsPayable"));
			payment.put("TradeDiscounts", paymentLandscape.get(0).get("TradeDiscounts"));
			payment.put("ParticularTradeDiscount", paymentLandscape.get(0).get("ParticularTradeDiscount"));

			appInd.scrollToElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AddContact_Btn);
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PayablesVolume_Edit, payment.get("PayablesVolume")));
			Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SpendFrequency_List, payment.get("SpendFrequency")));
			Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CurrentVirtualCardProgram_List, payment.get("VirtualCardProgram")));

			//remove the Financial Institution previous values
			int count = oBrowser.findElements(By.xpath(PayCRM_Modules_GeneralUIPage.obj_FinancialInstitution_Values +"//li/a")).size();
			for(int i=0; i<count; i++){
				Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_FinancialInstitution_Values +"//li/a")));
			}

			Assert.assertTrue(appInd.setMultipleValues(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_FinancialInstitution_Values +"//input[contains(@class, 'chosen-search-input')]"), PayCRM_Modules_GeneralUIPage.obj_FinancialInstitution_Values+"//ul[@class='chosen-results']/li/em", payment.get("FinancialInstitution")));
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CurrentRebateAmount_Edit, payment.get("CurrentRebate")));
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_RebateFrequency_Edit, payment.get("RebateFrequency")));
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CheckPercentage_Edit, payment.get("CheckPercentage")));
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PercentageACH_Edit, payment.get("ACHPercentage")));
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PercentageEnhancedACH_Edit, payment.get("EnhancedACHPercentage")));
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PercentageWire_Edit, payment.get("WirePercentage")));
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentRunFrequency_Edit, payment.get("PaymentRunFrequency")));
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentRunDays_Edit, payment.get("PaymentRunDays")));
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_FullTimeEmployeesAccountPayable_Edit, payment.get("FullTimeEmployeeAccountsPayable")));
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_TradeDiscounts_Textarea, payment.get("TradeDiscounts")));
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ParticularTradeDiscount_Textarea, payment.get("ParticularTradeDiscount")));

			Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SaveAnswers_Btn));
			appDep.threadSleep(5000);
			oBrowser.navigate().refresh();
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", waitTimeOut);

			//validate Current Payment Landscape information saved successful
			appInd.scrollToElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_AddContact_Btn);
			Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PayablesVolume_Edit, "Value", payment.get("PayablesVolume")));
			Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SpendFrequency_List, "Dropdown", payment.get("SpendFrequency")));
			Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CurrentVirtualCardProgram_List, "Dropdown", payment.get("VirtualCardProgram")));
			financialInstitution = payment.get("FinancialInstitution").split("#");
			for(int i=0; i<financialInstitution.length; i++){
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_FinancialInstitution_Values +"//li[@class='search-choice']/span)["+(i+1)+"]"), "Text", financialInstitution[i]));
			}
			Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CurrentRebateAmount_Edit, "Value", payment.get("CurrentRebate")));
			Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_RebateFrequency_Edit, "Value", payment.get("RebateFrequency")));
			Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CheckPercentage_Edit, "Value", payment.get("CheckPercentage")));
			Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PercentageACH_Edit, "Value", payment.get("ACHPercentage")));
			Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PercentageEnhancedACH_Edit, "Value", payment.get("EnhancedACHPercentage")));
			Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PercentageWire_Edit, "Value", payment.get("WirePercentage")));
			Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentRunFrequency_Edit, "Value", payment.get("PaymentRunFrequency")));
			Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentRunDays_Edit, "Value", payment.get("PaymentRunDays")));
			Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_FullTimeEmployeesAccountPayable_Edit, "Value", payment.get("FullTimeEmployeeAccountsPayable")));
			Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_TradeDiscounts_Textarea, "Value", payment.get("TradeDiscounts")));
			Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ParticularTradeDiscount_Textarea, "Value", payment.get("ParticularTradeDiscount")));
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'createCurrentPaymentLandscape()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'createCurrentPaymentLandscape()' method. " + e);
			return false;
		}finally{
			paymentLandscape = null; payment = null; timeStamp = null; financialInstitution = null;
		}
	}





	/********************************************************
	 * Method Name      : createAdditionalDetails()
	 * Purpose          : user enters Additional Details for test account setup
	 * Author           : Gudi
	 * Parameters       : oBrowser, dataTable
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean createAdditionalDetails(WebDriver oBrowser, DataTable dataTable){
		List<Map<String, String>> additionalDetails = null;
		Map<String, String> additional = null;
		String timeStamp = null;
		int index = 0;
		String bankingGroups[];
		try{
			timeStamp = appInd.getDateTime("Shhmmss");
			additional = new HashMap<String, String>();
			additionalDetails = dataTable.asMaps(String.class, String.class);
			additional.put("BankingGroupMember", additionalDetails.get(0).get("BankingGroupMember"));
			additional.put("DisbursementAccounts", additionalDetails.get(0).get("DisbursementAccounts"));
			additional.put("ERP", additionalDetails.get(0).get("ERP"));
			additional.put("BusinessLifecycle", additionalDetails.get(0).get("BusinessLifecycle"));
			additional.put("TreasurySolution", additionalDetails.get(0).get("TreasurySolution"));
			additional.put("PurchasingCards", additionalDetails.get(0).get("PurchasingCards"));
			additional.put("CulturalDisposition", additionalDetails.get(0).get("CulturalDisposition"));
			additional.put("Notes", additionalDetails.get(0).get("Notes"));
			additional.put("NextDetails", additionalDetails.get(0).get("NextDetails"));


			//remove the Banking Group Member previous values
			int count = oBrowser.findElements(By.xpath(PayCRM_Modules_GeneralUIPage.obj_BankingGroupMembers_Items +"//li/a")).size();
			for(int i=0; i<count; i++){
				Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_BankingGroupMembers_Items +"//li/a")));
			}
			appInd.scrollToElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_additionalDetails));
			appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_BankingGroupMembers_Items+"//input[contains(@class, 'chosen-search-input')]"), "");
			Assert.assertTrue(appInd.setMultipleValues(oBrowser,By.xpath(PayCRM_Modules_GeneralUIPage.obj_BankingGroupMembers_Items+"//input[contains(@class, 'chosen-search-input')]"), PayCRM_Modules_GeneralUIPage.obj_BankingGroupMembers_Items +"//ul[@class='chosen-results']/li/em", additional.get("BankingGroupMember")));

			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_DisbursementAccounts_Edit, additional.get("DisbursementAccounts")));
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ERP_Edit, additional.get("ERP")));
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_BusinessLifecycle_Edit, additional.get("BusinessLifecycle")));
			Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_TreasurySolution_List, additional.get("TreasurySolution")));
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PurchasingCards_Edit, additional.get("PurchasingCards")));
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CulturalDisposition_Edit, additional.get("CulturalDisposition")));
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Notes_Textarea, additional.get("Notes")));
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_NextMeeting_Textarea, additional.get("NextDetails")));

			Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SaveAnswers_Btn));
			oBrowser.navigate().refresh();
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_GeneralInfo_CompanyName_Edit, "Clickable", "", waitTimeOut);

			//validate Additional Details information saved successful
			bankingGroups = additional.get("BankingGroupMember").split("#");
			for(int i=0; i<bankingGroups.length; i++){
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_BankingGroupMembers_Items +"//li[@class='search-choice']/span)["+(i+1)+"]"), "Text", bankingGroups[i]));
			}
			Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_DisbursementAccounts_Edit, "Value", additional.get("DisbursementAccounts")));
			Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ERP_Edit, "Value", additional.get("ERP")));
			Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_BusinessLifecycle_Edit, "Value", additional.get("BusinessLifecycle")));
			Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_TreasurySolution_List, "Dropdown", additional.get("TreasurySolution")));
			Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PurchasingCards_Edit, "Value", additional.get("PurchasingCards")));
			Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_CulturalDisposition_Edit, "Value", additional.get("CulturalDisposition")));
			Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Notes_Textarea, "Value", additional.get("Notes")));
			Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_NextMeeting_Textarea, "Text", additional.get("NextDetails")));
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'createAdditionalDetails()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'createAdditionalDetails()' method. " + e);
			return false;
		}finally{
			additionalDetails = null; additional = null; timeStamp = null; bankingGroups = null;
		}
	}




	/********************************************************
	 * Method Name      : validateGridColumns()
	 * Purpose          : user validates the column names of the given table grid
	 * Author           : Gudi
	 * Parameters       : oBrowser, objGrid, columnNames
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean validateGridColumns(WebDriver oBrowser, String objGrid, String columnNames, String pageName){
		String arrColumnNames[];
		String cellData = null;
		try{
			arrColumnNames = columnNames.split("#");
			for(int i=0; i<arrColumnNames.length; i++){
				cellData = appInd.getTextOnElement(oBrowser, By.xpath("("+objGrid +")["+(i+1)+"]"), "Text");
				Assert.assertTrue(cellData.trim().equalsIgnoreCase(arrColumnNames[i]));
			}
			reports.writeResult(oBrowser, "Pass", "The Column names validation for the page '"+pageName+"' was successful");
			reports.writeResult(oBrowser, "Screenshot", "The '"+pageName+"' page table grid Column names");
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'validateGridColumns()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateGridColumns()' method. " + e);
			return false;
		}finally {
			arrColumnNames = null; cellData = null;
		}
	}



	/********************************************************
	 * Method Name      : validatePaymentDetailsGridData()
	 * Purpose          : user validates the Payment Details table grid data
	 * Author           : Gudi
	 * Parameters       : oBrowser, gridContent, columnNames
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean validatePaymentDetailsGridData(WebDriver oBrowser, Map<String, String> gridContent, String columnNames, String pageName){
		String arrColumnNames[];
		try{
			arrColumnNames = columnNames.split("#");
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentDetails_Grid +"//th[text()='Payment ID']/following-sibling::td[1]"), "Text", gridContent.get(arrColumnNames[0])));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentDetails_Grid +"//th[text()='Supplier name']/following-sibling::td[1]"), "Text", gridContent.get(arrColumnNames[1])));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentDetails_Grid +"//th[text()='Supplier pay net ID']/following-sibling::td[1]"), "Text", gridContent.get(arrColumnNames[2])));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentDetails_Grid +"//th[text()='Remit to ID']/following-sibling::td[1]"), "Text", gridContent.get(arrColumnNames[3])));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentDetails_Grid +"//th[text()='Client company name']/following-sibling::td[1]"), "Text", gridContent.get(arrColumnNames[4])));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentDetails_Grid +"//th[text()='Client company ID']/following-sibling::td[1]"), "Text", gridContent.get(arrColumnNames[5])));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentDetails_Grid +"//th[text()='Payment amount']/following-sibling::td[1]"), "Text", gridContent.get(arrColumnNames[6])));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_PaymentDetails_Grid +"//th[text()='Payment type']/following-sibling::td[1]"), "Text", gridContent.get(arrColumnNames[7])));
			reports.writeResult(oBrowser, "Screenshot", "Validating the grid data from the '"+pageName+"' page");
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'validatePaymentDetailsGridData()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'validatePaymentDetailsGridData()' method. " + e);
			return false;
		}finally {arrColumnNames = null;}
	}





	/********************************************************
	 * Method Name      : validatePaymentVoidCaseDetailsGridData()
	 * Purpose          : user validates the Payment Void cases Details table grid data
	 * Author           : Gudi
	 * Parameters       : oBrowser, gridContent, columnNames
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean validatePaymentVoidCaseDetailsGridData(WebDriver oBrowser, Map<String, String> gridContent, String columnNames, String pageName){
		String arrColumnNames[];
		try{
			arrColumnNames = columnNames.split("#");
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Company_Show_Grid +"//th[text()='Payment ID']/following-sibling::td[1]"), "Text", gridContent.get(arrColumnNames[0])));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Company_Show_Grid +"//th[text()='Supplier name']/following-sibling::td[1]"), "Text", gridContent.get(arrColumnNames[1])));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Company_Show_Grid +"//th[text()='Supplier pay net ID']/following-sibling::td[1]"), "Text", gridContent.get(arrColumnNames[2])));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Company_Show_Grid +"//th[text()='Remit to ID']/following-sibling::td[1]"), "Text", gridContent.get(arrColumnNames[3])));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Company_Show_Grid +"//th[text()='Client company name']/following-sibling::td[1]"), "Text", gridContent.get(arrColumnNames[4])));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Company_Show_Grid +"//th[text()='Client company ID']/following-sibling::td[1]"), "Text", gridContent.get(arrColumnNames[5])));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Company_Show_Grid +"//th[text()='Payment amount']/following-sibling::td[1]"), "Text", gridContent.get(arrColumnNames[6])));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Company_Show_Grid +"//th[text()='Payment type']/following-sibling::td[1]"), "Text", gridContent.get(arrColumnNames[7])));

			if(gridContent.containsKey("RequestCreatedBy"))
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Enroll_Cases_Section +"//dt[text()='Request Created By']/following-sibling::dd[1]"), "Text", gridContent.get("RequestCreatedBy")));
			if(gridContent.containsKey("AmounttoVoid"))
				Assert.assertTrue(gridContent.get("AmounttoVoid").contains(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Enroll_Cases_Section +"//dt[text()='Amount to Void']/following-sibling::dd[1]"), "Text")));
			if(gridContent.containsKey("ActiontoPerformAfterVoid"))
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Enroll_Cases_Section +"//dt[text()='Action to Perform After Void']/following-sibling::dd[1]"), "Text", gridContent.get("ActiontoPerformAfterVoid")));

			reports.writeResult(oBrowser, "Screenshot", "Validating the data from '"+pageName+"' page");
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'validatePaymentVoidCaseDetailsGridData()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'validatePaymentVoidCaseDetailsGridData()' method. " + e);
			return false;
		}finally {arrColumnNames = null;}
	}





	/********************************************************
	 * Method Name      : readManagedPaymentsGridDataAndReturnAsMap()
	 * Purpose          : user reads the table data from the 'Managed Payments' grid and returns the Map object
	 * Author           : Gudi
	 * Parameters       : oBrowser, objBy, KeyNames
	 * ReturnType       : Map<String, String>
	 ********************************************************/
	public Map<String, String> readManagedPaymentsGridDataAndReturnAsMap(WebDriver oBrowser, String objBy, String KeyNames, String pageName) {
		Map<String, String> objData = null;
		String arrKeys[];
		try {
			objData = new HashMap<String, String>();
			arrKeys = KeyNames.split("#");
			for (int i = 0; i < arrKeys.length; i++) {
				objData.put(arrKeys[i], appInd.getTextOnElement(oBrowser, By.xpath("("+objBy + ")[" + (i+1) + "]"), "Text"));
			}
			reports.writeResult(oBrowser, "Screenshot", "Captured the First row data from the '"+pageName+"' page and returned as a Map object");
			return objData;
		}catch (Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'readManagedPaymentsGridDataAndReturnAsMap()' method. " + e);
			objData.put("TestPassed", "False");
			return objData;
		}catch (AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'readManagedPaymentsGridDataAndReturnAsMap()' method. " + e);
			objData.put("TestPassed", "False");
			return objData;
		} finally {objData = null; arrKeys = null;}
	}




	/********************************************************
	 * Method Name      : validatePaymentSwitchReasonValues()
	 * Purpose          : to validate the data options displayed under 'Switch Reason' dropdown is appropriate for the selected switch options
	 * Author           : Gudi
	 * Parameters       : oBrowser, switchVariant
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean validatePaymentSwitchReasonValues(WebDriver oBrowser, String switchVariant){
		List<WebElement> objListOptions = null;
		String arrExpectedData[];
		Select oSelect = null;
		try{
			oSelect = new Select(appInd.createAndGetWebElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SwitchReason_List));
			objListOptions = oSelect.getOptions();
			switch(switchVariant.toLowerCase()){
				case "ach basic->virtual card": case "ach plus->virtual card":
					arrExpectedData = "Better payment terms#Prefers Card to ACH#Enrolled for incorrect offer".split("#");
					break;
				case "ach basic->ach basic":
					arrExpectedData = "Updated payment terms".split("#");
					break;
				case "ach basic->ach plus":
					arrExpectedData = "Accelerated payment terms".split("#");
					break;
				case "ach plus-->virtual card":
					arrExpectedData = "Better payment terms#Prefers Card to ACH#Enrolled for incorrect offer".split("#");
					break;
				case "virtual card->ach plus": case "virtual card->ach basic":
					arrExpectedData = "No longer accepts credit card due to fees#No longer accepts credit card due to no terminal#Charges a fee to process credit card payments#Account is ineligible for card payments#Enrolled for the incorrect offer".split("#");
					break;
				case "ach plus->ach basic":
					arrExpectedData = "No longer wishes to provide a discount#Accelerated payment terms were not honored#Enrolled for the incorrect offer".split("#");
					break;
				case "virtual card->virtual card":
					arrExpectedData = "Better payment terms#Accelerated payment terms were not honored#Client Request".split("#");
					break;
				case "ach plus->ach plus":
					arrExpectedData = "Needed billing to be monthly#Needed billing to be per payment#Accelerated payment terms were not honored#Client Request".split("#");
					break;
				default:
					reports.writeResult(oBrowser, "Info", "Validation is not applied for '"+switchVariant+"' payment options");
					return false;
			}

			for(int i=0; i<arrExpectedData.length; i++){
				Assert.assertTrue(appInd.compareValues(oBrowser, objListOptions.get(i+1).getText(), arrExpectedData[i]));
			}
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'validatePaymentSwitchReasonValues()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'validatePaymentSwitchReasonValues()' method. " + e);
			return false;
		}finally {
			objListOptions = null; arrExpectedData = null; oSelect = null;
		}
	}




	/********************************************************
	 * Method Name      : switchToPaymentTypes()
	 * Purpose          : user performs payment switch
	 * Author           : Gudi
	 * Parameters       : oBrowser, switchVariant
	 * ReturnType       : Map<String, String>
	 ********************************************************/
	public Map<String, String> switchToPaymentTypes(WebDriver oBrowser, String switchVariant, DataTable dataTable){
		List<Map<String, String>> switchData = null;
		boolean blnRes= false;
		String arrSwitchVariant[];
		String paymentTypeTo = null;
		String paymentTypeFrom = null;
		Map<String, String> switchDetails = null;
		boolean blnValidate = false;
		String supplierName = null;
		String supplierTIN = null;
		String supplierPhone = null;
		String URL = null;
		Alert oAlert = null;
		int offerNumberSwitchTo = 0;
		try{
			switchData = dataTable.asMaps(String.class, String.class);
			switchDetails = new HashMap<String, String>();
			arrSwitchVariant = switchVariant.split("->");
			if(!appInd.verifyOptionalElementPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ChangePaymentType_Btn))
				oBrowser.navigate().to(appInd.getPropertyValueByKeyName("paymentSwitch1"));

			if(switchVariant.equalsIgnoreCase("ACH Plus->ACH Plus") || switchVariant.equalsIgnoreCase("None->ACH Plus")){
				oBrowser.navigate().to(appInd.getPropertyValueByKeyName("paymentSwitch2"));
			}
			reports.writeResult(oBrowser, "Screenshot", "The Enrollment Case page has opened successful");
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_WaveAndOffers_Header, "Text", "Wave & Offers", waitTimeOut);
			URL = oBrowser.getCurrentUrl();
			switchDetails.put("caseNumber", URL.substring(URL.lastIndexOf("/")+1).trim());
			if(switchVariant.toLowerCase().contains("none")){
				blnRes = appInd.verifyOptionalElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_WaveOffer_Tabs + "//a[@class='nav-link offer_accepted active'][contains(text(), '"+arrSwitchVariant[1]+"')]"));
				if(blnRes){
					reports.writeResult(oBrowser, "Pass", "The Payment switch type '"+arrSwitchVariant[1]+"' was already the current payment type");
					switchDetails.put("paymentSwitch", "false");
					switchDetails.put("TestPassed", "true");
					return switchDetails;
				}else{
					reports.writeResult(oBrowser, "Info", "Switching to the payment type : " + arrSwitchVariant[1]);
					//paymentTypeFrom = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_WaveOffer_Tabs + "//a[@class='nav-link offer_accepted active']"), "Text");
					paymentTypeFrom = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_WaveOffer_Tabs + "//a[contains(@class, ' active') or contains(@class, 'offer_accepted')]"), "Text");
				}
			}else{
				blnValidate = true;
				paymentTypeFrom = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_WaveOffer_Tabs + "//a[@class='nav-link offer_accepted active'][contains(text(), '"+arrSwitchVariant[0]+"')]"), "Text");
			}

			//verify both From and To Payment switch options should be available
			if(blnValidate)
				Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_WaveOffer_Tabs + "//a[@class='nav-link offer_accepted active'][contains(text(), '"+arrSwitchVariant[0]+"')]")), "The payment type '"+paymentTypeFrom+"' doesnot exist. Exiting the 'switchToPaymentTypes()' method");
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_WaveOffer_Tabs + "//a[@class='nav-link red' or @class='nav-link '][contains(text(), '"+arrSwitchVariant[1]+"')]")), "The payment type '"+arrSwitchVariant[1]+"' doesnot exist. Exiting the 'switchToPaymentTypes()' method");
			paymentTypeTo = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_WaveOffer_Tabs + "//a[@class='nav-link red' or @class='nav-link '][contains(text(), '"+arrSwitchVariant[1]+"')]"), "Text");

			switchDetails.put("switchFromNameAndNumber", paymentTypeFrom);
			switchDetails.put("switchToPaymentNameAndNumber", paymentTypeTo);
			switchDetails.put("switchToPaymentName", arrSwitchVariant[1]);
			offerNumberSwitchTo = Integer.parseInt((switchDetails.get("switchToPaymentNameAndNumber").split("-"))[0].trim());
			//Read supplier details for further validations
			supplierName = oBrowser.findElement(By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierDetails_Card + "//dt/span[contains(text(), 'Name')]/parent::dt/following-sibling::dd[1]/span[@class='truncate-title']/a")).getAttribute("title");
			supplierTIN = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierDetails_Card + "//dt/span[contains(text(), 'TIN')]/parent::dt/following-sibling::dd[1]"), "Text");
			supplierPhone = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierDetails_Card + "//dt/span[contains(text(), 'Phone')]/parent::dt/following-sibling::dd[1]"), "Text");

			appInd.scrollToThePage(oBrowser, "Top");
			Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ChangePaymentType_Btn));
			oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_List, "Clickable", "", waitTimeOut);

			reports.writeResult(oBrowser, "Screenshot", "The Enrollment Case->Enrollment Form page has opened successful");

			//Validate that the same supplier page has opened
			Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Enroll_SupplierName_Edit, "Value", supplierName));
			Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Enroll_TIN_Edit, "Value", supplierTIN));
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Enroll_SupplierPhoneNumber_Edit, "Value").replace(" ", "").replace("-", "").replace("(", "").replace(")", "").equals(supplierPhone));

			//Validate that the default payment type is shown in the 'Switch Offer' dropdown
			Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_List, "Dropdown", "Offer "+ (paymentTypeFrom.split("\\("))[0].trim()));
			Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_List, "Offer "+(paymentTypeTo.split("\\("))[0].trim()));
			appDep.threadSleep(2000);
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SwitchReason_List, "Clickable", "", minTimeOut);

			switch(arrSwitchVariant[1].toLowerCase()){
				case "virtual card":
					if(switchData.get(0).get("ToggleforCMVP").equals("Yes"))
						Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_VirtualCreditCardDetails_Label +"//input[@id='inrollment_case_offers_"+offerNumberSwitchTo+"__is_vpay']/parent::label[1]/div/span[@class='toggle-no']")));
					else
						Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_VirtualCreditCardDetails_Label + "//input[@id='inrollment_case_offers_"+offerNumberSwitchTo+"__virtual_email']"), switchData.get(0).get("VCardMail")));

					if(switchData.get(0).get("MerchantAcquirer").equals("Yes"))
						Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_VirtualCreditCardDetails_Label +"//input[@id='inrollment_case_offers_"+offerNumberSwitchTo+"__merchant_acquirer']/parent::label[1]/div/span[@class='toggle-no']")));

					Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_VirtualCreditCardDetails_Label +"//input[@id='inrollment_case_offers_"+offerNumberSwitchTo+"__virtual_card_phone']"), switchData.get(0).get("VCardPhone")));
					Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_VirtualCreditCardDetails_Label +"//input[@id='inrollment_case_offers_"+offerNumberSwitchTo+"__remittance_advice_email_recipient']"), switchData.get(0).get("RemittanceAdviceEmailRecipient")));
					Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_VirtualCreditCardDetails_Label +"//select[@id='inrollment_case_offers_"+offerNumberSwitchTo+"__bank_country']"), switchData.get(0).get("Country")));
					Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_VirtualCreditCardDetails_Label +"//select[@id='inrollment_case_offers_"+offerNumberSwitchTo+"__bank_currency_code']"), switchData.get(0).get("Currency")));
					break;
				default:
					Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid +"//select[@id='inrollment_case_offers_"+offerNumberSwitchTo+"__maintenance_fee_method']"), switchData.get(0).get("MaintenanceFeeMethod")));
					Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid +"//input[@id='inrollment_case_offers_"+offerNumberSwitchTo+"__payment_notification_email_recipient']"), switchData.get(0).get("PaymentNotificationEmailRecipient")));
					Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid + "//input[@id='inrollment_case_offers_"+offerNumberSwitchTo+"__remittance_advice_email_recipient']"), switchData.get(0).get("RemittanceAdviceEmailRecipient")));
					Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid + "//input[@id='inrollment_case_offers_"+offerNumberSwitchTo+"__network_maintenance_fee_advice_email_recipient']"), switchData.get(0).get("NetworkMaintenanceFeeAdviceEmailRecipient")));
					Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid + "//select[@id='inrollment_case_offers_"+offerNumberSwitchTo+"__bank_account_type']"), switchData.get(0).get("AccountType")));
					Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid + "//input[@id='inrollment_case_offers_"+offerNumberSwitchTo+"__bank_name']"), switchData.get(0).get("BankName")));
					Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid +"//input[@id='inrollment_case_offers_"+offerNumberSwitchTo+"__bank_account_name']"), switchData.get(0).get("AccountName")));
					Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid + "//input[@id='inrollment_case_offers_"+offerNumberSwitchTo+"__bank_aba_routing_number']"), switchData.get(0).get("ABARoutingNumber")));
					Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid +"//input[@id='inrollment_case_offers_"+offerNumberSwitchTo+"__bank_account_number']"), switchData.get(0).get("AccountNumber")));
					Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid + "//select[@id='inrollment_case_offers_"+offerNumberSwitchTo+"__bank_country']"), switchData.get(0).get("Country")));
					Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid + "//select[@id='inrollment_case_offers_"+offerNumberSwitchTo+"__bank_currency_code']"), switchData.get(0).get("Currency")));
			}

			reports.writeResult(oBrowser, "Screenshot", "Filling the details for the SWitch Offers");
			if(!switchVariant.toLowerCase().contains("none")){
				Assert.assertTrue(appDep.validatePaymentSwitchReasonValues(oBrowser, switchVariant));
				switchDetails.put("paymentSwitch", "true");
			}else{
				switchDetails.put("paymentSwitch", "false");
			}

			appInd.selectObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SwitchReason_List, 1);
			switchDetails.put("switchReason", appInd.getTextOnElement(oBrowser, PayCRM_Modules_GeneralUIPage.obj_SwitchReason_List, "Dropdown"));
			appInd.setObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_PaymentSwitchNotes_Textarea, switchVariant.replace("None", (paymentTypeFrom.split(" - "))[1].trim()));
			switchDetails.put("Notes", switchVariant.replace("None", (paymentTypeFrom.split(" - "))[1].trim()));
			reports.writeResult(oBrowser, "Screenshot", "After entering the switch Reason and Notes for SWitch Offers");
			appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_UpdateAcceptedOffer_Btn);
			appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOfferSuccess_Grid + "//h3"), "Text", "Success!", maxWaitTimeOut);
			appDep.threadSleep(4000);
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOfferSuccess_Grid + "//h3"), "Text", "Success!"));
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_SwitchOfferSuccess_Grid + "//p)[1]"), "Text").contains("Case "+switchDetails.get("caseNumber")+" - "+supplierName+" Offer Successfully Updated"));
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_SwitchOfferSuccess_Grid + "//p)[2]"), "Text").contains("Offer was Successfully Updated!"));
			reports.writeResult(oBrowser, "Screenshot", "The Payment switch for '"+switchVariant+"' was successful");
			oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
			appDep.threadSleep(4000);
			switchDetails.put("TestPassed", "true");
			return switchDetails;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'switchToPaymentTypes()' method. " + e);
			switchDetails.put("TestPassed", "false");
			return switchDetails;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'switchToPaymentTypes()' method. " + e);
			switchDetails.put("TestPassed", "false");
			return switchDetails;
		}
	}




	/********************************************************
	 * Method Name      : switchPaymentAbbreviation()
	 * Purpose          : get the switch payment from and to names based on the log info
	 * Author           : Gudi
	 * Parameters       : paymentSwitchType
	 * ReturnType       : String
	 ********************************************************/
	public String switchPaymentAbbreviation(String paymentSwitchType){
		String switchName = null;
		try{
			switch(paymentSwitchType.toLowerCase()){
				case "virtual card":
					switchName = "VCA";
					break;
				case "ach basic":
					switchName = "ACH";
					break;
				case "ach plus":
					switchName = "VND";
					break;
				default:
					reports.writeResult(oBrowser, "Fail", "Invalid payment switch name '"+paymentSwitchType+"' was mentioned.");
					return null;
			}
			return switchName;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'switchPaymentAbbreviation()' method. " + e);
			return null;
		}
	}




	/*****************************************************************
	 * Method Name      : selectToggleIcon()
	 * Purpose          : to select the toggle icons under filter dashboard for the selected Queues
	 * 					: Whatever the value for action (Yes/No), the reverse text will be clicked
	 * Author           : Gudi
	 * Parameters       : oBrowser, toggleType, action
	 * ReturnType       : boolean
	 *****************************************************************/
	public boolean selectToggleIcon(WebDriver oBrowser, String strObjBy, String toggleType, String action){
		try{
			switch(toggleType.toLowerCase()){
				case "single":
					if(!oBrowser.findElement(By.xpath(strObjBy + "/parent::label/div[1]/span[@class='toggle-"+action.toLowerCase()+"']")).isDisplayed() && action.equalsIgnoreCase("Yes")){
						appInd.clickObject(oBrowser, By.xpath(strObjBy + "/parent::label/div[1]/span[@class='toggle-no']"));
					}else if(!oBrowser.findElement(By.xpath(strObjBy + "/parent::label/div[1]/span[@class='toggle-"+action.toLowerCase()+"']")).isDisplayed() && action.equalsIgnoreCase("No")) {
						appInd.clickObject(oBrowser, By.xpath(strObjBy + "/parent::label/div[1]/span[@class='toggle-yes']"));
					}
					break;
				case "multiple":
					if(oBrowser.findElement(By.xpath(strObjBy)).getAttribute("checked").equalsIgnoreCase("True")){
						appDep.threadSleep(1000);
						appInd.clickObject(oBrowser, By.xpath(strObjBy + "/following-sibling::span[@class='switch-sm-handle']"));
						appDep.threadSleep(1000);
						appInd.clickObject(oBrowser, By.xpath(strObjBy + "/following-sibling::span[@class='switch-sm-handle']"));
					}else {
						appInd.clickObject(oBrowser, By.xpath(strObjBy + "/following-sibling::span[@class='switch-sm-handle']"));
					}
					break;
				default:
					reports.writeResult(oBrowser, "Fail", "Invalid toggle button type '"+toggleType+"' value");
					return false;
			}
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'selectToggleIcon()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'selectToggleIcon()' method. " + e);
			return false;
		}
	}




	/*****************************************************************
	 * Method Name      : navigateToPaymentQuestions()
	 * Purpose          : to navigate to 'Payment Questions' page
	 * Author           : Gudi
	 * Parameters       : oBrowser
	 * ReturnType       : boolean
	 *****************************************************************/
	public boolean navigateToPaymentQuestions(WebDriver oBrowser){
		List<WebElement> oEmailID = null;
		try{
			appDep.threadSleep(2000);
			Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_EmailTemplate_Btn));
			Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentQuestion_Link));
			appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentQuestion_Send_Btn, "Clickable", "", waitTimeOut);

			appDep.waitForTheElementState(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_PaymentQuestions_To_Label), "Clickable", "", minTimeOut);
			oEmailID = oBrowser.findElements(By.xpath(AssistedPaymentServicesUIPage.obj_PaymentQuestions_To_Label + "//li[@class='search-choice']"));
			for(int i=0; i<oEmailID.size(); i++){
				if(!oEmailID.get(i).getText().equalsIgnoreCase(appInd.getPropertyValueByKeyName("emailUserName"))){
					Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_PaymentQuestions_To_Label + "//li[@class='search-choice']/a")));
				}
			}

			reports.writeResult(oBrowser, "Screenshot", "Navigated to 'Payment Questions' screen");
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'navigateToPaymentQuestions()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'navigateToPaymentQuestions()' method. " + e);
			return false;
		}finally {oEmailID = null;}
	}




	/*****************************************************************
	 * Method Name      : navigateToCasesPage()
	 * Purpose          : to navigate to Case page based on the QueueName
	 * Author           : Gudi
	 * Parameters       : oBrowser, queueName, queryKey, param
	 * ReturnType       : boolean
	 *****************************************************************/
	public boolean navigateToCasesPage(WebDriver oBrowser, String queueName, String queryKey, Object param[]){
		String caseNumber = null;
		String strURL = null;
		JSONArray caseDetails = null;
		try{
			caseDetails = apiDataProvider.getAPIDataProviderResponse(queryKey, param);
			caseNumber = ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("cases_id").toString();

			strURL = appInd.getPropertyValueByKeyName("qaURL")+"/cases/" + caseNumber;
			oBrowser.navigate().to(strURL);
			reports.writeResult(oBrowser, "Screenshot", "The constructed URL: " + strURL);
			appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentHelp_Label, "Text", "Payment Help", waitTimeOut);
			reports.writeResult(oBrowser, "Screenshot", "The page has opened with the case id '"+caseNumber+"'");
			Assert.assertTrue(appInd.verifyText(oBrowser, AssistedPaymentServicesUIPage.obj_PaymentHelp_Label, "Text", "Payment Help"));
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_CaseNumber_Label, "Text").contains("Case "+caseNumber));
			Assert.assertTrue(assistedPaymentServicesBaseSteps.setDefaultPaymentQuestionMailUnderPreferences(oBrowser, "Assisted Payment Services"));
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'navigateToCasesPage()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'navigateToCasesPage()' method. " + e);
			return false;
		}finally {
			caseNumber = null; strURL = null; caseDetails = null;
		}
	}




	/*****************************************************************
	 * Method Name      : navigateAndSelectTheContact()
	 * Purpose          : to navigate to Contacts page and select the contact which we got from the DB
	 * Author           : Gudi
	 * Parameters       : oBrowser, queryKey
	 * ReturnType       : boolean
	 *****************************************************************/
	public boolean navigateAndSelectTheContact(WebDriver oBrowser, String queryKey){
		JSONArray caseDetails = null;
		try{
			//Execute query to get the contact record
			caseDetails = apiDataProvider.getAPIDataProviderResponse(queryKey, new Object[] {});

			appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_ContactsTable_Grid + "//input)[1]"), ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("name").toString());
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
			appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_ContactsTable_Grid + "//input)[2]"), ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("phone_1").toString());
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
			appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_ContactsTable_Grid + "//input)[6]"), ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("email").toString());
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);

			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ContactsTable_Grid + "//tr[contains(@class, 'lines dx-column-lines')][1]/td[1]"), "Text", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("name").toString()));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ContactsTable_Grid + "//tr[contains(@class, 'lines dx-column-lines')][1]/td[2]"), "Text", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("phone_1").toString()));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_ContactsTable_Grid + "//tr[contains(@class, 'lines dx-column-lines')][1]/td[6]"), "Text", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("email").toString()));
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'navigateAndSelectTheContact()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'navigateAndSelectTheContact()' method. " + e);
			return false;
		}finally{caseDetails = null;}
	}




	/*****************************************************************
	 * Method Name      : navigateAndSelectTheWorkQueue()
	 * Purpose          : to navigate to Queue page and select the Queue which we got from the DB
	 * Author           : Gudi
	 * Parameters       : oBrowser, queryKey
	 * ReturnType       : boolean
	 *****************************************************************/
	public boolean navigateAndSelectTheWorkQueue(WebDriver oBrowser, String queryKey){
		JSONArray caseDetails = null;
		try{
			caseDetails = apiDataProvider.getAPIDataProviderResponse(queryKey, new Object[] {});

			appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_Queues_Grid + "//input)[1]"), ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("name").toString());
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
			appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_Queues_Grid + "//input)[2]"), ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("work_queue_type_name").toString());
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
			appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_GeneralUIPage.obj_Queues_Grid + "//input)[4]"), ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("priority_name").toString());
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);

			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Queues_Grid + "//tr[contains(@class, 'lines dx-column-lines')][1]/td[1]"), "Text", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("name").toString()));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Queues_Grid + "//tr[contains(@class, 'lines dx-column-lines')][1]/td[2]"), "Text", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("work_queue_type_name").toString()));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_Queues_Grid + "//tr[contains(@class, 'lines dx-column-lines')][1]/td[4]"), "Text", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("priority_name").toString()));
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'navigateAndSelectTheWorkQueue()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'navigateAndSelectTheWorkQueue()' method. " + e);
			return false;
		}finally{caseDetails = null;}
	}




	/*****************************************************************
	 * Method Name      : navigateAndGetRPACaseNumber()
	 * Purpose          : to navigate to Queue page and select the Queue which we got from the DB
	 * Author           : Gudi
	 * Parameters       : oBrowser
	 * ReturnType       : String
	 *****************************************************************/
	public String navigateAndGetRPACaseNumber(WebDriver oBrowser){
		String caseNumber= null;
		try{
			sParent = oBrowser.getWindowHandle();
			caseNumber = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_RPA_ModuleUIPage.obj_RPA_Cases_Grid + "//tr[contains(@class, 'lines dx-column-lines')][1]/td[1]"), "Text");
			appInd.clickObject(oBrowser, By.xpath(PayCRM_RPA_ModuleUIPage.obj_RPA_Cases_Grid + "//tr[contains(@class, 'lines dx-column-lines')][1]/td[1]"));
			oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
			appDep.waitForTheElementState(oBrowser, PayCRM_RPA_ModuleUIPage.obj_PaymentHelp_Label, "Text", "Payment Help", waitTimeOut);
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_RPA_ModuleUIPage.obj_CaseNumber_Details + "//h3"), "Text", "Case " + caseNumber));
			reports.writeResult(oBrowser, "Screenshot", "The case # '" + caseNumber + "' page was opened successful");
			return caseNumber;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'navigateAndGetRPACaseNumber()' method. " + e);
			return null;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'navigateAndGetRPACaseNumber()' method. " + e);
			return null;
		}finally {caseNumber = null;}
	}




	/*****************************************************************
	 * Method Name      : getTheUserRolesSelected()
	 * Purpose          : to get the all the user roles which are selected for the user
	 * Author           : Gudi
	 * Parameters       : oBrowser
	 * ReturnType       : List<String>
	 *****************************************************************/
	public List<String> getTheUserRolesSelected(WebDriver oBrowser){
		List<String> objRolesSelected_Names = null;
		List<WebElement> objRoleSelected_oEles = null;
		try{
			appInd.clickObject(oBrowser, By.xpath(PayCRM_RPA_ModuleUIPage.obj_NavigationBar_UserName_Tab + "//a[contains(@class, 'truncate-sm dropdown')]"));
			appInd.clickObject(oBrowser, By.xpath(PayCRM_RPA_ModuleUIPage.obj_NavigationBar_UserName_Tab + "//a[text()='Edit Profile']"));
			appDep.waitForTheElementState(oBrowser, PayCRM_RPA_ModuleUIPage.obj_UpdateRole_Btn, "Clickable", "", waitTimeOut);
			appDep.threadSleep(2000);
			objRolesSelected_Names = new ArrayList<String>();
			objRoleSelected_oEles = oBrowser.findElements(By.xpath(PayCRM_RPA_ModuleUIPage.obj_Roles_Grid + "/li/input[@checked]/following-sibling::label"));
			int roleChkboxesCount = objRoleSelected_oEles.size();
			for(int i=0; i < roleChkboxesCount; i++){
				objRolesSelected_Names.add(0, objRoleSelected_oEles.get(i).getText());
			}
			appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_payCRM_NavigateBar_Link);
			appDep.waitForTheElementState(oBrowser, PayCRMMainUIPage.obj_EnrollmentsManager_Label, "Clickable", "", maxWaitTimeOut);
			return objRolesSelected_Names;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'getTheUserRolesSelected()' method. " + e);
			return null;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'getTheUserRolesSelected()' method. " + e);
			return null;
		}
	}




	/*****************************************************************
	 * Method Name      : exportTheFile()
	 * Purpose          : to export the file
	 * Author           : Gudi
	 * Parameters       : oBrowser
	 * ReturnType       : String
	 *****************************************************************/
	public String exportTheFile(WebDriver oBrowser, By objExportBtn){
		Alert oAlert = null;
		String fileName = null;
		try{
			Assert.assertTrue(appInd.clickObject(oBrowser, objExportBtn));
			appDep.threadSleep(2000);
			oAlert = oBrowser.switchTo().alert();
			fileName = oAlert.getText();
			oAlert.accept();
			appDep.threadSleep(4000);
			return  fileName;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'exportTheFile()' method. " + e);
			return null;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'exportTheFile()' method. " + e);
			return null;
		}
	}



	/********************************************************
	 * Method Name      : validateTheListValues()
	 * Purpose          : User validates the list of given values must be present in the actual UI
	 * Author           : Gudi
	 * Parameters       : oBrowser, listValuesToCheck
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean validateTheListValues(WebDriver oBrowser, By objBy, String listValuesToCheck){
		List<WebElement> objList = null;
		String arrExpectedValues[];
		try{
			arrExpectedValues = listValuesToCheck.split("#");
			objList = new Select(oBrowser.findElement(objBy)).getOptions();

			for(int i=0; i<objList.size(); i++){
				Assert.assertTrue(appInd.compareValues(oBrowser, objList.get(i).getText(), arrExpectedValues[i]));
			}
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'exportTheFile()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'exportTheFile()' method. " + e);
			return false;
		}finally {objList = null; arrExpectedValues = null;}
	}




	/********************************************************
	 * Method Name      : searchAndOpenTheSupportCase()
	 * Purpose          : User search the opens the required (Manual/Automated/Any) support case
	 * Author           : Gudi
	 * Parameters       : oBrowser, arrColumnFilterData
	 * ReturnType       : Map<String, String>
	 ********************************************************/
	public Map<String, String> searchAndOpenTheSupportCase(WebDriver oBrowser, String arrColumnFilterData[]){
		Map<String, String> supportCaseDetails = null;
		try{
			supportCaseDetails = new HashMap<>();
			Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "Support Cases"));
			Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Tab));
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);

			if(!arrColumnFilterData[0].isEmpty())
				Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//input)[6]"), arrColumnFilterData[0]));
			if(!arrColumnFilterData[1].isEmpty())
				Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//input)[7]"), arrColumnFilterData[1]));
			if(!arrColumnFilterData[2].equalsIgnoreCase("Any")){
				Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//input)[14]"), arrColumnFilterData[2]));
			}
			appDep.threadSleep(2000);
			reports.writeResult(oBrowser, "Screenshot", "Filtering for the '"+arrColumnFilterData[2]+"' support Case");
			supportCaseDetails.put("caseNumber", appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td)[1]"), "Text"));
			supportCaseDetails.put("paynetID", appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td)[2]"), "Text"));
			supportCaseDetails.put("supplierName", appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td)[3]"), "Text"));
			supportCaseDetails.put("areaName", appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td)[5]"), "Text"));
			supportCaseDetails.put("caseType", appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td)[6]"), "Text"));
			supportCaseDetails.put("originationSource", appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td)[12]"), "Text"));

			appDep.waitForTheElementState(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td)[1]"), "Clickable", "", waitTimeOut);
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td)[1]")));
			oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_Link, "Clickable", "", waitTimeOut);
			supportCaseDetails.put("TIN", appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupplierInformation_Section + "//div[text()='TIN']/following-sibling::div"), "Text"));
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_Link));
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_CaseNumber_Label, "Text").contains(supportCaseDetails.get("caseNumber")));
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupplierInformation_Section + "//div[text()='PayNet ID']/following-sibling::div"), "Text").contains(supportCaseDetails.get("paynetID")));
			reports.writeResult(oBrowser, "Pass", "The '"+arrColumnFilterData[2]+"' support Case was selected and opened successful");
			reports.writeResult(oBrowser, "Screenshot", "The '"+arrColumnFilterData[2]+"' support Case was selected and opened successful");
			return supportCaseDetails;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'searchAndOpenTheSupportCase()' method. " + e);
			return null;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'searchAndOpenTheSupportCase()' method. " + e);
			return null;
		}finally{supportCaseDetails = null;}
	}




	/********************************************************
	 * Method Name      : validateSupportCaseStatusAndActivitiesTab()
	 * Purpose          : User validates Support Case status and activities tab once after performing the Log Activity for the selected Support cases
	 * Author           : Gudi
	 * Parameters       : oBrowser, supportCaseData
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean validateSupportCaseStatusAndActivitiesTab(WebDriver oBrowser, Map<String, String> supportCaseData)
	{
		try{
			if(supportCaseData.get("CaseOutcome").equalsIgnoreCase("Validation Pending")){
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Cases_Section + "//div[@class='sticky-status white']/div"), "Text", "Hold"));
				Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_LogActivity_Btn_Presence+"[not(@style)]//div[@id='support_case_btn_log_activity']")));
				Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_LogActivity_Btn_Presence+"[not(@style)]//div[@id='btn_reassign']")));
			}else if(supportCaseData.get("CaseOutcome").equalsIgnoreCase("Validation Closed")){
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Cases_Section + "//div[@class='sticky-status white']/div"), "Text", "Closed"));
				Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_LogActivity_Btn_Presence+"[not(@style)]//div[@id='support_case_btn_log_activity']")));
				Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_LogActivity_Btn_Presence+"[not(@style)]//div[@id='btn_reassign']")));
			}

			Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_Link));
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
			appDep.waitForTheElementState(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Activities_Grid + "//input)[7]"), "Clickable", "", minTimeOut);
			Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Activities_Grid + "//input)[7]"), supportCaseData.get("Notes")));
			appDep.threadSleep(2000);

			if (supportCaseData.get("CaseOutcome").equalsIgnoreCase("Validation Closed")) {
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Activities_Grid + "//tr[2][contains(@class, 'lines dx-column-lines')]/td)[" + (1) + "]"), "Text", "Open: " + supportCaseData.get("CaseOutcome")));
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Activities_Grid + "//tr[2][contains(@class, 'lines dx-column-lines')]/td)[" + (2) + "]"), "Text", supportCaseData.get("ActivityType")));
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Activities_Grid + "//tr[2][contains(@class, 'lines dx-column-lines')]/td)[" + (3) + "]"), "Text", supportCaseData.get("CaseOutcomeReason")));
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Activities_Grid + "//tr[2][contains(@class, 'lines dx-column-lines')]/td)[" + (4) + "]"), "Text", appInd.getPropertyValueByKeyName("qaUserName")));
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Activities_Grid + "//tr[2][contains(@class, 'lines dx-column-lines')]/td)[" + (6) + "]"), "Text", supportCaseData.get("Notes")));
			} else {
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Activities_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td)[" + (1) + "]"), "Text", "Open: " + supportCaseData.get("CaseOutcome")));
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Activities_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td)[" + (2) + "]"), "Text", supportCaseData.get("ActivityType")));
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Activities_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td)[" + (3) + "]"), "Text", supportCaseData.get("CaseOutcomeReason")));
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Activities_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td)[" + (4) + "]"), "Text", appInd.getPropertyValueByKeyName("qaUserName")));
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Activities_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td)[" + (6) + "]"), "Text", supportCaseData.get("Notes")));
			}
		return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'validateSupportCaseStatusAndActivitiesTab()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateSupportCaseStatusAndActivitiesTab()' method. " + e);
			return false;
		}
	}




	/********************************************************
	 * Method Name      : sendEmail_ComposeNewMessageFunctionality()
	 * Purpose          : User perform send Email functionality based on the Queue selected (Ex: Assisted Payment Services/Enrollment Managers queues)
	 * Author           : Gudi
	 * Parameters       : oBrowser, queueName, dataTable
	 * ReturnType       : Map<String, String>
	 ********************************************************/
	public Map<String, String> sendEmail_ComposeNewMessageFunctionality(WebDriver oBrowser, String queueName, DataTable dataTable){
		List<Map<String, String>> sendMailData = null;
		Map<String, String> mailData = null;
		String timeStamp = null;
		Actions oAction = null;
		List<WebElement> oMailReceipients = null;
		String arrFilesSupported[];
		try{
			timeStamp = appInd.getDateTime("Shhmmss");
			sendMailData = dataTable.asMaps(String.class, String.class);
			mailData = new HashMap<String, String>();
			mailData.put("To", appInd.getPropertyValueByKeyName("emailUserName"));
			mailData.put("Subject", sendMailData.get(0).get("Subject")+timeStamp);
			mailData.put("FileToUpload", sendMailData.get(0).get("FileToUpload"));
			mailData.put("Message", sendMailData.get(0).get("Message")+timeStamp);
			mailData.put("isFileSupported", sendMailData.get(0).get("isFileSupported"));

			arrFilesSupported = mailData.get("FileToUpload").split("#");
			for(int i=0; i<arrFilesSupported.length; i++){
				Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_EmailTemplate_Btn));
				Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_SendEmail_Link));
				appDep.waitForTheElementState(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_ComposeNewMail_Dialog + "//button[@id='modal_contact_email_submit']"), "Clickable", "", waitTimeOut);
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_ComposeNewMail_Dialog + "//h4"), "Text", "Compose New Message"));

				//Verify that email templates in PayCRM to send from @corcentric.com domain
				Assert.assertTrue(appInd.verifyText(oBrowser, AssistedPaymentServicesUIPage.obj_SendMail_From_Edit, "Text", appInd.getPropertyValueByKeyName("qaUserName")));

				oAction = new Actions(oBrowser);
				oAction.sendKeys(oBrowser.findElement(AssistedPaymentServicesUIPage.obj_SendEmail_To_Edit), mailData.get("To")).sendKeys(Keys.ENTER).build().perform();
				Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_ComposeNewMail_Dialog + "//h4")));

				oMailReceipients = oBrowser.findElements(By.xpath(EmailUtilities.obj_To_Receipient_Label + "/.."));
				for(int j=0; j<oMailReceipients.size(); j++){
					if(!oMailReceipients.get(j).getText().equalsIgnoreCase(mailData.get("To"))){
						Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+EmailUtilities.obj_To_Receipient_Label + ")["+(j+1)+"]")));
					}
				}

				oMailReceipients = oBrowser.findElements(By.xpath(EmailUtilities.obj_To_Receipient_Label + "/../a"));
				if(oMailReceipients.size() > 1) oMailReceipients.get(0).click();

				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(EmailUtilities.obj_To_Receipient_Label + "/.."), "Text", mailData.get("To")));
				Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_SendEmail_Subject_Edit, mailData.get("Subject")));

				if(mailData.get("FileToUpload")!=null){
					Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_SendMail_AttachFile_Edit, System.getProperty("user.dir").replace("\\", "/") + "/TestData/" + arrFilesSupported[i]));
					appDep.threadSleep(2000);
					if(arrFilesSupported[i].contains("/")) Assert.assertTrue(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_SendMail_AttachFile_Edit, "Value").contains((arrFilesSupported[i].split("/"))[1]));
					else Assert.assertTrue(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_SendMail_AttachFile_Edit, "Value").contains(arrFilesSupported[i]));
				}
				Assert.assertTrue(appInd.setObject(oBrowser, AssistedPaymentServicesUIPage.obj_SendMail_Body_Textarea, mailData.get("Message")));
				Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_ComposeNewMail_Dialog + "//button[@id='modal_contact_email_submit']")));
				appDep.waitForTheElementState(oBrowser, By.xpath(AssistedPaymentServicesUIPage.obj_ComposeNewMail_Dialog + "//button[@id='modal_contact_email_submit']"), "InVisibility", "", waitTimeOut);
				appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignConfirmationMessage_Label, "Visibility", "", waitTimeOut);
				if(mailData.get("isFileSupported").equalsIgnoreCase("Yes")){
					Assert.assertTrue(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignConfirmationMessage_Label, "Text").contains("Email has been successfully sent to " + mailData.get("To")));
					appInd.scrollToElement(oBrowser, AssistedPaymentServicesUIPage.obj_SplitterHorizonal_Line);
					Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_Reassign_Logs_Tab));
					appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
					Assert.assertTrue(appInd.clickObject(oBrowser, AssistedPaymentServicesUIPage.obj_Activities_Tab));
					appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
					reports.writeResult(oBrowser, "Screenshot", "The '"+queueName+"' 'Activities' tab details for 'Send Mail' functionality");
					if(queueName.equalsIgnoreCase("Assisted Payment Services")){
						Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+AssistedPaymentServicesUIPage.obj_Activities_Grid + "//input)[6]"), mailData.get("Message")));
					}else{
						Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+AssistedPaymentServicesUIPage.obj_Activities_Grid + "//input)[7]"), mailData.get("Message")));
					}

					appDep.threadSleep(2000);
					Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+AssistedPaymentServicesUIPage.obj_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]//td)[3]"), "Text", "Outbound Email (Case Email) to: " + mailData.get("To")));
					Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+AssistedPaymentServicesUIPage.obj_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]//td)[4]"), "Text", "Outbound Email"));
					if(queueName.equalsIgnoreCase("Assisted Payment Services"))
						Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+AssistedPaymentServicesUIPage.obj_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]//td)[5]"), "Text", appInd.getPropertyValueByKeyName("qaUserName")));
					else
						Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+AssistedPaymentServicesUIPage.obj_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]//td)[6]"), "Text", appInd.getPropertyValueByKeyName("qaUserName")));
					if(queueName.equalsIgnoreCase("Assisted Payment Services"))
						Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+AssistedPaymentServicesUIPage.obj_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]//td)[7]"), "Text", "Case Email email sent to "+mailData.get("To")+" on "+appInd.getDateTimeByTimezone("yyyy-MM-dd", "CST")+". EMAIL BODY: " + mailData.get("Message")));
					else
						Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+AssistedPaymentServicesUIPage.obj_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]//td)[8]"), "Text", "Case Email email sent to "+mailData.get("To")+" on "+appInd.getDateTimeByTimezone("yyyy-MM-dd", "CST")+". EMAIL BODY: " + mailData.get("Message")));
				}else{
					Assert.assertTrue(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignConfirmationMessage_Label, "Text").contains("Failed to send email to " + mailData.get("To")));
					Assert.assertTrue(appInd.getTextOnElement(oBrowser, AssistedPaymentServicesUIPage.obj_ReassignConfirmationMessage_Label, "Text").contains("Validation failed: File type must be one of: application/pdf, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/msword, application/vnd.openxmlformats-officedocument.wordprocessingml.document, image/jpeg, image/png, image/tiff, image/gif, image/svg, image/svg+xml, image/x-ms-bmp, image/bmp, application/x-zip-compressed, application/zip"));
				}
			}
			mailData.put("TestPassed", "true");
			return  mailData;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'sendEmail_ComposeNewMessageFunctionality()' method. " + e);
			mailData.put("TestPassed", "False");
			return  mailData;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'sendEmail_ComposeNewMessageFunctionality()' method. " + e);
			mailData.put("TestPassed", "False");
			return  mailData;
		}finally {sendMailData = null; mailData = null; timeStamp = null; oAction = null; oMailReceipients = null;}
	}





	/********************************************************
	 * Method Name      : searchPaymentID()
	 * Purpose          : User perform search Payment ID and validate that the payment id is searched successful
	 * Author           : Gudi
	 * Parameters       : oBrowser, paymentID
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean searchPaymentID(WebDriver oBrowser, String paymentID){
		boolean blnRes = false;
		try{
			blnRes = appInd.verifyOptionalElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EnterPaymentID_Edit);
			if(!blnRes)
				Assert.assertTrue(appDep.selectManagePaymentModules(oBrowser, "Payment Search"));

			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_EnterPaymentID_Edit, paymentID));
			Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_SubmitBtn));
			appDep.threadSleep(2000);
			if(appInd.verifyOptionalElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_SearchPaymentID_Error_Msg)){
				Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_SubmitBtn));
			}
			oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
			Assert.assertTrue(appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Link, "Clickable", "", waitTimeOut));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_PaymentSearch_PaymentDetails_Grid + "//th[text()='Payment ID']/following-sibling::td[1]"), "Text", paymentID));
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'searchPaymentID()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'searchPaymentID()' method. " + e);
			return false;
		}
	}


	/********************************************************
	 * Method Name      : supportCaseLogActivity()
	 * Purpose          : User perform log activity for the support cases
	 * Author           : Gudi
	 * Parameters       : oBrowser, logActivity
	 * ReturnType       : boolean
	 ********************************************************/
	public Map<String, String> supportCaseLogActivity(WebDriver oBrowser, String caseNumber, Map<String, String> logActivity){
		String arrActivityTypeData[];
		boolean blnAttachment = false;
		try{
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_LogActivity_Btn)));
			oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_LogActivity_Notes_Textarea, "Clickable", "", waitTimeOut);

			//Validate and perform the action for 'Unable To Validate' & 'Validation Completed' toggle buttons
			if(logActivity.get("UnableToValidate").equalsIgnoreCase("On")){
				boolean blnRes = appInd.verifyOptionalElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ActivityType_List + "[not(contains(@class, 'disable-submit'))]"));
				if(blnRes) Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_UnableToValidate_Toggle));
			}

			if(logActivity.get("ValidateCompleted").equalsIgnoreCase("On")){
				boolean blnRes = appInd.verifyOptionalElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ActivityType_List + "[not(contains(@class, 'disable-submit'))]"));
				if(blnRes) Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ValidationCompleted_Toggle));
			}

			if(logActivity.get("UnableToValidate").equalsIgnoreCase("Off") && logActivity.get("ValidateCompleted").equalsIgnoreCase("Off") || logActivity.get("UnableToValidate").equalsIgnoreCase("Off") && logActivity.get("ValidateCompleted").equalsIgnoreCase("On")){
				Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ActivityType_List), logActivity.get("ActivityType")));
				appDep.threadSleep(2000);
				arrActivityTypeData = logActivity.get("ActivityTypeSpecificData").split("#", -1);
				switch(logActivity.get("ActivityType").toLowerCase()){
					case "email":
						Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_EmailAddress_Edit, arrActivityTypeData[0]));
						Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ContactName_Edit, arrActivityTypeData[1]));
						Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ContactNumber_Edit, arrActivityTypeData[2]));
						Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ConversationID_Edit, arrActivityTypeData[3]));
						break;
					case "fax":
						Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_FaxNumber_Edit, arrActivityTypeData[0]));
						Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Fax_ConversationID_Edit, arrActivityTypeData[1]));
						break;
					case "phone (inbound/outbound)":
						Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_PhoneNumber_Edit, arrActivityTypeData[0]));
						Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Phone_ContactName_Edit, arrActivityTypeData[1]));
						Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Phone_JobTitle_Edit, arrActivityTypeData[2]));
						Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Phone_ConversationID_Edit, arrActivityTypeData[3]));
						break;
					case "website":
						Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WebAddress_Edit, appInd.getPropertyValueByKeyName(arrActivityTypeData[0])));
						break;
					default:
						reports.writeResult(oBrowser, "Fail", "Invalid option was specified for the Activity Type '"+logActivity.get("ActivityType")+"'");
						Assert.fail("Invalid option '"+logActivity.get("ActivityType")+"' was specified for the Activity Type dropdown");
				}

				if(logActivity.get("CaseOutcome").equalsIgnoreCase("Validation Pending")){
					Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_AlarmDate_Edit));
					Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_CaseOutCome_Hold_Msg, "Text", "This case will be put on hold"));
				}

				Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Reason_List, 2));
				logActivity.put("CaseOutcomeReason", appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Reason_List, "Dropdown"));
			}else{
				Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ActivityType_List)));
				Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ActivityType_List), logActivity.get("ActivityType")));
				Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WebAddress_Edit, appInd.getPropertyValueByKeyName("qaURL")));
				logActivity.put("ActivityType", "Case Update");

				Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_CaseOutcome_Disabled_List));
				Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_CaseOutcome_Disabled_List, "Dropdown", "Validation Closed"));
				logActivity.put("CaseOutcome", "Validation Closed");


				Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_CaseOutcomeReason_List));
				if(logActivity.get("UnableToValidate").equalsIgnoreCase("On")) {
					Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_CaseOutcomeReason_List, "Dropdown", "Unable to Validate"));
					logActivity.put("UnableToValidate", "Unable to Validate");
				}
				else if(logActivity.get("ValidateCompleted").equalsIgnoreCase("On")) {
					Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_CaseOutcomeReason_List, "Dropdown", "Validation Completed"));
					logActivity.put("ValidateCompleted", "Validation Completed");
				}
			}

			if(logActivity.get("SupportCaseType").equalsIgnoreCase("Automatic") && logActivity.get("FileToUpload")!=null){
				Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ChooseFile_Btn, System.getProperty("user.dir").replace("\\", "/") + "/TestData/" + logActivity.get("FileToUpload")+""));
				appDep.threadSleep(2000);
				blnAttachment = true;
			}

			String notes = logActivity.get("Notes") +" - "+appInd.getDateTime("Shhmmss");
			Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_LogActivity_Notes_Textarea, notes));
			logActivity.put("Notes", appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_LogActivity_Notes_Textarea, "Value"));

			Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activity_Save_Btn));
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_HideResults_Btn, "Clickable", "", waitTimeOut);
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_LogActivity_Result_Section + "//h3"), "Text").contains("Updated"));
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_LogActivity_Result_Section + "//p"), "Text").contains("Case " + caseNumber + " - has been successfully updated with a new Case Log Activity."));
			if(blnAttachment){
				Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_LogActivity_Result_Section + "//span"), "Text").contains("success: File successfully uploaded and attached"));
			}
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ExitForm_Btn));
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_HideResults_Btn));
			logActivity.put("TestPassed", "True");
			return logActivity;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'supportCaseLogActivity()' method. " + e);
			logActivity.put("TestPassed", "False");
			return logActivity;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'supportCaseLogActivity()' method. " + e);
			logActivity.put("TestPassed", "False");
			return logActivity;
		}finally {arrActivityTypeData = null;}
	}




	/********************************************************
	 * Method Name      : getTheTINMatchingDetailsByQuery()
	 * Purpose          : get the SupplierPayNetID from query and goto Modules-->Daily Grind-->StopFraud-->Validation Lookup, search by SupplierPayNetID & get the details
	 * 					  also validate that the given Validation Types (String arrValidationTypes[]) status should be pending.
	 * Author           : Gudi
	 * Parameters       : oBrowser, queryKey, arrValidationTypes
	 * ReturnType       : Map<String, String>
	 ********************************************************/
	public Map<String, String> getTheTINMatchingDetailsByQuery(WebDriver oBrowser, String queryKey, String validationTypes){
		Map<String, String> supplierData = null;
		JSONArray caseDetails = null;
		String payNetID = null;
		String arrValidationTypes[];
		try{
			supplierData = new HashMap<String, String>();
			arrValidationTypes = validationTypes.split("#");

			caseDetails = apiDataProvider.getAPIDataProviderResponse(queryKey, new Object[] {});
			payNetID = ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("CompanyID").toString();

			Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "Stopfraud"));
			Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_StopFraud_ValidationLookup_link));
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SearchByPayNetId_Edit, "Clickable", "", waitTimeOut);
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SearchByPayNetId_Edit, payNetID));
			Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Filter_Btn));
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", minTimeOut);

			supplierData.put("payNetSupplierID", appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Grid + "//td[1]"), "Text"));
			supplierData.put("supplierName", appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Grid + "//td[2]"), "Text"));
			supplierData.put("TIN", appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Grid + "//td[3]"), "Text"));

			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Grid + "//td[1]")));
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", minTimeOut);

			for(int i=0; i<arrValidationTypes.length; i++){
				boolean blnRes = false;
				Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Company_OuterGrid + "//table[contains(@id, 'DataTables_Table_')]//td[contains(text(), '"+arrValidationTypes[i]+"')]/following-sibling::td/span[text()='Pending']")), "The Validation Type '"+arrValidationTypes[i]+"' doesn't having the status 'PENDING'");
			}

			reports.writeResult(oBrowser, "Pass", "The '"+validationTypes+"' cases with the status 'PENDING' was selected successful");
			reports.writeResult(oBrowser, "Screenshot", "The '"+validationTypes+"' cases with the status 'PENDING' was selected successful");
			supplierData.put("TestPassed", "True");
			return supplierData;
		}catch (Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'getTheTINMatchingDetailsByQuery()' method. " + e);
			supplierData.put("TestPassed", "False");
			return supplierData;
		}catch (AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'getTheTINMatchingDetailsByQuery()' method. " + e);
			supplierData.put("TestPassed", "False");
			return supplierData;
		}finally{
			supplierData = null; caseDetails = null; payNetID = null; arrValidationTypes = null;
		}
	}




	/********************************************************
	 * Method Name      : searchTheSupplierBasedOnPayNetIDFromStopFraud()
	 * Purpose          : user navigates to Modules->Daily Grind->StopFraud-->Validation Lookup and select the supplier based on the TIN/PayNET ID
	 * Author           : Gudi
	 * Parameters       : oBrowser, supplierDetails
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean searchTheSupplierBasedOnPayNetIDFromStopFraud(WebDriver oBrowser, Map<String, String> supplierDetails){
		String tabGrid;
		try{
			Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "Stopfraud"));
			Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_StopFraud_ValidationLookup_link));
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SearchByPayNetId_Edit, "Clickable", "", waitTimeOut);
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SearchByPayNetId_Edit, supplierDetails.get("payNetSupplierID")));
			Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Filter_Btn));
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", minTimeOut);
			reports.writeResult(oBrowser, "screenshot", "PayNetID is successfully searched in StopFraud");

			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Grid + "//td[1]"), "Text", supplierDetails.get("payNetSupplierID")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Grid + "//td[2]"), "Text", supplierDetails.get("supplierName")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Grid + "//td[3]"), "Text", supplierDetails.get("TIN")));

			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Grid + "//td[1]")));
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", minTimeOut);
			reports.writeResult(oBrowser, "screenshot", "StopFraud : Paynet record details are visible in grid");
			if(supplierDetails.get("AreaName").equalsIgnoreCase("Payment Information")){
				tabGrid = PayCRM_Modules_DailyGrindUIPage.obj_StopFraud_ValidationLookUp_PaymentGrid;
				appInd.clickObject(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_Generic_Text, "Payment (Electronic)' or text()='Payment (Electronic) *")));
				threadSleep(3000);
			}
			else
				tabGrid = PayCRM_Modules_DailyGrindUIPage.obj_Company_OuterGrid;
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(tabGrid + "//table[contains(@id, 'DataTables_Table_')]//td[contains(text(), '"+supplierDetails.get("ValidationType")+"')]/following-sibling::td/span[text()='"+supplierDetails.get("ValidationCaseStatus")+"']")),
					"The Validation Type '"+supplierDetails.get("ValidationType")+"' doesn't having the status '"+supplierDetails.get("ValidationCaseStatus")+"'");
			reports.writeResult(oBrowser, "screenshot", "StopFraud : ValidationType status validate successfully");
			return true;
		}catch (Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'searchTheSupplierBasedOnPayNetIDFromStopFraud()' method. " + e);
			return false;
		}catch (AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'searchTheSupplierBasedOnPayNetIDFromStopFraud()' method. " + e);
			return false;
		}
	}




	/********************************************************
	 * Method Name      : performUnableToValidateForValidationTypes()
	 * Purpose          : User perform 'Unable To Validate' for the required Validation types
	 * Author           : Gudi
	 * Parameters       : oBrowser, supplierDetails
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean performUnableToValidateForValidationTypes(WebDriver oBrowser, Map<String, String> supplierDetails){
		try{
			Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Company_Tab));
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", minTimeOut);
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Company_OuterGrid + "//table[contains(@id, 'DataTables_Table_')]//td[contains(text(), '"+supplierDetails.get("ValidationType")+"')]/following-sibling::td/span[text()='Pending']")), "The Validation Type '"+supplierDetails.get("ValidationType")+"' doesn't having the status 'PENDING'");
			reports.writeResult(oBrowser, "Screenshot", "'Unable to Validate' for the '"+supplierDetails.get("ValidationType")+"'");
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Company_OuterGrid + "//td[text()='"+supplierDetails.get("ValidationType")+"']/following-sibling::td/button[text()='Unable to Validate']")));
			appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_UnableToValidate_Dialog + "//button[@id='btn_unable_to_validate']"), "Clickable", "", minTimeOut);

			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_UnableToValidate_Dialog + "//h4"), "Text", "Unable to Validate"));
			Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_UnableToValidate_Notes_Textarea, supplierDetails.get("Notes")));
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_UnableToValidate_Dialog + "//button[@id='btn_unable_to_validate']")));
			appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_UnableToValidate_Success_Msg + "//p"), "Text", "Success!", waitTimeOut);
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_UnableToValidate_Success_Msg + "//p"), "Text", "Success!"));
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_UnableToValidate_Dialog + "//button[@class='close']")));

			//Validate the grid for 'Unable to validate'
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", minTimeOut);
			reports.writeResult(oBrowser, "Screenshot", "'Unable to Validate' for the '"+supplierDetails.get("ValidationType")+"' is completed");
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Company_OuterGrid + "//td[text()='"+supplierDetails.get("ValidationType")+"']/following-sibling::td[1]"), "Text").contains(appInd.formatDate(appInd.getDateTimeByTimezone("dd-MM-yyyy", "CST"), "dd-MM-yyyy", "MM/dd/yyyy")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Company_OuterGrid + "//td[text()='"+supplierDetails.get("ValidationType")+"']/following-sibling::td[4]"), "Text", "unabletovalidate@vendorin.com"));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Company_OuterGrid + "//td[text()='"+supplierDetails.get("ValidationType")+"']/following-sibling::td[5]/span"), "Text", "Unable to validate"));
			return true;
		}catch (Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'performUnableToValidateForValidationTypes()' method. " + e);
			return false;
		}catch (AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'performUnableToValidateForValidationTypes()' method. " + e);
			return false;
		}
	}




	/********************************************************
	 * Method Name      : validateCompanyInformationGridData()
	 * Purpose          : User perform 'Company Iformation' gird data validationfor the selected Validation Types
	 * Author           : Gudi
	 * Parameters       : oBrowser, validationType, objGridElement, supplierDetails
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean validateCompanyInformationGridData(WebDriver oBrowser, String validationType, String objGridElement, Map<String, String> supplierDetails){
		try{
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(objGridElement + "//td[1]/span"), "Text", supplierDetails.get("supplierName")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(objGridElement + "//td[2]/span"), "Text", supplierDetails.get("payNetSupplierID")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(objGridElement + "//td[3]/span"), "Text", supplierDetails.get("TIN")));
			reports.writeResult(oBrowser, "Screenshot", "The 'Company Information' was validated for the '"+validationType+"'");
			return true;
		}catch (Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'validateCompanyInformationGridData()' method. " + e);
			return false;
		}catch (AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateCompanyInformationGridData()' method. " + e);
			return false;
		}
	}





	/********************************************************
	 * Method Name      : performValidateForValidationTypes()
	 * Purpose          : User perform 'Validate' for the required Validation types using either Manual and Auto validation
	 * Author           : Gudi
	 * Parameters       : oBrowser, validationMode
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean performValidateForValidationTypes(WebDriver oBrowser, String validationMode, Map<String, String> supplierData){
		String objConfirmationMsg = null;
		String objValidationDialog = null;
		By objChooseBtn = null;
		By objAutoValidateBtn = null;
		By objManualValidateBtn = null;
		By objNotesTextarea = null;
		By objValidateBtn = null;
		try{
			Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Company_Tab));
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", minTimeOut);
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Company_OuterGrid + "//table[contains(@id, 'DataTables_Table_')]//td[contains(text(), '"+supplierData.get("ValidationType")+"')]/following-sibling::td/span[text()='Pending']")), "The Validation Type '"+supplierData.get("ValidationType")+"' doesn't having the status 'PENDING'");
			reports.writeResult(oBrowser, "Screenshot", "'Unable to Validate' for the '"+supplierData.get("ValidationType")+"'");

			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Company_OuterGrid + "//td[text()='"+supplierData.get("ValidationType")+"']/following-sibling::td/button[text()='Validate']")));


			//Validate company info panel
			if(supplierData.get("ValidationType").equalsIgnoreCase("TIN Matching")){
				objConfirmationMsg = PayCRM_Modules_DailyGrindUIPage.obj_TINValidationResultModal_msg;
				objValidationDialog = PayCRM_Modules_DailyGrindUIPage.obj_TINValidationModal_Grid;
				objChooseBtn = PayCRM_Modules_DailyGrindUIPage.obj_ChooseFile_Edit;
				objAutoValidateBtn = PayCRM_Modules_DailyGrindUIPage.obj_AutoValidate_Btn;
				objManualValidateBtn = PayCRM_Modules_DailyGrindUIPage.obj_ManualValidate_Btn;
				objNotesTextarea = PayCRM_Modules_DailyGrindUIPage.obj_TinValidate_Notes_Textarea;
				appDep.waitForTheElementState(oBrowser, By.xpath(objValidationDialog + "//button[@id='btn_tin_validation_modal_validate_manual']"), "Clickable", "", minTimeOut);
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(objValidationDialog + "//h4"), "Text", "TIN Validation"));
				Assert.assertTrue(validateCompanyInformationGridData(oBrowser, "TIN Matching", PayCRM_Modules_DailyGrindUIPage.obj_TINValidation_CompanyInfo_Grid, supplierData));
			}
			else if(supplierData.get("ValidationType").equalsIgnoreCase("OFAC Scan")){
				objConfirmationMsg = PayCRM_Modules_DailyGrindUIPage.obj_OFACValidationResultModal_msg;
				objValidationDialog = PayCRM_Modules_DailyGrindUIPage.obj_OFACValidationModal_Dialog;
				appDep.waitForTheElementState(oBrowser, By.xpath(objValidationDialog + "//button[@id='btn_ofac_manual_validate']"), "Clickable", "", minTimeOut);
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(objValidationDialog + "//h4"), "Text", "OFAC Validation - Company Information"));
				Assert.assertTrue(validateCompanyInformationGridData(oBrowser, "OFAC Scan", PayCRM_Modules_DailyGrindUIPage.obj_OFACScan_CompanyOnfo_Grid, supplierData));
				objChooseBtn = PayCRM_Modules_DailyGrindUIPage.obj_OFAC_ChooseFile_Edit;
				objAutoValidateBtn = PayCRM_Modules_DailyGrindUIPage.obj_OFAC_AutoValidate_Btn;
				objManualValidateBtn = PayCRM_Modules_DailyGrindUIPage.obj_OFAC_ManualValidate_Btn;
				objNotesTextarea = PayCRM_Modules_DailyGrindUIPage.obj_OFAC_Notes_Textarea;
			}
			else if(supplierData.get("ValidationType").equalsIgnoreCase("Address Validation")){
				objConfirmationMsg = PayCRM_Modules_DailyGrindUIPage.obj_AddressValidationResultModal_msg;
				objValidationDialog = PayCRM_Modules_DailyGrindUIPage.obj_AddressValidationModal_Dialog;
				appDep.waitForTheElementState(oBrowser, By.xpath(objValidationDialog + "//button[@id='btn_tin_validation_modal_validate_manual']"), "Clickable", "", minTimeOut);
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(objValidationDialog + "//h4"), "Text", "Address Validation"));
				Assert.assertTrue(validateCompanyInformationGridData(oBrowser, "Address Validation", PayCRM_Modules_DailyGrindUIPage.obj_AddressValidation_CompanyInfo_Grid, supplierData));
				objChooseBtn = PayCRM_Modules_DailyGrindUIPage.obj_AddressValidation_ChooseFile_Edit;
				objAutoValidateBtn = PayCRM_Modules_DailyGrindUIPage.obj_AddressValidation_AutoValidate_Btn;
				objManualValidateBtn = PayCRM_Modules_DailyGrindUIPage.obj_AddressValidation_ManualValidate_Btn;
				objNotesTextarea = PayCRM_Modules_DailyGrindUIPage.obj_AddressValidation_Notes_Textarea;
			}
			else if(supplierData.get("ValidationType").equalsIgnoreCase("Profile Authorization Form") || supplierData.get("ValidationType").equalsIgnoreCase("PAF")){
				objConfirmationMsg = PayCRM_Modules_DailyGrindUIPage.obj_PAFValidationResultModal_msg;
				objValidationDialog = PayCRM_Modules_DailyGrindUIPage.obj_PAFValidationModal_Dialog;
				appDep.waitForTheElementState(oBrowser, By.xpath(objValidationDialog + "//button[@id='paf_validation_modal_validate_btn']"), "Clickable", "", minTimeOut);
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(objValidationDialog + "//h4"), "Text", "PAF Validation - Company Information"));
				Assert.assertTrue(validateCompanyInformationGridData(oBrowser, "Profile Authorization Form", PayCRM_Modules_DailyGrindUIPage.obj_AddressValidation_CompanyInfo_Grid, supplierData));
				objChooseBtn = PayCRM_Modules_DailyGrindUIPage.obj_PAF_ChooseFile_Edit;
				objValidateBtn = PayCRM_Modules_DailyGrindUIPage.obj_PAF_Validate_Btn;
				objNotesTextarea = PayCRM_Modules_DailyGrindUIPage.obj_PAF_Notes_Textarea;
			}
			else{
				reports.writeResult(oBrowser, "Fail", "Invalid validation type '"+supplierData.get("ValidationType")+"' was mentioned");
				Assert.fail("Invalid validation type '"+supplierData.get("ValidationType")+"' was mentioned");
			}

			//attach the file 'Validation Attachment'
			Assert.assertTrue(appInd.setObject(oBrowser, objChooseBtn, System.getProperty("user.dir").replace("\\", "/") + "/TestData/" + supplierData.get("FileToUpload")));
			appDep.threadSleep(2000);
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, objChooseBtn, "Value").contains(supplierData.get("FileToUpload")));


			//Select the Company Information checkbox
			if(supplierData.get("ValidationType").equalsIgnoreCase("TIN Matching")){
				if(!oBrowser.findElement(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_CompanyInformation_ChkBox +"/input")).isSelected()){
					Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_CompanyInformation_ChkBox +"/input")));
				}
			}

			Assert.assertTrue(appInd.setObject(oBrowser, objNotesTextarea, supplierData.get("Notes")));

			if(supplierData.get("ValidationMode").equalsIgnoreCase("Manual validate"))
				appInd.clickObject(oBrowser, objManualValidateBtn);
			else if(supplierData.get("ValidationMode").equalsIgnoreCase("Auto validate"))
				appInd.clickObject(oBrowser, objAutoValidateBtn);
			else if(supplierData.get("ValidationMode").equalsIgnoreCase("Validate"))
				appInd.clickObject(oBrowser, objValidateBtn);
			else {
				reports.writeResult(oBrowser, "Fail", "Invalid button name '"+validationMode+"' for '"+supplierData.get("ValidationType")+"' Validation");
				Assert.fail("Invalid button name '"+validationMode+"' for '"+supplierData.get("ValidationType")+"' Validation");
			}


			appDep.waitForTheElementState(oBrowser, By.xpath(objConfirmationMsg + "//p"), "Text", "Success!", waitTimeOut);
			appInd.verifyText(oBrowser, By.xpath(objConfirmationMsg + "//p"), "Text", "Success!");
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(objValidationDialog + "//button[@class='close']")));

			//Validate the grid for 'Unable to validate'
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", minTimeOut);
			reports.writeResult(oBrowser, "Screenshot", "'"+supplierData.get("ValidationMode")+"' for the '"+supplierData.get("ValidationType")+"' is completed");
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Company_OuterGrid + "//td[text()='"+supplierData.get("ValidationType")+"']/following-sibling::td[1]"), "Text").contains(appInd.formatDate(appInd.getDateTimeByTimezone("dd-MM-yyyy", "CST"), "dd-MM-yyyy", "MM/dd/yyyy")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Company_OuterGrid + "//td[text()='"+supplierData.get("ValidationType")+"']/following-sibling::td[5]/span"), "Text", "Complete"));
			return true;
		}catch (Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'performValidateForValidationTypes()' method. " + e);
			return false;
		}catch (AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'performValidateForValidationTypes()' method. " + e);
			return false;
		}finally {objConfirmationMsg = null; objValidationDialog = null; objChooseBtn = null; objAutoValidateBtn = null; objManualValidateBtn = null; objNotesTextarea = null; objValidateBtn = null;}
	}




	/********************************************************
	 * Method Name      : runAPIPollForTheSupportCase()
	 * Purpose          : User perform 'Support Case Polling' for the Support case to get reflect in the 'Support case' Grid
	 * Author           : Gudi
	 * Parameters       : oBrowser, supplierDetails
	 * ReturnType       : Map<String, String>
	 ********************************************************/
	public Map<String, String> runAPIPollForTheSupportCase(WebDriver oBrowser, Map<String, String> supplierDetails){
		try{
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Visibility", "", maxWaitTimeOut);
			Assert.assertTrue(enrollmentManagersUIBaseSteps.selectOptionsFromDevMenu(oBrowser, "Support Case Polling"));

			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_RunAPIPoll_Btn));
			appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCasePolling_Grid + "//td[1]"), "Visibility", "", waitTimeOut);
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
			reports.writeResult(oBrowser, "Screenshot", "'Support Case Polling' page opened successful");
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCasePolling_Grid + "//td[text()='"+supplierDetails.get("payNetSupplierID")+"']/following-sibling::td[2][text()='"+supplierDetails.get("ValidationType")+"']"), "Text", supplierDetails.get("ValidationType")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCasePolling_Grid + "//td[text()='"+supplierDetails.get("payNetSupplierID")+"']/following-sibling::td[2][text()='"+supplierDetails.get("ValidationType")+"']/preceding-sibling::td[1]"), "Text", supplierDetails.get("supplierName")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCasePolling_Grid + "//td[text()='"+supplierDetails.get("payNetSupplierID")+"']/following-sibling::td[2][text()='"+supplierDetails.get("ValidationType")+"']/preceding-sibling::td[2]"), "Text", supplierDetails.get("payNetSupplierID")));
			supplierDetails.put("supportCaseID", appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCasePolling_Grid + "//td[text()='"+supplierDetails.get("payNetSupplierID")+"']/following-sibling::td[2][text()='"+supplierDetails.get("ValidationType")+"']/preceding-sibling::td[3]"), "Text"));

			supplierDetails.remove("TestPassed");
			supplierDetails.put("TestPassed", "True");
			return supplierDetails;
		}catch (Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'runAPIPollForTheSupportCase()' method. " + e);
			supplierDetails.put("TestPassed", "False");
			return supplierDetails;
		}catch (AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'runAPIPollForTheSupportCase()' method. " + e);
			supplierDetails.put("TestPassed", "False");
			return supplierDetails;
		}
	}





	/********************************************************
	 * Method Name      : validate_VoidRequest_Dialog_FunctionalityAndElements()
	 * Purpose          : User validates functionality & UI components for the 'Void Request' dialog both in Managed Payments & Assisted Payment Services
	 * Author           : Gudi
	 * Parameters       : oBrowser, remainingAmount, voidRequest
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean validate_VoidRequest_Dialog_FunctionalityAndElements(WebDriver oBrowser, String remainingAmount, Map<String, String> voidRequest){
		List<WebElement> oActionAfterVoid = null;
		String arrActionAfterVoid[];
		try{
			appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidRequest_Dialog + "//input[@value='Submit']"), "Clickable", "", waitTimeOut);
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidRequest_Dialog + "//input[@value='Submit']")));
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_AmountRemaining_Label));
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidFullAmount_Btn));
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_AmountToVoid_Edit));
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ActionToPerformAfterVoid_List));
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.id("person_country_usa")));
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.id("person_country_canada")));
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidRequest_Notes_Textarea));

			//Verify 'Action to Perform After Void' dropdown have the following values:
			//------------------- [ Re-issue as ACH, Re-issue as Check, Refund, No Action, Other ] -------------------
			arrActionAfterVoid = "#Re-issue as ACH#Re-issue as Check#Refund#No Action#Other".split("#");
			oActionAfterVoid = new Select(oBrowser.findElement(PayCRM_Modules_ManagedPaymentsUIPage.obj_ActionToPerformAfterVoid_List)).getOptions();
			for(int i=0; i<oActionAfterVoid.size(); i++){
				Assert.assertTrue(appInd.compareValues(oBrowser, oActionAfterVoid.get(i).getText().trim(), arrActionAfterVoid[i].trim()), "Mis-match in the 'Action to Perform After Void' dropdown values");
			}

			//------------------- Verify 'Is Link Revoked' radio button have the [ Yes/No ] values -------------------
			Assert.assertTrue(appInd.compareValues(oBrowser, appInd.getAttribute(oBrowser, By.id("person_country_usa"), "value"), "Yes"), "Mis-match in the 'Is The Link Revoked' values");
			Assert.assertTrue(appInd.compareValues(oBrowser, appInd.getAttribute(oBrowser, By.id("person_country_canada"), "value"), "No"), "Mis-match in the 'Is The Link Revoked' values");



			//---------------------- verify [ Void Full Amount Link ] ---------------------------
			String remainingAmt = appInd.getTextOnElement(oBrowser, By.xpath("//form[@id='void_request_form']//div[@class='input-miller']"), "text").replace(",","");
			remainingAmt = remainingAmt.replace("$","");
			Assert.assertTrue(appInd.compareValues(oBrowser, String.valueOf("$"+Double.parseDouble(remainingAmt)), remainingAmount));
			double morethanRemaining = Double.parseDouble(remainingAmt) + 0.1;
			Assert.assertTrue(appInd.compareValues(oBrowser, String.valueOf(Double.parseDouble(remainingAmt)), appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_AmountToVoid_Edit, "value")));
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_AmountToVoid_Edit, String.valueOf(morethanRemaining)));
			oBrowser.findElement(PayCRM_Modules_ManagedPaymentsUIPage.obj_AmountToVoid_Edit).sendKeys(Keys.TAB);
			Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.id("void_request_amount-error"), "text").equalsIgnoreCase("Value cannot exceed Amount Remaining"));
			Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidFullAmount_Btn));
			Assert.assertTrue(appInd.compareValues(oBrowser, String.valueOf(Double.parseDouble(remainingAmt)), appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_AmountToVoid_Edit, "value")));


			//Without entering any data click on Submit
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_AmountToVoid_Edit, ""));
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidRequest_Dialog + "//input[@value='Submit']")));
			Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_AmountToVoid_Error_Label, "Text", "Please enter Amount to Void"));
			Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ActionToPerformAfterVoid_Error_Label, "Text", "Please choose an option"));

			//Action to Perform After Void select Others. Notes field should become mandatory field
			Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ActionToPerformAfterVoid_List, "Other"));
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidRequest_Dialog + "//input[@value='Submit']")));
			Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidRequest_Notes_Error_Label, "Text", "Please enter Notes"));


			//Enter Valid details and click on submit. Payment Void page should be created
			//Activity should be created in both Payment detail page and Payment Void page
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_AmountToVoid_Edit, remainingAmt));
			Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_ActionToPerformAfterVoid_List, voidRequest.get("ActionAfterVoid")));
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("//input[@name='void_request_link_action' and @value='"+voidRequest.get("LinkRevoked")+"']")));
			Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidRequest_Notes_Textarea, voidRequest.get("Notes")));
			reports.writeResult(oBrowser, "Screenshot", "Before creating the Void Request");
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidRequest_Dialog + "//input[@value='Submit']")));
			appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_VoidPaymentDetails_Grid + "//h3[contains(text(), 'Void Request Details')]"), "Clickable", "", waitTimeOut);
			reports.writeResult(oBrowser, "Screenshot", "After creating the Void Request");
			String caseNumber = (oBrowser.getCurrentUrl().split("/"))[5];
			reports.writeResult(oBrowser, "Screenshot", "Case Should '"+caseNumber+"' is created and application is redirected to new case page: URL is: " + oBrowser.getCurrentUrl());
			reports.writeResult(oBrowser, "Pass", "Case Should '"+caseNumber+"' is created and application is redirected to new case page");

			//Verify the entry in the Activities tab of "Payment Void" Page
			Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//input)[5]"), voidRequest.get("Notes")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'dx-row-lines dx-column-lines')]/td[1]"), "Text", "User Action - Void Requested"));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'dx-row-lines dx-column-lines')]/td[2]"), "Text", appInd.getPropertyValueByKeyName("qaUserName")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'dx-row-lines dx-column-lines')]/td[3]"), "Text", appInd.formatDate(appInd.getDateTime("MM/dd/yyyy"), "MM/dd/yyyy", "MM/dd/yyyy")));
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Activities_Grid + "//tr[contains(@class, 'dx-row-lines dx-column-lines')]/td[4]"), "Text", voidRequest.get("Notes")));
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'validateVoidRequestModalFunctionality()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateVoidRequestModalFunctionality()' method. " + e);
			return false;
		}finally {oActionAfterVoid = null; arrActionAfterVoid = null;}
	}





	/********************************************************
	 * Method Name      : navigateToUserSettingsAndSearchUser()
	 * Purpose          : User navigate to User Settings-->Search user and validate user is opened successful
	 * Author           : Gudi
	 * Parameters       : oBrowser, userName
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean navigateToUserSettingsAndSearchUser(WebDriver oBrowser, String userName){
		boolean blnExist = false;
		try{
			blnExist = appInd.verifyOptionalElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Users_Profile + "//li[text()='"+userName+"']"));
			if(!blnExist){
				Assert.assertTrue(appDep.selectManageModules(oBrowser, "User Console"));

				//Search the user and add/remove the permission for 'Partner Enrollment
				Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+ PartnerEnrollmentsUIPage.obj_UserConsole_Grid + "//input)[1]"), userName));
				appDep.threadSleep(2000);
				Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PartnerEnrollmentsUIPage.obj_UserConsole_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[1]")));
				appDep.waitForTheElementState(oBrowser, By.xpath(PartnerEnrollmentsUIPage.obj_UserRoles_Section + "//ul[@id='user_roles']/li"), "Clickable", "", minTimeOut);
				Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Users_Profile + "//li[text()='"+userName+"']"), "Text", userName));
				reports.writeResult(oBrowser, "Pass", "User '"+userName+"' was searched and opened successful");
			}else{
				reports.writeResult(oBrowser, "Pass", "User '"+userName+"' was already searched and opened");
			}
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'navigateToUserSettingsAndSearchUser()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'navigateToUserSettingsAndSearchUser()' method. " + e);
			return false;
		}
	}

	/********************************************************
	 * Method Name      : runPollForTheSupportCase()
	 * Purpose          : This method helps to run polling for support case
	 * Author           : Shidd
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean runPollForTheSupportCase(WebDriver oBrowser) {
		try {
			appDep.threadSleep(60000);
			Assert.assertTrue(enrollmentManagersUIBaseSteps.selectOptionsFromDevMenu(oBrowser, "Support Case Polling"));
			appDep.threadSleep(2000);
			Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_RunAPIPoll_Btn));
			appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCasePolling_Grid + "//td[1]"), "Visibility", "", waitTimeOut);
			appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
			reports.writeResult(oBrowser, "Screenshot", "'Support Case Polling' page opened successful");
			return true;
		} catch (Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'runPollForTheSupportCase()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'runPollForTheSupportCase()' method. " + e);
			return false;
		}
	}



	/*****************************************************************
	 * Method Name      : validateGoogleIconPresence()
	 * Purpose          : This method helps us to validate google icon.
	 * Author           : Shidd
	 * Parameters       : oBrowser, caseType
	 * ReturnType       : boolean
	 *****************************************************************/
	public boolean validateGoogleIconPresence(WebDriver oBrowser, String caseType) {
		String supplierName;
		WebElement element;
		try {
			if (caseType.equalsIgnoreCase("EnrollmentCase")) {
				element = oBrowser.findElement(By.xpath(String.format(EnrollmentsManagerUIPage.obj_enrollmentCaseSupplierSectionTile, "Name") + "/span[@class='truncate-title']/a"));
				supplierName = element.getAttribute("title");
				Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(String.format(EnrollmentsManagerUIPage.obj_enrollmentCaseSupplierSectionTile, "Phone") + "//i[@class='fab fa-google']")));
			} else {
				supplierName = appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_SupplierInfoTile, "1", "Name", "1")), "text");
				Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_SupplierInfoTile, "1", "Name", "1") + "//i[@class='fab fa-google']")));
			}
			oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
			Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format("//textarea[text()='%s']", supplierName)), "text"), supplierName);
			oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'validateGoogleIconPresence()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateGoogleIconPresence()' method. " + e);
			return false;
		}finally {
			supplierName = null; element = null;
		}
	}




	/*****************************************************************
	 * Method Name      : waitForMunchStatusToCompleted()
	 * Purpose          : This method helps us to waits and validates the status to get completed after munch
	 * Author           : Gudi
	 * Parameters       : oBrowser, importFileType, iterationCount
	 * ReturnType       : boolean
	 *****************************************************************/
	public boolean waitForMunchStatusToCompleted(WebDriver oBrowser, String importFileType, int iterationCount){
		String munchStatus = null;
		int count = 0;
		try{
			while(true){
				munchStatus = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierFileImport_Grid +"//tr[contains(@class, 'row-lines dx-column-lines')][1]/td[5]"), "Text");
				if(!munchStatus.equalsIgnoreCase("Completed")){
					Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierFileImport_Grid +"//*[@class='dx-icon dx-icon-refresh']")));
					appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
				}else{
					break;
				}
				count++;
				if(count == iterationCount){
					reports.writeResult(oBrowser, "Fail", "Failed to get the "+importFileType+" imported file status to COMPLETED after the munch");
					Assert.fail("Failed to get the "+importFileType+" imported file status to COMPLETED after the munch");
					break;
				}
			}
			Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SupplierFileImport_Grid +"//tr[contains(@class, 'row-lines dx-column-lines')][1]/td[5]"), "Text", "Completed"));
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'waitForMunchStatusToCompleted()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'waitForMunchStatusToCompleted()' method. " + e);
			return false;
		}finally{munchStatus = null;}
	}
}
