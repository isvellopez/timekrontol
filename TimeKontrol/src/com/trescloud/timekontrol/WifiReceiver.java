package com.trescloud.timekontrol;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.util.Log;

public class WifiReceiver extends BroadcastReceiver {

	private static final String TAG = "WifiReceiver";

	SharedPreferences prefs;

	@Override
	public void onReceive(Context context, Intent intent) {

		ConnectivityManager conMan = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMan.getActiveNetworkInfo();

		prefs = PreferenceManager.getDefaultSharedPreferences(context);
		
		// String ssid = conMan.
		if (netInfo != null
				&& netInfo.getType() == ConnectivityManager.TYPE_WIFI) {

			WifiManager wifiMgr = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
			String name = wifiInfo.getSSID();

			
			String ssid = prefs.getString("WifiName", "");

			// Log.d("WifiReceiver", "ActualName: " + name);
			// Log.d("WifiReceiver", "ConfiguredName: " + ssid);

			if (name.equalsIgnoreCase(ssid)) {
				// Log.d("WifiReceiver", "Inside the If, Yes");

				SendInfo sendinfostart = new SendInfo(prefs, "START");
				try {
					sendinfostart.execute();

				} catch (Exception e) {
					Log.d(TAG, "Error: " + e.getLocalizedMessage());

				}

			}

			// Log.d("WifiReceiver", "SSID: " + name);
			Log.d(TAG, "Have Wifi Connection");

		} else {
			Log.d(TAG, "BeforeInstance SendInfoStop");
			SendInfo sendinfostop = new SendInfo(prefs, "STOP");
			Log.d(TAG, "AfterInstance BeforeExecute");
			try {
				sendinfostop.execute();
				Log.d(TAG, "AfterExecute");

			} catch (Exception e) {
				Log.d(TAG, "Error: " + e.getLocalizedMessage());

			}
			Log.d(TAG, "Don't have Wifi Connection");
		}

	}

}