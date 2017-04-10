package com.programmer.awesome.mjclnf;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.programmer.awesome.mjclnf.customObject.ItemInformation;
import com.programmer.awesome.mjclnf.Record.ModifyActivity;
import com.programmer.awesome.mjclnf.customObject.StaticValues;
import com.programmer.awesome.mjclnf.customObject.Member;
import com.programmer.awesome.mjclnf.serverConnect.DownLoadItemInformation;
import com.programmer.awesome.mjclnf.serverConnect.DownloadImageTask;
import com.programmer.awesome.mjclnf.serverConnect.UpdateContents;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ItemView extends AppCompatActivity {
    private final int MODIFY_ACTIVITY = 11;
    private Member user = null;
    private String itext = null;
    private String texttitle = null;
    private String texttitle2 = null;
    private String type = null;
    private String itemNo = null;
    ItemInformation values = null;
    String image = null;
   Boolean image_check=false;

    //    위젯
    private ImageView iv_photo = null;
    private TextView tx1 = null;
    private TextView tv_place = null;
    private TextView tv_phonenumber = null;
    private TextView tv_kakaoID = null;
    private TextView tv_explain = null;
    private TextView tv_eventdate = null;
    // 게시물 변경 여부
    private boolean isModified = false;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.itemview);

        System.gc();
        Intent intent = getIntent();
        user = MainActivity.user;
        itext = intent.getStringExtra("title");
        texttitle = intent.getStringExtra("location");
        texttitle2 = intent.getStringExtra("location_date");
        type = intent.getStringExtra("type");
        itemNo = intent.getStringExtra("itemNo");
        tx1 = (TextView) findViewById(R.id.item_title);
        TextView txttitles = (TextView) findViewById(R.id.item_LorF);
        TextView txttitles2 = (TextView) findViewById(R.id.item_LorF_location);

        txttitles2.setText(texttitle2);
        txttitles.setText(texttitle);
        tx1.setText(itext);
        this.setTitle(itext);
        tv_place = (TextView) findViewById(R.id.tv_place);
        tv_phonenumber = (TextView) findViewById(R.id.tv_phonenumber);
        tv_kakaoID = (TextView) findViewById(R.id.tv_kakaoID);
        tv_explain = (TextView) findViewById(R.id.tv_explain);
        tv_eventdate = (TextView) findViewById(R.id.tv_eventdate);
        iv_photo = (ImageView)findViewById(R.id.itemimage);

        tv_explain.setMovementMethod(new ScrollingMovementMethod());
        try {
            List<ItemInformation> itemInformations = (List<ItemInformation>) new DownLoadItemInformation().execute(type,itemNo).get();

            for (ItemInformation i : itemInformations) {
                values = i;
                tv_place.setText(i.getPlace());
                tv_phonenumber.setText(i.getPhone());
                tv_kakaoID.setText(i.getKakao());
                tv_explain.setText(i.getExplain());
                tv_eventdate.setText(i.getEventDate());
                if (!i.getImgPath().equals("")) {
                    image = i.getImgPath();
                    new DownloadImageTask(new ImageView[]{iv_photo},true).execute(new String[]{image});
                }

            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        final Button modify_btn = (Button)findViewById(R.id.modify_btn);
        Button delete_btn = (Button)findViewById(R.id.delete_btn);

        modify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user!=null)
                {
                    UpdateContents.LoadToGet loadToGet= new UpdateContents.LoadToGet();
                    String serverMsg = null;
                    try {
                        serverMsg = loadToGet.execute(user.getStnum(),type,itemNo).get().toString();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    if(serverMsg.equals("ACCESS_DENY"))
                    {
                        showAlertDlg();
                    }
                    else if(serverMsg.equals("TARGET_NOT_EXIST"))
                    {
                        Toast.makeText(ItemView.this,"일시적인 오류가 발생했습니다. 현재창을 종료했다가 다시 시도해주세요.",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Intent modifyActivity = new Intent(ItemView.this, ModifyActivity.class);
                        //ArrayList<ItemInformation> list = jsonParser(serverMsg);
                        modifyActivity.putExtra("list",values);
                        // 라벨 제목
                        modifyActivity.putExtra("location", texttitle);
                        modifyActivity.putExtra("location_date", texttitle2);
                        // 타입
                        modifyActivity.putExtra("type", type);
                        // 번호
                        modifyActivity.putExtra("itemNo", itemNo);
                        startActivityForResult(modifyActivity,MODIFY_ACTIVITY);
                    }
                }
                else
                {
                    Toast.makeText(ItemView.this,"로그인이 필요한 서비스입니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        iv_photo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(iv_photo.getDrawable()!=null){
                    Intent intent = new Intent(getApplicationContext(), ImagePinchView.class);
                    intent.putExtra("image_url",image);
                    startActivity(intent);}

            }
        });
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ItemView.this);

                // AlertDialog 셋팅
                alertDialogBuilder
                        .setMessage("정말 삭제하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("취소",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                })
                        .setNegativeButton("확인",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {

                                        deleteItem();
                                    }
                                });

                // 다이얼로그 생성
                AlertDialog alertDialog = alertDialogBuilder.create();

                // 다이얼로그 보여주기
                alertDialog.show();

            }
        });
    }




    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==StaticValues.REQUEST_OK)
        {
            try {
                List<ItemInformation> itemInformations = (List<ItemInformation>) new DownLoadItemInformation().execute(type,itemNo).get();

                for (ItemInformation i : itemInformations) {
                    values = i;
                    tx1.setText("["+i.getCategory()+"]"+i.getTitle());
                    tv_place.setText(i.getPlace());
                    tv_phonenumber.setText(i.getPhone());
                    tv_kakaoID.setText(i.getKakao());
                    tv_explain.setText(i.getExplain());
                    tv_eventdate.setText(i.getEventDate());
                    if (!i.getImgPath().equals("")) {
                        image_check=true;
                        image = i.getImgPath();
                        new DownloadImageTask(new ImageView[]{iv_photo}).execute(new String[]{image});
                    }else{
                        iv_photo.setImageDrawable(null);
                    }
                }
                isModified = true;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(isModified){
            setResult(StaticValues.REQUEST_MODIFIED);
        }
        super.onBackPressed();
    }

    private ArrayList<ItemInformation> jsonParser(String jsonText)
    {
        ArrayList<ItemInformation> listItem;
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
                return listItem;
            }
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void showAlertDlg(){
        AlertDialog alertDialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(ItemView.this);
        builder.setTitle("권한 없음");
        builder.setMessage("해당 서비스를 이용할 권한이 없습니다.")
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        alertDialog = builder.create();
        alertDialog.show();
    }
    private void deleteItem(){
        if(user!=null)
        {
            UpdateContents.DeleteContent deleteContent= new UpdateContents.DeleteContent(ItemView.this);
            try {
                String msg = deleteContent.execute(type,itemNo,user.getStnum()).get().toString();
                switch (msg)
                {
                    case "REQUEST_ERROR":
                        Toast.makeText(ItemView.this,"잘못된 요청입니다. 현재창을 종료하고 다시 실행해주세요.",Toast.LENGTH_SHORT).show();
                        break;
                    case "ACCESS_DENY":
                        showAlertDlg();
                        break;
                    case "INTERNAL_ERROR":
                        Toast.makeText(ItemView.this,"서버 내부에 문제가 발생했습니다.",Toast.LENGTH_SHORT).show();
                        break;
                    case "SERVER_DISABLED":
                        Toast.makeText(ItemView.this,"서버가 응답할 수 없는 상태입니다. 잠시 후 다시 실행해주세요.",Toast.LENGTH_SHORT).show();
                        break;
                    case "CONTENT_DELETED":
                        Intent intent = new Intent();
                        setResult(StaticValues.REQUEST_DELETIED,intent);
                        finish();
                        break;
                    default:
                        Toast.makeText(ItemView.this,"일시적인 오류가 발생했습니다. 현재창을 종료했다가 다시 시도해주세요.",Toast.LENGTH_SHORT).show();
                        break;
                }
            }catch (NullPointerException e){
                e.printStackTrace();
                Toast.makeText(ItemView.this,"일시적인 오류가 발생했습니다. 현재창을 종료했다가 다시 시도해주세요.",Toast.LENGTH_SHORT).show();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
