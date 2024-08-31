package com.Fifty50.Fiftyone1.forty9.fortyeight.leetcode53.REstAPI1;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Respec {
    RequestSpecification respec;
    @BeforeClass
    public  void requspec(){
      respec = RestAssured.given().log().all();
      respec.basePath("https://restful-booker.herokuapp.com/booking");
     // respec.contentType("application/json");
    }

    @Test
    public  void creatbook1(){
       String rea= RestAssured.given().spec(respec).body("'{\n" +
                        "    \"firstname\" : \"Siddes\",\n" +
                        "    \"lastname\" : \"Brown\",\n" +
                        "    \"totalprice\" : 111,\n" +
                        "    \"depositpaid\" : true,\n" +
                        "    \"bookingdates\" : {\n" +
                        "        \"checkin\" : \"2018-01-01\",\n" +
                        "        \"checkout\" : \"2019-01-01\"\n" +
                        "    },\n" +
                        "    \"additionalneeds\" : \"Breakfast\"\n" +
                        "}'").when().
                post().
                then().log().all().extract().asString();
        System.out.println(rea);
    }


    public static void updatebook1(){
        RestAssured.given().log().all().body("'{\n" +
                        "    \"firstname\" : \"Test\",\n" +
                        "    \"lastname\" : \"Brown\",\n" +
                        "    \"totalprice\" : 111,\n" +
                        "    \"depositpaid\" : true,\n" +
                        "    \"bookingdates\" : {\n" +
                        "        \"checkin\" : \"2018-01-01\",\n" +
                        "        \"checkout\" : \"2019-01-01\"\n" +
                        "    },\n" +
                        "    \"additionalneeds\" : \"Breakfast\"\n" +
                        "}'").contentType("application/json").
                put("https://restful-booker.herokuapp.com/booking/2").
                then().log().all().extract().asString();
    }


}
