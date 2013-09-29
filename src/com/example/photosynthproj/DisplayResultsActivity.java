package com.example.photosynthproj;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.webkit.WebView;

public class DisplayResultsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent i=getIntent();
		int id=i.getExtras().getInt("id");
		String StringId=String.valueOf(id);
		Log.d("Test Thread", "in display results"+" "+StringId);
		String url="http://photosynq.venturit.org/projects/1/data/"+StringId+"-"+StringId;
		Log.d("Test Thread", "in display results"+" "+url);
		
		setContentView(R.layout.activity_display_results);
		 WebView webview = new WebView(this);
		 setContentView(webview);
		 webview.loadUrl(url);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_display_results, menu);
		return true;
	}

}
