package examples.yamba;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class StatusProvider extends ContentProvider {
	private static final String TAG = StatusProvider.class.getSimpleName();
	private DBHelper dbHelper;
	
	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static{
		sURIMatcher.addURI(StatusContract.AUTHORITY, StatusContract.TABLE, StatusContract.STATUS_DIR);
		sURIMatcher.addURI(StatusContract.AUTHORITY, StatusContract.TABLE + "/#", StatusContract.STATUS_ITEM);
	}
	
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		switch(sURIMatcher.match(uri)){
			case StatusContract.STATUS_DIR:
				Log.d(TAG,"gotType: " + StatusContract.STATUS_TYPE_DIR);
				return StatusContract.STATUS_TYPE_DIR;
			case StatusContract.STATUS_ITEM:
				Log.d(TAG,"gotType: " + StatusContract.STATUS_TYPE_ITEM);
				return StatusContract.STATUS_TYPE_ITEM;
			default:
				throw new IllegalArgumentException("Illegal uri: " + uri);
		}
		
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Uri ret = null;
		validateUri(uri);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long rowID = db.insertWithOnConflict(StatusContract.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
		// If it was successful
		if (rowID != -1){
			long id = values.getAsLong(StatusContract.Column.ID);
			ret = ContentUris.withAppendedId(uri, id);
			Log.d(TAG,"inserted uri " + ret);
			// notify that the uri for this item has changed
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return ret;
		
	}
	
	private void validateUri(Uri uri){
		if (sURIMatcher.match(uri) != StatusContract.STATUS_DIR){
			throw new IllegalArgumentException("Illegal uri: " + uri);
		}
		
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3,
			String arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}

}
