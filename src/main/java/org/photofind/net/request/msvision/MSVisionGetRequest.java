package org.photofind.net.request.msvision;

import org.photofind.net.request.VisionGetRequest;

import java.net.URI;

public class MSVisionGetRequest extends VisionGetRequest {

    private final String SUBSCRIPTION_HEADER = "Ocp-Apim-Subscription-Key";

    public MSVisionGetRequest(URI uri, RequestFunction function) {
        super(uri, function);
    }

    @Override
    public void setSubscriptionKey(String subscriptionKey) {
        getRequest().setHeader(SUBSCRIPTION_HEADER, subscriptionKey);
    }
}
