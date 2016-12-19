package com.bbs.iwhere.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bbs.iwhere.R;
import com.bbs.iwhere.view.fragment.common.BaseFragment;

/**
 * Created by 大森 on 2016/11/8.
 */

public class LocationopenFragment extends BaseFragment {

    public View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("aaaaaa", "onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_left_location_open, container, false);
        Log.e("aaaaaa", "onCreateView");

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("aaaaaa", "onDetach");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.e("aaaaaa", "onAttach");

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("aaaaaa", "onStart");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("aaaaaa", "onResume");

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("aaaaaa", "onPause");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("aaaaaa", "onStop");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("aaaaaa", "onDestroyView");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("aaaaaa", "onDestroy");

    }
}
