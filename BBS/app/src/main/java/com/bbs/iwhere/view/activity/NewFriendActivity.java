package com.bbs.iwhere.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.Text;
import com.bbs.iwhere.R;
import com.bbs.iwhere.model.DbFriendModel;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.widget.EaseAlertDialog;

/**
 * Created by 大森 on 2017/5/9.
 */

public class NewFriendActivity extends Activity implements View.OnClickListener {

    private ImageView backView;
    private EditText editText;
    private ImageView imageView;
    private TextView nameText;
    private Button addButton;
    private RelativeLayout showSearchFriend;
    private String toAddUsername;
    private ProgressDialog progressDialog;

    DbFriendModel dbFriendModel = new DbFriendModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_friend_layout);
        initView();
    }

    public void initView() {

        showSearchFriend = (RelativeLayout) findViewById(R.id.ll_user);
        editText = (EditText) findViewById(R.id.new_friend_edit);
        editText.setOnClickListener(this);
        imageView = (ImageView) findViewById(R.id.new_friend_bt);
        imageView.setOnClickListener(this);
        nameText = (TextView) findViewById(R.id.name);
        nameText.setOnClickListener(this);
        addButton = (Button) findViewById(R.id.indicator);
        addButton.setOnClickListener(this);
        backView = (ImageView) findViewById(R.id.new_friend_back);
        backView.setOnClickListener(this);
    }

    /**
     * search contact
     *
     * @param
     */
    public void searchContact() {
        String strName = editText.getText().toString();
        Log.e("bbs", strName);
//        String saveText = searchBtn.getText().toString();

        toAddUsername = strName;
        if (TextUtils.isEmpty(strName)) {
            new EaseAlertDialog(this, R.string.Please_enter_a_username).show();
            return;
        } else {

            // TODO you can search the user from your app server here.

            //show the userame and add button if user exist
            showSearchFriend.setVisibility(View.VISIBLE);
            nameText.setText(toAddUsername);
        }

    }

    /**
     * add contact
     *
     * @param
     */
    public void addContact() {
        if (EMClient.getInstance().getCurrentUser().equals(nameText.getText().toString())) {
            new EaseAlertDialog(this, R.string.not_add_myself).show();
            return;
        }

        if (dbFriendModel.getContactList().containsKey(nameText.getText().toString())) {
            //let the user know the contact already in your contact list
            if (EMClient.getInstance().contactManager().getBlackListUsernames().contains(nameText.getText().toString())) {
                new EaseAlertDialog(this, R.string.user_already_in_contactlist).show();
                return;
            }
            new EaseAlertDialog(this, R.string.This_user_is_already_your_friend).show();
            return;
        }

        progressDialog = new ProgressDialog(this);
        String stri = getResources().getString(R.string.Is_sending_a_request);
        progressDialog.setMessage(stri);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        new Thread(new Runnable() {
            public void run() {

                try {
                    //demo use a hardcode reason here, you need let user to input if you like
                    String s = getResources().getString(R.string.Add_a_friend);
                    EMClient.getInstance().contactManager().addContact(toAddUsername, s);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            String s1 = getResources().getString(R.string.send_successful);
                            Toast.makeText(getApplicationContext(), s1, Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            String s2 = getResources().getString(R.string.Request_add_buddy_failure);
                            Toast.makeText(getApplicationContext(), s2 + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_friend_back:
                finish();
                overridePendingTransition(0, R.anim.out_to_right);
                break;
            case R.id.new_friend_bt:
                searchContact();
                break;
            case R.id.indicator:
                addContact();
                break;
        }
    }
}
