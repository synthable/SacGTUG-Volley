package org.gdgsacramento.volley.request;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.gdgsacramento.volley.ApiResponse;
import org.gdgsacramento.volley.Fake.ApiListener;
import org.gdgsacramento.volley.User;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GetUsersRequest extends JsonObjectRequest {

	private static final String URL = "http://jason.ezek.org/gtug/users.json";
	private static final Type clazz = new TypeToken<ApiResponse<User[]>>(){}.getType();

	public GetUsersRequest(final ApiListener<User[]> listener) {
		super(Method.GET, URL, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				ApiResponse<User[]> users = new Gson().fromJson(response.toString(), clazz);
				if(listener != null) listener.onResponse(users.getData(), null);
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				if(listener != null) listener.onResponse(null, error);
			}
		});
	}

	@Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
		return super.parseNetworkResponse(response);
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		HashMap<String, String> h = new HashMap<String, String>();
		return h;
	}
}
