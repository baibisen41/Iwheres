package com.bbs.iwhere.view.fragment.common;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by beasley on 2016/11/27.
 */

public abstract class BaseFragment extends Fragment {

    public <T extends View> T findViewId(View view, int id) {
        return (T) view.findViewById(id);
    }

}
