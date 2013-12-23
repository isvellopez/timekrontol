package com.trescloud.timekontrol;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	// private static final String TAG = "MainActivity";

	SharedPreferences prefs;

	Button startButton;
	Button stopButton;
	EditText txtPost;
	EditText txtResponse;

	String command;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

//		startButton = (Button) findViewById(R.id.start);
//		startButton.setOnClickListener(this);
//		stopButton = (Button) findViewById(R.id.stop);
//		stopButton.setOnClickListener(this);
		txtPost = (EditText) findViewById(R.id.txtPost);
		txtResponse = (EditText) findViewById(R.id.txtResponse);
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void buttonStart(View view) {

		// txtPost.setText("asdf");
		// this.postData();
		prefs = PreferenceManager.getDefaultSharedPreferences(this);

		SendInfo sendinfo = new SendInfo(prefs, "START");
		try {
			String result = sendinfo.execute().get();
			Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
			txtPost.setText("Post: START");
			txtResponse.setText("Result: " + result);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

	}

	public void buttonStop(View view) {

		// txtPost.setText("asdf");
		// this.postData();
		prefs = PreferenceManager.getDefaultSharedPreferences(this);

		SendInfo sendinfo = new SendInfo(prefs, "STOP");
		try {
			String result = sendinfo.execute().get();
			Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
			txtPost.setText("Post: STOP");
			txtResponse.setText("Result: " + result);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent(this, PrefsActivity.class);
		startActivity(intent);
		return true;
	}

}