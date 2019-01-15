package com.minsoo.autocomplete.domain.request;

public class RequestParams {

    private String searchWord;
    private String language;
    private boolean useCache;
    // full name mode or word mode
    private String mode;

    public RequestParams(String searchWord, String language, boolean useCache, String mode) {
        this.searchWord = searchWord;
        this.language = language;
        this.useCache = useCache;
        this.mode = mode;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public String getLanguage() {
        return language;
    }

    public boolean isUseCache() {
        return useCache;
    }

    public String getMode(){return mode;}

}
