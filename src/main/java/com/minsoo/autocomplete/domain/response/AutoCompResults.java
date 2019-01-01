package com.minsoo.autocomplete.domain.response;

import java.util.List;

public class AutoCompResults {

    private List autocompList;
    private double responseTime;

    public List getAutocompList() {
        return autocompList;
    }

    public void setAutocompList(List autocompList) {
        this.autocompList = autocompList;
    }

    public double getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(double responseTime) {
        this.responseTime = responseTime;
    }
}
