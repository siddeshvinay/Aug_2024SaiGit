package com.Fifty50;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamMAP {
    public static void main(String[] args) {
        List<Integer> mapal= Arrays.asList(10,34,33,62,12,31,38);
        List<Integer> rew=new ArrayList<>();
        rew= mapal.stream().map(n->n*2).collect(Collectors.toList());
       // System.out.println(rew);

        List<String> ors=Arrays.asList("siddesh", "test","diay","rossy");

        ors.stream().map(s->s.toUpperCase()).forEach(n->System.out.println(n));

    }
}

