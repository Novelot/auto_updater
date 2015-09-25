package com.kaolafm.kaolaautoupdater;

import android.test.InstrumentationTestCase;

import com.kaolafm.kaolaautoupdater.download.DownloadCore;
import com.kaolafm.kaolaautoupdater.download.DownloadListener;
import com.kaolafm.kaolaautoupdater.download.LocalPathEmptyException;
import com.kaolafm.kaolaautoupdater.download.UrlEmptyException;
import com.kaolafm.kaolaautoupdater.util.Logger;

/**
 * Created by V on 2015/9/24.
 */
public class DownloadCoreTest extends InstrumentationTestCase {

    public static final String PATH_LOACL = "/sdcard/Download/test/test.mp3";
    public static final String URL_TEST = "http://www.novelot.com/test.mp3";

    public void testDownload() {
        DownloadCore.download(0,URL_TEST, PATH_LOACL, 0, new DownloadListener() {
            @Override
            public void onError(int id,Exception e) {
                Logger.i(e.toString());
            }

            @Override
            public void onProgress(int id,long progress, long totalSize) {
                Logger.i(String.format("[%d,%d]", progress, totalSize));
            }

            @Override
            public void onFinish(int id,String path) {
                Logger.i("onFinish:" + path);
            }
        });
    }

    public void testNullLocalPath() {
        DownloadCore.download(1,URL_TEST, null, 0, new DownloadListener() {
            @Override
            public void onError(int id,Exception e) {
                assertEquals(true, e instanceof LocalPathEmptyException);
            }

            @Override
            public void onProgress(int id,long progress, long totalSize) {
                Logger.i(String.format("[%d,%d]", progress, totalSize));
            }

            @Override
            public void onFinish(int id,String path) {
                Logger.i("onFinish:" + path);
            }
        });
    }

    public void testNullUrl() {
        DownloadCore.download(2,null, PATH_LOACL, 0, new DownloadListener() {
            @Override
            public void onError(int id,Exception e) {
                assertEquals(true, e instanceof UrlEmptyException);
            }

            @Override
            public void onProgress(int id,long progress, long totalSize) {
                Logger.i(String.format("[%d,%d]", progress, totalSize));
            }

            @Override
            public void onFinish(int id,String path) {
                Logger.i("onFinish:" + path);
            }
        });
    }
}
