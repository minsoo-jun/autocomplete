package com.minsoo.autocomplete.repository;

import com.minsoo.autocomplete.domain.EnDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ElasticRepositoryImpl implements ElasticRepository {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public List<EnDomain> queryForDocuments(SearchQuery searchQuery) {

        return elasticsearchTemplate.queryForList(searchQuery,EnDomain.class);
    }
}
