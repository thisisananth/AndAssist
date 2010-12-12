package com.ananth;




import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class TasksDBAdapter{
	
	 public static final String KEY_NAME = "name";
	 public static final String KEY_TIME = "time";
	 public static final String KEY_ROWID = "_id";
	private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE = "tasks";
    private static final int DATABASE_VERSION = 2;
    private static final String TAG = "NotesDbAdapter";
    private TaskDBHelper mDbHelper;
    private SQLiteDatabase mDb;
    private final Context mCtx;
	
    public TasksDBAdapter(Context mctx) {
		mCtx=mctx;
	}

	private static final String DATABASE_CREATE =
        "create table tasks (_id integer primary key autoincrement, "
        + "name text not null, time text not null);";

private static class TaskDBHelper extends SQLiteOpenHelper {


	public TaskDBHelper(Context context) {
		super(context,DATABASE_NAME,null, DATABASE_VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i("Creating database", "creating db data");
		 db.execSQL(DATABASE_CREATE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		 Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                 + newVersion + ", which will destroy all old data");
         db.execSQL("DROP TABLE IF EXISTS notes");
         onCreate(db);
		
	}
	
	

}

public TasksDBAdapter open() throws SQLException {
    mDbHelper = new TaskDBHelper(mCtx);
    mDb = mDbHelper.getWritableDatabase();
    return this;
}

public void close() {
    mDbHelper.close();
}


public long createTask(String title, String body) {
    ContentValues initialValues = new ContentValues();
    initialValues.put(KEY_NAME, title);
    initialValues.put(KEY_TIME, body);

    long rowId= mDb.insert(DATABASE_TABLE, null, initialValues);
    Log.i("ID returned is", String.valueOf(rowId));
    return rowId;
    
    
    
}


public boolean deleteTask(long rowId) {

    return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
}

public boolean deleteAllTasks(){
	return mDb.delete(DATABASE_TABLE, KEY_ROWID + ">"  + 0,null )> 0;
}

public Cursor fetchAllTasks() {

    return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME,
            KEY_TIME}, null, null, null, null, null);
}


public Cursor fetchTask(long rowId) throws SQLException {

    Cursor mCursor =

        mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
        		KEY_NAME, KEY_TIME}, KEY_ROWID + "=" + rowId, null,
                null, null, null, null);
    if (mCursor != null) {
        mCursor.moveToFirst();
    }
    return mCursor;

}





}
