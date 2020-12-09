package fawc.buptroom.services;

import com.alibaba.fastjson.JSONObject;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.util.Map;
import java.util.Objects;

public class UpdateUrl {

    public static double getUpdateInfo() {
        String API_TOKEN = "656c59bd6468ce6ab95b9013a8657b09";
        String APP_ID = "5fd0a3edb2eb4609be495aa8";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        JSONObject jsonObject = null;

        HttpGet httpGet = new HttpGet("http://api.bq04.com/apps/latest/" + APP_ID + "?api_token=" + API_TOKEN);
        try {
            response = httpClient.execute(httpGet);
            String response_body = EntityUtils.toString(response.getEntity(), "utf-8");
            jsonObject = JSONObject.parseObject(response_body);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (jsonObject != null) {
            return Double.parseDouble((String) Objects.requireNonNull(jsonObject.get("versionShort")));
        }
        return 0;
    }

    public static String getDownloadUrl() {
        String API_TOKEN = "656c59bd6468ce6ab95b9013a8657b09";
        String APP_ID = "5fd0a3edb2eb4609be495aa8";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        JSONObject jsonObject = null;

        HttpGet httpGet = new HttpGet("http://api.bq04.com/apps/" + APP_ID + "/download_token?api_token=" + API_TOKEN);
        try {
            response = httpClient.execute(httpGet);
            String response_body = EntityUtils.toString(response.getEntity(), "utf-8");
            jsonObject = JSONObject.parseObject(response_body);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (jsonObject != null) {
            for (Map.Entry<String, Object> stringObjectEntry : jsonObject.entrySet()) {
                if (stringObjectEntry.getKey().equals("download_token")) {
                    return "http://download.bq04.com/apps/" + APP_ID + "/install?download_token=" + stringObjectEntry.getValue().toString();
                }
            }
        }
        return null;
    }
}
