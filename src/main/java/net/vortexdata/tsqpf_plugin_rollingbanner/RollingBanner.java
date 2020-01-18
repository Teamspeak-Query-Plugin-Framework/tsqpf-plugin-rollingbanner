package net.vortexdata.tsqpf_plugin_rollingbanner;

import net.vortexdata.tsqpf.plugins.*;

import java.io.*;
import java.util.*;

public class RollingBanner extends TeamspeakPlugin {

    Thread rollBannerThread;

    @Override
    public void onEnable() {

        // Get Config
        getConfig().setDefault("bannerGfxUrls", "https://via.placeholder.com/350x150;https://via.placeholder.com/350x150/0000FF/808080");
        getConfig().setDefault("bannerUrls", "https://vortexdata.net");
        getConfig().setDefault("cycleDelay", "60");
        getConfig().saveAll();

        // Craete JSON File
        File jsonFile = new File("plugins//RollingBanner//banners.json");
        if (!jsonFile.exists() || !jsonFile.isFile()) {
            try {

                BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/banners.json")));
                ArrayList<String> templateLines = new ArrayList<>();
                while (br.ready()) {
                    templateLines.add(br.readLine());
                }
                br.close();

                BufferedWriter bw = new BufferedWriter(new FileWriter("plugins//RollingBanner//banners.json"));
                for (String line : templateLines) {
                    bw.write(line + "\n");
                }
                bw.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        LinkedList banners = BannerData.loadData();
        ArrayList<Banner> bannerObjects = new ArrayList<>();

        for (int i=0; i < banners.size(); i++) {
            HashMap hm = (HashMap) banners.get(i);
            Set s = hm.entrySet();
            Iterator iter = s.iterator();
            HashMap<String, String> bannerValues = new HashMap<>();
            while (iter.hasNext()) {
                Map.Entry me = (Map.Entry) iter.next();
                bannerValues.put(me.getKey().toString(), me.getValue().toString());
            }

            try {
                bannerObjects.add(new Banner(Integer.parseInt(bannerValues.get("displayTime")), bannerValues.get("imageSrc"), bannerValues.get("redirectUrl")));
            } catch (NumberFormatException e) {
                getLogger().printWarn("Failed to register a banner, appending info: " + e.getMessage());
            }
        }

        if (bannerObjects.size() > 0) {
            RollBannerTask rollBannerTask = new RollBannerTask(getLogger(), getAPI(), bannerObjects);
            rollBannerThread = new Thread(rollBannerTask);
            rollBannerThread.start();
        } else {
            getLogger().printError("No banner object could be parsed.");
        }
    }

    @Override
    public void onDisable() {
        rollBannerThread.interrupt();
        rollBannerThread = null;
    }

}
