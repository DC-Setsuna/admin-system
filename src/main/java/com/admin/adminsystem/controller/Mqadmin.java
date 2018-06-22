package com.admin.adminsystem.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import okhttp3.RequestBody;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;


/**
 * The type Mqadmin.
 */
@RestController
public class Mqadmin {

    // Define API constants
    private static final String API = "https://106.38.159.103:20070";

    // Avoid SSL by requesting certificate validation
    private static class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

        @Override
        public X509Certificate[] getAcceptedIssuers() {return new X509Certificate[0];}
    }
    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null,  new TrustManager[] { new TrustAllCerts() }, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return ssfFactory;
    }

    /**
     * Gets token.
     *
     * Functions that are executed at the beginning of each API interface to get Tocken
     *
     * @return the token
     */
    public static String getToken() {

        OkHttpClient client = new OkHttpClient().newBuilder().sslSocketFactory(createSSLSocketFactory()).hostnameVerifier(new TrustAllHostnameVerifier())
                .build();

        MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
        RequestBody body = RequestBody.create(mediaType, "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"name\"\r\n\r\nMDXPQMGR\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");
        Request request;
        request = new Request.Builder()
                .url(API + "/ibmmq/rest/v1/login")
                .get()
                .addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
                .addHeader("Authorization", "Basic bXFhZG1pbjptcWFkbWlu")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Postman-Token", "257f313d-a092-4363-a216-21275a0a9efd")
                .build();

        String token = null;
        try {
            Response response = client.newCall(request).execute();
            Headers headers = response.headers();
            HttpUrl loginUrl = request.url();
            List<Cookie> cookies = Cookie.parseAll(loginUrl, headers);
            token = cookies.get(0).value();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     * Gets qmgr.
     *
     * @return the qmgr
     */
    @RequestMapping(value = "/api/Mqadmin/qmgr", method = RequestMethod.GET)
    public JSONObject get_qmgr () {//

        JSONObject list=new JSONObject();

        String token = getToken();

        OkHttpClient client = new OkHttpClient().newBuilder().sslSocketFactory(createSSLSocketFactory()).hostnameVerifier(new TrustAllHostnameVerifier())
                .build();

        Request request;
        request = new Request.Builder()
                .url(API + "/ibmmq/rest/v1/qmgr")
                .get()
                .addHeader("Authorization", "Basic bXFhZG1pbjptcWFkbWlu")
                .addHeader("ibm-mq-rest-csrf-token", token)
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            String data = response.body().string();
            JSONObject jsonObject = JSONObject.parseObject(data);
            list.put("code",200);
            list.put("message","ok");
            list.put("data", jsonObject);
        } catch (IOException e) {
            list.put("code",400);
            list.put("message","interface error");
            list.put("data", "");
        }

        return list;
    }

    /**
     * Gets listener.
     *
     * @return the listener
     */
    @RequestMapping(value = "/api/Mqadmin/qmgr/listener", method = RequestMethod.GET)
    public JSONObject get_listener () {//

        JSONObject list=new JSONObject();

        String token = getToken();

        OkHttpClient client = new OkHttpClient().newBuilder().sslSocketFactory(createSSLSocketFactory()).hostnameVerifier(new TrustAllHostnameVerifier())
                .build();

        Request request;
        request = new Request.Builder()
                .url(API + "/ibmmq/console/internal/ibmmq/qmgr/MDXPQMGR/listener?showStatusAs=MQIACH_LISTENER_STATUS&hideSystem=true")
                .get()
                .addHeader("Authorization", "Basic bXFhZG1pbjptcWFkbWlu")
                .addHeader("ibm-mq-rest-csrf-token", token)
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            String data = response.body().string();
            JSONArray jsonArray = JSONArray.parseArray(data);
            list.put("code",200);
            list.put("message","ok");
            list.put("data", jsonArray);
        } catch (IOException e) {
            list.put("code",400);
            list.put("message","interface error");
            list.put("data", "");
        }

        return list;
    }

    /**
     * Gets channel.
     *
     * @return the channel
     */
    @RequestMapping(value = "/api/Mqadmin/qmgr/channel", method = RequestMethod.GET)
    public JSONObject get_channel () {//

        JSONObject list=new JSONObject();

        String token = getToken();

        OkHttpClient client = new OkHttpClient().newBuilder().sslSocketFactory(createSSLSocketFactory()).hostnameVerifier(new TrustAllHostnameVerifier())
                .build();

        Request request;
        request = new Request.Builder()
                .url(API + "/ibmmq/console/internal/ibmmq/qmgr/MDXPQMGR/channel?type=all&showStatusAs=MQIACH_CHANNEL_STATUS&hideSystem=true")
                .get()
                .addHeader("Authorization", "Basic bXFhZG1pbjptcWFkbWlu")
                .addHeader("ibm-mq-rest-csrf-token", token)
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            String data = response.body().string();
            JSONArray jsonArray = JSONArray.parseArray(data);
            list.put("code",200);
            list.put("message","ok");
            list.put("data", jsonArray);
        } catch (IOException e) {
            list.put("code",400);
            list.put("message","interface error");
            list.put("data", "");
        }

        return list;
    }

    /**
     * Gets queue.
     *
     * @return the queue
     */
    @RequestMapping(value = "/api/Mqadmin/qmgr/queue", method = RequestMethod.GET)
    public JSONObject get_queue () {//

        JSONObject list=new JSONObject();

        String token = getToken();

        OkHttpClient client = new OkHttpClient().newBuilder().sslSocketFactory(createSSLSocketFactory()).hostnameVerifier(new TrustAllHostnameVerifier())
                .build();

        Request request;
        request = new Request.Builder()
                .url(API + "/ibmmq/console/internal/ibmmq/qmgr/MDXPQMGR/queue?type=all&hideSystem=true")
                .get()
                .addHeader("Authorization", "Basic bXFhZG1pbjptcWFkbWlu")
                .addHeader("ibm-mq-rest-csrf-token", token)
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            String data = response.body().string();
            JSONArray jsonArray = JSONArray.parseArray(data);
            list.put("code",200);
            list.put("message","ok");
            list.put("data", jsonArray);
        } catch (IOException e) {
            list.put("code",400);
            list.put("message","interface error");
            list.put("data", "");
        }

        return list;
    }
}
