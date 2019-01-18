package com.minsoo.autocomplete.service;

import com.minsoo.autocomplete.domain.request.RequestParams;
import com.minsoo.autocomplete.domain.response.AutoCompResults;
import com.minsoo.autocomplete.domain.response.EnDomain;
import com.minsoo.autocomplete.logic.ElasticQueryBuilder;
import com.minsoo.autocomplete.repository.ElasticRepository;
import com.minsoo.autocomplete.util.StringHighlight;
import org.apache.commons.lang3.time.StopWatch;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.minsoo.autocomplete.constants.Constants.*;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

@Service
public class SearchService {
    private ElasticQueryBuilder elasticQueryBuilder;

    private ElasticRepository elasticRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    public void setElasticQueryBuilder(ElasticQueryBuilder elasticQueryBuilder) {
        this.elasticQueryBuilder = elasticQueryBuilder;
    }

    @Autowired
    public void setElasticRepository(ElasticRepository elasticRepository) {
        this.elasticRepository = elasticRepository;
    }

    @Autowired
    AutocompleteRedisService redisService;

    @Autowired
    StringHighlight highlight;

    public ResponseEntity searchDocuments(RequestParams rp) {
        StopWatch sw = new StopWatch();
        sw.start();
        String redisKey = rp.getMode()+ ":" + rp.getSearchWord();
        AutoCompResults acr = new AutoCompResults();
        List autocompList = new ArrayList();
        String indices = "";
        if(EN_SUPPORT.equals(rp.getLanguage())) {
            indices = TARGET_EN_INDEX;
        }else if(JA_SUPPORT.equals(rp.getLanguage())) {
            indices = TARGET_JA_INDEX;
        }

        QueryBuilder queryBuilder;
        if(rp.isUseCache()){
            System.out.println("*** Data from redis");
            autocompList = redisService.getFromRedis(redisKey) ;
            if(autocompList == null || autocompList.size() < 1){
                System.out.println("*** No Data in redis");
                queryBuilder = elasticQueryBuilder.getQueryShouldBuilder(rp);
                SearchQuery searchQuery = new NativeSearchQueryBuilder()
                        .withIndices(indices)
                        .withTypes(DOCUMENT_TYPE)
                        .withQuery(queryBuilder)
                        .withHighlightFields(new HighlightBuilder.Field(TARGET_FIELD))
                        .build();
                autocompList = elasticRepository.queryForDocuments(searchQuery);


                if(autocompList.size() > 0) {
                    System.out.println("*** data set to redis");
                    redisService.setToReids(redisKey, autocompList);
                }
            }
        }else{
            System.out.println("### Data from es");
            //delete all redis key
            redisService.deleteAllkey();
            queryBuilder = elasticQueryBuilder.getQueryShouldBuilder(rp);
            SearchQuery searchQuery = new NativeSearchQueryBuilder()
                    .withIndices(indices)
                    .withTypes(DOCUMENT_TYPE)
                    .withQuery(queryBuilder)
                    .withHighlightFields(new HighlightBuilder.Field(TARGET_FIELD))
                    .build();
            autocompList = elasticRepository.queryForDocuments(searchQuery);

        }

        acr.setAutocompList(autocompList);
        sw.stop();
        acr.setResponseTime(sw.getTime());
        return new ResponseEntity<>(acr, HttpStatus.OK);
    }

    public ResponseEntity searchDocumentsV2(RequestParams rp) {
        StopWatch sw = new StopWatch();
        sw.start();

        AutoCompResults acr = new AutoCompResults();
        List<?> autocompList = new ArrayList();
        String indices = "";
        if(EN_SUPPORT.equals(rp.getLanguage())) {
            indices = TARGET_EN_INDEX;
        }else if(JA_SUPPORT.equals(rp.getLanguage())) {
            indices = TARGET_JA_INDEX;
        }

        QueryBuilder queryBuilder;
        if(rp.isUseCache()){
            System.out.println("*** Data from redis V2");
            autocompList = redisService.getFromRedis(rp.getSearchWord()) ;
            if(autocompList == null || autocompList.size() < 1){
                System.out.println("*** No Data in redis V2");
                queryBuilder = elasticQueryBuilder.getQueryShouldBuilder(rp);
                SearchQuery searchQuery = new NativeSearchQueryBuilder()
                        .withIndices(indices)
                        .withTypes(DOCUMENT_TYPE)
                        .withQuery(queryBuilder)
                        .withPageable(PageRequest.of(0,100))
                        .withHighlightFields(new HighlightBuilder.Field(TARGET_FIELD))
                        .build();
                autocompList = elasticRepository.queryForDocumentsV2(searchQuery, rp);


                if(autocompList.size() > 0) {
                    System.out.println("*** data set to redis V2");
                    redisService.setToReids(rp.getSearchWord(), autocompList);
                }
            }
        }else{
            System.out.println("### Data from es V2");
            //delete all redis key
            redisService.deleteAllkey();
            queryBuilder = elasticQueryBuilder.getQueryShouldBuilder(rp);
            SearchQuery searchQuery = new NativeSearchQueryBuilder()
                    .withIndices(indices)
                    .withTypes(DOCUMENT_TYPE)
                    .withQuery(queryBuilder)
                    .withPageable(PageRequest.of(0,100))
                    .withHighlightFields(new HighlightBuilder.Field(TARGET_FIELD))
                    .build();
            System.out.println(queryBuilder.toString());
            autocompList = elasticRepository.queryForDocumentsV2(searchQuery, rp);

        }

        acr.setAutocompList(autocompList);
        sw.stop();
        acr.setResponseTime(sw.getTime());
        return new ResponseEntity<>(acr, HttpStatus.OK);
    }
}
