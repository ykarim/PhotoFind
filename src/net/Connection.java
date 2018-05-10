package net;

import net.request.VisionRequest;
import net.response.VisionResponse;
import net.response.VisionResponseParser;
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
     * @param request Get/Post request to send to server with image attached
     * @return change response type to program legible
     */
    public static VisionResponse sendRequest(VisionRequest request) {
        try {
            HttpResponse response = httpClient.execute(request.getRequest());

            switch (request.getVisionFunction()) {

                case ANALYZE:
                    return VisionResponseParser.parseResponse(request.getVisionFunction(), response);
                case DESCRIBE:
                    break;
                case GET_HANDWRITTEN_TEXT:
                    break;
                case GENERATE_THUMBNAIL:
                    break;
                case LIST_DOMAIN_MODELS:
                    break;
                case OBJECT_CHARACTER_RECOG:
                    break;
                case RECOG_DOMAIN_CONTENT:
                    break;
                case RECOG_TEXT:
                    break;
                case TAG_IMAGE:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
