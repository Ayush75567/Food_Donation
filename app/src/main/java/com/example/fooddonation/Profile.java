package com.example.fooddonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Profile extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home,profile,donate_food,change_pwd,news,about,logout,pay;
    TextView user_email1,user_name1;
    SharedPreferences sp;
    SQLiteDatabase db;
    Button update,edit;
    EditText name,email,dob,address,contact;
    Spinner city;
    RadioGroup gender;
    RadioButton male1,female1;
    String sCity,sGender;
//    RadioButton male1,female1;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Calendar calendar;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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
        user_name1=findViewById(R.id.user_name);
        user_email1=findViewById(R.id.user_email);
        name=findViewById(R.id.name1);
        gender=findViewById(R.id.radio_btn1);
        email=findViewById(R.id.email1);
        dob=findViewById(R.id.dob1);
        contact=findViewById(R.id.contact1);
        city=findViewById(R.id.spinner1);
        calendar=Calendar.getInstance();
        update=findViewById(R.id.update_btn1);
        edit=findViewById(R.id.edit_btn1);
        male1=findViewById(R.id.male1);
        female1=findViewById(R.id.female1);
        address=findViewById(R.id.address1);
        pay=findViewById(R.id.pay);

        db=openOrCreateDatabase("FOOD_DONATION",MODE_PRIVATE,null);
        String table_query="CREATE TABLE IF NOT EXISTS PROFILE(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(100),EMAIL VARCHAR(100),GENDER VARCHAR(6),DOB VARCAHR(10),ADDRESS VARCHAR(200),CITY VARCHAR(50),CONTACT INT(10),PASSWORD VARCHAR(20))";
        db.execSQL(table_query);

        user_name1.setText(sp.getString(ConstantSp.NAME,""));
        user_email1.setText(sp.getString(ConstantSp.EMAIL,""));

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(Profile.this,HomeActivity.class);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
        donate_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(Profile.this,DonateFood.class);
            }
        });
        change_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(Profile.this,ChangePassword.class);
            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(Profile.this,News.class);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(Profile.this,About.class);
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(Profile.this, Payment_Activity.class);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().clear().commit();
                redirectActivity(Profile.this,MainActivity.class);
            }
        });
        arrayList=new ArrayList<>();
        arrayList.add("Select City");
        arrayList.add("Ahmedabad");
        arrayList.add("Rajkot");
        arrayList.add("Mehsana");
        arrayList.add("Vadodara");
        arrayList.add("Surat");
        arrayList.add("Anand");
        arrayList.add("Mumbai");
        arrayList.add("Delhi");
        arrayList.add("Bhavnagar");
        arrayList.add("Chennai");
        arrayList.add("Kolkata");

        ArrayAdapter adapter=new ArrayAdapter(Profile.this, android.R.layout.simple_list_item_1,arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
                    sCity="";
                }else {
                    sCity=arrayList.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        city.setAdapter(adapter);

        //gender selection
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton=findViewById(i);
                sGender=radioButton.getText().toString();
            }
        });

        //Datepicker code
        DatePickerDialog.OnDateSetListener dateClick=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR,i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);


                SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                dob.setText(sdf.format(calendar.getTime()));

            }
        };

        dob.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                new DatePickerDialog(Profile.this,dateClick,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONDAY),calendar.get(Calendar.DAY_OF_MONTH)).show();
                return true;
            }
        });

        //button click event
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().trim().equals("")){
                    name.setError("Enter Name");
                } else if (email.getText().toString().trim().equals("")){
                    email.setError("Enter Email");
                } else if (!email.getText().toString().trim().matches(emailPattern)) {
                    email.setError("Enter Valid Email");
                } else if (gender.getCheckedRadioButtonId()==-1) {
                    new commomMethod(Profile.this,"Please Select Gender");
                } else if (sCity.equals("")) {
                    new commomMethod(Profile.this,"Please Enter City");
                } else if (dob.getText().toString().trim().equals("")) {
                    dob.setError("Please enter Date of Birth");
                } else if (contact.getText().toString().trim().equals("")) {
                    contact.setError("Enter Phone Number");
                } else if (contact.getText().toString().trim().length()>10) {
                    contact.setError("Enter Valid Phone Number");
                }else {
                    String selectQuery = "SELECT * FROM PROFILE WHERE USERID='" + sp.getString(ConstantSp.ID,"") + "'";
                    Cursor cursor = db.rawQuery(selectQuery, null);
                    if (cursor.getCount() > 0) {
                        String updateQuery = "UPDATE PROFILE SET NAME='"+name.getText().toString()+"',EMAIL='"+email.getText().toString()+"',CONTACT='"+contact.getText().toString()+"',GENDER='"+sGender+"',CITY='"+sCity+"',DOB='"+dob.getText().toString()+"' WHERE USERID='"+sp.getString(ConstantSp.ID,"")+"' ";
                        db.execSQL(updateQuery);
                        new commomMethod(Profile.this,"Update Successfully");

                        sp.edit().putString(ConstantSp.NAME,name.getText().toString()).commit();
                        sp.edit().putString(ConstantSp.EMAIL,email.getText().toString()).commit();
                        sp.edit().putString(ConstantSp.Contact,contact.getText().toString()).commit();
                        sp.edit().putString(ConstantSp.GENDER,sGender).commit();
                        sp.edit().putString(ConstantSp.CITY,sCity).commit();
                        sp.edit().putString(ConstantSp.DOB,dob.getText().toString()).commit();

                        setData(false);
                    } else {
                        new commomMethod(Profile.this,"Invalid UserId");
                    }
                }
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData(true);
            }
        });

        setData(false);

    }

    private void setData(boolean isEnable) {
        name.setEnabled(isEnable);
        email.setEnabled(isEnable);
        contact.setEnabled(isEnable);
        dob.setEnabled(isEnable);
        address.setEnabled(isEnable);

        male1.setEnabled(isEnable);
        female1.setEnabled(isEnable);

        city.setEnabled(isEnable);

        if(isEnable){
            edit.setVisibility(View.GONE);
            update.setVisibility(View.VISIBLE);
        }
        else{
            edit.setVisibility(View.VISIBLE);
            update.setVisibility(View.GONE);
        }

        name.setText(sp.getString(ConstantSp.NAME,""));
        email.setText(sp.getString(ConstantSp.EMAIL,""));
        contact.setText(sp.getString(ConstantSp.Contact,""));
        dob.setText(sp.getString(ConstantSp.DOB,""));
        address.setText(sp.getString(ConstantSp.ADDRESS,""));

        //male1.setChecked(true);
        sGender = sp.getString(ConstantSp.GENDER,"");
        if(sGender.equalsIgnoreCase("Male")){
            male1.setChecked(true);
        }
        else if(sGender.equalsIgnoreCase("Female")){
            female1.setChecked(true);
        }
        else{

        }

        //city.setSelection(2);
        sCity = sp.getString(ConstantSp.CITY,"");
        int iCityPosition = 0;
        for(int i=0;i<arrayList.size();i++){
            if(sCity.equalsIgnoreCase(arrayList.get(i))){
                iCityPosition = i;
            }
        }
        city.setSelection(iCityPosition);
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
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finishAffinity();
    }
}