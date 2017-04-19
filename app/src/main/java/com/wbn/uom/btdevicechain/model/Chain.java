package com.wbn.uom.btdevicechain.model;

import java.util.List;

/**
 * Created by chanaka on 4/16/17.
 */

public class Chain {
    private String name;
    private String state;
    private List<Device> deviceList;

    public Chain(String name){
        this.name = name;
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

    public int getDeviceCount(){
        return 3;
    }
}
