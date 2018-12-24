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
    AutocompleteRedisService redisService;

    public ResponseEntity getAutocompleteData(RequestParams rp){
        List autocompList = new ArrayList();

        if(EN_SUPPORT.equals(rp.getLanguage())){
            // if data exists in redis
            if(redisService.getFromRedis(rp.getSearchWord()) != null && redisService.getFromRedis(rp.getSearchWord()).size() > 0){
                System.out.println("*** Data from redis");
                autocompList = redisService.getFromRedis(rp.getSearchWord()) ;
            }else{
                System.out.println("*** Data from es");
                autocompList = autoEnRepo.findByNameLike(rp.getSearchWord());
                //TODO set to redis.
                if(autocompList.size() > 0) {
                    System.out.println("*** Set to redis");
                    redisService.setToReids(rp.getSearchWord(), autocompList);
                }

            }

        }else if(JA_SUPPORT.equals(rp.getLanguage())){
            if(redisService.getFromRedis(rp.getSearchWord()).size() > 0){
                autocompList = redisService.getFromRedis(rp.getSearchWord()) ;
            }else{
                autocompList = autoJaRepo.findByName(rp.getSearchWord());
                if(autocompList.size() > 0) {
                    redisService.setToReids(rp.getSearchWord(), autocompList);
                }
            }

        }
        return new ResponseEntity<>(autocompList, HttpStatus.OK);
    }
}
