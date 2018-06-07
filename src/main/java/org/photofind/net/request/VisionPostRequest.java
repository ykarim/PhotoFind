package org.photofind.net.request;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.photofind.media.Picture;

import java.net.URI;

//Extend HttpPost instead of reinventing the wheel and constantly using getter?
public class VisionPostRequest implements VisionRequest {

    private HttpPost httpPost;
    private VisionFunction visionFunction;

    //URI may not be with parameters so maybe create own type - unconfirmed
    VisionPostRequest(URI uri, VisionFunction visionFunction) {
        httpPost = new HttpPost(uri);
        this.visionFunction = visionFunction;
    }

    @Override
    public HttpUriRequest getRequest() {
        return httpPost;
    }

    @Override
    public VisionFunction getVisionFunction() {
        return visionFunction;
    }

    //Why constantly set sub key shouldn't just get from somewhere
    @Override
    public void setSubscriptionKey(String subscriptionKey) {
        httpPost.setHeader(RequestStrings.SUBSCRIPTION_HEADER, subscriptionKey);
    }

    void attachImage(Picture picture) {
        HttpEntity newReq = MultipartEntityBuilder
                .create()
                .addBinaryBody(RequestStrings.FILE_MULTIPART_NAME, picture.getFile(),
                        ContentType.DEFAULT_BINARY, picture.getFile().getName())
                .build();
        httpPost.setEntity(newReq);
    }
}
