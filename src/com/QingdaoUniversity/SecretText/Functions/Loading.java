package com.QingdaoUniversity.SecretText.Functions;

import java.util.Timer;
import java.util.TimerTask;

import com.example.uuu.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;

public class Loading extends Activity {
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.loading);
		new Timer().schedule(new TimerTask() {  
        @Override  
        public void run() {  
        	boolean isChecked;
        	SharedPreferences sp = getSharedPreferences("SettingsConfig", MODE_PRIVATE);
        	isChecked = sp.getBoolean("isChecked1", true);
        	if(isChecked){
        		startActivity(new Intent(Loading.this, MainActivity.class));  
        	}
        	else{
        		startActivity(new Intent(Loading.this, Login.class));  
        	}
            finish();  
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);  
        }  
    }, 2000);  
	}
}