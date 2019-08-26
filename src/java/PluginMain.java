import net.vortexdata.tsqpf.modules.*;

public class PluginMain extends PluginInterface {

    Thread rollBannerThread;

    @Override
    public String getName() {
        return "RollingBanner";
    }

    @Override
    public void onEnable() {

        // Get Config
        getConfig().setDefault("bannerGfxUrls", "https://via.placeholder.com/350x150;https://via.placeholder.com/350x150/0000FF/808080");
        getConfig().setDefault("bannerUrls", "https://vortexdata.net");
        getConfig().setDefault("cycleDelay", "10000");
        getConfig().saveAll();


        RollBannerTask rollBannerTask = new RollBannerTask(getLogger(), getAPI(), getConfig());
        boolean isReady = rollBannerTask.getIsReady();
        if (!isReady) {
            onDisable();
        } else {
            rollBannerThread = new Thread(rollBannerTask);
            rollBannerThread.start();
        }
    }

    @Override
    public void onDisable() {
        getLogger().printInfo("Unloading RollingBanner...");
        rollBannerThread.interrupt();
        rollBannerThread = null;
    }

}
