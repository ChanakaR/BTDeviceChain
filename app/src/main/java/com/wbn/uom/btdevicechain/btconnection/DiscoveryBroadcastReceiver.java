package com.wbn.uom.btdevicechain.btconnection;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.wbn.uom.btdevicechain.model.Device;
import com.wbn.uom.btdevicechain.view.SearchDeviceFragment;

/**
 * Created by inocer on 5/29/17.
 *  Purpose : BroadcastReceiver class to catch near by bluetooth devices
 */

public class DiscoveryBroadcastReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                // Add it to our adapter
                SearchDeviceFragment.addDeviceToAdapter(device);
            }
        }
}
