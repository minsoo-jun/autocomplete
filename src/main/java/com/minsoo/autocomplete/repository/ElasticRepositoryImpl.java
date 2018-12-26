package com.minsoo.autocomplete.repository;

import com.minsoo.autocomplete.domain.response.EnDomain;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.EntityMapper;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.minsoo.autocomplete.constants.Constants.TARGET_FIELD;

@Repository
public class ElasticRepositoryImpl implements ElasticRepository {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public List<EnDomain> queryForDocuments(SearchQuery searchQuery) {

        return elasticsearchTemplate.queryForList(searchQuery,EnDomain.class);
    }
}
