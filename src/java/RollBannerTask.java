import com.github.theholywaffle.teamspeak3.*;
import net.vortexdata.tsqpf.modules.*;

public class RollBannerTask implements Runnable {

    private boolean active = true;
    PluginLogger logger;
    TS3Api api;

    public RollBannerTask(PluginLogger logger, TS3Api api) {
        this.logger = logger;
        this.api = api;
    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                logger.printDebug("Encountered an Interrupted Exception whilst waiting for delay to complete.");
            }

        } while (active);
    }

    public void shutdown() {
        active = false;
    }

}
