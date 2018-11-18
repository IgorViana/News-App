package com.example.android.newsapp;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class News {
    private int newsImageId;
    private String newsTitle;
    private String newsSection;
    private String newsData;
    private String newsAuthor;
    private String webUrl;


    public News(int newsImageId, String newsTitle, String newsSection, String newsData, String newsAuthor, String url) {
        this.newsImageId = newsImageId;
        this.newsTitle = newsTitle;
        this.newsSection = newsSection;
        this.newsData = newsData;
        this.newsAuthor = newsAuthor;
        this.webUrl = url;
    }

    public int getNewsImageId() {
        return newsImageId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsSection() {
        return newsSection;
    }

    public String getNewsData() {
        return newsData;
    }

    public String getNewsAuthor() {
        return newsAuthor;
    }

    public String getWebUrl() {
        return webUrl;
    }
}
