package net.vortexdata.tsqpfp.rollingbanner;

import com.github.theholywaffle.teamspeak3.*;
import com.github.theholywaffle.teamspeak3.api.*;
import net.vortexdata.tsqpf.modules.*;
import net.vortexdata.tsqpf.plugins.*;

import java.util.*;

public class RollBannerTask implements Runnable {

    private PluginLogger logger;
    private TS3Api api;
    private PluginConfig config;
    private int cycleDelay;
    private String[] bannerGfxUrls;
    private String[] bannerUrls;
    private boolean useSingleUrlMode;
    private boolean useNoUrlMode;
    private boolean isReady = true;

    public RollBannerTask(PluginLogger logger, TS3Api api, PluginConfig config) {
        this.logger = logger;
        this.api = api;
        this.config = config;

        try {
            cycleDelay = (Integer.parseInt(config.readValue("cycleDelay")));
            if (cycleDelay < 3000)
                logger.printWarn("Cycle delay is lower than the recommended minimum delay of 3 seconds.");
        } catch (Exception e) {
            logger.printError("Could not set the banner cycle delay. Please check your config for non-numeric characters in field cycleDelay. Using default value (20) instead.");
            cycleDelay = 10000;
        }

        try {
            bannerGfxUrls = config.readValue("bannerGfxUrls").split(";");
        } catch (Exception e) {
            logger.printError("Could not set the banner GFX URLs. Please check your config. Using default values instead.");
            bannerGfxUrls = "https://via.placeholder.com/350x150;https://via.placeholder.com/350x150/0000FF/808080".split(";");
        }

        try {
            bannerUrls = config.readValue("bannerUrls").split(";");
            if (bannerUrls.length < bannerGfxUrls.length) {
                logger.printWarn("There are less banner URLs then banner GFX URls. Changing mode to useSingleUrlMode.");
                useSingleUrlMode = true;
            } else {
                useSingleUrlMode = false;
            }
        } catch (Exception e) {
            logger.printError("Could not set the banner URLs. Please check your config. Using default values instead.");
            bannerUrls = "https://vortexdata.net;https://projects.vortexdata.net".split(";");
        }

        if (bannerGfxUrls.length == 0) {
            isReady = false;
            logger.printError("Can not start RollingBanner task. Config value bannerGfxUrls is empty.");
        }
    }

    @Override
    public void run() {
        int iterator = 0;
        do {

            try {
                Thread.sleep(cycleDelay * 1000);
            } catch (InterruptedException e) {
                logger.printDebug("Encountered an Interrupted Exception whilst waiting for delay to complete.");
            }

            HashMap<VirtualServerProperty, String> newServerProperties = new HashMap<>();
            newServerProperties.put(VirtualServerProperty.VIRTUALSERVER_HOSTBANNER_GFX_URL, bannerGfxUrls[iterator]);
            if (!useSingleUrlMode && !useNoUrlMode) {
                newServerProperties.put(VirtualServerProperty.VIRTUALSERVER_HOSTBANNER_URL, bannerUrls[iterator]);
            } else if (!useNoUrlMode) {
                newServerProperties.put(VirtualServerProperty.VIRTUALSERVER_HOSTBANNER_URL, bannerUrls[0]);
            } else {
                newServerProperties.put(VirtualServerProperty.VIRTUALSERVER_HOSTBANNER_URL, "");
            }

            api.editServer(newServerProperties);

            iterator += 1;
            if (iterator == bannerGfxUrls.length)
                iterator = 0;
        } while (true);
    }

    public boolean getIsReady() {
        return isReady;
    }

}
