package com.ananth;





import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ShowAllTasksActivity extends ListActivity {
TasksDBAdapter mDbHelper = null;
SimpleCursorAdapter tasks =  null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.tasks_list);
	        mDbHelper = new TasksDBAdapter(this);
	        mDbHelper.open();
	        Button addTaskButton = (Button) findViewById(R.id.add_task);
	        addTaskButton.setOnClickListener(new View.OnClickListener() {

	            public void onClick(View view) {
	            	
	            	startActivityForResult(new Intent("com.ananth.GATHER_TASKS"),1);
	            	
	            	 setResult(RESULT_OK);
	            	// saveState();
	            	 
	            	
	            	//    finish();
	            	    
	            	    
	            }

	        });
	        
	        
	        
	        fillData();
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
				Intent i = new Intent(ShowAllTasksActivity.this, SettingsActivity.class);
				startActivity(i);
				// A toast is a view containing a quick little message for the user.
				Toast.makeText(ShowAllTasksActivity.this,
						"Enter you time settings",
						Toast.LENGTH_LONG).show();
				break;
				
			case R.id.deleteAll:
				mDbHelper.deleteAllTasks();
				
				fillData();
				Toast.makeText(ShowAllTasksActivity.this, "All tasks deleted", Toast.LENGTH_LONG).show();

			}
			return true;
		}
	
	 private void fillData() {
	        // Get all of the rows from the database and create the item list
	    	 Cursor mNotesCursor = mDbHelper.fetchAllTasks();
	        startManagingCursor(mNotesCursor);

	        // Create an array to specify the fields we want to display in the list (only TITLE)
	        String[] from = new String[]{TasksDBAdapter.KEY_NAME};

	        // and an array of the fields we want to bind those fields to (in this case just text1)
	        int[] to = new int[]{R.id.text1};

	        // Now create a simple cursor adapter and set it to display
	       
	          tasks =  new SimpleCursorAdapter(this, R.layout.tasks_row, mNotesCursor, from, to);
	        setListAdapter(tasks);
	    }

}
