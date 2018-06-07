package org.photofind.net;

import java.net.URI;
import java.net.URISyntaxException;

public class URIBuilder {

    private org.apache.http.client.utils.URIBuilder uriBuilder = null;

    private String region, API, version;

    public URIBuilder(String region, String API, String version) {
        this.region = region;
        this.API = API;
        this.version = version;
    }

    protected URI createURI(String functionName) {
        createBaseURI(functionName);

        try {
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected URI createURI(String function, ParameterList parameterList) {
        createBaseURI(function);
        setParameters(parameterList);

        try {
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void createBaseURI(String function) {
        if (function != null) {
            String urlString = URIStrings.PRE_REGION + region + URIStrings.POST_REGION + API + version + function;

            try {
                uriBuilder = new org.apache.http.client.utils.URIBuilder(urlString);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    private void setParameters(ParameterList parameterList) {
        if (parameterList != null) {
            for (Parameter parameter : parameterList.getParameters()) {
                uriBuilder.addParameter(parameter.getName(), parameter.getValue());
            }
        }
    }

}
