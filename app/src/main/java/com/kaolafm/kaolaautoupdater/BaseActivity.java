package com.kaolafm.kaolaautoupdater;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.kaolafm.kaolaautoupdater.bean.UpdateInfo;

import java.util.List;

/**
 * Created by V on 2015/9/22.
 */
public abstract class BaseActivity extends AppCompatActivity implements IView {

    private static final int UPDATE_ALL_UPDATE_INFOS = 1110111001;
    protected Presenter mPresenter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_ALL_UPDATE_INFOS:
                    List<UpdateInfo> result = (List<UpdateInfo>) msg.obj;
                    updateListView(result);
                    break;
            }

        }
    };
    private ProgressDialog mLoadingDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new Presenter(this, this);
    }

    /*******************************************************************************************/
    protected abstract void updateListView(List<UpdateInfo> result);

    /*******************************************************************************************/
    @Override
    public void updateAllUpdateInfos(List<UpdateInfo> result) {
        /*确保在主线程,操作UI*/
        Message msg = Message.obtain();
        msg.obj = result;
        msg.what = UPDATE_ALL_UPDATE_INFOS;
        mHandler.sendMessage(msg);
    }

    @Override
    public void hideLoading() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mLoadingDialog != null) {
                    mLoadingDialog.cancel();
                }
            }
        });

    }

    @Override
    public void showLoading(@Nullable final String s) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mLoadingDialog == null) {
                    mLoadingDialog = new ProgressDialog(BaseActivity.this);
                    mLoadingDialog.setTitle(s);
                    mLoadingDialog.setMessage(s);
                    mLoadingDialog.setIndeterminate(true);
                    mLoadingDialog.setCancelable(true);
                }

                if (!mLoadingDialog.isShowing())
                    mLoadingDialog.show();
            }
        });
    }

    @Override
    public void showLoading(@StringRes int rid) {
        showLoading(getString(rid));
    }

    @Override
    public void showToast(@NonNull final String info) {

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, info, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void showToast(@StringRes final int rid) {

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, rid, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
