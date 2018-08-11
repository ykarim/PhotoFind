package org.photofind.net.request.gcloudvision;

import org.photofind.net.request.VisionGetRequest;

import java.net.URI;

public class GCloudVisionGetRequest extends VisionGetRequest {

    private final String SUBSCRIPTION_HEADER = "key";

    public GCloudVisionGetRequest(URI uri, RequestFunction function) {
        super(uri, function);
    }

    @Override
    public void setSubscriptionKey(String subscriptionKey) {
        getRequest().setHeader(SUBSCRIPTION_HEADER, subscriptionKey);
    }
}
