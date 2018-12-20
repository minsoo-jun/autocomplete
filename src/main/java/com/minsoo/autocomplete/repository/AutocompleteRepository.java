package com.minsoo.autocomplete.repository;

import com.minsoo.autocomplete.domain.response.AutocompleteDomain;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface AutocompleteRepository extends ElasticsearchRepository<AutocompleteDomain, Integer> {

    List<AutocompleteDomain> findByName(String name);

}
