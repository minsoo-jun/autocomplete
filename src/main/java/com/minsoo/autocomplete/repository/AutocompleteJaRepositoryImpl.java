package com.minsoo.autocomplete.repository;

import com.minsoo.autocomplete.domain.response.JaDomain;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface AutocompleteJaRepositoryImpl extends ElasticsearchRepository<JaDomain, Integer> {

    List<JaDomain> findByName(String name);

}
