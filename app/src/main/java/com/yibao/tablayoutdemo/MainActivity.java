package com.yibao.tablayoutdemo;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.StringRes;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.yibao.tablayoutdemo.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        int childCount = tabLayout.getTabCount();
        for (int i = 0; i < childCount; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                if (i == 0) {
                    tab.setCustomView(getLeftTabView(this));
                } else if (i == childCount - 1) {
                    tab.setCustomView(getRightTabView(this));
                } else {
                    tab.setCustomView(getMiddleTabView(this));
                }

            }

        }


        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private View getMiddleTabView(Context context) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.item_tab_middle, null);
        TextView tv = view.findViewById(R.id.tab_text_middle);
        tv.setText(TAB_TITLES[0]);
        return view;

    }


    public View getLeftTabView(Context context) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.item_tab_left, null);
        TextView tv = view.findViewById(R.id.tab_text_left);
        tv.setText(TAB_TITLES[1]);
        return view;
    }

    public View getRightTabView(Context context) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.item_tab_right, null);
        TextView tv = view.findViewById(R.id.tab_text_right);
        tv.setText(TAB_TITLES[2]);
        return view;
    }


}