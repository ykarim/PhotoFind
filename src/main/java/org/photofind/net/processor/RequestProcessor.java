package org.photofind.net.processor;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.photofind.net.request.VisionRequest;
import org.photofind.process.watcher.RequestProcessWatcher;
import org.photofind.process.watcher.WatchableProcess;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Callable;

//Implements Watcher system as Task requires waiting for request thread finish (setOnSucceeded())before extracting values which would lead
// to more memory consumption as extra arraylist for results and infinite while loop to keep request thread running
//Watcher also used to retrieve progress easily
//Future<> return value may also be better with threads
public class RequestProcessor implements Callable<HttpResponse>, WatchableProcess<RequestProcessWatcher> {

    private static CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    private static Queue<VisionRequest> requestQueue = new LinkedList<>();
    private static Queue<LocalTime> timeStamps = new CircularFifoQueue<>(20); //Later on, implement own version instead of import

    private ArrayList<RequestProcessWatcher> watchers = new ArrayList<>();

    private int sentRequests = 0;
    private int totalRequests = 0;

    public HttpResponse call() {
        //Check if timestamps queue has 20 elements as this would indicate requests may exceed Vision API limit of 20/minute
        if (timeStamps.size() == 20) {
            //Calculate time difference between now and first request out of the last 20
            long timeDifference = Duration.between(timeStamps.peek(), LocalTime.now()).getSeconds();

            if (timeDifference <= 60) {
                try {
                    //If time difference is less than a minute, wait for remaining time difference
                    //when a minute will pass since last 20th request
                    Thread.sleep((60 - timeDifference) * 1000);
                    return executeRequest();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                return executeRequest();
            }
        } else {
            return executeRequest();
        }

        return null;
    }

    public void addRequestToQueue(VisionRequest request) {
        requestQueue.add(request);
        sentRequests = 0;
        totalRequests = 1;
    }

    public void addRequestsToQueue(ArrayList<VisionRequest> requests) {
        requestQueue.addAll(requests);
        sentRequests = 0;
        totalRequests = requests.size();
    }

    private HttpResponse executeRequest() {
        HttpResponse response = null;
        try {
            response = httpClient.execute(requestQueue.peek().getRequest());
        } catch (IOException e) {
            e.printStackTrace();
        }

        timeStamps.add(LocalTime.now());
        requestQueue.remove();
        sentRequests++;

        updateWatchers();

        return response;
    }

    @Override
    public void addWatcher(RequestProcessWatcher watcher) {
        watchers.add(watcher);
    }

    @Override
    public void updateWatchers() {
        for (RequestProcessWatcher watcher : watchers) {
            watcher.updateSentRequests(sentRequests);
        }
    }
}
