package com.bbs.iwhere.view.fragment;

import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bbs.iwhere.R;
import com.bbs.iwhere.db.DbFriendListManager;
import com.bbs.iwhere.db.MainHelper;
import com.bbs.iwhere.view.activity.AboutActivity;
import com.bbs.iwhere.view.activity.LoginActivity;
import com.bbs.iwhere.view.activity.MainActivity;
import com.hyphenate.EMCallBack;

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
    private Button outLogin;

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
        outLogin = (Button) view.findViewById(R.id.outLogin);
        outLogin.setOnClickListener(this);
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
                break;
            case R.id.outLogin:
                logout();
                break;
            default:
                break;
        }
    }

    void logout() {
        final ProgressDialog pd = new ProgressDialog(getActivity());
        String st = getResources().getString(R.string.Are_logged_out);
        pd.setMessage(st);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        MainHelper.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        pd.dismiss();
                        // show login screen
                        ((MainActivity) getActivity()).finish();
                        startActivity(new Intent(getActivity(), LoginActivity.class));

                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        pd.dismiss();
                        Toast.makeText(getActivity(), "unbind devicetokens failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
