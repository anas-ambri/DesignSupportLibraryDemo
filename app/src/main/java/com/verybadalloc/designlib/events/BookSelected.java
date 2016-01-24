package com.verybadalloc.designlib.events;

import com.verybadalloc.designlib.model.Book;

/**
 * Created by aambri on 15-06-10.
 */
public class BookSelected {

    public final Book selectedBook;

    public BookSelected(Book book) {
        this.selectedBook = book;
    }
}
