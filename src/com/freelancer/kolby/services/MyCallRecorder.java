package com.freelancer.kolby.services;

import java.io.File;
import java.io.IOException;

import com.freelancer.kolby.activity.MainActivity;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

public class MyCallRecorder {

	private MediaRecorder recorder;

	public void recordCall() {
		if (MainActivity.flag == 0) {
			Log.i("RECORDING", "STARTED");
			recorder = new MediaRecorder();
			recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
			recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
			recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

			recorder.setOutputFile(getFilename());

			recorder.setOnErrorListener(errorListener);
			recorder.setOnInfoListener(infoListener);
			try {
				recorder.prepare();
				recorder.start();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			MainActivity.flag = 1;
		}

		Log.i("DEBUG", "OFFHOOK");

	}

	public void stopRecord() {
		if (recorder != null) {
			try {
				Log.i("Recording", "Stoped");
				recorder.stop();
			} catch (IllegalStateException e) {
				// TODO: handle exception
			}
			recorder.reset();
			recorder.release();
			recorder = null;
			MainActivity.flag = 0;
		} 
	}

	private String getFilename() {
		String filepath = Environment.getExternalStorageDirectory().getPath();
		File file = null;
		if(CallReceiver.CALL_IN_OR_OUT.equals("in"))
		{
		file = new File(filepath, ".kolby/incoming");
		
		}
		else if(CallReceiver.CALL_IN_OR_OUT.equals("out")){
		file= new File(filepath,".kolby/outgoing");	
		}
		if (!file.exists()) {
			file.mkdirs();
		}
		String phoneNo = CallReceiver.phoneNumber;
		// +incomingNumber+ "-"
//		String time = System.currentTimeMillis() + "";
//		if (phoneNo == null) {
//			phoneNo = time;
//		}
		
		//if()
		filepath = file.getAbsolutePath() + "/" + phoneNo + ".aac";

		File file2 = new File(filepath);
		int i = 1;
		String temppath = filepath;
		while (file2.exists()) {
			String temp = CallReceiver.phoneNumber;
			// +incomingNumber+ "-"
			// time = System.currentTimeMillis() + "";
			temppath = file.getAbsolutePath() + "/" + temp + "(" + i + ")"+ ".aac";
			System.out.println("checking " + i + temppath);
			file2 = new File(temppath);
			i++;
		}
		return temppath;

	}

	private MediaRecorder.OnErrorListener errorListener = new MediaRecorder.OnErrorListener() {
		public void onError(MediaRecorder mr, int what, int extra) {
			// AppLog.logString("Error: " + what + ", " + extra);
		}
	};

	private MediaRecorder.OnInfoListener infoListener = new MediaRecorder.OnInfoListener() {
		public void onInfo(MediaRecorder mr, int what, int extra) {
			// AppLog.logString("Warning: " + what + ", " + extra);
		}
	};

}
