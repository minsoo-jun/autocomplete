package com.minsoo.autocomplete.domain.request;

import static com.minsoo.autocomplete.constants.Constants.DEFAULT_LIMITED_NUMBER;

public class RequestParams {

    private String searchWord;
    private String language;
    private int limited;

    public RequestParams(String searchWord, String language, int limited) {
        this.searchWord = searchWord;
        this.language = language;
        if(limited == 0){
            this.limited = DEFAULT_LIMITED_NUMBER;
        }else{
            this.limited = limited;
        }
    }

    public String getSearchWord() {
        return searchWord;
    }

    public String getLanguage() {
        return language;
    }

    public int getLimited() {
        return limited;
    }
}
