package net;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParameterList {

    private List<Parameter> parameters = new ArrayList<>();

    public ParameterList() {

    }

    public ParameterList(Parameter... parameters) {
        if (parameters != null) {
            Collections.addAll(this.parameters, parameters);
        }
    }

    public void addParameter(Parameter parameter) {
        parameters.add(parameter);
    }

    public void addParameter(String parameterName, String parameterValue) {
        parameters.add(new Parameter(parameterName, parameterValue));
    }

    List<Parameter> getParameters() {
        return parameters;
    }

    public Parameter getFirstParameter() {
        if (parameters.size() > 0) {
            return parameters.get(0);
        }

        return null;
    }
}
