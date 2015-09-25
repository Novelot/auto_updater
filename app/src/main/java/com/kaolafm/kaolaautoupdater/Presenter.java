package com.kaolafm.kaolaautoupdater;

import android.content.Context;
import android.media.MediaPlayer;

import com.kaolafm.kaolaautoupdater.bean.UpdateInfo;
import com.kaolafm.kaolaautoupdater.download.DownloadListener;
import com.kaolafm.kaolaautoupdater.sdk.ErrorInfo;
import com.kaolafm.kaolaautoupdater.sdk.IResultCallback;
import com.kaolafm.kaolaautoupdater.util.Logger;

import java.io.IOException;
import java.util.List;

/**
 * Created by V on 2015/9/22.
 */
public class Presenter implements DownloadListener {
    private Model mModel;
    private IView iView;

    public Presenter(Context context, IView iView) {
        this.iView = iView;
        this.mModel = new Model(context);
    }

    /**
     * 获取可更新的的应用的更新信息
     */
    public void getAllUpdateInfos() {
        iView.showLoading("正在加载...");
        mModel.requestAllUpdateInfos(new IResultCallback<List<UpdateInfo>>() {
            @Override
            public void onSuccess(List<UpdateInfo> result) {
                iView.hideLoading();
                mModel.getViewDataManager().update(result);
                iView.updateAllUpdateInfos(result);
            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {
                iView.hideLoading();
                iView.showToast("获取失败");
                iView.updateAllUpdateInfos(null);
            }
        });
    }

    /**
     * 更新全部App
     */
    public void updateAllApps() {
        /*获取界面数据*/
        List<UpdateInfo> infos = mModel.getViewDataManager().list();
        if (infos != null && !infos.isEmpty()) {
            mModel.updateAllAppsInBackgroundThread(infos, (DownloadListener) this);
        } else {
            iView.showToast("无可更新应用");
        }
    }

    /*********************************
     * DownloadListener
     *****************************************/
    @Override
    public void onError(final int downloadId, Exception e) {
        Logger.i(e.toString());
    }

    @Override
    public void onProgress(final int downloadId, long progress, long totalSize) {
        Logger.i(String.format("%d [%d,%d]", downloadId, progress, totalSize));
        iView.updateItemProgress(downloadId, progress, totalSize);
    }

    @Override
    public void onFinish(final int downloadId, String path) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateThisApp(int index, UpdateInfo data) {
        List<UpdateInfo> infos = mModel.getViewDataManager().list();
        if (infos != null && !infos.isEmpty()) {
            UpdateInfo info = infos.get(index);
            mModel.updateAppInBackgroundThread(index, info, (DownloadListener) this);
        }
    }

    /**************************************************************************/
}
