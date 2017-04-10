package com.programmer.awesome.mjclnf.serverConnect;

import android.os.AsyncTask;

import com.programmer.awesome.mjclnf.customObject.ItemInformation;
import com.programmer.awesome.mjclnf.customObject.StaticValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MJC on 2016-10-25.
 */

public class DownLoadItemInformation extends AsyncTask<String,Void,List<?>>{
    String type = "";
    String no ="";
    private String downloadToURL(String type,String no){
        HttpURLConnection conn = null;
        try {
            URL url = new URL(StaticValues.SERVER_BASE_URL+"/getReadItem.do?type="+type+"&no="+no);
            conn = (HttpURLConnection) url.openConnection();
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(bis,"UTF-8"));

            String line = null;
            String jsonText= "";
            while((line=br.readLine())!=null){
                jsonText+= line;
            }
            br.close();
            bis.close();
            return  jsonText;
        }catch (Exception e){
            throw  new RuntimeException(e);
        }finally {
            conn.disconnect();
        }
    }


    @Override
    protected List<?> doInBackground(String... params) {
        type = params[0];
        no = params[1];
        List<ItemInformation> listItem = null;
        String jsonText = downloadToURL(type,no);
        //아이템상세정보
        if(type.equals("lost")||type.equals("found"))
        {
            try {
                JSONObject jsonObject = new JSONObject(jsonText);
                JSONArray header =  jsonObject.getJSONArray("header");
                JSONObject state = header.getJSONObject(0);
                String headerCode = state.getString("state");
                if(headerCode.equals("0")){
                    JSONObject jsonObjects = new JSONObject(jsonText);
                    JSONArray lists =  jsonObjects.getJSONArray("readItems");
                    listItem = new ArrayList<ItemInformation>();
                    for(int i = 0 ; i<lists.length();i++){
                        ItemInformation temp = new ItemInformation();
                        JSONObject listObj =lists.getJSONObject(i);
                        temp.setNo(listObj.getString("no"));
                        temp.setTitle(listObj.getString("title"));
                        temp.setEventDate(listObj.getString("eventDate"));
                        temp.setRegitDate(listObj.getString("regitDate"));
                        temp.setCategory(listObj.getString("category"));
                        temp.setKakao(listObj.getString("kakao"));
                        temp.setPhone(listObj.getString("phone"));
                        temp.setImgPath(listObj.getString("imgPath"));
                        temp.setName(listObj.getString("name"));
                        temp.setExplain(listObj.getString("explain"));
                        temp.setPlace(listObj.getString("place"));
                        temp.setStnum(listObj.getString("stnum"));
                        listItem.add(temp);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return listItem;
    }

}
