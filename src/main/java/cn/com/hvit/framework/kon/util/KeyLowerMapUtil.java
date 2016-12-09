package cn.com.hvit.framework.kon.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by wp on 16-8-22.
 */
public class KeyLowerMapUtil {

    public static Map parse(Map map) {
        Map resultMap = new HashMap();
        Iterator iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String keyName = (String) iterator.next();
            resultMap.put(keyName.toLowerCase(), map.get(keyName));
        }

        return resultMap;
    }

}
