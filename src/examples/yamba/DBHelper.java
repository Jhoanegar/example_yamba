package examples.yamba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	private static final String TAG = DBHelper.class.getSimpleName();

	public DBHelper(Context context){
		super(context, StatusContract.DB_NAME, null, StatusContract.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = String.format(
				"CREATE TABLE %s("
				+ " %s INT PRIMARY KEY,"
				+ " %s TEXT,"
				+ " %s TEXT,"
				+ " %s INT);",
				StatusContract.TABLE,
					StatusContract.Column.ID,
					StatusContract.Column.USER,
					StatusContract.Column.MESSAGE,
					StatusContract.Column.CREATED_AT);
		Log.d(TAG,"onCreate with SQL: " + sql);
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + StatusContract.DB_NAME);
		onCreate(db);
	}

}
