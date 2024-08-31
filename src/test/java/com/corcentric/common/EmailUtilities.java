package com.corcentric.common;

import com.corcentric.pages.AssistedPaymentServicesUIPage;
import com.corcentric.runner.CucumberTestRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import java.util.Map;

public class EmailUtilities extends CucumberTestRunner {
    public static By EmailPhone_Edit = By.xpath("//input[@name='loginfmt']");
    public static By obj_EmailPhone_Next_btn = By.xpath("//input[@id='idSIButton9']");
    public static By obj_AccountType_Label = By.xpath("//div[@id='aadTileTitle']");
    public static By obj_Password_Edit = By.xpath("//input[@name='passwd']");
    public static By obj_Password_Submit_btn = By.xpath("//input[@id='idSIButton9']");
    public static By obj_DontShowthisAgain_Chkbox = By.xpath("//span[contains(text(), ' show this again')]");
    public static By obj_Inbox_Link = By.xpath("(//div[@draggable]//span[text()='Inbox'])[1]");
    public static By obj_Focused_Label = By.xpath("//span[text()='Focused']");
    public static String obj_Inbox_Mails_Scrollbar = "//div[contains(@class, 'customScrollBar')]//div[contains(@class, 'customScrollBar')]";
    public static String obj_Inbox_MailBody_Section = "//div[@id='ReadingPaneContainerId']";
    public static By obj_Delete_Btn = By.xpath("//button[@aria-label='Delete']");
    public static String obj_EmailMessage_Section = "//div[@*='Email message']";
    public static String obj_Attachment_MoreActions_Btn = "//div[@*='attachments']//button/span";
    public static String obj_Download_Btn = "//button[@name='Download']//span[text()='Download']";
    public static String obj_MessageBody_Section = "//div[@*='Message body']";
    public static String obj_To_Receipient_Label = "//a[@class='search-choice-close']";
    public static By obj_Other_Link = By.xpath("//span[@*='splitbuttonprimary']//span[text()='Other']");



