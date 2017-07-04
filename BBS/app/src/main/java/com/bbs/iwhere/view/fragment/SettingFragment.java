package com.bbs.iwhere.view.fragment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bbs.iwhere.R;
import com.bbs.iwhere.db.DbFriendListManager;
import com.bbs.iwhere.view.activity.AboutActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by 大森 on 2016/11/7.
 */

public class SettingFragment extends Fragment implements View.OnClickListener {

    //    private ImageView pushSwitch;
    private final static int PROGRESSVISIBLE = 0;
    private final static int PROGRESSGONE = 1;
    private ProgressBar progressBar;
    private RelativeLayout cleanlayout;
    private RelativeLayout aboutlayout;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PROGRESSGONE:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "缓存已清除", Toast.LENGTH_LONG).show();
                    break;
//                case PROGRESSVISIBLE:
//                    progressBar.setVisibility(View.VISIBLE);
//                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_left_setting, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.setting_progress_bar);
        progressBar.setVisibility(View.GONE);
        cleanlayout = (RelativeLayout) view.findViewById(R.id.cleanitem);
        cleanlayout.setOnClickListener(this);
        aboutlayout = (RelativeLayout) view.findViewById(R.id.aboutitem);
        aboutlayout.setOnClickListener(this);
        return view;
    }

    private void cleanTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                    Message message = Message.obtain();
                    message.what = 1;
                    handler.sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cleanitem:
/*                Message message = Message.obtain();
                message.what = 0;
                handler.sendMessage(message);*/
                progressBar.setVisibility(View.VISIBLE);
                cleanTime();
                break;
            case R.id.aboutitem:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                getActivity().overridePendingTransition(R.anim.in_to_left, 0);
            default:
                break;
        }
    }
}
