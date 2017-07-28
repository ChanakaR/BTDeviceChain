package com.wbn.uom.btdevicechain.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wbn.uom.btdevicechain.R;
import com.wbn.uom.btdevicechain.model.Device;

import java.util.List;

/**
 * Created by   : chanaka on 4/19/17.
 * Class name   : DeviceListSelectedAdapter
 * Purpose      : Adapter class for Device List RecyclerView view in home screen
 */

public class DeviceListSelectedAdapter extends RecyclerView.Adapter<DeviceListSelectedAdapter.DeviceListViewHolder> {

    private List<Device> deviceList;

    public class DeviceListViewHolder extends RecyclerView.ViewHolder{
        public TextView network_name, device_state;
        public DeviceListViewHolder(View itemView) {
            super(itemView);
            network_name = (TextView)itemView.findViewById(R.id.network_name_home);
            device_state = (TextView)itemView.findViewById(R.id.device_count_home);
        }

    }


    public DeviceListSelectedAdapter(List<Device> deviceList){
        this.deviceList = deviceList;
    }

    @Override
    public DeviceListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device_list_conn_row,parent,false);
        return new DeviceListViewHolder(itemView);
    }

    // change values of recycler view items
    @Override
    public void onBindViewHolder(DeviceListViewHolder holder, int position) {
        Device device = deviceList.get(position);
        holder.network_name.setText(device.getDisplayName());
        holder.device_state.setText(device.getState());
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    public void addDevice(Device device){
        this.deviceList.add(device);
    }

    public void updateDevice(int index, Device device){
        List<Device> temp = deviceList;
        temp.set(index,device);
        deviceList.clear();
        deviceList = temp;
        notifyDataSetChanged();
    }

}
