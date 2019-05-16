
package com.lenovo.studentClient.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lenovo.studentClient.R;
import com.lenovo.studentClient.adapter.MenuListAdapter;
import com.lenovo.studentClient.api.ApiService;
import com.lenovo.studentClient.bean.MenuModel;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements OnClickListener {
    private SlidingMenu mMenu;
    private ListView mLeftMenuLV;

    private Button btnSetCarAction;
    private  TextView tvCarActionResult;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            onRequestFinish();
            switch (msg.what) {
                case 0:
                    tvCarActionResult.setText(msg.obj == null ? "" : msg.obj.toString());
                    break;
                case 1:
                    tvCarActionResult.setText(msg.obj.toString());
                    break;
                case -1:
                    tvCarActionResult.setText(msg.obj.toString());
                    break;
            }

        }
    };


    @Override
    protected void initializeWithState(Bundle savedInstanceState) {

    }

    @Override
    protected String getLayoutTitle() {
        return getString(R.string.title_main);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
        initSlidingMenu();
        initLeftMenu();

        btnSetCarAction = findViewById(R.id.btn_setCarAction);
        tvCarActionResult = findViewById(R.id.tv_carActionResult);
        initListener();

    }

    /**
     * 初始化菜单栏
     */
    private void initLeftMenu() {
        View view = mMenu.getMenu();
        RelativeLayout layout7 =  view.findViewById(R.id.layout7);
        mLeftMenuLV = view.findViewById(R.id.listView1);
        layout7.setOnClickListener(this);
        prepare4ListView();
    }

    private void prepare4ListView() {
        List<MenuModel> list = new ArrayList<>();
         String[] lvs = {"Android", "iOS", "Python", "Html5", "Java"};
        int[] icons = {
                R.mipmap.my_car, R.mipmap.my_road, R.mipmap.park_query, R.mipmap.road_environment, R.mipmap.bus_query
        };
        for (int i = 0; i < lvs.length; i++) {
            list.add(new MenuModel(icons[i],lvs[i]));
        }

        MenuListAdapter adapter = new MenuListAdapter(this, list);
        mLeftMenuLV.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    /**
     * 初始化侧边菜单栏
     */
    private void initSlidingMenu() {
        mMenu = new SlidingMenu(this);
        mMenu.setMode(SlidingMenu.LEFT);
        mMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        mMenu.setShadowWidthRes(R.dimen.shadow_width);
        mMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        mMenu.setBehindWidth(300);
        mMenu.setFadeDegree(0.35f);
        mMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        mMenu.setMenu(R.layout.leftmenu1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout7:
                startActivity(new Intent(MainActivity.this, IpSetActivity.class));
                break;
        }
    }

    @Override
    protected void onAfter() {
        mMenu.toggle();
    }
    @SuppressLint("StaticFieldLeak")
    private void initListener() {
        btnSetCarAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        URL url;
                        try {
                            url = new URL(ApiService.getAllSense);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setDoInput(true);
                            conn.setDoOutput(true);
                            conn.setConnectTimeout(3000);
                            conn.setRequestMethod("POST"); // 设置请求方式
                            conn.setRequestProperty("Charset", "UTF-8");// 设置编码格式
                            conn.setUseCaches(false);
                            conn.setInstanceFollowRedirects(true);
                            conn.setRequestProperty("Content-Type", "application/json");
                            conn.connect();
                            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                            JSONObject jsonObj = new JSONObject();
                            jsonObj.put("UserName","user1");
                            out.writeBytes(jsonObj.toString());

                            //读取响应
                            int responseCode = conn.getResponseCode();

                            if (responseCode == 200) {
                                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                String lines = reader.readLine();
                                Message msg = Message.obtain();
                                msg.what = 0;
                                msg.obj = lines;
                                handler.sendMessage(msg);
                                reader.close();
                            } else {
                                Message msg = Message.obtain();
                                msg.what = 1;
                                msg.obj = "请求失败，请求码为：" + responseCode;
                                handler.sendMessage(msg);
                            }
                            // 断开连接
                            out.flush();
                            out.close();
                            conn.disconnect();

                        } catch (Exception e) {
                            e.printStackTrace();
                            Message msg = Message.obtain();
                            msg.what = -1;
                            msg.obj = "出错了：" + e;
                            handler.sendMessage(msg);
                        }
                    }
                }).start();
            }
        });
    }
}


