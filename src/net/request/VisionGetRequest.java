package net.request;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import java.net.URI;

public class VisionGetRequest implements VisionRequest {

    private HttpGet httpGet;

    public VisionGetRequest(URI uri) {
        httpGet = new HttpGet(uri);
    }

    @Override
    public HttpUriRequest getRequest() {
        return httpGet;
    }

    @Override
    public void setSubscriptionKey(String subscriptionKey) {
        httpGet.setHeader(RequestStrings.SUBSCRIPTION_HEADER, subscriptionKey);
    }
}
