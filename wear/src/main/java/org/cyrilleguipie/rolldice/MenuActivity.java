package org.cyrilleguipie.rolldice;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.wearable.view.GridViewPager;
import android.support.wearable.view.WatchViewStub;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowInsets;


public class MenuActivity extends Activity {

    private GridViewPager pager;

    private MenuGridViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mAdapter = new MenuGridViewPagerAdapter(MenuActivity.this, R.menu.main_menu);
        mAdapter.setOnItemClickedListener(new MenuGridViewPagerAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(View v, MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.start_game:
                        startActivity(new Intent(MenuActivity.this, RollWearActivity.class));
                        finish();
                        break;

                    case R.id.about:
                        startActivity(new Intent(MenuActivity.this, AboutActivity.class));
                        break;
                }
            }
        });

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                pager = (GridViewPager) stub.findViewById(R.id.pager);

                final Resources res = getResources();
                pager.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
                    @Override
                    public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {

                        final boolean round = insets.isRound();
                        int rowMargin = res.getDimensionPixelOffset(R.dimen.page_row_margin);
                        int colMargin = res.getDimensionPixelOffset(round ?
                                R.dimen.page_column_margin_round : R.dimen.page_column_margin);
                        pager.setPageMargins(rowMargin, colMargin);
                        return insets;
                    }
                });

                pager.setAdapter(mAdapter);
            }
        });
    }
}
