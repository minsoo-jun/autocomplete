package com.minsoo.autocomplete.controller;

import com.minsoo.autocomplete.domain.request.RequestParams;
import com.minsoo.autocomplete.domain.response.EnDomain;
import com.minsoo.autocomplete.repository.AutocompleteEnRepositoryImpl;
import com.minsoo.autocomplete.repository.AutocompleteJaRepositoryImpl;
import com.minsoo.autocomplete.service.AutocompleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.minsoo.autocomplete.constants.Constants.*;

/**
 *
 */
@RestController
public class AutocompleteController {

    @Autowired
    AutocompleteService as ;

    //test curl -i -H 'Content-Type: application/json' -XGET 'http://localhost:8080/ec/autocomplete/en/hello'
    @GetMapping(value="/autocomplete/{language}/{searchWord}", produces = "application/json")
    @CrossOrigin
    public ResponseEntity<List<EnDomain>> getAutocomplete(@PathVariable(SEARCH_WORD) String searchWord
            , @PathVariable(value = LANGUAGE) String language
            , @RequestParam(value = LIMITED, defaultValue = "10") int limited
        ){

        //TODO parameter check.
        //파라메터 세팅
        RequestParams rp = new RequestParams(searchWord.trim(), language.trim(), limited);


        return as.getAutocompleteData(rp);
    }
}
