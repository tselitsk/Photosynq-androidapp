package com.example.photosynthproj;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ProjectDescriptionSuccess2Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_description_success2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_project_description_success2,
				menu);
		return true;
	}

}
