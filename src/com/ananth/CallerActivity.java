package com.ananth;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

public class CallerActivity extends Activity {
	 private PendingIntent mAlarmSender;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		
		
		
        setContentView(R.layout.main);
         mAlarmSender = PendingIntent.getBroadcast(getApplicationContext(), 1, new Intent("com.ananth.PA_CALL"), PendingIntent.FLAG_ONE_SHOT);
		 AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 60*1000, mAlarmSender);
       
		
		
		
	}
	
	

}
