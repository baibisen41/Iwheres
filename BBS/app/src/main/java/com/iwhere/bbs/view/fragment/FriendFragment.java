package com.iwhere.bbs.view.fragment;


import android.view.View;
import android.widget.RelativeLayout;

import com.iwhere.bbs.R;
import com.iwhere.bbs.view.fragment.common.SwipeFragment;


public class FriendFragment extends SwipeFragment {

    protected View friendview;

    @Override
    protected View addView(View view) {
        super.addView(view);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.swipe_main);
        friendview = View.inflate(getActivity(), R.layout.content_left_friendmanager, null);
        relativeLayout.addView(friendview);
        return view;
    }

}
