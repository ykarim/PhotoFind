package org.photofind.net.request;

import org.apache.http.client.methods.HttpUriRequest;

public interface VisionRequest {

    HttpUriRequest getRequest();

    RequestFunction getFunction();

    void setSubscriptionKey(String subscriptionKey);

    enum RequestFunction {
        ANALYZE
    }
}
