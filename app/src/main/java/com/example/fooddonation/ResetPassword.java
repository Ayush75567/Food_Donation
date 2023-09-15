package com.example.fooddonation;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ResetPassword extends AppCompatActivity {

    EditText email;
    Button reset_btn;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);

        db=openOrCreateDatabase("FOOD_DONATION",MODE_PRIVATE,null);
        String table_query="CREATE TABLE IF NOT EXISTS PROFILE(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(100),EMAIL VARCHAR(100),GENDER VARCHAR(6),DOB VARCAHR(10),ADDRESS VARCHAR(200),CITY VARCHAR(50),CONTACT INT(10),PASSWORD VARCHAR(20))";
        db.execSQL(table_query);

        email=findViewById(R.id.forgot_email);
        reset_btn = findViewById(R.id.reset_button);

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().trim().equals("")){
                    email.setError("Enter Email");
                } else if (!email.getText().toString().trim().matches(emailPattern)) {
                    email.setError("Enter Valida Email");
                }else {
                    String select_query="SELECT * FROM PROFILE WHERE EMAIL='"+email.getText().toString()+"'";
                    Cursor cursor=db.rawQuery(select_query,null);

                    if (cursor.getCount()>0){
                        new commomMethod(ResetPassword.this, SetPassword.class);
                    }else {
                        new commomMethod(ResetPassword.this,"InValid Email");
                    }
                }
            }
        });
    }
}