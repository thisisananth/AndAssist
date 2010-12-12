package com.ananth;

import java.util.Calendar;
import java.util.Locale;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

public class SettingsActivity extends ListActivity {
	public static final String PREFS_NAME = "MyPrefsFile";
	static final int TIME_DIALOG_ID = 0;
	int mHour=0;
    int mMinute=0;
    TextView editedTime;
    int startEndIndicator;
    public static final int START_TIME=0;
    public static final int END_TIME=1;
    private PendingIntent mAlarmSender;
    
    
	/** Called when the activity is first created. */
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		// Create an array of Strings, that will be put to our ListActivity
		String[] names = new String[] { "Start Time", "End Time" };
		// Use your own layout and point the adapter to the UI elements which contains the label
		this.setListAdapter(new SettingsArrayAdapter(this, names));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		// Get the item that was clicked
		Object o = this.getListAdapter().getItem(position);
		 

	     
	  
		
		editedTime = (TextView)v.findViewById(R.id.time);
		
		String time = editedTime.getText().toString();
		
		String[] hours=time.split(":");
		mHour = Integer.parseInt(hours[0]);
		String[] mins = hours[1].split(" ");
		mMinute = Integer.parseInt(mins[0]);
		
		System.out.println(mHour + "" + mMinute);
		
		startEndIndicator=position;
		showDialog(TIME_DIALOG_ID);
		
	}
	
	// the callback received when the user "sets" the time in the dialog
	private TimePickerDialog.OnTimeSetListener mTimeSetListener =
	    new TimePickerDialog.OnTimeSetListener() {
	        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	            mHour = hourOfDay;
	            mMinute = minute;
	            
	            
	            updateDisplayAndScheduleAlarms();
	        }
	    };
	
	    @Override
	    protected Dialog onCreateDialog(int id) {
	        switch (id) {
	        case TIME_DIALOG_ID:
	            return new TimePickerDialog(this,
	                    mTimeSetListener, mHour, mMinute, false);
	        }
	        return null;
	    }
	    
	    public void updateDisplayAndScheduleAlarms(){
	    	editedTime.setText(
	        new StringBuilder()
	                .append(pad (checkPM(mHour)?mHour-12:mHour)).append(":")
	                .append(pad(mMinute)).append(checkPM(mHour)?" PM":" AM"));
	    	
	    	
	    	
	    	if(startEndIndicator==START_TIME){
	    	 mAlarmSender = PendingIntent.getBroadcast(getApplicationContext(), 1, new Intent("com.ananth.PA_START_CALL"), PendingIntent.FLAG_ONE_SHOT);
			 AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
			 Calendar cal = Calendar.getInstance(Locale.US);
			 am.cancel(mAlarmSender);
			 Log.i("Time before setting", cal.getTime().toString());
			 
			 
			 if(cal.get(Calendar.AM_PM)==1)
			 cal.set(Calendar.HOUR, mHour-12);
			 cal.set(Calendar.MINUTE, mMinute);
			 cal.set(Calendar.SECOND, 0);
			  Log.i("Cal",cal.getTime().toString());
	        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), mAlarmSender);
	        
	        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	        SharedPreferences.Editor editor = settings.edit();
	        editor.putString("startTime", editedTime.getText().toString());
	        
	        
	    	}else{
	    		 mAlarmSender = PendingIntent.getBroadcast(getApplicationContext(), 1, new Intent("com.ananth.PA_START_CALL"), PendingIntent.FLAG_ONE_SHOT);
				 AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
				 Calendar cal = Calendar.getInstance(Locale.US);
				 am.cancel(mAlarmSender);
				 Log.i("Time before setting", cal.getTime().toString());
				 
				 
				 if(cal.get(Calendar.AM_PM)==1)
				 cal.set(Calendar.HOUR, mHour-12);
				 cal.set(Calendar.MINUTE, mMinute);
				 cal.set(Calendar.SECOND, 0);
				  Log.i("Cal",cal.getTime().toString());
		        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), mAlarmSender);
		        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		        SharedPreferences.Editor editor = settings.edit();
		        editor.putString("startTime", editedTime.getText().toString());
	    	}
	    	
	    	
	    	
	    }
	    private static String pad(int c) {
		    if (c >= 10)
		        return String.valueOf(c);
		    else
		        return "0" + String.valueOf(c);
		}
	    
	    public boolean checkPM(int i){
	    	 boolean isPM=false;
	    	if(mHour>=12)
	    	isPM=true;
	    	return isPM;
	    }
}