    /********************************************************
     * Method Name      : loginToOutlookEmail()
     * Purpose          : Login to the Outlook email application
     * Author           : Gudi
     * Parameters       : oEmailBrowser, queueName
     * ReturnType       : boolean
     ********************************************************/
    public boolean loginToOutlookEmail(WebDriver oEmailBrowser, String appName, String qaDetails){
        String arrDetails[];
        try{
            arrDetails = appInd.getPropertyValueByKeyName(qaDetails).split("#", -1);
            if(oEmailBrowser!=null){
                Assert.assertTrue(appDep.navigateURL(oEmailBrowser, arrDetails[0], appName), "URL navigation is failed for '"+arrDetails[0]+"' URL");

                appDep.waitForTheElementState(oEmailBrowser, EmailPhone_Edit, "Clickable", "", waitTimeOut);
                Assert.assertTrue(appInd.setObject(oEmailBrowser, EmailPhone_Edit, appInd.getPropertyValueByKeyName(arrDetails[1])), "setObject() was failed for '"+String.valueOf(EmailPhone_Edit)+"' webelement");
                Assert.assertTrue(appInd.javaScriptClickObject(oEmailBrowser, obj_EmailPhone_Next_btn));
                boolean blnRes = appDep.waitForTheElementState(oEmailBrowser, obj_AccountType_Label, "Clickable", "", 10);
                if(blnRes) Assert.assertTrue(appInd.javaScriptClickObject(oEmailBrowser, obj_AccountType_Label), "Failed to click '"+String.valueOf(obj_AccountType_Label)+"' button");
                Assert.assertTrue(appInd.setObject(oEmailBrowser, obj_Password_Edit, appInd.getPropertyValueByKeyName(arrDetails[2])), "setObject() was failed for '"+String.valueOf(obj_Password_Edit)+"' webelement");
                Assert.assertTrue(appInd.javaScriptClickObject(oEmailBrowser, obj_Password_Submit_btn), "clickObject() was failed for '"+String.valueOf(obj_Password_Submit_btn)+"' webelement");
                appDep.waitForTheElementState(oEmailBrowser, obj_DontShowthisAgain_Chkbox, "Clickable", "", waitTimeOut);
                Assert.assertTrue(appInd.javaScriptClickObject(oEmailBrowser, obj_DontShowthisAgain_Chkbox));
                Assert.assertTrue(appInd.javaScriptClickObject(oEmailBrowser, obj_Password_Submit_btn), "clickObject() was failed for '"+String.valueOf(obj_Password_Submit_btn)+"' webelement");
                appDep.waitForTheElementState(oEmailBrowser, obj_Inbox_Link, "Clickable", "", waitTimeOut);
                Assert.assertTrue(appInd.verifyElementPresent(oEmailBrowser, obj_Inbox_Link));
                reports.writeResult(oEmailBrowser, "Screenshot", "Login to the '"+appName+"' was successful");
                return true;
            }else{reports.writeResult(oEmailBrowser, "Fail", "Failed to launch the '"+ appInd.browserName+"' browser for email validation");
                Assert.assertTrue(false, "Failed to launch the '"+ appInd.browserName+"' browser");
                return false;
            }
        }catch(Exception e) {
            reports.writeResult(oEmailBrowser, "Exception", "Exception in 'loginToOutlookEmail()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oEmailBrowser, "Exception", "AssertionError in 'loginToOutlookEmail()' method. " + e);
            return false;
        }finally {arrDetails = null;}
    }



    /********************************************************
     * Method Name      : connectAndOpenEmails()
     * Purpose          : to launch the required browser (Chrome, Firefox, Edge)
     * Author           : Gudi
     * Parameters       : emailData
     * ReturnType       : boolean
     ********************************************************/
    public boolean connectAndOpenEmails(Map<String, String> emailData){
        try{
            oEmailBrowser = appInd.launchBrowser();
            boolean blnRes = loginToOutlookEmail(oEmailBrowser, "OutLook", "emailDetails");
            if(blnRes){
                Assert.assertTrue(appInd.javaScriptClickObject(oEmailBrowser, obj_Inbox_Link));
                appDep.waitForTheElementState(oEmailBrowser, obj_Focused_Label, "Text", "Focused", waitTimeOut);
                Assert.assertTrue(appInd.clickObject(oEmailBrowser, obj_Focused_Label));
                appDep.threadSleep(2000);
                Assert.assertTrue(appInd.verifyElementPresent(oEmailBrowser, By.xpath(obj_Inbox_Mails_Scrollbar)));
                reports.writeResult(oEmailBrowser, "Screenshot", "Clicking on Inbox in the Web Outlook");

                blnRes = appInd.verifyOptionalElementPresent(oEmailBrowser, By.xpath("//div[contains(@aria-label, '"+(emailData.get("Subject").split("'"))[0]+"')]"));
                appDep.threadSleep(1000);
                if(!blnRes) {
                    Assert.assertTrue(appInd.clickObject(oEmailBrowser, obj_Other_Link));
                    appDep.threadSleep(1000);
                }

                Assert.assertTrue(appInd.clickObject(oEmailBrowser, By.xpath("//div[contains(@aria-label, '"+(emailData.get("Subject").split("'"))[0]+"')]")));
                appDep.threadSleep(2000);
                appDep.waitForTheElementState(oEmailBrowser, By.xpath(obj_EmailMessage_Section + "//div[contains(text(), '"+emailData.get("Message").substring(0, 34)+"')]"), "Clickable", "", minTimeOut);
                appDep.threadSleep(5000);
                Assert.assertTrue(appInd.getTextOnElement(oEmailBrowser, By.xpath("("+obj_Inbox_MailBody_Section + "//div[@title])[1]"), "Text").contains(emailData.get("Subject")));
                String mailTo = appInd.getTextOnElement(oEmailBrowser, By.xpath(obj_EmailMessage_Section + "//span[contains(text(), '@corcentric.com')]"), "Text");
                Assert.assertTrue(appInd.compareValues(oEmailBrowser, mailTo, appInd.getPropertyValueByKeyName("qaUserName")) || appInd.compareValues(oEmailBrowser, mailTo, emailData.get("From")));
                reports.writeResult(oEmailBrowser, "Screenshot", "Web Outlook Inbox for the selected email");
            }else{
                reports.writeResult(oEmailBrowser, "Fail", "Failed to login to Web Outlook");
                Assert.fail("Failed to login to Web Outlook");
            }
            return true;
        }catch(Exception e) {
            reports.writeResult(oEmailBrowser, "Exception", "Exception in 'connectAndOpenEmails()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oEmailBrowser, "Exception", "AssertionError in 'connectAndOpenEmails()' method. " + e);
            return false;
        }
    }





    /********************************************************
     * Method Name      : readEmailFromPaymentQuestions()
     * Purpose          : to read the mail triggered from the 'Payment Questions' functionality
     * Author           : Gudi
     * Parameters       : oEmailBrowser, issueOption, emailData
     * ReturnType       : boolean
     ********************************************************/
    public boolean readEmailFromPaymentQuestions(WebDriver oEmailBrowser, String issueOption, Map<String, String> emailData){
        String bodyContent = null;
        String message = null;
        try{
            reports.writeResult(oEmailBrowser, "Screenshot", "Validating the email for the '"+emailData.get("Issue")+"', '"+emailData.get("InvoiceIssue")+"' and '"+emailData.get("WhyOver_UnderPaid")+"' option value combination");

            //Verify the supplier detials in the email body:
            bodyContent = oEmailBrowser.findElement(By.xpath("(//table[@class='x_module']//span)[1]")).getText();
            if(!emailData.get("SupplierName").isEmpty()) Assert.assertTrue(bodyContent.contains("Supplier Name: " + emailData.get("SupplierName")), "The invalid supplier name '"+emailData.get("SupplierName")+"' was displayed. Line Number: "+appInd.getCurrentLineNumber());
            else Assert.assertTrue(bodyContent.contains("Supplier Name:"), "The invalid supplier name '"+emailData.get("SupplierName")+"' was displayed. Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(bodyContent.contains("PayCRM Case Number: "+emailData.get("CaseNumber")), "The invalid Case Number '"+emailData.get("CaseNumber")+"' was displayed. Line Number: "+appInd.getCurrentLineNumber());
            if(!emailData.get("SupplierID").isEmpty()) Assert.assertTrue(bodyContent.contains("Supplier ID: "+ emailData.get("SupplierID")), "The invalid supplier ID '"+emailData.get("SupplierID")+"' was displayed. Line Number: "+appInd.getCurrentLineNumber());
            else Assert.assertTrue(bodyContent.contains("Supplier ID:"), "The invalid supplier ID '"+emailData.get("SupplierID")+"' was displayed. Line Number: "+appInd.getCurrentLineNumber());
            if(!emailData.get("RemitTo").isEmpty()) Assert.assertTrue(bodyContent.contains("Remit to ID: " + emailData.get("RemitTo")), "The invalid Remit To '"+emailData.get("v")+"' was displayed. Line Number: "+appInd.getCurrentLineNumber());
            else Assert.assertTrue(bodyContent.contains("Remit to ID:"), "The invalid Remit To '"+emailData.get("v")+"' was displayed. Line Number: "+appInd.getCurrentLineNumber());
            if(!emailData.get("RequestPaymentID").isEmpty()) Assert.assertTrue(bodyContent.contains("Payment ID: " + emailData.get("RequestPaymentID")), "The invalid Payment ID '"+emailData.get("RequestPaymentID")+"' was displayed. Line Number: "+appInd.getCurrentLineNumber());
            else Assert.assertTrue(bodyContent.contains("Payment ID:"), "The invalid Payment ID '"+emailData.get("RequestPaymentID")+"' was displayed. Line Number: "+appInd.getCurrentLineNumber());
            if(!emailData.get("ReferenceNumber").isEmpty()) Assert.assertTrue(bodyContent.contains("Payment Reference: " + emailData.get("ReferenceNumber")), "The invalid Reference Number '"+emailData.get("ReferenceNumber")+"' was displayed. Line Number: "+appInd.getCurrentLineNumber());
            else Assert.assertTrue(bodyContent.contains("Payment Reference:"), "The invalid Reference Number '"+emailData.get("ReferenceNumber")+"' was displayed. Line Number: "+appInd.getCurrentLineNumber());
            if(!emailData.get("PaymentAmount").isEmpty()) Assert.assertTrue(bodyContent.contains("Payment Amount: " + emailData.get("PaymentAmount")), "The invalid Payment Amount '"+emailData.get("PaymentAmount")+"' was displayed. Line Number: "+appInd.getCurrentLineNumber());
            else Assert.assertTrue(bodyContent.contains("Payment Amount:"), "The invalid Payment Amount '"+emailData.get("PaymentAmount")+"' was displayed. Line Number: "+appInd.getCurrentLineNumber());
            if(!emailData.get("Invoices").isEmpty()) Assert.assertTrue(bodyContent.contains("Invoices(s) in Question: " + emailData.get("Invoices")), "The invalid Invoices '"+emailData.get("Invoices")+"' was displayed. Line Number: "+appInd.getCurrentLineNumber());
            else Assert.assertTrue(bodyContent.contains("Invoices(s) in Question:"), "The invalid Invoices '"+emailData.get("Invoices")+"' was displayed. Line Number: "+appInd.getCurrentLineNumber());

            message = oEmailBrowser.findElement(By.xpath(obj_EmailMessage_Section + "//div[contains(text(), '"+emailData.get("Message").substring(0, 34)+"')]")).getText();
            if(message.isEmpty()) message = oEmailBrowser.findElement(By.xpath(obj_EmailMessage_Section + "//div[contains(text(), '"+emailData.get("Message").substring(0, 34)+"')]")).getText();
            Assert.assertTrue(message.contains(emailData.get("Message"))||emailData.get("Message").contains(message), "Mis-match in actual '"+message+"' and Expected '"+emailData.get("Message")+"' message in the email body");

            reports.writeResult(oEmailBrowser, "Screenshot", "Inbox Mail was opened for reading purpose for the '"+emailData.get("Issue")+"', '"+emailData.get("InvoiceIssue")+"' and '"+emailData.get("WhyOver_UnderPaid")+"' option value combination");

            return true;
        }catch(Exception e) {
            reports.writeResult(oEmailBrowser, "Exception", "Exception in 'readEmailFromPaymentQuestions()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oEmailBrowser, "Exception", "AssertionError in 'readEmailFromPaymentQuestions()' method. " + e);
            return false;
        }finally{
            //delete the email
            Assert.assertTrue(appInd.javaScriptClickObject(oEmailBrowser, obj_Delete_Btn));
            appDep.threadSleep(4000);
            oEmailBrowser.close();
            bodyContent = null; oEmailBrowser = null;}
    }




    /********************************************************
     * Method Name      : readEmailFromSendMail()
     * Purpose          : to read the mail triggered from the 'Send Mail' functionality
     * Author           : Gudi
     * Parameters       : oEmailBrowser, emailData
     * ReturnType       : boolean
     ********************************************************/
    public boolean readEmailFromSendMail(WebDriver oEmailBrowser, Map<String, String> emailData){
        try{
            //Validate the attachment
            if(emailData.get("FileToUpload")!=null){
                Assert.assertTrue(appInd.verifyElementPresent(oEmailBrowser, By.xpath("("+obj_EmailMessage_Section + "//div[@*='file attachments']/div/div[contains(@aria-label, '"+emailData.get("FileToUpload")+"')])[1]")), "There are no attachments exist in the email");
                Assert.assertTrue(appInd.getTextOnElement(oEmailBrowser, By.xpath("("+obj_EmailMessage_Section + "//div[@*='file attachments']/div/div[contains(@aria-label, '"+emailData.get("FileToUpload")+"')])[1]"), "Text").contains(emailData.get("FileToUpload")));
                Assert.assertTrue(appInd.javaScriptClickObject(oEmailBrowser, By.xpath("(//button[@*='More actions'])[1]")));
                Assert.assertTrue(appInd.javaScriptClickObject(oEmailBrowser, By.xpath("("+obj_Download_Btn+")[1]")));
                appDep.waitForTheElementState(oEmailBrowser, AssistedPaymentServicesUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                appDep.threadSleep(5000);

                //Validate that the file downloaded successful
                boolean blnRes = appDep.validateDownloadedFileAndDelete(oEmailBrowser, emailData.get("FileToUpload"), ".pdf", "Dummy PDF file", "Yes");
                if (blnRes) {
                    reports.writeResult(oEmailBrowser, "Pass", "The 'Send Mail' attachment pdf file '"+emailData.get("FileToUpload")+"' was downloaded successful");
                } else {
                    reports.writeResult(oEmailBrowser, "Fail", "Failed to download the 'Send Mail' attachment pdf file '"+emailData.get("FileToUpload")+"'");
                    Assert.assertTrue(false, "Failed to download the 'Send Mail' attachment pdf file '"+emailData.get("FileToUpload")+"'");
                }
            }
            reports.writeResult(oEmailBrowser, "Screenshot", "Inbox Mail was opened for reading purpose for the 'Send Mail' functionality");
            Assert.assertTrue(appInd.verifyText(oEmailBrowser, By.xpath(obj_MessageBody_Section + "//div/div/div"), "Text", emailData.get("Message")));

            return true;
        }catch(Exception e) {
            reports.writeResult(oEmailBrowser, "Exception", "Exception in 'readEmailFromSendMail()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oEmailBrowser, "Exception", "AssertionError in 'readEmailFromSendMail()' method. " + e);
            return false;
        }finally{
            //delete the email
            Assert.assertTrue(appInd.javaScriptClickObject(oEmailBrowser, obj_Delete_Btn));
            appDep.threadSleep(4000);
            oEmailBrowser.close();
            oEmailBrowser = null;}
    }




    /********************************************************
     * Method Name      : readEmailFromPAFSendMail()
     * Purpose          : to read the mail triggered from the Enrollment case PAF 'Send Mail' functionality
     * Author           : Gudi
     * Parameters       : oEmailBrowser, emailData
     * ReturnType       : boolean
     ********************************************************/
    public boolean readEmailFromPAFSendMail(WebDriver oEmailBrowser, Map<String, String> emailData){
        try{
            //Validate the PAF attachment
            Assert.assertTrue(appInd.verifyElementPresent(oEmailBrowser, By.xpath("("+obj_EmailMessage_Section + "//div[@*='file attachments']/div/div[contains(@aria-label, '"+(emailData.get("generatedPAFAttachment").split("\\."))[1]+"')])[1]")), "There are no PAF attachments exist in the email");
            Assert.assertTrue(appInd.getTextOnElement(oEmailBrowser, By.xpath("("+obj_EmailMessage_Section + "//div[@*='file attachments']/div/div[contains(@aria-label, '"+(emailData.get("generatedPAFAttachment").split("\\."))[1]+"')])[1]"), "Text").replace("_", "").contains(emailData.get("generatedPAFAttachment").replace("_", "")));
            Assert.assertTrue(appInd.clickObject(oEmailBrowser, By.xpath("(//button[@*='More actions'])[1]")));
            Assert.assertTrue(appInd.clickObject(oEmailBrowser, By.xpath("("+obj_Download_Btn+")[1]")));
            appDep.waitForTheElementState(oEmailBrowser, AssistedPaymentServicesUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
            appDep.threadSleep(5000);
            emailData.put("generatedPAFAttachment", appInd.getTextOnElement(oEmailBrowser, By.xpath("("+obj_EmailMessage_Section + "//div[@*='file attachments']/div/div[contains(@aria-label, '"+(emailData.get("generatedPAFAttachment").split("\\."))[1]+"')])[1]//div[@title]"), "Text"));

            //Validate that the file downloaded successful
            boolean blnRes = appDep.validateDownloadedFileAndDelete(oEmailBrowser, emailData.get("generatedPAFAttachment"), ".pdf", "", "Yes");
            if (blnRes) {
                reports.writeResult(oEmailBrowser, "Pass", "The '"+emailData.get("generatedPAFAttachment")+"' file from PAF send mail was downloaded successful");
            } else {
                reports.writeResult(oEmailBrowser, "Fail", "Failed to download the '"+emailData.get("generatedPAFAttachment")+"' file from PAF send mail");
                Assert.assertTrue(false, "Failed to download the '"+emailData.get("generatedPAFAttachment")+"' file from PAF send mail");
            }

            if(emailData.get("FileToUpload")!=null){
                Assert.assertTrue(appInd.verifyElementPresent(oEmailBrowser, By.xpath("("+obj_EmailMessage_Section + "//div[@*='file attachments']/div/div[contains(@aria-label, '"+emailData.get("generatedPAFAttachment")+"')])[2]")), "There are no attachments exist in the email");
                Assert.assertTrue(appInd.verifyText(oEmailBrowser, By.xpath("("+obj_EmailMessage_Section + "//div[@*='file attachments']/div/div[contains(@aria-label, '"+emailData.get("generatedPAFAttachment")+"')])[2]"), "Text", emailData.get("FileToUpload")));
                Assert.assertTrue(appInd.clickObject(oEmailBrowser, By.xpath("(//button[@*='More actions'])[2]")));
                Assert.assertTrue(appInd.clickObject(oEmailBrowser, By.xpath("("+obj_Download_Btn+")[1]")));
                appDep.waitForTheElementState(oEmailBrowser, AssistedPaymentServicesUIPage.obj_search_Loading, "Invisibility", "", waitTimeOut);
                appDep.threadSleep(5000);

                //Validate that the file downloaded successful
                blnRes = appDep.validateDownloadedFileAndDelete(oEmailBrowser, emailData.get("FileToUpload"), ".pdf", "Dummy PDF file", "Yes");
                if (blnRes) {
                    reports.writeResult(oEmailBrowser, "Pass", "The '"+emailData.get("FileToUpload")+"' file from PAF send mail was downloaded successful");
                } else {
                    reports.writeResult(oEmailBrowser, "Fail", "Failed to download the '"+emailData.get("FileToUpload")+"' file from PAF send mail");
                    Assert.assertTrue(false, "Failed to download the '"+emailData.get("FileToUpload")+"' file from PAF send mail");
                }
            }
            reports.writeResult(oEmailBrowser, "Screenshot", "Inbox Mail was opened for reading purpose for the 'PAF Send Mail' functionality");
            Assert.assertTrue(appInd.compareValues(oEmailBrowser, appInd.getTextOnElement(oEmailBrowser, By.xpath(obj_MessageBody_Section + "//div/div/div"), "Text").replace("\n", ""), emailData.get("Message").trim().replace("\n", "")));

            return true;
        }catch(Exception e) {
            reports.writeResult(oEmailBrowser, "Exception", "Exception in 'readEmailFromPAFSendMail()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oEmailBrowser, "Exception", "AssertionError in 'readEmailFromPAFSendMail()' method. " + e);
            return false;
        }finally{
            //delete the email
            Assert.assertTrue(appInd.javaScriptClickObject(oEmailBrowser, obj_Delete_Btn));
            appDep.threadSleep(4000);
            oEmailBrowser.close();
            oEmailBrowser = null;
        }
    }




    /********************************************************
     * Method Name      : readEmailFromResendVoidNotification()
     * Purpose          : to read the mail triggered from the 'Re-Send Void Notification' functionality
     * Author           : Gudi
     * Parameters       : oEmailBrowser, emailData
     * ReturnType       : boolean
     ********************************************************/
    public boolean readEmailFromResendVoidNotification(WebDriver oEmailBrowser, Map<String, String> emailData){
        String messageBody = null;
        try{
            reports.writeResult(oEmailBrowser, "Screenshot", "Inbox Mail was opened for reading purpose for the 'Send Mail' functionality");
            messageBody = appInd.getTextOnElement(oEmailBrowser, By.xpath("("+obj_MessageBody_Section + "//div/div/div//table[@class='x_module']//td)[1]"), "Text");
            Assert.assertTrue(messageBody.contains("Please accept this as notification that the following listed card payment has been Voided."));
            Assert.assertTrue(messageBody.contains("Vendor Name: " + emailData.get("vendorName")));
            Assert.assertTrue(messageBody.contains("Remit ID: " + emailData.get("remitID")));
            Assert.assertTrue(messageBody.contains("Payment Reference: " + emailData.get("paymentReference")));
            Assert.assertTrue(messageBody.contains("Payment Amount: " + (emailData.get("paymentAmount").split("\\."))[0]));
            Assert.assertTrue(messageBody.contains("Kindly complete the following:"));
            Assert.assertTrue(messageBody.contains("Void this payment from your system.") || messageBody.contains("Void the remaining balance for this payment from your system"));
            Assert.assertTrue(messageBody.contains(emailData.get("paymentID")));
            Assert.assertTrue(messageBody.contains("Sincerely,"));
            Assert.assertTrue(messageBody.contains("Corcentric Client Support"));

            return true;
        }catch(Exception e) {
            reports.writeResult(oEmailBrowser, "Exception", "Exception in 'readEmailFromResendVoidNotification()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oEmailBrowser, "Exception", "AssertionError in 'mreadEmailFromResendVoidNotification()' method. " + e);
            return false;
        }finally{
            //delete the email
            Assert.assertTrue(appInd.javaScriptClickObject(oEmailBrowser, obj_Delete_Btn));
            appDep.threadSleep(4000);
            oEmailBrowser.close();
            oEmailBrowser = null;
        }
    }




    /********************************************************
     * Method Name      : readEmailFromDuplicateSupplierNotification()
     * Purpose          : to read the mail triggered from the 'Re-Send Void Notification' functionality
     * Author           : Gudi
     * Parameters       : oEmailBrowser, emailData
     * ReturnType       : boolean
     ********************************************************/
    public boolean readEmailFromDuplicateSupplierNotification(WebDriver oEmailBrowser, Map<String, String> emailData){
        String messageBody = null;
        try{
            reports.writeResult(oEmailBrowser, "Screenshot", "Inbox Mail was opened for reading purpose for the 'Send Mail' functionality");
            Assert.assertTrue(appInd.verifyText(oEmailBrowser, By.xpath(obj_MessageBody_Section + "//div/div/div/b"), "Text", "Duplicate Supplier Info:"));
            messageBody = appInd.getTextOnElement(oEmailBrowser, By.xpath(obj_MessageBody_Section + "//div/div/div/pre"), "Text");

            Assert.assertTrue(messageBody.contains("\"RequestId\":"));
            Assert.assertTrue(messageBody.contains("\"SupplierName\": \""+emailData.get("Company_IndividualName")+"\""));
            Assert.assertTrue(messageBody.contains("\"TaxId\": \"*****"+emailData.get("TaxID_SSN").substring(5)+"\""));
            Assert.assertTrue(messageBody.contains("\"PhoneNumber\": \""+emailData.get("PhoneNumber")+"\""));
            Assert.assertTrue(messageBody.contains("\"RequestDesc\": \""+emailData.get("RequestSupport")+"\""));
            Assert.assertTrue(messageBody.contains("\"Name\": \""+emailData.get("ContactFullName")+"\""));
            Assert.assertTrue(messageBody.contains("\"Email\": \""+emailData.get("ContactEmail")+"\""));
            Assert.assertTrue(messageBody.contains("\"Phone\": \""+emailData.get("ContactPhone")+" ext. "+"\""));
            Assert.assertTrue(messageBody.contains("\"IsNew\": true"));

            return true;
        }catch(Exception e) {
            reports.writeResult(oEmailBrowser, "Exception", "Exception in 'readEmailFromDuplicateSupplierNotification()' method. " + e);
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oEmailBrowser, "Exception", "AssertionError in 'readEmailFromDuplicateSupplierNotification()' method. " + e);
            return false;
        }finally{
            //delete the email
            Assert.assertTrue(appInd.javaScriptClickObject(oEmailBrowser, obj_Delete_Btn));
            appDep.threadSleep(4000);
            oEmailBrowser.close();
            oEmailBrowser = null;
        }
    }
}