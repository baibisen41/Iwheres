package com.bbs.iwhere.view.fragment.FriendManager;

import android.view.LayoutInflater;
import android.view.View;

import com.bbs.iwhere.R;
import com.hyphenate.easeui.ui.EaseContactListFragment;


/**
 * Created by 大森 on 2017/3/29.
 */

public class ContactListFragment extends EaseContactListFragment implements View.OnClickListener {


    @Override
    protected void initView() {
        super.initView();
        hideTitleBar();
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.contact_list_layout, null);
        listView.addHeaderView(headerView);
        registerForContextMenu(listView);
    }

    @Override
    protected void setUpView() {
        super.setUpView();
    }

    @Override
    public void onClick(View v) {

    }
}
