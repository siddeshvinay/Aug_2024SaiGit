package com.Fifty50.Fiftyone1.forty9.fortyeight.leetcode53.REstAPI1;

import io.restassured.RestAssured;

public class requestandresp {

    public static void createbook(){
        RestAssured.given().body("'{\n" +
                "    \"firstname\" : \"Tod\",\n" +
                "    \"lastname\" : \"Brown\",\n" +
                "    \"totalprice\" : 111,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}'").contentType("application/json")
                .when().post("https://restful-booker.herokuapp.com/booking").

                then().log().all().extract();
    }

    public static void main(String[] args) {
        createbook();
    }
}
