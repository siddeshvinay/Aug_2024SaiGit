package com.Fifty50.Fiftyone1.forty9.fortyeight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Product {

    private double price;
    private String name;
    private int id;

    Product(int id,String name,double price ){
        this.price=price;
        this.name=name;
        this.id=id;
    }
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void main(String[] args) {
        List<Product> products= Arrays.asList(
                new Product(1, "Siddesh", 22.5),
                new Product(2, "Vinay", 25),
                new Product(4, "Diya", 28),
                new Product(3, "Vinu", 29));

        int avg = 0;
        for (Object product : products) {
            Product p = (Product) product;
            //System.out.println("Price: " + p.getPrice());
            avg+=p.getPrice();
           // System.out.println(p.getName());
        }
       // System.out.println(avg/products.size());


        Product p=new Product(1, "Siddesh", 22.5);
        Product p1=new Product(2, "Vinay", 25);
        Product p2=new Product(4, "Diya", 28);
        Product p3=new Product(3, "Vinu", 29);

        List<Object> listpro= new ArrayList<>();
        listpro.add(p);
        listpro.add(p1);
        listpro.add(p2);
        listpro.add(p3);
        int sum = 0;
       int vag=listpro.size();
       int avag=0;
        for(Object xl: listpro){
            Product xl1 = (Product) xl;
            sum+=xl1.getPrice();
            avag=sum/vag;
        }
        System.out.println(avag);




    }


}
