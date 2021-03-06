package org.photofind.net.response.parser.msvision;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.photofind.media.descriptors.Caption;
import org.photofind.media.descriptors.Description;
import org.photofind.media.descriptors.Tag;
import org.photofind.net.response.VisionResponseParser;
import org.photofind.net.response.data.VisionAnalyzeResponse;

import java.io.IOException;
import java.util.ArrayList;

public class MSVisionResponseParser extends VisionResponseParser {

    @Override
    public VisionAnalyzeResponse parseAnalyzeResponse(HttpResponse response) throws IOException {
        if (response.getEntity() != null) {
            JSONObject jsonResponse = new JSONObject(EntityUtils.toString(response.getEntity()));

            JSONObject descriptionObj = jsonResponse.getJSONObject(MSVisionResponseStrings.DESCRIPTION_OBJECT);

            JSONArray descriptionCaptionsArray = descriptionObj.getJSONArray(MSVisionResponseStrings.DESCRIPTION_CAPTIONS_ARRAY);
            ArrayList<Caption> captions = new ArrayList<>();
            for (int index = 0; index < descriptionCaptionsArray.length(); index++) {
                String captionAtIndex =
                        descriptionCaptionsArray.getJSONObject(index).getString(MSVisionResponseStrings.CAPTIONS_TEXT_STRING);
                Double captionConfidence =
                        descriptionCaptionsArray.getJSONObject(index).getDouble(MSVisionResponseStrings.CAPTIONS_DOUBLE_STRING);
                captions.add(new Caption(captionAtIndex, captionConfidence));
            }

            JSONArray descriptionTagsArray = descriptionObj.getJSONArray(MSVisionResponseStrings.DESCRIPTION_TAGS_ARRAY);
            ArrayList<Tag> descriptionTags = new ArrayList<>();
            for (int index = 0; index < descriptionTagsArray.length(); index++) {
                String tagName = descriptionTagsArray.getString(index);
                descriptionTags.add(new Tag(tagName));
            }

            Description pictureDescription = new Description(captions, descriptionTags);

            JSONArray tagsArray = jsonResponse.getJSONArray(MSVisionResponseStrings.TAGS_ARRAY);
            ArrayList<Tag> tags = new ArrayList<>();
            for (int index = 0; index < tagsArray.length(); index++) {
                JSONObject tagObject = tagsArray.getJSONObject(index);

                String tagName = tagObject.getString(MSVisionResponseStrings.TAGS_OBJECT_NAME);
                Double tagConfidence = tagObject.getDouble(MSVisionResponseStrings.TAGS_OBJECT_CONFIDENCE);
                tags.add(new Tag(tagName, tagConfidence));
            }

            //dont want to create new picture but could create picturedescriptor class to hold this data and let sys extract from and apply to pic
            return new VisionAnalyzeResponse(response.getStatusLine(), tags, pictureDescription);
        }

        return new VisionAnalyzeResponse(response.getStatusLine());
    }
}
