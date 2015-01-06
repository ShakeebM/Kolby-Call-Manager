package com.freelancer.kolby.activity;

import java.io.File;
import java.util.ArrayList;
import com.soft.android.callrecord.R;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	BroadcastReceiver mReceiver;
	public static TelephonyManager telephony;
	public static int flag = 0;
	ListView listView;
	String path;
	LinearLayout optlayout;
	int positonmarker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Bundle extraBundle = getIntent().getExtras();
		String status = extraBundle.getString("status");
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		optlayout = (LinearLayout) findViewById(R.id.optlayout);
		listView = (ListView) findViewById(R.id.listView1);
		TextView heading = (TextView) findViewById(R.id.textHead);
		path = Environment.getExternalStorageDirectory().getPath() + "/.kolby/"
				+ status;
		Log.i(path, "info");
		final File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdirs();
		}

		// final File[] fileList = folder.listFiles();
		ArrayList<String> list = new ArrayList<String>();
		for (File f : folder.listFiles()) {
			String name = null;
			if (f.isFile())
				name = f.getName();
			list.add(name);
		}
		if (list.isEmpty()) {
			heading.setText("Sorry no Recorded files!!!!");
		} else {
			heading.setText("The Recorded calls are...");
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				optlayout.setVisibility(LinearLayout.VISIBLE);
				Toast.makeText(getApplicationContext(), "selected",
						Toast.LENGTH_SHORT).show();
				positonmarker = position;
				Log.i(path, parent.getItemAtPosition(position).toString());
				
			}
		});

	}

	public void optionsSelected(View v) {

		switch (v.getId()) {
		case R.id.playButton:
			try {
				String selectedFile = listView.getItemAtPosition(positonmarker)
						.toString();
				String fileloc = path + "/" + selectedFile;
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.parse("file://" + fileloc), "audio/*");
				startActivity(intent);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}
			onCreate(null);
			break;
		case R.id.shareButton:
			String selectedFile = listView.getItemAtPosition(positonmarker)
					.toString();
			String fileloc = path + "/" + selectedFile;
			Uri audioUri = Uri.parse("file://" + fileloc);
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("audio/aac");
			intent.putExtra(Intent.EXTRA_STREAM, audioUri);
			startActivity(Intent.createChooser(intent, "Share"));
			onCreate(null);
			break;
		case R.id.deleteButton:
			String filefordelete = listView.getItemAtPosition(positonmarker)
					.toString();
			String filelocation = path + "/" + filefordelete;
			AlertDialog diaBox = AskOption(filelocation);
			diaBox.show();

			break;
		}
	}

	private AlertDialog AskOption(final String filelocation) {
		AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
				// set message, title, and icon
				.setTitle("Delete")
				.setMessage("Do you want to Delete")
				.setPositiveButton("Delete",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int whichButton) {
								File f = new File(filelocation);
								f.delete();
								dialog.dismiss();
								onCreate(null);
							}

						})
				.setNegativeButton("cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								dialog.dismiss();

							}
						}).create();
		return myQuittingDialogBox;
	}

}
