package com.kaolafm.kaolaautoupdater;

import android.content.Context;

import com.kaolafm.kaolaautoupdater.bean.UpdateInfo;
import com.kaolafm.kaolaautoupdater.download.DownloadListener;
import com.kaolafm.kaolaautoupdater.download.DownloadManager;
import com.kaolafm.kaolaautoupdater.sdk.IResultCallback;
import com.kaolafm.kaolaautoupdater.sdk.UpdaterSdk;

import java.io.File;
import java.util.List;

/**
 * Created by V on 2015/9/22.
 */
public class Model {
    private Context context;
    private ViewDataManager mViewDataManager;
    private DownloadManager mDownloadManager;

    public Model(Context context) {
        this.context = context;
        UpdaterSdk.getInstance().init(context);
        mViewDataManager = new ViewDataManager();
        mDownloadManager = new DownloadManager();
    }

    public void requestAllUpdateInfos(IResultCallback<List<UpdateInfo>> callback) {
        UpdaterSdk.getInstance().requestAllUpdateInfos(callback);
    }

    public ViewDataManager getViewDataManager() {
        return mViewDataManager;
    }

    public DownloadManager getDownloadManager() {
        return mDownloadManager;
    }

    /**
     * 在后台线程更新所有可以更新的应用
     *
     * @param infos
     * @param downloadListener
     */
    public void updateAllAppsInBackgroundThread(List<UpdateInfo> infos, DownloadListener downloadListener) {
        if (infos != null && !infos.isEmpty()) {
            int size = infos.size();
            for (int i = 0; i < size; i++) {
                UpdateInfo info = infos.get(i);
                updateAppInBackgroundThread(i, info, downloadListener);
            }
        }

    }


    /**
     * 下载某一个app
     * @param index
     * @param info
     * @param listener
     */
    public void updateAppInBackgroundThread(int index, UpdateInfo info, DownloadListener listener) {
        String localPath = context.getCacheDir().getAbsolutePath() + File.separator + index + ".apk";
        getDownloadManager().download(index, info.apkUrl, localPath, listener);
    }
}
