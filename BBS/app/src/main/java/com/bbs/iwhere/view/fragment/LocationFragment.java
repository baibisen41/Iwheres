package com.bbs.iwhere.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bbs.iwhere.R;
import com.bbs.iwhere.service.LocationService.LocationCallback;
import com.bbs.iwhere.service.LocationService.LocationService;
import com.bbs.iwhere.view.activity.LocationShowActivity;
import com.bbs.iwhere.view.activity.RoutePlanActivity;
import com.bbs.iwhere.view.fragment.common.BaseFragment;


/**
 * Created by 大森 on 2016/11/8.
 */

public class LocationFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private TextView friendStatus;
    private TextView friendLocationAddress;
    private TextView showMapButton;
    private TextView showWayButton;
    private double[] friendLocation = {39.085994, 121.985379};//从服务器拉取的定位数据包

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_left_location, container, false);
        friendLocationAddress = findViewId(view, R.id.friendaddress);

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

        showMapButton = findViewId(view, R.id.showmap_tab);
        showMapButton.setOnClickListener(this);
        showWayButton = findViewId(view, R.id.showay_tab);
        showWayButton.setOnClickListener(this);

        return view;
    }

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
            default:
                break;
        }
    }
}
