package com.example.photosynthproj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class ResearchProject implements Parcelable {

	private int numOfCollaborators;
	private String profilePictureUrl;
	private String nameOfProj;
	private String nameOfPerson;
	private String description;
	private String dataCaptureTime;
	private String additionalInformation;
	private String photoString;
	
	

	public String getPhotoString() {
		return photoString;
	}

	public void setPhotoString(String photoString) {
		this.photoString = photoString;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDataCaptureTime() {
		return dataCaptureTime;
	}

	public void setDataCaptureTime(String dataCaptureTime) {
		this.dataCaptureTime = dataCaptureTime;
	}

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
	

	public ResearchProject()
	{
		
	}
	
	public ResearchProject(String nameOfPerson, int numOfCollaboratrs,String nameOfProject, String url, String description, String dataCaptureTime, String additionalInformation, List<String> listOfquestions)
	{
		this.nameOfPerson=nameOfPerson;
		this.numOfCollaborators=numOfCollaboratrs;
		this.nameOfProj=nameOfProject;
		this.profilePictureUrl=url;
		this.description=description;
		this.dataCaptureTime=dataCaptureTime;
		this.additionalInformation=additionalInformation;
	}
	
	public ResearchProject(Parcel in) {
		nameOfPerson=in.readString();
		numOfCollaborators=in.readInt();
		nameOfProj=in.readString();
		profilePictureUrl=in.readString();
		description=in.readString();
		dataCaptureTime=in.readString();
		additionalInformation=in.readString();
		photoString=in.readString();
    }
	
	public int getNumOfCollaborators() {
		return numOfCollaborators;
	}
	
	public void setNumOfCollaborators(int numOfCollaborators) {
		this.numOfCollaborators = numOfCollaborators;
	}
	
	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}
	
	public void setProfilePictureUrl(String profilePictureUrl) {
		this.profilePictureUrl = profilePictureUrl;
	}
	
	public String getNameOfProj() {
		return nameOfProj;
	}
	
	public void setNameOfProj(String nameOfProj) {
		this.nameOfProj = nameOfProj;
	}
	
	public String getNameOfPerson() {
		return nameOfPerson;
	}
	
	public void setNameOfPerson(String nameOfPerson) {
		this.nameOfPerson = nameOfPerson;
	}


	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		
		dest.writeString(this.nameOfPerson);
		dest.writeInt(this.numOfCollaborators);
		dest.writeString(this.nameOfProj);
		dest.writeString(this.profilePictureUrl);
		dest.writeString(this.description);
		dest.writeString(this.dataCaptureTime);
		dest.writeString(this.additionalInformation);
		dest.writeString(this.photoString);
	}
	
	public static final Parcelable.Creator<ResearchProject> CREATOR =
			new Parcelable.Creator<ResearchProject>() {

				@Override
				public ResearchProject createFromParcel(Parcel source) {
					return new ResearchProject(source);
				}

				@Override
				public ResearchProject[] newArray(int size) {
					return new ResearchProject[size];
				}
	};

}
