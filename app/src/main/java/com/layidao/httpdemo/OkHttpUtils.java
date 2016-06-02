package com.layidao.httpdemo;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/6/2.
 */
public class OkHttpUtils {
    private static final OkHttpClient okHttpClient = new OkHttpClient();


    /**
     * 获取Request对象
     * @param urlString
     * @return
     */
    private static Request getRequestFromUrl(String urlString){
        Request request = new Request.Builder()
                .url(urlString)
                .build();
        return request;
    }

    /**
     * 获取Response对象
     * @param urlString
     * @return
     * @throws IOException
     */
    private static Response getResponseFromUrl(String urlString) throws IOException{
        Request request = getRequestFromUrl(urlString);
        Response response = okHttpClient.newCall(request).execute();
        return response;
    }

    /**
     * 获取ResponseBody对象
     * @param urlString
     * @return
     * @throws IOException
     */
    private static ResponseBody getResponseBodyFromUrl(String urlString) throws IOException{
        Response response = getResponseFromUrl(urlString);
        if (response.isSuccessful()){
            return response.body();
        }
        return null;
    }

    /**
     * 通过网络请求获取字符串
     * @param urlString
     * @return
     * @throws IOException
     */
    public static String loadStringFromUrl(String urlString) throws IOException{
        ResponseBody responseBody = getResponseBodyFromUrl(urlString);
        if (responseBody != null){
            return responseBody.string();
        }
        return null;
    }

    public static byte[] loadByteFromUrl(String urlString) throws IOException{
        ResponseBody responseBody = getResponseBodyFromUrl(urlString);
        if (responseBody != null){
            return responseBody.bytes();
        }
        return null;
    }

    public static InputStream loadStreamFromUrl(String urlString) throws IOException{
        ResponseBody responseBody = getResponseBodyFromUrl(urlString);
        if (responseBody != null){
            return responseBody.byteStream();
        }
        return null;
    }

}
