package com.corcentric.common;

import com.corcentric.runner.CucumberTestRunner;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.asm.ex.NoSuchFieldException;
import net.minidev.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class APIDataProviderUtility extends CucumberTestRunner {
    /********************************************************
     * Method Name      : createAndGetAuthToken()
     * Purpose          : to generate the oAuth 2.0 authorization token
     * Author           : Gudi
     * Parameters       : String clientID, String clientSecrete, String audience
     * ReturnType       : String
     ********************************************************/
    public String createAndGetAuthToken(String clientID, String clientSecrete, String audience){
        Response response = null;
        try{
            response = given()
                    .header("content-type", "application/json")
                    .contentType("application/json")
                    .body("{\"client_id\":\""
                            + appInd.getPropertyValueByKeyName(clientID)+"\",\"client_secret\":\""
                            + appInd.getPropertyValueByKeyName(clientSecrete)+"\",\"audience\":\""
                            + appInd.getPropertyValueByKeyName(audience)+"\",\"grant_type\":\""
                            + appInd.getPropertyValueByKeyName("grant_type")+"\"}")
                    .when()
                    .post(appInd.getPropertyValueByKeyName("accessTokenURL"));

            JSONObject jsonObject = new JSONObject(response.getBody().asString());
            String accessToken = jsonObject.get("access_token").toString();
            String tokenType = jsonObject.get("token_type").toString();
            reports.writeResult(null, "Info", "Authorization Token for API DataProvider: " + authToken);
            return tokenType + " " + accessToken;
        }catch(JSONException e){
            reports.writeResult(null, "Exception", "Exception in 'createAndGetAuthToken()' method. " + e.getMessage());
            return null;
        }finally {response = null;}
    }




    /********************************************************
     * Method Name      : httpMethod_putResponse()
     * Purpose          : to generate Response from the put() API call
     * Author           : Gudi
     * Parameters       : itemName, param
     * ReturnType       : Response
     ********************************************************/
    public Response httpMethod_putResponse(String queryKey, Object objParams[]){
        RequestSpecification httpRequest = null;
        Response response = null;
        try{
            RestAssured.baseURI = appInd.getPropertyValueByKeyName("dataProvider_API_EndPoint");
            RestAssured.useRelaxedHTTPSValidation();
            httpRequest = given().header("Authorization", authToken)
                    .header("Content-Type", "application/json");


            response = httpRequest.body(generateDataProviderRequestJson(queryKey, objParams))
                    .put();
            reports.writeResult(null, "Info", "DB Response body: " + response.getBody().prettyPrint());
            return response;
        }catch(NoSuchFieldException e){
            reports.writeResult(null, "Exception", "Exception in 'httpMethod_putResponse()' method. " + e.getMessage());
            return null;
        }finally{httpRequest = null; response = null;}
    }




    /********************************************************
     * Method Name      : generateDataProviderRequestJson()
     * Purpose          : to generate Json response body for the API dataprovider API
     * Author           : Gudi
     * Parameters       : itemName, objParams[]
     * ReturnType       : String
     ********************************************************/
    public String generateDataProviderRequestJson(String queryKey, Object arrParams[]){
        org.json.JSONArray array = null;
        Map<String, Object> oMap = null;
        JSONObject data = null;
        try{
            array = new org.json.JSONArray();
            oMap = new HashMap<String, Object>();
            for(int i=0; i<arrParams.length; i++){
                oMap.put(arrParams[i].toString(), arrParams[i+1]);
                i++;
            }
            array.put(new JSONObject().put("name", queryKey).put("params", oMap));
            data = new JSONObject();
            data.put("queries", array);
            return data.toString();
        }catch(Exception e){
            reports.writeResult(null, "Exception", "Exception in 'generateDataProviderRequestJson()' method. " + e.getMessage());
            return null;
        }finally {array = null; oMap = null; data = null;}
    }



    /********************************************************
     * Method Name      : getAPIDataProviderResponse()
     * Purpose          : to execute the API DataProvider and to return the Response body
     * Author           : Gudi
     * Parameters       : queryKey, apiParam
     * ReturnType       : boolean
     ********************************************************/
    public JSONArray getAPIDataProviderResponse(String queryKey, Object arrParams[]){
        Response response = null;
        JSONArray responseDetails = null;
        try{
            response = httpMethod_putResponse(queryKey, arrParams);
            Assert.assertTrue(response.getStatusCode()==200, "Invalid response from the '"+queryKey+"' service" + response.getBody().prettyPrint());
            responseDetails = JsonPath.read(response.asString(), "$..['result'].['data']");
            Assert.assertTrue(((JSONArray) responseDetails.get(0)).size() > 0, "API service '"+queryKey+"' along with the Param '"+ Arrays.toString(arrParams) +"' was executed with zero records");
            return responseDetails;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'getAPIDataProviderResponse()' method. " + e);
            return null;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'getAPIDataProviderResponse()' method. " + e);
            Assert.fail("AssertionError in 'getAPIDataProviderResponse()' method. " + e);
            return null;
        }finally {response = null; responseDetails = null;}
    }
}
