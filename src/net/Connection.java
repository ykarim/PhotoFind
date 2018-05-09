package net;

import net.request.VisionRequest;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class Connection {

    private static CloseableHttpClient httpClient = HttpClientBuilder.create().build();

    public static CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    /**
     * @param request Get/Post request to send to server
     * @return change reponse type to program legible
     */
    public static HttpResponse sendRequest(VisionRequest request) {
        try {
            return httpClient.execute(request.getRequest());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
