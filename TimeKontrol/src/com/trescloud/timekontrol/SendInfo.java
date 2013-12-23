package com.trescloud.timekontrol;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

public class SendInfo extends AsyncTask<String, Integer, String> {

	private static final String TAG = "SendInfo";
	SharedPreferences prefs;
	String command;

	public SendInfo(SharedPreferences prefs1, String comm) {

		prefs = prefs1;
		command = comm;
	}

	protected String doInBackground(String... arg0) {

		// Log.d(TAG, "inside doInBackground");
		byte[] result = null;
		String str = "";

		String product;
		String tag;
		String sim;
		String url;

		product = prefs.getString("product", "");
		tag = prefs.getString("tag", "");
		sim = prefs.getString("sim", "");
		url = prefs.getString("url", "");

		try {

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("command", command));
			nameValuePairs.add(new BasicNameValuePair("product", product));
			nameValuePairs.add(new BasicNameValuePair("tag", tag));
			nameValuePairs.add(new BasicNameValuePair("sim", sim));

			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);

			post.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

			HttpResponse response = client.execute(post);

			StatusLine statusLine = response.getStatusLine();

			Log.d(TAG, "String:  " + statusLine.toString());

			if (statusLine.getStatusCode() == HttpURLConnection.HTTP_OK) {
				Log.d(TAG, "Inside If");
				result = EntityUtils.toByteArray(response.getEntity());
				str = new String(result, "UTF-8");
				Log.d(TAG, "Result: " + str);
			}

		} catch (Exception e) {
			Log.d(TAG, "Error: " + e.getLocalizedMessage());
			e.printStackTrace();

		}
		return str;
	}

	protected String onPostExecute(String... arg0) {
		return "hola";
	}

}
