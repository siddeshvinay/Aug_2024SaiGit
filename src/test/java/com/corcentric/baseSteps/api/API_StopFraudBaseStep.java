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

public class API_StopFraudBaseStep extends CucumberTestRunner {
    /********************************************************
     * Method Name      : validateStopFraudGETCompanyViewAPI()
     * Purpose          : to validate GET operations for Stopfraud GET Company View API
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateStopFraudGETCompanyViewAPI(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response getResponse = null;
        String strGetEndPoint = null;
        JSONArray dbResponse = null;
        String companyID = null;
        String requestedBy = null;
        String validationID = null;
        org.json.JSONObject json = null;
        org.json.JSONArray jsonArray = null;
        org.json.JSONObject arrNodeObject = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_StopFraud_GetCompanyViewInputParams", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            companyID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("CompanyID").toString();
            requestedBy = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("RequestedBy").toString();

            //CPAY-3020: API - Stop Fraud - GET Company View by CompanyID & Requested By email id
            strGetEndPoint = appInd.getPropertyValueByKeyName("stopFraudEndPoint") + appInd.getPropertyValueByKeyName("stopFraudCompanyView_GETByCompanyIDRequestBy");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", companyID).replace("%2", requestedBy), apiStopFraudAuthToken, "Valid");
            validationID = JsonPath.read(getResponse.asString(), "$.auditAreaInfos[:1].pvcsvipid").toString().replace("[", "").replace("]", "");

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_StopFraud_GetCompanyView_GETResponse", new Object[] {"companyID", companyID, "requestedBy", requestedBy});

            //Validate 'auditAreaInfos' details of Json Response against DB data
            Assert.assertTrue(apiUtility.compareJsonArrayElementsWithDBResponse(getResponse.asString(), dbResponse, "auditAreaInfos"
                    , "pvcsvipid#companyID#areaID#areaName#validationTypeID#validationTypeName#validationTypeDisplay#isPaf#statusID#statusName#detailID#historyID#validationDetailID#actionDate#auditEmail#auditDate"
                    , "PVCSVIPID#CompanyID#AreaID#AreaName#ValidationTypeID#ValidationTypeName#ValidationTypeDisplay#IsPaf#StatusID#StatusName#DetailID#HistoryID#ValidationDetailID#ActionDate#AuditEmail#AuditDate"));


            //CPAY-3020: API - Stop Fraud - GET Company View by CompanyID, Requested By email id & validationID (pvcsvipid)
            strGetEndPoint = appInd.getPropertyValueByKeyName("stopFraudEndPoint") + appInd.getPropertyValueByKeyName("stopFraudCompanyView_GETByCompanyIDRequestByValidationID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", companyID).replace("%2", requestedBy).replace("%3", validationID), apiStopFraudAuthToken, "Valid");
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_StopFraud_GetCompanyViewUsingValidationID_GETResponse", new Object[] {"companyID", companyID, "requestedBy", requestedBy, "validationID", validationID});

            //Validate 'companyAreaInfos' details of Json Response against DB data
            Assert.assertTrue(apiUtility.compareJsonArrayElementsWithDBResponse(getResponse.asString(), dbResponse, "companyAreaInfos"
                    , "detailID#companyID#embeddedUrl#companyName#address1#address2#city#zipPostal#phone#phoneExt#fax#duns#website#taxID#countryCode#stateCode"
                    , "CompanyID#CompanyID#EmbeddedURL#CompanyName#Address1#Address2#City#ZipPostal#Phone#PhoneExt#FAX#DUNS#Website#TaxID#CountryCode#StateCode"));

            //Validate 'paymentElectronicAreaInfos' details of Json Response against DB data
            Assert.assertTrue(apiUtility.compareJsonArrayElementsWithDBResponse(getResponse.asString(), dbResponse, "paymentElectronicAreaInfos"
                    , "pieid#accountName#bankName#accountNumber#accountNumberFull#localRoutingCode#remitDeliveryOptionID#remitDeliveryOptionDesc#remitFormatID#remitFormatDesc#remitDeliveryAddress#auditUserEmail"
                    , "PIEID#AccountName#BankName#AccountNumber#AccountNumberFull#LocalRoutingCode#RemitDeliveryOptionID#RemitDeliveryOptionDesc#RemitFormatID#RemitFormatDesc#RemitDeliveryAddress#AuditUserEmail"));


            //CPAY-3020: API - Stop Fraud - GET Company View by blank CompanyID, invalid Requested By email id & valid validationID (pvcsvipid)
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", "").replace("%2", "xyz"+requestedBy).replace("%3", validationID), apiStopFraudAuthToken, "Invalid");
            Assert.assertTrue(getResponse.getStatusCode() == 400, "GET Company View by blank CompanyID & Invalid Requested By email id and valid validationID : " + getResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.type").toString().equalsIgnoreCase("https://tools.ietf.org/html/rfc7231#section-6.5.1"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.title").toString().equalsIgnoreCase("One or more validation errors occurred."), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.status").toString().equalsIgnoreCase("400"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.errors").toString().contains("The value '' is invalid."), "Line Number: " + appInd.getCurrentLineNumber());


            //CPAY-3020: API - Stop Fraud - GET Company View by invalid CompanyID, valid Requested By email id & valid validationID (pvcsvipid)
            String invalidCompanyID = companyID+appInd.getDateTime("Sss");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", invalidCompanyID).replace("%2", requestedBy).replace("%3", validationID), apiStopFraudAuthToken, "Invalid");
            Assert.assertTrue(getResponse.getStatusCode() == 400, "GET Company View by blank CompanyID & Invalid Requested By email id and valid validationID : " + getResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.type").toString().equalsIgnoreCase("https://tools.ietf.org/html/rfc7231#section-6.5.1"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.title").toString().equalsIgnoreCase("One or more validation errors occurred."), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.status").toString().equalsIgnoreCase("400"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.errors").toString().contains("The value '"+invalidCompanyID+"' is not valid for CompanyId."), "Line Number: " + appInd.getCurrentLineNumber());


            //CPAY-3020: API - Stop Fraud - GET Company View by valid CompanyID, valid Requested By email id & invalid validationID (pvcsvipid)
            String invalidValidationID = validationID+appInd.getDateTime("Sss");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", companyID).replace("%2", requestedBy).replace("%3", invalidValidationID), apiStopFraudAuthToken, "Invalid");
            Assert.assertTrue(getResponse.getStatusCode() == 400, "GET Company View by blank CompanyID & Invalid Requested By email id and valid validationID : " + getResponse.getBody().prettyPrint());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.type").toString().equalsIgnoreCase("https://tools.ietf.org/html/rfc7231#section-6.5.1"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.title").toString().equalsIgnoreCase("One or more validation errors occurred."), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.status").toString().equalsIgnoreCase("400"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.errors").toString().contains("The value '"+invalidValidationID+"' is not valid for ValidationId."), "Line Number: " + appInd.getCurrentLineNumber());
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in ' validateStopFraudGETCompanyViewAPI()' method. " + e.getMessage());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateStopFraudGETCompanyViewAPI()' method. " + e.getMessage());
            return false;
        }finally {inputData = null; getResponse = null; strGetEndPoint = null; dbResponse = null; companyID = null; requestedBy = null; validationID = null;}
    }




    /********************************************************
     * Method Name      : validateStopFraudPollAndPollByDateGETAPI()
     * Purpose          : to validate GET operations for Stopfraud Poll and Poll By Date GET API
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateStopFraudPollAndPollByDateGETAPI(DataTable dataTable){
        Response getResponse = null;
        String strGetEndPoint = null;
        JSONArray dbResponse = null;
        org.json.simple.JSONArray objJsonGETResponseByPollDate = null;
        ResponseBody responseBody = null;
        try{
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_StopFraud_PollByDate_GETInput", new Object[]{});
            String startDate = (((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("Start Date").toString().split("T"))[0];
            String endDate = (((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("End Date").toString().split("T"))[0];

            //CPAY-2878: API- Stop Fraud- get - Poll and poll by date
            strGetEndPoint = appInd.getPropertyValueByKeyName("stopFraudEndPoint") + appInd.getPropertyValueByKeyName("stopFraudPollByDate_GETPollByDate");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", startDate).replace("%2", endDate), apiStopFraudAuthToken, "Valid");

            objJsonGETResponseByPollDate = (org.json.simple.JSONArray) apiUtility.convertJsonResponseIntoJSONObject("JsonArray", getResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_StopFraud_PollByDate_GETResponse", new Object[] {"startDate", startDate, "endDate", endDate});

            for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i), (JSONObject) objJsonGETResponseByPollDate.get(i)
                        , "PVCSVIPID#ValidationDetailID#CompanyID#CompanyName#AreaID#AreaName#ValidationTypeID#ValidationTypeName#IsPAF#StatusID#StatusName#DetailID#ActionDate"
                        , "validationId#validationDetailId#companyId#companyName#areaId#areaName#validationTypeId#validationTypeName#isPAF#statusId#statusName#detailId#actionDate"));
            }

            //CPAY-2878: API- Stop Fraud- get - Poll and poll by date: Invalid start and end date
            strGetEndPoint = appInd.getPropertyValueByKeyName("stopFraudEndPoint") + appInd.getPropertyValueByKeyName("stopFraudPollByDate_GETPollByDate");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", endDate).replace("%2", startDate), apiStopFraudAuthToken, "Invalid");
            appInd.compareValues(null, getResponse.statusCode(), 400);
            appInd.compareValues(null, JsonPath.read(getResponse.asString(), "$.errors.EndDate[0]"), "End date should be always greater than Start Date");

            //CPAY-2878: API- Stop Fraud- get - Poll
            strGetEndPoint = appInd.getPropertyValueByKeyName("stopFraudEndPoint") + appInd.getPropertyValueByKeyName("stopFraudPoll_GETPoll");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint, apiStopFraudAuthToken, "Valid");
            appInd.compareValues(null, getResponse.statusCode(), 200);
            // Some time it comes with empty array and some time it throws result. When result appears, below assertions does not work.
//            responseBody = getResponse.getBody();
//            Assert.assertTrue(responseBody.asString().replace(" ","").replace("\n", "").equals("[]"), "Failed to return the empty array when invalid date range was given for the StopFraud Poll API. The Line Number: " + appInd.getCurrentLineNumber());

            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateStopFraudPollAndPollByDateGETAPI()' method. " + e.getMessage());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateStopFraudPollAndPollByDateGETAPI()' method. " + e.getMessage());
            return false;
        }finally {getResponse = null; strGetEndPoint = null; dbResponse = null; objJsonGETResponseByPollDate = null; responseBody = null;}
    }




    /********************************************************
     * Method Name      : validateStopFraudAttachFileByUsingPOSTMethod()
     * Purpose          : to validate the Stop Fraud attach file API using POST method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateStopFraudAttachFileByUsingPOSTMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response postResponse = null;
        String strPostEndPoint = null;
        JSONArray dbResponse = null;
        String validationDetailID = null;
        String filedID = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_AttachFile_POSTInputParam", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            validationDetailID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("ValidationDetailID").toString();

            //CPAY-2055: API- Post Request - attach-file
            strPostEndPoint = appInd.getPropertyValueByKeyName("stopFraudEndPoint") + appInd.getPropertyValueByKeyName("stopFraudAttachFile_POSTByValidationDetailsID");
            postResponse = apiUtility.httpPOSTForAttachFileWithBearerAuth(strPostEndPoint.replace("%1", inputData.get(0).get("fileToAttach")).replace("%2", validationDetailID), apiStopFraudAuthToken, inputData.get(0).get("fileToAttach"), "Valid");
            Assert.assertTrue(postResponse.getStatusCode() == 200, "Stop Fraud attach-file API: " + postResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(postResponse.asString(), "$.success").toString().equalsIgnoreCase("true"), ": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(postResponse.asString(), "$.message").toString().equalsIgnoreCase("File successfully uploaded and attached"), ": Line Number: "+appInd.getCurrentLineNumber());
            filedID = JsonPath.read(postResponse.asString(), "$.fileId").toString();
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_StopFraud_AttachFilePOSTMethodResponse", new Object[] {"fieldID", filedID, "validationDetailID", validationDetailID});
            Assert.assertTrue(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("ID").toString().equalsIgnoreCase(filedID), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("ValidationDetailID").toString().equalsIgnoreCase(validationDetailID), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("FileName").toString().equalsIgnoreCase(inputData.get(0).get("fileToAttach")), "Line Number: " + appInd.getCurrentLineNumber());



            //CPAY-2055: API- Post Request - attach-file with the same file which is previously attached
            postResponse = apiUtility.httpPOSTForAttachFileWithBearerAuth(strPostEndPoint.replace("%1", inputData.get(0).get("fileToAttach")).replace("%2", validationDetailID), apiStopFraudAuthToken, inputData.get(0).get("fileToAttach"), "Valid");
            Assert.assertTrue(postResponse.getStatusCode() == 200, "Stop Fraud attach-file API: " + postResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(postResponse.asString(), "$.success").toString().equalsIgnoreCase("true"), ": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(postResponse.asString(), "$.message").toString().equalsIgnoreCase("File successfully uploaded and attached"), ": Line Number: "+appInd.getCurrentLineNumber());
            filedID = JsonPath.read(postResponse.asString(), "$.fileId").toString();
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_StopFraud_AttachFilePOSTMethodResponse", new Object[] {"fieldID", filedID, "validationDetailID", validationDetailID});
            Assert.assertTrue(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("ID").toString().equalsIgnoreCase(filedID), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("ValidationDetailID").toString().equalsIgnoreCase(validationDetailID), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("FileName").toString().equalsIgnoreCase(inputData.get(0).get("fileToAttach")), "Line Number: " + appInd.getCurrentLineNumber());


            //CPAY-2055: API- Post Request - attach-file with Invalid validationDetailID
            postResponse = apiUtility.httpPOSTForAttachFileWithBearerAuth(strPostEndPoint.replace("%1", inputData.get(0).get("fileToAttach")).replace("%2", "01"+validationDetailID+"1111"), apiStopFraudAuthToken, inputData.get(0).get("fileToAttach"), "Invalid");
            Assert.assertTrue(postResponse.getStatusCode() == 404, "Stop Fraud attach-file API with invalid validationDetailID: " + postResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(postResponse.asString(), "$.fileId") == null, ": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(postResponse.asString(), "$.success").toString().equalsIgnoreCase("false"), ": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(postResponse.asString(), "$.message").toString().equalsIgnoreCase("Validation record not found"), ": Line Number: "+appInd.getCurrentLineNumber());


            //CPAY-2055: API- Post Request - attach-file with Blank validationDetailID
            postResponse = apiUtility.httpPOSTForAttachFileWithBearerAuth(strPostEndPoint.replace("%1", inputData.get(0).get("fileToAttach")).replace("%2", ""), apiStopFraudAuthToken, inputData.get(0).get("fileToAttach"), "Invalid");
            Assert.assertTrue(postResponse.getStatusCode() == 400, "Stop Fraud attach-file API with blank validationDetailID: " + postResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(postResponse.asString(), "$.type").toString().equalsIgnoreCase("https://tools.ietf.org/html/rfc7231#section-6.5.1"), ": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(postResponse.asString(), "$.title").toString().equalsIgnoreCase("One or more validation errors occurred."), ": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(postResponse.asString(), "$.status").toString().equalsIgnoreCase("400"), ": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(postResponse.asString(), "$.errors").toString().contains("Either validationId or validationDetailId must be provided"), ": Line Number: "+appInd.getCurrentLineNumber());

            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateStopFraudAttachFileByUsingPOSTMethod()' method. " + e.getMessage());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateStopFraudAttachFileByUsingPOSTMethod()' method. " + e.getMessage());
            return false;
        }finally{inputData = null; postResponse = null; strPostEndPoint = null; dbResponse = null; validationDetailID = null; filedID = null;}
    }




    /********************************************************
     * Method Name      : validateStopFraudGetDetailsBySectionAPIByUsingGETMethod()
     * Purpose          : to validate the Stop Fraud get Details by section API using GET method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateStopFraudGetDetailsBySectionAPIByUsingGETMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response getResponse = null;
        String strGetEndPoint = null;
        JSONArray dbResponse = null;
        String areaID = null;
        String detailID = null;
        org.json.simple.JSONArray objJsonGETResponse = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_StopFraud_DetailsBySection_GETInput", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            areaID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("AreaID").toString();
            detailID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("DetailID").toString();

            //CPAY-2067: API- Get Request - details by section
            strGetEndPoint = appInd.getPropertyValueByKeyName("stopFraudEndPoint") + appInd.getPropertyValueByKeyName("stopFraudDetailsBySection_GETByAreaAndDetailID");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", areaID).replace("%2", detailID), apiStopFraudAuthToken, "Valid");
            Assert.assertTrue(getResponse.getStatusCode() == 200, "Stop Fraud details By Section API: " + getResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());

            objJsonGETResponse = (org.json.simple.JSONArray) apiUtility.convertJsonResponseIntoJSONObject("JsonArray", getResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_StopFraud_DetailsBySection_GETResponse", new Object[] {"areaID", areaID, "detailID", detailID});

            for(int i=0; i<((JSONArray) dbResponse.get(0)).size(); i++) {
                Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(i), (JSONObject) objJsonGETResponse.get(i)
                        , "Notes#ValidationDetailID#validationId#validationStatusId#StatusName#ValidationTypeID#HoldType"
                        , "notes#validationDetailId#validationId#validationStatusId#validationStatusName#validationTypeId#holdType"));
            }

            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateStopFraudGetDetailsBySectionAPIByUsingGETMethod()' method. " + e.getMessage());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateStopFraudGetDetailsBySectionAPIByUsingGETMethod()' method. " + e.getMessage());
            return false;
        }finally {inputData = null; getResponse = null; strGetEndPoint = null; dbResponse = null; areaID = null; detailID = null; objJsonGETResponse = null;}
    }




    /********************************************************
     * Method Name      : validateStopFraudAutoValidateAndOFACAutoValidateAPIByUsingPUTMethod()
     * Purpose          : to validate the Stop Fraud auto validate and OFAC Auto validate API using PUT method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateStopFraudAutoValidateAndOFACAutoValidateAPIByUsingPUTMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response putResponse = null;
        String strPutEndPoint = null;
        JSONArray dbResponse = null;
        String companyID = null;
        String areaID = null;
        String detailID = null;
        String strFieldsToUpdate = null;
        String arrFieldsToUpdate = null;
        String userName = null;
        String validationID = null;
        org.json.simple.JSONArray objJsonPUTResponse = null;
        String validated = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_StopFraud_AutoOFACValidate_PUTInput", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            companyID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("CompanyID").toString();
            areaID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("AreaID").toString();
            detailID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("DetailID").toString();

            strPutEndPoint = appInd.getPropertyValueByKeyName("stopFraudEndPoint") + appInd.getPropertyValueByKeyName("stopFraudOFACAutoValidate_PUTMethod");
            arrFieldsToUpdate = "sections,areaId#"+areaID+"#detailId#"+detailID;
            strFieldsToUpdate = "companyId#"+companyID+",username#ggudi,notes#AutoNotes"+appInd.getDateTime("hhmmss");
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint, apiStopFraudAuthToken, arrFieldsToUpdate, strFieldsToUpdate, inputData.get(0).get("InputFileForPUT"), "No", "Valid");
            Assert.assertTrue(putResponse.getStatusCode() == 200, "Stop Fraud OFAC Auto Validate API: " + putResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(), "[0].areaId").toString().equalsIgnoreCase(areaID), ": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(), "[0].detailId").toString().equalsIgnoreCase(detailID), ": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(), "[0].validated").toString().equalsIgnoreCase("true"), ": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(), "[0].message").toString().equalsIgnoreCase("Success"), ": Line Number: "+appInd.getCurrentLineNumber());

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_StopFraud_OFACValidation_PUTResponse", new Object[] {"detailID", detailID, "companyID", companyID});
            randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            Assert.assertTrue(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("AreaID").toString().equalsIgnoreCase(areaID));
            Assert.assertTrue(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("DetailID").toString().equalsIgnoreCase(detailID));
            Assert.assertTrue(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("Status").toString().equalsIgnoreCase("1"));



            //CPAY-1716: API- Put Request - auto-validate
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_AutoValidate_QueryToOpenTinValidation", new Object[] {});
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_StopFraud_AutoValidate_PUTInput", new Object[] {});
            randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            companyID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("CompanyID").toString();
            userName = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("UserName").toString();
            validationID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("ValidationTypeID").toString();


            strPutEndPoint = appInd.getPropertyValueByKeyName("stopFraudEndPoint") + appInd.getPropertyValueByKeyName("stopFraudAutoValidate_PUTMethod");
            strFieldsToUpdate = "companyID#"+companyID+",validationTypeID#"+validationID+",username#"+userName;
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint, apiStopFraudAuthToken, "", strFieldsToUpdate, inputData.get(0).get("InputFileForPut_AutoValidate"), "No", "Valid");
            Assert.assertTrue(putResponse.getStatusCode() == 200, "Stop Fraud Auto Validate API: " + putResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_StopFraud_AutoValidate_PUTResponse", new Object[] {"companyID", companyID});
            if(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("validationDetailId")!=null){
                Assert.assertTrue(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("validationDetailId").toString().equalsIgnoreCase(JsonPath.read(putResponse.asString(), "$.validationDetailId").toString()), "Line Number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.validated").toString().equalsIgnoreCase("true"), "Line Number: " + appInd.getCurrentLineNumber());
            }else{
                reports.writeResult(null, "Fail", "The Auto-Validate PUT request API failed. " + putResponse.getBody().prettyPrint());
                Assert.fail("Put Request - auto-validate failed. Line Number: " + appInd.getCurrentLineNumber());
            }



            //This is an invalid step as this can't be executed as per business
            //CPAY-1716: API- Put Request - auto-validate. API PUT when trying to validate already validated TIN for a company
            /*strFieldsToUpdate = "companyID#"+companyID+",validationTypeID#"+validationID+",username#"+userName;
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint, apiStopFraudAuthToken, "", strFieldsToUpdate, inputData.get(0).get("InputFileForPut_AutoValidate"), "No", "Valid");
            Assert.assertTrue(putResponse.getStatusCode() == 200, "Stop Fraud Auto Validate API: " + putResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_StopFraud_AutoValidate_PUTResponse", new Object[] {"companyID", companyID});
            Assert.assertTrue(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("validationDetailId").toString().equalsIgnoreCase(JsonPath.read(putResponse.asString(), "$.validationDetailId").toString()), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.validated").toString().equalsIgnoreCase("true"), "Line Number: " + appInd.getCurrentLineNumber());*/



