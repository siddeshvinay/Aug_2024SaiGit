package com.corcentric.baseSteps.api;

import com.corcentric.runner.CucumberTestRunner;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import org.testng.Assert;
import java.util.*;

public class API_AKFBaseStep extends CucumberTestRunner {
    /********************************************************
     * Method Name      : validateAKFScenario_CreateSupplier_UpdatePaymentInfo_CancelPayment_GET_POST_PUTRequest()
     * Purpose          : to validate GET, POST and PUT operations for AKF MSV API
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateAKFScenario_CreateSupplier_UpdatePaymentInfo_CancelPayment_GET_POST_PUTRequest(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response getResponse = null;
        Response postResponse = null;
        Response putResponse = null;
        String strGetEndPoint = null;
        String strPostEndPoint = null;
        String strPutEndPoint = null;
        String fieldsToUpdate = null;
        JSONArray dbResponse = null;
        String paymentID = null;
        org.json.simple.JSONObject objJsonGETResponnseByPaymentID = null;
        String clientCompanyId = null;
        String sfdcCampaignId = null;
        String taxID = null;
        String companyName = null;
        String entityIdentifier = null;
        org.json.simple.JSONArray objJsonPUTResponseUpdatePayment = null;
        String paymentIDWhichCantBeCancelled = null;
        String messages[] = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);

            //API - AKF MSV - GET Payment Information
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_AKFPaymentIDForPaymentInformation", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            paymentID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("PaymentID").toString();

            strGetEndPoint = appInd.getPropertyValueByKeyName("akfMSVEndPoint") + appInd.getPropertyValueByKeyName("akfMSVPaymentInfo_GETByPaymentID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", paymentID), apiAKFAuthToken, "Valid");

            objJsonGETResponnseByPaymentID = (org.json.simple.JSONObject) apiUtility.convertJsonResponseIntoJSONObject("JsonObject", getResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_AKF_GETPaymentInformationUsingPaymentID", new Object[] {"paymentID", paymentID});
            Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0), objJsonGETResponnseByPaymentID
                    , "PaymentID#ClientCompanyName#ParentClientCompanyName#PaymentReference#SupplierCompanyName#PayeeEntityIdentifier#RemitTo#PaymentAmount#DiscountAmount#AmountRequested#AmountPosted#AmountVoided#Currency#PaymentTypeCode#PaymentStatusDesc#CheckNumberProvided#CheckNumberGenerated#ACHTraceNumber#CardholderReferenceNumber#FundingMethod#FundingAccountNumber#FundingAccountIdentifier#PaymentProcessor#FundingAccountCode"
                    , "paymentId#clientCompanyName#parentClientCompanyName#paymentReference#supplierName#payeeEntityIdentifier#remitToId#paymentAmount#discountAmount#amountRequested#amountPosted#amountVoided#paymentCurrency#paymentTypeCode#paymentStatusDesc#checkNumberProvided#checkNumberGenerated#achTraceNumber#cardholderReferenceNumber#fundingMethod#fundingAccountNumber#fundingAccountIdentifier#paymentProcessor#fundingAccountCode"));


            //Negative scenario: API - AKF MSV - GET Payment Information by using invalid payment id
            strGetEndPoint = appInd.getPropertyValueByKeyName("akfMSVEndPoint") + appInd.getPropertyValueByKeyName("akfMSVPaymentInfo_GETByPaymentID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", inputData.get(0).get("InvalidPaymentID")), apiAKFAuthToken, "Invalid");
            Assert.assertTrue(appInd.compareValues(oBrowser, getResponse.getStatusCode(), 404), "Invalid statusCode when PaymentID is wrong: " + getResponse.getBody().prettyPrint());
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(getResponse.asString(),"$.status").toString(), "404"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(getResponse.asString(),"$.detail").toString(), "Payment doesn't exist"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(getResponse.asString(),"$.title").toString(), "Payment not found"), "Line Number: " + appInd.getCurrentLineNumber());



            //Trigger the POST request by using the CompanyID got from the previous GET request
            //CPAY-2982: API - AKF MSV - POST Create Supplier
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_AKFCreateSuppliersInputData", new Object[] {});
            clientCompanyId = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("ClientCompanyId").toString();
            sfdcCampaignId = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("SFDCCampaignId").toString();
            companyName = "APIAutomation_"+appInd.getDateTime("hhmmss");
            entityIdentifier = appInd.getDateTime("ShhmmssS");
            taxID = appInd.generateTaxID("Shhmmss");

            strPostEndPoint = appInd.getPropertyValueByKeyName("akfMSVEndPoint")+appInd.getPropertyValueByKeyName("akfMSVCreateSupplier_POSTMethod");
            fieldsToUpdate = "clientCompanyId#"+clientCompanyId+",sfdcCampaignId#"+sfdcCampaignId+",companyName#"+companyName+",entityIdentifier#"+entityIdentifier +",taxId#"+taxID+",vCardEmailAddress#"+appInd.getPropertyValueByKeyName("qaUserName");
            postResponse = apiUtility.httpPOSTMethodRequestWithBearerAuth(strPostEndPoint, apiAKFAuthToken, "", fieldsToUpdate, new Object[][] {}, inputData.get(0).get("InputFileForPOST"), "No", "Valid");

            //Validate the POST json response
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(postResponse.asString(),"$.success").toString(), "true"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(postResponse.asString(),"$.message").toString(), "Activation completed, Link Created"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(postResponse.asString(),"$.updatePaymentInfo").toString(), "false"), "Line Number: " + appInd.getCurrentLineNumber());


            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_AKF_CreateSuppliersPOSTResponse", new Object[] {"companyName", companyName, "taxID", taxID, "entityIdentifier", entityIdentifier, "sfdcCampaignID", sfdcCampaignId, "clientCompanyID", clientCompanyId});
            Assert.assertTrue(appInd.compareValues(null, ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("LegalName").toString(), companyName), "Mis-match in actual '"+companyName+"' and expected '"+((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("LegalName").toString()+"' values from 'API_AKF_CreateSuppliersPOSTResponse' json response");
            Assert.assertTrue(appInd.compareValues(null, ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("TaxID").toString(), taxID), "Mis-match in actual '"+taxID+"' and expected '"+((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("TaxID").toString()+"' values from 'API_AKF_CreateSuppliersPOSTResponse' json response");
            Assert.assertTrue(appInd.compareValues(null, ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("EntityIdentifier").toString(), entityIdentifier), "Mis-match in actual '"+entityIdentifier+"' and expected '"+((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("EntityIdentifier").toString()+"' values from 'API_AKF_CreateSuppliersPOSTResponse' json response");
            Assert.assertTrue(appInd.compareValues(null, ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("SFDCCampaignID").toString(), sfdcCampaignId), "Mis-match in actual '"+sfdcCampaignId+"' and expected '"+((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("SFDCCampaignID").toString()+"' values from 'API_AKF_CreateSuppliersPOSTResponse' json response");
            Assert.assertTrue(appInd.compareValues(null, ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("ClientCompanyID").toString(), clientCompanyId), "Mis-match in actual '"+clientCompanyId+"' and expected '"+((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("ClientCompanyID").toString()+"' values from 'API_AKF_CreateSuppliersPOSTResponse' json response");


            //Negative scenario: Trigger the POST request by using the CompanyID got from the previous GET request by using invalid ClientCompnyID
            fieldsToUpdate = "clientCompanyId#"+inputData.get(0).get("InvalidClientCompanyID")+",sfdcCampaignId#"+sfdcCampaignId+",companyName#APIAutomation_"+appInd.getDateTime("hhmmss")+",remittanceDeliveryEmail#"+appInd.getPropertyValueByKeyName("qaUserName")+",vCardEmailAddress#"+appInd.getPropertyValueByKeyName("qaUserName");
            postResponse = apiUtility.httpPOSTMethodRequestWithBearerAuth(strPostEndPoint, apiAKFAuthToken, "", fieldsToUpdate, new Object[][] {}, inputData.get(0).get("InputFileForPOST"),  "No","Invalid");
            Assert.assertTrue(Arrays.asList(422, 200, 403).contains(postResponse.statusCode()));
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(postResponse.asString(),"$.status").toString(), "403"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(postResponse.asString(),"$.detail").toString(), "ClientCompanyId is not matching"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(postResponse.asString(),"$.title").toString(), "ClientCompanyId must match either the companyId claim, or one of the ids in the additionalCompanyIds claim."), "Line Number: " + appInd.getCurrentLineNumber());


            //Negative scenario: Trigger the POST request by using the CompanyID got from the previous GET request by keeping ABARoutingNumber blank
            fieldsToUpdate = "clientCompanyId#"+clientCompanyId+",sfdcCampaignId#"+sfdcCampaignId+",companyName#APIAutomation_"+appInd.getDateTime("hhmmss")+",remittanceDeliveryEmail#"+appInd.getPropertyValueByKeyName("qaUserName")+",vCardEmailAddress#"+appInd.getPropertyValueByKeyName("qaUserName")+",abaRoutingNumber#";
            postResponse = apiUtility.httpPOSTMethodRequestWithBearerAuth(strPostEndPoint, apiAKFAuthToken, "", fieldsToUpdate, new Object[][] {}, inputData.get(0).get("InputFileForPOST"),  "No","Invalid");
            Assert.assertTrue(appInd.compareValues(null, postResponse.getStatusCode(), 400), "Invalid statusCode when ABARoutingNumber is blank: " + postResponse.getBody().prettyPrint()+ ": Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(postResponse.asString(),"$.status").toString(), "400"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.errors").toString().contains("ABARoutingNumber is required"));
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(postResponse.asString(),"$.title").toString(), "One or more validation errors occurred."), "Line Number: " + appInd.getCurrentLineNumber());


            //Negative scenario: Trigger the POST request by using the CompanyID got from the previous GET request by keeping SFDCCampaignID blank
            fieldsToUpdate = "clientCompanyId#"+clientCompanyId+",sfdcCampaignId#,companyName#APIAutomation_"+appInd.getDateTime("hhmmss")+",remittanceDeliveryEmail#"+appInd.getPropertyValueByKeyName("qaUserName")+",vCardEmailAddress#"+appInd.getPropertyValueByKeyName("qaUserName");
            postResponse = apiUtility.httpPOSTMethodRequestWithBearerAuth(strPostEndPoint, apiAKFAuthToken, "", fieldsToUpdate, new Object[][] {}, inputData.get(0).get("InputFileForPOST"),  "No","Invalid");
            Assert.assertTrue(appInd.compareValues(null, postResponse.getStatusCode(), 400), "Invalid statusCode when SFDCCampaignID is blank: " + postResponse.getBody().prettyPrint());
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(postResponse.asString(),"$.status").toString(), "400"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.errors").toString().contains("SfdcCampaignId is required"));
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(postResponse.asString(),"$.title").toString(), "One or more validation errors occurred."));


            //Negative scenario: Trigger the POST request by using the CompanyID got from the previous GET request by keeping remittanceDeliveryEmail blank
            fieldsToUpdate = "clientCompanyId#" +clientCompanyId+ ",sfdcCampaignId#"+sfdcCampaignId+",companyName#APIAutomation_"+appInd.getDateTime("hhmmss")+",remittanceDeliveryEmail#,vCardEmailAddress#"+appInd.getPropertyValueByKeyName("qaUserName");
            postResponse = apiUtility.httpPOSTMethodRequestWithBearerAuth(strPostEndPoint, apiAKFAuthToken, "", fieldsToUpdate, new Object[][] {}, inputData.get(0).get("InputFileForPOST"),  "No","Invalid");
            Assert.assertTrue(appInd.compareValues(null, postResponse.getStatusCode(), 400), "Invalid statusCode when remittanceDeliveryEmail is blank: " + postResponse.getBody().prettyPrint());
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(postResponse.asString(),"$.status").toString(), "400"));
            Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.errors").toString().contains("RemittanceDeliveryEmail is required"));
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(postResponse.asString(),"$.title").toString(), "One or more validation errors occurred."));


            //Negative scenario: Trigger the POST request by using the CompanyID got from the previous GET request by keeping EntityIdentifier blank
            fieldsToUpdate = "clientCompanyId#"+clientCompanyId+",sfdcCampaignId#"+sfdcCampaignId+",companyName#APIAutomation_"+appInd.getDateTime("hhmmss")+",entityIdentifier# ,remittanceDeliveryEmail#"+appInd.getPropertyValueByKeyName("qaUserName")+",vCardEmailAddress#"+appInd.getPropertyValueByKeyName("qaUserName");
            postResponse = apiUtility.httpPOSTMethodRequestWithBearerAuth(strPostEndPoint, apiAKFAuthToken, "", fieldsToUpdate, new Object[][] {}, inputData.get(0).get("InputFileForPOST"),  "No","Invalid");
            Assert.assertTrue(appInd.compareValues(null, postResponse.getStatusCode(), 400), "Invalid statusCode when EntityIdentifier is blank: " + postResponse.getBody().prettyPrint());
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(postResponse.asString(),"$.status").toString(), "400"));
            Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.errors").toString().contains("EntityIdentifier is required"));
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(postResponse.asString(),"$.title").toString(), "One or more validation errors occurred."));


            //Negative scenario: Trigger the POST request by using the CompanyID got from the previous GET request by keeping TaxID more than 10 digits
            fieldsToUpdate = "clientCompanyId#"+clientCompanyId+",sfdcCampaignId#"+sfdcCampaignId+",companyName#APIAutomation_"+appInd.getDateTime("hhmmss")+",remittanceDeliveryEmail#"+appInd.getPropertyValueByKeyName("qaUserName")+",vCardEmailAddress#"+appInd.getPropertyValueByKeyName("qaUserName")+",taxId#"+appInd.getDateTime("ShhmmssssS");
            postResponse = apiUtility.httpPOSTMethodRequestWithBearerAuth(strPostEndPoint, apiAKFAuthToken, "", fieldsToUpdate, new Object[][] {}, inputData.get(0).get("InputFileForPOST"),  "No","Invalid");
            Assert.assertTrue(appInd.compareValues(null, postResponse.getStatusCode(), 400), "Invalid statusCode when TaxID more than 10 digits: " + postResponse.getBody().prettyPrint());
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(postResponse.asString(),"$.status").toString(), "400"));
            Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.errors").toString().contains("TaxID should have 10 chars"));
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(postResponse.asString(),"$.title").toString(), "One or more validation errors occurred."));


            //Negative scenario: Trigger the POST request by using the CompanyID got from the previous GET request by keeping CompanyName as blank
            fieldsToUpdate = "clientCompanyId#"+clientCompanyId+",sfdcCampaignId#"+sfdcCampaignId+",companyName#,remittanceDeliveryEmail#"+appInd.getPropertyValueByKeyName("qaUserName")+",vCardEmailAddress#"+appInd.getPropertyValueByKeyName("qaUserName");
            postResponse = apiUtility.httpPOSTMethodRequestWithBearerAuth(strPostEndPoint, apiAKFAuthToken, "", fieldsToUpdate, new Object[][] {}, inputData.get(0).get("InputFileForPOST"),  "No","Invalid");
            Assert.assertTrue(appInd.compareValues(null, postResponse.getStatusCode(), 400), "Invalid statusCode when CompanyName is blank: " + postResponse.getBody().prettyPrint());
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(postResponse.asString(),"$.status").toString(), "400"));
            Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.errors").toString().contains("CompanyName is required"));
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(postResponse.asString(),"$.title").toString(), "One or more validation errors occurred."));


            //Negative scenario: Trigger the POST request by using the CompanyID got from the previous GET request by keeping ZipCode more than the limit
            fieldsToUpdate = "clientCompanyId#"+clientCompanyId+",sfdcCampaignId#"+sfdcCampaignId+",companyName#APIAutomation_"+appInd.getDateTime("hhmmss")+",remittanceDeliveryEmail#"+appInd.getPropertyValueByKeyName("qaUserName")+",vCardEmailAddress#"+appInd.getPropertyValueByKeyName("qaUserName");
            postResponse = apiUtility.httpPOSTMethodRequestWithBearerAuth(strPostEndPoint, apiAKFAuthToken, "", fieldsToUpdate, new Object[][] {}, inputData.get(0).get("InputFileForPOSTWithInvalidZopPostal"),  "No","Invalid");
            Assert.assertTrue(appInd.compareValues(null, postResponse.getStatusCode(), 400), "Invalid statusCode when ZipCode is more than the limit: " + postResponse.getBody().prettyPrint());
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(postResponse.asString(),"$.status").toString(), "400"));
            Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.errors").toString().contains("Valid zipPostal should be in the format 12345 or 12345-6789"));
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(postResponse.asString(),"$.title").toString(), "One or more validation errors occurred."));


            //Negative scenario: Trigger the POST request by using the CompanyID got from the previous GET request by keeping CountryISO blank
            fieldsToUpdate = "clientCompanyId#"+clientCompanyId+",sfdcCampaignId#"+sfdcCampaignId+",companyName#APIAutomation_"+appInd.getDateTime("hhmmss")+",remittanceDeliveryEmail#"+appInd.getPropertyValueByKeyName("qaUserName")+",vCardEmailAddress#"+appInd.getPropertyValueByKeyName("qaUserName")+",countryIso#";
            postResponse = apiUtility.httpPOSTMethodRequestWithBearerAuth(strPostEndPoint, apiAKFAuthToken, "", fieldsToUpdate, new Object[][] {}, inputData.get(0).get("InputFileForPOST"),  "No","Invalid");
            Assert.assertTrue(appInd.compareValues(null, postResponse.getStatusCode(),  400), "Invalid statusCode when countryISO ia blank: " + postResponse.getBody().prettyPrint());
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(postResponse.asString(),"$.status").toString(), "400"));
            Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.errors").toString().contains("CountryISO should have 2 chars"));
            Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.title").toString().equalsIgnoreCase("One or more validation errors occurred."));


            //Negative scenario: Trigger the POST request by using the CompanyID got from the previous GET request by keeping invalid campaign id
            fieldsToUpdate = "clientCompanyId#"+clientCompanyId+",sfdcCampaignId#"+inputData.get(0).get("InvalidCampaignID")+",companyName#APIAutomation_"+appInd.getDateTime("hhmmss")+",remittanceDeliveryEmail#"+appInd.getPropertyValueByKeyName("qaUserName")+",vCardEmailAddress#"+appInd.getPropertyValueByKeyName("qaUserName");
            postResponse = apiUtility.httpPOSTMethodRequestWithBearerAuth(strPostEndPoint, apiAKFAuthToken, "", fieldsToUpdate, new Object[][] {}, inputData.get(0).get("InputFileForPOST"),  "No","Invalid");
            Assert.assertTrue(appInd.compareValues(null, postResponse.getStatusCode(),  200), "Invalid statusCode when campaign id is invalid: " + postResponse.getBody().prettyPrint());
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(postResponse.asString(),"$.success").toString(), "false"));
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(postResponse.asString(),"$.message").toString(), "Provided campaign does not belong to provided company"));
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(postResponse.asString(),"$.updatePaymentInfo").toString(), "false"));


            //CPAY-2983: API - AKF MSV - PUT Update Payment Information
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_AKF_GetTaxIDForUpdatePaymentInfo", new Object[] {});
            randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            taxID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("TaxID").toString();

            fieldsToUpdate = "taxId#"+taxID+",bankName#Bank Of India,accountOwnerName#APIAutomation_"+appInd.getDateTime("hhmmss")+",remittanceDeliveryEmail#"+appInd.getPropertyValueByKeyName("qaUserName");
            strPutEndPoint = appInd.getPropertyValueByKeyName("akfMSVEndPoint") + appInd.getPropertyValueByKeyName("akfMSVUpdatePaymentInfo_PUTMethod");
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint, apiAKFAuthToken, "", fieldsToUpdate, inputData.get(0).get("InputFileForPUT"), "No", "Valid");
            Assert.assertTrue(appInd.compareValues(null, putResponse.getStatusCode(), 200), "AKF PUT Update Payment Information API: " + postResponse.getBody().prettyPrint());
            Assert.assertTrue(putResponse.getBody().prettyPrint().contains("\"success\": true"), "The AKF PUT response has returned false");
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_AKF_UpdatePaymentInfoPUTResponse", new Object[] {"taxID", taxID});
            for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                Assert.assertTrue(appInd.compareValues(null, ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("TaxID").toString(), taxID));
            }


            //Negative scenario: API - AKF MSV - PUT Update Payment Information with invalid TaxID
            fieldsToUpdate = "taxId#"+appInd.getDateTime("Shhmmss")+",bankName#Bank Of India,accountOwnerName#APIAutomation_"+appInd.getDateTime("hhmmss")+",remittanceDeliveryEmail#"+appInd.getPropertyValueByKeyName("qaUserName");
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint, apiAKFAuthToken, "", fieldsToUpdate, inputData.get(0).get("InputFileForPUT"), "No", "Invalid");
            Assert.assertTrue(appInd.compareValues(null, putResponse.getStatusCode(), 200), "Update Payment Information with invalid TaxID: " + putResponse.getBody().prettyPrint());
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(putResponse.asString(),"$.success").toString(), "false"));
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(putResponse.asString(),"$.message").toString(), "Link info cannot be found."));

            //Negative scenario: API - AKF MSV - PUT Update Payment Information with invalid AccountType
            fieldsToUpdate = "taxId#"+taxID+",bankName#Bank Of India,accountOwnerName#APIAutomation_"+appInd.getDateTime("hhmmss")+",remittanceDeliveryEmail#"+appInd.getPropertyValueByKeyName("qaUserName")+",accountType#";
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint, apiAKFAuthToken, "", fieldsToUpdate, inputData.get(0).get("InputFileForPUT"), "No", "Invalid");
            Assert.assertTrue(appInd.compareValues(null, putResponse.getStatusCode(), 200), "Update Payment Information with invalid AccountType: " + putResponse.getBody().prettyPrint());
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(putResponse.asString(),"$.success").toString(), "false"));
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(putResponse.asString(),"$.message").toString(), "Invalid account type."));


            //Negative scenario: API - AKF MSV - PUT Update Payment Information with invalid offerType
            fieldsToUpdate = "taxId#"+taxID+",bankName#Bank Of India,accountOwnerName#APIAutomation_"+appInd.getDateTime("hhmmss")+",remittanceDeliveryEmail#"+appInd.getPropertyValueByKeyName("qaUserName")+",offerType#";
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint, apiAKFAuthToken, "", fieldsToUpdate, inputData.get(0).get("InputFileForPUT"), "No", "Invalid");
            Assert.assertTrue(appInd.compareValues(null, putResponse.getStatusCode(),  200), "Update Payment Information with invalid offerType: " + putResponse.getBody().prettyPrint());
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(putResponse.asString(),"$.success").toString(), "false"));
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(putResponse.asString(),"$.message").toString(), "Invalid offer type."));


            //API - AKF MSV - PUT Cancel Payment
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_AKF_CancelPaymentPUT", new Object[] {});
            randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            paymentID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("PaymentID").toString();

            //paymentID = "267270162-300";
            strPutEndPoint = appInd.getPropertyValueByKeyName("akfMSVEndPoint") + appInd.getPropertyValueByKeyName("akfMSVCancelPayment_PUTByPaymentID");
            putResponse = apiUtility.httpPUTRequestWithoutBody(strPutEndPoint.replace("%", paymentID), apiAKFAuthToken, "Valid");
            Assert.assertTrue(appInd.compareValues(null, putResponse.getStatusCode(), 200), "Cancel Payment with valid PaymentID: " + putResponse.getBody().prettyPrint());
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(putResponse.asString(),"$.success").toString(), "true"));
            messages = "Payment Cancellation has been Received and is Under Review#Payment Cancellation has been Received and a Stop Check API call has been attempted with the following response#Payment Cancellation has been Received and is Under Review#Could not Cancel the Payment due to its Current Status#Payment not Found#Company not Found".split("#");
            boolean blnRes = false;
            String actualMessage = JsonPath.read(putResponse.asString(),"$.message").toString();
            for(int i=0; i<messages.length; i++){
                if(actualMessage.contains(messages[i])){
                    blnRes = true;
                    break;
                }
            }
            Assert.assertTrue(appInd.compareValues(null, blnRes, true), "Cancel Payments: Line Number: " + appInd.getCurrentLineNumber());


            //String paymentIDWhichCantBeCancelled = "267270162-301";
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_AKF_CannotCancelPaymentPUT", new Object[] {});
            randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            paymentIDWhichCantBeCancelled = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("PaymentID").toString();

            strPutEndPoint = appInd.getPropertyValueByKeyName("akfMSVEndPoint") + appInd.getPropertyValueByKeyName("akfMSVCancelPayment_PUTByPaymentID");
            putResponse = apiUtility.httpPUTRequestWithoutBody(strPutEndPoint.replace("%", paymentIDWhichCantBeCancelled), apiAKFAuthToken, "Valid");
            Assert.assertTrue(appInd.compareValues(null, putResponse.getStatusCode(), 200), "Invalid statusCode when PaymentID which can't be cancelled: " + putResponse.getBody().prettyPrint());
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(putResponse.asString(),"$.success").toString(), "false"));
            Assert.assertTrue(appInd.compareValues(null, JsonPath.read(putResponse.asString(),"$.message").toString(), "Could not Cancel the Payment due to its Current Status"));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in ' validateAKFScenario_CreateSupplier_UpdatePaymentInfo_CancelPayment_GET_POST_PUTRequest()' method. " + e.getMessage());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateAKFScenario_CreateSupplier_UpdatePaymentInfo_CancelPayment_GET_POST_PUTRequest()' method. " + e.getMessage());
            return false;
        }finally {inputData = null; getResponse = null; postResponse = null; putResponse = null; strGetEndPoint = null; strPostEndPoint = null; strPutEndPoint = null; fieldsToUpdate = null; dbResponse = null; paymentID = null; objJsonGETResponnseByPaymentID = null; clientCompanyId = null; sfdcCampaignId = null; taxID = null; companyName = null; entityIdentifier = null; objJsonPUTResponseUpdatePayment = null; paymentIDWhichCantBeCancelled = null; messages = null;}
    }
}
