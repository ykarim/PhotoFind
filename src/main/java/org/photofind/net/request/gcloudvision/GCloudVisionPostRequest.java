package org.photofind.net.request.gcloudvision;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.photofind.media.Picture;
import org.photofind.net.request.VisionPostRequest;
import org.photofind.net.request.VisionRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

public class GCloudVisionPostRequest extends VisionPostRequest {

    private final String SUBSCRIPTION_PARAM = "key";

    public GCloudVisionPostRequest(URI uri, VisionRequest.RequestFunction function) {
        super(uri, function);
    }

    @Override
    public void setSubscriptionKey(String subscriptionKey) {
        try {
            URI uriWithKey =
                    new URIBuilder(getRequest().getURI()).addParameter(SUBSCRIPTION_PARAM, subscriptionKey).build();
            getRequest().setURI(uriWithKey);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void attachImage(Picture picture) {
        String imageRequest = new JSONObject()
                .put("requests", new JSONArray()
                        .put(new JSONObject()
                                .put("features", new JSONArray()
                                        .put(new JSONObject()
                                                .put("type", "LABEL_DETECTION"))
                                        .put(new JSONObject()
                                                .put("type", "WEB_DETECTION")))
                                .put("image", new JSONObject()
                                        .put("content", encodeToBase64(picture)))))
                .toString();
        StringEntity newReq = new StringEntity(imageRequest, ContentType.APPLICATION_JSON);
        getRequest().setEntity(newReq);
    }

    private String encodeToBase64(Picture picture) {
        File file = picture.getFile();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStream.read(bytes);
            return new String(Base64.encodeBase64(bytes), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
