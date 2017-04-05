package com.bbs.iwhere.view.fragment.FriendManager;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bbs.iwhere.R;
import com.bbs.iwhere.view.adapter.FriendTabAdapter;
import com.bbs.iwhere.view.fragment.common.BaseFragment;

import java.util.ArrayList;
import java.util.List;


public class FriendFragment extends BaseFragment {

    private View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.content_left_friendmanager, container, false);
        initView();
        return view;
    }

    private void initView() {
        tabLayout = findViewId(view, R.id.tabs);
        viewPager = findViewId(view, R.id.viewpager);

        fragmentList.add(0, new ConversationListFragment());
        fragmentList.add(1, new ContactListFragment());
        titleList.add(0, "会话列表");
        titleList.add(1, "联系人");

        for (String title : titleList) {
            tabLayout.addTab(tabLayout.newTab().setText(title));
        }

        viewPager.setAdapter(new FriendTabAdapter(getChildFragmentManager(), fragmentList, titleList));
        tabLayout.setupWithViewPager(viewPager);
    }
}
