package com.corcentric.pages;

import org.openqa.selenium.By;

public class EnrollmentsManagerUIPage {
	public static By obj_Companies_Table_Loading = By.xpath("//table/tbody/tr/td[@class='dataTables_empty' and contains(text(), 'Loading...')]");
	public static By obj_ShowCases_btn = By.xpath("//div[@id='btn_show_cases']");
	public static By obj_EnrollmentCaseNumber_Label = By.xpath("//a[contains(text(),'Enrollment Cases')]/ancestor::div");
	public static By obj_Reassign_Btn = By.xpath("//div[@id='btn_reassign']");
	public static By obj_ReassignCase_UserName_Label = By.xpath("//div[@id='inrollment_case_user_id_chosen']/a/span");
	public static By obj_ReassignCase_UserName_EditList = By.xpath("//div[contains(@class,'chosen-with-drop')]/div/div/input");
	public static By obj_ReassignCase_UserName_SearchResult_Label = By.xpath("//ul[@class='chosen-results']/li");
	public static String obj_ReassignCase_User = "//div[@id='inrollment_case_user_id_chosen']";
	public static By obj_Assign_Btn = By.xpath("//button[@class='btn btn-darkgreen']");
	public static By obj_Activities_Tab = By.xpath("//a[text()='Activities']");
	public static By obj_EnrollmentActivities_Activity_Column = By.xpath("//table/tbody/tr/td[contains(text(), 'Enrollment Activities')]/following::tr[1]/td[contains(@aria-label, 'Column Activity')]");
	public static By obj_EnrollmentActivities_ActivityType_Column = By.xpath("//table/tbody/tr/td[contains(text(), 'Enrollment Activities')]/following::tr[1]/td[contains(@aria-label, 'Column Activity Type')]");
	public static By obj_EnrollmentActivities_CreatedBy_Column = By.xpath("//table/tbody/tr/td[contains(text(), 'Enrollment Activities')]/following::tr[1]/td[contains(@aria-label, 'Column Created By')]");
	public static By obj_EnrollmentActivities_DateCreated_Column = By.xpath("//table/tbody/tr/td[contains(text(), 'Enrollment Activities')]/following::tr[1]/td[contains(@aria-label, 'Column Date Created')]");
	public static By obj_EnrollmentActivities_Notes_Column = By.xpath("//table/tbody/tr/td[contains(text(), 'Enrollment Activities')]/following::tr[1]/td[contains(@aria-label, 'Column Notes')]");
	public static By obj_Logs_Tab = By.xpath("//a[text()='Logs']");
	public static String obj_EnrollmentCase_Grid = "//div[@id='divResultCasesContainer']";
	public static By obj_ResultCaseContainer_FirstCaseNumber = By.xpath("(//div[@class='dx-datagrid-content']/table//td[contains(@aria-label, 'Column Case #')])[1]");
	public static By obj_SectionCaseInfo_Label = By.xpath("//div[@id='section_case_information']/div//h3");
	public static By obj_WaveAndOffers_Header = By.xpath("//h3[text()='Wave & Offers']");
	public static By obj_ReassignCase_Dialog = By.xpath("//h4[text()='Reassign Case']");
	public static By obj_AssignedTo_Label = By.xpath("(//div[@id='section_case_information']/div/dl/dd)[1]");
	public static String obj_enrollmentCaseSupplierSectionTile = "(//div[@id='section_display_supplier_information']//dl//span[text()='%s']/parent::dt/following-sibling::dd)[1]";
	public static By obj_EnrollmentActivities_Grid = By.xpath("//td[text()='Enrollment Activities']");
	public static By obj_EnrollmentLogs_UserColumn = By.xpath("//div[@class='dataTables_scrollBody']/table//tr[1]/td[2]");
	public static By obj_EnrollmentLogs_ActionColumn = By.xpath("//div[@class='dataTables_scrollBody']/table//tr[1]/td[3]");
	public static final By obj_EnrollmentActivities_Pagination_Label = By.xpath("//div[@class='dx-pages']/div[contains(text(), 'Displaying Page 1')]");
	public static By obj_ReassignConfirmationMessage_Label = By.xpath("//div[@class='alert alert-info custom-alert-info']");
	public static By obj_Enrollments_tab = By.xpath("//div[@id='btn_inrollments' and not(contains(@class, 'highlight-button'))]");
	public static By obj_Enrollments_Clicked_tab = By.xpath("//div[@id='btn_inrollments' and contains(@class, 'highlight-button')]");
	public static By obj_SplitterHorizonal_Line = By.xpath("//div[@class='splitter-horizontal']");
	public static By obj_Attachments_Tab = By.xpath("//a[text()='Attachments']");
	public static By Obj_Attachments_Add_Btn = By.xpath("//a[@title='Add' and contains(@href, 'attachments')]");
	public static By Obj_Attachments_Create_Btn = By.xpath("//button[@id='attachment-submit']");
	public static By Obj_Attachments_Header = By.xpath("//div[contains(@style, 'block')]//h4[text()='New Attachment']");
	public static By obj_Attachments_ShowAttachment_Header = By.xpath("//div[contains(@style, 'block')]//h4[text()='Attachment']");
	public static By obj_Attachments_EditAttachment_Header = By.xpath("//div[contains(@style, 'block')]//h4[text()='Edit Attachment']");
	public static By Obj_Attachments_Name_Edit = By.xpath("//input[@id='attachment_name']");
	public static By Obj_Attachments_Description_Textarea = By.xpath("//textarea[@id='attachment_description']");
	public static By obj_attachments_AttachmentType_List = By.xpath("//select[@id='attachment_attachment_type_id']");
	public static By obj_Attachments_Status_List = By.xpath("//select[@id='attachment_status_id']");
	public static By obj_Attachments_AttachToSupplierYes_Btn = By.xpath("//div[@id='supplier_attach_switch']/span[text()='YES']");
	public static By obj_Attachments_AttachToSupplierNo_Btn = By.xpath("//div[@id='supplier_attach_switch']/span[text()='NO']");
	public static By Obj_Attachments_Grid = By.xpath("//table[contains(@class, 'fixed dx-datagrid-table-content')]/tbody");
	public static By obj_Attachments_Name_Edit = By.xpath("//div[@id='divCaseAttachmentsContainer']//input[@class='dx-texteditor-input']");
	public static By obj_ShowAttachments_Close_Btn = By.xpath("//h4[text()='Attachment']/following-sibling::button");
	public static By obj_ShowAttachment_Name_Label = By.xpath("//dt[text()='Name']/following-sibling::dd[1]");
	public static By obj_ShowAttachment_Description_Label = By.xpath("//dt[text()='Description']/following-sibling::dd[1]");
	public static By obj_ShowAttachment_Status_Label = By.xpath("//dt[text()='Status']/following-sibling::dd[1]");
	public static By obj_ShowAttachment_FileName_Label = By.xpath("//dt[text()='File Name']/following-sibling::dd[1]/a");
	public static By obj_ShowAttachment_AttachmentType_Label = By.xpath("//dt[text()='Attachment Type']/following-sibling::dd[1]");
	public static By obj_ShowAttachment_AttachedTo_Label = By.xpath("//dt[text()='Attached To:']/following-sibling::dd[1]/a");
	public static By obj_EditAttachments_FileName_Edit = By.xpath("//input[@id='attachment_file_name']");
	public static By obj_RemoveAttachment_Yes_Btn = By.xpath("//label[@id='delete_attach_switch']/div/span[text()='YES']");
	public static By obj_RemoveAttachment_No_Btn = By.xpath("//label[@id='delete_attach_switch']/div/span[text()='NO']");
	public static By obj_DeleteAttachmentSection_Message = By.xpath("//div[@id='delete_attachment_section']");
	public static By obj_ConfirmationMessage_close_Btn = By.xpath("//*[@id='flash_container']/div/div/div/button[@class='close']");
	public static By obj_Attachments_Pagination = By.xpath("//div[@id='divCaseAttachmentsContainer']//div[text()='Displaying Page 1 of 1 (1 records)']");
	public static By obj_Address_Tab = By.xpath("//a[text()='Addresses']");
	public static By obj_Address_Add_Btn = By.xpath("//a[@title='Add' and contains(@href, 'address')]");
	public static By obj_Address_Address1_Search_Edit = By.xpath("//div[@id='divAddressesContainer']//tr/td[contains(@aria-label, 'Address 1')]/div//input");
	public static By obj_ClientAddress_Address1_Search_Edit = By.xpath("//div[@id='divCompanyAddressesContainer']//tr/td[contains(@aria-label, 'Address 1')]/div//input");
	public static By obj_NewAddress_Create_Btn = By.xpath("//div[@class='form-group']//button[@name='button']");
	public static By obj_address_NewSupplierAddress_dialog = By.xpath("//h4[text()='New Supplier Address']");
	public static By obj_address_NewCompanyAddress_dialog = By.xpath("//h4[text()='New Company Address']");
	public static String obj_Address_Grid = "//div[@id='divAddressesContainer']";
	public static String obj_AddressTable_Grid = "//div[contains(@id, 'AddressesContainer')]";
	public static By obj_Address_AddressType_List = By.xpath("//select[contains(@id, '_address_address_type_id')]");
	public static By obj_Address_Country_Label = By.xpath("//div[contains(@id, '_address_country_chosen')]");
	public static By obj_Address_Country_Search_Edit = By.xpath("//div[contains(@id, '_address_country_chosen')]//div[@class='chosen-search']/input");
	public static By obj_Address_Country_SearchResult_Label = By.xpath("//ul[@class='chosen-results']/li[contains(@class, 'active-result result-selected highlighted')]");
	public static By obj_Address_Address1_Edit = By.xpath("//input[contains(@id, '_address_address_1')]");
	public static By obj_Address_Address2_Edit = By.xpath("//input[contains(@id, '_address_address_2')]");
	public static By obj_Address_Address3_Edit = By.xpath("//input[contains(@id, '_address_address_3')]");
	public static By obj_Address_City_Edit = By.xpath("//input[contains(@id, '_address_city')]");
	public static By obj_Address_StateProvince_Label = By.xpath("//div[contains(@id, '_address_state_province_chosen')]");
	public static By obj_Address_StateProvince_Search_Edit = By.xpath("//div[contains(@id, '_address_state_province_chosen')]//div[@class='chosen-search']/input");
	public static By obj_Address_StateProvince_SearchResult_Label = By.xpath("//ul[@class='chosen-results']/li[contains(@class, 'highlighted')]");
	public static By obj_Address_PostalCode_Edit = By.xpath("//input[contains(@id, '_address_postal_code')]");
	public static By obj_Address_Language_Edit = By.xpath("//input[contains(@id, '_address_language')]");
	public static String obj_Address_Table_Grid = "//table[contains(@class, '-fixed dx-datagrid-table-content')]/tbody";
	public static By obj_Address_ShowSupplierAddress_Dialog = By.xpath("//div[@class='modal fade show']//h4[contains(text(), ' Address')]");
	public static String obj_Address_ShowAddress_Grid = "//div[@class='modal-body']";
	public static By obj_Address_ShowAddress_close_Btn = By.xpath("//h4[text()='Supplier Address']/following-sibling::button[@class='close']");
	public static By obj_address_EditSupplierAddress_dialog = By.xpath("//h4[text()='Edit Supplier Address']");
	public static By obj_address_EditCompanyAddress_dialog = By.xpath("//h4[text()='Edit Company Address']");
	public static By obj_Address_Grid_WithNoData = By.xpath("//span[@class='dx-datagrid-nodata'][text()='No data']");
	public static By obj_AddressGrid_Pagination_NoData_Label = By.xpath("//div[text()='Displaying Page 1 of 1 (0 records)']");
	public static String obj_StateProvince_List = "//select[contains(@id, '_address_state_province')]";

