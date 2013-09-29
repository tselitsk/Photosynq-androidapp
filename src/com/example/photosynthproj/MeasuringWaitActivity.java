package com.example.photosynthproj;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MeasuringWaitActivity extends Activity {
	ResearchProject researchProject;
	TextView project_name_tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_measuring_wait);
		
		Bundle b = getIntent().getExtras();
		researchProject = b.getParcelable("research Project");	
		
		project_name_tv=(TextView)findViewById(R.id.project_name_tv);
		project_name_tv.setText(researchProject.getNameOfProj());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_measuring_wait, menu);
		return true;
	}

}
