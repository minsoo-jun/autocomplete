package com.minsoo.autocomplete.logic;

import com.minsoo.autocomplete.domain.request.RequestParams;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

import static com.minsoo.autocomplete.constants.Constants.*;
import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.elasticsearch.index.query.QueryBuilders.prefixQuery;

@Component
public class ElasticQueryBuilder {
    public QueryBuilder getQueryShouldBuilder(RequestParams params) {
        //search target input
        String input = params.getSearchWord();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if (!StringUtils.isEmpty(input)) {

            // add 'should match' query for the input string
            // fuzzyTranspositions https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-fuzzy-query.html
            boolQueryBuilder
                    .should(matchQuery(TARGET_FIELD, input)
                            .fuzzyTranspositions(true)
                            .boost(1));
            // check if input has multiple words
            List<String> tokens = Arrays.asList(input.split(REGEX_SPACE));
            boolean multiWord = tokens.size() > 1;

            if (multiWord) {
                // add phrase matching query
                // slop 검색 쿼리가 문서와 일치하는 것으로 간주하면서 허용되는 거리 https://www.elastic.co/guide/en/elasticsearch/guide/current/slop.html
                boolQueryBuilder
                        .should(matchPhraseQuery(TARGET_FIELD, input)
                                .slop(10)
                                .boost(1));

                // we will consider prefix match for each token
                for (String token : tokens) {
                    if (token.length() > 1) {
                        boolQueryBuilder.should(prefixQuery(TARGET_FIELD, token.toLowerCase()).boost(2));
                    }
                }
            } else {
                boolQueryBuilder.should(prefixQuery(TARGET_FIELD, input.toLowerCase()).boost(1));
            }

            // at-least one criteria has to be matched
            boolQueryBuilder.minimumShouldMatch(1);
        }
        return boolQueryBuilder;
    }

    public QueryBuilder getQueryMustBuilder(RequestParams params) {
        //search target input
        String input = params.getSearchWord();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if (!StringUtils.isEmpty(input)) {

            // add 'should match' query for the input string
            // fuzzyTranspositions https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-fuzzy-query.html
            boolQueryBuilder
                    .must(matchQuery(TARGET_FIELD, input)
                            .fuzzyTranspositions(true)
                            .boost(1));
            // check if input has multiple words
            List<String> tokens = Arrays.asList(input.split(REGEX_SPACE));
            boolean multiWord = tokens.size() > 1;

            if (multiWord) {
                // add phrase matching query
                // slop 검색 쿼리가 문서와 일치하는 것으로 간주하면서 허용되는 거리 https://www.elastic.co/guide/en/elasticsearch/guide/current/slop.html
                boolQueryBuilder
                        .must(matchPhraseQuery(TARGET_FIELD, input)
                                .slop(10)
                                .boost(1));

                // we will consider prefix match for each token
                for (String token : tokens) {
                    if (token.length() > 1) {
                        boolQueryBuilder.must(prefixQuery(TARGET_FIELD, token.toLowerCase()).boost(2));
                    }
                }
            } else {
                boolQueryBuilder.must(prefixQuery(TARGET_FIELD, input.toLowerCase()).boost(1));
            }

            // at-least one criteria has to be matched
            boolQueryBuilder.minimumShouldMatch(1);
        }
        return boolQueryBuilder;
    }
}
