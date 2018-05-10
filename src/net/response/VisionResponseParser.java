package net.response;

import media.descriptors.Caption;
import media.descriptors.Description;
import media.descriptors.Tag;
import net.request.VisionFunction;
import net.response.data.VisionAnalyzeResponse;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class VisionResponseParser {

    /**
     * Creates new picture file from response
     *
     * @param function Contains vision function requested
     */
    //Maybe create a system used for requests and repsonses where post and get funcs can be easily tagged and identified
    //Maybe a complex enum of both
    //Also func wont always return PictureDescriptor
    public static VisionResponse parseResponse(VisionFunction function, HttpResponse response) throws IOException {
        switch (function) {

            case ANALYZE:
                return parseAnalyzeResponse(response);
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

        return null;
    }

    //Should I throw or try catch
    private static VisionAnalyzeResponse parseAnalyzeResponse(HttpResponse response) throws IOException {
        if (response.getEntity() != null) {
            JSONObject jsonResponse = new JSONObject(EntityUtils.toString(response.getEntity()));

            JSONObject descriptionObj = jsonResponse.getJSONObject(ResponseStrings.DESCRIPTION_OBJECT);

            JSONArray descriptionCaptionsArray = descriptionObj.getJSONArray(ResponseStrings.DESCRIPTION_CAPTIONS_ARRAY);
            ArrayList<Caption> captions = new ArrayList<>();
            for (int index = 0; index < descriptionCaptionsArray.length(); index++) {
                String captionAtIndex =
                        descriptionCaptionsArray.getJSONObject(index).getString(ResponseStrings.CAPTIONS_TEXT_STRING);
                Double captionConfidence =
                        descriptionCaptionsArray.getJSONObject(index).getDouble(ResponseStrings.CAPTIONS_DOUBLE_STRING);
                captions.add(new Caption(captionAtIndex, captionConfidence));
            }

            JSONArray descriptionTagsArray = descriptionObj.getJSONArray(ResponseStrings.DESCRIPTION_TAGS_ARRAY);
            ArrayList<Tag> descriptionTags = new ArrayList<>();
            for (int index = 0; index < descriptionTagsArray.length(); index++) {
                String tagName = descriptionTagsArray.getString(index);
                descriptionTags.add(new Tag(tagName));
            }

            Description pictureDescription = new Description(captions, descriptionTags);

            JSONArray tagsArray = jsonResponse.getJSONArray(ResponseStrings.TAGS_ARRAY);
            ArrayList<Tag> tags = new ArrayList<>();
            for (int index = 0; index < tagsArray.length(); index++) {
                JSONObject tagObject = tagsArray.getJSONObject(index);

                String tagName = tagObject.getString(ResponseStrings.TAGS_OBJECT_NAME);
                Double tagConfidence = tagObject.getDouble(ResponseStrings.TAGS_OBJECT_CONFIDENCE);
                tags.add(new Tag(tagName, tagConfidence));
            }

            //dont want to create new picture but could create picturedescriptor class to hold this data and let sys extract from and apply to pic
            return new VisionAnalyzeResponse(response.getStatusLine(), tags, pictureDescription);
        }

        return new VisionAnalyzeResponse(response.getStatusLine());
    }
}