	public static By obj_Companies_Grid_Checkbox = By.xpath("//div[@class='dataTables_scrollHeadInner']/table//tr/th/input[@type='checkbox']");
	public static By obj_CaseNumber_Grid_Pagination = By.xpath("//div[contains(text(), 'Displaying Page 1 of ')]");
	public static String obj_Filter_OK_Btn = "//div[@aria-label='OK']";

	public static String obj_PaymentBasedEnrollments_FilterOptions_Label = "//div[@class='dx-popup-content']//div[contains(@class, 'dx-item dx-list-item')]/div[contains(@class, 'list-item-content')]";
	public static String obj_Enrollment_Case_Grid = "//div[contains(@class,'dx-datagrid-rowsview')]";

	public static String obj_Enrollment_Case_FilterValue = "//div[contains(@class,'dx-item dx-list-item')]";
	public static By obj_CloudWatchErrorLogs_Link = By.xpath("//a[text()='CloudWatch Error Logs']");
	public static By obj_RerunSinglePaymentPoll_Btn = By.xpath("//button[@id='btn_rerun_single_payment_poll']");
	public static String obj_CloudWatchErrorLogs_Header = "//div[@id='cloudwatch_logs']//h3";
	public static String obj_CloudWatchErrorLog_Grid = "//div[@class='dataTables_scrollHeadInner']";
	public static By obj_RerunSinglePaymentPoll_Run_Btn = By.xpath("//button[@id='btn_submit_rerun_single_payment_poll']");
	public static String obj_RerunSinglePaymentPoll_Dialog = "//div[@id='modal_rerun_single_payment_poll']";
	public static By obj_RerunSinglePaymentPoll_Status = By.xpath("//div[@id='outcome' and text()='Success']");
	public static By obj_AdditionalAction_ViewCase_Link = By.xpath("//li[@id='payment_case']/a/span");
	public static By obj_RerunSinglePaymentPoll_Close_Btn = By.xpath("//div[@id='modal_rerun_single_payment_poll']//button[@class='close']");
	public static By obj_WhiteListGroup_IPAddress_Grid_Header = By.xpath("//div[@id='table_whitelist_groups_wrapper']/parent::div/h3");
	public static By obj_Dev_List = By.xpath("//ul[@class='navbar-nav ml-auto']/li/a/span[text()='Dev ']");
	public static By obj_Settings_Link = By.xpath("//a[text()='Settings']");
	public static By obj_ManualAPIPolling_Link = By.xpath("//a[text()='Manual API Polling']");
	public static By obj_LinkManagement_Link = By.xpath("//a[text()='Link Management']");
	public static By obj_SupportCasePolling_Link = By.xpath("//a[text()='Support Case Polling']");
	public static By obj_Suppliers_search_Loading = By.xpath("//div[@class='dx-overlay-content dx-resizable dx-loadpanel-content']//div[text()='Loading records...']");
	public static By obj_RunAPIPoll_Btn = By.xpath("//button[@id='btn_submit']");
	public static By obj_MoveLink_Btn = By.xpath("//button[@id='btn_submit_link_swap']");
	public static By obj_Add_Link = By.xpath("//a[@title='Add']");
	public static String obj_NewApplicationSetting_Dialog = "//div[@id='modal_form_application_setting']";
	public static By obj_ApplicationSetting_Name_Edit = By.xpath("//input[@id='application_setting_name']");
	public static By obj_ApplicationSetting_Value_Edit = By.xpath("//input[@id='application_setting_value']");
	public static By obj_ApplicationSetting_ConfirmationMessage_Label = By.xpath("//div[@class='alert alert-info custom-alert-info']");
	public static String obj_ApplicationSetting_Grid = "//div[@id='divApplicationSettingsContainer']";
	public static By obj_ApplicationSetting_Edit_Btn = By.xpath("//a[@title='Edit']");
	public static By obj_ClientAddress_Show_Close_Btn = By.xpath("//div[@id='modal_show_company_address']//button[@class='close']");
	public static By obj_PaymentProcess_Link = By.xpath("//a[contains(@href, 'payment_processes') and contains(text(), 'Payment')]");
	public static String obj_PaymentProcess_Grid = "//div[@id='divPaymentProcessesGridContainer']";
	public static By obj_SupplierToggle_Btn = By.xpath("//form[@id='new_payment_processes_form']");
	public static String obj_PaymentProcess_dialog = "//div[contains(@id, 'modal_payment_processes')]";
	public static String obj_PaymentProcess_CreatePaymentProcess="//div[normalize-space(text()) = 'Create Payment Process']";
	public static String obj_PaymentProcess_EditPaymentProcess="//div[normalize-space(text()) = 'Edit Payment Process']";
	public static By obj_Supplier_Toggle_Green_Btn = By.xpath("//input[@id='is_supplier_payment_process']/../div");
	public static By obj_AccountNumbers_Edit = By.xpath("//input[@class='chosen-search-input default']");
	public static String obj_Payment_MethodChooser_Label = "//select[@id='payment_process_payment_process_method_id']";
	public static By obj_Payment_Address_Edit = By.xpath("//input[@id='payment_process_payment_process_method_address']");
	public static String obj_ServiceInformation_Value_Edit = "//input[@name='payment_process[service_info][][value]']";
	public static String obj_ServiceInformation_AddPlus_Btn = "//a[@class='glyph-icon icon-plus add-service-info']";
	public static String obj_AdditionalInformation_Name_Edit = "//input[@name='payment_process[additional_info][][key]']";
	public static String obj_AdditionalInformation_Value_Edit = "//input[@name='payment_process[additional_info][][value]']";
	public static String obj_AdditionalInformation_AddPlus_Btn = "//a[@class='glyph-icon icon-plus add-additional-info']";
	public static String obj_PaymentService_Steps_Edit = "//input[@class='input-miller step-instruction']";
	public static By obj_Payment_Notes_Textarea = By.xpath("//textarea[@id='payment_process_notes']");
	public static String obj_PaymentProcess_Show_Dialog = "//div[@id='modal_show_payment_process']";
	public static String obj_PaymentProcess_Section = "(//div[@class='d-flex flex-column gap-1']//div)[2]";
	public static String obj_RemoveAccountNumbers_Btn = "//div[@id='payment_process_account_number_chosen']//a[@class='search-choice-close']";
	public static String obj_Remove_ServiceInformation_Btn = "//span[contains(@class, 'remove-service-info')]";
	public static String obj_Remove_AdditionalInformation_Btn = "//span[@class='btn-miller icon-button secondary danger remove-additional-info']";
	public static String obj_Remove_Steps_Btn = "//span[@class='btn-miller icon-button secondary danger remove-step']";
	public static String obj_Remove_SecurityQuestions_Btn = "//span[contains(@class, 'remove-security-questions')]";
	public static String obj_SecurityQuestions_Edit = "//input[contains(@class, 'security-questions-key')]";
	public static String obj_SecurityAnswers_Edit = "//input[contains(@class, 'security-questions-value')]";
	public static By obj_AddQuestions_Btn = By.xpath("//div[contains(@class, 'add-security-questions')]");
	public static By obj_Search_Edit = By.xpath("//input[@id='search']");
	public static By obj_SearchIcon_Btn = By.xpath("//button//*[@class='fa-solid fa-magnifying-glass']");
	public static By obj_GetStarted_Btn = By.xpath("//button[@id='submit_code' and contains(text(), 'Get Started')]");
	public static By obj_ActivationCode_Edit = By.xpath("//input[@id='landing_access_code']");
	public static String obj_Company_IndividualName_Edit = "//input[@id='txtSupplierName']";
	public static String obj_TaxID_SSN_Edit = "//input[@id='txtTaxID']";
	public static String obj_PhysicalAddress_Edit = "//input[@id='txtStreet']";
	public static String obj_City_Edit = "//input[@id='txtCity']";
	public static String obj_State_List = "//select[@id='dpdState']";
	public static String obj_ZipCode_Edit = "//input[@id='txtZipCode']";
	public static String obj_TaxClarification_Grid = "//div[@id='step1']";
	public static String obj_FirstName_Edit = "//input[@id='txtFirstName']";
	public static String obj_LastName_Edit = "//input[@id='txtLastName']";
	public static String obj_EmailAddress_Edit = "//input[@id='txtEmail']";
	public static String obj_PhoneNumber_Edit = "//input[@id='txtPhoneNumber']";
	public static By obj_PhoneExtn_Edit = By.xpath("//input[@id='txtPhoneExt']");
	public static By obj_PaymentNotificationEmail_Edit = By.xpath("//input[@id='txtPaymentNotificationEmailRecipient']");
	public static By obj_Continue_Btn = By.xpath("//button[@id='btnContinue']");
	public static By obj_SelectPaymentTerm_List = By.xpath("//select[@id='txtPaymentTerms']");
	public static By obj_ElectronicPaymentPage_Header = By.xpath("//div[@id='page_2']//span[@class='section-header']");
	public static By obj_EmailForVisaCreditDetails_Edit = By.xpath("//input[@id='txtEmailAddressVirtualCard']");
	public static By obj_ClarifyEmpCompany_ChkBox = By.xpath("//input[@id='chkEmployeeCompany']");
	public static By obj_TermsAndCondition_ChkBox = By.xpath("//input[@id='chkTermsAndConditions']");
	public static By obj_Submit_Btn = By.xpath("//input[@id='btnSumbit']");
	public static String obj_DetailsCaptured_Grid = "//div[@id='pdf_data']";
	public static String obj_DuplicateSupplierManagement_Loaded_Grid = "//div[@id='divDuplicateSuppliersContainer']";
	public static String obj_Enrollment_Search_Case_Grid = "//table[@id='case_results']";
	public static By obj_AlertWarning_Msg = By.xpath("//div[@class='alert alert-warning']");
	public static String obj_Activities_Grid = "//div[@id='divCaseActivitiesDashboardContainer']";
	public static String obj_ManualAPIPolling_BtnType = "//div[@id='btn-type-status']";
	public static String obj_LinkPollingResult_Grid = "//table[@id='polling_output_link']";
	public static String obj_LinkPollingPaymentResult_Grid = "//table[@id='polling_output_payment']";
	public static By obj_LinkPollingResult_Header = By.xpath("//div[@id='link_poll_table']//h4/span");
	public static By obj_PaymentPollingResults_Header = By.xpath("//div[@id='payment_poll_table']//h4/span");
	public static String obj_Companies_Grid = "//table[@id='table_dashboard_open_case_companies']";
	public static String obj_Communities_Grid = "//table[@id='table_communities']";
	public static By obj_FileUpload_Edit = By.xpath("//input[@id='FileUploaderAttachment']");
	public static String obj_AgedOnly_Toggle = "//input[@id='aged_only_filter']";
	public static String obj_SpendOnly_Toggle = "//input[@id='spend_only_filter']";
	public static String obj_IncludeHold_Enrollment_Toggle = "//input[@id='inrollment_include_hold_filter']";
	public static String getObj_IncludeHold_Payment_Toggle = "//input[@id='include_hold_filter']";
	public static String obj_FirstPaymentOnly_Toggle = "//input[@id='first_time_payments_only_filter']";
	public static By obj_NumberOfChecks_Edit = By.xpath("//input[@id='check_count_filter']");
	public static By obj_TotalSpend_Edit = By.xpath("//input[@id='total_spend_filter']");
	public static By obj_DueDateDays_Edit = By.xpath("//input[@id='value_date_days_filter']");
	public static By obj_PaymentAmount_Edit = By.xpath("//input[@id='total_payment_amount_filter']");
	public static String obj_CaseType_Toggle = "//input[@id='chk_all_case_type_toggle_filter']";
	public static String obj_CaseStatuses_Toggle = "//input[@id='chk_all_status_toggle_filter']";
	public static String obj_SupplierPriorities_Toggle = "//input[@id='chk_all_supplier_priority_toggle_filter']";
	public static String obj_DueDateRange_Toggle = "//input[@id='chk_all_value_date_toggle_filter']";
	public static String obj_OfferType_Toggle = "//input[@id='chk_all_case_type_toggle_filter']";
	public static String obj_CasePriorities_Toggle = "//input[@id='chk_all_case_priority_toggle_filter']";
	public static String obj_AssignedTo_Toggle = "//input[@id='chk_all_user_toggle_filter']";
	public static By obj_Filter_Btn = By.xpath("//button[@id='btn_filter']");
	public static By obj_Companies_SelectAll_CheckBox = By.xpath("//th[contains(@class, 'dt-checkboxes-select-all')]/input[@type='checkbox']");
	public static String obj_Companies_Dashboard_Grid = "//table[@id='table_dashboard_open_case_companies']";
	public static By obj_AppliedFilter_List = By.xpath("//select[@id='queue_view_select']");
	public static String obj_PartnerType_Toggle = "//input[@id='chk_all_partner_type_toggle_filter']";
	public static String obj_Partner_CaseStatuses_Toggle = "//input[@id='chk_all_status_toggle_filter']";
	public static String obj_Partner_AssignedTo_Toggle = "//input[@id='chk_all_user_toggle_filter']";
	public static String obj_Companies_Header_Grid = "//div[@class='dataTables_scroll']";
	public static String obj_Companies_Data_Grid = "//table[@id='table_dashboard_open_case_companies']";
	public static By obj_ManualAPIPoling_StartDate_Edit = By.xpath("//input[@id='startdate']");
	public static By obj_ManualAPIPoling_EndDate_Edit = By.xpath("//input[@id='enddate']");
	public static String obj_PaymentTab_Link = "//*[@id='secondtab']";
	public static String obj_DuplicateSupplierFound_Dialog = "//div[@id='duplicate_supplier_found']";
	public static By obj_InvalidActivationCode_Label = By.xpath("//div[@id='access_code_invalid']");
	public static By obj_AdditionalInformation_Textarea = By.xpath("//textarea[@id='txtAdditionalInfoModal']");
	public static By obj_UseExistingContact_Btn = By.xpath("//a[contains(text(), 'Use existing contact')]");
	public static By obj_UseNewContact_Btn = By.xpath("//a[contains(text(), 'Use new contact')]");
	public static String obj_UseExistingContact_Pane = "//div[@id='existing_contact']";
	public static String obj_FullName_Edit = "//input[@id='txtFullNameModal']";
	public static String obj_Email_Edit = "//input[@id='txtEmailModal']";
	public static String obj_Phone_Edit = "//input[@id='txtPhoneModal']";
	public static By obj_Extension_Edit = By.xpath("//input[@id='txtPhoneExtModal']");
	public static String obj_EmailResults_Section = "//div[@id='email_results']";
	public static By obj_EnrollNow_Btn = By.xpath("//a[text()='Enroll now']");
	public static String obj_ServiceInformation_List = "//div/input[@name='payment_process[service_info][][key]']";
	public static String obj_ServiceInformation_vlaue = "//div/input[@class='input-miller service-info-value']";
	public static String obj_ServiceInformation_Addvlaue_btn = "//div[@class='btn-miller secondary add-service-info w--fit-content !p--2']";
	public static String obj_ServiceInformation_Sec = "//table[@id='payment_process_service_info_table']";
	public static String obj_AdditionalInformation_Addvlaue_btn = "//div[@class='btn-miller secondary add-additional-info w--fit-content !p--2']";
	public static By obj_Payment_Steps_Add_Btn = By.xpath("//div[contains(@class, 'add-step')]");
	public static String obj_Payment_create_Btn="//button[@id='btn_payment_process_submit']";
	public static String obj_EditPayments_Btn = ("//div[@id='button_row']//a[2]");
	public static String obj_DeletePayments_Btn = ("//div[@id='button_row']//a[1]");
	public static String obj_ViewPaymentProcess = "//div[@class='text-header-miller']";
	public static By obj_CompaniesTable_Loading = By.xpath("//table[@id='table_dashboard_open_case_companies']//td[@class='dataTables_empty'][contains(text(), 'Loading...')]");
	public static By obj_setOfferButton = By.id("btn_set_offer");
	public static By obj_setOfferButtonInPopup = By.id("set_offer_case");
	public static By obj_refreshButton = By.id("btn_quick_refresh");
}
