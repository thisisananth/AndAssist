package com.ananth;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SettingsArrayAdapter extends ArrayAdapter{

	private final Activity context;
	private final String[] names;

	public SettingsArrayAdapter(Activity context, String[] names) {
		super(context, R.layout.settings, names);
		this.context = context;
		this.names = names;
	}
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.settings, null, true);
		
		
		
		TextView label = (TextView) rowView.findViewById(R.id.message);
		label.setText(names[position]);
		label.setGravity(Gravity.LEFT);
		System.out.println(names[position]);
		// Change the icon for Windows and iPhone
		if (names[position].startsWith("Start")  ) {
			TextView time = (TextView) rowView.findViewById(R.id.time);
			time.setText("6:30 AM");
		} else {
			TextView time = (TextView) rowView.findViewById(R.id.time);
			time.setText("10:30 PM");
		}
		
		
		
		return rowView;
	}
}
