package com.example.visartmobile.util;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {

    public static final String DEFAULT_BASE_URL = "https://vis-art-application-production.herokuapp.com/";

    private static String baseUrl;
    private static OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    static {
        baseUrl = DEFAULT_BASE_URL;
    }

    public static void post(String url, String json, Callback callback) {
        RequestBody requestBody = RequestBody.create(json, JSON);
        postBody(url, requestBody, callback);
    }

    public static void postForm(String url, String[][] body, Callback callback) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String[] pair : body) {
            if (pair.length >= 2) {
                builder.add(pair[0], pair[1]);
            }
        }

        postBody(url, builder.build(), callback);
    }

    public static void postForm(String url, String[][] body, SimpleCallback<Response> success) {
        postForm(url, body, success, (f) -> {
            System.err.println(f);
        });
    }

    public static void postForm(String url, String[][] body, SimpleCallback<Response> success, SimpleCallback<String> failure) {
        postForm(url, body, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                failure.callback("Could not perform REST API Call");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                success.callback(response);
            }
        });
    }

    public static void postForm(String url, FormBody formBody, Callback callback) {
        postBody(url, formBody, callback);
    }


    public static void postBody(String url, RequestBody requestBody, Callback callback) {
        Request request = new Request.Builder()
                .url(getAbsoluteUrl(url))
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
    public static void get(String url, String[][] queryParameters, SimpleCallback<Response> success, SimpleCallback<String> failure) {
        get(url, queryParameters, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                failure.callback("Could not perform REST API Call");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                success.callback(response);
            }
        });
    }

    public static void get(String url, String[][] queryParameters, SimpleCallback<Response> success) {
        get(url,queryParameters,success,(f)->{
            System.err.println(f);
        });
    }

    public static void get(String url, String[][] queryParameters, Callback callback) {
        String params = "?";
        for (String[] query : queryParameters) {
            if (query.length >= 2) {
                params += query[0] + "=" + encodeValue(query[1]).replace("+", "%20");
            }
        }
        get(url + params, callback);
    }

    public static void get(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(getAbsoluteUrl(url))
                .get()
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        System.out.println("The URL is: " + baseUrl + relativeUrl);
        return baseUrl + relativeUrl;
    }

    public static String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static String decodeValue(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static interface SimpleCallback<T> {
        void callback(T t);
    }
}