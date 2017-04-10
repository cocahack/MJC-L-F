package com.programmer.awesome.mjclnf.Record;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.programmer.awesome.mjclnf.AsyncResponse;
import com.programmer.awesome.mjclnf.customObject.ItemInformation;
import com.programmer.awesome.mjclnf.MainActivity;
import com.programmer.awesome.mjclnf.R;
import com.programmer.awesome.mjclnf.customObject.StaticValues;
import com.programmer.awesome.mjclnf.customObject.Member;
import com.programmer.awesome.mjclnf.serverConnect.DownloadImageTask;
import com.programmer.awesome.mjclnf.serverConnect.UpdateContents;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by USER on 2016-11-09.
 */

public class ModifyActivity extends AppCompatActivity implements AsyncResponse{
    static final int GET_FROM_GALLERY = 1;
    final int DIALOG_DATE = 1;
    static final long MAX_FILE_SIZE = 5*1024*1024;

    private Member user =null;
    private ItemInformation itemInfo;

    /*위젯 변수*/
    private TextView eventDate;
    private TextView category;
    private EditText objname;
    private EditText place ;
    private EditText phone;
    private EditText kakao;
    private EditText information;
    private Bitmap before_Img;
    private Bitmap after_Img;
    private ImageView thumbnail;
    private String filePath = null;
    private String fileName = null;
    private Dialog mDialog = null;

