package com.kaolafm.kaolaautoupdater;

import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.kaolafm.kaolaautoupdater.bean.UpdateInfo;
import com.kaolafm.kaolaautoupdater.download.DownloadListener;
import com.kaolafm.kaolaautoupdater.download.DownloadManager;

import java.util.List;

/**
 * Created by V on 2015/9/22.
 */
public interface IView {

    /**
     * 显示加载中
     *
     * @param s
     */
    void showLoading(@Nullable String s);


    void showLoading(@StringRes int rid);

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 显示toast
     *
     * @param info
     */
    void showToast(String info);

    void showToast(@StringRes int rid);

    /**
     * 更新所有升级信息列表
     *
     * @param result
     */
    void updateAllUpdateInfos(List<UpdateInfo> result);

    /**
     * 更新条目的进度
     *
     * @param downloadId
     * @param progress
     * @param totalSize
     */
    void updateItemProgress(int downloadId, long progress, long totalSize);
}
