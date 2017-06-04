package com.wbn.uom.btdevicechain.view;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.wbn.uom.btdevicechain.MainActivity;
import com.wbn.uom.btdevicechain.R;
import com.wbn.uom.btdevicechain.model.Device;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class SearchDeviceFragment extends Fragment {

    private RecyclerView recyclerView;
    private static DeviceListNotConnectedAdapter deviceListNotConnectedAdapter;
    private List<Device> deviceList = new ArrayList<>();
    private static List<BluetoothDevice> bluetoothDeviceList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_search_device, container, false);


        recyclerView = (RecyclerView) view.findViewById(R.id.search_device_list_recycler_view);

        deviceListNotConnectedAdapter = new DeviceListNotConnectedAdapter(deviceList);

        ToggleButton scan = (ToggleButton) view.findViewById(R.id.search_btn);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new RecyclerViewItemDecorator(this.getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(deviceListNotConnectedAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity().getApplicationContext(),
                recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Device device = deviceList.get(position);
                device.getMacAddress();
                Toast.makeText(getActivity().getApplicationContext(), device.getMacAddress() + " is selected!", Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    try{
                        device.getbDevice().createBond();
                        ((MainActivity)getActivity()).deviceListChanged(device);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    try {
                        Method method = device.getbDevice().getClass().getMethod("createBond", (Class[]) null);
                        method.invoke(device.getbDevice(), (Object[]) null);
                        ((MainActivity)getActivity()).deviceListChanged(device);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getActivity().getApplicationContext(), "hah haa long click!", Toast.LENGTH_SHORT).show();
            }
        }));


        scan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    deviceListNotConnectedAdapter.clear();
                    ((MainActivity)getActivity()).bluetooth.startDeviceDiscovery();
                } else {
                    ((MainActivity)getActivity()).bluetooth.cancelDeviceDiscovery();
                }
            }
        });

        return view;

    }

    public static void addDeviceToAdapter(BluetoothDevice device){
//        bluetoothDeviceList.add(device);
        // Create a new device item
        Device newDevice = new Device(device);
        deviceListNotConnectedAdapter.add(newDevice);
        deviceListNotConnectedAdapter.notifyDataSetChanged();
    }
}


