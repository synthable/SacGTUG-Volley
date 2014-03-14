package org.gdgsacramento.volley.ui;

import org.gdgsacramento.volley.R;
import org.gdgsacramento.volley.User;
import org.gdgsacramento.volley.api.Fake;
import org.gdgsacramento.volley.api.Fake.ApiListener;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;

public class UserDetailsFragment extends Fragment {

	private NetworkImageView mImage;
	private TextView mName;
	private TextView mAge;
	private TextView mDetails;

	public static UserDetailsFragment newInstance(int id) {
		UserDetailsFragment f = new UserDetailsFragment();
		Bundle args = new Bundle();
		args.putInt("id", id);
		f.setArguments(args);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.user_details, container, false);

		mImage = (NetworkImageView) view.findViewById(R.id.image);
		mName = (TextView) view.findViewById(R.id.name);
		mAge = (TextView) view.findViewById(R.id.age);
		mDetails = (TextView) view.findViewById(R.id.details);

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();

		final int id = getArguments().getInt("id");
		Fake.getUserDetails(id, new ApiListener<User>() {
			@Override
			public void onResponse(User user, VolleyError error) {
				if(error != null) {
					Toast.makeText(getActivity(), "Ut-oh error!", Toast.LENGTH_SHORT).show();
					getActivity().getFragmentManager().popBackStack();
				} else {
					mImage.setImageUrl(user.getPicture(), Fake.sImageLoader);
					mName.setText(user.getName());
					mAge.setText(String.valueOf(user.getAge()));
					mDetails.setText(user.getDetails());
				}
			}
		});
	}
}
