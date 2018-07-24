package org.photofind.process;

import javafx.concurrent.Task;
import org.photofind.media.Picture;
import org.photofind.media.descriptors.Description;
import org.photofind.media.descriptors.Tag;
import org.photofind.net.Connection;
import org.photofind.net.request.VisionRequest;
import org.photofind.net.request.VisionRequestBuilder;
import org.photofind.net.response.VisionResponse;
import org.photofind.net.response.data.VisionAnalyzeResponse;
import org.photofind.process.watcher.RequestProcessWatcher;

import java.util.ArrayList;

//Extends Task to record and update progress
public class VisionAnalysisProcess extends Task<ArrayList<Picture>> implements RequestProcessWatcher {

    private ArrayList<Picture> pictures = new ArrayList<>();
    private int analyzedCount = 0;
    private int totalCount;

    private double sentRequests;

    public VisionAnalysisProcess(Picture picture) {
        Connection.getRequestProcessor().addWatcher(this);

        this.pictures.add(picture);
        this.totalCount = pictures.size();
    }

    public VisionAnalysisProcess(ArrayList<Picture> pictures) {
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

                //Why not setTags()?
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
        return VisionRequestBuilder.createAnalyzeRequest(picture);
    }

    @Override
    public void updateSentRequests(int sentRequests) {
        this.sentRequests = sentRequests;
        updateProgress(this.sentRequests, totalCount * 2);
    }
}
