
package com.lenovo.studentClient.activity;

import android.os.Bundle;
import android.view.View;
import com.lenovo.studentClient.R;


/**
 * ip设置activity，包涵网络连接方式切换，IP设置
 */
public class IpSetActivity extends BaseActivity {

    @Override
    protected void initializeWithState(Bundle savedInstanceState) {

    }

    @Override
    protected String getLayoutTitle() {
        return getString(R.string.title_ip);
    }

    @Override
    protected void onAfter() {
//        finish();
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ipset;
    }

    @Override
    public void onClick(View v) {
    }
}
