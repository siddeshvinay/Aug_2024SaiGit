package com.Fifty50.Fiftyone1.forty9.fortyeight;

import io.restassured.RestAssured;

import java.util.ArrayList;
import java.util.List;

public class responsclass {
    public static void main(String[] args) {

        Bicycle by= new Bicycle();
        by.setColor("Gree");
        by.setPrice(1000);
       //js.setBook();

        book b1= new book();
        b1.setCategory("History");
        b1.setTile("Chollas");
        b1.setAuthor("Roa");
        b1.setPrice(100);

        book b2= new book();
        b2.setCategory("Physics");
        b2.setTile("Raman effect");
        b2.setAuthor("Raman");
        b2.setPrice(150);

        book b3= new book();
        b3.setCategory("Chemistry");
        b3.setTile("Physiscal chemistry");
        b3.setAuthor("Suddher");
        b3.setPrice(350);

        List<book> b=new ArrayList<>();
        b.add(b1);
        b.add(b2);
        b.add(b3);

        Jsoncalss js= new Jsoncalss();
        js.setStore("Basavraj book depo");
        js.setBook(b);
        js.setBicycle(by);

        RestAssured.
                given().
                log().
                all().
                body(js).
                when().
                get();

    }

}
