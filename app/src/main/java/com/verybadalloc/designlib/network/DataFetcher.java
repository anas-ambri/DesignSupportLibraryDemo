package com.verybadalloc.designlib.network;

import com.google.gson.Gson;
import com.verybadalloc.designlib.model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aambri on 15-06-06.
 */

public class DataFetcher {

    private static final String BOOKS_API = "http://anasambri.com/downloads/data/apps/com_verybadalloc_designlib.json";

    public static void getBooks(final DataCallback<Book[]> callback) {
        HttpRestClient.get(BOOKS_API, null, new NetworkCallback() {

            @Override
            public void onSuccess(String response) {
                Book[] eateries = new Gson().fromJson(response, Book[].class);
                callback.onSuccess(eateries);
            }

            @Override
            public void onFailure(String reason) {
                callback.onFailure(reason);
            }
        });
    }

    public static void getBooksOfType(final String type, final DataCallback<Book[]> callback) {

        DataFetcher.getBooks(new DataCallback<Book[]>() {
            @Override
            public void onSuccess(Book[] data) {
                if (!type.equalsIgnoreCase("All")) {
                    List<Book> newList = new ArrayList<Book>(data.length);
                    for (Book b : data) {
                        if (b.type.equalsIgnoreCase(type)) {
                            newList.add(b);
                        }
                    }
                    callback.onSuccess(newList.toArray(new Book[newList.size()]));
                } else {
                    callback.onSuccess(data);
                }
            }

            @Override
            public void onFailure(String reason) {
                callback.onFailure(reason);
            }
        });
    }
}
