package examples.yamba;

import java.util.List;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClient.Status;
import com.marakana.android.yamba.clientlib.YambaClientException;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class RefreshService extends IntentService {
	static final String TAG = "RefreshService";

	public RefreshService() {
		super(TAG);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");
	}

	@Override
	public void onHandleIntent(Intent intent) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		final String username = prefs.getString("username", "");
		final String password = prefs.getString("password", "");
		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
			Toast.makeText(this, "Please update your username and password",
					Toast.LENGTH_LONG).show();
			return;
		}
		Log.d(TAG, "onStart");

		ContentValues values = new ContentValues();
		YambaClient cloud = new YambaClient(username, password);
		try {
			int count = 0;
			List<Status> timeline = cloud.getTimeline(20);
			for (Status status : timeline) {
				values.clear();
				values.put(StatusContract.Column.ID, status.getId());
				values.put(StatusContract.Column.USER, status.getUser());
				values.put(StatusContract.Column.MESSAGE, status.getMessage());
				values.put(StatusContract.Column.CREATED_AT, status
						.getCreatedAt().getTime());

				Uri uri = getContentResolver().insert(
						StatusContract.CONTENT_URI, values);
				if (uri != null) {
					count++;
					Log.d(TAG,
							String.format("%s: %s", status.getUser(),
									status.getMessage()));
				}


			}
			if (count > 0) {
				sendBroadcast(new Intent(
						"examples.yamba.action.NEW_STATUSES").putExtra(
						"count", count));
			}
		} catch (YambaClientException e) {
			Log.e(TAG, "Failed to fetch the timeline");
			e.printStackTrace();
		}

		return;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
