package com.freelancer.kolby.activity;

import com.soft.android.callrecord.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class HomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_home);

	}

	public void clickMe(View v) {
		Intent i = new Intent(getApplicationContext(), MainActivity.class);
		Intent settings = new Intent(getApplicationContext(),
				SettingsActivity.class);
		switch (v.getId()) {
		case R.id.viewInc:
			i.putExtra("status", "incoming");
			startActivity(i);
			break;
		case R.id.viewOut:
			i.putExtra("status", "outgoing");
			startActivity(i);
			break;
		case R.id.viewBlock:
			// startActivity(i);
			break;
		case R.id.viewSettings:
			startActivity(settings);
			break;
		}

	}

}
