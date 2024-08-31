package com.corcentric.common;

import com.corcentric.runner.CucumberTestRunner;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;
import java.io.*;
import java.util.*;
import static io.restassured.RestAssured.given;

public class APITestGenericUtility extends CucumberTestRunner {
    /********************************************************
     * Method Name      : httpGETRequest()
     * Purpose          : to perform GET http method operation and to return the Response object
     * Author           : Gudi
     * Parameters       : endPoint, validationType
     * ReturnType       : String
     ********************************************************/
    public Response httpGETRequest(String endPoint, String authToken, String validationType){
        RequestSpecification httpRequest = null;
        Response response = null;
        JsonObject jsonObject = null;
        try{
            RestAssured.baseURI = appInd.getPropertyValueByKeyName("dataProvider_API_EndPoint");
            RestAssured.useRelaxedHTTPSValidation();
            httpRequest = given().header("Authorization", authToken)
                    .header("Content-Type", "application/json");
            response = httpRequest
                    .when()
                    .get(endPoint).prettyPeek();

            if(validationType.equalsIgnoreCase("Valid")){
                reports.writeResult(null, "Info", "Validating the positive scenario for the 'httpGETRequest()' method with the EndPoint: " + endPoint);
                if(response.getBody().toString().contains("errors")){
                    reports.writeResult(null, "Fail", "The method 'httpGETRequest()' was failed. " + response.getBody().prettyPrint());
                    Assert.fail("The method 'httpGETRequest()' was failed. " + jsonObject.toString());
                    return null;
                }else{
                    Assert.assertTrue(response.getStatusCode() == 200, "Invalid responseCode for the 'httpGETRequest()' method");
                    reports.writeResult(null, "Pass", "The method 'httpGETRequest()' was successful. " + response.getBody().prettyPrint());
                    return response;
                }
            }else{
                reports.writeResult(null, "Info", "Validating the negative scenario for the 'httpGETRequest()' method with the EndPoint: "+endPoint +" \n & the Response Body: " + response.getBody().prettyPrint());
                return response;
            }

        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'httpGETRequest()' method. " + e.getMessage());
            return null;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'httpGETRequest()' method. " + e.getMessage());
            return null;
        }finally{httpRequest = null; response = null; jsonObject = null;}
    }



    /********************************************************
     * Method Name      : getJsonContentFromFile()
     * Purpose          : to read the json file content from the input json file
     * Author           : Gudi
     * Parameters       : inputJsonFile
     * ReturnType       : String
     ********************************************************/
    public String getJsonContentFromFile(String inputJsonFile){
        BufferedReader br = null;
        String fileContent = "";
        try{
            br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\main\\resources\\inputJSON\\"+inputJsonFile));
            while(true){
                String content = br.readLine();
                if(content==null) break;
                fileContent+= content;
            }
            reports.writeResult(null, "Pass", "The input JSON file '"+inputJsonFile+"' was read successful");
            return fileContent;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'getJsonContentFromFile()' method. " + e.getMessage());
            return null;
        }finally {
            try {br.close(); br = null; fileContent = null;
            }catch(Exception e){}
        }
    }




