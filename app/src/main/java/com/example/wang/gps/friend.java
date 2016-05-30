package com.example.wang.gps;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

/**
 * Created by sunset on 16/5/29.
 */
public class friend extends FragmentActivity {
    private FragmentTabHost fragmentTabHost;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.friend);
        fragmentTabHost=(FragmentTabHost)findViewById(R.id.view);
        fragmentTabHost.setup(this,getSupportFragmentManager(),R.id.tabcontent);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("alfriends").setIndicator("全部好友"), allfriends.class, null);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("onlinefriends").setIndicator("在线好友"), onlinefriends.class, null);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("alfriend").setIndicator("离线好友"),outlinefriends.class,null);

    }
}