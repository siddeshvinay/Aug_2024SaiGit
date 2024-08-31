package com.Fifty50.Fiftyone1.forty9.fortyeight.leetcode53.REstAPI1;

import io.restassured.RestAssured;

public class parale {
    public static void putreq(){
      int id =RestAssured.given().log().all().body("'{\n" +
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
                post("https://restful-booker.herokuapp.com/booking").
                then().log().all().extract().jsonPath().getInt("booking_id");
        System.out.println(id);
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

    public static void main(String[] args) {
       // putreq();
        updatebook1();
    }
}
