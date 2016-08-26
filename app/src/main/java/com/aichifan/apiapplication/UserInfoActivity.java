package com.aichifan.apiapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class UserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        Intent intent = getIntent();

        final User user = (User)intent.getSerializableExtra(MainActivity.USER);
        Log.v("intent user info", user.getId().toString());

        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream is = MyUtils.requestByUrl(MainActivity.HOST + "/user/" + user.getId(), "GET", null);
                Gson gson = new Gson();
                final User user = gson.fromJson(new InputStreamReader(is), User.class);

                new GetImageTask((ImageView) findViewById(R.id.userPhoto)).execute(MainActivity.HOST + user.getPhoto());
//                InputStream imageIs = MyUtils.requestByUrl(MainActivity.HOST + user.getPhoto(), "GET", null);
//
//                final Bitmap bitmap = BitmapFactory.decodeStream(imageIs);

                try {
                    is.close();
//                    imageIs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((TextView)findViewById(R.id.userName)).setText(user.getName());
                        ((TextView)findViewById(R.id.userTel)).setText(user.getTel());
//                        ((ImageView)findViewById(R.id.userPhoto)).setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
    }
}
