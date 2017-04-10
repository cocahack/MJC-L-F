package com.programmer.awesome.mjclnf.serverConnect;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.programmer.awesome.mjclnf.AsyncResponse;
import com.programmer.awesome.mjclnf.customObject.StaticValues;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by USER on 2016-10-27.
 */

public class RequestToServer extends AsyncTask<String ,Void,String>{
    private Context mContext;
    private static String lineEnd = "\r\n";
    private static String twoHyphens = "--";
    private ProgressDialog mDlg ;
    public AsyncResponse delegate = null;
    public RequestToServer(Context context){
        mContext = context;
    }
    @Override
    protected void onPreExecute() {
        mDlg = new ProgressDialog(mContext);
        mDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDlg.setMessage("게시물을 등록하고 있습니다.....");
        mDlg.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        mDlg.dismiss();
        delegate.processFinish(s);
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... strings) {
        String resp = "";
        String type = strings[0];
        String stnum = strings[1];
        String name = strings[2];
        String phonenum = strings[3].equals("")?"$null":strings[3];
        String kakao = strings[4].equals("")?"$null":strings[4];
        String eventDate = strings[5];
        String place = strings[6];
        String classify = strings[7];
        String detail = strings[8];
        String explain = strings[9].equals("")?"$null":strings[9];
        String fileName = strings[10];
        String filePath = strings[11];
        String[] dataName = {"type","stnum","name","phone","kakao","eventDate","place","classify","detail","explain"};
        String[] data = {type,stnum,name,phonenum,kakao,eventDate,place,classify,detail,explain};
        //랜덤 boundary 생성
        int boundaryLength = (int)(Math.random()*32)+16;
        RandomString rs = new RandomString(boundaryLength);
        String boundary = rs.nextString();
        File targetFile = null;
        if(filePath!=null) {
            targetFile = new File(filePath);
        }

        byte[] buffer;
        int maxBufferSize = 5*1024 * 1024;
        HttpURLConnection conn = null;
        String urlStr = StaticValues.SERVER_BASE_URL+"/writeForAndroid.do";
        try {
            conn = (HttpURLConnection) new URL(urlStr).openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            conn.setRequestMethod("POST");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);
        String delimiter = twoHyphens + boundary + lineEnd;
        StringBuffer postDataBuilder = new StringBuffer();
        for(int i=0;i<data.length;i++){
            postDataBuilder.append(delimiter);
            postDataBuilder.append(setValue(dataName[i], data[i]));
        }
        // 파일 첨부
        if(fileName!=null){
            postDataBuilder.append(delimiter);
            postDataBuilder.append(setFile("uploadedFile", fileName));
        }
        try {
            DataOutputStream ds = new DataOutputStream(conn.getOutputStream());
            ds.write(postDataBuilder.toString().getBytes());
            if(filePath!=null){
                ds.writeBytes(lineEnd);
                FileInputStream fStream = new FileInputStream(targetFile);
                buffer = new byte[maxBufferSize];
                int length = -1;
                while((length=fStream.read(buffer)) != -1){
                    ds.write(buffer,0,length);
                }
                ds.writeBytes(lineEnd);
                ds.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                fStream.close();
            }else{
                ds.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            }
            ds.flush();
            ds.close();
            int responseCode  = conn.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK)
            {
                String line = null;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while((line=br.readLine())!=null) {
                    resp += line;
                }
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            conn.disconnect();
        }
        return resp;
    }
    public static String setValue(String key, String value) {
        return "Content-Disposition: form-data; name=\"" + key +"\""+lineEnd+lineEnd+value+lineEnd;
    }
    public static String setFile(String key, String fileName) {
        return "Content-Disposition: form-data; name=\"" + key + "\";filename=\"" + fileName +"\"" + lineEnd;
    }
}
