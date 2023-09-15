package com.example.fooddonation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {
    EditText name,email,dob,address,contact,password,c_password;
    Spinner city;
    RadioGroup gender;
    Button sign_up;
    String sCity,sGender;
    RadioButton male,female;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Calendar calendar;
    ArrayList<String> arrayList;
    SharedPreferences sp;
    SQLiteDatabase db;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" + "(?=.*[@#$%^&+=])" + "(?=\\S+$)" + ".{6,}" + "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        dob=findViewById(R.id.dob);
        address=findViewById(R.id.address);
        contact=findViewById(R.id.contact);
        password=findViewById(R.id.signup_pwd);
        c_password=findViewById(R.id.signup_cpwd);
        city=findViewById(R.id.spinner);
        sign_up=findViewById(R.id.signup_button);
        gender=findViewById(R.id.radio_btn);
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        calendar=Calendar.getInstance();

        //databse creation
        db=openOrCreateDatabase("FOOD_DONATION",MODE_PRIVATE,null);
        String table_query="CREATE TABLE IF NOT EXISTS PROFILE(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(100),EMAIL VARCHAR(100),GENDER VARCHAR(6),DOB VARCAHR(10),ADDRESS VARCHAR(200),CITY VARCHAR(50),CONTACT INT(10),PASSWORD VARCHAR(20))";
        db.execSQL(table_query);


        //CityPicker
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

        ArrayAdapter adapter=new ArrayAdapter(Signup.this, android.R.layout.simple_list_item_1,arrayList);
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
                new DatePickerDialog(Signup.this,dateClick,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONDAY),calendar.get(Calendar.DAY_OF_MONTH)).show();
                return true;
            }
        });

        //button click event
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().trim().equals("")){
                    name.setError("Enter Your Name");
                }else if (email.getText().toString().trim().equals("")){
                    email.setError("Enter Email Id");
                } else if (!email.getText().toString().trim().matches(emailPattern)) {
                    email.setError("Enter Valid Email Id");
                } else if (dob.getText().toString().trim().equals("")) {
                    dob.setError("Enter date of birth");
                } else if (address.getText().toString().trim().equals("")){
                    address.setError("Enter Address");
                } else if (contact.getText().toString().trim().equals("")) {
                    contact.setError("Enter Contact Number");
                }else if (password.getText().toString().trim().equals("")){
                    password.setError("Password Required");
                }else if (!PASSWORD_PATTERN.matcher(password.getText().toString().trim()).matches()) {
                    password.setError("There Should be atleast 1 capital letter,atleast 1 symbol like (@,!,#,$,%,etc) and atleast one number and Minimum 6 Character should be there");
                } else if (c_password.getText().toString().trim().equals("")) {
                    c_password.setError("Enter Confirm Password");
                } else if (!c_password.getText().toString().trim().matches(password.getText().toString().trim())) {
                    c_password.setError("Enter password same as above");
                }else {
                    String select_query="SELECT * FROM PROFILE WHERE EMAIL='"+email.getText().toString()+"' OR CONTACT='"+contact.getText().toString()+"'";
                    Cursor cursor=db.rawQuery(select_query,null);
                    if (cursor.getCount()>0){
                        new commomMethod(Signup.this,"Email-id/Contact No Already Exists");
                    }else {
                        String insert_query = "INSERT INTO PROFILE VALUES(NULL,'" + name.getText().toString() + "','" + email.getText().toString() + "','" + sGender + "','" + dob.getText().toString() + "','" + address.getText().toString() + "','" + sCity + "','" + contact.getText().toString() + "','" + password.getText().toString() + "')";
                        db.execSQL(insert_query);
                        new commomMethod(Signup.this, "Signup Done");
                        new commomMethod(Signup.this, MainActivity.class);
//                        onBackPressed();
                    }
                }
            }
        });

    }
}