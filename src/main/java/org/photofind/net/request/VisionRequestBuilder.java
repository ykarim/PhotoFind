package org.photofind.net.request;

import org.photofind.media.Picture;
import org.photofind.net.Subscription;
import org.photofind.net.request.gcloudvision.GCloudVisionPostRequest;
import org.photofind.net.request.msvision.MSVisionPostRequest;

import java.net.URI;

public class VisionRequestBuilder {

    private static String MS_ANALYZE_FULL_REQUEST = "https://westcentralus.api.cognitive.microsoft.com/vision/v2.0/analyze" +
            "?visualFeatures=Categories%2CTags%2CDescription%2CFaces%2CImageType%2CColor%2CAdult" +
            "&details=Celebrities%2CLandmarks" +
            "&language=en";

    private static String GC_ANALYZE_FULL_REQUEST = "https://vision.googleapis.com/v1p3beta1/images:annotate";

    public static VisionPostRequest createAnalyzeRequest(Picture picture) {
        VisionPostRequest visionPostRequest = null;

        switch (Subscription.getCurrentSubscriptionProvider()) {
            default:
            case GOOGLE_CLOUD:
                visionPostRequest = new GCloudVisionPostRequest(URI.create(GC_ANALYZE_FULL_REQUEST),
                        VisionRequest.RequestFunction.ANALYZE);
                break;
            case MICROSOFT_VISION:
                visionPostRequest = new MSVisionPostRequest(URI.create(MS_ANALYZE_FULL_REQUEST),
                        VisionRequest.RequestFunction.ANALYZE);
                break;
        }

        visionPostRequest.attachImage(picture);
        visionPostRequest.setSubscriptionKey(Subscription.getSubscriptionKey());

        return visionPostRequest;
    }
}
