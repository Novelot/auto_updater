package com.kaolafm.kaolaautoupdater.sdk;

import android.content.Context;
import android.os.SystemClock;

import com.android.volley.toolbox.StringRequest;
import com.kaolafm.kaolaautoupdater.BuildConfig;
import com.kaolafm.kaolaautoupdater.R;
import com.kaolafm.kaolaautoupdater.bean.UpdateInfo;
import com.kaolafm.kaolaautoupdater.json.JAllUpdateInfos;

import java.util.ArrayList;
import java.util.List;

/**
 * 接口请求类
 * Created by V on 2015/9/23.
 */
public class UpdaterSdk extends Sdk {

    private static UpdaterSdk mInstance;
    private Context context;

    private UpdaterSdk() {
    }

    public static UpdaterSdk getInstance() {
        if (mInstance == null) {
            synchronized (UpdaterSdk.class) {
                if (mInstance == null) {
                    mInstance = new UpdaterSdk();
                }
            }
        }
        return mInstance;
    }

    public UpdaterSdk init(Context context) {
        this.context = context;
        return mInstance;
    }

    /**
     * 请求升级信息
     *
     * @param callback
     */
    public void requestAllUpdateInfos(final IResultCallback<List<UpdateInfo>> callback) {
        String url = Api.getAllUpdateInfosUrl();
        addGetRequest(context, url, new IResultCallback<JAllUpdateInfos>() {
            @Override
            public void onFailure(ErrorInfo errorInfo) {

//                if (callback != null) {
//                    callback.onFailure(errorInfo);
//                }

                     /*测试*/
                    List<UpdateInfo> infos = new ArrayList<UpdateInfo>();
                    for (int i = 0; i < 10; i++) {
                        UpdateInfo item = new UpdateInfo();
                        item.icon = R.mipmap.ic_launcher;
                        item.appName = "App-" + i;
                        item.version = i + "";
                        item.apkUrl = "http://www.novelot.com/test.mp3";
                        infos.add(item);
                    }
                    if (callback != null) {
                        callback.onSuccess(infos);
                    }
            }

            @Override
            public void onSuccess(JAllUpdateInfos result) {
                List<UpdateInfo> infos = result.asList();
                if (callback != null) {
                    callback.onSuccess(infos);
                }
            }
        }, JAllUpdateInfos.class);
    }
}
