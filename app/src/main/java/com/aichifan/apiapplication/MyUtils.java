package com.aichifan.apiapplication;

import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by yoda on 16/8/23.
 */
public class MyUtils {

    /**
     * 访问url获取数据流
     * @param urlStr 链接
     * @param method 方法
     * @param params 参数
     * @return 数据流
     */
    public static InputStream requestByUrl(String urlStr, String method, String params) {
        try {
            if(TextUtils.isEmpty(method)) {
                method = "GET";
            }
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            //如果有参数，默认是json格式数据提交
            if(!TextUtils.isEmpty(params)) {
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                OutputStreamWriter wr = new OutputStreamWriter(os);
                wr.write(params);
                wr.flush();
                wr.close();
                os.close();
            }
            if(conn.getResponseCode() == 200) {
                return conn.getInputStream();
            }
        } catch(MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
