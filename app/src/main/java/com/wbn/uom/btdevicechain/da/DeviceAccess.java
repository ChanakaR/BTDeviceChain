package com.wbn.uom.btdevicechain.da;

import com.wbn.uom.btdevicechain.model.Device;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by inocer on 5/27/17.
 */

public class DeviceAccess {
    List<Device> deviceList = new ArrayList<>();

    public DeviceAccess(){
//        prepareDummyData();
   }

//    private void prepareDummyData(){
//        Device device = new Device("My Tablet","1234","false");
//        device.setState("running");
//        deviceList.add(device);
//
//        device = new Device("My Laptop","1234","false");
//        device.setState("running");
//        deviceList.add(device);
//    }

    public List<Device> getDeviceList(){
        return this.deviceList;
    }

    public void addDevice(Device device){
        this.deviceList.add(device);
    }

    public void removeDevice(Device device){
        this.deviceList.remove(device);
    }

    public Device getByPosition(int position){
        return deviceList.get(position);
    }
}
