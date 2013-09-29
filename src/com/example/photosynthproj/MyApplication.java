package com.example.photosynthproj;

import android.app.Application;

public class MyApplication extends Application {
	
	String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}


}
