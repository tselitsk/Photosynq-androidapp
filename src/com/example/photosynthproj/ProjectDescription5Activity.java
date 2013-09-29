package com.example.photosynthproj;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class ProjectDescription5Activity extends Activity {
	ResearchProject researchProject;
	TextView project_name_tv;
	TextView measurement_time_tv;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_description5);
		
		Log.d("Test Thread", "in Proj 5");
		
		Bundle b = getIntent().getExtras();
		researchProject = b.getParcelable("research Project");
		
		project_name_tv=(TextView)findViewById(R.id.project_name_tv);
		project_name_tv.setText(researchProject.getNameOfProj());
		
		try{
            //do something
                    Thread.sleep(3000);
                    Log.d("Test Thread", "5 on click");
             		Intent intent=new Intent(this, ProjectDescriptionSuccessActivity.class);
             		intent.putExtra("research Project", researchProject);
             		startActivity(intent);
                }catch(InterruptedException e){
                // interrupted exception hit before the sleep time is completed.so how do i make my thread sleep for exactly 3 seconds?
                }
        }
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_project_description5, menu);
		return true;
	}
	
	public void on_click(View view)
	{
		
		//Log.d("Test Thread", "5 on click");
		//Intent intent=new Intent(this, ProjectDescriptionSuccessActivity.class);
		//intent.putExtra("research Project", researchProject);
		//startActivity(intent);
		
	}

}
