package com.programmer.awesome.mjclnf.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import com.programmer.awesome.mjclnf.AsyncResponse;
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
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by USER on 2016-10-30.
 */

public class LoginConnect {
    static private Context mContext;
    public LoginConnect(Context context){
        mContext = context;
    }
    static private ProgressDialog mDlg ;
    static public class KeyGen extends AsyncTask<String,Void,Pair>{
        @Override
        protected void onPreExecute() {
            mDlg = new ProgressDialog(mContext);
            mDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDlg.setMessage("서버에 연결하고 있습니다...");
            mDlg.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Pair pair) {
            mDlg.dismiss();
            super.onPostExecute(pair);
        }

        @Override
        protected Pair doInBackground(String... strings) {
            HttpURLConnection conn = null;
            Pair<String,String> publicKey = null;
            try {
                URL url = new URL("http://cocahack.cafe24.com/keyGen.do");
                conn = (HttpURLConnection) url.openConnection();
                BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(bis,"UTF-8"));
                String line = null;
                String texts = "";
                while((line=br.readLine())!=null){
                    texts+=line;
                }

                try {
                    JSONObject jsonObject = new JSONObject(texts);
                    JSONArray array = jsonObject.getJSONArray("PublicKeyPair");
                    String modulus = array.get(0).toString();
                    String exponent = array.get(1).toString();
                    publicKey = new Pair<String,String>(modulus,exponent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            conn.disconnect();
            return publicKey;
        }
    }
    static public class LoginAction extends AsyncTask<String,Void,String>{
        public AsyncResponse delegete = null;

        @Override
        protected void onPreExecute() {
            mDlg = new ProgressDialog(mContext);
            mDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDlg.setCancelable(false);
            mDlg.setMessage("로그인 중입니다.");
            mDlg.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            mDlg.dismiss();
            if(s==null)
                Toast.makeText(mContext,"로그인에 실패했습니다.",Toast.LENGTH_SHORT).show();
            else if(s.equals("ID_PWD_NOT_MATCHED"))
            {
                Toast.makeText(mContext, "로그인에 실패했습니다. ID와 패스워드를 확인해주세요", Toast.LENGTH_SHORT).show();
                delegete.processFinish(s);
            }
            else if(s.equals("KEY_EXIST"))
                delegete.processFinish(s);
            else if(s.equals("INTERNAL_SERVER_ERR"))
                Toast.makeText(mContext, "서버 작업 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            else
                delegete.processFinish(s);

        }

        @Override
        protected String doInBackground(String... strings) {
            String receiveText = "";
            String id = strings[0];
            String pwd = strings[1];
            String mode = null;
            try{
                mode = strings[2];
            }catch (ArrayIndexOutOfBoundsException e){
                mode = null;
            }
            HashMap<String,String> params = new HashMap<String, String>();
            params.put("id",id);
            params.put("pwd",pwd);
            if(mode!=null)
                params.put("mode",mode);
            HttpURLConnection conn = null;
             try {
                 URL url =  new URL(StaticValues.SERVER_BASE_URL+"/androidLogin.do");
                 conn = (HttpURLConnection) url.openConnection();
                 conn.setRequestMethod("POST");
            } catch (IOException e) {
                return null;
            }
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
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
                }
            } catch (IOException e) {
                return null;
            }


            return receiveText;
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
    static public class LoginCheckToKey extends AsyncTask<String,Void,String>{

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            String receiveText = "";
            String key = strings[0];
            HttpURLConnection conn = null;
            try {
                URL url = new URL(StaticValues.SERVER_BASE_URL+"/checkLogin.do");
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
            }catch (SocketTimeoutException e){
                return "timeout";
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            try {
                BufferedOutputStream os = new BufferedOutputStream(conn.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write("key="+key);
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
                }else if(responseCode == HttpURLConnection.HTTP_FORBIDDEN){
                    receiveText = "Forbidden";
                }
            }catch (SocketTimeoutException e){
                receiveText = "timeout";
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return receiveText;
        }
    }
    static public class LogoutAction extends  AsyncTask<String,Void,Boolean>{
        private Context thisContext ;
        public LogoutAction(Context context){
            thisContext = context;
        }
        @Override
        protected Boolean doInBackground(String... strings) {
            String key = strings[0];
            String receiveText = "";
            HttpURLConnection conn = null;
            try {
                URL url = new URL(StaticValues.SERVER_BASE_URL+"/androidLogout.do");
                conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
            } catch (IOException e) {
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
                writer.write("key="+key);
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
                }
            }catch (SocketTimeoutException e){
                receiveText += "timeout";
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            if(receiveText.equals("timeout") || receiveText.equals("REQ_FAIL") || receiveText.equals("")){
                return false;
            }
            if(receiveText.equals("REQ_SUCCESS")){
                return true;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            mDlg.dismiss();
            if(!aBoolean){
                Toast.makeText(thisContext,"로그아웃에 실패했습니다.",Toast.LENGTH_SHORT);
            }
            super.onPostExecute(aBoolean);
        }

        @Override
        protected void onPreExecute() {
            mDlg = new ProgressDialog(thisContext);
            mDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDlg.setCancelable(false);
            mDlg.setMessage("로그아웃 요청을 처리하고 있습니다...");
            mDlg.show();
        }
    }

}
