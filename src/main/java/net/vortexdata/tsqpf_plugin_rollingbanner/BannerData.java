package net.vortexdata.tsqpf_plugin_rollingbanner;

import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.*;
import java.util.*;

public class BannerData {

    public static LinkedList loadData() {

        JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(new FileReader("plugins//RollingBanner//banners.json"));
            JSONArray jsonArray = (JSONArray) obj;
            int length = jsonArray.size();

            LinkedList banners = new LinkedList();


            for (int i = 0; i < length; i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Set s = jsonObject.entrySet();
                Iterator iter = s.iterator();

                HashMap hm = new HashMap();

                while (iter.hasNext()) {
                    Map.Entry me = (Map.Entry) iter.next();
                    hm.put(me.getKey(), me.getValue());
                }
                banners.add(hm);
            }

            return banners;

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }
}
