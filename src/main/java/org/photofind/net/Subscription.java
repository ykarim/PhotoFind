package org.photofind.net;

public class Subscription {

    private static Provider currentSubscriptionProvider = Provider.NONE;
    private static String subscriptionKey = "";

    public static Provider getCurrentSubscriptionProvider() {
        return currentSubscriptionProvider;
    }

    public static void setCurrentSubscriptionProvider(Provider currentSubscriptionProvider) {
        Subscription.currentSubscriptionProvider = currentSubscriptionProvider;
    }

    public enum Provider {
        GOOGLE_CLOUD, MICROSOFT_VISION, NONE
    }

    public static String getSubscriptionKey() {
        return subscriptionKey;
    }

    public static void setSubscriptionKey(String subscriptionKey) {
        Subscription.subscriptionKey = subscriptionKey;
    }
}
