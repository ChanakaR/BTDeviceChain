package com.wbn.uom.btdevicechain.view;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wbn.uom.btdevicechain.MainActivity;
import com.wbn.uom.btdevicechain.R;
import com.wbn.uom.btdevicechain.model.Device;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/*
 * Purpose : Fragment class for paired device screen
 */

public class PairedDeviceFragment extends Fragment {

    private RecyclerView recyclerView;
    private DeviceListNotConnectedAdapter deviceListNotConnectedAdapter;
    private List<Device> deviceList = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_paired_device, container, false);


        recyclerView = (RecyclerView) view.findViewById(R.id.paired_device_list_recycler_view);

        deviceList = ((MainActivity)getActivity()).bluetooth.getPairedDevices();

        deviceListNotConnectedAdapter = new DeviceListNotConnectedAdapter(deviceList);

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
                Toast.makeText(getActivity().getApplicationContext(), device.getMacAddress() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getActivity().getApplicationContext(), "hah haa long click!", Toast.LENGTH_SHORT).show();
            }
        }));


        return view;
    }
}
