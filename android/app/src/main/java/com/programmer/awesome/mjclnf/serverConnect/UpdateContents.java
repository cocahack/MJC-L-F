package com.programmer.awesome.mjclnf.serverConnect;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.programmer.awesome.mjclnf.AsyncResponse;
import com.programmer.awesome.mjclnf.customObject.StaticValues;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by USER on 2016-11-09.
 */

public class UpdateContents{
    public static class LoadToGet extends AsyncTask<String,Void,String>
    {
        private String downloadToURL(String user_stnum,String type,String no){
            HttpURLConnection conn = null;
            try {
                URL url = new URL(StaticValues.SERVER_BASE_URL+"/modifyContent.do?type="+type+"&no="+no+"&stnum="+user_stnum);
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
        protected String doInBackground(String... params) {
            String user_stnum = params[0];
            String type = params[1];
            String no = params[2];
            String jsonText = downloadToURL(user_stnum,type,no);
            return jsonText;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

    }
    public static class UpdateToPost extends  AsyncTask<String,Void,String>
    {
        private static String lineEnd = "\r\n";
        private static String twoHyphens = "--";
        private String msg = null;
        private ProgressDialog mDlg ;
        public AsyncResponse delegate = null;
        private Context mContext = null;
        public UpdateToPost(Context context){
            mContext = context;
        }
        @Override
        protected void onPreExecute() {
            mDlg = new ProgressDialog(mContext);
            mDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDlg.setMessage("게시물을 수정하고 있습니다...");
            mDlg.setCancelable(false);
            mDlg.show();
        }
        @Override
        protected void onPostExecute(String s) {
            mDlg.dismiss();
            int resultRow = Integer.parseInt(s);
            if(resultRow>0)
            {
                delegate.processFinish(s);
            }else{
                Toast.makeText(mContext,"서버 오류로 등록되지 않았습니다.",Toast.LENGTH_SHORT);
            }
        }
        @Override
        protected String doInBackground(String... strings) {
            String resp = "";
            String type = strings[0];
            String no = strings[1];
            String phonenum = strings[2].equals("")?"$null":strings[2];
            String kakao = strings[3].equals("")?"$null":strings[3];
            String eventDate = strings[4];
            String place = strings[5];
            String classify = strings[6];
            String detail = strings[7];
            String explain = strings[8].equals("")?"$null":strings[8];
            String fileName = strings[9];
            String filePath = strings[10];
            String fileDelete = strings[11];
            String[] dataName = {"type","no","phone","kakao","eventDate","place","classify","detail","explain","fileDelete"};
            String[] data = {type,no,phonenum,kakao,eventDate,place,classify,detail,explain,fileDelete};

            //랜덤 boundary 생성
            int boundaryLength = (int)(Math.random()*32)+16;
            RandomString rs = new RandomString(boundaryLength);
            String boundary = rs.nextString();
            File targetFile = null;
            if(filePath!=null) {
                targetFile = new File(filePath);
            }
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 5*1024 * 1024;
            HttpURLConnection conn = null;
            String urlStr = StaticValues.SERVER_BASE_URL+"/modifyContent.do";
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
            if(fileName!=null){
                postDataBuilder.append(delimiter);
                postDataBuilder.append(setFile("uploadFile", fileName));
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
                    ds.writeBytes(lineEnd);
                    ds.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                    fStream.close();
                }else{
                    ds.writeBytes(lineEnd);
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
        public String setValue(String key, String value) {
            return "Content-Disposition: form-data; name=\"" + key +"\""+lineEnd+lineEnd+value+lineEnd;
        }
        public String setFile(String key, String fileName) {
            return "Content-Disposition: form-data; name=\"" + key + "\";filename=\"" + fileName +"\"" + lineEnd;
        }

    }
    public static class DeleteContent extends AsyncTask<String,Void,String>
    {
        private ProgressDialog mDlg ;
        private Context mContext = null;
        public DeleteContent(Context context){
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            mDlg = new ProgressDialog(mContext);
            mDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDlg.setMessage("게시물을 삭제하고 있습니다...");
            mDlg.setCancelable(false);
            mDlg.show();
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings) {
            String type = strings[0];
            String no = strings[1];
            String stnum = strings[2];
            String receiveText = "";
            HashMap<String,String> params = new HashMap<String, String>();
            params.put("type",type);
            params.put("no",no);
            params.put("stnum",stnum);
            HttpURLConnection conn = null;
            try {
                URL url = new URL(StaticValues.SERVER_BASE_URL+"/deleteForAndroid.do");
                conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            try {
                BufferedOutputStream os = new BufferedOutputStream(conn.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostString(params));
                writer.flush();
                writer.close();
                int responseCode  = conn.getResponseCode();
                if(responseCode == HttpURLConnection.HTTP_OK)
                {
                    String line = null;
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while((line=br.readLine())!=null) {
                        receiveText += line;
                    }
                    br.close();
                }else if(responseCode==HttpURLConnection.HTTP_FORBIDDEN){
                    receiveText+="SERVER_DISABLED";
                }else
                    receiveText=null;
            } catch (IOException e) {
                return null;
            }finally {
                conn.disconnect();
            }
            return receiveText;
        }
        @Override
        protected void onPostExecute(String s) {
            mDlg.dismiss();
            super.onPostExecute(s);
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

    }
}
