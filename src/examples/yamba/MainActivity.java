package examples.yamba;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	 @Override
     public boolean onCreateOptionsMenu(Menu menu) {
             // Inflate the menu items to the action bar.
             getMenuInflater().inflate(R.menu.main, menu);
             return true;
     }
     // Called every time user clicks on an action
     @Override
     public boolean onOptionsItemSelected(MenuItem item) { // 
             switch (item.getItemId()) { // 
             case R.id.action_settings:
            	 startActivity(new Intent(this, SettingsActivity.class)); 
            	 return true; // 
             case R.id.action_tweet:
            	 startActivity(new Intent(this, StatusActivity.class));
            	 return true;

             default:
                     return false;
             }
     }
}
