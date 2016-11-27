package com.iwhere.bbs.view.fragment.common;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iwhere.bbs.R;


/**
 * Created by beasley on 2016/11/27.
 */

public class SwipeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    protected View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.swipe_layout, container, false);
        initRefresh();
        addView(view);
        return view;
    }

    protected View addView(View view) {
        return view;
    }


    private void initRefresh() {
        SwipeRefreshLayout swiperefreshlayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_main);
        swiperefreshlayout.setColorSchemeResources(R.color.refreshcolor);
        swiperefreshlayout.setDistanceToTriggerSync(100);
        swiperefreshlayout.setProgressViewEndTarget(false, 200);
        swiperefreshlayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {

    }


}
