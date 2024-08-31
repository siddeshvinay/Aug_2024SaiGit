package com.Fifty50.Fiftyone1.forty9.fortyeight.leetcode53.REstAPI1;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class storedainMap {

    private storedainMap(){}

    private static LinkedHashMap<String, Object> datamap=
            new LinkedHashMap<String, Object>();

    private static ThreadLocal<LinkedHashMap<String,Object>> datamap1=
            ThreadLocal.withInitial(()-> new LinkedHashMap<String,Object>());

}
