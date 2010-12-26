package com.ananth;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;


/**
 * Recieves the alarm broadcast and sends a long ring notification to the user. 
 * @author ananthmajumdar
 *
 */
public class AlarmReceiver extends BroadcastReceiver {
	String ns = Context.NOTIFICATION_SERVICE;
	private static final int HELLO_ID = 1;

	@Override
	public void onReceive(Context context, Intent arg1) {
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(ns);
		int icon = R.drawable.icon;
		CharSequence tickerText = "Hello";
		CharSequence contentTitle = "My notification"; // expanded message title
		CharSequence contentText = "Hello"; // expanded message text

		Intent notificationIntent = new Intent("com.ananth.VIEW_TASKS");
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);

		long when = System.currentTimeMillis();

		Notification notification = new Notification(icon, tickerText, when);

		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);

		String rtURI = settings.getString("ringtone", "sometest");

		Log.i("RingtoneURI::", rtURI);
		if (rtURI.equals("")) {
			notification.defaults |= Notification.DEFAULT_SOUND;
		} else {
			notification.sound = Uri.parse(rtURI);
		}

		// notification.defaults |= Notification.DEFAULT_SOUND;

		notification.flags |= Notification.FLAG_INSISTENT;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		notification.setLatestEventInfo(context, contentTitle, contentText,
				contentIntent);

		mNotificationManager.notify(HELLO_ID, notification);

	}

}
