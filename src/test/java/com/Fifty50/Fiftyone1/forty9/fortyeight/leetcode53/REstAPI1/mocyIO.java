package com.Fifty50.Fiftyone1.forty9.fortyeight.leetcode53.REstAPI1;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

public class mocyIO {

    public static void getat(){

        String as=RestAssured.given().
                log().all().when().
                get("https://run.mocky.io/v3/b6e5a5f8-e5a4-4d44-a945-fd906e60fd33").then()
                .log().all().extract().asPrettyString();

        System.out.println(as);
    }

    //Deseriliazation
    public static void delsre(){

        Map jsonre= RestAssured.get("https://run.mocky.io/v3/b6e5a5f8-e5a4-4d44-a945-fd906e60fd33")
                .as(Map.class);

        String filename=(String)jsonre.get("name");
        System.out.println(filename);
    }

    public static void listja(){
       List<Map<String,Object>> reslis=  RestAssured.get("https://run.mocky.io/v3/a0b8b289-ead1-4a96-b1d3-179a225b516c").
                  as(new TypeRef<List<Map<String,Object>>>() {});
        System.out.println(reslis.size());
            Map<String, Object>epa=reslis.get(0);
        System.out.println(epa.get(""));
    }

    public  void ojectinst(){
       Response reslis=  RestAssured.get("https://run.mocky.io/v3/a0b8b289-ead1-4a96-b1d3-179a225b516c");
            Object oa=reslis.as(Object.class);
            if(oa instanceof List){
               List resplist= (List)oa;
                System.out.println(resplist.size());
            }
            else if(oa instanceof Map){
                Map omap= (Map)oa;
                System.out.println(omap.size());
            }
    }

    public static void main(String[] args) {
        //getat();
       // delsre();
      //  listja();
        mocyIO mp= new mocyIO();
        mp.ojectinst();
    }
}
