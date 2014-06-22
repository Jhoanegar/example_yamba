package examples.yamba;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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
            	 startActivity(new Intent(this, SettingsActivity.class)); // 
            	 return true; // 
             case R.id.action_tweet:
            	 startActivity(new Intent(this, StatusActivity.class));
            	 return true;
             case R.id.action_refresh:
            	 startService(new Intent(this, RefreshService.class));
            	 return true;
             case R.id.action_purge:
            	 int rows = getContentResolver().delete(StatusContract.CONTENT_URI, null, null);
            	 Toast.makeText(this, "Deleted " + rows + pluralize("row",rows), Toast.LENGTH_LONG).show();
            	 return true;
             default:
            	 return false;
             }
     }
     private String pluralize(String str, int quantity){
    	 if(str == "row"){
    		 return quantity == 1 ? "row" : "rows";
    	 }
		 else{
    		 throw new IllegalArgumentException();
    	 }
    	 
     }
     
}
