package com.bbs.iwhere.view.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bbs.iwhere.R;
import com.bbs.iwhere.db.DbFriendListManager;
import com.bbs.iwhere.db.DbFriendManager;
import com.bbs.iwhere.model.FriendLcationModel;
import com.bbs.iwhere.model.FriendListModel;
import com.bbs.iwhere.service.LocationService.LocationCallback;
import com.bbs.iwhere.service.LocationService.LocationService;
import com.bbs.iwhere.util.NetworkUtil;
import com.bbs.iwhere.view.activity.LocationShowActivity;
import com.bbs.iwhere.view.activity.RoutePlanActivity;
import com.bbs.iwhere.view.fragment.common.BaseFragment;
import com.hyphenate.easeui.domain.EaseUser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by 大森 on 2016/11/8.
 */

public class LocationFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private ImageView friendpic;
    private TextView friendname;
    private TextView friendStatus;
    private TextView friendLocationAddress;
    private TextView showMapButton;
    private TextView showWayButton;
    private TextView selectFriendButton;
    //    private double[] friendLocation = {39.085994, 121.985379};//从服务器拉取的定位数据包
    private double[] friendLocation;
    private AlertDialog.Builder selectFriendDialog;
    //    private List<FriendListModel> friendlist = new ArrayList<>();
    private LocationService locationLocationService = new LocationService();
    private Map<String, EaseUser> friendMap;
    private List<EaseUser> easeUsers;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        new LoadFriendListService().getFriendlist();
//        friendlist = DbFriendListManager.getInstance().getFriendList();
        friendMap = DbFriendManager.getInstance().getContactList();
        easeUsers = new ArrayList<EaseUser>(friendMap.values());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_left_location, container, false);
        initView();
        //拉取的好友列表
/*        new LoadFriendListService().setLoadFriendListCallback(new LoadFriendListCallback() {
            @Override
            public void showFriendList(List<FriendListModel> friendListModelList) {
                friendListDialog(friendListModelList);
            }
        });*/
//        friendListDialog(friendlist);
        return view;
    }

    private void initView() {
        friendpic = findViewId(view, R.id.friendpic);
        friendname = findViewId(view, R.id.friendname);
        friendStatus = findViewId(view, R.id.friendstatus);
        friendStatus.setVisibility(View.GONE);
        selectFriendButton = findViewId(view, R.id.select_friend_button);
        selectFriendButton.setOnClickListener(this);
        friendLocationAddress = findViewId(view, R.id.friendaddress);
        friendLocationAddress.setVisibility(View.GONE);
        showMapButton = findViewId(view, R.id.showmap_tab);
        showMapButton.setEnabled(false);
        showMapButton.setOnClickListener(this);
        showWayButton = findViewId(view, R.id.showay_tab);
        showWayButton.setOnClickListener(this);
        showWayButton.setEnabled(false);
    }

    //弹出好友列表dialog
    public void friendListDialog(final List<EaseUser> friendLists) {

        if (!NetworkUtil.isNetSupport(getActivity().getApplicationContext())) {
            Toast.makeText(getActivity(), R.string.no_net_error, Toast.LENGTH_LONG).show();
            return;
        }

        selectFriendDialog = new AlertDialog.Builder(getActivity());
        selectFriendDialog.setTitle("请选择好友");
        SelectFriendDialogAdapter selectFriendDialogAdapter = new SelectFriendDialogAdapter(getActivity(), friendLists);
        selectFriendDialog.setAdapter(selectFriendDialogAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "已点击", Toast.LENGTH_LONG).show();
                //     int userId = 0;//模拟
                //Log.e("testWhich", friendLists.get(which).getUserid());
                //////////拉取好友定位信息
                locationLocationService.postFriendLocation(friendLists.get(which).getUsername());//发送选择好友定位请求
                showFriendLocation();//显示结果
                ///////////////////////////
                showMapButton.setEnabled(true);
                showWayButton.setEnabled(true);
                //friendpic.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(friendLists.get(which).getPicsdurl())));
                friendpic.setImageResource(R.mipmap.ic_launcher);
                friendname.setText(friendLists.get(which).getNick());
                friendStatus.setVisibility(View.VISIBLE);
                friendLocationAddress.setVisibility(View.VISIBLE);
            }
        });
        selectFriendDialog.show();
    }

    //好友列表dialog适配器
    public class SelectFriendDialogAdapter extends BaseAdapter {

        private List<EaseUser> dataList;
        private LayoutInflater layoutInflater;

        public SelectFriendDialogAdapter(Context mContext, List<EaseUser> dataList) {
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
            //Log.e("loationPicture", dataList.get(position).getPicsdurl());
            //viewHolder.friendPic.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(dataList.get(position).getPicsdurl())));
            viewHolder.friendPic.setImageResource(R.mipmap.ic_launcher);
            viewHolder.friendName.setText(dataList.get(position).getUsername());

            return convertView;
        }

        public class ViewHolder {
            private ImageView friendPic;
            private TextView friendName;
        }
    }

    //选择好友，拉取定位信息
    private void showFriendLocation() {

        locationLocationService.setLocationCallback(new LocationCallback() {

            @Override
            public void showFriendLocationData(List<FriendLcationModel> friendLcationModelList) {
                double longitude = Double.valueOf(friendLcationModelList.get(0).getUserlongitude());
                Log.e("testlo", String.valueOf(longitude));
                double latitude = Double.valueOf(friendLcationModelList.get(0).getUserlatitude());
                Log.e("testla", String.valueOf(latitude));
                friendLocation = new double[]{longitude, latitude};
            }
        });
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
                friendListDialog(easeUsers);
                break;
            default:
                break;
        }
    }
}
