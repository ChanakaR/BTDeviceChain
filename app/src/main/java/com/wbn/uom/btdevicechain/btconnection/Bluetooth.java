package com.wbn.uom.btdevicechain.btconnection;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.wbn.uom.btdevicechain.MainActivity;
import com.wbn.uom.btdevicechain.model.Device;
import com.wbn.uom.btdevicechain.view.SearchDeviceFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by inocer on 5/29/17.
 */

public class Bluetooth {

    private BluetoothAdapter bluetoothAdapter;
    public static int REQUEST_BLUETOOTH = 1;
    private Context context;

    private final BroadcastReceiver bReciever = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                Log.d("DEVICELIST", "Bluetooth device found\n");
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Create a new device item
                Device newDevice = new Device(device.getName(), device.getAddress(), "false");
                // Add it to our adapter

                SearchDeviceFragment.addDeviceToAdapter(newDevice);
            }
        }
    };

    public Bluetooth(Context context){
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.context = context;
    }

    public BluetoothAdapter getBluetoothAdapter(){ return this.bluetoothAdapter; }

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

    public void enableBluetooth(){
        if(!bluetoothAdapter.isEnabled()){
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            ((Activity)context).startActivityForResult(enableBT, REQUEST_BLUETOOTH);
        }
    }

    public List<Device> getPairedDevices(){
        List<Device> pairedDeviceList = new ArrayList<>();
        Set<BluetoothDevice> pairedDevices = this.bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                Device newDevice= new Device(device.getName(),device.getAddress(),"false");
                pairedDeviceList.add(newDevice);
            }
        }
        return pairedDeviceList;
    }

    public void startDeviceDiscovery(){
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        context.registerReceiver(bReciever, filter);
        bluetoothAdapter.startDiscovery();
    }

    public void cancelDeviceDiscovery(){
        context.unregisterReceiver(bReciever);
        bluetoothAdapter.cancelDiscovery();
    }

}
