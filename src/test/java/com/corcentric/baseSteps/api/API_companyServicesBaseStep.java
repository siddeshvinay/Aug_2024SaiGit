package com.corcentric.baseSteps.api;

import com.corcentric.runner.CucumberTestRunner;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.datatable.DataTable;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;
import java.util.*;
import static io.restassured.RestAssured.given;

public class API_companyServicesBaseStep extends CucumberTestRunner {
    /********************************************************
     * Method Name      : companyServicesMarketingCRUD()
     * Purpose          : to validate CRUD operations for Company Services Marketing API
     * Author           : Gudi
     * Parameters       : NA
     * ReturnType       : boolean
     ********************************************************/
    public boolean companyServicesMarketingCRUD(DataTable dataTable){
        List<Map<String, String>> inputFileName = null;
        Response getResponse = null;
        Response postResponse = null;
        Response putResponse = null;
        String strGetEndPoint = null;
        String strPostEndPoint = null;
        String strPutEndPoint = null;
        String companyID = null;
        String Id = null;
        String accessCode = null;
        Response response = null;
        JSONArray dbResponse = null;
        org.json.simple.JSONObject objJsonPOSTResponse = null;
        org.json.simple.JSONObject objJsonPUTResponse = null;
        org.json.simple.JSONObject objJsonGETResponse = null;
        org.json.simple.JSONArray objJsonGETResponseByCompanyID = null;
        String idFromPostResponse = null;
        String invalidCompanyID = null;
        try{
            //LinkedHashMap a = (LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0);

            inputFileName = dataTable.asMaps(String.class, String.class);

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_CompanyServiceMarketingGETRequestParams", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            Id = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("ID").toString();


            //Trigger the GET to get the existing company id which is intern used to trigger the POST request
            strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companyServiceMarketing_GetByID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", Id), apiCompanyServicesAuthToken, "Valid");

            //Read the CompanyID fron the GET response
            companyID = JsonPath.read(getResponse.asString(), "$.companyId").toString();



            //===================Trigger the POST request by using the CompanyID got from the previous GET request & validate against the DB response values==========================
            strPostEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint")+appInd.getPropertyValueByKeyName("companyServiceMarketing_PostRequest");
            postResponse = companyServicesMarketingPOSTRequest(strPostEndPoint, companyID, "", inputFileName.get(0).get("InputFileForPOST"), "Valid");
            accessCode = JsonPath.read(postResponse.asString(), "$.accessCode").toString();
            idFromPostResponse = JsonPath.read(postResponse.asString(), "$.id").toString();

            objJsonPOSTResponse = (org.json.simple.JSONObject) apiUtility.convertJsonResponseIntoJSONObject("JsonObject", postResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_CompanyServicesMarketing_POSTandPOST", new Object[] {"typeID", idFromPostResponse});
            Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0), objJsonPOSTResponse
                    , "ID#CompanyID#AccessCode#SeedCount#HitCount#LastAccessedAt#ExpiresAt#AssignedTo#AdditionalInfo#IsActive#CreatedBy#CreatedAt#UpdatedBy#UpdatedAt#IsActivationCode"
                    , "id#companyId#accessCode#seedCount#hitCount#lastAccessedAt#expiresAt#assignedTo#additionalInfo#isActive#createdBy#createdAt#updatedBy#updatedAt#isActivationCode"));


            Thread.sleep(5000);

            //Negative scenario for the POST method
            response = companyServicesMarketingPOSTRequest(strPostEndPoint, companyID, accessCode, inputFileName.get(0).get("InputFileForPOST"), "Invalid");
            Assert.assertTrue(response.getStatusCode() == 500, "Invalid statusCode when Existing accessCode is given: " + response.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(response.asString(),"$.Status").toString().equalsIgnoreCase("500"));
            Assert.assertTrue(JsonPath.read(response.asString(),"$.Detail").toString().equalsIgnoreCase("Internal server error occurred!"));
            Assert.assertTrue(JsonPath.read(response.asString(),"$.Title").toString().replace("&#39;","").contains("Internal Server Error. Violation of UNIQUE KEY constraint IX_MarketingWebsiteAuthentication_AccessCode. Cannot insert duplicate key in object dbo.MarketingWebsiteAuthentication. The duplicate key value is ("+accessCode+")."));
            Assert.assertTrue(JsonPath.read(response.asString(),"$.Title").toString().contains("The statement has been terminated."));


            //===================Trigger the GET method by using the ID from the previous POST method  & validate the GET Response against DB response values========================
            strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companyServiceMarketing_GetByID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", idFromPostResponse), apiCompanyServicesAuthToken, "Valid");

