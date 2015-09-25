package com.kaolafm.kaolaautoupdater.download;

/**
 * 下载监听
 */
public interface DownloadListener {
    void onProgress(final int downloadId, long progress, long totalSize);

    void onFinish(final int downloadId, String path);

    void onError(final int downloadId, Exception e);
}