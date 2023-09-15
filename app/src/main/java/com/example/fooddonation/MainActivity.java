package com.example.fooddonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    TextView title,welcome,slogan_msg,sp1,sp2,forgot_pwd;
    EditText email,password;
    ImageView passwordHide,passwordShow;
    CheckBox Remember;
    Button login,signup;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    SQLiteDatabase db;
    SharedPreferences sp;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{6,}" +                // at least 4 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp=getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);

        title=findViewById(R.id.login);
        welcome=findViewById(R.id.welcome);
        slogan_msg=findViewById(R.id.msg);
        sp1=findViewById(R.id.msg1);
        sp2=findViewById(R.id.msg2);
        Remember=findViewById(R.id.remember);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login_btn);
        signup=findViewById(R.id.signup_btn);
        forgot_pwd=findViewById(R.id.forgot_pwd);
        passwordHide=findViewById(R.id.password_hide);
        passwordShow=findViewById(R.id.password_show);

        passwordHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordHide.setVisibility(View.GONE);
                passwordShow.setVisibility(View.VISIBLE);

                password.setTransformationMethod(new PasswordTransformationMethod());
            }
        });

        passwordShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordHide.setVisibility(View.VISIBLE);
                passwordShow.setVisibility(View.GONE);

                password.setTransformationMethod(null);
            }
        });

        //databse creation
        db=openOrCreateDatabase("FOOD_DONATION",MODE_PRIVATE,null);
        String table_query="CREATE TABLE IF NOT EXISTS PROFILE(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(100),EMAIL VARCHAR(100),GENDER VARCHAR(6),DOB VARCAHR(10),ADDRESS VARCHAR(200),CITY VARCHAR(50),CONTACT INT(10),PASSWORD VARCHAR(20))";
        db.execSQL(table_query);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if (email.getText().toString().trim().equals("")){
                  email.setError("Email Required");
              }else if (!email.getText().toString().trim().matches(emailPattern)) {
                  email.setError("Enter Valid EmailId");
              }else if (password.getText().toString().trim().equals("")){
                  password.setError("Password Required");
              }else if (!PASSWORD_PATTERN.matcher(password.getText().toString().trim()).matches()) {
                  password.setError("There Should be atleast 1 capital letter,atleast 1 symbol like (@,!,#,$,%,etc) and atleast one number and Minimum 6 Character should be there");
              }else {
                  String select_query="SELECT * FROM PROFILE WHERE EMAIL='"+email.getText().toString()+"' AND PASSWORD='"+password.getText().toString()+"'";
                  Cursor cursor=db.rawQuery(select_query,null);
                  if (cursor.getCount()>0){

                      while (cursor.moveToNext()){
                          String sUserid=cursor.getString(0);
                          String sName=cursor.getString(1);
                          String sEmail=cursor.getString(2);
                          String sGender=cursor.getString(3);
                          String sDob=cursor.getString(4);
                          String sAddress=cursor.getString(5);
                          String sCity=cursor.getString(6);
                          String sContact=cursor.getString(7);
                          String sPassword=cursor.getString(8);

                          sp.edit().putString(ConstantSp.ID,sUserid).commit();
                          sp.edit().putString(ConstantSp.NAME,sName).commit();
                          sp.edit().putString(ConstantSp.EMAIL,sEmail).commit();
                          sp.edit().putString(ConstantSp.DOB,sDob).commit();
                          sp.edit().putString(ConstantSp.ADDRESS,sAddress).commit();
                          sp.edit().putString(ConstantSp.CITY,sCity).commit();
                          sp.edit().putString(ConstantSp.Contact,sContact).commit();
                          sp.edit().putString(ConstantSp.Password,sPassword).commit();
                          if (Remember.isChecked()){
                              sp.edit().putString(ConstantSp.REMEMBER,"YES").commit();
                          }else {
                              sp.edit().putString(ConstantSp.REMEMBER,"").commit();
                          }
                      }
                      new commomMethod(MainActivity.this,"Login Successful");
                      new commomMethod(MainActivity.this, HomeActivity.class);
                  }else {
                      new commomMethod(MainActivity.this,"Login Unsuccessfull");
                  }
              }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new commomMethod(MainActivity.this, Signup.class);
            }
        });
        forgot_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new commomMethod(MainActivity.this, ResetPassword.class);
            }
        });
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finishAffinity();
    }
}