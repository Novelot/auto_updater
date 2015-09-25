package com.kaolafm.kaolaautoupdater.download;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * 命令模式
 * Created by V on 2015/9/25.
 */
public class DownloadTask {

    private int downloadId;
    private String apkUrl;
    private String localPath;
    private DownloadListener downloadListener;

    public DownloadTask(int downloadId, @NonNull String apkUrl, @NonNull String localPath, @Nullable DownloadListener downloadListener) {
        this.downloadId = downloadId;
        this.apkUrl = apkUrl;
        this.localPath = localPath;
        this.downloadListener = downloadListener;
    }

    public void exe() {
        /*判断apkUrl是否已经在队列中*/
            /*如果在,则判断目标地址localPath是否相同,*/
                    /*如果不同,则在之前任务下载完后,复制一份到新地址*/

        //查询上次下载的进度
        DownloadCore.download(downloadId, apkUrl, localPath, 0, new HandlerDownloadListener(downloadId, downloadListener));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DownloadTask that = (DownloadTask) o;

        if (downloadId != that.downloadId) return false;
        if (!apkUrl.equals(that.apkUrl)) return false;
        return localPath.equals(that.localPath);

    }

    @Override
    public int hashCode() {
        int result = downloadId;
        result = 31 * result + apkUrl.hashCode();
        result = 31 * result + localPath.hashCode();
        return result;
    }
}
