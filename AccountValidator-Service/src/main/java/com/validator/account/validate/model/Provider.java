package com.validator.account.validate.model;
public class Provider {
    private String name;
    private String url;

    /**
     * @return provider Name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name Set provider name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Provider URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url set the provider URl
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
