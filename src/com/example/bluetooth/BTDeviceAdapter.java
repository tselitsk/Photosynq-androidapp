package com.example.bluetooth;

import com.example.photosynthproj.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BTDeviceAdapter extends ArrayAdapter<BTDeviceItem>{
	
	public BTDeviceAdapter(Context context)
	{
		super(context,0);
	}
	
	@Override
	public void add(BTDeviceItem object) {
		// TODO Auto-generated method stub
		super.add(object);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater vi=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v=vi.inflate(R.layout.btdevice_item,null);
		
		BTDeviceItem btDeviceItem=this.getItem(position);
		TextView tvName=(TextView)v.findViewById(R.id.tvName);
		TextView tvAddress=(TextView)v.findViewById(R.id.tvAddress);
		TextView tvPaired=(TextView)v.findViewById(R.id.tvPaired);
		tvName.setText(btDeviceItem.getName());
		tvAddress.setText(btDeviceItem.getAddress());
		if(btDeviceItem.isPaired()){
			tvPaired.setText("Paired");
		}

		return v;
	}
	
	

}
