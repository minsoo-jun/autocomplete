package com.minsoo.autocomplete.domain.request;

public class RequestParams {

    private String searchWord;
    private String language;
    private boolean useCache;

    public RequestParams(String searchWord, String language, boolean useCache) {
        this.searchWord = searchWord;
        this.language = language;
        this.useCache = useCache;
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

}
