package com.ananth;

import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class TaskGatherActivity extends Activity  {
	private PendingIntent mAlarmSender;
	 private EditText mTaskText;
	   
	    private Long mRowId;
	    private TasksDBAdapter mDbHelper;
	    private TextView mTimeDisplay;
	    private Button mPickTime;

	    private int mHour;
	    private int mMinute;

	    static final int TIME_DIALOG_ID = 0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		 setContentView(R.layout.addtask);
		 
		 
		  mDbHelper = new TasksDBAdapter(this);

	        mDbHelper.open();
	     // capture our View elements
	        mTaskText = (EditText) findViewById(R.id.task);
	        mTimeDisplay = (TextView) findViewById(R.id.timeDisplay);
	        mPickTime = (Button) findViewById(R.id.pickTime);

	        // add a click listener to the button
	        mPickTime.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	                showDialog(TIME_DIALOG_ID);
	            }
	        });

	        // get the current time
	        final Calendar c = Calendar.getInstance();
	        mHour = c.get(Calendar.HOUR_OF_DAY);
	        mMinute = c.get(Calendar.MINUTE);

	        // display the current date
	        updateDisplay();
	        
	        mRowId = (savedInstanceState == null) ? null :
	            (Long) savedInstanceState.getSerializable(TasksDBAdapter.KEY_ROWID);
	        if (mRowId == null) {
	            Bundle extras = getIntent().getExtras();
	            mRowId = extras != null ? extras.getLong(TasksDBAdapter.KEY_ROWID)
	                                    : null;
	        }
	        Button confirmButton = (Button) findViewById(R.id.save);
	        confirmButton.setOnClickListener(new View.OnClickListener() {

	            public void onClick(View view) {
	            	
	            	
	            	
	            	 setResult(RESULT_OK);
	            	 saveState();
	            	    finish();
	            	    
	            	    
	            }

	        });
	        
		
	}
	
	// updates the time we display in the TextView
	private void updateDisplay() {
	    mTimeDisplay.setText(
	        new StringBuilder()
	                .append(pad(mHour)).append(":")
	                .append(pad(mMinute)));
	}

	private static String pad(int c) {
	    if (c >= 10)
	        return String.valueOf(c);
	    else
	        return "0" + String.valueOf(c);
	}
	
	// the callback received when the user "sets" the time in the dialog
	private TimePickerDialog.OnTimeSetListener mTimeSetListener =
	    new TimePickerDialog.OnTimeSetListener() {
	        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	            mHour = hourOfDay;
	            mMinute = minute;
	            updateDisplay();
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
	    
	    
	    @Override
	    protected void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	       // saveState();
	        outState.putSerializable(TasksDBAdapter.KEY_ROWID, mRowId);
	    }
	    @Override
	    protected void onPause() {
	        super.onPause();
	       // saveState();
	    }
	    
	    @Override
	    protected void onResume() {
	        super.onResume();
	        populateFields();
	    }
	    
	    private void saveState() {
	        String title = mTaskText.getText().toString();
	        String body = mTimeDisplay.getText().toString();

	        if (mRowId == null) {
	            long id = mDbHelper.createTask(title, body);
	            if (id > 0) {
	                mRowId = id;
	            }
	        } else {
	           // mDbHelper.updateTask(mRowId, title, body);
	        }
	        
	        mAlarmSender = PendingIntent.getBroadcast(getApplicationContext(), 1, new Intent("com.ananth.PA_CALL"), PendingIntent.FLAG_ONE_SHOT);
			 AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
			 Calendar cal = Calendar.getInstance(Locale.US);
			 
			 Log.i("Time before setting", cal.getTime().toString());
			 
			 
			 if(cal.get(Calendar.AM_PM)==1)
			 cal.set(Calendar.HOUR, mHour-12);
			 cal.set(Calendar.MINUTE, mMinute);
			 cal.set(Calendar.SECOND, 0);
			 //cal.set(Calendar.AM_PM, Calendar.PM);
			 
			 
			 
			 
			 Log.i("Cal",cal.getTime().toString());
	        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), mAlarmSender);
	        
	        Log.d("came", "here");
	        
	    }
	    
	    private void populateFields() {
	        if (mRowId != null) {
	            Cursor task = mDbHelper.fetchTask(mRowId);
	            startManagingCursor(task);
	            mTaskText.setText(task.getString(
	                        task.getColumnIndexOrThrow(TasksDBAdapter.KEY_NAME)));
	            mTimeDisplay.setText(task.getString(
	                    task.getColumnIndexOrThrow(TasksDBAdapter.KEY_TIME)));
	        }
	    }
}
