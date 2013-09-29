package com.example.bluetooth;

import android.bluetooth.BluetoothDevice;

public class BTDeviceItem {
	
	BluetoothDevice device;
	String name;
	String address;
	boolean paired;
	
	
	public BluetoothDevice getDevice() {
		return device;
	}
	public void setDevice(BluetoothDevice device) {
		this.device = device;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public boolean isPaired() {
		return paired;
	}
	public void setPaired(boolean paired) {
		this.paired = paired;
	}
}
