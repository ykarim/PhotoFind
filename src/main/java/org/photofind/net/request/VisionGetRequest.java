package org.photofind.net.request;

import org.apache.http.client.methods.HttpGet;

import java.net.URI;

public abstract class VisionGetRequest implements VisionRequest {

    private HttpGet httpGet;
    private RequestFunction function;

    public VisionGetRequest(URI uri, RequestFunction function) {
        httpGet = new HttpGet(uri);
        this.function = function;
    }

    @Override
    public HttpGet getRequest() {
        return httpGet;
    }

    @Override
    public RequestFunction getFunction() {
        return function;
    }

    @Override
    public abstract void setSubscriptionKey(String subscriptionKey);
}
