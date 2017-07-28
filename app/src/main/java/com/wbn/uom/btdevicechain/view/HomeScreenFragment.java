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
import android.widget.Button;
import android.widget.Toast;

import com.wbn.uom.btdevicechain.MainActivity;
import com.wbn.uom.btdevicechain.R;
import com.wbn.uom.btdevicechain.model.Device;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/*
 * Created by   : Chanaka
 * Class name   : HomeScreenFragment
 * Purpose      : Fragment class for the home screen fragment
 */

public class HomeScreenFragment extends Fragment {

    private List<Device> deviceList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DeviceListSelectedAdapter deviceListSelectedAdapter;


    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    private BluetoothDevice selectedDevice;
    private static List<BluetoothDevice> bluetoothDeviceSelectedList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void startBTConnection(BluetoothDevice device, UUID uuid){
        ((MainActivity)getActivity()).bluetoothCommunicationService.startClient(device,uuid);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_screen, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.network_list_recycler_view);

        deviceList = ((MainActivity)getActivity()).getSelectedDeviceList();
        deviceListSelectedAdapter = new DeviceListSelectedAdapter(deviceList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new RecyclerViewItemDecorator(this.getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(deviceListSelectedAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity().getApplicationContext(),
                recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Device device = deviceList.get(position);
                Toast.makeText(getActivity().getApplicationContext(), device.getDisplayName() + " is selected!", Toast.LENGTH_SHORT).show();
                if(((MainActivity)getActivity()).getMyPost().equals("MASTER")){
                    startBTConnection(device.getbDevice(),MY_UUID_INSECURE);
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getActivity().getApplicationContext(), "hah haa long click!", Toast.LENGTH_SHORT).show();
            }
        }));

        updateScreen();

        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onResume(){
        super.onResume();
        updateScreen();
    }

    public void updateScreen(){
        if(((MainActivity)getActivity()).isDeviceAdded()){
            dataSetChanged();
        }
    }

    public void updateExistingData(int index,Device device){
        deviceListSelectedAdapter.updateDevice(index,device);
    }

    public void dataSetChanged(){
        deviceListSelectedAdapter.notifyDataSetChanged();
        ((MainActivity)getActivity()).resetDeviceAddedFlag();
    }




}
