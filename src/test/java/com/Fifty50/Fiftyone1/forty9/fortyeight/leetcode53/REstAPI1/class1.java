package com.Fifty50.Fiftyone1.forty9.fortyeight.leetcode53.REstAPI1;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.util.HashMap;
import java.util.Map;

public class class1 {

    public static void putreq(){
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
                post("https://restful-booker.herokuapp.com/booking").
                then().log().all().extract().asString();
    }


    public static void baseuri(){
        Map<String, Object> pathapr= new HashMap<>();
        pathapr.put("basePath", "booking");
        pathapr.put("bookingId", 1);


        RestAssured.given()
        .log().all().
                baseUri("https://restful-booker.herokuapp.com/").
                basePath("{basePath}/{bookingId}").
               // pathParam("basePath", "booking")
               // .pathParam("bookingId", 1).
                 pathParams(pathapr).
                when().get().then().statusCode(200).log().all().extract();
    }


    // patch
    public static void patchur()
    {
        RestAssured.given().log().all().
                body("'{\n" +
                "    \"firstname\" : \"Siddesh\",\n" +
                "    \"lastname\" : \"Brown\"\n" +
                "}'").header("Authorization","Basic YWRtaW46cGFzc3dvcmQxMjM=").
                when().patch("https://restful-booker.herokuapp.com/booking/2").
                then().log().all().
                statusCode(200).extract();
    }

    // patch
    public static void Deletop()
    {
        RestAssured.given().log().all()
              .header("Authorization","Basic YWRtaW46cGFzc3dvcmQxMjM=").
                when().delete("https://restful-booker.herokuapp.com/booking/2").
                then().log().all().
                statusCode(200).extract();
    }

    //////
    public static void getreq(){
       String res= RestAssured.given().contentType("application/json").pathParam("id",2).
                when().get("https://restful-booker.herokuapp.com/booking/{id}").
                then().extract().asPrettyString();
        System.out.println(res);

    }

    public static void main(String[] args) {
        getreq();
       // baseuri();
        //patchur();

}

}
