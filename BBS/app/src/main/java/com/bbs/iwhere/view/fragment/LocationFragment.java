package com.bbs.iwhere.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bbs.iwhere.R;
import com.bbs.iwhere.service.LocationCallback;
import com.bbs.iwhere.service.LocationService;
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
    private int[] friendLocation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_left_location, container, false);
        friendLocationAddress = findViewId(view, R.id.friendaddress);
        showMapButton = findViewId(view, R.id.showmap_tab);
        showMapButton.setOnClickListener(this);
        showWayButton = findViewId(view, R.id.showay_tab);
        showWayButton.setOnClickListener(this);
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
        });


        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.showmap_tab:
//                Intent intent = new Intent(getActivity(), LocationShowActivity.class);
//                intent.putExtra("friendLocationStatus", 1);
//                intent.putExtra("friendLocation", friendLocation);
//                startActivity(intent);
//                getActivity().overridePendingTransition(R.anim.in_to_left, 0);
                break;
            case R.id.showay_tab:
                Log.e("aaa", "aaa");
                startActivity(new Intent(getActivity(), RoutePlanActivity.class));
                getActivity().overridePendingTransition(R.anim.in_to_left, 0);
                break;
            default:
                break;
        }
    }
}
