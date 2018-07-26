//package com.jbwl.util;
//
//import okhttp3.*;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//import org.springframework.util.StringUtils;
//
//import java.io.IOException;
//import java.net.URI;
//import java.net.URISyntaxException;
//
///**
// * @Author: jipeng
// * @Description:
// * @Date: Created in 2018/6/25 9:53
// */
//public class HttpUtil {
//
//    //发起推送请求
//    public static Response requestPush(String postBody, String url) throws IOException {
//        OkHttpClient client = new OkHttpClient();
//        MediaType JSON=MediaType.parse("application/json;charset=utf-8");
//
//        Request request=null;
//        if(StringUtils.hasText(postBody)) {
//            RequestBody body = RequestBody.create(JSON, postBody);
//            request = new Request.Builder()
//                    .url(url)
//                    .post(body)
//                    .build();
//        }else{
//            request = new Request.Builder()
//                    .url(url)
//                    .get()
//                    .build();
//        }
//        return client.newCall(request).execute();
//    }
//
//}
