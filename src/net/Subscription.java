package net;

public class Subscription {

    private static String subscriptionKey;

    public static String getSubscriptionKey() {
        return subscriptionKey;
    }

    public static void setSubscriptionKey(String subscriptionKey) {
        Subscription.subscriptionKey = subscriptionKey;
    }
}
