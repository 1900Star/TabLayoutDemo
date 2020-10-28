package com.yibao.tablayoutdemo.ui.main;

import android.os.Bundle;
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

import com.yibao.tablayoutdemo.MyGridViewAdapter;
import com.yibao.tablayoutdemo.R;
import com.yibao.tablayoutdemo.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

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
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        initRecyclerView(recyclerView, LinearLayoutManager.VERTICAL);

        final TextView textView = root.findViewById(R.id.section_label);
        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        MyGridViewAdapter adapter = new MyGridViewAdapter(getActivity(), getUserInfo(1));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return root;

    }

    protected void initRecyclerView(RecyclerView recyclerView, int type) {
//        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        manager.setOrientation(type);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), type));
        recyclerView.setVerticalScrollBarEnabled(true);
    }

    private List<UserInfo> getUserInfo(int ts) {
        ArrayList<UserInfo> mList = new ArrayList<>();

        for (int i = 1; i < 9; i++) {
            if (i % 2 == 0) {
                mList.add(new UserInfo("", ts == 1 ? "O " + i : "新垣结衣 " + i));
            } else {
                mList.add(new UserInfo("", ts == 1 ? "R1 " + i : "Find " + i));

            }
        }
        return mList;
    }
}