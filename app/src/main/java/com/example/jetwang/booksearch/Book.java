package com.example.jetwang.booksearch;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jet Wang on 2017/2/6.
 */

public class Book implements Serializable{

    private String openLibraryId;
    private String title;
    private String author;

    public String getOpenLibraryId() {
        return openLibraryId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCoverUrl(){
        return "http://covers.openlibrary.org/b/olid/" + openLibraryId + "-M.jpg?default=false";
    }

    public String getLargeCoverUrl(){
        return "http://covers.openlibrary.org/b/olid/" + openLibraryId + "-L.jpg?default=false";
    }

    public static Book fromJson(JSONObject jsonObject){
        Book book = new Book();
        try {
            if (jsonObject.has("cover_edition_key")){
                book.openLibraryId = jsonObject.getString("cover_edition_key");
            }else if (jsonObject.has("edition_key")){
               JSONArray ids = jsonObject.getJSONArray("edition_key");
                book.openLibraryId = ids.getString(0);
            }
            book.title = jsonObject.has("title_suggest") ? jsonObject.getString("title_suggest") : "";
            book.author = getAuthor(jsonObject);
            return book;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    private static String getAuthor(JSONObject jsonObject) {
        try{
            JSONArray authors = jsonObject.getJSONArray("author_name");
            int numAuthors = authors.length();
            String[] authorStrings = new String[numAuthors];
            for (int i = 0; i < numAuthors; i++){
                authorStrings[i] = authors.getString(i);
            }
            return TextUtils.join(", ", authorStrings);
        }catch (JSONException e){
            e.printStackTrace();
            return "";
        }
    }

    public static ArrayList<Book> fromJson(JSONArray jsonArray){
        ArrayList<Book> books = new ArrayList<>(jsonArray.length());

        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject bookJson = null;
            try {
                bookJson = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
            Book book = fromJson(bookJson);
            if (book != null){
                books.add(book);
            }
        }
        return books;
    }
}
