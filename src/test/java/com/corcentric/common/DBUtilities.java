package com.corcentric.common;

import com.corcentric.runner.CucumberTestRunner;
import org.testng.Assert;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtilities extends CucumberTestRunner {
    private static final int DB_RUN_QUERY_MAX_ROWS = 2000;
    private static final String DB_NULL_CHAR = "";

    /********************************************************
     * Method Name      : createAndReturnConnection()
     * Purpose          : user establish the Connection with DB
     * Author           : Gudi
     * Parameters       :
     * ReturnType       : Connection
     ********************************************************/
    public Connection createAndReturnConnection(){
        String dbURL = null;
        String dbUserName = null;
        String dbPassword = null;
        Connection conn = null;
        try{
            dbURL = appInd.getPropertyValueByKeyName("dbURL");
            dbUserName = appInd.getPropertyValueByKeyName("dbUserName");
            dbPassword = appInd.getPropertyValueByKeyName("dbPassword");

            conn = DriverManager.getConnection(dbURL, dbUserName, dbPassword);
            if(conn != null){
                reports.writeResult(oBrowser, "Pass", "The connection to '"+System.getProperty("environment")+"' DB was successful");
            }else{
                reports.writeResult(oBrowser, "Fail", "Failed to establish the connection with '"+System.getProperty("environment")+"' DB");
                Assert.fail("Failed to establish the connection with '"+System.getProperty("environment")+"' DB");
            }
            return conn;
        }catch(Exception e) {
            reports.writeResult(oBrowser, "Exception", "Exception in 'createAndReturnConnection()' method. " + e);
            return null;
        }catch(AssertionError e) {
            reports.writeResult(oBrowser, "Exception", "AssertionError in 'createAndReturnConnection()' method. " + e);
            return null;
        }finally {
            dbURL = null; dbUserName = null; dbPassword = null;
        }
    }




    /********************************************************
     * Method Name      : getQueryResultsInListOfMap()
     * Purpose          : user executes the query and stores the query output in List<Map<String, String>> object
     * Author           : Gudi
     * Parameters       : dbName, queryKey, arrParam
     * ReturnType       : List<Map<String, String>>
     ********************************************************/
    public List<Map<String, String>> getQueryResultsInListOfMap(String queryKey, Object arrParams[]){
        List<Map<String, String>> resultsMap = null;
        int count = 0;
        int numberOfColumns = 0;
        ResultSet res = null;
        ResultSetMetaData resMeta = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            resultsMap = new ArrayList<>();
            conn = createAndReturnConnection();
            stmt = conn.prepareStatement(appInd.getQueryByKeyName(queryKey), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            for(int i=0; i<arrParams.length; i++){
                stmt.setObject((i+1), arrParams[i]);
            }

            res = stmt.executeQuery();
            resMeta = res.getMetaData();
            numberOfColumns = resMeta.getColumnCount();

            if(res.next()){
                do{
                    Map<String, String> row = new HashMap<>();
                    resultsMap.add(row);

                    for(int i=1; i<=numberOfColumns; i++){
                        res.getString(i);
                        if(res.wasNull()){
                            row.put(resMeta.getColumnName(i), DB_NULL_CHAR);
                        }else{
                            row.put(resMeta.getColumnName(i), res.getString(i));
                        }
                    }
                    count++;
                }while(res.next() && count < DB_RUN_QUERY_MAX_ROWS);
            }
            reports.writeResult(oBrowser, "Pass", "Retrieved '"+count+"' record(s) from the '"+System.getProperty("environment")+"' DB");
            return resultsMap;
        }catch(Exception e){
            reports.writeResult(oBrowser, "Exception", "Exception in 'getQueryResultsInListOfMap()' method. " + e);
            throw new RuntimeException("Exception while executing 'getQueryResultsInListOfMap()' method. "+e);
        }finally {
            try{
                res.close();
                res = null;
                resMeta = null;
                conn.close();
                conn = null;
            }catch(Exception e){
                reports.writeResult(oBrowser, "Exception", "Exception in 'getQueryResultsInListOfMap()' method. " + e);
                throw new RuntimeException("Exception while executing 'getQueryResultsInListOfMap()' method. "+e);
            }
        }
    }
}
