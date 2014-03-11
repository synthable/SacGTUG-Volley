package org.gdgsacramento.volley;

import org.gdgsacramento.volley.ui.ListWithImagesFragment;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getFragmentManager()
			.beginTransaction()
			.replace(R.id.fragment_container, ListWithImagesFragment.newInstance())
			.commit();
	}
}
