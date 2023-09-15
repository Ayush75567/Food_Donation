package com.example.fooddonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Currency;
import java.util.regex.Pattern;

public class ChangePassword extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home,profile,donate_food,change_pwd,news,about,logout,pay;
    EditText oldpwd,newpwd,c_newpwd;
    TextView user_name2,user_email2;
    Button change;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" + "(?=.*[@#$%^&+=])" + "(?=\\S+$)" + ".{6,}" + "$");
    SQLiteDatabase db;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        sp=getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);

        db=openOrCreateDatabase("FOOD_DONATION",MODE_PRIVATE,null);
        String table_query="CREATE TABLE IF NOT EXISTS PROFILE(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(100),EMAIL VARCHAR(100),GENDER VARCHAR(6),DOB VARCAHR(10),ADDRESS VARCHAR(200),CITY VARCHAR(50),CONTACT INT(10),PASSWORD VARCHAR(20))";
        db.execSQL(table_query);

        drawerLayout=findViewById(R.id.drawer_layout);
        oldpwd=findViewById(R.id.old_pwd);
        newpwd=findViewById(R.id.change_pwd);
        c_newpwd=findViewById(R.id.change_cpwd);
        change=findViewById(R.id.change_button1);
        menu=findViewById(R.id.menu);
        home=findViewById(R.id.home);
        profile=findViewById(R.id.profile);
        donate_food=findViewById(R.id.donate);
        change_pwd=findViewById(R.id.changepwd);
        news=findViewById(R.id.news);
        about=findViewById(R.id.about);
        logout=findViewById(R.id.logout);
        user_name2=findViewById(R.id.user_name);
        user_email2=findViewById(R.id.user_email);
        pay=findViewById(R.id.pay);

        user_name2.setText(sp.getString(ConstantSp.NAME,""));
        user_email2.setText(sp.getString(ConstantSp.EMAIL,""));

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(ChangePassword.this,HomeActivity.class);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(ChangePassword.this,Profile.class);
            }
        });
        donate_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(ChangePassword.this,DonateFood.class);
            }
        });
        change_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(ChangePassword.this, News.class);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(ChangePassword.this, About.class);
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(ChangePassword.this, Payment_Activity.class);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().clear().commit();
                redirectActivity(ChangePassword.this,MainActivity.class);
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (oldpwd.getText().toString().trim().equals("")) {
                    oldpwd.setError("Enter Old Password");
                }else if (!PASSWORD_PATTERN.matcher(oldpwd.getText().toString().trim()).matches()) {
                    oldpwd.setError("There Should be atleast 1 capital letter,atleast 1 symbol like (@,!,#,$,%,etc) and atleast one number and Minimum 6 Character should be there");
                }else if (newpwd.getText().toString().trim().equals("")){
                        newpwd.setError("Enter New Password");
                }else if (!PASSWORD_PATTERN.matcher(newpwd.getText().toString().trim()).matches()) {
                    newpwd.setError("There Should be atleast 1 capital letter,atleast 1 symbol like (@,!,#,$,%,etc) and atleast one number and Minimum 6 Character should be there");
                } else if (c_newpwd.getText().toString().trim().equals("")) {
                    c_newpwd.setError("Enter Confirm Password");
                }else if (!PASSWORD_PATTERN.matcher(c_newpwd.getText().toString().trim()).matches()) {
                    c_newpwd.setError("There Should be atleast 1 capital letter,atleast 1 symbol like (@,!,#,$,%,etc) and atleast one number and Minimum 6 Character should be there");
                }else {
                    String select_query="SELECT * FROM PROFILE WHERE PASSWORD='"+oldpwd.getText().toString()+"'";
                    Cursor cursor=db.rawQuery(select_query,null);

                    if (cursor.getCount()>0){
                        String updateQuery = "UPDATE PROFILE SET PASSWORD='"+newpwd.getText().toString()+"'";
                        db.execSQL(updateQuery);
                        new commomMethod(ChangePassword.this,"Update Successfully");
                        oldpwd.setText("");
                        newpwd.setText("");
                        c_newpwd.setText("");
                    }else {
                        new commomMethod(ChangePassword.this,"Invalid Old Password");
                    }
                }
            }
        });
    }
    public static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public static void closeDrawer(DrawerLayout drawerLayout){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent=new Intent(activity,secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}