    /********************************************************
     * Method Name      : writeJsonContentToFile()
     * Purpose          : to write the json file content back to the input json file
     * Author           : Gudi
     * Parameters       : inputJsonFile, updatedJsonFileContent
     * ReturnType       : NA
     ********************************************************/
    public void writeJsonContentToFile(String inputJsonFile, String updatedJsonFileContent){
        BufferedWriter bw = null;
        try{
            bw = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "\\src\\main\\resources\\inputJSON\\"+inputJsonFile, false));
            bw.write(updatedJsonFileContent);
            reports.writeResult(null, "Pass", "The input JSON file '"+inputJsonFile+"' was updated with modified JsonContent successful");
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'writeJsonContentToFile()' method. " + e.getMessage());
        }finally {
            try {bw.flush(); bw.close(); bw = null;
            }catch(Exception e){}
        }
    }



    /********************************************************
     * Method Name      : httpPUTRequestWithoutBody()
     * Purpose          : to perform put() http method request with no body
     * Author           : Gudi
     * Parameters       : endPoint, requestInputFile
     * ReturnType       : boolean
     ********************************************************/
    public Response httpPUTRequestWithoutBody(String endPoint, String apiBearerToken, String validationType){
        RequestSpecification httpRequest = null;
        Response response = null;
        try{
            RestAssured.baseURI = appInd.getPropertyValueByKeyName("dataProvider_API_EndPoint");
            RestAssured.useRelaxedHTTPSValidation();
            httpRequest = given().header("Authorization", apiBearerToken)
                    .header("Content-Type", "application/json");
            response = httpRequest
                    .when()
                    .put(endPoint)
                    .prettyPeek();

            if(validationType.equalsIgnoreCase("Valid")){
                reports.writeResult(null, "Info", "Executing Positive scenario using 'httpPUTRequestWithoutBody()' method with EndPoint: " + endPoint);
                if(response.getBody().toString().contains("errors")){
                    reports.writeResult(null, "Fail", "The method 'httpPUTRequestWithoutBody()' was failed. " + response.getBody().prettyPrint());
                    Assert.fail("The method 'httpPUTRequestWithoutBody()' was failed. " + response.getBody().prettyPrint());
                    return null;
                }else{
                    Assert.assertTrue(response.getStatusCode() == 200, "Invalid responseCode for the 'httpPUTRequestWithoutBody()' method");
                    reports.writeResult(null, "Pass", "The method 'httpPUTRequestWithoutBody()' was successful. " + response.getBody().prettyPrint());
                    return response;
                }
            }else{
                reports.writeResult(null, "Info", "Executing negative scenario using 'httpPUTRequestWithoutBody()' method with EndPoint: "+endPoint+ "\n & the Response body is:" + response.getBody().prettyPrint());
                return response;
            }

        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'httpPUTRequestWithoutBody()' method. " + e.getMessage());
            return null;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'httpPUTRequestWithoutBody()' method. " + e.getMessage());
            return null;
        }finally{httpRequest = null; response = null;}
    }



    /********************************************************
     * Method Name      : convertJsonResponseIntoJSONObject()
     * Purpose          : to convert the JsonResponse which is of JsonArray, into java Map object
     * Author           : Gudi
     * Parameters       : responseType, strJsonResponse
     * ReturnType       : Object
     ********************************************************/
    public Object convertJsonResponseIntoJSONObject(String responseType, String strJsonResponse){
        JSONParser jsonP = new JSONParser();
        org.json.simple.JSONArray arrData = null;
        org.json.simple.JSONObject objData = null;
        Object object = null;
        try{
            Object obj = jsonP.parse(strJsonResponse);

            if(responseType.equalsIgnoreCase("JsonArray")){
                arrData = (org.json.simple.JSONArray) obj;
                object = arrData;
            }else if(responseType.equalsIgnoreCase("JsonObject")){
                objData = (org.json.simple.JSONObject) obj;
                object = objData;
            }else{
                reports.writeResult(null, "Fail","Invalid value for the Response type: '"+responseType+"'");
                return null;
            }
            return object;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'convertJsonResponseIntoJSONObject()' method. " + e.getMessage());
            return null;
        }
    }




    /********************************************************
     * Method Name      : compareJsonResponseWithDBResponse()
     * Purpose          : to validate the Json response against the DB values
     * Author           : Gudi
     * Parameters       : dbResponse, objJsonResp, dbColumnNames, jsonRespColumnNames
     * ReturnType       : boolean
     ********************************************************/
    public boolean compareJsonResponseWithDBResponse(LinkedHashMap dbResponse, org.json.simple.JSONObject objJsonResp, String dbColumnNames, String jsonRespColumnNames){
        String arrDBCols[];
        String arrJsonResponseCols[];
        String jsonRespData = null;
        String dbRespData = null;
        try{
            arrDBCols = dbColumnNames.split("#");
            arrJsonResponseCols = jsonRespColumnNames.split("#");
            for(int i=0; i<arrDBCols.length; i++){
                jsonRespData = String.valueOf(objJsonResp.get(arrJsonResponseCols[i]));
                dbRespData = String.valueOf(dbResponse.get(arrDBCols[i]));
                if(dbRespData.equalsIgnoreCase("null") && jsonRespData.equalsIgnoreCase("0") || dbRespData.equalsIgnoreCase("") && jsonRespData.equalsIgnoreCase("null")){
                    //As per Dev comments, When DB value is null & json value is Zero treat them as passed/matching.
                    //As per Dev comments, When DB value is "" & json value is null treat them as passed/matching.
                    reports.writeResult(null, "Pass", "The expected '"+dbRespData+"' & Actual '"+jsonRespData+"' values are matching.");
                }else{
                    Assert.assertTrue(jsonRespData.equalsIgnoreCase(dbRespData) || jsonRespData.contains(dbRespData) || dbRespData.contains(jsonRespData), "Mis-match in actual : '"+dbRespData+"' & expected '"+jsonRespData+"' values for the columnName: '"+arrJsonResponseCols[i]+"' : The Line Number: " + appInd.getCurrentLineNumber());
                }
            }
            reports.writeResult(null, "Pass", "The DB Response is matched with API Json Response");
            return true;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'compareJsonResponseWithDBResponse()' method. " + e.getMessage());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'compareJsonResponseWithDBResponse()' method. " + e.getMessage());
            return false;
        }finally {arrDBCols = null; arrJsonResponseCols = null; jsonRespData = null; dbRespData = null;}
    }



    /********************************************************
     * Method Name      : getBasicAuthenticationDetails()
     * Purpose          : to get the credentials (UserName/Password) for the Basic Authentication API
     * Author           : Gudi
     * Parameters       : NA
     * ReturnType       : Map<String, String>
     ********************************************************/
    public Map<String, String> getBasicAuthenticationDetails(){
        JSONArray dbResponse = null;
        String enrollmentID = null;
        String accessCode = null;
        long companyID = 0;
        Map<String, String> objMap = null;
        try{
            objMap = new HashMap<String, String>();
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_PayCRMQuery_To_Get_EnrollmentID", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            companyID = Long.parseLong(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("companyid").toString());
            enrollmentID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("enrollmentid").toString();

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_PayNetQuery_to_get_AccessCode", new Object[] {"companyID", companyID});
            randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            accessCode = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("AccessCode").toString();

            objMap.put("enrollmentID", enrollmentID);
            objMap.put("accessCode", accessCode);
            objMap.put("companyID", String.valueOf(companyID));
            reports.writeResult(null, "Pass", "Basic Auth Details: " + objMap);
            return objMap;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'getBasicAuthenticationDetails()' method. " + e.getMessage());
            return null;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'getBasicAuthenticationDetails()' method. " + e.getMessage());
            return null;
        }finally {dbResponse = null; enrollmentID = null; accessCode = null; objMap = null;}
    }


    /********************************************************
     * Method Name      : httpGETRequestBasicAuthentication()
     * Purpose          : to perform GET http method operation using Basic Authentication and to return the Response object
     * Author           : Gudi
     * Parameters       : endPoint, validationType
     * ReturnType       : String
     ********************************************************/
    public Response httpGETRequestBasicAuthentication(String endPoint, Map<String, String> basicAuth, String validationType){
        RequestSpecification httpRequest = null;
        Response response = null;
        JsonObject jsonObject = null;
        try{
            RestAssured.baseURI = appInd.getPropertyValueByKeyName("dataProvider_API_EndPoint");
            RestAssured.useRelaxedHTTPSValidation();
            httpRequest = given().auth().preemptive().basic(basicAuth.get("enrollmentID"), basicAuth.get("accessCode"));
            response = httpRequest
                    .when()
                    .get(endPoint).prettyPeek();

            if(validationType.equalsIgnoreCase("Valid")){
                reports.writeResult(null, "Info", "Executing Positive scenario using 'httpGETRequestBasicAuthentication()' method with EndPoint: " + endPoint);
                if(response.getBody().toString().contains("errors")){
                    reports.writeResult(null, "Fail", "The method 'httpGETRequestBasicAuthentication()' was failed. " + response.getBody().prettyPrint());
                    Assert.fail("The method 'httpGETRequest()' was failed. " + jsonObject.toString());
                    return null;
                }else{
                    Assert.assertTrue(response.getStatusCode() == 200, "Invalid responseCode for the 'httpGETRequestBasicAuthentication()' method");
                    reports.writeResult(null, "Pass", "The method 'httpGETRequestBasicAuthentication()' was successful. " + response.getBody().prettyPrint());
                    return response;
                }
            }else{
                reports.writeResult(null, "Info", "Executing Negative scenario using 'httpGETRequestBasicAuthentication()' method with EndPoint: "+endPoint+ "\n & the response Body:" + response.getBody().prettyPrint());
                return response;
            }

        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'httpGETRequestBasicAuthentication()' method. " + e.getMessage());
            return null;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'httpGETRequestBasicAuthentication()' method. " + e.getMessage());
            return null;
        }finally{httpRequest = null; response = null;}
    }



    /********************************************************
     * Method Name      : modifyJsonArrayNodeValues()
     * Purpose          : this method is used to modify the JsonArray node values by providing the node name
     * Author           : Gudi
     * Parameters       : jsonString, arrayNodeName, valueToUpdate
     * ReturnType       : String
     ********************************************************/
    public String modifyJsonArrayNodeValues(String jsonString, String arrayNodeName, Object valueToUpdate[]){
        JSONObject jsonObject = null;
        org.json.JSONArray arrayNode = null;
        JSONObject jsonModify = null;
        String modifiedJsonString = null;
        try{
            jsonObject = new JSONObject(jsonString);

            // Modify the JSONArray as needed
            arrayNode = jsonObject.getJSONArray(arrayNodeName);
            jsonModify = arrayNode.getJSONObject(0);
            for(int i=0; i<valueToUpdate.length; i++){
                jsonModify.put(valueToUpdate[i].toString(), valueToUpdate[i+1]);
                i++;
            }

            // Convert the JSON object back to a string
            modifiedJsonString = jsonObject.toString();
            return modifiedJsonString;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'modifyJsonArrayNodeValues()' method. " + e.getMessage());
            return null;
        }
        finally{jsonObject = null; arrayNode = null; jsonModify = null; modifiedJsonString = null;}
    }





    /********************************************************
     * Method Name      : modifyNestedJsonNodeValues()
     * Purpose          : this method is used to modify the Nested Json node values by providing the node name
     * Author           : Gudi
     * Parameters       : jsonString, nestedNodeName, valueToUpdate
     * ReturnType       : String
     ********************************************************/
    public String modifyNestedJsonNodeValues(String jsonString, String nestedNodeName, Object valueToUpdate[]){
        JSONObject jsonObject = null;
        JSONObject jsonNestedNode = null;
        String modifiedNestedJsonString = null;
        try{
            jsonObject = new JSONObject(jsonString);

            // Modify the nested Json as needed
            jsonNestedNode = new JSONObject(jsonObject.getString(nestedNodeName));
            for(int i=0; i<valueToUpdate.length; i++){
                jsonNestedNode.put(valueToUpdate[i].toString(), valueToUpdate[i+1]);
                i++;
            }
            jsonObject.put(nestedNodeName, jsonNestedNode);

            // Convert the JSON object back to a string
            modifiedNestedJsonString = jsonObject.toString();
            return modifiedNestedJsonString;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'modifyNestedJsonNodeValues()' method. " + e.getMessage());
            return null;
        }
        finally{jsonObject = null; jsonNestedNode = null; modifiedNestedJsonString = null;}
    }




    /********************************************************
     * Method Name      : modifyJsonObjectNodeValues()
     * Purpose          : this method is used to modify the JsonObject (Nested Json) node values by providing the node name
     * Author           : Gudi
     * Parameters       : jsonString, arrayNodeName, valueToUpdate
     * ReturnType       : String
     ********************************************************/
    public String modifyJsonObjectNodeValues(String jsonString, String arrayNodeName, Object valueToUpdate[]){
        JSONObject jsonObject = null;
        org.json.JSONObject jsonNode = null;
        String modifiedJsonString = null;
        try{
            jsonObject = new JSONObject(jsonString);

            // Modify the JSONObject/Nested Json Node as needed
            jsonNode = jsonObject.getJSONObject(arrayNodeName);
            for(int i=0; i<valueToUpdate.length; i++){
                jsonNode.put(valueToUpdate[i].toString(), valueToUpdate[i+1]);
                i++;
            }

            // Convert the JSON object back to a string
            modifiedJsonString = jsonObject.toString();
            return modifiedJsonString;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'modifyJsonObjectNodeValues()' method. " + e.getMessage());
            return null;
        }
        finally{jsonObject = null; jsonNode = null; modifiedJsonString = null;}
    }

    public String modifyJsonObjectNodeValues(Object nestedJsonNodevalueToUpdate[][], String jsonString){
        JSONObject jsonObject = null;
        org.json.JSONObject jsonNode = null;
        String modifiedJsonString = null;
        int columnCount = 0;
        try{
            jsonObject = new JSONObject(jsonString);

            // Modify the JSONObject/Nested Json Node as needed
            if(nestedJsonNodevalueToUpdate.length !=0){
                for(int i=0; i<nestedJsonNodevalueToUpdate.length; i++){
                    if(nestedJsonNodevalueToUpdate[i].length !=0){
                        jsonNode = jsonObject.getJSONObject(nestedJsonNodevalueToUpdate[i][0].toString());
                        if((nestedJsonNodevalueToUpdate[i].length-1)/2 == 0) columnCount = 1;
                        else columnCount = (nestedJsonNodevalueToUpdate[i].length-1)/2;
                        int x = 0;
                        for(int j=1; j<=columnCount; j++){
                            jsonNode.put(nestedJsonNodevalueToUpdate[i][j+x].toString(), nestedJsonNodevalueToUpdate[i][(j+1)+x]);
                            x++;
                        }
                    }
                }
            }

            // Convert the JSON object back to a string
            modifiedJsonString = jsonObject.toString();
            return modifiedJsonString;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'modifyJsonObjectNodeValues()' method. " + e.getMessage());
            return null;
        }
        finally{jsonObject = null; jsonNode = null; modifiedJsonString = null;}
    }




    /********************************************************
     * Method Name      : httpPUTMethodRequestWithBasicAuth()
     * Purpose          : to perform PUT http method operation using Basic Authentication and to return the Response object
     * Author           : Gudi
     * Parameters       : endPoint, basicAuth, requestInputFile, validationType
     * ReturnType       : String
     ********************************************************/
    public Response httpPUTMethodRequestWithBasicAuth(String endPoint, Map<String, String> basicAuth, String arrFieldsToModify, String strFieldsToModify, String requestInputFile, String updateJson, String validationType){
        RequestSpecification httpRequest = null;
        Response response = null;
        JsonObject jsonObject = null;
        String jsonFileContent = null;
        String updateJsonRequest = null;
        try{
            //Modify/Update the input Json
            jsonFileContent = apiUtility.getJsonContentFromFile(requestInputFile);

            jsonFileContent = updateJsonArrayNode(arrFieldsToModify, jsonFileContent);

            updateJsonRequest = updateJsonObjectElements(strFieldsToModify, jsonFileContent);

            reports.writeResult(null, "Info", "The modified Request body for the 'httpPUTMethodRequestWithBasicAuth()' POST request: " + updateJsonRequest);


            //Writing the updated json file back to the same file
            if(updateJson.equalsIgnoreCase("Yes"))
                apiUtility.writeJsonContentToFile(requestInputFile, updateJsonRequest);

            RestAssured.baseURI = appInd.getPropertyValueByKeyName("dataProvider_API_EndPoint");
            RestAssured.useRelaxedHTTPSValidation();
            httpRequest = given().auth().preemptive().basic(basicAuth.get("enrollmentID"), basicAuth.get("accessCode"));
            response = httpRequest
                    .header("Accept", ContentType.JSON.getAcceptHeader())
                    .contentType(ContentType.JSON)
                    .body(updateJsonRequest)
                    .put(endPoint)
                    .prettyPeek();
            appDep.threadSleep(2000);

            if(validationType.equalsIgnoreCase("Valid")){
                reports.writeResult(null, "Info", "Executing Positive scenario using 'httpPUTMethodRequestWithBasicAuth()' method with EndPoint: " + endPoint);
                if(response.getBody().toString().contains("errors")){
                    reports.writeResult(null, "Fail", "The method 'httpPUTMethodRequestWithBasicAuth()' was failed. " + response.getBody().prettyPrint());
                    Assert.fail("The method 'httpPUTMethodRequestWithBasicAuth()' was failed. " + jsonObject.toString());
                    return null;
                }else{
                    Assert.assertTrue(response.getStatusCode() == 200, "Invalid responseCode for the 'httpPUTMethodRequestWithBasicAuth()' method");
                    reports.writeResult(null, "Pass", "The method 'httpPUTMethodRequestWithBasicAuth()' was successful. " + response.getBody().prettyPrint());
                    return response;
                }
            }else{
                reports.writeResult(null, "Info", "Executing Negative scenario using 'httpPUTMethodRequestWithBasicAuth()' method with EndPoint: : "+endPoint+ "\n & the response body: " + response.getBody().prettyPrint());
                return response;
            }

        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'httpPUTMethodRequestWithBasicAuth()' method. " + e.getMessage());
            return null;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'httpPUTMethodRequestWithBasicAuth()' method. " + e.getMessage());
            return null;
        }finally{httpRequest = null; response = null; jsonObject = null; jsonFileContent = null; updateJsonRequest = null;}
    }




    /********************************************************
     * Method Name      : httpPOSTMethodRequestWithBasicAuth()
     * Purpose          : to perform POST http method operation using Basic Authentication and to return the Response object
     * Author           : Gudi
     * Parameters       : endPoint, basicAuth, requestInputFile, validationType
     * ReturnType       : String
     ********************************************************/
    public Response httpPOSTMethodRequestWithBasicAuth(String endPoint, Map<String, String> basicAuth, String arrFieldsToModify, String strFieldsToModify, String requestInputFile, String updateJson, String validationType){
        RequestSpecification httpRequest = null;
        Response response = null;
        JsonObject jsonObject = null;
        String jsonFileContent = null;
        String updateJsonRequest = null;
        try{
            //Modify/Update the input Json
            jsonFileContent = apiUtility.getJsonContentFromFile(requestInputFile);

            jsonFileContent = updateJsonArrayNode(arrFieldsToModify, jsonFileContent);

            updateJsonRequest = updateJsonObjectElements(strFieldsToModify, jsonFileContent);

            reports.writeResult(null, "Info", "The modified Request body for the 'httpPOSTMethodRequestWithBasicAuth()' POST request: " + updateJsonRequest);

            //Writing the updated json file back to the same file
            if(updateJson.equalsIgnoreCase("Yes"))
                apiUtility.writeJsonContentToFile(requestInputFile, updateJsonRequest);

            RestAssured.baseURI = appInd.getPropertyValueByKeyName("dataProvider_API_EndPoint");
            RestAssured.useRelaxedHTTPSValidation();
            httpRequest = given().auth().preemptive().basic(basicAuth.get("enrollmentID"), basicAuth.get("accessCode"));
            response = httpRequest
                    .header("Accept", ContentType.JSON.getAcceptHeader())
                    .contentType(ContentType.JSON)
                    .body(updateJsonRequest)
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
        }finally{httpRequest = null; response = null; jsonObject = null; jsonFileContent = null; updateJsonRequest = null;}
    }




    /********************************************************
     * Method Name      : httpPUTMethodRequestWithBearerAuth()
     * Purpose          : to perform PUT http method operation using Bearer Authentication and to return the Response object
     * Author           : Gudi
     * Parameters       : endPoint, bearerAuthToken, arrFieldsToModify, strFieldsToModify, requestInputFile, validationType
     * ReturnType       : String
     ********************************************************/
    public Response httpPUTMethodRequestWithBearerAuth(String endPoint, String bearerAuthToken, String arrFieldsToModify, String strFieldsToModify, String requestInputFile, String updateJson, String validationType){
        RequestSpecification httpRequest = null;
        Response response = null;
        String jsonFileContent = null;
        String updateJsonRequest = null;
        try{
            //Modify/Update the input Json
            jsonFileContent = apiUtility.getJsonContentFromFile(requestInputFile);

            jsonFileContent = updateJsonArrayNode(arrFieldsToModify, jsonFileContent);

            updateJsonRequest = updateJsonObjectElements(strFieldsToModify, jsonFileContent);


            reports.writeResult(null, "Info", "The modified Request body for the 'httpPUTMethodRequestWithBearerAuth()' POST request: " + updateJsonRequest);

            //Writing the updated json file back to the same file
            if(updateJson.equalsIgnoreCase("Yes"))
                apiUtility.writeJsonContentToFile(requestInputFile, updateJsonRequest);

            RestAssured.baseURI = appInd.getPropertyValueByKeyName("dataProvider_API_EndPoint");
            RestAssured.useRelaxedHTTPSValidation();
            httpRequest = given().header("Authorization", bearerAuthToken)
                    .header("Content-Type", "application/json");
            response = httpRequest
                    .when()
                    .body(updateJsonRequest)
                    .put(endPoint)
                    .prettyPeek();

            appDep.threadSleep(2000);
            if(validationType.equalsIgnoreCase("Valid")){
                reports.writeResult(null, "Info", "Executing Positive scenario using 'httpPUTMethodRequestWithBearerAuth()' method with EndPoint: " + endPoint);
                if(response.getBody().toString().contains("errors")){
                    reports.writeResult(null, "Fail", "The method 'httpPUTMethodRequestWithBearerAuth()' was failed. " + response.getBody().prettyPrint());
                    Assert.fail("The method 'httpPUTMethodRequestWithBearerAuth()' was failed. " + response.getBody().prettyPrint());
                    return null;
                }else{
                    Assert.assertTrue(response.getStatusCode() == 200, "Invalid responseCode for the 'managePaymentsPutRequest()' method");
                    reports.writeResult(null, "Pass", "The method 'httpPUTMethodRequestWithBearerAuth()' was successful. " + response.getBody().prettyPrint());
                    return response;
                }
            }else{
                reports.writeResult(null, "Info", "Executing Negative scenario using 'httpPUTMethodRequestWithBearerAuth()' method with endPoint: "+endPoint +" \n & the Response body is: " + response.getBody().prettyPrint());
                return response;
            }

        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'httpPUTMethodRequestWithBearerAuth()' method. " + e.getMessage());
            return null;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'httpPUTMethodRequestWithBearerAuth()' method. " + e.getMessage());
            return null;
        }finally{httpRequest = null; response = null; jsonFileContent = null;}
    }





    /********************************************************
     * Method Name      : httpPOSTMethodRequestWithBearerAuth()
     * Purpose          : to perform POST http method operation using Bearer Authentication and to return the Response object
     * Author           : Gudi
     * Parameters       : endPoint, bearerAuthToken, arrFieldsToModify, strFieldsToModify, requestInputFile, validationType
     * ReturnType       : String
     ********************************************************/
    public Response httpPOSTMethodRequestWithBearerAuth(String endPoint, String bearerAuthToken, String arrFieldsToModify, String strFieldsToModify, Object nestedJsonToModify[][], String requestInputFile, String updateJson, String validationType){
        RequestSpecification httpRequest = null;
        Response response = null;
        String jsonFileContent = null;
        String updateJsonRequest = null;
        try{
            //Modify/Update the input Json
            jsonFileContent = apiUtility.getJsonContentFromFile(requestInputFile);

            jsonFileContent = updateJsonArrayNode(arrFieldsToModify, jsonFileContent);

            jsonFileContent = updateJsonObjectElements(strFieldsToModify, jsonFileContent);

            //updateJsonRequest = updateNestedJsonObjectNode(nestedJsonToModify, jsonFileContent);
            updateJsonRequest = modifyJsonObjectNodeValues(nestedJsonToModify, jsonFileContent);


            reports.writeResult(null, "Info", "The modified Request body for the 'httpPOSTMethodRequestWithBearerAuth()' POST request: " + updateJsonRequest);

            //Writing the updated json file back to the same file
            if(updateJson.equalsIgnoreCase("Yes"))
                apiUtility.writeJsonContentToFile(requestInputFile, updateJsonRequest);

            RestAssured.baseURI = appInd.getPropertyValueByKeyName("dataProvider_API_EndPoint");
            RestAssured.useRelaxedHTTPSValidation();
            httpRequest = given().header("Authorization", bearerAuthToken)
                    .header("Content-Type", "application/json");
            response = httpRequest
                    .when()
                    .body(updateJsonRequest)
                    .post(endPoint)
                    .prettyPeek();
            appDep.threadSleep(2000);

            if(validationType.equalsIgnoreCase("Valid")){
                reports.writeResult(null, "Info", "Executing Positive scenario using 'httpPOSTMethodRequestWithBearerAuth()' method with EndPoint: " + endPoint);
                if(response.getBody().toString().contains("errors")){
                    reports.writeResult(null, "Fail", "The method 'httpPOSTMethodRequestWithBearerAuth()' was failed. " + response.getBody().prettyPrint());
                    Assert.fail("The method 'httpPOSTMethodRequestWithBearerAuth()' was failed. " + response.getBody().prettyPrint());
                    return null;
                }else{
                    int statusCode = response.getStatusCode();
                    Assert.assertTrue((statusCode == 200) || (statusCode == 201), "Invalid responseCode for the 'httpPOSTMethodRequestWithBearerAuth()' method");
                    reports.writeResult(null, "Pass", "The method 'httpPOSTMethodRequestWithBearerAuth()' was successful. " + response.getBody().prettyPrint());
                    return response;
                }
            }else{
                reports.writeResult(null, "Info", "Executing Negative scenario using 'httpPOSTMethodRequestWithBearerAuth()' method with EndPoint: " + endPoint + "\n & The Response body is: " + response.getBody().prettyPrint());
                return response;
            }

        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'httpPOSTMethodRequestWithBearerAuth()' method. " + e.getMessage());
            return null;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'httpPOSTMethodRequestWithBearerAuth()' method. " + e.getMessage());
            return null;
        }finally{httpRequest = null; response = null; jsonFileContent = null;}
    }





    /********************************************************
     * Method Name      : updateJsonObjectElements()
     * Purpose          : to update/modify the Json elements
     * Author           : Gudi
     * Parameters       : strFieldsToModify, jsonFileContent
     * ReturnType       : String
     ********************************************************/
    public String updateJsonObjectElements(String strFieldsToModify, String jsonFileContent){
        String arrValues[];
        JSONParser parser = null;
        Object obj = null;
        org.json.simple.JSONObject jsonObj = null;
        String updateJsonRequest = null;
        try{
            parser = new JSONParser();
            obj = parser.parse(jsonFileContent);
            jsonObj = (org.json.simple.JSONObject) obj;
            if(!strFieldsToModify.isEmpty()){
                arrValues = strFieldsToModify.split(",");
                for(int i=0; i<arrValues.length; i++){
                    String arr[] = arrValues[i].split("#", -1);
                    jsonObj.put(arr[0], arr[1]);
                }
                updateJsonRequest = jsonObj.toJSONString();
            }else{
                updateJsonRequest = jsonFileContent;
            }
            return updateJsonRequest;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'updateJsonObjectElements()' method. " + e.getMessage());
            return null;
        }finally {arrValues = null; parser = null; obj = null; jsonObj = null;}
    }




    /********************************************************
     * Method Name      : updateJsonArrayNode()
     * Purpose          : to update/modify the Json Array node based on the name
     * Author           : Gudi
     * Parameters       : arrFieldsToModify, jsonFileContent
     * ReturnType       : String
     ********************************************************/
    public String updateJsonArrayNode(String arrFieldsToModify, String jsonFileContent){
        String arrValues[];
        String updatedJson = null;
        try{
            if(!arrFieldsToModify.isEmpty()){
                arrValues = arrFieldsToModify.split(",", -1);
                for(int i=0; i<arrValues.length; i++){
                    Object objData = arrValues[i+1];
                    Object arrObjectData[] = objData.toString().split("#", -1);
                    updatedJson = apiUtility.modifyJsonArrayNodeValues(jsonFileContent, arrValues[i], arrObjectData);
                    jsonFileContent = updatedJson;
                    i++;
                }
            }
            return jsonFileContent;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'updateJsonArrayNode()' method. " + e.getMessage());
            return null;
        }finally {arrValues = null; updatedJson = null;}
    }




    /********************************************************
     * Method Name      : updateNestedJson()
     * Purpose          : to update/modify the Json Nested elements based on the name
     * Author           : Gudi
     * Parameters       : nestedFieldsToModify, jsonFileContent
     * ReturnType       : String
     ********************************************************/
    public String updateNestedJson(String nestedFieldsToModify, String jsonFileContent){
        String arrValues[];
        String updatedJson = null;
        try{
            if(!nestedFieldsToModify.isEmpty()){
                arrValues = nestedFieldsToModify.split(",", -1);
                for(int i=0; i<arrValues.length; i++){
                    Object objData = arrValues[i+1];
                    Object arrObjectData[] = objData.toString().split("#", -1);
                    updatedJson = apiUtility.modifyNestedJsonNodeValues(jsonFileContent, arrValues[i], arrObjectData);
                    jsonFileContent = updatedJson;
                    i++;
                }
            }
            return jsonFileContent;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'updateNestedJson()' method. " + e.getMessage());
            return null;
        }finally {arrValues = null; updatedJson = null;}
    }





    /********************************************************
     * Method Name      : compareJsonArrayElementsWithDBResponse()
     * Purpose          : to update/modify the Json Array node based on the name
     * Author           : Gudi
     * Parameters       : response, dbResponse, jsonArrayNodeName, jsonColumns, dbColumns
     * ReturnType       : boolean
     ********************************************************/
    public boolean compareJsonArrayElementsWithDBResponse(String response, JSONArray dbResponse, String jsonArrayNodeName, String jsonColumns, String dbColumns){
        org.json.JSONObject json = null;
        org.json.JSONArray jsonArray = null;
        String arrJsonResponseCols[];
        String arrDBColumns[];
        String dbRespData = null;
        String jsonRespData = null;
        org.json.JSONObject arrNodeObject = null;
        try{
            json = new org.json.JSONObject(response);
            jsonArray = json.getJSONArray(jsonArrayNodeName);
            arrJsonResponseCols = jsonColumns.split("#");
            arrDBColumns = dbColumns.split("#");
            for (int l = 0; l < jsonArray.length(); l++){
                arrNodeObject = jsonArray.getJSONObject(l);
                for(int i=0; i<arrJsonResponseCols.length; i++){
                    dbRespData = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(l)).get(arrDBColumns[i]));
                    jsonRespData = String.valueOf(arrNodeObject.get(arrJsonResponseCols[i]));
                    if(dbRespData.equalsIgnoreCase("null") && jsonRespData.equalsIgnoreCase("0")){
                        //As per Dev comments, When DB value is null & json value is Zero treat them as passed/matching.
                        reports.writeResult(null, "Pass", "The expected '"+dbRespData+"' & Actual '"+jsonRespData+"' values are matching.");
                    }else{
                        Assert.assertTrue(dbRespData.equalsIgnoreCase(jsonRespData) || jsonRespData.contains(dbRespData), "Mis-match in Actual: "+ dbRespData +" and Expected: "+ jsonRespData + " Values for the '"+arrJsonResponseCols[i]+"' column. \n Line Number: " + appInd.getCurrentLineNumber());
                    }
                }
            }
            reports.writeResult(null, "Pass", "The Link details from DB and Json response are matching");
            return true;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'compareJsonArrayElementsWithDBResponse()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'compareJsonArrayElementsWithDBResponse()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }finally {json = null; jsonArray = null; arrJsonResponseCols = null; arrDBColumns = null; dbRespData = null; jsonRespData = null; arrNodeObject = null;}
    }




    /********************************************************
     * Method Name      : compareJsonObjectElementsWithDBResponse()
     * Purpose          : to update/modify the Json Object node based on the name
     * Author           : Gudi
     * Parameters       : response, dbResponse, jsonObjectNodeName, jsonColumns, dbColumns
     * ReturnType       : boolean
     ********************************************************/
    public boolean compareJsonObjectElementsWithDBResponse(String response, JSONArray dbResponse, String jsonObjectNodeName, String jsonColumns, String dbColumns){
        org.json.JSONObject json = null;
        org.json.JSONObject jsonObject = null;
        String arrJSonColumns[];
        String arrDBColumns[];
        String dbRespData = null;
        String jsonRespData = null;
        try{
            json = new org.json.JSONObject(response);
            jsonObject = json.getJSONObject(jsonObjectNodeName);
            arrJSonColumns = jsonColumns.split("#");
            arrDBColumns = dbColumns.split("#");
            for(int i=0; i<arrJSonColumns.length; i++){
                dbRespData = String.valueOf(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get(arrDBColumns[i]));
                jsonRespData = String.valueOf(jsonObject.get(arrJSonColumns[i]));
                Assert.assertTrue(dbRespData.equalsIgnoreCase(jsonRespData), "Mis-match in Actual: "+ dbRespData +" and Expected: "+ jsonRespData + " Values for '"+arrJSonColumns[i]+"' column. \n Line Number: " + appInd.getCurrentLineNumber());
            }
            reports.writeResult(null, "Pass", "The Link details from DB and Json response are matching");
            return true;
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'compareJsonObjectElementsWithDBResponse()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'compareJsonObjectElementsWithDBResponse()' method. " + e.getMessage() +": Line Number: " + appInd.getCurrentLineNumber());
            return false;
        }finally {json = null; jsonObject = null; arrJSonColumns = null; arrDBColumns = null; dbRespData = null; jsonRespData = null;}
    }




    /********************************************************
     * Method Name      : httpPOSTForAttachFileWithBearerAuth()
     * Purpose          : to perform upload/Attach file API using POST method
     * Author           : Gudi
     * Parameters       : endPoint, bearerAuthToken, requestInputFile, validationType
     * ReturnType       : boolean
     ********************************************************/
    public Response httpPOSTForAttachFileWithBearerAuth(String endPoint, String bearerAuthToken, String requestInputFile, String validationType){
        RequestSpecification httpRequest = null;
        Response response = null;
        File fileToUpload = null;
        try{
            fileToUpload = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\inputJSON\\" + requestInputFile);
            RestAssured.baseURI = appInd.getPropertyValueByKeyName("dataProvider_API_EndPoint");
            RestAssured.useRelaxedHTTPSValidation();

            httpRequest = given().header("Authorization", bearerAuthToken);
            response = httpRequest
                    .multiPart("file", fileToUpload)
                    .when()
                    .post(endPoint)
                    .prettyPeek();

            if(validationType.equalsIgnoreCase("Valid")){
                reports.writeResult(null, "Info", "Executing Positive scenario using 'httpPOSTForAttachFileWithBearerAuth()' method with EndPoint: " + endPoint);
                if(response.getBody().toString().contains("errors")){
                    reports.writeResult(null, "Fail", "The method 'httpPOSTForAttachFileWithBearerAuth()' was failed. " + response.getBody().prettyPrint());
                    Assert.fail("The method 'httpPOSTForAttachFileWithBearerAuth()' was failed. " + response.getBody().prettyPrint());
                    return null;
                }else{
                    Assert.assertTrue(response.getStatusCode() == 200, "Invalid responseCode for the 'httpPOSTForAttachFileWithBearerAuth()' method");
                    reports.writeResult(null, "Pass", "The method 'httpPOSTForAttachFileWithBearerAuth()' was successful. " + response.getBody().prettyPrint());
                    return response;
                }
            }else{
                reports.writeResult(null, "Info", "Executing Negative scenario using 'httpPOSTForAttachFileWithBearerAuth()' method with Endpoint: " + endPoint + "\n & the Response body is: " + response.getBody().prettyPrint());
                return response;
            }
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'httpPOSTForAttachFileWithBearerAuth()' method. " + e.getMessage());
            return null;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'httpPOSTForAttachFileWithBearerAuth()' method. " + e.getMessage());
            return null;
        }finally{httpRequest = null; response = null; fileToUpload = null;}
    }





    /********************************************************
     * Method Name      : updateJsonNestedElements()
     * Purpose          : to convert the JsonResponse which is of JsonArray, into java Map object
     * Author           : Gudi
     * Parameters       : jsonFileContent, nestedJsonElementsToUpdate
     * ReturnType       : String
     ********************************************************/
    public String updateJsonNestedElements(String jsonFileContent, String nestedJsonElementsToUpdate){
        JSONObject jsonObject = null;
        JSONObject reqObj = null;
        try {
            jsonObject = new JSONObject(jsonFileContent);
            reqObj = new JSONObject(jsonObject.getString("reqObj"));

            // Update the value of the "ClientCompanyID" key
            reqObj.put("ClientCompanyID", "NEW_VALUE");

            // Put the updated "reqObj" back into the main JSON
            jsonObject.put("reqObj", reqObj);

            // Print the updated JSON
            return  jsonObject.toString();
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'updateJsonNestedElements()' method. " + e.getMessage());
            return null;
        }
    }
}

