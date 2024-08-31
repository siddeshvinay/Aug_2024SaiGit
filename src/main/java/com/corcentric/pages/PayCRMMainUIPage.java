package com.corcentric.pages;

import org.openqa.selenium.By;

public class PayCRMMainUIPage {
	public static By obj_UserName_Edit = By.xpath("//input[@name='email']");
	public static By obj_Password_Edit = By.xpath("//input[@name='password']");
	public static By obj_LOGIN_btn = By.xpath("//button[@name='submit']");
	public static By obj_EnrollmentsManager_Label = By.xpath("//h3[contains(text(),'Enrollments Manager')]");
	public static By obj_PartnerEnrollments_Label = By.xpath("//h3[contains(text(),'Partner Enrollments')]/..");
	public static By obj_AssistedPaymentServices_Label = By.xpath("//h3[contains(text(),'Assisted Payment Services')]");
	public static By obj_Logout_Navbar_List = By.xpath("//a[@class='truncate-sm dropdown-toggle nav-link']");
	public static String obj_Logout_Link = "//a[text()='%s']";
	public static By obj_Roles_visibility = By.xpath("//div[@class='single-loader' and @style='display: none;']");
	public static By obj_Profile_Update_Bt = By.id("btn_edit_user");
	
}
