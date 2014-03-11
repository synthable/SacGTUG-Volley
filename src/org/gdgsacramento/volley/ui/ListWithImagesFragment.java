package org.gdgsacramento.volley.ui;

import org.gdgsacramento.volley.Fake;
import org.gdgsacramento.volley.R;
import org.gdgsacramento.volley.User;
import org.gdgsacramento.volley.Fake.ApiListener;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;

import android.app.ListFragment;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class ListWithImagesFragment extends ListFragment {

	private UsersListAdapter mListAdapter;

	public static ListWithImagesFragment newInstance() {
		return new ListWithImagesFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		PullToRefreshListView list = new PullToRefreshListView(getActivity());
		mListAdapter = new UsersListAdapter(getActivity(), null);
		setListAdapter(mListAdapter);

		Fake.getUsers(new ApiListener<User[]>() {
			@Override
			public void onResponse(User[] users, VolleyError error) {
				/**
				 * Display an error if available.  Otherwise create a cursor filled
				 * with our API results to display in the ListView
				 */
				if(error != null) {
					Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
				} else {
					MatrixCursor cursor = new MatrixCursor(new String[] {
						"_id", "name", "age", "picture"
					});
					for(User user : users) {
						cursor.addRow(new String[] {
							null,
							user.getName(), String.valueOf(user.getAge()), user.getPicture()
						});
					}
					mListAdapter.swapCursor(cursor);
				}
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//return super.onCreateView(inflater, container, savedInstanceState);
		return new PullToRefreshListView(getActivity());
	}

	@Override
	public void onResume() {
		super.onResume();
	}
	
	private static class PullToRefreshListView extends ListView {
		public PullToRefreshListView(Context context) {
			super(context);
			setOverScrollMode(OVER_SCROLL_ALWAYS);
		}

		@Override
		protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
				int scrollY, int scrollRangeX, int scrollRangeY,
				int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
			return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
					scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
		}

		@Override
		protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
			super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);

			Log.v("scroll", "scrollX:" + scrollX + " scrollY:" + scrollY
                    + " clampedX:" + clampedX + " clampedY:" + clampedX);
		}
	}


	private static class UsersListAdapter extends SimpleCursorAdapter {

		private static final String[] FROM = new String[] {
			"name", "age"
		};

		private static final int[] TO = new int[] {
			R.id.list_item_name, R.id.list_item_age
		};

		public UsersListAdapter(Context context, Cursor c) {
			super(context, R.layout.users_list_item, c, FROM, TO, 0);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			super.bindView(view, context, cursor);

			String url = cursor.getString(cursor.getColumnIndex("picture"));
			NetworkImageView pictureView = (NetworkImageView) view.findViewById(R.id.list_item_image);
			pictureView.setImageUrl(url, Fake.sImageLoader);
		}
	}
}
