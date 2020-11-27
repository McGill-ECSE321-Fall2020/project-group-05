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

/**
 * HttpUtils provides helper methods to simplify performing POST and GET requests to the specific
 * backend for this project.
 */
public class HttpUtils {

    public static final String DEFAULT_BASE_URL = "https://vis-art-application-production.herokuapp.com/";

    private static String baseUrl;
    private static OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    static {
        baseUrl = DEFAULT_BASE_URL;
    }

    /**
     * Performs a POST request to the url endpoint, given a RequestBody, and okhttp3 callback method.
     *
     * @param url         the relative url path to call
     * @param requestBody the request body data to send
     * @param callback    the method called on response
     */
    public static void postBody(String url, RequestBody requestBody, Callback callback) {
        Request request = new Request.Builder()
                .url(getAbsoluteUrl(url))
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    /**
     * Performs a POST request to the url endpoint, given a json string, and okhttp3 callback method.
     *
     * @param url      the relative url path to call
     * @param json     the request json data to send
     * @param callback the method called on response
     */
    public static void post(String url, String json, Callback callback) {
        RequestBody requestBody = RequestBody.create(json, JSON);
        postBody(url, requestBody, callback);
    }

    /**
     * Performs a POST request to the url endpoint, given a 2d array of key-value pairs, and okhttp3 callback method.
     * <p>
     * Each sub-array of the body should be two values, first a key, then the corresponding value.
     *
     * @param url      the relative url path to call
     * @param body     the form data to send as array
     * @param callback the method called on response
     */
    public static void postForm(String url, String[][] body, Callback callback) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String[] pair : body) {
            if (pair.length >= 2) {
                builder.add(pair[0], pair[1]);
            }
        }

        postBody(url, builder.build(), callback);
    }

    /**
     * Performs a POST request to the url endpoint, given a 2d array of key-value pairs, and a simpler callback method.
     * <p>
     * Each sub-array of the body should be two values, first a key, then the corresponding value.
     *
     * @param url     the relative url path to call
     * @param body    the form data to send as array
     * @param success the method called on response
     */
    public static void postForm(String url, String[][] body, SimpleCallback<Response> success) {
        postForm(url, body, success, (f) -> {
            System.err.println(f);
        });
    }

    /**
     * Performs a POST request to the url endpoint, given a 2d array of key-value pairs, a simpler callback method,
     * and an optional failure handler.
     * <p>
     * Each sub-array of the body should be two values, first a key, then the corresponding value.
     *
     * @param url     the relative url path to call
     * @param body    the form data to send as array
     * @param success the method called on response
     * @param failure the method called on failed call
     */
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

    /**
     * Performs a POST request to the url endpoint, given a FormBody, and okhttp3 callback method.
     *
     * @param url      the relative url path to call
     * @param formBody the form body data to send
     * @param callback the method called on response
     */
    public static void postForm(String url, FormBody formBody, Callback callback) {
        postBody(url, formBody, callback);
    }


    /**
     * Performs a GET request to the url endpoint, given a 2d array of queryParameters, a simpler success handler, and failure handler.
     * <p>
     * Each sub-array of the queryParameters should be of length 2, representing a key-value pair to be passed in.
     *
     * @param url             the relative url path to call
     * @param queryParameters the query params to send as array
     * @param success         the method called on response
     * @param failure         the method called on failed call
     */
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

    /**
     * Performs a GET request to the url endpoint, given a 2d array of queryParameters, a simpler success handler.
     * <p>
     * Each sub-array of the queryParameters should be of length 2, representing a key-value pair to be passed in.
     *
     * @param url             the relative url path to call
     * @param queryParameters the query params to send as array
     * @param success         the method called on response
     */
    public static void get(String url, String[][] queryParameters, SimpleCallback<Response> success) {
        get(url, queryParameters, success, (f) -> {
            System.err.println(f);
        });
    }

    /**
     * Performs a GET request to the url endpoint, given a 2d array of queryParameters, and a okhttp3 Callback.
     * <p>
     * Each sub-array of the queryParameters should be of length 2, representing a key-value pair to be passed in.
     *
     * @param url             the relative url path to call
     * @param queryParameters the query params to send as array
     * @param callback        the callback upon response or failure
     */
    public static void get(String url, String[][] queryParameters, Callback callback) {
        String params = "?";
        for (String[] query : queryParameters) {
            if (query.length >= 2) {
                params += query[0] + "=" + encodeValue(query[1]).replace("+", "%20");
            }
        }
        get(url + params, callback);
    }

    /**
     * Performs a GET request to the url endpoint given a okhttp3 Callback.
     *
     * @param url      the relative url path to call
     * @param callback the callback upon response or failure
     */
    public static void get(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(getAbsoluteUrl(url))
                .get()
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    /**
     * Converts the relative url endpoint to an absolute url, including the baseUrl, for REST API calls.
     *
     * @param relativeUrl the relative url endpoint
     * @return an absolute url based on baseUrl and the relative url
     */
    private static String getAbsoluteUrl(String relativeUrl) {
        return baseUrl + relativeUrl;
    }

    /**
     * Converts a string value into percent-encoded string for form and query data.
     *
     * @param value any string
     * @return the given string in percent-encoding UTF-8
     */
    public static String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * Converts any percent-encoded UTF-8 string into its proper string value
     *
     * @param value the percent-encoded UTF-8 string
     * @return the original decoded value
     */
    public static String decodeValue(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * An interface to provide a simpler callback method, usable with lambdas.
     *
     * @param <T> the type of parameter to pass into the callback function
     */
    public static interface SimpleCallback<T> {
        void callback(T t);
    }
}