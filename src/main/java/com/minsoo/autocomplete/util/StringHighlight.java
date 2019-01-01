package com.minsoo.autocomplete.util;

import com.minsoo.autocomplete.domain.request.RequestParams;
import com.minsoo.autocomplete.domain.response.EnDomain;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Component
public class StringHighlight {

    public List<EnDomain> highlightEnString(List<EnDomain> enList, RequestParams requestParam){
        List<EnDomain> enListNew = new ArrayList<>();
        EnDomain newEd;
        for(EnDomain ed : enList){
            newEd = new EnDomain();
            newEd.setCategory(ed.getCategory());
            newEd.setId(ed.getId());
            newEd.setImage(ed.getImage());
            newEd.setScore(ed.getScore());
            newEd.setSku(ed.getSku());
            newEd.setUrl(ed.getUrl());
            //add tag
            newEd.setName(toBoldReverse(ed.getName(), requestParam.getSearchWord()));
            enListNew.add(newEd);
        }
        return enListNew;
    }

    /**
     * 검색외의 단어를 하이라이트 한다.
     * @param nameInput
     * @param targetWordInput
     * @return
     */
    public String toBold(String nameInput, String targetWordInput){
        String name = nameInput.toUpperCase();
        String targetWord = targetWordInput.toUpperCase();

        StringBuffer sb = new StringBuffer();
        String tagStart = "<b>";
        String tagEnd = "</b>";
        int strPoint = name.indexOf(targetWord);
        // not find
        if(strPoint < 0){
            System.out.println("Not Found: " + name);
            return name;
        }
        if(name.indexOf(targetWord) > 0) {
            sb.append(tagStart + nameInput.substring(0, name.indexOf(targetWord)) + tagEnd);
        }
        sb.append(nameInput.substring(name.indexOf(targetWord), name.indexOf(targetWord) + targetWord.length()));
        sb.append(tagStart + nameInput.substring(name.indexOf(targetWord) + targetWord.length(), nameInput.length())+ tagEnd) ;

        System.out.println("Result: " + sb.toString());
        return sb.toString();
    }

    /**
     * 검색 단어를 하일라이트 한다.
     * @param nameInput
     * @param targetWordInput
     * @return
     */
    public String toBoldReverse(String nameInput, String targetWordInput){
        String name = nameInput.toLowerCase();
        String targetWords = targetWordInput.toLowerCase();

        StringTokenizer st = new StringTokenizer(targetWords, " ");

        StringBuffer sb = new StringBuffer();
        String tagStart = "<span class=\"highlight\">";
        String tagEnd = "</span>";
        String targetWord = "";
        for(int i =0 ; st.hasMoreTokens(); i++){
            targetWord = st.nextToken();
            int strPoint = name.indexOf(targetWord);
            // not find
            if(strPoint < 0){
                System.out.println("Not Found: " + name);
                return name;
            }
            if(name.indexOf(targetWord) > 0) {
                sb.append(nameInput.substring(0, name.indexOf(targetWord)));
            }
            sb.append(tagStart + (nameInput.substring(name.indexOf(targetWord), name.indexOf(targetWord) + targetWord.length())) + tagEnd);
            sb.append(nameInput.substring(name.indexOf(targetWord) + targetWord.length(), nameInput.length()));
        }

        System.out.println("Result: " + sb.toString());

        return sb.toString();
    }
}
