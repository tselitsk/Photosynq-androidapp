package com.example.photosynthproj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

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

import com.example.bluetooth.BTDeviceAdapter;
import com.example.bluetooth.BTDeviceItem;
import com.example.photosynthproj.R;
import com.example.photosynthproj.R.layout;
import com.example.photosynthproj.R.menu;
import com.example.utils.JSONBuilder;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class BluetoothActivity extends Activity implements OnItemClickListener {
	Button connectNew;
	ListView listView;
	ResearchProject researchProject;
	
	BluetoothAdapter btAdapter;
	ArrayList<String> pDevices;
	ArrayList<BTDeviceItem> deviceListItem;
	Set<BluetoothDevice> pairedDevices;
	ArrayAdapter<String> mArrayAdapter;
	BTDeviceAdapter btDeviceAdapter;
	IntentFilter filter;
	BroadcastReceiver receiver;
	ConnectThread mConnectThread;
	ConnectedThread mConnectedThread;
	TextView input_tv;
	String token;
	
	  protected Handler handler=new Handler(){
		  	@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					input_tv.setText((String)msg.obj);	
				}
			};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bluetooth);
		connectNew=(Button)findViewById(R.id.bConnectionNew);
		listView=(ListView)findViewById(R.id.listView1);
		listView.setOnItemClickListener(this);
		input_tv=(TextView)findViewById(R.id.input_tv);
		
		deviceListItem=new ArrayList<BTDeviceItem>();
		btDeviceAdapter=new BTDeviceAdapter(this);
		listView.setAdapter(btDeviceAdapter);
		
		pDevices=new ArrayList<String>();
		
		btAdapter = BluetoothAdapter.getDefaultAdapter();
		
		Bundle b = getIntent().getExtras();
		researchProject = b.getParcelable("research Project");
		token=((MyApplication) this.getApplication()).getToken();
		Log.d("Test Thread", "token in bluetooth"+" "+token);
	
		
		
		if (btAdapter== null) {
		    Toast.makeText(getApplicationContext(),"No Bluetooth enabled", 0).show();
		    finish();
		}
		if (!btAdapter.isEnabled()) {
		    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    startActivityForResult(enableBtIntent, 1);
		}
		
		getPairedDevices();
		startDiscovery();
		
		
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		receiver=new BroadcastReceiver() {
		    public void onReceive(Context context, Intent intent) {
		        String action = intent.getAction();
		        // When discovery finds a device
		        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
		            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		           Toast.makeText(getApplicationContext(), device.getName(),0).show();
		          BTDeviceItem btDeviceItem=new BTDeviceItem();
	              btDeviceItem.setAddress(device.getAddress());
	              btDeviceItem.setName(device.getName());		          
	              btDeviceItem.setDevice(device);
	              
		            
		            for(int i=0;i<pDevices.size();i++)
		            {
		            	if(pDevices.get(i).equals(device.getName()))
		            	{
		            		btDeviceItem.setPaired(true);
		            		break;
		            	}
		            }
		            btDeviceAdapter.add(btDeviceItem);
		            //deviceListItem.add(btDeviceItem);
		            
		        }
		        else if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action))
		        {
		        	Toast.makeText(getApplicationContext(),"Discovery Started...", 0).show();
		        }
		        else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
		        {
		        	Toast.makeText(getApplicationContext(),"Discovery Finished", 0).show();
		        	Toast.makeText(getApplicationContext(),String.valueOf(deviceListItem.size()), 0).show();

		    		
		        }
		        else if(BluetoothAdapter.ACTION_STATE_CHANGED.equals(action))
		        {
		        	if(btAdapter.getState()==btAdapter.STATE_OFF)
		        	{
		        		Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    		    startActivityForResult(enableBtIntent, 1);
		        	}
		        }
		    }
		    };
		    
		   // Don't forget to unregister during onDestroy
		   registerReceiver(receiver, filter);
		   filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		   registerReceiver(receiver, filter);
		   filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		   registerReceiver(receiver, filter);
		   filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
		   registerReceiver(receiver, filter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_bluetooth, menu);
		return true;
	}
	
	private void getPairedDevices() {
		pairedDevices = btAdapter.getBondedDevices();
		 int num=pairedDevices.size();
		if (pairedDevices.size() > 0) {
		    // Loop through paired devices
		    for (BluetoothDevice device : pairedDevices) {
		       
		        pDevices.add(device.getName());
		    }
		}
		
	}

	private void startDiscovery() {
		btAdapter.cancelDiscovery();
		btAdapter.startDiscovery();
		
	}
	
	  public void finish_Click(View view)
	{
		Intent intent=new Intent(this, ProjectDescriptionSuccessActivity.class);
		intent.putExtra("research Project", researchProject);
		startActivity(intent);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		unregisterReceiver(receiver);
	}


	private void init()
	{
		
	}
	

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
	         
		BTDeviceItem btDeviceItem=(BTDeviceItem)listView.getAdapter().getItem(position);
		connect(btDeviceItem.getDevice());
		
	}
	
	
	private void connect(BluetoothDevice device) {
		ConnectThread connectThread=new ConnectThread(device);
		connectThread.start();
		
	}


	private class ConnectThread extends Thread {
		private final UUID MY_UUID=UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	    //private  final UUID MY_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
		
		private final BluetoothDevice mmDevice;
		private final BluetoothSocket mmSocket;
		
				
			public ConnectThread(BluetoothDevice device)
			{
				Log.d("Test Thread", "in constructor");
				Log.d("Test Thread", device.getName());
				BluetoothSocket tmp=null;
				mmDevice=device;
				
				BluetoothDevice actual = btAdapter.getRemoteDevice(mmDevice.getAddress());
				if(actual.getBondState()==device.BOND_BONDED){
					
					Toast.makeText(getApplicationContext(),"is bonded",0).show();
				}
				
				try{
					tmp = actual.createRfcommSocketToServiceRecord(MY_UUID);
				}catch(Exception e){
					Log.d("Test Thread", e.toString());
				}
				mmSocket=tmp;
			}

			@Override
			public void run() {
				Log.d("Test Thread", "in run");
		
				btAdapter.cancelDiscovery();
				try {
					mmSocket.connect();
				} catch (IOException e) {
					Log.d("Test Thread", "not connected");
					Log.d("Test Thread", e.toString());
					
					try {
						mmSocket.close();
					} catch (IOException closeException) {
						// TODO Auto-generated catch block
						closeException.printStackTrace();
					}//catch
				}
				
				// Do work to manage the connection (in a separate thread)
		        	manageConnectedSocket(mmSocket);

				}//run
			
			
			
			
			private void manageConnectedSocket(BluetoothSocket socket) {
		       // if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}
		       ConnectedThread connectedThread=new ConnectedThread(socket);
		       connectedThread.start();
		       try {
				connectedThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		       String getResults=connectedThread.getString();
		       Log.d("Test Thread", "results"+" "+getResults);
		       JSONBuilder jsonBuilder=new JSONBuilder();
		       JSONObject results=jsonBuilder.buildJSONObject(getResults);
		       Log.d("Test Thread", "object"+" "+results.toString());
		      
		       Intent intent=new Intent(getApplicationContext(), AddTagActivity.class);
		       intent.putExtra("research Project", researchProject);
		       intent.putExtra("results",results.toString()); 
		       //intent.set
		       startActivity(intent);
		       //NetworkPostResultsThread thread=new  NetworkPostResultsThread(submitResults);
		       //thread.start();
			}

			public void cancel()
			{
				try {
					mmSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}//cancel
		}
		
	private class ConnectedThread extends Thread {
	    private final BluetoothSocket mmSocket;
	    private final InputStream mmInStream;
	    private final OutputStream mmOutStream;
		private String totalResultString;
	 
	    public ConnectedThread(BluetoothSocket socket) {
	        mmSocket = socket;
	        InputStream tmpIn = null;
	        OutputStream tmpOut = null;
	        
	        
	 
	        Log.d("Test Thread", "in connected Thread");
	        
	        if(mmSocket.isConnected())
	        {
	        	Log.d("Test Thread", "is connected");
	        }
	        // Get the input and output streams, using temp objects because
	        // member streams are final
	        try {
	            tmpIn = socket.getInputStream();
	            tmpOut = socket.getOutputStream();
	        } catch (IOException e) 
	        {
	        	Log.d("Test Thread",e.toString());
	        }
	 
	        mmInStream = tmpIn;
	        mmOutStream = tmpOut;
	        
	    }
	    
	    public String getString()
	    {
	    	return this.totalResultString;
	    }
	 
	    public void run() {
	    	
	        byte[] buffer = new byte[1024];  // buffer store for the stream
	        int bytes; // bytes returned from read()
	
	        Log.d("Test Thread", "in connected Thread run");
	        StringBuffer textBuffer=new StringBuffer();
	        String protocol="001";
         	byte[] protocolbyte;
			try {
				protocolbyte = protocol.getBytes("US-ASCII");
				write(protocolbyte);
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
         	
	        while (true) {
	            try {        
	            	
	            	// Read from the InputStream
	                bytes = mmInStream.read(buffer);
	                String getString= new String(buffer,0,bytes);
	                if(getString.endsWith("}")){
	                	textBuffer.append(getString);
	                	this.totalResultString=textBuffer.toString();
	                	Log.d("Test Thread", "the end"+" "+getString);
	                	break;
	                }
	                textBuffer.append(getString);
	            
	                Message message=handler.obtainMessage();
	    			message.obj=textBuffer.toString();
	    			handler.sendMessage(message);
	                
	                //input_tv.setText(textBuffer.toString());
	                
	      
		            Log.d("Test Thread", getString);
	            } catch (IOException e) {
	               Log.d("Test Thread", e.toString());
	            }
	           
	            
	            //JSONBuilder jsonBuilder=new JSONBuilder();
		        //String resultsString=textBuffer.toString();
		        //if(resultsString!=null){
		        	//Log.d("Test Thread", "results String"+" "+resultsString);
		        	//JSONObject jsonObject=jsonBuilder.buildJSONObject(resultsString);
		        	//Log.d("Test Thread", "jsonBuilder"+" "+jsonObject.toString());
		        //}else{
		        	//Log.d("Test Thread", "response string is null");
		        //}
	        }
	        
	        
	    }
	 
	    public void write(byte[] bytes) {
	        try {
	            mmOutStream.write(bytes);
	        } catch (IOException e) { }
	    }
	    
	  

	    public void cancel() {
	        try {
	            mmSocket.close();
	        } catch (IOException e) { }
	    }
	}
	
//	class NetworkPostResultsThread extends Thread
//	{
//		String url="http://photosynq.venturit.org/projects/1/contribute.json";
//		String name;
//		String password;
//		
//		JSONObject resultsJSONObject;
//		JSONObject token;
//		JSONObject responseJSON;
//
//		
//		int TIMEOUT_MILLISEC = 10000;
//		
//		
//		public NetworkPostResultsThread(JSONObject resultsJSONObject)
//		{
//			this.resultsJSONObject=resultsJSONObject;
//			Log.d("Test Thread", "in constructor");
//			Log.d("Test Thread", "results"+" "+resultsJSONObject.toString());
//			
//		}
//		
//		
//		@Override
//		public void run() {
//			int TIMEOUT_MILLISEC = 10000;
//			HttpParams httpParams = new BasicHttpParams();
//	        HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
//	        HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
//	        HttpClient httpclient = new DefaultHttpClient();
//	        HttpPost request = new HttpPost(url);
//	       
//			
//			StringEntity input=null;
//	        
//	        //pass the object into a string entity
//	        try {
//	                input = new StringEntity(resultsJSONObject.toString());
//	        } catch (UnsupportedEncodingException e1) {
//	                e1.printStackTrace();
//	        }
//	        
//	        input.setContentType("application/json");
//	        
//	        //pass the entity into a HttpPost request
//	        request.setEntity(input);
//	        InputStream is = null;
//	       
//	        try{
//	               
//	                //execute a request and get a response
//	                HttpResponse response = httpclient.execute(request);
//	                HttpEntity entityResponse = response.getEntity();
//	                //get the entity from the response
//	                is=entityResponse.getContent();
//	                String returnString=returnString(is);
//	                //responseJSON=buildJSON(is);
//	                Log.d("Test Thread", "return"+" "+returnString);
//	        }catch(Exception e){
//	                Log.d("error", e.toString() );
//	        }
//	       
//	        //String responseString=returnString(is);
//	        
//	        //Log.d("Test Thread", responseString);
//	        
//	        super.run();
//	}
//		
//		public JSONObject getResponseJSON()
//		{
//			return this.responseJSON;
//		}
//
//		
//		private JSONObject buildJSON(InputStream is)
//		{
//			JSONBuilder jsonBuilder=new JSONBuilder();
//			String jsonString=jsonBuilder.buildJSONString(is);
//			JSONObject jsonObject=jsonBuilder.buildJSONObject(jsonString);
//			return jsonObject;
//			
//		}
//		private String returnString(InputStream is)
//		{
//		        StringBuilder sb = new StringBuilder();
//		        try{
//		        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//		        String line = null;
//		                while ((line = reader.readLine()) != null) {
//		                                sb.append(line + "\n");
//		                }
//		                           is.close();
//		                }catch(Exception e){
//		                                System.out.println(e.toString());
//		                }
//		
//		                return sb.toString();
//		}
//		
//		
//					
//	}


}
