package com.corcentric.baseSteps.api;

import com.corcentric.runner.CucumberTestRunner;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import net.minidev.json.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class API_DataDimensionBaseStep extends CucumberTestRunner {
    /********************************************************
     * Method Name      : validateDataDimension_BlockAndUnblockPaymentAPIUsingPUTMethodAndValidatePollAndPollByDateAPIForTheSameUsingGETMethod()
     * Purpose          : to validate Data Dimension Block & Unblock Payments API using PUT method & Also validate Poll & Ploo by date API for the same using GET Method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateDataDimension_BlockAndUnblockPaymentAPIUsingPUTMethodAndValidatePollAndPollByDateAPIForTheSameUsingGETMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response putResponse = null;
        Response getResponse = null;
        String strPutEndPoint = null;
        String strGetEndPoint = null;
        JSONArray dbResponse = null;
        String paymentID = null;
        String fieldsToUpdate = null;
        String startDate = null;
        String endDate = null;
        String startTime = null;
        String endTime = null;
        org.json.simple.JSONArray objJsonGETPaymentDetailsByPollDate = null;
        org.json.simple.JSONArray objJsonGETPaymentDetailsByPoll = null;
        ResponseBody responseBody = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);
            startDate = appInd.getDateTime("yyyy-MM-dd");
            endDate = appInd.getDateTime("yyyy-MM-dd");
            startTime = "00:00:00";
            endTime = "11:59:00";

            //Block account of invalid paymentID (PaymentID is valid in system but not belongs to CARD) - Not belong to VCA
            //PaymentID which belongs to any given status = Voided, Partially Voided, Expired, Processed cards
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_DataDimension_BlockUnblockPayment_NonVCA_PUTInput", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            paymentID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("PaymentID").toString();

            strPutEndPoint = appInd.getPropertyValueByKeyName("dataDimensionEndPoint") + appInd.getPropertyValueByKeyName("dataDimensionBlockPayment_PUTMethod");
            fieldsToUpdate = "username#ggudi"+appInd.getDateTime("hhmmss")+",requestedFrom#payCRM,actionType#9";
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint.replace("%", paymentID), apiDataDimensionAuthToken, "", fieldsToUpdate, inputData.get(0).get("InputFileForPost_Block"), "No", "Invalid");
            Assert.assertTrue(putResponse.getStatusCode() == 403, "Block Payment using PaymentID not VCA & status other than 'Confirmed/Partially Processed/Authorized': " + putResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.title").toString().equalsIgnoreCase("ClientCompanyId must match either the companyId claim, or one of the ids in the additionalCompanyIds claim."), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.status").toString().equalsIgnoreCase("403"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.detail").toString().equalsIgnoreCase("ClientCompanyId is not matching"), "The line number: " + appInd.getCurrentLineNumber());


            //CPAY-3866: try to Block paymentID with invalid paymentID (wrong paymentID)
            fieldsToUpdate = "username#ggudi"+appInd.getDateTime("hhmmss")+",requestedFrom#payCRM,actionType#9";
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint.replace("%", inputData.get(0).get("InvalidPaymentID")), apiDataDimensionAuthToken, "", fieldsToUpdate, inputData.get(0).get("InputFileForPost_Block"), "No", "Invalid");
            Assert.assertTrue(putResponse.getStatusCode() == 403, "Block Payment using Invalid PaymentID: " + putResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.title").toString().equalsIgnoreCase("ClientCompanyId must match either the companyId claim, or one of the ids in the additionalCompanyIds claim."), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.status").toString().equalsIgnoreCase("403"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.detail").toString().equalsIgnoreCase("ClientCompanyId is not matching"), "The line number: " + appInd.getCurrentLineNumber());


            //CPAY-3866: API - MSV - Data Dimension - Block Payment Endpoint
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_DataDimension_BlockUnblockPayments_PUTInput", new Object[] {});
            randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            paymentID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("PaymentID").toString();
            fieldsToUpdate = "username#ggudi"+appInd.getDateTime("hhmmss")+",requestedFrom#payCRM,actionType#9";
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint.replace("%", paymentID), apiDataDimensionAuthToken, "", fieldsToUpdate, inputData.get(0).get("InputFileForPost_Block"), "No", "Valid");
            Assert.assertTrue(putResponse.getStatusCode() == 200, "Block Payment API: " + putResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.success").toString().equalsIgnoreCase("true"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.message").toString().equalsIgnoreCase("Success. "), "The line number: " + appInd.getCurrentLineNumber());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_DataDimension_BlockUnblockPayment_PUTResponse", new Object[] {"paymentID", paymentID});
            Assert.assertTrue(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("Card Status").toString().equalsIgnoreCase("Blocked"), "Blocking Payments Failed. Line Number: " + appInd.getCurrentLineNumber());


            //CPAY-3893: API- MSV - Managed Payment || GET- Bill Pay Case Poll by date
            strGetEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsBlockUnblock_PollByDate_GETMethod");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", startDate).replace("%2", endDate).replace("%3", startTime).replace("%4", endTime), apiManagePaymentsAuthToken, "Valid");
            Assert.assertTrue(getResponse.getStatusCode() == 200, "Managed Payment - Get Bill Pay Case By Poll By Date API for Block: " + getResponse.getBody().prettyPrint());
            objJsonGETPaymentDetailsByPollDate = (org.json.simple.JSONArray) apiUtility.convertJsonResponseIntoJSONObject("JsonArray", getResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagedPayments_PollByDate_GETResponse", new Object[]{"startDate", startDate, "endDate", endDate});
            for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i), (JSONObject) objJsonGETPaymentDetailsByPollDate.get(i)
                        , "PaymentID#ClientVNetID#ClientName#FileID#BFileID#PayeeName#PayeeCompanyID#PayeeCompanyName#PayeeEntityIdentifier#PaymentIdentifier#RemitTo#CreditBankCountryISO#CurrencyCode#Amount#AmountPosted#AmountVoided#ValueDateAssigned#LinkID#RemittanceCount#DebitAccountNumber#ReturnStatusID#PICID#CardAccountNumber#CardAccountID#PIID#VirtualCardProcessedAt#CardNumber#Expiry#CVC#CardIssueDate#ProviderName#PaymentCreatedAt#ApprovedAt#ReleasedAt#DeliveredAt#LastStatusUpdateAt#DueDate#RequestedAmount#AdjustmentAmount#PaymentAmount#ReferenceNumber#BuyerID#VCardEmailAddress#IsVPay#UserName#ActionDate#IsBlocked#IsUnblocked#IsBlockAndRefund"
                        , "paymentId#clientVNetId#clientName#fileId#bFileId#payeeName#payeeCompanyId#payeeCompanyName#payeeEntityIdentifier#paymentIdentifier#remitTo#creditBankCountryIso#currencyCode#amount#amountPosted#amountVoided#valueDateAssigned#linkId#remittanceCount#debitAccountNumber#returnStatusId#picId#cardAccountNumber#cardAccountId#piId#virtualCardProcessedAt#cardNumber#expiry#cvc#cardIssueDate#providerName#paymentCreatedAt#approvedAt#releasedAt#deliveredAt#lastStatusUpdateAt#dueDate#requestedAmount#adjustmentAmount#paymentAmount#referenceNumber#buyerId#vCardEmailAddress#isVPay#userName#actionDate#isBlocked#isUnblocked#isBlockAndRefund"));
            }


            //Block paymentID which already blocked
            fieldsToUpdate = "username#ggudi"+appInd.getDateTime("hhmmss")+",requestedFrom#payCRM,actionType#9";
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint.replace("%", paymentID), apiDataDimensionAuthToken, "", fieldsToUpdate, inputData.get(0).get("InputFileForPost_Block"), "No", "Valid");
            Assert.assertTrue(putResponse.getStatusCode() == 200, "Block the PaymentID which ia already blocked API: " + putResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.success").toString().equalsIgnoreCase("false"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.message").toString().equalsIgnoreCase("Fail.  Card already blocked"), "The line number: " + appInd.getCurrentLineNumber());


            //CPAY-3867: API - MSV - Data Dimension - UnBlock Payment Endpoint
            strPutEndPoint = appInd.getPropertyValueByKeyName("dataDimensionEndPoint") + appInd.getPropertyValueByKeyName("dataDimensionUnblockPayment_PUTMethod");
            fieldsToUpdate = "username#ggudi"+appInd.getDateTime("hhmmss")+",requestedFrom#payCRM";
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint.replace("%", paymentID), apiDataDimensionAuthToken, "", fieldsToUpdate, inputData.get(0).get("InputFileForPost_Unblock"), "No", "Valid");
            Assert.assertTrue(putResponse.getStatusCode() == 200, "Unblock Payment API: " + putResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.success").toString().equalsIgnoreCase("true"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.message").toString().equalsIgnoreCase("Success. "), "The line number: " + appInd.getCurrentLineNumber());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_DataDimension_BlockUnblockPayment_PUTResponse", new Object[] {"paymentID", paymentID});
            Assert.assertTrue(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("Card Status").toString().equalsIgnoreCase("Not Blocked"), "Blocking Payments Failed. Line Number: " + appInd.getCurrentLineNumber());


            //CPAY-3893: API- MSV - Managed Payment || GET- Bill Pay Case Poll by date
            strGetEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsBlockUnblock_PollByDate_GETMethod");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", startDate).replace("%2", endDate).replace("%3", startTime).replace("%4", endTime), apiManagePaymentsAuthToken, "Valid");
            Assert.assertTrue(getResponse.getStatusCode() == 200, "Managed Payment - Get Bill Pay Case By Poll By Date API for Unblock: " + getResponse.getBody().prettyPrint());
            objJsonGETPaymentDetailsByPollDate = (org.json.simple.JSONArray) apiUtility.convertJsonResponseIntoJSONObject("JsonArray", getResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagedPayments_PollByDate_GETResponse", new Object[]{"startDate", startDate, "endDate", endDate});
            for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i), (JSONObject) objJsonGETPaymentDetailsByPollDate.get(i)
                        , "PaymentID#ClientVNetID#ClientName#FileID#BFileID#PayeeName#PayeeCompanyID#PayeeCompanyName#PayeeEntityIdentifier#PaymentIdentifier#RemitTo#CreditBankCountryISO#CurrencyCode#Amount#AmountPosted#AmountVoided#ValueDateAssigned#LinkID#RemittanceCount#DebitAccountNumber#ReturnStatusID#PICID#CardAccountNumber#CardAccountID#PIID#VirtualCardProcessedAt#CardNumber#Expiry#CVC#CardIssueDate#ProviderName#PaymentCreatedAt#ApprovedAt#ReleasedAt#DeliveredAt#LastStatusUpdateAt#DueDate#RequestedAmount#AdjustmentAmount#PaymentAmount#ReferenceNumber#BuyerID#VCardEmailAddress#IsVPay#UserName#ActionDate#IsBlocked#IsUnblocked#IsBlockAndRefund"
                        , "paymentId#clientVNetId#clientName#fileId#bFileId#payeeName#payeeCompanyId#payeeCompanyName#payeeEntityIdentifier#paymentIdentifier#remitTo#creditBankCountryIso#currencyCode#amount#amountPosted#amountVoided#valueDateAssigned#linkId#remittanceCount#debitAccountNumber#returnStatusId#picId#cardAccountNumber#cardAccountId#piId#virtualCardProcessedAt#cardNumber#expiry#cvc#cardIssueDate#providerName#paymentCreatedAt#approvedAt#releasedAt#deliveredAt#lastStatusUpdateAt#dueDate#requestedAmount#adjustmentAmount#paymentAmount#referenceNumber#buyerId#vCardEmailAddress#isVPay#userName#actionDate#isBlocked#isUnblocked#isBlockAndRefund"));
            }



            //CPAY-3893: API- MSV - Managed Payment || GET- Bill Pay Case Poll - Execute Poll API
            strGetEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsBlockUnblock_Poll_GETMethod");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint, apiManagePaymentsAuthToken, "Valid");
            Assert.assertTrue(getResponse.getStatusCode() == 200, "Managed Payment - Get Bill Pay Case By Poll API: " + getResponse.getBody().prettyPrint());
            objJsonGETPaymentDetailsByPoll = (org.json.simple.JSONArray) apiUtility.convertJsonResponseIntoJSONObject("JsonArray", getResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagedPayments_PollBillPayCases_GETResponse", new Object[]{});

            for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i), (JSONObject) objJsonGETPaymentDetailsByPoll.get(i)
                        , "PaymentID#ClientVNetID#ClientName#FileID#BFileID#PayeeName#PayeeCompanyID#PayeeCompanyName#PayeeEntityIdentifier#PaymentIdentifier#RemitTo#CreditBankCountryISO#CurrencyCode#Amount#AmountPosted#AmountVoided#ValueDateAssigned#LinkID#RemittanceCount#DebitAccountNumber#ReturnStatusID#PICID#CardAccountNumber#CardAccountID#PIID#VirtualCardProcessedAt#CardNumber#Expiry#CVC#CardIssueDate#ProviderName#PaymentCreatedAt#ApprovedAt#ReleasedAt#DeliveredAt#LastStatusUpdateAt#DueDate#RequestedAmount#AdjustmentAmount#PaymentAmount#ReferenceNumber#BuyerID#VCardEmailAddress#IsVPay#UserName#ActionDate#IsBlocked#IsUnblocked#IsBlockAndRefund"
                        , "paymentId#clientVNetId#clientName#fileId#bFileId#payeeName#payeeCompanyId#payeeCompanyName#payeeEntityIdentifier#paymentIdentifier#remitTo#creditBankCountryIso#currencyCode#amount#amountPosted#amountVoided#valueDateAssigned#linkId#remittanceCount#debitAccountNumber#returnStatusId#picId#cardAccountNumber#cardAccountId#piId#virtualCardProcessedAt#cardNumber#expiry#cvc#cardIssueDate#providerName#paymentCreatedAt#approvedAt#releasedAt#deliveredAt#lastStatusUpdateAt#dueDate#requestedAmount#adjustmentAmount#paymentAmount#referenceNumber#buyerId#vCardEmailAddress#isVPay#userName#actionDate#isBlocked#isUnblocked#isBlockAndRefund"));
            }


            //CPAY-3893: API- MSV - Managed Payment || GET- Bill Pay Case Poll - Execute Poll API twice (One more time)
            getResponse = apiUtility.httpGETRequest(strGetEndPoint, apiManagePaymentsAuthToken, "Valid");
            Assert.assertTrue(getResponse.getStatusCode() == 200, "Re-Executing Managed Payment - Get Bill Pay Case By Poll API: " + getResponse.getBody().prettyPrint());
            responseBody = getResponse.getBody();
            Assert.assertTrue(responseBody.asString().replace("\n","").replace(" ", "").equalsIgnoreCase("[]"), "Line Number: " + appInd.getCurrentLineNumber());


            //CPAY-3893: API- MSV - Managed Payment || GET- Bill Pay Case Poll - Execute Poll By Date twice (one More time)
            strGetEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsBlockUnblock_PollByDate_GETMethod");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", startDate).replace("%2", endDate).replace("%3", startTime).replace("%4", endTime), apiManagePaymentsAuthToken, "Valid");
            Assert.assertTrue(getResponse.getStatusCode() == 200, "Re-Execute Managed Payment - Get Bill Pay Case By Poll By Date API: " + getResponse.getBody().prettyPrint());
            objJsonGETPaymentDetailsByPollDate = (org.json.simple.JSONArray) apiUtility.convertJsonResponseIntoJSONObject("JsonArray", getResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagedPayments_PollByDate_GETResponse", new Object[]{"startDate", startDate, "endDate", endDate});
            for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i), (JSONObject) objJsonGETPaymentDetailsByPollDate.get(i)
                        , "PaymentID#ClientVNetID#ClientName#FileID#BFileID#PayeeName#PayeeCompanyID#PayeeCompanyName#PayeeEntityIdentifier#PaymentIdentifier#RemitTo#CreditBankCountryISO#CurrencyCode#Amount#AmountPosted#AmountVoided#ValueDateAssigned#LinkID#RemittanceCount#DebitAccountNumber#ReturnStatusID#PICID#CardAccountNumber#CardAccountID#PIID#VirtualCardProcessedAt#CardNumber#Expiry#CVC#CardIssueDate#ProviderName#PaymentCreatedAt#ApprovedAt#ReleasedAt#DeliveredAt#LastStatusUpdateAt#DueDate#RequestedAmount#AdjustmentAmount#PaymentAmount#ReferenceNumber#BuyerID#VCardEmailAddress#IsVPay#UserName#ActionDate#IsBlocked#IsUnblocked#IsBlockAndRefund"
                        , "paymentId#clientVNetId#clientName#fileId#bFileId#payeeName#payeeCompanyId#payeeCompanyName#payeeEntityIdentifier#paymentIdentifier#remitTo#creditBankCountryIso#currencyCode#amount#amountPosted#amountVoided#valueDateAssigned#linkId#remittanceCount#debitAccountNumber#returnStatusId#picId#cardAccountNumber#cardAccountId#piId#virtualCardProcessedAt#cardNumber#expiry#cvc#cardIssueDate#providerName#paymentCreatedAt#approvedAt#releasedAt#deliveredAt#lastStatusUpdateAt#dueDate#requestedAmount#adjustmentAmount#paymentAmount#referenceNumber#buyerId#vCardEmailAddress#isVPay#userName#actionDate#isBlocked#isUnblocked#isBlockAndRefund"));
            }



            //CPAY-3893: API- MSV - Managed Payment || GET- Bill Pay Case Poll - Poll By Date > Start date is greater than End Date
            startDate = appInd.dateAddAndReturn("yyyy-MM-dd", 2);
            endDate = appInd.getDateTime("yyyy-MM-dd");
            strGetEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsBlockUnblock_PollByDate_GETMethod");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", startDate).replace("%2", endDate).replace("%3", startTime).replace("%4", endTime), apiManagePaymentsAuthToken, "Valid");
            Assert.assertTrue(getResponse.getStatusCode() == 200, "Managed Payment - Get Bill Pay Case By Poll API with startdate more than end date: " + getResponse.getBody().prettyPrint());
            responseBody = getResponse.getBody();
            Assert.assertTrue(responseBody.asString().replace("\n","").replace(" ", "").equalsIgnoreCase("[]"), "Line Number: " + appInd.getCurrentLineNumber());


            //CPAY-3893: API- MSV - Managed Payment || GET- Bill Pay Case Poll - Poll By Date > Start date is blank
            endDate = appInd.getDateTime("yyyy-MM-dd");
            strGetEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsBlockUnblock_PollByDate_GETMethod");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", "").replace("%2", endDate).replace("%3", startTime).replace("%4", endTime), apiManagePaymentsAuthToken, "Invalid");
            Assert.assertTrue(getResponse.getStatusCode() == 400, "Managed Payment - Get Bill Pay Case By Poll API with blank startDate: " + getResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.type").toString().equalsIgnoreCase("https://tools.ietf.org/html/rfc7231#section-6.5.1"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.title").toString().equalsIgnoreCase("One or more validation errors occurred."), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.status").toString().equalsIgnoreCase("400"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.errors").toString().contains("'Start Date' must not be empty."), "The line number: " + appInd.getCurrentLineNumber());



            //CPAY-3893: API- MSV - Managed Payment || GET- Bill Pay Case Poll - Poll By Date > End date is blank
            startDate = appInd.getDateTime("yyyy-MM-dd");
            strGetEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsBlockUnblock_PollByDate_GETMethod");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", startDate).replace("%2", "").replace("%3", startTime).replace("%4", endTime), apiManagePaymentsAuthToken, "Invalid");
            Assert.assertTrue(getResponse.getStatusCode() == 400, "Managed Payment - Get Bill Pay Case By Poll API with blank endDate: " + getResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.type").toString().equalsIgnoreCase("https://tools.ietf.org/html/rfc7231#section-6.5.1"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.title").toString().equalsIgnoreCase("One or more validation errors occurred."), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.status").toString().equalsIgnoreCase("400"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.errors").toString().contains("'End Date' must not be empty."), "The line number: " + appInd.getCurrentLineNumber());


            //CPAY-3893: API- MSV - Managed Payment || GET- Bill Pay Case Poll - Poll By Date > keep start and end date blank
            strGetEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsBlockUnblock_PollByDate_GETMethod");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", "").replace("%2", "").replace("%3", startTime).replace("%4", endTime), apiManagePaymentsAuthToken, "Invalid");
            Assert.assertTrue(getResponse.getStatusCode() == 400, "Managed Payment - Get Bill Pay Case By Poll API with blank start & endDate: " + getResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.type").toString().equalsIgnoreCase("https://tools.ietf.org/html/rfc7231#section-6.5.1"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.title").toString().equalsIgnoreCase("One or more validation errors occurred."), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.status").toString().equalsIgnoreCase("400"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.errors").toString().contains("'End Date' must not be empty."), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.errors").toString().contains("'Start Date' must not be empty."), "The line number: " + appInd.getCurrentLineNumber());


            //CPAY-3893: API- MSV - Managed Payment || GET- Bill Pay Case Poll - Poll By Date > Keep invalid time format (greater than 24 Hrs
            startDate = appInd.getDateTime("yyyy-MM-dd");
            endDate = appInd.getDateTime("yyyy-MM-dd");
            startTime = "00:00:00";
            endTime = "25:59:00";
            strGetEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsBlockUnblock_PollByDate_GETMethod");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", startDate).replace("%2", endDate).replace("%3", startTime).replace("%4", endTime), apiManagePaymentsAuthToken, "Invalid");
            Assert.assertTrue(getResponse.getStatusCode() == 500, "Managed Payment - Get Bill Pay Case By Poll API with blank start & endDate: " + getResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.type").toString().equalsIgnoreCase("https://tools.ietf.org/html/rfc7231#section-6.6.1"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.title").toString().equalsIgnoreCase("Internal Server Error."), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.status").toString().equalsIgnoreCase("500"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.detail").toString().equalsIgnoreCase("Internal server error occurred! The conversion of a varchar data type to a datetime data type resulted in an out-of-range value."), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.instance").toString().equalsIgnoreCase("/v1/payments/poll-by-date/bill-pay-cases"), "The line number: " + appInd.getCurrentLineNumber());

            return true;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'validateDataDimension_BlockAndUnblockPaymentAPIUsingPUTMethodAndValidatePollAndPollByDateAPIForTheSameUsingGETMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateDataDimension_BlockAndUnblockPaymentAPIUsingPUTMethodAndValidatePollAndPollByDateAPIForTheSameUsingGETMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }finally {inputData = null; putResponse = null; getResponse = null; strPutEndPoint = null; strGetEndPoint = null; dbResponse = null; paymentID = null; fieldsToUpdate = null; startDate = null; endDate = null; startTime = null; endTime = null; objJsonGETPaymentDetailsByPollDate = null; objJsonGETPaymentDetailsByPoll = null; responseBody = null;}
    }





    /********************************************************
     * Method Name      : validateDataDimension_GetTransactionHistoryAPIUsingGETMethod()
     * Purpose          : to validate Data Dimension get Transaction History API using GET method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateDataDimension_GetTransactionHistoryAPIUsingGETMethod(DataTable dataTable){
        List<Map<String, String>> inputFileName = null;
        Response getResponse = null;
        String strGetEndPoint = null;
        JSONArray dbResponse = null;
        String paymentID = null;
        String userName = null;
        Map<String, String> basicAuthentication = null;
        try{
            inputFileName = dataTable.asMaps(String.class, String.class);
            //basicAuthentication = apiUtility.getBasicAuthenticationDetails();

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_DataDimension_TransactionHistory_GETInput", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            paymentID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("PaymentID").toString();
            //userName = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("").toString();
            userName = "rami@test.com";


            //CPAY-3986: API | Data Dimension |GET | Transaction History
            strGetEndPoint = appInd.getPropertyValueByKeyName("dataDimensionEndPoint") + appInd.getPropertyValueByKeyName("dataDimensionTransactionHistory_GETMethod");
            //getResponse = apiUtility.httpGETRequestBasicAuthentication(strGetEndPoint.replace("%1", paymentID).replace("%2", userName), basicAuthentication, "Valid");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", paymentID).replace("%2", userName), apiDataDimensionAuthToken, "Valid");

            return true;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'validateDataDimension_GetTransactionHistoryAPIUsingGETMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateDataDimension_GetTransactionHistoryAPIUsingGETMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }finally {inputFileName = null; getResponse = null; strGetEndPoint = null; dbResponse = null; paymentID = null; userName = null;}
    }




    /********************************************************
     * Method Name      : validateDataDimension_BlockRefundPaymentAPIUsingPUTMethod()
     * Purpose          : to validate Data Dimension Block-Refund Payments API using PUT method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateDataDimension_BlockRefundPaymentAPIUsingPUTMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response putResponse = null;
        String strPutEndPoint = null;
        JSONArray dbResponse = null;
        String paymentID = null;
        String fieldsToUpdate = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_DataDimension_BlockRefundPayments_PUTInput", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            paymentID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("PaymentID").toString();

            //CPAY-3970: API - MSV - Data Dimension - Block-Refund Payment Endpoint
            strPutEndPoint = appInd.getPropertyValueByKeyName("dataDimensionEndPoint") + appInd.getPropertyValueByKeyName("dataDimensionBlockRefund_PUTMethod");
            fieldsToUpdate = "username#ggudi"+appInd.getDateTime("hhmmss")+",requestedFrom#payCRM,notes#Automation Notes - " + appInd.getDateTime("hhmmss");
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint.replace("%", paymentID), apiDataDimensionAuthToken, "", fieldsToUpdate, inputData.get(0).get("InputFileForPUT"), "No", "Valid");
            Assert.assertTrue(putResponse.getStatusCode() == 200, "Block-Refund Payment API: " + putResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.success").toString().equalsIgnoreCase("True"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.message").toString().equalsIgnoreCase("Success. "), "The line number: " + appInd.getCurrentLineNumber());

            //CPAY-3970: Defund PaymentID which already defund (block-refund)
            fieldsToUpdate = "username#ggudi"+appInd.getDateTime("hhmmss")+",requestedFrom#payCRM,notes#Automation Notes - " + appInd.getDateTime("hhmmss");
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint.replace("%", paymentID), apiDataDimensionAuthToken, "", fieldsToUpdate, inputData.get(0).get("InputFileForPUT"), "No", "Valid");
            Assert.assertTrue(putResponse.getStatusCode() == 200, "Block-Refund Payment API using the Payment which is already block-refunded: " + putResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.success").toString().equalsIgnoreCase("False"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.message").toString().equalsIgnoreCase("Fail.  DENIED - ACCOUNT BLOCKED"), "The line number: " + appInd.getCurrentLineNumber());


            //CPAY-3970: Defund with paymentID of belong to VCA PaymentID which belongs to any given status = Voided, Partially Voided, Expired, Processed cards
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_DataDimension_BlockRefundWithInvalidStatus_PUTInput", new Object[] {});
            randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            paymentID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("PaymentID").toString();
            fieldsToUpdate = "username#ggudi"+appInd.getDateTime("hhmmss")+",requestedFrom#payCRM,notes#Automation Notes - " + appInd.getDateTime("hhmmss");
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint.replace("%", paymentID), apiDataDimensionAuthToken, "", fieldsToUpdate, inputData.get(0).get("InputFileForPUT"), "No", "Valid");
            Assert.assertTrue(putResponse.getStatusCode() == 200, "Block-Refund Payment API with PaymentID which is VCA and Status Voided, Partially Voided, Expired, Processed cards: " + putResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.success").toString().equalsIgnoreCase("False"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.message").toString().equalsIgnoreCase("Fail.  DENIED - ACCOUNT BLOCKED"), "The line number: " + appInd.getCurrentLineNumber());


            //CPAY-3970: Defund PaymentID of invalid PaymentID (not Present in System)
            fieldsToUpdate = "username#ggudi"+appInd.getDateTime("hhmmss")+",requestedFrom#payCRM,notes#Automation Notes - " + appInd.getDateTime("hhmmss");
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint.replace("%", inputData.get(0).get("InvalidPaymentID")), apiDataDimensionAuthToken, "", fieldsToUpdate, inputData.get(0).get("InputFileForPUT"), "No", "Invalid");
            Assert.assertTrue(putResponse.getStatusCode() == 403, "Block-Refund Payment API with invalid PaymentID: " + putResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.title").toString().equalsIgnoreCase("ClientCompanyId must match either the companyId claim, or one of the ids in the additionalCompanyIds claim."), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.status").toString().equalsIgnoreCase("403"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.detail").toString().equalsIgnoreCase("ClientCompanyId is not matching"), "The line number: " + appInd.getCurrentLineNumber());



            //CPAY-3970: defund paymentID which is already Blocked
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_DataDimension_DefundPaymentIDAlreadyBlocked_PUTInput", new Object[] {});
            randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            paymentID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("PaymentID").toString();

            fieldsToUpdate = "username#ggudi"+appInd.getDateTime("hhmmss")+",requestedFrom#payCRM,notes#Automation Notes - " + appInd.getDateTime("hhmmss");
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint.replace("%", paymentID), apiDataDimensionAuthToken, "", fieldsToUpdate, inputData.get(0).get("InputFileForPUT"), "No", "Invalid");
            Assert.assertTrue(putResponse.getStatusCode() == 200, "Block-Refund Payment API with PaymentID which is already Blocked: " + putResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.success").toString().equalsIgnoreCase("False"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.message").toString().equalsIgnoreCase("Fail.  DENIED - ACCOUNT BLOCKED"), "The line number: " + appInd.getCurrentLineNumber());

            return true;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'validateDataDimension_BlockRefundPaymentAPIUsingPUTMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateDataDimension_BlockRefundPaymentAPIUsingPUTMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }finally {inputData = null; putResponse = null; strPutEndPoint = null; dbResponse = null; paymentID = null; fieldsToUpdate = null;}
    }





    /********************************************************
     * Method Name      : validateDataDimension_EnableMultiSwipeAPIUsingPUTMethod()
     * Purpose          : to validate Data Dimension Enable Multi-Swipe API using PUT method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateDataDimension_EnableMultiSwipeAPIUsingPUTMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response putResponse = null;
        String strPutEndPoint = null;
        JSONArray dbResponse = null;
        String paymentID = null;
        String fieldsToUpdate = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagedPayment_MultiSwipe_PUTInput", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            paymentID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("PaymentID").toString();

            //CPAY-3949: API -Data Dimension - Put - Enable multi-swipe
            strPutEndPoint = appInd.getPropertyValueByKeyName("dataDimensionEndPoint") + appInd.getPropertyValueByKeyName("dataDimensionEnableMultiSwipe_PUTMethod");
            //fieldsToUpdate = "username#ggudi"+appInd.getDateTime("hhmmss")+",enabled#"+true;
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint.replace("%", paymentID), apiDataDimensionAuthToken, "", "", inputData.get(0).get("InputFileForPUT"), "No", "Valid");
            Assert.assertTrue(putResponse.getStatusCode() == 200, "Enable Multi-Swipe API: " + putResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.result").toString().equalsIgnoreCase("success"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.message").toString().equalsIgnoreCase("Success. "), "The line number: " + appInd.getCurrentLineNumber());

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagePayment_MultiSwipe_PUTResponse", new Object[] {"paymentID", paymentID});
            Assert.assertTrue(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("IsCardMultiSwipe").toString().equalsIgnoreCase("Enabled"), "Enable Multi-Swipe Failed. Line Number: " + appInd.getCurrentLineNumber());
            return true;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'validateDataDimension_EnableMultiSwipeAPIUsingPUTMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateDataDimension_EnableMultiSwipeAPIUsingPUTMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }finally {inputData = null; putResponse = null; strPutEndPoint = null; dbResponse = null; paymentID = null; fieldsToUpdate = null;}
    }




    /********************************************************
     * Method Name      : validateDataDimension_CardInfoAPIUsingGETMethod()
     * Purpose          : to validate Data Dimension CardInfo API using GET method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateDataDimension_CardInfoAPIUsingGETMethod(DataTable dataTable){
        List<Map<String, String>> inputFileName = null;
        Response getResponse = null;
        String strGetEndPoint = null;
        JSONArray dbResponse = null;
        String paymentID = null;
        org.json.simple.JSONObject objJsonGETResponse = null;
        try{
            inputFileName = dataTable.asMaps(String.class, String.class);
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_PaymentCardInfo_GetInput", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            paymentID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("PaymentID").toString();


            //CPAY-4870: Hit the API as mentioned in Test Data using postman
            strGetEndPoint = appInd.getPropertyValueByKeyName("dataDimensionEndPoint") + appInd.getPropertyValueByKeyName("dataDimensionCardInfo_GETMethod");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", paymentID), apiDataDimensionAuthToken, "Valid");

            objJsonGETResponse = (org.json.simple.JSONObject) apiUtility.convertJsonResponseIntoJSONObject("JsonObject", getResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_PaymentCardInfo_GetOutput", new Object[] {"paymentID", paymentID});

            Assert.assertTrue(apiUtility.compareJsonObjectElementsWithDBResponse(getResponse.asString(), dbResponse, "cardInfo"
                    , "paymentID#pan#cvv"
                    , "PaymentID#CardNumber#CVV"));


            //CPAY-4870: Hit the API with wrong payment Id in URL as mentioned in test data
            strGetEndPoint = appInd.getPropertyValueByKeyName("dataDimensionEndPoint") + appInd.getPropertyValueByKeyName("dataDimensionCardInfo_GETMethod");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", (paymentID.split("-"))[0]), apiDataDimensionAuthToken, "InValid");
            Assert.assertTrue(getResponse.getStatusCode() == 400, "CardInfo API using Invalid PaymentID: " + getResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.title").toString().equalsIgnoreCase("Incorrect id format. It must be companyId-paymentId"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.status").toString().equalsIgnoreCase("400"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.detail").toString().equalsIgnoreCase("Validation errors."), "The line number: " + appInd.getCurrentLineNumber());
            return true;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'validateDataDimension_GetTransactionHistoryAPIUsingGETMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateDataDimension_GetTransactionHistoryAPIUsingGETMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }finally {inputFileName = null; getResponse = null; strGetEndPoint = null; dbResponse = null; paymentID = null; objJsonGETResponse = null;}
    }

}