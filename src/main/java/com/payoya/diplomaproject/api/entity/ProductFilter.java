package com.payoya.diplomaproject.api.entity;

public class ProductFilter {

    private String titleContains;

    private String bodyContains;

    private Double mixPrice;

    private Double maxPrice;

    private String tagsContains;

    public String getTitleContains() {
        return titleContains;
    }

    public void setTitleContains(String titleContains) {
        this.titleContains = titleContains;
    }

    public String getBodyContains() {
        return bodyContains;
    }

    public void setBodyContains(String bodyContains) {
        this.bodyContains = bodyContains;
    }

    public Double getMixPrice() {
        return mixPrice;
    }

    public void setMixPrice(Double mixPrice) {
        this.mixPrice = mixPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getTagsContains() {
        return tagsContains;
    }

    public void setTagsContains(String tagsContains) {
        this.tagsContains = tagsContains;
    }
}
