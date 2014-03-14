package org.gdgsacramento.volley.api;

import org.gdgsacramento.volley.User;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

public class Fake {

	public static RequestQueue sRequestQueue;
	public static ImageLoader sImageLoader;

	public static interface ApiListener<T> {
		public abstract void onResponse(T t, VolleyError error);
	}

	public static void getUsers(ApiListener<User[]> listener) {
		sRequestQueue.add(new GetUsersRequest(listener));
	}

	public static void getUserDetails(int id, ApiListener<User> listener) {
		sRequestQueue.add(new GetUserDetailsRequest(id, listener));
	}
}
