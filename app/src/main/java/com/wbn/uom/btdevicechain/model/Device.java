package com.wbn.uom.btdevicechain.model;

import android.bluetooth.BluetoothDevice;

import java.util.List;

/**
 * Created by chanaka on 4/16/17.
 * Class name   : Device
 * Purpose      : Class for represent a device. Contain device information
 */

public class Device {


    private String displayName;
    private String state;
    private BluetoothDevice bDevice;

    public Device(BluetoothDevice device){
        this.bDevice = device;
        this.state = "DISABLED";
        this.displayName = device.getName();
    }


    public String getDisplayName() {
        return displayName;
    }

    public String getState() {
        return state;
    }

    public BluetoothDevice getbDevice() {
        return bDevice;
    }


    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setbDevice(BluetoothDevice bDevice) {
        this.bDevice = bDevice;
    }

    public String getMacAddress(){
        return this.bDevice.getAddress();
    }

}
