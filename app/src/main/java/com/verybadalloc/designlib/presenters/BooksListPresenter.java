package com.verybadalloc.designlib.presenters;


import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.verybadalloc.designlib.model.Book;
import com.verybadalloc.designlib.network.DataCallback;
import com.verybadalloc.designlib.network.DataFetcher;
import com.verybadalloc.designlib.views.BooksListView;

/**
 * Created by aambri on 15-06-09.
 */
public class BooksListPresenter extends MvpBasePresenter<BooksListView> {

    private static final String TAG = "BooksListPresenter";

    public void loadEateries(final boolean pullToRefresh) {

        if(isViewAttached()) {
            getView().showLoading(pullToRefresh);
        }

        DataFetcher.getBooks(new DataCallback<Book[]>() {

            @Override
            public void onSuccess(Book[] books) {
                if (isViewAttached()) {
                    getView().setData(books);
                    getView().showContent();
                }
            }

            @Override
            public void onFailure(String reason) {
                String message = "Failed to load books because " + reason;
                if (isViewAttached()) {
                    getView().showError(new Throwable(message), pullToRefresh);
                }
            }
        });
    }
}
