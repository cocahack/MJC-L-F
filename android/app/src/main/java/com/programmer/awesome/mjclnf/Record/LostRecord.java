package com.programmer.awesome.mjclnf.Record;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.programmer.awesome.mjclnf.AsyncResponse;
import com.programmer.awesome.mjclnf.R;
import com.programmer.awesome.mjclnf.customObject.StaticValues;
import com.programmer.awesome.mjclnf.customObject.Member;
import com.programmer.awesome.mjclnf.serverConnect.DownloadImageTask;
import com.programmer.awesome.mjclnf.serverConnect.RequestToServer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LostRecord extends AppCompatActivity implements AsyncResponse {
    public static final int GET_FROM_GALLERY = 1;
    final int DIALOG_DATE = 1;
    public static final long MAX_FILE_SIZE = 5*1024*1024;
    //widget member
    private TextView sellist;
    private TextView phoneText;
    private TextView findNameText ;
    private TextView findPlaceText;
    private TextView kakaoText ;
    private TextView information ;
    private Button fileUploadBtn ;
    private String fileName;
    private String filePath;
    private ImageView thumbnail;
    // App user
    private Member user;
    // Data of lost item
    private String eventDateString = "";
    private String phone;
    private String kakao;
    private String findPlace;
    private String category;
    private String findName;
    private String info;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lostrecode);

        Intent intent = getIntent();
        user = (Member) intent.getSerializableExtra("user");
        Button b1 = (Button) findViewById(R.id.dateselect);
        Button b2 = (Button) findViewById(R.id.listbtn);
        final Button lost_record = (Button)findViewById(R.id.btn_lostrecord);
        sellist = (TextView) findViewById(R.id.list);
        phoneText = (TextView)findViewById(R.id.phone);
        findNameText = (TextView)findViewById(R.id.findname);
        findPlaceText = (TextView)findViewById(R.id.findplace);
        kakaoText = (TextView)findViewById(R.id.kakao);
        information = (TextView) findViewById(R.id.information);
        fileUploadBtn = (Button) findViewById(R.id.fin);
        thumbnail = (ImageView)findViewById(R.id.uploadImg);
        fileName = null;
        filePath = null;

        fileUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LostRecord.this);

                // 제목셋팅
                // alertDialogBuilder.setTitle("업로드할 이미지 선택");

                // AlertDialog 셋팅
                alertDialogBuilder
                        .setMessage("업로드할 이미지 선택")
                        .setCancelable(false)
                        .setPositiveButton("앨범선택",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        doTakeAlbumAction();
                                    }
                                })
                        .setNeutralButton("취소",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                })
                        .setNegativeButton("사진촬영",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(

                                            DialogInterface dialog, int id) {
                                        doTakePhotoAction();
                                    }
                                });

                // 다이얼로그 생성
                AlertDialog alertDialog = alertDialogBuilder.create();

                // 다이얼로그 보여주기
                alertDialog.show();

            }
        });
        lost_record.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                phone = phoneText.getText().toString();
                kakao = kakaoText.getText().toString();
                findPlace = findPlaceText.getText().toString();
                try{
                    category = sellist.getText().toString();
                }catch(NullPointerException ignore){
                    category = null;
                }
                findName = findNameText.getText().toString();
                info = information.getText().toString();
                if(phone.length()<10)
                    Toast.makeText(LostRecord.this,"전화번호를 다시 입력하세요.",Toast.LENGTH_SHORT).show();
                else{
                    if(compulsory()&&existential()){
                        RequestToServer connServer = (RequestToServer) new RequestToServer(LostRecord.this);
                        connServer.delegate = LostRecord.this;
                        connServer.execute("lost"
                                ,user.getStnum()
                                ,user.getName()
                                ,phone
                                ,kakao
                                ,eventDateString
                                ,findPlace
                                ,category
                                ,findName
                                ,info
                                ,fileName
                                ,filePath
                        );
                    }else{
                        Toast.makeText(LostRecord.this,"일부 항목을 입력하지 않았습니다.",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_DATE); // 날짜 설정 다이얼로그 띄우기
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogSelectOption();
            }
        });
    }

    @Override
    public void processFinish(Object output) {

    }

    @Override
    public void processFinish(String output) {
        try {
            int msg = Integer.parseInt(output);
            if(msg>0){
                setResult(1);
                finishActivity(StaticValues.REQUEST_LOSTRECORD);
                finish();
            }else if(msg==0){
                Toast.makeText(LostRecord.this,"서버 오류로 등록되지 않았습니다.",Toast.LENGTH_SHORT).show();
            }else if(msg==-1){
                Toast.makeText(LostRecord.this,"서버 DB에 오류가 발생했습니다.",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(LostRecord.this,"서버가 응답하지 않습니다.",Toast.LENGTH_SHORT).show();
            }
        }catch (NumberFormatException e){
            Toast.makeText(LostRecord.this,"서버가 응답하지 않습니다.",Toast.LENGTH_SHORT).show();
        }
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

//                    uploadImg = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImageUri);
                    BitmapFactory factory = new BitmapFactory();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(filePath);
                    int imageWidth = 700;
                    int samplsize = DownloadImageTask.calculateInSampleSize(options,imageWidth,imageWidth*9/16);
                    options.inSampleSize = samplsize;
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    options.inJustDecodeBounds = false;
                    uploadImg = BitmapFactory.decodeFile(filePath,options);
                    thumbnail.setImageBitmap(uploadImg);

                /* catch (IOException e) {
                    e.printStackTrace();
                }*/
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
                                thumbnail.setImageDrawable(null);
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
        return cursor.getString(column_index);
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
                        (LostRecord.this, // 현재화면의 제어권자
                                new DatePickerDialog.OnDateSetListener() {
                                    public void onDateSet(DatePicker view,
                                                          int year, int monthOfYear, int dayOfMonth) {
                                        TextView seldate = (TextView) findViewById(R.id.lostdate);
                                        seldate.setText(year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth + "일");
                                        if(dayOfMonth>=1&&dayOfMonth<=10)
                                            eventDateString+=year+"-"+(monthOfYear+1)+"-0"+dayOfMonth;
                                        else
                                        eventDateString+=year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                                    }
                                }
                                , // 사용자가 날짜설정 후 다이얼로그 빠져나올때
                                //    호출할 리스너 등록
                                nowyear, nowmonth - 1, nowday); // 기본값 연월일
                return dpd;
        }
        return super.onCreateDialog(id);
    }

    private void DialogSelectOption() {
        final String items[] = {"분류를 선택하세요.","핸드폰","지갑","카드","의류","신발","가방","학생증","USB","기타"};
        AlertDialog.Builder ab = new AlertDialog.Builder(LostRecord.this);
        ab.setTitle("분류선택");
        ab.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (items[whichButton] == "분류를 선택하세요."){
                            sellist.setText(null);
                        } else
                        sellist.setText(items[whichButton]);
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
                        sellist.setText(null);
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
        return !(phone.equals("") && kakao.equals(""));
    }
    private boolean compulsory(){
        return (!category.equals("") && !findName.equals("") && !findPlace.equals("") && !eventDateString.equals(""));
    }
    private void doTakePhotoAction(){
        Toast.makeText(LostRecord.this,"기본카메라가 아닌 경우 어플이 비정상적으로 종료되거나 사진을 받아오지 못할 수 있습니다.",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
        startActivityForResult(intent, GET_FROM_GALLERY);
    }
    public void doTakeAlbumAction()
    {
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,GET_FROM_GALLERY);
    }
}

