package com.taotao.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpClientTest {
    public void doGet()throws Exception{
        //创建一个httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建以个get对象
        HttpGet get = new HttpGet("http://www.sogou.com");
        //执行请求
        CloseableHttpResponse response = httpClient.execute(get);
        //取响应的结果
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        HttpEntity entity = response.getEntity();
        String entityStr = EntityUtils.toString(entity,"utf-8");
        System.out.println(entityStr);
        //关闭连接
        response.close();
        httpClient.close();
    }
    public void doGetWithParam()throws Exception{
        //创建一个httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        URIBuilder uriBuilder = new URIBuilder("http://www.sogou.com/web");
        uriBuilder.addParameter("query","lol");
        HttpGet get = new HttpGet(uriBuilder.build());
        //执行请求
        CloseableHttpResponse response = httpClient.execute(get);
        //取响应的结果
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        HttpEntity entity = response.getEntity();
        String entityStr = EntityUtils.toString(entity,"utf-8");
        System.out.println(entityStr);
        //关闭连接
        response.close();
        httpClient.close();
    }

    public void doPost() throws Exception {
        //创建一个httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost("http://localhost:8090/httpclient/post.html");
        //执行post请求
        CloseableHttpResponse response = httpClient.execute(post);
        String postStr = EntityUtils.toString(response.getEntity());
        System.out.println(postStr);
        response.close();
        httpClient.close();
    }

    public void doPostWithParam() throws Exception {
        //创建一个httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost("http://localhost:8090/httpclient/post.html");
        //创建一个Entity。模拟一个表单
        List<NameValuePair>list = new ArrayList<>();
        list.add(new BasicNameValuePair("username","张山"));
        list.add(new BasicNameValuePair("password","123456"));
        //包装成一个entity实体
        StringEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list);
        //设置请求的内容
        post.setEntity(urlEncodedFormEntity);
        //执行post请求
        CloseableHttpResponse response = httpClient.execute(post);
        String postStr = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(postStr);
        response.close();
        httpClient.close();
    }
}
