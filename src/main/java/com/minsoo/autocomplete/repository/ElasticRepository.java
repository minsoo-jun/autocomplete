package com.minsoo.autocomplete.repository;

import com.minsoo.autocomplete.domain.request.RequestParams;
import com.minsoo.autocomplete.domain.response.EnDomain;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.util.List;

public interface ElasticRepository {

    List<EnDomain> queryForDocuments(SearchQuery searchQuery);

    List<EnDomain> queryForDocumentsV2(SearchQuery searchQuery, RequestParams rp);
}