            objJsonGETResponse = (org.json.simple.JSONObject) apiUtility.convertJsonResponseIntoJSONObject("JsonObject", getResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_CompanyServiceMarketingGETRequestIDParam", new Object[] {"typeID", idFromPostResponse});

            Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0), objJsonGETResponse
                    , "ID#CompanyID#AccessCode#SeedCount#HitCount#LastAccessedAt#ExpiresAt#AssignedTo#AdditionalInfo#IsActive#CreatedBy#CreatedAt#UpdatedBy#UpdatedAt#IsActivationCode"
                    , "id#companyId#accessCode#seedCount#hitCount#lastAccessedAt#expiresAt#assignedTo#additionalInfo#isActive#createdBy#createdAt#updatedBy#updatedAt#isActivationCode"));

            //Negative senario's for Get By ID
            strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companyServiceMarketing_GetByID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", ""), apiCompanyServicesAuthToken, "Invalid");
            Assert.assertTrue(getResponse.getStatusCode() == 400, "Invalid statusCode when ID is blank: " + getResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.status").toString().equalsIgnoreCase("422"));
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.details").toString().equalsIgnoreCase("The companyId field is required."));
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.title").toString().equalsIgnoreCase("The companyId field is required."));

            strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companyServiceMarketing_GetByID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", "000"), apiCompanyServicesAuthToken, "Invalid");
            Assert.assertTrue(getResponse.getStatusCode() == 404, "Invalid statusCode when Invalid ID was given: " + getResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.status").toString().equalsIgnoreCase("404"));
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.detail").toString().equalsIgnoreCase("Marketing website authentication information doesn't exist."));
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.title").toString().equalsIgnoreCase("Marketing website authentication information not found."));


            //==================Trigger the GET method by using the AccessCode from the previous POST method & validate the GET Response against DB response values========================
            accessCode = JsonPath.read(postResponse.asString(),"$.accessCode").toString();
            strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companyServiceMarketing_GetByAccessCode");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", accessCode), apiCompanyServicesAuthToken, "Valid");

            objJsonGETResponse = (org.json.simple.JSONObject) apiUtility.convertJsonResponseIntoJSONObject("JsonObject", getResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_CompanyServiceMarketingGETRequestAccessCodeParam", new Object[] {"typeID", accessCode});
            Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0), objJsonGETResponse
                    , "ID#CompanyID#AccessCode#SeedCount#HitCount#LastAccessedAt#ExpiresAt#AssignedTo#AdditionalInfo#IsActive#CreatedBy#CreatedAt#UpdatedBy#UpdatedAt#IsActivationCode"
                    , "id#companyId#accessCode#seedCount#hitCount#lastAccessedAt#expiresAt#assignedTo#additionalInfo#isActive#createdBy#createdAt#updatedBy#updatedAt#isActivationCode"));

            //Negative senario's for Get By accessCode
            strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companyServiceMarketing_GetByAccessCode");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", ""), apiCompanyServicesAuthToken, "Invalid");
            Assert.assertTrue(getResponse.getStatusCode() == 400, "Invalid statusCode when accessCode is blank: " + getResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.status").toString().equalsIgnoreCase("422"));
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.details").toString().equalsIgnoreCase("The accessCode field is required."));
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.title").toString().equalsIgnoreCase("The accessCode field is required."));


            strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companyServiceMarketing_GetByAccessCode");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", "11111111111"), apiCompanyServicesAuthToken, "Invalid");
            Assert.assertTrue(getResponse.getStatusCode() == 404, "Invalid statusCode when Invalid accessCode was given: " + getResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.status").toString().equalsIgnoreCase("404"));
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.detail").toString().equalsIgnoreCase("Marketing website authentication information doesn't exist."));
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.title").toString().equalsIgnoreCase("Marketing website authentication information not found."));


            //======================Trigger the GET method by using the companyID from the previous POST method & validate the GET Response against DB response values========================
            companyID = JsonPath.read(postResponse.asString(),"$.companyId").toString();
            strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companyServiceMarketing_GetByCompanyID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", companyID), apiCompanyServicesAuthToken, "Valid");

            objJsonGETResponseByCompanyID = (org.json.simple.JSONArray) apiUtility.convertJsonResponseIntoJSONObject("JsonArray", getResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_CompanyServiceMarketingGETRequestCompanyIDParam", new Object[] {"typeID", companyID});

            for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i), (JSONObject) objJsonGETResponseByCompanyID.get(i)
                        , "ID#CompanyID#AccessCode#SeedCount#HitCount#LastAccessedAt#ExpiresAt#AssignedTo#AdditionalInfo#IsActive#CreatedBy#CreatedAt#UpdatedBy#UpdatedAt#IsActivationCode"
                        , "id#companyId#accessCode#seedCount#hitCount#lastAccessedAt#expiresAt#assignedTo#additionalInfo#isActive#createdBy#createdAt#updatedBy#updatedAt#isActivationCode"));
            }



            //Negative senario's for Get By CompanyID
            strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companyServiceMarketing_GetByCompanyID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", ""), apiCompanyServicesAuthToken, "Invalid");
            Assert.assertTrue(getResponse.getStatusCode() == 400, "Invalid statusCode when CompanyID is blank: " + getResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.status").toString().equalsIgnoreCase("422"));
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.details").toString().equalsIgnoreCase("The companyId field is required."));
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.title").toString().equalsIgnoreCase("The companyId field is required."));

            strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companyServiceMarketing_GetByCompanyID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", "11111111111"), apiCompanyServicesAuthToken, "Invalid");
            Assert.assertTrue(getResponse.getStatusCode() == 500, "Invalid statusCode when Invalid CompanyID was given: " + getResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.Status").toString().equalsIgnoreCase("500"));
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.Detail").toString().equalsIgnoreCase("Internal server error occurred!"));
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.Title").toString().equalsIgnoreCase("Internal Server Error. Error converting data type nvarchar to int."));


            //Trigger the PUT method by using the ID, CompanyID, AccessCode from the POST response
            idFromPostResponse = JsonPath.read(postResponse.asString(),"$.id").toString();
            strPutEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companyServiceMarketing_PutMethod");
            putResponse = companyServicesMarketingPutRequest(strPutEndPoint.replace("%", idFromPostResponse), postResponse, inputFileName.get(0).get("InputFileForPUT"));

            objJsonPUTResponse = (org.json.simple.JSONObject) apiUtility.convertJsonResponseIntoJSONObject("JsonObject", postResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_CompanyServicesMarketing_POSTandPOST", new Object[] {"typeID", idFromPostResponse});
            Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0), objJsonPUTResponse
                    , "ID#CompanyID#AccessCode#SeedCount#HitCount#LastAccessedAt#ExpiresAt#IsActive#CreatedBy#CreatedAt#IsActivationCode"
                    , "id#companyId#accessCode#seedCount#hitCount#lastAccessedAt#expiresAt#isActive#createdBy#createdAt#isActivationCode"));
            return true;
        }catch(Exception e) {
            reports.writeResult(null, "Exception", "Exception in ' companyServicesMarketingCRUD()' method. " + e.getMessage());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(null, "Exception", "AssertionError in 'companyServicesMarketingCRUD()' method. " + e.getMessage());
            return false;
        }finally {inputFileName = null; getResponse = null; postResponse = null; putResponse = null; strGetEndPoint = null; strPostEndPoint = null; strPutEndPoint = null;
            companyID = null; Id = null; accessCode = null; response = null; objJsonPOSTResponse = null; objJsonPUTResponse = null; objJsonGETResponse = null; objJsonGETResponseByCompanyID = null;}
    }




    /********************************************************
     * Method Name      : companyServicesMarketingPutRequest()
     * Purpose          : to perform put() http method request for 'Comapny Service Marketing' API and return the Response object
     * Author           : Gudi
     * Parameters       : endPoint, postResponse, requestInputFile
     * ReturnType       : boolean
     ********************************************************/
    public Response companyServicesMarketingPutRequest(String endPoint, Response postResponse, String requestInputFile){
        RequestSpecification httpRequest = null;
        Response response = null;
        JsonObject jsonObject = null;
        String jsonFileContent = null;
        JSONParser parser = null;
        Object obj = null;
        JSONObject jsonObj = null;
        try{
            //Modify/Update the input Json
            jsonFileContent = apiUtility.getJsonContentFromFile(requestInputFile);
            parser = new JSONParser();
            obj = parser.parse(jsonFileContent);

            jsonObj = (JSONObject) obj;
            jsonObj.put("companyId", JsonPath.read(postResponse.asString(),"$.companyId"));
            jsonObj.put("accessCode", JsonPath.read(postResponse.asString(),"$.accessCode"));
            jsonObj.put("createdBy", JsonPath.read(postResponse.asString(),"$.createdBy"));
            jsonObj.put("seedCount", JsonPath.read(postResponse.asString(),"$.seedCount"));
            jsonObj.put("hitCount", JsonPath.read(postResponse.asString(),"$.hitCount"));
            jsonObj.put("createdAt", JsonPath.read(postResponse.asString(),"$.createdAt"));
            jsonObj.put("assignedTo", "ggudi@corcentric.com");
            jsonObj.put("expiresAt", JsonPath.read(postResponse.asString(),"$.expiresAt"));
            jsonObj.put("additionalInfo", "The Company Service Marketing API was updated");
            jsonObj.put("isActive", JsonPath.read(postResponse.asString(),"$.isActive"));
            jsonObj.put("isActivationCode", JsonPath.read(postResponse.asString(),"$.isActivationCode"));
            jsonObj.put("updatedBy", "ggudi@corcentric.com");
            jsonObj.put("updatedAt", JsonPath.read(postResponse.asString(),"$.updatedAt"));
            reports.writeResult(null, "Info", "The modified Request body for the 'CompanyServicesMarketing' PUT request: " + jsonObj.toJSONString());

            RestAssured.baseURI = appInd.getPropertyValueByKeyName("dataProvider_API_EndPoint");
            RestAssured.useRelaxedHTTPSValidation();
            httpRequest = given().header("Authorization", apiCompanyServicesAuthToken)
                    .header("Content-Type", "application/json");
            response = httpRequest
                    .when()
                    .body(jsonObj.toString())
                    .put(endPoint)
                    .prettyPeek();
            reports.writeResult(null, "Info", "Response body from 'companyServicesMarketingPutRequest()' method: " + response.getBody().prettyPrint());

            if(response.getBody().toString().contains("errors")){
                reports.writeResult(null, "Fail", "The method 'companyServicesMarketingPutRequest()' was failed. " + response.getBody().prettyPrint());
                Assert.fail("The method 'companyServicesMarketingPutRequest()' was failed. " + response.getBody().prettyPrint());
                return null;
            }else{
                Assert.assertTrue(response.getStatusCode() == 200, "Invalid responseCode for the 'companyServicesMarketingPutRequest()' method");
                reports.writeResult(null, "Pass", "The method 'companyServicesMarketingPutRequest()' was successful. " + response.getBody().prettyPrint());
                return response;
            }
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'companyServicesMarketingPutRequest()' method. " + e.getMessage());
            return null;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'companyServicesMarketingPutRequest()' method. " + e.getMessage());
            return null;
        }finally{httpRequest = null; response = null; jsonObject = null; jsonFileContent = null; parser = null; obj = null; jsonObj = null;}
    }




    /********************************************************
     * Method Name      : companyServicesMarketingPOSTRequest()
     * Purpose          : to perform POST() http method request for 'Comapny Service Marketing' API and return the Response object
     * Author           : Gudi
     * Parameters       : endPoint, companyID, accessCode, requestInputFile, validationType
     * ReturnType       : boolean
     ********************************************************/
    public Response companyServicesMarketingPOSTRequest(String endPoint, String companyID, String accessCode, String requestInputFile, String validationType){
        RequestSpecification httpRequest = null;
        Response response = null;
        String jsonFileContent = null;
        JSONParser parser = null;
        Object obj = null;
        JSONObject jsonObj = null;
        try{
            //Modify/Update the input Json
            jsonFileContent = apiUtility.getJsonContentFromFile(requestInputFile);
            parser = new JSONParser();
            obj = parser.parse(jsonFileContent);

            jsonObj = (JSONObject) obj;
            jsonObj.put("companyId", Integer.parseInt(companyID));

            if(accessCode.isEmpty()){
                String randomNumers = appInd.generateRandomNumbers(65, 90, 1);
                char ch = (char) Integer.parseInt(randomNumers);
                jsonObj.put("accessCode", String.valueOf(ch+"_"+appInd.getDateTime("Shhmmss")+"_" + appInd.getDateTime("S")));
            }else{
                jsonObj.put("accessCode", accessCode);
            }


            jsonObj.put("updatedBy", appInd.getPropertyValueByKeyName("qaUserName"));
            jsonObj.put("createdBy", appInd.getPropertyValueByKeyName("qaUserName"));
            reports.writeResult(null, "Info", "The modified Request body for the 'CompanyServicesMarketing' POST request: " + jsonObj.toJSONString());

            RestAssured.baseURI = appInd.getPropertyValueByKeyName("dataProvider_API_EndPoint");
            RestAssured.useRelaxedHTTPSValidation();
            httpRequest = given().header("Authorization", apiCompanyServicesAuthToken)
                    .header("Content-Type", "application/json");
            response = httpRequest
                    .when()
                    .body(jsonObj.toString())
                    .post(endPoint)
                    .prettyPeek();
            appDep.threadSleep(2000);
            reports.writeResult(null, "Info", "Response body from 'companyServicesMarketingPOSTRequest()' method: " + response.getBody().prettyPrint());
            if(validationType.equalsIgnoreCase("Valid")){
                reports.writeResult(null, "Info", "Validating the positive scenario for the 'companyServicesMarketingPOSTRequest()' method");
                if(response.getBody().toString().contains("errors")){
                    reports.writeResult(null, "Fail", "The method 'companyServicesMarketingPOSTRequest()' was failed. " + response.getBody().prettyPrint());
                    Assert.fail("The method 'companyServicesMarketingPOSTRequest()' was failed. " + response.getBody().prettyPrint());
                    return null;
                }else{
                    Assert.assertTrue(response.getStatusCode() == 200, "Invalid responseCode for the 'companyServicesMarketingPOSTRequest()' method");
                    reports.writeResult(null, "Pass", "The method 'companyServicesMarketingPOSTRequest()' was successful. " + response.getBody().prettyPrint());
                    return response;
                }
            }else{
                reports.writeResult(null, "Info", "Validating the negative scenario for the 'companyServicesMarketingPOSTRequest()' method");
                return response;
            }
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'companyServicesMarketingPOSTRequest()' method. " + e.getMessage());
            return null;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'companyServicesMarketingPOSTRequest()' method. " + e.getMessage());
            return null;
        }finally{httpRequest = null; response = null; jsonFileContent = null; parser = null; obj = null; jsonObj = null;}
    }




    /********************************************************
     * Method Name      : companyServicesContactGETRequest()
     * Purpose          : to perform GET() http method request for 'Comapny Service Contact' API and return the Response object
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean companyServicesContactGETRequest(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        String strGetEndPoint = null;
        Response getResponse = null;
        org.json.simple.JSONArray objJsonGETResponseVerbalContacts = null;
        JSONArray dbResponse = null;
        String validCompanyID = null;
       try{
           dbResponse = apiDataProvider.getAPIDataProviderResponse("API_CompanyServiceSContact_GetCompanyID", new Object[] {});
           String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
           validCompanyID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("CompanyID").toString();
           String InvalidCompanyID = appInd.getDateTime("ShhmmssS");

           inputData = dataTable.asMaps(String.class, String.class);


           //API - GET - Contacts - GetVerbalContacts with type as validation
           strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companyServiceContact_GETVerbalContacts");
           getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", validCompanyID).replace("%2", inputData.get(0).get("validType")), apiCompanyServicesAuthToken, "Valid");

           objJsonGETResponseVerbalContacts = (org.json.simple.JSONArray) apiUtility.convertJsonResponseIntoJSONObject("JsonArray", getResponse.asString());
           dbResponse = apiDataProvider.getAPIDataProviderResponse("API_GETContact_GETVerbalContact", new Object[] {"companyID", validCompanyID});

           for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
               Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i), (JSONObject) objJsonGETResponseVerbalContacts.get(i)
                       , "ContactID#FirstName#LastName#ContactName"
                       , "contactId#firstName#lastName#displayName"));
           }


           //Invalid scenario for: API - GET - Contacts - GetVerbalContacts: Give wrong CompanyID
           strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companyServiceContact_GETVerbalContacts");
           getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", InvalidCompanyID).replace("%2", inputData.get(0).get("validType")), apiCompanyServicesAuthToken, "Invalid");
           Assert.assertTrue(getResponse.getStatusCode() == 400, "Invalid statusCode when Invalid CompanyID was used: " + getResponse.getBody().prettyPrint());
           Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.status").toString().equalsIgnoreCase("422"));
           Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.details").toString().replace("\n", "").contains("The value '"+InvalidCompanyID+"' is not valid for CompanyId.'Company Id' must not be empty."));
           Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.title").toString().replace("\n", "").contains("The value '"+InvalidCompanyID+"' is not valid for CompanyId.'Company Id' must not be empty."));


           //Invalid scenario for: API - GET - Contacts - GetVerbalContacts: Give CompanyID is EMPTY
           strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companyServiceContact_GETVerbalContacts");
           getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", "").replace("%2", inputData.get(0).get("validType")), apiCompanyServicesAuthToken, "Invalid");
           Assert.assertTrue(getResponse.getStatusCode() == 400, "Invalid statusCode when CompanyID is Empty: " + getResponse.getBody().prettyPrint());
           Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.status").toString().equalsIgnoreCase("422"));
           Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.details").toString().equalsIgnoreCase("'Company Id' must not be empty."));
           Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.title").toString().equalsIgnoreCase("'Company Id' must not be empty."));


           //Invalid scenario for: API - GET - Contacts - GetVerbalContacts: Give wrong Type details
           strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companyServiceContact_GETVerbalContacts");
           getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", validCompanyID).replace("%2", inputData.get(0).get("invalidType")), apiCompanyServicesAuthToken, "Invalid");
           Assert.assertTrue(getResponse.getStatusCode() == 400, "Invalid statusCode when Invalid Type was used: " + getResponse.getBody().prettyPrint());
           Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.status").toString().equalsIgnoreCase("422"));
           Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.details").toString().equalsIgnoreCase("Type value should be validation "));
           Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.title").toString().equalsIgnoreCase("Type value should be validation "));

           //Invalid scenario for: API - GET - Contacts - GetVerbalContacts: Give type as empty
           strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companyServiceContact_GETVerbalContacts");
           getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", validCompanyID).replace("%2", ""), apiCompanyServicesAuthToken, "Invalid");
           Assert.assertTrue(getResponse.getStatusCode() == 400, "Invalid statusCode when Type was Empty: " + getResponse.getBody().prettyPrint());
           Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.status").toString().equalsIgnoreCase("422"));
           Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.details").toString().replace("\n", "").contains("'Type' must not be empty.Type value should be validation"));
           Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.title").toString().replace("\n", "").contains("'Type' must not be empty.Type value should be validation"));
           return true;
       }catch(Exception e){
           reports.writeResult(null, "Exception", "Exception in 'companyServicesContactGETRequest()' method. " + e.getMessage());
           return false;
       }catch(AssertionError e) {
           reports.writeResult(oBrowser, "Exception", "AssertionError in 'companyServicesContactGETRequest()' method. " + e.getMessage());
           return false;
       }finally {inputData = null; strGetEndPoint = null; getResponse = null; objJsonGETResponseVerbalContacts = null; dbResponse = null; validCompanyID = null;}
    }



    /********************************************************
     * Method Name      : companyServicesSupportCaseGETRequest()
     * Purpose          : to perform GET() http method request for 'Comapny Service Support-CAse' API and return the Response object
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean companyServicesSupportCaseGETRequest(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        String strGetEndPoint = null;
        Response getResponse = null;
        JSONArray dbResponse = null;
        String validCompanyID = null;
        JSONObject objJsonGETResponseByCompanyID = null;
        try{
            String invalidCompanyID = appInd.getDateTime("Shhmmss");

            inputData = dataTable.asMaps(String.class, String.class);

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_SupportCase_GETInput", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            validCompanyID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("CompanyID").toString();

            //CPAY-3029: API - company-service - GET Support Case Companies By ID
            strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companyServiceSupportCase_GetByID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", validCompanyID), apiCompanyServicesAuthToken, "Valid");
            objJsonGETResponseByCompanyID = (JSONObject) apiUtility.convertJsonResponseIntoJSONObject("JsonObject", getResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_GETSupportCaseCompaniesByIDResponse", new Object[] {"companyID", validCompanyID});
            Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0), objJsonGETResponseByCompanyID
                    , "CompanyID#LegalName#TIN#EmbeddedURL"
                    , "id#legalName#tin#embeddedURL"));


            //Validate Links details of Json Response against DB data
            Assert.assertTrue(apiUtility.compareJsonArrayElementsWithDBResponse(getResponse.asString(), dbResponse, "links"
                    , "id#statusId#linkStatus#clientCompanyId#supplierCompanyId#campaignID#campaignName#offerNumber"
                    , "LinkID#LinkStatusID#LinkStatus#ClientCompanyID#SupplierCompanyId#CampaignID#CampaignName#OfferNumber"));



            //Validate PaymentTypes details of Json Response against DB data
            Assert.assertTrue(apiUtility.compareJsonArrayElementsWithDBResponse(getResponse.asString(), dbResponse, "paymentTypes"
                    , "piId#pieId#paymentTypeId#paymentType#accountNumber#bankName#accountOwnerName#abaNumber#countryCode#currencyCode#vcardEmail#vcardFax#remittanceEmail"
                    , "PIID#PIEID#PaymentTypeId#PaymentType#AccountNumber#BankName#AccountOwnerName#ABARoutingNumber#CountryCode#CurrencyCode#VcardEmail#VcardFax#RemittanceEmail"));



            //CPAY-3029: Negative scenario: API - company-service - GET Support Case Companies By ID with invalid companyID
            strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companyServiceSupportCase_GetByID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", invalidCompanyID), apiCompanyServicesAuthToken, "Invalid");
            Assert.assertTrue(getResponse.getStatusCode() == 404, "Invalid statusCode when Invalid CompanyId was used: " + getResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.status").toString().equalsIgnoreCase("404"));
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.detail").toString().equalsIgnoreCase("A company with the specified CompanyId doesn't exist"));
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.title").toString().equalsIgnoreCase("Company not found"));
            return true;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'companyServicesSupportCaseGETRequest()' method. " + e.getMessage());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'companyServicesSupportCaseGETRequest()' method. " + e.getMessage());
            return false;
        }finally{inputData = null; strGetEndPoint = null; getResponse = null; dbResponse = null; validCompanyID = null; objJsonGETResponseByCompanyID = null;}
    }




    /********************************************************
     * Method Name      : companyServicesSupplierLinksGETAndPUTRequests()
     * Purpose          : to perform GET() & PUT() http method requests for 'Comapny Service Supplier Links' API and return the Response object
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean companyServicesSupplierLinksGETAndPUTRequests(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        String strGetEndPoint = null;
        String strPutEndPoint = null;
        Response getResponse = null;
        Response putResponse = null;
        String linkID = null;
        String entityIdentifier = null;
        String clientCompanyID = null;
        String supplierCompanyID = null;
        JSONArray dbResponse = null;
        org.json.simple.JSONObject objJsonGETResponseByLinkID = null;
        String jsonRespData = null;
        String dbRespData = null;
        org.json.simple.JSONArray objJsonGETSuppliersByPollDate = null;
        Object objSuppliers = null;
        JSONObject suppliers = null;
        Object objLinks = null;
        JSONObject links = null;
        String arrJSonColumns[];
        String dbColumns[];
        org.json.simple.JSONArray arrPaymentInfo = null;
        JSONObject paymentInfo = null;
        org.json.simple.JSONArray objJsonGETResponnseSuppliersByPoll = null;
        ResponseBody responseBody = null;
        String campaignID = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);

            //CPAY-3322: API-Company Management Service || GET- Supplier Links Poll by date
            dbResponse = apiDataProvider.getAPIDataProviderResponse("SuppliersLinkPollStartAndEndDate", new Object[]{});
            String startDate = (((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("Start Date").toString().split("T"))[0];
            String endDate = (((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("End Date").toString().split("T"))[0];

            strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companyServiceSupplierLinks_GETSupplierByPollDate");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", startDate).replace("%2", endDate), apiCompanyServicesAuthToken, "Valid");
            linkID = JsonPath.read(getResponse.asString(), "$[0].linkId").toString();

            objJsonGETSuppliersByPollDate = (org.json.simple.JSONArray) apiUtility.convertJsonResponseIntoJSONObject("JsonArray", getResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_CompanyServicesGETSupplierLinksByPollDateResponseValidation", new Object[]{"startDate", startDate, "endDate", endDate});

            for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i), (JSONObject) objJsonGETSuppliersByPollDate.get(i)
                        , "SupplierVNetID#SupplierName#SupplierStatusID#SupplierProfileType#LinkActCompanyID#SupplierTIN#LinkPaymentTypeID#SupplierDUNS#SupplierDBAs#SupplierSFDCAcctID#LinkClientVNetID#LinkClientName#LinkID#LinkStatusID#LinkStatusDescription#LinkVendorIDs#LinkSharedRemitTos#LinkSFDCCaseID#LinkSFDCCampaignID#LinkSFDCCampaignHistoryID#LinkActivationCode#LinkVenNumber#LinkNumberOfChecks#LinkTotalSpend#LinkACHLimit#LinkVNDPercentage#LinkPaymentTerms#LinkMaintenanceFeeMethod#LinkPaymentTypeDesc#LinkOfferNumber#UpdatedTableName#UpdatedFieldName"
                        , "supplierVNetId#supplierName#supplierStatusId#supplierProfileType#linkActCompanyId#supplierTIN#linkPaymentTypeId#supplierDUNS#supplierDBAs#supplierSFDCAcctID#linkClientVNetId#linkClientName#linkId#linkStatusId#linkStatusDescription#linkVendorIds#linkSharedRemitTos#linkSFDCCaseId#linkSFDCCampaignId#linkSFDCCampaignHistoryId#linkActivationCode#linkVenNumber#linkNumberOfChecks#linkTotalSpend#linkACHLimit#linkVNDPercentage#linkPaymentTerms#linkMaintenanceFeeMethod#linkPaymentTypeDesc#linkOfferNumber#updatedTableName#updatedFieldName"));
            }


            //Negative Scenario: API-Company Management Service || GET- Supplier Links Poll by date
            strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companyServiceSupplierLinks_GETSupplierByPollDate");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", "").replace("%2", ""), apiCompanyServicesAuthToken, "Invalid");
            Assert.assertTrue(getResponse.getStatusCode() == 400, "Invalid statusCode when both start and end dates are blank: " + getResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.status").toString().equalsIgnoreCase("422"));
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.details").toString().replace("\n", "").equalsIgnoreCase("The EndDate field is required.'End Date' must not be empty.The StartDate field is required.'Start Date' must not be empty."));
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.title").toString().replace("\n", "").equalsIgnoreCase("The EndDate field is required.'End Date' must not be empty.The StartDate field is required.'Start Date' must not be empty."));


            //CPAY-3326: API-Company Management Service || GET- Supplier Link by ID
            strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companyServiceSupplierLinks_GETSupplierByLinkID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", linkID), apiCompanyServicesAuthToken, "Valid");

            //Validate the GET method Json response output against DB values
            objJsonGETResponseByLinkID = (org.json.simple.JSONObject) apiUtility.convertJsonResponseIntoJSONObject("JsonObject", getResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_CompanyServicesGETSupplierByLinkID", new Object[] {"typeID", linkID});

            objSuppliers = objJsonGETResponseByLinkID.get("supplier");
            suppliers = (JSONObject) objSuppliers;
            objLinks = objJsonGETResponseByLinkID.get("link");
            links = (JSONObject) objLinks;
            arrPaymentInfo = (org.json.simple.JSONArray) objJsonGETResponseByLinkID.get("paymentInformations");

            //Validate Links details of Json Response against DB data
            arrJSonColumns = "linkID#linkStatusID#entityIdentifier#clientCompanyID#supplierCompanyID#sfdcCampaignID#numberOfChecks#totalDollar#venNumber#vndPercentage#offerNumber#maintenanceFeeMethod#paymentTerms#sendRemittance".split("#");
            dbColumns = "LinkID#LinkStatusID#EntityIdentifier#ClientCompanyID#SupplierCompanyID#SFDCCampaignID#NumberOfChecks#TotalDollar#VenNumber#VNDPercentage#OfferNumber#MaintenanceFeeMethod#PaymentTerms#SendRemittance".split("#");
            for(int i=0; i<arrJSonColumns.length; i++){
                dbRespData = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get(dbColumns[i]));
                jsonRespData = String.valueOf(links.get(arrJSonColumns[i]));
                Assert.assertTrue(dbRespData.equalsIgnoreCase(jsonRespData), "Mis-match in Actual: "+ dbRespData +" and Expected: "+ jsonRespData + " Values for Links Data for the columnName: '"+arrJSonColumns[i]+"'");
            }
            reports.writeResult(null, "Pass", "The Link details from DB and Json response are matching");

            //Validate Suppliers details of Json Response against DB data
            arrJSonColumns = "companyID#legalName#website#taxID#taxIDIsSSN#profileType#embeddedURL#sfdcAccountID".split("#");
            dbColumns = "CompanyID#LegalName#Website#TaxID#TaxIDIsSSN#ProfileType#EmbeddedURL#SFDCAccountID".split("#");
            for(int i=0; i<arrJSonColumns.length; i++){
                dbRespData = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get(dbColumns[i]));
                jsonRespData = String.valueOf(suppliers.get(arrJSonColumns[i]));
                Assert.assertTrue(dbRespData.equalsIgnoreCase(jsonRespData), "Mis-match in Actual: "+ dbRespData +" and Expected: "+ jsonRespData + " Values for Suppliers Data for the columnName '"+arrJSonColumns[i]+"'");
            }
            reports.writeResult(null, "Pass", "The Supplier details from DB and Json response are matching");


            //Validate Payment Information details of Json Response against DB data
//            Assert.assertTrue(apiUtility.compareJsonArrayElementsWithDBResponse(getResponse.asString(), dbResponse, "paymentInformations"
//                    , "paymentTypeID#companyID#piid#picid#pieid#accountNumber#bankName#accountOwnerName#abaRoutingNumber#countryISO#currencyCode#vCardEmailAddress#vCardFax#remittanceDeliveryAddress"
//                    , "PaymentTypeID#CompanyID#PIID#PICID#PIEID#AccountNumber#BankName#AccountOwnerName#ABARoutingNumber#CountryISO#CurrencyCode#VCardEmailAddress#VCardFax#RemittanceDeliveryAddress"));

            int index = 0;
            for(int i=0; i<arrPaymentInfo.size(); i++){
                arrJSonColumns = "paymentTypeID#companyID#piid#picid#pieid#accountNumber#bankName#accountOwnerName#abaRoutingNumber#countryISO#currencyCode#vCardEmailAddress#vCardFax#remittanceDeliveryAddress".split("#");
                dbColumns = "PaymentTypeID#CompanyID#PIID#PICID#PIEID#AccountNumber#BankName#AccountOwnerName#ABARoutingNumber#CountryISO#CurrencyCode#VCardEmailAddress#VCardFax#RemittanceDeliveryAddress".split("#");

                dbRespData = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("PIID"));
                for(int x=0; x<arrPaymentInfo.size(); x++){
                    paymentInfo = (JSONObject) arrPaymentInfo.get(x);
                    if(dbRespData.equalsIgnoreCase(String.valueOf(paymentInfo.get("piid")))){
                        index = x;
                        break;
                    }
                }

                paymentInfo = (JSONObject) arrPaymentInfo.get(index);
                for(int j=0; j<arrJSonColumns.length; j++){
                    dbRespData = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get(dbColumns[j]));
                    jsonRespData = String.valueOf(paymentInfo.get(arrJSonColumns[j]));
                    if(dbRespData.equalsIgnoreCase("null") && jsonRespData.equalsIgnoreCase("0")){
                        //As per Dev comments, When DB value is null & json value is Zero treat them as passed/matching.
                        reports.writeResult(null, "Pass", "The expected '"+dbRespData+"' & Actual '"+jsonRespData+"' values are matching.");
                    }else{
                        Assert.assertTrue(dbRespData.equalsIgnoreCase(jsonRespData), "Mis-match in Actual: "+ dbRespData +" and Expected: "+ jsonRespData + " Values for Payment Information Data for the column: '"+arrJSonColumns[j]+"'");
                    }
                }
            }
            reports.writeResult(null, "Pass", "The Payment Information details from DB and Json response are matching");


            if(JsonPath.read(getResponse.asString(), "$..entityIdentifier").toString().replace("\"\"","").equalsIgnoreCase("[]")){
                entityIdentifier = "";
            }else{
                entityIdentifier = JsonPath.read(getResponse.asString(), "$..entityIdentifier").toString().replace("\"\"","").replace("[","").replace("]", "");
            }
            clientCompanyID = JsonPath.read(getResponse.asString(), "$..clientCompanyID").toString().replace("[","").replace("]", "");
            supplierCompanyID = JsonPath.read(getResponse.asString(), "$..supplierCompanyID").toString().replace("[","").replace("]", "");
            campaignID = JsonPath.read(getResponse.asString(), "$..sfdcCampaignID").toString().replace("[","").replace("]", "");



            //Negative scenario: API-Company Management Service || GET- Supplier Link by invalid link id (as number)
            strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companyServiceSupplierLinks_GETSupplierByLinkID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", inputData.get(0).get("invalidLinkID1")), apiCompanyServicesAuthToken, "Invalid");
            Assert.assertTrue(getResponse.getStatusCode() == 404, "Invalid statusCode when invalid linkid was given: " + getResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.status").toString().equalsIgnoreCase("404"));
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.title").toString().equalsIgnoreCase("Link not found"));
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.detail").toString().equalsIgnoreCase("A link doesn't exist"));

            //Negative scenario: API-Company Management Service || GET- Supplier Link by invalid link id (as string)
            strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companyServiceSupplierLinks_GETSupplierByLinkID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", inputData.get(0).get("invalidLinkID2")), apiCompanyServicesAuthToken, "Invalid");
            Assert.assertTrue(getResponse.getStatusCode() == 400, "Invalid statusCode when invalid linkid was given: " + getResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.status").toString().equalsIgnoreCase("422"));
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.title").toString().equalsIgnoreCase("The value '"+inputData.get(0).get("invalidLinkID2")+"' is not valid."));
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.details").toString().equalsIgnoreCase("The value '"+inputData.get(0).get("invalidLinkID2")+"' is not valid."));



            //API-Company Management Service || GET- Supplier Links Poll
            strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companySupplierSupplierLinks_GETSupplierByPoll");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint, apiCompanyServicesAuthToken, "Valid");

            responseBody = getResponse.getBody();
            if(responseBody.asString().replace("\n", "").trim().equals("[    ]") || responseBody.asString().replace("\n", "").trim().equals("[]")){
                reports.writeResult(null, "Info", "Supplier Creation/Updation should have done for any supplier to display data. Else empty array is displayed []");
            }else{
                objJsonGETResponnseSuppliersByPoll = (org.json.simple.JSONArray) apiUtility.convertJsonResponseIntoJSONObject("JsonArray", getResponse.asString());
                dbResponse = apiDataProvider.getAPIDataProviderResponse("API_CompanyServicesGETSupplierLinksPoll", new Object[]{});

                for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                    Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i), (JSONObject) objJsonGETResponnseSuppliersByPoll.get(i)
                            , "SupplierVNetID#SupplierName#SupplierStatusID#SupplierProfileType#SupplierTIN#SupplierDUNS#SupplierDBAs#SupplierSFDCAcctID#LinkClientVNetID#LinkClientName#LinkID#LinkStatusID#LinkStatusDescription#LinkVendorIDs#LinkSharedRemitTos#LinkSFDCCaseID#LinkSFDCCampaignID#LinkSFDCCampaignHistoryID#LinkActCompanyID#LinkActivationCode#LinkVenNumber#LinkNumberOfChecks#LinkTotalSpend#LinkACHLimit#LinkVNDPercentage#LinkPaymentTerms#LinkMaintenanceFeeMethod#LinkOfferNumber#LinkPaymentTypeID#LinkPaymentTypeDesc#UpdatedTableName#UpdatedFieldName"
                            , "supplierVNetId#supplierName#supplierStatusId#supplierProfileType#supplierTIN#supplierDUNS#supplierDBAs#supplierSFDCAcctID#linkClientVNetId#linkClientName#linkId#linkStatusId#linkStatusDescription#linkVendorIds#linkSharedRemitTos#linkSFDCCaseId#linkSFDCCampaignId#linkSFDCCampaignHistoryId#linkActCompanyId#linkActivationCode#linkVenNumber#linkNumberOfChecks#linkTotalSpend#linkACHLimit#linkVNDPercentage#linkPaymentTerms#linkMaintenanceFeeMethod#linkOfferNumber#linkPaymentTypeId#linkPaymentTypeDesc#updatedTableName#updatedFieldName"));
                }
            }


            //CPAY-3329: API-Company Management Service || GET- Supplier Links
            strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companySupplierSupplierLinks_GETSupplierLinks");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", linkID).replace("%2", supplierCompanyID).replace("%3", clientCompanyID).replace("%4", entityIdentifier), apiCompanyServicesAuthToken, "Valid");

            //Validate the GET method Json response output against DB values
            objJsonGETResponseByLinkID = (org.json.simple.JSONObject) apiUtility.convertJsonResponseIntoJSONObject("JsonObject", getResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_CompanyServicesGETSupplierLinks", new Object[] {"linkID", linkID, "supplierCompanyID", supplierCompanyID, "clientCompanyID",  clientCompanyID, "entityIdentifier", entityIdentifier});

            objSuppliers = objJsonGETResponseByLinkID.get("supplier");
            suppliers = (JSONObject) objSuppliers;
            objLinks = objJsonGETResponseByLinkID.get("link");
            links = (JSONObject) objLinks;
            arrPaymentInfo = (org.json.simple.JSONArray) objJsonGETResponseByLinkID.get("paymentInformations");

            //Validate Links details of Json Response against DB data
            arrJSonColumns = "linkID#linkStatusID#entityIdentifier#clientCompanyID#supplierCompanyID#sfdcCampaignID#numberOfChecks#totalDollar#venNumber#vndPercentage#offerNumber#maintenanceFeeMethod#paymentTerms#sendRemittance".split("#");
            dbColumns = "LinkID#LinkStatusID#EntityIdentifier#ClientCompanyID#SupplierCompanyID#SFDCCampaignID#NumberOfChecks#TotalDollar#VenNumber#VNDPercentage#OfferNumber#MaintenanceFeeMethod#PaymentTerms#SendRemittance".split("#");
            for(int i=0; i<arrJSonColumns.length; i++){
                dbRespData = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get(dbColumns[i]));
                jsonRespData = String.valueOf(links.get(arrJSonColumns[i]));
                if(dbRespData.equalsIgnoreCase("") && jsonRespData.equalsIgnoreCase("null")){
                    //As per Dev comments, When DB value is "" & json value is null treat them as passed/matching.
                    reports.writeResult(null, "Pass", "The expected '"+dbRespData+"' & Actual '"+jsonRespData+"' values are matching.");
                }else{
                    Assert.assertTrue(dbRespData.equalsIgnoreCase(jsonRespData), "Mis-match in Actual: "+ dbRespData +" and Expected: "+ jsonRespData + " Values for Links Data for the columnName: "+arrJSonColumns[i]+"'");
                }
            }
            reports.writeResult(null, "Pass", "The Link details from DB and Json response are matching");

            //Validate Suppliers details of Json Response against DB data
            arrJSonColumns = "companyID#legalName#website#taxID#taxIDIsSSN#profileType#embeddedURL#sfdcAccountID".split("#");
            dbColumns = "CompanyID#LegalName#Website#TaxID#TaxIDIsSSN#ProfileType#EmbeddedURL#SFDCAccountID".split("#");
            for(int i=0; i<arrJSonColumns.length; i++){
                dbRespData = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get(dbColumns[i]));
                jsonRespData = String.valueOf(suppliers.get(arrJSonColumns[i]));
                Assert.assertTrue(dbRespData.equalsIgnoreCase(jsonRespData), "Mis-match in Actual: "+ dbRespData +" and Expected: "+ jsonRespData + " Values for Suppliers Data for the columnName: '"+arrJSonColumns[i]+"'");
            }
            reports.writeResult(null, "Pass", "The Supplier details from DB and Json response are matching");


            //Validate Payment Information details of Json Response against DB data
            index = 0;
            for(int i=0; i<arrPaymentInfo.size(); i++){
                arrJSonColumns = "paymentTypeID#companyID#piid#picid#pieid#accountNumber#bankName#accountOwnerName#abaRoutingNumber#countryISO#currencyCode#vCardEmailAddress#vCardFax#remittanceDeliveryAddress".split("#");
                dbColumns = "PaymentTypeID#CompanyID#PIID#PICID#PIEID#AccountNumber#BankName#AccountOwnerName#ABARoutingNumber#CountryISO#CurrencyCode#VCardEmailAddress#VCardFax#RemittanceDeliveryAddress".split("#");

                dbRespData = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("PIID"));
                for(int x=0; x<arrPaymentInfo.size(); x++){
                    paymentInfo = (JSONObject) arrPaymentInfo.get(x);
                    if(dbRespData.equalsIgnoreCase(String.valueOf(paymentInfo.get("piid")))){
                        index = x;
                        break;
                    }
                }

                paymentInfo = (JSONObject) arrPaymentInfo.get(index);
                for(int j=0; j<arrJSonColumns.length; j++){
                    dbRespData = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get(dbColumns[j]));
                    jsonRespData = String.valueOf(paymentInfo.get(arrJSonColumns[j]));
                    if(dbRespData.equalsIgnoreCase("null") && jsonRespData.equalsIgnoreCase("0")){
                        //As per Dev comments, When DB value is null & json value is Zero treat them as passed/matching.
                        reports.writeResult(null, "Pass", "The expected '"+dbRespData+"' & Actual '"+jsonRespData+"' values are matching.");
                    }else{
                        Assert.assertTrue(dbRespData.equalsIgnoreCase(jsonRespData), "Mis-match in Actual: "+ dbRespData +" and Expected: "+ jsonRespData + " Values for Payment Information Data for the columnName: '"+arrJSonColumns[j]+"'");
                    }
                }
            }
            reports.writeResult(null, "Pass", "The Payment Information details from DB and Json response are matching");



            //Negative scenario: API-Company Management Service || GET- Supplier Links: Enter invalid SupplierCompanyId, ClientCompanyId, EntityIdentifier
            //It should work as SupplierCompanyId, ClientCompanyId, EntityIdentifier are not mandatory fields
            strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companySupplierSupplierLinks_GETSupplierLinks");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", linkID).replace("%2", inputData.get(0).get("invalidSupplierCompanyID")).replace("%3", inputData.get(0).get("invalidClientCompanyID")).replace("%4", inputData.get(0).get("invalidEntityIdentifier")), apiCompanyServicesAuthToken, "Valid");


            //API-Company Management Service || PUT- Supplier Link ID
            strPutEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companySupplierSupplierLinks_PUTSupplierLinkID");
            putResponse = companyServicesSupplierLinkPUTRequest(strPutEndPoint.replace("%", linkID), inputData.get(0).get("inputFileForPUT"), "Valid");
            Assert.assertTrue(putResponse.getStatusCode() == 200, "Company Management Service || PUT- Supplier Link ID API: " + putResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.success").toString().equalsIgnoreCase("true"));
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.linkStatus").toString().equalsIgnoreCase("2"));
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.message").toString().equalsIgnoreCase("Link updated successfully."));


            //Negative scenario: API-Company Management Service || PUT- Supplier Link ID bu giving worng linkid number
            strPutEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companySupplierSupplierLinks_PUTSupplierLinkID");
            putResponse = companyServicesSupplierLinkPUTRequest(strPutEndPoint.replace("%", inputData.get(0).get("invalidLinkID1")), inputData.get(0).get("inputFileForPUT"), "Invalid");
            Assert.assertTrue(putResponse.getStatusCode() == 200, "Invalid statusCode when invalid linkid was given: " + putResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.success").toString().equalsIgnoreCase("false"));
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.linkStatus").toString().equalsIgnoreCase("6"));
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.message").toString().equalsIgnoreCase("Link not found."));



            //Negative scenario: API-Company Management Service || PUT- Supplier Link ID by giving worng linkid string
            strPutEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companySupplierSupplierLinks_PUTSupplierLinkID");
            putResponse = companyServicesSupplierLinkPUTRequest(strPutEndPoint.replace("%", inputData.get(0).get("invalidLinkID2")), inputData.get(0).get("inputFileForPUT"), "Invalid");
            Assert.assertTrue(putResponse.getStatusCode() == 400, "Invalid statusCode when invalid linkid was given: " + putResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.status").toString().equalsIgnoreCase("422"));
            Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.details").toString().equalsIgnoreCase("The value '"+inputData.get(0).get("invalidLinkID2")+"' is not valid."));
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.title").toString().equalsIgnoreCase("The value '"+inputData.get(0).get("invalidLinkID2")+"' is not valid."));


            //API-Company Management Service || PUT- Supplier Link ID SWAP
            String checkNumbers = "5";
            String totalDollars = "2";
            strPutEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companySupplierSupplierLinks_PUTSupplierLinkIDSWAP");
            putResponse = apiUtility.httpPUTRequestWithoutBody(strPutEndPoint.replace("%1", linkID).replace("%2", "").replace("%3", clientCompanyID).replace("%4", campaignID).replace("%5", checkNumbers).replace("%6", totalDollars), apiCompanyServicesAuthToken, "Valid");
            Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.response").toString().equalsIgnoreCase("Success"));

            //Negative scenario: //API-Company Management Service || PUT- Supplier Link ID SWAP invalid linkid as 000
            strPutEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companySupplierSupplierLinks_PUTSupplierLinkIDSWAP");
            putResponse = apiUtility.httpPUTRequestWithoutBody(strPutEndPoint.replace("%1", "000").replace("%2", "000").replace("%3", clientCompanyID).replace("%4", campaignID).replace("%5", checkNumbers).replace("%6", totalDollars), apiCompanyServicesAuthToken, "Invalid");
            Assert.assertTrue(putResponse.getStatusCode() == 400, "Invalid statusCode when invalid linkid '000' was given: " + putResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.status").toString().equalsIgnoreCase("422"));
            Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.detail").toString().equalsIgnoreCase("Link id is required"));
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.title").toString().equalsIgnoreCase("Link id is required"));


            //Negative scenario: //API-Company Management Service || PUT- Supplier Link ID SWAP invalid linkid as abc
            strPutEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companySupplierSupplierLinks_PUTSupplierLinkIDSWAP");
            putResponse = apiUtility.httpPUTRequestWithoutBody(strPutEndPoint.replace("%1", "abc").replace("%2", "abc").replace("%3", clientCompanyID).replace("%4", campaignID).replace("%5", checkNumbers).replace("%6", totalDollars), apiCompanyServicesAuthToken, "Invalid");
            Assert.assertTrue(putResponse.getStatusCode() == 400, "Invalid statusCode when invalid linkid 'abc' was given: " + putResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.status").toString().equalsIgnoreCase("422"));
            Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.details").toString().equalsIgnoreCase("The value 'abc' is not valid."));
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.title").toString().equalsIgnoreCase("The value 'abc' is not valid."));
            return true;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'companyServicesSupplierLinksGETAndPUTRequests()' method. " + e.getMessage());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'companyServicesSupplierLinksGETAndPUTRequests()' method. " + e.getMessage());
            return false;
        }finally {inputData = null; strGetEndPoint = null; strPutEndPoint = null; getResponse = null; putResponse = null; linkID = null; entityIdentifier = null; clientCompanyID = null; supplierCompanyID = null; dbResponse = null;objJsonGETResponseByLinkID = null; jsonRespData = null; dbRespData = null; objJsonGETSuppliersByPollDate = null; objSuppliers = null;
            suppliers = null; objLinks = null; links = null;arrJSonColumns = null; dbColumns = null; arrPaymentInfo = null; paymentInfo = null; objJsonGETResponnseSuppliersByPoll = null; responseBody = null; campaignID = null;}
    }




    /********************************************************
     * Method Name      : companyServicesSupplierLinkPUTRequest()
     * Purpose          : to perform put() http method request for 'Comapny Service Supplier Links' API and return the Response object
     * Author           : Gudi
     * Parameters       : endPoint, requestInputFile
     * ReturnType       : boolean
     ********************************************************/
    public Response companyServicesSupplierLinkPUTRequest(String endPoint, String requestInputFile, String validationType){
        RequestSpecification httpRequest = null;
        Response response = null;
        String jsonFileContent = null;
        JSONParser parser = null;
        Object obj = null;
        JSONObject jsonObj = null;
        try{
            //Modify/Update the input Json
            jsonFileContent = apiUtility.getJsonContentFromFile(requestInputFile);
            parser = new JSONParser();
            obj = parser.parse(jsonFileContent);

            jsonObj = (JSONObject) obj;
            jsonObj.put("accountNumber", appInd.getDateTime("hhmmss"));
            jsonObj.put("vCardEmailAddress", appInd.getPropertyValueByKeyName("qaUserName"));
            jsonObj.put("remittanceDeliveryAddress", appInd.getPropertyValueByKeyName("qaUserName"));
            reports.writeResult(null, "Info", "The modified Request body for the 'companyServicesSupplierLinkPUTRequest' PUT request: " + jsonObj.toJSONString());

            RestAssured.baseURI = appInd.getPropertyValueByKeyName("dataProvider_API_EndPoint");
            RestAssured.useRelaxedHTTPSValidation();
            httpRequest = given().header("Authorization", apiCompanyServicesAuthToken)
                    .header("Content-Type", "application/json");
            response = httpRequest
                    .when()
                    .body(jsonObj.toString())
                    .put(endPoint)
                    .prettyPeek();
            reports.writeResult(null, "Info", "Response body from 'companyServicesSupplierLinkPUTRequest()' method: " + response.getBody().prettyPrint());

            if(validationType.equalsIgnoreCase("Valid")){
                if(response.getBody().toString().contains("errors")){
                    reports.writeResult(null, "Fail", "The method 'companyServicesSupplierLinkPUTRequest()' was failed. " + response.getBody().prettyPrint());
                    Assert.fail("The method 'companyServicesSupplierLinkPUTRequest()' was failed. " + response.getBody().prettyPrint());
                    return null;
                }else{
                    Assert.assertTrue(response.getStatusCode() == 200, "Invalid responseCode for the ' companyServicesSupplierLinkPUTRequest()' method");
                    reports.writeResult(null, "Pass", "The method 'companyServicesSupplierLinkPUTRequest()' was successful. " + response.getBody().prettyPrint());
                    return response;
                }
            }else{
                reports.writeResult(null, "Info", "Validating the negative scenario for the 'companyServicesSupplierLinkPUTRequest()' method");
                return response;
            }

        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'companyServicesSupplierLinkPUTRequest()' method. " + e.getMessage());
            return null;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'companyServicesSupplierLinkPUTRequest()' method. " + e.getMessage());
            return null;
        }finally{httpRequest = null; response = null; jsonFileContent = null; parser = null; obj = null; jsonObj = null;}
    }





    /********************************************************
     * Method Name      : searchSupplierAPIUsingGETRequest()
     * Purpose          : to perform GET() http method request for 'Supplier Search' API and return the Response object
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean searchSupplierAPIUsingGETRequest(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        String strGetEndPoint = null;
        Response getResponse = null;
        ResponseBody responseBody = null;
        JSONArray dbResponse = null;
        String supplierPaynetID = null;
        String supplierName = null;
        String supplierTaxID = null;
        org.json.simple.JSONArray objJsonGETResponnse = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_SearchSuppliersGETInput", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            supplierPaynetID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("CompanyID").toString();
            supplierName = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("Supplier Name").toString();
            supplierTaxID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("TaxID").toString();


            //API - GET - SupplierSearch - Supplier Search Endpoint using supplier PayNetID
            //String supplierPaynetID = "314228126";
            strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companySupplierSearchSupplier_GETByPayNetID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", supplierPaynetID), apiCompanyServicesAuthToken, "Valid");

            objJsonGETResponnse = (org.json.simple.JSONArray) apiUtility.convertJsonResponseIntoJSONObject("JsonArray", getResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_SearchSuppliersGETResponse", new Object[] {"companyID", supplierPaynetID});

            for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i), (JSONObject) objJsonGETResponnse.get(i)
                        , "CompanyID#LegalName#TaxID"
                        , "companyId#legalName#taxId"));
            }


            //API - GET - SupplierSearch - Supplier Search Endpoint using supplier Name
            //String supplierName = "Hershey Candy LLC";
            strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companySupplierSearchSupplier_GETByName");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", supplierName), apiCompanyServicesAuthToken, "Valid");

            objJsonGETResponnse = (org.json.simple.JSONArray) apiUtility.convertJsonResponseIntoJSONObject("JsonArray", getResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_SearchSuppliersGETResponse", new Object[] {"supplierName", supplierName});

            for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i), (JSONObject) objJsonGETResponnse.get(i)
                        , "CompanyID#LegalName#TaxID"
                        , "companyId#legalName#taxId"));
            }

            //API - GET - SupplierSearch - Supplier Search Endpoint using supplier TaxID
            //String supplierTaxID = "773334444";
            strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companySupplierSearchSupplier_GETByTaxID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", supplierTaxID), apiCompanyServicesAuthToken, "Valid");

            objJsonGETResponnse = (org.json.simple.JSONArray) apiUtility.convertJsonResponseIntoJSONObject("JsonArray", getResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_SearchSuppliersGETResponse", new Object[] {"taxID", supplierTaxID});

            for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i), (JSONObject) objJsonGETResponnse.get(i)
                        , "CompanyID#LegalName#TaxID"
                        , "companyId#legalName#taxId"));
            }


            //Negative scenario: API - GET - SupplierSearch - Supplier Search Endpoint using invalid supplier PayNetID
            supplierPaynetID = appInd.getDateTime("Shhmmss");
            strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companySupplierSearchSupplier_GETByPayNetID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", supplierPaynetID), apiCompanyServicesAuthToken, "Invalid");
            responseBody = getResponse.getBody();
            Assert.assertTrue(responseBody.asString().replace(" ","").replace("\n", "").equals("[]"), "Failed to return the empty array when invalid Supplier PayNetID is used to search the suppliers");

            //Negative scenario: API - GET - SupplierSearch - Supplier Search Endpoint using invalid supplier Name
            strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companySupplierSearchSupplier_GETByName");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", inputData.get(0).get("InvalidSupplierName")), apiCompanyServicesAuthToken, "Invalid");
            responseBody = getResponse.getBody();
            Assert.assertTrue(responseBody.asString().replace(" ","").replace("\n", "").equals("[]"), "Failed to return the empty array when invalid Supplier name is used to search the suppliers");


            //Negative scenario: API - GET - SupplierSearch - Supplier Search Endpoint using invalid supplier TaxID
            supplierTaxID = appInd.getDateTime("Shhmmss");
            strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companySupplierSearchSupplier_GETByTaxID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", supplierTaxID), apiCompanyServicesAuthToken, "Invalid");
            responseBody = getResponse.getBody();
            Assert.assertTrue(responseBody.asString().replace(" ","").replace("\n", "").equals("[]"), "Failed to return the empty array when invalid Supplier TaxID is used to search the suppliers");


            //Negative scenario: API - GET - SupplierSearch - Supplier Search Endpoint using blank values
            strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companySupplierSearchSupplier_GETByNoParam");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint, apiCompanyServicesAuthToken, "Invalid");
            Assert.assertTrue(getResponse.getStatusCode() == 404, "Invalid statusCode when blank value was given for Supplier search API: " + getResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.status").toString().equalsIgnoreCase("400"));
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.detail").toString().equalsIgnoreCase("Either id, name and/or TIN is required"));
            Assert.assertTrue(JsonPath.read(getResponse.asString(),"$.title").toString().equalsIgnoreCase(""));

            return true;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'searchSupplierAPIUsingGETRequest()' method. " + e.getMessage());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'searchSupplierAPIUsingGETRequest()' method. " + e.getMessage());
            return false;
        }finally {inputData = null; strGetEndPoint = null; getResponse = null; responseBody = null; dbResponse = null; supplierPaynetID = null; supplierName = null; supplierTaxID = null; objJsonGETResponnse = null;}
    }




    /********************************************************
     * Method Name      : validateGETCampaignAPIByID()
     * Purpose          : to perform GET() http method request for 'Campaign' API and return the Response object
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateGETCampaignAPIByID(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        JSONArray dbResponse = null;
        String campaignID = null;
        String strGetEndPoint = null;
        Response getResponse = null;
        JSONObject objJsonGETResponseByCampaignID = null;
        JSONObject objJsonGETResponseByInvalidCampaignID = null;
        String columnNames[];
        try{
            inputData = dataTable.asMaps(String.class, String.class);

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_GETCampaignID_InputParam", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            campaignID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("CampaignID").toString();

            //CAPY-3319: API - GET - Campaign - Get Campaign By valid ID
            strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companyServiceCampaign_GETByID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", campaignID), apiCompanyServicesAuthToken, "Valid");

            //Validate the GET method Json response output against DB values
            objJsonGETResponseByCampaignID = (JSONObject) apiUtility.convertJsonResponseIntoJSONObject("JsonObject", getResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_GetCampaignID_GETResponseData", new Object[] {"campaignID", campaignID});

            Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0), objJsonGETResponseByCampaignID
                    , "ID#CampaignID#CampaignName#Description#CompanyID#StartDate#EndDate#CreatedDate#IsActive#CampainDataSectionIDMask#IsLaunched#IsHipaa#Phone#Email#EPESClientLogo#CountryISO#LaunchedAt#IsDefault#MicrositeID#CurrencyCode#CMActivationCode#CMActivationCodeEnabled#CampaignCountryMask#DocuSignBrandID#DocuSignEmailSubject#CampaignType#CustomizedNo"
                    , "id#campaignId#campaignName#description#companyId#startDate#endDate#createdDate#isActive#campainDataSectionIdMask#isLaunched#isHipaa#phone#email#epesClientLogo#countryISO#launchedAt#isDefault#micrositeId#currencyCode#cmActivationCode#cmActivationCodeEnabled#campaignCountryMask#docuSignBrandId#docuSignEmailSubject#campaignType#customizedNo"));


            //Validate 'campaignOffers' details of Json Response against DB data
            Assert.assertTrue(apiUtility.compareJsonArrayElementsWithDBResponse(getResponse.asString(), dbResponse, "campaignOffers"
                    , "offerId#campaignId#offerNo#introduction#acceptance#achLimitText#createdAt#createdBy#offerType#offerTemplate#agentIntervention#byPassACH#recordStatus#vndPercentage#vndPercentageText#sendRemittance"
                    , "OfferID#CampaignID#OfferNo#Introduction#Acceptance#ACHLimitText#CreatedAt#CreatedBy#OfferType#OfferTemplate#AgentIntervention#ByPassACH#RecordStatus#VNDPercentage#VNDPercentageText#SendRemittance"));


            //Validate 'commercialCardCampaign' details of Json Response against DB data
            Assert.assertTrue(apiUtility.compareJsonObjectElementsWithDBResponse(getResponse.asString(), dbResponse, "commercialCardCampaign"
                    , "companyId#campaignId#cardPaymentTypeId#cardCampaignTypeId#cardPlatformId#cardNetworkId#virtualCardDeliveryMethod#templateHtmlFile#transitionImageFileName#duration#templateId#numberOfOffres"
                    , "CompanyID#CampaignID#CardPaymentTypeID#CardCampaignTypeID#CardPlatformID#CardNetworkID#VirtualCardDeliveryMethod#TemplateHtmlFile#TransitionImageFileName#Duration#TemplateID#NumberOfOffres"));




            //CAPY-3319: API - GET - Campaign - Get Campaign By invalid ID
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", inputData.get(0).get("InvalidCampaignID")), apiCompanyServicesAuthToken, "Invalid");
            Assert.assertTrue(getResponse.getStatusCode() == 200, "Get Campaign By giving Invalid id: " + getResponse.getBody().prettyPrint()+ ".\n The line number: " + appInd.getCurrentLineNumber());
            objJsonGETResponseByInvalidCampaignID = (JSONObject) apiUtility.convertJsonResponseIntoJSONObject("JsonObject", getResponse.asString());
            columnNames = "campaignId#campaignName#description#companyId#startDate#endDate#createdDate#isActive#campainDataSectionIdMask#isLaunched#isHipaa#phone#email#epesClientLogo#countryISO#launchedAt#isDefault#micrositeId#currencyCode#cmActivationCode#campaignCountryMask#docuSignBrandId#docuSignEmailSubject#campaignType#customizedNo#commercialCardCampaign#campaignOffers".split("#");
            for(int i=0; i<columnNames.length; i++){
                Assert.assertTrue(objJsonGETResponseByInvalidCampaignID.get(columnNames[i]) == null, "Line Number: " + appInd.getCurrentLineNumber());
            }
            Assert.assertTrue(objJsonGETResponseByInvalidCampaignID.get("id").toString().equals("0"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(objJsonGETResponseByInvalidCampaignID.get("cmActivationCodeEnabled").toString().equals("0"), "Line Number: " + appInd.getCurrentLineNumber());


            //CAPY-3319: API - GET - Campaign - Get Campaign By blank ID
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", ""), apiCompanyServicesAuthToken, "Invalid");
            Assert.assertTrue(getResponse.getStatusCode() == 404, "Get Campaign By giving id as blank: " + getResponse.getBody().prettyPrint()+ ".\n The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(getResponse.getBody().prettyPrint().isEmpty(), "The line number: " + appInd.getCurrentLineNumber());

            return true;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'validateGETCampaignAPIByID()' method. " + e.getMessage());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateGETCampaignAPIByID()' method. " + e.getMessage());
            return false;
        }finally {inputData = null; dbResponse = null; campaignID = null; strGetEndPoint = null; getResponse = null; objJsonGETResponseByCampaignID = null; objJsonGETResponseByInvalidCampaignID = null; columnNames = null;}
    }




    /********************************************************
     * Method Name      : validateGetPayNetUserInfoWithValidPayNetIdAPIByUsingGETMethod()
     * Purpose          : to perform GET() http method request for 'GET Paynet Usre Information' API and return the Response object
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateGetPayNetUserInfoWithValidPayNetIdAPIByUsingGETMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        JSONArray dbResponse = null;
        String companyID = null;
        String strGetEndPoint = null;
        Response getResponse = null;
        org.json.simple.JSONArray objJsonGETResponseByCompanyID = null;
        String invalidCompanyID = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_GetPaynetUsersInfo_GETInput", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            companyID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("CompanyID").toString();

            //CPAY-2622: API- GET Request - PayNet user information based on valid PayNetID
            strGetEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint") + appInd.getPropertyValueByKeyName("companyServicesPayNetUserInfo_GETByPayNetID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", companyID), apiCompanyServicesAuthToken, "Valid");
            Assert.assertTrue(getResponse.getStatusCode() == 200, "PayNet user information based on valid PayNetID API: " + getResponse.getBody().prettyPrint()+ ".\n The line number: " + appInd.getCurrentLineNumber());

            objJsonGETResponseByCompanyID = (org.json.simple.JSONArray) apiUtility.convertJsonResponseIntoJSONObject("JsonArray", getResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_GetPayNetUsersInfo_GETResponse", new Object[] {"companyID", companyID});

            for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i), (JSONObject) objJsonGETResponseByCompanyID.get(i)
                        , "UserID#CompanyID#type#UserTypeID#AuditName#Phone#PhoneExt#DFAType#phoneTypeName#Email#Login"
                        , "id#companyId#typeName#typeId#name#phone#phoneExt#phoneTypeId#phoneTypeName#email#login"));
            }


            //CPAY-2623: API- GET Request - PayNet user information based on invalid PayNetID
            invalidCompanyID = companyID+inputData.get(0).get("InvalidCompanyID")+appInd.getDateTime("s");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%", invalidCompanyID), apiCompanyServicesAuthToken, "Invalid");
            Assert.assertTrue(getResponse.getStatusCode() == 400, "PayNet user information based on Invalid PayNetID API: " + getResponse.getBody().prettyPrint()+ ".\n The line number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.status").toString().equalsIgnoreCase("422"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.details").toString().replace("\n", "").equalsIgnoreCase("The value '"+invalidCompanyID+"' is not valid for CompanyId.'Company Id' must not be empty."), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.title").toString().replace("\n", "").equalsIgnoreCase("The value '"+invalidCompanyID+"' is not valid for CompanyId.'Company Id' must not be empty."), "Line Number: " + appInd.getCurrentLineNumber());

            return true;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'validateGetPayNetUserInfoWithValidPayNetIdAPIByUsingGETMethod()' method. " + e.getMessage());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateGetPayNetUserInfoWithValidPayNetIdAPIByUsingGETMethod()' method. " + e.getMessage());
            return false;
        }finally {inputData = null; dbResponse = null; companyID = null; strGetEndPoint = null; getResponse = null; objJsonGETResponseByCompanyID = null; invalidCompanyID = null;}
    }





    /********************************************************
     * Method Name      : validateCompanyServiceSupplierLinkCloneAPIByUsingPOSTMethod()
     * Purpose          : to validate Company Service - Supplier Link Clone API using POST method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateCompanyServiceSupplierLinkCloneAPIByUsingPOSTMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response postResponse = null;
        String strPostEndPoint = null;
        JSONArray dbResponse = null;
        String arrFieldsToUpdate = null;
        String Id = null;
        String parentVNetCompanyID = null;
        String supplierVNetCompanyID = null;
        String clientVNetCompanyID = null;
        String accessCode = null;
        String enrollmentID = null;
        String responseCode = null;
        String expectedStatus = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_CompanyServicesSupplierLinkClone_POSTInput", new Object[] {});
            for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                Id = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("ID").toString();
                parentVNetCompanyID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("parentVNetCompanyID").toString();
                supplierVNetCompanyID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("supplierVNetCompanyID").toString();
                clientVNetCompanyID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("clientVNetCompanyID").toString();
                accessCode = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("accessCode").toString();
                enrollmentID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("enrollmentId").toString();
                responseCode = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("ResponseCode").toString();
                expectedStatus = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i)).get("ExpectedStatus").toString();

                strPostEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint")+appInd.getPropertyValueByKeyName("companyServicesSupplierLinkClone_POSTMethod");
                switch(expectedStatus){
                    case "Link Created":
                        //CPAY-3476 - API - POST - Company Service -  Supplier Link Clone: Successfully link supplier and clone link
                        strPostEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint")+appInd.getPropertyValueByKeyName("companyServicesSupplierLinkClone_POSTMethod");
                        arrFieldsToUpdate = "links,id#"+Id+"#parentVNetCompanyId#"+parentVNetCompanyID+"#supplierVNetCompanyId#"+supplierVNetCompanyID+"#clientVNetCompanyId#"+clientVNetCompanyID+"#accessCode#"+accessCode+"#enrollmentId#"+enrollmentID;
                        postResponse = apiUtility.httpPOSTMethodRequestWithBearerAuth(strPostEndPoint, apiCompanyServicesAuthToken, arrFieldsToUpdate, "", new Object[][] {}, inputData.get(0).get("InputFileForPOST"), "No", "Valid");
                        Assert.assertTrue(postResponse.getStatusCode() == 200, "Company Service - Supplier Link Clone API: " + postResponse.getBody().prettyPrint());
                        break;
                    case "Supplier-Client link already exists":
                        //CPAY-3476 - API - POST - Company Service -  Supplier Link Clone: Already linked supplier
                        strPostEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint")+appInd.getPropertyValueByKeyName("companyServicesSupplierLinkClone_POSTMethod");
                        arrFieldsToUpdate = "links,id#"+Id+"#parentVNetCompanyId#"+parentVNetCompanyID+"#supplierVNetCompanyId#"+supplierVNetCompanyID+"#clientVNetCompanyId#"+clientVNetCompanyID+"#accessCode#"+accessCode+"#enrollmentId#"+enrollmentID;
                        postResponse = apiUtility.httpPOSTMethodRequestWithBearerAuth(strPostEndPoint, apiCompanyServicesAuthToken, arrFieldsToUpdate, "", new Object[][] {}, inputData.get(0).get("InputFileForPOST"), "No", "Valid");
                        Assert.assertTrue(postResponse.getStatusCode() == 200, "Company Service - Supplier Link Clone API with Already linked supplier: " + postResponse.getBody().prettyPrint());
                        break;
                    case "Parent and Subsidiary relationship does not exist between both clients":
                        //CPAY-3476 - API - POST - Company Service -  Supplier Link Clone: Try to link parent & child supplier with invalid relationship
                        strPostEndPoint = appInd.getPropertyValueByKeyName("companyServiceEndPoint")+appInd.getPropertyValueByKeyName("companyServicesSupplierLinkClone_POSTMethod");
                        arrFieldsToUpdate = "links,id#"+Id+"#parentVNetCompanyId#"+parentVNetCompanyID+"#supplierVNetCompanyId#"+supplierVNetCompanyID+"#clientVNetCompanyId#"+clientVNetCompanyID+"#accessCode#"+accessCode+"#enrollmentId#"+enrollmentID;
                        postResponse = apiUtility.httpPOSTMethodRequestWithBearerAuth(strPostEndPoint, apiCompanyServicesAuthToken, arrFieldsToUpdate, "", new Object[][] {}, inputData.get(0).get("InputFileForPOST"), "No", "Invalid");
                        Assert.assertTrue(postResponse.getStatusCode() == 200, "Company Service - Supplier Link Clone API with parent & child supplier with invalid relationship: " + postResponse.getBody().prettyPrint());
                        break;
                    default:
                        reports.writeResult(null, "Fail", "Invalid status '"+expectedStatus+"' for the Company services Supplier Link Clone API");
                        Assert.fail("Invalid status '"+expectedStatus+"' for the Company services Supplier Link Clone API");
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
        }finally {inputData = null; postResponse = null; strPostEndPoint = null; dbResponse = null; arrFieldsToUpdate = null; Id = null; parentVNetCompanyID = null; supplierVNetCompanyID = null; clientVNetCompanyID = null; accessCode = null; enrollmentID = null;}
    }

}
