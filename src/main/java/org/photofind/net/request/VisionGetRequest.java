package org.photofind.net.request;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import java.net.URI;

public class VisionGetRequest implements VisionRequest {

    private HttpGet httpGet;
    private VisionFunction visionFunction;

    public VisionGetRequest(URI uri, VisionFunction visionFunction) {
        httpGet = new HttpGet(uri);
        this.visionFunction = visionFunction;
    }

    @Override
    public HttpUriRequest getRequest() {
        return httpGet;
    }

    @Override
    public VisionFunction getVisionFunction() {
        return visionFunction;
    }

    @Override
    public void setSubscriptionKey(String subscriptionKey) {
        httpGet.setHeader(RequestStrings.SUBSCRIPTION_HEADER, subscriptionKey);
    }
}
