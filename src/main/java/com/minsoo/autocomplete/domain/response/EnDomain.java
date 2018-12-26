package com.minsoo.autocomplete.domain.response;

import com.minsoo.autocomplete.domain.Category;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Score;

import java.util.List;

@Document(indexName = "products-en", type = "document")
public class EnDomain {
    @Id
    private String id;
    private String name;
    @Score
    private Float score;
    private String sku;
    private List<Category> category ;
    private String url;
    private String image;
    // meta info
    private List<String> highlights;

    public List<String> getHighlights() {
        return highlights;
    }

    public void setHighlights(List<String> highlights) {
        this.highlights = highlights;
    }

    public String getUrl() { return url;}

    public void setUrl(String url) { this.url = url;}

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public List<Category> getCategory() { return category; }

    public void setCategory(List<Category> category) { this.category = category; }

    @Override
    public String toString(){
        return "{"
                + "id:" + id
                + ",name:" + name
                + ",score:" + score
                + ",sku:" + sku
                + ",category:" + category
                + ",url:" + url
                + ",image:" + image
                + "}";
    }
}
