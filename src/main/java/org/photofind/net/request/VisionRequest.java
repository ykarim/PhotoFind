package org.photofind.net.request;

import org.apache.http.client.methods.HttpUriRequest;

public interface VisionRequest {

    HttpUriRequest getRequest();

    VisionFunction getVisionFunction();

    void setSubscriptionKey(String subscriptionKey);
}
