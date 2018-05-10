package net.vision;

import net.Parameter;
import net.ParameterList;
import net.URIBuilder;
import net.URIStrings;

import java.net.URI;

public class VisionURIBuilder extends URIBuilder {

    public VisionURIBuilder(String region, String version) {
        super(region, URIStrings.VISION_API, version);
    }

    //mb use vision func instead of uri strings?
    public URI getAnalyzeImageURI(ParameterList parameters) {
        return this.createURI(URIStrings.ANALYZE, parameters);
    }

    public URI getDescribeImageURI(ParameterList parameters) {
        return this.createURI(URIStrings.DESCRIBE, parameters);
    }

    public URI getGetHandwrittenTextURI(ParameterList parameters) {
        return this.createURI(URIStrings.GET_HANDWRITTEN_TEXT, parameters);
    }

    public URI getGenThumbnailURI(ParameterList parameters) {
        return this.createURI(URIStrings.GENERATE_THUMBNAIL, parameters);
    }

    public URI getListDomainModelsURI(ParameterList parameters) {
        return this.createURI(URIStrings.LIST_DOMAIN_MODELS);
    }

    public URI getOCRURI(ParameterList parameters) {
        return this.createURI(URIStrings.OBJECT_CHARACTER_RECOG, parameters);
    }

    //Feels hacky
    public URI getRecogDomainContentURI(ParameterList parameters) {
        //Instantiate string to hold "model" parameter value
        String parameterValue = "";

        if (parameters != null) {
            if (parameters.getFirstParameter() != null) {
                //Obtain "model" parameter and set to parameterValue string
                Parameter recogDomainContentParameter = parameters.getFirstParameter();
                parameterValue = recogDomainContentParameter.getValue();
            }
        }

        //Return newly created URI with parameterValue formatted within
        return this.createURI(String.format(URIStrings.RECOG_DOMAIN_CONTENT, parameterValue));
    }

    public URI getRecogTextURI(ParameterList parameters) {
        return this.createURI(URIStrings.RECOG_TEXT, parameters);
    }

    public URI getTagURI(ParameterList parameters) {
        return this.createURI(URIStrings.TAG_IMAGE);
    }
}
