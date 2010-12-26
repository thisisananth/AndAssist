package com.ananth;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SettingsArrayAdapter extends ArrayAdapter{

	private final Activity context;
	private final String[] names;
/**
 * An array adapter class which holds the text and the start or end time. 
 * @param context
 * @param names
 */
	public SettingsArrayAdapter(Activity context, String[] names) {
		super(context, R.layout.settings, names);
		this.context = context;
		this.names = names;
	}
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.settings, null, true);
		
		SharedPreferences settings=context.getSharedPreferences(SettingsActivity.PREFS_NAME, 0);
		String startTime = settings.getString("startTime", "Click to set time");
		System.out.println("Start Time is " + startTime);
		String endTime = settings.getString("endTime", "Click to set time");
		System.out.println("End Time is "+ endTime);
		TextView label = (TextView) rowView.findViewById(R.id.message);
		label.setText(names[position]);
		label.setGravity(Gravity.LEFT);
		System.out.println(names[position]);
		// Change the icon for Windows and iPhone
		if (names[position].startsWith("Start")  ) {
			TextView time = (TextView) rowView.findViewById(R.id.time);
			time.setText(startTime);
		} else {
			TextView time = (TextView) rowView.findViewById(R.id.time);
			time.setText(endTime);
		}
		
		
		
		return rowView;
	}
}