    /*등록에 필요한 데이터*/
    private String date_Str = null;
    private String category_Str = null;
    private String place_Str = null;
    private String objname_Str = null;
    private String phone_Str = null;
    private String kakao_Str =null;
    private String info_Str = null;
    private String type_TextViewStr = null;
    private String itemNo_TextViewStr = null;
    private String fileDelete = "false";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_layout);

        mDialog = new Dialog(ModifyActivity.this);
        mDialog.setTitle("사진 업로드");
        mDialog.setContentView(R.layout.upload_dialog);
        ViewGroup.LayoutParams params = mDialog.getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        mDialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        Intent intent = getIntent();
        user = MainActivity.user;
        String loc_TextViewStr = intent.getStringExtra("location");
        String date_TextViewStr = intent.getStringExtra("location_date");
        type_TextViewStr = intent.getStringExtra("type");
        itemNo_TextViewStr = intent.getStringExtra("itemNo");
        itemInfo = (ItemInformation)intent.getSerializableExtra("list");
        Button dateSelectBtn = (Button)findViewById(R.id.dateselect);
        TextView textView_place = (TextView)findViewById(R.id.textview_place);
        dateSelectBtn.setHint(date_TextViewStr);
        textView_place.setText(loc_TextViewStr);

        eventDate = (TextView)findViewById(R.id.eventdate);
        category = (TextView)findViewById(R.id.category);
        objname = (EditText)findViewById(R.id.objname);
        place =(EditText)findViewById(R.id.place);
        phone = (EditText)findViewById(R.id.phone);
        kakao =(EditText)findViewById(R.id.kakao);
        information = (EditText)findViewById(R.id.modify_information);
        thumbnail = (ImageView) findViewById(R.id.uploadImg);
        String beforeEventDate = itemInfo.getEventDate();
        String[] splitDate = beforeEventDate.split("-");

        eventDate.setText(splitDate[0]+"년 "+splitDate[1]+"월 "+splitDate[2]+"일");
        date_Str = beforeEventDate;
        category.setText(itemInfo.getCategory());
        objname.setText(itemInfo.getTitle());
        place.setText(itemInfo.getPlace());
        phone.setText(itemInfo.getPhone());
        kakao.setText(itemInfo.getKakao());
        information.setText(itemInfo.getExplain());
        String before_ImgPath = itemInfo.getImgPath();
        if(before_ImgPath!=null)
        {
            new DownloadImageTask(new ImageView[]{thumbnail},true).execute(new String[]{before_ImgPath});
        }


        Button categoryBtn = (Button)findViewById(R.id.listbtn);
        Button uploadBtn = (Button)findViewById(R.id.fin);
        Button submitBtn = (Button)findViewById(R.id.btn_submit);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.show();
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone_Str = phone.getText().toString();
                kakao_Str = kakao.getText().toString();
                place_Str = place.getText().toString();
                category_Str = category.getText().toString();
                objname_Str = objname.getText().toString();
                info_Str = information.getText().toString();
                if(phone_Str.length()<10)
                    Toast.makeText(ModifyActivity.this,"전화번호를 다시 입력하세요.",Toast.LENGTH_SHORT).show();
                else{
                    if(existential()&&compulsory()) {
                        UpdateContents.UpdateToPost updateToPost = new UpdateContents.UpdateToPost(ModifyActivity.this);
                        updateToPost.delegate = ModifyActivity.this;
                        updateToPost.execute(
                                type_TextViewStr
                                , itemNo_TextViewStr
                                , phone_Str
                                , kakao_Str
                                , date_Str
                                , place_Str
                                , category_Str
                                , objname_Str
                                , info_Str
                                , fileName
                                , filePath
                                , fileDelete
                        );
                    }else{
                        Toast.makeText(ModifyActivity.this,"일부 항목을 입력하지 않았습니다.",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        dateSelectBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_DATE); // 날짜 설정 다이얼로그 띄우기
            }
        });

        categoryBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogSelectOption();
            }
        });
    }
    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat CurYearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat CurMonthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat CurDayFormat = new SimpleDateFormat("dd");
        String strCurYear = CurYearFormat.format(date);
        String strCurMonth = CurMonthFormat.format(date);
        String strCurDay = CurDayFormat.format(date);
        int nowyear = Integer.parseInt(strCurYear);
        int nowmonth = Integer.parseInt(strCurMonth);
        int nowday = Integer.parseInt(strCurDay);

        switch (id) {
            case DIALOG_DATE:
                DatePickerDialog dpd = new DatePickerDialog
                        (ModifyActivity.this, // 현재화면의 제어권자
                                new DatePickerDialog.OnDateSetListener() {
                                    public void onDateSet(DatePicker view,
                                                          int year, int monthOfYear, int dayOfMonth) {
                                        TextView seldate = (TextView) findViewById(R.id.lostdate);
                                        eventDate.setText(year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth + "일");
                                        if(dayOfMonth>=1&&dayOfMonth<=10)
                                            date_Str=year+"-"+(monthOfYear+1)+"-0"+dayOfMonth;
                                        else
                                            date_Str=year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                                    }
                                }
                                , // 사용자가 날짜설정 후 다이얼로그 빠져나올때
                                //    호출할 리스너 등록
                                nowyear, nowmonth - 1, nowday); // 기본값 연월일
                return dpd;
        }
        return super.onCreateDialog(id);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==GET_FROM_GALLERY&&resultCode==RESULT_OK){
            Uri selectedImageUri = data.getData();
            filePath = selectedImageUri.getPath();
            String uriString = selectedImageUri.toString();
            File myFile = new File(uriString);
            long size = 0;
            Bitmap uploadImg = null;
            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = getContentResolver().query(selectedImageUri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        filePath = getPath(selectedImageUri);
                        size = new File(filePath).length();
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                fileName = myFile.getName();
            }
            if(size<MAX_FILE_SIZE){
                try {
                    after_Img = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImageUri);
                    thumbnail.setImageBitmap(after_Img);
                    fileDelete = "false";
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                size = 0;
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("파일 업로드 에러")        // 제목 설정
                        .setMessage("파일 크기는 5MB 이하여야 합니다.")        // 메세지 설정
                        .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                        .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                            // 확인 버튼 클릭시 설정
                            public void onClick(DialogInterface dialog, int whichButton){
                                filePath = null;
                                fileName =null;
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }
    private String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String returnVal =cursor.getString(column_index);
        cursor.close();
        return returnVal;
    }
    private void DialogSelectOption() {
        final String items[] = {"분류를 선택하세요.","핸드폰","지갑","카드","의류","신발","가방","학생증","USB","기타"};
        AlertDialog.Builder ab = new AlertDialog.Builder(ModifyActivity.this);
        ab.setTitle("분류선택");
        ab.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (items[whichButton] == "분류를 선택하세요."){

                        } else
                            category.setText(items[whichButton]);
                        // 각 리스트를 선택했을때
                    }
                }).setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // OK 버튼 클릭시 , 여기서 선택한 값을 메인 Activity 로 넘기면 된다.
                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Cancel 버튼 클릭시
                    }
                });
        ab.show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private boolean existential(){
        return !(phone_Str.equals("") && kakao_Str.equals(""));
    }
    private boolean compulsory(){
        return (!category_Str.equals("") && !objname_Str.equals("") && !place_Str.equals("") && !date_Str.equals(""));
    }

    @Override
    public void processFinish(Object output) {

    }

    @Override
    public void processFinish(String output) {
        Intent intent = new Intent();
        setResult(StaticValues.REQUEST_OK,intent);
        finish();
    }

    private void doTakePhotoAction(){
        Toast.makeText(ModifyActivity.this,"기본카메라가 아닌 경우 어플이 비정상적으로 종료되거나 사진을 받아오지 못할 수 있습니다.",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
        startActivityForResult(intent, GET_FROM_GALLERY);
    }
    public void doTakeAlbumAction()
    {
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,GET_FROM_GALLERY);
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.upload_back_btn:
                mDialog.dismiss();
                break;
            case R.id.upload_cancel_btn:
                fileDelete = "true";
                thumbnail.setImageDrawable(null);
                mDialog.dismiss();
                break;
            case R.id.upload_camera_btn:
                doTakePhotoAction();
                mDialog.dismiss();
                break;
            case R.id.upload_gallery_btn:
                doTakeAlbumAction();
                mDialog.dismiss();
                break;
        }
    }
}
