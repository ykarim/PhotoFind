package org.photofind.net;

public class VisionProvider {

    private static Provider currentVisionProvider = Provider.GOOGLE_CLOUD;

    public static Provider getCurrentVisionProvider() {
        return currentVisionProvider;
    }

    public static void setCurrentVisionProvider(Provider currentVisionProvider) {
        VisionProvider.currentVisionProvider = currentVisionProvider;
    }

    public enum Provider {
        GOOGLE_CLOUD, MICROSOFT_VISION
    }
}
