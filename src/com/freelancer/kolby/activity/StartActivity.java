package com.freelancer.kolby.activity;

import com.soft.android.callrecord.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class StartActivity extends Activity {

	private static long SLEEP_TIME = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // Removes title bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.flashactivity);
		IntentLauncher launcher = new IntentLauncher();
		launcher.start();
	}
	private class IntentLauncher extends Thread {
		@Override
		/**
		 * Sleep for some time and than start new activity.
		 */
		public void run() {
			try {
				// Sleeping
				Thread.sleep(SLEEP_TIME * 1000);
			} catch (Exception e) {
			}
			// Start main activity
			Intent intent = new Intent(StartActivity.this,
					HomeActivity.class);
			StartActivity.this.startActivity(intent);
			StartActivity.this.finish();
		}
	}
}
