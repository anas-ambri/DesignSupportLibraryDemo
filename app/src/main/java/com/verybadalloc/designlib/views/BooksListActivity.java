package com.verybadalloc.designlib.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.verybadalloc.designlib.R;
import com.verybadalloc.designlib.adapters.TabsPagerAdapter;
import com.verybadalloc.designlib.events.BookSelected;
import com.verybadalloc.designlib.events.BusProvider;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * An activity representing a list of Books. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link BookDetailsActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 */
public class BooksListActivity extends AppCompatActivity {

    private final Bus bus = BusProvider.getInstance();

    @InjectView(R.id.navigation)
    NavigationView navigationView;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @InjectView(R.id.viewPager)
    ViewPager viewPager;
    @InjectView(R.id.tabLayout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.books_list_activity);

        ButterKnife.inject(this);

        //Setup navigation
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();
                selectedMenu((String) item.getTitle());
                return true;//Return true if handled
            }
        });

        //Setup toolbar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.app_name);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //Setup tabLayout
        TabsPagerAdapter adapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void selectedMenu(String title) {
        Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    public boolean isTwoPane() {
        return findViewById(R.id.book_details_container) != null;
    }

    @Subscribe
    public void onBookSelected(BookSelected event) {
        if(isTwoPane()) {
            Fragment fragment = new BookDetailsFragmentBuilder(event.selectedBook).build();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.book_details_container, fragment)
                    .commit();
        } else {
            Intent detailIntent = new Intent(this, BookDetailsActivity.class);
            detailIntent.putExtra(BookDetailsFragment.BOOK, event.selectedBook);
            startActivity(detailIntent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
