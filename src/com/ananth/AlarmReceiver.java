package com.ananth;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class AlarmReceiver extends BroadcastReceiver {
	String ns = Context.NOTIFICATION_SERVICE;
	private static final int HELLO_ID = 1;
	@Override
	public void onReceive(Context context, Intent arg1) {
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);
		int icon = R.drawable.icon;
		CharSequence tickerText = "Hello";
		CharSequence contentTitle = "My notification";  // expanded message title
		CharSequence contentText = "Hello";      // expanded message text

		Intent notificationIntent = new Intent("com.ananth.VIEW_TASKS");
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

		long when = System.currentTimeMillis();
	
		Notification notification = new Notification(icon, tickerText, when);
		//notification.sound = Uri.parse("file:///sdcard/vedam.mp3");
		notification.defaults |= Notification.DEFAULT_SOUND;
		//notification.defaults |= Notification.DEFAULT_SOUND;
		//mNotificationManager.notify(1, notification);
		notification.flags |= Notification.FLAG_INSISTENT;
		notification.flags |=  Notification.FLAG_AUTO_CANCEL ;
		
		/*notification.ledARGB = 0xff00ff00;
		notification.ledOnMS = 300;
		notification.ledOffMS = 1000;
		notification.flags |= Notification.FLAG_SHOW_LIGHTS;*/
		
	
		
		
		// the next two lines initialize the Notification, using the configurations above
	//	Notification notification = new Notification(icon, tickerText, when);
		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		

		mNotificationManager.notify(HELLO_ID, notification);
		
	}

}
