package com.corcentric.baseSteps.api;

import com.corcentric.runner.CucumberTestRunner;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import net.minidev.json.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class API_ManagePaymentsBaseStep extends CucumberTestRunner {
    /********************************************************
     * Method Name      : managePaymentsCRUD()
     * Purpose          : to validate CRUD operations for Manage Payments API
     * Author           : Gudi
     * Parameters       : NA
     * ReturnType       : boolean
     ********************************************************/
    public boolean managePaymentsCRUD(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response getResponse = null;
        Response postResponse = null;
        Response putResponse = null;
        String strGetEndPoint = null;
        String strPutEndPoint = null;
        String strPostEndPoint = null;
        String paymentDetails[];
        String fieldsToUpdate = null;
        org.json.simple.JSONArray objJsonGETAllPaymentsByPaymentType = null;
        JSONArray dbResponse = null;
        String paymentID = null;
        org.json.simple.JSONObject objJsonGETDetailsByPaymentID = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);

            //CPAY-3134: API - GET - Managed-Payments - GetAllPaymentsByPaymentType
            strGetEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managePayments_GetByTypeID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", inputData.get(0).get("validTypeID")), apiManagePaymentsAuthToken, "Valid");

            objJsonGETAllPaymentsByPaymentType = (org.json.simple.JSONArray) apiUtility.convertJsonResponseIntoJSONObject("JsonArray", getResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagePaymentGETAllPaymentsByPaymentType", new Object[] {"paymentTypeID", 256});

            for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i), (JSONObject) objJsonGETAllPaymentsByPaymentType.get(i)
                        , "PaymentID#FormattedPaymentID#PaymentFileID#BankFileID#ClientVNetID#ClientName#SupplierVNetID#SupplierName#PaymentReference#PayeeEntityIdentifier#RemitToID#AmountIssued#DiscountAmount#AmountRequested#AmountPosted#AmountVoided#AmountRemaining#Currency#LinkID#PaymentTypeID#PaymentStatusID#PaymentCreatedAt#PaymentValueDate#PaymentFileApprovedAt#BankFileReleasedAt#BankFileDeliveredAt#LastStatusUpdateAt#VCardEmailAddress#IsVPay"
                        , "paymentId#formattedPaymentId#paymentFileId#bankFileId#clientVNetId#clientName#supplierVNetId#supplierName#paymentReference#payeeEntityIdentifier#remitToId#amountIssued#discountAmount#amountRequested#amountPosted#amountVoided#amountRemaining#currency#linkId#paymentTypeId#paymentStatusId#paymentCreatedAt#paymentValueDate#paymentFileApprovedAt#bankFileReleasedAt#bankFileDeliveredAt#lastStatusUpdateAt#vCardEmailAddress#isVPay"));
            }

            //Invalid scenario for get by TypeID
            strGetEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managePayments_GetByTypeID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", inputData.get(0).get("invalidTypeID")), apiManagePaymentsAuthToken, "Invalid");
            Assert.assertTrue(getResponse.getStatusCode() == 400, "Invalid statusCode when TypeID is wrong: " + getResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.status").toString().equalsIgnoreCase("400"));
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.title").toString().equalsIgnoreCase("One or more validation errors occurred."));
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.errors").toString().contains("TypeId should be 256"));

            //CPAY-3136: API - GET - Managed-Payments - GetBy ID
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagePaymentGetInputPaymentID", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            paymentID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("PaymentID").toString();

            strGetEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managePayments_GetByID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", paymentID), apiManagePaymentsAuthToken, "Valid");

            objJsonGETDetailsByPaymentID = (org.json.simple.JSONObject) apiUtility.convertJsonResponseIntoJSONObject("JsonObject", getResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagePayments_GETDetailsByPaymentID", new Object[] {"paymentID", paymentID});
            Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0), objJsonGETDetailsByPaymentID
                    , "PaymentFileID#PaymentFileName#BankFileID#BankFileRouteDesc#PaymentID#FormattedPaymentID#ClientCompanyID#ClientCompanyName#ParentClientCompanyID#ParentClientCompanyName#PaymentReference#SupplierCompanyID#SupplierCompanyName#PayeeEntityIdentifier#PayerEntityIdentifier#RemitTo#PaymentAmount#DiscountAmount#AmountRequested#AmountPosted#AmountVoided#AmountRemaining#Currency#LinkID#PaymentTypeID#PaymentTypeCode#PaymentStatusID#PaymentStatusDesc#VirtualCardEmailAddress#CheckNumberProvided#CheckNumberGenerated#ACHTraceNumber#CardholderReferenceNumber#FundingMethod#FundingAccountNumber#FundingAccountIdentifier#PaymentProcessor#FundingAccountCode#SupplierAddress1#SupplierAddress2#SupplierCity#SupplierStateProvince#SupplierCountryISO#CHKZipPostal#CheckClearedDate#CardIssueDate#SupplierBankAccountName#SupplierBankAccountNumber#SupplierBankRoutingNumber#SupplierBankName#CardHolderName#CardBankZipPostal#CardNumber#CardProcessedAt#CardCVC#CardExpiry#PaymentRemittanceFormatID#PaymentRemittanceFormatDesc#PaymentRemittanceDeliveryID#PaymentRemittanceDeliveryDesc#PaymentRemittanceDeliveryAddress"
                    , "paymentFileId#paymentFileName#bankFileId#bankFileRouteDesc#paymentId#formattedPaymentId#clientCompanyId#clientCompanyName#parentClientCompanyId#parentClientCompanyName#paymentReference#supplierCompanyId#supplierCompanyName#payeeEntityIdentifier#payerEntityIdentifier#remitTo#paymentAmount#discountAmount#amountRequested#amountPosted#amountVoided#amountRemaining#currency#linkId#paymentTypeId#paymentTypeCode#paymentStatusId#paymentStatusDesc#virtualCardEmailAddress#checkNumberProvided#checkNumberGenerated#achTraceNumber#cardholderReferenceNumber#fundingMethod#fundingAccountNumber#fundingAccountIdentifier#paymentProcessor#fundingAccountCode#supplierAddress1#supplierAddress2#supplierCity#supplierStateProvince#supplierCountryIso#chkZipPostal#checkClearedDate#cardIssueDate#supplierBankAccountName#supplierBankAccountNumber#supplierBankRoutingNumber#supplierBankName#cardHolderName#cardBankZipPostal#cardNumber#cardProcessedAt#cardCvc#cardExpiry#paymentRemittanceFormatId#paymentRemittanceFormatDesc#paymentRemittanceDeliveryId#paymentRemittanceDeliveryDesc#paymentRemittanceDeliveryAddress"));

            //Negative scenario for PaymentID to trigger the GET() method
            strGetEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managePayments_GetByID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", inputData.get(0).get("InvalidPaymentID")), apiManagePaymentsAuthToken, "Invalid");
            Assert.assertTrue(getResponse.getStatusCode() == 404, "Invalid statusCode when TypeID is wrong: " + getResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.status").toString().equalsIgnoreCase("404"));
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.title").toString().equalsIgnoreCase("Payment not found"));
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.detail").toString().contains("Payment doesn't exist"));

            //CPAY-3137: API - PUT - Managed-Payments - UpdateStatus-V2
            paymentDetails = getPaymentIDWithModifyStatusCode("API_ManagedPayment_UpdatePaymentStatus").split("#");
            fieldsToUpdate = "username#"+appInd.getPropertyValueByKeyName("qaUserName")+",statusId#"+paymentDetails[1];
            strPutEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managePayments_PutRequest");
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint.replace("%", paymentDetails[0]), apiManagePaymentsAuthToken, "", fieldsToUpdate, inputData.get(0).get("inputFileForPUT"), "No", "Valid");

            Assert.assertTrue(putResponse.getStatusCode() == 200, "PUT - Managed-Payments - UpdateStatus-V2: " +  putResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.result").toString().equalsIgnoreCase("success"));
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.message").toString().equalsIgnoreCase("Success"));

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagePayment_PUTUpdateStatusV2", new Object[] {"paymentID", paymentDetails[0]});
            Assert.assertTrue(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("PaymentID").toString().equalsIgnoreCase(paymentDetails[0]));
            Assert.assertTrue(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("FormattedPaymentID").toString().equalsIgnoreCase(paymentDetails[0]));
            Assert.assertTrue(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("PaymentStatusID").toString().equalsIgnoreCase(paymentDetails[1]));

            //Negative scenario for the manage Payments PUT request with invalid paymmentID
            strPutEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managePayments_PutRequest");
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint.replace("%", inputData.get(0).get("InvalidPaymentID")), apiManagePaymentsAuthToken, "", fieldsToUpdate, inputData.get(0).get("inputFileForPUT"), "No", "Invalid");
            Assert.assertTrue(putResponse.getStatusCode() == 404, "Invalid statusCode when PaymentID is wrong: " + putResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.status").toString().equalsIgnoreCase("404"));
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.title").toString().equalsIgnoreCase("Payment not found"));
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.detail").toString().contains("Payment cannot be found"));

            //Negative scenario for manage Payments POST request with invalid paymentID
            strPostEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managePaymentsUploadFile_PostRequest");
            postResponse = apiUtility.httpPOSTForAttachFileWithBearerAuth(strPostEndPoint.replace("%1", inputData.get(0).get("fileToUpload")).replace("%2", inputData.get(0).get("InvalidPaymentID")), apiManagePaymentsAuthToken, inputData.get(0).get("fileToUpload"), "Invalid");
            Assert.assertTrue((postResponse.getStatusCode() == 422) || (postResponse.getStatusCode() == 400), "Invalid statusCode when PaymentID is wrong: " + postResponse.getBody().prettyPrint());

            appInd.compareValues(null, JsonPath.read(postResponse.asString(), "$.fileId"), null);
            appInd.compareValues(null, JsonPath.read(postResponse.asString(),"$.success"), false);
            appInd.verifyContainsValues(JsonPath.read(postResponse.asString(),"$.message").toString(), "unable to get save location");
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in ' managePaymentsCRUD()' method. " + e.getMessage());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'managePaymentsCRUD()' method. " + e.getMessage());
            return false;
        }finally {inputData = null; getResponse = null; postResponse = null; putResponse = null; strGetEndPoint = null; strPutEndPoint = null; strPostEndPoint = null; paymentDetails = null; fieldsToUpdate = null; objJsonGETAllPaymentsByPaymentType = null; dbResponse = null; paymentID = null; objJsonGETDetailsByPaymentID = null;}
    }

    /********************************************************
     * Method Name      : getPaymentIDWithModifyStatusCode()
     * Purpose          : to get the paymentID as well as its update payment status id
     * Author           : Gudi
     * Parameters       : endPoint, postResponse, requestInputFile
     * ReturnType       : boolean
     ********************************************************/
    public String getPaymentIDWithModifyStatusCode(String queryKey){
        JSONArray dbResponse = null;
        String paymentID = null;
        String paymentType = null;
        String paymentStatus = null;
        String statusCode = null;
        try{
            dbResponse = apiDataProvider.getAPIDataProviderResponse(queryKey, new Object[] {});

            for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                int randomNum = Integer.parseInt(appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(), 1));
                paymentID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(randomNum-1)).get("PaymentID").toString();
                paymentType = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(randomNum-1)).get("PaymentType").toString();
                paymentStatus = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(randomNum-1)).get("PaymentStatus").toString();

                if(paymentType.equalsIgnoreCase("ACH Plus")) paymentType = "ACP";
                if(paymentType.equalsIgnoreCase("Virtual Card")) paymentType = "VCA";
                if(paymentType.equalsIgnoreCase("Check")) paymentType = "CHK";
                if(paymentType.equalsIgnoreCase("Prepaid Card")) paymentType = "VPC";

                switch(paymentType.toLowerCase()){
                    case "chk":
                        switch(paymentStatus.toLowerCase()){
                            case "processed":
                                statusCode = String.valueOf(appInd.getRandomNumberFromTheListSpecified(new Integer[]{21, 20, 14, 15}));
                                break;
                            case "shipped":
                                statusCode = String.valueOf(appInd.getRandomNumberFromTheListSpecified(new Integer[]{20, 14, 15}));
                                break;
                        }
                        break;
                    case "ach": case "acp":
                        switch(paymentStatus.toLowerCase()){
                            case "processed": case "confirmed":
                                statusCode = "7";
                                break;
                        }
                        break;
                    case "vca":
                        switch(paymentStatus.toLowerCase()){
                            case "authorized":
                                statusCode = String.valueOf(appInd.getRandomNumberFromTheListSpecified(new Integer[]{17, 18, 19, 14, 15, 6}));
                                break;
                            case "confirmed":
                                statusCode = String.valueOf(appInd.getRandomNumberFromTheListSpecified(new Integer[]{13, 19, 17, 18, 14, 15, 6}));
                                break;
                            case "partially processed":
                                statusCode = String.valueOf(appInd.getRandomNumberFromTheListSpecified(new Integer[]{19, 17, 18, 6}));
                                break;
                        }
                        break;
                    case  "vpc":
                        switch(paymentStatus.toLowerCase()){
                            case "confirmed":
                                statusCode = String.valueOf(appInd.getRandomNumberFromTheListSpecified(new Integer[]{17, 18, 19, 14, 15, 22}));
                                break;
                            case "partially processed":
                                statusCode = String.valueOf(appInd.getRandomNumberFromTheListSpecified(new Integer[]{17, 18, 19, 22}));
                                break;
                        }
                        break;
                }
                if(statusCode!=null) break;
            }

            return paymentID+"#"+statusCode;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'getPaymentIDWithModifyStatusCode()' method. " + e.getMessage());
            return null;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'getPaymentIDWithModifyStatusCode()' method. " + e.getMessage());
            return null;
        }finally {dbResponse = null; paymentID = null; paymentType = null; paymentStatus = null; statusCode = null;}
    }




    /********************************************************
     * Method Name      : getPaymentIDWithModifyStatusCode()
     * Purpose          : to validate Update Proxy Number API using PUT method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateUpdateProxyNumberByPUTMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response putResponse = null;
        String strPutEndPoint = null;
        JSONArray dbResponse = null;
        String companyID = null;
        String paymentID = null;
        String existingProxyNumber = null;
        String fieldsToUpdate = null;
        String newProxyNumber = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_UpdateProxyNumber_PUTInputs", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            paymentID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("PaymentID").toString();
            companyID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("CompanyID").toString();
            existingProxyNumber = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("Proxy").toString();
            newProxyNumber = appInd.getDateTime("ShhmmssS");

            //CPAY-1194: API - Put- Payments - Update proxy number
            strPutEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsUpdateProxyNumber_PUTRequest");
            fieldsToUpdate = "companyId#"+companyID+",proxyOld#"+existingProxyNumber+",proxyNew#"+newProxyNumber+",username#ggudi@corcentric.com";
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint.replace("%", companyID+"-"+paymentID), apiManagePaymentsAuthToken, "", fieldsToUpdate, inputData.get(0).get("inputFileForPUT"), "No", "Valid");
            Assert.assertTrue(putResponse.getStatusCode() == 200, "Update Proxy number API: " + putResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.result").toString().equalsIgnoreCase("success"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.message").toString().equalsIgnoreCase("Success"), "The line number: " + appInd.getCurrentLineNumber());

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_UpdateProxyNumber_PUTResponseOutput", new Object[] {"proxyNumber", newProxyNumber});

            for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                Assert.assertTrue(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("CompanyID").toString().equalsIgnoreCase(companyID), "The line number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("Proxy").toString().equalsIgnoreCase(newProxyNumber), "The line number: " + appInd.getCurrentLineNumber());

            }

            //CPAY-1194: API - Put- Payments - Update proxy number using both old and new proxy numbers are same
            strPutEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsUpdateProxyNumber_PUTRequest");
            fieldsToUpdate = "companyId#" + companyID + ",proxyOld#" + existingProxyNumber + ",proxyNew#" + existingProxyNumber + ",username#ggudi@corcentric.com";
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint.replace("%", companyID+"-"+paymentID), apiManagePaymentsAuthToken, "", fieldsToUpdate, inputData.get(0).get("inputFileForPUT"), "No", "Invalid");
            Assert.assertTrue(putResponse.getStatusCode() == 400, "Update Proxy number API with same old and new Proxy numbers: " + putResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.title").toString().equalsIgnoreCase("One or more validation errors occurred."), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.status").toString().equalsIgnoreCase("400"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.errors").toString().contains("ProxyNew value cannot be the same as ProxyOld value"), "The line number: " + appInd.getCurrentLineNumber());


            //CPAY-1194: API - Put- Payments - Update proxy number using invalid old proxy numbers and valid new proxy number
            strPutEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsUpdateProxyNumber_PUTRequest");
            fieldsToUpdate = "companyId#" + companyID + ",proxyOld#" + existingProxyNumber+"21212" + ",proxyNew#" + newProxyNumber + ",username#ggudi@corcentric.com";
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint.replace("%", companyID+"-"+paymentID), apiManagePaymentsAuthToken, "", fieldsToUpdate, inputData.get(0).get("inputFileForPUT"), "No", "Invalid");
            Assert.assertTrue(putResponse.getStatusCode() == 422, "Update Proxy number API with Invalid old and Valid new Proxy numbers: " + putResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.title").toString().equalsIgnoreCase("Error"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.status").toString().equalsIgnoreCase("422"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.detail").toString().contains("Fail: Old Proxy Not found for this Payment"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.instance").toString().contains("/v1/payments/"+companyID+"-"+paymentID+"/proxy-number"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.errors").toString().contains("Fail: Old Proxy Not found for this Payment"), "The line number: " + appInd.getCurrentLineNumber());

            return true;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'validateUpdateProxyNumberByPUTMethod()' method. " + e.getMessage());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateUpdateProxyNumberByPUTMethod()' method. " + e.getMessage());
            return false;
        }finally {inputData = null; putResponse = null; strPutEndPoint = null; dbResponse = null; companyID = null; paymentID = null; existingProxyNumber = null; fieldsToUpdate = null; newProxyNumber = null;}
    }




    /********************************************************
     * Method Name      : validateReturnPaymentStatusByGETMethod()
     * Purpose          : to validate Return payment status API using GET method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateReturnPaymentStatusByGETMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response getResponse = null;
        String strGetEndPoint = null;
        String strPutEndPoint = null;
        Response putResponse = null;
        JSONArray dbResponse = null;
        String paymentID = null;
        org.json.simple.JSONArray objJsonGETResponse = null;
        String fieldsToUpdate = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagePaymentGetInputPaymentID", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            paymentID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("PaymentID").toString();

            //CPAY-1208:  API - Get - Payments - return statuses with valid PaymentTypeID & StatusID
            strGetEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsReturnStatus_GETRequest");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint, apiManagePaymentsAuthToken, "Valid");

            objJsonGETResponse = (org.json.simple.JSONArray) apiUtility.convertJsonResponseIntoJSONObject("JsonArray", getResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ReturnPaymentStatusGETResponseOutput", new Object[] {});
            Assert.assertTrue(getResponse.getStatusCode() == 200, "return statuses: " + getResponse.getBody().prettyPrint());

            for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i), (JSONObject) objJsonGETResponse.get(i)
                        , "StatusID#Description"
                        , "statusId#name"));
            }


            //CPAY-1208:  API - Get - Payments - return statuses with valid PaymentTypeID & StatusID
            strGetEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsReturnStatus_GETByPaymentTypeIDAndStatusID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", inputData.get(0).get("paymentTypeID")).replace("%2", inputData.get(0).get("statusID")), apiManagePaymentsAuthToken, "Valid");

            objJsonGETResponse = (org.json.simple.JSONArray) apiUtility.convertJsonResponseIntoJSONObject("JsonArray", getResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_GETPaymentStatusByPaymentTypeAndStatusID", new Object[] {"paymentTypeID", inputData.get(0).get("paymentTypeID"), "statusID", inputData.get(0).get("statusID")});
            Assert.assertTrue(getResponse.getStatusCode() == 200, "return statuses with valid PaymentTypeID & StatusID: " + getResponse.getBody().prettyPrint());

            for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i), (JSONObject) objJsonGETResponse.get(i)
                        , "StatusID#Description"
                        , "statusId#name"));
            }


            //statusID and paymentTypeId are optional parameters. If one is provided then the other must be provided too. If provided then only return statuses that are valid transitions for the given status and payment type.
            // Otherwise this returns all statuses.
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", "").replace("%2", ""), apiManagePaymentsAuthToken, "Valid");
            objJsonGETResponse = (org.json.simple.JSONArray) apiUtility.convertJsonResponseIntoJSONObject("JsonArray", getResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ReturnPaymentStatusGETResponseOutput", new Object[] {});
            Assert.assertTrue(getResponse.getStatusCode() == 200, "return statuses: " + getResponse.getBody().prettyPrint());

            for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i), (JSONObject) objJsonGETResponse.get(i)
                        , "StatusID#Description"
                        , "statusId#name"));
            }


            //CPAY-1216: API - Put - Payments - Update Status id
//            fieldsToUpdate = "username#"+appInd.getPropertyValueByKeyName("qaUserName")+",statusId#";
//            strPutEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsReturnStatus_GETByPaymentID");
//            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint, apiManagePaymentsAuthToken, "", fieldsToUpdate, inputData.get(0).get("InputFileForPUT"), "No", "Valid");
            return true;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'validateReturnPaymentStatusByGETMethod()' method. " + e.getMessage());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateReturnPaymentStatusByGETMethod()' method. " + e.getMessage());
            return false;
        }finally{inputData = null; getResponse = null; strGetEndPoint = null; dbResponse = null; paymentID = null; objJsonGETResponse = null;}
    }




    /********************************************************
     * Method Name      : validateUpdateMultiSwipeByGETMethod()
     * Purpose          : to validate update Multi-swipe API using PUT method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateUpdateMultiSwipeByPUTMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response putResponse = null;
        String strPutEndPoint = null;
        JSONArray dbResponse = null;
        String paymentID = null;
        String invalidPaymentID = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_Payments_UpdateMultiSwipeGETInput", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            paymentID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("PaymentID").toString();

            //CPAY-1245: API - Put- Payments - Update proxy number with valid paymentID
            strPutEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsUpdateMultiSwipe_PUTByPaymentID");
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint.replace("%", paymentID), apiManagePaymentsAuthToken, "", "", inputData.get(0).get("inputFileForPUT"), "No", "Valid");
            Assert.assertTrue(putResponse.getStatusCode() == 200, "Update Multi-Swipe API with valid paymentID: " + putResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.result").toString().equalsIgnoreCase("success"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.message").toString().equalsIgnoreCase("Success. "), "The line number: " + appInd.getCurrentLineNumber());


            //CPAY-1245: API - Put- Payments - Update proxy number with invalid paymentID
            invalidPaymentID = paymentID+"21212";
            strPutEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsUpdateMultiSwipe_PUTByPaymentID");
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint.replace("%", invalidPaymentID), apiManagePaymentsAuthToken, "", "", inputData.get(0).get("inputFileForPUT"), "No", "Invalid");
            Assert.assertTrue(putResponse.getStatusCode() == 422, "Update Multi-Swipe API with Invalid paymentID: " + putResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.title").toString().equalsIgnoreCase("Error"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.status").toString().equalsIgnoreCase("422"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.detail").toString().equalsIgnoreCase("PayNet could not complete the requested action"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.instance").toString().equalsIgnoreCase("/v1/payments/"+invalidPaymentID+"/multiswipe"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.errors").toString().contains("PayNet could not complete the requested action"), "The line number: " + appInd.getCurrentLineNumber());

            return true;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'validateUpdateMultiSwipeByPUTMethod()' method. " + e.getMessage());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateUpdateMultiSwipeByPUTMethod()' method. " + e.getMessage());
            return false;
        }finally {inputData = null; putResponse = null; strPutEndPoint = null; dbResponse = null; paymentID = null; invalidPaymentID = null;}
    }




    /********************************************************
     * Method Name      : validateViewTransactionHistoryByGETMethod()
     * Purpose          : to validate update Multi-swipe API using PUT method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateViewTransactionHistoryByGETMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response getResponse = null;
        String strGetEndPoint = null;
        JSONArray dbResponse = null;
        String paymentID = null;
        org.json.simple.JSONArray objJsonGETResponseByPaymentID = null;
        org.json.JSONObject json = null;
        org.json.JSONArray jsonArray = null;
        org.json.JSONObject arrNodeObject = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_Payments_ViewTransactionHistory_GETInput", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            paymentID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("PaymentID").toString();

            //CPAY-1264:API - Get- Payments - View Transaction History by using valid paymentID
            strGetEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsViewTransactionHistory_GETByPaymentID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", paymentID), apiManagePaymentsAuthToken, "Valid");
            Assert.assertTrue(getResponse.getStatusCode() == 200, "View Transaction History by using valid paymentID: " + getResponse.getBody().prettyPrint());

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_Payment_ViewTransactionHistoryGETResponseOutput", new Object[] {"paymentID", paymentID});

            Assert.assertTrue(compareTransactionHistoryJsonArrayWithDBValues(getResponse, dbResponse, "dxcTransactions"
                    , "authorizationDate#postedDate#transactionStatus#cardNumber#tokenNumber#expirationDate#mcc#mccDescription#mccClassification#merchantName#authorizationResponseSetPointerDesc#merchantStreet#merchantCity#merchantState#merchantPostalCode#authorizationAmount#authorizationUniqueId#approvalCode#reverseFlag#eaCreditExpDate#eaInvoice"
                    , "AuthorizationDate#PostedDate#TransactionStatus#CardNumber#TokenNumber#ExpirationDate#MCC#MCCDescription#MCCClassification#MerchantName#AuthorizationResponseSetPointerDesc#MerchantStreet#MerchantCity#MerchantState#MerchantPostalCode#AuthorizationAmount#AuthorizationUniqueId#ApprovalCode#ReverseFlag#EACreditExpDate#EAInvoice"));


            json = new org.json.JSONObject(getResponse.asString());
            jsonArray = json.getJSONObject("data").getJSONArray("dxcTransactions");

            for(int i=0; i<jsonArray.length(); i++) {
                arrNodeObject = jsonArray.getJSONObject(i);
                Assert.assertTrue(String.valueOf(arrNodeObject.get("authorizationTime")).contains(String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("AuthorizationTime"))), "Mis-match in Actual: '"+String.valueOf(arrNodeObject.get("authorizationTime"))+"' & Expected: '"+String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("AuthorizationTime"))+"' values. Line Number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(String.valueOf(arrNodeObject.get("postedTime")).contains(String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("PostedTime"))), "Mis-match in Actual '"+String.valueOf(arrNodeObject.get("postedTime"))+"' & Expected '"+String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("PostedTime"))+"' values. Line Number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(Double.parseDouble(String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("CardBalance"))) == Double.parseDouble((String.valueOf(arrNodeObject.get("cardBalance")))), "Mis-match in Actual '"+Double.parseDouble(String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("CardBalance")))+"' & Expected '"+Double.parseDouble((String.valueOf(arrNodeObject.get("cardBalance"))))+"' values. Line Number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(Double.parseDouble(String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("PostedAmount"))) == Double.parseDouble((String.valueOf(arrNodeObject.get("postedAmount")))), "Mis-match in Actual '"+Double.parseDouble(String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("PostedAmount")))+"' & Expected '"+Double.parseDouble((String.valueOf(arrNodeObject.get("postedAmount"))))+"' values. Line Number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(Double.parseDouble(String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("InterchangeFeeAmt"))) == Double.parseDouble((String.valueOf(arrNodeObject.get("interchangeFeeAmt")))), "Mis-match in Actual '"+Double.parseDouble(String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("InterchangeFeeAmt")))+"' & Expected '"+Double.parseDouble((String.valueOf(arrNodeObject.get("interchangeFeeAmt"))))+"' values. Line Number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(Double.parseDouble(String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("EACreditLimit"))) == Double.parseDouble((String.valueOf(arrNodeObject.get("eaCreditLimit")))), "Mis-match in Actual '"+Double.parseDouble(String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("EACreditLimit")))+"' & Expected '"+Double.parseDouble((String.valueOf(arrNodeObject.get("eaCreditLimit"))))+"' values. Line Number: " + appInd.getCurrentLineNumber());
            }


            //CPAY-1264:API - Get- Payments - View Transaction History by using Invalid paymentID
            strGetEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsViewTransactionHistory_GETByPaymentID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", paymentID+inputData.get(0).get("invalidPaymentId")), apiManagePaymentsAuthToken, "Invalid");
            Assert.assertTrue(getResponse.getStatusCode() == 422, "View Transaction History by using Invalid paymentID: " + getResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.type").toString().equalsIgnoreCase("https://datatracker.ietf.org/doc/html/rfc4918#section-11.2"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.title").toString().equalsIgnoreCase("Error"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.status").toString().equalsIgnoreCase("422"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.detail").toString().replace("\n", "").contains("Internal server error System.Text.Json.JsonException: The input does not contain any JSON tokens. Expected the input to start with a valid JSON token, when isFinalBlock is true. Path: $ | LineNumber: 0 | BytePositionInLine: 0. ---> System.Text.Json.JsonReaderException: The input does not contain any JSON tokens. Expected the input to start with a valid JSON token, when isFinalBlock is true. LineNumber: 0 | BytePositionInLine: 0.   at System.Text.Json.ThrowHelper.ThrowJsonReaderException(Utf8JsonReader& json, ExceptionResource resource, Byte nextByte, ReadOnlySpan`1 bytes)   at System.Text.Json.Utf8JsonReader.Read()   at System.Text.Json.Serialization.JsonConverter`1.ReadCore(Utf8JsonReader& reader, JsonSerializerOptions options, ReadStack& state)   --- End of inner exception stack trace ---   at System.Text.Json.ThrowHelper.ReThrowWithPath(ReadStack& state, JsonReaderException ex)   at System.Text.Json.Serialization.JsonConverter`1.ReadCore(Utf8JsonReader& reader, JsonSerializerOptions options, ReadStack& state)   at System.Text.Json.JsonSerializer.ReadFromSpan[TValue](ReadOnlySpan`1 utf8Json, JsonTypeInfo jsonTypeInfo, Nullable`1 actualByteCount)   at System.Text.Json.JsonSerializer.ReadFromSpan[TValue](ReadOnlySpan`1 json, JsonTypeInfo jsonTypeInfo)   at System.Text.Json.JsonSerializer.Deserialize[TValue](String json, JsonSerializerOptions options)   at MP.Application.GetProviderTransactions.Handler.Handle(GetProviderTransactions request, CancellationToken cancellationToken) in /opt/atlassian/pipelines/agent/build/MP.Application/Modules/Payments/Queries/GetProviderTransactions.cs:line"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.instance").toString().equalsIgnoreCase("/v1/payments/"+paymentID+inputData.get(0).get("invalidPaymentId")+"/provider-transactions"), "Line Number: " + appInd.getCurrentLineNumber());
            //Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.errors").toString().replace("\\", "").replace("\\n", "").contains("Internal server error System.Text.Json.JsonException: The input does not contain any JSON tokens. Expected the input to start with a valid JSON token, when isFinalBlock is true. Path: $ | LineNumber: 0 | BytePositionInLine: 0. ---> System.Text.Json.JsonReaderException: The input does not contain any JSON tokens. Expected the input to start with a valid JSON token, when isFinalBlock is true. LineNumber: 0 | BytePositionInLine: 0.   at System.Text.Json.ThrowHelper.ThrowJsonReaderException(Utf8JsonReader& json, ExceptionResource resource, Byte nextByte, ReadOnlySpan`1 bytes)   at System.Text.Json.Utf8JsonReader.Read()   at System.Text.Json.Serialization.JsonConverter`1.ReadCore(Utf8JsonReader& reader, JsonSerializerOptions options, ReadStack& state)   --- End of inner exception stack trace ---   at System.Text.Json.ThrowHelper.ReThrowWithPath(ReadStack& state, JsonReaderException ex)   at System.Text.Json.Serialization.JsonConverter`1.ReadCore(Utf8JsonReader& reader, JsonSerializerOptions options, ReadStack& state)   at System.Text.Json.JsonSerializer.ReadFromSpan[TValue](ReadOnlySpan`1 utf8Json, JsonTypeInfo jsonTypeInfo, Nullable`1 actualByteCount)   at System.Text.Json.JsonSerializer.ReadFromSpan[TValue](ReadOnlySpan`1 json, JsonTypeInfo jsonTypeInfo)   at System.Text.Json.JsonSerializer.Deserialize[TValue](String json, JsonSerializerOptions options)   at MP.Application.GetProviderTransactions.Handler.Handle(GetProviderTransactions request, CancellationToken cancellationToken) in /opt/atlassian/pipelines/agent/build/MP.Application/Modules/Payments/Queries/GetProviderTransactions.cs:line"), "Line Number: " + appInd.getCurrentLineNumber());
            return true;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'validateViewTransactionHistoryByGETMethod()' method. " + e.getMessage());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateViewTransactionHistoryByGETMethod()' method. " + e.getMessage());
            return false;
        }finally {inputData = null; getResponse = null; strGetEndPoint = null; dbResponse = null; paymentID = null; objJsonGETResponseByPaymentID = null; json = null; jsonArray = null; arrNodeObject = null;}
    }




    /********************************************************
     * Method Name      : validatePaymentsPollAndPollByDateByGETMethod()
     * Purpose          : to validate Payments Poll & Poll By Date API using GET method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validatePaymentsPollAndPollByDateByGETMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response getResponse = null;
        String strGetEndPoint = null;
        JSONArray dbResponse = null;
        org.json.simple.JSONArray objJsonGETResponseByPollDate = null;
        org.json.simple.JSONArray objJsonGETResponseByPoll = null;
        ResponseBody responseBody = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_Payments_PollByDateGETInputParams", new Object[]{});
            String startDate = (((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("Start Date").toString().split("T"))[0];
            String endDate = (((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("End Date").toString().split("T"))[0];

            //CPAY-2878: API- Stop Fraud- get - Poll and poll by date
            strGetEndPoint = appInd.getPropertyValueByKeyName("managePaymentsPollEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsPollByDate_GETPollByDate");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", startDate).replace("%2", endDate), apiManagePaymentsAuthToken, "Valid");
            Assert.assertTrue(getResponse.getStatusCode() == 200, "Payments poll by date API: " + getResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());

            objJsonGETResponseByPollDate = (org.json.simple.JSONArray) apiUtility.convertJsonResponseIntoJSONObject("JsonArray", getResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_Payments_GetPollByDate_GETResponse", new Object[] {"startDate", startDate, "endDate", endDate});

            for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i), (JSONObject) objJsonGETResponseByPollDate.get(i)
                        , "PaymentID#ClientCompanyID#ClientCompanyName#ParentClientCompanyID#ParentClientCompanyName#PaymentReference#SupplierCompanyID#SupplierCompanyName#RemitToID#DiscountAmount#AmountPosted#AmountVoided#Currency#PaymentType#PaymentStatus"
                        , "paymentId#clientCompanyId#clientCompanyName#parentClientCompanyId#parentClientCompanyName#paymentReference#supplierCompanyId#supplierName#remitToId#discountAmount#postedAmount#voidedAmount#paymentCurrency#paymentType#status"));

                Assert.assertTrue((((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("PaymentAmount").toString().contains(((JSONObject) objJsonGETResponseByPollDate.get(i)).get("amount").toString())), "Mis-match in Actual: '"+(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("PaymentAmount").toString()+"' & Expected '"+((JSONObject) objJsonGETResponseByPollDate.get(i)).get("amount").toString()+"' values. Line Number: "+appInd.getCurrentLineNumber()));
                Assert.assertTrue((((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("AmountRequested").toString().contains(((JSONObject) objJsonGETResponseByPollDate.get(i)).get("requestedAmount").toString())), "Mis-match in Actual: '"+(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("AmountRequested").toString()+"' & Expected '"+((JSONObject) objJsonGETResponseByPollDate.get(i)).get("requestedAmount").toString()+"' values. Line Number: "+appInd.getCurrentLineNumber()));
            }


            //CPAY-2878: API- Stop Fraud- get - Poll and poll by date: Invalid start and end date
            strGetEndPoint = appInd.getPropertyValueByKeyName("managePaymentsPollEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsPollByDate_GETPollByDate");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", endDate).replace("%2", startDate), apiManagePaymentsAuthToken, "Invalid");
            Assert.assertTrue(getResponse.getStatusCode() == 200, "Payments poll by date API by invalid date range : " + getResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());
            responseBody = getResponse.getBody();
            Assert.assertTrue(responseBody.asString().replace(" ","").replace("\n", "").equals("[]"), "Failed to return the empty array when invalid date range was given for the Poll By Date API. The Line Number: " + appInd.getCurrentLineNumber());


            //CPAY-2878: API- Stop Fraud- get - Poll
            strGetEndPoint = appInd.getPropertyValueByKeyName("managePaymentsPollEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsPoll_GETPoll");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint, apiManagePaymentsAuthToken, "Valid");
            Assert.assertTrue(getResponse.getStatusCode() == 200, "Payments poll API: " + getResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());

            responseBody = getResponse.getBody();
            if(responseBody.asString().replace("\n", "").trim().equals("[    ]") || responseBody.asString().replace("\n", "").trim().equals("[]")){
                reports.writeResult(null, "Info", "Supplier Creation/Updation should have done for any supplier to display data. Else empty array is displayed []");
            }else{
                objJsonGETResponseByPoll = (org.json.simple.JSONArray) apiUtility.convertJsonResponseIntoJSONObject("JsonArray", getResponse.asString());
                dbResponse = apiDataProvider.getAPIDataProviderResponse("API_CompanyServicesGETSupplierLinksPoll", new Object[]{});

                for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                    Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i), (JSONObject) objJsonGETResponseByPoll.get(i)
                            , "PaymentID#ClientCompanyID#ClientCompanyName#ParentClientCompanyID#ParentClientCompanyName#PaymentReference#SupplierCompanyID#SupplierCompanyName#RemitToID#DiscountAmount#AmountPosted#AmountVoided#Currency#PaymentType#PaymentStatus"
                            , "paymentId#clientCompanyId#clientCompanyName#parentClientCompanyId#parentClientCompanyName#paymentReference#supplierCompanyId#supplierName#remitToId#discountAmount#postedAmount#voidedAmount#paymentCurrency#paymentType#status"));
                }
            }

            return true;

        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'validatePaymentsPollAndPollByDateByGETMethod()' method. " + e.getMessage());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validatePaymentsPollAndPollByDateByGETMethod()' method. " + e.getMessage());
            return false;
        }finally {inputData = null; getResponse = null; strGetEndPoint = null; dbResponse = null; objJsonGETResponseByPollDate = null; objJsonGETResponseByPoll = null; responseBody = null;}
    }

    /********************************************************
     * Method Name      : compareTransactionHistoryJsonArrayWithDBValues()
     * Purpose          : to update/modify the Json Array node based on the name
     * Author           : Gudi
     * Parameters       : arrFieldsToModify, jsonFileContent
     * ReturnType       : String
     ********************************************************/
    public boolean compareTransactionHistoryJsonArrayWithDBValues(Response response, JSONArray dbResponse, String jsonArrayNodeName, String jsonColumns, String dbColumns){
        org.json.JSONObject json = null;
        org.json.JSONArray jsonArray = null;
        String arrJSonColumns[];
        String arrDBColumns[];
        String dbData = null;
        String jsonData = null;
        org.json.JSONObject arrNodeObject = null;
        try{
            json = new org.json.JSONObject(response.asString());
            jsonArray = json.getJSONObject("data").getJSONArray(jsonArrayNodeName);
            arrJSonColumns = jsonColumns.split("#");
            arrDBColumns = dbColumns.split("#");
            for (int l = 0; l < jsonArray.length(); l++){
                arrNodeObject = jsonArray.getJSONObject(l);
                for(int i=0; i<arrJSonColumns.length; i++){
                    jsonData = String.valueOf(arrNodeObject.get(arrJSonColumns[i]));
                    dbData = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(l)).get(arrDBColumns[i]));
                    Assert.assertTrue(jsonData.equalsIgnoreCase(dbData) || jsonData.contains(dbData), "Mis-match in Actual: "+ dbData +" and Expected: "+ jsonData + " Values for Links Data for the column name: '"+arrJSonColumns[i]+"'. \n Line Number: " + appInd.getCurrentLineNumber());
                }
            }
            reports.writeResult(null, "Pass", "The Link details from DB and Json response are matching");
            return true;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'compareTransactionHistoryJsonArrayWithDBValues()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'compareTransactionHistoryJsonArrayWithDBValues()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }finally {json = null; jsonArray = null; arrJSonColumns = null; arrDBColumns = null; dbData = null; jsonData = null; arrNodeObject = null;}
    }





    /********************************************************
     * Method Name      : validateGetPaymentDetailsAndSearchPaymentIDByGETMethod()
     * Purpose          : to validate search Payment ID & Get Payment Details API using GET method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : String
     ********************************************************/
    public boolean validateGetPaymentDetailsAndSearchPaymentIDByGETMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response getResponse = null;
        String strGetEndPoint = null;
        JSONArray dbResponse = null;
        String paymentID = null;
        String companyID = null;
        String responseJson = null;
        org.json.simple.JSONObject objJsonGETResponse = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_SearchPaymentID_GETInputParams", new Object[]{});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            String paymentDetails = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("PaymentID").toString();
            companyID = (paymentDetails.split("-"))[0].trim();
            paymentID = (paymentDetails.split("-"))[1].trim();

            //CPAY-1076: API - GET- Payments-Search Payment ID
            strGetEndPoint = appInd.getPropertyValueByKeyName("managePaymentsPollEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsSearchPaymentID_GETByCompanyAndPaymentID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", companyID).replace("%2", paymentID), apiManagePaymentsAuthToken, "Valid");
            Assert.assertTrue(getResponse.getStatusCode() == 200, "Search Payment By PaymentID: " + getResponse.getBody().prettyPrint()+": Line Number: " + appInd.getCurrentLineNumber());
            responseJson = JsonPath.read(getResponse.asString(), "$.isValidPaymentId").toString();
            Assert.assertTrue(responseJson.equalsIgnoreCase("true"), "Line Number: " + appInd.getCurrentLineNumber());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_SearchPaymentID_GETByPaymentIDResponse", new Object[]{"paymentID", companyID+"-"+paymentID});
            Assert.assertTrue(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("isValidPaymentId").toString().equalsIgnoreCase(responseJson), "Line Number: " + appInd.getCurrentLineNumber());


            //CPAY-1077: API - GET- Payments-Get Payment Details
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_GetPaymentDetails_GETInput", new Object[]{});
            randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            paymentDetails = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("PaymentID").toString();
            companyID = (paymentDetails.split("-"))[0].trim();
            paymentID = (paymentDetails.split("-"))[1].trim();


            //CPAY-1077: API - GET- Payments-Get Payment Details
            strGetEndPoint = appInd.getPropertyValueByKeyName("managePaymentsPollEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsGetPaymentDetails_GETByPaymentAndCompanyID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", paymentID).replace("%2", companyID), apiManagePaymentsAuthToken, "Valid");

            objJsonGETResponse = (org.json.simple.JSONObject) apiUtility.convertJsonResponseIntoJSONObject("JsonObject", getResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_GetPaymentDetails_GETByPaymentIDResponse", new Object[] {"paymentID", paymentDetails});
            Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0), objJsonGETResponse
                    , "PaymentID#PaymentFileID#PaymentReference#SupplierName#SupplierPayNetID#PayeeEntityIdentifier#RemitToID#ClientCompanyID#ClientCompanyName#ParentClientCompanyID#ParentClientCompanyName#Amount#PaymentCurrency#PaymentType#PaymentStatusDesc#RouteDescription#ACHTraceNumber#CheckNumber"
                    , "paymentId#paymentFileId#paymentReference#supplierName#supplierPayNetId#payeeEntityIdentifier#remitToId#clientCompanyId#clientCompanyName#parentClientCompanyId#parentClientCompanyName#amount#paymentCurrency#paymentType#status#routeDescription#ACHTraceNumber#checkNumber"));

            return true;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'validateGetPaymentDetailsAndSearchPaymentIDByGETMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateGetPaymentDetailsAndSearchPaymentIDByGETMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }finally {inputData = null; getResponse = null; strGetEndPoint = null; dbResponse = null; paymentID = null; companyID = null; responseJson = null; objJsonGETResponse = null;}
    }





    /********************************************************
     * Method Name      : validatePaymentFileIDAndBankFileIDForDownloadByGETMethod()
     * Purpose          : to validate Payment File ID & Bank File ID API using GET method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validatePaymentFileIDAndBankFileIDForDownloadByGETMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response getResponse = null;
        String strGetEndPoint = null;
        JSONArray dbResponse = null;
        String paymentFileID = null;
        String bankFileID = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);

            //CPAY-1080: API - GET- Payment Files-Download Payment File
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_Payments_PaymentFileIDDownload_GETInput", new Object[]{});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            paymentFileID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("FileID").toString();

            strGetEndPoint = appInd.getPropertyValueByKeyName("managePaymentsPollEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsPaymentFileIDDownload_GETByFileID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", paymentFileID), apiManagePaymentsAuthToken, "Valid");
            Assert.assertTrue(getResponse.getStatusCode() == 200, "Payment Files-Download API: " + getResponse.getBody().prettyPrint()+": Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(getResponse.getBody().prettyPrint().getBytes().length > 0, "Download Payment File API was failed. Line Number: " + appInd.getCurrentLineNumber());


            //CPAY-1083: API - API - GET- Payments - Bank Files - Download Bank Files
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_Payments_BankFileIDDownlad_GETInput", new Object[]{});
            randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            bankFileID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("BFileID").toString();

            strGetEndPoint = appInd.getPropertyValueByKeyName("managePaymentsPollEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsBankFileIDDownload_GETByFileID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", bankFileID), apiManagePaymentsAuthToken, "Valid");
            Assert.assertTrue(getResponse.getStatusCode() == 200, "Bank Files-Download API: " + getResponse.getBody().prettyPrint()+": Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(getResponse.getBody().prettyPrint().getBytes().length > 0, "Download Bank File API was failed. Line Number: " + appInd.getCurrentLineNumber());
            return true;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'validatePaymentFileIDAndBankFileIDForDownloadByGETMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validatePaymentFileIDAndBankFileIDForDownloadByGETMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }finally {inputData = null; getResponse = null; strGetEndPoint = null; dbResponse = null; paymentFileID = null; bankFileID = null;}
    }




    /********************************************************
     * Method Name      : validatePaymentFileDetailsAndPaymentFilesListDetailsByBankIDUsingGETMethod()
     * Purpose          : to validate Payment Files List Details by Bank ID API using GET method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validatePaymentFileDetailsAndPaymentFilesListDetailsByBankIDUsingGETMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response getResponse = null;
        String strGetEndPoint = null;
        JSONArray dbResponse = null;
        String bankFileID = null;
        String paymentFileID = null;
        org.json.simple.JSONArray objJsonGETResponseByFileID = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);

            //CPAY-1078: API - GET- Payments - Payment Files-Get Payment Details by Bank ID
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_PaymentDetailFileList_GETByBankIDInput", new Object[]{});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            bankFileID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("BFileID").toString();

            strGetEndPoint = appInd.getPropertyValueByKeyName("managePaymentsPollEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsPaymentFileList_GETByFileID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", bankFileID), apiManagePaymentsAuthToken, "Valid");
            Assert.assertTrue(getResponse.getStatusCode() == 200, "Get Payment Files List API: " + getResponse.getBody().prettyPrint()+": Line Number: " + appInd.getCurrentLineNumber());


            objJsonGETResponseByFileID = (org.json.simple.JSONArray) apiUtility.convertJsonResponseIntoJSONObject("JsonArray", getResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_GetPaymentFileListDetails_GETResponse", new Object[] {"bankFileID", bankFileID});

            for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i), (JSONObject)  objJsonGETResponseByFileID.get(i)
                        , "paymentFileId#MailboxID#CompanyID#CompanyName#StatusID#statusName#ProcessingStatusID#Recalculate#FileName#FileFormatID#PaymentCount#ExceptionCount#ProcessingFlags#ReceiveTypeID#UploadedBy#UploadComments#LastUpdatedBy#WarningCount#ApprovedBy"
                        , "paymentFileId#mailboxId#companyId#companyName#statusId#statusName#processingStatusId#recalculate#filename#fileFormatId#paymentCount#exceptionCount#processingFlags#receiveTypeId#uploadedBy#uploadComments#lastUpdatedBy#warningCount#approvedBy"));
            }




            //CPAY-1079: API - GET- Payments - Payment Files-Get Payment File Details
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_PaymentFileDetails_GETInput", new Object[]{});
            randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            paymentFileID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("PFileID").toString();

            strGetEndPoint = appInd.getPropertyValueByKeyName("managePaymentsPollEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsPaymentFileDetails_GETByPaymentFileID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", paymentFileID), apiManagePaymentsAuthToken, "Valid");

            objJsonGETResponseByFileID = (org.json.simple.JSONArray) apiUtility.convertJsonResponseIntoJSONObject("JsonArray", getResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_PaymentFileDetails_GETResponse", new Object[] {"paymentFileID", paymentFileID});

            for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i), (JSONObject)  objJsonGETResponseByFileID.get(i)
                        , "bankFileId#paymentFileId#companyId#companyName#bankFileStatusName#paymentFileStatusName#paymentFileReceivedAt#paymentFileApprovedAt#paymentFileApprovedBy#paymentFileControlTotal#paymentFileControlTotalDiscounted#filepath#paymentFileName#paymentFilePath#numPayments#paymentType#routeName#fundingConfirmationRequired#fundingMethod#debitAccount#vcCompany#vcAccount"
                        , "bankFileId#paymentFileId#companyId#companyName#bankFileStatusName#paymentFileStatusName#paymentFileReceivedAt#paymentFileApprovedAt#paymentFileApprovedBy#paymentFileControlTotal#paymentFileControlTotalDiscounted#filepath#paymentFileName#paymentFilePath#numPayments#paymentType#routeName#fundingConfirmationRequired#fundingMethod#debitAccount#vcCompany#vcAccount"));
            }
            return true;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'validatePaymentFileDetailsAndPaymentFilesListDetailsByBankIDUsingGETMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validatePaymentFileDetailsAndPaymentFilesListDetailsByBankIDUsingGETMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }finally {inputData = null; getResponse = null; strGetEndPoint = null; dbResponse = null; bankFileID = null; objJsonGETResponseByFileID = null; paymentFileID = null;}
    }




    /********************************************************
     * Method Name      : validatePaymentStopCheckAPIUsingPUTMethod()
     * Purpose          : to validate Payment StopCheck API using Put method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validatePaymentStopCheckAPIUsingPUTMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response putResponse = null;
        String strPutEndPoint = null;
        JSONArray dbResponse = null;
        String paymentID = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_AKF_CancelPaymentPUT", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            paymentID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("PaymentID").toString();


            //CPAY-1309: API - Put - Payments - StopCheck with valid paymentID
            strPutEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsStopCheck_PUTMethod");
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint.replace("%", paymentID), apiManagePaymentsAuthToken, "", "", inputData.get(0).get("inputFileForPUT"), "No", "Invalid");
            Assert.assertTrue(putResponse.getStatusCode() == 400, "StopCheck with valid paymentID API: " + putResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.result").toString().equalsIgnoreCase("error"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.message").toString().equalsIgnoreCase("Fail.  No Matching Record Found."), "Line Number: " + appInd.getCurrentLineNumber());


            //CPAY-1309: tAPI - Put - Payments - StopCheck with invalid payment ID
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_AKF_CannotCancelPaymentPUT", new Object[] {});
            randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            paymentID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("PaymentID").toString();
            strPutEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsStopCheck_PUTMethod");
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint.replace("%", paymentID), apiManagePaymentsAuthToken, "", "", inputData.get(0).get("inputFileForPUT"), "No", "Invalid");
            Assert.assertTrue(putResponse.getStatusCode() == 400, "StopCheck with valid paymentID API: " + putResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.result").toString().equalsIgnoreCase("error"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.message").toString().equalsIgnoreCase("Could not Cancel the Payment due to its Current Status. "), "Line Number: " + appInd.getCurrentLineNumber());

            return true;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'validatePaymentStopCheckAPIUsingPUTMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validatePaymentStopCheckAPIUsingPUTMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }finally {inputData = null; putResponse = null; strPutEndPoint = null; dbResponse = null; paymentID = null;}
    }




    /********************************************************
     * Method Name      : validateManagedPaymentUpdateFeesAPIUsingPUTMethod()
     * Purpose          : to validate Payment Update Fees API using Put method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateManagedPaymentUpdateFeesAPIUsingPUTMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response putResponse = null;
        String strPutEndPoint = null;
        JSONArray dbResponse = null;
        String paymentID = null;
        String feeAmount = null;
        String fieldsToUpdate = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagePayments_UpdateFees_PUTInput", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            paymentID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("PaymentID").toString();
            feeAmount = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("feeAmount").toString();


            //CPAY-1309: API - Put - Payments - Update Fees with valid paymentID
            strPutEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsUpdateFees_PUTMethod");
            fieldsToUpdate = "feeAmount#"+feeAmount;
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint.replace("%", paymentID), apiManagePaymentsAuthToken, "", fieldsToUpdate, inputData.get(0).get("inputFileForPUT"), "No", "Valid");
            Assert.assertTrue(putResponse.getStatusCode() == 200, "Update Fees with valid paymentID API: " + putResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.result").toString().equalsIgnoreCase("Success"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.message").toString().equalsIgnoreCase("Success"), "Line Number: " + appInd.getCurrentLineNumber());

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagedPayments_UpdateFees_PUTResponse", new Object[] {"paymentID", paymentID});
            Assert.assertTrue(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("PaymentID").toString().equalsIgnoreCase(paymentID), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(Double.parseDouble(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("FeeAmount").toString()) == Double.parseDouble(feeAmount), "Line Number: " + appInd.getCurrentLineNumber());
            return true;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'validateManagedPaymentUpdateFeesAPIUsingPUTMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateManagedPaymentUpdateFeesAPIUsingPUTMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }finally {inputData = null; putResponse = null; strPutEndPoint = null; dbResponse = null; paymentID = null;}
    }





    /********************************************************
     * Method Name      : validateManagedPaymentDefundAPIUsingPUTMethod()
     * Purpose          : to validate Payment Defund API using Put method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateManagedPaymentDefundAPIUsingPUTMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response putResponse = null;
        String strPutEndPoint = null;
        JSONArray dbResponse = null;
        String paymentID = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagePayments_Defund_PUTInput", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            paymentID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("PaymentID").toString();


            //CPAY-1309: tAPI - Put - Payments - Defund with valid paymentID
            strPutEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsDefund_PUTMethod");
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint.replace("%", paymentID), apiManagePaymentsAuthToken, "", "", inputData.get(0).get("inputFileForPUT"), "No", "Valid");
            Assert.assertTrue(putResponse.getStatusCode() == 200, "Defund with valid paymentID API: " + putResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.result").toString().equalsIgnoreCase("Success"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.message").toString().equalsIgnoreCase("Success"), "Line Number: " + appInd.getCurrentLineNumber());

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagePayments_Defund_PUTResponse", new Object[] {"paymentID", paymentID});
            Assert.assertTrue(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("PaymentID").toString().equalsIgnoreCase(paymentID), "Line Number: " + appInd.getCurrentLineNumber());

            //CPAY-1309: tAPI - Put - Payments - Defund with Invalid paymentID
            String invalidPaymentID = "000000000-"+appInd.getDateTime("mmss");
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint.replace("%", invalidPaymentID), apiManagePaymentsAuthToken, "", "", inputData.get(0).get("inputFileForPUT"), "No", "Invalid");
            Assert.assertTrue(putResponse.getStatusCode() == 422, "Defund with invalid paymentID API: " + putResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.type").toString().equalsIgnoreCase("https://datatracker.ietf.org/doc/html/rfc4918#section-11.2"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.title").toString().equalsIgnoreCase("Error"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.status").toString().equalsIgnoreCase("422"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.detail").toString().replace("\n", "").contains("Internal server error System.Text.Json.JsonException: The input does not contain any JSON tokens. Expected the input to start with a valid JSON token, when isFinalBlock is true. Path: $ | LineNumber: 0 | BytePositionInLine: 0. ---> System.Text.Json.JsonReaderException: The input does not contain any JSON tokens. Expected the input to start with a valid JSON token, when isFinalBlock is true. LineNumber: 0 | BytePositionInLine: 0.   at System.Text.Json.ThrowHelper.ThrowJsonReaderException(Utf8JsonReader& json, ExceptionResource resource, Byte nextByte, ReadOnlySpan`1 bytes)   at System.Text.Json.Utf8JsonReader.Read()   at System.Text.Json.Serialization.JsonConverter`1.ReadCore(Utf8JsonReader& reader, JsonSerializerOptions options, ReadStack& state)   --- End of inner exception stack trace ---   at System.Text.Json.ThrowHelper.ReThrowWithPath(ReadStack& state, JsonReaderException ex)   at System.Text.Json.Serialization.JsonConverter`1.ReadCore(Utf8JsonReader& reader, JsonSerializerOptions options, ReadStack& state)   at System.Text.Json.JsonSerializer.ReadFromSpan[TValue](ReadOnlySpan`1 utf8Json, JsonTypeInfo jsonTypeInfo, Nullable`1 actualByteCount)   at System.Text.Json.JsonSerializer.ReadFromSpan[TValue](ReadOnlySpan`1 json, JsonTypeInfo jsonTypeInfo)   at System.Text.Json.JsonSerializer.Deserialize[TValue](String json, JsonSerializerOptions options)   at MP.Application.DefundPayment.Handler.Handle(DefundPayment request, CancellationToken cancellationToken) in /opt/atlassian/pipelines/agent/build/MP.Application/Modules/Payments/Commands/DefundPayment.cs:line"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.instance").toString().equalsIgnoreCase("/v1/payments/"+invalidPaymentID+"/defund"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.errors").toString().replace("\\n", "").replace("\\", "").contains("Internal server error System.Text.Json.JsonException: The input does not contain any JSON tokens. Expected the input to start with a valid JSON token, when isFinalBlock is true. Path: $ | LineNumber: 0 | BytePositionInLine: 0. ---> System.Text.Json.JsonReaderException: The input does not contain any JSON tokens. Expected the input to start with a valid JSON token, when isFinalBlock is true. LineNumber: 0 | BytePositionInLine: 0.   at System.Text.Json.ThrowHelper.ThrowJsonReaderException(Utf8JsonReader& json, ExceptionResource resource, Byte nextByte, ReadOnlySpan`1 bytes)   at System.Text.Json.Utf8JsonReader.Read()   at System.Text.Json.Serialization.JsonConverter`1.ReadCore(Utf8JsonReader& reader, JsonSerializerOptions options, ReadStack& state)   --- End of inner exception stack trace ---   at System.Text.Json.ThrowHelper.ReThrowWithPath(ReadStack& state, JsonReaderException ex)   at System.Text.Json.Serialization.JsonConverter`1.ReadCore(Utf8JsonReader& reader, JsonSerializerOptions options, ReadStack& state)   at System.Text.Json.JsonSerializer.ReadFromSpan[TValue](ReadOnlySpan`1 utf8Json, JsonTypeInfo jsonTypeInfo, Nullable`1 actualByteCount)   at System.Text.Json.JsonSerializer.ReadFromSpan[TValue](ReadOnlySpan`1 json, JsonTypeInfo jsonTypeInfo)   at System.Text.Json.JsonSerializer.Deserialize[TValue](String json, JsonSerializerOptions options)   at MP.Application.DefundPayment.Handler.Handle(DefundPayment request, CancellationToken cancellationToken) in /opt/atlassian/pipelines/agent/build/MP.Application/Modules/Payments/Commands/DefundPayment.cs:line"), "Line Number: " + appInd.getCurrentLineNumber());
            return true;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'validateManagedPaymentDefundAPIUsingPUTMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateManagedPaymentDefundAPIUsingPUTMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }finally {inputData = null; putResponse = null; strPutEndPoint = null; dbResponse = null; paymentID = null;}
    }




    /********************************************************
     * Method Name      : validateManagedPaymentBankFileAndBankFileReceiptAPIByUsingPOSTMethod()
     * Purpose          : to validate Managed Payment Bank File & Bank File Receipt API using POST method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateManagedPaymentBankFileAndBankFileReceiptAPIByUsingPOSTMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response postResponse = null;
        String strPostEndPoint = null;
        JSONArray dbResponse = null;
        String startDate = null;
        String endDate = null;
        String filedsToUpdate = null;
        org.json.simple.JSONArray objJsonPOSTResponseByDate = null;
        Object nestedJsonFieldsToUpdate[][];
        String bankFileID = null;
        String paymentFileID = null;
        org.json.simple.JSONArray objJsonPOSTResponse = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);

            //CPAY-1081: API - POST- Payments - Bank Files - Bank File Receipts
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagedPayment_BankFileReceiptsList_POSTInputs", new Object[] {});
            startDate = (((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("Start Date").toString().split("T"))[0];
            endDate = (((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("End Date").toString().split("T"))[0];

            strPostEndPoint = appInd.getPropertyValueByKeyName("managePaymentsPollEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsBankFileReceiptsList_POSTMethod");
            filedsToUpdate = "startDate#"+startDate+",endDate#"+endDate;
            postResponse = apiUtility.httpPOSTMethodRequestWithBearerAuth(strPostEndPoint, apiManagePaymentsAuthToken, "", filedsToUpdate, new Object[][] {}, inputData.get(0).get("inputFileForPOST_BankFileReceipt"), "No", "Valid");
            Assert.assertTrue(postResponse.getStatusCode() == 201, "Bank File Receipts List API: " + postResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());

            objJsonPOSTResponseByDate = (org.json.simple.JSONArray) apiUtility.convertJsonResponseIntoJSONObject("JsonArray", postResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagedPayment_BankFileReceiptsList_POSTResponse", new Object[] {"startDate", startDate, "endDate", endDate});

            for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i), (JSONObject) objJsonPOSTResponseByDate.get(i)
                        , "PayNetClientID#ClientName#BankFileID#TransactionFileID#FileName#ReturnFileID#FileReceiptName#status#FileTotal"
                        , "companyId#companyName#bankFileId#transactionFileId#filename#returnFileId#fileReceiptName#status#fileTotal"));

                String fileType_DB = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("FileType").toString();
                String fileType_Json = ((JSONObject) objJsonPOSTResponseByDate.get(i)).get("fileType").toString();
                if(fileType_DB.equalsIgnoreCase("Bank File")){
                    Assert.assertTrue(fileType_DB.toLowerCase().contains("bank"), "Mis-match in actual : '"+fileType_DB+"' & expected '"+fileType_Json+"' values for the columnName: 'fileType' : The Line Number: " + appInd.getCurrentLineNumber());
                }else{
                    fileType_DB = "0"+fileType_DB;
                    Assert.assertTrue(fileType_DB.toLowerCase().contains("0150"), "Mis-match in actual : '"+fileType_DB+"' & expected '"+fileType_Json+"' values for the columnName: 'fileType' : The Line Number: " + appInd.getCurrentLineNumber());
                }
            }


            //CPAY-1084: API - POST- Payments -Bank Files - getBank Files with countOnly = true
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagePayment_GetBankFiles_POSTInput", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            bankFileID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("bankFileId").toString();
            paymentFileID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("paymentFileId").toString();

            strPostEndPoint = appInd.getPropertyValueByKeyName("managePaymentsPollEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsBankFiles_POSTMethod");
            nestedJsonFieldsToUpdate = new Object[][] {{"filter", "bankFileId", bankFileID, "paymentFileId", paymentFileID}, {"options", "countOnly", true}};
            postResponse = apiUtility.httpPOSTMethodRequestWithBearerAuth(strPostEndPoint, apiManagePaymentsAuthToken, "", "", nestedJsonFieldsToUpdate, inputData.get(0).get("inputFileForPOST_BankFile"), "No", "Valid");
            Assert.assertTrue(postResponse.getStatusCode() == 200, "Bank File Receipts List API: " + postResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagePayment_GetBankFile_CountOnlyTrue_PostResponse", new Object[] {"bankFileID", bankFileID, "paymentFileID", paymentFileID});
            Assert.assertTrue(JsonPath.read(postResponse.asString(), "$.count").toString().equalsIgnoreCase(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("count").toString()), "Line Number: "+appInd.getCurrentLineNumber());

            //CPAY-1084: API - POST- Payments -Bank Files - getBank Files with countOnly = false
            nestedJsonFieldsToUpdate = new Object[][] {{"filter", "bankFileId", bankFileID, "paymentFileId", paymentFileID}, {"options", "countOnly", false}};
            postResponse = apiUtility.httpPOSTMethodRequestWithBearerAuth(strPostEndPoint, apiManagePaymentsAuthToken, "", "", nestedJsonFieldsToUpdate, inputData.get(0).get("inputFileForPOST_BankFile"), "No", "Valid");
            Assert.assertTrue(postResponse.getStatusCode() == 200, "Bank File Receipts List API: " + postResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());

            objJsonPOSTResponse = (org.json.simple.JSONArray) apiUtility.convertJsonResponseIntoJSONObject("JsonArray", postResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagePayment_GetBankFile_CountOnlyFalse_PostResponse", new Object[] {"bankFileID", bankFileID, "paymentFileID", paymentFileID});
            for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i), (JSONObject) objJsonPOSTResponse.get(i)
                        , "BankFileID#paymentFileId#CompanyID#companyName#bankFileStatusName#routeId#BankRoute#paymentCount#isPrintImage#fileIdModifier#fileCreatedAt#isVcaACHCredit#BankFilePaymentType#fundingMethod#debitAccount#vcCompany#vcAccount#paymentFileStatusId#paymentFileStatusName#PaymentFileControlTotal#PaymentFileTotalwithDiscountsApplied"
                        , "bankFileId#paymentFileId#companyId#companyName#bankFileStatusName#routeId#routeName#paymentCount#isPrintImage#fileIdModifier#fileCreatedAt#isVcaACHCredit#paymentType#fundingMethod#debitAccount#vcCompany#vcAccount#paymentFileStatusId#paymentFileStatusName#paymentFileControlTotal#paymentFileTotalDiscounted"));
            }

            return true;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'validateManagedPaymentBankFileAndBankFileReceiptAPIByUsingPOSTMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateManagedPaymentBankFileAndBankFileReceiptAPIByUsingPOSTMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }finally {inputData = null; postResponse = null; strPostEndPoint = null; dbResponse = null; startDate = null; endDate = null; filedsToUpdate = null; objJsonPOSTResponseByDate = null; nestedJsonFieldsToUpdate = null; bankFileID = null; paymentFileID = null; objJsonPOSTResponse = null;}
    }


    /********************************************************
     * Method Name      : validateManagedPaymentFilterBankFilesByStatusesAPIByUsingPOSTMethod()
     * Purpose          : to validate Managed Payment Bank File Filter by statuses API using POST method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateManagedPaymentFilterBankFilesByStatusesAPIByUsingPOSTMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response postResponse = null;
        String strPostEndPoint = null;
        JSONArray dbResponse = null;
        String status1 = null;
        String status2 = null;
        org.json.simple.JSONArray objJsonGETResponseByStatus = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);

            //CPAY-2091: API- Post Request -bank-files - filter-by-statuses
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_BankFiles_FilterByStatuses_POSTInput", new Object[] {});
            status1 = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("statuses").toString();
            status2 = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(1)).get("statuses").toString();


            strPostEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsBankFileFilterByStatus_POSTMethod");
            postResponse = apiUtility.httpPOSTMethodRequestWithBearerAuth(strPostEndPoint, apiManagePaymentsAuthToken, "", "", new Object[][] {}, inputData.get(0).get("inputFileForPOST"), "No", "Valid");
            Assert.assertTrue(postResponse.getStatusCode() == 200, "Bank File Receipts List API: " + postResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());

            objJsonGETResponseByStatus = (org.json.simple.JSONArray) apiUtility.convertJsonResponseIntoJSONObject("JsonArray", postResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_BankFiles_FilterByStatuses_POSTResponse", new Object[] {"status1", status1, "status2", status2});

            for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i), (JSONObject) objJsonGETResponseByStatus.get(i)
                        , "BankFileID#PaymentFileID#PayNetClientID#ClientName#BankFileStatus#PaymentFileStatus#PaymentFileControlTotal#PaymentFileTotalwithDiscountsApplied#BankFilePath#BankFileNumberofPayments#BankFileTotalamount#PaymentType#BankFilePaymentType#BankRoute#FundingMethod#DebitAccount#VCCompany#VCAccount"
                        , "bankFileId#paymentFileId#companyId#companyName#bankFileStatusName#paymentFileStatusName#paymentFileControlTotal#paymentFileTotalDiscounted#filepath#paymentCount#bankFileTotal#paymentType#bankFilePaymentType#routeName#fundingMethod#debitAccount#vcCompany#vcAccount"));
            }

            // Asserting FundingConfirmationRequired field
            for (int i = 0; i <((JSONArray) dbResponse.get(0)).size(); i++) {
                String actual = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("FundingConfirmationRequired"));
                boolean expected = (boolean) ((JSONObject) objJsonGETResponseByStatus.get(i)).get("fundingConfirmationRequired");

                if (expected)
                    appInd.compareValues(null, actual.toLowerCase(), "yes");
                else
                    appInd.compareValues(null, actual.toLowerCase(), "no");
            }

            return true;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'validateManagedPaymentFilterBankFilesByStatusesAPIByUsingPOSTMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateManagedPaymentFilterBankFilesByStatusesAPIByUsingPOSTMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }finally {inputData = null; postResponse = null; strPostEndPoint = null; dbResponse = null; status1 = null; status2 = null; objJsonGETResponseByStatus = null;}
    }




    /********************************************************
     * Method Name      : validateManagedPaymentPendingAuthorizationDateAPIByUsingGETMethod()
     * Purpose          : to validate Managed Payment Authorization Date API using GET method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateManagedPaymentPendingAuthorizationDateAPIByUsingGETMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response getResponse = null;
        String strGetEndPoint = null;
        JSONArray dbResponse = null;
        String paymentID = null;
        String authorizationDate = null;
        String paymentProcessor = null;
        String paymentStatus = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);

            //CPAY-3891: API - MSV - Pending Authorization Date
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagePayments_AuthorizationDate_GETInput", new Object[]{});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            paymentID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("PaymentID").toString();
            authorizationDate = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("AuthorizationDate").toString();

            strGetEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsPendingAuthorizationDate_GETByPaymentID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", paymentID), apiManagePaymentsAuthToken, "Valid");
            Assert.assertTrue(getResponse.getStatusCode() == 200, "MSV - Pending Authorization Date API: " + getResponse.getBody().prettyPrint()+": Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.authorizationDate").toString().equalsIgnoreCase(authorizationDate), "Failed MSV - Pending Authorization Date: The Line number: " + appInd.getCurrentLineNumber());

            //CPAY-3891: API - MSV - Pending Authorization Date using the PaymentID with Payment Processor = DXC & has Payment status != Pending Authorization
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagePayments_DXC_NonAuthorizationDate_GETInput", new Object[]{});
            for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                paymentProcessor = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("Processor").toString();
                paymentStatus = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("PaymentStatus").toString();
                if(paymentProcessor.equalsIgnoreCase("DXC") && !paymentStatus.equalsIgnoreCase("Pending Authorization")){
                    paymentID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("PaymentID").toString();
                    break;
                }
            }
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", paymentID), apiManagePaymentsAuthToken, "Valid");
            Assert.assertTrue(getResponse.getStatusCode() == 200, "MSV - Pending Authorization Date API with invalid status & valid Processor: " + getResponse.getBody().prettyPrint()+": Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.authorizationDate")==null, "Failed MSV - Pending Authorization Date: The Line number: " + appInd.getCurrentLineNumber());



            //CPAY-3891: API - MSV - Pending Authorization Date using the PaymentID which Payment Processor != DXC
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagePayments_NonDXC_GETInput", new Object[]{});
            for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                paymentProcessor = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("Processor").toString();
                if(!paymentProcessor.equalsIgnoreCase("DXC")){
                    paymentID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("PaymentID").toString();
                    break;
                }
            }
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", paymentID), apiManagePaymentsAuthToken, "Valid");
            Assert.assertTrue(getResponse.getStatusCode() == 200, "MSV - Pending Authorization Date API with invalid status & valid Processor: " + getResponse.getBody().prettyPrint()+": Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.authorizationDate")==null, "Failed MSV - Pending Authorization Date: The Line number: " + appInd.getCurrentLineNumber());



            //CPAY-3891: API - MSV - Pending Authorization Date using the Invalid PaymentID (Not present in system)
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", inputData.get(0).get("invalidPaymentID")), apiManagePaymentsAuthToken, "Valid");
            Assert.assertTrue(getResponse.getStatusCode() == 200, "MSV - Pending Authorization Date API with invalid paymentID: " + getResponse.getBody().prettyPrint()+": Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.authorizationDate")==null, "Failed MSV - Pending Authorization Date: The Line number: " + appInd.getCurrentLineNumber());



            //CPAY-3891: API - MSV - Pending Authorization Date using the blank PaymentID
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", ""), apiManagePaymentsAuthToken, "Invalid");
            Assert.assertTrue(getResponse.getStatusCode() == 404, "MSV - Pending Authorization Date API with blank PaymentID: " + getResponse.getBody().prettyPrint()+": Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(getResponse.getBody().prettyPrint().equals(""), "Failed MSV - Pending Authorization Date: The Line number: " + appInd.getCurrentLineNumber());
            return true;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'validateManagedPaymentPendingAuthorizationDateAPIByUsingGETMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateManagedPaymentPendingAuthorizationDateAPIByUsingGETMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }finally {inputData = null; getResponse = null; strGetEndPoint = null; dbResponse = null; paymentID = null; authorizationDate = null; paymentProcessor = null; paymentStatus = null;}
    }




    /********************************************************
     * Method Name      : validateManagedPaymentGetBankFilesFilterByPaymentFileIDAPIByUsingGETMethod()
     * Purpose          : to validate Managed Payment Get Bank Files Filter by Payment File ID API using GET method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateManagedPaymentGetBankFilesFilterByPaymentFileIDAPIByUsingGETMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response getResponse = null;
        String strGetEndPoint = null;
        JSONArray dbResponse = null;
        String paymentFileID = null;
        org.json.simple.JSONArray objJsonGETResponseByPaymentFileID = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);

            //CPAY-2097: API- get Request -bank-files - filter-by-payment-files
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagePayment_BankFiles_FilterByPaymentFiles_GETInput", new Object[]{});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            paymentFileID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("PFileID").toString();

            strGetEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsBankFileFilterByPaymentFile_GETByPaymentFileID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", paymentFileID), apiManagePaymentsAuthToken, "Valid");
            Assert.assertTrue(getResponse.getStatusCode() == 200, "Payment Files-Download API: " + getResponse.getBody().prettyPrint()+": Line Number: " + appInd.getCurrentLineNumber());

            objJsonGETResponseByPaymentFileID = (org.json.simple.JSONArray) apiUtility.convertJsonResponseIntoJSONObject("JsonArray", getResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagePayment_BankFiles_FilterByPaymentFiles_GETResponse", new Object[] {"paymentFileID", paymentFileID});

            for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i), (JSONObject) objJsonGETResponseByPaymentFileID.get(i)
                        , "BankFileID#PaymentFileID#PayNetClientID#ClientName#BankFileStatus#PaymentFileStatus#PaymentFileControlTotal#PaymentFileTotalwithDiscountsApplied#BankFilePath#BankFileNumberofPayments#BankFilePaymentType#BankRoute"
                        , "bankFileId#paymentFileId#companyId#companyName#bankFileStatusName#paymentFileStatusName#paymentFileControlTotal#paymentFileTotalDiscounted#filepath#paymentCount#bankFilePaymentType#routeName"));
            }
            return true;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'validateManagedPaymentGetBankFilesFilterByPaymentFileIDAPIByUsingGETMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateManagedPaymentGetBankFilesFilterByPaymentFileIDAPIByUsingGETMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }finally {inputData = null; getResponse = null; strGetEndPoint = null; dbResponse = null; paymentFileID = null; objJsonGETResponseByPaymentFileID = null;}
    }




    /********************************************************
     * Method Name      : validateManagedPaymentReissueAPIByUsingPUTMethod()
     * Purpose          : to validate Managed Payment Reissue API using PUT method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateManagedPaymentReissueAPIByUsingPUTMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response putResponse = null;
        String strPutEndPoint = null;
        JSONArray dbResponse = null;
        String paymentID = null;
        String fieldsToUpdate = null;
        String paymentType = null;
        String paymentAmount = null;
        String paymentDate = null;
        String paymentReference = null;
        String remitToID = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagedPayment_ReIssue_PUTInput", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            paymentID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("PaymentID").toString();

            randomNum = appInd.generateRandomNumbers(1, 4,1);
            switch(Integer.parseInt(randomNum)){
                case 1: paymentType = "CHK";
                    break;
                case 2: paymentType = "ACH";
                    break;
                case 3: paymentType = "VCA";
                    break;
                case 4: paymentType = "ACP";
                    break;
                default:
                    reports.writeResult(null, "Fail", "Invalid option for 'Payment Type' for ReIssue");
                    Assert.fail("Invalid option for 'Payment Type' for ReIssue");
            }
            paymentAmount = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("Amount").toString();
            paymentDate = appInd.getDateTime("yyyyMMdd");
            paymentReference = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("Amount").toString();
            remitToID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("RemitTo").toString();

            //CPAY-1259: API - Put - Payments - Reissue with valid PaymentID
            strPutEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsReissue_PUTMethod");
            fieldsToUpdate = "paymentType#"+paymentType+",paymentAmount#"+paymentAmount+",paymentDate#"+paymentDate+",paymentReference#"+paymentReference+",remitToID#"+remitToID;
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint.replace("%", paymentID), apiManagePaymentsAuthToken, "", fieldsToUpdate, inputData.get(0).get("inputFileForPUT"), "No", "Valid");
            Assert.assertTrue(putResponse.getStatusCode() == 200, "Reissue with valid details API: " + putResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.result").toString().equalsIgnoreCase("success"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.message") == null, "The line number: " + appInd.getCurrentLineNumber());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagedPayment_ReIssue_PUTResponse", new Object[] {"paymentID", paymentID});
            Assert.assertTrue(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("ResponseStatus").toString().equalsIgnoreCase("True"));


            //CPAY-1259: API - Put - Payments - Reissue with invalid paymentID
            strPutEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsReissue_PUTMethod");
            fieldsToUpdate = "paymentType#"+paymentType+",paymentAmount#"+paymentAmount+",paymentDate#"+paymentDate+",paymentReference#"+paymentReference+",remitToID#"+remitToID;
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint.replace("%", inputData.get(0).get("invalidPaymentId")), apiManagePaymentsAuthToken, "", fieldsToUpdate, inputData.get(0).get("inputFileForPUT"), "No", "Invalid");
            Assert.assertTrue(putResponse.getStatusCode() == 422, "Reissue API with invalid PaymentID: " + putResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.type").toString().equalsIgnoreCase("https://datatracker.ietf.org/doc/html/rfc4918#section-11.2"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.title").toString().equalsIgnoreCase("Error"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.status").toString().equalsIgnoreCase("422"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.detail").toString().equalsIgnoreCase("Reissue Mail box not configured for this company"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.instance").toString().equalsIgnoreCase("/v1/payments/"+ inputData.get(0).get("invalidPaymentId") +"/reissue"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.errors").toString().contains("Reissue Mail box not configured for this company"), "The line number: " + appInd.getCurrentLineNumber());


            //CPAY-1259: API - Put - Payments - Reissue without providing mandatory values
            strPutEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsReissue_PUTMethod");
            fieldsToUpdate = "paymentType#,paymentAmount#1,paymentDate#,paymentReference#,remitToID#,payeeAddress1#,PayeeCity#,PayeeZip#,PayeeState#,PayeeCountry#";
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint.replace("%", paymentID), apiManagePaymentsAuthToken, "", fieldsToUpdate, inputData.get(0).get("inputFileForPUT"), "No", "Invalid");
            Assert.assertTrue(putResponse.getStatusCode() == 400, "Reissue API with invalid PaymentID: " + putResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.type").toString().equalsIgnoreCase("https://tools.ietf.org/html/rfc7231#section-6.5.1"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.title").toString().equalsIgnoreCase("One or more validation errors occurred."), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.status").toString().equalsIgnoreCase("400"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.errors").toString().contains("'Remit To Id' must not be empty."), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.errors").toString().contains("'Payment Type' must not be empty."), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.errors").toString().contains("Payment Type should be one of these values chk,ach,vca,acp,vpc"), "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.errors").toString().contains("'Payment Reference' must not be empty."), "The line number: " + appInd.getCurrentLineNumber());

            return true;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'validateManagedPaymentReissueAPIByUsingPUTMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateManagedPaymentReissueAPIByUsingPUTMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }finally {inputData = null; putResponse = null; strPutEndPoint = null; dbResponse = null; paymentID = null; fieldsToUpdate = null; paymentType = null; paymentAmount = null; paymentDate = null; paymentReference = null; remitToID = null;}
    }




    /********************************************************
     * Method Name      : validateManagedPaymentGetBankFileListAPIByUsingPOSTMethod()
     * Purpose          : to validate Managed Payment Get List Of Bank Files Based on the given criteria API using POST method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateManagedPaymentGetBankFileListAPIByUsingPOSTMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response postResponse = null;
        String strPostEndPoint = null;
        JSONArray dbResponse = null;
        String statussus = null;
        org.json.simple.JSONArray objJsonPOSTResponse = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);

            //CPAY-603: API- Verify API to Get a list of bank files that match the given criteria
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_Payments_GetListOfBankFiles_GETInput", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            statussus = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("statuses").toString();

            strPostEndPoint = appInd.getPropertyValueByKeyName("managePaymentsPollEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsBankFiles_POSTMethod");
            postResponse = apiUtility.httpPOSTMethodRequestWithBearerAuth(strPostEndPoint, apiManagePaymentsAuthToken, "", "", new Object[][] {}, inputData.get(0).get("inputFileForPOST"), "No", "Valid");
            Assert.assertTrue(postResponse.getStatusCode() == 200, "Get Bank File List API: " + postResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());

            objJsonPOSTResponse = (org.json.simple.JSONArray) apiUtility.convertJsonResponseIntoJSONObject("JsonArray", postResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_Payments_GetListOfBankFiles_GETResponse", new Object[] {"statusses", statussus});

            for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i), (JSONObject) objJsonPOSTResponse.get(i)
                        , "BankFileID#PaymentFileID#PayNetClientID#ClientName#BankFileStatus#BankRoute#BankFileNumberofPayments#BankFilePath#BankFilePaymentType#FundingMethod#DebitAccount#PaymentFileStatus#PaymentFileReceived#PaymentFileApproved#BankFileTotalamount"
                        , "bankFileId#paymentFileId#companyId#companyName#bankFileStatusName#routeName#paymentCount#filename#paymentType#fundingMethod#debitAccount#paymentFileStatusName#paymentFileReceivedAt#paymentFileApprovedAt#paymentFileControlTotal"));
            }
            return true;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'validateManagedPaymentGetBankFileListAPIByUsingPOSTMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateManagedPaymentGetBankFileListAPIByUsingPOSTMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }finally {inputData = null; postResponse = null; strPostEndPoint = null; dbResponse = null; statussus = null;}
    }



    /********************************************************
     * Method Name      : validateManagedPaymentGetPollByDateAPIForBankFileReceiptsByUsingGETMethod()
     * Purpose          : to validate Managed Payment Poll By Date API for BAnkFile Receipt using GET method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateManagedPaymentGetPollByDateAPIForBankFileReceiptsByUsingGETMethod(){
        Response getResponse = null;
        String strGetEndPoint = null;
        JSONArray dbResponse = null;
        String startDate = null;
        String startTime = null;
        String endDate = null;
        String endTime = null;
        org.json.simple.JSONArray objJsonGETResponseByPollDate = null;
        try{
            //CPAY-4872: API- Verify Poll By Date Time API for BankFile Receipts by using GET method
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_Payments_PollByDateGETInputParams", new Object[]{});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            String start = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("Start Date").toString();
            String end = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("End Date").toString();
            startDate = (start.split("T"))[0];
            startTime = (start.split("T"))[1];
            endDate = (end.split("T"))[0];
            endTime = (end.split("T"))[1];

            strGetEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managedPaymentsPollByDateTimeForBankRecepts_GETMethod");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", startDate).replace("%2", startTime).replace("%3", endDate).replace("%4", endTime), apiManagePaymentsAuthToken, "Valid");
            Assert.assertTrue(getResponse.getStatusCode() == 200, "Payments poll by date for BankFile Receipts API: " + getResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());
            objJsonGETResponseByPollDate = (org.json.simple.JSONArray) apiUtility.convertJsonResponseIntoJSONObject("JsonArray", getResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagedPayment_BankFileReceiptsList_POSTResponse", new Object[] {"startDate", startDate, "endDate", endDate});
            for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i), (JSONObject) objJsonGETResponseByPollDate.get(i)
                        , "PayNetClientID#ClientName#BankFileID#TransactionFileID#FileName#ReturnFileID#FileReceiptName#status"
                        , "companyId#companyName#bankFileId#transactionFileId#fileName#returnFileId#fileReceiptName#status"));
            }
            return true;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'validateManagedPaymentGetBankFilesFilterByPaymentFileIDAPIByUsingGETMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateManagedPaymentGetBankFilesFilterByPaymentFileIDAPIByUsingGETMethod()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }finally {getResponse = null; strGetEndPoint = null; dbResponse = null; objJsonGETResponseByPollDate = null;}
    }

    /********************************************************
     * Method Name      : validateNotesSectionForPaymentIdEndpoint()
     * Purpose          : This method is to validate Notes section using remittance param for payment id
     * Author           : Shidd
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateNotesSectionForPaymentIdEndpoint() {
        JSONArray dbResponse;
        Response response;
        String paymentId, randomNum, strGetEndPoint;
        List<Object> dataProviderResponseExpectedValue, apiResponseActualValue;
        try {
            // Fetching valid payment id
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_PaymentID_GETPaymentNotesInput", new Object[]{});
            randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            paymentId = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("PaymentID").toString();

            // Calling payment API and storing notes and remittance notes values
            strGetEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managePaymentsGetNotes");
            response = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", paymentId).replace("%2", "true"), apiManagePaymentsAuthToken, "Valid");
            appInd.compareValues(null, response.getStatusCode(), 200);
            apiResponseActualValue = new ArrayList<>();
            apiResponseActualValue.add(JsonPath.read(response.asString(), "$.notes"));
            apiResponseActualValue.add(JsonPath.read(response.asString(), "$.remittances[0].notes"));

            // Calling db response API and storing notes and remittance notes values
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_PaymentID_GETPaymentNotesResponse", new Object[]{"PaymentID", paymentId});
            dataProviderResponseExpectedValue = new ArrayList<>();
            dataProviderResponseExpectedValue.add(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("PaymentNotes"));
            dataProviderResponseExpectedValue.add(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("RemittanceNotes"));

            // Calling payment API with remittance flag as false
            appInd.compareValues(null, apiResponseActualValue, dataProviderResponseExpectedValue);
            response = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", paymentId).replace("%2", "false"), apiManagePaymentsAuthToken, "Negative");
            appInd.compareValues(null, response.getStatusCode(), 200);
            if (response.path("$.remittances") != null)
                Assert.fail("Remittance section being displayed even when param is set as false.");
            return true;
        } catch (Exception e) {
            reports.writeResult(null, "Exception", "Exception in 'validateNotesSectionForPaymentIdEndpoint()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateNotesSectionForPaymentIdEndpoint()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        } finally {
            dbResponse = null; response = null; paymentId = null; randomNum = null; strGetEndPoint = null; dataProviderResponseExpectedValue = null; apiResponseActualValue = null;
        }
    }

    /********************************************************
     * Method Name      : validateExceptAPIEndpointUsingPaymentId()
     * Purpose          : This method is to validate except api endpoint
     * Author           : Shidd
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateExceptAPIEndpointUsingPaymentId() {
        JSONArray dbResponse;
        Response response;
        String paymentId, randomNum, strGetEndPoint;
        try {
            // Fetching valid payment id
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagedPayment_ExceptAPI_Input", new Object[]{});
            randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            paymentId = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("PaymentID").toString();

            // Calling except payment API and verifying response
            strGetEndPoint = appInd.getPropertyValueByKeyName("managePaymentsEndPoint") + appInd.getPropertyValueByKeyName("managePaymentsExceptPUT");
            response = apiUtility.httpPUTMethodRequestWithBearerAuth(strGetEndPoint.replace("%", paymentId), apiManagePaymentsAuthToken, "",
                    "username#paycrm_testuser_gudi@corcentric.com",  "managePayementsExcept_PUT.txt",  "No", "Valid");
            appInd.compareValues(null, response.getStatusCode(), 200);
            appInd.compareValues(null, JsonPath.read(response.asString(), "$.success"), true);
            appInd.compareValues(null, JsonPath.read(response.asString(), "$.message"), "Payment exception was created");

            // Calling except payment API and verifying response by providing same paymentId
            response = apiUtility.httpPUTMethodRequestWithBearerAuth(strGetEndPoint.replace("%", paymentId), apiManagePaymentsAuthToken, "",
                    "username#paycrm_testuser_gudi@corcentric.com",  "managePayementsExcept_PUT.txt",  "No", "Valid");
            appInd.compareValues(null, response.getStatusCode(), 200);
            appInd.compareValues(null, JsonPath.read(response.asString(), "$.success"), true);
            appInd.compareValues(null, JsonPath.read(response.asString(), "$.message"), "Payment exception already exists");

            // Calling db response API and asserting details of except API.
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagedPayment_ExceptAPI_OutputResponse", new Object[]{"PaymentID", paymentId});
            appInd.compareValues(null, ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("PaymentID"), paymentId);
            appInd.compareValues(null, ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("Username"), "paycrm_testuser_gudi@corcentric.com");
            appInd.compareValues(null, ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("CreatedBy"), "paycrm_user");
            return true;
        } catch (Exception e) {
            reports.writeResult(null, "Exception", "Exception in 'validateExceptAPIEndpointUsingPaymentId()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        } catch (AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateExceptAPIEndpointUsingPaymentId()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        } finally {
            dbResponse = null; response = null; paymentId = null; randomNum = null; strGetEndPoint = null;
        }
    }
}
