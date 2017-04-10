package com.programmer.awesome.mjclnf;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.programmer.awesome.mjclnf.login.LoginConnect;
import com.programmer.awesome.mjclnf.customObject.Member;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by USER on 2016-10-28.
 */

public class StartActivity extends AppCompatActivity{
    final static int PARAMETER_READY = 1;
    final static int PARAMETER_NULL = 0;
    final static String STORAGE_NOT_PERMIT = "파일에 대한 접근이 제한되고 있습니다. 안드로이드 설정에서 바꿔주세요.";
    final static String CAMERA_NOT_PERMIT = "카메라에 대한 접근이 제한되고 있습니다. 안드로이드 설정에서 바꿔주세요.";
    final static String BOTH_NOT_PERMIT = "카메라와 파일에 대한 접근이 제한되고 있습니다. 안드로이드 설정에서 바꿔주세요.";
    private Thread t;
    private Member user;
    private Handler handler = new Handler();
    private ProgressBar progressBar;
    private boolean storagePermission;
    private boolean cameraPermission;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initiateapp);

        t = new Thread(new Runnable() {
            Handler handler = new Handler();
            @Override
            public void run() {
            progressBar.setProgress(1);
                SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);
                String key = pref.getString("userkey","");
                String text = "";
                try {
                    text += new LoginConnect.LoginCheckToKey().execute(key).get().toString();
                }catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                //서버 응답이 없을 때
                if(text.equals("timeout")){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(StartActivity.this,"서버가 응답하지 않습니다.",Toast.LENGTH_LONG).show();
                        }
                    });
                    finish();
                }
                else if(text.equals("Forbidden")){
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(StartActivity.this,"서버가 응답하지 않습니다.",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    },3000);
                }
                else if(text.equals("KEY_HAS_NOT_VLAUE")){
                    SharedPreferences.Editor ed = pref.edit();
                    ed.remove("userkey");
                    ed.commit();
                    goToMain();
                }
                else if(text.equals("MEMBER_NOT_EXIST")){
                    /*DB에 해당 키를 갖는 유저가 없음*/
                    SharedPreferences.Editor ed = pref.edit();
                    ed.remove("userkey");
                    ed.commit();
                    goToMain();
                }
                else{
                    try {
                        JSONObject jsonObject = new JSONObject(text);
                        String st_num = jsonObject.getString("st_num");
                        String st_name = jsonObject.getString("st_name");
                        user = new Member(st_num, st_name);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    goToMain();
                }
            }
        });

        LoginConnect loginConnect = new LoginConnect(StartActivity.this);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        String[] perms = {"android.permission.READ_EXTERNAL_STORAGE","android.permission.CAMERA"};
        int permsRequestCode = 200;
        // SDK LEVEL 체크
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            /*int permissionCheck = ContextCompat.checkSelfPermission(StartActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE);
            //퍼미션 체크 여부 확인
            if(permissionCheck==-1){*/
            if (permissionCheck()) {
                boolean showRationale = shouldShowRequestPermissionRationale( Manifest.permission.READ_EXTERNAL_STORAGE );
                boolean showRationaleCamera = shouldShowRequestPermissionRationale(Manifest.permission.CAMERA);
                // 퍼미션 창을 보여준 적이 있는지 확인
                if(showRationale==true&&showRationaleCamera==true){
                    if(cameraPermission&&storagePermission){
                        Toast.makeText(StartActivity.this, BOTH_NOT_PERMIT, Toast.LENGTH_LONG).show();
                        t.start();
                    }else if(!cameraPermission&&storagePermission){
                        Toast.makeText(StartActivity.this, STORAGE_NOT_PERMIT, Toast.LENGTH_LONG).show();
                        t.start();
                    }
                    else if(cameraPermission&&!storagePermission){
                        Toast.makeText(StartActivity.this, CAMERA_NOT_PERMIT, Toast.LENGTH_LONG).show();
                        t.start();
                    }
                }
                else {
                    requestPermissions(perms, permsRequestCode);
                }
            }else{
                t.start();
        }
        }else{
            t.start();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 200) {
            boolean readAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            if (!readAccepted){
                Toast.makeText(StartActivity.this, "파일에 대한 접근이 제한되고 있습니다. 안드로이드 설정에서 바꿔주세요.", Toast.LENGTH_LONG).show();
            }
        }
        t.start();
    }
    private void goToMain(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        if (user != null) {
            intent.putExtra("request", PARAMETER_READY);
            intent.putExtra("st_num", user.getStnum());
            intent.putExtra("name", user.getName());
        } else {
            intent.putExtra("request", PARAMETER_NULL);
        }

        startActivity(intent);
        StartActivity.this.finish();
    }
    private boolean permissionCheck()
    {
        storagePermission = ContextCompat.checkSelfPermission(StartActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
        cameraPermission = ContextCompat.checkSelfPermission(StartActivity.this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED;
        return storagePermission||cameraPermission;
    }
}
