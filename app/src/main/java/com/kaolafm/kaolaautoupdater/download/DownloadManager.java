package com.kaolafm.kaolaautoupdater.download;

import android.os.Process;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kaolafm.kaolaautoupdater.util.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 下载管理类
 * 其实,这是个单例.哈~哈~哈~没发现吧~
 * <p>
 * Created by V on 2015/9/24.
 */
public class DownloadManager {

    private static BlockingQueue<DownloadTask> queue = new LinkedBlockingQueue<DownloadTask>();
    private static Thread loopThread;

    public DownloadManager() {
        if (loopThread == null) {
            loopThread = new Thread() {
                @Override
                public void run() {
                    Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                    while (true) {
                        try {
                            DownloadTask task = queue.take();
                            task.exe();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            loopThread.start();
        }
    }

    /**
     * 开始下载
     *
     * @param apkUrl           网络地址
     * @param localPath        本地目标地址
     * @param downloadListener 监听器
     */
    public synchronized void download(final int downloadId, @NonNull final String apkUrl, @NonNull final String localPath,
                                      @Nullable final DownloadListener downloadListener) {
        /*将断点续传的逻辑判断放到DownloadTask类中*/
        DownloadTask task = new DownloadTask(downloadId, apkUrl, localPath, downloadListener);
        if (!queue.contains(task)) {
            try {
                queue.put(task);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            Logger.i("queue contains this downloadtask");
        }
    }

}
