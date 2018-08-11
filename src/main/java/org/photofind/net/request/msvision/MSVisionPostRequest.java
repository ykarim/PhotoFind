package org.photofind.net.request.msvision;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.photofind.media.Picture;
import org.photofind.net.request.VisionPostRequest;

import java.net.URI;

public class MSVisionPostRequest extends VisionPostRequest {

    private final String SUBSCRIPTION_HEADER = "Ocp-Apim-Subscription-Key";
    private final String FILE_MULTIPART_NAME = "file_upload";

    public MSVisionPostRequest(URI uri, RequestFunction function) {
        super(uri, function);
    }

    @Override
    public void setSubscriptionKey(String subscriptionKey) {
        getRequest().setHeader(SUBSCRIPTION_HEADER, subscriptionKey);
    }

    public void attachImage(Picture picture) {
        HttpEntity newReq = MultipartEntityBuilder
                .create()
                .addBinaryBody(FILE_MULTIPART_NAME, picture.getFile(),
                        ContentType.DEFAULT_BINARY, picture.getFile().getName())
                .build();
        getRequest().setEntity(newReq);
    }
}
