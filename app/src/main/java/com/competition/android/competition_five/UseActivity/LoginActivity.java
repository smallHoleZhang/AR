package com.competition.android.competition_five.UseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;
import com.competition.android.competition_five.Entity.User;
import com.competition.android.competition_five.HomepageActivity;
import com.competition.android.competition_five.R;
import com.competition.android.competition_five.Uilt.L;

/**
 * Created by hasee on 2017/8/20.
 */

public class LoginActivity extends AppCompatActivity {
        private EditText username;
        private EditText password;
        private Button  goin;
        private Button  sgin;
        private Button  gout;



        private String user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
        initView();
       /* AVObject testObject = new AVObject("TestObject");
        testObject.put("words","Hello World");
        testObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    L.d("Success");
                }else {
                    L.d("失败");
                }
            }
        });*/
/*        User user =  new User();
        user.setUsername("zyc");
        user.setPassword("123");
        user.setEmail("772657404@qq.com");
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if(e == null)
                {
                    L.d("Succerr");
                }else {

                    L.d("false");
                }
            }
        });*/



    }

    private void LogIn() {
        String pass = password.getText().toString();
        user = username.getText().toString();
        AVUser.logInInBackground(user, pass, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if(e == null)
                {
                    startActivity(new Intent(LoginActivity.this, HomepageActivity.class));
                }else
                {
                    L.d(""+e.toString());
                }
            }
        });

    }

    private void initView() {
        username = (EditText) findViewById(R.id.loginactivity_username);
        password = (EditText) findViewById(R.id.loginactivity_password);
        goin = (Button) findViewById(R.id.loginactiviy_gonin);
        sgin = (Button) findViewById(R.id.sign_in);
        gout = (Button) findViewById(R.id.go_out);
        sgin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SgIn();
            }
        });
        goin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogIn();
            }
        });
    }

    //注册
    private void SgIn() {
        String usernames = username.getText().toString();
        String passwords = password.getText().toString();
        String emile = "1233@qq.com";
        User user =  new User();
        user.setUsername(usernames);
        user.setPassword(passwords);
        user.setEmail(emile);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if(e == null)
                {
                    L.d("Succerr");
                }else {
                    e.toString();
                    L.d("false"+  e.toString());
                }
            }
        });

    }
}
