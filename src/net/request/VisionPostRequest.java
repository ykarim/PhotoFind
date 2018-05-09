package net.request;

import media.Picture;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.net.URI;

//Extend HttpPost instead of reinventing the wheel and constantly using getter?
public class VisionPostRequest implements VisionRequest {

    private HttpPost httpPost;

    //URI may not be with parameters so maybe create own type - unconfirmed
    public VisionPostRequest(URI uri) {
        httpPost = new HttpPost(uri);
    }

    @Override
    public HttpUriRequest getRequest() {
        return httpPost;
    }

    @Override
    public void setSubscriptionKey(String subscriptionKey) {
        httpPost.setHeader(RequestStrings.SUBSCRIPTION_HEADER, subscriptionKey);
    }

    public void attachImage(Picture picture) {
        HttpEntity newReq = MultipartEntityBuilder
                .create()
                .addBinaryBody(RequestStrings.FILE_MULTIPART_NAME, picture.getFile(),
                        ContentType.DEFAULT_BINARY, picture.getFile().getName())
                .build();
        httpPost.setEntity(newReq);
    }

}