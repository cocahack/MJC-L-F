package com.programmer.awesome.mjclnf.serverConnect;

import android.os.AsyncTask;

import com.programmer.awesome.mjclnf.AsyncResponse;
import com.programmer.awesome.mjclnf.customObject.ListItem;
import com.programmer.awesome.mjclnf.customObject.ListNoticeFaq;
import com.programmer.awesome.mjclnf.customObject.StaticValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MJC on 2016-10-25.
 */

public class DownLoadList extends AsyncTask<String, Void, List<?>> {
    private String type = "";
    private String pageNo = "";
    private String keyword = null;
    public AsyncResponse delegate = null;

    private String downloadToURL(String type, String pageNo) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(StaticValues.SERVER_BASE_URL+"/getList.do?type=" + type + "&pageNo=" + pageNo);
            conn = (HttpURLConnection) url.openConnection();
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(bis, "UTF-8"));

            String line = null;
            String jsonText = "";
            int responseCode = conn.getResponseCode();
            if(responseCode==HttpURLConnection.HTTP_OK)
            {
                while ((line = br.readLine()) != null) {
                    jsonText += line;
                }
            }
            else if(responseCode==HttpURLConnection.HTTP_FORBIDDEN)
                jsonText+="SERVER_DISABLED";
            else
                jsonText+="UNEXPECTED_ERROR";
            br.close();
            bis.close();
            return jsonText;

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            conn.disconnect();
        }
    }
    private String downloadToKeyword(String type, String pageNo, String keyword) {
        HttpURLConnection conn = null;
        String urlStr= StaticValues.SERVER_BASE_URL+"/searchForAndroid.do";
        String jsonText = null;
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
            HashMap<String,String> params = new HashMap<>();
            params.put("type",type);
            params.put("pageNo",pageNo);
            params.put("keyword",keyword);
            String urlQuery = getPostString(params);
            try {
                BufferedOutputStream os = new BufferedOutputStream(conn.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                writer.write(urlQuery);
                writer.flush();
                writer.close();

                int responseCode = conn.getResponseCode();
                if(responseCode==HttpURLConnection.HTTP_OK)
                {
                    String line = null;
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    jsonText = "";
                    while((line=br.readLine())!=null) {
                        jsonText += line;
                    }
                    br.close();
                }else if(responseCode==HttpURLConnection.HTTP_FORBIDDEN)
                    jsonText="SERVER_DISABLED";
                else
                    jsonText="UNEXPECTED_ERROR";
                writer.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }finally {
                conn.disconnect();
            }
            return jsonText;
    }
    private String getPostString(HashMap<String, String> map) {
        StringBuilder result = new StringBuilder();
        boolean first = true; // 첫 번째 매개변수 여부

        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (first)
                first = false;
            else // 첫 번째 매개변수가 아닌 경우엔 앞에 &를 붙임
                result.append("&");

            try { // UTF-8로 주소에 키와 값을 붙임
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            } catch (UnsupportedEncodingException ue) {
                ue.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result.toString();
    }

    @Override
    protected void onPostExecute(List<?> objects) {
        if(delegate!=null)
            delegate.processFinish(objects);
        super.onPostExecute(objects);
    }

    @Override
    protected List<?> doInBackground(String... params) {
        type = params[0];
        pageNo = params[1];
        try{
            keyword = params[2];
        }catch (ArrayIndexOutOfBoundsException ignore){}

        List<ListItem> listItem = null;
        List<ListNoticeFaq> listnoticefaq = null;
        String jsonText = null;
        if(keyword==null)
            jsonText = downloadToURL(type, pageNo);
        else
            jsonText = downloadToKeyword(type,pageNo,keyword);
        //유실물 리스트뷰 목록
        if (type.equals("lost") || type.equals("found")) {
            try {
                JSONObject jsonObject = new JSONObject(jsonText);
                JSONArray header = jsonObject.getJSONArray("header");
                JSONObject state = header.getJSONObject(0);
                String headerCode = state.getString("state");
                if (headerCode.equals("0")) {
                    JSONObject jsonObjects = new JSONObject(jsonText);
                    JSONArray lists = jsonObjects.getJSONArray("lists");
                    listItem = new ArrayList<ListItem>();
                    for (int i = 0; i < lists.length(); i++) {
                        ListItem temp = new ListItem();
                        JSONObject listObj = lists.getJSONObject(i);
                        temp.setNo(listObj.getString("no"));
                        temp.setTitle(listObj.getString("title"));
                        temp.setCategory(listObj.getString("class"));
                        temp.setEventDate(listObj.getString("eventDate"));
                        temp.setRegitDate(listObj.getString("regitDate"));
                        listItem.add(temp);

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (type.equals("notice") || type.equals("faq")) {
            try {
                JSONObject jsonObject = new JSONObject(jsonText);
                JSONArray header = jsonObject.getJSONArray("header");
                JSONObject state = header.getJSONObject(0);
                String headerCode = state.getString("state");
                if (headerCode.equals("0")) {
                    JSONObject jsonObjects = new JSONObject(jsonText);
                    JSONArray lists = jsonObjects.getJSONArray("lists");
                    listnoticefaq = new ArrayList<ListNoticeFaq>();
                    for (int i = 0; i < lists.length(); i++) {
                        ListNoticeFaq temp = new ListNoticeFaq();
                        JSONObject listObj = lists.getJSONObject(i);
                        temp.setNo(listObj.getString("no"));
                        temp.setTitle(listObj.getString("title"));
                        temp.sethit(listObj.getString("hit"));
                        temp.setRegitDate(listObj.getString("regitDate"));
                        listnoticefaq.add(temp);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (listItem != null) {
            return listItem;
        } else
            return listnoticefaq;
    }
}
