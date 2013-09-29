package com.example.photosynthproj;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.utils.JSONBuilder;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {
	
	EditText name_editText;
	EditText password_editText;
	Button submit_btn;
	TextView response_textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		name_editText=(EditText) findViewById(R.id.name_et);
		password_editText=(EditText)findViewById(R.id.password_et);
		submit_btn=(Button)findViewById(R.id.submit_btn);
		response_textView=(TextView)findViewById(R.id.reponse_tv);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}
	
	public void click_submit(View view)
	{
		String name=name_editText.getText().toString();
		String password=password_editText.getText().toString();
		NetworkLoginThead thread=new NetworkLoginThead(name,password);
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JSONObject responseJSON=thread.getResponseJSON();
//		boolean isSuccess=false;
//		try {
//			isSuccess=(Boolean) responseJSON.get("success");
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		if(isSuccess)
//		{
			String token=null;
			try {
				token=responseJSON.getString("auth_token");
				((MyApplication) this.getApplication()).setToken(token);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(token!=null){
				Log.d("Test Thread", "token"+" "+token);
				Intent intent=new Intent(this, MainActivity.class);
				startActivity(intent);
			}
		}
}


class NetworkLoginThead extends Thread
{
	String url="http://photosynq.venturit.org/api/v1/sign_in.json";

	String name;
	String password;
	
	JSONObject credentials;
	JSONObject user;
	JSONObject responseJSON;

	
	int TIMEOUT_MILLISEC = 10000;
	
	
	public NetworkLoginThead(String name, String password)
	{
		this.name=name;
		this.password=password;
		
		
	}
	
	
	@Override
	public void run() {
		int TIMEOUT_MILLISEC = 10000;
		HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
        HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost request = new HttpPost(url);
        //pass the client the HttpParms to define some of its characteristics
        
      
        
		credentials=new JSONObject();
		user=new JSONObject();
		try {
			Log.d("Test Thread", "name"+" "+name);
			Log.d("Test Thread", "password"+" "+password);
			credentials.put("email", name);
			credentials.put("password", password);
			
			user.put("user", credentials);
			Log.d("Test Thread", "user"+" "+user.toString());
			
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		StringEntity input=null;
        
        //pass the object into a string entity
        try {
                input = new StringEntity(user.toString());
        } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
        }
        
        input.setContentType("application/json");
        
        //pass the entity into a HttpPost request
        request.setEntity(input);
        InputStream is = null;
       
        try{
               
                //execute a request and get a response
                HttpResponse response = httpclient.execute(request);
                HttpEntity entityResponse = response.getEntity();
                //get the entity from the response
                is=entityResponse.getContent();
                responseJSON=buildJSON(is);
                Log.d("Test Thread", "JSONObject"+" "+responseJSON.toString());
        }catch(Exception e){
                Log.d("error", e.toString() );
        }
       
        String responseString=returnString(is);
        
        Log.d("Test Thread", responseString);
        
        super.run();
}
	
	public JSONObject getResponseJSON()
	{
		return this.responseJSON;
	}

	
	private JSONObject buildJSON(InputStream is)
	{
		JSONBuilder jsonBuilder=new JSONBuilder();
		String jsonString=jsonBuilder.buildJSONString(is);
		JSONObject jsonObject=jsonBuilder.buildJSONObject(jsonString);
		return jsonObject;
		
	}
	private String returnString(InputStream is)
	{
	        StringBuilder sb = new StringBuilder();
	        try{
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	        String line = null;
	                while ((line = reader.readLine()) != null) {
	                                sb.append(line + "\n");
	                }
	                           is.close();
	                }catch(Exception e){
	                                System.out.println(e.toString());
	                }
	
	                return sb.toString();
	}
	
	
				
}
