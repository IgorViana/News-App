package com.example.android.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.content.AsyncTaskLoader;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private String stringUrl;

    public NewsLoader(@NonNull Context context) {
        super(context);
        this.stringUrl = null;
    }
    public NewsLoader(@NonNull Context context, String url) {
        super(context);
        this.stringUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<News> loadInBackground() {
        return Utils.makeHttpConnection(stringUrl);
    }
}
