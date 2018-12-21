package com.minsoo.autocomplete.repository;

import com.minsoo.autocomplete.domain.response.EnDomain;
import com.minsoo.autocomplete.domain.response.JaDomain;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface AutocompleteEnRepositoryImpl extends ElasticsearchRepository<EnDomain, Integer> {

    List<EnDomain> findByName(String name);

}
