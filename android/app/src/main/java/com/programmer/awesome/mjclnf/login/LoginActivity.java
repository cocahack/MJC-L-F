package com.programmer.awesome.mjclnf.login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.programmer.awesome.mjclnf.AsyncResponse;
import com.programmer.awesome.mjclnf.R;
import com.programmer.awesome.mjclnf.customObject.StaticValues;

/**
 * Created by USER on 2016-10-30.
 */

public class LoginActivity extends AppCompatActivity implements AsyncResponse {
    private Dialog mDialog = null;
    private String idString;
    private String pwdString;
    private EditText mid;
    private EditText mpwd;
    @Override
    protected void onCreate(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mDialog = new Dialog(LoginActivity.this);
        mDialog.setTitle("로그인");
        mDialog.setContentView(R.layout.dialog_layout);
        Button loginActionBtn = (Button)findViewById(R.id.loginActionBtn);
        mid = (EditText)findViewById(R.id.id_value);
        mpwd= (EditText)findViewById(R.id.pwd_value);
        loginActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idString = mid.getText().toString();
                pwdString = mpwd.getText().toString();
                AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                alert.setPositiveButton("확인",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                if(idString==null||idString.equals("")){
                    alert.setMessage("아이디를 입력하세요.");
                    alert.show();
                }
                else if(pwdString==null||pwdString.equals("")){
                    alert.setMessage("비밀번호를 입력하세요.");
                    alert.show();
                }
                else{
                    LoginConnect loginConnect = new LoginConnect(LoginActivity.this);
                    LoginConnect.LoginAction loginAction = new LoginConnect.LoginAction();
                    loginAction.delegete = LoginActivity.this;
                    loginAction.execute(idString,pwdString);
                }
            }
        });
    }

    @Override
    public void processFinish(Object output) {

    }

    @Override
    public void processFinish(String output) {
        Intent intent = new Intent();
        if(output.contains("Success")){
            output = output.replace("Success","");
            SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);
            SharedPreferences.Editor ed = pref.edit();
            ed.putString("userkey",output);
            ed.commit();
            intent.putExtra("userkey",output);
            setResult(1,intent);
            finishActivity(StaticValues.REQUEST_LOGINACTIVITY);
            finish();
        }else if(output.equals("ID_PWD_NOT_MATCHED")){
            mpwd.setText("");
        }
        else{
            mDialog.show();
        }
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.dialog_ok_btn:
                LoginConnect loginConnect = new LoginConnect(LoginActivity.this);
                LoginConnect.LoginAction loginAction = new LoginConnect.LoginAction();
                loginAction.delegete = LoginActivity.this;
                loginAction.execute(idString,pwdString,"LOGIN_AFTER_DELETE");
                mDialog.dismiss();
                break;
            case R.id.dialog_cancel_btn:
                mDialog.dismiss();
                mid.setText("");
                mpwd.setText("");
                break;
        }
    }
}
