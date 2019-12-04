package net.vortexdata.tsqpfp.rollingbanner;

import net.vortexdata.tsqpf.plugins.*;

public class RollingBanner extends TeamspeakPlugin {

    Thread rollBannerThread;

    @Override
    public void onEnable() {

        // Get Config
        getConfig().setDefault("bannerGfxUrls", "https://via.placeholder.com/350x150;https://via.placeholder.com/350x150/0000FF/808080");
        getConfig().setDefault("bannerUrls", "https://vortexdata.net");
        getConfig().setDefault("cycleDelay", "60");
        getConfig().saveAll();


        RollBannerTask rollBannerTask = new RollBannerTask(getLogger(), getAPI(), getConfig());
        boolean isReady = rollBannerTask.getIsReady();
        if (!isReady) {
            getLogger().printError("Can not start RollBanner task. Unloading...");
            onDisable();
        } else {
            rollBannerThread = new Thread(rollBannerTask);
            rollBannerThread.start();
        }
    }

    @Override
    public void onDisable() {
        rollBannerThread.interrupt();
        rollBannerThread = null;
    }

}
