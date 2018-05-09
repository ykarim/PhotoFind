package net.request;

import org.apache.http.client.methods.HttpUriRequest;

public interface VisionRequest {

    HttpUriRequest getRequest();

    void setSubscriptionKey(String subscriptionKey);
}
