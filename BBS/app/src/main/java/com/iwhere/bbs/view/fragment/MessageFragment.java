package com.iwhere.bbs.view.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;


import com.iwhere.bbs.R;
import com.iwhere.bbs.view.fragment.common.SwipeFragment;


public class MessageFragment extends SwipeFragment {

    @Override
    protected View addView(View view) {
        super.addView(view);
        RelativeLayout relativelayout = (RelativeLayout) view.findViewById(R.id.swipe_main);
        Button button = new Button(relativelayout.getContext());
        button.setText("aaa");
        relativelayout.addView(button);
        return view;
    }


}
