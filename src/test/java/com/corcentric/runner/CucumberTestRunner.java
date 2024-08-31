package com.corcentric.runner;

import static com.corcentric.common.ReportUtility.*;
import com.corcentric.baseSteps.api.*;
import com.corcentric.baseSteps.ui.*;
import com.corcentric.common.*;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import com.relevantcodes.extentreports.ExtentReports;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions
        (
                tags = "@Test123Shid",
                glue = {"com.corcentric.stepDefinitions"},
                plugin = {
                        "pretty",
                        "html:target/cucumber-report.html",
                        "json:target/cucumber.json",
                        "rerun:target/rerun.txt"
                },
                features = {"src/test/resources/features"},
                dryRun = false, monochrome = true
        )

public class CucumberTestRunner extends AbstractTestNGCucumberTests {
    public static String sParent;
    public static WebDriver oBrowser;
    public static WebDriver oBrowserTwo;
    public static WebDriver oEmailBrowser;
    public static String authToken;
    public static String apiCompanyServicesAuthToken;
    public static String apiManagePaymentsAuthToken;
    public static String apiAKFAuthToken;
    public static String apiStopFraudAuthToken;
    public static String apiDataDimensionAuthToken;
    public static String environment;
    protected static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    public static ExtentReports extent;
    public static ExtentTest test;
    public static ExtentReportUtils reports;
    public static AppIndependentMethods appInd;
    public static AppDependentMethods appDep;
    public static DBUtilities dbUtils;
    public static String screenShotLocation;
    public static final long minTimeOut = 60;
    public static final long waitTimeOut = 120;
    public static final long maxWaitTimeOut = 240;
    public static AssistedPaymentServicesUIBaseStep assistedPaymentServicesBaseSteps;
    public static PartnerEnrollmentsUIBaseStep partnerEnrollmentsUIBaseStep;
    public static GenericUIBaseStep enrollmentManagersUIBaseSteps;
    public static PayCRM_Modules_GeneralUIBaseStep payCRM_modules_generalUIBaseStep;
    public static PayCRM_RPA_ModuleUIBaseStep payCRM_rpa_moduleUIBaseStep;
    public static PayCRM_Modules_ManagedPaymentsUIBaseStep payCRM_ManagedPaymentsUIBaseStep;
    public static PayCRM_Modules_DailyGrindUIBaseStep payCRM_modules_DailyGrindUIBaseStep;
    public static PayCRM_Modules_ImportsUIBaseStep payCRM_Modules_importsUIBaseStep;
    public static PayCRM_Modules_ManageUIBaseStep payCRM_Modules_ManageUIBaseStep;
    public static APIDataProviderUtility apiDataProvider;
    public static APITestGenericUtility apiUtility;
    public static EmailUtilities emailUtilities = null;
    public static API_companyServicesBaseStep api_companyServicesBaseStep;
    public static API_ManagePaymentsBaseStep api_managePaymentsBaseStep;
    public static API_AKFBaseStep api_akfBaseStep;
    public static API_EnrollmentServicesBaseStep api_enrollmentServicesBaseStep;
    public static API_StopFraudBaseStep api_stopFraudBaseStep;
    public static API_DataDimensionBaseStep api_dataDimensionBaseStep;
    public static API_PayNetAPIBaseStep api_payNetAPIBaseStep;
    public static Datatable excelDatatable;

    @BeforeSuite
    public void Initializer() {
        extent = ExtentReportUtils.startExtentReport("TestReportFile");
        appDep = new AppDependentMethods();
        appInd = new AppIndependentMethods();
        reports = new ExtentReportUtils();
        assistedPaymentServicesBaseSteps = new AssistedPaymentServicesUIBaseStep();
        partnerEnrollmentsUIBaseStep = new PartnerEnrollmentsUIBaseStep();
        enrollmentManagersUIBaseSteps = new GenericUIBaseStep();
        payCRM_modules_generalUIBaseStep = new PayCRM_Modules_GeneralUIBaseStep();
        payCRM_rpa_moduleUIBaseStep = new PayCRM_RPA_ModuleUIBaseStep();
        dbUtils = new DBUtilities();
        payCRM_ManagedPaymentsUIBaseStep = new PayCRM_Modules_ManagedPaymentsUIBaseStep();
        payCRM_modules_DailyGrindUIBaseStep = new PayCRM_Modules_DailyGrindUIBaseStep();
        payCRM_Modules_importsUIBaseStep = new PayCRM_Modules_ImportsUIBaseStep();
        payCRM_Modules_ManageUIBaseStep = new PayCRM_Modules_ManageUIBaseStep();
        apiDataProvider = new APIDataProviderUtility();
        emailUtilities = new EmailUtilities();
        authToken = apiDataProvider.createAndGetAuthToken("client_ID", "client_Secrete", "audience");
        apiUtility = new APITestGenericUtility();
        api_companyServicesBaseStep = new API_companyServicesBaseStep();
        api_managePaymentsBaseStep = new API_ManagePaymentsBaseStep();
        apiCompanyServicesAuthToken = apiDataProvider.createAndGetAuthToken("companyServiceClient_ID", "companyServiceClient_Secrete", "companyServiceAudience");
        apiManagePaymentsAuthToken = apiDataProvider.createAndGetAuthToken("managePaymentClient_ID", "managePaymentClient_Secrete", "managePaymentAudience");
        api_akfBaseStep = new API_AKFBaseStep();
        apiAKFAuthToken = apiDataProvider.createAndGetAuthToken("akfClient_ID", "akfClient_Secrete", "akfAudience");
        api_enrollmentServicesBaseStep = new API_EnrollmentServicesBaseStep();
        api_stopFraudBaseStep = new API_StopFraudBaseStep();
        api_dataDimensionBaseStep = new API_DataDimensionBaseStep();
        apiStopFraudAuthToken = apiDataProvider.createAndGetAuthToken("stopFraudClient_ID", "stopFraudClient_Secrete", "stopFraudAudience");
        apiDataDimensionAuthToken = apiDataProvider.createAndGetAuthToken("dataDClient_ID", "dataDClient_Secrete", "dataDAudience");
        api_payNetAPIBaseStep = new API_PayNetAPIBaseStep();
        excelDatatable = new Datatable();
    }


    @AfterSuite
    public void manageReports() {
        String root = System.getProperty("user.dir").trim()+"\\";
        String baseLocation = "E:\\Reports\\history\\";
        String allureResult = "allure-results";
        String allureHTMLReport = "allure-html-report";
        if(appInd.getPropertyValueByKeyName("executionPlatform").equalsIgnoreCase("grid")){
            createReportDirectories(baseLocation+allureResult);
            createReportDirectories(baseLocation+allureHTMLReport);
            copyReports((root+allureResult),(baseLocation+allureResult));
            generateAllureReport((baseLocation));
            generateAllureReport((root));
        }else{
            generateAllureReport(root);
        }
    }

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
