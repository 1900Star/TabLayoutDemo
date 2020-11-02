package com.yibao.tablayoutdemo.ui.main;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.yibao.tablayoutdemo.MyAdapter;
import com.yibao.tablayoutdemo.MyGridViewAdapter;
import com.yibao.tablayoutdemo.R;
import com.yibao.tablayoutdemo.UserInfo;
import com.yibao.tablayoutdemo.view.ScrollItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private SwipeRefreshLayout mRefreshLayout;
    private AppBarLayout mAppBarLayout;
    private ScrollItemView mScrollItemView;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        this.pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        mAppBarLayout = root.findViewById(R.id.app_bar);
        mScrollItemView = root.findViewById(R.id.scroll_view_excel);
        mRefreshLayout = root.findViewById(R.id.refresh_layout);
        mRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW);
        mRefreshLayout.setProgressViewOffset(true, -20, 50);
        mRefreshLayout.setOnRefreshListener(this);
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        initRecyclerView(recyclerView, LinearLayoutManager.VERTICAL);

        final TextView textView = root.findViewById(R.id.section_label);
        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
//        MyGridViewAdapter adapter = new MyGridViewAdapter(getActivity(), getUserInfo(1));
        MyAdapter adapter = new MyAdapter(getActivity(), getUserInfo(1), mScrollItemView);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        initListener();
        return root;

    }

    private void initListener() {
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                mRefreshLayout.setEnabled(verticalOffset >= 0);
            }
        });
    }

    protected void initRecyclerView(RecyclerView recyclerView, int type) {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1);
        manager.setOrientation(type);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), type));
        recyclerView.setVerticalScrollBarEnabled(false);
    }

    private List<UserInfo> getUserInfo(int ts) {
        ArrayList<UserInfo> mList = new ArrayList<>();

        for (int i = 1; i < 50; i++) {
            if (i % 2 == 0) {
                mList.add(new UserInfo("", ts == 1 ? "O " + i : "新垣结衣 " + i));
            } else {
                mList.add(new UserInfo("", ts == 1 ? "R1 " + i : "Find " + i));

            }
        }
        return mList;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }
}