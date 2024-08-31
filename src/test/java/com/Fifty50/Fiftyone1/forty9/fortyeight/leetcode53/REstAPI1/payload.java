package com.Fifty50.Fiftyone1.forty9.fortyeight.leetcode53.REstAPI1;

import io.restassured.RestAssured;

import java.util.*;

public class payload {
    public static void payload(){
        Map<String, Object> ema=new LinkedHashMap<>();
        ema.put("id", "1");
        ema.put("name", "siddesh");
        ema.put("lastname", "Y J");
        ema.put("School", "Rotary");
        ema.put("City", "CTA");


        Map<String, Object> address=new LinkedHashMap<>();
        address.put("School", "Rotary");
        address.put("City", "CTA");
        address.put("state", "karnataka");
        address.put("Zipcode", 566501);
        ema.put("myaddress", address);

        List<String> mobile= new ArrayList<>();
        mobile.add("34534534");
        mobile.add("455554455");

        Map<String, Object> emap=new LinkedHashMap<>();
        emap.put("id", "2");
        emap.put("name", "Tet");
        emap.put("lastname", "Y J");
        emap.put("School", "SSG");
        emap.put("City", "BLR");
        emap.put("Mobile", mobile);

        List<Map<String,Object>> listpa= new ArrayList<>();
        listpa.add(ema);
        listpa.add(emap);
        RestAssured.given().log().all().
                body(listpa).when().post().then().extract().asPrettyString();
    }

    public static void main(String[] args) {
        payload();
    }

}
