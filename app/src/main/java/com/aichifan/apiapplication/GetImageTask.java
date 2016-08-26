package com.aichifan.apiapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yoda on 16/8/23.
 */
public class GetImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView imageView;
    public GetImageTask() {

    }
    public GetImageTask(ImageView imageView) {
        this.imageView = imageView;
    }


    @Override
    protected Bitmap doInBackground(String... urls) {
        InputStream imageIs =  MyUtils.requestByUrl(urls[0], "GET", null);
        Bitmap bitmap = BitmapFactory.decodeStream(imageIs);
        try {
            imageIs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap imageBit) {

        imageView.setImageBitmap(imageBit);
        Log.v("test", "image set");
    }
}
