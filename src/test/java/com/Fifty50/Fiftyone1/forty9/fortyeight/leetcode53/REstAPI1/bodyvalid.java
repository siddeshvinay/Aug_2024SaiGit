package com.Fifty50.Fiftyone1.forty9.fortyeight.leetcode53.REstAPI1;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

public class bodyvalid {
    public static void body1(){
        RestAssured.given().log().all().
                body("{\n" +
                        "    \"username\" : \"admin\",\n" +
                        "    \"password\" : \"password123\"\n" +
                        "}")
        .when().post("https://restful-booker.herokuapp.com/auth").then()
                .log().all().
                body("token", Matchers.nullValue()).
                body("token.length()",Matchers.is(15));
                //body("token.length()",Matchers.equalTo(15)).
                //body("token.length()",Matchers.matchesRegex("^[a-z0-9]+$"));
    }

    public static void validatbookinid(){
        RestAssured.given().
                log().
                all().baseUri("https://restful-booker.herokuapp.com/booking")
        .when().get().
                then().
                log().
                all().
                body( "bookingid",Matchers.notNullValue());

    }

    public static void main(String[] args) {
       // body1();
        validatbookinid();
    }
}
