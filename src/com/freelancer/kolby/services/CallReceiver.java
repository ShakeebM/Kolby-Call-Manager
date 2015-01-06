package com.freelancer.kolby.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class CallReceiver extends BroadcastReceiver {

	public static int flag = 0;
	public static TelephonyManager telephony;
	public static String phoneNumber;
	public static String CALL_IN_OR_OUT;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Bundle bundle = intent.getExtras();
		if (bundle == null)
			return;
	
		MyPhoneStateListener phoneListener = new MyPhoneStateListener();

		telephony = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		telephony.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
		String state = bundle.getString(TelephonyManager.EXTRA_STATE);
		if ((state != null)
				&& (state
						.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING))) {
			phoneNumber = bundle
					.getString(TelephonyManager.EXTRA_INCOMING_NUMBER) + "_in";
			CALL_IN_OR_OUT = "in";
			// Here: do something with the number
		}
		// Outgoing call
		else if (state == null) {
			phoneNumber = bundle.getString(Intent.EXTRA_PHONE_NUMBER) + "_out";
			CALL_IN_OR_OUT = "out";
		}

	}

}
