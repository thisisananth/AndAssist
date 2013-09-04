package com.ananth;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Shows all the activities and has menus to set Settings and Ringtones. 
 * @author ananthmajumdar
 *
 */
public class ShowAllTasksActivity extends ListActivity {
	TasksDBAdapter mDbHelper = null;
	SimpleCursorAdapter tasks = null;
	
	String test="Ananth";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		SharedPreferences settings = getSharedPreferences(
				SettingsActivity.PREFS_NAME, 0);
		String startTime = settings.getString("startTime", "");
		System.out.println("Start Time is " + startTime);
		String endTime = settings.getString("endTime", "");

		if (startTime.equals("") || endTime.equals("")) {

			Intent i = new Intent(ShowAllTasksActivity.this,
					SettingsActivity.class);
			startActivity(i);

		}

		setContentView(R.layout.tasks_list);
		mDbHelper = new TasksDBAdapter(this);
		mDbHelper.open();
		Button addTaskButton = (Button) findViewById(R.id.add_task);
		addTaskButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {

				startActivityForResult(new Intent("com.ananth.GATHER_TASKS"), 1);

				setResult(RESULT_OK);
				// saveState();

				// finish();

			}

		});

		fillData();
		mDbHelper.close();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.settingsmenu, menu);
		return true;
	}

	// This method is called once the menu is selected
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		// We have only one menu option
		case R.id.settings:
			// Launch Preference activity
			Intent i = new Intent(ShowAllTasksActivity.this,
					SettingsActivity.class);
			startActivity(i);
			// A toast is a view containing a quick little message for the user.
			Toast.makeText(ShowAllTasksActivity.this,
					"Enter you time settings", Toast.LENGTH_LONG).show();
			break;

		case R.id.deleteAll:
			mDbHelper = new TasksDBAdapter(this);
			mDbHelper.open();

			mDbHelper.deleteAllTasks();

			fillData();
			mDbHelper.close();
			Toast.makeText(ShowAllTasksActivity.this, "All tasks deleted",
					Toast.LENGTH_LONG).show();
			break;
		case R.id.ringtone:
			Intent i1 = new Intent(ShowAllTasksActivity.this, Preferences.class);
			startActivity(i1);

		}
		return true;
	}

	private void fillData() {

		// Get all of the rows from the database and create the item list
		Cursor mNotesCursor = mDbHelper.fetchAllTasks();
		startManagingCursor(mNotesCursor);

		// Create an array to specify the fields we want to display in the list
		// (only TITLE)
		String[] from = new String[] { TasksDBAdapter.KEY_NAME };

		// and an array of the fields we want to bind those fields to (in this
		// case just text1)
		int[] to = new int[] { R.id.text1 };

		// Now create a simple cursor adapter and set it to display

		tasks = new SimpleCursorAdapter(this, R.layout.tasks_row, mNotesCursor,
				from, to);
		setListAdapter(tasks);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mDbHelper.close();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {
			mDbHelper = new TasksDBAdapter(this);
			mDbHelper.open();
			fillData();
			mDbHelper.close();

		}

	}

}
