package com.minsoo.autocomplete.controller;

import com.minsoo.autocomplete.domain.request.RequestParams;
import com.minsoo.autocomplete.domain.response.EnDomain;
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

    @GetMapping(value="/autocomplete/{language}/{searchWord}", produces = "application/json")
    @CrossOrigin
    public ResponseEntity<List<EnDomain>> getAutocompleteV2(@PathVariable(SEARCH_WORD) String searchWord
            , @PathVariable(value = LANGUAGE) String language
            , @RequestParam(value = USE_CACHE, defaultValue = "false") boolean useCache
            , @RequestParam(value = SEARCH_MODE, defaultValue = "fullname") String mode
    ){

        //파라메터 세팅
        RequestParams rp = new RequestParams(searchWord.trim(), language.trim(), useCache, mode);

        System.out.println("## Search Keyword: " + searchWord);

        if("word".equals(mode)){
            return ss.searchDocumentsV2(rp);
        }
        return ss.searchDocuments(rp);
    }
}
