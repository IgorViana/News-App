package com.example.android.newsapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.app.LoaderManager;
import android.content.Loader;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {
    private static final int NEWS_LOADER_ID = 1;
    NewsAdapter adapter;
    RecyclerView recyclerView;
    private static final String API_REQUEST_URL =
            "https://content.guardianapis.com/search?api-key=cef1da17-3c71-4283-83aa-2378810e845c";

    private static final String API_KEY = "cef1da17-3c71-4283-83aa-2378810e845c";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        recyclerView = findViewById(R.id.newsList);
    }


    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        return new NewsLoader(this, API_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> newsList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new NewsAdapter(getApplicationContext(),newsList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<News>> loader) {
        adapter.clear();
    }
}
