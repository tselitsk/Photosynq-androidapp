package com.example.photosynthproj;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResearchProjectItemActivity extends Activity {
	TextView num_Of_collaborators_tv;
	TextView nameofProject_tv;
	TextView nameOfPerson_tv;
	TextView description_content_tv;
	TextView data_capture_time_results_tv;
	Button participate;
	ResearchProject researchProject;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_research_project_item);
		Bundle b = getIntent().getExtras();
		researchProject = b.getParcelable("research Project");
		
		num_Of_collaborators_tv=(TextView)findViewById(R.id.num_Of_collaborators_tv);
		nameofProject_tv=(TextView)findViewById(R.id.nameofProject_txtView);
		nameOfPerson_tv=(TextView)findViewById(R.id.nameOfPerson_tv);
		description_content_tv=(TextView)findViewById(R.id.description_content_tv);
		data_capture_time_results_tv=(TextView)findViewById(R.id.data_capture_time_results_tv);
		participate=(Button)findViewById(R.id.participate_btn);
		
		
		num_Of_collaborators_tv.setText(String.valueOf(researchProject.getNumOfCollaborators()+" "+"collaborators"));
		nameofProject_tv.setText(researchProject.getNameOfProj());
		nameOfPerson_tv.setText(researchProject.getNameOfPerson());
		description_content_tv.setText(researchProject.getDescription());
		data_capture_time_results_tv.setText(researchProject.getDataCaptureTime());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_research_project_item, menu);
		return true;
	}
	
	public void on_click(View view)
	{
		Intent intent=new Intent(this, ProjectDescriptionActivity.class);
		intent.putExtra("research Project", researchProject);
		startActivity(intent);
		
		
	}

}
