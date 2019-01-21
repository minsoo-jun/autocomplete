package com.minsoo.autocomplete.util;

import com.minsoo.autocomplete.domain.response.EnDomain;

import java.util.Comparator;

public class SortingByPopularity implements Comparator<EnDomain> {

    public int compare(EnDomain a, EnDomain b){
        return b.getPopurality() - a.getPopurality();
    }
}
