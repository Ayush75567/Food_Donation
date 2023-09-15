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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class DonateFood extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home,profile,donate_food,change_pwd,news,about,logout,pay;
    SharedPreferences sp;
    TextView user_name4,user_email4;
    EditText fname,lname,donate_email,user_address,ngo_address,user_contact,quantity;
    Spinner item;
    String sItem;
    Button donate;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ArrayList<String> arrayList1;

    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_food);
        sp=getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);

        drawerLayout=findViewById(R.id.drawer_layout);
        menu=findViewById(R.id.menu);
        home=findViewById(R.id.home);
        profile=findViewById(R.id.profile);
        donate_food=findViewById(R.id.donate);
        change_pwd=findViewById(R.id.changepwd);
        news=findViewById(R.id.news);
        about=findViewById(R.id.about);
        logout=findViewById(R.id.logout);
        fname=findViewById(R.id.first_name);
        lname=findViewById(R.id.last_name);
        item=findViewById(R.id.food_item);
        donate_email=findViewById(R.id.donate_email);
        user_address=findViewById(R.id.donate_address);
        ngo_address=findViewById(R.id.donate_ngoaddress);
        user_contact=findViewById(R.id.donate_contact);
        quantity=findViewById(R.id.donate_enterquantity);
        donate=findViewById(R.id.donate_btn);
        pay=findViewById(R.id.pay);
        user_name4=findViewById(R.id.user_name);
        user_email4=findViewById(R.id.user_email);

        db=openOrCreateDatabase("FOOD_DONATION",MODE_PRIVATE,null);

        String table_query="CREATE TABLE IF NOT EXISTS PROFILE(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(100),EMAIL VARCHAR(100),GENDER VARCHAR(6),DOB VARCAHR(10),ADDRESS VARCHAR(200),CITY VARCHAR(50),CONTACT INT(10),PASSWORD VARCHAR(20))";
        db.execSQL(table_query);

        String food_query="CREATE TABLE IF NOT EXISTS DONATE_FOOD(USERID INTEGER PRIMARY KEY AUTOINCREMENT,FNAME VARCHAR(100),LNAME VARCHAR(100),EMAIL VARCHAR(100),USER_ADDRESS VARCHAR(200),NGO_ADDRESS VARCHAR(200),CONTACT INTEGER(10),ITEM VARCHAR(100),QUANTITY VARCHAR(1000))";
        db.execSQL(food_query);
        user_name4.setText(sp.getString(ConstantSp.NAME,""));
        user_email4.setText(sp.getString(ConstantSp.EMAIL,""));

        arrayList1=new ArrayList<>();
        arrayList1.add("Select Item to Donate");
        arrayList1.add("Vegeterian");
        arrayList1.add("Non-Vegeterian");
        arrayList1.add("Fruits");
        arrayList1.add("Vegetables");


        //spinner setting
        ArrayAdapter adapter1=new ArrayAdapter(DonateFood.this, android.R.layout.simple_expandable_list_item_1,arrayList1);
        adapter1.setDropDownViewResource(android.R.layout.simple_list_item_checked);

        item.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
                    sItem="";
                }else {
                    sItem=arrayList1.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        item.setAdapter(adapter1);


        //validations:
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fname.getText().toString().trim().equals("")){
                    fname.setError("Enter Your First Name");
                }else if (lname.getText().toString().trim().equals("")){
                    lname.setError("Enter Your Last Name");
                } else if (donate_email.getText().toString().trim().equals("")) {
                    donate_email.setError("Enter Email Id");
                }else if (!donate_email.getText().toString().trim().matches(emailPattern)){
                    donate_email.setError("Enter Valid Email");
                }else if (user_address.getText().toString().equals("")){
                    user_address.setError("Enter Your Address");
                } else if (ngo_address.getText().toString().equals("")) {
                    ngo_address.setError("Enter NGO Address");
                } else if (user_contact.getText().toString().equals("")) {
                    user_contact.setError("Enter contact Number");
                } else if (quantity.getText().toString().equals("")) {
                    quantity.setError("Enter Quantity to Donate");
                }else{
                        String insert_food = "INSERT INTO DONATE_FOOD VALUES(NULL,'" + fname.getText().toString() + "','" + lname.getText().toString() + "','" + donate_email.getText().toString() + "','" + user_address.getText().toString() + "','" + ngo_address.getText().toString() + "','" + user_contact.getText().toString() + "','" + sItem + "','" + quantity.getText().toString() + "')";
                        db.execSQL(insert_food);
                        new commomMethod(DonateFood.this, "Record Added SuccessFully");
                        new commomMethod(DonateFood.this, HomeActivity.class);

                    sp.edit().putString(ConstantSp.FNAME,fname.getText().toString()).commit();
                    sp.edit().putString(ConstantSp.LNAME,lname.getText().toString()).commit();
                    sp.edit().putString(ConstantSp.D_EMAIL,donate_email.getText().toString()).commit();
                    sp.edit().putString(ConstantSp.D_ADDRESS,user_address.getText().toString()).commit();
                    sp.edit().putString(ConstantSp.NGO_ADDRESS,ngo_address.getText().toString()).commit();
                    sp.edit().putString(ConstantSp.U_CONTACT,user_contact.getText().toString()).commit();
                    sp.edit().putString(ConstantSp.FOODTYPE,sItem).commit();
                    sp.edit().putString(ConstantSp.QUANTITY,quantity.getText().toString()).commit();
                        clear();
                }
            }
            private void clear() {
                fname.setText("");
                lname.setText("");
                donate_email.setText("");
                user_address.setText("");
                ngo_address.setText("");
                user_contact.setText("");
                quantity.setText("");
                sItem=arrayList1.get(0);
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(DonateFood.this,HomeActivity.class);
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(DonateFood.this, Payment_Activity.class);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(DonateFood.this,Profile.class);
            }
        });
        donate_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
        change_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(DonateFood.this,ChangePassword.class);
            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(DonateFood.this, News.class);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(DonateFood.this, About.class);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().clear().commit();
                redirectActivity(DonateFood.this,MainActivity.class);
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
    public void onBackPressed() {
        startActivity(new Intent(DonateFood.this, HomeActivity.class));
    }
}