package com.example.fooddonation;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONException;
import org.json.JSONObject;
import com.razorpay.PaymentResultListener;

public class Payment_Activity extends AppCompatActivity  implements PaymentResultListener {
    EditText amount;
    DrawerLayout drawerLayout;
    LinearLayout home,profile,donate_food,change_pwd,news,about,logout,pay;
    ImageView menu;
    Button pay1;
    SharedPreferences sp;
    SQLiteDatabase db;
    TextView user_name3,user_email3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences(ConstantSp.PREF, MODE_PRIVATE);
        setContentView(R.layout.activity_payment);
        amount = findViewById(R.id.amount);
        pay1 = findViewById(R.id.pay_btn);
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.home);
        profile = findViewById(R.id.profile);
        donate_food = findViewById(R.id.donate);
        change_pwd = findViewById(R.id.changepwd);
        news = findViewById(R.id.news);
        about = findViewById(R.id.about);
        user_name3=findViewById(R.id.user_name);
        user_email3=findViewById(R.id.user_email);
        logout = findViewById(R.id.logout);
        pay = findViewById(R.id.pay);
        db=openOrCreateDatabase("FOOD_DONATION",MODE_PRIVATE,null);
        String table_query="CREATE TABLE IF NOT EXISTS PROFILE(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(100),EMAIL VARCHAR(100),GENDER VARCHAR(6),DOB VARCAHR(10),ADDRESS VARCHAR(200),CITY VARCHAR(50),CONTACT INT(10),PASSWORD VARCHAR(20))";
        db.execSQL(table_query);

        user_name3.setText(sp.getString(ConstantSp.NAME,""));
        user_email3.setText(sp.getString(ConstantSp.EMAIL,""));

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               redirectActivity(Payment_Activity.this,HomeActivity.class);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(Payment_Activity.this, Profile.class);
            }
        });
        donate_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(Payment_Activity.this, DonateFood.class);
            }
        });
        change_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(Payment_Activity.this, ChangePassword.class);
            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(Payment_Activity.this, News.class);
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(Payment_Activity.this, About.class);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().clear().commit();
                redirectActivity(Payment_Activity.this, MainActivity.class);
            }
        });

        pay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String samount = amount.getText().toString();
                int amount = Math.round(Float.parseFloat(samount) * 100);

                // initialize Razorpay account.
                Checkout checkout = new Checkout();

                // set your id as below
                checkout.setKeyID("rzp_test_xsiOz9lYtWKHgF");

                // set image
                checkout.setImage(R.mipmap.fd);

                // initialize json object
                JSONObject object = new JSONObject();
                try {
                    // to put name
                    object.put("name", getResources().getString(R.string.app_name));
//
//                    // put description
//                    object.put("description", "Test payment");

                    // to set theme color
                    object.put("theme.color", "");

                    // put the currency
                    object.put("currency", "INR");

                    // put amount
                    object.put("amount", amount);

                    // put mobile number
                    object.put("prefill.contact", sp.getString(ConstantSp.Contact, ""));

                    // put email
                    object.put("prefill.email", sp.getString(ConstantSp.EMAIL, ""));

                    // open razorpay to checkout activity
                    checkout.open(Payment_Activity.this, object);
                    onBackPressed();
                } catch (JSONException e) {
                    Toast.makeText(Payment_Activity.this, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

    }
    @Override
    public void onPaymentSuccess(String s) {
        // this method is called on payment success.
        try {
            Toast.makeText(this, "Payment Successful :\nPayment ID: " + s + "\nPayment Data: " + s, Toast.LENGTH_SHORT).show();
            Log.d("RESPONSE_SUCCESS", "Payment Successful :\nPayment ID: " + s + "\nPayment Data: " + s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentError(int i, String s) {
        // on payment failed.
        try {
            Toast.makeText(this, "Payment Failed:\nPayment Data: " + s, Toast.LENGTH_SHORT).show();
//            Log Log;
//            Log.d("RESPONSE_FAIL", "Payment Failed:\nPayment Data: " + paymentData.getData());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
//        super.onBackPressed();
        new commomMethod(Payment_Activity.this,HomeActivity.class);
    }
}
