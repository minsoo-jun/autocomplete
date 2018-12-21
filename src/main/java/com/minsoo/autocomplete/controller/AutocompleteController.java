package com.minsoo.autocomplete.controller;

import com.minsoo.autocomplete.domain.request.RequestParams;
import com.minsoo.autocomplete.domain.response.EnDomain;
import com.minsoo.autocomplete.repository.AutocompleteEnRepositoryImpl;
import com.minsoo.autocomplete.repository.AutocompleteJaRepositoryImpl;
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
    AutocompleteEnRepositoryImpl autoEnRepo;

    @Autowired
    AutocompleteJaRepositoryImpl autoJaRepo;

    //test curl -i -H 'Content-Type: application/json' -XGET 'http://localhost:8080/ec/autocomplete/en/hello'
    @GetMapping(value="/autocomplete/{language}/{searchWord}", produces = "application/json")
    @CrossOrigin
    public ResponseEntity<List<EnDomain>> getAutocomplete(@PathVariable(SEARCH_WORD) String searchWord
            , @PathVariable(value = LANGUAGE) String language
            , @RequestParam(value = LIMITED, defaultValue = "10") int limited
        ){

        List autocompList = new ArrayList();
        //파라메터 세팅
        RequestParams rp = new RequestParams(searchWord, language, limited);
        if(EN_SUPPORT.equals(language)){
            autocompList = autoEnRepo.findByName(searchWord);
        }else if(JA_SUPPORT.equals(language)){
            System.out.println("JA Support");
            autocompList = autoJaRepo.findByName(searchWord);
        }

        return new ResponseEntity<>(autocompList, HttpStatus.OK);
    }
}
