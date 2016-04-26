package com.example.uuu;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
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
            startActivity(new Intent(Loading.this, Login.class));  
            finish();  
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);  
        }  
    }, 2000);  
	}
}