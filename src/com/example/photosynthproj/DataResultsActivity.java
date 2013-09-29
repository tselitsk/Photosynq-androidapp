package com.example.photosynthproj;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class DataResultsActivity extends Activity {
ResearchProject researchProject;
//TextView project_name_tv;
TextView num_Of_collaborators_tv;
TextView nameofProject_tv;
TextView nameOfPerson_tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data_results);
		
		Bundle b = getIntent().getExtras();
		researchProject = b.getParcelable("research Project");
		
		num_Of_collaborators_tv=(TextView)findViewById(R.id.num_Of_collaborators_tv);
		nameofProject_tv=(TextView)findViewById(R.id.nameofProject_txtView);
		nameOfPerson_tv=(TextView)findViewById(R.id.nameOfPerson_tv);
		
		num_Of_collaborators_tv.setText(String.valueOf(researchProject.getNumOfCollaborators()+" "+"collaborators"));
		nameofProject_tv.setText(researchProject.getNameOfProj());
		nameOfPerson_tv.setText(researchProject.getNameOfPerson());
		
		//project_name_tv=(TextView)findViewById(R.id.project_name_tv);
		//project_name_tv.setText(researchProject.getNameOfProj());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_data_results, menu);
		return true;
	}

}