            //CPAY-1716: API- Put Request - auto-validate with invalid request body
            strFieldsToUpdate = "companyID#"+companyID+appInd.getDateTime("S")+",validationTypeID#"+validationID+",username#"+userName;
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint, apiStopFraudAuthToken, "", strFieldsToUpdate, inputData.get(0).get("InputFileForPut_AutoValidate"), "No", "Invalid");
            Assert.assertTrue(putResponse.getStatusCode() == 400, "Stop Fraud Auto Validate API: " + putResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.type").toString().equalsIgnoreCase("https://tools.ietf.org/html/rfc7231#section-6.5.1"));
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.title").toString().equalsIgnoreCase("One or more validation errors occurred."));
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.status").toString().equalsIgnoreCase("400"));
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.errors").toString().contains("The requestParam field is required."));
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.errors").toString().contains("The JSON value could not be converted to System.Int32. Path: $.companyID | LineNumber: 0 | BytePositionInLine: 27."));

            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateStopFraudAutoValidateAndOFACAutoValidateAPIByUsingPUTMethod()' method. " + e.getMessage());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateStopFraudAutoValidateAndOFACAutoValidateAPIByUsingPUTMethod()' method. " + e.getMessage());
            return false;
        }finally {inputData = null; putResponse = null; strPutEndPoint = null; dbResponse = null; companyID = null; areaID = null; detailID = null; strFieldsToUpdate = null; arrFieldsToUpdate = null; userName = null; validationID = null; objJsonPUTResponse = null;}
    }




    /********************************************************
     * Method Name      : validateStopFraudManualTINValidationAPIByUsingPUTMethod()
     * Purpose          : to validate the Stop Fraud Manual TIN Validation API using PUT method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateStopFraudManualTINValidationAPIByUsingPUTMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response putResponse = null;
        String strPutEndPoint = null;
        JSONArray dbResponse = null;
        String companyID = null;
        String strFieldsToUpdate = null;
        String timeStamp = null;
        try{
            timeStamp = appInd.getDateTime("hhmmss");
            inputData = dataTable.asMaps(String.class, String.class);

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_StopFraud_AutoOFACValidate_PUTInput", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            companyID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("CompanyID").toString();

            //CPAY-1674: API- Put Request - validate-tin-manual
            strPutEndPoint = appInd.getPropertyValueByKeyName("stopFraudEndPoint") + appInd.getPropertyValueByKeyName("stopFraudManualTinValidate_PUTMethod");
            strFieldsToUpdate = "companyId#"+companyID+",notes#Manual TIN Notes"+timeStamp+",username#"+appInd.getPropertyValueByKeyName("qaUserName");
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint, apiStopFraudAuthToken, "", strFieldsToUpdate, inputData.get(0).get("InputFileForPUT"), "No", "Valid");
            Assert.assertTrue(putResponse.getStatusCode() == 200, "Manual TIN Validate API: " + putResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.validated").toString().equalsIgnoreCase("true"));

            //CPAY-1674: API- Put Request - validate-tin-manual - validate already validated TIN for a company
            strFieldsToUpdate = "companyId#"+companyID+",notes#Manual TIN Notes"+timeStamp+",username#"+appInd.getPropertyValueByKeyName("qaUserName");
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint, apiStopFraudAuthToken, "", strFieldsToUpdate, inputData.get(0).get("InputFileForPUT"), "No", "Valid");
            Assert.assertTrue(putResponse.getStatusCode() == 200, "Revalidate already validated Manual TIN Validate API: " + putResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.validated").toString().equalsIgnoreCase("true"));

            //CPAY-1674: API- Put Request - validate-tin-manual - with invalid companyID in the request body
            strFieldsToUpdate = "companyId#,notes#Manual TIN Notes"+timeStamp+",username#"+appInd.getPropertyValueByKeyName("qaUserName");
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint, apiStopFraudAuthToken, "", strFieldsToUpdate, inputData.get(0).get("InputFileForPUT"), "No", "Invalid");
            Assert.assertTrue(putResponse.getStatusCode() == 400, "Manual TIN Validate API with invalid CompanyID : " + putResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.type").toString().equalsIgnoreCase("https://tools.ietf.org/html/rfc7231#section-6.5.1"), "Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.title").toString().equalsIgnoreCase("One or more validation errors occurred."), "Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.status").toString().equalsIgnoreCase("400"), "Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.errors").toString().contains("The requestParam field is required."), "Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.errors").toString().contains("The JSON value could not be converted to System.Int32. Path: $.companyId | LineNumber: 0 | BytePositionInLine: 15."), "Line Number: "+appInd.getCurrentLineNumber());


            //CPAY-1674: API- Put Request - validate-tin-manual - with invalid companyID in the request body which doesnot exist
            String companyIDNotExist = appInd.generateTaxID("Shhmmss");
            strFieldsToUpdate = "companyId#"+companyIDNotExist+",notes#Manual TIN Notes"+timeStamp+",username#"+appInd.getPropertyValueByKeyName("qaUserName");
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint, apiStopFraudAuthToken, "", strFieldsToUpdate, inputData.get(0).get("InputFileForPUT"), "No", "Invalid");
            Assert.assertTrue(putResponse.getStatusCode() == 422, "Manual TIN Validate API with invalid CompanyID : " + putResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.validated").toString().equalsIgnoreCase("false"), "Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.message").toString().equalsIgnoreCase("Company details not found"), "Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.messageDetail") == null, "Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.validationDetailId") == null, "Line Number: "+appInd.getCurrentLineNumber());

            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateStopFraudManualTINValidationAPIByUsingPUTMethod()' method. " + e.getMessage());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateStopFraudManualTINValidationAPIByUsingPUTMethod()' method. " + e.getMessage());
            return false;
        }finally {inputData = null; putResponse = null; strPutEndPoint = null; dbResponse = null; companyID = null; strFieldsToUpdate = null; timeStamp = null;}
    }




    /********************************************************
     * Method Name      : validateStopFraudAddressAutoValidateAPIByUsingPUTMethod()
     * Purpose          : to validate the Stop Fraud Address Auto Validation API using PUT method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateStopFraudAddressAutoValidateAPIByUsingPUTMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response putResponse = null;
        String strPutEndPoint = null;
        JSONArray dbResponse = null;
        String companyID = null;
        String areaID = null;
        String detailID = null;
        String strFieldsToUpdate = null;
        String arrFieldsToUpdate = null;
        String pvcsID = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagedPayment_AddressAutoValidate_PUTInput", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            companyID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("CompanyID").toString();
            areaID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("AreaID").toString();
            detailID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("DetailId").toString();
            pvcsID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("pvcsid").toString();

            //CPAY-3023: API - Stop Fraud - Put - Address - validate-auto
            strPutEndPoint = appInd.getPropertyValueByKeyName("stopFraudEndPoint") + appInd.getPropertyValueByKeyName("stopFraudAddressAutoValidate_PUTMethod");
            arrFieldsToUpdate = "sections,areaId#"+areaID+"#detailId#"+detailID;
            strFieldsToUpdate = "companyId#"+companyID+",username#ggudi,notes#AutoNotes"+appInd.getDateTime("hhmmss");
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint, apiStopFraudAuthToken, arrFieldsToUpdate, strFieldsToUpdate, inputData.get(0).get("InputFileForPUT"), "No", "Valid");
            Assert.assertTrue(putResponse.getStatusCode() == 200, "Stop Fraud Address Auto Validate API: " + putResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"[0].validated").toString().equalsIgnoreCase("true"), "Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"[0].areaId").toString().equalsIgnoreCase(areaID), "Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"[0].detailId").toString().equalsIgnoreCase(detailID), "Line Number: "+appInd.getCurrentLineNumber());

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagedPayment_AddressAutoValidate_PUTResponse", new Object[] {"pvcsID", pvcsID});
            Assert.assertTrue(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("ValidationStatus").toString().equalsIgnoreCase("Validated"));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateStopFraudAddressAutoValidateAPIByUsingPUTMethod()' method. " + e.getMessage());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateStopFraudAddressAutoValidateAPIByUsingPUTMethod()' method. " + e.getMessage());
            return false;
        }finally {inputData = null; putResponse = null; strPutEndPoint = null; dbResponse = null; companyID = null; areaID = null; detailID = null; strFieldsToUpdate = null; arrFieldsToUpdate = null; pvcsID = null;}
    }





    /********************************************************
     * Method Name      : validateStopFraudPaymentInfoHoldAndReleaseAPIByUsingPUTMethod()
     * Purpose          : to validate the Stop Fraud Payment Info Release API using PUT method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public  boolean validateStopFraudPaymentInfoHoldAndReleaseAPIByUsingPUTMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response putResponse = null;
        String strPutEndPoint = null;
        JSONArray dbResponse = null;
        String companyID = null;
        String detailID = null;
        String strFieldsToUpdate = null;
        String arrFieldsToUpdate = null;
        String areaID = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);

            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_ManagedPayment_HoldAndRelease_PUTInput", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            companyID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("CompanyID").toString();
            detailID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("DetailID").toString();
            areaID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("AreaID").toString();


            //CPAY-2048: API- Put Request - payment-info-hold
            strPutEndPoint = appInd.getPropertyValueByKeyName("stopFraudEndPoint") + appInd.getPropertyValueByKeyName("stopFraudPaymentInfoHold_PUTMethod");
            arrFieldsToUpdate = "payments,companyId#"+companyID+"#detailId#"+detailID;
            strFieldsToUpdate = "username#"+appInd.getPropertyValueByKeyName("qaUserName")+",notes#AutoNotes"+appInd.getDateTime("hhmmss");
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint, apiStopFraudAuthToken, arrFieldsToUpdate, strFieldsToUpdate, inputData.get(0).get("InputFileForPUT"), "No", "Valid");
            Assert.assertTrue(putResponse.getStatusCode() == 200, "Stop Fraud payment-info-hold API: " + putResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"[0].areaId").toString().equalsIgnoreCase(areaID), "Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"[0].detailId").toString().equalsIgnoreCase(detailID), "Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"[0].success").toString().equalsIgnoreCase("true"), "Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"[0].message").toString().equalsIgnoreCase("Success - Account marked as unabletovalidated"), "Line Number: "+appInd.getCurrentLineNumber());


            //CPAY-2050: API- Put Request - payment-info-release
            strPutEndPoint = appInd.getPropertyValueByKeyName("stopFraudEndPoint") + appInd.getPropertyValueByKeyName("stopFraudPaymentInfoRelease_PUTMethod");
            arrFieldsToUpdate = "payments,companyId#"+companyID+"#detailId#"+detailID;
            strFieldsToUpdate = "username#"+appInd.getPropertyValueByKeyName("qaUserName")+",notes#AutoNotes"+appInd.getDateTime("hhmmss");
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint, apiStopFraudAuthToken, arrFieldsToUpdate, strFieldsToUpdate, inputData.get(0).get("InputFileForPUT"), "No", "Valid");
            Assert.assertTrue(putResponse.getStatusCode() == 200, "Stop Fraud payment-info-release API: " + putResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"[0].areaId").toString().replace("[", "").replace("]", "").equalsIgnoreCase(areaID), "Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"[0].detailId").toString().replace("[", "").replace("]", "").equalsIgnoreCase(detailID), "Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"[0].success").toString().replace("[", "").replace("]", "").equalsIgnoreCase("true"), "Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"[0].message").toString().replace("[", "").replace("]", "").equalsIgnoreCase("Success - Account validations marked as completed"), "Line Number: "+appInd.getCurrentLineNumber());

            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateStopFraudPaymentInfoHoldAndReleaseAPIByUsingPUTMethod()' method. " + e.getMessage());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateStopFraudPaymentInfoHoldAndReleaseAPIByUsingPUTMethod()' method. " + e.getMessage());
            return false;
        }finally {inputData = null; putResponse = null; strPutEndPoint = null; dbResponse = null; companyID = null; detailID = null; strFieldsToUpdate = null; arrFieldsToUpdate = null;}
    }





    /********************************************************
     * Method Name      : validateStopFraudValidateTinAutoAPIByUsingPUTMethod()
     * Purpose          : to validate the Stop Fraud Payment Info Release API using PUT method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateStopFraudValidateTinAutoAPIByUsingPUTMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response putResponse = null;
        String strPutEndPoint = null;
        JSONArray dbResponse = null;
        String companyID = null;
        String strFieldsToUpdate = null;
        String userName = null;
        String validationID = null;
        String randomNum = null;
        try{
            inputData = dataTable.asMaps(String.class, String.class);

            //CPAY-1681: API- Put Request - validate-tin-auto
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_AutoValidate_QueryToOpenTinValidation", new Object[] {});
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_StopFraud_AutoValidate_PUTInput", new Object[] {});
            randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            companyID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("CompanyID").toString();
            userName = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("UserName").toString();

            strPutEndPoint = appInd.getPropertyValueByKeyName("stopFraudEndPoint") + appInd.getPropertyValueByKeyName("stopFraudAutoValidate_PUTMethod");
            strFieldsToUpdate = "companyId#"+companyID+",notes#Validate_tin-Auto Notes_"+appInd.getDateTime("hhmmss")+",username#"+userName;
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint, apiStopFraudAuthToken, "", strFieldsToUpdate, inputData.get(0).get("InputFileForPUT"), "No", "Valid");
            Assert.assertTrue(putResponse.getStatusCode() == 200, "Stop Fraud Validate-Tin-Auto API: " + putResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_StopFraud_AutoValidate_PUTResponse", new Object[] {"companyID", companyID});
            if(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("validationDetailId")!=null){
                Assert.assertTrue(((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0)).get("validationDetailId").toString().equalsIgnoreCase(JsonPath.read(putResponse.asString(), "$.validationDetailId").toString()), "Line Number: " + appInd.getCurrentLineNumber());
                Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.validated").toString().equalsIgnoreCase("true"), "Line Number: " + appInd.getCurrentLineNumber());
            }else{
                reports.writeResult(null, "Fail", "The Auto-Validate PUT request API failed. " + putResponse.getBody().prettyPrint());
                Assert.fail("Put Request - auto-validate failed. Line Number: " + appInd.getCurrentLineNumber());
            }


            //CPAY-1681: API- Put Request - auto-validate with invalid request body
            strFieldsToUpdate = "companyId#"+inputData.get(0).get("InvalidCompanyID")+",notes#Validate_tin-Auto Notes_"+appInd.getDateTime("hhmmss")+",username#"+userName;
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint, apiStopFraudAuthToken, "", strFieldsToUpdate, inputData.get(0).get("InputFileForPUT"), "No", "Invalid");
            Assert.assertTrue(putResponse.getStatusCode() == 422, "Stop Fraud Validate-Tin-Auto API with invalid request body: " + putResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.validated").toString().equalsIgnoreCase("false"));
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.message").toString().equalsIgnoreCase("Company details not found"));
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.messageDetail") == null);
            Assert.assertTrue(JsonPath.read(putResponse.asString(),"$.validationDetailId") == null);

            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateStopFraudValidateTinAutoAPIByUsingPUTMethod()' method. " + e.getMessage());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateStopFraudValidateTinAutoAPIByUsingPUTMethod()' method. " + e.getMessage());
            return false;
        }finally {inputData = null; putResponse = null; strPutEndPoint = null; dbResponse = null; companyID = null; strFieldsToUpdate = null; userName = null; validationID = null; randomNum = null;}
    }





    /********************************************************
     * Method Name      : validateStopFraudGetAuditInfoAPIByUsingGETMethod()
     * Purpose          : to validate the Stop Fraud get Audio Info API using GET method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateStopFraudGetAuditInfoAPIByUsingGETMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response getResponse = null;
        String strGetEndPoint = null;
        JSONArray dbResponse = null;
        String areaID = null;
        String companyID = null;
        org.json.simple.JSONObject objJsonGETResponse = null;
        try{
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_AuditTrailtab_validation_for_SupportCases_GET_Input", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            areaID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("AreaID").toString();
            companyID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("CompanyID").toString();

            //CPAY-4674: API -MSV-GET-Add endpoint to get audit info:
            strGetEndPoint = appInd.getPropertyValueByKeyName("stopFraudEndPoint") + appInd.getPropertyValueByKeyName("stopFraudGetAudioInfo_GETMethod");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", companyID).replace("%2", areaID).replace("%3", ""), apiStopFraudAuthToken, "Valid");
            Assert.assertTrue(getResponse.getStatusCode() == 200, "Stop Fraud get Audio Info API with comapnyID and AreadID: " + getResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());

            objJsonGETResponse = (JSONObject) apiUtility.convertJsonResponseIntoJSONObject("JsonObject", getResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_AuditTrailtab_validation_for_SupportCases_GET_Response", new Object[] {"CompanyID", companyID, "AreaID", areaID});

            //Validate auditInfos details of Json Response against DB data
            Assert.assertTrue(apiUtility.compareJsonArrayElementsWithDBResponse(getResponse.asString(), dbResponse, "auditInfos"
                    , "companyId#timestamp#username#actionTypeId#actionTypeLegacy#actionTypeDesc#actionTypeExtraDesc#actionId#validationDetailId#areaId#areaDesc#addEditUser"
                    , "CompanyID#Timestamp#Username#ActionTypeID#ActionTypeLegacy#ActionTypeDesc#ActionTypeExtraDesc#ActionID#ValidationDetailID#AreaID#AreaDesc#AddEditUser"));


            //CPAY-4674: API -MSV-GET-Add endpoint to get audit info: Don't provide the CompnayID and AreaID
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", "").replace("%2", "").replace("%3", ""), apiStopFraudAuthToken, "Invalid");
            Assert.assertTrue(getResponse.getStatusCode() == 400, "Stop Fraud get Audio Info API without CompanyID & AreaID: " + getResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.type").toString().equalsIgnoreCase("https://tools.ietf.org/html/rfc7231#section-6.5.1"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.title").toString().equalsIgnoreCase("One or more validation errors occurred."), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.status").toString().equalsIgnoreCase("400"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.errors").toString().contains("'Company Id' must not be empty."), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.errors").toString().contains("Invalid Company Id"), "Line Number: " + appInd.getCurrentLineNumber());


            ////CPAY-4674: API -MSV-GET-Add endpoint to get audit info: Provide the CompnayID, AreaID & Requested By
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", companyID).replace("%2", areaID).replace("%3", "vendorin.system@vendorin.com"), apiStopFraudAuthToken, "Valid");
            Assert.assertTrue(getResponse.getStatusCode() == 200, "Stop Fraud get Audio Info API with comapnyID and AreadID: " + getResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());

            objJsonGETResponse = (JSONObject) apiUtility.convertJsonResponseIntoJSONObject("JsonObject", getResponse.asString());
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_AuditTrailtab_validation_for_SupportCases_GET_Response", new Object[] {"CompanyID", companyID, "AreaID", areaID});

            //Validate auditInfos details of Json Response against DB data
            Assert.assertTrue(apiUtility.compareJsonArrayElementsWithDBResponse(getResponse.asString(), dbResponse, "auditInfos"
                    , "companyId#timestamp#username#actionTypeId#actionTypeLegacy#actionTypeDesc#actionTypeExtraDesc#actionId#validationDetailId#areaId#areaDesc#addEditUser"
                    , "CompanyID#Timestamp#Username#ActionTypeID#ActionTypeLegacy#ActionTypeDesc#ActionTypeExtraDesc#ActionID#ValidationDetailID#AreaID#AreaDesc#AddEditUser"));
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateStopFraudGetAuditInfoAPIByUsingGETMethod()' method. " + e.getMessage());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateStopFraudGetAuditInfoAPIByUsingGETMethod()' method. " + e.getMessage());
            return false;
        }finally {inputData = null; getResponse = null; strGetEndPoint = null; dbResponse = null; areaID = null; objJsonGETResponse = null;}
    }






    /********************************************************
     * Method Name      : validateStopFraudPollingEndpointsWithNotesAddedAPIByUsingGETMethod()
     * Purpose          : to validate the Stop Fraud polling endpoints with notes addes API using GET method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateStopFraudPollingEndpointsWithNotesAddedAPIByUsingGETMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response getResponse = null;
        String strGetEndPoint = null;
        JSONArray dbResponse = null;
        String pvcsID = null;
        String startDate = null;
        String endDate = null;
        String startTime = null;
        String endTime = null;
        org.json.simple.JSONArray objJsonGETResponse = null;
        try{
            dbResponse = apiDataProvider.getAPIDataProviderResponse("API_StopFraudNotes_addedto_SupportCase_GET_Input", new Object[] {});
            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
            pvcsID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("PVCSID").toString();
            String start = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("actionDate").toString();
            String end = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("statusDate").toString();
            startDate = (start.split("T"))[0];
            startTime = (start.split("T"))[1];
            endDate = (end.split("T"))[0];
            endTime = (end.split("T"))[1];

            //CPAY-4683: API -MSV-GET-Polling (poll-by-date) endpoints with Notes added with start and end date and time
            strGetEndPoint = appInd.getPropertyValueByKeyName("stopFraudEndPoint") + appInd.getPropertyValueByKeyName("stopFraudGetPollingNotesAddedDetails_GETMethod");
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", startDate).replace("%2", endDate).replace("%3", startTime).replace("%4", endTime), apiStopFraudAuthToken, "Valid");
            Assert.assertTrue(getResponse.getStatusCode() == 200, "Stop Fraud Poll-by-date Endpoint with start, end date and time API: " + getResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());

            objJsonGETResponse = (org.json.simple.JSONArray) apiUtility.convertJsonResponseIntoJSONObject("JsonArray", getResponse.asString());

            for(int i=0; i<objJsonGETResponse.size(); i++){
                    dbResponse = apiDataProvider.getAPIDataProviderResponse("API_StopFraudNotes_addedto_SupportCase_GET_Response", new Object[] {"PVCSID", ((JSONObject) objJsonGETResponse.get(i)).get("validationId")});
                    Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0), (JSONObject) objJsonGETResponse.get(i)
                            , "PVCSID#CompanyID#area#DetailID#actionDate#statusDate#Notes"
                            , "validationId#companyId#areaName#detailId#actionDate#statusDate#notes"));
            }


            //CPAY-4683: API -MSV-GET-Polling (poll-by-date) endpoints with Notes added with start and end date and without start and end time
            strGetEndPoint = appInd.getPropertyValueByKeyName("stopFraudEndPoint") + (appInd.getPropertyValueByKeyName("stopFraudGetPollingNotesAddedDetails_GETMethod").split("&StartTime"))[0];
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", startDate).replace("%2", endDate), apiStopFraudAuthToken, "Valid");
            Assert.assertTrue(getResponse.getStatusCode() == 200, "Stop Fraud Poll-by-date Endpoint with start & end date and without start & end time API: " + getResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());

            objJsonGETResponse = (org.json.simple.JSONArray) apiUtility.convertJsonResponseIntoJSONObject("JsonArray", getResponse.asString());

            for(int i=0; i<objJsonGETResponse.size(); i++){
                    dbResponse = apiDataProvider.getAPIDataProviderResponse("API_StopFraudNotes_addedto_SupportCase_GET_Response", new Object[] {"PVCSID", ((JSONObject) objJsonGETResponse.get(i)).get("validationId")});
                    Assert.assertTrue(apiUtility.compareJsonResponseWithDBResponse((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(0), (JSONObject) objJsonGETResponse.get(i)
                            , "PVCSID#CompanyID#area#DetailID#actionDate#statusDate#Notes"
                            , "validationId#companyId#areaName#detailId#actionDate#statusDate#notes"));
            }


            //CPAY-4683: API -MSV-GET-Polling (poll-by-date) endpoints with Notes added without start and end date
            strGetEndPoint = appInd.getPropertyValueByKeyName("stopFraudEndPoint") + (appInd.getPropertyValueByKeyName("stopFraudGetPollingNotesAddedDetails_GETMethod").split("&StartTime"))[0];
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", "").replace("%2", ""), apiStopFraudAuthToken, "InValid");
            Assert.assertTrue(getResponse.getStatusCode() == 400, "Stop Fraud Poll-by-date Endpoint without start & end date: " + getResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.type").toString().equalsIgnoreCase("https://tools.ietf.org/html/rfc7231#section-6.5.1"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.title").toString().equalsIgnoreCase("One or more validation errors occurred."), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.status").toString().equalsIgnoreCase("400"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.errors").toString().contains("EndDate should be in format of 'yyyy-MM-dd'"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.errors").toString().contains("Startdate should be provided with the format of 'yyyy-MM-dd'"), "Line Number: " + appInd.getCurrentLineNumber());


            //CPAY-4683: API -MSV-GET-Polling (poll-by-date) endpoints with Notes added with start date which is bigger than the end date
            strGetEndPoint = appInd.getPropertyValueByKeyName("stopFraudEndPoint") + (appInd.getPropertyValueByKeyName("stopFraudGetPollingNotesAddedDetails_GETMethod").split("&StartTime"))[0];
            getResponse = apiUtility.httpGETRequest(strGetEndPoint.replace("%1", appInd.dateAddAndReturn("yyyy-MM-dd", 10)).replace("%2", startDate), apiStopFraudAuthToken, "InValid");
            Assert.assertTrue(getResponse.getStatusCode() == 400, "Stop Fraud Poll-by-date Endpoint without start & end date: " + getResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.type").toString().equalsIgnoreCase("https://tools.ietf.org/html/rfc7231#section-6.5.1"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.title").toString().equalsIgnoreCase("One or more validation errors occurred."), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.status").toString().equalsIgnoreCase("400"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(getResponse.asString(), "$.errors").toString().contains("End date should be always greater than Start Date"), "Line Number: " + appInd.getCurrentLineNumber());

            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateStopFraudPollingEndpointsWithNotesAddedAPIByUsingGETMethod()' method. " + e.getMessage());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateStopFraudPollingEndpointsWithNotesAddedAPIByUsingGETMethod()' method. " + e.getMessage());
            return false;
        }finally {inputData = null; getResponse = null; strGetEndPoint = null; dbResponse = null; pvcsID = null; startDate = null; endDate = null; startTime = null; endTime = null; objJsonGETResponse = null;}
    }




    /********************************************************
     * Method Name      : validateStopFraudClearUnableToValidateAPIByUsingPUTMethod()
     * Purpose          : to validate the Stop Fraud clear unable to validate API using PUT method
     * Author           : Gudi
     * Parameters       : dataTable
     * ReturnType       : boolean
     ********************************************************/
    public boolean validateStopFraudClearUnableToValidateAPIByUsingPUTMethod(DataTable dataTable){
        List<Map<String, String>> inputData = null;
        Response putResponse = null;
        String strPutEndPoint = null;
        JSONArray dbResponse = null;
        String validationID = null;
        String userName = null;
        String strFieldsToUpdate = null;
        String timeStamp = null;
        try{
            timeStamp = appInd.getDateTime("hhmmss");
            inputData = dataTable.asMaps(String.class, String.class);

//            dbResponse = apiDataProvider.getAPIDataProviderResponse("", new Object[] {});
//            String randomNum = appInd.generateRandomNumbers(1, ((JSONArray) dbResponse.get(0)).size(),1);
//            validationID = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("CompanyID").toString();
//            userName = ((LinkedHashMap) ((JSONArray) dbResponse.get(0)).get(Integer.parseInt(randomNum)-1)).get("userName").toString();

            validationID = "1087929";
            userName = "bob@bob.com";
            //CPAY-4692: API- To run the clear unable to validate API with valid data
//            strPutEndPoint = appInd.getPropertyValueByKeyName("stopFraudEndPoint") + appInd.getPropertyValueByKeyName("stopFraudClearUnableToValidate_PUTMethod");
//            strFieldsToUpdate = "username#"+userName+",validationId#"+validationID;
//            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint, apiStopFraudAuthToken, "", strFieldsToUpdate, inputData.get(0).get("InputFileForPUT"), "No", "Valid");
//            Assert.assertTrue(putResponse.getStatusCode() == 200, "Stop Fraud Clear Unable To Validate API: " + putResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());
//            Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.success").toString().equalsIgnoreCase("true"), "Line Number: " + appInd.getCurrentLineNumber());
//            Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.message").toString().equalsIgnoreCase("Successfully rest \"unable to validate\" validation record back to open"), "Line Number: " + appInd.getCurrentLineNumber());
//            Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.numRecordsCleared").toString().equalsIgnoreCase("1"), "Line Number: " + appInd.getCurrentLineNumber());


            //CPAY-4692: API- To run the clear unable to validate API with Invalid validationID
            strPutEndPoint = appInd.getPropertyValueByKeyName("stopFraudEndPoint") + appInd.getPropertyValueByKeyName("stopFraudClearUnableToValidate_PUTMethod");
            strFieldsToUpdate = "username#"+userName+",validationId#"+(validationID+appInd.getDateTime("S"));
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint, apiStopFraudAuthToken, "", strFieldsToUpdate, inputData.get(0).get("InputFileForPUT"), "No", "Valid");
            Assert.assertTrue(putResponse.getStatusCode() == 200, "Stop Fraud Clear Unable To Validate API with invalid ValidationID: " + putResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.success").toString().equalsIgnoreCase("false"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.message").toString().equalsIgnoreCase("Validation record does not exist"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.numRecordsCleared").toString().equalsIgnoreCase("0"), "Line Number: " + appInd.getCurrentLineNumber());


            //CPAY-4692: API- To run the clear unable to validate API with Invalid userName
            strPutEndPoint = appInd.getPropertyValueByKeyName("stopFraudEndPoint") + appInd.getPropertyValueByKeyName("stopFraudClearUnableToValidate_PUTMethod");
            strFieldsToUpdate = "username#"+"gggggudi"+userName+",validationId#"+validationID;
            putResponse = apiUtility.httpPUTMethodRequestWithBearerAuth(strPutEndPoint, apiStopFraudAuthToken, "", strFieldsToUpdate, inputData.get(0).get("InputFileForPUT"), "No", "Valid");
            Assert.assertTrue(putResponse.getStatusCode() == 200, "Stop Fraud Clear Unable To Validate API with Invalid UserName: " + putResponse.getBody().prettyPrint()+": Line Number: "+appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.success").toString().equalsIgnoreCase("false"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.message").toString().equalsIgnoreCase("Validation record does not exist"), "Line Number: " + appInd.getCurrentLineNumber());
            Assert.assertTrue(JsonPath.read(putResponse.asString(), "$.numRecordsCleared").toString().equalsIgnoreCase("0"), "Line Number: " + appInd.getCurrentLineNumber());
            return true;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'validateStopFraudManualTINValidationAPIByUsingPUTMethod()' method. " + e.getMessage());
            return false;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateStopFraudManualTINValidationAPIByUsingPUTMethod()' method. " + e.getMessage());
            return false;
        }finally {inputData = null; putResponse = null; strPutEndPoint = null; dbResponse = null; validationID = null; strFieldsToUpdate = null; timeStamp = null; userName = null;}
    }
}
