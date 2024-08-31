package com.Fifty50.Fiftyone1.forty9.fortyeight.leetcode53.REstAPI1;

import io.restassured.RestAssured;
import io.restassured.config.HeaderConfig;

public class Headee {
    public static void main(String[] args) {
        RestAssured.given().log().all().
                config(RestAssured.config()
                        .headerConfig(HeaderConfig.headerConfig().
                        overwriteHeadersWithName("header1"))).
                header("header1", "valueese1").
                header("header1", "valueese2").
                //headers("header1", "valeu1", "vale2", "valu3").
                when().get().
                then().log().all().extract();
    }
}
