package com.example.android.newsapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public final class Utils {
    public static final String LOG_TAG = MainActivity.class.getName();

    public static List<News> makeHttpConnection(String string) {
        URL url = null;
        String json = null;
        List<News> newsList = new ArrayList<>();
        try {
            url = createUrl(string);
            json = makeHttpRequest(url);
            return extractNews(json);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static URL createUrl(String string) {
        URL url = null;
        try {
            url = new URL(string);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(1500);
            httpURLConnection.connect();
            httpURLConnection.getResponseCode();

            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code:" + httpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results:" + e);
            e.printStackTrace();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results:" + e);
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link News} objects that has been built up from
     * parsing a JSON response.
     */
    private static ArrayList<News> extractNews(String Json) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<News> noticias = new ArrayList<>();
        try {

            JSONObject news = new JSONObject(Json);
            JSONObject response = news.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject currentNews = results.getJSONObject(i);
                String title = currentNews.getString("webTitle");
                String section = currentNews.getString("sectionName");
                JSONArray tag = currentNews.getJSONArray("tags");
                JSONObject currentTag = tag.getJSONObject(0);
                String author = currentTag.getString("webTitle");
                String data = currentNews.getString("webPublicationDate");
                String url = currentNews.getString("webUrl");
                noticias.add(new News(R.drawable.theguardian_logo, title, section, turnDate(data), author, url));
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of News
        return noticias;
    }

    public static String turnDate (String oldDate){
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(oldDate);
            String dateString = new SimpleDateFormat("dd/MM/yyyy").format(date);
            return dateString;
        } catch (ParseException e) {
            Log.i("TESTE", e.toString());
        }
        return oldDate;
    }
}
