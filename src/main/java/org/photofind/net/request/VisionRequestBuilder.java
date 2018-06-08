package org.photofind.net.request;

import org.photofind.media.Picture;
import org.photofind.net.Subscription;

import java.net.URI;

public class VisionRequestBuilder {

    private static String ANALYZE_FULL_REQUEST = "https://westcentralus.api.cognitive.microsoft.com/vision/v2.0/analyze" +
            "?visualFeatures=Categories%2CTags%2CDescription%2CFaces%2CImageType%2CColor%2CAdult" +
            "&details=Celebrities%2CLandmarks" +
            "&language=en";

    public static VisionPostRequest createAnalyzeRequest(Picture picture) {

        VisionPostRequest visionPostRequest =
                new VisionPostRequest(URI.create(ANALYZE_FULL_REQUEST), VisionRequest.RequestFunction.ANALYZE);
        visionPostRequest.attachImage(picture);
        visionPostRequest.setSubscriptionKey(Subscription.getSubscriptionKey());

        return visionPostRequest;
    }
}
