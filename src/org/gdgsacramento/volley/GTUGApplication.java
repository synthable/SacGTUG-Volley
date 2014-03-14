package org.gdgsacramento.volley;

import org.gdgsacramento.volley.api.Fake;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import android.app.Application;

public class GTUGApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		Fake.sRequestQueue = Volley.newRequestQueue(this);
		Fake.sImageLoader = new ImageLoader(Fake.sRequestQueue, new BitmapLruCache());
	}

}
