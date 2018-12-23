package com.minsoo.autocomplete.service;

import com.minsoo.autocomplete.domain.request.RequestParams;
import com.minsoo.autocomplete.repository.AutocompleteEnRepositoryImpl;
import com.minsoo.autocomplete.repository.AutocompleteJaRepositoryImpl;
import com.minsoo.autocomplete.util.StringHighlight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.minsoo.autocomplete.constants.Constants.EN_SUPPORT;
import static com.minsoo.autocomplete.constants.Constants.JA_SUPPORT;

@Service
public class AutocompleteService {

    @Autowired
    AutocompleteEnRepositoryImpl autoEnRepo;

    @Autowired
    AutocompleteJaRepositoryImpl autoJaRepo;

    @Autowired
    StringHighlight highlight;

    public ResponseEntity getAutocompleteData(RequestParams rp){
        List autocompList = new ArrayList();

        if(EN_SUPPORT.equals(rp.getLanguage())){
            //HTML + Javascript로 구현해서 불필요.
            //autocompList = highlight.highlightEnString(autoEnRepo.findByNameLike(rp.getSearchWord()),rp);
            autocompList = autoEnRepo.findByNameLike(rp.getSearchWord());
        }else if(JA_SUPPORT.equals(rp.getLanguage())){
            System.out.println("JA Support");
            autocompList = autoJaRepo.findByName(rp.getSearchWord());
        }
        return new ResponseEntity<>(autocompList, HttpStatus.OK);
    }
}
