package com.wbn.uom.btdevicechain.btconnection;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import com.wbn.uom.btdevicechain.model.Device;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by inocer on 5/29/17.
 * Purpose ; Handle bluetooth basic functionalities (eg: enable, getDefaultAdapter, etc)
 *
 */

public class Bluetooth {

    public static int REQUEST_BLUETOOTH = 1;
    private BluetoothAdapter bluetoothAdapter;
    private Context context;
    private DiscoveryBroadcastReceiver broadcardReciever;

    public Bluetooth(Context context){
        broadcardReciever = new DiscoveryBroadcastReceiver();
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.context = context;
    }

    /*
     * return the Default bluetooth adapter
     */
    public BluetoothAdapter getBluetoothAdapter(){ return this.bluetoothAdapter; }

    /*
     *  Check whether the device support Bluetooth
     *  if Device does not have an adapter application will exit showing a message
     */
    public void checkBluetoothAdapter(){
        if (bluetoothAdapter == null) {
            new AlertDialog.Builder((Activity)context)
                    .setTitle("Not compatible")
                    .setMessage("Your phone does not support Bluetooth")
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    /*
     * enable bluetooth
     */
    public void enableBluetooth(){
        if(!bluetoothAdapter.isEnabled()){
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            ((Activity)context).startActivityForResult(enableBT, REQUEST_BLUETOOTH);
        }
    }


    /*
     * get list of paired devices
     */
    public List<Device> getPairedDevices(){
        List<Device> pairedDeviceList = new ArrayList<>();
        Set<BluetoothDevice> pairedDevices = this.bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                Device newDevice= new Device(device);
                pairedDeviceList.add(newDevice);
            }
        }
        return pairedDeviceList;
    }


    /*
     * Start device discovery process
     */
    public void startDeviceDiscovery(){
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        context.registerReceiver(broadcardReciever, filter);
        bluetoothAdapter.startDiscovery();
    }

    /*
     * cancel device discovery
     */
    public void cancelDeviceDiscovery(){
        context.unregisterReceiver(broadcardReciever);
        bluetoothAdapter.cancelDiscovery();
    }

    /*
     * Set Display name
     */
    public void setDisplayName(String name){
        this.bluetoothAdapter.setName(name);
    }


    /*
     * Enable the visibility of device to the other bluetooth devices
     */
    public void startVisible(){
        Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        ((Activity)context).startActivityForResult(getVisible, 0);
    }

}
