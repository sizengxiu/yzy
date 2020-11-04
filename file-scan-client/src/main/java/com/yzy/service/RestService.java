package com.yzy.service;

import com.alibaba.fastjson.JSONObject;
import com.yzy.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

@Slf4j
@Service
public class RestService {

    public static String url="http://localhost:8080/fileScanServer/fileScan/test";
    public static void main(String[] args) {

    }

    /**
     * 发送post请求
     * @param url
     * @param param
     * @param tryTimes
     * @return
     */
    public Result doPost(String url, String param, int tryTimes){
        int count=1;
        Result result = doPost(url, param);
        while(!result.isSuccess() && count<tryTimes){
            log.error("第{}次请求发送失败，请求地址:url={},请求数据：param={}",count,url,param);
            count++;
            result=doPost(url,param);
        }
        if(result.isSuccess()){
            log.info("第{}次请求发送成功，请求地址:url={},请求数据：param={}，返回数据：data={}",count,url,param,JSONObject.toJSONString(result));
        }else{
            log.error("第{}次请求发送失败，请求地址:url={},请求数据：param={}",count,url,param);
        }
        return result;
    }

    /**
     * 发送post请求
     * @param url
     * @param param
     * @return
     */
    public Result doPost(String url,String param){
        JSONObject json=null;
        try {
            // 创建httpClient实例
            CloseableHttpClient httpClient = HttpClients.createDefault();
            // 创建httpPost远程连接实例
            HttpPost httpPost = new HttpPost(url);
            // 配置请求参数实例
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000)// 设置连接主机服务超时时间
                    .setConnectionRequestTimeout(10000)// 设置连接请求超时时间
                    .setSocketTimeout(30000)// 设置读取数据连接超时时间
                    .build();
            // 为httpPost实例设置配置
            httpPost.setConfig(requestConfig);
            // 设置请求头
            httpPost.addHeader("content-type", "application/json;charset=utf-8");
//            httpPost.setHeader("Accept", "application/json");
//            httpPost.addHeader("content-type", "text/html;charset=UTF-8");
//            stringEntity.setContentType("application/x-www-form-urlencoded");  --- String参数
            // 封装post请求参数
            StringEntity entity = new StringEntity(param, Charset.forName("UTF-8"));
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            // httpClient对象执行post请求,并返回响应参数对象
            //   HttpResponse httpResponse = httpClient.execute(httpPost);
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            // 从响应对象中获取响应内容
            String result = EntityUtils.toString(httpResponse.getEntity());
            json = JSONObject.parseObject(result);
            if(400!=json.getInteger("status")){
                log.info("请求异常：{}",json.toJSONString());
            }
            return JSONObject.toJavaObject(json,Result.class);
        } catch (UnsupportedEncodingException e) {
            log.error("URLUtil.httpPostRequest encounters an UnsupportedEncodingException : {}",e);
        } catch (IOException e) {
            log.error("URLUtil.httpPostRequest encounters an IOException1111 : {}",e);
        }catch (Exception e){
            log.error("post 发送异常：{}",e);
        }
        Result result = new Result();
        result.setSuccess(false);
        return result;
    }

}
