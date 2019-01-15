package com.minsoo.autocomplete.domain;

public class Refs {
    private String word;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }


    @Override
    public String toString(){
        return "{"
                + "word:" + word
                + "}";
    }
}
