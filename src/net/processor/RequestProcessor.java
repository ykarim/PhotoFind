package net.processor;

import net.request.VisionRequest;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Callable;

public class RequestProcessor implements Callable<HttpResponse> {

    private static CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    private static Queue<VisionRequest> requestQueue = new LinkedList<>();
    private static Queue<LocalTime> timeStamps = new CircularFifoQueue<>(20); //Later on, implement own version instead of import

    public HttpResponse call() {
        //Check if request queue has 20 elements of more as Vision API limit is 20/minute
        if (requestQueue.size() >= 20) {
            //Calculate time difference between now and first request out of the last 20
            long timeDifference = Duration.between(timeStamps.peek(), LocalTime.now()).getSeconds();
            if (timeDifference < 60) {
                try {
                    //If time difference is less than a minute, wait till a minute passes
                    Thread.sleep(timeDifference * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                executeRequest();
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

        return response;
    }
}
