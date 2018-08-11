package org.photofind.net.response.parser.gcloudvision;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.photofind.media.descriptors.Description;
import org.photofind.media.descriptors.Tag;
import org.photofind.net.response.VisionResponseParser;
import org.photofind.net.response.data.VisionAnalyzeResponse;

import java.io.IOException;
import java.util.ArrayList;

public class GCloudVisionResponseParser extends VisionResponseParser {

    @Override
    public VisionAnalyzeResponse parseAnalyzeResponse(HttpResponse response) throws IOException {
        if (response.getEntity() != null) {
            JSONObject jsonResponse = new JSONObject(EntityUtils.toString(response.getEntity()));

            JSONArray responsesArray = jsonResponse.getJSONArray(GCloudVisionResponseStrings.RESPONSES_ARRAY);

            JSONArray labelAnnotationsArray = responsesArray.getJSONObject(0)
                    .getJSONArray(GCloudVisionResponseStrings.LABEL_ANNOTATIONS_ARRAY);
            ArrayList<Tag> pictureTags = new ArrayList<>();
            for (int index = 0; index < labelAnnotationsArray.length(); index++) {
                JSONObject labelAnnotationObject = labelAnnotationsArray.getJSONObject(index);

                String tagName = labelAnnotationObject.getString(GCloudVisionResponseStrings.DESCRIPTION_OBJECT_NAME);
                Double tagScore = labelAnnotationObject.getDouble(GCloudVisionResponseStrings.SCORE_OBJECT_NAME) * 100;
                pictureTags.add(new Tag(tagName, tagScore));
            }

            return new VisionAnalyzeResponse(response.getStatusLine(), pictureTags,
                    new Description(null, null));
        }

        return new VisionAnalyzeResponse(response.getStatusLine());
    }
}
