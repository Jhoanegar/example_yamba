package examples.yamba;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class PostTask extends AsyncTask<String, Void, String> {
	private Context callerContext;
	public PostTask(Context context) {
		super();
		callerContext = context;
	}


	@Override
	protected String doInBackground(String... params) {
		YambaClient yambaCloud = new YambaClient("student","password");
		try{
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
