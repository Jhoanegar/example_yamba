package examples.yamba;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class AlarmCreator {
	private static final String TAG = AlarmCreator.class.getSimpleName();
	private static final long DEFAULT_INTERVAL = AlarmManager.INTERVAL_FIFTEEN_MINUTES;

	public static void setAlarm(Context context, Intent intent) {
		context.startService(new Intent(context, RefreshService.class));
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		long interval = Long.parseLong(prefs.getString("interval",
				Long.toString(DEFAULT_INTERVAL)));
		PendingIntent operation = PendingIntent.getService(context, -1,
				new Intent(context, RefreshService.class),
				PendingIntent.FLAG_UPDATE_CURRENT);
		Log.d(TAG, "onReceived");

		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		if (interval == 0) {
			alarmManager.cancel(operation);
			Log.d(TAG, "cancelling repeat operation");
		} else {
			alarmManager.setInexactRepeating(AlarmManager.RTC,
					System.currentTimeMillis(), interval, operation);
			Log.d(TAG, "setting repeat operation for: " + interval);
		}
	}
}
