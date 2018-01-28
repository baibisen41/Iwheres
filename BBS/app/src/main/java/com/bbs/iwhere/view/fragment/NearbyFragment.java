package com.bbs.iwhere.view.fragment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Image;
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
import android.widget.TextView;

import com.bbs.iwhere.R;
import com.bbs.iwhere.db.DbFriendListManager;
import com.bbs.iwhere.model.FriendListModel;
import com.bbs.iwhere.view.activity.NearbyDetailActivity;
import com.bbs.iwhere.view.fragment.common.BaseFragment;
import com.bbs.okhttp.utils.Exceptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 大森 on 2017/6/1.
 */

public class NearbyFragment extends Fragment implements View.OnClickListener {

    private final static int PROGRESSGONE = 1;

    List<FriendListModel> friendListModelList = new ArrayList<>();
    private ProgressBar progressBar;
    private RelativeLayout relativeLayout1;
    private RelativeLayout relativeLayout2;
    private ImageView imageView1;
    private ImageView imageView2;

    private View view;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PROGRESSGONE:
                    try {
                        progressBar.setVisibility(View.GONE);
                        imageView1.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(friendListModelList.get(0).getPicsdurl())));
                        imageView2.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(friendListModelList.get(1).getPicsdurl())));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    relativeLayout1.setVisibility(View.VISIBLE);
                    relativeLayout2.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_left_nearby, container, false);
        initView();
        keepTime();
        return view;
    }

    public void initView() {
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        relativeLayout1 = (RelativeLayout) view.findViewById(R.id.nearby_user1);
        relativeLayout1.setOnClickListener(this);
        relativeLayout1.setVisibility(View.GONE);
        relativeLayout2 = (RelativeLayout) view.findViewById(R.id.nearby_user2);
        relativeLayout2.setOnClickListener(this);
        relativeLayout2.setVisibility(View.GONE);
        imageView1 = (ImageView) view.findViewById(R.id.nearby_pic1);
        imageView2 = (ImageView) view.findViewById(R.id.nearby_pic2);
    }

    private void keepTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    friendListModelList = DbFriendListManager.getInstance().getFriendList();
                    Thread.sleep(3000);
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
            case R.id.nearby_user1:
                Intent intent = new Intent(getActivity(), NearbyDetailActivity.class);
                intent.putExtra("userpic", friendListModelList.get(0).getPicsdurl());
                intent.putExtra("username", friendListModelList.get(0).getUsername());
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in_to_left, 0);
                break;
            case R.id.nearby_user2:
                Intent intent1 = new Intent(getActivity(), NearbyDetailActivity.class);
                intent1.putExtra("userpic", friendListModelList.get(1).getPicsdurl());
                intent1.putExtra("username", friendListModelList.get(1).getUsername());
                startActivity(intent1);
                getActivity().overridePendingTransition(R.anim.in_to_left, 0);
                break;
            default:
                break;
        }
    }
}
