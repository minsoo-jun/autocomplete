package com.minsoo.autocomplete.controller;

import com.minsoo.autocomplete.domain.request.RequestParams;
import com.minsoo.autocomplete.domain.response.AutocompleteDomain;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.minsoo.autocomplete.Constants.Constants.*;

/**
 *
 */
@RestController
public class AutocompleteController {

    @GetMapping(value="/autocomplete/{language}/{searchWord}", produces = "application/json")
    @CrossOrigin
    public ResponseEntity<List<AutocompleteDomain>> getAutocomplete(@PathVariable(SEARCH_WORD) String searchWord
            , @PathVariable(LANGUAGE) String langauge
            , @RequestParam(value = LIMITED, defaultValue = "10") int limited
        ){

        //파라메터 세팅
        RequestParams rp = new RequestParams(searchWord, langauge, limited);


        //dummy return
        List acList = new ArrayList();
        AutocompleteDomain ac = new AutocompleteDomain();
        ac.setName("Test Name");
        ac.setScore(3.44);
        ac.setSku("SkuSku");
        ac.setPharse("<font color=red>Pharse</font>");
        acList.add(ac);

        return new ResponseEntity<>(acList, HttpStatus.OK);
    }
}
