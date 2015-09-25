package com.kaolafm.kaolaautoupdater.sdk;

/**
 * Model回调接口
 * Created by V on 2015/9/22.
 */
public interface IResultCallback<T> {
    /**
     * 成功
     *
     * @param result
     */
    void onSuccess(T result);

    /**
     * 失败
     *
     * @param errorInfo
     */
    void onFailure(ErrorInfo errorInfo);
}
