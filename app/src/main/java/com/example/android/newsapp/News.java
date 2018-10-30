package com.example.android.newsapp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class News {
    private int newsImageId;
    private String newsTitle;
    private String newsSection;
    private String newsData;
    private String newsAuthor;

    public News(int newsImageId, String newsTitle, String newsSection) {
        this.newsImageId = newsImageId;
        this.newsTitle = newsTitle;
        this.newsSection = newsSection;
        this.newsData = null;
        this.newsAuthor = null;
    }

    public News(int newsImageId, String newsTitle, String newsSection, String newsData, String newsAuthor) {
        this.newsImageId = newsImageId;
        this.newsTitle = newsTitle;
        this.newsSection = newsSection;
        this.newsData = newsData;
        this.newsAuthor = newsAuthor;
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


}
