package org.photofind.net;

import org.apache.http.NameValuePair;

import java.util.List;

public class Parameter implements NameValuePair {

    private String parameterName, parameterValue;

    Parameter(String parameterName, String parameterValue) {
        this.parameterName = parameterName;
        this.parameterValue = parameterValue;
    }

    public Parameter(String parameterName, List<String> parameterValues) {
        if (parameterValues != null) {
            //Create new StringBuilder object from empty string
            StringBuilder paramValuesString = new StringBuilder();

            //Convert multiple string values into one String appended by commas
            for (String paramValue : parameterValues) {
                paramValuesString.append(paramValue).append(",");
            }

            //Remove last comma from string
            if (paramValuesString.toString().length() > 0) {
                paramValuesString.deleteCharAt(paramValuesString.length() - 1);
            }

            this.parameterName = parameterName;
            this.parameterValue = paramValuesString.toString();
        }
    }

    @Override
    public String getName() {
        return parameterName;
    }

    @Override
    public String getValue() {
        return parameterValue;
    }
}
