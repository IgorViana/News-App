package com.example.android.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.app.LoaderManager;
import android.content.Loader;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>, NewsClickListener{
    private static final int NEWS_LOADER_ID = 1;
    NewsAdapter adapter;
    RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView emptyStateText;
    private static final String API_REQUEST_URL =
            "https://content.guardianapis.com/search?show-tags=contributor&api-key=cef1da17-3c71-4283-83aa-2378810e845c";

    private static final String API_KEY = "cef1da17-3c71-4283-83aa-2378810e845c";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.newsList);
        emptyStateText = findViewById(R.id.emptyState);

        ConnectivityManager cnnManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cnnManager != null) {
            NetworkInfo networkInfo = cnnManager.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isConnected()){
                LoaderManager loaderManager = getLoaderManager();
                loaderManager.initLoader(NEWS_LOADER_ID, null, this);
            }
            else
            {
                progressBar.setVisibility(View.GONE);
                emptyStateText.setText(R.string.NoInternet);
            }
        }
    }


    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        return new NewsLoader(this, API_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> newsList) {
        progressBar.setVisibility(View.GONE);
        if(newsList.isEmpty()){
            emptyStateText.setText(R.string.NoData);
        }else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            adapter = new NewsAdapter(getApplicationContext(), newsList, this);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<News>> loader) {
        adapter.clear();
    }

    @Override
    public void onNewsSelected(News news) {
        Uri newsUri = Uri.parse(news.getWebUrl());
        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
        startActivity(websiteIntent);
    }
}
