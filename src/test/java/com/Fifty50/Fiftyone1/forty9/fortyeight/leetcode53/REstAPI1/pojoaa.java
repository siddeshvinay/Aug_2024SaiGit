package com.Fifty50.Fiftyone1.forty9.fortyeight.leetcode53.REstAPI1;

import io.restassured.RestAssured;

public class pojoaa {

    public static void poa(){
        empl ee=RestAssured.get("https://run.mocky.io/v3/96f44bb1-6fc2-4b1c-852d-977cbe4921b6").
                as(empl.class);
        System.out.println(ee.getEmail());
    }

    public static void main(String[] args) {
        poa();
    }
}
