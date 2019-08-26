import net.vortexdata.tsqpf.modules.*;

public class PluginMain extends PluginInterface {

    Thread rollBannerThread;

    @Override
    public String getName() {
        return "RollBannerTask";
    }

    @Override
    public void onEnable() {

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
