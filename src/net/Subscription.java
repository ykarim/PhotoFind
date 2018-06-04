package net;

public class Subscription {

    private static String subscriptionKey = "ac0a0f84ef4f4d20bff30a783bc0eeb2";

    public static String getSubscriptionKey() {
        return subscriptionKey;
    }

    public static void setSubscriptionKey(String subscriptionKey) {
        Subscription.subscriptionKey = subscriptionKey;
    }
}
