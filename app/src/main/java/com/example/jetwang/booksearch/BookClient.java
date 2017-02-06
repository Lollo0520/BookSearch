package com.example.jetwang.booksearch;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Jet Wang on 2017/2/6.
 */

public class BookClient {

    public static final String API_BASE_URL = "http://openlibrary.org/";
    public AsyncHttpClient client;
    public BookClient() {
        client = new AsyncHttpClient();
    }

    public void getBooks(final String query, JsonHttpResponseHandler handler){
        String url = getApiUrl("search.json?q=");
        try {
            client.get(url + URLEncoder.encode(query, "utf-8"),  handler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }
}
