import net.vortexdata.tsqpf.modules.*;

public class PluginMain extends PluginInterface {

    Thread rollBannerThread;

    @Override
    public String getName() {
        return "RollBannerTask";
    }

    @Override
    public void onEnable() {
        rollBannerThread = new Thread(new RollBannerTask(getLogger(), getAPI()));
        rollBannerThread.start();
    }

    @Override
    public void onDisable() {
        rollBannerThread.interrupt();
        rollBannerThread = null;
    }

}
