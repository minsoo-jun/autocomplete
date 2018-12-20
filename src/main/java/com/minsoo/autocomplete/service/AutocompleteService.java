package com.minsoo.autocomplete.service;

import com.minsoo.autocomplete.domain.response.AutocompleteDomain;
import com.minsoo.autocomplete.repository.AutocompleteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutocompleteService {

    private AutocompleteRepository autoRepo;

    public AutocompleteDomain save(AutocompleteDomain autocomp){

        return autoRepo.save(autocomp);
    }

    public void delete(AutocompleteDomain autocomp){

        autoRepo.delete(autocomp);
    }

    public AutocompleteDomain findOne(Integer id){

        return autoRepo.findById(id).get();
    }

    public Iterable<AutocompleteDomain> findAll(){

        return autoRepo.findAll();
    }

    public List<AutocompleteDomain> findByName(String name){

        return autoRepo.findByName(name);
    }
}
