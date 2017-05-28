package com.wbn.uom.btdevicechain.model;

import java.util.List;

/**
 * Created by chanaka on 4/16/17.
 * Class name   : Device
 * Purpose      : Class for represent a group of devices. Contain device information
 */

public class Device {
    private String name;
    private String address;
    private String state;
    private Boolean connected;

    public Device(String name,String address, String connected){
        this.name = name;
        this.address = address;
        if(connected == "true"){
            this.connected = true;
        }
        else{
            this.connected = false;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public Boolean getConnected() { return connected; }

    public String getAddress(){ return this.address; }

    public int getDeviceCount(){
        return 3;
    }
}
