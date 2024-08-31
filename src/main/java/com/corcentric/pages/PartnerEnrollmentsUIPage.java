package com.corcentric.pages;

import org.openqa.selenium.By;

public class PartnerEnrollmentsUIPage {
	public static By obj_Communities_Grid = By.xpath("//table[@id='table_communities']/tbody/tr/td[contains(@class, 'select-checkbox')]");
	public static By obj_PaymentHelp_Header = By.xpath("//h3[text()='Payment Help']");
	public static By obj_ReassignCase_UserName_Label = By.xpath("//div[@id='partner_case_user_id_chosen']/a/span");
	public static By obj_ReassignCase_UserName_SearchResult_Label = By.xpath("//div[@class='chosen-drop']//ul/li");
	public static By obj_Assign_Btn = By.xpath("//button[@class='btn btn-custom']");
	public static By obj_AssignedTo_Label = By.xpath("//div[@id='section_case_information']/div/dl/dt[text()='User']/following-sibling::dd[1]");
	public static By obj_EnrollmentActivities_Outcome_Column = By.xpath("//div[@class='dx-datagrid-content']/table//tr/td[contains(@aria-label, 'Outcome')]");
	public static By obj_EnrollmentActivities_CreatedBy_Column = By.xpath("//div[@class='dx-datagrid-content']/table//tr/td[contains(@aria-label, 'Created By')]");
	public static By obj_EnrollmentActivities_DateCreated_Column = By.xpath("//div[@class='dx-datagrid-content']/table//tr/td[contains(@aria-label, 'Date Created')]");
	public static By obj_EnrollmentActivities_Notes_Column = By.xpath("//div[@class='dx-datagrid-content']/table//tr/td[contains(@aria-label, 'Notes')]");
	public static By obj_search_Loading = By.xpath("//div[@class='dx-overlay-content dx-resizable dx-loadpanel-content']//div[text()='Loading records...']");
	public static By obj_LogActivity_Btn = By.id("btn_log_activity");
	public static By obj_LogActivity_Information = By.id("partner_case_entry_partner_activity_id");
	public static By obj_LogActivity_Information_Email_ContactName = By.id("partner_case_entry_contacted_name");
	public static By obj_LogActivity_Information_Email_ContactEmail = By.id("partner_case_entry_contacted_email_address");
	public static By obj_LogActivity_Information_Email_ContactNumber = By.id("partner_case_entry_contacted_phone_number");
	public static By obj_LogActivity_CaseOutcome = By.id("partner_case_entry_partner_outcome_id");
	public static By obj_LogActivity_Notes = By.id("partner_case_entry_notes");
	public static By obj_LogActivity_CaseOutcome_AlarmDate = By.xpath("//input[@id='partner_case_entry_alarm_date']");
	public static By obj_LogActivity_CaseOutcome_Description = By.xpath("//span[@id='case_outcome_description']");
	public static By obj_LogActivity_Submit_Btn = By.id("btn_submit_log_activity");
	public static By obj_ActivitiesTab_SearchBoxes = By.xpath("//div[@id='divCaseActivitiesDashboardContainer']//input");
	public static String activityModelVerification = "//div[@id='view_notes' and contains(@style,'block;')]//div[text()='%s']";
	public static By obj_LogActivity_CaseUpdate_Msg = By.xpath("//div[@id='case_entry_results_section']//p");
	public static String activitiesGrid_Record_Open = "//tbody/tr/td[text()='%s']";
	public static String obj_CommunitiesTable_Grid = "//table[@id='table_communities']";
	public static By obj_ShowCases_btn = By.xpath("//div[@id='btn_show_cases']");
	public static By obj_PartnerEnrollments_Link = By.xpath("//div/*[contains(text(), 'Partner Enrollments')]");
	public static String obj_CasesStatuses_Panel = "//div[@id='filter_status']";
	public static String obj_AssignedTo_Panel = "//div[@id='filter_assigned_to']";
	public static String obj_PartnerEnrollmentCases_Grid = "//div[@id='divPartnerResultCasesContainer']";
	public static By obj_payCRM_NavigateBar_Link = By.xpath("//a[@class='navbar-brand']");
	public static String obj_HomePage_Page = "//div[@id='home_landing']";
	public static String obj_PartnerType_Items = "//div[@id='filter_partner_type']";
	public static String obj_Activity_ViewNotes_Dialog = "//div[@id='view_notes']";
	public static By obj_PartnerEnrollments_Report_Link = By.xpath("//a[contains(@href, 'partner_enrollments_report')]");
	public static String obj_UserConsole_Grid = "//div[@id='divUsersContainer']";
	public static String obj_UserRoles_Section = "//div[@id='summary']";
	public static By obj_Roles_Update_Btn = By.xpath("//button[@id='btn_edit_user']");
	public static By obj_ConfirmationMessage_Label = By.xpath("//div[@class='alert alert-info custom-alert-info']");
	public static By obj_AssistedPaymentServices_Link = By.xpath("//div[@id='select_payments']//*[text()='Assisted Payment Services']");
	public static By obj_PartnerEnrollments_Label = By.xpath("//h3[contains(text(),'Partner Enrollments')]/..");
}