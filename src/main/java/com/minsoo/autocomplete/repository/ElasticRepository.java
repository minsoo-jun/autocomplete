package com.minsoo.autocomplete.repository;

import com.minsoo.autocomplete.domain.EnDomain;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.util.List;

public interface ElasticRepository {

    List<EnDomain> queryForDocuments(SearchQuery searchQuery);
}
