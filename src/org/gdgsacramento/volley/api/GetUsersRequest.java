package org.gdgsacramento.volley.api;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.gdgsacramento.volley.User;
import org.gdgsacramento.volley.api.Fake.ApiListener;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

public class GetUsersRequest extends GsonRequest<ApiResponse<User[]>> {

	private static final String URL = "http://jason.ezek.org/gtug/users.json";
	private static final Type clazz = new TypeToken<ApiResponse<User[]>>(){}.getType();

	public GetUsersRequest(final ApiListener<User[]> listener) {
		super(URL, clazz, null, new Response.Listener<ApiResponse<User[]>>() {
			@Override
			public void onResponse(ApiResponse<User[]> response) {
				if(listener != null) listener.onResponse(response.getData(), null);
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				if(listener != null) listener.onResponse(null, error);
			}
		});
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		HashMap<String, String> h = new HashMap<String, String>();
		h.put("xxx", "aaa");
		return h;
	}

	@Override
	protected Response<ApiResponse<User[]>> parseNetworkResponse(NetworkResponse response) {
		switch(response.statusCode) {
			default:
			case 200:
				return super.parseNetworkResponse(response);
		}
	}
}
