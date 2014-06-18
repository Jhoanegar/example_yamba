package examples.yamba;

import java.util.List;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClient.Status;
import com.marakana.android.yamba.clientlib.YambaClientException;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class RefreshService extends IntentService {
	static final String TAG = "RefreshService";
	
	public RefreshService(){
		super(TAG);
	}
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG,"onCreate");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG,"onDestroy");
	}

	@Override
	public void onHandleIntent(Intent intent) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		final String username = prefs.getString("username","");
		final String password = prefs.getString("password","");
		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
			Toast.makeText(this, "Update your username and password", Toast.LENGTH_LONG).show();
		}
		Log.d(TAG,"onStart");
		
		YambaClient cloud = new YambaClient(username,password);
		try{
			List<Status> timeline = cloud.getTimeline(20);
			for (Status status : timeline){
				Log.d(TAG,String.format("%s: %s", status.getUser(),status.getMessage()));
			}
		} catch (YambaClientException e){
			Log.e(TAG, "Failed to fetch timeline",e);
			e.printStackTrace();
		}
		return;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}


}
