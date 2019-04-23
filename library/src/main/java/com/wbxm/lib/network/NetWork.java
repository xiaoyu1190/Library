package com.wbxm.lib.network;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/3.
 */

public class NetWork {

    private static NetWork instance = null;

    private static OkHttpClient mClient;

    public static NetWork getInstance() {
        if (instance == null) {
            instance = new NetWork();
        }
        if (mClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS);
            mClient = builder.build();
        }
        return instance;
    }

    public interface NetWorkRequestListener {
        void onSuccess(JSONObject response);

        void onFailer(int code, String result);

        void onError();
    }

    public interface NetWorkdownloadAppRequestListener {
        void onProgress(long totalSize, long bytesWritten);

        void onSuccess();

        void onError();
    }

    public interface NetWorkuploadFileRequestListener {

        void onSuccess(JSONObject response);

        void onFailer(int code, String result);

        void onError();
    }

    public Call getRequest(String urlRequest, HashMap<String, String> headers, final NetWorkRequestListener listener) {
        Log.i("NETWORD_OKHTTP", "urlRequest: " + urlRequest);
        Request.Builder builder = new Request.Builder().url(urlRequest);
        //添加头部信息
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        Call call = mClient.newCall(builder.build());
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {//非UI线程
                if (listener != null) {
                    listener.onError();
                }
                Log.i("NETWORD_OKHTTP", e.toString() + "");
            }

            @Override
            public void onResponse(Call call, Response res) throws IOException {//UI线程
                String result = res.body().string();
                Log.i("NETWORD_OKHTTP", "onResponse: code->" + res.code() + ",result->" + result);
                if (listener == null) {
                    return;
                }
                if (res.code() == 200) {
                    try {
                        JSONObject response = JSON.parseObject(result);
                        if (response != null) {
                            listener.onSuccess(response);
                        } else {
                            listener.onError();
                        }
                    } catch (Exception e) {
                        listener.onError();
                    }
                } else {
                    listener.onFailer(res.code(), result);
                }
            }
        });
        return call;
    }

    public Call postRequest(String urlRequest, HashMap<String, String> headers, JSONObject jsonRequest, final NetWorkRequestListener listener) {
        MediaType type = MediaType.parse("application/json; charset=utf-8");
        Log.i("NETWORD_OKHTTP", "urlRequest: " + urlRequest);
        Log.i("NETWORD_OKHTTP", "postRequest: " + jsonRequest.toString());
        RequestBody formBody = RequestBody.create(type, jsonRequest.toString());
        Request.Builder builder = new Request.Builder().url(urlRequest).post(formBody);
        //添加头部信息
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        Call call = mClient.newCall(builder.build());
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {//非UI线程
                if (listener != null) {
                    listener.onError();
                }
                Log.i("NETWORD_OKHTTP", e.toString() + "");
            }

            @Override
            public void onResponse(Call call, Response res) throws IOException {//非UI线程
                String result = res.body().string();
                Log.i("NETWORD_OKHTTP", "onResponse: code->" + res.code() + ",result->" + result);
                if (listener == null) {
                    return;
                }
                if (res.code() == 200) {
                    try {
                        JSONObject response = JSON.parseObject(result);
                        if (response != null) {
                            listener.onSuccess(response);
                        } else {
                            listener.onError();
                        }
                    } catch (Exception e) {
                        listener.onError();
                    }
                } else {
                    listener.onFailer(res.code(), result);
                }
            }
        });
        return call;
    }

    public Call deleteRequest(String urlRequest, HashMap<String, String> headers, JSONObject jsonRequest, final NetWorkRequestListener listener) {
        MediaType type = MediaType.parse("application/json; charset=utf-8");
        Log.i("NETWORD_OKHTTP", "urlRequest: " + urlRequest);
        Log.i("NETWORD_OKHTTP", "postRequest: " + jsonRequest.toString());
        RequestBody formBody = RequestBody.create(type, jsonRequest.toString());
        Request.Builder builder = new Request.Builder().url(urlRequest).delete(formBody);
        //添加头部信息
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        Call call = mClient.newCall(builder.build());
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {//非UI线程
                if (listener != null) {
                    listener.onError();
                }
                Log.i("NETWORD_OKHTTP", e.toString() + "");
            }

            @Override
            public void onResponse(Call call, Response res) throws IOException {//非UI线程
                String result = res.body().string();
                Log.i("NETWORD_OKHTTP", "onResponse: code->" + res.code() + ",result->" + result);
                if (listener == null) {
                    return;
                }
                if (res.code() == 200) {
                    try {
                        JSONObject response = JSON.parseObject(result);
                        if (response != null) {
                            listener.onSuccess(response);
                        } else {
                            listener.onError();
                        }
                    } catch (Exception e) {
                        listener.onError();
                    }
                } else {
                    listener.onFailer(res.code(), result);
                }
            }
        });
        return call;
    }

    public void uploadFile(String urlRequest, String Token, String filename, File file, Map<String, String> params, final NetWorkRequestListener listener) {
        if (listener == null) {
            return;
        }
        Log.i("NETWORD_OKHTTP", "urlRequest: " + urlRequest);
        //MultipartBody多功能的请求实体对象,,,formBody只能传表单形式的数据
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.setType(MultipartBody.FORM);
        //参数
        if (params != null) {
            for (String key : params.keySet()) {
                builder.addFormDataPart(key, params.get(key));
            }
        }
        builder.addFormDataPart("filename", filename, RequestBody.create(MediaType.parse("application/octet-stream"), file));
        //构建
        MultipartBody multipartBody = builder.build();
        //创建Request
        Request request = new Request.Builder()
                .url(urlRequest)
                .post(multipartBody)
                .addHeader("Authorization", Token)
                .build();
        //得到Call
        Call call = mClient.newCall(request);
        //执行请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {//非UI线程
                if (listener != null) {
                    listener.onError();
                }
                Log.i("NETWORD_OKHTTP", e.toString() + "");
            }

            @Override
            public void onResponse(Call call, Response res) throws IOException {//非UI线程
                String result = res.body().string();
                Log.i("NETWORD_OKHTTP", "onResponse: code->" + res.code() + ",result->" + result);
                if (listener == null) {
                    return;
                }
                if (res.code() == 200) {
                    try {
                        JSONObject response = JSON.parseObject(result);
                        if (response != null) {
                            listener.onSuccess(response);
                        } else {
                            listener.onError();
                        }
                    } catch (Exception e) {
                        listener.onError();
                    }
                } else {
                    listener.onFailer(res.code(), result);
                }
            }
        });
    }

}
