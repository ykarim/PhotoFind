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
    private final String SUBSCRIPTION_HEADER = "Ocp-Apim-Subscription-Key";
    private final String FILE_MULTIPART_NAME = "file_upload";
    private RequestFunction function;

    //URI may not be with parameters so maybe create own type - unconfirmed
    VisionPostRequest(URI uri, RequestFunction function) {
        httpPost = new HttpPost(uri);
        this.function = function;
    }

    @Override
    public HttpUriRequest getRequest() {
        return httpPost;
    }

    @Override
    public RequestFunction getFunction() {
        return function;
    }

    //Why constantly set sub key shouldn't just get from somewhere
    @Override
    public void setSubscriptionKey(String subscriptionKey) {
        httpPost.setHeader(SUBSCRIPTION_HEADER, subscriptionKey);
    }

    void attachImage(Picture picture) {
        HttpEntity newReq = MultipartEntityBuilder
                .create()
                .addBinaryBody(FILE_MULTIPART_NAME, picture.getFile(),
                        ContentType.DEFAULT_BINARY, picture.getFile().getName())
                .build();
        httpPost.setEntity(newReq);
    }
}
