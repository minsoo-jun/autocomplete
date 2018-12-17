package com.minsoo.autocomplete.domain.response;

public class AutocompleteDomain {
    private String name;
    private double score;
    private String sku;
    private String pharse;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getPharse() {
        return pharse;
    }

    public void setPharse(String pharse) {
        this.pharse = pharse;
    }

    @Override
    public String toString(){
        return "{"
                + "phase:" + pharse
                + "name:" + name
                + "score:" + score
                + "sku:" + sku
                + "}";
    }


}
