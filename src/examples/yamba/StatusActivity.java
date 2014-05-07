package examples.yamba;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.*;

public class StatusActivity extends Activity implements OnClickListener {
	private final static String TAG = "StatusActivity";
	private EditText editStatus;
	private Button buttonTweet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status);
		
		editStatus = (EditText) findViewById(R.id.editStatus);
		buttonTweet = (Button) findViewById(R.id.buttonTweet);
		buttonTweet.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		String status = editStatus.getText().toString();
		Log.d(TAG,"onClick with status: " + status);
		
		new PostTask().execute(status);
	}
	
	private final class PostTask extends AsyncTask<String, Void, String> {

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
			Toast.makeText(StatusActivity.this, result, Toast.LENGTH_LONG).show();
		}
	
	}
}
