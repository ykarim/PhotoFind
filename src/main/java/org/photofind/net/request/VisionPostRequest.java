package org.photofind.net.request;

import org.apache.http.client.methods.HttpPost;
import org.photofind.media.Picture;

import java.net.URI;

//Extend HttpPost instead of reinventing the wheel and constantly using getter?
public abstract class VisionPostRequest implements VisionRequest {

    private HttpPost httpPost;
    private RequestFunction function;

    //URI may not be with parameters so maybe create own type - unconfirmed
    public VisionPostRequest(URI uri, RequestFunction function) {
        httpPost = new HttpPost(uri);
        this.function = function;
    }

    @Override
    public HttpPost getRequest() {
        return httpPost;
    }

    @Override
    public RequestFunction getFunction() {
        return function;
    }

    //Why constantly set sub key shouldn't just get from somewhere
    @Override
    public abstract void setSubscriptionKey(String subscriptionKey);

    public abstract void attachImage(Picture picture);
}
