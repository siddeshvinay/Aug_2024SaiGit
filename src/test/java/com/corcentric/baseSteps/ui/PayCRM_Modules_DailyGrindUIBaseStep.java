package com.corcentric.baseSteps.ui;

import com.corcentric.pages.*;
import com.corcentric.runner.CucumberTestRunner;
import com.corcentric.utiles.GridRecordAction;
import io.cucumber.datatable.DataTable;
import net.minidev.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class PayCRM_Modules_DailyGrindUIBaseStep extends CucumberTestRunner {
    private String newOfferTerm;
    private String waveTypeName;
    private String editOfferTerm;
    private String editWaveTypeName;
    private String newScriptName;
    private String editScriptName;
    private String editQueueName;
    private String queueName;
    private String caseNumber;

    String supplierName = null;
    /* Object is use for collecting created suppliers and it's respective details*/
    private List<Map<String, String>> cPay2765CreatedsupplierList = new ArrayList<>();
    /********************************************************
     * Method Name      : createNewOffers()
     * Purpose          : Create new offers from Modules->Daily Grind->Program Management->Offers
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : Map<String, String>
     ********************************************************/
    public Map<String, String> createNewOffers(WebDriver oBrowser, Map<String, String> offersData) {
        try {
            Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "Program Management"));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ProgramManagement_Grid + "/table//tr[1]/td[1]"), "Clickable", "", waitTimeOut);
            waveTypeName = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ProgramManagement_Grid + "/table//tr[1]/td[1]"), "Text");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ProgramManagement_Grid + "/table//tr[1]/td[1]")));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ShowEditDetails_Header, "Visibility", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_GoToWave_PageTitle, "Text").contains(waveTypeName));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_Link));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_Add_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_Create_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_NewWaveOffer_Dialog, "Text", "New Wave Offer"));

            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_OfferNumber_Edit, offersData.get("OfferNumber")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_OfferType_List, offersData.get("OfferType")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_CardPlatform_List, offersData.get("CardPlatform")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_CardNetwork_List, offersData.get("CardNetwork")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_CardCampaignType_List, offersData.get("CardCampainType")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_VirtualCardDeliveryMethod_List, offersData.get("VirtualCardDeliverMethod")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_Currency_List, offersData.get("Currency")));
            Assert.assertTrue(appInd.setOptionalObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_AmountFrom_Edit, offersData.get("AmountFrom")));
            Assert.assertTrue(appInd.setOptionalObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_AmountTo_Edit, offersData.get("AmountTo")));
            Assert.assertTrue(appInd.setOptionalObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_ACHLimit_Edit, offersData.get("ACHLimits")));
            Assert.assertTrue(appInd.setOptionalObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_Terms_Edit, offersData.get("Terms")));
            Assert.assertTrue(appInd.setOptionalObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_Percentage_Edit, offersData.get("Percentage")));
            Assert.assertTrue(appInd.setOptionalObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_VendorinNetworkDiscount_Edit, offersData.get("VendorinNetworkDiscount")));

            appInd.scrollToElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_Create_Btn);
            if (offersData.get("AgentIntervention") != null && offersData.get("AgentIntervention").equalsIgnoreCase("Yes")) {
                if (!appInd.createAndGetWebElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_AgentIntervention_ChkBox)).isSelected())
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_AgentIntervention_ChkBox + "/../../label[text()='Agent Intervention']")));
            } else {
                if (appInd.createAndGetWebElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_AgentIntervention_ChkBox)).isSelected())
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_AgentIntervention_ChkBox + "/../../label[text()='Agent Intervention']")));
            }

            if (offersData.get("BypassACH") != null && offersData.get("BypassACH").equalsIgnoreCase("Yes")) {
                if (!appInd.createAndGetWebElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_BypassACH_ChkBox)).isSelected())
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_BypassACH_ChkBox + "/../../label[text()='Bypass ACH']")));
            } else {
                if (appInd.createAndGetWebElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_BypassACH_ChkBox)).isSelected())
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_BypassACH_ChkBox + "/../../label[text()='Bypass ACH']")));
            }

            if (offersData.get("OfferEnabled") != null && offersData.get("OfferEnabled").equalsIgnoreCase("Yes")) {
                if (!appInd.createAndGetWebElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_OfferEnabled_ChkBox)).isSelected())
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_OfferEnabled_ChkBox + "/../../label[text()='Offer enabled']")));
            } else {
                if (appInd.createAndGetWebElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_OfferEnabled_ChkBox)).isSelected())
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_OfferEnabled_ChkBox + "/../../label[text()='Offer enabled']")));
            }
            reports.writeResult(oBrowser, "Screenshot", "Entered all the required data to create the offer");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_Create_Btn));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_ConfirmationMessage, "Visibility", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_ConfirmationMessage, "Text").contains("Enrollment Detail has been successfully created"));
            reports.writeResult(oBrowser, "Screenshot", "The new offer was created successful");

            appDep.waitForTheElementState(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_ProgramManagement_Offers_Grid + "//table//td[contains(@aria-label, 'Column Terms,')]//input)[1]"), "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_ProgramManagement_Offers_Grid + "//table//td[contains(@aria-label, 'Column Terms,')]//input)[1]"), offersData.get("Terms")));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_OffersGrid + "//td[text()='" + offersData.get("Terms") + "']/preceding-sibling::td[2]"), "Text", offersData.get("OfferType")));
            String offerNum = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_OffersGrid + "//td[text()='" + offersData.get("Terms") + "']/preceding-sibling::td[3]"), "Text");
            Assert.assertTrue(offerNum.equals(offersData.get("OfferNumber")) || offerNum.equals(String.valueOf(Integer.parseInt(offersData.get("OfferNumber")) + 1)));

            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_OffersGrid + "//td[text()='" + offersData.get("Terms") + "']"), "Text", offersData.get("Terms")));
            if (offersData.get("OfferEnabled") != null && offersData.get("OfferEnabled").equalsIgnoreCase("Yes"))
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_OffersGrid + "//td[text()='" + offersData.get("Terms") + "']/following-sibling::td[2]/div[@aria-checked='true']")));
            else
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_OffersGrid + "//td[text()='" + offersData.get("Terms") + "']/following-sibling::td[2]/div[@aria-checked='true']")));
            offersData.put("TestPassed", "True");
            return offersData;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createNewOffers()' method. " + e);
            offersData.put("TestPassed", "False");
            return offersData;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createNewOffers()' method. " + e);
            offersData.put("TestPassed", "False");
            return offersData;
        }
    }



    /********************************************************
     * Method Name      : editAndValidateTheWaveType()
     * Purpose          : to verify that the use can edit new Wave Type under modules in the Daily Grind section & validate the same
     * Author           : Gudi
     * Parameters       : oBrowser, dailyGrindType
     * ReturnType       : boolean
     ********************************************************/
    public boolean editAndValidateTheWaveType(WebDriver oBrowser, String waveTypeStatus, DataTable dataTable) {
        Map<String, String> waveData = null;
        List<Map<String, String>> waveDetails = null;
        String timeStamp = null;
        try {
            timeStamp = appInd.getDateTime("Shhmmss");
            waveDetails = dataTable.asMaps(String.class, String.class);
            waveData = new HashMap<String, String>();
            waveData.put("Name", waveDetails.get(0).get("Name") + "_" + timeStamp);
            editWaveTypeName = waveData.get("Name");
            waveData.put("Company", waveDetails.get(0).get("Company"));
            if (waveDetails.get(0).get("StartDate") == null) {
                waveData.put("StartDate", appInd.dateAddAndReturn("yyyy-MM-dd", 0));
            } else {
                waveData.put("StartDate", waveDetails.get(0).get("StartDate"));
            }

            if (waveDetails.get(0).get("EndDate") == null) {
                waveData.put("EndDate", appInd.dateAddAndReturn("yyyy-MM-dd", 1));
            } else {
                waveData.put("vEndDate", waveDetails.get(0).get("EndDate"));
            }
            waveData.put("WaveType", waveDetails.get(0).get("WaveType"));
            waveData.put("Status", waveDetails.get(0).get("Status"));
            waveData.put("Priority", waveDetails.get(0).get("Priority"));

            waveData = editTheWaveType(oBrowser, waveData);
            Assert.assertTrue(waveData.get("TestPassed").equalsIgnoreCase("True"), "The 'editTheWaveType()' method was failed");
            Assert.assertTrue(validateTheWaveTypeDetails(oBrowser, waveTypeStatus, waveData));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'editAndValidateTheWaveType()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'editAndValidateTheWaveType()' method. " + e);
            return false;
        } finally {
            waveData = null; waveDetails = null; timeStamp = null;
        }
    }



    /********************************************************
     * Method Name      : editTheQueues()
     * Purpose          : to verify that the use can edit queues under modules in the Daily Grind section
     * Author           : Gudi
     * Parameters       : oBrowser, queueData
     * ReturnType       : Map<String, String>
     ********************************************************/
    public Map<String, String> editTheQueues(WebDriver oBrowser, Map<String, String> queueData) {
        String ownerValue[];
        String queueValue[];
        String statusValue[];
        try {
            queueName = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Queues_Grid +"//tr[contains(@class, 'lines dx-column-lines')]/td[1]"), "Text");
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Edit_Link, "Clickable", "", waitTimeOut);
            Assert.assertTrue(payCRM_modules_generalUIBaseStep.actionOnTheFirstRowOfTheGrid(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Queues_Grid +"//tr[1][contains(@class, 'row-lines dx-column-lines')]/td[1]"), GridRecordAction.EDIT.getValue()));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_NewQueues_Create_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queue_Name_Edit, "Value", queueName), "verifyText() was failed for the '" + PayCRM_Modules_DailyGrindUIPage.obj_Queue_Name_Edit + "' webElement");
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_NewQueues_Create_Btn, "Clickable", "", waitTimeOut);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Edit_Queue_PageTitle, "Text", "Edit Work Queue"));

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queue_Name_Edit, queueData.get("Name")));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Tag_Edit, queueData.get("Tag")));
            ownerValue = queueData.get("Owner").split("#");
            if (appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Owner_List, "Dropdown").equalsIgnoreCase(ownerValue[0])) {
                queueData.put("Owner", ownerValue[1]);
            } else {
                queueData.put("Owner", ownerValue[0]);
            }
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Owner_List, queueData.get("Owner")));

            queueValue = queueData.get("WorkQueueType").split("#");
            if (appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_WorkQueueType_List, "Dropdown").equalsIgnoreCase(queueValue[0])) {
                queueData.put("WorkQueueType", queueValue[1]);
            } else {
                queueData.put("WorkQueueType", queueValue[0]);
            }
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_WorkQueueType_List, queueData.get("WorkQueueType")));


            statusValue = queueData.get("Status").split("#");
            if (appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Status_List, "Dropdown").equalsIgnoreCase(statusValue[0])) {
                queueData.put("Status", statusValue[1]);
            } else {
                queueData.put("Status", statusValue[0]);
            }
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Status_List, queueData.get("Status")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Priority_List, queueData.get("Priority")));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Notes_TextArea, queueData.get("Notes")));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_NewQueues_Create_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_ShowDetails_Header, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_EditConfirmationMessage_Label, "Text").contains("Work queue was successfully updated"));
            queueData.put("TestPassed", "True");
            return queueData;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'editTheQueues()' method. " + e);
            queueData.put("TestPassed", "False");
            return queueData;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'editTheQueues()' method. " + e);
            queueData.put("TestPassed", "False");
            return queueData;
        } finally {
            ownerValue = null; queueValue = null; statusValue = null;
        }
    }



    /********************************************************
     * Method Name      : createAndValidateTheQueues()
     * Purpose          : to verify that the use can create new Queues under modules in the Daily Grind section & validate the same
     * Author           : Gudi
     * Parameters       : oBrowser, queuesStatus, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean createAndValidateTheQueues(WebDriver oBrowser, String queuesStatus, DataTable dataTable) {
        Map<String, String> queueData = null;
        List<Map<String, String>> queueDetails = null;
        String timeStamp = null;
        try {
            timeStamp = appInd.getDateTime("Shhmmss");
            queueDetails = dataTable.asMaps(String.class, String.class);
            queueData = new HashMap<String, String>();
            queueData.put("Name", queueDetails.get(0).get("Name") + "_" + timeStamp);
            queueName = queueData.get("Name");
            queueData.put("Tag", queueDetails.get(0).get("Tag"));
            queueData.put("Owner", queueDetails.get(0).get("Owner"));
            queueData.put("WorkQueueType", queueDetails.get(0).get("WorkQueueType"));
            queueData.put("Status", queueDetails.get(0).get("Status"));
            queueData.put("Priority", queueDetails.get(0).get("Priority"));
            queueData.put("Notes", queueDetails.get(0).get("Notes"));

            queueData = createNewQueues(oBrowser, queueData);
            Assert.assertTrue(queueData.get("TestPassed").equalsIgnoreCase("True"), "The createNewQueues() method was failed");
            Assert.assertTrue(validateTheQueuesDetails(oBrowser, queuesStatus, queueData));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createAndValidateTheQueues()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createAndValidateTheQueues()' method. " + e);
            return false;
        } finally {
            queueData = null; queueDetails = null; timeStamp = null;
        }
    }



    /********************************************************
     * Method Name      : createNewQueues()
     * Purpose          : to verify that the use can create new Queues under modules in the Daily Grind section
     * Author           : Gudi
     * Parameters       : oBrowser, queueData
     * ReturnType       : Map<String, String>
     ********************************************************/
    public Map<String, String> createNewQueues(WebDriver oBrowser, Map<String, String> queueData) {
        try {
            Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "Queues"));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Add_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_NewQueues_Create_Btn, "Clickable", "", waitTimeOut);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Edit_Queue_PageTitle, "Text", "New Work Queue"));

            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queue_Name_Edit, queueData.get("Name")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Tag_Edit, queueData.get("Tag")));
            if (queueData.get("Owner") == null) {
                Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Owner_List, "Dropdown", appInd.getPropertyValueByKeyName("qaUserName")));
            } else {
                /*Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Owner_Default_Label));
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Owner_SearchField_Edit, "Clickable", "", waitTimeOut);
                Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Owner_SearchField_Edit, queueData.get("Owner")));
                appDep.threadSleep(1000);
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Owner_SearchResult_Label));*/
                Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Owner_List, queueData.get("Owner")));
            }

            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_WorkQueueType_List, queueData.get("WorkQueueType")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Status_List, queueData.get("Status")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Priority_List, queueData.get("Priority")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Notes_TextArea, queueData.get("Notes")));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_NewQueues_Create_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_ShowDetails_Header, "Clickable", "", waitTimeOut);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_EditConfirmationMessage_Label, "Text").contains("Work queue was successfully created"));
            queueData.put("TestPassed", "True");
            return queueData;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createNewQueues()' method. " + e);
            queueData.put("TestPassed", "False");
            return queueData;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createNewQueues()' method. " + e);
            queueData.put("TestPassed", "False");
            return queueData;
        }
    }


    /********************************************************
     * Method Name      : deleteAndValidateTheQueues()
     * Purpose          : to verify that the use can delete the Queues under modules in the Daily Grind section & validate the same
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean deleteAndValidateTheQueues(WebDriver oBrowser) {
        try {
            Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "Queues"));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Add_Link, "Clickable", "", waitTimeOut);

            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_QueuesName_Search_Edit, editQueueName), "setObject() was failed for the '" + String.valueOf(PayCRM_Modules_DailyGrindUIPage.obj_QueuesName_Search_Edit) + "' webelement");
            appDep.threadSleep(5000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_QueuesGrid_Pagination_Label, "Text", "Displaying Page 1 of 1 (1 records)", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "The Queues was searched successful");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Delete_Link), "clickObject() was failed for '" + String.valueOf(PayCRM_Modules_DailyGrindUIPage.obj_Queues_Delete_Link) + "' webelement");
            appDep.threadSleep(2000);
            if (appInd.isAlertPresent(oBrowser)) {
                oBrowser.switchTo().alert().accept();
                appDep.threadSleep(2000);
            } else {
                reports.writeResult(oBrowser, "Fail", "Unable to display the delete Queues confirmation alert.");
                Assert.assertTrue(false, "Failed to get the delete Queues confirmation alert.");
            }
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_EditConfirmationMessage_Label, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_EditConfirmationMessage_Label, "Text").contains("Work queue was successfully destroyed"));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_QueuesName_Search_Edit, "Clickable", "", waitTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Grid_WithNoData, "Invisibility", "", waitTimeOut);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_QueuesName_Search_Edit, editQueueName), "setObject() was failed for the '" + String.valueOf(PayCRM_Modules_DailyGrindUIPage.obj_QueuesName_Search_Edit) + "' webelement");
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Grid_WithNoData, "Visibility", "", waitTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ContactsGrid_Pagination_NoData_Label, "Text", "Displaying Page 1 of 1 (0 records)", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "The Queues was not found as it was deleted successful");
            return true;

        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'deleteAndValidateTheQueues()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'deleteAndValidateTheQueues()' method. " + e);
            return false;
        }
    }



    /********************************************************
     * Method Name      : applyFilterAndSearchCasesData()
     * Purpose          : to verify that the Filter functionality works for Cases data under modules->Cases section
     * Author           : Gudi
     * Parameters       : oBrowser, valueType, queryKey, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean applyFilterAndSearchCasesData(WebDriver oBrowser, String valueType, String queryKey, DataTable dataTable) {
        List<Map<String, String>> casesDetails = null;
        List<WebElement> rows = null;
        int rowNum = 0;
        Map<String, String> casesData = null;
        JSONArray caseDetails = null;
        try {
            casesDetails = dataTable.asMaps(String.class, String.class);
            casesData = new HashMap<>();
            casesData.put("SupplierName", casesDetails.get(0).get("SupplierName"));
            casesData.put("CompanyName", casesDetails.get(0).get("CompanyName"));
            casesData.put("CaseType", casesDetails.get(0).get("CaseType"));
            casesData.put("Status", casesDetails.get(0).get("Status"));
            casesData.put("Priority", casesDetails.get(0).get("Priority"));
            casesData.put("RecordLimit", casesDetails.get(0).get("RecordLimit"));
            casesData.put("TotalSpend", casesDetails.get(0).get("TotalSpend"));
            casesData.put("Amount", casesDetails.get(0).get("Amount"));

            if(!queryKey.isEmpty()) {
                caseDetails = apiDataProvider.getAPIDataProviderResponse(queryKey, new Object[] {});

                if(casesData.get("SupplierName")!=null){
                    if(valueType.equalsIgnoreCase("Valid")) {
                        casesData.remove("SupplierName");
                        casesData.put("SupplierName", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("supplier_name").toString());
                    }else{
                        casesData.remove("SupplierName");
                        casesData.put("SupplierName", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("supplier_name").toString()+"!@#$%^");
                    }
                }
                if(casesData.get("CompanyName")!=null){
                    casesData.remove("CompanyName");
                    casesData.put("CompanyName", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("company_name").toString());
                }
                if(casesData.get("CaseType")!=null){
                    casesData.remove("CaseType");
                    casesData.put("CaseType", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("case_type_name").toString());
                }
                if(casesData.get("Status")!=null){
                    casesData.remove("Status");
                    casesData.put("Status", (((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("status_desc").toString().split("\\["))[0].trim());
                }
                if(casesData.get("Priority")!=null){
                    casesData.remove("Priority");
                    casesData.put("Priority", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("priority_name").toString());
                }

                if(casesData.get("Amount")!=null){
                    casesData.remove("Amount");
                    casesData.put("Amount", ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("total_spend").toString());
                }
            }

            Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "Cases"));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_Filter_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_Clear_Btn));
            Assert.assertTrue(payCRM_modules_generalUIBaseStep.validateClearButtonInCasesPage(oBrowser));
            reports.writeResult(oBrowser, "Screenshot", "The 'Cases' filter was cleared successful");

            if (casesData.get("SupplierName") != null) {
                Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_SupplierName_Edit, casesData.get("SupplierName")));
            }

            if (casesData.get("CompanyName") != null) {
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_CompanyName_Edit));
                Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_CompanyName_Edit, casesData.get("CompanyName")));
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_cases_SearchResult_dropdown));
            }

            if (casesData.get("CaseType") != null) {
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_CaseType_Edit));
                Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_CaseType_Edit, casesData.get("CaseType")));
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_cases_SearchResult_dropdown));
            }

            if (casesData.get("Status") != null) {
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_status_Edit));
                Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_status_Edit, casesData.get("Status")));
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_cases_SearchResult_dropdown));
            }

            if (casesData.get("Priority") != null) {
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_Priority_Edit));
                Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_Priority_Edit, casesData.get("Priority")));
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_cases_SearchResult_dropdown));
            }

            if (casesData.get("RecordLimit") != null) {
                Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_RecordLimit_Edit, casesData.get("RecordLimit")));
            }

            oBrowser.switchTo().activeElement().sendKeys(Keys.TAB);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_Filter_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);

            if (valueType.equalsIgnoreCase("Valid")) {
                rows = oBrowser.findElements(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_SearchResultGrid_rows + "/table[@style]/tbody/tr[not(@style)]"));
                if(casesData.get("RecordLimit")!=null && rows.size() == Integer.parseInt(casesData.get("RecordLimit"))){
                    rowNum = Integer.parseInt(casesData.get("RecordLimit"));
                }else{
                    rowNum = rows.size();
                }
                for (int i = 0; i < rowNum; i++) {
                    if (casesData.get("SupplierName") != null)
                        Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_SearchResultGrid_rows + "/table[@style]/tbody/tr[" + (i + 1) + "][not(@style)]/td[3]"), "Text").toLowerCase().contains(casesData.get("SupplierName").toLowerCase()));

                    if (casesData.get("CompanyName") != null)
                        Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_SearchResultGrid_rows + "/table[@style]/tbody/tr[" + (i + 1) + "][not(@style)]/td[2]"), "Text", casesData.get("CompanyName")));

                    if (casesData.get("CaseType") != null)
                        Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_SearchResultGrid_rows + "/table[@style]/tbody/tr[" + (i + 1) + "][not(@style)]/td[6]"), "Text", casesData.get("CaseType")));

                    if (casesData.get("Status") != null)
                        Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_SearchResultGrid_rows + "/table[@style]/tbody/tr[" + (i + 1) + "][not(@style)]/td[8]"), "Text").contains(casesData.get("Status")));

                    if (casesData.get("Priority") != null)
                        Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_SearchResultGrid_rows + "/table[@style]/tbody/tr[" + (i + 1) + "][not(@style)]/td[9]"), "Text", casesData.get("Priority")));
                }
            } else if (valueType.equalsIgnoreCase("FilterWithNoValidation")) {
                reports.writeResult(oBrowser, "Info", "User applied the filter for the cases grid with no validation of the filtered data");
            } else {
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_Grid_WithNoData, "Visibility", "", waitTimeOut);
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_Grid_WithNoData));
            }
            reports.writeResult(oBrowser, "Screenshot", "The 'Cases' filter functionality was working successful");
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'applyFilterAndSearchCasesData()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'applyFilterAndSearchCasesData()' method. " + e);
            return false;
        } finally {
            casesDetails = null; rows = null; casesData = null;
        }
    }



    /********************************************************
     * Method Name      : validateTheQueuesDetails()
     * Purpose          : to verify that the use can validate create/edit queues details
     * Author           : Gudi
     * Parameters       : oBrowser, queueStatus, queuesData
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateTheQueuesDetails(WebDriver oBrowser, String queuesStatus, Map<String, String> queuesData) {
        try {

            if (queuesStatus.equalsIgnoreCase("New")) {
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Show_Details_Grid, "Text").contains("Work Queue: " + queuesData.get("Name") + " - Created"));
            } else {
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Show_Details_Grid, "Text").contains("Work Queue: " + queuesData.get("Name") + " - Updated"));
            }

            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Show_Details_Grid, "Text").contains("by " + appInd.getPropertyValueByKeyName("qaUserName")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_ShowDetails_Header, "Text", "Summary", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Queues_ShowGrid + "//dl/dd)[1]"), "Text", queuesData.get("Name")));
            if (queuesData.get("Owner") == null) {
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Queues_ShowGrid + "//dl/dd)[2]"), "Text", appInd.getPropertyValueByKeyName("qaUserName")));
            } else {
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Queues_ShowGrid + "//dl/dd)[2]"), "Text", queuesData.get("Owner")));
            }
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Queues_ShowGrid + "//dl/dd)[3]"), "Text", queuesData.get("Priority")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Queues_ShowGrid + "//dl/dd)[4]"), "Text", queuesData.get("Status")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Queues_ShowGrid + "//dl/dd)[5]"), "Text", queuesData.get("WorkQueueType")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Queues_ShowGrid + "//dl/dd)[6]"), "Text", queuesData.get("Tag")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Queues_ShowGrid + "//dl/dd)[7]"), "Text", queuesData.get("Notes")));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateTheQueuesDetails()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateTheQueuesDetails()' method. " + e);
            return false;
        }
    }



    /********************************************************
     * Method Name      : validateTheEditedQueueInShowSection()
     * Purpose          : to verify that the edited Queues details under show section
     * Author           : Gudi
     * Parameters       : oBrowser, queueData
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateTheEditedQueueInShowSection(WebDriver oBrowser, Map<String, String> queuesData) {
        try {
            Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "Queues"));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_QueuesName_Search_Edit, editQueueName), "setObject() was failed for the '" + String.valueOf(PayCRM_Modules_DailyGrindUIPage.obj_QueuesName_Search_Edit) + "' webelement");
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_QueuesGrid_Pagination_Label, "Visibility", "", waitTimeOut);
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "The Queues was searched successful");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Show_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_ShowDetails_Header, "Text", "Summary", waitTimeOut);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Show_Edit_Link, "Clickable", "", waitTimeOut);

            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Show_Details_Grid, "Text").contains("Work Queue: " + queuesData.get("Name") + " - Updated"));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Show_Details_Grid, "Text").contains("by " + appInd.getPropertyValueByKeyName("qaUserName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Queues_ShowGrid + "//dl/dd)[1]"), "Text", queuesData.get("Name")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Queues_ShowGrid + "//dl/dd)[2]"), "Text", queuesData.get("Owner")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Queues_ShowGrid + "//dl/dd)[3]"), "Text", queuesData.get("Priority")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Queues_ShowGrid + "//dl/dd)[4]"), "Text", queuesData.get("Status")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Queues_ShowGrid + "//dl/dd)[5]"), "Text", queuesData.get("WorkQueueType")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Queues_ShowGrid + "//dl/dd)[6]"), "Text", queuesData.get("Tag")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Queues_ShowGrid + "//dl/dd)[7]"), "Text", queuesData.get("Notes")));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateTheEditedQueueInShowSection()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateTheEditedQueueInShowSection()' method. " + e);
            return false;
        }
    }



    /********************************************************
     * Method Name      : editAndValidateTheQueues()
     * Purpose          : to verify that the use can edit Queues under modules in the Daily Grind section & validate the same
     * Author           : Gudi
     * Parameters       : oBrowser, queuesStatus, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean editAndValidateTheQueues(WebDriver oBrowser, String queuesStatus, DataTable dataTable) {
        Map<String, String> queueData = null;
        List<Map<String, String>> queueDetails = null;
        String timeStamp = null;
        try {
            timeStamp = appInd.getDateTime("Shhmmss");
            queueDetails = dataTable.asMaps(String.class, String.class);
            queueData = new HashMap<String, String>();
            queueData.put("Name", queueDetails.get(0).get("Name") + "_" + timeStamp);
            editQueueName = queueData.get("Name");
            queueData.put("Tag", queueDetails.get(0).get("Tag") + timeStamp);
            queueData.put("Owner", queueDetails.get(0).get("Owner"));
            queueData.put("WorkQueueType", queueDetails.get(0).get("WorkQueueType"));
            queueData.put("Status", queueDetails.get(0).get("Status"));
            queueData.put("Priority", queueDetails.get(0).get("Priority"));
            queueData.put("Notes", queueDetails.get(0).get("Notes") + " - " + timeStamp);

            Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "Queues"));
            appDep.threadSleep(2000);
            Assert.assertTrue(appDep.navigateAndSelectTheWorkQueue(oBrowser, "DailyGrindWorkQueues"));
            queueData = editTheQueues(oBrowser, queueData);
            Assert.assertTrue(queueData.get("TestPassed").equalsIgnoreCase("True"), "The editTheQueues() method was failed");
            Assert.assertTrue(validateTheQueuesDetails(oBrowser, queuesStatus, queueData));
            Assert.assertTrue(validateTheEditedQueueInShowSection(oBrowser, queueData));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'editAndValidateTheQueues()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'editAndValidateTheQueues()' method. " + e);
            return false;
        } finally {
            queueData = null; queueDetails = null; timeStamp = null;
        }
    }



    /********************************************************
     * Method Name      : editTheWaveType()
     * Purpose          : to verify that the use can edit new Wave Type under modules in the Daily Grind section
     * Author           : Gudi
     * Parameters       : oBrowser, waveData
     * ReturnType       : Map<String, String>
     ********************************************************/
    public Map<String, String> editTheWaveType(WebDriver oBrowser, Map<String, String> waveData) {
        try {
            Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "Program Management"));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WaveType_Add_Link, "Clickable", "", waitTimeOut);

            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WaveName_Search_Edit, waveTypeName), "setObject() was failed for the '" + String.valueOf(PayCRM_Modules_DailyGrindUIPage.obj_WaveName_Search_Edit) + "' webelement");
            appDep.threadSleep(5000);
            reports.writeResult(oBrowser, "Screenshot", "The Wave Type was searched successful");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WaveType_Edit_Link), "clickObject() was failed for '" + String.valueOf(PayCRM_Modules_DailyGrindUIPage.obj_WaveType_Edit_Link) + "' webelement");
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_NewWaveType_Create_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Edit_WaveType_PageTitle, "Text", "Edit Wave"));

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WaveType_Name_Edit, waveData.get("Name")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WaveType_Company_List, waveData.get("Company")));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WaveType_StartDate_Edit, waveData.get("StartDate")));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WaveType_EndDate_Edit, waveData.get("EndDate")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WaveType_WaveType_List, waveData.get("WaveType")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WaveType_Status_List, waveData.get("Status")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WaveType_Priority_List, waveData.get("Priority")));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_NewWaveType_Create_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ShowEditDetails_Header, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WaveTypeEditConfirmationMessage_Label, "Text").contains("Wave was successfully updated"));
            waveData.put("TestPassed", "True");
            return waveData;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'editTheWaveType()' method. " + e);
            waveData.put("TestPassed", "False");
            return waveData;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'editTheWaveType()' method. " + e);
            waveData.put("TestPassed", "False");
            return waveData;
        }
    }



    /********************************************************
     * Method Name      : editExistingOffers()
     * Purpose          : edit the existing offers from Modules->Daily Grind->Program Management->Offers
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : Map<String, String>
     ********************************************************/
    public Map<String, String> editExistingOffers(WebDriver oBrowser, Map<String, String> offersData) {
        try {
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_SearchTerms_Edit, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_SearchTerms_Edit, newOfferTerm));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_OffersGrid + "//td[text()='" + newOfferTerm + "']/following-sibling::td/a[@title='Edit']")));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_Create_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_EditWave_Dialog, "Text", "Edit Wave Offer"));

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_OfferNumber_Edit, offersData.get("OfferNumber")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_OfferType_List, offersData.get("OfferType")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_CardPlatform_List, offersData.get("CardPlatform")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_CardNetwork_List, offersData.get("CardNetwork")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_CardCampaignType_List, offersData.get("CardCampainType")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_VirtualCardDeliveryMethod_List, offersData.get("VirtualCardDeliverMethod")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_Currency_List, offersData.get("Currency")));
            Assert.assertTrue(appInd.setOptionalObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_AmountFrom_Edit, offersData.get("AmountFrom")));
            Assert.assertTrue(appInd.setOptionalObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_AmountTo_Edit, offersData.get("AmountTo")));
            Assert.assertTrue(appInd.setOptionalObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_ACHLimit_Edit, offersData.get("ACHLimits")));
            Assert.assertTrue(appInd.setOptionalObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_Terms_Edit, offersData.get("Terms")));
            Assert.assertTrue(appInd.setOptionalObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_Percentage_Edit, offersData.get("Percentage")));
            Assert.assertTrue(appInd.setOptionalObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_VendorinNetworkDiscount_Edit, offersData.get("VendorinNetworkDiscount")));

            if (offersData.get("AgentIntervention") != null && offersData.get("AgentIntervention").equalsIgnoreCase("Yes")) {
                if (!appInd.createAndGetWebElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_AgentIntervention_ChkBox)).isSelected())
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_AgentIntervention_ChkBox + "/../../label[text()='Agent Intervention']")));
            } else {
                if (appInd.createAndGetWebElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_AgentIntervention_ChkBox)).isSelected())
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_AgentIntervention_ChkBox + "/../../label[text()='Agent Intervention']")));
            }

            if (offersData.get("BypassACH") != null && offersData.get("BypassACH").equalsIgnoreCase("Yes")) {
                if (!appInd.createAndGetWebElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_BypassACH_ChkBox)).isSelected())
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_BypassACH_ChkBox + "/../../label[text()='Bypass ACH']")));
            } else {
                if (appInd.createAndGetWebElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_BypassACH_ChkBox)).isSelected())
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_BypassACH_ChkBox + "/../../label[text()='Bypass ACH']")));
            }

            if (offersData.get("OfferEnabled") != null && offersData.get("OfferEnabled").equalsIgnoreCase("Yes")) {
                if (!appInd.createAndGetWebElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_OfferEnabled_ChkBox)).isSelected())
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_OfferEnabled_ChkBox + "/../../label[text()='Offer enabled']")));
            } else {
                if (appInd.createAndGetWebElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_OfferEnabled_ChkBox)).isSelected())
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_OfferEnabled_ChkBox + "/../../label[text()='Offer enabled']")));
            }
            reports.writeResult(oBrowser, "Screenshot", "The required offer details were modified successful");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_Create_Btn));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_ConfirmationMessage, "Visibility", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_ConfirmationMessage, "Text").contains("Enrollment Detail has been successfully created"));

            reports.writeResult(oBrowser, "Screenshot", "The offer was modified/updated successful");
            appDep.waitForTheElementState(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_ProgramManagement_Offers_Grid + "//table//td[contains(@aria-label, 'Column Terms,')]//input)[1]"), "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_ProgramManagement_Offers_Grid + "//table//td[contains(@aria-label, 'Column Terms,')]//input)[1]"), offersData.get("Terms")));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_OffersGrid + "//td[text()='" + offersData.get("Terms") + "']/preceding-sibling::td[2]"), "Text", offersData.get("OfferType")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_OffersGrid + "//td[text()='" + offersData.get("Terms") + "']/preceding-sibling::td[3]"), "Text", offersData.get("OfferNumber")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_OffersGrid + "//td[text()='" + offersData.get("Terms") + "']"), "Text", offersData.get("Terms")));
            if (offersData.get("OfferEnabled") != null && offersData.get("OfferEnabled").equalsIgnoreCase("Yes"))
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_OffersGrid + "//td[text()='" + offersData.get("Terms") + "']/following-sibling::td[2]/div[@aria-checked='true']")));
            else
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_OffersGrid + "//td[text()='" + offersData.get("Terms") + "']/following-sibling::td[2]/div[@aria-checked='false']")));
            offersData.put("TestPassed", "True");
            return offersData;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'editExistingOffers()' method. " + e);
            offersData.put("TestPassed", "False");
            return offersData;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'editExistingOffers()' method. " + e);
            offersData.put("TestPassed", "False");
            return offersData;
        }
    }



    /********************************************************
     * Method Name      : createNewWaveType()
     * Purpose          : to verify that the use can create new Wave Type under modules in the Daily Grind section
     * Author           : Gudi
     * Parameters       : oBrowser, waveData
     * ReturnType       : Map<String, String>
     ********************************************************/
    public Map<String, String> createNewWaveType(WebDriver oBrowser, Map<String, String> waveData) {
        try {
            Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "Program Management"));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WaveType_Add_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_NewWaveType_Create_Btn, "Clickable", "", waitTimeOut);
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "The 'New Wave' page opened");
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Edit_WaveType_PageTitle, "Text", "New Wave"));

            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WaveType_Name_Edit, waveData.get("Name")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WaveType_Company_List, waveData.get("Company")));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WaveType_StartDate_Edit, waveData.get("StartDate")));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WaveType_EndDate_Edit, waveData.get("EndDate")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WaveType_WaveType_List, waveData.get("WaveType")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WaveType_Status_List, waveData.get("Status")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WaveType_Priority_List, waveData.get("Priority")));
            reports.writeResult(oBrowser, "Screenshot", "The details are entered in 'New Wave' page");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_NewWaveType_Create_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ShowEditDetails_Header, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WaveTypeEditConfirmationMessage_Label, "Text").contains("Wave was successfully created"));
            reports.writeResult(oBrowser, "Screenshot", "The 'New Wave' was created successful");
            waveData.put("TestPassed", "True");
            return waveData;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createNewWaveType()' method. " + e);
            waveData.put("TestPassed", "False");
            return waveData;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createNewWaveType()' method. " + e);
            waveData.put("TestPassed", "False");
            return waveData;
        }
    }



    /********************************************************
     * Method Name      : editExistingScripts()
     * Purpose          : Edit existing Scripts from Modules->Daily Grind->Program Management->Scripts
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : Map<String, String>
     ********************************************************/
    public Map<String, String> editExistingScripts(WebDriver oBrowser, Map<String, String> scriptsData) {
        try {
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ScriptName_Search_Edit, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ScriptName_Search_Edit, newScriptName));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ScriptDetails_Grid + "//tr/td[text()='" + newScriptName + "']/following-sibling::td[4]/a[@title='Edit']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Scripts_Create_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Script_EditWaveScript_Dialog, "Text", "Edit Wave Script"));

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ScriptName_Edit, scriptsData.get("ScriptName")));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ScriptText_Textarea, scriptsData.get("ScriptText")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ScriptType_List, scriptsData.get("ScriptType")));

            if (scriptsData.get("WaveOffer") != null)
                Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Scripts_WaveOffer_List, scriptsData.get("WaveOffer")));
            else {
                Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Scripts_WaveOffer_List, 1));
                scriptsData.put("WaveOffer", appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Scripts_WaveOffer_List, "Dropdown"));
            }

            reports.writeResult(oBrowser, "Screenshot", "The required data was entered to edit the existing script");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Scripts_Create_Btn));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ConfirmationMessage, "Visibility", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ConfirmationMessage, "Text").trim().contains("Enrollment Script has been successfully updated"));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ScriptName_Search_Edit, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ScriptName_Search_Edit, scriptsData.get("ScriptName")));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "The edited script was searched");
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ScriptDetails_Grid + "//tr/td[text()='" + scriptsData.get("ScriptName") + "']"), "Text", scriptsData.get("ScriptName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ScriptDetails_Grid + "//tr/td[text()='" + scriptsData.get("ScriptName") + "']/following-sibling::td[1]"), "Text", scriptsData.get("ScriptText")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ScriptDetails_Grid + "//tr/td[text()='" + scriptsData.get("ScriptName") + "']/following-sibling::td[2]"), "Text", scriptsData.get("ScriptType")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ScriptDetails_Grid + "//tr/td[text()='" + scriptsData.get("ScriptName") + "']/following-sibling::td[3]"), "Text", scriptsData.get("WaveOffer")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ScriptDetails_Grid + "//tr/td[text()='" + scriptsData.get("ScriptName") + "']/following-sibling::td[4]/a[@title='Show']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ScriptDetails_Grid + "//tr/td[text()='" + scriptsData.get("ScriptName") + "']/following-sibling::td[4]/a[@title='Edit']")));
            //Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ScriptDetails_Grid + "//tr/td[text()='" + scriptsData.get("ScriptName") + "']/following-sibling::td[4]/a[@title='Delete']")));
            scriptsData.put("TestPassed", "True");
            return scriptsData;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'editExistingScripts()' method. " + e);
            scriptsData.put("TestPassed", "False");
            return scriptsData;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'editExistingScripts()' method. " + e);
            scriptsData.put("TestPassed", "False");
            return scriptsData;
        }
    }



    public boolean validateCasesTabOfSelectedWavesType(WebDriver oBrowser, By objectMapper, String gridID) {
        try {
            gotoProgramManagementFirstRecord();
            reports.writeResult(oBrowser, "Screenshot", "The Program Manager page opened successful");
            Assert.assertTrue(appInd.clickObject(oBrowser, objectMapper));
            appDep.threadSleep(4000);
            String caseGrid = String.format(PayCRM_Modules_DailyGrindUIPage.obj_programManagement_Show_Case_Grid, gridID);
            appDep.waitForTheElementState(oBrowser, By.xpath(caseGrid), "Visibility", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "The Program Manager 1st record tab opened successful");
            Assert.assertTrue(appInd.getWebElements(oBrowser, By.xpath(caseGrid + "//tr[contains(@class, 'lines dx-column-lines')]")).size() > 0);
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'updateTabVisibilityFromInternalSetup()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'updateTabVisibilityFromInternalSetup()' method. " + e);
            return false;
        }
        return true;
    }



    /********************************************************
     * Method Name      : navigateAndSelectDailyGrindCase
     * Purpose          : user navigates to Daily Grind-->cases and select the case using query
     * Author           : Gudi
     * Parameters       : oBrowser, queryKey
     * ReturnType       : List<Map<String, String>>
     ********************************************************/
    public JSONArray navigateAndSelectDailyGrindCase(WebDriver oBrowser, String queryKey){
        String strURL = null;
        String caseNumber = null;
        JSONArray caseDetails = null;
        try{
            caseDetails = apiDataProvider.getAPIDataProviderResponse(queryKey, new Object[] {});
            caseNumber = ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("cases_id").toString();

            strURL = appInd.getPropertyValueByKeyName("qaURL")+"/inrollment_cases/" + caseNumber;
            oBrowser.navigate().to(strURL);
            reports.writeResult(oBrowser, "Screenshot", "The constructed URL: " + strURL);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WaveAndOffers_Header, "Text", "Wave & Offers", waitTimeOut);
            return caseDetails;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'navigateAndSelectDailyGrindCase()' method. " + e);
            return null;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'navigateAndSelectDailyGrindCase()' method. " + e);
            return null;
        }finally {strURL = null; caseNumber = null; caseDetails = null;}
    }

    public boolean verifyTabFromWaveDetailPage(WebDriver oBrowser, String tabName) {
        String tab = String.format(PayCRM_Modules_DailyGrindUIPage.obj_tab_names, tabName);
        appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "invisibility", "", waitTimeOut);
        appDep.waitForTheElementState(oBrowser, By.xpath(tab), "Clickable", "", waitTimeOut);
        Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(tab)));
        appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "invisibility", "", waitTimeOut);
        return true;
    }



    public boolean applyFilterOnQueue(WebDriver oBrowser, String queueName, Map<String, String> filterMap){
        String heading = String.format(PayCRM_Modules_DailyGrindUIPage.obj_queues_heading,queueName);
        List<String> filterList = new ArrayList<>();
        filterMap.keySet().stream().forEach(key -> filterList.add(key));
        String filterHeading = String.format(PayCRM_Modules_DailyGrindUIPage.obj_queues_filter_section, filterList.get(0));
        appDep.waitForTheElementState(oBrowser, By.xpath(heading),"Clickable","",waitTimeOut);
        Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(heading)));
        appDep.waitForTheElementState(oBrowser, EnrollmentsManagerUIPage.obj_Companies_Table_Loading, "InVisibility", "", waitTimeOut);
        appDep.waitForTheElementState(oBrowser, By.xpath(filterHeading),"visibility","",waitTimeOut);
        filterList.forEach(filter -> {
            String filterOption = String.format(PayCRM_Modules_DailyGrindUIPage.obj_queues_filter_section, filter);
            if(filter.equalsIgnoreCase("Case Statuses")){
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(filterOption)));
                appDep.threadSleep(1000);
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(filterOption)));
                appDep.threadSleep(1000);
            }
            appDep.threadSleep(1000);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(filterOption)));
            appDep.threadSleep(2000);
            if(filterMap.get(filter)!=null && filterMap.get(filter)!=""){
                String filterKeyVal = String.format(PayCRM_Modules_DailyGrindUIPage.obj_queues_filter_options,filter
                        ,filterMap.get(filter));
                appDep.threadSleep(1000);
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(filterKeyVal)));
                appDep.threadSleep(1000);
            }
        });
        if(!oBrowser.findElement(EnrollmentsManagerUIPage.obj_Companies_Grid_Checkbox).isSelected())
            Assert.assertTrue(appInd.clickObject(oBrowser, EnrollmentsManagerUIPage.obj_Companies_Grid_Checkbox));
        Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_queues_show_case_button));
        appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_quick_filter_grid,"visibility","",waitTimeOut);
        return true;
    }



    /********************************************************
     * Method Name      : validateStopFraudValidationsPageFunctionality()
     * Purpose          : user validates the 'StopFraud-->Validations' UI functionality
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateStopFraudValidationsPageFunctionality(WebDriver oBrowser) {
        String validations_TileNames[];
        String validations_Categories[];
        int tilesCount = 0;
        int categoryCount = 0;
        List<WebElement> objCategories = null;
        List<WebElement> objTiles = null;
        int rowCount = 0;
        boolean blnRes = true;
        int expected_AllOpenValidations_count = 0;
        int actual_AllOpenValidations_count = 0;
        Alert oAlert = null;
        String fileName = null;
        int stateValidationRowCount = 0;
        try {
            Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "StopFraud"));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "StopFraud->Validations page");
            validations_Categories = "Action Type: TIN Matching#Action Type: Profile Authorization Form#Action Type: Verbal Validation#Action Type: Dual Approval#Action Type: Address Validation#Action Type: OFAC#Action Type: Website Validation#Action Type: Account Validation".split("#");
            objCategories = oBrowser.findElements(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_SearchResultGrid_rows + "//td[contains(@aria-label, 'Column Action Type, Value')]"));
            categoryCount = objCategories.size();
            Assert.assertTrue(validations_Categories.length == categoryCount);
            for (int i = 0; i < categoryCount; i++) {
                objCategories = oBrowser.findElements(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_SearchResultGrid_rows + "//td[contains(@aria-label, 'Column Action Type, Value')]"));
                Assert.assertEquals(objCategories.get(i).getText(), validations_Categories[i], "Mis-match in actual: '" + objCategories.get(i).getText() + "' & expected: '" + validations_Categories[i] + "' values");
                Assert.assertTrue(appInd.clickObject(oBrowser, objCategories.get(i)));
                appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_StopFraud_Category_Grid + "//table//tr[@role='row' and contains(@class, 'dx-data-row')]"), "Clickable", "", waitTimeOut);
                appDep.threadSleep(2000);
                rowCount = oBrowser.findElements(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_StopFraud_Category_Grid + "//table//tr[@role='row' and contains(@class, 'dx-data-row')]")).size();
                Assert.assertTrue((rowCount > 0), "The stopFraud category '" + validations_Categories[i] + "' doesnot contains any row of data values");
                objCategories = oBrowser.findElements(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_SearchResultGrid_rows + "//td[contains(@aria-label, 'Column Action Type, Value')]"));
                if (objCategories.get(i).getText().contains("State Validation")) {
                    stateValidationRowCount = rowCount;
                }
                Assert.assertTrue(appInd.clickObject(oBrowser, objCategories.get(i)));
                appDep.threadSleep(2000);
            }


            validations_TileNames = "Address Validation#Website Validation#OFAC Scan#TIN Matching#Account Verification#Profile Authorization Form#Verbal Validation#Dual Approval".split("#");
            objTiles = oBrowser.findElements(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_StopFraud_TileHeaders + "/div[@class='tile-header']"));
            tilesCount = objTiles.size();
            Assert.assertTrue(validations_TileNames.length == tilesCount);
            expected_AllOpenValidations_count = Integer.parseInt(appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ShowAllOpenValidations_Link, "Text").trim());

            for (int i = 0; i < tilesCount; i++) {
                Assert.assertEquals(objTiles.get(i).getText(), validations_TileNames[i], "Mis-match in actual: '" + objTiles.get(i).getText() + "' & expected: '" + validations_TileNames[i] + "' values");
                if (objTiles.get(i).getText().contains("State Validation")) {
                    blnRes = false;
                }
                actual_AllOpenValidations_count += Integer.parseInt(appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_StopFraud_TileHeaders + "/div[@class='tile-header']/following-sibling::div//div[@class='tile-content'])[" + (i + 1) + "]"), "Text").trim());
            }

            if (blnRes == false) {
                reports.writeResult(oBrowser, "Fail", "The Validations Tiles contains 'State Validation' section");
                Assert.assertTrue(false, "The Validations Tiles contains 'State Validation' section");
            }
            Assert.assertTrue((actual_AllOpenValidations_count + stateValidationRowCount) == expected_AllOpenValidations_count, "mis-match in the total count of all the open validations");
            appInd.moveToElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_StopFraud_Export_Btn);
            appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_StopFraud_Export_Btn);
            reports.writeResult(oBrowser, "Screenshot", "StopFraud->Validations. Exporting the 'Supplier Validations' data excel file");
            appDep.threadSleep(2000);
            if(appInd.isAlertPresent(oBrowser)){
                oAlert = oBrowser.switchTo().alert();
                fileName = oAlert.getText();
                oAlert.accept();
            }
            appDep.threadSleep(5000);

            blnRes = appDep.validateDownloadedFileAndDelete(oBrowser, fileName, ".xlsx", "", "Yes");
            if (blnRes) {
                reports.writeResult(oBrowser, "Pass", "The 'Supplier Validations' data excel file was exported successful");
            } else {
                reports.writeResult(oBrowser, "Fail", "Failed to export the 'Supplier Validations' data excel file");
                Assert.assertTrue(false, "Failed to export the 'Supplier Validations' xlsx file");
            }

            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateStopFraudValidationsPageFunctionality()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateStopFraudValidationsPageFunctionality()' method. " + e);
            return false;
        } finally {
            validations_TileNames = null; validations_Categories = null; objCategories = null; objTiles = null; oAlert = null; fileName = null;
        }
    }

    /********************************************************
     * Method Name      : validateSearchFunctionalitiesUnderStopFraudValidationLookup()
     * Purpose          : user validates the 'StopFraud-->Validation Lookup' UI serach functionality
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateSearchFunctionalitiesUnderStopFraudValidationLookup(WebDriver oBrowser, DataTable dataTable) {
        List<Map<String, String>> supplierData = null;
        int rowNum = 0;
        int flag = 0;
        try {
            supplierData = dataTable.asMaps(String.class, String.class);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_StopFraud_ValidationLookup_link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Clear_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SearchBySupplierName_Edit, supplierData.get(0).get("SupplierName")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SearchByPayNetId_Edit, supplierData.get(0).get("PayNetID")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SearchByTIN_Edit, supplierData.get(0).get("TIN")));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Filter_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Search functionality under 'StopFraud->Validation Lookup' page");
            rowNum = oBrowser.findElements(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Grid + "/tbody/tr")).size();
            for (int i = 0; i < rowNum; i++) {
                flag++;
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Grid + "/tbody/tr[" + (i + 1) + "]/td[1]"), "Text", supplierData.get(0).get("PayNetID")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Grid + "/tbody/tr[" + (i + 1) + "]/td[2]"), "Text", supplierData.get(0).get("SupplierName")));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Grid + "/tbody/tr[" + (i + 1) + "]/td[3]"), "Text", supplierData.get(0).get("TIN")));
            }

            if (flag == 0) {
                reports.writeResult(oBrowser, "Fail", "Search was not found for the given search criteria for the 'Suppliers'");
                return false;
            }

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Clear_Btn));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SearchBySupplierName_Edit, "placeholder", "Search by Supplier Name"));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SearchByPayNetId_Edit, "placeholder", "Search by PayNet ID"));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SearchByTIN_Edit, "placeholder", "Search by TIN"));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateSearchFunctionalitiesUnderStopFraudValidationLookup()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateSearchFunctionalitiesUnderStopFraudValidationLookup()' method. " + e);
            return false;
        } finally {supplierData = null;}
    }

    /********************************************************
     * Method Name      : getAccountValidationID_CreatePreNoteFailureCase()
     * Purpose          : to validate Pre-Note Failure support case should automatic create once we mark TGBR as unable to validate
     * Author           : Deepak
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean getAccountValidationID_CreatePreNoteFailureCase(WebDriver oBrowser) {
        JSONArray caseDetails;
        String queryKey = "StopFraudTINValidation";
        String notes = "CPAY-2766 : TGBR Pre-Note Failure Case Notes";

        Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "StopFraud"));
        appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
        reports.writeResult(oBrowser, "Screenshot", "StopFraud->Validations page");
        appInd.clickObject(oBrowser, By.linkText("Validation Lookup"));
        try {
            caseDetails = apiDataProvider.getAPIDataProviderResponse(queryKey, new Object[] {});
            Collection<Integer> paynetIDs = ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).values();
            for(Integer paynetId : paynetIDs){
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_StopFraud_ValidationLookup_link));
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SearchByPayNetId_Edit, "Clickable", "", waitTimeOut);
                Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SearchByPayNetId_Edit, String.valueOf(paynetId.intValue())));
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Filter_Btn));
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", minTimeOut);

                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Grid + "//td[1]")));
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", minTimeOut);
                reports.writeResult(oBrowser, "Screenshot", "StopFraud->Validations page");
                if(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_Generic_Text,"Payment (Electronic)' or text()='Payment (Electronic) *")))){
                    appInd.clickObject(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_Generic_Text,"Payment (Electronic)' or text()='Payment (Electronic) *")));
                    appDep.threadSleep(3000);
                    if(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_StopFraud_ValidationLookUp_PaymentGrid + "//table[contains(@id, 'DataTables_Table_')]//td[contains(text(), 'TGBR Validation')]/following-sibling::td/span[text()='Pending']"))){
                        reports.writeResult(oBrowser, "screenshot", "StopFraud : ValidationType status validate successfully");
                        appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_StopFraud_ValidationLookUp_PaymentGrid + "//table[contains(@id, 'DataTables_Table_')]//td[contains(text(), 'TGBR Validation')]/following-sibling::td/button[text()='Unable to Validate']"));
                        appDep.threadSleep(1000);
                        appInd.setObject(oBrowser, By.id("unable_to_validate_modal_notes"), notes);
                        appInd.clickObject(oBrowser, By.id("btn_unable_to_validate"));
                        appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_UnableToValidate_Success_Msg + "//p"), "Text", "Success!", waitTimeOut);
                        Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_UnableToValidate_Success_Msg + "//p"), "Text", "Success!"));
                        appInd.clickObject(oBrowser, By.id("btn_unable_to_validate"));
                        appDep.threadSleep(1000);
                        oBrowser.switchTo().alert().accept();
                        appDep.threadSleep(1000);
                        appInd.setObject(oBrowser, By.id("primary_contact_stopfraud"), notes);
                        appInd.clickObject(oBrowser, By.id("create_pre_note_failure_support_case_btn"));
                        appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_LogActivity_Btn), "visibility", "", waitTimeOut);
                        reports.writeResult(oBrowser, "screenshot", "Case is successfully created and verify case detail page");
                        //----------------------------------- verify Additional information tile -----------------------------------
                        Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("//div[@id='section_contact_information']//h3"),"text").contains("Additional Information"));
                        Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AdditionalInfoTile, "1", "Primary/enrollment contact", "1")), "text"),notes);
                        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_LogActivity_Btn)));
                        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Reassign_Btn));
                        //----------------------------------- verify Case Information -----------------------------------
                        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_CaseStatus, "Open"))));
                        Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_CaseInfoTile, "1", "Assigned to", "1")), "text"),appInd.getPropertyValueByKeyName("qaUserName"));
                        Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_CaseInfoTile, "2", "Client", "2")), "text"),"");
                        Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_CaseInfoTile, "3", "Created", "3")), "text"),appInd.dateAddAndReturn("MM/dd/yyyy",0));
                        Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_CaseInfoTile, "4", "Case Type", "4")), "text"),"Pre-note Failure");
                        Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_CaseInfoTile, "5", "Priority", "5")), "text"),"Low");
                        Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_CaseInfoTile, "6", "Area Name", "6")), "text"),"Company Information");
                        String newCaseID = appInd.getTextOnElement(oBrowser, By.xpath("//div[@id='section_case_information']//h3"), "text").split(" ")[1];
                        //----------------------------------- verify Supplier Information -----------------------------------
                        Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_SupplierInfoTile, "2", "PayNet ID", "2")), "text"), String.valueOf(paynetId.intValue()));
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'getAccountValidationID_CreatePreNoteFailureCase()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'getAccountValidationID_CreatePreNoteFailureCase()' method. " + e);
            return false;
        }
        return true;
    }





    /********************************************************
     * Method Name      : validatePaymentHoldAndRelease()
     * Purpose          : to validate the Paument Hold and Release functionality from Validation lookup tab
     * Author           : Deepak
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validatePaymentHoldAndRelease(WebDriver oBrowser, String queryKey){
        JSONArray payNetIds;
        String payNetID;
        try{
            Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "StopFraud"));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "StopFraud->Validations page");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.linkText("Validation Lookup")));

            payNetIds = apiDataProvider.getAPIDataProviderResponse(queryKey, new Object[] {});
            payNetID = ((LinkedHashMap) ((JSONArray) payNetIds.get(0)).get(0)).get("CompanyID").toString();

            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SearchByPayNetId_Edit, payNetID));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Filter_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Search functionality under 'StopFraud->Validation Lookup' page");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Grid + "/tbody/tr")));
            appDep.waitForTheElementState(oBrowser, By.xpath("//a[text()='Payment (Electronic)' or text()='Payment (Electronic) *']"), "visibility", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("//a[text()='Payment (Electronic)' or text()='Payment (Electronic) *']")));
            reports.writeResult(oBrowser, "Screenshot", "Payment (Electronic) tab visible");
            Assert.assertTrue(verifyPaymentFlow("Hold Payment",true));
            Assert.assertTrue(verifyPaymentFlow("Hold Payment",false));
            Assert.assertTrue(verifyPaymentFlow("Remove hold",false));
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validatePaymentHoldAndRelease()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validatePaymentHoldAndRelease()' method. " + e);
            return false;
        }
        return true;
    }

    private boolean verifyPaymentFlow(String paymentType, boolean isClearToValidate){
        By payment_Model,payment_model_submit,payment_model_errorMsg, payment_model_note, payment_model_close;
        String modelTitle, postProcessIcon, unableToValidate, username;
        String validationPath = "(//div[@id='divPaymentElectronicValidationsContainer']//table)[3]/tbody/tr/td[text()='TGBR Validation']/..";
        String paymentInfo = "(//div[@id='divPaymentElectronicValidationsContainer']//table)[%s]/tbody/tr[%s]";
        String auditValidation = "(//div[@id='tab_audit_area_infos']//table/tbody)[2]/tr[1]/td[%s]";
        String validationDataModel = "//div[@id='validationDataDetailsModal' and contains(@style,'block')]";
        if(paymentType.equals("Hold Payment")){
            payment_Model = By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.STOP_FRAUD_PAYMENT_MODEL,"payment_hold_modal"));
            payment_model_submit = By.id(String.format(PayCRM_Modules_DailyGrindUIPage.STOP_FRAUD_PAYMENT_MODEL_SUBMIT,"btn_payment_hold_modal"));
            payment_model_errorMsg = By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.STOP_FRAUD_PAYMENT_MODEL_ERROR_MSG,"payment_hold_modal_result_msg"));
            payment_model_note = By.id(String.format(PayCRM_Modules_DailyGrindUIPage.STOP_FRAUD_PAYMENT_MODEL_NOTES,"hold_payment_modal_notes"));
            payment_model_close = By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.STOP_FRAUD_PAYMENT_MODEL_CLOSE,"payment_hold_modal"));
            modelTitle = "Payment Hold";
            postProcessIcon = "Remove hold";
            unableToValidate = "Unable to validate";
            username = "unabletovalidate_HoldPayment@corcentric.com";
        }
        else{
            payment_Model = By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.STOP_FRAUD_PAYMENT_MODEL,"payment_release_modal"));
            payment_model_submit = By.id(String.format(PayCRM_Modules_DailyGrindUIPage.STOP_FRAUD_PAYMENT_MODEL_SUBMIT,"btn_payment_release_modal"));
            payment_model_errorMsg = By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.STOP_FRAUD_PAYMENT_MODEL_ERROR_MSG,"payment_release_modal_result_msg"));
            payment_model_note = By.id(String.format(PayCRM_Modules_DailyGrindUIPage.STOP_FRAUD_PAYMENT_MODEL_NOTES,"release_payment_modal_notes"));
            payment_model_close = By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.STOP_FRAUD_PAYMENT_MODEL_CLOSE,"payment_release_modal"));
            modelTitle = "Remove Hold";
            postProcessIcon = "Hold Payment";
            unableToValidate = "Complete";
            username = "unabletovalidate_ReleasePayment@corcentric.com";
        }
        try{
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("//a[text()='Payment (Electronic)' or text()='Payment (Electronic) *']")));
            String holdPayment = String.format(PayCRM_Modules_DailyGrindUIPage.PAYMENT_ICON,paymentType);
            appDep.waitForTheElementState(oBrowser, By.xpath(holdPayment), "visibility", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(holdPayment)));
            reports.writeResult(oBrowser, "Screenshot", paymentType+" icon is visible");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(holdPayment)));
            appDep.waitForTheElementState(oBrowser, payment_Model, "visibility", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", paymentType+" Model is visible");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.H4_HEADING,modelTitle))));
            Assert.assertTrue(appInd.clickObject(oBrowser, payment_model_submit));
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, payment_model_errorMsg, "text"), "Please add a note as it's a required field");
            reports.writeResult(oBrowser, "Screenshot", paymentType+" Model with validation error message");
            Assert.assertTrue(appInd.setObject(oBrowser, payment_model_note,"Automation Regression Testing"));
            Assert.assertTrue(appInd.clickObject(oBrowser, payment_model_submit));
            appDep.waitForTheElementState(oBrowser, payment_model_errorMsg, "text", "Success", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Payment hold validate successfully");
            Assert.assertTrue(appInd.clickObject(oBrowser, payment_model_close));
            appDep.threadSleep(3000);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("//a[text()='Payment (Electronic)' or text()='Payment (Electronic) *']")));
            //---------------------------------- PAYMENT VALIDATION FLOW -----------------------------------
            String remove_hold = String.format(PayCRM_Modules_DailyGrindUIPage.PAYMENT_ICON,postProcessIcon);
            appDep.waitForTheElementState(oBrowser, By.xpath(remove_hold), "visibility", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", remove_hold+" icon should be visible");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(remove_hold)));

            if(isClearToValidate){
                appInd.moveToElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_stopFraudPaymentsTabClearUnableToValidateButton));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_stopFraudPaymentsTabClearUnableToValidateButton)));
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_stopFraudPaymentsTabClearUnableToValidateButton)));
                appDep.threadSleep(600);
                oBrowser.switchTo().alert().accept();
                appDep.threadSleep(2000);
                Assert.assertTrue(oBrowser.switchTo().alert().getText().equals("Successfully cleared unable-to-validate status"));
                oBrowser.switchTo().alert().accept();
                appDep.threadSleep(2000);
                reports.writeResult(oBrowser, "Screenshot", " Clean Unable to Validate button is passed");
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("//a[text()='Payment (Electronic)' or text()='Payment (Electronic) *']")));
                appDep.waitForTheElementState(oBrowser, By.xpath(holdPayment), "visibility", "", waitTimeOut);
                reports.writeResult(oBrowser, "Screenshot", "Clear Unable to Validate process complete");
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(holdPayment)));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_PaymentElectronic_Grid + "//td[text()='TGBR Validation']/following-sibling::td[@class='pending-class']/span"), "Text", "Pending"));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_PaymentElectronic_Grid + "//td[text()='TGBR Validation']/following-sibling::td/button[1]"), "Text", "Validate"));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_PaymentElectronic_Grid + "//td[text()='TGBR Validation']/following-sibling::td/button[2]"), "Text", "Unable to Validate"));
                Assert.assertTrue(appInd.clickObject(oBrowser, By.linkText("Audit Info")));
                appDep.threadSleep(2000);
                String dateTime = appInd.getTextOnElement(oBrowser, By.xpath(String.format(auditValidation,"1")+"/div"), "text");
                Assert.assertTrue(dateTime.startsWith(appInd.dateAddAndReturn("yyyy-MM-dd",0)));
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(String.format(auditValidation,"3")),"text").equalsIgnoreCase("Clear Unable to Validate"));
                return true;
            }
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_stopFraudPaymentsElectronicsTab)));
            String name = appInd.getTextOnElement(oBrowser, By.xpath(String.format(paymentInfo,"2","1")+"/td/span"),"text");
            String account = appInd.getTextOnElement(oBrowser, By.xpath(String.format(paymentInfo,"2","1")+"/td[3]"),"text");
            String bank = appInd.getTextOnElement(oBrowser, By.xpath(String.format(paymentInfo,"2","1")+"/td[4]"),"text");
            String code = appInd.getTextOnElement(oBrowser, By.xpath(String.format(paymentInfo,"2","1")+"/td[5]"),"text");
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(validationPath+"/td[1]"),"text"), "TGBR Validation");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(validationPath+"//span[text()='"+unableToValidate+"']")));
            reports.writeResult(oBrowser, "Screenshot", "TGBR Validation is show as "+unableToValidate);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.linkText("Audit Info")));
            appDep.threadSleep(2000);
            String dateTime = appInd.getTextOnElement(oBrowser, By.xpath(String.format(auditValidation,"1")+"/div"), "text");
            Assert.assertTrue(dateTime.startsWith(appInd.dateAddAndReturn("yyyy-MM-dd",0)));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(String.format(auditValidation,"2")),"text").equalsIgnoreCase(username));
            reports.writeResult(oBrowser, "Screenshot", "User navigate to Audit Info tab and verify basic information");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(String.format(auditValidation,"6")+"/a")));
            appDep.waitForTheElementState(oBrowser, By.xpath(validationDataModel), "visibility", "", waitTimeOut);
            List<WebElement> paymentDetails = appInd.getWebElements(oBrowser, By.xpath(validationDataModel+"//table/tbody/tr/td[2]"));
            Assert.assertTrue(paymentDetails.get(0).getText().equalsIgnoreCase(code));
            Assert.assertTrue(account.endsWith(paymentDetails.get(1).getText().replace("*","")));
            if(paymentType.equals("Hold Payment")){
                Assert.assertTrue(paymentDetails.get(2).getText().equalsIgnoreCase(name));
                Assert.assertTrue(paymentDetails.get(3).getText().equalsIgnoreCase(bank));
            }
            else{
                Assert.assertTrue(paymentDetails.get(2).getText().equalsIgnoreCase(bank));
            }
            reports.writeResult(oBrowser, "Screenshot", "User validate Data Details");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(validationDataModel+"//button")));
            appDep.threadSleep(1000);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.linkText("Audit Info")));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Close the validate Data Details Model");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(String.format(auditValidation,"7")+"/a")));
            appDep.waitForTheElementState(oBrowser, By.xpath(validationDataModel), "visibility", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(validationDataModel+"//textarea"), "value").contains("Automation Regression Testing"));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(validationDataModel+"//li"),"text").contains(name));
            reports.writeResult(oBrowser, "Screenshot", "User validate Validation Details");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(validationDataModel+"//button")));
            appDep.threadSleep(1000);
            reports.writeResult(oBrowser, "Screenshot", "Close validate Validation Details Model");
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifyPaymentFlow()' generic method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifyPaymentFlow()' generic method. " + e);
            return false;
        }
        return true;
    }



    /********************************************************
     * Method Name      : validateGoToWavePage()
     * Purpose          : to validate the GotoWave->Page tabs & Information section columns
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateGoToWavePage(WebDriver oBrowser) {
        String goToWaveName = null;
        String infoSectionColumns = null;
        String Information[];
        try {
            /*appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_Grid_FirstRow, "Clickable", "", waitTimeOut);
            caseNumber = appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_Grid_FirstRow, "Text");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_Grid_FirstRow));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WaveAndOffers_Header, "Text", "Wave & Offers", waitTimeOut);*/
            //caseNumber = clickAndValidateTheSelectedCase(oBrowser, "Daily Grind - Cases");
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WaveAndOffer_GoToWaveLink, "Clickable", "", waitTimeOut);
            goToWaveName = appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WaveAndOffer_GoToWaveLink, "Text");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WaveAndOffer_GoToWaveLink));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_GoToWave_PageTitle, "Text").trim().contains(goToWaveName));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Information_Link));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_Link));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Link));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_Link));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Scripts_Link));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_Link));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Settings_Link));

            infoSectionColumns = "Name#Start Date#End Date#Company#Wave Type#Status#Priority";
            Information = infoSectionColumns.split("#");
            for (int i = 0; i < Information.length; i++) {
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_GoToWave_Information_Section + "//div/dl/dt)[" + (i + 1) + "]"), "Text", Information[i]));
            }

            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateGoToWavePage()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateGoToWavePage()' method. " + e);
            return false;
        } finally {
            goToWaveName = null; infoSectionColumns = null; Information = null;
        }
    }



    /********************************************************
     * Method Name      : validateCasePendLogicForEnrollData()
     * Purpose          : user validates the Case Pend Logic For Enrolled Data
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateCasePendLogicForEnrollData(WebDriver oBrowser, DataTable dataTable) {
        List<Map<String, String>> enrollData = null;
        String caseNumber = null;
        String alarmDate = null;
        String alarmDateFormatted = null;
        try {
            caseNumber = (oBrowser.getCurrentUrl().split("/"))[4];
            enrollData = dataTable.asMaps(String.class, String.class);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_Enroll_Btn));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_VirtualCreditCardDetails_Label + "/div/div/h3"), "Text", "Virtual Credit Card Details", waitTimeOut);
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_VcardEmail_Edit, enrollData.get(0).get("VCardEmail")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_VcardPhone_Edit, enrollData.get(0).get("VCardPhone")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Vcard_Country_List, enrollData.get(0).get("Country")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Vcard_Currency_List, enrollData.get(0).get("Currency")));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_OfferAccepted_SelectOne_Label));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Contact_ChooseContact_Edit, enrollData.get(0).get("OfferAccepted")));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_OfferAccepted_SelectOne_SearchResult_Label));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Activity_List, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Activity_List, enrollData.get(0).get("Activity")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_PhoneNumber_Edit, enrollData.get(0).get("PhoneNumber")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_PhoneType_List, enrollData.get(0).get("PhoneType")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_CaseOutcome_List, enrollData.get(0).get("CaseOutCome")));
            alarmDate = appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.objAlarmDate_Edit, "Value");
            alarmDateFormatted = appInd.formatDateFromOnetoAnother(alarmDate, "yyyy-MM-dd", "MM/dd/yyyy");
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_AdditionalInfo_AdditionalNotes_Textarea, enrollData.get(0).get("Notes")));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Update_Btn));
            appDep.waitForTheElementState(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Result_Update_Grid + "//h3)[1]"), "Text", "Updated", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Result_Update_Grid + "//h3)[1]"), "Text", "Updated"));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Result_Update_Grid + "//p)[1]"), "Text").contains("Case " + caseNumber + " - Consolidated Storage Companies, Inc. has been successfully updated"));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Result_Update_Grid + "//p)[2]"), "Text").contains("Offer saved."));
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Cases_Section + "//dl/dt[text()='Alarm']/following-sibling::dd)[1]"), "Text").contains(alarmDateFormatted));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Cases_Section + "//dl/dt[text()='Last Activity']/following-sibling::dd)[1]"), "Text").contains(enrollData.get(0).get("Activity")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Cases_Section + "//dl/dt[text()='Last Activity']/following-sibling::dd)[1]"), "Text").contains(enrollData.get(0).get("PhoneNumber") + " » " + enrollData.get(0).get("PhoneType")));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Outcome_Label, "Text", enrollData.get(0).get("CaseOutCome")));

            //Offer Log
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_OfferLog_Grid + "//td[1]/span"), "Text").contains(appInd.getDateTimeByTimezone("MM/dd/yyyy", "CST")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_OfferLog_Grid + "//td[2]"), "Text").contains("Offer #1: In Progress"));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_OfferLog_Grid + "//td[3]/span"), "Text").contains(enrollData.get(0).get("Notes")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_OfferLog_Grid + "//td[4]"), "Text").contains(appInd.getPropertyValueByKeyName("qaUserName")));

            //Activity Tab
            appInd.scrollToElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SplitterHorizonal_Line);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_EnrollmentActivities_Grid, "Text", "Enrollment Activities", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_SearchResultGrid_rows + "//table//tr[2]/td[3]"), "Text", "Hold: " + enrollData.get(0).get("CaseOutCome")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_SearchResultGrid_rows + "//table//tr[2]/td[4]"), "Text", "Case Update"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_SearchResultGrid_rows + "//table//tr[2]/td[5]"), "Text", enrollData.get(0).get("Activity")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_SearchResultGrid_rows + "//table//tr[2]/td[6]"), "Text", appInd.getPropertyValueByKeyName("qaUserName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_SearchResultGrid_rows + "//table//tr[2]/td[8]"), "Text", enrollData.get(0).get("Notes")));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateCasePendLogicForEnrollData()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateCasePendLogicForEnrollData()' method. " + e);
            return false;
        } finally {
            enrollData = null; caseNumber = null; alarmDate = null; alarmDateFormatted = null;
        }
    }


    public boolean verifyTrashIconVisibility(String visibility) {
        visibility = visibility.toLowerCase();
        boolean isVisible = visibility.equals("should") ? true : false;
        reports.writeResult(oBrowser, "Screenshot", "The Opened tab links visibility");
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Show_Link),
                "Show Icon is not visible to the functionality");
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Show_Edit_Link),
                "Edit Icon is not visible to the functionality");
        if (isVisible) {
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Show_Edit_Link),
                    "Trash Icon is not visible to the functionality");
        }
        return true;
    }



    /********************************************************
     * Method Name      : validateRelatedCasesAndActivitiesTab()
     * Purpose          : user validated the Related Cases and Activities Tab from Modules-->Daily Grind-->Cases
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateRelatedCasesAndActivitiesTab(WebDriver oBrowser) {
        String caseNumber = null;
        try {
            caseNumber = (oBrowser.getCurrentUrl().split("/"))[4];
            reports.writeResult(oBrowser, "Screenshot", "Case '" + caseNumber + "+' has opened successful");
            appInd.scrollToElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SplitterHorizonal_Line);
            appDep.threadSleep(2000);

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_RelatedCases_Tab));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Related Cases Tab Grid");
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_RelatedCases_Grid + "//table//tr/td/div[text()='Case Number']"), "Text", "Case Number", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_RelatedCases_Grid + "//table//tr/td/div[text()='Case Number']"), "Text", "Case Number"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_RelatedCases_Grid + "//table//tr/td/div[text()='Company Name']"), "Text", "Company Name"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_RelatedCases_Grid + "//table//tr/td/div[text()='Case Type']"), "Text", "Case Type"));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_RelatedCases_Grid + "//tr[contains(@class, 'lines dx-column-lines')]")), "'Related cases' grid doesnot contain any data");

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Activities Tab Grid");

            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Activities_Grid + "//tr[1]/td/div[text()='Activity']"), "Text", "Activity"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Activities_Grid + "//tr[1]/td/div[text()='Activity Type']"), "Text", "Activity Type"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Activities_Grid + "//tr[1]/td/div[text()='Activity Action']"), "Text", "Activity Action"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Activities_Grid + "//tr[1]/td/div[text()='Created By']"), "Text", "Created By"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Activities_Grid + "//tr[1]/td/div[text()='Date Created']"), "Text", "Date Created"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Activities_Grid + "//tr[1]/td/div[text()='Notes']"), "Text", "Notes"));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_Export_Btn));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateRelatedCasesAndActivitiesTab()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateRelatedCasesAndActivitiesTab()' method. " + e);
            return false;
        }
    }



    /********************************************************
     * Method Name      : navigateAndSelectDailyGrindProgramManagements
     * Purpose          : user navigates to Daily Grind-->Program Managements and select the Wave using query
     * Author           : Gudi
     * Parameters       : oBrowser, queryKey
     * ReturnType       : JSONArray
     ********************************************************/
    public JSONArray navigateAndSelectDailyGrindProgramManagements(WebDriver oBrowser, String queryKey){
        String strURL = null;
        String enrollmentID = null;
        JSONArray caseDetails = null;
        try{
            caseDetails = apiDataProvider.getAPIDataProviderResponse(queryKey, new Object[] {});
            enrollmentID = ((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0)).get("cases_id").toString();


            strURL = appInd.getPropertyValueByKeyName("qaURL")+"/enrollments/" + enrollmentID;
            oBrowser.navigate().to(strURL);
            reports.writeResult(oBrowser, "Screenshot", "The constructed URL: " + strURL);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_WaveAndOffers_Information), "Text", "Information", waitTimeOut);
            return caseDetails;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'navigateAndSelectDailyGrindProgramManagements()' method. " + e);
            return null;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'navigateAndSelectDailyGrindProgramManagements()' method. " + e);
            return null;
        }finally {strURL = null; enrollmentID = null; caseDetails = null;}
    }



    /********************************************************
     * Method Name      : validatesActivitiesTabFunctionality()
     * Purpose          : user validates the Activities tab functionality for the selected Wave
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validatesActivitiesTabFunctionality(WebDriver oBrowser){
        String caseNumber = null;
        try{
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appDep.waitForTheElementState(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_Waves_Activities_Grid + "//tr[1][contains(@class, 'row-lines dx-column-lines')])[1]/td[1]"), "Clickable", "", minTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Program Management-->Activities page");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_Waves_Activities_Grid + "//tr[1][contains(@class, 'row-lines dx-column-lines')])[1]/td[1]")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_GoToCase_Link, "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog +"//h4/span)[2]"), "Text", "Offer Notes"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog +"//h4/span)[3]"), "Text", "Enrollment Data"));
            caseNumber = appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog +"//dl/dd/a)[2]"), "Text");
            reports.writeResult(oBrowser, "Screenshot", "Program Management-->Activities-->Notes modal");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_GoToCase_Link));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_GoToCase_Link));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WaveAndOffers_Header, "Text", "Wave & Offers", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Program Management-->Activities-->Case Page");
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_CaseNumber_Label, "Text").contains(caseNumber));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validatesActivitiesTabFunctionality()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validatesActivitiesTabFunctionality()' method. " + e);
            return false;
        }finally {caseNumber = null;}
    }




    /********************************************************
     * Method Name      : createMissingEnrollmentCases()
     * Purpose          : user creates missing Enrollment cases from Modules-->Daily Grind-->Program Management-->Suppliers
     * Author           : Gudi
     * Parameters       : oBrowser, queryKey
     * ReturnType       : boolean
     ********************************************************/
    public boolean createMissingEnrollmentCases(WebDriver oBrowser, String queryKey){
        String supplierName = null;
        String taxID = null;
        String companyName = null;
        String caseType = null;
        try{
            Assert.assertNotNull(navigateAndSelectDailyGrindProgramManagements(oBrowser, queryKey));
            companyName = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_GoToWave_Information_Section + "//dt[text()='Name']/following-sibling::dd[1]"), "Text").trim();
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SuppliersTab_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Program Management->Show Page opened");
            supplierName = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Columns_Label + "//tr[contains(@class, 'dx-row-lines dx-column-lines')]/td[1]"), "Text");
            taxID = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Columns_Label + "//tr[contains(@class, 'dx-row-lines dx-column-lines')]/td[3]"), "Text");

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Columns_Label + "//tr[contains(@class, 'dx-row-lines dx-column-lines')]/td[1]")));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ClientSupplier_Show_Grid +"//dl/dd[1]/a"), "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ClientSupplier_Show_Grid +"//dl/dd[1]/a")));
            reports.writeResult(oBrowser, "Screenshot", "Company Supplier dialog opened");
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ClientSupplier_Show_Grid +"//dl/dd[1]/a"), "Text").contains(supplierName));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ClientSupplier_Show_Grid +"//dl/dd[3]"), "Text").contains(taxID));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ClientSupplier_Show_Grid +"//button[@class='close']")));

            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CompanySuppliers_Section +"//label)[1]"), "Text").contains("Suppliers with missing"));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CompanySuppliers_Section +"//label)[1]"), "Text").contains("Enrollment cases only?"));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_CompanySuppliers_Section +"//div/span[@class='toggle-no']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);

            reports.writeResult(oBrowser, "Screenshot", "'Suppliers With Missing Enrollment Cases only' toggle was clicked");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Columns_Label + "//tr[contains(@class, 'dx-row-lines dx-column-lines')]/td[1]")));
            supplierName = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Columns_Label + "//tr[contains(@class, 'dx-row-lines dx-column-lines')]/td[1]"), "Text");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Columns_Label + "//tr[contains(@class, 'dx-row-lines dx-column-lines')]/td[1]")));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ClientSupplier_Show_Grid +"//dl/dd[1]/a")));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ViewActivities_Btn, "Clickable", "", waitTimeOut);

            reports.writeResult(oBrowser, "Screenshot", "'Enrollment Cases » Case #' page was opened successful");

            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Show_SuppliersName_Label +"//h3/span"), "Text", supplierName));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ClientCases_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Cases grid doenot contais any Enrollment cases.");
            boolean blnRes = appInd.verifyOptionalElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Cases_Grid +"//span[text()='No data']"));
            if(!blnRes){
                int rowCount = oBrowser.findElements(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Cases_Grid + "//tr[contains(@class, 'lines dx-column-lines')]")).size();
                for(int i=0; i<rowCount; i++){
                    caseType = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Cases_Grid + "//tr[contains(@class, 'lines dx-column-lines')]["+(i+1)+"]/td[4]"), "Text");
                    if(caseType.equalsIgnoreCase("Enrollment")){
                        String Company = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Cases_Grid + "//tr[contains(@class, 'lines dx-column-lines')]["+(i+1)+"]/td[2]"), "Text");
                        Assert.assertNotEquals(companyName, Company, "Supplier company with missing Enrollment cases are displayed in the Case Tab");
                    }
                }
            }
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ClientSupplier_Show_Grid +"//button[@class='close']")));


            //Create missing Enrollment case
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Columns_Label + "//tr/td[12]/a[@title='Create missing Enrollment cases'])[1]")));

            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WaveAndOffers_Header, "Text", "Wave & Offers", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "'Enrollment case has been successfully created' confirmation message displayed successful");
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WaveTypeEditConfirmationMessage_Label, "Text").contains("Enrollment case has been successfully created."));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createMissingEnrollmentCases()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createMissingEnrollmentCases()' method. " + e);
            return false;
        }finally {
            supplierName = null; taxID = null;
        }
    }



    /********************************************************
     * Method Name      : validateElementsUnderRecordedEnrollmentsAndAdditionalInformationTab()
     * Purpose          : user validated elements from REcorded Enrollments and Additional Imformation Tab from Modules-->Daily Grind-->Cases
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateElementsUnderRecordedEnrollmentsAndAdditionalInformationTab(WebDriver oBrowser) {
        String tableColNames[];
        int rowNum = 0;
        String caseNumber = null;
        try {
            caseNumber = (oBrowser.getCurrentUrl().split("/"))[4];
            reports.writeResult(oBrowser, "Screenshot", "Case '" + caseNumber + "+' has opened successful");
            appInd.scrollToElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SplitterHorizonal_Line);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_RecordedEnrollment_Link));
            if(appInd.verifyOptionalElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_RecordedEnrollment_Grid +"//p[contains(text(), 'no accepted offers')]"))){
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_RecordedEnrollment_Grid +"//p[contains(text(), 'no accepted offers')]"), "Text", "There are currently no accepted offers."));
                reports.writeResult(oBrowser, "Fail", "The 'Recorded Enrollment' tab doesnot have any grid details");
                Assert.fail("The 'Recorded Enrollment' tab doesnot have any grid details");
            }else{
                appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_RecordedEnrollment_Grid + "//h3[text()='Accepted Offer']"), "Text", "Accepted Offer", waitTimeOut);
                reports.writeResult(oBrowser, "Screenshot", "Recorded Enrollment section has opened successful");
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_RecordedEnrollment_Grid + "//h3[text()='Accepted Offer']"), "Text", "Accepted Offer"));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_RecordedEnrollment_Grid + "//h3[text()='Terms']"), "Text", "Terms"));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_RecordedEnrollment_Grid + "//h3[text()='Bank Information: Payments']"), "Text", "Bank Information: Payments"));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_RecordedEnrollment_Grid + "//h3[text()='Notifications']"), "Text", "Notifications"));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_RecordedEnrollment_Grid + "//h3[text()='Bank Information: Method of ACH Plus Maintenance Fee']"), "Text", "Bank Information: Method of ACH Plus Maintenance Fee"));

                rowNum = oBrowser.findElements(By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_RecordedEnrollment_Grid + "//table)[2]//tr")).size();
                if (rowNum == 3) {
                    tableColNames = "Offer#Payment Type#Wave".split("#");
                } else {
                    tableColNames = "Offer#Payment Type#Wave#Merchant Acquirer#Fees#Limit#Fee Amount".split("#");
                }
                Assert.assertTrue((rowNum == tableColNames.length), "Mis-match in the Recorded Enrollment-->Accepted Offer and expected column name counts");
                for (int i = 0; i < rowNum; i++) {
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_RecordedEnrollment_Grid + "//table)[1]//tr[" + (i + 1) + "]/td[1]"), "Text", tableColNames[i]));
                }

                tableColNames = "Network Maintenance Fee#Payment Terms#Maintenance Fee Method".split("#");
                rowNum = oBrowser.findElements(By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_RecordedEnrollment_Grid + "//table)[2]//tr")).size();
                Assert.assertTrue((rowNum == tableColNames.length), "Mis-match in the Recorded Enrollment-->Virtual Card and expected column name counts");
                for (int i = 0; i < rowNum; i++) {
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_RecordedEnrollment_Grid + "//table)[2]//tr[" + (i + 1) + "]/td[1]"), "Text", tableColNames[i]));
                }
            }


            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_EnrollmentActivities_Grid, "Text", "Enrollment Activities", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "'Activities' tab has opened successful");
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Activities_Grid + "//tr[1]/td/div[text()='Activity']"), "Text", "Activity"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Activities_Grid + "//tr[1]/td/div[text()='Activity Type']"), "Text", "Activity Type"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Activities_Grid + "//tr[1]/td/div[text()='Activity Action']"), "Text", "Activity Action"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Activities_Grid + "//tr[1]/td/div[text()='Created By']"), "Text", "Created By"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Activities_Grid + "//tr[1]/td/div[text()='Date Created']"), "Text", "Date Created"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Activities_Grid + "//tr[1]/td/div[text()='Notes']"), "Text", "Notes"));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_Export_Btn));

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_AdditionalInformation_Link));
            appDep.waitForTheElementState(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_AdditionalInfo_CampaignData + "/parent::div/div/dl/dt)[1]"), "Text", "Enrollment ID:", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "'Additional Information' tab has opened successful");

            tableColNames = "Enrollment ID:#Enrollment Name:#Enrollment History:#Enrollment Acct Name:#Link:#Client Link Status:#Supplier Link Status:#Supplier Link Approved Date:#Total Spend:#Number of Checks:".split("#");
            rowNum = oBrowser.findElements(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_AdditionalInfo_CampaignData + "/parent::div/div/dl/dt")).size();
            Assert.assertTrue((rowNum == tableColNames.length), "Mis-match in the Additional Information-->Campaign Data and expected column name counts");
            for (int i = 0; i < rowNum; i++) {
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_AdditionalInfo_CampaignData + "/parent::div/div/dl/dt)[" + (i + 1) + "]"), "Text", tableColNames[i]));
            }
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateElementsUnderRecordedEnrollmentsAndAdditionalInformationTab()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateElementsUnderRecordedEnrollmentsAndAdditionalInformationTab()' method. " + e);
            return false;
        } finally {tableColNames = null; caseNumber = null;}
    }



    /********************************************************
     * Method Name      : validateActivitiesGrid()
     * Purpose          : to validate the Activities grid for the activities actions
     * Author           : Gudi
     * Parameters       : oBrowser, activitiesType, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateActivitiesGrid(WebDriver oBrowser, String activitiesType, Map<String, String> activities) {
        Calendar cal = null;
        String dateCreated = null;
        try {
            reports.writeResult(oBrowser, "Screenshot", "The Enrollment Case 'Activities' tab details");
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_Activity_Column, "Text", "Closed: " + activities.get("CaseOutCome")));
            if (activitiesType.equalsIgnoreCase("New"))
                Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_ActivityType_Column, "Text", "Case Update"));
            else
                Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_ActivityType_Column, "Text", activities.get("ActivityType")));

            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_CreatedBy_Column, "Text", appInd.getPropertyValueByKeyName("qaUserName")));

            cal = Calendar.getInstance();
            dateCreated = String.valueOf((cal.get(Calendar.MONTH) + 1)) + "/" + String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(cal.get(Calendar.YEAR));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_DateCreated_Column, "Text", dateCreated));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_Notes_Column, "Text", activities.get("Notes")));
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateActivitiesGrid()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateActivitiesGrid()' method. " + e);
            return false;
        } finally {cal = null; dateCreated = null;}
    }



    public boolean applyPAFSettings(WebDriver oBrowser, String companyLevel, String waveLevel){
        try{
            //Company Level Settings
            appDep.navigateURL(oBrowser,"PAFCompanyDetails_CPAY2108","na");
            appDep.waitForTheElementState(oBrowser, By.id("exTab3"),"visibility","",minTimeOut);
            appInd.scrollToElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_tab_names,"Preferences")));
            appInd.clickObject(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_tab_names,"Preferences")));
            appDep.threadSleep(2000);
            appInd.selectObject(oBrowser, By.xpath("(//table[@id='table_preferences']//select)[2]"), companyLevel);
            appInd.clickObject(oBrowser, By.id("btn_update_preferences"));
            //Wave Level Settings
            appDep.navigateURL(oBrowser,"PAFWaveDetail_CPAY2108","na");
            appDep.waitForTheElementState(oBrowser, By.id("exTab3"),"visibility","",minTimeOut);
            appInd.clickObject(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_tab_names,"Settings")));
            appDep.threadSleep(2000);
            appInd.selectObject(oBrowser, By.id("bypass_paf_vc_enrollment"), waveLevel);
            appInd.clickObject(oBrowser, By.id("btn_update_email_template_id"));
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'applyPAFSettings()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'applyPAFSettings()' method. " + e);
            return false;
        }
        return true;
    }




    /********************************************************
     * Method Name      : performPaymentSwitchAndValidateTheSame()
     * Purpose          : user performs payment switch and validates the same
     * Author           : Gudi
     * Parameters       : oBrowser, switchVariant, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean performPaymentSwitchAndValidateTheSame(WebDriver oBrowser, String switchVariant, DataTable dataTable){
        Map<String, String> switchDetails = null;
        int offerNumberSwitchTo = 0;
        int offerNumberSwitchFrom = 0;
        String arrSwitchVariant[];
        String switchFrom = null;
        String switchTo = null;
        try{
            arrSwitchVariant = switchVariant.split("->");
            switchDetails = appDep.switchToPaymentTypes(oBrowser, switchVariant, dataTable);
            Assert.assertTrue(switchDetails.get("TestPassed").equalsIgnoreCase("True"), "The 'switchToPaymentTypes()' method was failed.");

            if(switchDetails.get("paymentSwitch").equalsIgnoreCase("True")){
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_WaveOffer_Tabs + "//a[@class='nav-link offer_accepted active'][contains(text(), '"+switchDetails.get("switchToPaymentName")+"')]")));
                reports.writeResult(oBrowser, "Screenshot", "validating the details for '"+switchVariant+"' payment switch under 'Offer Log' section");
                //Offer Log section validation
                offerNumberSwitchTo = Integer.parseInt((switchDetails.get("switchToPaymentNameAndNumber").split("-"))[0].trim());
                offerNumberSwitchFrom = Integer.parseInt((switchDetails.get("switchFromNameAndNumber").split("-"))[0].trim());
                int index = 1; int paymentNum = offerNumberSwitchTo;
                for(int i=offerNumberSwitchTo; i>=1; i--){
                    if(i==offerNumberSwitchTo){
                        Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_OfferLog_Grid + "//tr["+(index)+"]/td[2]"), "Text").contains("Offer #"+paymentNum+": Accepted"));
                    }else{
                        Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_OfferLog_Grid + "//tr["+(index)+"]/td[2]"), "Text").contains("Offer #"+paymentNum+": Declined"));
                    }
                    Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_OfferLog_Grid + "//tr["+(index)+"]/td[3]"), "Text").contains(switchDetails.get("Notes")));
                    Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_OfferLog_Grid + "//tr["+(index)+"]/td[3]"), "Text").contains(switchDetails.get("Notes")));
                    index++;
                    paymentNum--;
                }

                //Activities tab section validation
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_Link));
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_EnrollmentActivities_Grid, "Text", "Enrollment Activities", minTimeOut);
                appDep.waitForTheElementState(oBrowser, AssistedPaymentServicesUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                appInd.scrollToElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SplitterHorizonal_Line);
                reports.writeResult(oBrowser, "Screenshot", "validating the details for '"+switchVariant+"' payment switch under 'Activities' tab");
                switchFrom = appDep.switchPaymentAbbreviation(arrSwitchVariant[0]);
                switchTo = appDep.switchPaymentAbbreviation(arrSwitchVariant[1]);
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_EnrollmentCases_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td)[3]"), "Text").contains("Updated Offer from Offer #"+offerNumberSwitchFrom+" - "+switchFrom+" to Offer #"+offerNumberSwitchTo+" - "+switchTo+" by "+appInd.getPropertyValueByKeyName("qaUserName")));
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_EnrollmentCases_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td)[4]"), "Text").contains("Switch Offer Number"));
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_EnrollmentCases_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td)[6]"), "Text").contains(appInd.getPropertyValueByKeyName("qaUserName")));
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_EnrollmentCases_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td)[8]"), "Text").contains(switchDetails.get("Notes")));


                //Show view section
                appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Show_Link);
                appDep.waitForTheElementState(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog +"//h4)[1]"), "Visibility", "", minTimeOut);
                reports.writeResult(oBrowser, "Screenshot", "validating the details for '"+switchVariant+"' payment switch under 'Show Activities' tab");
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog +"//h4)[1]"), "Text").contains("Switch Offer Number"));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog +"//h4)[2]/span"), "Text", "Activity Details"));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog +"//h4)[3]/span"), "Text", "Outcome Details"));
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog +"//div[@class='modal-body']/div/div[contains(@style, 'padding')]"), "Text").contains("No additional outcome details found."));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog +"//h4)[4]/span"), "Text", "Notes"));
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog + "//div[contains(@style, '!important;')]"), "Text", switchDetails.get("Notes")));

                Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog +"//li)[1]"), "Text").contains("Error -"));
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog +"//li)[2]"), "Text").contains("Success - true"));
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog +"//li)[3]"), "Text").contains("New offer type - " + switchTo));
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog +"//li)[4]"), "Text").contains("Old offer type - " + switchFrom));
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog +"//li)[5]"), "Text").contains("New offer number - " + offerNumberSwitchTo));
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog +"//li)[6]"), "Text").contains("Old offer number - " + offerNumberSwitchFrom));
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog +"//li)[7]"), "Text").contains("Switched offer date - " + appInd.getDateTimeByTimezone("yyyy-MM-dd", "CST")));
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog +"//li)[8]"), "Text").contains("Switched offer reason - " + switchDetails.get("switchReason")));
                appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog +"//button[@class='close']"));
            }
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'performPaymentSwitchAndValidateTheSame()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'performPaymentSwitchAndValidateTheSame()' method. " + e);
            return false;
        }finally {
            switchDetails = null; arrSwitchVariant = null; switchFrom = null; switchTo = null;
        }
    }



    /********************************************************
     * Method Name      : createAndEditActivity()
     * Purpose          : to perform Creat & Edit activities for the selected case from Modules->Daily Grind->Cases
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean createAndEditActivity(WebDriver oBrowser, DataTable dataTable) {
        List<Map<String, String>> activitiesData = null;
        Map<String, String> activity = null;
        String timeStamp;
        try {
            activitiesData = dataTable.asMaps(String.class, String.class);
            activity = new HashMap<String, String>();
            timeStamp = appInd.getDateTime("Shhmmss");
            activity.put("Notes", activitiesData.get(0).get("Notes") + timeStamp);
            activity.put("CaseOutCome", activitiesData.get(0).get("CaseOutCome"));
            activity.put("ActivityType", activitiesData.get(0).get("ActivityType"));

            appInd.scrollToElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SplitterHorizonal_Line);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_EnrollmentActivities_Grid, "Visibility", "", waitTimeOut);

            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Activities_Grid + "//tr[1]/td/div[text()='Activity']"), "Text", "Activity"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Activities_Grid + "//tr[1]/td/div[text()='Activity Type']"), "Text", "Activity Type"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Activities_Grid + "//tr[1]/td/div[text()='Activity Action']"), "Text", "Activity Action"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Activities_Grid + "//tr[1]/td/div[text()='Created By']"), "Text", "Created By"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Activities_Grid + "//tr[1]/td/div[text()='Date Created']"), "Text", "Date Created"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Activities_Grid + "//tr[1]/td/div[text()='Notes']"), "Text", "Notes"));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_Export_Btn));

            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_Activity_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_Activity_Btn));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_Activity_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activity_Save_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activity_LogActivity_Title, "Text", "Log Activity"));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activity_Notes_Textarea, activity.get("Notes")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activity_CaseOutcome_List, activity.get("CaseOutCome")));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activity_Save_Btn));
            appDep.threadSleep(4000);

            Assert.assertTrue(validateActivitiesGrid(oBrowser, "New", activity));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Activities_EnrollmentActivities_Grid + "//td[text()='" + activity.get("Notes") + "']/following-sibling::td/a[@title='Edit']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_Update_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activity_EditActivity_Title, "Text", "Edit Activity"));
            timeStamp = appInd.getDateTime("Shhmmss");
            activity.remove("Notes");
            activity.put("Notes", activitiesData.get(0).get("Notes") + timeStamp);
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_ActivityType_List, activity.get("ActivityType")));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_Edit_ActivityNotes_Textarea, activity.get("Notes")));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_Update_Btn));

            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Activities_EnrollmentActivities_Grid + "//td[text()='" + activity.get("Notes") + "']"), "Text", activity.get("Notes"), waitTimeOut);
            appDep.threadSleep(4000);
            Assert.assertTrue(validateActivitiesGrid(oBrowser, "Updated", activity));
            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createAndEditActivity()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createAndEditActivity()' method. " + e);
            return false;
        }finally {
            activitiesData = null; activity = null; timeStamp = null;}
    }



    /********************************************************
     * Method Name      : validateEnrollmentCasesTabsColumns()
     * Purpose          : to validate the columns present in the different tabs viz., Logs, Related cases, Address, Contacts and Transactions etc for Modules->Daily Grind->Cases
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateEnrollmentCasesTabsColumns(WebDriver oBrowser) {
        boolean blnRes = false;
        try {
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_Logs_Tab));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Cases Logs Tab Grid");
            appDep.waitForTheElementState(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Logs_Grid + "/table//tr/th)[1]"), "Text", "Date", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Logs_Grid + "/table//tr/th)[1]"), "Text", "Date"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Logs_Grid + "/table//tr/th)[2]"), "Text", "User"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Logs_Grid + "/table//tr/th)[3]"), "Text", "Action"));

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_RelatedCases_Tab));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Related Cases Tab Grid");
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_RelatedCases_Grid + "//table//tr/td/div[text()='Case Number']"), "Text", "Case Number", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_RelatedCases_Grid + "//table//tr/td/div[text()='Case Number']"), "Text", "Case Number"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_RelatedCases_Grid + "//table//tr/td/div[text()='Company Name']"), "Text", "Company Name"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_RelatedCases_Grid + "//table//tr/td/div[text()='Case Type']"), "Text", "Case Type"));

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_Address_Tab));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Cases Address Tab Grid");
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Address_Grid + "//table//tr/td/div[text()='Address 1']"), "Text", "Address 1", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Address_Grid + "//table//tr/td/div[text()='Address 1']"), "Text", "Address 1"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Address_Grid + "//table//tr/td/div[text()='Address 2']"), "Text", "Address 2"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Address_Grid + "//table//tr/td/div[text()='Address 3']"), "Text", "Address 3"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Address_Grid + "//table//tr/td/div[text()='City']"), "Text", "City"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Address_Grid + "//table//tr/td/div[text()='State / Province']"), "Text", "State / Province"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Address_Grid + "//table//tr/td/div[text()='Postal Code']"), "Text", "Postal Code"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Address_Grid + "//table//tr/td/div[text()='Country']"), "Text", "Country"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Address_Grid + "//table//tr/td/div[text()='Address Type']"), "Text", "Address Type"));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Address_Grid + "//table//tr/td/div[@class='dx-datagrid-text-content']")));

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_Contacts_Tab));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Cases Contacts Tab Grid");
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Contacts_Grid + "//table//tr/td/div[text()='Name']"), "Text", "Name", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Contacts_Grid + "//table//tr/td/div[text()='Name']"), "Text", "Name"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Contacts_Grid + "//table//tr/td/div[text()='Email']"), "Text", "Email"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Contacts_Grid + "//table//tr/td/div[text()='Phone 1']"), "Text", "Phone 1"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Contacts_Grid + "//table//tr/td/div[text()='Phone 1 Type']"), "Text", "Phone 1 Type"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Contacts_Grid + "//table//tr/td/div[text()='Phone 2']"), "Text", "Phone 2"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Contacts_Grid + "//table//tr/td/div[text()='Phone 2 Type']"), "Text", "Phone 2 Type"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Contacts_Grid + "//table//tr/td/div[text()='Status']"), "Text", "Status"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Contacts_Grid + "//table//tr/td/div[text()='Priority']"), "Text", "Priority"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Contacts_Grid + "//table//tr/td/div[text()='Contact Type']"), "Text", "Contact Type"));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Contacts_Grid + "//table//tr/td/div[@class='dx-datagrid-text-content']")));

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_Transactions_Tab));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Cases Transactions Tab Grid");
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Transactions_Grid + "//table//tr/td/div[text()='Name']"), "Text", "Name", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Transactions_Grid + "//table//tr/td/div[text()='Name']"), "Text", "Name"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Transactions_Grid + "//table//tr/td/div[text()='Payment ID']"), "Text", "Payment ID"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Transactions_Grid + "//table//tr/td/div[text()='Payment Reference']"), "Text", "Payment Reference"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Transactions_Grid + "//table//tr/td/div[text()='Total Spend']"), "Text", "Total Spend"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Transactions_Grid + "//table//tr/td/div[text()='Type']"), "Text", "Type"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Transactions_Grid + "//table//tr/td/div[text()='Created']"), "Text", "Created"));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Transactions_Grid + "//table//tr/td/div[@class='dx-datagrid-text-content']")));

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Transactions_Export_Btn));
            appDep.threadSleep(1000);
            if (appInd.isAlertPresent(oBrowser)) {
                oBrowser.switchTo().alert().accept();
                appDep.threadSleep(2000);
            } else {
                reports.writeResult(oBrowser, "Fail", "Unable to display the Export Transactions confirmation alert.");
                Assert.assertTrue(false, "Failed to get the Export Transactions confirmation alert");
            }

            blnRes = appDep.validateDownloadedFileAndDelete(oBrowser, "Transactions" + appInd.getDateTime("yyyy-MM-dd"), ".xlsx", "", "Yes");

            if (blnRes) {
                reports.writeResult(oBrowser, "Pass", "The 'Transaction' data excel file was exported successful");
            } else {
                reports.writeResult(oBrowser, "Fail", "Failed to export the 'Transaction' data excel file");
                Assert.assertTrue(false, "Failed to export the 'Transactions' xlsx file");
            }
            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateEnrollmentCasesTabsColumns()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateEnrollmentCasesTabsColumns()' method. " + e);
            return false;
        }
    }




    /********************************************************
     * Method Name      : createSupplier()
     * Purpose          : to create supplier, enroll it and collecte information.
     * Author           : Deepak
     * Parameters       : oBrowser, paymentType
     * ReturnType       : boolean
     ********************************************************/
    public boolean createSupplier(WebDriver oBrowser, String paymentType){
        Map<String, String> supplierInfoMap = new HashMap<>();
        String tin = String.valueOf(appInd.getRandomNumber(9));
        String id = "9898"+appInd.getRandomNumber(4);
        String supplierName = "CPAY_"+id+"_"+appInd.getRandomNumber(6);
        String mobile = "9898"+appInd.getRandomNumber(6);
        String address = "Address_"+appInd.getRandomNumber(10);
        String name = "FName_"+appInd.getRandomNumber(3)+" "+"LName_"+appInd.getRandomNumber(3);
        String email = "corcentric"+appInd.getRandomNumber(5)+"@cpay.com";
        String city = "City_"+appInd.getRandomNumber(4);
        String zip = String.valueOf(appInd.getRandomNumber(5));
        String enroll_ACH_RoutingNo = "071000039";
        String bankAccountNo = "00000000"+appInd.getRandomNumber(9);

        supplierInfoMap.put("TIN", tin);
        supplierInfoMap.put("SupplierName", supplierName);
        supplierInfoMap.put("Phone",mobile);
        supplierInfoMap.put("SupplierContactName",name);
        supplierInfoMap.put("SupplierContactEmail",email);
        supplierInfoMap.put("SupplierContactAddress",address);
        supplierInfoMap.put("SupplierContactCity",city);
        supplierInfoMap.put("SupplierContactZip",zip);
        supplierInfoMap.put("SupplierEnrollACHRoutingNo",enroll_ACH_RoutingNo);
        supplierInfoMap.put("SupplierBankAccNo",bankAccountNo);
        supplierInfoMap.put("SupplierType",paymentType);
        try{
            //Create New Supplier
            appInd.clickObject(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_tab_names,"Suppliers")));
            appInd.clickObject(oBrowser, By.id("btn_create_supplier"));
            appDep.waitForTheElementState(oBrowser, By.xpath("//div[@id='modal_create_wave_supplier' and contains(@style,'display: block;')]"), "visibility", "", minTimeOut);
            appDep.threadSleep(2000);
            appInd.setObject(oBrowser, By.id("supplier_name"), supplierName);
            appInd.setObject(oBrowser, By.id("supplier_tin"), tin);
            appInd.setObject(oBrowser, By.id("supplier_primary_phone"), mobile);
            appInd.selectObject(oBrowser, By.xpath("//div[@id='origination_source_id']//select"),"Other");
            appInd.setObject(oBrowser, By.id("supplier_contact_name"), name);
            appInd.setObject(oBrowser, By.id("supplier_contact_email"),email);
            appInd.setObject(oBrowser, By.id("supplier_contact_phone_1"), mobile);
            appInd.setObject(oBrowser, By.id("supplier_supplier_address_address_1"), address);
            appInd.setObject(oBrowser, By.id("supplier_supplier_address_city"), city);
            appInd.setObject(oBrowser, By.id("supplier_supplier_address_postal_code"), zip);
            appInd.setObject(oBrowser, By.id("supplier_transaction_amount"), String.valueOf(appInd.getRandomNumber(7)));
            appInd.setObject(oBrowser, By.id("supplier_transaction_tx_count"), "1000");
            appInd.clickObject(oBrowser, By.id("btn_create_wave_supplier"));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, By.id("status"), "text", "Supplier and Case successfully created!", maxWaitTimeOut);
            appDep.waitForTheElementState(oBrowser, By.xpath("//li[@id='supplier']/a/span[text()='View Supplier']"), "visibility", "", maxWaitTimeOut);
            String supplierLink = appInd.getWebElements(oBrowser, By.xpath("//li[@id='supplier']/a")).get(0).getAttribute("href");
            String caseLink = appInd.getWebElements(oBrowser, By.xpath("//li[@id='case']/a")).get(0).getAttribute("href");

            //Create Enrollment of case
            oBrowser.navigate().to(caseLink);
            appDep.waitForTheElementState(oBrowser, By.id("btn_inrollment_form"), "visibility", "", maxWaitTimeOut);
            supplierInfoMap.put("SupplierPayNetURL",appInd.getAttribute(oBrowser, By.xpath("//div[@id='section_case_information']//a[@data-original-title='Supplier URL - Login to PayNet']"), "href"));
            appInd.clickObject(oBrowser, By.id("btn_inrollment_form"));
            appDep.threadSleep(3000);
            appInd.switchToLastWindow(oBrowser);
            appDep.waitForTheElementState(oBrowser, By.xpath("(//div[@id='contact_section']//select)[1]"), "visibility", "", waitTimeOut);
            appInd.selectObject(oBrowser, By.xpath("(//div[@id='contact_section']//select)[1]"), name);
            appInd.selectObject(oBrowser, By.xpath("(//div[@id='address_section']//select)[1]"), address);
            if(paymentType.equalsIgnoreCase("virtual card")){
                appInd.setObject(oBrowser, By.id("inrollment_case_offers_1__virtual_email"), email);
                appInd.setObject(oBrowser, By.id("inrollment_case_offers_1__virtual_card_phone"), mobile);
                appInd.selectObject(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.INROLLMENT_CASE_OFFER_OUTCOME,"1")), "Yes");
                appInd.selectObject(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.INROLLMENT_METHOD_OF_CONTACT,"1")), "Phone Number");
                appInd.setObject(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.INROLLMENT_CASE_OFFER_CONTACT_NAME,"1")), name);
                appInd.setObject(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.INROLLMENT_CASE_OFFER_CONTACT_NUMBER,"1")), mobile);
                appInd.selectObject(oBrowser, By.id("inrollment_case_offers_1__offer_accepted_fees_accepted"), "No");
                appInd.selectObject(oBrowser, By.id("inrollment_case_offers_1__offer_accepted_limit_accepted"), "No");
                appInd.setObject(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.INROLLMENT_CASE_OFFER_ADIITIONAL_NOTE,"1")),"Automation Testing");
                appInd.clickObject(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.INROLLMENT_CASE_ENROLL_BUTTON,"1")));
            }
            else{
                appInd.clickObject(oBrowser, By.xpath("//ul[@id='inrollment_offer_tabs']/li[3]/a")); //Replce this with ACH Plus
                appInd.setObject(oBrowser, By.id("inrollment_case_offers_3__bank_name"), "UBS Regression");
                appInd.setObject(oBrowser, By.id("inrollment_case_offers_3__bank_account_name"), "Regression Account");
                appInd.setObject(oBrowser, By.id("inrollment_case_offers_3__bank_aba_routing_number"), enroll_ACH_RoutingNo);
                appInd.setObject(oBrowser, By.id("inrollment_case_offers_3__bank_account_number"), bankAccountNo);
                appInd.selectObject(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.INROLLMENT_CASE_OFFER_OUTCOME,"3")), "Yes");
                appInd.selectObject(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.INROLLMENT_METHOD_OF_CONTACT,"3")), "Phone Number");

                appInd.setObject(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.INROLLMENT_CASE_OFFER_CONTACT_NAME,"3")), name);
                appInd.setObject(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.INROLLMENT_CASE_OFFER_CONTACT_NUMBER,"3")), mobile);
                appInd.setObject(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.INROLLMENT_CASE_OFFER_ADIITIONAL_NOTE,"3")),"Automation Testing");
                appInd.clickObject(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.INROLLMENT_CASE_ENROLL_BUTTON,"3")));
            }
            appDep.waitForTheElementState(oBrowser, By.xpath("//div[@id='inrollment_results_success']//h3[text()='Success!']"), "visibility", "", maxWaitTimeOut);
            oBrowser.close();
            appInd.switchToLastWindow(oBrowser);

            //Navigate to Supplier page for validation
            oBrowser.navigate().to(supplierLink);
            appDep.waitForTheElementState(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_tab_names,"Validations")), "visibility", "", maxWaitTimeOut);
            appInd.clickObject(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_tab_names,"Validations")));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "visibility", "", minTimeOut);
            cPay2765CreatedsupplierList.add(supplierInfoMap);
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createSupplier()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createSupplier()' method. " + e);
            return false;
        }
        return true;
    }





    /********************************************************
     * Method Name      : createNewSupplier()
     * Purpose          : to create new supplier from Program Management
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : Map<String, String>
     ********************************************************/
    public Map<String, String> createNewSupplier(WebDriver oBrowser, DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Map<String, String> supplierDetails = null;
        String timeStamp = null;
        try{
            timeStamp = appInd.getDateTime("Shhmmss");
            supplierDetails = new HashMap<String, String>();
            inputData = dataTable.asMaps(String.class, String.class);
            supplierDetails.put("supplierName", inputData.get(0).get("supplierName")+timeStamp);
            supplierDetails.put("TIN", appInd.generateTaxID("Shhmmss"));
            supplierDetails.put("Phone", appInd.generatePhoneNumber("9538", "hhmmss"));
            supplierDetails.put("OriginationSource", inputData.get(0).get("OriginationSource"));
            supplierDetails.put("ContactName", inputData.get(0).get("ContactName")+timeStamp);
            supplierDetails.put("ContactEmail", inputData.get(0).get("ContactEmail"));
            supplierDetails.put("ContactPhone", inputData.get(0).get("ContactPhone"));
            supplierDetails.put("ContactType", inputData.get(0).get("ContactType"));
            supplierDetails.put("Country", inputData.get(0).get("Country"));
            supplierDetails.put("Address1", inputData.get(0).get("Address1"));
            supplierDetails.put("City", inputData.get(0).get("City"));
            supplierDetails.put("State", inputData.get(0).get("State"));
            supplierDetails.put("PostalCode", inputData.get(0).get("PostalCode"));
            supplierDetails.put("Amount", inputData.get(0).get("Amount"));
            supplierDetails.put("CheckCount", inputData.get(0).get("CheckCount"));

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SuppliersTab_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_CreateNew_Link, "Clickable", "", minTimeOut);

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_CompanySuppliers_Section +"//div/span[@class='toggle-no']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_CreateNew_Link));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_CreateSupplier_Dialog + "//button[@type='submit']"), "Clickable", "", waitTimeOut);

            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupplierName_Edit, supplierDetails.get("supplierName")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupplierTIN_Edit, supplierDetails.get("TIN")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupplierPhone_Edit, supplierDetails.get("Phone")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_OriginationSource_List, supplierDetails.get("OriginationSource")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupplierContactName_Edit, supplierDetails.get("ContactName")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupplierEmail_Edit, supplierDetails.get("ContactEmail")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupplierPhone1_Edit, supplierDetails.get("ContactPhone")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupplierPhone1Type_List, supplierDetails.get("ContactType")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupplierCountry_List, supplierDetails.get("Country")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupplierAddress1_Edit, supplierDetails.get("Address1")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupplierCity_Edit, supplierDetails.get("City")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_PostalCode_Edit, supplierDetails.get("PostalCode")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Amount_Edit, supplierDetails.get("Amount")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_CheckCount_Edit, supplierDetails.get("CheckCount")));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_CreateSupplier_Dialog + "//button[@type='submit']")));
            appDep.waitForTheElementState(oBrowser, PartnerEnrollmentsUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_CreateSupplier_Dialog + "//div[@Id='status']"), "Text").contains("Supplier and Case successfully created!"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_AdditionalAction_Section + "/following-sibling::ul/li[1]//span"), "Text", "View Supplier"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_AdditionalAction_Section + "/following-sibling::ul/li[2]//span"), "Text", "View Case"));
            supplierDetails.put("TestPassed", "True");
            return supplierDetails;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createNewSupplier()' method. " + e);
            supplierDetails.put("TestPassed", "False");
            return supplierDetails;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createNewSupplier()' method. " + e);
            supplierDetails.put("TestPassed", "False");
            return supplierDetails;
        }finally {inputData = null; supplierDetails = null; timeStamp = null;}
    }









    /********************************************************
     * Method Name      : verifyEnrollCaseReOpenFlow()
     * Purpose          : This step is cover Reopen functionality for enroll case.
     * Author           : Deepak
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean verifyEnrollCaseReOpenFlow(WebDriver oBrowser){
        try{
            appDep.waitForTheElementState(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_tab_names,"Cases")), "visibility", "", maxWaitTimeOut);
            appInd.clickObject(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_tab_names,"Cases")));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "screenshot", "Case tab is open");
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "visibility", "", minTimeOut);
            appInd.clickObject(oBrowser, By.xpath("(//div[@id='divSupplierCasesContainer']//table)[2]//tr[1]"));
            appDep.threadSleep(1000);
            appInd.switchToLastWindow(oBrowser);
            reports.writeResult(oBrowser, "screenshot", "Reopen case page is open");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_Reopen_Btn));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_Enroll_Btn));
            String caseNum = oBrowser.getCurrentUrl().split("/")[4];
            //reopen the case
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_Reopen_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ReopenCase_Reopen_Btn, "Clickable", "", waitTimeOut);
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "screenshot", "Reopen case model is open");
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ReopenCases_Dialog, "Text", "Reopen Case - " + caseNum));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Reopen_Notes_Textarea, "Re-opening the closed case"));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ReopenCase_Reopen_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ReopenConfirmationMessage_Label, "Visibility", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ReopenConfirmationMessage_Label, "Text").trim().contains("Case has been successfully reopened"));
            reports.writeResult(oBrowser, "screenshot", "Case Reopen successfully completed");
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifyEnrollCaseReOpenFlow()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifyEnrollCaseReOpenFlow()' method. " + e);
            return false;
        }
        return true;
    }




    /********************************************************
     * Method Name      : generateAndVerifyMultipleValidations()
     * Purpose          : This step is cover CPAY-2765 flow but due to time crunch this step is ON HOLD, will continue once we get a time.
     * Author           : Deepak
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean generateAndVerifyMultipleValidations(WebDriver oBrowser){
        try {
            System.out.println(cPay2765CreatedsupplierList.size());
            cPay2765CreatedsupplierList.stream().forEach(supplier -> supplier.forEach((k,v) -> System.out.println("Key - "+k+"  <>  Value - "+v)));
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'generateAndVerifyMultipleValidations()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'generateAndVerifyMultipleValidations()' method. " + e);
            return false;
        }
        return true;
    }




    /********************************************************
     * Method Name      : reopenClosedCasesAndValidate()
     * Purpose          : to validate user can re-open the closed cases for Modules->Daily Grind->Cases
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean reopenClosedCasesAndValidate(WebDriver oBrowser, DataTable dataTable) {
        List<Map<String, String>> reopenData = null;
        Map<String, String> reOpen = null;
        String timeStamp = null;
        String caseNumber = null;
        try {
            reopenData = dataTable.asMaps(String.class, String.class);
            reOpen = new HashMap<String, String>();
            timeStamp = appInd.getDateTime("Shhmmss");
            reOpen.put("ReopenReason", reopenData.get(0).get("ReopenReason") + "_" + timeStamp);
            reOpen.put("ResumeOnOffer", reopenData.get(0).get("ResumeOnOffer"));

            /*appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_Grid_FirstRow, "Clickable", "", waitTimeOut);
            caseNumber = appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_Grid_FirstRow, "Text");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_Grid_FirstRow));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WaveAndOffers_Header, "Text", "Wave & Offers", waitTimeOut);*/
            //caseNumber = clickAndValidateTheSelectedCase(oBrowser, "Daily Grind - Cases");
            caseNumber = (oBrowser.getCurrentUrl().split("/"))[4];
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_Reopen_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_Reopen_Btn));

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_Reopen_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ReopenCase_Reopen_Btn, "Clickable", "", waitTimeOut);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ReopenCases_Dialog, "Text", "Reopen Case - " + caseNumber));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Reopen_Notes_Textarea, reOpen.get("ReopenReason")));
            if (reOpen.get("ResumeOnOffer") != null)
                Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_reopen_ResumeOnOffer_List + ""), reOpen.get("ResumeOnOffer")));

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ReopenCase_Reopen_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ReopenConfirmationMessage_Label, "Visibility", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ReopenConfirmationMessage_Label, "Text").trim().contains("Case has been successfully reopened"));
            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'reopenClosedCasesAndValidate()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'reopenClosedCasesAndValidate()' method. " + e);
            return false;
        }finally {
            reopenData = null; reOpen = null; timeStamp = null;
        }
    }



    /********************************************************
     * Method Name      : editAndValidateScripts()
     * Purpose          : edit the Scripts from Modules->Daily Grind->Program Management->Scripts
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean editAndValidateScripts(WebDriver oBrowser, DataTable dataTable) {
        List<Map<String, String>> scriptData = null;
        Map<String, String> scripts = null;
        String timeStamp = null;
        try {
            timeStamp = appInd.getDateTime("Shhmmss");
            scriptData = dataTable.asMaps(String.class, String.class);
            scripts = new HashMap<String, String>();
            scripts.put("ScriptName", scriptData.get(0).get("ScriptName") + timeStamp);
            editScriptName = scripts.get("ScriptName");
            scripts.put("ScriptText", scriptData.get(0).get("ScriptText"));
            scripts.put("ScriptType", scriptData.get(0).get("ScriptType"));
            scripts.put("WaveOffer", scriptData.get(0).get("WaveOffer"));

            scripts = editExistingScripts(oBrowser, scripts);
            Assert.assertTrue(scripts.get("TestPassed").equalsIgnoreCase("True"), "The editExistingScripts() method failed");
            Assert.assertTrue(validateScriptDetails(oBrowser, "Edit", scripts));
            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createAndValidateScripts()' method. " + e);
            return true;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createAndValidateScripts()' method. " + e);
            return false;
        }finally {
            scriptData = null; scripts = null; timeStamp = null;
        }
    }



    /********************************************************
     * Method Name      : editAndValidateTheOffers()
     * Purpose          : edit the existing offers from Modules->Daily Grind->Program Management->Offers
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean editAndValidateTheOffers(WebDriver oBrowser, DataTable dataTable) {
        List<Map<String, String>> offerData = null;
        Map<String, String> offers = null;
        String timeStamp = null;
        try {
            timeStamp = appInd.getDateTime("Shhmmss");
            offerData = dataTable.asMaps(String.class, String.class);
            offers = new HashMap<String, String>();
            offers.put("OfferNumber", offerData.get(0).get("OfferNumber"));
            offers.put("OfferType", offerData.get(0).get("OfferType"));
            offers.put("CardPlatform", offerData.get(0).get("CardPlatform"));
            offers.put("CardNetwork", offerData.get(0).get("CardNetwork"));
            offers.put("CardCampainType", offerData.get(0).get("CardCampainType"));
            offers.put("VirtualCardDeliverMethod", offerData.get(0).get("VirtualCardDeliverMethod"));
            offers.put("Currency", offerData.get(0).get("Currency"));
            offers.put("AmountFrom", offerData.get(0).get("AmountFrom"));
            offers.put("AmountTo", offerData.get(0).get("AmountTo"));
            offers.put("ACHLimits", offerData.get(0).get("ACHLimits"));
            offers.put("Terms", offerData.get(0).get("Terms") + timeStamp);
            editOfferTerm = offers.get("Terms");
            offers.put("Percentage", offerData.get(0).get("Percentage"));
            offers.put("VendorinNetworkDiscount", offerData.get(0).get("VendorinNetworkDiscount"));
            offers.put("AgentIntervention", offerData.get(0).get("AgentIntervention"));
            offers.put("BypassACH", offerData.get(0).get("BypassACH"));
            offers.put("OfferEnabled", offerData.get(0).get("OfferEnabled"));

            offers = editExistingOffers(oBrowser, offers);
            Assert.assertTrue(offers.get("TestPassed").equalsIgnoreCase("True"), "The 'editExistingOffers() method was failed'");
            Assert.assertTrue(verifyOffersDetails(oBrowser, "Edit", offers));
            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'editAndValidateTheOffers()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'editAndValidateTheOffers()' method. " + e);
            return false;
        }finally {
            offerData = null; offers = null; timeStamp = null;
        }
    }



    /********************************************************
     * Method Name      : createNewScripts()
     * Purpose          : Create new Scripts from Modules->Daily Grind->Program Management->Scripts
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : Map<String, String>
     ********************************************************/
    public Map<String, String> createNewScripts(WebDriver oBrowser, Map<String, String> scriptsData) {
        try {
            Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "Program Management"));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ProgramManagement_Grid + "/table//tr[1]/td[1]"), "Clickable", "", waitTimeOut);
            waveTypeName = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ProgramManagement_Grid + "/table//tr[1]/td[1]"), "Text");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ProgramManagement_Grid + "/table//tr[1]/td[1]")));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ShowEditDetails_Header, "Visibility", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_GoToWave_PageTitle, "Text").contains(waveTypeName));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Scripts_Link));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Scripts_Add_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Scripts_Create_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Scripts_NewWaveScript_Dialog, "Text", "New Wave Script"));

            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ScriptName_Edit, scriptsData.get("ScriptName")));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ScriptText_Textarea, scriptsData.get("ScriptText")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ScriptType_List, scriptsData.get("ScriptType")));
            if (scriptsData.get("WaveOffer") != null)
                Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Scripts_WaveOffer_List, scriptsData.get("WaveOffer")));
            else {
                Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Scripts_WaveOffer_List, 1));
                scriptsData.put("WaveOffer", appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Scripts_WaveOffer_List, "Dropdown"));
            }
            reports.writeResult(oBrowser, "Screenshot", "The required data was entered for creating the new Script");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Scripts_Create_Btn));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ConfirmationMessage, "Visibility", "", waitTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ConfirmationMessage, "Text").trim().contains("Enrollment Script has been successfully created"));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ScriptName_Search_Edit, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ScriptName_Search_Edit, scriptsData.get("ScriptName")));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ScriptDetails_Grid + "//tr/td[text()='" + scriptsData.get("ScriptName") + "']"), "Text", scriptsData.get("ScriptName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ScriptDetails_Grid + "//tr/td[text()='" + scriptsData.get("ScriptName") + "']/following-sibling::td[1]"), "Text", scriptsData.get("ScriptText")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ScriptDetails_Grid + "//tr/td[text()='" + scriptsData.get("ScriptName") + "']/following-sibling::td[2]"), "Text", scriptsData.get("ScriptType")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ScriptDetails_Grid + "//tr/td[text()='" + scriptsData.get("ScriptName") + "']/following-sibling::td[3]"), "Text", scriptsData.get("WaveOffer")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ScriptDetails_Grid + "//tr/td[text()='" + scriptsData.get("ScriptName") + "']/following-sibling::td[4]/a[@title='Show']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ScriptDetails_Grid + "//tr/td[text()='" + scriptsData.get("ScriptName") + "']/following-sibling::td[4]/a[@title='Edit']")));
            //Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ScriptDetails_Grid + "//tr/td[text()='" + scriptsData.get("ScriptName") + "']/following-sibling::td[4]/a[@title='Delete']")));
            scriptsData.put("TestPassed", "True");
            return scriptsData;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createNewScripts()' method. " + e);
            scriptsData.put("TestPassed", "False");
            return scriptsData;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createNewScripts()' method. " + e);
            scriptsData.put("TestPassed", "False");
            return scriptsData;
        }
    }



    private void gotoProgramManagementFirstRecord() {
        Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "Program Management"));
        appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ProgramManagement_Grid + "/table//tr[1]/td[1]"), "Clickable", "", waitTimeOut);
        waveTypeName = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ProgramManagement_Grid + "/table//tr[1]/td[1]"), "Text");
        Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ProgramManagement_Grid + "/table//tr[1]/td[1]")));
        oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
        appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ShowEditDetails_Header, "Visibility", "", waitTimeOut);
    }



    /********************************************************
     * Method Name      : validateTheWaveTypeDetails()
     * Purpose          : to verify that the use can validate create/edit Wave Type details
     * Author           : Gudi
     * Parameters       : oBrowser, waveData
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateTheWaveTypeDetails(WebDriver oBrowser, String waveTypeStatus, Map<String, String> waveData) {
        try {

            if (waveTypeStatus.equalsIgnoreCase("New")) {
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WaveType_Show_Details_Grid, "Text").contains("Wave: " + waveData.get("Name") + " - Created"));
            } else {
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WaveType_Show_Details_Grid, "Text").contains("Wave: " + waveData.get("Name") + " - Updated"));
            }

            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_WaveType_Show_Details_Grid, "Text").contains("by " + appInd.getPropertyValueByKeyName("qaUserName")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ShowEditDetails_Header, "Text", "Information", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_WaveType_Show_Grid + "/dd)[1]"), "Text", waveData.get("Name")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_WaveType_Show_Grid + "/dd)[2]"), "Text").contains(waveData.get("StartDate")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_WaveType_Show_Grid + "/dd)[3]"), "Text").contains(waveData.get("EndDate")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_WaveType_Show_Grid + "/dd/a)[1]"), "Text", waveData.get("Company")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_WaveType_Show_Grid + "/dd)[5]"), "Text", waveData.get("WaveType")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_WaveType_Show_Grid + "/dd)[6]"), "Text", waveData.get("Status")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_WaveType_Show_Grid + "/dd)[7]"), "Text", waveData.get("Priority")));
            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateTheWaveTypeDetails()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateTheWaveTypeDetails()' method. " + e);
            return false;
        }
    }



    /********************************************************
     * Method Name      : createAndValidateScripts()
     * Purpose          : Create the Scripts from Modules->Daily Grind->Program Management->Scripts
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean createAndValidateScripts(WebDriver oBrowser, DataTable dataTable) {
        List<Map<String, String>> scriptData = null;
        Map<String, String> scripts = null;
        String timeStamp = null;
        try {
            timeStamp = appInd.getDateTime("Shhmmss");
            scriptData = dataTable.asMaps(String.class, String.class);
            scripts = new HashMap<String, String>();
            scripts.put("ScriptName", scriptData.get(0).get("ScriptName") + timeStamp);
            newScriptName = scripts.get("ScriptName");
            scripts.put("ScriptText", scriptData.get(0).get("ScriptText"));
            scripts.put("ScriptType", scriptData.get(0).get("ScriptType"));
            scripts.put("WaveOffer", scriptData.get(0).get("WaveOffer"));

            scripts = createNewScripts(oBrowser, scripts);
            Assert.assertTrue(scripts.get("TestPassed").equalsIgnoreCase("True"), "The createNewScripts() method failed");
            Assert.assertTrue(validateScriptDetails(oBrowser, "New", scripts));
            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createAndValidateScripts()' method. " + e);
            return true;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createAndValidateScripts()' method. " + e);
            return false;
        }finally {
            scriptData = null; scripts = null; timeStamp = null;
        }
    }


    /********************************************************
     * Method Name      : createAndValidateTheWaveType()
     * Purpose          : to verify that the use can create new Wave Type under modules in the Daily Grind section & validate the same
     * Author           : Gudi
     * Parameters       : oBrowser, dailyGrindType
     * ReturnType       : boolean
     ********************************************************/
    public boolean createAndValidateTheWaveType(WebDriver oBrowser, String waveTypeStatus, DataTable dataTable) {
        Map<String, String> waveData = null;
        List<Map<String, String>> waveDetails = null;
        String timeStamp = null;
        try {
            timeStamp = appInd.getDateTime("Shhmmss");
            waveDetails = dataTable.asMaps(String.class, String.class);
            waveData = new HashMap<String, String>();
            waveData.put("Name", waveDetails.get(0).get("Name") + "_" + timeStamp);
            waveTypeName = waveData.get("Name");
            waveData.put("Company", waveDetails.get(0).get("Company"));
            if (waveDetails.get(0).get("StartDate") == null) {
                waveData.put("StartDate", appInd.dateAddAndReturn("yyyy-MM-dd", 0));
            } else {
                waveData.put("StartDate", waveDetails.get(0).get("StartDate"));
            }

            if (waveDetails.get(0).get("EndDate") == null) {
                waveData.put("EndDate", appInd.dateAddAndReturn("yyyy-MM-dd", 1));
            } else {
                waveData.put("EndDate", waveDetails.get(0).get("EndDate"));
            }
            waveData.put("WaveType", waveDetails.get(0).get("WaveType"));
            waveData.put("Status", waveDetails.get(0).get("Status"));
            waveData.put("Priority", waveDetails.get(0).get("Priority"));

            waveData = createNewWaveType(oBrowser, waveData);
            Assert.assertTrue(waveData.get("TestPassed").equalsIgnoreCase("True"), "The createNewWaveType() method was failed");
            Assert.assertTrue(validateTheWaveTypeDetails(oBrowser, waveTypeStatus, waveData));
            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createAndValidateTheWaveType()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createAndValidateTheWaveType()' method. " + e);
            return false;
        }finally {
            waveData = null; waveDetails = null; timeStamp = null;
        }
    }



    /********************************************************
     * Method Name      : validateScriptDetails()
     * Purpose          : validate the Scripts details from Modules->Daily Grind->Program Management->Scripts->Show
     * Author           : Gudi
     * Parameters       : oBrowser, scriptStatus, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateScriptDetails(WebDriver oBrowser, String scriptStatus, Map<String, String> scriptsData) {
        String scriptName = null;
        try {
            if (scriptStatus.equalsIgnoreCase("New")) {
                scriptName = newScriptName;
            } else {
                scriptName = editScriptName;
            }
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ScriptName_Search_Edit, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ScriptName_Search_Edit, scriptName));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Scripts_ScriptDetails_Grid + "//tr/td[text()='" + scriptName + "']/following-sibling::td[4]/a[@title='Show']")));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Scripts_WaveScript_Dialog), "Text", "Wave Script", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Scripts_WaveScript_Dialog + "/parent::div/following-sibling::div/dl//dd[1]"), "Text", scriptsData.get("ScriptName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Scripts_WaveScript_Dialog + "/parent::div/following-sibling::div/dl//dd[2]"), "Text", scriptsData.get("ScriptText")));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Scripts_WaveScript_Dialog + "/following-sibling::button")));
            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateScriptDetails()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateScriptDetails()' method. " + e);
            return false;
        }finally {scriptName = null;}
    }



    /********************************************************
     * Method Name      : verifyOffersDetails()
     * Purpose          : validate offer details from show section for Modules->Daily Grind->Program Management->Offers
     * Author           : Gudi
     * Parameters       : oBrowser, offerStatus, offersData
     * ReturnType       : boolean
     ********************************************************/
    public boolean verifyOffersDetails(WebDriver oBrowser, String offerStatus, Map<String, String> offersData) {
        String offerTerm = null;
        try {
            if (offerStatus.equalsIgnoreCase("New")) {
                offerTerm = newOfferTerm;
            } else {
                offerTerm = editOfferTerm;
            }
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_OffersGrid + "//td[text()='" + offerTerm + "']/following-sibling::td/a[@title='Show']")));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_ShowWaveOfferDetails_Dialog, "Text", "Wave Offer Details", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_ShowWaveOfferDetails_Dialog, "Text", "Wave Offer Details"));

            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_ShowWaveDetails_Content + "/dl/dt[text()='Wave']/following-sibling::dd"), "Text", waveTypeName));
            String offerNum = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_ShowWaveDetails_Content + "/dl/dt[text()='Offer #']/following-sibling::dd"), "Text");
            Assert.assertTrue(offerNum.equals(offersData.get("OfferNumber")) || offerNum.equals(String.valueOf(Integer.parseInt(offersData.get("OfferNumber")) + 1)));

            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_ShowWaveDetails_Content + "/dl/dt[text()='Offer Type']/following-sibling::dd"), "Text", offersData.get("OfferType")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_ShowWaveDetails_Content + "/dl/dt[text()='Currency']/following-sibling::dd"), "Text", offersData.get("Currency")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_ShowWaveDetails_Content + "/dl/dt[text()='Agent Intervention']/following-sibling::dd"), "Text", offersData.get("AgentIntervention")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Offers_ShowWaveDetails_Content + "/dl/dt[text()='Bypass ACH']/following-sibling::dd"), "Text", offersData.get("BypassACH")));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Offers_ShowWaveOfferDetails_Close_Btn));
            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifyOffersDetails()' method. " + e);
            return true;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifyOffersDetails()' method. " + e);
            return false;
        }finally {offerTerm = null;}
    }



    /********************************************************
     * Method Name      : createAndValidateTheOffers()
     * Purpose          : Create new offers from Modules->Daily Grind->Program Management->Offers
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean createAndValidateTheOffers(WebDriver oBrowser, DataTable dataTable) {
        List<Map<String, String>> offerData = null;
        Map<String, String> offers = null;
        String timeStamp = null;
        try {
            timeStamp = appInd.getDateTime("Shhmmss");
            offerData = dataTable.asMaps(String.class, String.class);
            offers = new HashMap<String, String>();
            offers.put("OfferNumber", offerData.get(0).get("OfferNumber"));
            offers.put("OfferType", offerData.get(0).get("OfferType"));
            offers.put("CardPlatform", offerData.get(0).get("CardPlatform"));
            offers.put("CardNetwork", offerData.get(0).get("CardNetwork"));
            offers.put("CardCampainType", offerData.get(0).get("CardCampainType"));
            offers.put("VirtualCardDeliverMethod", offerData.get(0).get("VirtualCardDeliverMethod"));
            offers.put("Currency", offerData.get(0).get("Currency"));
            offers.put("AmountFrom", offerData.get(0).get("AmountFrom"));
            offers.put("AmountTo", offerData.get(0).get("AmountTo"));
            offers.put("ACHLimits", offerData.get(0).get("ACHLimits"));
            offers.put("Terms", offerData.get(0).get("Terms") + timeStamp);
            newOfferTerm = offers.get("Terms");
            offers.put("Percentage", offerData.get(0).get("Percentage"));
            offers.put("VendorinNetworkDiscount", offerData.get(0).get("VendorinNetworkDiscount"));
            offers.put("AgentIntervention", offerData.get(0).get("AgentIntervention"));
            offers.put("BypassACH", offerData.get(0).get("BypassACH"));
            offers.put("OfferEnabled", offerData.get(0).get("OfferEnabled"));

            offers = createNewOffers(oBrowser, offers);
            Assert.assertTrue(offers.get("TestPassed").equalsIgnoreCase("True"), "The 'createNewOffers() method was failed'");
            Assert.assertTrue(verifyOffersDetails(oBrowser, "New", offers));
            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createAndValidateTheOffers()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createAndValidateTheOffers()' method. " + e);
            return false;
        }finally {
            offerData = null; offers = null; timeStamp = null;
        }
    }



    /********************************************************
     * Method Name      : clickOnSuppliersTabAndValidateColumnGridNames()
     * Purpose          : user click on Suppliers tab for Modules->Daily Grind->Program Management
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean clickOnSuppliersTabAndValidateColumnGridNames(WebDriver oBrowser) {
        String columnNames = null;
        String arrColNames[];
        try {
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_TotalSuppliers_Label, "Visibility", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "'Suppliers' tab was opened from the 'Program Management->show' section");
            columnNames = "Supplier,Vendor ID,Tax ID,PayNet Company ID,Supplier Type,Status,Link Priority,Most Recent Activity,Last Offered/Accepted Offer,# Checks,Total Spend";
            arrColNames = columnNames.split(",");
            for(int i=0; i< arrColNames.length; i++){
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Columns_Label + "//table//tbody/tr[contains(@class, 'dx-header-row')]/td["+(i+1)+"]/div[contains(@class, 'text')]"), "Text", arrColNames[i]));
            }
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_CreateNew_Link));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_AddExisting_Link));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Assign_Link));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Export_Link));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_ColumnChooser_Link));
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'clickOnSuppliersTabAndValidateColumnGridNames()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'clickOnSuppliersTabAndValidateColumnGridNames()' method. " + e);
            return false;
        }finally {
            columnNames = null; arrColNames = null;
        }
    }



    /********************************************************
     * Method Name      : navigateAndValidateInformationTabOfWaveAndOffer()
     * Purpose          : user perform Link Update of cases for Modules->Daily Grind->Program Management
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean navigateAndValidateInformationTabOfWaveAndOffer(WebDriver oBrowser) {
        String name = null;
        String startDate = null;
        String endDate = null;
        String company = null;
        String waveType = null;
        String status = null;
        String priority = null;
        String arrStartDt[];
        String arrEndDt[];
        String strInfoColumns[];
        int index = 0;
        try {
            Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "Program Management"));
            int rowCount = oBrowser.findElements(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ProgramManagement_Grid + "/table//tr")).size();
            for(int i=0; i<rowCount; i++){
                status = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ProgramManagement_Grid + "/table//tr["+(i+1)+"]/td[6]"), "Text");
                if(!status.equalsIgnoreCase("Closed")){
                    index = (i+1);
                    break;
                }
            }

            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ProgramManagement_Grid + "/table//tr["+index+"]/td[1]"), "Clickable", "", minTimeOut);
            name = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ProgramManagement_Grid + "/table//tr["+index+"]/td[1]"), "Text");
            startDate = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ProgramManagement_Grid + "/table//tr["+index+"]/td[2]"), "Text");
            arrStartDt = startDate.split("/");
            if (Integer.parseInt(arrStartDt[0]) < 10) {
                arrStartDt[0] = "0" + arrStartDt[0];
            }
            if (Integer.parseInt(arrStartDt[1]) < 10) {
                arrStartDt[1] = "0" + arrStartDt[1];
            }
            startDate = arrStartDt[2] + "-" + arrStartDt[0] + "-" + arrStartDt[1];
            endDate = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ProgramManagement_Grid + "/table//tr["+index+"]/td[3]"), "Text");
            arrEndDt = endDate.split("/");
            if (Integer.parseInt(arrEndDt[0]) < 10) {
                arrEndDt[0] = "0" + arrEndDt[0];
            }
            if (Integer.parseInt(arrEndDt[1]) < 10) {
                arrEndDt[1] = "0" + arrEndDt[1];
            }
            endDate = arrEndDt[2] + "-" + arrEndDt[0] + "-" + arrEndDt[1];
            company = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ProgramManagement_Grid + "/table//tr["+index+"]/td[4]"), "Text");
            waveType = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ProgramManagement_Grid + "/table//tr["+index+"]/td[5]"), "Text");
            status = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ProgramManagement_Grid + "/table//tr["+index+"]/td[6]"), "Text");
            priority = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ProgramManagement_Grid + "/table//tr["+index+"]/td[7]"), "Text");

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ProgramManagement_Grid + "/table//tr["+index+"]/td[1]")));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Information_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ShowEditDetails_Header, "Visibility", "", waitTimeOut);
            appDep.waitForTheElementState(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_WaveAndOffers_Information + "/parent::div/div//dl/dd[4]/a)[1]"), "Text", company, minTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Information section of 'Program management' was opened successful");

            strInfoColumns = "Name#Start Date#End Date#Company#Wave Type#Status#Priority".split("#");
            for (int i = 0; i < strInfoColumns.length; i++) {
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_WaveAndOffers_Information + "/parent::div/div//dl/dt[" + (i + 1) + "]"), "Text", strInfoColumns[i]));
            }

            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_WaveAndOffers_Information + "/parent::div/div//dl/dd[1]"), "Text", name));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_WaveAndOffers_Information + "/parent::div/div//dl/dd[4]/a)[1]"), "Text", company));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_WaveAndOffers_Information + "/parent::div/div//dl/dd[5]"), "Text", waveType));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_WaveAndOffers_Information + "/parent::div/div//dl/dd[6]"), "Text", status));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_WaveAndOffers_Information + "/parent::div/div//dl/dd[7]"), "Text", priority));
            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'navigateAndValidateInformationTabOfWaveAndOffer()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'navigateAndValidateInformationTabOfWaveAndOffer()' method. " + e);
            return false;
        }finally {
            name = null; startDate = null; endDate = null; company = null; waveType = null; status = null; priority = null; arrStartDt = null; arrEndDt = null; strInfoColumns = null;
        }
    }




    /********************************************************
     * Method Name      : validateTIN_Manual_Auto_ValidateFunctionality()
     * Purpose          : user validates Manual/Auto Validation page UI functionality from Modules->Daily Grind->StopFraud-->Validation LookUp
     * Author           : Gudi
     * Parameters       : oBrowser, tinValidationType, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateTIN_Manual_Auto_ValidateFunctionality(WebDriver oBrowser, String tinValidationType, DataTable dataTable){
        Map<String, String> supplierData = null;
        List<Map<String, String>> manualValidation = null;
        try{
            manualValidation = dataTable.asMaps(String.class, String.class);

            //Filter Action Type to TIN Matching & Select a case which has Supplier Link Status as - Approved
            //supplierData = appDep.selectTheStopFraudCaseWithApprovedStatus(oBrowser, "TIN Matching", "Approved");
            String validationErrMsg = tinValidationType.equalsIgnoreCase("Auto validate")?"Error":"Validation Attachment is needed to upload";
            supplierData = appDep.getTheTINMatchingDetailsByQuery(oBrowser, "StopFraudTINValidation", "TIN Matching");
            Assert.assertTrue(supplierData.get("TestPassed").equalsIgnoreCase("True"), "The 'getTheTINMatchingDetailsByQuery()' method was failed");

            appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Company_Tab);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Company_OuterGrid + "//td[text()='TIN Matching']"), "text", "TIN Matching", minTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "'Validation Lookup' page was opened successful");
            appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Company_OuterGrid + "//tr[contains(@class, 'lines dx-column-lines')]/td[2]"), "Text", supplierData.get("supplierName"));
            appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Company_OuterGrid + "//tr[contains(@class, 'lines dx-column-lines')]/td[3]"), "Text", supplierData.get("payNetSupplierID"));
            appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Company_OuterGrid + "//tr[contains(@class, 'lines dx-column-lines')]/td[4]"), "Text", supplierData.get("TIN"));

            appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Company_OuterGrid + "//td[text()='TIN Matching']"), "text", "TIN Matching");
            appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Company_OuterGrid + "//td[text()='TIN Matching']/following-sibling::td[5]/span"), "Text", "Pending");

            appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Company_OuterGrid + "//td[text()='TIN Matching']/following-sibling::td[6]/button[text()='Validate']"));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ManualValidate_Btn, "Clickable", "", waitTimeOut);
            appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_TINValidationModal_Grid + "//h4"), "Text", "TIN Validation");
            reports.writeResult(oBrowser, "Screenshot", "'TIN Validation' modal was opened successful");

            //Validate Company Information from TIN Validation Modal
            appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_TINValidationModal_Grid + "//td[1]/span"), "Text", supplierData.get("supplierName"));
            appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_TINValidationModal_Grid + "//td[2]/span"), "Text", supplierData.get("payNetSupplierID"));
            appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_TINValidationModal_Grid + "//td[3]/span"), "Text", supplierData.get("TIN"));

            //Validate the labels
            appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_TINValidationModal_Grid + "//label)[1]"), "Text", "Validation attachments");
            appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_TINValidationModal_Grid + "//label)[2]"), "Text", "Company Information");
            appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_TINValidationModal_Grid + "//label)[3]"), "Text", "Notes");

            appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_TINValidationModal_Grid + "//input[@id='tin_validation_modal_attachment_btn']"));
            appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_TINValidationModal_Grid + "//div[@id='tin_validation_modal_sections']"));
            appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ManualValidate_Btn);
            appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ManualValidate_Btn, "Text", "Manual Validate");
            appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_AutoValidate_Btn);
            appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_AutoValidate_Btn, "Text", "Auto-Validate");

            //Click on Manual-Validate OR Auto-Validate Button,A Validation error should be displayed saying 'At least one file attachment is required'
            if(tinValidationType.equalsIgnoreCase("Manual validate"))
                appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ManualValidate_Btn);
            else if(tinValidationType.equalsIgnoreCase("Auto validate"))
                appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_AutoValidate_Btn);
            else {
                reports.writeResult(oBrowser, "Fail", "Invalid button name '"+tinValidationType+"' for TIN Validation");
                Assert.fail("Invalid button name '"+tinValidationType+"' for TIN Validation");
            }
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_TINValidationResultModal_msg + "//p"), "Text", validationErrMsg, minTimeOut);
            appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_TINValidationResultModal_msg + "//p"), "Text", validationErrMsg);

            //Details
            appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_TINValidationModal_Grid + "//button[text()='Details']"));
            appDep.waitForTheElementState(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_TINValidationModal_Grid + "//table)[2]//td[1]"), "Text", supplierData.get("supplierName"), minTimeOut);
            appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_TINValidationModal_Grid + "//table)[2]//td[1]"), "Text", supplierData.get("supplierName"));
            appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_TINValidationModal_Grid + "//table)[2]//td[2]"), "Text", supplierData.get("payNetSupplierID"));
            appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_TINValidationModal_Grid + "//table)[2]//td[3]"), "Text", supplierData.get("TIN"));


            //Add validation attachment
            appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ChooseFile_Edit, System.getProperty("user.dir").replace("\\", "/") + "/TestData/" + manualValidation.get(0).get("FileToUpload"));
            appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_TinValidate_Notes_Textarea, manualValidation.get(0).get("TINValidationNote"));
            appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_CompanyInformation_ChkBox));
            if(tinValidationType.equalsIgnoreCase("Manual validate"))
                appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ManualValidate_Btn);
            else if(tinValidationType.equalsIgnoreCase("Auto validate"))
                appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_AutoValidate_Btn);
            else {
                reports.writeResult(oBrowser, "Fail", "Invalid button name '"+tinValidationType+"' for TIN Validation");
                Assert.fail("Invalid button name '"+tinValidationType+"' for TIN Validation");
            }
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_TINValidationResultModal_msg + "//p"), "Text", "At least one section must be selected before validating", minTimeOut);
            appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_TINValidationResultModal_msg + "//p"), "Text", "At least one section must be selected before validating");
            appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_TINValidationModal_Grid + "//p[@class='help-block']"), "Text", "Select the sections that you have validated");

            //Provide all the details and click on Manual Validate button. The TIN Validation should be successful
            appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_CompanyInformation_ChkBox));
            if(tinValidationType.equalsIgnoreCase("Manual validate"))
                appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ManualValidate_Btn);
            else if(tinValidationType.equalsIgnoreCase("Auto validate"))
                appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_AutoValidate_Btn);
            else {
                reports.writeResult(oBrowser, "Fail", "Invalid button name '"+tinValidationType+"' for TIN Validation");
                Assert.fail("Invalid button name '"+tinValidationType+"' for TIN Validation");
            }
            validationErrMsg = tinValidationType.equalsIgnoreCase("Auto validate")?"Error":"Success!";
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_TINValidationResultModal_msg + "//p"), "Text", validationErrMsg, waitTimeOut);
            appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_TINValidationResultModal_msg + "//p"), "Text", validationErrMsg);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_TINValidationModal_Grid + "//button[@class='close']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            validationErrMsg = tinValidationType.equalsIgnoreCase("Auto validate")?"Pending":"Complete";
            appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Company_OuterGrid + "//td[text()='TIN Matching']/following-sibling::td[5]/span"), "Text", validationErrMsg);
            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateTIN_Manual_Auto_ValidateFunctionality()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateTIN_Manual_Auto_ValidateFunctionality()' method. " + e);
            return false;
        }finally {supplierData = null; manualValidation = null;}
    }




    //================================ SUPPORT CASE SCENARIOS =============================
    /********************************************************
     * Method Name      : verifySupportCasePermission()
     * Purpose          : to verify that the Support case link visible based on permission
     * Author           : Deepak
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean verifySupportCasePermission(WebDriver oBrowser, DataTable dataTable) {
        Map<String, String> mapping = new HashMap<>();
        try{
            mapping = payCRM_rpa_moduleUIBaseStep.setPermissionAndOpenNewTab(oBrowser, dataTable);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Modules_Menu, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Modules_Menu), "clickObject() was failed for '"+PayCRM_Modules_GeneralUIPage.obj_Modules_Menu+"' webelement");
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_Modules_General_Companies_Link, "Clickable", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "Support Cases are not visible");
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_GeneralUIPage.obj_DailyGrind_SupportCases_Link));
            appDep.selectManageModules(oBrowser, "Reports");
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Reports page");
            appInd.scrollToThePage(oBrowser, "Down");
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_ManageUIPage.obj_SupportCase_Link));
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifySupportCasePermission()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifySupportCasePermission()' method. " + e);
            reports.writeResult(oBrowser, "screenshot", "AssertionError in 'verifySupportCasePermission()' method. " + e);
            return false;
        }
        finally {
            payCRM_rpa_moduleUIBaseStep.resetPermission(oBrowser, mapping);
        }
        return true;
    }




    /********************************************************
     * Method Name      : verifySupportCaseGrid()
     * Purpose          : to verify that the Support case grid columns and its tabs
     * Author           : Deepak
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean verifySupportCaseGrid(WebDriver oBrowser){
        String[] tabs = {"Active", "Pending"};
        try{
            Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "Support Cases"));
            for(String tab : tabs){
                appDep.waitForTheElementState(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_row,tab, "1")),"Visibility","",waitTimeOut);
                reports.writeResult(oBrowser, "screenshot", "Support case grid load successfully");
                //Active Tab headers
                WebElement headingRow = oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Header,tab)));
                List<WebElement> activeHeadings = headingRow.findElements(By.tagName("td"));
                Assert.assertTrue(activeHeadings.get(0).getText().trim().equalsIgnoreCase("Case #"));
                Assert.assertTrue(activeHeadings.get(1).getText().trim().equalsIgnoreCase("PayNet Supplier ID"));
                Assert.assertTrue(activeHeadings.get(2).getText().trim().equalsIgnoreCase("Supplier Name"));
                Assert.assertTrue(activeHeadings.get(3).getText().trim().equalsIgnoreCase("Case Category"));
                Assert.assertTrue(activeHeadings.get(4).getText().trim().equalsIgnoreCase("Area Name"));
                Assert.assertTrue(activeHeadings.get(5).getText().trim().equalsIgnoreCase("Case Type"));
                Assert.assertTrue(activeHeadings.get(6).getText().trim().equalsIgnoreCase("Action Date"));
                Assert.assertTrue(activeHeadings.get(7).getText().trim().equalsIgnoreCase("Status"));
                Assert.assertTrue(activeHeadings.get(8).getText().trim().equalsIgnoreCase("Country Code"));
                Assert.assertTrue(activeHeadings.get(9).getText().trim().equalsIgnoreCase("Priority"));
                Assert.assertTrue(activeHeadings.get(10).getText().trim().equalsIgnoreCase("Assigned To"));
                Assert.assertTrue(activeHeadings.get(11).getText().trim().equalsIgnoreCase("Origination Source"));
                Assert.assertTrue(oBrowser.findElement(By.id(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_Export_Btn,tab))).isDisplayed());
                if(tab.equalsIgnoreCase("Active"))
                    appInd.verifyElementPresent(oBrowser,By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_Generic_Title,"New Case")));
                appInd.clickObject(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_Generic_Text, "Pending")));
            }
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifySupportCaseGrid()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifySupportCaseGrid()' method. " + e);
            reports.writeResult(oBrowser, "screenshot", "AssertionError in 'verifySupportCaseGrid()' method. " + e);
            return false;
        }
        return true;
    }

    /********************************************************
     * Method Name      : verifyAdditionalFieldsForAutomaticCases()
     * Purpose          : to verify Additional Information tile from automatic case
     * Author           : Deepak
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean verifyAdditionalFieldsForAutomaticCases(WebDriver oBrowser){
        try{
            Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "Support Cases"));
            appDep.threadSleep(1000);
            Assert.assertTrue(verifySupportCaseGridRecordVisible("Active","Area Name", "Company Information", true));
            boolean isVisible = (appInd.verifyElementPresent(oBrowser, By.xpath("(("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//table)[2]//tr[1])[1]/td[contains(@aria-label,'Country Code') and (text()='US' or text()='CA')]")));
            Assert.assertTrue(isCountryVisible(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_ActiveTab_ClickFirst_Record, isVisible));
            appInd.clearAndSetObject(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_ActiveTabColumnSearch, "Area Name")), "");
            appDep.threadSleep(1000);
            Assert.assertTrue(verifySupportCaseGridRecordVisible("Active","Origination Source", "Automatic Support Case", true));
            isVisible = (appInd.verifyElementPresent(oBrowser, By.xpath("(("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//table)[2]//tr[1])[1]/td[contains(@aria-label,'Country Code') and (text()='US' or text()='CA')]")));
            Assert.assertTrue(isCountryVisible(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_ActiveTab_ClickFirst_Record, isVisible));
            appDep.threadSleep(1000);
            reports.writeResult(oBrowser, "screenshot", "Support Case : Columns are filter as per required data");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_ActiveTab_ClickFirst_Record_Data));
            appDep.threadSleep(1000);
            appInd.switchToLastWindow(oBrowser);
            reports.writeResult(oBrowser, "screenshot", "Support Case : We click on 1st record from grid");
            //----------------------------------- verify Additional information tile [Company Information] -----------------------------------
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("//div[@id='section_contact_information']//h3"),"text").contains("Additional Information"));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AutoCase_AdditionalInfo, "Company name"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AutoCase_AdditionalInfo, "Address1"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AutoCase_AdditionalInfo, "City"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AutoCase_AdditionalInfo, "Zip postal"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AutoCase_AdditionalInfo, "Phone"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AutoCase_AdditionalInfo, "Tax ID"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AutoCase_AdditionalInfo, "Country code"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AutoCase_AdditionalInfo, "State code"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//div[@id='additional_details_support_cases']//div[starts-with(@class,'col-md-8')][text()='US' or text()='CA']")));
            reports.writeResult(oBrowser, "screenshot", "Company Information - Support Case : verification of Additional Information is complete");
            oBrowser.close();
            appInd.switchToLastWindow(oBrowser);
            Assert.assertTrue(verifySupportCaseGridRecordVisible("Active","Area Name", "Contacts", true));
            Assert.assertTrue(isCountryVisible(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_ActiveTab_ClickFirst_Record, appInd.verifyElementPresent(oBrowser, By.xpath("(("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//table)[2]//tr[1])[1]/td[contains(@aria-label,'Country Code') and (text()='US' or text()='CA')]"))));
            appDep.threadSleep(1000);
            reports.writeResult(oBrowser, "screenshot", "Contact - Support Case : filter column as per requirement");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_ActiveTab_ClickFirst_Record_Data));
            appDep.threadSleep(1000);
            appInd.switchToLastWindow(oBrowser);
            //----------------------------------- verify Additional information tile [Contacts] -----------------------------------
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("//div[@id='section_contact_information']//h3"),"text").contains("Additional Information"));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AutoCase_AdditionalInfo, "Company name"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AutoCase_AdditionalInfo, "Address1"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AutoCase_AdditionalInfo, "City"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AutoCase_AdditionalInfo, "Zip postal"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AutoCase_AdditionalInfo, "Phone"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AutoCase_AdditionalInfo, "Tax ID"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AutoCase_AdditionalInfo, "Country code"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AutoCase_AdditionalInfo, "State code"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AutoCase_AdditionalInfo, "First name"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AutoCase_AdditionalInfo, "Last name"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AutoCase_AdditionalInfo, "Job title"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AutoCase_AdditionalInfo, "Contact type desc"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//div[@id='additional_details_support_cases']//div[starts-with(@class,'col-md-8')][text()='US' or text()='CA']")));
            reports.writeResult(oBrowser, "screenshot", "Contacts - Support Case : verification of Additional Information is complete");
            oBrowser.close();
            appInd.switchToLastWindow(oBrowser);
            //----------------------------------- verify Additional information tile [Payment Information] -----------------------------------
            Assert.assertTrue(verifySupportCaseGridRecordVisible("Active","Area Name", "Payment Information", true));
            isVisible = (appInd.verifyElementPresent(oBrowser, By.xpath("(("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//table)[2]//tr[1])[1]/td[contains(@aria-label,'Country Code') and (text()='US' or text()='CA')]")));
            Assert.assertTrue(isCountryVisible(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_ActiveTab_ClickFirst_Record, isVisible));
            appInd.clearAndSetObject(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_ActiveTabColumnSearch, "Area Name")), "");
            appDep.threadSleep(1000);
            Assert.assertTrue(verifySupportCaseGridRecordVisible("Active","Case Type", "PAF", true));
            isVisible = (appInd.verifyElementPresent(oBrowser, By.xpath("(("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//table)[2]//tr[1])[1]/td[contains(@aria-label,'Country Code') and (text()='US' or text()='CA')]")));
            Assert.assertTrue(isCountryVisible(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_ActiveTab_ClickFirst_Record, isVisible));
            appInd.clearAndSetObject(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_ActiveTabColumnSearch, "Case Type")), "");
            appDep.threadSleep(1000);
            Assert.assertFalse(verifySupportCaseGridRecordVisible("Pending","Origination Source", "Manual Support Case", false));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath("(("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Pending_Grid + "//table)[2]//tr[1])[1]/td[contains(@aria-label,'Country Code') and (text()='US' or text()='CA')]")));
            reports.writeResult(oBrowser, "screenshot", "Payment Information - Support Case : Filter columns as per requirement");
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifySupportCaseGrid()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifySupportCaseGrid()' method. " + e);
            reports.writeResult(oBrowser, "screenshot", "AssertionError in 'verifySupportCaseGrid()' method. " + e);
            return false;
        }
        return true;
    }

    private boolean isCountryVisible(WebDriver oBrowser, By tab, boolean isVisible){
        if(!isVisible){
            appInd.clickObject(oBrowser, tab);
            appDep.threadSleep(1000);
            appInd.switchToLastWindow(oBrowser);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//div[@id='additional_details_support_cases']//div[starts-with(@class,'col-md-8')][text()='US' or text()='CA']")));
            reports.writeResult(oBrowser, "screenshot", "Payment Information - Support Case : verification of Additional Information is complete");
            oBrowser.close();
            appInd.switchToLastWindow(oBrowser);
            appDep.threadSleep(2000);
            appInd.clickObject(oBrowser, tab);
            appDep.threadSleep(1000);
        }
        return true;
    }

    /********************************************************
     * Method Name      : getPayNetInfo()
     * Purpose          : helper method for get PayNet Object infomration
     * Author           : Deepak
     * Parameters       : oBrowser
     * ReturnType       : Lists of PayNetObjects
     ********************************************************/
    public List<LinkedHashMap<String, Object>> getPayNetInfo(){
        return getPayNetInfo(1);
    }




    public List<LinkedHashMap<String, Object>> getPayNetInfo(int expectedRecords) {
        List<LinkedHashMap<String, Object>> payNetObjectList = new ArrayList<>();
        JSONArray caseDetails;
        caseDetails = apiDataProvider.getAPIDataProviderResponse("StopFraudTINValidation", new Object[] {});
        //int index = Integer.parseInt(appInd.generateRandomNumbers(1,((JSONArray) caseDetails.get(0)).size()-1,1));
        payNetObjectList.add((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(0));
        if(expectedRecords>1)
            payNetObjectList.add((LinkedHashMap) ((JSONArray) caseDetails.get(0)).get(1));
        return payNetObjectList;
    }



    /********************************************************
     * Method Name      : verifySupportCaseTypeValidations()
     * Purpose          : to validate fields of different support cases
     * Author           : Deepak
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean verifySupportCaseTypeValidations(WebDriver oBrowser, DataTable caseType) {
        List<LinkedHashMap<String, Object>> paynetInfoMap;
        paynetInfoMap = getPayNetInfo(2);
        try{
            Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "Support Cases"));
            appDep.threadSleep(1000);
            List<String> casesList = caseType.asList();
            casesList.forEach(caseName -> {
                appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Tab);
                appDep.threadSleep(3000);
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_AddIcon, "clickable","", minTimeOut);
                appInd.clickObject(oBrowser,PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_AddIcon);
                appDep.threadSleep(1000);
                appInd.switchToLastWindow(oBrowser);
                reports.writeResult(oBrowser, "screenshot", "Support case creation page visible");
                Assert.assertTrue(appInd.getAttribute(oBrowser, By.id("sc_case_category_id"), "class").endsWith("disable-submit"));
                Assert.assertTrue(validateSupportManualCaseFields(oBrowser, caseName, paynetInfoMap));
                Assert.assertTrue(validateCreatedSupportManualCase(oBrowser, caseName, paynetInfoMap));
            });
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'verifySupportCaseTypeValidations()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifySupportCaseTypeValidations()' method. " + e);
            reports.writeResult(oBrowser, "screenshot", "AssertionError in 'verifySupportCaseTypeValidations()' method. " + e);
            return false;
        }
        return true;
    }


    /********************************************************
     * Method Name      : validateCreatedSupportManualCase()
     * Purpose          : to validate manual support cases creation & verification
     * Author           : Deepak
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateCreatedSupportManualCase(WebDriver oBrowser, String caseType, List<LinkedHashMap<String, Object>> paynetInfoMap){
        switch (caseType){
            case "ACH Return/Reject/NOC":
                return createAndVerifyACH_Return_Reject_NOC(oBrowser, caseType, paynetInfoMap.get(0));
            case "New User Request":
                return createAndVerifyNewUserRequest(oBrowser, caseType, paynetInfoMap.get(0));
            case "Pre-note Failure":
                return createAndVerifyPreNotFailure(oBrowser, caseType, paynetInfoMap.get(0));
            case "Verbal Validation":
                return createAndVerifyVerbalValidation(oBrowser, caseType, paynetInfoMap.get(0));
            case "Merger/Acquisition":
                return createAndVerifyMergerAcquisition(oBrowser, caseType, paynetInfoMap);
            case "Website Validation":
                return createAndVerifyWebsiteValidation(oBrowser, caseType, paynetInfoMap.get(0));
            default:
                System.out.println("Invalid Manual Case type");
                return false;
        }
    }

    private boolean createAndVerifyACH_Return_Reject_NOC(WebDriver oBrowser, String caseType, LinkedHashMap<String, Object> paynetInfoMap){
        try{
            appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_CaseType_DD, caseType);
            appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_supportCase_NewManualCase_PayNetTextBoxID,paynetInfoMap.get("CompanyID").toString());
            appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_supportCase_NewManualCase_PayNetSearch_Btn);
            appDep.threadSleep(1000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_PayNetLoading,"invisibility","", minTimeOut);
            reports.writeResult(oBrowser, "screenshot", "Blank paynetID validation verification");
            appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_CreateBtn);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_LogActivity_Btn), "visibility", "", minTimeOut);
            //----------------------------------- verify manual case is created ---------------------------------------
            verifyCreatedCaseDetails(oBrowser, caseType, paynetInfoMap);
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createAndVerifyACH_Return_Reject_NOC()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createAndVerifyACH_Return_Reject_NOC()' method. " + e);
            reports.writeResult(oBrowser, "screenshot", "AssertionError in 'createAndVerifyACH_Return_Reject_NOC()' method. " + e);
            return false;
        }
        return true;
    }

    private boolean createAndVerifyNewUserRequest(WebDriver oBrowser, String caseType, LinkedHashMap<String, Object> paynetInfoMap){
        String name = "Name-"+appInd.getRandomString(10);
        String email = "automation@cpay.com";
        String jobTitle = "JobTitle-"+appInd.getRandomString(5);
        String superName = "SuperName-"+appInd.getRandomString(10);
        String superEmail = "super.automation@cpay.com";
        String superJobTitle = "SuperJobTitle-"+appInd.getRandomString(5);
        try{
            appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_CaseType_DD, caseType);
            appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_supportCase_NewManualCase_PayNetTextBoxID,paynetInfoMap.get("CompanyID").toString());
            appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_supportCase_NewManualCase_PayNetSearch_Btn);
            appDep.threadSleep(1000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_PayNetLoading,"invisibility","", minTimeOut);
            reports.writeResult(oBrowser, "screenshot", "New User Request paymentID case load successfully");
            appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_NewUserRequest_Name, name);
            appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_NewUserRequest_JobTitle, jobTitle);
            appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_NewUserRequest_Email, email);
            appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_NewUserRequest_SupervisorName, superName);
            appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_NewUserRequest_SuperVisorEmail, superEmail);
            appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_NewUserRequest_SuperVisorJobTitle, superJobTitle);
            appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_CreateBtn);
            appDep.threadSleep(1000);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_LogActivity_Btn), "visibility", "", minTimeOut);
            //----------------------------------- verify Additional information tile -----------------------------------
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("//div[@id='section_contact_information']//h3"),"text").contains("Additional Information"));
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AdditionalInfoTile, "1", "New user name", "1")), "text"),name);
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AdditionalInfoTile, "2", "New user email", "2")), "text"),email);
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AdditionalInfoTile, "3", "New user job title", "3")), "text"),jobTitle);
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AdditionalInfoTile, "4", "Officer/supervisor to verify name", "4")), "text"),superName);
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AdditionalInfoTile, "5", "Officer/supervisor to verify email", "5")), "text"),superEmail);
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AdditionalInfoTile, "6", "Officer/supervisor to verify job title", "6")), "text"),superJobTitle);
            reports.writeResult(oBrowser, "screenshot", "Case is successfully created and verify case detail page");
            //------------------------------- verify Case & Supplier Information Tile ----------------------------------
            verifyCreatedCaseDetails(oBrowser, caseType, paynetInfoMap);
         }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createAndVerifyNewUserRequest()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createAndVerifyNewUserRequest()' method. " + e);
            reports.writeResult(oBrowser, "screenshot", "AssertionError in 'createAndVerifyNewUserRequest()' method. " + e);
            return false;
        }
        return true;
    }

    private boolean createAndVerifyPreNotFailure(WebDriver oBrowser, String caseType, LinkedHashMap<String, Object> paynetInfoMap){
        String contact = "PreNote-Contact-"+appInd.getRandomString(10);
        try{
            appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_CaseType_DD, caseType);
            appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_supportCase_NewManualCase_PayNetTextBoxID,paynetInfoMap.get("CompanyID").toString());
            appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_supportCase_NewManualCase_PayNetSearch_Btn);
            appDep.threadSleep(1000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_PayNetLoading,"invisibility","", minTimeOut);
            reports.writeResult(oBrowser, "screenshot", "Field verification for Pre-note Failure");
            appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_PreNoteFailure_Contact, contact);
            appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_CreateBtn);
            appDep.threadSleep(1000);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_LogActivity_Btn), "visibility", "", minTimeOut);
            reports.writeResult(oBrowser, "screenshot", "Case is successfully created and verify case detail page");
            //----------------------------------- verify Additional information tile -----------------------------------
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("//div[@id='section_contact_information']//h3"),"text").contains("Additional Information"));
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AdditionalInfoTile, "1", "Primary/enrollment contact", "1")), "text"),contact);
            verifyCreatedCaseDetails(oBrowser, caseType, paynetInfoMap);
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createAndVerifyPreNotFailure()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createAndVerifyPreNotFailure()' method. " + e);
            reports.writeResult(oBrowser, "screenshot", "AssertionError in 'createAndVerifyPreNotFailure()' method. " + e);
            return false;
        }
        return true;
    }

    private boolean createAndVerifyVerbalValidation(WebDriver oBrowser, String caseType, LinkedHashMap<String, Object> paynetInfoMap){
        String contact = "VerbalContact-"+appInd.getRandomString(10);
        try{
            appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_CaseType_DD, caseType);
            appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_supportCase_NewManualCase_PayNetTextBoxID,paynetInfoMap.get("CompanyID").toString());
            appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_supportCase_NewManualCase_PayNetSearch_Btn);
            appDep.threadSleep(1000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_PayNetLoading,"invisibility","", minTimeOut);
            reports.writeResult(oBrowser, "screenshot", "Field verification for Verbal Validation");
            appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_verbalValidation_Contact, contact);
            appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_CreateBtn);
            appDep.threadSleep(1000);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_LogActivity_Btn), "visibility", "", minTimeOut);
            reports.writeResult(oBrowser, "screenshot", "Case is successfully created and verify case detail page");
            //----------------------------------- verify Additional information tile -----------------------------------
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("//div[@id='section_contact_information']//h3"),"text").contains("Additional Information"));
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AdditionalInfoTile, "1", "Primary/enrollment contact", "1")), "text"),contact);
            verifyCreatedCaseDetails(oBrowser, caseType, paynetInfoMap);
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createAndVerifyVerbalValidation()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createAndVerifyVerbalValidation()' method. " + e);
            reports.writeResult(oBrowser, "screenshot", "AssertionError in 'createAndVerifyVerbalValidation()' method. " + e);
            return false;
        }
        return true;
    }

    private boolean createAndVerifyWebsiteValidation(WebDriver oBrowser, String caseType, LinkedHashMap<String, Object> paynetInfoMap){
        try{
            appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_CaseType_DD, caseType);
            appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_supportCase_NewManualCase_PayNetTextBoxID,paynetInfoMap.get("CompanyID").toString());
            appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_supportCase_NewManualCase_PayNetSearch_Btn);
            appDep.threadSleep(1000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_PayNetLoading,"invisibility","", minTimeOut);
            reports.writeResult(oBrowser, "screenshot", "Field verification for Website Validation");
            appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_CreateBtn);
            appDep.threadSleep(1000);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_LogActivity_Btn), "visibility", "", minTimeOut);
            verifyCreatedCaseDetails(oBrowser, caseType, paynetInfoMap);
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createAndVerifyWebsiteValidation()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createAndVerifyWebsiteValidation()' method. " + e);
            reports.writeResult(oBrowser, "screenshot", "AssertionError in 'createAndVerifyWebsiteValidation()' method. " + e);
            return false;
        }
        return true;
    }

    private boolean createAndVerifyMergerAcquisition(WebDriver oBrowser, String caseType, List<LinkedHashMap<String, Object>> paynetInfoMap){
        String supplier1Address = appInd.getRandomString(15);
        String supplier1City = appInd.getRandomString(7);
        String supplier1Pin = String.valueOf(appInd.getRandomNumber(5));
        String supplier2Address = appInd.getRandomString(15);
        String supplier2City = appInd.getRandomString(7);
        String supplier2Pin = String.valueOf(appInd.getRandomNumber(5));
        try{
            //------------------------------ Merger/Acquisition --------------------------------
            appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_CaseType_DD, caseType);
            appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_supportCase_NewManualCase_PayNetTextBoxID,paynetInfoMap.get(0).get("CompanyID").toString());
            appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_supportCase_NewManualCase_PayNetSearch_Btn);
            appDep.threadSleep(1000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_PayNetLoading,"invisibility","", minTimeOut);
            reports.writeResult(oBrowser, "screenshot", "Field verification for Merger/Acquisition - supplier-1");
            appInd.selectObject(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_Merger_SupplierState,"1")), 2);
            appInd.clearAndSetObject(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_Merger_SupplierAddress, "1")), supplier1Address);
            appInd.clearAndSetObject(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_Merger_SupplierCity, "1")), supplier1City);
            appInd.clearAndSetObject(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_Merger_SupplierPinCode, "1")), supplier1Pin);
            //-------------- supplier-2 information -----------------
            appInd.clearAndSetObject(oBrowser, By.id("sc_paynet_supplier_id2"),paynetInfoMap.get(1).get("CompanyID").toString());
            appInd.clickObject(oBrowser, By.id("btn_search_paynet_supplier_id2"));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_PayNetLoading,"invisibility","", minTimeOut);
            reports.writeResult(oBrowser, "screenshot", "Field verification for Merger/Acquisition - supplier-2");
            appInd.selectObject(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_Merger_SupplierState,"2")), 3);
            appInd.clearAndSetObject(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_Merger_SupplierAddress, "2")), supplier2Address);
            appInd.clearAndSetObject(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_Merger_SupplierCity, "2")), supplier2City);
            appInd.clearAndSetObject(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_Merger_SupplierPinCode, "2")), supplier2Pin);

            List<String> userNameList = appInd.getWebElements(oBrowser, By.xpath("//td[contains(@aria-label,'Column Name')]")).stream()
                    .filter( ele -> !(ele.getText().equalsIgnoreCase(""))).map(ele -> ele.getText()).collect(Collectors.toList());
            List<String> userEmailList = appInd.getWebElements(oBrowser, By.xpath("//td[contains(@aria-label,'Column Email')]")).stream()
                    .filter( ele -> !(ele.getText().equalsIgnoreCase(""))).map(ele -> ele.getText()).collect(Collectors.toList());

            appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_CreateBtn);
            appDep.threadSleep(1000);
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_LogActivity_Btn), "visibility", "", minTimeOut);
            //----------------------------------- verify Additional information tile -----------------------------------
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("//div[@id='section_contact_information']//h3"),"text").contains("Additional Information"));
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AdditionalInfoTile, "1", "Name", "1")), "text"),paynetInfoMap.get(0).get("companyName"));
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AdditionalInfoTile, "2", "Tin", "2")), "text"),paynetInfoMap.get(0).get("TIN"));
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AdditionalInfoTile, "3", "Country", "3")), "text"),"US");
            Assert.assertTrue(StringUtils.isNotBlank(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AdditionalInfoTile, "4", "State/province", "4")), "text")));
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AdditionalInfoTile, "5", "Address", "5")), "text"),supplier1Address);
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AdditionalInfoTile, "6", "City", "6")), "text"),supplier1City);
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AdditionalInfoTile, "7", "Postal code", "7")), "text"),supplier1Pin);

            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AdditionalInfoTile, "8", "Name", "8")), "text"), paynetInfoMap.get(1).get("companyName"));
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AdditionalInfoTile, "9", "Tin", "9")), "text"),paynetInfoMap.get(1).get("TIN"));
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AdditionalInfoTile, "10", "Country", "10")), "text"),"US");
            Assert.assertTrue(StringUtils.isNotBlank(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AdditionalInfoTile, "11", "State/province", "11")), "text")));
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AdditionalInfoTile, "12", "Address", "12")), "text"),supplier2Address);
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AdditionalInfoTile, "13", "City", "13")), "text"),supplier2City);
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_AdditionalInfoTile, "14", "Postal code", "14")), "text"),supplier2Pin);

            //------------------------------------ Activity & User Tab verification ----------------------------------
            appInd.clickObject(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_Generic_Text,"Activities")));
            appDep.threadSleep(2000);
            appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_CaseDetailGrid, "divSupportCasesActivitiesContainer", "Activity Column")));
            appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_CaseDetailGrid, "divSupportCasesActivitiesContainer", "Activity Type Column")));
            appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_CaseDetailGrid, "divSupportCasesActivitiesContainer", "Reason Column")));
            appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_CaseDetailGrid, "divSupportCasesActivitiesContainer", "Created By Column")));
            appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_CaseDetailGrid, "divSupportCasesActivitiesContainer", "Date Created Column")));
            appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_CaseDetailGrid, "divSupportCasesActivitiesContainer", "Notes Column")));
            appInd.clickObject(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_Generic_Text,"Users")));

            List<String> gridNameList = appInd.getWebElements(oBrowser, By.xpath("//td[contains(@aria-label,'Column Name')]")).stream()
                    .filter( ele -> !(ele.getText().equalsIgnoreCase(""))).map(ele -> ele.getText()).collect(Collectors.toList());
            List<String> gridEmailList = appInd.getWebElements(oBrowser, By.xpath("//td[contains(@aria-label,'Column Email')]")).stream()
                    .filter( ele -> !(ele.getText().equalsIgnoreCase(""))).map(ele -> ele.getText()).collect(Collectors.toList());

            Assert.assertEquals(userNameList, gridNameList);
            Assert.assertEquals(userEmailList, gridEmailList);
            //------------------------- verify Case & Supplier Information & Grid verification -----------------------
            verifyCreatedCaseDetails(oBrowser, caseType, paynetInfoMap.get(0));
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createAndVerifyMergerAcquisition()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createAndVerifyMergerAcquisition()' method. " + e);
            reports.writeResult(oBrowser, "screenshot", "AssertionError in 'createAndVerifyMergerAcquisition()' method. " + e);
            return false;
        }
        return true;
    }

    private void verifyCreatedCaseDetails(WebDriver oBrowser, String caseType, LinkedHashMap<String, Object> paynetInfoMap){
        //----------------------------------- verify manual case is created ---------------------------------------
        reports.writeResult(oBrowser, "screenshot", "Case is successfully created and verify case detail page");
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_LogActivity_Btn)));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Reassign_Btn));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_CaseStatus, "Open"))));
        Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_CaseInfoTile, "1", "Assigned to", "1")), "text"),appInd.getPropertyValueByKeyName("qaUserName"));
        Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_CaseInfoTile, "2", "Client", "2")), "text"),"");
        Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_CaseInfoTile, "3", "Created", "3")), "text"),appInd.dateAddAndReturn("MM/dd/yyyy",0));
        Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_CaseInfoTile, "4", "Case Type", "4")), "text"),caseType);
        Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_CaseInfoTile, "5", "Priority", "5")), "text"),"Low");
        Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_CaseInfoTile, "6", "Area Name", "6")), "text"),"Company Information");
        Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_CaseInfoTile, "7", "Last Activity", "7")), "text"),"");
        Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_CaseInfoTile, "8", "Outcome", "8")), "text"),"");
        Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_CaseInfoTile, "9", "Alarm Date", "9")), "text"),"");
        Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_CaseInfoTile, "10", "Updated By", "10")), "text"),appInd.getPropertyValueByKeyName("qaUserName"));
        String newCaseID = appInd.getTextOnElement(oBrowser, By.xpath("//div[@id='section_case_information']//h3"), "text").split(" ")[1];
        //----------------------------------- verify supplier information tile -----------------------------------
        Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("//div[@id='section_paynet_supplier_information']//h3"),"text").contains("Supplier Information"));
        Assert.assertTrue(appInd.getAttribute(oBrowser, By.xpath("//div[@id='section_paynet_supplier_information']//h3/a"),"data-original-title").contains("Information fetched from PayNet"));
        Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_SupplierInfoTile, "2", "PayNet ID", "2")), "text"),paynetInfoMap.get("CompanyID").toString());
        Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_SupplierInfoTile, "3", "DBA", "3")), "text"),"");
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_DetailPage_Tab, "Activities"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_DetailPage_Tab, "Users"))));
        Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_DetailPage_Tab, "Internal Attachments"))));
        //-------------------------------------------- Verify record in grid --------------------------------------
        oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
        Assert.assertTrue(verifySupportCaseGridRecordVisible("Active","Case #", newCaseID, true));
        appDep.threadSleep(2000);
        String date = appInd.formatDate(appInd.dateAddAndReturn("MM/dd/yyyy",0), "MM/dd/yyyy","MM/dd/yyyy");
        Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//tr[contains(@class, 'dx-row-lines')]/td[7]"), "Text").contains(date));
        Assert.assertTrue((appInd.verifyElementNotPresent(oBrowser, By.xpath("(("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//table)[2]//tr[1])[1]/td[contains(@aria-label,'Country Code') and (text()='US' or text()='CA')]"))));
        Assert.assertFalse(verifySupportCaseGridRecordVisible("Pending","Case #", newCaseID, false));
        appDep.threadSleep(2000);
        Assert.assertTrue((appInd.verifyElementNotPresent(oBrowser, By.xpath("(("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Pending_Grid + "//table)[2]//tr[1])[1]/td[contains(@aria-label,'Country Code') and (text()='US' or text()='CA')]"))));
        Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath("(("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Pending_Grid + "//table)[2]//tr[1])[1]/td[contains(@aria-label,'Action Date') and text()='"+date+"']")));
    }



    /********************************************************
     * Method Name      : validateSupportManualCaseFields()
     * Purpose          : to validate manual support case form fields validations & verifications.
     * Author           : Deepak
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateSupportManualCaseFields(WebDriver oBrowser, String caseType, List<LinkedHashMap<String, Object>> paynetInfoMap){
          switch (caseType){
              case "ACH Return/Reject/NOC":
                   return validateSupportManualCaseTypeACH_Return_Reject_NOC(oBrowser, paynetInfoMap.get(0));
              case "New User Request":
                    return validateSupportManualCaseTypeNew_User_Request(oBrowser, paynetInfoMap.get(0));
              case "Pre-note Failure":
                    return validateSupportManualCaseTypePreNoteFailure(oBrowser, paynetInfoMap.get(0));
              case "Verbal Validation":
                    return validateSupportManualCaseTypeVerbalValidation(oBrowser, paynetInfoMap.get(0));
              case "Merger/Acquisition":
                    return validateSupportManualCaseTypeMergerAcquisition(oBrowser, paynetInfoMap);
              case "Website Validation":
                    return validateSupportManualCaseTypeWebsiteValidation(oBrowser, paynetInfoMap.get(0));
              default:
                  System.out.println("Invalid Manual Case type");
                  return false;
          }
    }

    private boolean validateSupportManualCaseTypeACH_Return_Reject_NOC(WebDriver oBrowser, LinkedHashMap<String, Object> paynetInfoMap){
        try {
            //------------------------------ ACH Return/Reject/NOC --------------------------------
            appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_CaseType_DD, "ACH Return/Reject/NOC");
            appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_supportCase_NewManualCase_PayNetSearch_Btn);
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "screenshot", "Blank paynetID validation verification");
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_PayNetErr_Msg, "text"), "Please enter PayNet Supplier ID");
            appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_supportCase_NewManualCase_PayNetTextBoxID,String.valueOf(appInd.getRandomNumber(6)));
            appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_supportCase_NewManualCase_PayNetSearch_Btn);
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "screenshot", "Invalid paynetID validation verification");
            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.id("sc_error_paynet_id1_message"), "text"), "Above PayNet ID does not exist.");
            appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_supportCase_NewManualCase_PayNetTextBoxID,paynetInfoMap.get("CompanyID").toString());
            appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_supportCase_NewManualCase_PayNetSearch_Btn);
            reports.writeResult(oBrowser, "screenshot", "valid paynetID verification");
            appDep.threadSleep(1000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_PayNetLoading,"invisibility","", minTimeOut);
            String sysAdminPath = "//div[@id='sc_type_ach_return_reject_noc']".concat(String.format(PayCRM_Modules_DailyGrindUIPage.obj_generic_heading,"System Administrator(s)"));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_generic_heading,"Additional Fields"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(sysAdminPath)));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_generic_td_aria_label,"Name Column"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_generic_td_aria_label,"Email Column"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_generic_td_aria_label,"Phone Column"))));
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateSupportManualCaseTypeACH_Return_Reject_NOC()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateSupportManualCaseTypeACH_Return_Reject_NOC()' method. " + e);
            reports.writeResult(oBrowser, "screenshot", "AssertionError in 'validateSupportManualCaseTypeACH_Return_Reject_NOC()' method. " + e);
            return false;
        }
        return true;
    }

    private boolean validateSupportManualCaseTypeNew_User_Request(WebDriver oBrowser, LinkedHashMap<String, Object> paynetInfoMap){
        try {
            //------------------------------ New User Request default fields verification --------------------------------
            appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_CaseType_DD, "New User Request");
            appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_supportCase_NewManualCase_PayNetTextBoxID,paynetInfoMap.get("CompanyID").toString());
            appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_supportCase_NewManualCase_PayNetSearch_Btn);
            appDep.threadSleep(1000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_PayNetLoading,"invisibility","", minTimeOut);
            reports.writeResult(oBrowser, "screenshot", "Field verification for New User Request");
            String sysAdminPath = "//div[@id='sc_type_new_user_request']".concat(String.format(PayCRM_Modules_DailyGrindUIPage.obj_generic_heading,"System Administrator(s)"));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_generic_heading,"Additional Fields"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(sysAdminPath)));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_generic_td_aria_label,"Name Column"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_generic_td_aria_label,"Email Column"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_generic_td_aria_label,"Phone Column"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_NewUserRequest_Name));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_NewUserRequest_Email));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_NewUserRequest_JobTitle));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_NewUserRequest_SupervisorName));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_NewUserRequest_SuperVisorEmail));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_NewUserRequest_SuperVisorJobTitle));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_CreateBtn));
            //------------------------------------------ Mandatory fields verifications -------------------------------------------
            appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_CreateBtn);
            appDep.threadSleep(1000);
            Assert.assertTrue(StringUtils.isNotBlank(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_ErrorMsg, "Please enter New User Name")),"text")));
            Assert.assertTrue(StringUtils.isNotBlank(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_ErrorMsg, "Please enter New User Email")),"text")));
            Assert.assertTrue(StringUtils.isNotBlank(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_ErrorMsg, "Please enter New User Job Title")),"text")));
            Assert.assertTrue(StringUtils.isNotBlank(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_ErrorMsg, "Please enter Officer/Supervisor to Verify Name")),"text")));
            Assert.assertTrue(StringUtils.isNotBlank(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_ErrorMsg, "Please enter Officer/Supervisor to Verify Email")),"text")));
            Assert.assertTrue(StringUtils.isNotBlank(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_ErrorMsg, "Please enter Officer/Supervisor to Verify Job Title")),"text")));
            appDep.threadSleep(1000);
            appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_NewUserRequest_Name, String.valueOf(appInd.getRandomNumber(5)));
            appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_NewUserRequest_Email, String.valueOf(appInd.getRandomNumber(5)));
            appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_NewUserRequest_JobTitle, String.valueOf(appInd.getRandomNumber(5)));
            appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_NewUserRequest_SupervisorName, String.valueOf(appInd.getRandomNumber(5)));
            appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_NewUserRequest_SuperVisorEmail, String.valueOf(appInd.getRandomNumber(5)));
            appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_NewUserRequest_SuperVisorJobTitle, String.valueOf(appInd.getRandomNumber(5)));
            appDep.threadSleep(1000);
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_ErrorMsg, "Please enter New User Name"))));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_ErrorMsg, "Please enter New User Job Title"))));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_ErrorMsg, "Please enter Officer/Supervisor to Verify Name"))));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_ErrorMsg, "Please enter Officer/Supervisor to Verify Job Title"))));
            Assert.assertTrue(appInd.getWebElements(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_ErrorMsg, "Please enter a valid email address"))).size()==2);
            appDep.threadSleep(1000);
            appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_NewUserRequest_Email, "test@cpay.com");
            appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_NewUserRequest_SuperVisorEmail, "cpay@test.com");
            appDep.threadSleep(1000);
            Assert.assertTrue(appInd.getWebElements(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_ErrorMsg, "Please enter a valid email address"))).size()==0);
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateSupportManualCaseTypeNew_User_Request()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateSupportManualCaseTypeNew_User_Request()' method. " + e);
            reports.writeResult(oBrowser, "screenshot", "AssertionError in 'validateSupportManualCaseTypeNew_User_Request()' method. " + e);
            return false;
        }
        return true;
    }

    private boolean validateSupportManualCaseTypePreNoteFailure(WebDriver oBrowser, LinkedHashMap<String, Object> paynetInfoMap){
        try {
            //------------------------------ Pre-note Failure --------------------------------
            appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_CaseType_DD, "Pre-note Failure");
            appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_supportCase_NewManualCase_PayNetTextBoxID,paynetInfoMap.get("CompanyID").toString());
            appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_supportCase_NewManualCase_PayNetSearch_Btn);
            appDep.threadSleep(1000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_PayNetLoading,"invisibility","", minTimeOut);
            reports.writeResult(oBrowser, "screenshot", "Field verification for Pre-note Failure");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_generic_heading,"Additional Fields"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_PreNoteFailure_Contact));
            appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_CreateBtn);
            appDep.threadSleep(1000);
            reports.writeResult(oBrowser, "screenshot", "Field validation error message for Pre-note Failure");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_ErrorMsg,"Please enter Primary/Enrollment Contact"))));
            appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_PreNoteFailure_Contact, String.valueOf(appInd.getRandomNumber(7)));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_ErrorMsg,"Please enter Primary/Enrollment Contact"))));

        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateSupportManualCaseTypePreNoteFailure()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateSupportManualCaseTypePreNoteFailure()' method. " + e);
            reports.writeResult(oBrowser, "screenshot", "AssertionError in 'validateSupportManualCaseTypePreNoteFailure()' method. " + e);
            return false;
        }
        return true;
    }

    private boolean validateSupportManualCaseTypeVerbalValidation(WebDriver oBrowser, LinkedHashMap<String, Object> paynetInfoMap){
        try {
            //------------------------------ Verbal Validation --------------------------------
            appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_CaseType_DD, "Verbal Validation");
            appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_supportCase_NewManualCase_PayNetTextBoxID,paynetInfoMap.get("CompanyID").toString());
            appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_supportCase_NewManualCase_PayNetSearch_Btn);
            appDep.threadSleep(1000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_PayNetLoading,"invisibility","", minTimeOut);
            reports.writeResult(oBrowser, "screenshot", "Field verification for Verbal Validation");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_generic_heading,"Additional Fields"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_verbalValidation_Contact));
            appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_CreateBtn);
            appDep.threadSleep(1000);
            reports.writeResult(oBrowser, "screenshot", "Field validation error message for verbal validation");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_ErrorMsg,"Please enter Primary/Enrollment Contact"))));
            appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_verbalValidation_Contact, String.valueOf(appInd.getRandomNumber(7)));
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_ErrorMsg,"Please enter Primary/Enrollment Contact"))));
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateSupportManualCaseTypeVerbalValidation()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateSupportManualCaseTypeVerbalValidation()' method. " + e);
            reports.writeResult(oBrowser, "screenshot", "AssertionError in 'validateSupportManualCaseTypeVerbalValidation()' method. " + e);
            return false;
        }
        return true;
    }

    private boolean validateSupportManualCaseTypeWebsiteValidation(WebDriver oBrowser, LinkedHashMap<String, Object> paynetInfoMap){
        try {
            //------------------------------ Website Validation --------------------------------
            appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_CaseType_DD, "Website Validation");
            appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_supportCase_NewManualCase_PayNetTextBoxID,paynetInfoMap.get("CompanyID").toString());
            appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_supportCase_NewManualCase_PayNetSearch_Btn);
            appDep.threadSleep(1000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_PayNetLoading,"invisibility","", minTimeOut);
            reports.writeResult(oBrowser, "screenshot", "Field verification for Website Validation");
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateSupportManualCaseTypeWbsiteValidation()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateSupportManualCaseTypeWbsiteValidation()' method. " + e);
            reports.writeResult(oBrowser, "screenshot", "AssertionError in 'validateSupportManualCaseTypeWbsiteValidation()' method. " + e);
            return false;
        }
        return true;
    }

    private boolean validateSupportManualCaseTypeMergerAcquisition(WebDriver oBrowser, List<LinkedHashMap<String, Object>> paynetInfoMap){

        try {
            //------------------------------ Merger/Acquisition --------------------------------
            appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_CaseType_DD, "Merger/Acquisition");
            appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_supportCase_NewManualCase_PayNetTextBoxID,paynetInfoMap.get(0).get("CompanyID").toString());
            appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_supportCase_NewManualCase_PayNetSearch_Btn);
            appDep.threadSleep(1000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_PayNetLoading,"invisibility","", minTimeOut);
            reports.writeResult(oBrowser, "screenshot", "Field verification for Merger/Acquisition - supplier-1");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_generic_heading,"Enter Supplier Profile 1 PayNet Supplier ID"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_generic_heading,"Additional Fields"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_generic_heading,"Supplier Profile 1"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_Merger_SupplierName,"1"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_Merger_SupplierTin,"1"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_Merger_SupplierCountry,"1"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_Merger_SupplierState,"1"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_Merger_SupplierAddress, "1"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_Merger_SupplierCity, "1"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_Merger_SupplierPinCode, "1"))));
            //------------------------------------------------- verify fields contains default values [supplier-1]------------------------------------------------
            Assert.assertTrue(StringUtils.isNotBlank(appInd.getTextOnElement(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_Merger_SupplierName,"1")), "value")));
            Assert.assertTrue(StringUtils.isNotBlank(appInd.getTextOnElement(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_Merger_SupplierTin,"1")), "value")));
            Assert.assertTrue(appInd.getAttribute(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_CreateBtn, "class").endsWith("disable-submit"));
            paynetInfoMap.get(0).put("companyName", appInd.getTextOnElement(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_Merger_SupplierName,"1")), "value"));
            paynetInfoMap.get(0).put("TIN", appInd.getTextOnElement(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_Merger_SupplierTin,"1")), "value"));
            appInd.scrollToElement(oBrowser, By.id("sc_paynet_supplier_id2"));
            appInd.setObject(oBrowser, By.id("sc_paynet_supplier_id2"),paynetInfoMap.get(0).get("CompanyID").toString());
            appInd.clickObject(oBrowser, By.id("btn_search_paynet_supplier_id2"));
            appDep.threadSleep(3000);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_ErrorMsg, "PayNet Supplier ID for Supplier Profile 1 & Supplier Profile 2 can not be same."))));

            appInd.scrollToElement(oBrowser, By.id("sc_paynet_supplier_id2"));
            appInd.clearAndSetObject(oBrowser, By.id("sc_paynet_supplier_id2"),paynetInfoMap.get(1).get("CompanyID").toString());
            appInd.clickObject(oBrowser, By.id("btn_search_paynet_supplier_id2"));
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_PayNetLoading,"invisibility","", minTimeOut);
            reports.writeResult(oBrowser, "screenshot", "Field verification for Merger/Acquisition - supplier-2");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_Merger_SupplierName,"2"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_Merger_SupplierTin,"2"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_Merger_SupplierCountry,"2"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_Merger_SupplierState,"2"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_Merger_SupplierAddress, "2"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_Merger_SupplierCity, "2"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_Merger_SupplierPinCode, "2"))));
            //------------------------------------------------- verify fields contains default values [supplier-2]------------------------------------------------
            Assert.assertTrue(StringUtils.isNotBlank(appInd.getTextOnElement(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_Merger_SupplierName,"2")), "value")));
            Assert.assertTrue(StringUtils.isNotBlank(appInd.getTextOnElement(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_Merger_SupplierTin,"2")), "value")));
            paynetInfoMap.get(1).put("companyName", appInd.getTextOnElement(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_Merger_SupplierName,"2")), "value"));
            paynetInfoMap.get(1).put("TIN", appInd.getTextOnElement(oBrowser, By.id(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_Merger_SupplierTin,"2")), "value"));

            Assert.assertTrue(appInd.getAttribute(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_CreateBtn, "class").endsWith("btn-success"));
            appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_CreateBtn);
            appDep.threadSleep(1000);
            Assert.assertTrue(appInd.getWebElements(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_ErrorMsg, "Please enter State/Province"))).size()==2);
            Assert.assertTrue(appInd.getWebElements(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_ErrorMsg, "Please enter Address"))).size()==2);
            Assert.assertTrue(appInd.getWebElements(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_ErrorMsg, "Please enter City"))).size()==2);
            Assert.assertTrue(appInd.getWebElements(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_ErrorMsg, "Please enter Postal Code"))).size()==2);
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateSupportManualCaseTypeMergerAcquisition()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateSupportManualCaseTypeMergerAcquisition()' method. " + e);
            reports.writeResult(oBrowser, "screenshot", "AssertionError in 'validateSupportManualCaseTypeMergerAcquisition()' method. " + e);
            return false;
        }
        return true;
    }

    private boolean verifySupportCaseGridRecordVisible(String tabName, String columnName, String data, boolean isRecordPresent){
        String path, gridInputBox = "";
        By tab;
        if(tabName.equalsIgnoreCase("active")){
            path = PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid;
            tab = PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Tab;
            gridInputBox = PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_ActiveTabColumnSearch;
            Assert.assertTrue(appInd.clickObject(oBrowser, tab));
        }
        else{
            tab = PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Pending_Tab;
            Assert.assertTrue(appInd.clickObject(oBrowser, tab));
            path = PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Pending_Grid;
            gridInputBox = PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_PendingTabColumnSearch;
        }

        appDep.threadSleep(1000);
        appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
        appInd.clearAndSetObject(oBrowser, By.xpath(String.format(gridInputBox, columnName)), data);
        oBrowser.findElement(By.xpath(String.format(gridInputBox, columnName))).sendKeys(Keys.ENTER);
        appDep.threadSleep(1000);
        appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
        reports.writeResult(oBrowser, "Screenshot", "Record status in grid : Tab = "+tabName+" , column = "+columnName+ " , search Data = "+data+" , should visible = "+isRecordPresent);
        appDep.threadSleep(1000);
        boolean isDisplay = appInd.verifyElementPresent(oBrowser, By.xpath("(("+path + "//table)[2]//tr[1])[1]/td[contains(@aria-label,'"+columnName+"') and text()='"+data+"']"));
        Assert.assertTrue(appInd.clickObject(oBrowser, tab));
        appDep.threadSleep(1000);
        appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
        return isDisplay;
    }

    /********************************************************
     * Method Name      : validateTheSwitchPaymentTypeReportForPaymentSwitchEntry()
     * Purpose          : to verify that the switch payment type report for payment switch entry
     * Author           : Aravind
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateTheSwitchPaymentTypeReportForPaymentSwitchEntry(WebDriver oBrowser){
        try{
            Assert.assertTrue(appDep.selectManageModules(oBrowser,"Reports"));
            Assert.assertTrue(appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SwitchedPaymentTypes_btn,"Visibility","",waitTimeOut));
            Assert.assertTrue(appInd.clickObject(oBrowser,PayCRM_Modules_DailyGrindUIPage.obj_SwitchedPaymentTypes_btn));
            Assert.assertTrue(appDep.waitForTheElementState(oBrowser,PayCRM_Modules_DailyGrindUIPage.obj_SwitchedPaymentTypes_RunReport_btn, "Visibility","",waitTimeOut));
            Assert.assertTrue(appInd.clickObject(oBrowser,PayCRM_Modules_DailyGrindUIPage.obj_SwitchedPaymentTypes_RunReport_btn));
            Assert.assertTrue(appDep.waitForTheElementState(oBrowser,By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_Report_Grid_CaseId, "divSwitchPaymentTypesContainer")),"Visibility","",minTimeOut));
            Assert.assertNotNull(appInd.getTextOnElement(oBrowser,By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_Report_Grid_CaseId,"divSwitchPaymentTypesContainer")),"text"));
            Assert.assertTrue(appInd.clickObject(oBrowser,PayCRM_Modules_DailyGrindUIPage.obj_SwitchDate_Calender_btn));
            Assert.assertTrue(appInd.clickObject(oBrowser,PayCRM_Modules_DailyGrindUIPage.obj_SwitchDate_Calender_CurrentDate));
            Assert.assertTrue(appInd.clickObject(oBrowser,PayCRM_Modules_DailyGrindUIPage.obj_SwitchedPaymentTypes_RunReport_btn));
            Assert.assertTrue(appDep.waitForTheElementState(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_Report_Grid_Calender_CurrentDate_value,"divSwitchPaymentTypesContainer")),"visibility","",waitTimeOut));
            Assert.assertNotNull(appInd.getTextOnElement(oBrowser,By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_Report_Grid_Calender_CurrentDate_value,"divSwitchPaymentTypesContainer")),"text"));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser,PayCRM_Modules_DailyGrindUIPage.obj_SwitchedPaymentTypes_RunReport_btn));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser,By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_Report_Grid_CaseId,"divSwitchPaymentTypesContainer"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser,By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_Report_Grid_Calender_CurrentDate_value,"divSwitchPaymentTypesContainer"))));
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "validateTheSwitchPaymentTypeReportForPaymentSwitchEntry()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateTheSwitchPaymentTypeReportForPaymentSwitchEntry()' method. " + e);
            return false;
        }
        return true;
    }



    /********************************************************
     * Method Name      : validateSupportCaseEmailLogActivityFields()
     * Purpose          : User validates Support Case Email Log Activity field functionalities
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateSupportCaseEmailLogActivityFields(WebDriver oBrowser){
        Map<String, String> supportCaseDetails = null;
        Select oSelect = null;
        List<WebElement> oEles = null;
        String arrDropdownOptions[];
        try{
            supportCaseDetails = appDep.searchAndOpenTheSupportCase(oBrowser, new String[] {"", "", "Any"});

            //click on Log Activity button
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_LogActivity_Btn)));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_LogActivity_Notes_Textarea, "Clickable", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "The 'Log Activity' page has opened successful");

            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ActivityType_List + "[not(contains(@class, 'disable-submit'))]")), "The 'Unable to Validate' toggle is by default off");

            //Activity Type dropdown option values
            oSelect = new Select(oBrowser.findElement(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ActivityType_List + "[not(contains(@class, 'disable-submit'))]")));
            oEles = oSelect.getOptions();
            arrDropdownOptions = "-- Select Activity --#Email#Fax#Phone (Inbound/Outbound)#Website".split("#");
            for(int i=0; i<arrDropdownOptions.length; i++){
                Assert.assertTrue(appInd.compareValues(oBrowser, oEles.get(i).getText(), arrDropdownOptions[i]), "Mis-match in Actual '"+oEles.get(i).getText()+"' & Expected '"+arrDropdownOptions[i]+"' values from the 'Activity Type' dropdown");
            }


            //Case outcome dropdown option values
            oSelect = new Select(oBrowser.findElement(PayCRM_Modules_DailyGrindUIPage.Obj_CaseOutcome_List));
            oEles = oSelect.getOptions();
            arrDropdownOptions = "-- Select Case Outcome --#Validation Closed#Validation Pending".split("#");
            for(int i=0; i<arrDropdownOptions.length; i++){
                Assert.assertTrue(appInd.compareValues(oBrowser, oEles.get(i).getText(), arrDropdownOptions[i]), "Mis-match in Actual '"+oEles.get(i).getText()+"' & Expected '"+arrDropdownOptions[i]+"' values from the 'Case Outcome' dropdown");
            }
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.Obj_CaseOutcome_List, "Validation Closed"));
            appDep.threadSleep(2000);

            //Reason dropdown option values
            oSelect = new Select(oBrowser.findElement(PayCRM_Modules_DailyGrindUIPage.obj_Reason_List));
            oEles = oSelect.getOptions();
            arrDropdownOptions = ("-- Select Case Outcome Reason --#Address validated#Assisted with updating Company information#Assisted with updating Payment information#Bank Letter/Void Check received and processed#Bank Letter/Void Check Requested#Client Request Removal#Dual Approval Completed#Follow up email sent#Login Assistance#Merger/Acquisition Documents Received and Processed#Merger/Acquisition Documents Requested#No response#" +
                    "Not Listed#OFAC validated#PAF and Sole Prop Documentation Received and Processed#PAF Received and Processed#PAF Rejected – Contains Edits#PAF Rejected – Need Officer Signature#PAF Rejected – Need Sole Prop documents#PAF Request sent#Referred back to Banker Support#" +
                    "Referred back to Client#Remittance delivery assistance#Vendor Refused to Participate#Verbal Validation completed#Voicemail – left message#W-9 Received and Processed#Website Validated").split("#");
            for(int i=0; i<arrDropdownOptions.length; i++){
                Assert.assertTrue(appInd.compareValues(oBrowser, oEles.get(i).getText(), arrDropdownOptions[i]), "Mis-match in Actual '"+oEles.get(i).getText()+"' & Expected '"+arrDropdownOptions[i]+"' values from the 'Reason' dropdown");
            }

            //Automated support cases should have attachment button
            if(supportCaseDetails.get("originationSource").contains("Automatic")){
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ChooseFile_Btn), "The 'Choose File' button of Attachment was not found for automatic support cases'");
            }else{
                Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ChooseFile_Btn), "The 'Choose File' button of Attachment was still present for Manual support cases'");
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_CompanyEditConfirmationMessage_Label));
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_CompanyEditConfirmationMessage_Label, "Text").contains("This is a manually created case. If there are any open validations in StopFraud related to this case, you will need to close them directly in StopFraud"));
            }

            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_LogActivity_Notes_Textarea));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activity_Save_Btn));
            oBrowser.navigate().refresh();
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_LogActivity_Notes_Textarea, "Clickable", "", waitTimeOut);


            // click on "Log Activity" submit button without entering any values. & verify the error messages
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activity_Save_Btn));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "click on 'Log Activity' submit button without entering any values. & verify the error messages");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ActivityType_Error_Msg));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ActivityType_Error_Msg, "Text", "This field is required."));

            // functionality removed
//            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_CaseOutcome_Error_Msg));
//            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_CaseOutcome_Error_Msg, "Text", "This field is required."));


            // click on "Log Activity" submit button with Unable to Validate toggle ON and without entering any values. & verify the LogActivity_Notes error messages
            Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_UnableToValidate_Toggle));

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activity_Save_Btn));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "click on 'Log Activity' submit button with Unable to Validate toggle ON and without entering any values. & verify the LogActivity_Notes error messages");

            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_LogActivity_Notes_Error_Msg));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_LogActivity_Notes_Error_Msg, "Text", "This field is required."));
            // Unable to Validate toggle OFF
            Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_UnableToValidate_Toggle));

            //Email Option under 'Activity Type' dropdown
            Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ActivityType_List + "[not(contains(@class, 'disable-submit'))]"), "Email"));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "'Activity Type' as Email");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_EmailAddress_Edit));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ContactName_Edit));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ContactNumber_Edit));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ConversationID_Edit));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activity_Save_Btn));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_EmailAddress_Error_Msg));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_EmailAddress_Error_Msg, "Text", "This field is required."));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_EmailAddress_Edit, "ggudi_corcentric.com"));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_EmailAddress_Error_Msg, "Text", "Please enter a valid email address."));


            //Fax Option under 'Activity Type' dropdown
            Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ActivityType_List + "[not(contains(@class, 'disable-submit'))]"), "Fax"));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "'Activity Type' as Fax");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_FaxNumber_Edit));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Fax_ConversationID_Edit));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activity_Save_Btn));
            appDep.threadSleep(2000);
//            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_EmailAddress_Error_Msg));
//            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_FaxNumber_Error_Msg, "Text", "This field is required."));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_FaxNumber_Edit, "gudi2602"));
            appDep.threadSleep(2000);
//            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_FaxNumber_Error_Msg, "Text", "Please enter only digits."));


            //Phone Option under 'Activity Type' dropdown
            Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ActivityType_List + "[not(contains(@class, 'disable-submit'))]"), "Phone (Inbound/Outbound)"));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "'Activity Type' as Phone (Inbound/Outbound)");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_PhoneNumber_Edit));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Phone_ContactName_Edit));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Phone_ConversationID_Edit));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Phone_JobTitle_Edit));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activity_Save_Btn));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_PhoneNumber_Error_Msg));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_PhoneNumber_Error_Msg, "Text", "This field is required."));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ContactName_Error_Msg));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ContactName_Error_Msg, "Text", "This field is required."));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_JobTitle_Error_Msg));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_JobTitle_Error_Msg, "Text", "This field is required."));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_PhoneNumber_Edit, "123456789111"));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_PhoneNumber_Error_Msg, "Text", "Please enter no more than 10 characters."));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_PhoneNumber_Edit, "gudi1234567"));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_PhoneNumber_Error_Msg, "Text", "Please enter only digits."));



            //Do not enter notes and save the record
            Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ActivityType_List + "[not(contains(@class, 'disable-submit'))]"), "Fax"));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_FaxNumber_Edit, "1234567891"));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.Obj_CaseOutcome_List, "Validation Pending"));
            appDep.threadSleep(1000);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_CaseOutCome_Hold_Msg, "Text", "This case will be put on hold"));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Reason_List, 2));
            // Validation Completed toggle ON
            Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ValidationCompleted_Toggle));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activity_Save_Btn));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_LogActivity_Notes_Error_Msg));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_LogActivity_Notes_Error_Msg, "Text", "This field is required."));
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
            appDep.threadSleep(3000);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Tab));
            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "validateSupportCaseEmailLogActivityFields()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateSupportCaseEmailLogActivityFields()' method. " + e);
            return false;
        }finally {supportCaseDetails = null; oSelect = null; oEles = null; arrDropdownOptions = null;}
    }





    /********************************************************
     * Method Name      : createAndValidateLogActivityForDifferentTypes_SupportCases()
     * Purpose          : User validates create and validate Activity Types for different types for Support Cases
     * Author           : Gudi
     * Parameters       : oBrowser, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean createAndValidateLogActivityForDifferentTypes_SupportCases(WebDriver oBrowser, DataTable dataTable){
        Map<String, String> supportCaseDetails = null;
        List<Map<String, String>> logActivityData = null;
        Map<String, String> logActivity = null;
        Alert oAlert = null;
        String fileName = null;
        try{
            logActivityData = dataTable.asMaps(String.class, String.class);
            for(int i=0; i<logActivityData.size(); i++){
                supportCaseDetails = appDep.searchAndOpenTheSupportCase(oBrowser, new String[]{"", "", "Any"});
                logActivity = new HashMap<>();
                logActivity.put("SupportCaseType", logActivityData.get(i).get("SupportCaseType"));
                logActivity.put("UnableToValidate", logActivityData.get(i).get("UnableToValidate"));
                logActivity.put("ValidateCompleted", logActivityData.get(i).get("ValidateCompleted"));
                logActivity.put("ActivityType", logActivityData.get(i).get("ActivityType"));
                logActivity.put("ActivityTypeSpecificData", logActivityData.get(i).get("ActivityTypeSpecificData"));
                logActivity.put("CaseOutcome", logActivityData.get(i).get("CaseOutcome"));
                logActivity.put("CaseOutcomeReason", logActivityData.get(i).get("CaseOutcomeReason"));
                logActivity.put("FileToUpload", logActivityData.get(i).get("FileToUpload"));
                logActivity.put("Notes", logActivityData.get(i).get("Notes"));


                //user perform 'Log Activity' for the selected Support Case
                logActivity = appDep.supportCaseLogActivity(oBrowser, supportCaseDetails.get("caseNumber"), logActivity);

                //Close the 'Log Activity' support form
                oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_Link, "Clickable", "", waitTimeOut);

                //Validate the support case status, Activities tab & External attachment tab if attachment was added for Automatic support case
                Assert.assertTrue(appDep.validateSupportCaseStatusAndActivitiesTab(oBrowser, logActivity));

                //validate external attachments
                if(logActivity.get("SupportCaseType").equalsIgnoreCase("Automatic") && logActivity.get("FileToUpload")!=null){
                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ExternalAttachments_Link));
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                    Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_ExternalAttachments_Grid + "//input)[3]"), logActivity.get("FileToUpload")));
                    appDep.threadSleep(2000);
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ExternalAttachments_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[2]"), "Text", logActivity.get("FileToUpload")));

                    //Download the external attachment and validate the same
                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ExternalAttachments_Download_Link));
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                    appDep.threadSleep(5000);
                    boolean blnRes = appDep.validateDownloadedFileAndDelete(oBrowser, logActivity.get("FileToUpload"), ".pdf", "Dummy PDF file", "Yes");
                    if (blnRes) {
                        reports.writeResult(oBrowser, "Pass", "The pdf file from 'External Attachment' was downloaded successful");
                    } else {
                        reports.writeResult(oBrowser, "Fail", "Failed to download the pdf file from the 'External Attachment'");
                        Assert.assertTrue(false, "Failed to download the pdf file from the 'External Attachment'");
                    }
                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_Link));
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                }

                //Export the Support Case - Log Activity records
                reports.writeResult(oBrowser, "Screenshot", "Support Case - Log Activity Export functionality");
                appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_LogActivity_Export_Btn);
                appDep.threadSleep(2000);
                if(appInd.isAlertPresent(oBrowser)){
                    oAlert = oBrowser.switchTo().alert();
                    fileName = oAlert.getText();
                    oAlert.accept();
                }
                appDep.threadSleep(5000);
                boolean blnRes = false;
                blnRes = appDep.exportAndCompareTheExportedDataWithTableGridData(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Activities_Grid, fileName);

                if (blnRes) {
                    reports.writeResult(oBrowser, "Pass", "Exported 'Log Activity' excel file cell data and 'Log Activity' grid cell datas are matching");
                    new File("C:/Users/" + System.getProperty("user.name") + "/Downloads/" + fileName + ".xlsx").delete();
                    new File("C:/Users/" + System.getProperty("user.name") + "/Downloads/" + fileName + ".xlsx").deleteOnExit();
                }else{
                    reports.writeResult(oBrowser, "Fail", "Mis-match in Exported 'Log Activity' excel file cell data and 'Log Activity' grid cell data values");
                    Assert.fail("Mis-match in Exported 'Log Activity' excel file cell data and 'Log Activity' grid cell data values");
                }

                //Validate Support CAses-->Show page details
                Assert.assertTrue(validateSupportCasesShowPageDetails(oBrowser, logActivity));

                //Close the 'Support Cases » Show' & go back to 'Support Cases' grid
                oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);

                //If the Support cases status is Hold, then it should be removed from the 'Active' grid and moved to 'Pending' grid
                //If the Support cases status is Closed, then it should be removed from both the 'Active' grid and 'Pending' grid
                if(logActivity.get("CaseOutcome").equalsIgnoreCase("Validation Pending")){
                    //verify the record is removed from the 'Active' grid
                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Tab));
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                    Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//input)[2]"), supportCaseDetails.get("caseNumber")));
                    appDep.threadSleep(2000);
                    Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td)[1]")));
                    reports.writeResult(oBrowser, "Screenshot", "Failed to remove the record (Case #: "+supportCaseDetails.get("caseNumber")+") from the 'Active' grid for Hold status");

                    //verify the record moved to 'Pending' grid
                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Pending_Tab));
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                    Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Pending_Grid + "//input)[2]"), supportCaseDetails.get("caseNumber")));
                    appDep.threadSleep(2000);
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Pending_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td)[1]")));
                    reports.writeResult(oBrowser, "Screenshot", "The record (Case #: "+supportCaseDetails.get("caseNumber")+") was Removed from the 'Pending' grid for Hold status");
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Pending_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td)[1]"), "Text", supportCaseDetails.get("caseNumber")));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Pending_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td)[2]"), "Text", supportCaseDetails.get("paynetID")));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Pending_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td)[8]"), "Text", "Hold"));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Pending_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td)[12]"), "Text", supportCaseDetails.get("originationSource")));
                }else{
                    //verify the record is removed from the 'Active' grid
                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Tab));
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                    Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//input)[2]"), supportCaseDetails.get("caseNumber")));
                    appDep.threadSleep(2000);
                    Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td)[1]")));
                    reports.writeResult(oBrowser, "Screenshot", "Failed to remove the record (Case #: "+supportCaseDetails.get("caseNumber")+") from the 'Active' grid for Closed status");

                    //verify the record removed from the 'Pending' grid
                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Pending_Tab));
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                    Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Pending_Grid + "//input)[2]"), supportCaseDetails.get("caseNumber")));
                    appDep.threadSleep(2000);
                    Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Pending_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td)[1]")));
                    reports.writeResult(oBrowser, "Screenshot", "Failed to remove the record (Case #: "+supportCaseDetails.get("caseNumber")+") from the 'Pending' grid for Closed status");

                    //verify the record present in the 'Closed' grid
                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Closed_Tab));
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                    Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Closed_Grid + "//input)[4]"), supportCaseDetails.get("caseNumber")));
                    appDep.threadSleep(2000);
                    //Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Closed_Grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td)[1]")));
                    reports.writeResult(oBrowser, "Screenshot", "Failed to remove the record (Case #: "+supportCaseDetails.get("caseNumber")+") from the 'Closed' grid for Closed status");

                }
            }

            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "createAndValidateLogActivityForDifferentTypes_SupportCases()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createAndValidateLogActivityForDifferentTypes_SupportCases()' method. " + e);
            return false;
        }finally {
            supportCaseDetails = null; logActivityData = null; logActivity = null; oAlert = null; fileName = null;
        }
    }




    /********************************************************
     * Method Name      : validateSupportCasesShowPageDetails()
     * Purpose          : User validates the SupportCases-->Show dialog details for the Activities tab record
     * Author           : Gudi
     * Parameters       : oBrowser, supportCaseData
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateSupportCasesShowPageDetails(WebDriver oBrowser, Map<String, String> supportCaseData){
        String arrActivityTypeSpecificData[];
        try{
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_Link));
            appDep.waitForTheElementState(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Activities_Grid + "//input)[7]"), "Clickable", "", minTimeOut);
            if (supportCaseData.get("CaseOutcome").equalsIgnoreCase("Validation Closed")) {
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Activities_Grid + "//tr[2][contains(@class, 'lines dx-column-lines')]/td)[" + (7) + "]//a")));
            } else {
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Queues_Show_Link));
            }
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog + "[contains(@style,'block')]//div[@class='modal-body']//h4/span"), "Text", "Activity", minTimeOut);
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog + "[contains(@style,'block')]//h4"), "Text").contains("Activity"));

            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog + "[contains(@style, 'block')]//div[@class='modal-body']//div[@class='small']"), "Text").contains("Created by:"));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog + "[contains(@style, 'block')]//div[@class='modal-body']//div[@class='small']"), "Text").contains(appInd.getPropertyValueByKeyName("qaUserName")));

            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog + "[contains(@style, 'block')]//div[@class='modal-body']//h4)[1]/span"), "Text", "Activity"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog + "[contains(@style, 'block')]//div[@class='modal-body']//h4)[1]/following-sibling::div[1]"), "Text", "Open: " + supportCaseData.get("CaseOutcome")));

            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog + "[contains(@style, 'block')]//div[@class='modal-body']//h4)[2]/span"), "Text", "Activity Type"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog + "[contains(@style, 'block')]//div[@class='modal-body']//h4)[2]/following-sibling::div[1]"), "Text", supportCaseData.get("ActivityType")));

            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog + "[contains(@style, 'block')]//div[@class='modal-body']//h4)[3]/span"), "Text", "Reason"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog + "[contains(@style, 'block')]//div[@class='modal-body']//h4)[3]/following-sibling::div[1]"), "Text", supportCaseData.get("CaseOutcomeReason")));

            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog + "[contains(@style, 'block')]//div[@class='modal-body']//h4)[4]/span"), "Text", "Notes"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog + "[contains(@style, 'block')]//div[@class='modal-body']//h4)[4]/following-sibling::div[1]"), "Text", supportCaseData.get("Notes")));

            arrActivityTypeSpecificData = supportCaseData.get("ActivityTypeSpecificData").split("#", -1);
            switch(supportCaseData.get("ActivityType").toLowerCase()){
                case "email":
                    Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog + "[contains(@style, 'block')]//div[@class='modal-body']//h4)[5]/following-sibling::ul/li[1]"), "Text").contains(arrActivityTypeSpecificData[0]));
                    Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog + "[contains(@style, 'block')]//div[@class='modal-body']//h4)[5]/following-sibling::ul/li[2]"), "Text").contains(arrActivityTypeSpecificData[1]));
                    Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog + "[contains(@style, 'block')]//div[@class='modal-body']//h4)[5]/following-sibling::ul/li[3]"), "Text").contains(arrActivityTypeSpecificData[2]));
                    Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog + "[contains(@style, 'block')]//div[@class='modal-body']//h4)[5]/following-sibling::ul/li[4]"), "Text").contains(arrActivityTypeSpecificData[3]));
                    break;
                case "fax":
                    Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog + "[contains(@style, 'block')]//div[@class='modal-body']//h4)[5]/following-sibling::ul/li[1]"), "Text").contains(arrActivityTypeSpecificData[0]));
                    Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog + "[contains(@style, 'block')]//div[@class='modal-body']//h4)[5]/following-sibling::ul/li[2]"), "Text").contains(arrActivityTypeSpecificData[1]));
                    break;
                case "phone (inbound/outbound)":
                    Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog + "[contains(@style, 'block')]//div[@class='modal-body']//h4)[5]/following-sibling::ul/li[1]"), "Text").contains(arrActivityTypeSpecificData[0]));
                    Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog + "[contains(@style, 'block')]//div[@class='modal-body']//h4)[5]/following-sibling::ul/li[2]"), "Text").contains(arrActivityTypeSpecificData[1]));
                    Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog + "[contains(@style, 'block')]//div[@class='modal-body']//h4)[5]/following-sibling::ul/li[3]"), "Text").contains(arrActivityTypeSpecificData[2]));
                    break;
                default:
                    reports.writeResult(oBrowser, "Fail", "Invalid option '"+supportCaseData.get("ActivityType")+"' was provided for 'Activity Type' dropdown field");
                    Assert.fail("Invalid option '"+supportCaseData.get("ActivityType")+"' was provided for 'Activity Type' dropdown field");
            }
            reports.writeResult(oBrowser, "Screenshot", "Support Cases - Show case --> Dialog");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog + "//button[@class='close']")));
            appDep.threadSleep(2000);
            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "validateSupportCasesShowPageDetails()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateSupportCasesShowPageDetails()' method. " + e);
            return false;
        }finally {arrActivityTypeSpecificData = null;}
    }




    /********************************************************
     * Method Name      : validateSendMailFromDailyGrindCases()
     * Purpose          : User validates the SupportCases-->Show dialog details for the Activities tab record
     * Author           : Gudi
     * Parameters       : oBrowser, supportCaseData
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateSendMailFromDailyGrindCases(WebDriver oBrowser, DataTable dataTable){
        Map<String, String> emailData = null;
        try{
            emailData = appDep.sendEmail_ComposeNewMessageFunctionality(oBrowser, "Enrollment Cses->Send Mail", dataTable);
            Assert.assertTrue(emailData.get("TestPassed").equalsIgnoreCase("True"), "The 'sendEmail_ComposeNewMessageFunctionality()' method was failed");

            //Check user received the email
            Assert.assertTrue(emailUtilities.connectAndOpenEmails(emailData));
            Assert.assertTrue(emailUtilities.readEmailFromSendMail(oEmailBrowser, emailData));
            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "validateSendMailFromDailyGrindCases()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateSendMailFromDailyGrindCases()' method. " + e);
            return false;
        }
    }




    /********************************************************
     * Method Name      : validateGeneratePAFSendEmailFromDailyGrindCases()
     * Purpose          : User validates the SupportCases-->Show dialog details for the Activities tab record
     * Author           : Gudi
     * Parameters       : oBrowser, supportCaseData
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateGeneratePAFSendEmailFromDailyGrindCases(WebDriver oBrowser, DataTable dataTable){
        List<Map<String, String>> sendEmailData = null;
        String caseNumber = null;
        Map<String, String> emailData = null;
        String supplierName = null;
        String contactName = null;
        try{
            sendEmailData = dataTable.asMaps(String.class, String.class);
            emailData = new HashMap<String, String>();
            emailData.put("ContactEmail", sendEmailData.get(0).get("ContactEmail"));
            emailData.put("From", sendEmailData.get(0).get("From"));
            emailData.put("CC", sendEmailData.get(0).get("CC"));
            emailData.put("Subject", sendEmailData.get(0).get("Subject"));
            emailData.put("Message", sendEmailData.get(0).get("Message"));
            emailData.put("FileToUpload", sendEmailData.get(0).get("FileToUpload"));
            emailData.put("OfferAccepted", sendEmailData.get(0).get("OfferAccepted"));

            appDep.waitForTheElementState(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_DailyGrind_Cases_grid + "//input)[10]"), "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_DailyGrind_Cases_grid + "//input)[10]"), "ACH"));
            appDep.threadSleep(2000);
            caseNumber = appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_DailyGrind_Cases_grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td)[1]"), "Text");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_DailyGrind_Cases_grid + "//tr[1][contains(@class, 'lines dx-column-lines')]/td)[1]")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_WaveAndOffers_Header, "Text", "Wave & Offers", waitTimeOut);

            appInd.scrollToElement(oBrowser,PayCRM_Modules_DailyGrindUIPage.obj_SplitterHorizonal_Line);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ClientCompany_Contacts_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);

            //Click Contacts >> Edit Primary >> change to your email
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_Contacts_Grid + "//input)[9]"), "Primary"));
            appDep.threadSleep(2000);
            boolean blnRes = appInd.verifyOptionalElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Contacts_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[1]"));
            if(!blnRes){
                Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_Contacts_Grid + "//input)[9]"), ""));
                appDep.threadSleep(2000);
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Contacts_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[1]")), "Failed to find any records in the 'Contacts' tab");
            }

            reports.writeResult(oBrowser, "Screenshot", "Before editing the Contacts detail");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Contacts_Grid + "//a[@title='Edit']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_NewContact_Create_Btn, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_EditContact_Header, "Text", "Edit Contact"));
            if(!blnRes) Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_NewContact_ContactType_List, "Primary"));
            contactName = "Name " + appInd.getDateTime("Shhmmss");
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_NewContact_Name_Edit, contactName));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_NewContact_Email_Edit, emailData.get("ContactEmail")));

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_NewContact_Create_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appInd.clearAndSetObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_Contacts_Grid +"//input)[1]"), contactName);
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "After editing the Contacts detail");
            appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_Contacts_Grid + "//tr[contains(@class, 'lines dx-column-lines')])[1]/td[1]"), "Text", contactName);
            appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_Contacts_Grid + "//tr[contains(@class, 'lines dx-column-lines')])[1]/td[2]"), "Text", emailData.get("ContactEmail"));
            appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_Contacts_Grid + "//tr[contains(@class, 'lines dx-column-lines')])[1]/td[10]"), "Text", "Primary");


            //Click on generate PAF button & update the contact email to your address of choice
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_ManageUIPage.obj_GeneratePAF_Btn), "Failed to find the 'Generate PAF' button");
            reports.writeResult(oBrowser, "Screenshot", "'Enrollment Cases' Page has opened");
            supplierName = appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_ManageUIPage.obj_SupplierInformation_Section + "//a[@class='panel-link'])[1]"), "Text");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_ManageUIPage.obj_GeneratePAF_Btn));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_PreviewPAFDoc_Dialog + "//button[@id='btn_paf_edit_submit']"), "Clickable", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "'Review PAF Document' Page has opened after clicking 'Generate PAF' button");
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_ManageUIPage.obj_PreviewPAFDoc_Dialog + "//h4"), "Text").contains("Review PAF Document » " + supplierName));
            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_ManageUIPage.obj_SupplierName_Edit, "Value", supplierName));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_PreviewPAFDoc_Dialog + "[contains(@style,'block')]//input[@id='contact_email']"), emailData.get("ContactEmail")));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+PayCRM_Modules_ManageUIPage.obj_PreviewPAFDoc_Dialog + "//button[@id='btn_paf_edit_submit'])[1]")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);

            emailData = payCRM_Modules_ManageUIBaseStep.sendMail_GeneratePAF(oBrowser, caseNumber, emailData);
            Assert.assertTrue(emailData.get("TestPassed").equalsIgnoreCase("True"), "The 'sendMail_GeneratePAF()' method was failed.");

            //Check user received the PAF email
            Assert.assertTrue(emailUtilities.connectAndOpenEmails(emailData));
            Assert.assertTrue(emailUtilities.readEmailFromPAFSendMail(oEmailBrowser, emailData));

            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "validateGeneratePAFSendEmailFromDailyGrindCases()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateGeneratePAFSendEmailFromDailyGrindCases()' method. " + e);
            return false;
        }finally{caseNumber = null; emailData = null; supplierName = null; contactName = null;}
    }




    /********************************************************
     * Method Name      : validateLoginToPayNETFromSupportCaseShowPage()
     * Purpose          : User validates that PayNET login can be successful from SupportCases-->Show page-->Supplier Information
     * Author           : Gudi
     * Parameters       : oBrowser, supportCaseData
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateLoginToPayNETFromSupportCaseShowPage(WebDriver oBrowser){
        String supplierName = null;
        try{
            Assert.assertNotNull(appDep.searchAndOpenTheSupportCase(oBrowser, new String[]{"", "", "Automatic"}));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_LoginToPayNet_Link));

            supplierName = appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupplierInformation_Section + "//div[text()='Name']/following-sibling::div)[1]"), "Text");

            //On Supplier tile, locate the globe icon next to Supplier Name and click the icon.
//            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_LoginToPayNet_Link));
//
//            //PayNet URL will open in new tab. Login to PayNet
//            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
//            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_PayNet_LoginPage_Label, "Clickable", "", waitTimeOut);
//            Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_PayNet_LoginPage_Label, "Text", "If you already have a Corcentric Profile, you can sign-in here."));
//            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_PayNet_UserName_Edit, appInd.getPropertyValueByKeyName("payNetUserName")));
//            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_PayNet_Password_Edit, appInd.getPropertyValueByKeyName("payNetPassword")));
//            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_PayNet_SignIn_Btn));
//            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_PaymentsAndRemittance_Label, "Text", "Payments and Remittance", waitTimeOut);
//            Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_PayNet_CompanyName_Label, "Text").contains(supplierName));
//
//            //Close the 'Validation Profile Data' dialog if Exist
//            if(appInd.verifyOptionalElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_PayNet_ValidateProfileData_Close_Btn)){
//                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_PayNet_ValidateProfileData_Close_Btn));
//            }
//            Assert.assertTrue(appInd.javaScriptClickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_PayNet_Signout_Link));
//            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "validateLoginToPayNETFromSupportCaseShowPage()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateLoginToPayNETFromSupportCaseShowPage()' method. " + e);
            return false;
        }finally{supplierName = null;}
    }




    /********************************************************
     * Method Name      : addEditInternalAttachment()
     * Purpose          : User validates the functionality of Add OR Edit Support Case Internal attachment & download attachment from show dialog
     * Author           : Gudi
     * Parameters       : oBrowser, action, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public Map<String, String> addEditInternalAttachment(WebDriver oBrowser, String action, DataTable dataTable){
        List<Map<String, String>> internalAttachmentData = null;
        Map<String, String> internalAttachment = null;
        String timeStamp = null;
        try{
            timeStamp = appInd.getDateTime("Shhmmss");
            internalAttachmentData = dataTable.asMaps(String.class, String.class);
            internalAttachment = new HashMap<String, String>();
            internalAttachment.put("Name", internalAttachmentData.get(0).get("Name") + timeStamp);
            internalAttachment.put("Description", internalAttachmentData.get(0).get("Description"));
            internalAttachment.put("AttachmentType", internalAttachmentData.get(0).get("AttachmentType"));
            internalAttachment.put("FileToUpload", internalAttachmentData.get(0).get("FileToUpload"));

            if(action.equalsIgnoreCase("Add"))
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Contacts_Add_Link));
            else
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_InternalAttachment_Grid + "//a[@title='Edit']")));

            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_NewInternalAttachment_Dialog + "//button[@id='support_case_attachment_submit']"), "Clickable", "", waitTimeOut);

            if(action.equalsIgnoreCase("Add"))
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_NewInternalAttachment_Dialog + "//h4"), "Text", "New Attachment"));
            else
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_NewInternalAttachment_Dialog + "//h4"), "Text", "Edit Attachment"));

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_InternalAttachment_Name_Edit, internalAttachment.get("Name")));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_InternalAttachment_Description_Textarea, internalAttachment.get("Description")));
            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_InternalAttachment_AttachmentType_List, internalAttachment.get("AttachmentType")));
            if(action.equalsIgnoreCase("Add"))
                Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_InternalAttachment_File_Edit, System.getProperty("user.dir").replace("\\", "/") + "/TestData/" + internalAttachment.get("FileToUpload")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManagedPaymentsUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appDep.threadSleep(2000);
            if(action.equalsIgnoreCase("Add"))
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_InternalAttachment_File_Edit, "Value").contains(internalAttachment.get("FileToUpload")));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_NewInternalAttachment_Dialog + "//button[@id='support_case_attachment_submit']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);

            if(action.equalsIgnoreCase("Add"))
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_CompanyEditConfirmationMessage_Label, "Text").contains("Internal Attachment has been successfully created."));
            else
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_CompanyEditConfirmationMessage_Label, "Text").contains("Internal Attachment has been successfully updated."));

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_InternalAttachment_Grid + "//input)[1]"), internalAttachment.get("Name")));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_InternalAttachment_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[1]"), "Text", internalAttachment.get("Name")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_InternalAttachment_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[2]"), "Text", internalAttachment.get("Description")));
            internalAttachment.put("TestPassed", "True");
            return internalAttachment;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'addEditInternalAttachment()' method. " + e);
            internalAttachment.put("TestPassed", "False");
            return internalAttachment;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'addEditInternalAttachment()' method. " + e);
            internalAttachment.put("TestPassed", "False");
            return internalAttachment;
        }finally {internalAttachmentData = null; internalAttachment = null; timeStamp = null;}
    }






    /********************************************************
     * Method Name      : validateSupportCaseInternalAttachment()
     * Purpose          : User validates the functionality of Support Case Internal attachment
     * Author           : Gudi
     * Parameters       : oBrowser, action, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateSupportCaseInternalAttachment(WebDriver oBrowser, String action, DataTable dataTable){
        Map<String, String> attachmentData = null;
        try{
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_InternalAttachment_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);

            //Add OR Edit the attachment
            attachmentData = addEditInternalAttachment(oBrowser, action, dataTable);

            //Click on Show link and download the attachment
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_InternalAttachment_Grid + "//a[@title='Show']")));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_InternalAttachment_Show_Dialog + "//a"), "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_InternalAttachment_Show_Dialog + "//h4"), "Text", "Internal Attachment"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_InternalAttachment_Show_Dialog + "//dt[text()='Name']/following-sibling::dd[1]"), "Text", attachmentData.get("Name")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_InternalAttachment_Show_Dialog + "//dt[text()='Description']/following-sibling::dd[1]"), "Text", attachmentData.get("Description")));
            if(action.equalsIgnoreCase("Add"))
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_InternalAttachment_Show_Dialog + "//dt[text()='File Name']/following-sibling::dd[1]"), "Text").contains(attachmentData.get("FileToUpload")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_InternalAttachment_Show_Dialog + "//dt[text()='Attachment Type']/following-sibling::dd[1]"), "Text", attachmentData.get("AttachmentType")));

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_InternalAttachment_Show_Dialog + "//dt[text()='File Name']/following-sibling::dd[1]/a")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appDep.threadSleep(5000);
            boolean blnRes = appDep.validateDownloadedFileAndDelete(oBrowser, "", "."+(attachmentData.get("FileToUpload").split("\\."))[1], "Dummy PDF file", "Yes");
            if (blnRes) {
                reports.writeResult(oBrowser, "Pass", "The internal attachment '"+attachmentData.get("FileToUpload")+"' was downloaded successful");
            } else {
                reports.writeResult(oBrowser, "Fail", "Failed to download the file from the internal attachment '"+attachmentData.get("FileToUpload")+"'");
                Assert.assertTrue(false, "Failed to download the file from the internal attachment '"+attachmentData.get("FileToUpload")+"'");
            }
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_InternalAttachment_Show_Dialog + "//button[@class='close']")));
            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "validateSupportCaseInternalAttachment()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateSupportCaseInternalAttachment()' method. " + e);
            return false;
        }finally {attachmentData = null;}
    }



    /********************************************************
     * Method Name      : userPerformValidationCompleteSupportCase()
     * Purpose          : User validates the functionality of Validation Complete Support Case
     * Author           : Deepak
     * Parameters       : oBrowser, originationSource, caseType, areaName, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean userPerformValidationCompleteSupportCase(WebDriver oBrowser, String originationSource, String caseType, String areaName, DataTable dataTable){
        Map<String, String> logActivity = new HashMap<>();
        Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "Support Cases"));
        appDep.threadSleep(1000);
        String tab = caseType.equalsIgnoreCase("paf") || caseType.equalsIgnoreCase("account validation")?"Pending":"Active";
        String path, gridInputBox = "";
        if(tab.equalsIgnoreCase("active")){
            path = PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid;
            gridInputBox = PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_ActiveTabColumnSearch;
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Tab));
        }
        else{
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Pending_Tab));
            path = PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Pending_Grid;
            gridInputBox = PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_PendingTabColumnSearch;
        }
        try{
            appDep.threadSleep(1000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appInd.clearAndSetObject(oBrowser, By.xpath(String.format(gridInputBox, "Origination Source")), originationSource);
            appInd.clearAndSetObject(oBrowser, By.xpath(String.format(gridInputBox, "Case Type")), caseType);
            appInd.clearAndSetObject(oBrowser, By.xpath(String.format(gridInputBox, "Area Name")), areaName);
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            reports.writeResult(oBrowser, "screenshot", "Support Case : Columns are filter as per required data");
            appInd.clickObject(oBrowser, By.xpath("(("+path+"//table)[2]//tr)[1]/td[1]"));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            reports.writeResult(oBrowser, "screenshot", "Support Case detail page is open");
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_LogActivity_Btn), "visibility", "", minTimeOut);
            String newCaseID = appInd.getTextOnElement(oBrowser, By.xpath("//div[@id='section_case_information']//h3"), "text").split(" ")[1];
            logActivity.put("payNetSupplierID",appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_SupplierInfoTile, "2", "PayNet ID", "2")), "text"));
            logActivity.put("supplierName",appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_SupplierInfoTile, "1", "Name", "1")), "text"));
            logActivity.put("TIN",appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_SupplierInfoTile, "4", "TIN", "4")), "text"));
            logActivity.put("ValidationCaseStatus", "Pending");
            logActivity.put("ValidationType", caseTypeMapping(caseType));
            logActivity.put("AreaName", areaName.trim());
            reports.writeResult(oBrowser, "screenshot", "Support Case Detail Page is open and collecting required information.");
            oBrowser.switchTo().newWindow(WindowType.TAB);
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            oBrowser.navigate().to(appInd.getPropertyValueByKeyName("qaURL")+"/stopfraud");
            Assert.assertTrue(appDep.searchTheSupplierBasedOnPayNetIDFromStopFraud(oBrowser, logActivity));
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
            logActivity.putAll(dataTable.asMaps(String.class, String.class).get(0));
            //------------------ validate "Case Info" & "Supplier Info" --------------------------
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_LogActivity_Btn)));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_LogActivity_Notes_Textarea, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//div[@id='support_form_case_information']//p[text()='"+areaName+"']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//div[@id='support_form_case_information']//p[text()=' "+caseType+"']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//div[@id='support_form_supplier_information']//p[text()='"+logActivity.get("supplierName")+"']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//div[@id='support_form_supplier_information']//p[text()=' "+logActivity.get("payNetSupplierID")+"']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//div[@id='support_form_supplier_information']//p[text()=' "+logActivity.get("TIN")+"']")));
            enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
            //------------------------------------ LOG ACTIVITY ---------------------------------
            Map<String, String> actualData = appDep.supportCaseLogActivity(oBrowser, newCaseID, logActivity);
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
            Assert.assertTrue(actualData.get("TestPassed").equals("True"));
            logActivity.put("ValidationCaseStatus", "Complete");
            Assert.assertTrue(appDep.searchTheSupplierBasedOnPayNetIDFromStopFraud(oBrowser, logActivity));
            logActivity.clear();
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "userPerformValidationCompleteSupportCase()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'userPerformValidationCompleteSupportCase()' method. " + e);
            return false;
        }
        return true;
    }


    private String caseTypeMapping(String caseType){
        switch (caseType){
            case "Account Validation":
                return "TGBR Validation";
            case "PAF":
                return "Profile Authorization Form";
            case "OFAC Validation":
                return "OFAC Scan";
            case "TIN Validation":
                return "TIN Matching";
            default:
                return caseType.trim();
        }
    }

    /********************************************************
     * Method Name      : userPerformUnableToValidateSupportCaseScenario()
     * Purpose          : User validates the functionality of Support Case Internal attachment
     * Author           : Gudi
     * Parameters       : oBrowser, originationSource, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean userPerformUnableToValidateSupportCaseScenario(WebDriver oBrowser, String originationSource, DataTable dataTable){
        Map<String, String> supportCaseDetails = null;
        List<Map<String, String>> logActivityData = null;
        Map<String, String> logActivity = null;
        try{
            logActivityData = dataTable.asMaps(String.class, String.class);

            if(originationSource.equalsIgnoreCase("Automatic"))
                supportCaseDetails = appDep.searchAndOpenTheSupportCase(oBrowser, new String[]{"Company Information", "Address Validation", "Automatic"});
            else{
                supportCaseDetails = appDep.searchAndOpenTheSupportCase(oBrowser, new String[]{"", "", "Manual"});
                //------------------ validate "Case Info" & "Supplier Info" --------------------------
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_LogActivity_Btn)));
                oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_LogActivity_Notes_Textarea, "Clickable", "", waitTimeOut);
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//div[@id='support_form_case_information']//p[text()='"+supportCaseDetails.get("areaName")+"']")));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//div[@id='support_form_case_information']//p[text()=' "+supportCaseDetails.get("caseType")+"']")));
                if(oBrowser.findElements(By.xpath("//p[text()='Name:']")).size() > 1){
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//div[@id='support_form_supplier_information']//p[text()='"+(supportCaseDetails.get("supplierName").split("\\,"))[0].trim()+"']")));
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//div[@id='support_form_supplier_information']//p[text()='"+(supportCaseDetails.get("supplierName").split("\\,"))[1].trim()+"']")));
                }

                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//div[@id='support_form_supplier_information']//p[text()=' "+supportCaseDetails.get("paynetID")+"']")));
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//div[@id='support_form_supplier_information']//p[text()=' "+supportCaseDetails.get("TIN")+"']")));
                enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
            }

            logActivity = new HashMap<>();
            logActivity.put("SupportCaseType", logActivityData.get(0).get("SupportCaseType"));
            logActivity.put("UnableToValidate", logActivityData.get(0).get("UnableToValidate"));
            logActivity.put("ValidateCompleted", logActivityData.get(0).get("ValidateCompleted"));
            logActivity.put("ActivityType", logActivityData.get(0).get("ActivityType"));
            logActivity.put("ActivityTypeSpecificData", logActivityData.get(0).get("ActivityTypeSpecificData"));
            logActivity.put("CaseOutcome", logActivityData.get(0).get("CaseOutcome"));
            logActivity.put("CaseOutcomeReason", logActivityData.get(0).get("CaseOutcomeReason"));
            logActivity.put("FileToUpload", logActivityData.get(0).get("FileToUpload"));
            logActivity.put("Notes", logActivityData.get(0).get("Notes"));

            //user perform 'Log Activity' for the selected Support Case
            logActivity = appDep.supportCaseLogActivity(oBrowser, supportCaseDetails.get("caseNumber"), logActivity);
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);


            //Validate the attachments under 'External Attachments' section
            if(logActivity.get("SupportCaseType").equalsIgnoreCase("Automatic") && !logActivity.get("FileToUpload").isEmpty()){
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ExternalAttachments_Link));
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_ExternalAttachments_Grid + "//input)[3]"), logActivity.get("FileToUpload")));
                appDep.threadSleep(2000);
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ExternalAttachments_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[2]"), "Text", logActivity.get("FileToUpload")));
            }


            Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "StopFraud"));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_StopFraud_ValidationLookup_link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SearchByPayNetId_Edit, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SearchByPayNetId_Edit, supportCaseDetails.get("paynetID")));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Filter_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "'StopFraud' validation ookup page with payNet Details");
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Grid + "//td[1]"), "Text", supportCaseDetails.get("paynetID")));
            String supplierName = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Grid + "//td[2]"), "Text");
            Assert.assertTrue(supportCaseDetails.get("supplierName").contains(supplierName), "The invalid supplier name was found: " + supportCaseDetails.get("supplierName"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Grid + "//td[3]"), "Text", supportCaseDetails.get("TIN")));

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Grid + "//td[1]")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", maxWaitTimeOut);


            if(originationSource.equalsIgnoreCase("Automatic")){
                switch(supportCaseDetails.get("areaName").toLowerCase()){
                    case "payment information":
                        Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_PaymentTab_Link));
                        break;
                    case "company information":
                        Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Company_Tab));
                        break;
                    case "contacts":
                        Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Contacts_Link));
                        break;
                    default:
                        reports.writeResult(oBrowser, "Fail", "Invalid Area Name '"+supportCaseDetails.get("areaName")+"' for the selected Support Case");
                        Assert.fail("Invalid Area Name '"+supportCaseDetails.get("areaName")+"' for the selected Support Case");
                }
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);

                if(supportCaseDetails.get("caseType").equalsIgnoreCase("Address Validation")){
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Company_OuterGrid + "//table[contains(@id,'DataTables_Table_')]//td[text()='Address Validation']"), "Text", "Address Validation"));
                    Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Company_OuterGrid + "//td[text()='Address Validation']/following-sibling::td[1]"), "Text").contains(appInd.formatDate(appInd.getDateTimeByTimezone("dd-MM-yyyy", "CST"), "dd-MM-yyyy", "MM/dd/yyyy")));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Company_OuterGrid + "//td[text()='Address Validation']/following-sibling::td[3]"), "Text", supportCaseDetails.get("areaName")));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Company_OuterGrid + "//td[text()='Address Validation']/following-sibling::td[4]"), "Text", "unabletovalidate@vendorin.com"));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Company_OuterGrid + "//table[contains(@id,'DataTables_Table_')]//td[text()='Address Validation']/following-sibling::td[5]/span"), "Text", "Unable to validate"));
                }
                reports.writeResult(oBrowser, "Screenshot", "'Address Validation' with 'Unable to validate' status");
            }else{
                Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_AuditInfo_Link));
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                //Assert.assertTrue(!appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_AuditInfo_Grid + "//tr[contains(@class, 'lines dx-column-lines')][1]/td[3]"), "Text").contains("Unable to Validate"), "'Unable to Validate' For Manual support cases, verify there is no record for 'unable to validate' under 'Audit Info' grid");
                reports.writeResult(oBrowser, "Screenshot", "Manual Support case with Unable to validate case scenario");
            }

            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "userPerformUnableToValidateSupportCaseScenario()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'userPerformUnableToValidateSupportCaseScenario()' method. " + e);
            return false;
        }finally {supportCaseDetails = null; logActivityData = null; logActivity = null;}
    }




    /********************************************************
     * Method Name      : validateReportsForSupportCase()
     * Purpose          : User validates the functionality of Support Case Internal attachment
     * Author           : Gudi
     * Parameters       : oBrowser, originationSource, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateReportsForSupportCase(WebDriver oBrowser){
        String startDate = null;
        String endDate = null;
        String arrColumnNames[];
        Alert oAlert = null;
        String fileName = null;
        Select oSelect;
        String caseNumber, payNetSupplierID, supplierName, caseType, status, priority = null;
        try{
            Assert.assertTrue(payCRM_Modules_ManageUIBaseStep.validateSupportCasesReportFilters(oBrowser));
            appDep.threadSleep(1000);
            oSelect = new Select(oBrowser.findElement(PayCRM_Modules_DailyGrindUIPage.obj_supportCaseActivityReportCaseStatusesDropdown));
            oSelect.selectByValue("Closed");
            appDep.threadSleep(1000);
            //Click on export button & validate the data
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCasesReport_Export_Btn));
            appDep.threadSleep(2000);
            oAlert = oBrowser.switchTo().alert();
            fileName = oAlert.getText();
            oAlert.accept();
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appDep.threadSleep(5000);

            boolean blnRes = appDep.validateDownloadedFileAndDelete(oBrowser, fileName+".xlsx", ".xlsx", "", "Yes");
            if (blnRes) {
                reports.writeResult(oBrowser, "Pass", "The 'Support Cases Report' excel file was downloaded successful");
            } else {
                reports.writeResult(oBrowser, "Fail", "Failed to download the 'Support Cases Report' excel file");
                Assert.assertTrue(false, "Failed to download the 'Support Cases Report' excel file");
            }

            //Click on any case and validate the case opened in new tab with all the details
            caseNumber = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCasesReport_Grid + "//tr[contains(@class, 'lines dx-column-lines')]//td[1]"), "Text");
            payNetSupplierID = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCasesReport_Grid + "//tr[contains(@class, 'lines dx-column-lines')]//td[2]"), "Text");
            supplierName = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCasesReport_Grid + "//tr[contains(@class, 'lines dx-column-lines')]//td[3]"), "Text");
            caseType = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCasesReport_Grid + "//tr[contains(@class, 'lines dx-column-lines')]//td[5]"), "Text");
            status = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCasesReport_Grid + "//tr[contains(@class, 'lines dx-column-lines')]//td[7]"), "Text");
            priority = appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCasesReport_Grid + "//tr[contains(@class, 'lines dx-column-lines')]//td[8]"), "Text");

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCasesReport_Grid + "//tr[contains(@class, 'lines dx-column-lines')]//td[1]")));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_Link, "Clickable", "", minTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "'» Support Cases » Show' page");

            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Cases_Section + "//h3"), "Text", "Case " + caseNumber));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Cases_Section + "//dt[text()='Case Type']/following-sibling::dd[1]"), "Text", caseType));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Cases_Section + "//dt[text()='Priority']/following-sibling::dd[1]"), "Text", priority));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupplierInformation_Section + "//div[text()='Name']/following-sibling::div[1]"), "Text", supplierName));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupplierInformation_Section + "//div[text()='PayNet ID']/following-sibling::div[1]"), "Text", payNetSupplierID));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Cases_Section + "/div/div/div)[1]"), "Text", status));
            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "validateReportsForSupportCase()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateReportsForSupportCase()' method. " + e);
            return false;
        }finally {startDate = null; endDate = null; arrColumnNames = null; oAlert = null; fileName = null; caseNumber = null; payNetSupplierID = null; supplierName = null; caseType = null; status = null; priority = null; oSelect = null;}
    }




    /********************************************************
     * Method Name      : validateSupportCaseReassignFunctionality()
     * Purpose          : User validates the Reassign functionality of Support Case
     * Author           : Gudi
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateSupportCaseReassignFunctionality(WebDriver oBrowser){
        String status = null;
        String assignedUser = null;
        try{
            status = appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Cases_Section + "/div/div/div)[1]"), "Text");
            if(status.equalsIgnoreCase("New") || status.equalsIgnoreCase("Open") || status.equalsIgnoreCase("Hold")){
                Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Reassign_Btn), "The 'ReAssign' button doesn't exist");
            }

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Reassign_Btn));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Reassign_Dialog + "[not(@style='display: none;')]//button[@id='btn_submit_reassign']"), "Clickable", "", minTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Reassign_Dialog + "//h4"), "Text", "Reassign"));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Reassign_Dialog + "//button[@class='close']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_AssignToUser_List));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Reassign_Dialog + "[not(@style='display: none;')]//button[@id='btn_submit_reassign']")));

            //If user click on (x) icon of Reassign model then model should be close
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Reassign_Dialog + "//button[@class='close']")));
            appDep.threadSleep(1000);
            Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Reassign_Dialog + "[not(@style='display: none;')]//button[@id='btn_submit_reassign']")));

            //user select any user from dropdown and click on submit button
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Reassign_Btn));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Reassign_Dialog + "[not(@style='display: none;')]//button[@id='btn_submit_reassign']"), "Clickable", "", minTimeOut);

            Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_AssignToUser_List, 2));
            assignedUser = appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_AssignToUser_List, "Dropdown");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Reassign_Dialog + "[not(@style='display: none;')]//button[@id='btn_submit_reassign']")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Cases_Section + "//dt[text()='Assigned to']/following-sibling::dd[1]"), "Text", assignedUser));

            //Click on Activities tab & verify Reassign user activity log.
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_Link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_ManageUIPage.obj_search_Loading, "Invisibility", "", minTimeOut);
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Activities_Grid + "//input)[3]"), "Reassigned"));
            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Activities_Grid + "//input)[7]"), appInd.getPropertyValueByKeyName("qaUserName")+" reassigned case to " + assignedUser));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[1]"), "Text", "Open: Validation Pending"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[2]"), "Text", "Case Update"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[3]"), "Text", "Reassigned"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[4]"), "Text", appInd.getPropertyValueByKeyName("qaUserName")));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[6]"), "Text", appInd.getPropertyValueByKeyName("qaUserName")+" reassigned case to " + assignedUser));

            //Click on activity log record & model should be open. verify details
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Activities_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td[1]")));
            appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog + "[contains(@style,'display: block;')]//h4/span[text()='Activity']"), "Text", "Activity", minTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog + "[contains(@style,'display: block;')]//h4/following-sibling::div)[1]"), "Text", "Open: Validation Pending"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog + "[contains(@style,'display: block;')]//h4/following-sibling::div)[2]"), "Text", "Case Update"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog + "[contains(@style,'display: block;')]//h4/following-sibling::div)[3]"), "Text", "Reassigned"));
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog + "[contains(@style,'display: block;')]//h4/following-sibling::div)[4]"), "Text", appInd.getPropertyValueByKeyName("qaUserName")+" reassigned case to " + assignedUser));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_CaseModal_Dialog + "//button")));
            appDep.threadSleep(2000);
            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "validateSupportCaseReassignFunctionality()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateSupportCaseReassignFunctionality()' method. " + e);
            return false;
        }finally {status = null; assignedUser = null;}
    }




    /********************************************************
     * Method Name      : validateSupportCasesAreMarkedUnableToValidateFromStopFraud()
     * Purpose          : User validates the support cases which are marked as Unable to validate from the StopFraud
     * Author           : Gudi
     * Parameters       : oBrowser, validationType, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateSupportCasesAreMarkedUnableToValidateFromStopFraud(WebDriver oBrowser, String caseOutcomeReason, DataTable dataTable){
        Map<String, String> supplierData = null;
        List<Map<String, String>> supportCaseValidation = null;
        String timeStamp = null;
        try{
            supportCaseValidation = dataTable.asMaps(String.class, String.class);

            //Filter Action Type to TIN Matching & Select a case which has Supplier Link Status as - Approved
            supplierData = appDep.getTheTINMatchingDetailsByQuery(oBrowser, "StopFraudTINValidation", "TIN Matching#OFAC Scan#Address Validation");
            Assert.assertTrue(supplierData.get("TestPassed").equalsIgnoreCase("True"), "The 'getTheTINMatchingDetailsByQuery()' method was failed");

            for(int i=0; i<supportCaseValidation.size(); i++){
                timeStamp = appInd.getDateTime("Shhmmss");
                supplierData.put("ValidationType", supportCaseValidation.get(i).get("ValidationType"));
                supplierData.put("ValidationMode", supportCaseValidation.get(i).get("ValidationMode"));
                supplierData.put("Notes", supportCaseValidation.get(i).get("Notes") + timeStamp);
                supplierData.put("FileToUpload", supportCaseValidation.get(i).get("FileToUpload"));
                supplierData.put("ValidationCaseStatus", "Pending");

                //Copy Paynet id
                //Navigate to Support cases
                //Paste Paynet id in Paynet Supplier id filed and verify if any case is already present in Active/Pending tab for TIN Validation
                if(i==0){
                    Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "Support Cases"));
                    Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//input)[3]"), supplierData.get("payNetSupplierID")));
                    appDep.threadSleep(2000);
                    Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td)[1]")), "The 'PayNet ID' '"+supplierData.get("payNetSupplierID")+"' already exist in the Support Cases");

                    //Pending grid
                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Pending_Tab));
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", minTimeOut);
                    Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Pending_Grid + "//input)[3]"), supplierData.get("payNetSupplierID")));
                    appDep.threadSleep(2000);
                    Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Pending_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td)[1]")), "The 'PayNet ID' '"+supplierData.get("payNetSupplierID")+"' already exist in the Support Cases");
                }


                //Go back to stop fraud and perform unable to validate button for selected Validation type
                Assert.assertTrue(appDep.searchTheSupplierBasedOnPayNetIDFromStopFraud(oBrowser, supplierData));
                if(caseOutcomeReason.equalsIgnoreCase("Unable to Validate"))
                    Assert.assertTrue(appDep.performUnableToValidateForValidationTypes(oBrowser, supplierData));
                else if(caseOutcomeReason.equalsIgnoreCase("Validate")) Assert.assertTrue(appDep.performValidateForValidationTypes(oBrowser, caseOutcomeReason, supplierData));


                //After Unable to validate action, go to dev-->Support Case Polling-->Run API Poll
                //The new case should be created with the  suppier and paynet id which is used in the 'Unable to validate'
                supplierData = appDep.runAPIPollForTheSupportCase(oBrowser, supplierData);
                Assert.assertTrue(supplierData.get("TestPassed").equalsIgnoreCase("True"), "The 'runAPIPollForTheSupportCase()' method was failed");


                //Copy Paynet id
                //Navigate to Support cases
                //Paste Paynet id in Paynet Supplier id filed and verify if any case is created in Active tab for TIN Validation
                if(supplierData.get("ValidationType").equalsIgnoreCase("TIN Matching")) supplierData.put("ValidationType", "TIN Validation");
                if(supplierData.get("ValidationType").equalsIgnoreCase("OFAC Scan")) supplierData.put("ValidationType", "OFAC Validation");
                if(supplierData.get("ValidationType").equalsIgnoreCase("Address Validation")) supplierData.put("ValidationType", "Address Validation");

                if(caseOutcomeReason.equalsIgnoreCase("Unable to Validate")){
                    Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "Support Cases"));
                    Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//input)[2]"), supplierData.get("supportCaseID")));
                    Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//input)[3]"), supplierData.get("payNetSupplierID")));
                    Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//input)[7]"), supplierData.get("ValidationType")));
                    Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//input)[11]"), "Automatic Support Case"));
                    appDep.threadSleep(2000);
                    Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td)[1]")), "The 'PayNet ID' '"+supplierData.get("payNetSupplierID")+"' doesn't exist in the Support Cases");
                    Assert.assertTrue(oBrowser.findElements(By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td)[1]")).size()==1, "The number of records for '"+supplierData.get("payNetSupplierID")+"' & case Type '"+supplierData.get("ValidationType")+"' should not be more than ONE");

                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td)[1]"), "Text", supplierData.get("supportCaseID")));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td)[2]"), "Text", supplierData.get("payNetSupplierID")));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td)[6]"), "Text", supplierData.get("ValidationType")));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td)[10]"), "Text", "Automatic Support Case"));

                    //Open the case and validate
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td)[1]")));
                    oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_Link, "Clickable", "", minTimeOut);

                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Cases_Section +"/div/div/div)[1]"), "Text", "Open"));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Cases_Section +"//dt[text()='Created']/following-sibling::dd)[1]"), "Text", appInd.getDateTimeByTimezone("MM/dd/yyyy", "CST")));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Cases_Section +"//dt[text()='Case Type']/following-sibling::dd)[1]"), "Text", supplierData.get("ValidationType")));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Cases_Section +"//dt[text()='Priority']/following-sibling::dd)[1]"), "Text", "High"));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupplierInformation_Section +"//div[text()='Name']/following-sibling::div)[1]"), "Text", supplierData.get("supplierName")));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupplierInformation_Section +"//div[text()='PayNet ID']/following-sibling::div)[1]"), "Text", supplierData.get("payNetSupplierID")));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupplierInformation_Section +"//div[text()='TIN']/following-sibling::div)[1]"), "Text", supplierData.get("TIN")));
                }else if(caseOutcomeReason.equalsIgnoreCase("Validate")){
                    Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "Support Cases"));
                    Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//input)[2]"), supplierData.get("supportCaseID")));
                    Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//input)[3]"), supplierData.get("payNetSupplierID")));
                    Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//input)[7]"), supplierData.get("ValidationType")));
                    Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//input)[11]"), "Automatic Support Case"));
                    appDep.threadSleep(2000);
                    Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td)[1]")), "The 'PayNet ID' '"+supplierData.get("payNetSupplierID")+"' was still exist in the Support Cases Active grid");

                    //Validate in 'Pending' grid
                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Pending_Tab));
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", minTimeOut);
                    Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Pending_Grid + "//input)[2]"), supplierData.get("supportCaseID")));
                    Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Pending_Grid + "//input)[3]"), supplierData.get("payNetSupplierID")));
                    Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Pending_Grid + "//input)[7]"), supplierData.get("ValidationType")));
                    Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Pending_Grid + "//input)[11]"), "Automatic Support Case"));
                    appDep.threadSleep(2000);
                    Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Pending_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td)[1]")), "The 'PayNet ID' '"+supplierData.get("payNetSupplierID")+"' already exist in the Support Cases");


                    //The closed support case should exit in the Support Cases Report
                    Assert.assertTrue(payCRM_Modules_ManageUIBaseStep.validateSupportCasesReportFilters(oBrowser));
                    Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCasesReport_Grid + "//input)[2]"), supplierData.get("supportCaseID")));
                    Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCasesReport_Grid + "//input)[3]"), supplierData.get("payNetSupplierID")));
                    Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCasesReport_Grid + "//input)[4]"), supplierData.get("supplierName")));
                    Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCasesReport_Grid + "//input)[6]"), supplierData.get("ValidationType")));
                    appDep.threadSleep(2000);
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCasesReport_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td)[1]"), "Text", supplierData.get("supportCaseID")));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCasesReport_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td)[2]"), "Text", supplierData.get("payNetSupplierID")));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCasesReport_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td)[3]"), "Text", supplierData.get("supplierName")));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCasesReport_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td)[5]"), "Text", supplierData.get("ValidationType")));
                    //Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCasesReport_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td)[6]"), "Text", appInd.getDateTimeByTimezone("MM/dd/yyyy", "CST")));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCasesReport_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td)[7]"), "Text", "Closed"));

                    //Click and open the support case and validate the values
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupportCasesReport_Grid + "//tr[contains(@class, 'lines dx-column-lines')]/td)[1]")));
                    oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_Link, "Clickable", "", minTimeOut);

                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Cases_Section +"/div/div/div)[1]"), "Text", "Closed"));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Cases_Section +"//dt[text()='Created']/following-sibling::dd)[1]"), "Text", appInd.getDateTimeByTimezone("MM/dd/yyyy", "CST")));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Cases_Section +"//dt[text()='Case Type']/following-sibling::dd)[1]"), "Text", supplierData.get("ValidationType")));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Cases_Section +"//dt[text()='Priority']/following-sibling::dd)[1]"), "Text", "Low"));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupplierInformation_Section +"//div[text()='Name']/following-sibling::div)[1]"), "Text", supplierData.get("supplierName")));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupplierInformation_Section +"//div[text()='PayNet ID']/following-sibling::div)[1]"), "Text", supplierData.get("payNetSupplierID")));
                    Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_SupplierInformation_Section +"//div[text()='TIN']/following-sibling::div)[1]"), "Text", supplierData.get("TIN")));
                }else{
                    reports.writeResult(oBrowser, "Fail", "Invalid action type '"+caseOutcomeReason+"' for the Support Case from the StopFraud");
                    Assert.fail("Invalid action type '"+caseOutcomeReason+"' for the Support Case from the StopFraud");
                }
                oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
            }

            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "validateSupportCasesAreMarkedUnableToValidateFromStopFraud()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateSupportCasesAreMarkedUnableToValidateFromStopFraud()' method. " + e);
            return false;
        }finally{supplierData = null; supportCaseValidation = null; timeStamp = null;}
    }

    public boolean verifySupportCaseGridColumn(WebDriver oBrowser){
        String activeTabSupportCaseHeader = "//div[@id='divActiveSupportCasesContainer']//td[@role='columnheader' and @aria-label='%s Column']";
        String pendingTabSupportCaseHeader = "//div[@id='divPendingSupportCasesContainer']//td[@role='columnheader' and @aria-label='%s Column']";
        String closeTabSupportCaseHeader = "//div[@id='divClosedSupportCasesContainer']//td[@role='columnheader' and @aria-label='%s Column']";
        try {
            Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "Support Cases"));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activities_Link, "Clickable", "", 10);
            reports.writeResult(oBrowser, "screenshot", "Active tab verification with required columns ");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(activeTabSupportCaseHeader, "Case #"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(activeTabSupportCaseHeader, "PayNet Supplier ID"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(activeTabSupportCaseHeader, "Supplier Name"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(activeTabSupportCaseHeader, "Case Category"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(activeTabSupportCaseHeader, "Area Name"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(activeTabSupportCaseHeader, "Action Date"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(activeTabSupportCaseHeader, "Country Code"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(activeTabSupportCaseHeader, "Status"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(activeTabSupportCaseHeader, "Priority"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(activeTabSupportCaseHeader, "Assigned To"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(activeTabSupportCaseHeader, "Origination Source"))));
            appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Pending_Tab);
            appDep.threadSleep(3000);
            reports.writeResult(oBrowser, "screenshot", "Pending tab verification with required columns ");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(pendingTabSupportCaseHeader, "Case #"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(pendingTabSupportCaseHeader, "PayNet Supplier ID"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(pendingTabSupportCaseHeader, "Supplier Name"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(pendingTabSupportCaseHeader, "Case Category"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(pendingTabSupportCaseHeader, "Area Name"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(pendingTabSupportCaseHeader, "Case Type"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(pendingTabSupportCaseHeader, "Action Date"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(pendingTabSupportCaseHeader, "Country Code"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(pendingTabSupportCaseHeader, "Status"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(pendingTabSupportCaseHeader, "Priority"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(pendingTabSupportCaseHeader, "Assigned To"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(pendingTabSupportCaseHeader, "Origination Source"))));
            appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Closed_Tab);
            appDep.threadSleep(3000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "visibility", "", 10);
            reports.writeResult(oBrowser, "screenshot", "Closed tab verification with required columns ");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.id("sc_name_filter")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.id("sc_vnet_id_filter")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.id("sc_tin_filter")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.id("sc_record_limit")));
            Assert.assertTrue(appInd.getAttribute(oBrowser, By.id("sc_record_limit"), "placeholder").equalsIgnoreCase("1000"));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.id("btn_closed_sc_filter")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(closeTabSupportCaseHeader, "Last Updated"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(closeTabSupportCaseHeader, "Case #"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(closeTabSupportCaseHeader, "PayNet Supplier ID"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(closeTabSupportCaseHeader, "Supplier Name"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(closeTabSupportCaseHeader, "Case Category"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(closeTabSupportCaseHeader, "Area Name"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(closeTabSupportCaseHeader, "Case Type"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(closeTabSupportCaseHeader, "Action Date"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(closeTabSupportCaseHeader, "Country Code"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(closeTabSupportCaseHeader, "Status"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(closeTabSupportCaseHeader, "Priority"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(closeTabSupportCaseHeader, "Assigned To"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(closeTabSupportCaseHeader, "Origination Source"))));
            appDep.threadSleep(1000);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.id("btnExportdivClosedSupportCasesContainer")));
            appInd.clickObject(oBrowser, By.id("btnExportdivClosedSupportCasesContainer"));
            appDep.threadSleep(4000);
            Alert alert = oBrowser.switchTo().alert();
            Assert.assertTrue(alert.getText().trim().startsWith("Export-"));
            alert.accept();
            appDep.threadSleep(3000);
            File file = new File(System.getProperty("user.home")+"/Downloads/Export-" + (appInd.dateAddAndReturn("yyyy-MM-dd",0)) + ".xlsx");
            Assert.assertTrue(file.exists());
            file.delete();
            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "verifySupportCaseGridColumn()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifySupportCaseGridColumn()' method. " + e);
            return false;
        }
    }

    public boolean verifyClosedTabFilterFunctionality(WebDriver oBrowser){
        try{
            appInd.clickObject(oBrowser, By.xpath("//a[text()='Closed']"));
            //------------ Filter By Record Limit ----------------
            appInd.setObject(oBrowser, By.id("sc_record_limit"), "1");
            appInd.clickObject(oBrowser, By.id("btn_closed_sc_filter"));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "visibility", "", 15);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//div[text()='Displaying Page 1 of 1 (1 records)']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.id("btn_clear_filter")));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.id("sc_record_limit"),"text").equalsIgnoreCase(""));
            appInd.clearAndSetObject(oBrowser, By.id("sc_record_limit"), "10");
            appInd.clickObject(oBrowser, By.id("btn_closed_sc_filter"));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "visibility", "", 5);
            String supplierName = appInd.getTextOnElement(oBrowser, By.xpath("(//div[@id='divClosedSupportCasesContainer']//table)[2]//tr[1]/td[4]"), "text");
            String paynetID = appInd.getTextOnElement(oBrowser, By.xpath("(//div[@id='divClosedSupportCasesContainer']//table)[2]//tr[1]/td[3]"), "text");
            //------------ Filter By Supplier Name ----------------
            Assert.assertTrue(appInd.clickObject(oBrowser, By.id("btn_clear_filter")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "visibility", "", 5);
            appInd.setObject(oBrowser, By.id("sc_name_filter"), supplierName);
            appInd.clickObject(oBrowser, By.id("btn_closed_sc_filter"));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "visibility", "", 15);
            appDep.threadSleep(2000);
            int index = appInd.getWebElements(oBrowser, By.xpath("(//div[@id='divClosedSupportCasesContainer']//table)[2]//tr[not(contains(@class, 'freespace'))]")).size();
            for(int i=1;i<index;i++){
               Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("(//div[@id='divClosedSupportCasesContainer']//table)[2]//tr["+i+"]/td[4]"), "text").equalsIgnoreCase(supplierName));
            }
            //------------ Filter By PayNetID ----------------
            Assert.assertTrue(appInd.clickObject(oBrowser, By.id("btn_clear_filter")));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "visibility", "", 5);
            appInd.setObject(oBrowser, By.id("sc_vnet_id_filter"), paynetID);
            appInd.clickObject(oBrowser, By.id("btn_closed_sc_filter"));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "visibility", "", 15);
            appDep.threadSleep(2000);
            index = appInd.getWebElements(oBrowser, By.xpath("(//div[@id='divClosedSupportCasesContainer']//table)[2]//tr[not(contains(@class, 'freespace'))]")).size();
            for(int i=1;i<index;i++){
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("(//div[@id='divClosedSupportCasesContainer']//table)[2]//tr["+i+"]/td[3]"), "text").equalsIgnoreCase(paynetID));
            }
            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "verifyClosedTabFilterFunctionality()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifyClosedTabFilterFunctionality()' method. " + e);
            return false;
        }
    }
    public boolean verifySearchingSortingOperationOnColumn(WebDriver oBrowser){
        try{
            appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Tab);
            appDep.threadSleep(3000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_AddIcon, "visibility","", minTimeOut);
            appInd.clickObject(oBrowser,PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_NewManualCase_AddIcon);
            appDep.threadSleep(1000);
            appInd.switchToLastWindow(oBrowser);
            reports.writeResult(oBrowser, "screenshot", "Support case creation page visible");
            Assert.assertTrue(appInd.getAttribute(oBrowser, By.id("sc_case_category_id"), "class").endsWith("disable-submit"));
            List<LinkedHashMap<String, Object>> paynetInfoMap = getPayNetInfo(1);
            createAndVerifyACH_Return_Reject_NOC(oBrowser, "ACH Return/Reject/NOC", paynetInfoMap.get(0));
            reports.writeResult(oBrowser, "screenshot", "Support case creation done");
            //--------- Active Tab > Action Date Filter ---------
            String gridInputBox = PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_ActiveTabColumnSearch;
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Tab));
            appDep.threadSleep(1000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            final String date = appInd.formatDate(appInd.dateAddAndReturn("MM/dd/yyyy",0), "MM/dd/yyyy","MM/dd/yyyy");
            appDep.threadSleep(3000);
            appInd.clearAndSetObject(oBrowser, By.xpath(String.format(gridInputBox, "Action Date")), date);
            oBrowser.findElement(By.xpath(String.format(gridInputBox, "Action Date"))).sendKeys(Keys.ENTER);
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "screenshot", "Active tab is visible");
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            reports.writeResult(oBrowser, "screenshot", "Support case column filter applied");
            //--------- Active Tab > Country Code Filter [US] ---------
            appInd.clearAndSetObject(oBrowser, By.xpath(String.format(gridInputBox, "Action Date")), "");
            appInd.clearAndSetObject(oBrowser, By.xpath(String.format(gridInputBox, "Country Code")), "US");
            appDep.threadSleep(2000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            reports.writeResult(oBrowser, "screenshot", "Support case column filter applied");
            List<String> countryCodeList = appInd.getWebElements(oBrowser, By.xpath("//div[@id='divActiveSupportCasesContainer']//td[@role='gridcell' and contains(@aria-label,'Column Country Code, Value')]"))
                    .stream().map(element -> element.getText().trim()).collect(Collectors.toList());
            int count = countryCodeList.size()>=15?15:countryCodeList.size();
            for(int i=0;i<count;i++){
                Assert.assertTrue(countryCodeList.get(i).equalsIgnoreCase("US"));
            }
            //--------- Active Tab > Country Code Filter [CA] ---------
            appInd.clearAndSetObject(oBrowser, By.xpath(String.format(gridInputBox, "Country Code")), "CA");
            appDep.threadSleep(1000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            reports.writeResult(oBrowser, "screenshot", "Support case column filter applied");
            countryCodeList.clear();
            countryCodeList = appInd.getWebElements(oBrowser, By.xpath("//div[@id='divActiveSupportCasesContainer']//td[@role='gridcell' and contains(@aria-label,'Column Country Code, Value')]"))
                    .stream().map(element -> element.getText().trim()).collect(Collectors.toList());
            count = countryCodeList.size()>=15?15:countryCodeList.size();
            for(int i=0;i<count;i++){
                Assert.assertTrue(countryCodeList.get(i).equalsIgnoreCase("CA"));
            }
            //--------- Pending Tab > Country Code [US] Filter ---------
            gridInputBox = PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_PendingTabColumnSearch;
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Pending_Tab));
            appDep.threadSleep(1000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            reports.writeResult(oBrowser, "screenshot", "Pending tab is visible");
            appInd.clearAndSetObject(oBrowser, By.xpath(String.format(gridInputBox, "Country Code")), "US");
            appDep.threadSleep(1000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            reports.writeResult(oBrowser, "screenshot", "Support case Pending tab Country Code column filter applied");
            countryCodeList.clear();
            countryCodeList = appInd.getWebElements(oBrowser, By.xpath("//div[@id='divPendingSupportCasesContainer']//td[@role='gridcell' and contains(@aria-label,'Column Country Code, Value')]"))
                    .stream().map(element -> element.getText().trim()).collect(Collectors.toList());
            count = countryCodeList.size()>=15?15:countryCodeList.size();
            for(int i=0;i<count;i++){
                Assert.assertTrue(countryCodeList.get(i).equalsIgnoreCase("US"));
            }
            //--------- Pending Tab > Country Code [CA] Filter ---------
            appInd.clearAndSetObject(oBrowser, By.xpath(String.format(gridInputBox, "Country Code")), "CA");
            appDep.threadSleep(1000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            reports.writeResult(oBrowser, "screenshot", "Support case Pending tab Country Code column filter applied");
            countryCodeList.clear();
            countryCodeList = appInd.getWebElements(oBrowser, By.xpath("//div[@id='divPendingSupportCasesContainer']//td[@role='gridcell' and contains(@aria-label,'Column Country Code, Value')]"))
                    .stream().map(element -> element.getText().trim()).collect(Collectors.toList());
            count = countryCodeList.size()>=15?15:countryCodeList.size();
            for(int i=0;i<count;i++){
                Assert.assertTrue(countryCodeList.get(i).equalsIgnoreCase("CA"));
            }
            //--------- Active Tab > CountryCode verification ---------
            gridInputBox = PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_ActiveTabColumnSearch;
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Active_Tab));
            appDep.threadSleep(1000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appInd.clearAndSetObject(oBrowser, By.xpath(String.format(gridInputBox, "Country Code")), "US");
            appDep.threadSleep(1000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            reports.writeResult(oBrowser, "screenshot", "Support case Pending tab Country Code column filter applied");
            appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_ActiveTab_ClickFirst_Record_Data);
            appDep.threadSleep(1000);
            appInd.switchToLastWindow(oBrowser);
            reports.writeResult(oBrowser, "screenshot", "Support Case : We click on 1st record from grid");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath("//div[@id='additional_details_support_cases']/div[text()='US']")));
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "verifySearchingSortingOperationOnColumn()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifySearchingSortingOperationOnColumn()' method. " + e);
            return false;
        }
    }

    public boolean verifySupportCaseLogNote(WebDriver oBrowser){
        String noteText = "LogNote_Automation_"+appInd.getRandomString(5);
        String activityRow = "(//div[@id='%s']//table)[2]//tr[1]//td[text()='%s']";
        String logNoteBtnID = "closed_support_case_log_notes";
        String logNoteModelPath = "//div[@id='closed_case_log_notes_modal' and contains(@style,'block;')]";
        String logNoteTextArea = "//textarea[@id='closed_case_log_notes']";
        String logNoteCancelBtn = "cancel_btn_log_notes";
        String logNoteSubmitBtn = "btn_log_notes";
        String activityModel = "//div[@id='view_notes']//h4/span[text()='%s']/../../div[text()='%s']";
        try{
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.id(logNoteBtnID)));
            reports.writeResult(oBrowser, "screenshot", "Close support case page is open");
            appInd.clickObject(oBrowser, By.id(logNoteBtnID));
            appDep.threadSleep(1000);
            //---------------- VERIFY MODEL -----------------
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(logNoteModelPath)));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(logNoteModelPath+"//div[text()='Log Notes']")));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(logNoteTextArea)));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.id(logNoteCancelBtn)));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.id(logNoteSubmitBtn)));
            reports.writeResult(oBrowser, "screenshot", "Log Note model is open and successfully verified fields");
            //---------------- VERIFY VALIDATION ERROR MESSAGE ------------------
            appInd.clickObject(oBrowser, By.id(logNoteSubmitBtn));
            appDep.threadSleep(500);
            reports.writeResult(oBrowser, "screenshot", "Verify validation message to the LogNote model");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(logNoteModelPath)));
            //----------------- ENTER SOME NOTES AND VERIFY ACTIVITY LOG -------------------
            appInd.setObject(oBrowser, By.xpath(logNoteTextArea), noteText);
            appInd.clickObject(oBrowser, By.id(logNoteSubmitBtn));
            appDep.waitForTheElementState(oBrowser, By.xpath("//div[@id='closed_case_log_notes_modal' and contains(@style,'none;')]"), "visibility","", 15);
            appInd.clickObject(oBrowser, By.xpath(String.format(PayCRM_Modules_ManagedPaymentsUIPage.obj_tab_names,"Activities")));
            reports.writeResult(oBrowser, "screenshot", "Verified LogNote row in Activity tab");
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(activityRow, "divSupportCasesActivitiesContainer", "Closed: Validation Closed"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(activityRow, "divSupportCasesActivitiesContainer", "Case Update"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(activityRow, "divSupportCasesActivitiesContainer", "Closed Case Update"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(activityRow, "divSupportCasesActivitiesContainer", appInd.formatDate((appInd.dateAddAndReturn("MM/dd/yyyy",0)), "MM/dd/yyyy","MM/dd/yyyy")))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(activityRow, "divSupportCasesActivitiesContainer", noteText))));
            //----------------- VERIFY ACTIVITY LOG DETAIN IN MODEL -------------------
            appInd.clickObject(oBrowser, By.xpath(String.format(activityRow, "divSupportCasesActivitiesContainer", noteText)));
            appDep.threadSleep(1000);
            reports.writeResult(oBrowser, "screenshot", "Verified Activity model for log note");
            appDep.waitForTheElementState(oBrowser, By.xpath("//div[@id='view_notes' and contains(@style,'block;')]"), "visibility","", minTimeOut);
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(activityModel,"Activity", "Closed: Validation Closed"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(activityModel,"Activity Type", "Case Update"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(activityModel,"Reason", "Closed Case Update"))));
            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(activityModel,"Notes", noteText))));
            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "verifySupportCaseLogNote()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'verifySupportCaseLogNote()' method. " + e);
            return false;
        }
    }





    /*************************************************************************************************************
     * Method Name      : userCreatesNewEnrollmentCaseUnderProgramManagementAndEnrollWithSpecificOfferTypeAndOfferAccepted()
     * Purpose          : User creates the New supplier under program management, enroll it with specific offer type and different offer selected values
     * Author           : Gudi
     * Parameters       : oBrowser, offerType, isOfferAccepted, appName, loginDetails, dataTable
     * ReturnType       : boolean
     **************************************************************************************************************/
    public boolean userCreatesNewEnrollmentCaseUnderProgramManagementAndEnrollWithSpecificOfferTypeAndOfferAccepted(WebDriver oBrowser, String enrollmentCaseType, String offerType, String isOfferAccepted, DataTable dataTable){
        Map<String, String> supplierDetails = null;
        int randomNumber = 0;
        try{
            if(oBrowser.toString().contains("(null)")){
                oBrowser = enrollmentManagersUIBaseSteps.loginToPayCRMWithAnotherUser("PayCRM", "qaPermissionDetails");
            }

            if(enrollmentCaseType.equalsIgnoreCase("New")){
                //Navigate to Program Management, open it & create new supplier-->Open the enrollment case and enroll it
                randomNumber = Integer.parseInt(appInd.generateRandomNumbers(1, 3, 1));
                oBrowser.navigate().to(appInd.getPropertyValueByKeyName("enrollmentCase"+randomNumber));
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ShowEditDetails_Header, "Clickable", "", waitTimeOut);

                supplierDetails = createNewSupplier(oBrowser, dataTable);
                Assert.assertTrue(supplierDetails.get("TestPassed").equalsIgnoreCase("True"), "The 'createNewSupplier()' method was failed");
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_AdditionalAction_Section + "/following-sibling::ul/li[@id='case']//*[contains(@class, 'icon-external-link')]")));
                oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            }else{
                //Open existing Enrollment case, enroll it
            }

            //Enroll the enrollment case & validate for the assigned to, activity logs & status change
            Assert.assertTrue(userEnrollTheCaseForTheSpecificOfferTypeAndAcceptedOfferOption(oBrowser, offerType, isOfferAccepted, dataTable));
            return true;
        }catch (Exception e) {
            reports.writeResult(oBrowser, "Exception", "userCreatesNewEnrollmentCaseUnderProgramManagementAndEnrollWithSpecificOfferTypeAndOfferAccepted()' method. " + e);
            return false;
        }catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'userCreatesNewEnrollmentCaseUnderProgramManagementAndEnrollWithSpecificOfferTypeAndOfferAccepted()' method. " + e);
            return false;
        }
    }




    /*************************************************************************************************
     * Method Name      : userChooseTheRolesAndLinkWorkQueueToTheSelectedUser()
     * Purpose          : user select/unselect the roles and link/unlink users to the work queue under users settings
     * Author           : Gudi
     * Parameters       : oBrowser, permissionAction, permissionNames, workQueueNameAndActions
     * ReturnType       : boolean
     ************************************************************************************************/
    public boolean userChooseTheRolesAndLinkWorkQueueToTheSelectedUser(WebDriver oBrowser, String permissionAction, String permissionNames, String workQueueNameAndActions){
        try{
            Assert.assertTrue(payCRM_Modules_ManageUIBaseStep.addRemoveUserToWorkQueueUnderUsersSettings(oBrowser, workQueueNameAndActions));
            Assert.assertTrue(payCRM_Modules_ManageUIBaseStep.add_OR_RemovePermissionForTheSelectedRoles(oBrowser, permissionAction, permissionNames));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowserTwo, "Exception", "Exception in 'userChooseTheRolesAndLinkWorkQueueToTheSelectedUser()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowserTwo, "Exception", "AssertionError in 'userChooseTheRolesAndLinkWorkQueueToTheSelectedUser()' method. " + e);
            return false;
        }finally {
            oBrowser.quit();
            oBrowser = null;
        }
    }





    /*************************************************************************************************
     * Method Name      : userEnrollTheCaseForTheSpecificOfferTypeAndAcceptedOfferOption()
     * Purpose          : user select/unselect the roles and link/unlink users to the work queue under users settings
     * Author           : Gudi
     * Parameters       : oBrowser, permissionAction, permissionNames, workQueueNameAndActions
     * ReturnType       : boolean
     ************************************************************************************************/
    public boolean enterTheDetailsForEnrollCase(WebDriver oBrowser, String offerType, String isOfferAccepted, DataTable dataTable){
        List<Map<String, String>> enrollmentDetails = null;
        Map<String, String> enrollmentData = null;
        String arrOfferTypeOptions[];
        String arrOfferAcceptedOptions[];
        int index = 0;
        String offerNumber = null;
        String supplierName = null;
        String caseID = null;
        String offerName = null;
        String ABARoutingNumber = null;
        List<String> strOfferTypes = null;
        List<WebElement> objOfferTypes = null;
        try{
            if(System.getProperty("environment").equalsIgnoreCase("Staging")) ABARoutingNumber = "063100277";
            else ABARoutingNumber = "071000039";
            enrollmentDetails = dataTable.asMaps(String.class, String.class);
            enrollmentData = new HashMap<String, String>();
            enrollmentData.put("offerTypeOptions", enrollmentDetails.get(0).get("offerTypeOptions"));
            enrollmentData.put("OfferAcceptedOptions", enrollmentDetails.get(0).get("OfferAcceptedOptions"));
            enrollmentData.put("Notes", enrollmentDetails.get(0).get("Notes")+appInd.getDateTime("hhmmss"));

            arrOfferTypeOptions = enrollmentData.get("offerTypeOptions").split("#", -1);
            arrOfferAcceptedOptions = enrollmentData.get("OfferAcceptedOptions").split("#", -1);

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_Enroll_Btn));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);

            //Read the supplier name and case id:
            supplierName = appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_SupplierName_Edit, "Value");
            caseID = (oBrowser.getCurrentUrl().split("/"))[4].trim();

            //Choose Contact - select existing contact
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ChooseContact_Label)));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ChooseContact_Option + "/parent::span/following-sibling::span//li[2]")));

            //Choose Address - select existing address
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ChooseAddress_Label)));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ChooseAddress_Option + "/parent::span/following-sibling::span//li[2]")));

            objOfferTypes = oBrowser.findElements(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_OfferTypes_Labels + "//li/a"));
            strOfferTypes = new ArrayList<String>();

            for(int i=0; i<objOfferTypes.size(); i++){
                strOfferTypes.add(objOfferTypes.get(i).getText());
            }

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("//a[contains(text(), ' - "+offerType+"')]")));
            appDep.threadSleep(2000);
            offerNumber = oBrowser.findElement(By.xpath("//h3[contains(text(), '"+offerType.replace(" Card", "")+"')]/ancestor::div[contains(@id, 'offer')][contains(@class, 'active')]")).getAttribute("id");
            index = Integer.parseInt(offerNumber.substring(offerNumber.length()-1, offerNumber.length()));

            switch(offerType){
                case "Virtual Card":
                    offerName = "VCA";
                    offerNumber = oBrowser.findElement(By.xpath("//h3[contains(text(), '"+offerType.replace(" Card", "")+"')]/ancestor::div[contains(@id, 'offer')][contains(@class, 'active')]")).getAttribute("id");
                    index = Integer.parseInt(offerNumber.substring(offerNumber.length()-1, offerNumber.length()));
                    appDep.waitForTheElementState(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_VirtualCreditCardDetails_Label + "/div/div/h3"), "Text", "Virtual Credit Card Details", waitTimeOut);
                    Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_VcardEmail_Edit, arrOfferTypeOptions[0]));
                    Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_VcardPhone_Edit, arrOfferTypeOptions[1]));
                    Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Vcard_Country_List, arrOfferTypeOptions[2]));
                    Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Vcard_Currency_List, arrOfferTypeOptions[3]));
                    break;
                case "ACH Plus":
                    offerName = "VCA";
                    Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid +"//select[@id='inrollment_case_offers_"+index+"__maintenance_fee_method']"), arrOfferTypeOptions[0]));
                    Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid +"//input[@id='inrollment_case_offers_"+index+"__payment_notification_email_recipient']"), arrOfferTypeOptions[1]));
                    Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid + "//input[@id='inrollment_case_offers_"+index+"__remittance_advice_email_recipient']"), arrOfferTypeOptions[2]));
                    Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid + "//input[@id='inrollment_case_offers_"+index+"__network_maintenance_fee_advice_email_recipient']"), arrOfferTypeOptions[3]));
                    Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid + "//select[@id='inrollment_case_offers_"+index+"__bank_account_type']"), arrOfferTypeOptions[4]));
                    Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid + "//input[@id='inrollment_case_offers_"+index+"__bank_name']"), arrOfferTypeOptions[5]));
                    Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid +"//input[@id='inrollment_case_offers_"+index+"__bank_account_name']"), arrOfferTypeOptions[6]));
                    Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid + "//input[@id='inrollment_case_offers_"+index+"__bank_aba_routing_number']"), ABARoutingNumber));
                    Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid +"//input[@id='inrollment_case_offers_"+index+"__bank_account_number']"), arrOfferTypeOptions[8]));
                    Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid + "//select[@id='inrollment_case_offers_"+index+"__bank_country']"), arrOfferTypeOptions[9]));
                    Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid + "//select[@id='inrollment_case_offers_"+index+"__bank_currency_code']"), arrOfferTypeOptions[10]));
                case "ACH Basic":
                    offerName = "VCA";
                    Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid +"//select[@id='inrollment_case_offers_"+index+"__maintenance_fee_method']"), arrOfferTypeOptions[0]));
                    Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid +"//input[@id='inrollment_case_offers_"+index+"__payment_notification_email_recipient']"), arrOfferTypeOptions[1]));
                    Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid + "//input[@id='inrollment_case_offers_"+index+"__remittance_advice_email_recipient']"), arrOfferTypeOptions[2]));
                    Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid + "//input[@id='inrollment_case_offers_"+index+"__network_maintenance_fee_advice_email_recipient']"), arrOfferTypeOptions[3]));
                    Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid + "//select[@id='inrollment_case_offers_"+index+"__bank_account_type']"), arrOfferTypeOptions[4]));
                    Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid + "//input[@id='inrollment_case_offers_"+index+"__bank_name']"), arrOfferTypeOptions[5]));
                    Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid +"//input[@id='inrollment_case_offers_"+index+"__bank_account_name']"), arrOfferTypeOptions[6]));
                    Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid + "//input[@id='inrollment_case_offers_"+index+"__bank_aba_routing_number']"), ABARoutingNumber));
                    Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid +"//input[@id='inrollment_case_offers_"+index+"__bank_account_number']"), arrOfferTypeOptions[8]));
                    Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid + "//select[@id='inrollment_case_offers_"+index+"__bank_country']"), arrOfferTypeOptions[9]));
                    Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid + "//select[@id='inrollment_case_offers_"+index+"__bank_currency_code']"), arrOfferTypeOptions[10]));
                default:
                    reports.writeResult(oBrowser, "Fail", "Invalid Offer Type '"+offerType+"' was specified.");
                    Assert.fail("Invalid Offer Type '"+offerType+"' was specified.");
            }

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_OfferAccepted_SelectOne_Label));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Contact_ChooseContact_Edit, isOfferAccepted));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_OfferAccepted_SelectOne_SearchResult_Label));
            appInd.scrollToThePage(oBrowser, "Bottom");

            switch(isOfferAccepted){
                case "Yes":
                    appDep.waitForTheElementState(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_Enroll_MethodOfContact_List+")["+index+"]"), "Clickable", "", waitTimeOut);
                    Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_Enroll_MethodOfContact_List+")["+index+"]"), arrOfferAcceptedOptions[0]));
                    Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_Enroll_AdditionalInfo_ContactName_Edit+")["+index+"]"), arrOfferAcceptedOptions[1]));
                    Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_Enroll_AdditionalInfo_ContactEmail_Edit+")["+index+"]"), arrOfferAcceptedOptions[2]));
                    Assert.assertTrue(appInd.verifyText(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_AdditionalInfo_EnrolledBy_List, "Dropdown", appInd.getPropertyValueByKeyName("qaPermissionUser")));
                    Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_AdditionalInfo_EnrollmentSource_List, arrOfferAcceptedOptions[3]));
                    if(offerType.equalsIgnoreCase("Virtual Card")){
                        Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_AdditionalInfo_Fees_List, arrOfferAcceptedOptions[4]));
                        Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_AdditionalInfo_Limit_List, arrOfferAcceptedOptions[5]));
                    }
                    break;
                case "No":
                    appDep.waitForTheElementState(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_Enroll_MethodOfContact_List+")["+index+"]"), "Clickable", "", waitTimeOut);
                    Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_Enroll_MethodOfContact_List+")["+index+"]"), arrOfferAcceptedOptions[0]));
                    Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_Enroll_AdditionalInfo_ContactName_Edit+")["+index+"]"), arrOfferAcceptedOptions[1]));
                    Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_Enroll_AdditionalInfo_ContactEmail_Edit+")["+index+"]"), arrOfferAcceptedOptions[2]));
                    if(offerType.equalsIgnoreCase("Virtual Card")){
                        Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_CanSupplierAcceptDebitCard_Label));
                        Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_CanSupplierAcceptDebitCard_Input), "No"));
                        Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_CanSupplierAcceptDebitCard_Input + "/parent::span/following-sibling::span//li")));
                    }
                    break;
                case "Not Yet":
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Activity_List, "Clickable", "", waitTimeOut);
                    Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Activity_List, arrOfferAcceptedOptions[0]));
                    Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_PhoneNumber_Edit, arrOfferAcceptedOptions[1]));
                    Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_PhoneType_List, arrOfferAcceptedOptions[2]));
                    Assert.assertTrue(appInd.selectObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_CaseOutcome_List, arrOfferAcceptedOptions[3]));
                    break;
                default:
                    reports.writeResult(oBrowser, "Fail", "Invalid Was this offer accepted? '"+isOfferAccepted+"' was specified.");
                    Assert.fail("Invalid Was this offer accepted? '"+isOfferAccepted+"' was specified.");
            }

            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("//textarea[@id='inrollment_case_offers_"+index+"__notes']"), enrollmentData.get("Notes")));

            if(isOfferAccepted.equalsIgnoreCase("Yes")){
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Btn+")["+index+"]")));
                appDep.waitForTheElementState(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Enroll_ConfirmarionMessage_Label + "//h3)[1]"), "Text", "Success!", waitTimeOut);
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Enroll_ConfirmarionMessage_Label + "//h3)[1]"), "Text", "Success!"));
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Enroll_ConfirmarionMessage_Label + "//p)[1]"), "Text").contains("Case "+caseID+" - "+supplierName+" successfully enrolled."));
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Enroll_ConfirmarionMessage_Label + "//p)[2]"), "Text").contains("Supplier activated. Offer accepted: "+index+" - "+offerName+""));
            }else if(isOfferAccepted.equalsIgnoreCase("No")){
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_NextOffer_Btn+")["+index+"]")));
                appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Visibility", "", 10);
            }else if(isOfferAccepted.equalsIgnoreCase("Not Yet")){
                Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_Update_Btn+")["+index+"]")));
                appDep.waitForTheElementState(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Result_Update_Grid + "//h3)[1]"), "Text", "Updated", waitTimeOut);
                Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Result_Update_Grid + "//h3)[1]"), "Text", "Updated"));
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Result_Update_Grid + "//p)[1]"), "Text").contains("Case " + caseNumber + " - Consolidated Storage Companies, Inc. has been successfully updated"));
                Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Result_Update_Grid + "//p)[2]"), "Text").contains("Offer saved."));
            }
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowserTwo, "Exception", "Exception in 'enterTheDetailsForEnrollCase()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowserTwo, "Exception", "AssertionError in 'enterTheDetailsForEnrollCase()' method. " + e);
            return false;
        }finally {enrollmentDetails = null; enrollmentData = null; arrOfferTypeOptions = null; arrOfferAcceptedOptions = null; offerNumber = null; supplierName = null; caseID = null; offerName = null; ABARoutingNumber = null;}
    }




    /*************************************************************************************************
     * Method Name      : userEnrollTheCaseForTheSpecificOfferTypeAndAcceptedOfferOption()
     * Purpose          : user select/unselect the roles and link/unlink users to the work queue under users settings
     * Author           : Gudi
     * Parameters       : oBrowser, permissionAction, permissionNames, workQueueNameAndActions
     * ReturnType       : boolean
     ************************************************************************************************/
    public boolean userEnrollTheCaseForTheSpecificOfferTypeAndAcceptedOfferOption(WebDriver oBrowser, String offerType, String isOfferAccepted, DataTable dataTable){
        try{
            Assert.assertTrue(enterTheDetailsForEnrollCase(oBrowser, offerType, isOfferAccepted, dataTable));

            //After enroll/update/Next Offer
            if(isOfferAccepted.equalsIgnoreCase("Yes")){
                switch(offerType){
                    case "Virtual Card":
                        oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
                        appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", minTimeOut);
                        Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Cases_Section+ "//div[contains(@class, 'sticky')]"), "Text", "Closed"));
                        Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Cases_Section+ "//dt[contains(text(), 'Assigned to')]/following-sibling::dd"), "Text").contains(appInd.getPropertyValueByKeyName("qaPermissionUser")));
                        appInd.scrollToThePage(oBrowser, "Bottom");

                        //Activities Tab
                        Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_EnrollmentCases_Activities_Grid + "//tr[2][contains(@class, 'lines dx-column-lines')]/td)[3]"), "Text").contains("Closed: Enrolled"));
                        Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_EnrollmentCases_Activities_Grid + "//tr[2][contains(@class, 'lines dx-column-lines')]/td)[4]"), "Text").contains("Enrollment Complete"));
                        Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_EnrollmentCases_Activities_Grid + "//tr[2][contains(@class, 'lines dx-column-lines')]/td)[6]"), "Text").contains(appInd.getPropertyValueByKeyName("qaPermissionUser")));
                        break;
                }
            }else if(isOfferAccepted.equalsIgnoreCase("No")){

            }else if(isOfferAccepted.equalsIgnoreCase("Not Yet")){

            }

            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);

            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowserTwo, "Exception", "Exception in 'userEnrollTheCaseForTheSpecificOfferTypeAndAcceptedOfferOption()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowserTwo, "Exception", "AssertionError in 'userEnrollTheCaseForTheSpecificOfferTypeAndAcceptedOfferOption()' method. " + e);
            return false;
        }
    }

    /*************************************************************************************************
     * Method Name      : createSupplierAndEnrollWithACHPlusOffer()
     * Purpose          : Create supplier and enroll it with ACH Plus offer
     * Author           : Shidd
     * Parameters       : oBrowser, dataTale
     * ReturnType       : boolean
     ************************************************************************************************/
    public boolean createSupplierAndEnrollWithACHPlusOffer(WebDriver oBrowser, DataTable dataTable) {
        Map<String, String> supplierDetails = null;
        String enrollmentURL = null;
        try {
            enrollmentURL = oBrowser.getCurrentUrl() + appInd.getPropertyValueByKeyName("enrollmentId");

            oBrowser.navigate().to(enrollmentURL);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_GeneralUIPage.obj_ShowEditDetails_Header, "Clickable", "", waitTimeOut);

            supplierDetails = createNewSupplier(oBrowser, dataTable);
            Assert.assertTrue(supplierDetails.get("TestPassed").equalsIgnoreCase("True"), "The 'createNewSupplier()' method was failed");

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_AdditionalAction_Section + "/following-sibling::ul/li[@id='case']//*[contains(@class, 'fa-arrow-up-right-from-square')]")));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);

            Assert.assertTrue(enterTheDetailsAndEnrollCaseForACHPlus(oBrowser, "ACH Plus", "Yes", dataTable));
            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowserTwo, "Exception", "Exception in 'createSupplierAndEnrollWithACHPlusOffer()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowserTwo, "Exception", "AssertionError in 'createSupplierAndEnrollWithACHPlusOffer()' method. " + e);
            return false;
        } finally {
            supplierDetails = null; enrollmentURL = null;
        }
    }

    /*************************************************************************************************
     * Method Name      : enterTheDetailsAndEnrollCaseForACHPlus()
     * Purpose          : Enrolls created supplier
     * Author           : Shidd
     * Parameters       : oBrowser, offerType, isOfferAccepted, dataTable
     * ReturnType       : boolean
     ************************************************************************************************/
    public boolean enterTheDetailsAndEnrollCaseForACHPlus(WebDriver oBrowser, String offerType, String isOfferAccepted, DataTable dataTable){
        List<Map<String, String>> enrollmentDetails = null;
        Map<String, String> enrollmentData = null;
        int index = 0;
        String offerNumber = null;
        String caseID = null;
        String ABARoutingNumber = null;
        try{
            if(System.getProperty("environment").equalsIgnoreCase("Staging")) ABARoutingNumber = "063100277";
            else ABARoutingNumber = "071000039";
            enrollmentDetails = dataTable.asMaps(String.class, String.class);
            enrollmentData = new HashMap<>();
            enrollmentData.put("Notes", enrollmentDetails.get(0).get("Notes")+appInd.getDateTime("hhmmss"));

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_Enroll_Btn));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);

            //Read the supplier name and case id:
            supplierName = appInd.getTextOnElement(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_SupplierName_Edit, "Value");
            caseID = (oBrowser.getCurrentUrl().split("/"))[4].trim();

            //Choose Contact - select existing contact
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ChooseContact_Label)));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ChooseContact_Option + "/parent::span/following-sibling::span//li[2]")));

            //Choose Address - select existing address
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ChooseAddress_Label)));
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ChooseAddress_Option + "/parent::span/following-sibling::span//li[2]")));

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("//a[contains(text(), ' - "+offerType+"')]")));
            appDep.threadSleep(2000);
            offerNumber = oBrowser.findElement(By.xpath("//h3[contains(text(), '"+offerType.replace(" Card", "")+"')]/ancestor::div[contains(@id, 'offer')][contains(@class, 'active')]")).getAttribute("id");
            index = Integer.parseInt(offerNumber.substring(offerNumber.length()-1, offerNumber.length()));

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid + "//input[@id='inrollment_case_offers_"+index+"__bank_name']"), "Corcentric"));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid +"//input[@id='inrollment_case_offers_"+index+"__bank_account_name']"), "Test Gudi"));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid + "//input[@id='inrollment_case_offers_"+index+"__bank_aba_routing_number']"), ABARoutingNumber));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(PayCRM_Modules_GeneralUIPage.obj_SwitchOffer_Grid +"//input[@id='inrollment_case_offers_"+index+"__bank_account_number']"), "55955955"));

            appInd.scrollToThePage(oBrowser, "Bottom");
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_OfferAccepted_ACHPlus));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Contact_ChooseContact_Edit, isOfferAccepted));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Enroll_OfferAccepted_SelectOne_ACHPlus_Label));

            appDep.waitForTheElementState(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_Enroll_MethodOfContact_List+")["+index+"]"), "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_Enroll_MethodOfContact_List+")["+index+"]"), "Email Address"));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("//input[@id='inrollment_case_offers_" + index + "__offer_contact_name']"), "Test Gudi"));
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath("//input[@id='inrollment_case_offers_" + index + "__offer_contact_email']"), "test@corcentric.com"));

            Assert.assertTrue(appInd.setObject(oBrowser, By.xpath("//textarea[@id='inrollment_case_offers_"+index+"__notes']"), enrollmentData.get("Notes")));
            reports.writeResult(oBrowser, "Screenshot", "Entered all the required information before enrolling supplier");

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("("+PayCRM_Modules_DailyGrindUIPage.obj_Enroll_Btn+")["+index+"]")));
            appDep.waitForTheElementState(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Enroll_ConfirmarionMessage_Label + "//h3)[1]"), "Text", "Success!", waitTimeOut);
            Assert.assertTrue(appInd.verifyText(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Enroll_ConfirmarionMessage_Label + "//h3)[1]"), "Text", "Success!"));
            Assert.assertTrue(appInd.getTextOnElement(oBrowser, By.xpath("(" + PayCRM_Modules_DailyGrindUIPage.obj_Enroll_ConfirmarionMessage_Label + "//p)"), "Text").contains("Case "+caseID+" - "+supplierName+" successfully enrolled."));

            reports.writeResult(oBrowser, "Screenshot", "After enrolling the supplier");
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowserTwo, "Exception", "Exception in 'enterTheDetailsAndEnrollCaseForACHPlus()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowserTwo, "Exception", "AssertionError in 'enterTheDetailsAndEnrollCaseForACHPlus()' method. " + e);
            return false;
        } finally {
           enrollmentDetails = null; enrollmentData = null; offerNumber = null; caseID = null; ABARoutingNumber = null;
        }
    }

    /*************************************************************************************************
     * Method Name      : fetchAndValidateAuditInfoTableData()
     * Purpose          : Navigates to stop fraud and support cases and validates it
     * Author           : Shidd
     * ReturnType       : boolean
     ************************************************************************************************/
    public boolean fetchAndValidateAuditInfoTableData(WebDriver oBrowser) {
        String tinNumber = null;
        Map<String, Object> stopFraudDetails = null;
        Map<String, Object> supportCaseDetails = null;
        try {
            tinNumber = oBrowser.findElement(PayCRM_Modules_DailyGrindUIPage.obj_stopFraud_PayNetId_Label).getText();
            Assert.assertTrue(appDep.runPollForTheSupportCase(oBrowser));
            stopFraudDetails = validateAuditInfoTabColumnNamesAndReturnTableData(oBrowser, tinNumber);
            Assert.assertTrue(stopFraudDetails.get("TestPassed").toString().equalsIgnoreCase("True"), "The 'validateAuditInfoTabColumnNamesAndReturnTableData()' method was failed");

            supportCaseDetails = validateAuditInfoTabColumnNamesAndReturnTableDataForSupportCases(oBrowser, stopFraudDetails.get("payNetId"));
            Assert.assertTrue(supportCaseDetails.get("TestPassed").toString().equalsIgnoreCase("True"), "The 'supportCaseDetails()' method was failed");

            Assert.assertEquals(stopFraudDetails, supportCaseDetails);

            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowserTwo, "Exception", "Exception in 'fetchAndValidateAuditInfoTableData)' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowserTwo, "Exception", "Exception in 'fetchAndValidateAuditInfoTableData)' method. " + e);
            return false;
        } finally {
            tinNumber = null; stopFraudDetails = null; supportCaseDetails = null;
        }
    }

    /*************************************************************************************************
     * Method Name      : validateAuditInfoTabColumnNamesAndReturnTableData()
     * Purpose          : Navigates to stop fraud and fetches data from audit info tab
     * Author           : Shidd
     * Parameters       : tinNumber
     * ReturnType       : boolean
     ************************************************************************************************/
    public Map<String, Object> validateAuditInfoTabColumnNamesAndReturnTableData(WebDriver oBrowser, String tinNumber) {
        Map<String, Object> stopFraudDetails = new HashMap<>();
        try {
            Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "Stopfraud"));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_StopFraud_ValidationLookup_link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SearchByTIN_Edit, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SearchByTIN_Edit, tinNumber));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Filter_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", minTimeOut);

            stopFraudDetails.put("payNetId", oBrowser.findElement(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_stopFraud_payNetId)).getText());
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_stopFraud_payNetId)));
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_AuditInfo_Link));
            reports.writeResult(oBrowser, "Screenshot", "Audit Info table screenshot");
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);

            List<String> columnNames = new ArrayList<>();
            columnNames.add(oBrowser.findElement(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_AuditInfoTimestamp)).getText());
            columnNames.add(oBrowser.findElement(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_AuditInfoUsername)).getText());
            columnNames.add(oBrowser.findElement(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_AuditInfoActionType)).getText());
            columnNames.add(oBrowser.findElement(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_AuditInfoArea)).getText());
            columnNames.add(oBrowser.findElement(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_AuditInfoEffectedTo)).getText());
            columnNames.add(oBrowser.findElement(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_AuditInfoDataDetails)).getText());
            columnNames.add(oBrowser.findElement(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_AuditInfoValidationDetails)).getText());
            stopFraudDetails.put("columnNames", columnNames);

            List<String> paymentInformationTableData = new ArrayList<>();
            paymentInformationTableData.add(oBrowser.findElement(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_AuditInfoTableData + "//td[text()='AddNew']")).getText());
            paymentInformationTableData.add(oBrowser.findElement(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_AuditInfoTableData + "//td[text()='Payment Information']")).getText());

            Assert.assertTrue(appInd.clickObject(oBrowser, oBrowser.findElement(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_AuditInfoTableData + "//td[text()='Payment Information']/following-sibling::td[2]/a"))));
            reports.writeResult(oBrowser, "Screenshot", "Payment Information");

            paymentInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_stopFraud_more_details, "ABA Number"))).getText());
            paymentInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_stopFraud_more_details, "Account Classification"))).getText());
            paymentInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_stopFraud_more_details, "Account Number"))).getText());
            paymentInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_stopFraud_more_details, "Account Owner Name"))).getText());
            paymentInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_stopFraud_more_details, "Account Type"))).getText());
            paymentInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_stopFraud_more_details, "Bank City"))).getText());
            paymentInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_stopFraud_more_details, "Bank Country"))).getText());
            paymentInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_stopFraud_more_details, "Bank Name"))).getText());
            paymentInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_stopFraud_more_details, "Bank State"))).getText());
            paymentInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_stopFraud_more_details, "Bank Zip Code"))).getText());
            stopFraudDetails.put("paymentInformationTableData", paymentInformationTableData);
            Assert.assertTrue(appInd.clickObject(oBrowser, oBrowser.findElement(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_stopFraud_more_details_close_button))));

            List<String> companyInformationTableData = new ArrayList<>();
            companyInformationTableData.add(oBrowser.findElement(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_AuditInfoTableData + "//td[text()='Initial Activation']")).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_AuditInfoTableData + "//td[text()='Company Information']")).getText());
            Assert.assertTrue(appInd.clickObject(oBrowser, oBrowser.findElement(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_AuditInfoTableData + "//td[text()='Company Information']/following-sibling::td[2]/a"))));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "Company Information");

            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Address"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "City"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Company Name"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Country"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "IsTaxIDSSN"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Phone"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "State"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "TaxID"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Zip Code"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Contact Locations"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Contact Type"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Email"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "First Name"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Job Title"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Last Name"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Name Prefix"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Phone"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Primary Location"))).getText());

            stopFraudDetails.put("companyInformationTableData", companyInformationTableData);
            Assert.assertTrue(appInd.clickObject(oBrowser, oBrowser.findElement(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_stopFraud_more_details_close_button))));
            stopFraudDetails.put("TestPassed", "True");
            return stopFraudDetails;
        } catch (Exception e) {
            reports.writeResult(oBrowserTwo, "Exception", "Exception in 'validateAuditInfoTabColumnNamesAndReturnTableData)' method. " + e);
            stopFraudDetails.put("TestPassed", "False");
            return stopFraudDetails;
        } catch (AssertionError e) {
            reports.writeResult(oBrowserTwo, "Exception", "Exception in 'validateAuditInfoTabColumnNamesAndReturnTableData)' method. " + e);
            stopFraudDetails.put("TestPassed", "False");
            return stopFraudDetails;
        }
    }

    /*************************************************************************************************
     * Method Name      : validateAuditInfoTabColumnNamesAndReturnTableDataForSupportCases()
     * Purpose          : Navigates to support cases and fetches data from audit info tab
     * Author           : Shidd
     * Parameters       : payNetId
     * ReturnType       : boolean
     ************************************************************************************************/
    public Map<String, Object> validateAuditInfoTabColumnNamesAndReturnTableDataForSupportCases(WebDriver oBrowser, Object payNetId) {
        Map<String, Object> supportCaseDetails = new HashMap<>();
        try {
            supportCaseDetails.put("payNetId", payNetId);
            Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "Support Cases"));
            appDep.threadSleep(1000);

            verifySupportCaseGridRecordVisible("Active", "PayNet Supplier ID", String.valueOf(payNetId), true);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_ActiveTab_ClickFirst_Record_Data));
            appDep.threadSleep(1000);
            appInd.switchToLastWindow(oBrowser);

            Assert.assertTrue(appInd.clickObject(oBrowser, oBrowser.findElement(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_supportCase_auditTab))));
            reports.writeResult(oBrowser, "Screenshot", "Audit tab for support case");

            List<String> columnNames = new ArrayList<>();
            columnNames.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_supportCase_auditTab_columnNames, "Timestamp"))).getText());
            columnNames.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_supportCase_auditTab_columnNames, "Username"))).getText());
            columnNames.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_supportCase_auditTab_columnNames, "Action Type"))).getText());
            columnNames.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_supportCase_auditTab_columnNames, "Area"))).getText());
            columnNames.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_supportCase_auditTab_columnNames, "Effected To"))).getText());
            columnNames.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_supportCase_auditTab_columnNames, "Data Details"))).getText());
            columnNames.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_supportCase_auditTab_columnNames, "Validation Details"))).getText());
            supportCaseDetails.put("columnNames", columnNames);

            String tableDataXpath = PayCRM_Modules_DailyGrindUIPage.obj_SupportCaseAuditInfoTableData + "//td[text()='%s']";
            List<String> companyInformationTableData = new ArrayList<>();
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(tableDataXpath, "Initial Activation"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(tableDataXpath, "Company Information"))).getText());
            Assert.assertTrue(appInd.clickObject(oBrowser, oBrowser.findElement(By.xpath(String.format(tableDataXpath, "Company Information") + "/following-sibling::td[2]/a"))));
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", "More details for support case");

            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Address"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "City"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Company Name"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Country"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "IsTaxIDSSN"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Phone"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "State"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "TaxID"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Zip Code"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Contact Locations"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Contact Type"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Email"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "First Name"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Job Title"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Last Name"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Name Prefix"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Phone"))).getText());
            companyInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Primary Location"))).getText());

            supportCaseDetails.put("companyInformationTableData", companyInformationTableData);
            Assert.assertTrue(appInd.clickObject(oBrowser, oBrowser.findElement(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_supportCase_moreDetails_closeButton))));

            oBrowser = enrollmentManagersUIBaseSteps.closeChildWindowAndSwitchBackToParentWindow(oBrowser);

            verifySupportCaseGridRecordVisible("Pending", "PayNet Supplier ID", String.valueOf(payNetId), true);
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_PendingTab_ClickFirst_Record));
            appDep.threadSleep(1000);
            appInd.switchToLastWindow(oBrowser);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.clickObject(oBrowser, oBrowser.findElement(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_supportCase_auditTab))));


            List<String> paymentInformationTableData = new ArrayList<>();
            paymentInformationTableData.add(oBrowser.findElement(By.xpath(String.format(tableDataXpath, "AddNew"))).getText());
            paymentInformationTableData.add(oBrowser.findElement(By.xpath(String.format(tableDataXpath, "Payment Information"))).getText());
            Assert.assertTrue(appInd.clickObject(oBrowser, oBrowser.findElement(By.xpath(String.format(tableDataXpath, "Payment Information") + "/following-sibling::td[2]/a"))));
            reports.writeResult(oBrowser, "Screenshot", "Audit tab for support case");
            appDep.threadSleep(1000);

            paymentInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "ABA Number"))).getText());
            paymentInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Account Classification"))).getText());
            paymentInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Account Number"))).getText());
            paymentInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Account Owner Name"))).getText());
            paymentInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Account Type"))).getText());
            paymentInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Bank City"))).getText());
            paymentInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Bank Country"))).getText());
            paymentInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Bank Name"))).getText());
            paymentInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Bank State"))).getText());
            paymentInformationTableData.add(oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.companyMoreDetailsXpath, "Bank Zip Code"))).getText());

            supportCaseDetails.put("paymentInformationTableData", paymentInformationTableData);
            Assert.assertTrue(appInd.clickObject(oBrowser, oBrowser.findElement(By.xpath("//div[@id='modal-title']/following-sibling::button/i"))));
            supportCaseDetails.put("TestPassed", "True");
            return supportCaseDetails;
        } catch (Exception e) {
            reports.writeResult(oBrowserTwo, "Exception", "Exception in 'validateAuditInfoTabColumnNamesAndReturnTableDataForSupportCases)' method. " + e);
            supportCaseDetails.put("TestPassed", "False");
            return supportCaseDetails;
        } catch (AssertionError e) {
            reports.writeResult(oBrowserTwo, "Exception", "Exception in 'validateAuditInfoTabColumnNamesAndReturnTableDataForSupportCases)' method. " + e);
            supportCaseDetails.put("TestPassed", "False");
            return supportCaseDetails;
        }
    }

    /*************************************************************************************************
     * Method Name      : verifyClearUnableToValidate()
     * Purpose          : Navigates to support cases and verifies Clear Unable to Validate
     * Author           : Shidd
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ************************************************************************************************/
    public boolean verifyClearUnableToValidate(WebDriver oBrowser) {
        String[] tabs = {"Active", "Closed"};
        String url, caseId, caseType, areaName, payNetId;
        try {
            Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "Support Cases"));
            appDep.waitForTheElementState(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_row, tabs[0], "1")),"Visibility","",waitTimeOut);
            reports.writeResult(oBrowser, "screenshot", "Support case grid load successfully");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_Generic_Text, "Active"))));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);

            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_ActiveTabColumnSearch, "Area Name")), "Company Information"));
            oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_ActiveTabColumnSearch, "Area Name"))).sendKeys(Keys.ENTER);
            appDep.threadSleep(1000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appDep.threadSleep(1000);

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_ActiveTab_ClickFirst_Record_Data));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);

            url = oBrowser.getCurrentUrl();
            caseId = url.split("/")[5];
            caseType = appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_CaseInfoTile, "4", "Case Type", "4")), "text").split(" ")[0];
            areaName = appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_CaseInfoTile, "6", "Area Name", "6")), "text");
            payNetId = appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_SupplierInfoTile, "2", "PayNet ID", "2")), "text");

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_LogActivity_Btn)));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);

            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_LogActivity_Notes_Textarea, "Clickable", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "The 'Log Activity' page has opened successful");

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.getObj_UnableToValidate_Toggle));
            appDep.threadSleep(1000);
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ActivityType_List)));

            Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ActivityType_List + "[not(contains(@class, 'disable-submit'))]"), "Email"));
            oBrowser.switchTo().activeElement().sendKeys(Keys.DOWN);
            oBrowser.switchTo().activeElement().sendKeys(Keys.ENTER);
            appDep.threadSleep(2000);
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_EmailAddress_Edit, "test@corcentric.com"));
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_ConversationID_Edit, "Id " + appInd.getDateTime("Shhmmss")));
            String notes = "Notes" + " - " + appInd.getDateTime("Shhmmss");
            Assert.assertTrue(appInd.setObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_LogActivity_Notes_Textarea, notes));

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Activity_Save_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_HideResults_Btn, "Clickable", "", waitTimeOut);

            oBrowser = enrollmentManagersUIBaseSteps.switchBackToParentWindowWithoutClosingChildWindow(oBrowser);

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_clearUnableToValidate));

            appDep.threadSleep(600);
            oBrowser.switchTo().alert().accept();
            appDep.threadSleep(2000);
            reports.writeResult(oBrowser, "Screenshot", " Clear Unable to Validate button is passed");

            Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_CaseStatus, "Open"))));

            oBrowser = enrollmentManagersUIBaseSteps.switchBackToParentWindowWithoutClosingChildWindow(oBrowser);

            Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "Stopfraud"));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_StopFraud_ValidationLookup_link));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SearchByPayNetId_Edit, "Clickable", "", waitTimeOut);
            Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SearchByPayNetId_Edit, payNetId));
            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Suppliers_Filter_Btn));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", minTimeOut);

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_stopFraud_payNetId)));
            appDep.threadSleep(2000);

            Assert.assertEquals(appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_supplierValidationType, caseType) + "/following-sibling::td[5]/span"), "text"), "Pending");
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowserTwo, "Exception", "Exception in 'verifyClearUnableToValidate()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowserTwo, "Exception", "Exception in 'verifyClearUnableToValidate()' method. " + e);
            return false;
        } finally {
            tabs = null; url = null; caseId = null; caseType = null; areaName = null; payNetId = null;
        }
    }

    /*************************************************************************************************
     * Method Name      : verifyCaseOutComeReason()
     * Purpose          : Navigates to support cases and verify case out come reason while logging activity
     * Author           : Shidd
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ************************************************************************************************/
    public boolean verifyCaseOutComeReason(WebDriver oBrowser, String caseType) {
        Select oSelect;
        List<WebElement> oEles;
        try {
            Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "Support Cases"));
            appDep.waitForTheElementState(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_row, "Active", "1")),"Visibility","",waitTimeOut);
            reports.writeResult(oBrowser, "screenshot", "Support case grid load successfully");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_Generic_Text, "Active"))));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);

            if (caseType.equalsIgnoreCase("automatic")) {
                Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_ActiveTabColumnSearch, "Origination Source")), "Automatic Support Case"));
                oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_ActiveTabColumnSearch, "Origination Source"))).sendKeys(Keys.ENTER);
            } else {
                Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_ActiveTabColumnSearch, "Origination Source")), "Manual Support Case"));
                oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_ActiveTabColumnSearch, "Origination Source"))).sendKeys(Keys.ENTER);
            }
            appDep.threadSleep(1000);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appDep.threadSleep(1000);

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_ActiveTab_ClickFirst_Record_Data));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);

            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_LogActivity_Btn)));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_LogActivity_Notes_Textarea, "Clickable", "", waitTimeOut);
            reports.writeResult(oBrowser, "Screenshot", "The 'Log Activity' page has opened successful");

            Assert.assertTrue(appInd.selectObject(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_ActivityType_List + "[not(contains(@class, 'disable-submit'))]"), "Email"));
            oBrowser.switchTo().activeElement().sendKeys(Keys.ENTER);
            appDep.threadSleep(2000);

            oSelect = new Select(oBrowser.findElement(PayCRM_Modules_DailyGrindUIPage.obj_Reason_List));
            oEles = oSelect.getOptions();

            Assert.assertTrue(oEles.size()>28, "All case outcome reasons are not available.");
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowserTwo, "Exception", "Exception in 'verifyCaseOutComeReason()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowserTwo, "Exception", "Exception in 'verifyCaseOutComeReason()' method. " + e);
            return false;
        } finally {
            oSelect = null; oEles = null;
        }
    }

    /*************************************************************************************************
     * Method Name      : verifyRelatedCases()
     * Purpose          : This method helps to validate all related cases along with export button feature.
     * Author           : Shidd
     * Parameters       : oBrowser
     * ReturnType       : boolean
     ************************************************************************************************/
    public boolean verifyRelatedCases(WebDriver oBrowser) {
        HashMap<String, String> relatedCases;
        String payNetId, fileName = null;
        String[] expectedColumnNames = {"Case #", "Supplier", "CaseType", "Alarm Date", "Status", "Assigned To"};
        List<String> actualColumnNames, supportCaseIds;
        List<WebElement> columnNames, supportCaseIdElements;
        Alert oAlert;
        try {
            relatedCases = new HashMap<>();
            Assert.assertTrue(appDep.selectDailyGrindModules(oBrowser, "Support Cases"));
            appDep.waitForTheElementState(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_row, "Active", "1")),"Visibility","",waitTimeOut);
            reports.writeResult(oBrowser, "screenshot", "Support case grid load successfully");
            Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_Generic_Text, "Active"))));
            appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_ActiveTab_ClickFirst_Record_Data));
            oBrowser = enrollmentManagersUIBaseSteps.switchToChildWindowAndReturnDriverObject(oBrowser);
            payNetId = appInd.getTextOnElement(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_SupplierInfoTile, "2", "PayNet ID", "2")), "text");
            Assert.assertTrue(appDep.validateGoogleIconPresence(oBrowser, "SupportCase"));

            Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_Cases_RelatedCases_Tab));

            columnNames = oBrowser.findElements(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_relatedCaseTableColumnNames));
            actualColumnNames = new ArrayList<>();
            for (WebElement element: columnNames) {
                actualColumnNames.add(element.getText());
            }

            for (int i = 0; i < actualColumnNames.size(); i++) {
                Assert.assertEquals(expectedColumnNames[i], actualColumnNames.get(i));
            }

            appInd.scrollToThePage(oBrowser, "Bottom");
            int rowsSize = oBrowser.findElements(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_relatedCaseTableData)).size();
            for (int i = 1; i < rowsSize; i++) {
                relatedCases.put(oBrowser.findElement(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_relatedCaseTableData + "[" + i + "]//td[1]")).getText(),
                        oBrowser.findElement(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_relatedCaseTableData + "[" + i + "]//td[5]")).getText());
            }

            Assert.assertTrue(appInd.clickObject(oBrowser, oBrowser.findElement(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_relatedSupportCaseTableExportButton))));
            reports.writeResult(oBrowser, "Screenshot", "StopFraud->Validations. Exporting the 'Supplier Validations' data excel file");
            appDep.threadSleep(2000);
            if(appInd.isAlertPresent(oBrowser)){
                oAlert = oBrowser.switchTo().alert();
                fileName = oAlert.getText();
                oAlert.accept();
            }
            appDep.threadSleep(5000);

            boolean blnRes = appDep.validateDownloadedFileAndDelete(oBrowser, fileName, ".xlsx", "", "Yes");
            if (blnRes) {
                reports.writeResult(oBrowser, "Pass", "The 'Supplier Validations' data excel file was exported successful");
            } else {
                reports.writeResult(oBrowser, "Fail", "Failed to export the 'Supplier Validations' data excel file");
                Assert.fail("Failed to export the 'Supplier Validations' xlsx file");
            }

            oBrowser = enrollmentManagersUIBaseSteps.switchBackToParentWindowWithoutClosingChildWindow(oBrowser);

            for (String key: relatedCases.keySet()) {
                if (relatedCases.get(key).equalsIgnoreCase("open")) {
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_Generic_Text, "Active"))));
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);

                    Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_ActiveTabColumnSearch, "PayNet Supplier ID")), payNetId));
                    oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_ActiveTabColumnSearch, "PayNet Supplier ID"))).sendKeys(Keys.ENTER);
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                    reports.writeResult(oBrowser, "Screenshot", "Active Tab related case search inside support cases table");

                    supportCaseIdElements = oBrowser.findElements(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_supportCaseActiveTableRows));
                    supportCaseIds = new ArrayList<>();
                    for (int i = 1; i < supportCaseIdElements.size(); i++) {
                        supportCaseIds.add(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_supportCaseActiveTableRows + "["+i+"]/td[1]"), "text"));
                    }
                    Assert.assertTrue(supportCaseIds.contains(key));
                } else if (relatedCases.get(key).equalsIgnoreCase("hold")) {
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_Generic_Text, "Pending"))));
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);

                    Assert.assertTrue(appInd.clearAndSetObject(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_PendingTabColumnSearch, "PayNet Supplier ID")), payNetId));
                    oBrowser.findElement(By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_SupportCase_Grid_PendingTabColumnSearch, "PayNet Supplier ID"))).sendKeys(Keys.ENTER);
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                    reports.writeResult(oBrowser, "Screenshot", "Pending Tab related case search inside support cases table");

                    supportCaseIdElements = oBrowser.findElements(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_supportCasePendingTableRows));
                    supportCaseIds = new ArrayList<>();
                    for (int i = 1; i < supportCaseIdElements.size(); i++) {
                        supportCaseIds.add(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_supportCasePendingTableRows + "["+i+"]/td[1]"), "text"));
                    }
                    Assert.assertTrue(supportCaseIds.contains(key));
                } else if (relatedCases.get(key).equalsIgnoreCase("closed")) {
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                    Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(String.format(PayCRM_Modules_DailyGrindUIPage.obj_Generic_Text, "Closed"))));
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);

                    Assert.assertTrue(appInd.clearAndSetObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_supportCaseClosedTab_payNetIdTextBox, payNetId));
                    Assert.assertTrue(appInd.clickObject(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_supportCaseClosedTabFilterButton));
                    appDep.waitForTheElementState(oBrowser, PayCRM_Modules_DailyGrindUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                    reports.writeResult(oBrowser, "Screenshot", "Closed Tab related case search inside support cases table");

                    supportCaseIdElements = oBrowser.findElements(By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_supportCaseClosedTableRows));
                    supportCaseIds = new ArrayList<>();
                    for (int i = 1; i < supportCaseIdElements.size(); i++) {
                        supportCaseIds.add(appInd.getTextOnElement(oBrowser, By.xpath(PayCRM_Modules_DailyGrindUIPage.obj_supportCaseClosedTableRows + "["+i+"]/td[2]"), "text"));
                    }
                    Assert.assertTrue(supportCaseIds.contains(key));
                }
            }
            return true;
        } catch (Exception e) {
            reports.writeResult(oBrowserTwo, "Exception", "Exception in 'verifyRelatedCases()' method. " + e);
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowserTwo, "Exception", "Exception in 'verifyRelatedCases()' method. " + e);
            return false;
        } finally {
            relatedCases = null; payNetId = null; fileName = null; expectedColumnNames = null; actualColumnNames = null; supportCaseIds = null; columnNames = null; supportCaseIdElements = null; oAlert = null;
        }
    }
}
