package com.example.fooddonation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.razorpay.PaymentResultListener;

import java.util.ArrayList;
//import java.util.IllegalFormatCodePointException;
//import java.util.List;


public class HomeActivity extends AppCompatActivity{
    DrawerLayout drawerLayout;
    TextView user_email,user_name;
    ImageView menu;
    SharedPreferences sp;
    SQLiteDatabase db;
    ArrayList<donate_struct> donate_array;
    RecyclerView recyclerView;
    LinearLayout home,profile,donate_food,change_pwd,news,about,logout,pay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout=findViewById(R.id.drawer_layout);
        user_email=findViewById(R.id.user_email);
        menu=findViewById(R.id.menu);
        home=findViewById(R.id.home);
        profile=findViewById(R.id.profile);
        donate_food=findViewById(R.id.donate);
        change_pwd=findViewById(R.id.changepwd);
        news=findViewById(R.id.news);
        about=findViewById(R.id.about);
        user_name=findViewById(R.id.user_name);
        pay=findViewById(R.id.pay);
        logout=findViewById(R.id.logout);
        sp=getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);
        recyclerView=findViewById(R.id.recyler_donate);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        donate_array=new ArrayList<>();

//        donate_array.add(new donate_struct("a","b","ab@gmail.com","ghodasar","vatva","9999999999","veg","4"));
//        donate_array.add(new donate_struct("a","b","ab@gmail.com","ghodasar","vatva","9999999999","veg","4"));
//        donate_array.add(new donate_struct("a","b","ab@gmail.com","ghodasar","vatva","9999999999","veg","4"));
//        DonateCardAdapter adapter=new DonateCardAdapter(HomeActivity.this,donate_array);
//        recyclerView.setAdapter(adapter);


        db=openOrCreateDatabase("FOOD_DONATION",MODE_PRIVATE,null);
        String table_query="CREATE TABLE IF NOT EXISTS PROFILE(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(100),EMAIL VARCHAR(100),GENDER VARCHAR(6),DOB VARCAHR(10),ADDRESS VARCHAR(200),CITY VARCHAR(50),CONTACT INT(10),PASSWORD VARCHAR(20))";
        db.execSQL(table_query);

        String food_query="CREATE TABLE IF NOT EXISTS DONATE_FOOD(USERID INTEGER PRIMARY KEY AUTOINCREMENT,FNAME VARCHAR(100),LNAME VARCHAR(100),EMAIL VARCHAR(100),USER_ADDRESS VARCHAR(200),NGO_ADDRESS VARCHAR(200),CONTACT INTEGER(10),ITEM VARCHAR(100),QUANTITY VARCHAR(1000))";
        db.execSQL(food_query);

        String selectqy="SELECT * FROM DONATE_FOOD";
        Cursor cursor=db.rawQuery(selectqy,null);

        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
               donate_struct food=new donate_struct();
               food.setFname(cursor.getString(1));
               food.setLname(cursor.getString(2));
               food.setEmail(cursor.getString(3));
               food.setAddress(cursor.getString(4));
               food.setNgo_address(cursor.getString(5));
               food.setContact(cursor.getString(6));
               food.setFtype(cursor.getString(7));
               food.setQuantity(cursor.getString(8));
               donate_array.add(food);
            }
            DonateCardAdapter adapter=new DonateCardAdapter(HomeActivity.this,donate_array);
            recyclerView.setAdapter(adapter);
        }


        user_name.setText(sp.getString(ConstantSp.NAME,""));
        user_email.setText(sp.getString(ConstantSp.EMAIL,""));


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(HomeActivity.this,Profile.class);
            }
        });
        donate_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(HomeActivity.this,DonateFood.class);
            }
        });
        change_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(HomeActivity.this,ChangePassword.class);
            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(HomeActivity.this,News.class);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(HomeActivity.this,About.class);
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(HomeActivity.this, Payment_Activity.class);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().clear().commit();
                redirectActivity(HomeActivity.this,MainActivity.class);
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
    public static void redirectActivity(Activity activity,Class secondActivity){
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
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finishAffinity();
    }


}