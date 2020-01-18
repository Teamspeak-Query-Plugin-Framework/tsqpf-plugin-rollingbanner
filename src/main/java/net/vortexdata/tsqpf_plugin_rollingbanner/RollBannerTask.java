package net.vortexdata.tsqpf_plugin_rollingbanner;

import com.github.theholywaffle.teamspeak3.*;
import com.github.theholywaffle.teamspeak3.api.*;
import net.vortexdata.tsqpf.modules.*;
import net.vortexdata.tsqpf.plugins.*;

import java.util.*;

public class RollBannerTask implements Runnable {

    private PluginLogger logger;
    private TS3Api api;
    private ArrayList<Banner> banners;

    public RollBannerTask(PluginLogger logger, TS3Api api, ArrayList<Banner> banners) {
        this.logger = logger;
        this.api = api;
        this.banners = banners;
    }

    @Override
    public void run() {
        int iterator = 0;
        do {

            Banner currentBanner = banners.get(iterator);

            // Edit server banner
            HashMap<VirtualServerProperty, String> newServerProperties = new HashMap<>();
            newServerProperties.put(VirtualServerProperty.VIRTUALSERVER_HOSTBANNER_GFX_URL, currentBanner.getImageSrc());
            newServerProperties.put(VirtualServerProperty.VIRTUALSERVER_HOSTBANNER_URL, currentBanner.getRedirectUrl());
            api.editServer(newServerProperties);

            try {
                Thread.sleep(currentBanner.getDisplayTime() * 1000);
            } catch (InterruptedException e) {
                logger.printDebug("Shutting down RollBannerTask...");
            }

            if (iterator == banners.size() - 1)
                iterator = 0;
            else
                iterator++;

        } while (true);
    }

}
