package com.programmer.awesome.mjclnf.serverConnect;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.programmer.awesome.mjclnf.customObject.StaticValues;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by USER on 2016-11-17.
 */

public class DownloadImageTask extends AsyncTask<String, Void, ArrayList<Bitmap>> {
    private ImageView[] bmImage;
    private boolean useSampling = false;
    private BitmapFactory.Options options = new BitmapFactory.Options();
    public DownloadImageTask(ImageView[] bmImage) {
        this.bmImage = bmImage;
    }
    public DownloadImageTask(ImageView[] bmImage, boolean option)
    {
        this(bmImage);
        useSampling = option;
    }
    @Override
    protected void onPostExecute(ArrayList<Bitmap> bitmap) {
        if (bitmap.size() < 1) {

        } else {
            for (int i = 0; i < bitmap.size(); i++)
                bmImage[i].setImageBitmap(bitmap.get(i));
        }

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Bitmap> doInBackground(String... strings) {
        int size = strings.length;
        String urls[] = new String[5];
        for (int i = 0; i < size; ++i)
            urls[i] = strings[i];
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        try {
            InputStream in = null;
            for (int i = 0; i < 5; ++i) {
                if (urls[i] == null)
                    break;
                if(useSampling){
                    options.inJustDecodeBounds = true;
                    in = new URL(StaticValues.SERVER_BASE_URL+urls[i]).openStream();
                    BitmapFactory.decodeStream(in,null,options);
                    int imageWidth = 700;
                    int samplesize = calculateInSampleSize(options,imageWidth,imageWidth*9/16);
                    options.inSampleSize  = samplesize;
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    options.inJustDecodeBounds = false;
                    in.close();
                    in = new URL(StaticValues.SERVER_BASE_URL+urls[i]).openStream();
                    bitmaps.add(BitmapFactory.decodeStream(in,null,options)) ;
                    in.close();
                }else{
                    in = new URL(StaticValues.SERVER_BASE_URL+urls[i]).openStream();
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    bitmaps.add(BitmapFactory.decodeStream(in,null,options));
                    in.close();
                }
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bitmaps;
    }
    static public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}