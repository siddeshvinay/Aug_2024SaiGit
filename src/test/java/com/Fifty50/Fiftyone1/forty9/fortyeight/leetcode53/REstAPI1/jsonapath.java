package com.Fifty50.Fiftyone1.forty9.fortyeight.leetcode53.REstAPI1;

import io.restassured.path.json.JsonPath;

import java.util.List;

public class jsonapath {
    public static void jspath(){

        String jas="{\n" +
                "    \"firstname\": \"Sally\",\n" +
                "    \"lastname\": \"Brown\",\n" +
                "    \"totalprice\": 111,\n" +
                "    \"depositpaid\": true,\n" +
                "    \"bookingdates\": {\n" +
                "        \"checkin\": \"2013-02-23\",\n" +
                "        \"checkout\": \"2014-10-23\"\n" +
                "    },\n" +
                "    \"additionalneeds\": \"Breakfast\"\n" +
                "}";
        JsonPath js= new JsonPath(jas);
        String as= js.getString("bookingdates.checkin");
        System.out.println(as);

    }

    public static  void jsonarr(){
        String jsare="[\n" +
                "  [\n" +
                "    \"10\",\n" +
                "    \"20\",\n" +
                "    \"30\",\n" +
                "    \"40\",\n" +
                "    \"50\",\n" +
                "    \"70\"\n" +
                "  ],\n" +
                "  [\n" +
                "    \"100\",\n" +
                "    \"200\",\n" +
                "    \"300\",\n" +
                "    \"400\",\n" +
                "    \"600\",\n" +
                "    \"500\"\n" +
                "  ]\n" +
                "]";
        JsonPath js= new JsonPath(jsare);
        String as= js.getString("[0][5]");
        //List<String> ka=js.getList("$");
        //System.out.println(ka.size());
        ///System.out.println(as);
        List<Object> intera= (List<Object>) js.getList("$").get(0);
        System.out.println(intera.get(1));
    }

    public static void test(){
        String aff="[\n" +
                "{\n" +
                "    \"firstname\": \"Sally\",\n" +
                "    \"lastname\": \"Brown\",\n" +
                "    \"totalprice\": 111,\n" +
                "    \"depositpaid\": true,\n" +
                "    \"address\":[\n" +
                "      {\n" +
                "        \"city\": \"Bangalore\",\n" +
                "        \"state\": \"Karnataka\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"city\": \"Chitradurga\",\n" +
                "        \"state\": \"Karnataka\"\n" +
                "      }\n" +
                "    ]\n" +
                "},\n" +
                "{\n" +
                "    \"firstname\": \"Siddesh\",\n" +
                "    \"lastname\": \"Blue\",\n" +
                "    \"totalprice\": 927,\n" +
                "    \"depositpaid\": true,\n" +
                "    \"Mobile\": \"234322342\",\n" +
                "    \"address\":[\n" +
                "      {\n" +
                "        \"city\": \"Delhi\",\n" +
                "        \"state\": \"New Delhi\"\n" +
                "      }\n" +
                "    ]\n" +
                "}\n" +
                "]";
        JsonPath jsa= new JsonPath(aff);
      String cit= jsa.getString("[1].address[0].city");
        //System.out.println(cit);
        System.out.println(jsa.getList("address.city"));

    }

    public static void main(String[] args) {
        test();
    }
}
