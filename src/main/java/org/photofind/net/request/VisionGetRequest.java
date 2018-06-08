package org.photofind.net.request;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import java.net.URI;

public class VisionGetRequest implements VisionRequest {

    private HttpGet httpGet;
    private final String SUBSCRIPTION_HEADER = "Ocp-Apim-Subscription-Key";
    private RequestFunction function;

    public VisionGetRequest(URI uri, RequestFunction function) {
        httpGet = new HttpGet(uri);
        this.function = function;
    }

    @Override
    public HttpUriRequest getRequest() {
        return httpGet;
    }

    @Override
    public RequestFunction getFunction() {
        return function;
    }

    @Override
    public void setSubscriptionKey(String subscriptionKey) {
        httpGet.setHeader(SUBSCRIPTION_HEADER, subscriptionKey);
    }
}
