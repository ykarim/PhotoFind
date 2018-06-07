package org.photofind.net.request;

//Maybe one day implement into making requests and convert into class
//Main blocker is diff parameters for each func
//Maybe use RequestType to also make it easy to send request
//Clean it up so it doesnt take 5 lines to make params
//Also maybe store URL in these
public enum VisionFunction {
    ANALYZE(RequestType.POST),
    DESCRIBE(RequestType.POST),
    GET_HANDWRITTEN_TEXT(RequestType.GET),
    GENERATE_THUMBNAIL(RequestType.POST),
    LIST_DOMAIN_MODELS(RequestType.GET),
    OBJECT_CHARACTER_RECOG(RequestType.POST),
    RECOG_DOMAIN_CONTENT(RequestType.POST),
    RECOG_TEXT(RequestType.POST),
    TAG_IMAGE(RequestType.POST);

    private RequestType requestType;

    VisionFunction(RequestType requestType) {
        this.requestType = requestType;
    }

    public RequestType getRequestType() {
        return requestType;
    }
}
