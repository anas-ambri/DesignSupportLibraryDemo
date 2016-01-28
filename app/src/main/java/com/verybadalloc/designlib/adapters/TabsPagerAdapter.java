package com.verybadalloc.designlib.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.verybadalloc.designlib.views.BooksListFragmentBuilder;

/**
 * Created by aambri on 16-01-28.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    private static final String[] categories = new String[]{"All", "New Arrival", "Novels", "Non-fiction", "Classics", "Science-fiction"};

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return BooksListFragmentBuilder.newBooksListFragment(categories[position]);
    }

    @Override
    public int getCount() {
        return categories.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return categories[position];
    }
}
