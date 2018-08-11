package org.photofind.net.response;

import org.apache.http.HttpResponse;
import org.photofind.net.request.VisionRequest;
import org.photofind.net.response.data.VisionAnalyzeResponse;

import java.io.IOException;

public abstract class VisionResponseParser {

    /**
     * Creates new picture file from response
     *
     * @param requestFunction Contains vision function requested
     */
    //Maybe create a system used for requests and repsonses where post and get funcs can be easily tagged and identified
    //Maybe a complex enum of both
    //Also func wont always return PictureDescriptor
    public VisionResponse parseResponse(VisionRequest.RequestFunction requestFunction, HttpResponse response) throws IOException {
        switch (requestFunction) {
            case ANALYZE:
                return parseAnalyzeResponse(response);
        }

        return null;
    }

    //Should I throw or try catch
    public abstract VisionAnalyzeResponse parseAnalyzeResponse(HttpResponse response) throws IOException;
}
