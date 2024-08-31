package com.corcentric.baseSteps.ui;

import com.corcentric.pages.PayCRM_Modules_GeneralUIPage;
import com.corcentric.runner.CucumberTestRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

public class CommonUIBase extends CucumberTestRunner {
    /********************************************************
     * Method Name      : navigateToRecord()
     * Purpose          : filter the record from grid and click on filter record
     * Author           : Deepak
     * Parameters       : oBrowser, tableID, ColumnName, record
     * ReturnType       : boolean
     ********************************************************/
    public boolean navigateToRecord(WebDriver oBrowser, String tableID, String columnName, String record){
        List<String> listOfHeaders = getMainGridTableHeaders(oBrowser, tableID);
        int index = listOfHeaders.indexOf(columnName);
        String filter = String.format(PayCRM_Modules_GeneralUIPage.obj_companies_grid_filter,++index);
        appInd.clickObject(oBrowser, By.xpath(filter));
        appInd.setObject(oBrowser, By.xpath(filter+"/input"), record);
        appDep.threadSleep(1000);
        reports.writeResult(oBrowser, "screenshot", "Record is filter");
        return true;
    }


    /********************************************************
     * Method Name      : getMainGridTableHeaders()
     * Purpose          : Supportive private method for getting the records from the given table header.
     * Author           : Deepak
     * Parameters       : oBrowser, headerID
     * ReturnType       : list of string
     ********************************************************/
    protected List<String> getMainGridTableHeaders(WebDriver oBrowser, String headerID){
        List<String> tableHeaders = new ArrayList<>();
        appInd.getWebElements(oBrowser, By.xpath(String.format(PayCRM_Modules_GeneralUIPage.obj_Main_Grid_Header, headerID)))
                .forEach( webElement -> tableHeaders.add(webElement.getText().trim()));
        return tableHeaders;
    }


    /********************************************************
     * Method Name      : clickOnFirstRecord()
     * Purpose          : User click on first record and navigate inside the cord
     * Author           : Deepak
     * Parameters       : oBrowser, tableID, ColumnName, record
     * ReturnType       : boolean
     ********************************************************/
    public boolean clickOnFirstRecord(WebDriver oBrowser, String tableID){
        appInd.clickObject(oBrowser, By.xpath(String.format(PayCRM_Modules_GeneralUIPage.obj_Grid_Column_FirstRecord, tableID)));
        appDep.threadSleep(1000);
        return true;
    }

}
