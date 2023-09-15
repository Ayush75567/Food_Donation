package com.example.fooddonation;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;

import java.sql.SQLInput;
import java.util.regex.Pattern;

public class SetPassword extends AppCompatActivity {

    EditText set_pwd,set_cpwd;
    Button change_pwd;
    SQLiteDatabase db;
    ImageView passwordHide,passwordShow,passwordHide1,passwordShow1;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" + "(?=.*[@#$%^&+=])" + "(?=\\S+$)" + ".{6,}" + "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        db=openOrCreateDatabase("FOOD_DONATION",MODE_PRIVATE,null);
        String table_query="CREATE TABLE IF NOT EXISTS PROFILE(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(100),EMAIL VARCHAR(100),GENDER VARCHAR(6),DOB VARCAHR(10),ADDRESS VARCHAR(200),CITY VARCHAR(50),CONTACT INT(10),PASSWORD VARCHAR(20))";
        db.execSQL(table_query);

        set_pwd=findViewById(R.id.set_pwd);
        set_cpwd=findViewById(R.id.set_cpwd);
        change_pwd=findViewById(R.id.change_button);
        passwordHide=findViewById(R.id.set_password_hide);
        passwordShow=findViewById(R.id.set_password_show);
        passwordHide1=findViewById(R.id.set_password_hide1);
        passwordShow1=findViewById(R.id.set_password_show1);

        passwordHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordHide.setVisibility(View.GONE);
                passwordShow.setVisibility(View.VISIBLE);

                set_pwd.setTransformationMethod(new PasswordTransformationMethod());
            }
        });

        passwordShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordHide.setVisibility(View.VISIBLE);
                passwordShow.setVisibility(View.GONE);

                set_pwd.setTransformationMethod(null);
            }
        });

        passwordHide1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordHide1.setVisibility(View.GONE);
                passwordShow1.setVisibility(View.VISIBLE);

                set_cpwd.setTransformationMethod(new PasswordTransformationMethod());
            }
        });

        passwordShow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordHide1.setVisibility(View.VISIBLE);
                passwordShow1.setVisibility(View.GONE);

                set_cpwd.setTransformationMethod(null);
            }
        });

        change_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (set_pwd.getText().toString().trim().equals("")){
                    set_pwd.setError("Password Required");
                }else if (!PASSWORD_PATTERN.matcher(set_pwd.getText().toString().trim()).matches()) {
                    set_pwd.setError("There Should be atleast 1 capital letter,atleast 1 symbol like (@,!,#,$,%,etc) and atleast one number and Minimum 6 Character should be there");
                } else if (set_cpwd.getText().toString().trim().equals("")) {
                    set_cpwd.setError("Enter Confirm Password");
                } else if (!set_cpwd.getText().toString().trim().matches(set_pwd.getText().toString().trim())) {
                    set_cpwd.setError("Enter password same as above");
                }else {
                    String updateQuery = "UPDATE PROFILE SET PASSWORD='"+set_pwd.getText().toString()+"'";
                    db.execSQL(updateQuery);
                    new commomMethod(SetPassword.this,"Update Successfully");
                    new commomMethod(SetPassword.this, MainActivity.class);
                }
            }
        });
    }
}