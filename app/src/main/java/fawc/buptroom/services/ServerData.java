package fawc.buptroom.services;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class ServerData {

    private int campusCode = 2;// 0为本部，1为沙河，其它为全部

    /***
     *
     * @param campus 0为本部，1为沙河，其它为全部
     */
    public ServerData(int campus) {
        setCampusCode(campus);
    }

    public ServerData() {

    }

    public static String convertToString(int[][] array, int row, int col) {
        StringBuilder str = new StringBuilder();
        String tempStr;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                tempStr = String.valueOf(array[i][j]);
                str.append(tempStr).append(",");
            }
        }
        return str.toString();
    }

    public static int[][] convertToArray(String str, int row, int col) {
        int[][] arrayConvert = new int[row][col];
        int count = 0;
        String[] strArray = str.split(",");
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (!Objects.equals(strArray[count], "0") || !Objects.equals(strArray[count], "1"))
                    strArray[count] = "0";
                arrayConvert[i][j] = Integer.parseInt(strArray[count]);
                ++count;
            }
        }
        return arrayConvert;
    }

    public Map<String, int[][]> doPostToServer() {
        //创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        JSONObject jsonObject;
        Map<String, int[][]> roomData = new HashMap<>();
        try {
            //创建http请求
            HttpPost httpPost = new HttpPost("http://49.233.52.156:5438/");
            httpPost.setHeader("Content-type", "application/json;charset=utf-8");
            httpPost.setHeader("Connection", "Close");
            //创建请求内容
            String jsonStr = "{\"id\":\"lgh\", \"password\":\"lgh438\", \"campusCode\":\"" + getCampusCode() + "\"}";
            httpPost.setEntity(new StringEntity(jsonStr));
            response = httpClient.execute(httpPost);
            String response_body = EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(response_body);

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
                if (temp.isEmpty()) {
                    roomData.put(stringObjectEntry.getKey(), new int[7][14]);
                    continue;
                }
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
            jsonObject = null;
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
