package com.example.photosynthproj;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ResearchProjActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_research_proj);
		
		ArrayList<ResearchProject> researchProjectList=new ArrayList<ResearchProject>();
		ResearchProject researchProject1=new ResearchProject();
		researchProject1.setNameOfPerson("Greg Austic");
		researchProject1.setProfilePictureUrl("http://www.phlmetropolis.com/Cats.jpg");
		researchProject1.setNameOfProj("Long term climate change study");
		researchProject1.setDescription("We will track climate change by measuring maple trees across North America over the course of 5 years.");
		researchProject1.setDataCaptureTime("5 secs");
		researchProject1.setAdditionalInformation("Additional Information for project 1 is like this..");
		researchProject1.setPhotoString("simple_leaf");
		researchProject1.setNumOfCollaborators(20);
		researchProjectList.add(researchProject1);
		
		ResearchProject researchProject2=new ResearchProject();
		researchProject2.setNameOfPerson("Bob George");
		researchProject2.setProfilePictureUrl("http://www.phlmetropolis.com/Cats.jpg");
		researchProject2.setNameOfProj("3D Trees!");
		researchProject2.setDataCaptureTime("5 secs");
		researchProject2.setNumOfCollaborators(7);
		researchProject2.setPhotoString("leaf2");
		researchProjectList.add(researchProject2);
		
		ResearchProject researchProject3=new ResearchProject();
		researchProject3.setNameOfPerson("Lisa Marie");
		researchProject3.setProfilePictureUrl("http://www.phlmetropolis.com/Cats.jpg");
		researchProject3.setDataCaptureTime("5 secs");
		researchProject3.setNameOfProj("Beetle Plant Project");
		researchProject3.setPhotoString("leaf3");
		researchProject3.setNumOfCollaborators(12);
		researchProjectList.add(researchProject3);
		
		ResearchProject researchProject4=new ResearchProject();
		researchProject4.setNameOfPerson("Sara Smith");
		researchProject4.setProfilePictureUrl("http://www.phlmetropolis.com/Cats.jpg");
		researchProject4.setNameOfProj("Tracking pollution through algae blooms");
		researchProject4.setDataCaptureTime("5 secs");
		researchProject4.setNumOfCollaborators(14);
		researchProject2.setPhotoString("leaf4");
		researchProjectList.add(researchProject4);
		
		ResearchProjectAdapter ra=new ResearchProjectAdapter(this, researchProjectList);
		ListView lv=(ListView)findViewById(R.id.researchProjListView);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView adapterView, View view, int position,
					long id) {
					ResearchProject researchProject=(ResearchProject) adapterView.getItemAtPosition(position);
					Intent intent=new Intent(getApplicationContext(), ResearchProjectItemActivity.class);
					intent.putExtra("research Project", researchProject);
					startActivity(intent);
				
				
			}
			});
		lv.setAdapter(ra);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_research_proj, menu);
		return true;
	}

}
