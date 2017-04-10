package com.programmer.awesome.mjclnf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.programmer.awesome.mjclnf.serverConnect.DownloadImageTask;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by hyunsoo on 2016-11-09.
 */

public class ImagePinchView extends Activity {

    PhotoViewAttacher photoViewAttacher;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagepinchview);
        ImageView iv_photo = (ImageView)findViewById(R.id.pinchview);
        Intent intent = getIntent();
        String url = intent.getStringExtra("image_url");
        new DownloadImageTask(new ImageView[]{iv_photo}).execute(url);
        photoViewAttacher = new PhotoViewAttacher(iv_photo);

    }
}
