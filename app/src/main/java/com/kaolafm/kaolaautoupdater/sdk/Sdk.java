package com.kaolafm.kaolaautoupdater.sdk;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kaolafm.kaolaautoupdater.util.Logger;

import java.util.Map;

/**
 * Created by V on 2015/9/23.
 */
public class Sdk {
    /**
     * 添加get方式的请求
     *
     * @param <T>
     * @param context
     * @param url
     */
    protected static <T> void addGetRequest(Context context, String url,
                                            final IResultCallback<T> callback, final Class<T> clazz) {
        StringRequest request = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Logger.v(response);
                        if (callback != null) {
                            T t = JSON.parseObject(response, clazz);
                            callback.onSuccess(t);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.e(error.toString());
                if (callback != null) {
                    int errCode = 0;
                    if (error != null && error.networkResponse != null) {
                        errCode = error.networkResponse.statusCode;
                    }
                    callback.onFailure(new ErrorInfo(errCode));
                }
            }
        });
        Volley.newRequestQueue(context).add(request);
    }

    /**
     * 添加post方式的请求
     *
     * @param <T>
     * @param context
     * @param url
     * @param params
     */
    protected static <T> void addPostRequest(Context context, String url,
                                             final Map<String, String> params,
                                             final IResultCallback<T> callback, final Class<T> clazz) {
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Logger.v(response);
                        if (callback != null) {
                            T t = JSON.parseObject(response, clazz);
                            callback.onSuccess(t);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.e(error.toString());
                if (callback != null) {
                    int errCode = 0;
                    if (error != null && error.networkResponse != null) {
                        errCode = error.networkResponse.statusCode;
                    }
                    callback.onFailure(new ErrorInfo(errCode));
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        Volley.newRequestQueue(context).add(request);
    }
}
