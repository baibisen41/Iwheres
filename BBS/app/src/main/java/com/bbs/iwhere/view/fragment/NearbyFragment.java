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
import android.widget.Toast;

import com.baidu.mapapi.map.Text;
import com.bbs.iwhere.R;
import com.bbs.iwhere.db.DbFriendListManager;
import com.bbs.iwhere.model.FriendListModel;
import com.bbs.iwhere.util.NetworkUtil;
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

    //    List<FriendListModel> friendListModelList = new ArrayList<>();
    private ProgressBar progressBar;
    private ImageView no_friend_pic;
    private TextView find_friend_text;
//    private RelativeLayout relativeLayout2;
//    private ImageView imageView1;
//    private ImageView imageView2;

    private View view;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PROGRESSGONE:
                    try {
                        progressBar.setVisibility(View.GONE);
                        no_friend_pic.setVisibility(View.VISIBLE);
                        find_friend_text.setText(R.string.nearby_no_friend);
//                        imageView1.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(friendListModelList.get(0).getPicsdurl())));
//                        imageView2.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(friendListModelList.get(1).getPicsdurl())));
//                    relativeLayout2.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
        no_friend_pic = (ImageView) view.findViewById(R.id.no_friend_image);
        no_friend_pic.setVisibility(View.GONE);
        find_friend_text = (TextView) view.findViewById(R.id.find_friend_text);
        find_friend_text.setVisibility(View.VISIBLE);
//        relativeLayout1.setOnClickListener(this);
//        relativeLayout2 = (RelativeLayout) view.findViewById(R.id.nearby_user2);
//        relativeLayout2.setOnClickListener(this);
//        relativeLayout2.setVisibility(View.GONE);
//        imageView1 = (ImageView) view.findViewById(R.id.nearby_pic1);
//        imageView2 = (ImageView) view.findViewById(R.id.nearby_pic2);
    }

    private void keepTime() {
        if (!NetworkUtil.isNetSupport(getActivity().getApplicationContext())) {
            Toast.makeText(getActivity(), R.string.no_net_error, Toast.LENGTH_LONG).show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //friendListModelList = DbFriendListManager.getInstance().getFriendList();
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
//        switch (v.getId()) {
//            case R.id.nearby_user1:
//                Intent intent = new Intent(getActivity(), NearbyDetailActivity.class);
//                intent.putExtra("userpic", friendListModelList.get(0).getPicsdurl());
//                intent.putExtra("username", friendListModelList.get(0).getUsername());
//                startActivity(intent);
//                getActivity().overridePendingTransition(R.anim.in_to_left, 0);
//                break;
//            case R.id.nearby_user2:
//                Intent intent1 = new Intent(getActivity(), NearbyDetailActivity.class);
//                intent1.putExtra("userpic", friendListModelList.get(1).getPicsdurl());
//                intent1.putExtra("username", friendListModelList.get(1).getUsername());
//                startActivity(intent1);
//                getActivity().overridePendingTransition(R.anim.in_to_left, 0);
//                break;
//            default:
//                break;
//        }
    }
}
