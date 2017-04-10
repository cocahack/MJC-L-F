package com.programmer.awesome.mjclnf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.programmer.awesome.mjclnf.customObject.Member;
import com.programmer.awesome.mjclnf.customObject.NoticeFaq;
import com.programmer.awesome.mjclnf.serverConnect.DownLoadNoticeFaq;
import com.programmer.awesome.mjclnf.serverConnect.DownloadImageTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class NoticeFaqView extends AppCompatActivity {
    private Member user = null;
    private String type = null;
    private String itemNo = null;
    private String[] imgPath = null;

    private ImageView[] img = new ImageView[5];
    private TextView contents = null;
    private TextView writer = null;
    private TextView regitdate = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.noicefaqdetail);
        Intent intent = getIntent();
        user = MainActivity.user;
        type = intent.getStringExtra("type");
        itemNo = intent.getStringExtra("itemNo");

        System.gc();
        contents = (TextView) findViewById(R.id.child_content);
        writer = (TextView) findViewById(R.id.child_writer);
        regitdate = (TextView) findViewById(R.id.child_regitdata);
        img[0] = (ImageView)findViewById(R.id.child_image1);
        img[1] = (ImageView)findViewById(R.id.child_image2);
        img[2] = (ImageView)findViewById(R.id.child_image3);
        img[3] = (ImageView)findViewById(R.id.child_image4);
        img[4] = (ImageView)findViewById(R.id.child_image5);
        try {
            List<NoticeFaq> noticeFaqs = (List<NoticeFaq>) new DownLoadNoticeFaq().execute(type,itemNo).get();

            for (NoticeFaq i : noticeFaqs) {
               this.setTitle(i.getTitle());
                contents.setText(i.getContent());
                writer.setText(i.getWriter());
                regitdate.setText(i.getRegitDate());
                imgPath = i.getImgPath().split(";");
            }

            new DownloadImageTask(img,true).execute(imgPath);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}