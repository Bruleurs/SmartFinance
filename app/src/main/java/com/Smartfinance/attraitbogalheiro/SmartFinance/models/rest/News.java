package com.Smartfinance.attraitbogalheiro.SmartFinance.models.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "guid",
        "published_on",
        "imageurl",
        "title",
        "url",
        "source",
        "body",
        "tags",
        "lang",
        "source_info"
})
public class News {
    @JsonProperty("published_on")
    private Integer publishedOn;
    @JsonProperty("imageurl")
    private String imageurl;
    @JsonProperty("title")
    private String title;
    @JsonProperty("url")
    private String url;
    @JsonProperty("source")
    private String source;
    @JsonProperty("body")
    private String body;

    public Integer getPublishedOn() {
        return publishedOn;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getSource() {
        return source;
    }

    public String getBody() {
        return body;
    }

}
