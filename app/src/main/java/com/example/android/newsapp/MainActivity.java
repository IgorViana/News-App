package com.example.android.newsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.app.LoaderManager;
import android.content.Loader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URI;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>, NewsClickListener {
    private static final int NEWS_LOADER_ID = 1;
    NewsAdapter adapter;
    RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView emptyStateText;
    private static final String API_REQUEST_URL =
            "https://content.guardianapis.com/search?show-tags=contributor&api-key=cef1da17-3c71-4283-83aa-2378810e845c";

    private static final String API_REQUEST_URL_TESTE =
            "https://content.guardianapis.com/search?";

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
            if (networkInfo != null && networkInfo.isConnected()) {
                LoaderManager loaderManager = getLoaderManager();
                loaderManager.initLoader(NEWS_LOADER_ID, null, this);
            } else {
                progressBar.setVisibility(View.GONE);
                emptyStateText.setText(R.string.NoInternet);
            }
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String topico = sharedPreferences.getString(
                getString(R.string.settings_topic_key),
                getString(R.string.settings_topic_default));
        String order_by = sharedPreferences.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default));

        Uri baseUri = Uri.parse(API_REQUEST_URL_TESTE);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("show-tags", "contributor");
        if (!topico.isEmpty()) {
            uriBuilder.appendQueryParameter("section", topico);
        }

        if (!order_by.isEmpty()) {
            uriBuilder.appendQueryParameter("order-by", order_by);
        }

        uriBuilder.appendQueryParameter("api-key", API_KEY);
        Log.i("API_URI", uriBuilder.toString());

        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> newsList) {
        progressBar.setVisibility(View.GONE);
        if (newsList.isEmpty()) {
            emptyStateText.setText(R.string.NoData);
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            adapter = new NewsAdapter(getApplicationContext(), newsList, this);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<News>> loader) {
        if(adapter != null)
        adapter.clear();
    }

    @Override
    public void onNewsSelected(News news) {
        Uri newsUri = Uri.parse(news.getWebUrl());
        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
        startActivity(websiteIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
