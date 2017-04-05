package com.bbs.iwhere.view.fragment.FriendManager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bbs.iwhere.R;

/**
 * Created by 大森 on 2017/3/29.
 */

public class ConversationListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ab, container, false);
        return view;
    }

}
