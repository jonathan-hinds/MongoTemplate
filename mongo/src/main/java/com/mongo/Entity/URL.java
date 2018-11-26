package com.mongo.Entity;

import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "URLs")
public class URL {

    private static final String URL = "URL";
    private static final String WEB_TEXT = "web-text";

    @Field(URL)
    @Indexed(unique = true, direction = IndexDirection.ASCENDING)
    private String url;

    @Field(WEB_TEXT)
    @Indexed(unique = true, direction = IndexDirection.ASCENDING)
    private String text;

    public URL(String url, String text) {
        this.url = url;
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
