package com.corcentric.baseSteps.api;

import com.corcentric.runner.CucumberTestRunner;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import org.testng.Assert;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class API_EnrollmentServicesBaseStep extends CucumberTestRunner {
    /********************************************************
     * Method Name      : validateEnrollmentServicesSearchSupplierAPI()
     * Purpose          : to validate GET operations for Enrollment Services API
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateEnrollmentServicesSearchSupplierAPI(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Map<String, String> basicAuthentication = null;
        String strGetEndPoint = null;
        Response getResponse = null;
        String supplierName = null;
        String supplierTaxID = null;
        String supplierPhoneNumber = null;
        String invalidSupplierName = null;
        String invalidSupplierTaxID = null;
        String invalidSupplierPhone = null;
        JSONArray dbResponse = null;
        Map<String, String> invalidBasicAuth = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);
            invalidBasicAuth = new HashMap<String, String>();
            basicAuthentication = apiUtility.getBasicAuthenticationDetails();

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_EnrollmentServices_SearchSuppliersInput", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            supplierName = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("Supplier Name").toString();
            supplierTaxID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("TaxID").toString();
            supplierPhoneNumber = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("Phone").toString();

            for(int i=1; i<=2; i++){
                if(i==2){
                    dbResponse = apiDataProvider.getAPIDataProviderResponse("API_EnrollmentServices_SearchClientSpecificSuppliersInput", new Object[] {"companyID", Long.parseLong(basicAuthentication.get("companyID"))});
                    randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
                    supplierName = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("suppliername").toString();
                    supplierTaxID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("tin").toString();
                    supplierPhoneNumber = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("primary_phone").toString();

                    dbResponse = apiDataProvider.getAPIDataProviderResponse("API_GETClientSpecificSearchAPI_BySupplierName", new Object[] {"supplierName", supplierName, "companyID", Long.parseLong(basicAuthentication.get("companyID"))});
                    if(dbResponse == null){
                        dbResponse = apiDataProvider.getAPIDataProviderResponse("API_GETClientSpecificSearchAPI_BySupplierTaxID", new Object[] {"tinNumber", supplierTaxID, "companyID", Long.parseLong(basicAuthentication.get("companyID"))});
                        if(dbResponse == null){
                            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_GETClientSpecificSearchAPI_BySupplierPhoneNumber", new Object[] {"phoneNumber", supplierPhoneNumber, "companyID", Long.parseLong(basicAuthentication.get("companyID"))});
                        }
                    }

                    if(dbResponse!=null && ((JSONArray) dbResponse.get(0)).size() > 0){
                        randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
                        supplierName = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("suppliername").toString();
                        supplierTaxID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("tin").toString();
                        supplierPhoneNumber = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("primary_phone").toString();
                    }else{
                        reports.writeResult(null, "Fail", "Failed to get the supplier details for the Client Specific search");
                        Assert.fail("Failed to get the supplier details for the Client Specific search. Line Number: " + appInd.getCurrentLineNumber());
                    }
                }


                //CPAY-3382: API - GET - Enrollment Service - Global Supplier Search by Supplier Name
                if(i==1)
                    strGetEndPoint = appInd.getPropertyValueByKeyName("enrollmentServicesEndPoint") + appInd.getPropertyValueByKeyName("enrollmentServices_GETGolbalSupplierSearchByName");
                else strGetEndPoint = appInd.getPropertyValueByKeyName("enrollmentServicesEndPoint") + appInd.getPropertyValueByKeyName("enrollmentServices_GETGolbalLinkedSupplierSearchByName");

                getResponse = apiUtility.httpGETRequestBasicAuthentication(strGetEndPoint.replace("%", supplierName), basicAuthentication, "Valid");
                Assert.assertTrue(getResponse.getStatusCode() == 200, "Global Supplier Search by Supplier Name: " + getResponse.getBody().prettyPrint());
                Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.result").toString().equalsIgnoreCase("exists"), "Line Number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.requestId").toString()!=null || !JsonPath.read(getResponse.asString(),"$.requestId").toString().isEmpty(), "Line Number: " + appInd.getCurrentLineNumber());

                //Enrollment Service - Global Supplier Search by Invalid Supplier Name
                invalidSupplierName = inputData.get(0).get("InvalidSupplierName")+appInd.getDateTime("Shhmmss");
                getResponse = apiUtility.httpGETRequestBasicAuthentication(strGetEndPoint.replace("%", invalidSupplierName), basicAuthentication, "Invalid");
                Assert.assertTrue(getResponse.getStatusCode() == 200, "Global Supplier Search by Invalid Supplier Name: " + getResponse.getBody().prettyPrint());
                Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.result").toString().equalsIgnoreCase("none"));
                Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.requestId").toString()!=null || !JsonPath.read(getResponse.asString(),"$.requestId").toString().isEmpty());



                //CPAY-3382: API - GET - Enrollment Service - Global Supplier Search by Supplier TaxID
                if(i==1)
                    strGetEndPoint = appInd.getPropertyValueByKeyName("enrollmentServicesEndPoint") + appInd.getPropertyValueByKeyName("enrollmentServices_GETGolbalSupplierSearchByTaxID");
                else strGetEndPoint = appInd.getPropertyValueByKeyName("enrollmentServicesEndPoint") + appInd.getPropertyValueByKeyName("enrollmentServices_GETGolbalLinkedSupplierSearchByTaxID");
                getResponse = apiUtility.httpGETRequestBasicAuthentication(strGetEndPoint.replace("%", supplierTaxID), basicAuthentication, "Valid");
                Assert.assertTrue(getResponse.getStatusCode() == 200, "Global Supplier Search by Supplier TaxID: " + getResponse.getBody().prettyPrint());
                Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.result").toString().equalsIgnoreCase("exists"));
                Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.requestId").toString()!=null || !JsonPath.read(getResponse.asString(),"$.requestId").toString().isEmpty());

                //Enrollment Service - Global Supplier Search by Invalid Supplier TaxID
                invalidSupplierTaxID = appInd.getDateTime("ShhmmssS");
                getResponse = apiUtility.httpGETRequestBasicAuthentication(strGetEndPoint.replace("%", invalidSupplierTaxID), basicAuthentication, "Invalid");
                Assert.assertTrue(getResponse.getStatusCode() == 200, "Global Supplier Search by Invalid Supplier TaxID: " + getResponse.getBody().prettyPrint());
                Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.result").toString().equalsIgnoreCase("none"));
                Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.requestId").toString()!=null || !JsonPath.read(getResponse.asString(),"$.requestId").toString().isEmpty());



                //CPAY-3382: API - GET - Enrollment Service - Global Supplier Search by Supplier Phone number
                if(i==1)
                    strGetEndPoint = appInd.getPropertyValueByKeyName("enrollmentServicesEndPoint") + appInd.getPropertyValueByKeyName("enrollmentServices_GETGolbalSupplierSearchByPhoneNumber");
                else strGetEndPoint = appInd.getPropertyValueByKeyName("enrollmentServicesEndPoint") + appInd.getPropertyValueByKeyName("enrollmentServices_GETGolbalLinkedSupplierSearchByPhoneNumber");
                getResponse = apiUtility.httpGETRequestBasicAuthentication(strGetEndPoint.replace("%", supplierPhoneNumber), basicAuthentication, "Valid");
                Assert.assertTrue(getResponse.getStatusCode() == 200, "Global Supplier Search by Supplier Phone Number: " + getResponse.getBody().prettyPrint());
                Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.result").toString().equalsIgnoreCase("exists"));
                Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.requestId").toString()!=null || !JsonPath.read(getResponse.asString(),"$.requestId").toString().isEmpty());

                //Enrollment Service - Global Supplier Search by Invalid Supplier Phone Number
                invalidSupplierPhone = appInd.getDateTime("ShhmmssS");
                getResponse = apiUtility.httpGETRequestBasicAuthentication(strGetEndPoint.replace("%", invalidSupplierPhone), basicAuthentication, "Invalid");
                Assert.assertTrue(getResponse.getStatusCode() == 200, "Global Supplier Search by Invalid Supplier Phone Number: " + getResponse.getBody().prettyPrint());
                Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.result").toString().equalsIgnoreCase("none"));
                Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.requestId").toString()!=null || !JsonPath.read(getResponse.asString(),"$.requestId").toString().isEmpty());



                //CPAY-3382: API - GET - Enrollment Service - Global Supplier Search by Supplier Name, TaxID & Phone Number
                if(i==1)
                    strGetEndPoint = appInd.getPropertyValueByKeyName("enrollmentServicesEndPoint") + appInd.getPropertyValueByKeyName("enrollmentServices_GETGlobalSupplierSearchByNameTaxIDPhone");
                else strGetEndPoint = appInd.getPropertyValueByKeyName("enrollmentServicesEndPoint") + appInd.getPropertyValueByKeyName("enrollmentServices_GETGlobalLinkedSupplierSearchByNameTaxIDPhone");

                getResponse = apiUtility.httpGETRequestBasicAuthentication(strGetEndPoint.replace("%1", supplierName).replace("%2", supplierTaxID).replace("%3", supplierPhoneNumber), basicAuthentication, "Valid");
                Assert.assertTrue(getResponse.getStatusCode() == 200, "Global Supplier Search by Supplier Name, TaxID & Phone Number: " + getResponse.getBody().prettyPrint());
                Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.result").toString().equalsIgnoreCase("exists"));
                Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.requestId").toString()!=null || !JsonPath.read(getResponse.asString(),"$.requestId").toString().isEmpty());

                //Enrollment Service - Global Supplier Search by Invalid Supplier Name & valid TaxID & Phone Number
                getResponse = apiUtility.httpGETRequestBasicAuthentication(strGetEndPoint.replace("%1", invalidSupplierName).replace("%2", supplierTaxID).replace("%3", supplierPhoneNumber), basicAuthentication, "Valid");
                Assert.assertTrue(getResponse.getStatusCode() == 200, "Global Supplier Search by Invalid Supplier Name and Valid TaxID & Phone Number: " + getResponse.getBody().prettyPrint());
                Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.result").toString().equalsIgnoreCase("exists"));
                Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.requestId").toString()!=null || !JsonPath.read(getResponse.asString(),"$.requestId").toString().isEmpty());

                //Enrollment Service - Global Supplier Search by invalid taxID and Valid Supplier Name & Phone Number
                getResponse = apiUtility.httpGETRequestBasicAuthentication(strGetEndPoint.replace("%1", supplierName).replace("%2", invalidSupplierTaxID).replace("%3", supplierPhoneNumber), basicAuthentication, "Valid");
                Assert.assertTrue(getResponse.getStatusCode() == 200, "Global Supplier Search by Invalid TaxID & Valid Supplier Name & Phone Number: " + getResponse.getBody().prettyPrint());
                Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.result").toString().equalsIgnoreCase("exists"));
                Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.requestId").toString()!=null || !JsonPath.read(getResponse.asString(),"$.requestId").toString().isEmpty());

                //Enrollment Service - Global Supplier Search by invalid phone number and Valid Supplier Name & taxID
                getResponse = apiUtility.httpGETRequestBasicAuthentication(strGetEndPoint.replace("%1", supplierName).replace("%2", supplierTaxID).replace("%3", invalidSupplierPhone), basicAuthentication, "Valid");
                Assert.assertTrue(getResponse.getStatusCode() == 200, "Global Supplier Search by Invalid Phone Number and Valid Supplier Name & TaxID: " + getResponse.getBody().prettyPrint());
                Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.result").toString().equalsIgnoreCase("exists"));
                Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.requestId").toString()!=null || !JsonPath.read(getResponse.asString(),"$.requestId").toString().isEmpty());

                //Enrollment Service - Global Supplier Search by without any parameter
                getResponse = apiUtility.httpGETRequestBasicAuthentication(strGetEndPoint.replace("%1", "").replace("%2", "").replace("%3", ""), basicAuthentication, "Invalid");
                Assert.assertTrue(getResponse.getStatusCode() == 400, "Global Supplier Search by without any parameters: " + getResponse.getBody().prettyPrint());
                Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.status").toString().equalsIgnoreCase("422"));
                Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.details").toString().replace("\n", "").equalsIgnoreCase("*Either Supplier name or  Phone Number or TaxId is required*Either Supplier name or  Phone Number or TaxId is required*Either Supplier name or  Phone Number or TaxId is required"));
                Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.title").toString().replace("\n", "").equalsIgnoreCase("*Either Supplier name or  Phone Number or TaxId is required*Either Supplier name or  Phone Number or TaxId is required*Either Supplier name or  Phone Number or TaxId is required"));


                //set correct password as accessCode & set wrong username as enrollmentID
                invalidBasicAuth.put("enrollmentID", basicAuthentication.get("enrollmentID")+appInd.getDateTime("hhmmss"));
                invalidBasicAuth.put("accessCode", basicAuthentication.get("accessCode"));
                getResponse = apiUtility.httpGETRequestBasicAuthentication(strGetEndPoint.replace("%1", "").replace("%2", "").replace("%3", ""), invalidBasicAuth, "Invalid");
                Assert.assertTrue(getResponse.getStatusCode() == 401, "Global Supplier Search by wrong enrollmentID & accessCode: " + getResponse.getBody().prettyPrint());
                Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.status").toString().equalsIgnoreCase("401"));
                Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.title").toString().equalsIgnoreCase("Invalid enrollment Id"));
                Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.detail").toString().equalsIgnoreCase("Invalid enrollment Id"));

                //set wrong password as accessCode & set correct username as enrollmentID
                invalidBasicAuth.put("enrollmentID", basicAuthentication.get("enrollmentID"));
                invalidBasicAuth.put("accessCode", basicAuthentication.get("accessCode")+"xyZ");
                getResponse = apiUtility.httpGETRequestBasicAuthentication(strGetEndPoint.replace("%1", "").replace("%2", "").replace("%3", ""), invalidBasicAuth, "Invalid");
                Assert.assertTrue(getResponse.getStatusCode() == 401, "Global Supplier Search by wrong enrollmentID & accessCode: " + getResponse.getBody().prettyPrint());
                Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.status").toString().equalsIgnoreCase("401"));
                Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.title").toString().equalsIgnoreCase("Not valid accesscode"));
                Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.detail").toString().equalsIgnoreCase("Not valid accesscode"));
            }
            return true;
        }catch(Exception e) {
            reports.writeResult(null, "Exception", "Exception in 'validateEnrollmentServicesSearchSupplierAPI()' method. " + e.getMessage());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(null, "Exception", "AssertionError in 'validateEnrollmentServicesSearchSupplierAPI()' method. " + e.getMessage());
            return false;
        }finally {basicAuthentication = null; strGetEndPoint = null; getResponse = null; supplierName = null; supplierTaxID = null; supplierPhoneNumber = null; invalidSupplierName = null; invalidSupplierTaxID = null; invalidSupplierPhone = null; invalidBasicAuth = null;}
    }




    /********************************************************
     * Method Name      : validateEnrollmentServicesCreateAndUpdateEnrollmentCasesAPI()
     * Purpose          : to validate POST& PUT operations for Enrollment Services API
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateEnrollmentServicesCreateAndUpdateEnrollmentCasesAPI(DataTable dataTable){
        List<Map<String, String>> inputFileName = null;
        Map<String, String> basicAuthentication = null;
        String strPostEndPoint = null;
        Response postResponse = null;
        Response putResponse = null;
        String requestID = null;
        long caseID = 0;
        String phoneNum = null;
        String taxID = null;
        String supplierName = null;
        String inputFileForPOST = null;
        String strPutEndPoint = null;
        String invalidRequestID = null;
        String duplicateRequestID = null;
        String fieldsToUpdate = null;
        org.json.simple.JSONObject objJsonPOSTResponse = null;
        org.json.simple.JSONObject objJsonPUTResponse = null;
        JSONArray dbResponse = null;
        Map<String, String> invalidBasicAuth = null;
        String arrFieldsToUpdate = null;
        String strFieldsToUpdate = null;
        String inputFileForPUT = null;
        String supplierID = null;
        long companyID = 0;
        String accessCode = null;
        long enrollmentID = 0;
        String vnetClientCompanyID = null;
        String timeStamp = null;
        try{
            timeStamp = appInd.getDateTime("hhmmss");
            inputFileName = dataTable.asMaps(String.class, String.class);
            invalidBasicAuth = new HashMap<String, String>();
            basicAuthentication = apiUtility.getBasicAuthenticationDetails();

            for(int i=1; i<=2; i++){
                if(i==1){
                    inputFileForPOST = inputFileName.get(0).get("InputFileForPOST_VCA");
                    inputFileForPUT = inputFileName.get(0).get("InputFileForPUT_UpdateEnrollmentVCA");
                }else{
                    inputFileForPOST = inputFileName.get(0).get("InputFileForPOST_ACH");
                    inputFileForPUT = inputFileName.get(0).get("InputFileForPUT_UpdateEnrollmentACH");
                }

                //CPAY-3392 - CPAY-3383: API - POST - Enrollment Service - Create New Enrollment Case - VCA/ACH
                supplierName = "auto_supplier_"+appInd.getDateTime("Shhmmss");
                phoneNum = appInd.generatePhoneNumber("9538", "hhmmss");
                taxID = appInd.generateTaxID("Shhmmss");
                strPostEndPoint = appInd.getPropertyValueByKeyName("enrollmentServicesEndPoint") + appInd.getPropertyValueByKeyName("enrollmentServices_POSTMethod");
                arrFieldsToUpdate = "contacts,firstName#ggudi#lastName#sg"+timeStamp+"#phone#"+phoneNum+"#email#"+appInd.getPropertyValueByKeyName("qaUserName");
                strFieldsToUpdate = "supplierName#"+supplierName+",taxId#"+taxID;
                postResponse = apiUtility.httpPOSTMethodRequestWithBasicAuth(strPostEndPoint, basicAuthentication, arrFieldsToUpdate, strFieldsToUpdate, inputFileForPOST, "Yes", "Valid");
                Assert.assertTrue(postResponse.getStatusCode() == 200, "Create Enrollment Case POST with Unique details: " + postResponse.getBody().prettyPrint() +": \n" + "The line number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.result").toString().equalsIgnoreCase("success"), "The line number: " + appInd.getCurrentLineNumber());
                requestID = JsonPath.read(postResponse.asString(),"$.requestId").toString();
                caseID = Long.parseLong(JsonPath.read(postResponse.asString(),"$.caseId").toString());
                supplierID = JsonPath.read(postResponse.asString(),"$.supplierId").toString();

                objJsonPOSTResponse = (org.json.simple.JSONObject) apiUtility.convertJsonResponseIntoJSONObject("JsonObject", apiUtility.getJsonContentFromFile(inputFileForPOST));
                dbResponse = apiDataProvider.getAPIDataProviderResponse("API_CreateNewEnrollmentCasePOST_VCA_ACH", new Object[] {"caseID", caseID});

                Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0), objJsonPOSTResponse
                        , "name#web_address#remittance_advice_email_recipient#network_maintenance_fee_advice_email_recipient#virtual_email#tin#offer_number#network_discount_percentage#payment_notification_email_recipient#virtual_card_phone"
                        , "supplierName#webAddress#remittanceEmailAddress#networkMaintenanceFeeAdviceEmailRecipient#virtualCardEmail#taxId#selectedOfferNumber#networkDiscount#paymentNotificationEmailRecipient#virtualCardPhone"));

                //Validate Address details of Json Response against DB data
                Assert.assertTrue(apiUtility.compareJsonArrayElementsWithDBResponse(String.valueOf(objJsonPOSTResponse), dbResponse, "addresses"
                        , "zip#country#city#address1#language#state#type"
                        , "postal_code#country#city#address_1#language#state_province#addresstype"));

                //Validate attachments details of Json Response against DB data
                Assert.assertTrue(apiUtility.compareJsonArrayElementsWithDBResponse(String.valueOf(objJsonPOSTResponse), dbResponse, "attachments"
                        , "filename"
                        , "attachment_file_name"));

                //Validate contacts details of Json Response against DB data
                Assert.assertTrue(apiUtility.compareJsonArrayElementsWithDBResponse(String.valueOf(objJsonPOSTResponse), dbResponse, "contacts"
                        , "phone#email"
                        , "phone_1#contactemail"));


                //API - POST - Enrollment Service - Create Enrollment Case - VCA with duplicate details
                arrFieldsToUpdate = "contacts,firstName#ggudi#lastName#sg"+timeStamp+"#phone#"+phoneNum+"#email#"+appInd.getPropertyValueByKeyName("qaUserName");
                strFieldsToUpdate = "supplierName#"+supplierName+",taxId#"+taxID;
                postResponse = apiUtility.httpPOSTMethodRequestWithBasicAuth(strPostEndPoint, basicAuthentication, arrFieldsToUpdate, strFieldsToUpdate, inputFileForPOST, "No", "Valid");
                duplicateRequestID = JsonPath.read(postResponse.asString(),"$.requestId").toString();
                Assert.assertTrue(postResponse.getStatusCode() == 200, "Create Enrollment Case POST with duplicate details: " + postResponse.getBody().prettyPrint() +": \n" + "The line number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.result").toString().equalsIgnoreCase("duplicate"), "The line number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.message").toString().equalsIgnoreCase("The enrollment data has been partially processed but requires manual attention from our enrollment team. Someone will contact you for more information."), "The line number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.caseId") == null, "The line number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.supplierId") == null, "The line number: " + appInd.getCurrentLineNumber());


                //API - POST - Enrollment Service - Create Enrollment Case - VCA with No supplier Name
                arrFieldsToUpdate = "contacts,firstName#ggudi#lastName#sg"+appInd.getDateTime("hhmmss")+"#phone#"+phoneNum;
                strFieldsToUpdate = "supplierName#,taxId#"+taxID;
                postResponse = apiUtility.httpPOSTMethodRequestWithBasicAuth(strPostEndPoint, basicAuthentication, arrFieldsToUpdate, strFieldsToUpdate, inputFileForPOST, "No", "Invalid");
                Assert.assertTrue(postResponse.getStatusCode() == 400, "Create Enrollment Case POST with no suppler name: " + postResponse.getBody().prettyPrint() +": \n" + "The line number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.status").toString().equalsIgnoreCase("422"), "The line number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.details").toString().replace("\n", "").equalsIgnoreCase("The SupplierName field is required.'Supplier Name' must not be empty."), "The line number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.title").toString().replace("\n", "").equalsIgnoreCase("The SupplierName field is required.'Supplier Name' must not be empty."), "The line number: " + appInd.getCurrentLineNumber());


                //API - POST - Enrollment Service - Create Enrollment Case - VCA with No TaxID
                arrFieldsToUpdate = "contacts,firstName#ggudi#lastName#sg"+appInd.getDateTime("hhmmss")+"#phone#"+phoneNum;
                strFieldsToUpdate = "supplierName#"+supplierName+",taxId#";
                postResponse = apiUtility.httpPOSTMethodRequestWithBasicAuth(strPostEndPoint, basicAuthentication, arrFieldsToUpdate, strFieldsToUpdate, inputFileForPOST, "No", "Invalid");
                Assert.assertTrue(postResponse.getStatusCode() == 400, "Create Enrollment Case POST with no Tax ID: " + postResponse.getBody().prettyPrint() +": \n" + "The line number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.status").toString().equalsIgnoreCase("422"), "The line number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.details").toString().replace("\n", "").equalsIgnoreCase("The TaxId field is required.'Tax Id' must not be empty."), "The line number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.title").toString().replace("\n", "").equalsIgnoreCase("The TaxId field is required.'Tax Id' must not be empty."), "The line number: " + appInd.getCurrentLineNumber());


                //API - POST - Enrollment Service - Create Enrollment Case - VCA with No Phone Number
                arrFieldsToUpdate = "contacts,firstName#ggudi#lastName#sg"+appInd.getDateTime("hhmmss")+"#phone#";
                strFieldsToUpdate = "supplierName#"+supplierName+",taxId#"+taxID;
                postResponse = apiUtility.httpPOSTMethodRequestWithBasicAuth(strPostEndPoint, basicAuthentication, arrFieldsToUpdate, strFieldsToUpdate, inputFileForPOST, "No", "Invalid");
                Assert.assertTrue(postResponse.getStatusCode() == 400, "Create Enrollment Case POST with no Phone Number: " + postResponse.getBody().prettyPrint() +": \n" + "The line number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.status").toString().equalsIgnoreCase("422"), "The line number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.detail").toString().equalsIgnoreCase("Contact Primary type phone number mandatory"), "The line number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.title").toString().equalsIgnoreCase("Contact Primary type phone number mandatory"), "The line number: " + appInd.getCurrentLineNumber());



                //CPAY-3394: API - PUT - Enrollment Service - Update New Enrollment Case
                strPutEndPoint = appInd.getPropertyValueByKeyName("enrollmentServicesEndPoint") + appInd.getPropertyValueByKeyName("enrollmentServices_PUTUpdateEnrollCase");
                strFieldsToUpdate = "requestId#"+requestID+",supplierId#"+supplierID+",caseId#"+caseID+",supplierName#"+supplierName+",taxId#"+taxID+",paymentNotificationEmailRecipient#"+appInd.getPropertyValueByKeyName("qaUserName")+",remittanceEmailAddress#"+appInd.getPropertyValueByKeyName("qaUserName")+",networkMaintenanceFeeAdviceEmailRecipient#"+appInd.getPropertyValueByKeyName("qaUserName");
                putResponse = apiUtility.httpPUTMethodRequestWithBasicAuth(strPutEndPoint, basicAuthentication, "", strFieldsToUpdate, inputFileForPUT, "Yes", "Valid");
                Assert.assertTrue(putResponse.getStatusCode() == 200, "Update Enrollment Case PUT with unique Case: " + putResponse.getBody().prettyPrint() +": \n" + "The line number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.result").toString().equalsIgnoreCase("success"), "The line number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.message")==null, "The line number: " + appInd.getCurrentLineNumber());
                objJsonPUTResponse = (org.json.simple.JSONObject) apiUtility.convertJsonResponseIntoJSONObject("JsonObject", apiUtility.getJsonContentFromFile(inputFileForPUT));
                dbResponse = apiDataProvider.getAPIDataProviderResponse("API_CreateNewEnrollmentCasePOST_VCA_ACH", new Object[] {"caseID", caseID});

                Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0), objJsonPUTResponse
                        , "name#web_address#remittance_advice_email_recipient#network_maintenance_fee_advice_email_recipient#virtual_email#tin#offer_number#network_discount_percentage#payment_notification_email_recipient#virtual_card_phone"
                        , "supplierName#webAddress#remittanceEmailAddress#networkMaintenanceFeeAdviceEmailRecipient#virtualCardEmail#taxId#selectedOfferNumber#networkDiscount#paymentNotificationEmailRecipient#virtualCardPhone"));

                //Validate Address details of Json Response against DB data
                Assert.assertTrue(apiUtility.compareJsonArrayElementsWithDBResponse(String.valueOf(objJsonPUTResponse), dbResponse, "addresses"
                        , "zip#country#city#address1#language#state#type"
                        , "postal_code#country#city#address_1#language#state_province#addresstype"));

                //Validate attachments details of Json Response against DB data
                Assert.assertTrue(apiUtility.compareJsonArrayElementsWithDBResponse(String.valueOf(objJsonPUTResponse), dbResponse, "attachments"
                        , "filename"
                        , "attachment_file_name"));

                //Validate contacts details of Json Response against DB data
                Assert.assertTrue(apiUtility.compareJsonArrayElementsWithDBResponse(String.valueOf(objJsonPUTResponse), dbResponse, "contacts"
                        , "phone#email"
                        , "phone_1#contactemail"));



                //API - PUT - Enrollment Service - Update New Enrollment Case with invalid requestID
                invalidRequestID = "reqID_Auto_"+requestID + "-"+appInd.getDateTime("hmss")+"-"+appInd.getDateTime("hhmmss");
                strFieldsToUpdate = "requestId#"+invalidRequestID+",caseId#"+caseID+",supplierName#"+supplierName+",taxId#"+taxID;
                putResponse = apiUtility.httpPUTMethodRequestWithBasicAuth(strPutEndPoint, basicAuthentication, "", strFieldsToUpdate, inputFileForPUT, "No", "Invalid");
                Assert.assertTrue(putResponse.getStatusCode() == 200, "Update Enrollment Case PUT with duplicate Case: " + putResponse.getBody().prettyPrint() +": \n" + "The line number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.result").toString().equalsIgnoreCase("error"), "The line number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.message").toString().equalsIgnoreCase("That requestId does not match any known previous request"), "The line number: " + appInd.getCurrentLineNumber());



                //API - PUT - Enrollment Service - Enroll Case with invalid request ID
                strFieldsToUpdate = "requestId#"+invalidRequestID;
                putResponse = apiUtility.httpPUTMethodRequestWithBasicAuth(strPutEndPoint, basicAuthentication, "", strFieldsToUpdate, inputFileName.get(0).get("InputFileForPUT_EnrollCase"), "No", "Invalid");
                Assert.assertTrue(putResponse.getStatusCode() == 200, "Enroll Case PUT with invlaid requestID: " + putResponse.getBody().prettyPrint() +": \n" + "The line number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.result").toString().equalsIgnoreCase("error"), "The line number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.message").toString().equalsIgnoreCase("That requestId does not match any known previous request"), "The line number: " + appInd.getCurrentLineNumber());


                //CPAY-3443: API - PUT - Enrollment Service - Enroll Case
                strPutEndPoint = appInd.getPropertyValueByKeyName("enrollmentServicesEndPoint") + appInd.getPropertyValueByKeyName("enrollmentServices_PUTEnrollCase");
                strFieldsToUpdate = "requestId#"+requestID;
                putResponse = apiUtility.httpPUTMethodRequestWithBasicAuth(strPutEndPoint, basicAuthentication, "", strFieldsToUpdate, inputFileName.get(0).get("InputFileForPUT_EnrollCase"), "No", "Valid");
                Assert.assertTrue(putResponse.getStatusCode() == 200, "Enroll Case PUT with valid requestID: " + putResponse.getBody().prettyPrint() +": \n" + "The line number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.requestId").toString().equalsIgnoreCase(requestID), "The line number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.result").toString().equalsIgnoreCase("success"), "The line number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.message").toString().equalsIgnoreCase("Success"), "The line number: " + appInd.getCurrentLineNumber());
                dbResponse = apiDataProvider.getAPIDataProviderResponse("API_CreateNewEnrollmentCasePOST_VCA_ACH", new Object[] {"caseID", caseID});
                for(int j=0; j<((JSONArray) dbResponse.get(0)).size(); j++){
                    Assert.assertTrue(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(j)).get("status_desc").toString().equalsIgnoreCase("Closed [Enrolled]"), "The line number: " + appInd.getCurrentLineNumber());
                }



                //CPAY-3320: API - PUT - EnrollmentService - Update Existing Enrollment Case after enroll is completed (Status is closed)
                strFieldsToUpdate = "requestId#"+requestID;
                putResponse = apiUtility.httpPUTMethodRequestWithBasicAuth(strPutEndPoint, basicAuthentication, "", strFieldsToUpdate, inputFileName.get(0).get("InputFileForPUT_EnrollCase"), "No", "Invalid");
                Assert.assertTrue(putResponse.getStatusCode() == 400, "Enroll Case PUT with already enrolled requestID: " + putResponse.getBody().prettyPrint() +": \n" + "The line number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.title").toString().equalsIgnoreCase("Enrollment is not waiting for offer info, so nothing to do"), "The line number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.status").toString().equalsIgnoreCase("422"), "The line number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.detail").toString().equalsIgnoreCase("Enrollment is not waiting for offer info, so nothing to do"), "The line number: " + appInd.getCurrentLineNumber());


                //CPAY-3392 - CPAY-3383: API - POST - Enrollment Service - Create Enrollment Case - VCA with Invalid EnrollmentID & valid AccessCode
                invalidBasicAuth.put("enrollmentID", basicAuthentication.get("enrollmentID")+appInd.getDateTime("hhmmss"));
                invalidBasicAuth.put("accessCode", basicAuthentication.get("accessCode"));
                postResponse = apiUtility.httpPOSTMethodRequestWithBasicAuth(strPostEndPoint, invalidBasicAuth, "", "", inputFileForPOST, "No", "Invalid");
                Assert.assertTrue(postResponse.getStatusCode() == 401, "Global Supplier Search by with invalid EnrollmentID & accesscode: " + postResponse.getBody().prettyPrint()+ "\n The line number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.status").toString().equalsIgnoreCase("401"), "The line number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.title").toString().equalsIgnoreCase("Invalid enrollment Id"), "The line number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.detail").toString().equalsIgnoreCase("Invalid enrollment Id"), "The line number: " + appInd.getCurrentLineNumber());


                //CPAY-3392 - CPAY-3383: API - POST - Enrollment Service - Create Enrollment Case - VCA with valid EnrollmentID & invalid AccessCode
                invalidBasicAuth.put("enrollmentID", basicAuthentication.get("enrollmentID"));
                invalidBasicAuth.put("accessCode", basicAuthentication.get("accessCode")+"xyZ");
                postResponse = apiUtility.httpPOSTMethodRequestWithBasicAuth(strPostEndPoint, invalidBasicAuth, "", "", inputFileForPOST, "No", "Invalid");
                Assert.assertTrue(postResponse.getStatusCode() == 401, "Global Supplier Search by with invalid EnrollmentID & accesscode: " + postResponse.getBody().prettyPrint()+ "\n The line number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.status").toString().equalsIgnoreCase("401"), "The line number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.title").toString().equalsIgnoreCase("Not valid accesscode"), "The line number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.detail").toString().equalsIgnoreCase("Not valid accesscode"), "The line number: " + appInd.getCurrentLineNumber());
            }


            //CPAY-3510: API - Enrollment-Service - Search & Create Duplicate Supplier Entry
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_SearchAndCreateDuplicateSupplierEntry_AccessCode_CompanyIDInput", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            companyID = Long.parseLong(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("CompanyID").toString());
            accessCode = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("AccessCode").toString();

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_SearchAndCreateDuplicateSupplierEntry_GETRequestInput", new Object[] {"companyID", companyID});
            randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            supplierName = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("suppliername").toString();
            phoneNum = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("primary_phone").toString();
            enrollmentID = Long.parseLong(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("enrollmentid").toString());
            vnetClientCompanyID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("clientcompanyid").toString();

            strPostEndPoint = appInd.getPropertyValueByKeyName("enrollmentServicesEndPoint") + appInd.getPropertyValueByKeyName("enrollmentServices_POST_SearchAndCreateSupplier");
            fieldsToUpdate = "supplierName#"+supplierName+",phoneNumber#"+phoneNum+",enrollmentId#"+enrollmentID+",vnetClientId#"+vnetClientCompanyID+",accessCode#"+accessCode;
            postResponse = apiUtility.httpPOSTMethodRequestWithBasicAuth(strPostEndPoint, basicAuthentication, "", fieldsToUpdate, inputFileName.get(0).get("InputFileForPOST_createAndSearch"), "No","Valid");
            Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.status").toString().equalsIgnoreCase("pending"), "The line number: " + appInd.getCurrentLineNumber());

            //CPAY-3510: API - Enrollment-Service - Search & Create Duplicate Supplier Entry. Using details which doesnot exist
            supplierName = "ggudi_"+appInd.getDateTime("Shhmmss");
            phoneNum = appInd.generatePhoneNumber("9538", "hhmmss");
            fieldsToUpdate = "supplierName#"+supplierName+"xyzzzz"+",phoneNumber#"+phoneNum+",enrollmentId#"+enrollmentID+",vnetClientId#"+vnetClientCompanyID+",accessCode#"+accessCode;
            postResponse = apiUtility.httpPOSTMethodRequestWithBasicAuth(strPostEndPoint, basicAuthentication, "", fieldsToUpdate, inputFileName.get(0).get("InputFileForPOST_createAndSearch"), "No", "Invalid");
            Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.status").toString().equalsIgnoreCase("none"), "The line number: " + appInd.getCurrentLineNumber());

            //Valid access code and invalid emrollmentID
            invalidBasicAuth.put("enrollmentID", basicAuthentication.get("enrollmentID")+appInd.getDateTime("hhmmss"));
            invalidBasicAuth.put("accessCode", basicAuthentication.get("accessCode"));
            fieldsToUpdate = "supplierName#"+supplierName+",phoneNumber#"+phoneNum+",enrollmentId#"+invalidBasicAuth.get("enrollmentID")+",vnetClientId#"+vnetClientCompanyID+",accessCode#"+accessCode;
            postResponse = apiUtility.httpPOSTMethodRequestWithBasicAuth(strPostEndPoint, invalidBasicAuth, "", fieldsToUpdate, inputFileName.get(0).get("InputFileForPOST_createAndSearch"), "No", "Invalid");
            Assert.assertTrue(postResponse.getStatusCode() == 200, "Search & Create Duplicate Supplier Entry API by wrong enrollmentID & Valid accessCode: " + postResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.status").toString().equalsIgnoreCase("error"));
            Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.message").toString().equalsIgnoreCase("enrollmentId and vnetClientId are invalid or do not match"));

            invalidBasicAuth.put("enrollmentID", basicAuthentication.get("enrollmentID"));
            invalidBasicAuth.put("accessCode", basicAuthentication.get("accessCode")+"xyZ12werwr");
            fieldsToUpdate = "supplierName#"+supplierName+",phoneNumber#"+phoneNum+",enrollmentId#"+enrollmentID+",vnetClientId#"+invalidBasicAuth.get("accessCode")+",accessCode#"+accessCode;
            postResponse = apiUtility.httpPOSTMethodRequestWithBasicAuth(strPostEndPoint, invalidBasicAuth, "", fieldsToUpdate, inputFileName.get(0).get("InputFileForPOST_createAndSearch"), "No","Invalid");
            Assert.assertTrue(postResponse.getStatusCode() == 200, "Search & Create Duplicate Supplier Entry API by valid enrollmentID & invalid accessCode: " + postResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.status").toString().equalsIgnoreCase("error"));
            Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.message").toString().equalsIgnoreCase("vnetClientId is missing"));
            return true;
        }catch(Exception e) {
            reports.writeResult(null, "Exception", "Exception in 'validateEnrollmentServicesCreateAndUpdateEnrollmentCasesAPI()' method. " + e.getMessage());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(null, "Exception", "AssertionError in 'validateEnrollmentServicesCreateAndUpdateEnrollmentCasesAPI()' method. " + e.getMessage());
            return false;
        }finally {inputFileName = null; basicAuthentication = null; strPostEndPoint = null; postResponse = null; putResponse = null; requestID = null; phoneNum = null; taxID = null; supplierName = null; inputFileForPOST = null; strPutEndPoint = null; invalidRequestID = null; duplicateRequestID = null; fieldsToUpdate = null; objJsonPOSTResponse = null; dbResponse = null; invalidBasicAuth = null; arrFieldsToUpdate = null; strFieldsToUpdate = null; inputFileForPUT = null;}
    }





    /********************************************************
     * Method Name      : validateEnrollmentServicesNotificationsAPIByUsingPOSTMethod()
     * Purpose          : to perform Enrollment services Notifications API using POST methof
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateEnrollmentServicesNotificationsAPIByUsingPOSTMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Map<String, String> basicAuthentication = null;
        String strPostEndPoint = null;
        String strGetEndPoint = null;
        Response postResponse = null;
        Response getResponse = null;
        String supplierName = null;
        String supplierTaxID = null;
        String supplierPhoneNumber = null;
        String strFieldsToUpdate = null;
        JSONArray dbResponse = null;
        String requestID = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);
            basicAuthentication = apiUtility.getBasicAuthenticationDetails();

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_EnrollmentServices_SearchSuppliersInput", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            supplierName = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("Supplier Name").toString();
            supplierTaxID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("TaxID").toString();
            supplierPhoneNumber = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("Phone").toString();

            strGetEndPoint = appInd.getPropertyValueByKeyName("enrollmentServicesEndPoint") + appInd.getPropertyValueByKeyName("enrollmentServices_GETGlobalSupplierSearchByNameTaxIDPhone");
            getResponse = apiUtility.httpGETRequestBasicAuthentication(strGetEndPoint.replace("%1", supplierName).replace("%2", supplierTaxID).replace("%3", supplierPhoneNumber), basicAuthentication, "Valid");
            Assert.assertTrue(getResponse.getStatusCode() == 200, "Global Supplier Search by Supplier Name, TaxID & Phone Number: " + getResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.result").toString().equalsIgnoreCase("exists"));
            requestID = JsonPath.read(getResponse.asString(),"$.requestId").toString();

            //CPAY-3883: API - POST - Enrollment Service - Notifications
            strPostEndPoint = appInd.getPropertyValueByKeyName("enrollmentServicesEndPoint") + appInd.getPropertyValueByKeyName("enrollmentServicesNotification_POSTMethod");
            strFieldsToUpdate = "requestDesc#TestAutomation"+appInd.getDateTime("hhmmss")+",requestId#"+requestID;
            postResponse = apiUtility.httpPOSTMethodRequestWithBasicAuth(strPostEndPoint, basicAuthentication, "", strFieldsToUpdate, inputData.get(0).get("InputFileForPOST"), "No", "Valid");
            Assert.assertTrue(postResponse.getStatusCode() == 200, "Enrollment Services - Notifications API: " + postResponse.getBody().prettyPrint() +": \n" + "The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.result").toString().equalsIgnoreCase("success"));
            Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.message") == null);
            Assert.assertTrue(JsonPath.read(postResponse.asString(),"$.requestId").toString()!=null || !JsonPath.read(getResponse.asString(),"$.requestId").toString().isEmpty());
            return true;
        }catch(Exception e) {
            reports.writeResult(null, "Exception", "Exception in 'validateEnrollmentServicesNotificationsAPIByUsingPOSTMethod()' method. " + e.getMessage());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(null, "Exception", "AssertionError in 'validateEnrollmentServicesNotificationsAPIByUsingPOSTMethod()' method. " + e.getMessage());
            return false;
        }finally {inputData = null; basicAuthentication = null; strPostEndPoint = null; strGetEndPoint = null; postResponse = null; getResponse = null; supplierName = null; supplierTaxID = null; supplierPhoneNumber = null; strFieldsToUpdate = null; dbResponse = null; requestID = null;}
    }





    /********************************************************
     * Method Name      : validateReceiveAdditionalLinksToExistingSuppliersAndNewClientsAPIByUsingPOSTMethod()
     * Purpose          : to validate Company Service - Supplier Link Clone API using POST method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateReceiveAdditionalLinksToExistingSuppliersAndNewClientsAPIByUsingPOSTMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response postResponse = null;
        String strPostEndPoint = null;
        JSONArray dbResponse = null;
        Map<String, String> basicAuthentication = null;
        String arrFieldsToUpdate = null;
        String Id = null;
        String parentVNetCompanyID = null;
        String supplierVNetCompanyID = null;
        String clientVNetCompanyID = null;
        String accessCode = null;
        String enrollmentID = null;
        String expectedStatus = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);
            basicAuthentication = apiUtility.getBasicAuthenticationDetails();

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_CompanyServicesSupplierLinkClone_POSTInput", new Object[] {});
            for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                Id = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("ID").toString();
                parentVNetCompanyID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("parentVNetCompanyID").toString();
                supplierVNetCompanyID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("supplierVNetCompanyID").toString();
                clientVNetCompanyID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("clientVNetCompanyID").toString();
                accessCode = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("accessCode").toString();
                enrollmentID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("enrollmentId").toString();
                expectedStatus = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("ExpectedStatus").toString();

                strPostEndPoint = appInd.getPropertyValueByKeyName("enrollmentServicesAdditionalLinksToExistingSuppliers_POSTMethod");
                switch(expectedStatus){
                    case "Link Created":
                        //CPAY-1319 - API - POST Corcentric Payments-CP API user, to receive additional links to existing suppliers and new clients: API POST to link - if link not already exist
                        arrFieldsToUpdate = "linksToCreate,id#"+Id+"#parentVNetCompanyId#"+parentVNetCompanyID+"#supplierVNetCompanyId#"+supplierVNetCompanyID+"#clientVNetCompanyId#"+clientVNetCompanyID+"#accessCode#"+accessCode+"#enrollmentId#"+enrollmentID;
                        postResponse = apiUtility.httpPOSTMethodRequestWithBasicAuth(strPostEndPoint, basicAuthentication, arrFieldsToUpdate, "", inputData.get(0).get("InputFileForPOST"), "No", "Valid");
                        Assert.assertTrue(postResponse.getStatusCode() == 200, "receive additional links to existing suppliers and new clients: API POST to link - if link not already exist: " + postResponse.getBody().prettyPrint());
                        break;
                    case "Supplier Does Not Exist":
                        //CPAY-1319 - API - POST Corcentric Payments-CP API user, to receive additional links to existing suppliers and new clients: API POST to link - Supplier Does Not Exist
                        arrFieldsToUpdate = "linksToCreate,id#"+Id+"#parentVNetCompanyId#"+parentVNetCompanyID+"#supplierVNetCompanyId#"+supplierVNetCompanyID+"#clientVNetCompanyId#"+clientVNetCompanyID+"#accessCode#"+accessCode+"#enrollmentId#"+enrollmentID;
                        postResponse = apiUtility.httpPOSTMethodRequestWithBearerAuth(strPostEndPoint, apiCompanyServicesAuthToken, arrFieldsToUpdate, "", new Object[][] {}, inputData.get(0).get("InputFileForPOST"), "No", "Valid");
                        Assert.assertTrue(postResponse.getStatusCode() == 200, "receive additional links to existing suppliers and new clients: API POST to link - Supplier Does Not Exist: " + postResponse.getBody().prettyPrint());
                        break;
                    case "Client does not exist":
                        //CPAY-1319 - API - POST Corcentric Payments-CP API user, to receive additional links to existing suppliers and new clients: API POST to link - Client Does Not Exist
                        arrFieldsToUpdate = "linksToCreate,id#"+Id+"#parentVNetCompanyId#"+parentVNetCompanyID+"#supplierVNetCompanyId#"+supplierVNetCompanyID+"#clientVNetCompanyId#"+clientVNetCompanyID+"#accessCode#"+accessCode+"#enrollmentId#"+enrollmentID;
                        postResponse = apiUtility.httpPOSTMethodRequestWithBearerAuth(strPostEndPoint, apiCompanyServicesAuthToken, arrFieldsToUpdate, "", new Object[][] {}, inputData.get(0).get("InputFileForPOST"), "No", "Valid");
                        Assert.assertTrue(postResponse.getStatusCode() == 200, "receive additional links to existing suppliers and new clients: API POST to link - Client Does Not Exist: " + postResponse.getBody().prettyPrint());
                        break;
                    case "Supplier-Client link already exists":
                        //CPAY-1319 - API - POST Corcentric Payments-CP API user, to receive additional links to existing suppliers and new clients: API POST to link -If link already exist
                        arrFieldsToUpdate = "linksToCreate,id#"+Id+"#parentVNetCompanyId#"+parentVNetCompanyID+"#supplierVNetCompanyId#"+supplierVNetCompanyID+"#clientVNetCompanyId#"+clientVNetCompanyID+"#accessCode#"+accessCode+"#enrollmentId#"+enrollmentID;
                        postResponse = apiUtility.httpPOSTMethodRequestWithBasicAuth(strPostEndPoint, basicAuthentication, arrFieldsToUpdate, "", inputData.get(0).get("InputFileForPOST"), "No", "Valid");
                        Assert.assertTrue(postResponse.getStatusCode() == 200, "receive additional links to existing suppliers and new clients: API POST to link - If link already exist: " + postResponse.getBody().prettyPrint());
                        break;
                    case "Link does NOT exist between parent/sub client and supplier":
                        //CPAY-1319 - API - POST Corcentric Payments-CP API user, to receive additional links to existing suppliers and new clients: API POST to link - Link does NOT exist between parent/sub client and supplier
                        arrFieldsToUpdate = "linksToCreate,id#"+Id+"#parentVNetCompanyId#"+parentVNetCompanyID+"#supplierVNetCompanyId#"+supplierVNetCompanyID+"#clientVNetCompanyId#"+clientVNetCompanyID+"#accessCode#"+accessCode+"#enrollmentId#"+enrollmentID;
                        postResponse = apiUtility.httpPOSTMethodRequestWithBearerAuth(strPostEndPoint, apiCompanyServicesAuthToken, arrFieldsToUpdate, "", new Object[][] {}, inputData.get(0).get("InputFileForPOST"), "No", "Valid");
                        Assert.assertTrue(postResponse.getStatusCode() == 200, "receive additional links to existing suppliers and new clients: API POST to link - Link does NOT exist between parent/sub client and supplier: " + postResponse.getBody().prettyPrint());
                        break;
                    case "Inactive Link or Pending Status":
                        //CPAY-1319 - API - POST Corcentric Payments-CP API user, to receive additional links to existing suppliers and new clients: API POST to link - In invalid link or pending status
                        arrFieldsToUpdate = "linksToCreate,id#"+Id+"#parentVNetCompanyId#"+parentVNetCompanyID+"#supplierVNetCompanyId#"+supplierVNetCompanyID+"#clientVNetCompanyId#"+clientVNetCompanyID+"#accessCode#"+accessCode+"#enrollmentId#"+enrollmentID;
                        postResponse = apiUtility.httpPOSTMethodRequestWithBearerAuth(strPostEndPoint, apiCompanyServicesAuthToken, arrFieldsToUpdate, "", new Object[][] {}, inputData.get(0).get("InputFileForPOST"), "No", "Valid");
                        Assert.assertTrue(postResponse.getStatusCode() == 200, "receive additional links to existing suppliers and new clients: API POST to link - In invalid link or pending status: " + postResponse.getBody().prettyPrint());
                        break;
                    case "Parent and Subsidiary relationship does not exist between both clients":
                        //CPAY-1319 - API - POST Corcentric Payments-CP API user, to receive additional links to existing suppliers and new clients: API POST to link - Parent and Subsidiary relationship does not exist between both clients
                        arrFieldsToUpdate = "linksToCreate,id#"+Id+"#parentVNetCompanyId#"+parentVNetCompanyID+"#supplierVNetCompanyId#"+supplierVNetCompanyID+"#clientVNetCompanyId#"+clientVNetCompanyID+"#accessCode#"+accessCode+"#enrollmentId#"+enrollmentID;
                        postResponse = apiUtility.httpPOSTMethodRequestWithBearerAuth(strPostEndPoint, apiCompanyServicesAuthToken, arrFieldsToUpdate, "", new Object[][] {}, inputData.get(0).get("InputFileForPOST"), "No", "Valid");
                        Assert.assertTrue(postResponse.getStatusCode() == 200, "receive additional links to existing suppliers and new clients: API POST to link - Parent and Subsidiary relationship does not exist between both clients: " + postResponse.getBody().prettyPrint());
                        break;
                    case "Parent campaign offer details does not matched with Subsidery client":
                        //CPAY-1319 - API - POST Corcentric Payments-CP API user, to receive additional links to existing suppliers and new clients: API POST to link - Parent campaign offer details does not matched with Subsidery client
                        arrFieldsToUpdate = "linksToCreate,id#"+Id+"#parentVNetCompanyId#"+parentVNetCompanyID+"#supplierVNetCompanyId#"+supplierVNetCompanyID+"#clientVNetCompanyId#"+clientVNetCompanyID+"#accessCode#"+accessCode+"#enrollmentId#"+enrollmentID;
                        postResponse = apiUtility.httpPOSTMethodRequestWithBearerAuth(strPostEndPoint, apiCompanyServicesAuthToken, arrFieldsToUpdate, "", new Object[][] {}, inputData.get(0).get("InputFileForPOST"), "No", "Valid");
                        Assert.assertTrue(postResponse.getStatusCode() == 200, "receive additional links to existing suppliers and new clients: API POST to link - Parent campaign offer details does not matched with Subsidery client: " + postResponse.getBody().prettyPrint());
                        break;
                    default:
                        reports.writeResult(null, "Fail", "Invalid status '"+expectedStatus+"' for the Receive Additional Links To Existing Suppliers And New Clients API");
                        Assert.fail("Invalid status '"+expectedStatus+"' for the Receive Additional Links To Existing Suppliers And New Clients API");
                }


                //Validate the Response Json against DB response
                dbResponse = apiDataProvider.getAPIDataProviderResponse("API_CompanyServicesSupplierLinkClone_POSTResponse", new Object[] {"linkID", Id});
                Assert.assertTrue(apiUtility.compareJsonArrayElementsWithDBResponse(postResponse.asString(), dbResponse, "responses"
                        , "id#enrollmentId#clientVNetCompanyId#accessCode#accessCode#parentVNetCompanyId#supplierVNetCompanyId#responseCode#response"
                        , "ID#enrollmentId#clientVNetCompanyID#accessCode#SFDCCampaignID#parentVNetCompanyID#supplierVNetCompanyID#ResponseCode#ExpectedStatus"));
            }

            return true;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'validateCompanyServiceSupplierLinkCloneAPIByUsingPOSTMethod()' method. " + e.getMessage());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateCompanyServiceSupplierLinkCloneAPIByUsingPOSTMethod()' method. " + e.getMessage());
            return false;
        }finally{inputData = null; postResponse = null; strPostEndPoint = null; dbResponse = null; basicAuthentication = null; arrFieldsToUpdate = null; Id = null; parentVNetCompanyID = null; supplierVNetCompanyID = null; clientVNetCompanyID = null; accessCode = null; enrollmentID = null;}
    }

}
