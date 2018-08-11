package org.photofind.net;

import org.apache.http.HttpResponse;
import org.photofind.net.processor.RequestProcessor;
import org.photofind.net.request.VisionRequest;
import org.photofind.net.response.VisionResponse;
import org.photofind.net.response.VisionResponseParser;
import org.photofind.net.response.parser.gcloudvision.GCloudVisionResponseParser;
import org.photofind.net.response.parser.msvision.MSVisionResponseParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Connection {

    private static ExecutorService executorService = Executors.newCachedThreadPool();
    private static RequestProcessor requestProcessor = new RequestProcessor();
    private static VisionResponseParser visionResponseParser = null;

    public static RequestProcessor getRequestProcessor() {
        return requestProcessor;
    }

    /**
     * @param request Get/Post request to send to server with image attached
     * @return change response type to program legible
     */
    public static VisionResponse sendRequest(VisionRequest request) {
        try {
            requestProcessor.addRequestToQueue(request);

            //Get response after processing request
            //Waits for response if request queue is busy processing
            //Should hold network thread and not current or UI thread if waiting
            //Should allow for more requests to be added to queue during wait
            HttpResponse response = executorService.submit(requestProcessor).get();
            return processResponse(request.getFunction(), response);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<VisionResponse> sendRequests(ArrayList<VisionRequest> requests) {
        ArrayList<VisionResponse> responses = new ArrayList<>();

        requestProcessor.addRequestsToQueue(requests);
        for (VisionRequest request : requests) {
            try {
                HttpResponse response = executorService.submit(requestProcessor).get();
                responses.add(processResponse(request.getFunction(), response));
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return responses;
    }

    private static VisionResponse processResponse(VisionRequest.RequestFunction requestFunction, HttpResponse response) {
        //TODO: Change so that previous current provider is checked to prevent redundant creation
        switch (VisionProvider.getCurrentVisionProvider()) {

            case GOOGLE_CLOUD:
                visionResponseParser = new GCloudVisionResponseParser();
                break;
            case MICROSOFT_VISION:
                visionResponseParser = new MSVisionResponseParser();
                break;
        }

        try {
            return visionResponseParser.parseResponse(requestFunction, response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
