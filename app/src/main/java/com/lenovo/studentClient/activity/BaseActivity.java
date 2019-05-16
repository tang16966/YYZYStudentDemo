
package com.lenovo.studentClient.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lenovo.studentClient.R;
import com.lenovo.studentClient.utils.LoadingDialog;

import butterknife.ButterKnife;

/**
 * BaseActivity 所有Activity都是继承此类
 */
public abstract class BaseActivity extends FragmentActivity implements OnClickListener {
    /**
     * 标题信息
     */
    protected TextView mTitleTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
//        getIP();
        initView();
        initializeWithState(savedInstanceState);
        initData();
        setTitleLayout();
    }
    protected abstract void initializeWithState(Bundle savedInstanceState);

    /**
     * 设置页面标题栏
     */
    private void setTitleLayout() {
        LinearLayout layout =findViewById(R.id.titlelayout);
        ImageView imageView = layout.findViewById(R.id.back);
        mTitleTV = layout.findViewById(R.id.title);
        mTitleTV.setText(getLayoutTitle());
    }

    /**
     * @return 页面标题
     */
    protected abstract String getLayoutTitle();

    /**
     * 点击返回图标处理事件
     */
    protected abstract void onAfter();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * @return 对应的布局文件id
     */
    protected abstract int getLayoutId();


    private LoadingDialog mLoadingDialog;


    protected void showLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }
        mLoadingDialog.show();
    }

    protected void onRequestFinish() {
        hideLoadingDialog();
    }

    private void hideLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideLoadingDialog();
    }


}
