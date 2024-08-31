package com.corcentric.baseSteps.api;

import com.corcentric.runner.CucumberTestRunner;
import com.google.gson.JsonObject;
import io.cucumber.datatable.DataTable;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONArray;
import org.testng.Assert;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class API_PayNetAPIBaseStep extends CucumberTestRunner {
    /********************************************************
     * Method Name      : validateUpdatesOfferWithDifferentPaymentTypesAPIUsingPOSTMethod()
     * Purpose          : to Verify Update offer for Pending and Approved Links with differnet Payment Types using POST method
     * Author           : Gudi
     * Parameters       : linkStatus, fromPaymentType, toPaymentType, dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateUpdatesOfferWithDifferentPaymentTypesAPIUsingPOSTMethod(String linkStatus, String fromPaymentType, String toPaymentType, DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response postResponse = null;
        String strPostEndPoint = null;
        Map<String, String> basicAuthentication = null;
        JSONArray dbResponse = null;
        String randomNum = null;
        String strNestedJosnFieldToUpdate = null;
        String clientCompanyID = null;
        String entityIdentifier = null;
        String sfdcCampaignID = null;
        String numberofChecks = null;
        String totalDollar = null;
        String venNumber = null;
        String vndPercentage = null;
        String accountNumber = null;
        String bankName = null;
        String accountOwnerName = null;
        String abaRoutingNumber = null;
        String remittanceDeliveryEmail = null;
        String vCardEmailAddress = null;
        String paymentTypeID = null;
        String bankCountryISO = null;
        String currencyCode = null;
        String accountTypeID = null;
        String offerNumber = null;
        String vCardFax = null;
        String linkID = null;
        String supplierCompanyID = null;
        String vCardNetwordID = null;
        String inputJsonFile = null;
        String inputQueryName = null;
        String outputQueryName = null;
        org.json.simple.JSONObject objJsonPOSTResponse = null;
        String message = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);
            basicAuthentication = new HashMap<String, String>();
            basicAuthentication.put("enrollmentID", appInd.getPropertyValueByKeyName("payNetEnrollID"));
            basicAuthentication.put("accessCode", appInd.getPropertyValueByKeyName("payNetAccessCode"));

            /*basicAuthentication = new HashMap<String, String>();
            if(System.getProperty("environment").equalsIgnoreCase("STAGING")){
                basicAuthentication.put("enrollmentID", "VNetDemoWSUser");
                basicAuthentication.put("accessCode", "qsOMKW%&Rz#sJ2PI");
            }else{
                basicAuthentication.put("enrollmentID", "VNetTestWSUser");
                basicAuthentication.put("accessCode", "qsOMKW%&Rz#sJ2PI");
            }*/

            strPostEndPoint = appInd.getPropertyValueByKeyName("payNetUpdateOffer") + appInd.getPropertyValueByKeyName("payNetUpdateOffer_ACH_VCA_POSTMethod");

            if(linkStatus.equalsIgnoreCase("Pending") && fromPaymentType.equalsIgnoreCase("ACH") && toPaymentType.equalsIgnoreCase("VCA")){
                inputJsonFile = inputData.get(0).get("InputFileForPOST_Pending_ACH_VCA");
                inputQueryName = "API_UpdateOffer_PendingApproval_ACH_VCA_POSTInput";
                outputQueryName = "API_UpdateOffer_PendingApproval_ACH_VCA_POSTResponse";
                message = "Pending Approval ACH to VCA";
            }else if(linkStatus.equalsIgnoreCase("Pending") && fromPaymentType.equalsIgnoreCase("VCA") && toPaymentType.equalsIgnoreCase("ACH")){
                inputJsonFile = inputData.get(0).get("InputFileForPOST_Pending_VCA_ACH");
                inputQueryName = "API_UpdateOffer_PendingApproval_VCA_ACH_POSTInput";
                outputQueryName = "API_UpdateOffer_PendingApproval_VCA_ACH_POSTResponse";
                message = "Pending Approval VCA to ACH";
            }else if(linkStatus.equalsIgnoreCase("Approved") && fromPaymentType.equalsIgnoreCase("ACH") && toPaymentType.equalsIgnoreCase("VCA")){
                inputJsonFile = inputData.get(0).get("InputFileForPOST_Approved_ACH_VCA");
                inputQueryName = "API_UpdateOffer_ApprovedLink_ACH_VCA_POSTInput";
                outputQueryName = "API_UpdateOffer_ApprovedLink_ACH_VCA_POSTResponse";
                message = "Approved Link ACH to VCA";
            }else if(linkStatus.equalsIgnoreCase("Approved") && fromPaymentType.equalsIgnoreCase("VCA") && toPaymentType.equalsIgnoreCase("ACH")){
                inputJsonFile = inputData.get(0).get("InputFileForPOST_Approved_VCA_ACH");
                inputQueryName = "API_UpdateOffer_ApprovedLink_VCA_ACH_POSTInput";
                outputQueryName = "API_UpdateOffer_ApprovedLink_VCA_ACH_POSTResponse";
                message = "Approved Link VCA to ACH";
            }

            dbResponse = apiDataProvider.getAPIDataProviderResponse(inputQueryName, new Object[] {});
            randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            clientCompanyID = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("ClientCompanyID"));
            if(clientCompanyID.equals("null")) clientCompanyID = "";
            entityIdentifier = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("EntityIdentifier"));
            if(entityIdentifier.equals("null")) entityIdentifier = "";
            sfdcCampaignID = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("SFDCCampaignID"));
            if(sfdcCampaignID.equals("null")) sfdcCampaignID = "";
            numberofChecks = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("NumberOfChecks"));
            if(numberofChecks.equals("null")) numberofChecks = "";
            totalDollar = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("TotalDollar"));
            if(totalDollar.equals("null")) totalDollar = "";
            venNumber = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("VenNumber"));
            if(venNumber.equals("null")) venNumber = "";
            vndPercentage = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("VNDPercentage"));
            if(vndPercentage.equals("null")) vndPercentage = "";
            accountNumber = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("AccountNumber"));
            if(accountNumber.equals("null")) accountNumber = "";
            bankName = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("BankName"));
            if(bankName.equals("null")) bankName = "";
            accountOwnerName = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("AccountOwnerName"));
            if(accountOwnerName.equals("null")) accountOwnerName = "";
            abaRoutingNumber = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("ABARoutingNumber"));
            if(abaRoutingNumber.equals("null")) abaRoutingNumber = "";
            remittanceDeliveryEmail = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("RemittanceDeliveryEmail"));
            if(remittanceDeliveryEmail.equals("null")) remittanceDeliveryEmail = "";
            else remittanceDeliveryEmail = appInd.getPropertyValueByKeyName("qaUserName");
            vCardEmailAddress = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("VCardEmailAddress"));
            if(vCardEmailAddress.equals("null")) vCardEmailAddress = "";
            else vCardEmailAddress = appInd.getPropertyValueByKeyName("qaUserName");
            paymentTypeID = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("PaymentTypeID"));
            if(paymentTypeID.equals("null")) paymentTypeID = "";
            bankCountryISO = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("BankCountryISO"));
            if(bankCountryISO.equals("null")) bankCountryISO = "";
            currencyCode = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("CurrencyCode"));
            if(currencyCode.equals("null")) currencyCode = "";
            accountTypeID = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("AccountTypeID"));
            if(accountTypeID.equals("null")) accountTypeID = "";
            offerNumber = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("OfferNumber"));
            if(offerNumber.equals("null")) offerNumber = "";
            vCardFax = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("VCardFax"));
            if(vCardFax.equals("null")) vCardFax = "";
            linkID = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("LinkID"));
            if(linkID.equals("null")) linkID = "";
            supplierCompanyID = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("SupplierCompanyID"));
            if(supplierCompanyID.equals("null")) supplierCompanyID = "";
            vCardNetwordID = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("VCardNetworkID"));
            if(vCardNetwordID.equals("null")) vCardNetwordID = "";

            strNestedJosnFieldToUpdate = "{\"reqObj\": \"{'ClientCompanyID':'"+clientCompanyID+"', " +
                    "'EntityIdentifier':'"+entityIdentifier+"', " +
                    "'SFDCCampaignID':'"+sfdcCampaignID+"', " +
                    "'NumberOfChecks':'"+numberofChecks+"', " +
                    "'TotalDollar':'"+totalDollar+"', " +
                    "'VenNumber':'"+venNumber+"', " +
                    "'VNDPercentage':'"+vndPercentage+"'," +
                    "'AccountNumber':'"+accountNumber+"', " +
                    "'BankName':'"+bankName+"', " +
                    "'AccountOwnerName':'"+accountOwnerName+"'," +
                    "'ABARoutingNumber':'"+abaRoutingNumber+"', " +
                    "'RemittanceDeliveryEmail':'"+remittanceDeliveryEmail+"'," +
                    "'VCardEmailAddress':'"+vCardEmailAddress+"', " +
                    "'PaymentTypeID':'"+paymentTypeID+"', 'BankCountryISO':'"+bankCountryISO+"', 'CurrencyCode':'"+currencyCode+"', 'AccountTypeID':'"+accountTypeID+"', 'OfferNumber':'"+offerNumber+"', " +
                    "'VCardFax':'"+vCardFax+"', 'LinkID':'"+linkID+"', 'SupplierCompanyID':'"+supplierCompanyID+"', 'VCardNetworkID':'"+vCardNetwordID+"'}\"}";

            postResponse = httpPOSTMethodRequestWithBasicAuth(strPostEndPoint, basicAuthentication, strNestedJosnFieldToUpdate, inputJsonFile, "No", "Valid");
            Assert.assertTrue(postResponse.getStatusCode() == 200, message+": " + postResponse.getBody().prettyPrint());

            String responseJson = postResponse.asString().replace("\\r\\n", "").replace("\\", "").replace("\\n", "").replace("\n", "").replace(" ", "").replace("\"", "");
            dbResponse = apiDataProvider.getAPIDataProviderResponse(outputQueryName, new Object[] {"linkID", linkID});
            Assert.assertTrue(responseJson.contains("Response:"));
            if(responseJson.contains("LinkID")) Assert.assertTrue(responseJson.contains("LinkID:"+((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("LinkID")));
            if(responseJson.contains("SupplierCompanyID")) Assert.assertTrue(responseJson.contains("SupplierCompanyID:"+((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("SupplierCompanyID")));
            if(responseJson.contains("ClientCompanyID")) Assert.assertTrue(responseJson.contains("ClientCompanyID:"+((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("ClientCompanyID")));
            if(responseJson.contains("EntityIdentifier")) Assert.assertTrue(responseJson.contains("EntityIdentifier:"+((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("EntityIdentifier")));
            if(responseJson.contains("CompanyID")) Assert.assertTrue(responseJson.contains("CompanyID:"+((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("SupplierCompanyID")));
            //if(responseJson.contains("CompanyName")) Assert.assertTrue(responseJson.contains("CompanyName:"+((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("CompanyName")));
            if(responseJson.contains("TaxID")) Assert.assertTrue(responseJson.contains("TaxID:"+((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("TaxID")));
            if(responseJson.contains("SFDCCampaignID")) Assert.assertTrue(responseJson.contains("SFDCCampaignID:"+((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("SFDCCampaignID")));
            if(responseJson.contains("NumberOfChecks")) Assert.assertTrue(responseJson.contains("NumberOfChecks:"+((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("NumberOfChecks")));
            if(responseJson.contains("TotalDollar")) Assert.assertTrue(responseJson.contains("TotalDollar:"+((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("TotalDollar")));
            if(responseJson.contains("VenNumber")) Assert.assertTrue(responseJson.contains("VenNumber:"+((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("VenNumber")));
            if(responseJson.contains("VNDPercentage")) Assert.assertTrue(responseJson.contains("VNDPercentage:"+((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("VNDPercentage")));
            if(responseJson.contains("PaymentTerms")) Assert.assertTrue(responseJson.contains("PaymentTerms:"+((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("PaymentTerms")));
            if(responseJson.contains("MaintenanceFeeMethod")) Assert.assertTrue(responseJson.contains("MaintenanceFeeMethod:"+((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("MaintenanceFeeMethod")));
            if(responseJson.contains("PaymentTypeID")) Assert.assertTrue(responseJson.contains("PaymentTypeID:"+((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("PaymentTypeID")));
            if(responseJson.contains("OfferNumber")) Assert.assertTrue(responseJson.contains("OfferNumber:"+((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("OfferNumber")));
            if(responseJson.contains("AccountNumber")) Assert.assertTrue(responseJson.contains("AccountNumber:"+((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("AccountNumber")));
            if(responseJson.contains("BankName")) Assert.assertTrue(responseJson.contains("BankName:"+((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("BankName")));
            if(responseJson.contains("AccountOwnerName")) Assert.assertTrue(responseJson.contains("AccountOwnerName:"+((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("AccountOwnerName")));
            if(responseJson.contains("ABARoutingNumber")) Assert.assertTrue(responseJson.contains("ABARoutingNumber:"+((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("ABARoutingNumber")));
            if(responseJson.contains("BankCountryISO")) Assert.assertTrue(responseJson.contains("BankCountryISO:"+((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("BankCountryISO")));
            //if(responseJson.contains("CurrencyCode")) Assert.assertTrue(responseJson.contains("CurrencyCode:"+((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("CurrencyCode")));
            if(responseJson.contains("AccountTypeID")) Assert.assertTrue(responseJson.contains("AccountTypeID:"+((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("AccountTypeID")));
            if(responseJson.contains("VCardEmailAddress")) Assert.assertTrue(responseJson.contains("VCardEmailAddress:"+((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("VCardEmailAddress")));
            if(responseJson.contains("VCardFax")) Assert.assertTrue(responseJson.contains("VCardFax:"+((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("VCardFax")));
            if(responseJson.contains("VCardNetworkID")) Assert.assertTrue(responseJson.contains("VCardNetworkID:"+((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("VCardNetworkID")));
            if(responseJson.contains("RemittanceDeliveryEmail")) Assert.assertTrue(responseJson.contains("RemittanceDeliveryEmail:"+((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("RemittanceDeliveryEmail")));
            Assert.assertTrue(responseJson.contains("Updated Successfully") || responseJson.contains("UpdatedSuccessfully"));
            return true;
        }catch(Exception e) {
            reports.writeResult(null, "Exception", "Exception in 'validateUpdatesOfferWithDifferentPaymentTypesAPIUsingPOSTMethod()' method. " + e.getMessage());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(null, "Exception", "AssertionError in 'validateUpdatesOfferWithDifferentPaymentTypesAPIUsingPOSTMethod()' method. " + e.getMessage());
            return false;
        }finally {inputData = null; postResponse = null; strPostEndPoint = null; basicAuthentication = null; dbResponse = null; randomNum = null; strNestedJosnFieldToUpdate = null; clientCompanyID = null; entityIdentifier = null; sfdcCampaignID = null; numberofChecks = null; totalDollar = null; venNumber = null; vndPercentage = null; accountNumber = null; bankName = null; accountOwnerName = null;
            abaRoutingNumber = null; remittanceDeliveryEmail = null; vCardEmailAddress = null; paymentTypeID = null; bankCountryISO = null; currencyCode = null; accountTypeID = null; offerNumber = null; vCardFax = null; linkID = null; supplierCompanyID = null; vCardNetwordID = null; inputJsonFile = null; inputQueryName = null; outputQueryName = null; objJsonPOSTResponse = null; message = null;
        }
    }





    /********************************************************
     * Method Name      : httpPOSTMethodRequestWithBasicAuth()
     * Purpose          : to perform POST http method operation using Basic Authentication and to return the Response object
     * Author           : Gudi
     * Parameters       : endPoint, basicAuth, String requestInputFile, requestInputFile, updateJson, validationType
     * ReturnType       : String
     ********************************************************/
    public Response httpPOSTMethodRequestWithBasicAuth(String endPoint, Map<String, String> basicAuth, String updatedJsonInput, String requestInputFile, String updateJson, String validationType){
        RequestSpecification httpRequest = null;
        Response response = null;
        JsonObject jsonObject = null;
        String jsonFileContent = null;
        try{
            //Modify/Update the input Json
            jsonFileContent = apiUtility.getJsonContentFromFile(requestInputFile);

            reports.writeResult(null, "Info", "The modified Request body for the 'httpPOSTMethodRequestWithBasicAuth()' POST request: " + updatedJsonInput);

            //Writing the updated json file back to the same file
            if(updateJson.equalsIgnoreCase("Yes"))
                apiUtility.writeJsonContentToFile(updatedJsonInput, jsonFileContent);

            RestAssured.baseURI = appInd.getPropertyValueByKeyName("dataProvider_API_EndPoint");
            RestAssured.useRelaxedHTTPSValidation();
            httpRequest = given().auth().preemptive().basic(basicAuth.get("enrollmentID"), basicAuth.get("accessCode"));
            response = httpRequest
                    .header("Accept", ContentType.JSON.getAcceptHeader())
                    .contentType(ContentType.JSON)
                    .body(updatedJsonInput)
                    .post(endPoint)
                    .prettyPeek();
            appDep.threadSleep(2000);
            if(validationType.equalsIgnoreCase("Valid")){
                reports.writeResult(null, "Info", "Executing Positive scenario using 'httpPOSTMethodRequestWithBasicAuth()' method with EndPoint: " + endPoint);
                if(response.getBody().toString().contains("errors")){
                    reports.writeResult(null, "Fail", "The method 'httpPOSTMethodRequestWithBasicAuth()' was failed. " + response.getBody().prettyPrint());
                    Assert.fail("The method 'httpPOSTMethodRequestWithBasicAuth()' was failed. " + jsonObject.toString());
                    return null;
                }else{
                    Assert.assertTrue(response.getStatusCode() == 200, "Invalid responseCode for the 'httpPOSTMethodRequestWithBasicAuth()' method");
                    reports.writeResult(null, "Pass", "The method 'httpPOSTMethodRequestWithBasicAuth()' was successful. " + response.getBody().prettyPrint());
                    return response;
                }
            }else{
                reports.writeResult(null, "Info", "Executing Negative scenario using 'httpPOSTMethodRequestWithBasicAuth()' method with EndPoint: "+ endPoint + "\n & the Response Body is: " + response.getBody().prettyPrint());
                return response;
            }

        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'httpPOSTMethodRequestWithBasicAuth()' method. " + e.getMessage());
            return null;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'httpPOSTMethodRequestWithBasicAuth()' method. " + e.getMessage());
            return null;
        }finally{httpRequest = null; response = null; jsonObject = null; jsonFileContent = null;}
    }

}
