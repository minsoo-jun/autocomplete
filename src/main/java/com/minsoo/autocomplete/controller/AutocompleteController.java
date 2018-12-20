package com.minsoo.autocomplete.controller;

import com.minsoo.autocomplete.domain.request.RequestParams;
import com.minsoo.autocomplete.domain.response.AutocompleteDomain;
import com.minsoo.autocomplete.repository.AutocompleteRepository;
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
    AutocompleteRepository autoRepo;

    //test curl -i -H 'Content-Type: application/json' -XGET 'http://localhost:8080/ec/autocomplete/en/hello'
    @GetMapping(value="/autocomplete/{language}/{searchWord}", produces = "application/json")
    @CrossOrigin
    public ResponseEntity<List<AutocompleteDomain>> getAutocomplete(@PathVariable(SEARCH_WORD) String searchWord
            , @PathVariable(value = LANGUAGE) String language
            , @RequestParam(value = LIMITED, defaultValue = "10") int limited
        ){

        //파라메터 세팅
        RequestParams rp = new RequestParams(searchWord, language, limited);

        List<AutocompleteDomain> autocompList = autoRepo.findByName(searchWord);


        return new ResponseEntity<>(autocompList, HttpStatus.OK);
    }
}
