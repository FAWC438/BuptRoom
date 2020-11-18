package fawc.buptroom.services;

import android.content.SharedPreferences;
import com.alibaba.fastjson.JSONObject;
import fawc.buptroom.R;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GetServerData {

    GetServerData() {
    }

    public static Map<String, int[][]> doPost() {
        //创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        JSONObject jsonObject = null;
        Map<String, int[][]> roomData = new HashMap<>();
        try {
            //创建http请求
            HttpPost httpPost = new HttpPost("http://49.233.52.156:5438/");
            httpPost.setHeader("Content-type", "application/json;charset=utf-8");
            httpPost.setHeader("Connection", "Close");
            //创建请求内容
            String jsonStr = "{\"id\":\"lgh\", \"password\":\"lgh438\", \"campusCode\":\"1\"}";
            httpPost.setEntity(new StringEntity(jsonStr));
            response = httpClient.execute(httpPost);
            String response_body = EntityUtils.toString(response.getEntity(), "utf-8");

//            System.out.println(response_body);
//
//            result = StringEscapeUtils.unescapeJava(response_body);
//            byte[] result_byte = result.getBytes(StandardCharsets.UTF_8);
//            System.out.println(result);

//            File outPutFile = new File("result.txt");
//            OutputStream outputStream = new FileOutputStream(outPutFile);
//            outputStream.write(result_byte);

//            JSONObject obj = JSONObject.parseObject(response_body);
//            System.out.println(obj.toJSONString());

            jsonObject = JSONObject.parseObject(response_body);

//            for (Map.Entry<String, Object> stringObjectEntry : jsonObject.entrySet()) {
//                System.out.println("key: " + stringObjectEntry.getKey());
//                System.out.println("value: " + stringObjectEntry.getValue().toString());
//            }


            for (Map.Entry<String, Object> stringObjectEntry : jsonObject.entrySet()) {

                String temp = stringObjectEntry.getValue().toString();
                temp = temp.substring(1, temp.length() - 1);
                String[] tempArr = temp.split(",");
                for (int i = 0; i < tempArr.length; i++) tempArr[i] = tempArr[i].substring(1, tempArr[i].length() - 1);

                int[][] valueArr = new int[7][14];
                for (String s : tempArr) {
                    int weekNum = Integer.parseInt(s.split(" ")[0]);
                    int lessonNum = Integer.parseInt(s.split(" ")[1]);
                    valueArr[weekNum - 1][lessonNum - 1] = 1;
                }
                roomData.put(stringObjectEntry.getKey(), valueArr);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            if (response != null) {
                try {
                    response.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }

        return jsonObject == null ? null : roomData;
    }

//    public static void main(String[] args) throws Exception {
//
//        System.out.println("\nTesting 2 - Do Http POST request");
//        doPost("http://49.233.52.156:5438/");
//
//    }

}
