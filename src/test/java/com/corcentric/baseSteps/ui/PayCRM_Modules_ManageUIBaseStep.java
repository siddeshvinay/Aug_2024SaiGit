package com.corcentric.baseSteps.ui;

import com.corcentric.pages.*;
import com.corcentric.runner.CucumberTestRunner;
import io.cucumber.datatable.DataTable;
import net.minidev.json.JSONArray;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import java.util.*;

public class PayCRM_Modules_ManageUIBaseStep extends CucumberTestRunner {
    private String newRoleDesc;
    /********************************************************
     * Method Name      : createAndValidateTheRole()
     * Purpose          : to verify that the use can create the new role and validate the same.
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean createAndValidateTheRole(WebDriver oBrowser, DataTable dataTable) {
        List<Map<String, String>> roleData = null;
        Map<String, String> roles = null;
        String timeStamp;
        try {
            roleData = dataTable.asMaps(String.class, String.class);
            timeStamp = appInd.getDateTime("Shhmms");
            roles = new HashMap<String, String>();
            roles.put("RoleName", roleData.get(0).get("RoleName") + timeStamp);
            roles.put("Description", roleData.get(0).get("Description") + timeStamp);
            newRoleDesc = roles.get("Description");

            roles = createNewRole(oBrowser, roles);
            Assert.assertTrue(roles.get("TestPassed").equalsIgnoreCase("True"), "The 'createNewRole()' method was failed.");
            Assert.assertTrue(validateRoleDetails(oBrowser, "New"));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createAndValidateTheRole()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createAndValidateTheRole()' method. " + e);
            return false;
        } finally {
            roleData = null; roles = null; timeStamp = null;
        }
    }



    /********************************************************
     * Method Name      : editAndValidateTheRole()
     * Purpose          : to verify that the use can create the new role and validate the same.
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean editAndValidateTheRole(WebDriver oBrowser) {
        try {
            Assert.assertTrue(editExistingRole(oBrowser));
            Assert.assertTrue(validateRoleDetails(oBrowser, "Edit"));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'editAndValidateTheRole()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'editAndValidateTheRole()' method. " + e);
            return false;
        }
    }



    /********************************************************
     * Method Name      : editExistingRole()
     * Purpose          : to verify that the use can edit the existing role under Modules->Manage->User Console-->Role Manage.
     * Author           : Gudi
     * Parameters       : oBrowser,
     * ReturnType       : boolean
     ********************************************************/
    public boolean editExistingRole(WebDriver oBrowser) {
        try {
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Manage_RoleManager_Permission_Header), "Visibility", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Manage_RoleManager_Permission_Header), "Text").contains(newRoleDesc + " - Permissions"));
            appInd.appInd.scrollToThePage(oBrowser, "Top");
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Before editing the role");
            appInd.scrollToElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Manage_RoleManager_Roles_Label + "/li[text()='" + newRoleDesc + "']/preceding-sibling::li[2]"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Manage_RoleManager_Roles_Label + "/li[text()='" + newRoleDesc + "']"), "Text", newRoleDesc));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Manage_RoleManager_Roles_Label + "/li[text()='" + newRoleDesc + "']/button")));
            appInd.appInd.scrollToThePage(oBrowser, "Top");
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Manage_RoleManager_Permission_Header), "Visibility", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Manage_RoleManager_Permission_Header), "Text").contains(newRoleDesc + " - Permissions"));

            for (int i = 0; i < 10; i++) {
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("(" + PayCRM_Modules_ManageUIPage.obj_Manage_RolePermission_ChkBox + "/li/input)[" + (i + 1) + "]")));
            }

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_Manage_SavePermission_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_Manage_SavePermission_ConfirmationMessage_Label, "Visibility", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManageUIPage.obj_Manage_SavePermission_ConfirmationMessage_Label, "Text").contains("Successfully saved Role"));
            reports.writeResult(oBrowser, "Screenshot", "After editing the role");
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'editExistingRole()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'editExistingRole()' method. " + e);
            return false;
        }
    }



    /********************************************************
     * Method Name      : createUserAndAssignRoleGroupCompanyQueueAndViews()
     * Purpose          : create user and assign role, group, company, queues and Views & validate the same
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean createUserAndAssignRoleGroupCompanyQueueAndViews(WebDriver oBrowser, DataTable dataTable) {
        List<Map<String, String>> userData = null;
        String userEmailId = null;
        String timeStamp;
        String array[];
        try {
            userData = dataTable.asMaps(String.class, String.class);
            timeStamp = appInd.getDateTime("Shhmms");
            userEmailId = userData.get(0).get("Email") + "_" + timeStamp + "@corcentric.com";

            Assert.assertTrue(appDep.selectManageModules(oBrowser, "User Console"));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_Contacts_Add_Link));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_NewUserSetup_GeneratePassword_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_NewUserSetup_CreateUser_Email_Edit, userEmailId));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_NewUserSetup_GeneratePassword_Btn));
            Assert.assertTrue(appInd.setMultipleValues(oBrowser, PayCRM_Modules_ManageUIPage.obj_NewUserSetup_CreateUser_Roles_Edit, PayCRM_Modules_ManageUIPage.obj_NewUserSetup_CreateUser_Groups_SearchResult_Label+"/li", userData.get(0).get("Roles")));

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_NewUserSetup_CreateUser_Groups_Label));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_NewUserSetup_CreateUser_Groups_SearchEdit, userData.get(0).get("Groups")));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_NewUserSetup_CreateUser_Groups_SearchResult_Label + "/li[text()='" + userData.get(0).get("Groups") + "']")));

            Assert.assertTrue(appInd.setMultipleValues(oBrowser, PayCRM_Modules_ManageUIPage.obj_NewUserSetup_CreateUser_AssignToQueue_Edit, PayCRM_Modules_ManageUIPage.obj_NewUserSetup_CreateUser_Groups_SearchResult_Label+"/li", userData.get(0).get("AssignToQueues")));
            Assert.assertTrue(appInd.setMultipleValues(oBrowser, PayCRM_Modules_ManageUIPage.obj_NewUserSetup_CreateUser_IncludeCompanies_Edit, PayCRM_Modules_ManageUIPage.obj_NewUserSetup_CreateUser_Groups_SearchResult_Label+"/li", userData.get(0).get("CompaniesCanView")));
            Assert.assertTrue(appInd.setMultipleValues(oBrowser, PayCRM_Modules_ManageUIPage.obj_NewUserSetup_CreateUser_ExcludeCompanies_Edit, PayCRM_Modules_ManageUIPage.obj_NewUserSetup_CreateUser_Groups_SearchResult_Label+"/li", userData.get(0).get("CompaniesCannotView")));
            Assert.assertTrue(appInd.setMultipleValues(oBrowser, PayCRM_Modules_ManageUIPage.obj_NewUserSetup_CreateUser_AssignViews_Edit, PayCRM_Modules_ManageUIPage.obj_NewUserSetup_RoleManager_AssignToViews_Label+"/li", userData.get(0).get("AssignToViews")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_NewUserSetup_CreateUser_CreateUser_Btn, "Clickable", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Creating the new user");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_NewUserSetup_CreateUser_CreateUser_Btn));
            appDep.threadSleep(4000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_NewUserSetup_CreateUser_Gravatar_Img, "Clickable", "", maxWaitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManageUIPage.obj_Manage_SavePermission_ConfirmationMessage_Label, "Text").contains("User was successfully created"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_NewUserSetUp_Settings_Profile + "//ul/li[1]"), "Text", userEmailId));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_NewUserSetUp_Settings_Profile + "//ul/li[2]"), "Text", "Created on " + appInd.getDateTimeByTimezone("MM/dd/yyyy", "CST")));
            oBrowser.navigate().refresh();
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_NewUserSetup_Settings_Update_Btn, "Clickable", "", maxWaitTimeOut);
            appDep.threadSleep(4000);
            reports.writeResult(oBrowser, "Screenshot", "New user settings-->Profile tab");

            array = userData.get(0).get("Roles").split("#");
            for (int i = 0; i < array.length; i++) {
                Assert.assertTrue(oBrowser.findElement(By.xpath("//input[@value='" + array[i] + "']")).getAttribute("checked").equalsIgnoreCase("True"));
            }

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_NewUserSetUp_Settings_Queues_Tab));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_NewUserSetup_Settings_Queues_Pagination, "Visibility", "", waitTimeOut);
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "New user settings-->Queue tab");
            array = userData.get(0).get("AssignToQueues").split("#");
            for (int i = 0; i < array.length; i++) {
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Companies_Table_ColumnNames + "//td[1][text()='" + array[i] + "']"), "Text", array[i]));
            }

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_NewUserSetup_Settings_Companies_Tab));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_NewUserSetup_Settings_Companies_Pagination, "Visibility", "", waitTimeOut);
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "New user settings-->Companies tab");
            array = userData.get(0).get("CompaniesCanView").split("#");
            for (int i = 0; i < array.length; i++) {
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Companies_Table_ColumnNames + "//td[1][text()='" + array[i] + "']"), "Text", array[i]));
            }

            array = userData.get(0).get("CompaniesCannotView").split("#");
            for (int i = 0; i < array.length; i++) {
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Companies_Table_ColumnNames + "//td[1][text()='" + array[i] + "']/following-sibling::td[3]/div[@aria-checked='true']")));
            }

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_NewUserSetup_Settings_Views_Tab));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_NewUserSetup_Settings_Views_Pagination, "Visibility", "", waitTimeOut);
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "New user settings-->Views tab");
            array = userData.get(0).get("AssignToViews").split("#");
            for (int i = 0; i < array.length; i++) {
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Companies_Table_ColumnNames + "//td[1][text()='" + array[i] + "']"), "Text", array[i]));
            }

            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createUserAndAssignRoleGroupCompanyQueueAndViews()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createUserAndAssignRoleGroupCompanyQueueAndViews()' method. " + e);
            return false;
        } finally {
            userData = null; userEmailId = null; timeStamp = null; array = null;
        }
    }




    /********************************************************
     * Method Name      : validateColumnDataFilterFunctionalityForDuplicateSupplier()
     * Purpose          : user validate filter functionality for 'Duplicate Supplier Management' grid data column values
     * Author           : Gudi
     * Parameters       : oBrowser, duplicateSupplierTabName, queryKey
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateColumnDataFilterFunctionalityForDuplicateSupplier(WebDriver oBrowser, String duplicateSupplierTabName, String queryKey) {
        int index1 = 0;
        int index2 = 0;
        String columnName = null;
        String clientName = "";
        String clientNames = "";
        int rowNum = 0;
        String cellData = null;
        boolean dataFilter = false;
        List<WebElement> oEles = null;
        JSONArray caseDetails = null;
        try {
            caseDetails = apiDataProvider.getAPIDataProviderResponse(queryKey, new Object[] {});

            if(((JSONArray) caseDetails.get(0)).size() == 0){
                reports.writeResult(oBrowser, "Warning", "No records were retrieved from the '" + System.getProperty("environment") + "' DB, \n Hence exiting the 'validateColumnDataFilterFunctionalityForDuplicateSupplier() method'");
                return true;
            }else{
                reports.writeResult(oBrowser, "Pass", "Records were retrieved from the '" + System.getProperty("environment") + "' DB");
            }

            columnName = "Client Name";
            clientName = ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("company_name").toString();

            if (!appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManageUIPage.obj_MonetizationTracking_Page_Header, "Text").contains("Duplicate Supplier Management"))
                Assert.assertTrue(appDep.selectManageModules(oBrowser, "Duplicate Supplier Management"));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);

            if (duplicateSupplierTabName.equalsIgnoreCase("Completed")) {
                index1 = 2;
                index2 = 1;
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_LoadData_Btn));
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                appDep.threadSleep(4000);
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_ClientNameFilter_Btn + ")["+index1+"]")));
                appDep.threadSleep(2000);
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_FilterOptions_Label + "/div[text()='"+clientName+"']")));
            } else {
                index1 = 1;
                index2 = 2;
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Loaded_Tab_Link));
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
                appDep.threadSleep(2000);
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_ClientNameFilter_Btn + ")["+index1+"]")));
                appDep.threadSleep(2000);
                oEles = oBrowser.findElements(PayCRM_Modules_ManageUIPage.obj_PaymentBasedEnrollments_FilterOptions_Label);
                rowNum = oEles.size();
                for(int i=1; i<=3; i++){
                    clientNames+= oEles.get(i).getText().trim();
                    Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, By.xpath("(" + PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_FilterOptions_Label + "/div[text()='" + oEles.get(i).getText() + "'])[" + index1 + "]")));
                }
            }
            reports.writeResult(oBrowser, "Screenshot", "Applying the filter for 'Client Name' column for '"+duplicateSupplierTabName+"' section");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+PayCRM_Modules_ManageUIPage.obj_Filter_OK_Btn+")["+index2+"]")));
            appDep.threadSleep(4000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);

            int maxRowNum = 15;
            if (duplicateSupplierTabName.equalsIgnoreCase("Loaded")) {
                rowNum = oBrowser.findElements(By.xpath(PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Completed_Grid + "//tr")).size() - 1;
            } else {
                rowNum = oBrowser.findElements(By.xpath(PayCRM_Modules_ManageUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//tr")).size() - 1;
            }
            if (rowNum > maxRowNum) rowNum = maxRowNum;

            for (int i = 0; i < rowNum - 1; i++) {
                dataFilter = true;
                if (duplicateSupplierTabName.equalsIgnoreCase("Loaded")) {
                    cellData = appInd.createAndGetWebElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//tr[" + (i + 1) + "]/td[1]")).getText();
                    Assert.assertTrue(clientNames.contains(cellData), "Mis-match in actual '" + cellData + "' & expected '" + clientName + "' values in the 'Duplicate Supplier Management - " + duplicateSupplierTabName + "' tab");
                } else {
                    cellData = appInd.createAndGetWebElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Completed_Grid + "//tr[" + (i + 1) + "]/td[1]")).getText();
                    Assert.assertTrue(appInd.compareValues(oBrowser, cellData, clientName), "Mis-match in actual '" + cellData + "' & expected '" + clientName + "' values in the 'Duplicate Supplier Management - " + duplicateSupplierTabName + "' tab");
                }
            }

            if (dataFilter == false) {
                reports.writeResult(oBrowser, "Fail", "No records are found for the applied filter for the column '" + columnName + "' and the filter value '" + clientName + "'");
                Assert.assertTrue(false, "No records are found for the applied filter for the column '" + columnName + "' and the filter value '" + clientName + "'");
            }
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateColumnDataFilterFunctionalityForDuplicateSupplier()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateColumnDataFilterFunctionalityForDuplicateSupplier()' method. " + e);
            return false;
        } finally {
            columnName = null; clientName = null; cellData = null; clientNames = null; caseDetails = null;
        }
    }



    /********************************************************
     * Method Name      : validateAllEnrolledReportFilters()
     * Purpose          : user validates the 'All Enrolled' reports filter functionality
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateAllEnrolledReportFilters(WebDriver oBrowser) {
        String startDate = null;
        String endDate = null;
        int rowCount = 0;
        int maxRows = 0;
        String cellData = null;
        String formatedActualDate = null;
        int flag = 0;
        try {
            Assert.assertTrue(appDep.selectManageModules(oBrowser, "Reports"));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Reports page");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_AllEnrolled_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_AllEnrolled_RunReport_Btn, "Clickable", "", waitTimeOut);
            startDate = appInd.addDaysToDate("", "dd-MM-yyyy", -1000);
            endDate = appInd.addDaysToDate("", "dd-MM-yyyy", 0);
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_AllEnroll_startDate_Edit, startDate));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_AllEnroll_endDate_Edit, endDate));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_AllEnrolled_RunReport_Btn));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "'All Enrolled' reports page");
            maxRows = 11;
            rowCount = oBrowser.findElements(By.xpath(PayCRM_Modules_ManageUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//tr/td[contains(@aria-label, 'Date Enrolled, Value')]")).size();
            if (rowCount > 11) rowCount = maxRows;
            Assert.assertTrue(rowCount > 0, "Failed to find the records form the 'All Enrolled' report");
            flag = 1;
            for (int i = 0; i < rowCount; i++) {
                flag++;
                cellData = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//tr/td[contains(@aria-label, 'Date Enrolled, Value')]"), "Text");
                formatedActualDate = appInd.formatDateFromOnetoAnother(cellData, "MM/dd/yyyy", "dd-MM-yyyy");
                Assert.assertTrue(appDep.compareDateRangeWithActualDate(startDate, endDate, formatedActualDate));
            }

            if (flag == 0)
                Assert.assertTrue(false, "No records were found when applied filter for the 'All Enrolled' report");
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateAllEnrolledReportFilters()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateAllEnrolledReportFilters()' method. " + e);
            return false;
        } finally {
            startDate = null; endDate = null; cellData = null; formatedActualDate = null;
        }
    }



    /********************************************************
     * Method Name      : validateActivityReportFilters()
     * Purpose          : user validates the 'Activity Report' reports filter functionality
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateActivityReportFilters(WebDriver oBrowser) {
        String startDate = null;
        String endDate = null;
        int rowCount = 0;
        int maxRows = 0;
        String cellData = null;
        String formatedActualDate = null;
        int flag = 0;
        WebElement casesParentTab;
        try {
            casesParentTab = oBrowser.findElement(PayCRM_Modules_ManageUIPage.obj_casesParentSection);
            if (casesParentTab.getAttribute("aria-expanded").equalsIgnoreCase("false")) {
                Assert.assertTrue(appInd.clickObject(oBrowser, casesParentTab));
            }
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_ActivityReport_Link, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_ActivityReport_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_ActivityReports_RunReport_Btn, "Clickable", "", waitTimeOut);
            startDate = appInd.addDaysToDate("", "dd-MM-yyyy", -500);
            endDate = appInd.addDaysToDate("", "dd-MM-yyyy", 0);
            //Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_ActivityReport_Company_List, "Accelerate360, LLC"));
            //Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_NewUserSetup_CreateUser_Groups_SearchResult_Label + "/li[text()='Accelerate360, LLC']")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_ActivityReport_StartDate_Edit, startDate));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_ActivityReport_EndDate_Edit, endDate));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_ActivityReports_RunReport_Btn));
            appDep.threadSleep(3000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.threadSleep(3000);
            reports.writeResult(oBrowser, "Screenshot", "'Activity Report' reports page");
            maxRows = 11;
            rowCount = oBrowser.findElements(By.xpath(PayCRM_Modules_ManageUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//tr/td[contains(@aria-label, 'Company, Value')]")).size();
            if (rowCount > 11) rowCount = maxRows;
            for (int i = 0; i < rowCount; i++) {
                flag++;
                //cellData = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//tr/td[contains(@aria-label, 'Company, Value')]"), "Text");
                //Assert.assertTrue(appInd.compareValues(oBrowser, cellData, "Accelerate360, LLC"));
                cellData = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//tr/td[contains(@aria-label, 'Date, Value')]"), "Text");
                formatedActualDate = appInd.formatDateFromOnetoAnother(cellData, "MM/dd/yyyy", "dd-MM-yyyy");
                Assert.assertTrue(appDep.compareDateRangeWithActualDate(startDate, endDate, formatedActualDate));
            }
            if (flag == 0)
                Assert.assertTrue(false, "No records were found when applied filter for the 'Activity Report' report");
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateActivityReportFilters()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateActivityReportFilters()' method. " + e);
            return false;
        } finally {
            startDate = null; endDate = null; cellData = null; formatedActualDate = null; casesParentTab = null;
        }
    }



    /********************************************************
     * Method Name      : validateAgentActivityReportFilters()
     * Purpose          : user validates the 'Agent Activity' reports filter functionality
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateAgentActivityReportFilters(WebDriver oBrowser) {
        String startDate = null;
        String endDate = null;
        int rowCount = 0;
        int maxRows = 0;
        String cellData = null;
        String formatedActualDate = null;
        String emailID = null;
        int flag = 0;
        WebElement casesParentTab;
        try {
            casesParentTab = oBrowser.findElement(PayCRM_Modules_ManageUIPage.obj_casesParentSection);
            if (casesParentTab.getAttribute("aria-expanded").equalsIgnoreCase("false")) {
                Assert.assertTrue(appInd.clickObject(oBrowser, casesParentTab));
            }
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_AgentActivity_Link, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_AgentActivity_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_AgentActivity_RunReport_Btn, "Clickable", "", waitTimeOut);
            startDate = appInd.addDaysToDate("", "dd-MM-yyyy", -1000);
            endDate = appInd.addDaysToDate("", "dd-MM-yyyy", 0);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_AgentActivity_Email_Label));
            emailID = appInd.getPropertyValueByKeyName("qaUserName");
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_AgentActivity_Email_Edit, emailID));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_NewUserSetup_CreateUser_Groups_SearchResult_Label + "/li[text()='" + emailID + "']")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_AgentActivity_StartDate_Edit, startDate));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_AgentActivity_EndDate_Edit, endDate));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_AgentActivity_RunReport_Btn));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.threadSleep(3000);
            reports.writeResult(oBrowser, "Screenshot", "'Agent Activity' reports page");
            maxRows = 11;
            rowCount = oBrowser.findElements(By.xpath(PayCRM_Modules_ManageUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//tr/td[contains(@aria-label, 'Created At, Value')]")).size();
            if (rowCount > 11) rowCount = maxRows;
            for (int i = 0; i < rowCount; i++) {
                flag++;
                cellData = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.objAgentActivity_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[contains(@aria-label, 'Team Member, Value')]"), "Text");
                Assert.assertTrue(appInd.compareValues(oBrowser, cellData, emailID));
                cellData = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.objAgentActivity_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[contains(@aria-label, 'Created At, Value')]"), "Text");
                formatedActualDate = appInd.formatDateFromOnetoAnother(cellData, "MM/dd/yyyy", "dd-MM-yyyy");
                Assert.assertTrue(appDep.compareDateRangeWithActualDate(startDate, endDate, formatedActualDate));
            }
            if (flag == 0)
                Assert.assertTrue(false, "No records were found when applied filter for the 'Agent Activity' report");
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateAgentActivityReportFilters()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateAgentActivityReportFilters()' method. " + e);
            return false;
        } finally {
            startDate = null; endDate = null; cellData = null; formatedActualDate = null; emailID = null; casesParentTab = null;
        }
    }


    /********************************************************
     * Method Name      : validateSupportCasesAgentActivityReport()
     * Purpose          : user validates the 'Agent Activity' reports along with filter functionality for Support Case
     * Author           : Shidd
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateSupportCasesAgentActivityReport(WebDriver oBrowser) {
        String startDate = null;
        String endDate = null;
        String cellData = null;
        String formatedActualDate = null;
        String emailID = null;
        int rowCount = 0;
        int maxRows = 0;
        int flag = 0;
        WebElement supportCaseParentTab = null;
        try {
            supportCaseParentTab = oBrowser.findElement(PayCRM_Modules_ManageUIPage.obj_supportCaseParentSection);
            if (supportCaseParentTab.getAttribute("aria-expanded").equalsIgnoreCase("false")) {
                Assert.assertTrue(appInd.clickObject(oBrowser, supportCaseParentTab));
            }
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_SupportCase_agentActivity_Link, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_SupportCase_agentActivity_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_supportCaseAgentActivity_RunReportBtn, "Clickable", "", waitTimeOut);
            startDate = appInd.addDaysToDate("", "dd-MM-yyyy", -1000);
            endDate = appInd.addDaysToDate("", "dd-MM-yyyy", 0);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_SupportCase_AgentActivityEmailLabel));
            emailID = appInd.getPropertyValueByKeyName("qaUserName");
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_AgentActivity_Email_Edit, emailID));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_NewUserSetup_CreateUser_Groups_SearchResult_Label + "/li[text()='" + emailID + "']")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_supportCaseAgentActivityStartDate, startDate));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_supportCaseAgentActivityEndDate, endDate));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_supportCaseAgentActivity_RunReportBtn));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.threadSleep(3000);
            reports.writeResult(oBrowser, "Screenshot", "'Support Case Agent Activity' reports page");
            maxRows = 11;
            rowCount = oBrowser.findElements(By.xpath(PayCRM_Modules_ManageUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//tr/td[contains(@aria-label, 'Created At, Value')]")).size();
            if (rowCount > 11) rowCount = maxRows;
            for (int i = 0; i < rowCount; i++) {
                flag++;
                cellData = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_SupportCaseAgentActivityGrid + "//tr[contains(@class, 'lines dx-column-lines')]/td[contains(@aria-label, 'Team Member, Value')]"), "Text");
                Assert.assertTrue(appInd.compareValues(oBrowser, cellData, emailID));
                cellData = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_SupportCaseAgentActivityGrid + "//tr[contains(@class, 'lines dx-column-lines')]/td[contains(@aria-label, 'Created At, Value')]"), "Text");
                formatedActualDate = appInd.formatDateFromOnetoAnother(cellData, "MM/dd/yyyy", "dd-MM-yyyy");
                Assert.assertTrue(appDep.compareDateRangeWithActualDate(startDate, endDate, formatedActualDate));
            }
            if (flag == 0) {
                reports.writeResult(oBrowser, "Screenshot", "'Support Case Agent Activity' reports page for no results.");
                Assert.assertTrue(false, "No records were found when applied filter for the 'Agent Activity' report");
            }
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateSupportCasesAgentActivityReport()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateSupportCasesAgentActivityReport()' method. " + e);
            return false;
        } finally {
            startDate = null; endDate = null; cellData = null; formatedActualDate = null; emailID = null; supportCaseParentTab = null;
        }
    }


    /********************************************************
     * Method Name      : validateBillPayReportFilters()
     * Purpose          : user validates the 'Bill Pay Report' reports filter functionality
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateBillPayReportFilters(WebDriver oBrowser) {
        String startDate = null;
        String endDate = null;
        int rowCount = 0;
        int maxRows = 0;
        String cellData = null;
        String formatedActualDate = null;
        int flag = 0;
        WebElement casesParentTab;
        try {
            casesParentTab = oBrowser.findElement(PayCRM_Modules_ManageUIPage.obj_casesParentSection);
            if (casesParentTab.getAttribute("aria-expanded").equalsIgnoreCase("false")) {
                Assert.assertTrue(appInd.clickObject(oBrowser, casesParentTab));
            }
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_BillPayReport_Link, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_BillPayReport_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_BillPayReport_RunReport_Btn, "Clickable", "", waitTimeOut);
            startDate = appInd.addDaysToDate("", "dd-MM-yyyy", -1000);
            endDate = appInd.addDaysToDate("", "dd-MM-yyyy", 0);
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_BillPayReport_StartDate_Edit, startDate));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_BillPayReport_EndDate_Edit, endDate));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_BillPayReport_RunReport_Btn));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.threadSleep(3000);
            reports.writeResult(oBrowser, "Screenshot", "'Bill Pay Report' reports page");
            maxRows = 11;
            rowCount = oBrowser.findElements(By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Report_BillPayReport + "//table//tr/td[contains(@aria-label, 'Processed Date, Value')]")).size();
            if (rowCount > 11) rowCount = maxRows;
            for (int i = 0; i < rowCount; i++) {
                flag++;
                cellData = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManagedPaymentsUIPage.obj_Report_BillPayReport + "//table//tr["+(i+1)+"]/td[contains(@aria-label, 'Processed Date, Value')]"), "Text");
                formatedActualDate = appInd.formatDateFromOnetoAnother(cellData, "yyyy-MM-dd", "dd-MM-yyyy");
                Assert.assertTrue(appDep.compareDateRangeWithActualDate(startDate, endDate, formatedActualDate));
            }
            if (flag == 0)
                Assert.assertTrue(false, "No records were found when applied filter for the 'Bill Pay Report' report");
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateBillPayReportFilters()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateBillPayReportFilters()' method. " + e);
            return false;
        } finally {
            startDate = null; endDate = null; cellData = null; formatedActualDate = null; casesParentTab = null;
        }
    }



    /********************************************************
     * Method Name      : validateCaseOutcomesReportFilters()
     * Purpose          : user validates the 'Case Outcomes Report' reports filter functionality
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateCaseOutcomesReportFilters(WebDriver oBrowser) {
        int rowCount, maxRows, flag = 0;
        String cellData,startDate;
        WebElement casesParentTab;
        try {
            casesParentTab = oBrowser.findElement(PayCRM_Modules_ManageUIPage.obj_casesParentSection);
            if (casesParentTab.getAttribute("aria-expanded").equalsIgnoreCase("false")) {
                Assert.assertTrue(appInd.clickObject(oBrowser, casesParentTab));
            }
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_CaseOutcomeReport_Link, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_CaseOutcomeReport_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_CaseOutcomesReport_RunReport_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_CaseOutcomesReport_CaseOutcome_Edit, "Call Transfer"));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_NewUserSetup_CreateUser_Groups_SearchResult_Label + "/li[text()='Call Transfer']")));
            startDate = appInd.addDaysToDate("", "dd-MM-yyyy", -1000);
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_startDateActivityFilterCaseOutcomeStartDate, startDate));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_CaseOutcomesReport_RunReport_Btn));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.threadSleep(3000);
            reports.writeResult(oBrowser, "Screenshot", "'Case Outcomes Report' reports page");
            maxRows = 11;
            rowCount = oBrowser.findElements(By.xpath(PayCRM_Modules_ManageUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//tr/td[contains(@aria-label, 'Case Outcome, Value')]")).size();
            if (rowCount > 11) rowCount = maxRows;
            for (int i = 0; i < rowCount; i++) {
                flag++;
                cellData = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//tr/td[contains(@aria-label, 'Case Outcome, Value')]"), "Text");
                Assert.assertTrue(appInd.compareValues(oBrowser, cellData, "Call Transfer"));
            }
            if (flag == 0)
                Assert.assertTrue(false, "No records were found when applied filter for the 'Case Outcomes Report' report");
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateCaseOutcomesReportFilters()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateCaseOutcomesReportFilters()' method. " + e);
            return false;
        } finally {
            cellData = null; casesParentTab = null;
        }
    }

    /********************************************************
     * Method Name      : validateSupportCasesOutcomesReport()
     * Purpose          : user validates the 'Support Case Outcomes Report' page along with filter functionality
     * Author           : Shidd
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    private boolean validateSupportCasesOutcomesReport(WebDriver oBrowser) {
        int rowCount, maxRows, flag = 0;
        String cellData, startDate;
        WebElement supportCaseParentTab;
        try {
            supportCaseParentTab = oBrowser.findElement(PayCRM_Modules_ManageUIPage.obj_supportCaseParentSection);
            if (supportCaseParentTab.getAttribute("aria-expanded").equalsIgnoreCase("false")) {
                Assert.assertTrue(appInd.clickObject(oBrowser, supportCaseParentTab));
            }
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_supportCase_outcomesReport_Link, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_supportCase_outcomesReport_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_supportCase_outcomeReport_runReportButton, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_supportCaseEmail, "Assisted with update"));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_NewUserSetup_CreateUser_Groups_SearchResult_Label + "/li[text()='Assisted with update']")));
            startDate = appInd.addDaysToDate("", "dd-MM-yyyy", -1000);
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_supportCaseOutcomeReportLastActivityStartDate, startDate));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_supportCase_outcomeReport_runReportButton));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.threadSleep(3000);
            reports.writeResult(oBrowser, "Screenshot", "'Support Case Outcomes report' page");
            maxRows = 11;
            rowCount = oBrowser.findElements(By.xpath(PayCRM_Modules_ManageUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//tr/td[contains(@aria-label, 'Case Outcome, Value')]")).size();
            if (rowCount > 11) rowCount = maxRows;
            for (int i = 0; i < rowCount; i++) {
                flag++;
                cellData = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//tr/td[contains(@aria-label, 'Case Outcome, Value')]"), "Text");
                Assert.assertTrue(appInd.compareValues(oBrowser, cellData, "Assisted with update"));
            }
            if (flag == 0) {
                reports.writeResult(oBrowser, "Screenshot", "'Support Case Outcomes report' page for no results.");
                Assert.assertTrue(false, "No records were found when applied filter for the 'Support Case Outcomes Report' report");
            }
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateSupportCasesOutcomesReport()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateSupportCasesOutcomesReport()' method. " + e);
            return false;
        } finally {
            cellData = null; supportCaseParentTab = null;
        }
    }

    /********************************************************
     * Method Name      : validateHighPrioritySuppliersReportFilters()
     * Purpose          : user validates the 'High Priority Suppliers' reports filter functionality
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateHighPrioritySuppliersReportFilters(WebDriver oBrowser) {
        int rowCount = 0;
        String columnNames[];
        WebElement supplierParentTab;
        try {
            supplierParentTab = oBrowser.findElement(PayCRM_Modules_ManageUIPage.obj_suppliersParentSection);
            if (supplierParentTab.getAttribute("aria-expanded").equalsIgnoreCase("false")) {
                Assert.assertTrue(appInd.clickObject(oBrowser, supplierParentTab));
            }
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_HighPrioritySuppliers_Link, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_HighPrioritySuppliers_Link));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_HighPrioritySuppliers_RunReport_btn));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.threadSleep(3000);
            reports.writeResult(oBrowser, "Screenshot", "'High Priority Suppliers' reports page");
            columnNames = "Supplier#Tax ID#PayNet Company ID#Supplier Type#Status".split("#");
            for (int i = 0; i < columnNames.length; i++) {
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("(//div[@id='divHighPrioritySuppliersReportContainer']//table)[1]//td["+(i+1)+"]/div[text()='"+columnNames[i]+"']")));
            }

            rowCount = oBrowser.findElements(By.xpath(PayCRM_Modules_ManageUIPage.obj_HighPrioritySuppliers_Grid + "//div[contains(@class, 'dx-datagrid-rowsview')]//table//tr")).size();
            if (rowCount > 0)
                reports.writeResult(oBrowser, "Pass", "The 'High Priority Suppliers' report filter working successful");
            if (rowCount == 0)
                Assert.assertTrue(false, "No records were found when applied filter for the 'High Priority Suppliers' report");
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateHighPrioritySuppliersReportFilters()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateHighPrioritySuppliersReportFilters()' method. " + e);
            return false;
        } finally {columnNames = null; supplierParentTab = null;}
    }



    /********************************************************
     * Method Name      : validateFirstTimePaymentsReportFilters()
     * Purpose          : user validates the 'First Time Payments' reports filter functionality
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateFirstTimePaymentsReportFilters(WebDriver oBrowser) {
        String startDate = null;
        String endDate = null;
        int rowCount = 0;
        int maxRows = 0;
        String cellData = null;
        String formatedActualDate = null;
        int flag = 0;
        try {
            Assert.assertTrue(appDep.selectManageModules(oBrowser, "Reports"));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_FirstTimePayments_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_FirstTimePayments_RunReport_Btn, "Clickable", "", waitTimeOut);
            startDate = appInd.addDaysToDate("", "dd-MM-yyyy", -1000);
            endDate = appInd.addDaysToDate("", "dd-MM-yyyy", 0);
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_FirstTimePayments_StartDate_Edit, startDate));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_FirstTimePayments_EndDate_Edit, endDate));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_FirstTimePayments_RunReport_Btn));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.threadSleep(3000);
            reports.writeResult(oBrowser, "Screenshot", "'First Time Payments' reports page");
            maxRows = 11;
            rowCount = oBrowser.findElements(By.xpath(PayCRM_Modules_ManageUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//tr/td[contains(@aria-label, 'Case Created, Value')]")).size();
            if (rowCount > 11) rowCount = maxRows;
            for (int i = 0; i < rowCount; i++) {
                flag++;
                cellData = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//tr/td[contains(@aria-label, 'Case Created, Value')]"), "Text");
                formatedActualDate = appInd.formatDateFromOnetoAnother(cellData, "MM/dd/yyyy", "dd-MM-yyyy");
                Assert.assertTrue(appDep.compareDateRangeWithActualDate(startDate, endDate, formatedActualDate));
            }

            if (flag == 0)
                Assert.assertTrue(false, "No records were found when applied filter for the 'First Time Payments' report");
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateFirstTimePaymentsReportFilters()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateFirstTimePaymentsReportFilters()' method. " + e);
            return false;
        } finally {
            startDate = null; endDate = null; cellData = null; formatedActualDate = null;
        }
    }



    /********************************************************
     * Method Name      : validateEnrollmentsByTeamMemberReportFilters()
     * Purpose          : user validates the 'Enrollments By Team Member' reports filter functionality
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateEnrollmentsByTeamMemberReportFilters(WebDriver oBrowser) {
        int rowCount = 0;
        String columnNames[];
        WebElement enrollmentParentTab;
        try {
            enrollmentParentTab = oBrowser.findElement(PayCRM_Modules_ManageUIPage.obj_enrollmentsParentSection);
            if (enrollmentParentTab.getAttribute("aria-expanded").equalsIgnoreCase("false")) {
                Assert.assertTrue(appInd.clickObject(oBrowser, enrollmentParentTab));
            }
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_EnrollmentsByTeamMember_Link, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_EnrollmentsByTeamMember_Link));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_EnrollmentsByTeamMembers_RunReport_Btn));
            appDep.threadSleep(2000);
            columnNames = "Case #,Supplier,Company,Team Member,Work Queue,Date Inrolled,Total Spend,Average Monthly Spend".split(",");
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.threadSleep(3000);
            reports.writeResult(oBrowser, "Screenshot", "'Enrollments By Team Member' reports page");
            for (int i = 0; i < columnNames.length; i++) {
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("(//div[@id='divEnrollmentsByTeamMemberContainer']//table)[1]//td["+(i+1)+"]/div[text()='"+columnNames[i]+"']")));
            }

            rowCount = oBrowser.findElements(By.xpath(PayCRM_Modules_ManageUIPage.obj_EnrollmentsByTeamMembers_Grid + "//div[contains(@class, 'dx-datagrid-rowsview')]//table//tr")).size();
            if (rowCount > 0)
                reports.writeResult(oBrowser, "Pass", "The 'Enrollments By Team Member' report filter working successful");
            if (rowCount == 0)
                Assert.assertTrue(false, "No records were found when applied filter for the 'Enrollments By Team Member' report");
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateEnrollmentsByTeamMemberReportFilters()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateEnrollmentsByTeamMemberReportFilters()' method. " + e);
            return false;
        } finally {columnNames = null; enrollmentParentTab = null;}
    }




    /********************************************************
     * Method Name      : validateEnrollmentReportFilters()
     * Purpose          : user validates the 'Enrollment Report' reports filter functionality
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateEnrollmentReportFilters(WebDriver oBrowser) {
        String startDate = null;
        String endDate = null;
        int rowCount = 0;
        int maxRows = 0;
        String cellData = null;
        int flag = 0;
        try {
            Assert.assertTrue(appDep.selectManageModules(oBrowser, "Reports"));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_EnrollmentReport_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_EnrollmentReport_RunReport_Btn, "Clickable", "", waitTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            startDate = appInd.addDaysToDate("", "dd-MM-yyyy", -1000);
            endDate = appInd.addDaysToDate("", "dd-MM-yyyy", 0);
//            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_EnrollmentReport_Company_Edit, "Accelerate360, LLC"));
//            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_NewUserSetup_CreateUser_Groups_SearchResult_Label + "/li[text()='Accelerate360, LLC']")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_EnrollmentReport_StartDate_Edit, startDate));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_EnrollmentReport_EndDate_Edit, endDate));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_EnrollmentReport_RunReport_Btn));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.threadSleep(3000);
            reports.writeResult(oBrowser, "Screenshot", "'Enrollment Report' reports page");
            maxRows = 11;
            rowCount = oBrowser.findElements(By.xpath(PayCRM_Modules_ManageUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//tr/td[contains(@aria-label, 'Company, Value')]")).size();
            if (rowCount > 11) rowCount = maxRows;
            for (int i = 0; i < rowCount; i++) {
                flag++;
                cellData = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//tr/td[contains(@aria-label, 'Outcome, Value Enrolled')]"), "Text");
                Assert.assertTrue(appInd.compareValues(oBrowser, cellData, "Enrolled"));
            }
            if (flag == 0)
                Assert.assertTrue(false, "No records were found when applied filter for the 'Enrollment Report' report");
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateEnrollmentReportFilters()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateEnrollmentReportFilters()' method. " + e);
            return false;
        } finally {
            startDate = null; endDate = null; cellData = null;
        }
    }



    /********************************************************
     * Method Name      : validateMultiSwipeReportFilters()
     * Purpose          : user validates the 'Multi Swipe' reports filter functionality
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateMultiSwipeReportFilters(WebDriver oBrowser) {
        int rowCount = 0;
        String columnNames[];
        WebElement supplierParentTab;
        try {
            supplierParentTab = oBrowser.findElement(PayCRM_Modules_ManageUIPage.obj_suppliersParentSection);
            if (supplierParentTab.getAttribute("aria-expanded").equalsIgnoreCase("false")) {
                Assert.assertTrue(appInd.clickObject(oBrowser, supplierParentTab));
            }
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_MultiSwipe_Link, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_MultiSwipe_Link));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_MultiSwipe_RunReport_Btn));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.threadSleep(3000);
            reports.writeResult(oBrowser, "Screenshot", "'Multi Swipe' reports page");
            columnNames = "Supplier ID#Name#TIN#PayNet Company ID#Supplier Type#Status#Priority".split("#");
            for (int i = 0; i < columnNames.length; i++) {
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("(//div[@id='divMultiSwipeContainer']//table)[1]//td["+(i+1)+"]/div[text()='"+columnNames[i]+"']")));
            }

            rowCount = oBrowser.findElements(By.xpath(PayCRM_Modules_ManageUIPage.obj_MultiSwipe_Grid + "//div[contains(@class, 'dx-datagrid-rowsview')]//table//tr")).size();
            if (rowCount > 0)
                reports.writeResult(oBrowser, "Pass", "The 'Multi Swipe' report filter working successful");
            if (rowCount == 0)
                Assert.assertTrue(false, "No records were found when applied filter for the 'Multi Swipe' report");
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateMultiSwipeReportFilters()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateMultiSwipeReportFilters()' method. " + e);
            return false;
        } finally {columnNames = null; supplierParentTab = null;}
    }

    /********************************************************
     * Method Name      : validateSuppliersActivityReport()
     * Purpose          : user validates the 'Supplier Activity Report' reports filter functionality
     * Author           : Shidd
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateSuppliersActivityReport(WebDriver oBrowser) {
        int rowCount, maxRows, flag = 0;
        WebElement supplierParentTab;
        String startDate, endDate, cellData, formatedActualDate;

        try {
            supplierParentTab = oBrowser.findElement(PayCRM_Modules_ManageUIPage.obj_suppliersParentSection);
            if (supplierParentTab.getAttribute("aria-expanded").equalsIgnoreCase("false")) {
                Assert.assertTrue(appInd.clickObject(oBrowser, supplierParentTab));
            }
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_suppliersActivityReportLink, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_suppliersActivityReportLink));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_suppliersActivityReportRunReportBtn, "Clickable", "", waitTimeOut);

            startDate = appInd.addDaysToDate("", "dd-MM-yyyy", -1000);
            endDate = appInd.addDaysToDate("", "dd-MM-yyyy", 0);
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_supplierActivityOutcomeFilter, "Remittance Inquiry"));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_NewUserSetup_CreateUser_Groups_SearchResult_Label + "/li[text()='Remittance Inquiry']")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_supplierActivityReportStartDate, startDate));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_supplierActivityReportEndDate, endDate));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_suppliersActivityReportRunReportBtn));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.threadSleep(2000);

            reports.writeResult(oBrowser, "Screenshot", "'Supplier Activity' reports page");
            maxRows = 11;
            rowCount = oBrowser.findElements(By.xpath(PayCRM_Modules_ManageUIPage.obj_supplierActivityReportGrid + "//tr/td[contains(@aria-label, 'Column Outcome, Value')]")).size();
            if (rowCount > 11) rowCount = maxRows;
            for (int i = 0; i < rowCount; i++) {
                flag++;
                cellData = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_supplierActivityReportGrid + "//tr[contains(@class, 'lines dx-column-lines')]/td[contains(@aria-label, 'Column Outcome, Value')]"), "Text");
                Assert.assertTrue(appInd.compareValues(oBrowser, cellData, "Remittance Inquiry"));
                cellData = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_supplierActivityReportGrid + "//tr[contains(@class, 'lines dx-column-lines')]/td[contains(@aria-label, 'Created Date, Value')]"), "Text");
                formatedActualDate = appInd.formatDateFromOnetoAnother(cellData, "MM/dd/yyyy", "dd-MM-yyyy");
                Assert.assertTrue(appDep.compareDateRangeWithActualDate(startDate, endDate, formatedActualDate));
            }
            if (flag == 0) {
                reports.writeResult(oBrowser, "warning", "No data is available");
            }
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateSuppliersActivityReport()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateSuppliersActivityReport()' method. " + e);
            return false;
        } finally {supplierParentTab = null; startDate = null; endDate = null; cellData = null; formatedActualDate = null;}
    }

    /********************************************************
     * Method Name      : validateCompaniesActivityReport()
     * Purpose          : user validates the 'Companies Activity Report' reports filter functionality
     * Author           : Shidd
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateCompaniesActivityReport(WebDriver oBrowser) {
        int rowCount, maxRows, flag = 0;
        WebElement supplierParentTab;
        String startDate, endDate, cellData, formatedActualDate;

        try {
            supplierParentTab = oBrowser.findElement(PayCRM_Modules_ManageUIPage.obj_companiesParentSection);
            if (supplierParentTab.getAttribute("aria-expanded").equalsIgnoreCase("false")) {
                Assert.assertTrue(appInd.clickObject(oBrowser, supplierParentTab));
            }
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_companiesActivityReportLink, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_companiesActivityReportLink));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_companiesActivityReportRunReportBtn, "Clickable", "", waitTimeOut);

            startDate = appInd.addDaysToDate("", "dd-MM-yyyy", -1000);
            endDate = appInd.addDaysToDate("", "dd-MM-yyyy", 0);
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_companiesActivityOutcomeFilter, "Login Assistance"));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_NewUserSetup_CreateUser_Groups_SearchResult_Label + "/li[text()='Login Assistance']")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_companyActivityReportStartDate, startDate));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_companyActivityReportEndDate, endDate));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_companiesActivityReportRunReportBtn));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.threadSleep(2000);

            reports.writeResult(oBrowser, "Screenshot", "'Companies Activity' reports page");
            maxRows = 11;
            rowCount = oBrowser.findElements(By.xpath(PayCRM_Modules_ManageUIPage.obj_companyActivityReportGrid + "//tr/td[contains(@aria-label, 'Column Outcome, Value')]")).size();
            if (rowCount > 11) rowCount = maxRows;
            for (int i = 0; i < rowCount; i++) {
                flag++;
                cellData = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_companyActivityReportGrid + "//tr[contains(@class, 'lines dx-column-lines')]/td[contains(@aria-label, 'Column Outcome, Value')]"), "Text");
                Assert.assertTrue(appInd.compareValues(oBrowser, cellData, "Login Assistance"));
                cellData = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_companyActivityReportGrid + "//tr[contains(@class, 'lines dx-column-lines')]/td[contains(@aria-label, 'Created Date, Value')]"), "Text");
                formatedActualDate = appInd.formatDateFromOnetoAnother(cellData, "MM/dd/yyyy", "dd-MM-yyyy");
                Assert.assertTrue(appDep.compareDateRangeWithActualDate(startDate, endDate, formatedActualDate));
            }
            if (flag == 0) {
                reports.writeResult(oBrowser, "warning", "No data is available");
            }
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateCompaniesActivityReport()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateCompaniesActivityReport()' method. " + e);
            return false;
        } finally {supplierParentTab = null; startDate = null; endDate = null; cellData = null; formatedActualDate = null;}
    }




    /********************************************************
     * Method Name      : validateNeedInvoiceReportFilters()
     * Purpose          : user validates the 'Need Invoice' reports filter functionality
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateNeedInvoiceReportFilters(WebDriver oBrowser) {
        int rowCount = 0;
        String columnNames[];
        WebElement paymentsParentTab;
        try {
            paymentsParentTab = oBrowser.findElement(PayCRM_Modules_ManageUIPage.obj_paymentsParentSection);
            if (paymentsParentTab.getAttribute("aria-expanded").equalsIgnoreCase("false")) {
                Assert.assertTrue(appInd.clickObject(oBrowser, paymentsParentTab));
            }
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_NeedInvoice_Link, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_NeedInvoice_Link));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_NeedInvoice_RunReport_Btn));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.threadSleep(3000);
            reports.writeResult(oBrowser, "Screenshot", "'Need Invoice' reports page");
            columnNames = "Case #,Company,Last Activity Notes,Supplier,Vendor ID,Date,Case Notes,Activity Action".split(",");
            for (int i = 0; i < columnNames.length; i++) {
                System.out.println("(//div[@id='divNeedInvoiceContainer']//table)[1]//td["+(i+2)+"]/div[text()='"+columnNames[i]+"']");
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("(//div[@id='divNeedInvoiceContainer']//table)[1]//td["+(i+2)+"]/div[text()='"+columnNames[i]+"']")));
                //Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_NeedInvoice_Grid + "//table[1]//td[" + (i + 2) + "]/div[not(@style)]"), "Text", columnNames[i]));
            }

            rowCount = oBrowser.findElements(By.xpath(PayCRM_Modules_ManageUIPage.obj_NeedInvoice_Grid + "//div[contains(@class, 'dx-datagrid-rowsview')]//table//tr")).size();
            if (rowCount > 0)
                reports.writeResult(oBrowser, "Pass", "The 'Need Invoice' report filter working successful");
            if (rowCount == 0)
                Assert.assertTrue(false, "No records were found when applied filter for the 'Need Invoice' report");
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateNeedInvoiceReportFilters()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateNeedInvoiceReportFilters()' method. " + e);
            return false;
        } finally {columnNames = null; paymentsParentTab = null;}
    }



    /********************************************************
     * Method Name      : validateOriginationSourcesReportFilters()
     * Purpose          : user validates the 'Origination Sources' reports filter functionality
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateOriginationSourcesReportFilters(WebDriver oBrowser) {
        int rowCount = 0;
        String columnNames[];
        WebElement enrollmentParentTab;
        try {
            enrollmentParentTab = oBrowser.findElement(PayCRM_Modules_ManageUIPage.obj_enrollmentsParentSection);
            if (enrollmentParentTab.getAttribute("aria-expanded").equalsIgnoreCase("false")) {
                Assert.assertTrue(appInd.clickObject(oBrowser, enrollmentParentTab));
            }
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_OriginationSources_Link, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_OriginationSources_Link));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_OriginationSources_List, "Accelerate360, LLC"));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_OriginationSources_ViewAll_Btn));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.threadSleep(3000);
            reports.writeResult(oBrowser, "Screenshot", "'Origination Sources' reports page");
            columnNames = "Origination Type#Origination Source#Total".split("#");
            for (int i = 0; i < columnNames.length; i++) {
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("(//div[@id='divOriginationSourcesContainer']//table)[1]//td["+(i+1)+"]/div[text()='"+columnNames[i]+"']")));
                //Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_OriginationSources_Grid + "//table[1]//td[" + (i + 1) + "]/div[not(@style)]"), "Text", columnNames[i]));
            }
            rowCount = oBrowser.findElements(By.xpath(PayCRM_Modules_ManageUIPage.obj_OriginationSources_Grid + "//div[contains(@class, 'dx-datagrid-rowsview')]//table//tr")).size();
            if (rowCount > 0)
                reports.writeResult(oBrowser, "Pass", "The 'Origination Sources' report filter working successful");
            if (rowCount == 0)
                Assert.assertTrue(false, "No records were found when applied filter for the 'Origination Sources' report");
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateOriginationSourcesReportFilters()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateOriginationSourcesReportFilters()' method. " + e);
            return false;
        } finally {columnNames = null; enrollmentParentTab = null;}
    }



    /********************************************************
     * Method Name      : validatePartnerEnrollmentsReportFilters()
     * Purpose          : user validates the 'Partner Enrollments' reports filter functionality
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validatePartnerEnrollmentsReportFilters(WebDriver oBrowser) {
        String startDate = null;
        String endDate = null;
        int rowCount = 0;
        int maxRows = 0;
        String cellData = null;
        String formatedActualDate = null;
        int flag = 0;
        WebElement enrollmentParentTab;
        try {
            enrollmentParentTab = oBrowser.findElement(PayCRM_Modules_ManageUIPage.obj_enrollmentsParentSection);
            if (enrollmentParentTab.getAttribute("aria-expanded").equalsIgnoreCase("false")) {
                Assert.assertTrue(appInd.clickObject(oBrowser, enrollmentParentTab));
            }
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_PartnerEnrollments_Link, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_PartnerEnrollments_Link));
            appInd.scrollToThePage(oBrowser, "Top");
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_PartnerEnrollments_RunReport_Btn, "Clickable", "", waitTimeOut);
            startDate = appInd.addDaysToDate("", "dd-MM-yyyy", -1000);
            endDate = appInd.addDaysToDate("", "dd-MM-yyyy", 0);
            Assert.assertTrue(appInd.setObject(oBrowser, By.id("start_date_filter_partner_enrollment_report"), startDate));
            Assert.assertTrue(appInd.setObject(oBrowser, By.id("end_date_filter_partner_enrollment_report"), endDate));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_PartnerEnrollments_RunReport_Btn));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.threadSleep(3000);
            reports.writeResult(oBrowser, "Screenshot", "'Partner Enrollments' reports page");
            maxRows = 11;
            rowCount = oBrowser.findElements(By.xpath(PayCRM_Modules_ManageUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//tr/td[contains(@aria-label, 'Created Date, Value')]")).size();
            if (rowCount > 11) rowCount = maxRows;
            for (int i = 0; i < rowCount; i++) {
                flag++;
                cellData = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//tr/td[contains(@aria-label, 'Created Date, Value')]"), "Text");
                formatedActualDate = appInd.formatDateFromOnetoAnother(cellData, "MM/dd/yyyy", "dd-MM-yyyy");
                Assert.assertTrue(appDep.compareDateRangeWithActualDate(startDate, endDate, formatedActualDate));
            }

            if(rowCount > 0) flag = 1;
            if (flag == 0)
                Assert.assertTrue(false, "No records were found when applied filter for the 'Partner Enrollments' report");
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validatePartnerEnrollmentsReportFilters()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validatePartnerEnrollmentsReportFilters()' method. " + e);
            return false;
        } finally {
            startDate = null; endDate = null; cellData = null; formatedActualDate = null; enrollmentParentTab = null;
        }
    }



    /********************************************************
     * Method Name      : validateReportFilters()
     * Purpose          : user validates the All Enrolled reports filter functionality
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateReportFilters(WebDriver oBrowser, String reportType) {
        try {
            switch (reportType.toLowerCase()) {
                case "all enrolled":
                    Assert.assertTrue(validateAllEnrolledReportFilters(oBrowser));
                    break;
                case "activity report":
                    Assert.assertTrue(validateActivityReportFilters(oBrowser));
                    break;
                case "agent activity":
                    Assert.assertTrue(validateAgentActivityReportFilters(oBrowser));
                    break;
                case "bill pay report":
                    Assert.assertTrue(validateBillPayReportFilters(oBrowser));
                    break;
                case "case outcomes report":
                    Assert.assertTrue(validateCaseOutcomesReportFilters(oBrowser));
                    break;
                case "high priority suppliers":
                    Assert.assertTrue(validateHighPrioritySuppliersReportFilters(oBrowser));
                    break;
                case "first time payments":
                    Assert.assertTrue(validateFirstTimePaymentsReportFilters(oBrowser));
                    break;
                case "enrollments by team member":
                    Assert.assertTrue(validateEnrollmentsByTeamMemberReportFilters(oBrowser));
                    break;
                case "enrollment report":
                    Assert.assertTrue(validateEnrollmentReportFilters(oBrowser));
                    break;
                case "multi swipe":
                    Assert.assertTrue(validateMultiSwipeReportFilters(oBrowser));
                    break;
                case "need invoice":
                    Assert.assertTrue(validateNeedInvoiceReportFilters(oBrowser));
                    break;
                case "origination sources":
                    Assert.assertTrue(validateOriginationSourcesReportFilters(oBrowser));
                    break;
                case "partner enrollments":
                    Assert.assertTrue(validatePartnerEnrollmentsReportFilters(oBrowser));
                    break;
                case "switched payment types":
                    Assert.assertTrue(validateSwitchedPaymentTypesReportFilters(oBrowser));
                    break;
                case "support case agent activity":
                    Assert.assertTrue(validateSupportCasesAgentActivityReport(oBrowser));
                    break;
                case "support case outcomes report":
                    Assert.assertTrue(validateSupportCasesOutcomesReport(oBrowser));
                    break;
                case "supplier activity report":
                    Assert.assertTrue(validateSuppliersActivityReport(oBrowser));
                    break;
                case "company activity report":
                    Assert.assertTrue(validateCompaniesActivityReport(oBrowser));
                    break;
                default:
                    reports.writeResult(oBrowser, "Fail", "Invalid report type '" + reportType + "' was specified.");
                    return false;
            }
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateReportFilters()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateReportFilters()' method. " + e);
            return false;
        }
    }

    /********************************************************
     * Method Name      : readGridSectionDataAndReturnAsMap()
     * Purpose          : user reads the table data from the merged section and returns the Map object
     * Author           : Gudi
     * Parameters       : oBrowser, objBy, KeyNames
     * ReturnType       : Map<String, String>
     ********************************************************/
    public Map<String, String> readGridSectionDataAndReturnAsMap(WebDriver oBrowser, String objBy, String KeyNames) {
        Map<String, String> objData = null;
        String arrKeys[];
        int index = 2;
        try {
            objData = new HashMap<String, String>();
            arrKeys = KeyNames.split("#");
            for (int i = 0; i < arrKeys.length; i++) {
                if (arrKeys[i].isEmpty())
                    index++;
                else {
                    objData.put(arrKeys[i], appInd.getTextOnElement(oBrowser, By.xpath(objBy + "/tbody//td[" + index + "]"), "Text"));
                    index++;
                }
            }
            return objData;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'readMergedSectionDataAndReturnAsMap()' method. " + e);
            objData.put("TestPassed", "False");
            return objData;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'readMergedSectionDataAndReturnAsMap()' method. " + e);
            objData.put("TestPassed", "False");
            return objData;
        } finally {objData = null; arrKeys = null;}
    }



    /********************************************************
     * Method Name      : donotMergingOfDuplicateSuppliers()
     * Purpose          : user performs donot merging of duplicate suppliers from Modules-->Manage-->Duplicate Supplier Management-->Loaded
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean donotMergingOfDuplicateSuppliers(WebDriver oBrowser) {
        String clientName = null;
        Map<String, String> mergeChildParent_ParentGrid = null;
        Alert oAlert = null;
        boolean blnRes = false;
        try {
            blnRes = appInd.verifyOptionalElementPresent(oBrowser, PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Loaded_Tab_Link);
            if (!blnRes)
                Assert.assertTrue(appDep.selectManageModules(oBrowser, "Duplicate Supplier Management"));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "'Duplicate Supplier Management->Loaded' page opened successful");
            clientName = appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_ManageUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//td)[1]"), "Text").trim();
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("(" + PayCRM_Modules_ManageUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//td)[1]")));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_MergeChildParent_DoNotMerge_Btn, "Clickable", "", waitTimeOut);

            //Merge child into parent supplier
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_PageTitle_Label + "//span[@data-step='1']"), "Text", "Merge child into parent supplier"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedSuppliersTable_Grid + "//td[@class='dataTables_empty']"), "Text", "Supplier has not been merged yet."));

            //Merge child into parent supplier
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_PageTitle_Label + "//span[@data-step='1']"), "Text", "Merge child into parent supplier"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedSuppliersTable_Grid + "//td[@class='dataTables_empty']"), "Text", "Supplier has not been merged yet."));
            mergeChildParent_ParentGrid = new HashMap<String, String>();
            mergeChildParent_ParentGrid = readGridSectionDataAndReturnAsMap(oBrowser, PayCRM_Modules_ManageUIPage.obj_ParentSupplierTable_Grid, "MatchedDescription#MatchedValue##Name#TIN#Phone#PayNetID#Status#SuppliersType");

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_ParentSupplierTable_Grid + "/tbody/tr//input[@type='checkbox']")));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedSuppliersTable_Grid + "//td[@class='dataTables_empty']"), "Invisibility", "", waitTimeOut);

            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedSuppliersTable_Grid + "//td[1]/input[1]"), "Value", mergeChildParent_ParentGrid.get("Name")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedSuppliersTable_Grid + "//td[2]/input[1]"), "Value", mergeChildParent_ParentGrid.get("TIN")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedSuppliersTable_Grid + "//td[3]/input[1]"), "Value", mergeChildParent_ParentGrid.get("Phone")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedSuppliersTable_Grid + "//td[4]/input[1]"), "Value", mergeChildParent_ParentGrid.get("PayNetID")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedSuppliersTable_Grid + "//td[5]/input[1]"), "Value", mergeChildParent_ParentGrid.get("Status")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedSuppliersTable_Grid + "//td[6]/select[1]"), "Dropdown", (mergeChildParent_ParentGrid.get("SuppliersType").split("\\)")[1].trim())));
            reports.writeResult(oBrowser, "Screenshot", "'Merge child into parent supplier' page opened successful");

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_MergeChildParent_DoNotMerge_Btn));
            appDep.threadSleep(2000);
            oAlert = oBrowser.switchTo().alert();
            Assert.assertTrue(appInd.compareValues(oBrowser, oAlert.getText(), "You have selected Do Not Merge. Are you sure you want to proceed?"));
            oAlert.accept();
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergeComplete_section + "//h4[@class='bold black']"), "Text", "Did Not Merge", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergeComplete_section + "//h4[@class='bold black']"), "Text", "Did Not Merge"));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManageUIPage.obj_CompanyEditConfirmationMessage_Label, "Text").contains("Did Not Merge Supplier"));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManageUIPage.obj_MergeCompleted_Img));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'donotMergingOfDuplicateSuppliers()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'donotMergingOfDuplicateSuppliers()' method. " + e);
            return false;
        } finally {
            clientName = null; mergeChildParent_ParentGrid = null; oAlert = null;
        }
    }



    /********************************************************
     * Method Name      : validateOverallMergedDuplicateSuppliers()
     * Purpose          : user performs merging of duplicate suppliers from Modules-->Manage-->Duplicate Supplier Management-->Loaded
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateOverallMergedDuplicateSuppliers(WebDriver oBrowser, String objBy, String mergeName, Map<String, String> objSupplierData, String columnNames) {
        int index = 1;
        String arrColumns[];
        try {
            arrColumns = columnNames.split("#");
            for (String key : arrColumns) {
                if (key.equalsIgnoreCase("MatchedValue")) {
                    String matchedValues;
                    if (objSupplierData.get(key).contains("…")) {
                        matchedValues = objSupplierData.get(key).substring(0, objSupplierData.get(key).indexOf("…"));
                    } else {
                        matchedValues = objSupplierData.get(key);
                    }
                    Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(objBy + "/td[" + index + "]"), "Text").contains(matchedValues));
                } else {
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(objBy + "/td[" + index + "]"), "Text", objSupplierData.get(key)));
                }
                index++;
            }
            reports.writeResult(oBrowser, "Pass", "The '" + mergeName + "' section of 'Merge Duplicate Supplier' was validated successful");
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateOverallMergedDuplicateSuppliers()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateOverallMergedDuplicateSuppliers()' method. " + e);
            return false;
        } finally {arrColumns = null;}
    }



    /********************************************************
     * Method Name      : mergingOfDuplicateSuppliers()
     * Purpose          : user performs merging of duplicate suppliers from Modules-->Manage-->Duplicate Supplier Management-->Loaded
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean mergingOfDuplicateSuppliers(WebDriver oBrowser) {
        String clientName = null;
        Map<String, String> mergeChildParent_ParentGrid = null;
        Map<String, String> selectContact_ChildGrid = null;
        Map<String, String> selectContact_ParentGrid = null;
        Map<String, String> selectAddress_ChildGrid = null;
        Map<String, String> selectAddress_ParentGrid = null;
        Map<String, String> clientLink_ChildGrid = null;
        Map<String, String> clientLink_ParentGrid = null;
        boolean isChildSelectContactExist = false;
        boolean isParentSelectContactExist = false;
        boolean isChildSelectAddressExist = false;
        boolean isParentSelectAddressExist = false;
        boolean isChildClientLinkExist = false;
        boolean isParentClientLinkExist = false;
        int index = 0;
        int rowNum = 0;
        try {
            Assert.assertTrue(appDep.selectManageModules(oBrowser, "Duplicate Supplier Management"));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "'Duplicate Supplier Management->Loaded' page opened successful");
            clientName = appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_ManageUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//td)[1]"), "Text").trim();
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("(" + PayCRM_Modules_ManageUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//td)[1]")));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_MergeChildParent_DoNotMerge_Btn, "Clickable", "", waitTimeOut);

            //Merge child into parent supplier
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_PageTitle_Label + "//span[@data-step='1']"), "Text", "Merge child into parent supplier"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedSuppliersTable_Grid + "//td[@class='dataTables_empty']"), "Text", "Supplier has not been merged yet."));
            mergeChildParent_ParentGrid = new HashMap<String, String>();
            mergeChildParent_ParentGrid = readGridSectionDataAndReturnAsMap(oBrowser, PayCRM_Modules_ManageUIPage.obj_ParentSupplierTable_Grid, "MatchedDescription#MatchedValue##Name#TIN#Phone#PayNetID#Status#SuppliersType");

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_ParentSupplierTable_Grid + "/tbody/tr//input[@type='checkbox']")));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedSuppliersTable_Grid + "//td[@class='dataTables_empty']"), "Invisibility", "", waitTimeOut);

            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedSuppliersTable_Grid + "//td[1]/input[1]"), "Value", mergeChildParent_ParentGrid.get("Name")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedSuppliersTable_Grid + "//td[2]/input[1]"), "Value", mergeChildParent_ParentGrid.get("TIN")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedSuppliersTable_Grid + "//td[3]/input[1]"), "Value", mergeChildParent_ParentGrid.get("Phone")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedSuppliersTable_Grid + "//td[4]/input[1]"), "Value", mergeChildParent_ParentGrid.get("PayNetID")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedSuppliersTable_Grid + "//td[5]/input[1]"), "Value", mergeChildParent_ParentGrid.get("Status")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedSuppliersTable_Grid + "//td[6]/select[1]"), "Dropdown", (mergeChildParent_ParentGrid.get("SuppliersType").split("\\)")[1].trim())));
            reports.writeResult(oBrowser, "Screenshot", "'Merge child into parent supplier' page opened successful");

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergeSupplier_Continue_Btn + "[@data-step='1']/div")));


            //Select contacts
            if (appInd.verifyOptionalElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_ChildContactsTable_Grid + "/tbody//td[1]/input[@type='checkbox']"))) {
                appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergeSupplier_Continue_Btn + "[@data-step='2']/div"), "Clickable", "", waitTimeOut);
                appDep.threadSleep(2000);
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_PageTitle_Label + "//span[@data-step='2']"), "Text", "Select contacts"));
                isChildSelectContactExist = true;
                selectContact_ChildGrid = new HashMap<String, String>();
                selectContact_ChildGrid = readGridSectionDataAndReturnAsMap(oBrowser, PayCRM_Modules_ManageUIPage.obj_ChildContactsTable_Grid, "Name#Type#Phone1#Phone2#Email");

                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_ChildContactsTable_Grid + "/tbody//td[1]/input[@type='checkbox']")));
                appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedContactsTable_Grid + "//td[@class='dataTables_empty']"), "Invisibility", "", waitTimeOut);

                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedContactsTable_Grid + "//tr[1]/td[1]/input[1]"), "Value", selectContact_ChildGrid.get("Name")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedContactsTable_Grid + "//tr[1]/td[2]/input[1]"), "Value", selectContact_ChildGrid.get("Type")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedContactsTable_Grid + "//tr[1]/td[3]/input[1]"), "Value", selectContact_ChildGrid.get("Phone1")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedContactsTable_Grid + "//tr[1]/td[4]/input[1]"), "Value", selectContact_ChildGrid.get("Phone2")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedContactsTable_Grid + "//tr[1]/td[5]/input[1]"), "Value", selectContact_ChildGrid.get("Email")));
            }


            if (appInd.verifyOptionalElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_ParentContactsTable_Grid + "/tbody//td[1]/input[@type='checkbox']"))) {
                isParentSelectContactExist = true;
                selectContact_ParentGrid = new HashMap<String, String>();
                selectContact_ParentGrid = readGridSectionDataAndReturnAsMap(oBrowser, PayCRM_Modules_ManageUIPage.obj_ParentContactsTable_Grid, "Name#Type#Phone1#Phone2#Email");

                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_ParentContactsTable_Grid + "/tbody//td[1]/input[@type='checkbox']")));

                if (isChildSelectContactExist == true) {
                    if (selectContact_ParentGrid.get("Type").equalsIgnoreCase("Primary")) {
                        selectContact_ChildGrid.remove("Type");
                        selectContact_ChildGrid.put("Type", "Other");
                    }
                    index = 2;
                } else index = 1;
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedContactsTable_Grid + "//tr[" + index + "]/td[1]/input[1]"), "Value", selectContact_ParentGrid.get("Name")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedContactsTable_Grid + "//tr[" + index + "]/td[2]/input[1]"), "Value", selectContact_ParentGrid.get("Type")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedContactsTable_Grid + "//tr[" + index + "]/td[3]/input[1]"), "Value", selectContact_ParentGrid.get("Phone1")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedContactsTable_Grid + "//tr[" + index + "]/td[4]/input[1]"), "Value", selectContact_ParentGrid.get("Phone2")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedContactsTable_Grid + "//tr[" + index + "]/td[5]/input[1]"), "Value", selectContact_ParentGrid.get("Email")));
            }

            //Verify contact details are prenet for the selected Duplicate Supplier Management
            if (isChildSelectContactExist == true || isParentSelectContactExist == true) {
                reports.writeResult(oBrowser, "Screenshot", "'Select contacts' page opened successful");
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergeSupplier_Continue_Btn + "[@data-step='2']/div")));
            }


            //Select Addresses
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergeSupplier_Continue_Btn + "[@data-step='3']/div"), "Clickable", "", waitTimeOut);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_PageTitle_Label + "//span[@data-step='3']"), "Text", "Select Addresses"));

            if (appInd.verifyOptionalElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_ChildAddressTable_Grid + "/tbody//td[1]/input[@type='checkbox']"))) {
                isChildSelectAddressExist = true;
                selectAddress_ChildGrid = new HashMap<String, String>();
                selectAddress_ChildGrid = readGridSectionDataAndReturnAsMap(oBrowser, PayCRM_Modules_ManageUIPage.obj_ChildAddressTable_Grid, "Type#Address1#City#StateProvince#PostalCode#Country");

                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_ChildAddressTable_Grid + "/tbody//td[1]/input[@type='checkbox']")));
                appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedAddressTable_Grid + "//td[@class='dataTables_empty']"), "Invisibility", "", waitTimeOut);

                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedAddressTable_Grid + "//tr[1]/td[1]/input[1]"), "Value", selectAddress_ChildGrid.get("Type")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedAddressTable_Grid + "//tr[1]/td[2]/input[1]"), "Value", selectAddress_ChildGrid.get("Address1")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedAddressTable_Grid + "//tr[1]/td[3]/input[1]"), "Value", selectAddress_ChildGrid.get("City")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedAddressTable_Grid + "//tr[1]/td[4]/input[1]"), "Value", selectAddress_ChildGrid.get("StateProvince")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedAddressTable_Grid + "//tr[1]/td[5]/input[1]"), "Value", selectAddress_ChildGrid.get("PostalCode")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedAddressTable_Grid + "//tr[1]/td[6]/input[1]"), "Value", selectAddress_ChildGrid.get("Country")));
            }


            if (appInd.verifyOptionalElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_ParentAddressTable_Grid + "/tbody//td[1]/input[@type='checkbox']"))) {
                selectAddress_ParentGrid = new HashMap<String, String>();
                selectAddress_ParentGrid = readGridSectionDataAndReturnAsMap(oBrowser, PayCRM_Modules_ManageUIPage.obj_ParentAddressTable_Grid, "Type#Address1#City#StateProvince#PostalCode#Country");

                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_ParentAddressTable_Grid + "/tbody//td[1]/input[@type='checkbox']")));

                if (isChildSelectAddressExist == true) {
                    if (selectAddress_ParentGrid.get("Type").equalsIgnoreCase("Primary") && selectAddress_ChildGrid.get("Type").equalsIgnoreCase("Primary")) {
                        selectAddress_ChildGrid.remove("Type");
                        selectAddress_ChildGrid.put("Type", "Other");
                    }
                    index = 2;
                } else index = 1;
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedAddressTable_Grid + "//tr[" + index + "]/td[1]/input[1]"), "Value", selectAddress_ParentGrid.get("Type")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedAddressTable_Grid + "//tr[" + index + "]/td[2]/input[1]"), "Value", selectAddress_ParentGrid.get("Address1")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedAddressTable_Grid + "//tr[" + index + "]/td[3]/input[1]"), "Value", selectAddress_ParentGrid.get("City")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedAddressTable_Grid + "//tr[" + index + "]/td[4]/input[1]"), "Value", selectAddress_ParentGrid.get("StateProvince")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedAddressTable_Grid + "//tr[" + index + "]/td[5]/input[1]"), "Value", selectAddress_ParentGrid.get("PostalCode")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergedAddressTable_Grid + "//tr[" + index + "]/td[6]/input[1]"), "Value", selectAddress_ParentGrid.get("Country")));
            }

            //Verify Address details are prenet for the selected Duplicate Supplier Management
            if (isChildSelectAddressExist == true || isParentSelectAddressExist == true) {
                reports.writeResult(oBrowser, "Screenshot", "'Select Addresses' page opened successful");
                appDep.threadSleep(2000);
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergeSupplier_Continue_Btn + "[@data-step='3']/div")));
            }


            //Merge and/or Select Links
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_ChildClientLink_Confirmation_Message, "Visibility", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManageUIPage.obj_ChildClientLink_Confirmation_Message, "Text").contains("All Child Client Links must be selected to continue to the next step."));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_PageTitle_Label + "//span[@data-step='4']"), "Text", "Merge and/or Select Links"));

            if (appInd.verifyOptionalElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_ChildLinksTable_Grid + "/tbody//td[1]/input[@type='checkbox']"))) {
                isChildClientLinkExist = true;
                clientLink_ChildGrid = new HashMap<String, String>();
                clientLink_ChildGrid = readGridSectionDataAndReturnAsMap(oBrowser, PayCRM_Modules_ManageUIPage.obj_ChildLinksTable_Grid, "ClientName#Wave#SupplierType#VendorIDs#LinkID#Status#NumChecks#TotalSpend");

                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_ChildLinksTable_Grid + "/tbody//td[1]/input[@type='checkbox']")));
                appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergeLinks_Table_Grid + "//td[@class='dataTables_empty']"), "Invisibility", "", waitTimeOut);

                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergeLinks_Table_Grid + "//tr[1]/td[1]/input[1]"), "Value", clientLink_ChildGrid.get("ClientName")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergeLinks_Table_Grid + "//tr[1]/td[2]//select[1]"), "Dropdown", clientLink_ChildGrid.get("Wave")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergeLinks_Table_Grid + "//tr[1]/td[3]/input[1]"), "Value", clientLink_ChildGrid.get("SupplierType")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergeLinks_Table_Grid + "//tr[1]/td[4]//input[1]"), "Value", clientLink_ChildGrid.get("VendorIDs")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergeLinks_Table_Grid + "//tr[1]/td[5]/input[1]"), "Value", clientLink_ChildGrid.get("LinkID")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergeLinks_Table_Grid + "//tr[1]/td[6]/input[1]"), "Value", clientLink_ChildGrid.get("Status")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergeLinks_Table_Grid + "//tr[1]/td[7]//input[1]"), "Value", clientLink_ChildGrid.get("NumChecks")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergeLinks_Table_Grid + "//tr[1]/td[8]//input[1]"), "Value", clientLink_ChildGrid.get("TotalSpend")));
            }


            if (appInd.verifyOptionalElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_ParentLinksTable_Grid + "/tbody//td[1]/input[@type='checkbox']"))) {
                isParentClientLinkExist = true;
                clientLink_ParentGrid = new HashMap<String, String>();
                clientLink_ParentGrid = readGridSectionDataAndReturnAsMap(oBrowser, PayCRM_Modules_ManageUIPage.obj_ParentLinksTable_Grid, "ClientName#Wave#SupplierType#VendorIDs#LinkID#Status#NumChecks#TotalSpend");
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_ParentLinksTable_Grid + "/tbody//td[1]/input[@type='checkbox']")));
                appDep.threadSleep(2000);

                if (isChildClientLinkExist == true) index = 2;
                else index = 1;
                boolean blnExist = appInd.verifyOptionalElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergeLinks_Table_Grid + "//tr[" + index + "]/td[1]/input[1]"));
                if (blnExist) {
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergeLinks_Table_Grid + "//tr[" + index + "]/td[1]/input[1]"), "Value", clientLink_ParentGrid.get("ClientName")));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergeLinks_Table_Grid + "//tr[" + index + "]/td[2]//select[1]"), "Dropdown", clientLink_ParentGrid.get("Wave")));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergeLinks_Table_Grid + "//tr[" + index + "]/td[3]/input[1]"), "Value", clientLink_ParentGrid.get("SupplierType")));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergeLinks_Table_Grid + "//tr[" + index + "]/td[4]//input[1]"), "Value", clientLink_ParentGrid.get("VendorIDs")));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergeLinks_Table_Grid + "//tr[" + index + "]/td[5]/input[1]"), "Value", clientLink_ParentGrid.get("LinkID")));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergeLinks_Table_Grid + "//tr[" + index + "]/td[6]/input[1]"), "Value", clientLink_ParentGrid.get("Status")));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergeLinks_Table_Grid + "//tr[" + index + "]/td[7]//input[1]"), "Value", clientLink_ParentGrid.get("NumChecks")));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergeLinks_Table_Grid + "//tr[" + index + "]/td[8]//input[1]"), "Value", clientLink_ParentGrid.get("TotalSpend")));
                } else {
                    isParentClientLinkExist = false;
                }
            }

            //Verify Address details are prenet for the selected Duplicate Supplier Management
            if (isChildClientLinkExist == true || isParentClientLinkExist == true) {
                reports.writeResult(oBrowser, "Screenshot", "'Merge and/or Select Links' page opened successful");
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergeSupplier_Continue_Btn + "[@data-step='4']/div")));
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_ConfirmMerge_Chkbox, "Clickable", "", waitTimeOut);
                appDep.threadSleep(2000);
            }


            //Preview Merge Changes
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_PageTitle_Label + "//span[@data-step='5']"), "Text", "Preview Merge Changes"));
            Assert.assertTrue(validateOverallMergedDuplicateSuppliers(oBrowser, PayCRM_Modules_ManageUIPage.obj_MergedSuppliersPreview_TableGrid + "//tr[1]", "Merged Supplier", mergeChildParent_ParentGrid, "MatchedDescription#MatchedValue#Name#TIN#Phone#PayNetID#Status#SuppliersType"));

            rowNum = 0;
            //validate contacts
            if (isChildSelectContactExist == true) {
                rowNum = 1;
                Assert.assertTrue(validateOverallMergedDuplicateSuppliers(oBrowser, PayCRM_Modules_ManageUIPage.obj_MergedContactPreview_TableGrid + "//tr[" + rowNum + "]", "Child Contacts", selectContact_ChildGrid, "Name#Type#Phone1#Phone2#Email"));
            }

            if (isParentSelectContactExist == true) {
                rowNum += 1;
                Assert.assertTrue(validateOverallMergedDuplicateSuppliers(oBrowser, PayCRM_Modules_ManageUIPage.obj_MergedContactPreview_TableGrid + "//tr[" + rowNum + "]", "Parent Contacts", selectContact_ParentGrid, "Name#Type#Phone1#Phone2#Email"));
            }


            //Validate Addresses
            rowNum = 0;
            if (isChildSelectAddressExist == true) {
                rowNum = 1;
                Assert.assertTrue(validateOverallMergedDuplicateSuppliers(oBrowser, PayCRM_Modules_ManageUIPage.obj_MergedAddressPreview_TableGrid + "//tr[" + rowNum + "]", "Child Address", selectAddress_ChildGrid, "Type#Address1#City#StateProvince#PostalCode#Country"));
            }

            if (isParentSelectAddressExist == true) {
                rowNum += 1;
                Assert.assertTrue(validateOverallMergedDuplicateSuppliers(oBrowser, PayCRM_Modules_ManageUIPage.obj_MergedAddressPreview_TableGrid + "//tr[" + rowNum + "]", "Parent Address", selectAddress_ParentGrid, "Type#Address1#City#StateProvince#PostalCode#Country"));
            }


            //Validate company suppliers
            rowNum = 0;
            if (isChildClientLinkExist == true) {
                rowNum = 1;
                Assert.assertTrue(validateOverallMergedDuplicateSuppliers(oBrowser, PayCRM_Modules_ManageUIPage.obj_MergedCompanySuppliersPreview_TableGrid + "//tr[" + rowNum + "]", "Child Client Link", clientLink_ChildGrid, "ClientName#Wave#SupplierType#VendorIDs#LinkID#Status#NumChecks#TotalSpend"));
            }

            if (isParentClientLinkExist == true) {
                rowNum += 1;
                Assert.assertTrue(validateOverallMergedDuplicateSuppliers(oBrowser, PayCRM_Modules_ManageUIPage.obj_MergedCompanySuppliersPreview_TableGrid + "//tr[" + rowNum + "]", "Parent Client Link", clientLink_ParentGrid, "ClientName#Wave#SupplierType#VendorIDs#LinkID#Status#NumChecks#TotalSpend"));
            }


            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_ConfirmMerge_Chkbox, "Clickable", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "'Preview Merge Changes' page opened successful");

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_ConfirmMerge_Chkbox));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.Obj_CompleteMerge_Btn));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergeComplete_section + "//h4[@class='bold black']"), "Text", "Success!", waitTimeOut);

            //Merging Complete
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergeComplete_section + "//h4[@class='bold black']"), "Text", "Success!"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_PageTitle_Label + "//span[@data-step='6']"), "Text", "Merging Complete"));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManageUIPage.obj_MergeCompleted_Img));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManageUIPage.obj_CompanyEditConfirmationMessage_Label, "Text").contains("Suppliers Merged Successfully"));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MergeComplete_section + "//h5[@class='black']"), "Text").contains("Pat yourself on the back because you just completed a merge."));
            reports.writeResult(oBrowser, "Screenshot", "'Merging Complete' page opened successful");
            appInd.scrollToThePage(oBrowser, "Botton");

            //Validate final merged history details
            //Validate Merged Supplier
            Assert.assertTrue(validateOverallMergedDuplicateSuppliers(oBrowser, PayCRM_Modules_ManageUIPage.obj_MergedSupplier_History_Grid + "//tr[1]", "Merged Supplier", mergeChildParent_ParentGrid, "MatchedDescription#MatchedValue#Name#TIN#Phone#PayNetID#Status#SuppliersType"));

            //Validate Contacts
            rowNum = 0;
            if (isChildSelectContactExist == true) {
                rowNum = 1;
                Assert.assertTrue(validateOverallMergedDuplicateSuppliers(oBrowser, PayCRM_Modules_ManageUIPage.obj_MergedContacts_History_Grid + "//tr[" + rowNum + "]", "Child Contacts", selectContact_ChildGrid, "Name#Type#Phone1#Phone2#Email"));
            }

            if (isParentSelectContactExist == true) {
                rowNum += 1;
                Assert.assertTrue(validateOverallMergedDuplicateSuppliers(oBrowser, PayCRM_Modules_ManageUIPage.obj_MergedContacts_History_Grid + "//tr[" + rowNum + "]", "Parent Contacts", selectContact_ParentGrid, "Name#Type#Phone1#Phone2#Email"));
            }


            //Validate Addresses
            rowNum = 0;
            if (isChildSelectAddressExist == true) {
                rowNum = 1;
                Assert.assertTrue(validateOverallMergedDuplicateSuppliers(oBrowser, PayCRM_Modules_ManageUIPage.obj_MergedAddresses_History_Grid + "//tr[" + rowNum + "]", "Child Address", selectAddress_ChildGrid, "Type#Address1#City#StateProvince#PostalCode#Country"));
            }

            if (isParentSelectAddressExist == true) {
                rowNum += 1;
                Assert.assertTrue(validateOverallMergedDuplicateSuppliers(oBrowser, PayCRM_Modules_ManageUIPage.obj_MergedAddresses_History_Grid + "//tr[" + rowNum + "]", "Parent Address", selectAddress_ParentGrid, "Type#Address1#City#StateProvince#PostalCode#Country"));
            }


            //validate Company Suppliers
            rowNum = 0;
            if (isChildClientLinkExist == true) {
                rowNum = 1;
                Assert.assertTrue(validateOverallMergedDuplicateSuppliers(oBrowser, PayCRM_Modules_ManageUIPage.obj_MergedCompanySuppliers_History_Grid + "//tr[" + rowNum + "]", "Child Client Link", clientLink_ChildGrid, "ClientName#Wave#SupplierType#VendorIDs#LinkID#Status#NumChecks#TotalSpend"));
            }

            if (isParentClientLinkExist == true) {
                rowNum += 1;
                Assert.assertTrue(validateOverallMergedDuplicateSuppliers(oBrowser, PayCRM_Modules_ManageUIPage.obj_MergedCompanySuppliers_History_Grid + "//tr[" + rowNum + "]", "Parent Client Link", clientLink_ParentGrid, "ClientName#Wave#SupplierType#VendorIDs#LinkID#Status#NumChecks#TotalSpend"));
            }


            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'mergingOfDuplicateSuppliers()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'mergingOfDuplicateSuppliers()' method. " + e);
            return false;
        } finally {
            mergeChildParent_ParentGrid = null; selectContact_ChildGrid = null; selectContact_ParentGrid = null; selectAddress_ChildGrid = null; selectAddress_ParentGrid = null; clientLink_ChildGrid = null; clientLink_ParentGrid = null;
        }
    }



    /********************************************************
     * Method Name      : validateTheCustomizationForDataColumns()
     * Purpose          : User validates the Customized Data columns functionality for Duplicate Supplier Management tabs viz., Loaded/Completed
     * Author           : Gudi
     * Parameters       : oBrowser, duplicateSupplierTabName, queryKey
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateTheCustomizationForDataColumns(WebDriver oBrowser, String duplicateSupplierTabName, String queryKey) {
        String columnNamesBefore = "";
        String columnNamesAfter = "";
        int columns = 0;
        int index = 0;
        String arrColumnToChoose[];
        boolean blnRes = false;
        JSONArray caseDetails = null;
        try {
            if (!appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManageUIPage.obj_MonetizationTracking_Page_Header, "Text").contains("Duplicate Supplier Management"))
                Assert.assertTrue(appDep.selectManageModules(oBrowser, "Duplicate Supplier Management"));
            appDep.threadSleep(4000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            if (duplicateSupplierTabName.equalsIgnoreCase("Completed")) {
                caseDetails = apiDataProvider.getAPIDataProviderResponse(queryKey, new Object[] {});

                if(((JSONArray) caseDetails.get(0)).size() == 0){
                    reports.writeResult(oBrowser, "Warning", "No records were retrieved from the '" + System.getProperty("environment") + "' DB, \n Hence exiting the 'validateTheCustomizationForDataColumns() method'");
                    return true;
                }else{
                    reports.writeResult(oBrowser, "Pass", "Records were retrieved from the '" + System.getProperty("environment") + "' DB");
                }

                index = 2;
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Completed_Link));
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_LoadData_Btn, "Clickable", "", waitTimeOut);
                if (appInd.createAndGetWebElement(oBrowser, PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Company_Edit).getAttribute("placeholder").equalsIgnoreCase("Select Some Options")) {
                    Assert.assertTrue(appInd.setMultipleValues(oBrowser, PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Company_Edit, PayCRM_Modules_ManageUIPage.obj_NewUserSetup_CreateUser_Groups_SearchResult_Label+"/li", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("company_name").toString()));
                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_LoadData_Btn));
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                    appDep.threadSleep(4000);
                }

                columns = oBrowser.findElements(By.xpath(PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Completed_Columns)).size();
                for (int i = 0; i < columns; i++) {
                    columnNamesBefore += appInd.createAndGetWebElement(oBrowser, By.xpath("(" + PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Completed_Columns + ")[" + (i + 1) + "]")).getText() + "#";
                }
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Completed_ColumnChooser_btn));
                appDep.waitForTheElementState(oBrowser, By.xpath("(" + PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_ColumnChooser_close_Btn + ")[" + index + "]"), "clickable", "", waitTimeOut);
            } else {
                index = 1;
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Loaded_Tab_Link));
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
                appDep.threadSleep(2000);
                columns = oBrowser.findElements(By.xpath(PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Loaded_Columns)).size();
                for (int i = 0; i < columns; i++) {
                    columnNamesBefore += appInd.createAndGetWebElement(oBrowser, By.xpath("(" + PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Loaded_Columns + ")[" + (i + 1) + "]")).getText() + "#";
                }
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Loaded_ColumnChooser_btn));
                appDep.waitForTheElementState(oBrowser, By.xpath("(" + PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_ColumnChooser_close_Btn + ")[" + index + "]"), "clickable", "", waitTimeOut);
            }

            reports.writeResult(oBrowser, "Screenshot", "Before Customizing column names from '" + duplicateSupplierTabName + "' section");
            arrColumnToChoose = "Supplier Name#Source#TIN".split("#");
            for (int i = 0; i < arrColumnToChoose.length; i++) {
                appDep.waitForTheElementState(oBrowser, By.xpath("(//li[@aria-label='" + arrColumnToChoose[i] + "']/div[@role='checkbox'])[" + index + "]"), "Clickable", "", waitTimeOut);
                WebElement objCheckbox = appInd.createAndGetWebElement(oBrowser, By.xpath("(//li[@aria-label='" + arrColumnToChoose[i] + "']/div[@role='checkbox'])[" + index + "]"));
                if (objCheckbox.getAttribute("aria-checked").equals("true"))
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("(//li[@aria-label='" + arrColumnToChoose[i] + "']/div[@role='checkbox'])[" + index + "]")));
            }
            appDep.threadSleep(4000);
            if (duplicateSupplierTabName.equalsIgnoreCase("Completed")) {
                columns = oBrowser.findElements(By.xpath(PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Completed_Columns)).size();
                for (int i = 0; i < columns; i++) {
                    columnNamesAfter += appInd.createAndGetWebElement(oBrowser, By.xpath("(" + PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Completed_Columns + ")[" + (i + 1) + "]")).getText() + "#";
                }
            } else {
                columns = oBrowser.findElements(By.xpath(PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Loaded_Columns)).size();
                for (int i = 0; i < columns; i++) {
                    columnNamesAfter += appInd.createAndGetWebElement(oBrowser, By.xpath("(" + PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Loaded_Columns + ")[" + (i + 1) + "]")).getText() + "#";
                }
            }

            reports.writeResult(oBrowser, "Screenshot", "After Customizing column names from '" + duplicateSupplierTabName + "' section");

            for (int i = 0; i < arrColumnToChoose.length; i++) {
                blnRes = !columnNamesAfter.contains(arrColumnToChoose[i]);
                if (!blnRes) {
                    reports.writeResult(oBrowser, "Fail", "The column '" + arrColumnToChoose[i] + "' was NOT removed from the 'Duplicate Supplier Management - " + duplicateSupplierTabName + "' grid section");
                    Assert.assertTrue(false, "The column '" + arrColumnToChoose[i] + "' was NOT removed from '" + duplicateSupplierTabName + "' section");
                    break;
                }
            }
            if (blnRes)
                reports.writeResult(oBrowser, "Pass", "The columns '" + Arrays.toString(arrColumnToChoose) + "' were removed from the 'Duplicate Supplier Management - " + duplicateSupplierTabName + "' grid section");


            //Reset all the columns back to normal
            appDep.threadSleep(4000);
            for (int i = 0; i < arrColumnToChoose.length; i++) {
                appDep.waitForTheElementState(oBrowser, By.xpath("(//li[@aria-label='" + arrColumnToChoose[i] + "']/div[@role='checkbox'])[" + index + "]"), "Clickable", "", waitTimeOut);
                WebElement objCheckbox = appInd.createAndGetWebElement(oBrowser, By.xpath("(//li[@aria-label='" + arrColumnToChoose[i] + "']/div[@role='checkbox'])[" + index + "]"));
                if (!objCheckbox.getAttribute("aria-checked").equals("true"))
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("(//li[@aria-label='" + arrColumnToChoose[i] + "']/div[@role='checkbox'])[" + index + "]")));
            }

            appDep.threadSleep(4000);
            String resettingColumns = "";
            if (duplicateSupplierTabName.equalsIgnoreCase("Completed")) {
                columns = oBrowser.findElements(By.xpath(PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Completed_Columns)).size();
                for (int i = 0; i < columns; i++) {
                    resettingColumns += appInd.createAndGetWebElement(oBrowser, By.xpath("(" + PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Completed_Columns + ")[" + (i + 1) + "]")).getText() + "#";
                }
            } else {
                columns = oBrowser.findElements(By.xpath(PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Loaded_Columns)).size();
                for (int i = 0; i < columns; i++) {
                    resettingColumns += appInd.createAndGetWebElement(oBrowser, By.xpath("(" + PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Loaded_Columns + ")[" + (i + 1) + "]")).getText() + "#";
                }
            }

            Set<String> oSet1 = new TreeSet<>(Arrays.asList(resettingColumns.split("#")));
            Set<String> oSet2 = new TreeSet<>(Arrays.asList(columnNamesBefore.split("#")));
            if (oSet1.equals(oSet2)) {
                reports.writeResult(oBrowser, "Pass", "All the columns are reset back to normal");
                Assert.assertTrue(true, "All the columns are reset back to normal");
            } else {
                reports.writeResult(oBrowser, "Fail", "Failed to set the columns back to normal");
                Assert.assertTrue(false, "Failed to reset the columns back to normal");
            }

            reports.writeResult(oBrowser, "Screenshot", "The columns are reset for the 'Duplicate Supplier Management - "+duplicateSupplierTabName+"' grid section");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_ColumnChooser_close_Btn +")["+index+"]")));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateTheCustomizationForDataColumns()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateTheCustomizationForDataColumns()' method. " + e);
            return false;
        } finally {
            arrColumnToChoose = null; columnNamesBefore = null; columnNamesAfter = null; caseDetails = null;
        }
    }



    /********************************************************
     * Method Name      : validateSearchFilterFunctionalities_DuplicateSupplierManagement_Completed_ClientName()
     * Purpose          : User navigate to Duplicate Supplier Management-->Completed page and validate the search filter functionalities for 'Equals','Does not equal','Contains','Does not contain','Starts with','Ends with' & 'Reset'
     * Author           : Gudi
     * Parameters       : oBrowser, clientName, filterType
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateSearchFilterFunctionalities_DuplicateSupplierManagement_Completed_ClientName(WebDriver oBrowser, String clientName, String filterType) {
        boolean filterData = false;
        String cellData = null;
        try {
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Completed_clientName_SearchIcon));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Completed_FilterOptions + "//ul/li//span[text()='" + filterType + "']"), "Clickable", "", waitTimeOut);
            appDep.threadSleep(1000);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Completed_FilterOptions + "//ul/li//span[text()='" + filterType + "']")));
            appDep.threadSleep(1000);
            if (!filterType.equalsIgnoreCase("Reset"))
                Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Completed_ClientName_Search_Edit, clientName));
            appDep.threadSleep(4000);
            reports.writeResult(oBrowser, "Screenshot", "DuplicateSupplierManagement->Completed: Applied filter for '" + filterType + "' for the data '" + clientName + "'");
            int maxRowNum = 15;
            int rowNum = oBrowser.findElements(By.xpath(PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Completed_Grid + "//tr")).size() - 1;
            if((filterType.equalsIgnoreCase("Does not equal") || filterType.equalsIgnoreCase("Does not contain")) && rowNum == 0){
                reports.writeResult(oBrowser, "Pass", "As there is not data for the filter type '"+filterType+"'");
                return true;
            }
            if (rowNum > maxRowNum) rowNum = maxRowNum;

            for (int i = 0; i < rowNum; i++) {
                filterData = true;
                cellData = appInd.createAndGetWebElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Completed_Grid + "//tr[" + (i + 1) + "]/td[1]")).getText();
                switch (filterType.toLowerCase()) {
                    case "equals":
                        Assert.assertTrue(appInd.compareValues(oBrowser, cellData, clientName), "Actual +'" + cellData + "' is NOT EQUAL to expected value + '" + clientName + "' in 'Duplicate Supplier Management->Loaded' Grid filtered value");
                        break;
                    case "does not equal":
                        Assert.assertTrue(!cellData.equalsIgnoreCase(clientName), "Actual +'" + cellData + "' is EQUAL to expected value + '" + clientName + "' in 'Duplicate Supplier Management->Loaded' Grid filtered value");
                        break;
                    case "contains":
                        Assert.assertTrue(cellData.contains(clientName), "Actual +'" + cellData + "' NOT contains the expected value + '" + clientName + "' in 'Duplicate Supplier Management->Loaded' Grid filtered value");
                        break;
                    case "does not contain":
                        Assert.assertFalse(cellData.contains(clientName), "Actual +'" + cellData + "' contains the expected value + '" + clientName + "' in 'Duplicate Supplier Management->Loaded' Grid filtered value");
                        break;
                    case "starts with":
                        Assert.assertTrue(cellData.startsWith(clientName), "Actual +'" + cellData + "' NOT starts with expected value + '" + clientName + "' in 'Duplicate Supplier Management->Loaded' Grid filtered value");
                        break;
                    case "ends with":
                        Assert.assertTrue(cellData.endsWith(clientName), "Actual +'" + cellData + "' NOt ends with expected value + '" + clientName + "' in 'Duplicate Supplier Management->Loaded' Grid filtered value");
                        break;
                    case "reset":
                        Assert.assertTrue(clientName.contains(cellData), "Mis-match in actual +'" + cellData + "' & expected value + '" + clientName + "' in 'Duplicate Supplier Management->Loaded' Grid filtered values");
                        break;
                    default:
                        reports.writeResult(oBrowser, "Fail", "Invalid search filter option '" + filterType + "'. The supported are 'Equals','Does not equal','Contains','Does not contain','Starts with','Ends with' & 'Reset'");
                        return false;
                }
            }

            if (filterData == false) {
                reports.writeResult(oBrowser, "Fail", "No records are found for the applied filter '" + clientName + "' with '" + filterType + "'");
                Assert.assertTrue(false, "No records are found for the applied filter '" + clientName + "' with '" + filterType + "'");
            }
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateSearchFilterFunctionalities_DuplicateSupplierManagement_Completed_ClientName()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateSearchFilterFunctionalities_DuplicateSupplierManagement_Completed_ClientName()' method. " + e);
            return false;
        } finally {cellData = null;}
    }



    /********************************************************
     * Method Name      : validateSearchFiltersOnDuplicateSuppliersCompletedScreen()
     * Purpose          : User navigate to Duplicate Supplier Management-->Completed page and validate the search filter functionalities
     * Author           : Gudi
     * Parameters       : oBrowser, queryKey
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateSearchFiltersOnDuplicateSuppliersCompletedScreen(WebDriver oBrowser, String queryKey) {
        String companyName = null;
        String arrCompanyName[];
        String firstName = null;
        String lastName = null;
        JSONArray caseDetails = null;
        try {
            caseDetails = apiDataProvider.getAPIDataProviderResponse(queryKey, new Object[] {});
            companyName = ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("company_name").toString();

            if(((JSONArray) caseDetails.get(0)).size() == 0){
                reports.writeResult(oBrowser, "Warning", "No records were retrieved from the '" + System.getProperty("environment") + "' DB, \n Hence exiting the 'validateSearchFiltersOnDuplicateSuppliersCompletedScreen() method'");
                return true;
            }else{
                reports.writeResult(oBrowser, "Pass", "Records were retrieved from the '" + System.getProperty("environment") + "' DB");
            }

            if (!appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManageUIPage.obj_MonetizationTracking_Page_Header, "Text").contains("Duplicate Supplier Management"))
                Assert.assertTrue(appDep.selectManageModules(oBrowser, "Duplicate Supplier Management"));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Completed_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_LoadData_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.setMultipleValues(oBrowser, PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Company_Edit, PayCRM_Modules_ManageUIPage.obj_NewUserSetup_CreateUser_Groups_SearchResult_Label+"/li", companyName));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_LoadData_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appDep.threadSleep(4000);
            reports.writeResult(oBrowser, "Screenshot", "Selected the following companies: " + companyName.replace("#", ", "));
            appDep.threadSleep(2000);
            int maxRowNum = 15;
            int rowNum = oBrowser.findElements(By.xpath(PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Completed_Grid + "//tr")).size() - 1;
            if (rowNum > maxRowNum) rowNum = maxRowNum;
            for (int i = 0; i < rowNum; i++) {
                String cellData = appInd.createAndGetWebElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Completed_Grid + "//tr[" + (i + 1) + "]/td[1]")).getText();
                Assert.assertTrue(companyName.contains(cellData), "The actual '" + cellData + "' value is NOT present in the expected '" + companyName + "' value");
            }

            if(companyName.contains(" ")){
                arrCompanyName = companyName.split(" ");
                firstName = arrCompanyName[0];
                lastName = arrCompanyName[1];
            }else{
                firstName = companyName.substring(0, 5);
                lastName = companyName.substring(5);
            }
            Assert.assertTrue(validateSearchFilterFunctionalities_DuplicateSupplierManagement_Completed_ClientName(oBrowser, companyName, "Equals"));
            Assert.assertTrue(validateSearchFilterFunctionalities_DuplicateSupplierManagement_Completed_ClientName(oBrowser, companyName, "Does not equal"));
            Assert.assertTrue(validateSearchFilterFunctionalities_DuplicateSupplierManagement_Completed_ClientName(oBrowser, companyName, "Contains"));
            Assert.assertTrue(validateSearchFilterFunctionalities_DuplicateSupplierManagement_Completed_ClientName(oBrowser, companyName, "Does not contain"));
            Assert.assertTrue(validateSearchFilterFunctionalities_DuplicateSupplierManagement_Completed_ClientName(oBrowser, firstName, "Starts with"));
            Assert.assertTrue(validateSearchFilterFunctionalities_DuplicateSupplierManagement_Completed_ClientName(oBrowser, lastName, "Ends with"));
            Assert.assertTrue(validateSearchFilterFunctionalities_DuplicateSupplierManagement_Completed_ClientName(oBrowser, companyName, "Reset"));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateSearchFiltersOnDuplicateSuppliersCompletedScreen()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateSearchFiltersOnDuplicateSuppliersCompletedScreen()' method. " + e);
            return false;
        } finally {
            companyName = null; arrCompanyName = null; firstName = null; lastName = null; caseDetails = null;
        }
    }



    /********************************************************
     * Method Name      : validateSearchFilterFunctionalities_DuplicateSupplierManagement_Loaded_ClientName()
     * Purpose          : User navigate to Duplicate Supplier Management-->Loaded page and validate the search filter functionalities for 'Equals' & 'Does not equal'
     * Author           : Gudi
     * Parameters       : oBrowser, clientName, filterType
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateSearchFilterFunctionalities_DuplicateSupplierManagement_Loaded_ClientName(WebDriver oBrowser, String clientName, String filterType) {
        boolean filterData = false;
        String cellData = null;
        try {
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+PayCRM_Modules_ManageUIPage.obj_DuplicateSupplierManagement_Loaded_Grid + "//tr//li//div[@aria-haspopup])[1]")));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Loaded_FilterOptions +"//ul/li//span[text()='"+filterType+"']"), "Clickable", "", minTimeOut);
            appDep.threadSleep(1000);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Loaded_FilterOptions +"//ul/li//span[text()='"+filterType+"']")));
            appDep.threadSleep(1000);
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_DuplicateSupplier_Loaded_ClientName_Search_Edit, clientName));
            appDep.threadSleep(4000);
            reports.writeResult(oBrowser, "Screenshot", "DuplicateSupplierManagement->Loaded: Applied filter for '" + filterType + "' for the data '" + clientName + "'");
            int maxRowNum = 15;
            int rowNum = oBrowser.findElements(By.xpath(PayCRM_Modules_ManageUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//tr")).size() - 1;
            if (rowNum > maxRowNum) rowNum = maxRowNum;

            for (int i = 0; i < rowNum - 1; i++) {
                filterData = true;
                cellData = appInd.createAndGetWebElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Suppliers_SearchResultGrid_rows + "/table//tr[" + (i + 1) + "]/td[1]")).getText();
                switch (filterType.toLowerCase()) {
                    case "equals":
                        Assert.assertTrue(appInd.compareValues(oBrowser, cellData, clientName), "Mis-match in actual +'" + cellData + "' & expected value + '" + clientName + "' in 'Duplicate Supplier Management->Loaded' Grid filtered values");
                        break;
                    case "does not equal":
                        Assert.assertTrue(!cellData.equalsIgnoreCase(clientName), "Match in actual +'" + cellData + "' & expected value + '" + clientName + "' in 'Duplicate Supplier Management->Loaded' Grid filtered values");
                        break;
                    default:
                        reports.writeResult(oBrowser, "Fail", "Invalid search filter option '" + filterType + "'. The supported are 'Equals' & 'Does not equal'");
                        return false;
                }
            }

            if (filterData == false) {
                reports.writeResult(oBrowser, "Fail", "No records are found for the applied filter '" + clientName + "' with '" + filterType + "'");
                Assert.assertTrue(false, "No records are found for the applied filter '" + clientName + "' with '" + filterType + "'");
            }
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateSearchFilterFunctionalities_DuplicateSupplierManagement_Loaded_ClientName()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateSearchFilterFunctionalities_DuplicateSupplierManagement_Loaded_ClientName()' method. " + e);
            return false;
        } finally {cellData = null;}
    }



    /********************************************************
     * Method Name      : validateSearchFiltersOnDuplicateSuppliersLoadedScreen()
     * Purpose          : User navigate to Duplicate Supplier Management-->Loaded page and validate the search filter functionalities
     * Author           : Gudi
     * Parameters       : oBrowser, queryKey
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateSearchFiltersOnDuplicateSuppliersLoadedScreen(WebDriver oBrowser, String queryKey) {
        String clientName = null;
        JSONArray caseDetails = null;
        try {
            caseDetails = apiDataProvider.getAPIDataProviderResponse(queryKey, new Object[] {});

            if(((JSONArray) caseDetails.get(0)).size() ==0){
                reports.writeResult(oBrowser, "Warning", "No records were retrieved from the '" + System.getProperty("environment") + "' DB, \n Hence exiting the 'validateSearchFiltersOnDuplicateSuppliersLoadedScreen() method'");
                return true;
            }else{
                reports.writeResult(oBrowser, "Pass", "Records were retrieved from the '" + System.getProperty("environment") + "' DB");
            }

            clientName = ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("company_name").toString();
            Assert.assertTrue(appDep.selectManageModules(oBrowser, "Duplicate Supplier Management"));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            Assert.assertTrue(validateSearchFilterFunctionalities_DuplicateSupplierManagement_Loaded_ClientName(oBrowser, clientName, "Equals"));
            Assert.assertTrue(validateSearchFilterFunctionalities_DuplicateSupplierManagement_Loaded_ClientName(oBrowser, clientName, "Does not equal"));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateSearchFiltersOnDuplicateSuppliersLoadedScreen()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateSearchFiltersOnDuplicateSuppliersLoadedScreen()' method. " + e);
            return false;
        } finally {clientName = null; caseDetails = null;}
    }

    /********************************************************
     * Method Name      : validateSearchFiltersOnDuplicateSuppliersLoadedScreen()
     * Purpose          : User navigate to Duplicate Supplier Management-->Loaded page and validate the search filter functionalities
     * Author           : Deepak
     * Parameters       : oBrowser, queryKey
     * ReturnType       : boolean
     ********************************************************/
    public boolean verifyDSMNewDesignedPage(WebDriver oBrowser){
        String totalRows = "(//div[@id='divDuplicateSuppliersContainer']//table)[2]//tr";
        String dsm_tabs = "//a[@aria-controls='%s']";
        String tableChildHeader = "//table[@id='%s']/thead//th[text()='%s']";
        String tableParentHeader = "//div[@id='%s']//thead//th[text()='%s']";
        String tableMergedHeader = "//div[@id='%s']//thead//th[text()='%s']";
        String tableFirstRowRecord = "//table[@id='%s']/tbody/tr[1]/td[%s]";

        try {
            Assert.assertTrue(appDep.selectManageModules(oBrowser, "Duplicate Supplier Management"));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "screenshot", "Duplicate Supplier Management Page is open");
            int size = appInd.getWebElements(oBrowser, By.xpath(totalRows)).size() > 14 ?  14 : appInd.getWebElements(oBrowser, By.xpath(totalRows)).size();
            int index = Integer.parseInt(appInd.generateRandomNumbers(1, size, 1));
            String supplier = appInd.getTextOnElement(oBrowser, By.xpath(totalRows+"["+index+"]"+"/td[2]"), "text");
            appInd.clickObject(oBrowser, By.xpath(totalRows+"["+index+"]"+"/td[1]"));
            appDep.threadSleep(1000);
            appInd.switchBetweenTabsOrWindow(oBrowser);
            reports.writeResult(oBrowser, "screenshot", "Successfully clicked on supplier record.");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.id("btn_do_not_merge_suppliers")), "Do Not Merge button is not present");
            //---------------------- VERIFY TAB TEXT SHOULD PRESENTS ------------------
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath("//a[@id='btn_do_not_merge_suppliers']/../div"), "text"), supplier);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(dsm_tabs, "merging_child"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(dsm_tabs, "contacts"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(dsm_tabs, "addresses"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(dsm_tabs, "company_suppliers"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(dsm_tabs, "preview_changes"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(dsm_tabs, "complete"))));
            reports.writeResult(oBrowser, "screenshot", "Verified all header tabs successfully");
            //--------------------- VERIFY CHILD GRID HEADERS -----------------------------
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_child_record", "Name"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_child_record", "TIN"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_child_record", "Vendor ID(s)"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_child_record", "Phone"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_child_record", "PayNet ID"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_child_record", "Status"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_child_record", "Supplier Type"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_child_record", "Created By"))));
            reports.writeResult(oBrowser, "screenshot", "Verified Child Grid table");
            //--------------------- VERIFY PARENT GRID HEADERS -----------------------------
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableParentHeader, "table_parent_record_wrapper", "Name"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableParentHeader, "table_parent_record_wrapper", "TIN"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableParentHeader, "table_parent_record_wrapper", "Vendor ID(s)"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableParentHeader, "table_parent_record_wrapper", "Phone"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableParentHeader, "table_parent_record_wrapper", "PayNet ID"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableParentHeader, "table_parent_record_wrapper", "Status"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableParentHeader, "table_parent_record_wrapper", "Supplier Type"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableParentHeader, "table_parent_record_wrapper", "Matched Description"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableParentHeader, "table_parent_record_wrapper", "Matched Value"))));
            reports.writeResult(oBrowser, "screenshot", "Verified Parent Grid table");
            //--------------------- VERIFY MERGED GRID HEADERS -----------------------------
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableMergedHeader,"table_merged_suppliers_wrapper", "Name"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableMergedHeader,"table_merged_suppliers_wrapper", "TIN"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableMergedHeader,"table_merged_suppliers_wrapper", "Primary Phone"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableMergedHeader,"table_merged_suppliers_wrapper", "Status"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableMergedHeader,"table_merged_suppliers_wrapper", "Supplier Type"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableMergedHeader,"table_merged_suppliers_wrapper", "PayNet ID"))));
            reports.writeResult(oBrowser, "screenshot", "Verified Merged Grid table");
            //-------------------------------- SELECT SUPPLIER FROM PARENTS GRID AND VERIFY VALUE WITH MERGED SUPPLIER GRID --------------------------------
            int row = appInd.getWebElements(oBrowser, By.xpath("//table[@id='table_parent_record']/tbody/tr")).size();
            if(row<1){
                reports.writeResult(oBrowser, "screenshot", "Parents grid don't have any supplier record");
                return false;
            }
            appInd.clickObject(oBrowser,By.xpath(String.format(tableFirstRowRecord, "table_parent_record","1")+"//input"));
            appDep.threadSleep(300);
            List<String> parentSupplierDetailList = new ArrayList<>();
            parentSupplierDetailList.add(appInd.getTextOnElement(oBrowser, By.xpath(String.format(tableFirstRowRecord,"table_parent_record", "2")), "text"));
            parentSupplierDetailList.add(appInd.getTextOnElement(oBrowser, By.xpath(String.format(tableFirstRowRecord,"table_parent_record", "3")), "text"));
            parentSupplierDetailList.add(appInd.getTextOnElement(oBrowser, By.xpath(String.format(tableFirstRowRecord,"table_parent_record", "5")), "text"));
            parentSupplierDetailList.add(appInd.getTextOnElement(oBrowser, By.xpath(String.format(tableFirstRowRecord,"table_parent_record", "6")), "text"));
            parentSupplierDetailList.add(appInd.getTextOnElement(oBrowser, By.xpath(String.format(tableFirstRowRecord,"table_parent_record", "7")), "text"));
            parentSupplierDetailList.add(appInd.getTextOnElement(oBrowser, By.xpath(String.format(tableFirstRowRecord,"table_parent_record", "8")), "text"));

            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(tableFirstRowRecord, "table_parent_record", "2")), "text"), parentSupplierDetailList.get(0));

            appInd.clickObject(oBrowser, By.xpath("//div[@id='merge_button_row' and @data-step='1']//div[@class='btn-miller-primary btnContinue']"));
            //---------------------------------------- CLICK ON CONTINUE AND VERIFY CONTACT, ADDRESS, CLIENT LINKS TAB PAGE -----------------------------------
            reports.writeResult(oBrowser, "screenshot", "Start verifying contacts tab");
            Assert.assertTrue(verifyDSMTabs(oBrowser, "contacts"));
            reports.writeResult(oBrowser, "screenshot", "contacts verification complete successfully");
            Assert.assertTrue(verifyDSMTabs(oBrowser, "addresses"));
            reports.writeResult(oBrowser, "screenshot", "addresses verification complete successfully");
            Assert.assertTrue(verifyDSMTabs(oBrowser, "company_suppliers"));
            reports.writeResult(oBrowser, "screenshot", "Client Links verification complete successfully");
            //--------------------------------------- CLICK ON CONTINUE AND VERIFY PREVIEW CHANGES TAB --------------------------------
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//div[@id='preview_changes' and contains(@class,'active')]")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_parent_record_preview", "Matched Description"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_parent_record_preview", "Name"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_parent_record_preview", "TIN"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_parent_record_preview", "Phone"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_parent_record_preview", "PayNet ID"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_parent_record_preview", "Status"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_parent_record_preview", "Matched Value"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_parent_record_preview", "Supplier Type"))));

            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_merged_contacts_preview", "Name"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_merged_contacts_preview", "Type"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_merged_contacts_preview", "Phone 1"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_merged_contacts_preview", "Phone 2"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_merged_contacts_preview", "Email"))));

            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_merged_addresses_preview", "Type"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_merged_addresses_preview", "Address 1"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_merged_addresses_preview", "City"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_merged_addresses_preview", "State/Province"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_merged_addresses_preview", "Postal Code"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_merged_addresses_preview", "Country"))));

            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_merged_links_preview", "Client Name"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_merged_links_preview", "Wave"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_merged_links_preview", "Supplier Type"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_merged_links_preview", "Vendor IDs"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_merged_links_preview", "Link ID"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_merged_links_preview", "Status"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_merged_links_preview", "# Checks"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(tableChildHeader, "table_merged_links_preview", "Total Spend"))));

            reports.writeResult(oBrowser, "screenshot", "Preview verification complete successfully");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//label[contains(text(),'Confirm Merge')]")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//div[@id='merge_button_row_final' and contains(@class,'disabled')]")));
            appInd.clickObject(oBrowser, By.xpath("//label[contains(text(),'Confirm Merge')]"));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//div[@id='merge_button_row_final' and contains(@class,'success')]")));
            appInd.clickObject(oBrowser, By.xpath("//div[@id='merge_button_row_final' and contains(@class,'success')]"));
            reports.writeResult(oBrowser, "screenshot", "Merged Button verification complete successfully");
            //------------------------------------- VERIFY RESULT TAB AND GRIDS -------------------------------------
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//div[@id='complete' and contains(@class,'active')]")));
            reports.writeResult(oBrowser, "screenshot", "Result page is display successfully");
            Assert.assertEquals(appInd.getWebElements(oBrowser, By.xpath("//div[@class='text-header-miller']/a")).size(), 2);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//div[@id='table_merge_results_wrapper']//table//th/div[text()='Company Supplier ID']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//div[@id='table_merge_results_wrapper']//table//th/div[text()='Merged']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//div[@id='table_merge_results_wrapper']//table//th/div[text()='Outcome']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//div[@id='table_merge_results_wrapper']//table//th/div[text()='Enrollment Case Created']")));

            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//table[@id='table_parent_record_history']/thead//th[text()='Matched Description']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//table[@id='table_parent_record_history']/thead//th[text()='Matched Value']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//table[@id='table_parent_record_history']/thead//th[text()='Name']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//table[@id='table_parent_record_history']/thead//th[text()='TIN']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//table[@id='table_parent_record_history']/thead//th[text()='Phone']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//table[@id='table_parent_record_history']/thead//th[text()='PayNet ID']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//table[@id='table_parent_record_history']/thead//th[text()='Status']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//table[@id='table_parent_record_history']/thead//th[text()='Supplier Type']")));

            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//table[@id='table_merged_contacts_history']/thead//th[text()='Name']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//table[@id='table_merged_contacts_history']/thead//th[text()='Type']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//table[@id='table_merged_contacts_history']/thead//th[text()='Phone 1']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//table[@id='table_merged_contacts_history']/thead//th[text()='Phone 2']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//table[@id='table_merged_contacts_history']/thead//th[text()='Email']")));

            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//table[@id='table_merged_addresses_history']/thead//th[text()='Type']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//table[@id='table_merged_addresses_history']/thead//th[text()='Address 1']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//table[@id='table_merged_addresses_history']/thead//th[text()='State/Province']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//table[@id='table_merged_addresses_history']/thead//th[text()='Postal Code']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//table[@id='table_merged_addresses_history']/thead//th[text()='Country']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//table[@id='table_merged_addresses_history']/thead//th[text()='City']")));

            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//table[@id='table_merged_links_history']/thead//th[text()='Client Name']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//table[@id='table_merged_links_history']/thead//th[text()='Wave']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//table[@id='table_merged_links_history']/thead//th[text()='Supplier Type']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//table[@id='table_merged_links_history']/thead//th[text()='Vendor IDs']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//table[@id='table_merged_links_history']/thead//th[text()='Link ID']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//table[@id='table_merged_links_history']/thead//th[text()='Status']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//table[@id='table_merged_links_history']/thead//th[text()='# Checks']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//table[@id='table_merged_links_history']/thead//th[text()='Total Spend']")));
            reports.writeResult(oBrowser, "screenshot", "Result Page Verification Complete Successfully");
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifyDSMNewDesignedPage()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifyDSMNewDesignedPage()' method. " + e);
            return false;
        }
    }

    private boolean verifyDSMTabs(WebDriver oBrowser, String tabName){
        tabName = tabName.equalsIgnoreCase("company_suppliers")?"links":tabName;
        tabName = tabName.toLowerCase().trim();
        boolean isChildContact = false, isParentContact=false;
        List<String> dsm_contacts_child_list = new ArrayList<>();
        List<String> dsm_contacts_parent_list = new ArrayList<>();
        try{
            appDep.threadSleep(300);
            appInd.verifyElementPresent(oBrowser, By.xpath("//div[@id='"+tabName+"' and contains(@class,'active')]"));
            if(!oBrowser.findElements(By.xpath("//table[@id='table_child_" + tabName + "']/tbody/tr/td[2]")).isEmpty()){
                isChildContact=true;
                appInd.clickObject(oBrowser, By.xpath("//table[@id='table_child_"+tabName+"']/tbody/tr[1]/td[1]//input"));
                if(tabName.equalsIgnoreCase("links")){
                    dsm_contacts_child_list.add(appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_child_"+tabName+"']/tbody/tr[1]/td[2]"), "text"));
                    dsm_contacts_child_list.add(appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_child_"+tabName+"']/tbody/tr[1]/td[3]"), "text"));
                    dsm_contacts_child_list.add(appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_child_"+tabName+"']/tbody/tr[1]/td[4]"), "text"));
                    dsm_contacts_child_list.add(appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_child_"+tabName+"']/tbody/tr[1]/td[5]"), "text"));
                    dsm_contacts_child_list.add(appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_child_"+tabName+"']/tbody/tr[1]/td[6]"), "text"));
                    dsm_contacts_child_list.add(appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_child_"+tabName+"']/tbody/tr[1]/td[7]"), "text"));
                    dsm_contacts_child_list.add(appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_child_"+tabName+"']/tbody/tr[1]/td[8]"), "text"));
                }else{
                    dsm_contacts_child_list.add(appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_child_"+tabName+"']/tbody/tr[1]/td[2]"), "text"));
                    dsm_contacts_child_list.add(appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_child_"+tabName+"']/tbody/tr[1]/td[3]"), "text"));
                    dsm_contacts_child_list.add(appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_child_"+tabName+"']/tbody/tr[1]/td[4]"), "text"));
                    dsm_contacts_child_list.add(appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_child_"+tabName+"']/tbody/tr[1]/td[5]"), "text"));
                    dsm_contacts_child_list.add(appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_child_"+tabName+"']/tbody/tr[1]/td[6]"), "text"));
                }
            }
            reports.writeResult(oBrowser, "screenshot", tabName+"  Child table verification complete successfully");
            if(appInd.verifyElementPresent(oBrowser, By.xpath("//table[@id='table_parent_"+tabName+"']/tbody/tr/td[2]"))){
                isParentContact=true;
                appInd.clickObject(oBrowser, By.xpath("//table[@id='table_parent_"+tabName+"']/tbody/tr[1]/td[1]//input"));
                if(tabName.equalsIgnoreCase("links")){
                    dsm_contacts_parent_list.add(appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_parent_"+tabName+"']/tbody/tr[1]/td[2]"), "text"));
                    dsm_contacts_parent_list.add(appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_parent_"+tabName+"']/tbody/tr[1]/td[3]"), "text"));
                    dsm_contacts_parent_list.add(appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_parent_"+tabName+"']/tbody/tr[1]/td[4]"), "text"));
                    dsm_contacts_parent_list.add(appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_parent_"+tabName+"']/tbody/tr[1]/td[5]"), "text"));
                    dsm_contacts_parent_list.add(appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_parent_"+tabName+"']/tbody/tr[1]/td[6]"), "text"));
                    dsm_contacts_parent_list.add(appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_parent_"+tabName+"']/tbody/tr[1]/td[7]"), "text"));
                    dsm_contacts_parent_list.add(appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_parent_"+tabName+"']/tbody/tr[1]/td[8]"), "text"));

                }else{
                    dsm_contacts_parent_list.add(appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_parent_"+tabName+"']/tbody/tr[1]/td[2]"), "text"));
                    dsm_contacts_parent_list.add(appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_parent_"+tabName+"']/tbody/tr[1]/td[3]"), "text"));
                    dsm_contacts_parent_list.add(appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_parent_"+tabName+"']/tbody/tr[1]/td[4]"), "text"));
                    dsm_contacts_parent_list.add(appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_parent_"+tabName+"']/tbody/tr[1]/td[5]"), "text"));
                    dsm_contacts_parent_list.add(appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_parent_"+tabName+"']/tbody/tr[1]/td[6]"), "text"));
                }
            }
            reports.writeResult(oBrowser, "screenshot", tabName+"  Parent table verification complete successfully");
            if(isChildContact){
                int i = 1;
                if(tabName.equalsIgnoreCase("links")){
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(".//div[@class='alert blurb-info']")));
                    Assert.assertEquals(dsm_contacts_child_list.get(0), appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_merged_"+tabName+"']/tbody/tr["+i+"]/td[1]//input"), "value"));
                    Assert.assertEquals(dsm_contacts_child_list.get(2), appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_merged_"+tabName+"']/tbody/tr["+i+"]/td[3]//input"), "value"));
                    Assert.assertEquals(dsm_contacts_child_list.get(3), appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_merged_"+tabName+"']/tbody/tr["+i+"]/td[4]//input"), "value"));
                    Assert.assertEquals(dsm_contacts_child_list.get(4), appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_merged_"+tabName+"']/tbody/tr["+i+"]/td[5]//input"), "value"));
                    Assert.assertEquals(dsm_contacts_child_list.get(5), appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_merged_"+tabName+"']/tbody/tr["+i+"]/td[6]//input"), "value"));
                }else{
                    //Assert.assertEquals(dsm_contacts_child_list.get(0), appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_merged_"+tabName+"']/tbody/tr[1]/td[1]"), "text"));
                    //Assert.assertEquals(dsm_contacts_child_list.get(1), appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_merged_contacts']/tbody/tr[1]/td[2]"), "text"));
                    Assert.assertEquals(dsm_contacts_child_list.get(2), appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_merged_"+tabName+"']/tbody/tr[1]/td[3]"), "text"));
                    Assert.assertEquals(dsm_contacts_child_list.get(3), appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_merged_"+tabName+"']/tbody/tr[1]/td[4]"), "text"));
                    Assert.assertEquals(dsm_contacts_child_list.get(4), appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_merged_"+tabName+"']/tbody/tr[1]/td[5]"), "text"));
                }
            }
            if(isParentContact){
                int i = isChildContact?2:1;
                if(tabName.equalsIgnoreCase("links")){
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(".//div[@class='alert blurb-info']")));
                    Assert.assertEquals(dsm_contacts_parent_list.get(0), appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_merged_"+tabName+"']/tbody/tr["+i+"]/td[1]//input"), "value"));
                    Assert.assertEquals(dsm_contacts_parent_list.get(2), appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_merged_"+tabName+"']/tbody/tr["+i+"]/td[3]//input"), "value"));
                    Assert.assertEquals(dsm_contacts_parent_list.get(3), appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_merged_"+tabName+"']/tbody/tr["+i+"]/td[4]//input"), "value"));
                    Assert.assertEquals(dsm_contacts_parent_list.get(4), appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_merged_"+tabName+"']/tbody/tr["+i+"]/td[5]//input"), "value"));
                    Assert.assertEquals(dsm_contacts_parent_list.get(5), appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_merged_"+tabName+"']/tbody/tr["+i+"]/td[6]//input"), "value"));
                    Assert.assertEquals(dsm_contacts_parent_list.get(6), appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_merged_"+tabName+"']/tbody/tr["+i+"]/td[7]//input"), "value"));
                }else{
                    Assert.assertEquals(dsm_contacts_parent_list.get(0), appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_merged_"+tabName+"']/tbody/tr["+i+"]/td[1]"), "text"));
                    Assert.assertEquals(dsm_contacts_parent_list.get(1), appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_merged_"+tabName+"']/tbody/tr["+i+"]/td[2]"), "text"));
                    Assert.assertEquals(dsm_contacts_parent_list.get(2), appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_merged_"+tabName+"']/tbody/tr["+i+"]/td[3]"), "text"));
                    Assert.assertEquals(dsm_contacts_parent_list.get(3), appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_merged_"+tabName+"']/tbody/tr["+i+"]/td[4]"), "text"));
                    Assert.assertEquals(dsm_contacts_parent_list.get(4), appInd.getTextOnElement(oBrowser, By.xpath("//table[@id='table_merged_"+tabName+"']/tbody/tr["+i+"]/td[5]"), "text"));
                }
            }
            reports.writeResult(oBrowser, "screenshot", tabName+" verification complete successfully");
            //Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath("//div[@id='alert_primary_"+tabName+"']/div"), "text"), "Two primary contacts found. Parent contact will take precedence.");
            if(tabName.equalsIgnoreCase("links")){
                appInd.clickObject(oBrowser, By.xpath("//div[@id='company_suppliers' and contains(@class,'active')]//div[@class='btn-miller-primary btnContinue']"));
            }else{
                appInd.clickObject(oBrowser, By.xpath("//div[@id='"+tabName+"' and contains(@class,'active')]//div[@class='btn-miller-primary btnContinue']"));
            }
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifyDSMTabs()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifyDSMTabs()' method. " + e);
            return false;
        }
        return true;
    }

    /********************************************************
     * Method Name      : navigateToMonetizationTrackingAndValidateDashboard()
     * Purpose          : User navigate to Monetization Tracking page and validate the dashboard
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean navigateToMonetizationTrackingAndValidateDashboard(WebDriver oBrowser) {
        try {
            Assert.assertTrue(appDep.selectManageModules(oBrowser, "Monetization Tracking"));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManageUIPage.obj_MonetizationTracking_Page_Header, "Text").contains("» Monetization Tracking"));
            Assert.assertTrue(appInd.createAndGetWebElement(oBrowser, PayCRM_Modules_ManageUIPage.obj_MonetizationTracking_Dashboard_Link).getAttribute("class").equalsIgnoreCase("nav-link active"));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_ManageUIPage.obj_MonetizationTracking_Dashboard_Header, "Text", "Spend Efficiency"));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'navigateToMonetizationTrackingAndValidateDashboard()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'navigateToMonetizationTrackingAndValidateDashboard()' method. " + e);
            return false;
        }
    }



    /********************************************************
     * Method Name      : validateRoleDetails()
     * Purpose          : to verify role details
     * Author           : Gudi
     * Parameters       : oBrowser, roleStatus, rolesData
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateRoleDetails(WebDriver oBrowser, String roleStatus) {
        String selectedPermissions = "";
        String unselectedPermissions = "";
        int end = 0;
        try {
            reports.writeResult(oBrowser, "Screenshot", "The role permission screen after '" + roleStatus + "' role");
            appInd.appInd.scrollToThePage(oBrowser, "Top");
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Manage_RoleManager_Permission_Header), "Visibility", "", waitTimeOut);

            if (roleStatus.equalsIgnoreCase("New")) {
                end = 5;
                for (int i = 0; i < end; i++) {
                    Assert.assertTrue(verifyCheckboxStatus(oBrowser, By.xpath("(" + PayCRM_Modules_ManageUIPage.obj_Manage_RolePermission_ChkBox + "/li/input)[" + (i + 1) + "]"), "Checked"));
                    selectedPermissions += appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_ManageUIPage.obj_Manage_RolePermission_ChkBox + "/li)[" + (i + 1) + "]"), "Text") + "\n";
                }
                reports.writeResult(oBrowser, "Pass", "The following permission '" + selectedPermissions + "' are selected for the role '" + newRoleDesc + "'");
            } else {
                end = 10;
                for (int i = 0; i < end; i++) {
                    if (i < 5) {
                        Assert.assertTrue(verifyCheckboxStatus(oBrowser, By.xpath("(" + PayCRM_Modules_ManageUIPage.obj_Manage_RolePermission_ChkBox + "/li/input)[" + (i + 1) + "]"), "Unchecked"));
                        unselectedPermissions += appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_ManageUIPage.obj_Manage_RolePermission_ChkBox + "/li)[" + (i + 1) + "]"), "Text") + "\n";
                    } else {
                        Assert.assertTrue(verifyCheckboxStatus(oBrowser, By.xpath("(" + PayCRM_Modules_ManageUIPage.obj_Manage_RolePermission_ChkBox + "/li/input)[" + (i + 1) + "]"), "Checked"));
                        selectedPermissions += appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_ManageUIPage.obj_Manage_RolePermission_ChkBox + "/li)[" + (i + 1) + "]"), "Text") + "\n";
                    }
                }
                reports.writeResult(oBrowser, "Pass", "The following permission '" + selectedPermissions + "' are selected for the role '" + newRoleDesc + "'");
                reports.writeResult(oBrowser, "Pass", "The following permission '" + unselectedPermissions + "' are unselected for the role '" + newRoleDesc + "'");
            }

            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateRoleDetails()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateRoleDetails()' method. " + e);
            return false;
        } finally {selectedPermissions = null; unselectedPermissions = null;}
    }



    /********************************************************
     * Method Name      : verifyCheckboxStatus()
     * Purpose          : to verify that the permission checkbox status viz., Checked/UnChecked
     * Author           : Gudi
     * Parameters       : oBrowser, objBy, status
     * ReturnType       : boolean
     ********************************************************/
    public boolean verifyCheckboxStatus(WebDriver oBrowser, By objBy, String status) {
        try {
            switch (status.toLowerCase()) {
                case "checked":
                    Assert.assertTrue(appInd.createAndGetWebElement(oBrowser, objBy).isSelected());
                    break;
                case "unchecked":
                    Assert.assertFalse(appInd.createAndGetWebElement(oBrowser, objBy).isSelected());
                    break;
                default:
                    reports.writeResult(oBrowser, "Fail", "Invalid checkbox status '" + status + "'. Provide the supported viz., Checked OR Unchecked");
                    return false;
            }
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifyCheckboxStatus()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifyCheckboxStatus()' method. " + e);
            return false;
        }
    }



    /********************************************************
     * Method Name      : createNewRole()
     * Purpose          : to verify that the use can create the new role under Modules->Manage->User Console-->Role Manage.
     * Author           : Gudi
     * Parameters       : oBrowser, rolesData
     * ReturnType       : Map<String, String>
     ********************************************************/
    public Map<String, String> createNewRole(WebDriver oBrowser, Map<String, String> rolesData) {
        String permissionName = "";
        boolean blnRes = false;
        try {
            Assert.assertTrue(appDep.selectManageModules(oBrowser, "User Console"));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_Manage_UserConsole_RoleManager_Link));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_NewUserSetup_RoleManager_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_Manage_UserConsole_RoleManager_NewRole_btn));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_Manage_CreateNewRole_Dialog_Header, "Text", "Create New Role", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Creating new Role");
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_ManageUIPage.obj_Manage_CreateNewRole_Dialog_Header, "Text", "Create New Role"));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_Manage_CreateNewRole_RoleName_Edit, rolesData.get("RoleName")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_Manage_CreateNewRole_RoleDescription_Edit, rolesData.get("Description")));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_Manage_CreateNewRole_Create_Btn));
            appDep.threadSleep(4000);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Manage_RoleManager_Permission_Header + "/span[contains(text(), '" + rolesData.get("Description") + "')]"), "Visibility", "", waitTimeOut);
            appInd.scrollToElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Manage_RoleManager_Roles_Label + "/li[text()='" + rolesData.get("Description") + "']/preceding-sibling::li[2]"));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Manage_RoleManager_Permission_Header), "Text").contains(rolesData.get("Description") + " - Permissions"));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "The new Role was created successful");
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Manage_RoleManager_Roles_Label + "/li[text()='" + rolesData.get("Description") + "']"), "Text", rolesData.get("Description")));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Manage_RoleManager_Roles_Label + "/li[text()='" + rolesData.get("Description") + "']/button")));
            appInd.appInd.scrollToThePage(oBrowser, "Top");
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Manage_RoleManager_Permission_Header), "Visibility", "", waitTimeOut);

            for (int i = 0; i < 5; i++) {
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("(" + PayCRM_Modules_ManageUIPage.obj_Manage_RolePermission_ChkBox + "/li/input)[" + (i + 1) + "]")));
                if (verifyCheckboxStatus(oBrowser, By.xpath("(" + PayCRM_Modules_ManageUIPage.obj_Manage_RolePermission_ChkBox + "/li/input)[" + (i + 1) + "]"), "Checked")) {
                    blnRes = true;
                    permissionName += appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_ManageUIPage.obj_Manage_RolePermission_ChkBox + "/li)[" + (i + 1) + "]"), "Text") + "\n";
                } else {
                    blnRes = false;
                    permissionName = appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_ManageUIPage.obj_Manage_RolePermission_ChkBox + "/li)[" + (i + 1) + "]"), "Text");
                    reports.writeResult(oBrowser, "Fail", "Failed to select the '" + permissionName + "' permission checkbox");
                    Assert.assertTrue(false, "Failed to select the '" + permissionName + "' permission checkbox");
                    break;
                }
            }

            if (blnRes) {
                reports.writeResult(oBrowser, "Pass", "The following permission '" + permissionName + "' are selected for the role '" + rolesData.get("RoleName") + "'");
            }
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_Manage_SavePermission_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_Manage_SavePermission_ConfirmationMessage_Label, "Visibility", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManageUIPage.obj_Manage_SavePermission_ConfirmationMessage_Label, "Text").contains("Successfully saved Role"));
            rolesData.put("TestPassed", "True");
            return rolesData;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createNewRole()' method. " + e);
            rolesData.put("TestPassed", "False");
            return rolesData;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createNewRole()' method. " + e);
            rolesData.put("TestPassed", "False");
            return rolesData;
        }
    }





    /********************************************************
     * Method Name      : validateSwitchedPaymentTypesReportFilters()
     * Purpose          : user validates the 'Switched Payment Types' reports filter functionality
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateSwitchedPaymentTypesReportFilters(WebDriver oBrowser) {
        String startDate = null;
        String endDate = null;
        int rowCount = 0;
        int maxRows = 0;
        String cellData = null;
        String formatedActualDate = null;
        String emailID = null;
        int flag = 0;
        String companyName = null;
        try {
            Assert.assertTrue(appDep.selectManageModules(oBrowser, "Reports"));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_SwitchedPaymentTypes_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_SwitchedPaymentType_RunReport_Btn, "Clickable", "", waitTimeOut);
            startDate = appInd.addDaysToDate("", "dd-MM-yyyy", -1000);
            endDate = appInd.addDaysToDate("", "dd-MM-yyyy", 0);
            emailID = appInd.getPropertyValueByKeyName("qaUserName");
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_SwitchedPaymentType_StartDate_Edit, startDate));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_SwitchedPaymentType_EndDate_Edit, endDate));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_SwitchedPaymentType_RunReport_Btn));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.threadSleep(3000);
            reports.writeResult(oBrowser, "Screenshot", "'Switched Payment Types' reports page");
            maxRows = 11;
            rowCount = oBrowser.findElements(By.xpath(PayCRM_Modules_ManageUIPage.obj_SwitchedPaymentTypes_Grid + "//tr[contains(@class, 'lines dx-column-lines')]")).size();
            if (rowCount > 11) rowCount = maxRows;
            for (int i = 0; i < rowCount; i++) {
                flag++;
                cellData = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_SwitchedPaymentTypes_Grid + "//tr["+(i+1)+"][contains(@class, 'lines dx-column-lines')]/td[9]"), "Text");
                formatedActualDate = appInd.formatDateFromOnetoAnother(cellData, "MM/dd/yyyy", "dd-MM-yyyy");
                Assert.assertTrue(appDep.compareDateRangeWithActualDate(startDate, endDate, formatedActualDate));
            }
            if (flag == 0)
                Assert.assertTrue(false, "No records were found when applied filter for the 'Switched Payment Types' report");


            //Filter for Created By column cell values
            flag = 0;
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PayCRM_Modules_ManageUIPage.obj_SwitchedPaymentTypes_Grid + "//input)[13]"), emailID));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Applied filter for the 'Created By' column cell data under 'Switched Payment Types' reports page");
            maxRows = 11;
            rowCount = oBrowser.findElements(By.xpath(PayCRM_Modules_ManageUIPage.obj_SwitchedPaymentTypes_Grid + "//tr[contains(@class, 'lines dx-column-lines')]")).size();
            if (rowCount > 11) rowCount = maxRows;
            for (int i = 0; i < rowCount; i++) {
                flag++;
                cellData = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_SwitchedPaymentTypes_Grid + "//tr["+(i+1)+"][contains(@class, 'lines dx-column-lines')]/td[11]"), "Text");
                Assert.assertTrue(appInd.compareValues(oBrowser, cellData, emailID));
            }
            if (flag == 0)
                Assert.assertTrue(false, "No records were found when applied filter for the 'Switched Payment Types' report");

            //clear the filter
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PayCRM_Modules_ManageUIPage.obj_SwitchedPaymentTypes_Grid + "//input)[13]"), ""));
            appDep.threadSleep(2000);


            //Apply filter for the Company Name column
            companyName = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_SwitchedPaymentTypes_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td[2]"), "Text");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_SwitchedPaymentTypes_Grid + "//td[@aria-label = 'Company Name Column']/div[@class='dx-column-indicators']")));
            appDep.waitForTheElementState(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_PaymentBasedEnrollments_FilterOptions_Label), "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Enrollment_Case_FilterValue +"/div[contains(text(), '"+companyName+"')]")));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(EnrollmentsManagerUIPage.obj_Filter_OK_Btn)));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Applied filter for the 'Company Name' column cell data under 'Switched Payment Types' reports page");

            flag = 0;
            appDep.threadSleep(2000);
            maxRows = 11;
            rowCount = oBrowser.findElements(By.xpath(PayCRM_Modules_ManageUIPage.obj_SwitchedPaymentTypes_Grid + "//tr[contains(@class, 'lines dx-column-lines')]")).size();
            if (rowCount > 11) rowCount = maxRows;
            for (int i = 0; i < rowCount; i++) {
                flag++;
                cellData = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_SwitchedPaymentTypes_Grid + "//tr["+(i+1)+"][contains(@class, 'lines dx-column-lines')]/td[2]"), "Text");
                Assert.assertTrue(appInd.compareValues(oBrowser, cellData, companyName));
            }
            if (flag == 0)
                Assert.assertTrue(false, "No records were found when applied filter for the 'Switched Payment Types' report");

            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateSwitchedPaymentTypesReportFilters()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateSwitchedPaymentTypesReportFilters()' method. " + e);
            return false;
        }finally {
            startDate = null; endDate = null; cellData = null; formatedActualDate = null; emailID = null; companyName = null;
        }
    }




    /********************************************************
     * Method Name      : filterAndOpenTheEnrollmentCasesBasedOnOfferAccepted()
     * Purpose          : user apply filters based on the Offer Accepted critera in the Enrollment Report grid and open the enrollment case
     * Author           : Gudi
     * Parameters       : oBrowser, offerAccepted
     * ReturnType       : boolean
     ********************************************************/
    public String filterAndOpenTheEnrollmentCasesBasedOnOfferAccepted(WebDriver oBrowser, String offerAccepted){
        String caseNumber = null;
        try{
            //Filter Offer Accepted to VND-->Open any Case
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_ManageUIPage.obj_EnrollmentReports_Grid + "//input)[7]"), offerAccepted));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_EnrollmentReports_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]//td[1]")), "Failed to find the records for the Filter Applied value to 'VND'");
            reports.writeResult(oBrowser, "Screenshot", "Apply filter with Offer accepted as VND");
            caseNumber = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_EnrollmentReports_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]//td[1]"), "Text");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_EnrollmentReports_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]//td[1]")));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Enroll_Cases_Section + "//h3"), "Text").contains(caseNumber));
            reports.writeResult(oBrowser, "Screenshot", "The Enrollment case with Offer accepted as VND was opened successful");
            return caseNumber;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'filterAndOpenTheEnrollmentCasesBasedOnOfferAccepted()' method. " + e);
            return null;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'filterAndOpenTheEnrollmentCasesBasedOnOfferAccepted()' method. " + e);
            return null;
        }
    }



    /********************************************************
     * Method Name      : verifyGeneratePAF_SendEmail()
     * Purpose          : user validates the Generate PAF send email functionality
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean verifyGeneratePAF_SendEmail(WebDriver oBrowser, DataTable dataTable){
        List<Map<String, String>> sendEmailData = null;
        Map<String, String> emailData = null;
        String caseNumber = null;
        String supplierName = null;
        try{
            sendEmailData = dataTable.asMaps(String.class, String.class);
            emailData = new HashMap<String, String>();
            emailData.put("ContactEmail", appInd.getPropertyValueByKeyName("emailUserName"));
            emailData.put("From", sendEmailData.get(0).get("From"));
            emailData.put("CC", sendEmailData.get(0).get("CC"));
            emailData.put("Subject", sendEmailData.get(0).get("Subject"));
            emailData.put("Message", sendEmailData.get(0).get("Message"));
            emailData.put("FileToUpload", sendEmailData.get(0).get("FileToUpload"));
            emailData.put("OfferAccepted", sendEmailData.get(0).get("OfferAccepted"));

            Assert.assertTrue(validateEnrollmentReportFilters(oBrowser));

            //Filter Offer Accepted to VND-->Open any Case
            caseNumber = filterAndOpenTheEnrollmentCasesBasedOnOfferAccepted(oBrowser, emailData.get("OfferAccepted"));

            //Click on generate PAF button & update the contact email to your address of choice
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManageUIPage.obj_GeneratePAF_Btn), "Failed to find the 'Generate PAF' button");
            reports.writeResult(oBrowser, "Screenshot", "'Enrollment Cases' Page has opened");
            supplierName = appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_ManageUIPage.obj_SupplierInformation_Section + "//a[@class='panel-link'])[1]"), "Text");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_GeneratePAF_Btn));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_PreviewPAFDoc_Dialog + "//button[@id='btn_paf_edit_submit']"), "Clickable", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "'Review PAF Document' Page has opened after clicking 'Generate PAF' button");
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_PreviewPAFDoc_Dialog + "//h4"), "Text").contains("Review PAF Document » " + supplierName));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_ManageUIPage.obj_SupplierName_Edit, "Value", supplierName));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_Contacts_Email_Edit, emailData.get("ContactEmail")));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_PreviewPAFDoc_Dialog + "//button[@id='btn_paf_edit_submit']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);

            emailData = sendMail_GeneratePAF(oBrowser, caseNumber, emailData);
            Assert.assertTrue(emailData.get("TestPassed").equalsIgnoreCase("True"), "The 'sendMail_GeneratePAF()' method was failed.");
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);

            //Check user received the PAF email
            Assert.assertTrue(emailUtilities.connectAndOpenEmails(emailData));
            Assert.assertTrue(emailUtilities.readEmailFromPAFSendMail(oEmailBrowser, emailData));
            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifyGeneratePAF_SendEmail()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifyGeneratePAF_SendEmail()' method. " + e);
            return false;
        }finally{sendEmailData = null; emailData = null; caseNumber = null; supplierName = null;}
    }




    /********************************************************
     * Method Name      : sendMail_GeneratePAF()
     * Purpose          : user sends EMail using Generate PAF functionality
     * Author           : Gudi
     * Parameters       : oBrowser, emailData
     * ReturnType       : boolean
     ********************************************************/
    public Map<String, String> sendMail_GeneratePAF(WebDriver oBrowser, String caseNumber, Map<String, String> emailData){
        String subject = null;
        String emailBody = null;
        String generatedPAFAttachment = null;
        Actions oAction = null;
        try{
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_PreviewPAFEmail_Dialog +"//button[@id='btn_paf_preview_submit']"), "Clickable", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "'Preview PAF Email' Page has opened after clicking 'Submit' button from 'Review PAF Document' dialog");
            generatedPAFAttachment = (appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_PreviewPAFEmail_Dialog +"//h4"), "Text").split("»"))[1].trim();
            emailData.put("generatedPAFAttachment", generatedPAFAttachment.replace(" ", "_").replace(",", "") + "_PAF_"+caseNumber+".pdf");

            String mail_To = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_PAFEmail_To_Edit + "/a"), "Text");
            if(mail_To.equalsIgnoreCase(emailData.get("ContactEmail"))){
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_PAFEmail_To_Edit + "/a"), "Text", emailData.get("ContactEmail")));
            }else{
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_PAFEmail_To_Edit + "/a")));
                appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_PAFEmail_To_Edit + "//input"), "Clickable", "", minTimeOut);
                oAction = new Actions(oBrowser);
                oAction.sendKeys(oBrowser.findElement(By.xpath(PayCRM_Modules_ManageUIPage.obj_PAFEmail_To_Edit + "//input")), emailData.get("ContactEmail")).sendKeys(Keys.ENTER).build().perform();
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_AFMail_Body_Textarea));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_PAFEmail_To_Edit + "/a"), "Text", emailData.get("ContactEmail")));
            }

            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_ManageUIPage.obj_PAFEmail_From_Edit, "Text", emailData.get("From")));
            subject = appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManageUIPage.obj_PAFEmail_Subject_Edit, "Value");
            emailData.put("Subject", subject);

            if(emailData.get("FileToUpload")!=null){
                Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_PAFMail_Attachment_Edit, System.getProperty("user.dir").replace("\\", "/") + "/TestData/" + emailData.get("FileToUpload")));
                appDep.threadSleep(2000);
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManageUIPage.obj_PAFMail_Attachment_Edit, "Value").contains(emailData.get("FileToUpload")));
            }
            emailBody = appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManageUIPage.obj_AFMail_Body_Textarea, "Value");
            emailData.put("Message", emailBody);

            reports.writeResult(oBrowser, "Screenshot", "'Preview PAF Email' Page - Before clicking 'Send Email' button");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_PreviewPAFEmail_Dialog +"//button[@id='btn_paf_preview_submit']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_CompanyEditConfirmationMessage_Label, "Visibility", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManageUIPage.obj_CompanyEditConfirmationMessage_Label, "Text").contains("PAF email has been successfully sent."));
            reports.writeResult(oBrowser, "Screenshot", "Confirmation message was displayed regarding 'PAF Send Email'");
            appInd.scrollToElement(oBrowser, PayCRM_Modules_ManageUIPage.obj_SplitterHorizonal_Line);

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_Reassign_Logs_Tab));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_Activities_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_EnrollmentActivities_Grid, "Text", "Enrollment Activities", minTimeOut);
            String notes = "Profile Authorization Form email sent to " + emailData.get("ContactEmail")+" on "+ appInd.getDateTimeByTimezone("yyyy-MM-dd", "CST");
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_ManageUIPage.obj_EnrollmentCase_Activities_Grid + "//input)[7]"), notes));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "'Activities' page for 'PAF Send Email'");
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManageUIPage.obj_EnrollmentCase_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td)[3]"), "Text", "Outbound Email (Profile Authorization Form) to: " + emailData.get("ContactEmail")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManageUIPage.obj_EnrollmentCase_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td)[4]"), "Text", "Outbound Email"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_ManageUIPage.obj_EnrollmentCase_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td)[6]"), "Text", emailData.get("From")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_ManageUIPage.obj_EnrollmentCase_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td)[8]"), "Text").contains(notes));
            emailData.put("TestPassed", "True");
            return emailData;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'sendMail_GeneratePAF()' method. " + e);
            emailData.put("TestPassed", "False");
            return emailData;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'sendMail_GeneratePAF()' method. " + e);
            emailData.put("TestPassed", "False");
            return emailData;
        }finally{subject = null; emailBody = null; oAction = null;}
    }




    /********************************************************
     * Method Name      : verifySendEmailFunctionality()
     * Purpose          : user sends mail using Send Mail functionality for the Enrollment Cases with Offer Accepted type filter
     * Author           : Gudi
     * Parameters       : oBrowser, emailData
     * ReturnType       : boolean
     ********************************************************/
    public boolean verifySendEmailFunctionality(WebDriver oBrowser, DataTable dataTable){
        String caseNumber = null;
        Map<String, String> emailData = null;
        try{
            Assert.assertTrue(validateEnrollmentReportFilters(oBrowser));
            //Filter Offer Accepted to VND-->Open any Case
            caseNumber = filterAndOpenTheEnrollmentCasesBasedOnOfferAccepted(oBrowser, "VND");

            emailData = appDep.sendEmail_ComposeNewMessageFunctionality(oBrowser, "Enrollment Cses->Send Mail", dataTable);
            Assert.assertTrue(emailData.get("TestPassed").equalsIgnoreCase("True"), "The 'sendEmail_ComposeNewMessageFunctionality()' method was failed");

            //Go back to the case from the dashboard and Validate Last touch column
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);

            //Check user received the email
            Assert.assertTrue(emailUtilities.connectAndOpenEmails(emailData));
            Assert.assertTrue(emailUtilities.readEmailFromSendMail(oEmailBrowser, emailData));
            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifySendEmailFunctionality()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifySendEmailFunctionality()' method. " + e);
            return false;
        }finally {caseNumber = null; emailData = null; oBrowser = null;}
    }




    /********************************************************
     * Method Name      : validateSupportCasesReportFilters()
     * Purpose          : user validates the 'Support Cases' reports filter functionality
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateSupportCasesReportFilters(WebDriver oBrowser) {
        String startDate = null;
        String endDate = null;
        String arrColumnNames[];
        int flag = 0;
        WebElement supportCaseParentTab = null;
        try {
            Assert.assertTrue(appDep.selectManageModules(oBrowser, "Reports"));
            supportCaseParentTab = oBrowser.findElement(PayCRM_Modules_ManageUIPage.obj_supportCaseParentSection);
            if (supportCaseParentTab.getAttribute("aria-expanded").equalsIgnoreCase("false")) {
                Assert.assertTrue(appInd.clickObject(oBrowser, supportCaseParentTab));
            }
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_supportCaseActivityReportLink, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_supportCaseActivityReportLink));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_SupportCasesReport_RunReport_Btn, "Clickable", "", waitTimeOut);
            appInd.scrollToThePage(oBrowser, "Top");
            startDate = "01-01-2020";
            endDate = appInd.addDaysToDate("", "dd-MM-yyyy", 0);
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_SupportCasesReport_StartDate_Edit, startDate));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_SupportCasesReport_endDate_Edit, endDate));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_SupportCasesReport_RunReport_Btn));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            appDep.threadSleep(3000);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCasesReport_Grid + "//tr[contains(@class, 'dx-header-row')]//td[1]")), "No records were found when applied filter for the 'Support Cases' report");
            reports.writeResult(oBrowser, "Screenshot", "'Support Cases Report' page");

            //Validate 'Support Cases Report' grid columns
            arrColumnNames = "Case #,PayNet Supplier ID,Supplier Name,Case Category,Case Type,Action Date,Status,Priority,Origination Source".split(",");
            for(int i=0; i<arrColumnNames.length; i++){
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCasesReport_Grid + "//tr[contains(@class, 'dx-header-row')]//td["+(i+1)+"]/div[contains(@class, 'text-content')]"), "Text", arrColumnNames[i]));
            }
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateSupportCasesReportFilters()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateSupportCasesReportFilters()' method. " + e);
            return false;
        } finally {startDate = null; endDate = null; supportCaseParentTab = null;}
    }




    /********************************************************
     * Method Name      : add_OR_RemovePermissionForTheSelectedRoles()
     * Purpose          : user add/removes the permission for the selected roles
     * Author           : Gudi
     * Parameters       : oBrowser, permissionAction, appName, loginDetails
     * ReturnType       : boolean
     ********************************************************/
    public boolean add_OR_RemovePermissionForTheSelectedRoles(WebDriver oBrowser, String permissionAction, String permissionNames){
        String arrPermissions[];
        try{
            Assert.assertTrue(appDep.navigateToUserSettingsAndSearchUser(oBrowser, appInd.getPropertyValueByKeyName("qaPermissionUser")));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Profile_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "InVisibility", "", waitTimeOut);

            arrPermissions = permissionNames.split("#");
            for(int i=0; i<arrPermissions.length; i++){
                if(permissionAction.equalsIgnoreCase("Enable")){
                    if(!oBrowser.findElement(By.xpath(PartnerEnrollmentsUIPage.obj_UserRoles_Section + "//ul[@id='user_roles']/li/input[@value='"+arrPermissions[i]+"']")).isSelected())
                        Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("//input[@value='"+arrPermissions[i]+"']/following-sibling::label")));
                }else{
                    if(oBrowser.findElement(By.xpath(PartnerEnrollmentsUIPage.obj_UserRoles_Section + "//ul[@id='user_roles']/li/input[@value='"+arrPermissions[i]+"']")).isSelected())
                        Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("//input[@value='"+arrPermissions[i]+"']/following-sibling::label")));
                }
            }
            Assert.assertTrue(appInd.clickObject(oBrowser, PartnerEnrollmentsUIPage.obj_Roles_Update_Btn));
            appDep.waitForTheElementState(oBrowser, PartnerEnrollmentsUIPage.obj_ConfirmationMessage_Label, "Visibility", "", waitTimeOut);
            appDep.waitForTheElementState(oBrowser, PartnerEnrollmentsUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PartnerEnrollmentsUIPage.obj_ConfirmationMessage_Label, "Text").contains("User was successfully updated."));
            reports.writeResult(oBrowser, "Screenshot", "The Roles '"+permissionNames.replace("#", ",")+" were '"+permissionAction+"d'");
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in ' add_OR_RemovePermissionForTheSelectedRoles()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'add_OR_RemovePermissionForTheSelectedRoles()' method. " + e);
            return false;
        }finally{arrPermissions = null;
            Assert.assertTrue(enrollmentManagersUIBaseSteps.logoutFromTheApplication(oBrowser, "PayCRM"));
        }
    }




    /********************************************************
     * Method Name      : validateMasterSupplierFileRoleAndPermission()
     * Purpose          : user validates the Roles/PErmission for Master Supplier Files
     * Author           : Gudi
     * Parameters       : oBrowser, permissionStatus
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateMasterSupplierFileRoleAndPermission(WebDriver oBrowser, String permissionStatus){
        try{
            Assert.assertTrue(appInd.clickObject(oBrowser, PartnerEnrollmentsUIPage.obj_payCRM_NavigateBar_Link));
            appDep.waitForTheElementState(oBrowser, PartnerEnrollmentsUIPage.obj_AssistedPaymentServices_Link, "Clickable", "", minTimeOut);
            oBrowser.navigate().refresh();
            appDep.waitForTheElementState(oBrowser, PartnerEnrollmentsUIPage.obj_AssistedPaymentServices_Link, "Clickable", "", minTimeOut);
            appDep.threadSleep(5000);
            Assert.assertTrue(appDep.selectImportsModules(oBrowser, "Master Supplier Files"));

            if(permissionStatus.equalsIgnoreCase("Before")){
                reports.writeResult(oBrowser, "Screenshot", "Before disabling the permission for 'Master Supplier Files'");
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManageUIPage.obj_Contacts_Add_Link));
                Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_ManageUIPage.obj_MasterSupplierFiles_Grid + "//input)[8]"), "Imported"));
                appDep.threadSleep(2000);
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+PayCRM_Modules_ManageUIPage.obj_MasterSupplierFiles_Grid + "//tr[contains(@class, 'lines dx-column-lines')]//td)[1]")));
                appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MasterSupplierFiles_Show + "//div[contains(@class, 'panel-footer')]//a"), "Clickable", "", waitTimeOut);
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MasterSupplierFiles_Show + "//a[text()='Data']")));
                appDep.threadSleep(2000);
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_Munch_Btn, "Clickable", "", waitTimeOut);
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManageUIPage.obj_Munch_Btn));
                reports.writeResult(oBrowser, "Screenshot", "Before disabling the permission for 'Master Supplier Files', the 'MUNCH' button exist");
            }else{
                reports.writeResult(oBrowser, "Screenshot", "After disabling the permission for 'Master Supplier Files'");
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_ManageUIPage.obj_Contacts_Add_Link));
                Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_ManageUIPage.obj_MasterSupplierFiles_Grid + "//input)[8]"), "Imported"));
                appDep.threadSleep(2000);
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+PayCRM_Modules_ManageUIPage.obj_MasterSupplierFiles_Grid + "//tr[contains(@class, 'lines dx-column-lines')]//td)[1]")));
                appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MasterSupplierFiles_Show + "//div[contains(@class, 'panel-footer')]//a"), "Clickable", "", waitTimeOut);
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_MasterSupplierFiles_Show + "//a[text()='Data']")));
                appDep.threadSleep(2000);
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_ManageUIPage.obj_Munch_Btn));
                reports.writeResult(oBrowser, "Screenshot", "Before disabling the permission for 'Master Supplier Files', the 'MUNCH' button doesnot exist");
            }
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateMasterSupplierFileRoleAndPermission()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateMasterSupplierFileRoleAndPermission()' method. " + e);
            return false;
        }finally {
            Assert.assertTrue(enrollmentManagersUIBaseSteps.logoutFromTheApplication(oBrowser, "PayCRM"));
        }
    }




    /*************************************************************************************************
     * Method Name      : addRemoveUserToWorkQueueUnderUsersSettings()
     * Purpose          : user link/unlink users to the work queue under users settings
     * Author           : Gudi
     * Parameters       : oBrowser, queueNamesAndActions
     * ReturnType       : boolean
     ************************************************************************************************/
    public boolean addRemoveUserToWorkQueueUnderUsersSettings(WebDriver oBrowser, String queueNamesAndActions){
        String arrQueuesAndActions[];
        String queueActions[];
        boolean blnExist = false;
        boolean blnRes = false;
        try{
            Assert.assertTrue(appDep.navigateToUserSettingsAndSearchUser(oBrowser, appInd.getPropertyValueByKeyName("qaPermissionUser")));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_Queue_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "InVisibility", "", waitTimeOut);

            if(!queueNamesAndActions.isEmpty()){
                arrQueuesAndActions = queueNamesAndActions.split(",");
                for(int i=0; i<arrQueuesAndActions.length; i++){
                    queueActions = arrQueuesAndActions[i].split("#");
                    switch(queueActions[1].toLowerCase()){
                        case "add":
                            blnExist = appInd.verifyOptionalElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Users_Queue_Grid +"//tr[contains(@class, 'dx-row-lines')]/td[contains(text(), '"+queueActions[0]+"')]"));
                            if(!blnExist){
                                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Users_Queue_Grid +"//a[@title='Link']")));
                                appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_LinkUsersToWorkQueue_Dialog + "//button[@type='submit']"), "Clickable", "", minTimeOut);
                                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_WorkQueue_List)));
                                appDep.threadSleep(1000);
                                Assert.assertTrue(appInd.setObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_WorkQueue_List+ "//div[@class='chosen-search']/input"), queueActions[0]));
                                appDep.threadSleep(1000);
                                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_WorkQueue_List+ "//div[@class='chosen-search']/following-sibling::ul/li")));
                                appDep.threadSleep(1000);
                                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_WorkQueue_List +"/a/span"), "Text", queueActions[0]));
                                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_LinkUsersToWorkQueue_Dialog + "//button[@type='submit']")));
                                blnRes = appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "InVisibility", "", waitTimeOut);
                                if(!blnRes){
                                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ClientCases_Link));
                                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "InVisibility", "", waitTimeOut);
                                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_Queue_Link));
                                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "InVisibility", "", waitTimeOut);
                                }
                                Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManageUIPage.obj_LinkUpdateConfirmationMessage_Label, "Text").contains("User has been successfully added to this Queue."));
                                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Users_Queue_Grid +"//tr[contains(@class, 'dx-row-lines')]/td[contains(text(), '"+queueActions[0]+"')]")));
                                reports.writeResult(oBrowser, "Pass" ,"The queue '"+queueActions[0]+"' was linked successful under Users-->Settings-->Queues section");
                                reports.writeResult(oBrowser, "Screenshot" ,"The queue '"+queueActions[0]+"' was linked successful under Users-->Settings-->Queues section");
                            }else{
                                reports.writeResult(oBrowser, "Pass" ,"The queue '"+queueActions[0]+"' was already exist under Users-->Settings-->Queues section");
                                reports.writeResult(oBrowser, "Screenshot" ,"The queue '"+queueActions[0]+"' was already exist under Users-->Settings-->Queues section");
                            }
                            break;
                        case "remove":
                            blnExist = appInd.verifyOptionalElementPresent(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Users_Queue_Grid +"//tr[contains(@class, 'dx-row-lines')]/td[contains(text(), '"+queueActions[0]+"')]"));
                            if(blnExist){
                                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Users_Queue_Grid +"//tr[contains(@class, 'dx-row-lines')]/td[contains(text(), '"+queueActions[0]+"')]/following-sibling::td//a")));
                                appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_UnlinkUsersFromWorkQueue_Dialog + "//button[@type='submit']"), "Clickable", "", minTimeOut);
                                Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManageUIPage.obj_UnlinkUser_Alert_Message, "Text").contains("You are removing the user "+appInd.getPropertyValueByKeyName("qaUserName")+" from the work queue "+queueActions[0]+"."));
                                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_UnlinkUsersFromWorkQueue_Dialog + "//button[@type='submit']")));
                                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "InVisibility", "", waitTimeOut);
                                Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_ManageUIPage.obj_LinkUpdateConfirmationMessage_Label, "Text").contains("User has been successfully removed from this Queue."));
                                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_Users_Queue_Grid +"//tr[contains(@class, 'dx-row-lines')]/td[contains(text(), '"+queueActions[0]+"')]")));
                                reports.writeResult(oBrowser, "Pass" ,"The queue '"+queueActions[0]+"' was Unlinked successful under Users-->Settings-->Queues section");
                                reports.writeResult(oBrowser, "Screenshot" ,"The queue '"+queueActions[0]+"' was Unlinked successful under Users-->Settings-->Queues section");
                            }else{
                                reports.writeResult(oBrowser, "Pass" ,"The queue '"+queueActions[0]+"' doesn't exist under Users-->Settings-->Queues section");
                                reports.writeResult(oBrowser, "Screenshot" ,"The queue '"+queueActions[0]+"' doesn't exist under Users-->Settings-->Queues section");
                            }
                            break;
                        default:
                            reports.writeResult(oBrowser, "Fail", "Invalid Action '"+queueActions[1]+"' was specified for the Link/Unlink users to Work Queue");
                            Assert.fail("Invalid Action '"+queueActions[1]+"' was specified for the Link/Unlink users to Work Queue");
                    }
                }
            }

            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowserTwo, "Exception", "Exception in 'addRemoveUserToWorkQueueUnderUsersSettings()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowserTwo, "Exception", "AssertionError in 'addRemoveUserToWorkQueueUnderUsersSettings()' method. " + e);
            return false;
        }finally {arrQueuesAndActions = null; queueActions = null;}
    }
}
