package org.photofind.net.request;

import org.photofind.media.Picture;
import org.photofind.net.ParameterList;
import org.photofind.net.Subscription;
import org.photofind.net.vision.VisionParameterBuilder;
import org.photofind.net.vision.VisionURIBuilder;

import java.util.ArrayList;

public class VisionRequestBuilder {

    private VisionURIBuilder visionURIBuilder;
    private VisionParameterBuilder visionParameterBuilder = new VisionParameterBuilder();

    public VisionRequestBuilder(String region, String API_VERSION) {
        visionURIBuilder = new VisionURIBuilder(region, API_VERSION);
    }

    //Create better enums so that vis features and details are separate
    public VisionRequest createAnaylzeRequest(ArrayList<VisionParameterBuilder.AnalyzeImageParamValue> visualFeatures,
                                              ArrayList<VisionParameterBuilder.AnalyzeImageParamValue> details,
                                              VisionParameterBuilder.AnalyzeImageParamValue language,
                                              Picture picture) {
        ParameterList parameterList = new ParameterList();

        if (visualFeatures != null && visualFeatures.size() > 0) {
            parameterList.addParameter(
                    visionParameterBuilder.createAnalyzeParameter(
                            VisionParameterBuilder.AnalyzeImageParamName.VISUAL_FEATURES, visualFeatures));
        }

        if (details != null && details.size() > 0) {
            parameterList.addParameter(
                    visionParameterBuilder.createAnalyzeParameter(
                            VisionParameterBuilder.AnalyzeImageParamName.DETAILS, details));
        }

        if (language != null) {
            parameterList.addParameter(VisionParameterBuilder.AnalyzeImageParamName.LANGUAGE.getParameterName(),
                    language.getParameterValue());
        }

        VisionPostRequest visionPostRequest =
                new VisionPostRequest(visionURIBuilder.getAnalyzeImageURI(parameterList), VisionFunction.ANALYZE);
        visionPostRequest.attachImage(picture);
        visionPostRequest.setSubscriptionKey(Subscription.getSubscriptionKey()); //Do we want to set subkey here?

        return visionPostRequest;
    }
}
