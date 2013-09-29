package com.example.photosynthproj;

import java.util.List;

import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ResearchProjectAdapter extends ArrayAdapter<ResearchProject> {

	public ResearchProjectAdapter(Context context,
			List<ResearchProject> objects) {
		super(context, 0, objects);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater vi=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v=vi.inflate(R.layout.research_proj_item, null);
		ResearchProject researchProject=this.getItem(position);
		
		
		TextView tvPersonName=(TextView)v.findViewById(R.id.name_of_person_tv);
		TextView tvProjectName=(TextView)v.findViewById(R.id.name_of_proj_tv);
		TextView tvNumOfCollab=(TextView)v.findViewById(R.id.name_of_collab_tv);
		ImageView imageView=(ImageView)v.findViewById(R.id.image_project);
		//SmartImageView smartImageView=(SmartImageView)v.findViewById(R.id.proj_img);

		tvPersonName.setText(researchProject.getNameOfPerson());
		tvProjectName.setText(researchProject.getNameOfProj());
		String numOfCollabs=String.valueOf(researchProject.getNumOfCollaborators());
		//imageView.setImageDrawable(researchProject.getPhotoString());
		Log.d("Test Thread", researchProject.getProfilePictureUrl());
		tvNumOfCollab.setText(numOfCollabs+" "+"collaborators");
		
		return v;
		
	}
	
	
	

}
