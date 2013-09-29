package com.example.photosynthproj;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class ProjectDescriptionActivity2 extends Activity {
ResearchProject researchProject;
TextView project_name_tv;
TextView num_Of_collaborators_tv;
TextView nameofProject_tv;
TextView nameOfPerson_tv;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_description_activity2);
		
		Bundle b = getIntent().getExtras();
		researchProject = b.getParcelable("research Project");
		
		num_Of_collaborators_tv=(TextView)findViewById(R.id.num_Of_collaborators_tv);
		nameofProject_tv=(TextView)findViewById(R.id.nameofProject_txtView);
		nameOfPerson_tv=(TextView)findViewById(R.id.nameOfPerson_tv);
		
		num_Of_collaborators_tv.setText(String.valueOf(researchProject.getNumOfCollaborators()+" "+"collaborators"));
		nameofProject_tv.setText(researchProject.getNameOfProj());
		nameOfPerson_tv.setText(researchProject.getNameOfPerson());
		
		
		//
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(
				R.menu.activity_project_description_activity2, menu);
		return true;
	}
	
	public void on_click(View view)
	{
		Intent intent=new Intent(this, ProjectDescriptionActivity3.class);
		intent.putExtra("research Project", researchProject);
		startActivity(intent);
	}

}
