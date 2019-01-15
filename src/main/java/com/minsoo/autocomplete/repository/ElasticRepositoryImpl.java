package com.minsoo.autocomplete.repository;

import com.minsoo.autocomplete.domain.Refs;
import com.minsoo.autocomplete.domain.request.RequestParams;
import com.minsoo.autocomplete.domain.response.EnDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

@Repository
public class ElasticRepositoryImpl implements ElasticRepository {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public List<EnDomain> queryForDocuments(SearchQuery searchQuery) {
        List<?> autocompList = elasticsearchTemplate.queryForList(searchQuery,EnDomain.class);
        List<EnDomain> autocompListTmp = new ArrayList<>();
        EnDomain tmpDomain;
        for(int j = 0; j < autocompList.size() ; j++){
            tmpDomain = (EnDomain)autocompList.get(j);
            tmpDomain.setPhrase(tmpDomain.getName());
            autocompListTmp.add(tmpDomain);
        }
        return autocompListTmp;
    }

    @Override
    public List<EnDomain> queryForDocumentsV2(SearchQuery searchQuery, RequestParams rp) {
        List<?> autocompList = elasticsearchTemplate.queryForList(searchQuery,EnDomain.class);
        List<EnDomain> autocompListTmp = new ArrayList<>();
        EnDomain tmpDomain;
        StringBuffer phraseTmp;
        // MAx 3 word
        String firstWord = "";
        String secondWord = "";
        String thirdWord = "";
        String firstResult = "";
        String secondResult = "";
        String thirdResult = "";
        boolean second = false;
        boolean third = false;

        //중복된 결과를 제거 하기 위해서
        HashMap regcheck = new HashMap<>();

        StringTokenizer st = new StringTokenizer(rp.getSearchWord(), " ");
        int i = 0;
        while(st.hasMoreTokens()){
            if(i == 0){
                firstWord = st.nextToken().toLowerCase();
            }else if(i == 1){
                secondWord = st.nextToken().toLowerCase();
                second = true;
            }else if(i == 2){
                thirdWord = st.nextToken().toLowerCase();
                third = true;
            }
            i++;
        }
        //System.out.println("firstWord:" + firstWord + ", secondWord:" + secondWord + ", thirdWord:" + thirdWord);
        List<Refs> refs;
        for(int j = 0; j < autocompList.size() ; j++){
            phraseTmp = new StringBuffer();
            firstResult = "";
            secondResult = "";
            thirdResult = "";
            tmpDomain = (EnDomain)autocompList.get(j);
            refs = tmpDomain.getRefs();
            for(int k = 0; k < refs.size(); k++){
                //System.out.println("refs.get(k).getWord=" + refs.get(k).getWord());
                if(refs.get(k).getWord().toLowerCase().indexOf(firstWord) >= 0){
                    firstResult = refs.get(k).getWord();
                }
                if(second && refs.get(k).getWord().toLowerCase().indexOf(secondWord) >= 0){
                    secondResult = refs.get(k).getWord();
                }
                if(third && refs.get(k).getWord().toLowerCase().indexOf(thirdWord) >= 0){
                    thirdResult = refs.get(k).getWord();
                }
            }
            //System.out.println("firstResult:" + firstResult + ", secondResult:" + secondResult + ", thirdResult:" + thirdResult);
            if(!"".equals(firstResult)){
                phraseTmp.append(firstResult);
            }
            if(!"".equals(secondResult)){
                phraseTmp.append(" " + secondResult);
            }
            if(!"".equals(thirdResult)){
                phraseTmp.append(" " + thirdResult);
            }
            //최소 한개의 결과가 있을것
            if(!"".equals(firstResult)){

                if(regcheck.get(phraseTmp.toString()) == null){
                    //등록이 안된거면 추가
                    tmpDomain.setPhrase(phraseTmp.toString());
                    autocompListTmp.add(tmpDomain);
                    regcheck.put(phraseTmp.toString(),"checked");
                }

            }

        }

        return autocompListTmp;
    }

}
