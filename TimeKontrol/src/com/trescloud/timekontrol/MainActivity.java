package com.trescloud.timekontrol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private static final String TAG = "MainActivity";

	SharedPreferences prefs;

	Button buttonUpdate;
	EditText editStatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		buttonUpdate = (Button) findViewById(R.id.btnAction);
		editStatus = (EditText) findViewById(R.id.editText1);

		buttonUpdate.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {

		// this.editStatus.setText("asdf");
		this.postData();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent(this, PrefsActivity.class);
		startActivity(intent);
		return true;
	}

	public void postData() {

		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String username = prefs.getString("username", "");
		String password = prefs.getString("password", "");
		String command = prefs.getString("command", "");
		String url = prefs.getString("url", "");

		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("user", username));
			nameValuePairs.add(new BasicNameValuePair("pass", password));
			nameValuePairs.add(new BasicNameValuePair("comm", command));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			Log.d(TAG, "postData Before Post");
			httpclient.execute(httppost);
			// HttpResponse response = httpclient.execute(httppost);
			// Log.d(TAG, "postData After Post" + response.toString());
			Toast.makeText(this, "Information Sent", Toast.LENGTH_SHORT).show();
			// Log.d(TAG, "postData Information Sent ");

		} catch (ClientProtocolException e) {
			Log.d(TAG, "postData first catch:");
			e.printStackTrace();

		} catch (IOException e) {
			Log.d(TAG, "postData second catch:");
			e.printStackTrace();
		}
	}
}
