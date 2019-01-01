package com.minsoo.autocomplete.controller;

import com.minsoo.autocomplete.domain.request.RequestParams;
import com.minsoo.autocomplete.domain.EnDomain;
import com.minsoo.autocomplete.service.AutocompleteService;
import com.minsoo.autocomplete.service.SearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.minsoo.autocomplete.constants.Constants.*;

/**
 *
 */
@RestController
public class AutocompleteController {

    @Autowired
    AutocompleteService as ;

    @Autowired
    SearchService ss;

    //test curl -i -H 'Content-Type: application/json' -XGET 'http://localhost:8080/ec/autocomplete/en/hello'
    @GetMapping(value="/autocomplete/{language}/{searchWord}", produces = "application/json")
    @CrossOrigin
    public ResponseEntity<List<EnDomain>> getAutocomplete(@PathVariable(SEARCH_WORD) String searchWord
            , @PathVariable(value = LANGUAGE) String language
            , @RequestParam(value = USE_CACHE, defaultValue = "false") boolean useCache
        ){

        //파라메터 세팅
        RequestParams rp = new RequestParams(searchWord.trim(), language.trim(), useCache);

        System.out.println("## Search Keyword: " + searchWord);

        return ss.searchDocuments(rp);
    }
}
