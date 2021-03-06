package com.verybadalloc.designlib.views;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceFragment;
import com.verybadalloc.designlib.R;
import com.verybadalloc.designlib.adapters.BooksAdapter;
import com.verybadalloc.designlib.adapters.viewHolders.BookItemViewHolder;
import com.verybadalloc.designlib.model.Book;
import com.verybadalloc.designlib.presenters.BooksListPresenter;

import butterknife.InjectView;
import icepick.Icicle;

/**
 * Created by aambri on 15-06-09.
 */
public class BooksListFragment extends MvpLceFragment<SwipeRefreshLayout, Book[], BooksListView, BooksListPresenter>
        implements BooksListView, SwipeRefreshLayout.OnRefreshListener {

    @Icicle
    @Arg
    String bookType;

    private static final String TAG = "BooksListFragment";
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    @InjectView(R.id.contentView)
    SwipeRefreshLayout refreshLayout;
    BooksAdapter adapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        contentView.setOnRefreshListener(this);
        adapter = new BooksAdapter(getActivity(), BookItemViewHolder.class);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //loadData(false);
        loadBooks(bookType, false);
    }

    @Override
    protected String getErrorMessage(Throwable throwable, boolean b) {
        return throwable.getMessage();
    }

    @Override
    public BooksListPresenter createPresenter() {
        return new BooksListPresenter();
    }

    @Override
    public void setData(Book[] data) {
        adapter.setBooks(data); //notifyDatasetChanged called implicitly
    }

    @Override protected int getLayoutRes() {
        return R.layout.books_list_fragment;
    }

    private void loadBooks(String bookType, boolean pullToRefresh) {
        presenter.loadBooksOfType(bookType, pullToRefresh);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadBooks(pullToRefresh);
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }

    @Override
    public void showContent() {
        super.showContent();
        refreshLayout.setRefreshing(false);
    }
}
