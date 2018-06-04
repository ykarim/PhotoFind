package process;

import javafx.concurrent.Task;
import media.Picture;
import media.descriptors.Description;
import media.descriptors.Tag;
import net.Connection;
import net.request.VisionRequest;
import net.request.VisionRequestBuilder;
import net.response.VisionResponse;
import net.response.data.VisionAnalyzeResponse;
import net.vision.VisionParameterBuilder;
import process.watcher.RequestProcessWatcher;
import util.VisionSettings;

import java.util.ArrayList;

//Extends Task to record and update progress
public class VisionAnalysisProcess extends Task<ArrayList<Picture>> implements RequestProcessWatcher {

    private ArrayList<Picture> pictures = new ArrayList<>();
    private VisionRequestBuilder requestBuilder;
    private int analyzedCount = 0;
    private int totalCount;

    private double sentRequests;

    public VisionAnalysisProcess(Picture picture) {
        requestBuilder = new VisionRequestBuilder(VisionSettings.REGION, VisionSettings.VERSION);
        Connection.getRequestProcessor().addWatcher(this);

        this.pictures.add(picture);
        this.totalCount = pictures.size();
    }

    public VisionAnalysisProcess(ArrayList<Picture> pictures) {
        requestBuilder = new VisionRequestBuilder(VisionSettings.REGION, VisionSettings.VERSION);
        Connection.getRequestProcessor().addWatcher(this);

        this.pictures = pictures;
        this.totalCount = pictures.size();
    }

    public ArrayList<Picture> call() {
        return analyzePictures();
    }

    private ArrayList<Picture> analyzePictures() {
        ArrayList<VisionRequest> analyzeRequests = new ArrayList<>();
        for (Picture picture : pictures) {
            analyzeRequests.add(createAnalyzeRequest(picture));
        }

        ArrayList<VisionResponse> responses = Connection.sendRequests(analyzeRequests);
        for (int index = 0; index < pictures.size(); index++) {
            if (responses.get(index) instanceof VisionAnalyzeResponse) {
                VisionAnalyzeResponse analyzeResponse = (VisionAnalyzeResponse) responses.get(index);
                //Add picture analysis (tags and description) to picture
                //Also deal with errors in response
                ArrayList<Tag> responseTags = analyzeResponse.getTags();
                Description responseDescription = analyzeResponse.getDescription();

                if (responseTags != null) {
                    pictures.get(index).getTags().addAll(responseTags);
                }

                if (responseDescription != null) {
                    if (responseDescription.getCaptions() != null) {
                        pictures.get(index).getDescription().getCaptions().addAll(responseDescription.getCaptions());
                    }

                    if (responseDescription.getTags() != null) {
                        pictures.get(index).getDescription().getTags().addAll(responseDescription.getTags());
                    }
                }

                analyzedCount++;
                updateProgress(sentRequests + analyzedCount, totalCount * 2);
            }
        }

        return pictures;
    }

    private VisionRequest createAnalyzeRequest(Picture picture) {
        //Create easier way to do this than 100 wrapper classes. Feature: addAll() method to specify all param values
        ArrayList<VisionParameterBuilder.AnalyzeImageParamValue> visualFeatures = new ArrayList<>();
        visualFeatures.add(VisionParameterBuilder.AnalyzeImageParamValue.CATEGORIES);
        visualFeatures.add(VisionParameterBuilder.AnalyzeImageParamValue.TAGS);
        visualFeatures.add(VisionParameterBuilder.AnalyzeImageParamValue.DESCRIPTION);
        visualFeatures.add(VisionParameterBuilder.AnalyzeImageParamValue.FACES);
        visualFeatures.add(VisionParameterBuilder.AnalyzeImageParamValue.IMAGETYPE);
        visualFeatures.add(VisionParameterBuilder.AnalyzeImageParamValue.COLOR);
        visualFeatures.add(VisionParameterBuilder.AnalyzeImageParamValue.ADULT);

        ArrayList<VisionParameterBuilder.AnalyzeImageParamValue> details = new ArrayList<>();
        details.add(VisionParameterBuilder.AnalyzeImageParamValue.CELEBRITIES);
        details.add(VisionParameterBuilder.AnalyzeImageParamValue.LANDMARKS);

        return requestBuilder.createAnaylzeRequest(visualFeatures, details,
                VisionParameterBuilder.AnalyzeImageParamValue.ENGLISH, picture);
    }

    @Override
    public void updateSentRequests(int sentRequests) {
        this.sentRequests = sentRequests;
        updateProgress(this.sentRequests, totalCount * 2);
    }
}
