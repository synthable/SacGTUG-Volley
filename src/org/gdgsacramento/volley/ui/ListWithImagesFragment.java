package org.gdgsacramento.volley.ui;

import org.gdgsacramento.volley.api.Fake;
import org.gdgsacramento.volley.api.Fake.ApiListener;
import org.gdgsacramento.volley.R;
import org.gdgsacramento.volley.User;

import android.app.ListFragment;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;

public class ListWithImagesFragment extends ListFragment implements
	OnItemClickListener {

	private UsersListAdapter mListAdapter;
	private MatrixCursor mCursor;

	public static ListWithImagesFragment newInstance() {
		return new ListWithImagesFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

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
					mCursor = new MatrixCursor(new String[] {
						"_id", "userId", "name", "age", "picture"
					});
					for(User user : users) {
						mCursor.addRow(new String[] {
							null,
							String.valueOf(user.getId()),
							user.getName(),
							String.valueOf(user.getAge()),
							user.getPicture()
						});
					}
					mListAdapter.swapCursor(mCursor);
				}
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();

		getListView().setOnItemClickListener(this);
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

	@Override
	public void onItemClick(AdapterView<?> list, View view, int position, long id) {
		mCursor.moveToPosition(position);
		int userId = mCursor.getInt(mCursor.getColumnIndex("userId"));

		getFragmentManager()
			.beginTransaction()
			.replace(R.id.fragment_container, UserDetailsFragment.newInstance(userId))
			.addToBackStack(null)
			.commit();
	}
}
