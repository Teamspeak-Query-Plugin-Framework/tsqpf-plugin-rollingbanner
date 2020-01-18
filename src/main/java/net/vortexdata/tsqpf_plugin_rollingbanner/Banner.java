package net.vortexdata.tsqpf_plugin_rollingbanner;

public class Banner {

    private int displayTime;
    private String imageSrc;
    private String redirectUrl;

    public Banner(int displayTime, String imageSrc, String redirectUrl) {
        this.displayTime = displayTime;
        this.imageSrc = imageSrc;
        this.redirectUrl = redirectUrl;
    }

    public int getDisplayTime() {
        return displayTime;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
}
