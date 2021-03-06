package examples.yamba;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.provider.Settings;

public class NotificationReceiver extends BroadcastReceiver {
	public static final int NOTIFICATION_ID = 42;

	@Override
	public void onReceive(Context context, Intent intent) {
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		int count = intent.getIntExtra("count", 0);
		PendingIntent operation = PendingIntent.getActivity(context, -1,
				new Intent(context, YambaApplication.class),
				PendingIntent.FLAG_ONE_SHOT);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		@SuppressWarnings("deprecation")
		Notification notification = new Notification.Builder(context)
				.setContentTitle("New teeets!")
				.setContentText("You've got " + count + " new tweets " + prefs.getString("interval", "none"))
				.setSmallIcon(android.R.drawable.sym_action_email)
				.setContentIntent(operation).setAutoCancel(true)
				.setSound(alarmSound)
				.getNotification();
		notificationManager.notify(NOTIFICATION_ID,notification);
	}

}
