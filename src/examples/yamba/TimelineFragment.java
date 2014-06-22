package examples.yamba;

import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;

public class TimelineFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {
	private static final String TAG = TimelineFragment.class.getSimpleName();
	private static final String[] FROM = { StatusContract.Column.USER,
			StatusContract.Column.MESSAGE, StatusContract.Column.CREATED_AT };
	// StatusContract.Column.CREATED_AT };
	private static final int[] TO = { R.id.list_item_text_user,
			R.id.list_item_text_message, R.id.list_item_text_created_at };
	// R.id.list_item_freshness };
	private static final int LOADER_ID = 42;

	private SimpleCursorAdapter myAdapter;

	@SuppressWarnings("deprecation")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		myAdapter = new SimpleCursorAdapter(getActivity(), R.layout.list_item,
				null, FROM, TO);
		setListAdapter(myAdapter);
		
		getLoaderManager().initLoader(LOADER_ID, null, this);
	}

	// -- Loader callback --

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		if (id != LOADER_ID) {
			return null;
		}
		Log.d(TAG, "onCreateLoader");
		return new CursorLoader(getActivity(), StatusContract.CONTENT_URI,
				null, null, null, StatusContract.DEFAULT_SORT);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		Log.d(TAG, "onLoad finished with cursor " + cursor.getCount());
		myAdapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> cursor) {
		myAdapter.swapCursor(null);

	}
}
