package com.bbs.iwhere.view.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bbs.iwhere.R;
import com.bbs.iwhere.model.FriendListModel;
import com.bbs.iwhere.service.LocationService.LoadFriendListCallback;
import com.bbs.iwhere.service.LocationService.LoadFriendListService;
import com.bbs.iwhere.service.LocationService.LocationCallback;
import com.bbs.iwhere.service.LocationService.LocationService;
import com.bbs.iwhere.view.activity.LocationShowActivity;
import com.bbs.iwhere.view.activity.RoutePlanActivity;
import com.bbs.iwhere.view.fragment.common.BaseFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 大森 on 2016/11/8.
 */

public class LocationFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private TextView friendStatus;
    private TextView friendLocationAddress;
    private TextView showMapButton;
    private TextView showWayButton;
    private TextView selectFriendButton;
    private double[] friendLocation = {39.085994, 121.985379};//从服务器拉取的定位数据包
    private AlertDialog.Builder selectFriendDialog;
    private FriendListModel friendListModel = new FriendListModel();
    private FriendListModel friendListModel1 = new FriendListModel();
    private List<FriendListModel> friendlist = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_left_location, container, false);
        initView();

        //拉取的好友列表
        new LoadFriendListService().setLoadFriendListCallback(new LoadFriendListCallback() {
            @Override
            public void showFriendList(List<FriendListModel> friendListModelList) {
                friendListDialog(friendListModelList);
            }
        });

        new LocationService().setLocationCallback(new LocationCallback() {
            @Override
            public void showFriendLocationStatus() {

            }

            @Override
            public void showFriendPic() {

            }

            @Override
            public void showFriendName() {

            }

            @Override
            public void showFriendLocation(String text) {
                friendLocationAddress.setText(text);
            }

            @Override
            public void showFriendLocationData(int[] data) {
                //好友数据包
                //friendLocation = data;
            }
        });


        return view;
    }

    private void initView() {

        selectFriendButton = findViewId(view, R.id.select_friend_button);
        selectFriendButton.setOnClickListener(this);
        friendLocationAddress = findViewId(view, R.id.friendaddress);
        showMapButton = findViewId(view, R.id.showmap_tab);
        showMapButton.setOnClickListener(this);
        showWayButton = findViewId(view, R.id.showay_tab);
        showWayButton.setOnClickListener(this);
    }

    //弹出好友列表dialog
    public void friendListDialog(List<FriendListModel> friendLists) {

/*
        ////////////模拟数据/////////////////
        friendListModel.setUserId(0);
        friendListModel.setFriendPic("aaa");
        friendListModel.setFriendName("bbs");

        friendListModel1.setUserId(1);
        friendListModel1.setFriendPic("bbb");
        friendListModel1.setFriendName("ssb");

        friendlist.add(friendListModel);
        friendlist.add(friendListModel1);
        ////////////模拟数据/////////////////
*/

        selectFriendDialog = new AlertDialog.Builder(getActivity());
        selectFriendDialog.setTitle("请选择好友");
        SelectFriendDialogAdapter selectFriendDialogAdapter = new SelectFriendDialogAdapter(getActivity(), friendlist);
        selectFriendDialog.setAdapter(selectFriendDialogAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "已点击", Toast.LENGTH_LONG).show();
            }
        });
        selectFriendDialog.show();
    }

    //好友列表dialog适配器
    public class SelectFriendDialogAdapter extends BaseAdapter {

        private List<FriendListModel> dataList;
        private LayoutInflater layoutInflater;

        public SelectFriendDialogAdapter(Context mContext, List<FriendListModel> dataList) {
            layoutInflater = LayoutInflater.from(mContext);
            this.dataList = dataList;
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.select_friendlist_item, null);
                viewHolder.friendPic = (ImageView) convertView.findViewById(R.id.friend_pic);
                viewHolder.friendName = (TextView) convertView.findViewById(R.id.friend_name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
//            viewHolder.friendPic.setBackgroundResource(R.mipmap.ic_launcher);
//            viewHolder.friendName.setText(dataList.get(position).getFriendName());

            return convertView;
        }

        public class ViewHolder {
            private ImageView friendPic;
            private TextView friendName;
        }
    }

    //顶部选项卡
    private void changeLocationButtonIcon(int buttonId) {

        if (buttonId == R.id.showmap_tab) {
            showMapButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            showWayButton.setBackgroundColor(getResources().getColor(R.color.locationbutton));
        } else if (buttonId == R.id.showay_tab) {
            showMapButton.setBackgroundColor(getResources().getColor(R.color.locationbutton));
            showWayButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.showmap_tab:
                changeLocationButtonIcon(R.id.showmap_tab);
                Intent intent = new Intent(getActivity(), LocationShowActivity.class);
                intent.putExtra("friendLocationType", "1");//地图显示来源
                intent.putExtra("friendLocation", friendLocation);//好友定位数据包
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in_to_left, 0);
                break;
            case R.id.showay_tab:
                changeLocationButtonIcon(R.id.showay_tab);
                startActivity(new Intent(getActivity(), RoutePlanActivity.class));
                getActivity().overridePendingTransition(R.anim.in_to_left, 0);
                break;
            case R.id.select_friend_button:
        //        friendListDialog();
                break;
            default:
                break;
        }
    }
}
