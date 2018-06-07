package org.photofind.net.vision;

import org.photofind.net.Parameter;

import java.util.ArrayList;
import java.util.List;

public class VisionParameterBuilder {

    public Parameter createAnalyzeParameter(AnalyzeImageParamName paramName,
                                            List<AnalyzeImageParamValue> paramValues) {
        List<String> parameterValuesStrings = new ArrayList<>();
        for (AnalyzeImageParamValue paramValue : paramValues) {
            parameterValuesStrings.add(paramValue.getParameterValue());
        }

        return new Parameter(paramName.getParameterName(), parameterValuesStrings);
    }

    public Parameter createDescribeParameter(DescribeImageParamName paramName, List<String> paramValues) {
        return new Parameter(paramName.getParameterName(), paramValues);
    }

    public Parameter createGetHandwrittenTextParameter(GetHandwrittenTextParamName paramName,
                                                       List<String> paramValues) {
        return new Parameter(paramName.getParameterName(), paramValues);
    }

    public Parameter createGenThumbnailParameter(GenThumbnailParamName paramName, List<String> paramValues) {
        return new Parameter(paramName.getParameterName(), paramValues);
    }

    public Parameter createOCRParameter(OCRParamName paramName,
                                        List<OCRParamValue> paramValues) {
        List<String> parameterValuesStrings = new ArrayList<>();
        for (OCRParamValue paramValue : paramValues) {
            parameterValuesStrings.add(paramValue.getParameterValue());
        }

        return new Parameter(paramName.getParameterName(), parameterValuesStrings);
    }

    public Parameter createRecogDomainContentParameter(RecogDomainContentParamName paramName,
                                                       RecogDomainContentParamValue paramValue) {
        List<String> parameterValuesStrings = new ArrayList<>();
        parameterValuesStrings.add(paramValue.getParameterValue());

        return new Parameter(paramName.getParameterName(), parameterValuesStrings);
    }

    public Parameter createRecogTextParameter(RecogTextParamName paramName,
                                              List<RecogTextParamValue> paramValues) {
        List<String> parameterValuesStrings = new ArrayList<>();
        for (RecogTextParamValue paramValue : paramValues) {
            parameterValuesStrings.add(paramValue.getParameterValue());
        }

        return new Parameter(paramName.getParameterName(), parameterValuesStrings);
    }

    public enum AnalyzeImageParamName {
        VISUAL_FEATURES("visualFeatures"),
        DETAILS("details"),
        LANGUAGE("language");

        private String parameterName;

        AnalyzeImageParamName(String parameterName) {
            this.parameterName = parameterName;
        }

        public String getParameterName() {
            return parameterName;
        }
    }

    public enum AnalyzeImageParamValue {
        CATEGORIES("Categories"),
        TAGS("Tags"),
        DESCRIPTION("Description"),
        FACES("Faces"),
        IMAGETYPE("ImageType"),
        COLOR("Color"),
        ADULT("Adult"),

        CELEBRITIES("Celebrities"),
        LANDMARKS("Landmarks"),

        ENGLISH("en"),
        CHINESE("zh");

        private String parameterValue;

        AnalyzeImageParamValue(String parameterValue) {
            this.parameterValue = parameterValue;
        }

        public String getParameterValue() {
            return parameterValue;
        }
    }

    public enum DescribeImageParamName {
        MAX_CANDIDATES("maxCandidates");

        private String parameterName;

        DescribeImageParamName(String parameterName) {
            this.parameterName = parameterName;
        }

        public String getParameterName() {
            return parameterName;
        }
    }

    public enum GetHandwrittenTextParamName {
        OPERATION_ID("operationId");

        private String parameterName;

        GetHandwrittenTextParamName(String parameterName) {
            this.parameterName = parameterName;
        }

        public String getParameterName() {
            return parameterName;
        }
    }

    public enum GenThumbnailParamName {
        WIDTH("width"),
        HEIGHT("height"),
        SMART_CROPPING("smartCropping");

        private String parameterName;

        GenThumbnailParamName(String parameterName) {
            this.parameterName = parameterName;
        }

        public String getParameterName() {
            return parameterName;
        }
    }

    public enum OCRParamName {
        LANGUAGE("language"),
        DETECT_ORIENTATION("detectOrientation");

        private String parameterName;

        OCRParamName(String parameterName) {
            this.parameterName = parameterName;
        }

        public String getParameterName() {
            return parameterName;
        }
    }

    public enum OCRParamValue {
        AUTO("unk"),
        CHINESE_SIMPLE("zh-Hans"),
        CHINESE_TRADITIONAL("zh-Hant"),
        CZECH("cs"),
        DANISH("da"),
        DUTCH("nl"),
        ENGLISH("en"),
        FINNISH("fi"),
        FRENCH("fr"),
        GERMAN("de"),
        GREEK("el"),
        HUNGARIAN("hu"),
        ITALIAN("it"),
        JAPANESE("ja"),
        KOREAN("ko"),
        NORWEGIAN("nb"),
        POLISH("pl"),
        PORTUGESE("pt"),
        RUSSIAN("ru"),
        SPANISH("es"),
        SWEDISH("sv"),
        TURKISH("tr"),
        ARABIC("ar"),
        ROMANIAN("ro"),
        SERBIAN_CRYLLIC("sr-Cyrl"),
        SERBIAN_LATIN("sr-Latn"),
        SLOVAK("sk"),

        TRUE("true"),
        FALSE("false");

        private String parameterValue;

        OCRParamValue(String parameterValue) {
            this.parameterValue = parameterValue;
        }

        public String getParameterValue() {
            return parameterValue;
        }
    }

    public enum RecogDomainContentParamName {
        MODEL("model");

        private String parameterName;

        RecogDomainContentParamName(String parameterName) {
            this.parameterName = parameterName;
        }

        public String getParameterName() {
            return parameterName;
        }
    }

    public enum RecogDomainContentParamValue {
        CELEBRITIES("celebrities"),
        LANDMARKS("landmarks");

        private String parameterValue;

        RecogDomainContentParamValue(String parameterValue) {
            this.parameterValue = parameterValue;
        }

        public String getParameterValue() {
            return parameterValue;
        }
    }

    public enum RecogTextParamName {
        HANDWRITING("handwriting");

        private String parameterName;

        RecogTextParamName(String parameterName) {
            this.parameterName = parameterName;
        }

        public String getParameterName() {
            return parameterName;
        }
    }

    public enum RecogTextParamValue {
        TRUE("true"),
        FALSE("false");

        private String parameterValue;

        RecogTextParamValue(String parameterValue) {
            this.parameterValue = parameterValue;
        }

        public String getParameterValue() {
            return parameterValue;
        }
    }
}
