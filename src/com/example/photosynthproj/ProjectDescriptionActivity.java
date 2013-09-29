package com.example.photosynthproj;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class ProjectDescriptionActivity extends Activity {
	ResearchProject researchProject;
	TextView additional_information_results_tv;
	TextView num_Of_collaborators_tv;
	TextView nameofProject_tv;
	TextView nameOfPerson_tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_description);
		
		Bundle b = getIntent().getExtras();
		researchProject = b.getParcelable("research Project");
		
		num_Of_collaborators_tv=(TextView)findViewById(R.id.num_Of_collaborators_tv);
		nameofProject_tv=(TextView)findViewById(R.id.nameofProject_txtView);
		nameOfPerson_tv=(TextView)findViewById(R.id.nameOfPerson_tv);
		
		num_Of_collaborators_tv.setText(String.valueOf(researchProject.getNumOfCollaborators()+" "+"collaborators"));
		nameofProject_tv.setText(researchProject.getNameOfProj());
		nameOfPerson_tv.setText(researchProject.getNameOfPerson());
		
		additional_information_results_tv=(TextView)findViewById(R.id.additional_information_results_tv);
		additional_information_results_tv.setText(researchProject.getAdditionalInformation());

	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_project_description, menu);
		return true;
	}
	
	public void on_click(View view)
	{
		Intent intent=new Intent(this, ProjectDescription4Activity.class);
		intent.putExtra("research Project", researchProject);
		startActivity(intent);
	}

}
