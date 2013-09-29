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
import android.widget.EditText;

public class AddTagActivity extends Activity {

	EditText add_tag_et;
	JSONObject resultsJSON;
	String token;
	ResearchProject researchProject;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_tag);
		add_tag_et=(EditText)findViewById(R.id.add_tag_et);
		
		Bundle b = getIntent().getExtras();
		researchProject = b.getParcelable("research Project");
		
		token=((MyApplication) this.getApplication()).getToken();
		
		Intent i=getIntent();
		String result=(String) i.getExtras().get("results");
		JSONBuilder jsonBuilder=new JSONBuilder();
	    resultsJSON=jsonBuilder.buildJSONObject(result);
	    //submitResults.put("auth_token", token);
		
	    Log.d("Test Thread","get"+" "+result);
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add_tag, menu);
		return true;
	}
	
	public void click_submit(View view)
	{
		 //submitResults.put("auth_token", token);
		String tag=add_tag_et.getText().toString();
		JSONObject submitResults = null;
		
	   
		 try {
			resultsJSON.put("tags", tag);
		    submitResults=new JSONObject();
			submitResults.put("auth_token", token);
			Log.d("Test Thread", "submit1"+" "+submitResults.toString());
		    submitResults.put("datum",resultsJSON);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 Log.d("Test Thread", "in action tag"+" "+submitResults.toString());
		 NetworkPostResultsThread thread=new NetworkPostResultsThread(submitResults);
		 thread.start();
		 
		 try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 	JSONObject jsonObject=thread.getResponseJSON();
		 	int id=0;
		 	try {
				id=(Integer) jsonObject.get("id");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 	Log.d("Test Thread","id"+" "+String.valueOf(id));
		   Intent intent=new Intent(this, DisplayResultsActivity.class);
		   intent.putExtra("id", id);
;		   intent.putExtra("research Project", researchProject);
		   startActivity(intent);
		 
		 
		 
	}
	
	class NetworkPostResultsThread extends Thread
	{
		//api/v1/projects/1/data.json
		String url="http://photosynq.venturit.org/api/v1/projects/1/data.json";
		String name;
		String password;
		
		JSONObject resultsJSONObject;
		JSONObject token;
		JSONObject responseJSON;

		
		int TIMEOUT_MILLISEC = 10000;
		
		
		public NetworkPostResultsThread(JSONObject resultsJSONObject)
		{
			this.resultsJSONObject=resultsJSONObject;
			Log.d("Test Thread", "in constructor");
			Log.d("Test Thread", "results"+" "+resultsJSONObject.toString());
			
		}
		
		
		@Override
		public void run() {
			int TIMEOUT_MILLISEC = 10000;
			HttpParams httpParams = new BasicHttpParams();
	        HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
	        HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
	        HttpClient httpclient = new DefaultHttpClient();
	        HttpPost request = new HttpPost(url);
	       
			
			StringEntity input=null;
	        
	        //pass the object into a string entity
	        try {
	                input = new StringEntity(resultsJSONObject.toString());
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
	                this.responseJSON=buildJSON(is);
	                String returnString=returnString(is);
	                //responseJSON=buildJSON(is);
	                Log.d("Test Thread", "return"+" "+returnString);
	        }catch(Exception e){
	                Log.d("error", e.toString() );
	        }
	       
	        //String responseString=returnString(is);
	        
	        //Log.d("Test Thread", responseString);
	        
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
	
	

}
