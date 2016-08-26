package com.aichifan.apiapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    public final static String HOST = "http://192.168.0.103:3000";

    public final static String USER = "userinfo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream is = MyUtils.requestByUrl(HOST + "/user/1", "GET", null);
                Gson gson = new Gson();
                User user = gson.fromJson(new InputStreamReader(is), User.class);

                user.setName("shansuli");
                InputStream newIs = MyUtils.requestByUrl(HOST + "/user/1", "PUT", gson.toJson(user));
//                Log.v("put result", gson.fromJson(new InputStreamReader(newIs), JSONObject.class).toString());
            }
        }).start();
    }

    //登陆按钮事件
    public void clickLogin(View view) {
        String name = ((EditText)findViewById(R.id.nameText)).getText().toString();
        String password = ((EditText)findViewById(R.id.passwordText)).getText().toString();
        final User user = new User( name, password );

        new Thread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                InputStream is = MyUtils.requestByUrl(HOST + "/login", "POST", gson.toJson(user));
                if(is == null) {
                    Log.v("error", "return stream is null");
                } else {
                    User returnUser = gson.fromJson(new InputStreamReader(is), User.class);

                    if(returnUser.getId() == null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, getText(R.string.login_fail), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {

                        user.setId(returnUser.getId());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                                intent.putExtra(USER, user);
                                startActivity(intent);
                            }
                        });
                    }
                }
            }
        }).start();
    }
}
