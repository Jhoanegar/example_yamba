package examples.yamba;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class PostTask extends AsyncTask<String, Void, String> {
	private Context callerContext;
	public PostTask(Context context) {
		super();
		callerContext = context;
	}


	@Override
	protected String doInBackground(String... params) {
		
		try{
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(callerContext);
			String username = prefs.getString("username","");
			String password = prefs.getString("password","");
			if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
				callerContext.startActivity(new Intent(callerContext,SettingsActivity.class));
				return "Please update your username and password";
			}
			
			YambaClient yambaCloud = new YambaClient(username,password);
			yambaCloud.postStatus(params[0]);
			return "Successfully posted";
			
		} catch(YambaClientException e){
			e.printStackTrace();
			return "Failed to post to yamba service";
		}
	}
	
	@Override 
	protected void onPostExecute(String result){
		super.onPostExecute(result);
		Toast.makeText(callerContext, result, Toast.LENGTH_LONG).show();
	}
		

}
