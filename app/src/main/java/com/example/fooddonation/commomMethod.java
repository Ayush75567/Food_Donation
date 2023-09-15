package com.example.fooddonation;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class commomMethod {
    commomMethod(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
    commomMethod(Context context,Class nextClass){
        Intent intent=new Intent(context,nextClass);
        context.startActivity(intent);
    }
}
