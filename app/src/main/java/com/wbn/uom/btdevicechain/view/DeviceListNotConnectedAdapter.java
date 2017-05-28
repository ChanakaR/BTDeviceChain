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
 * Created by inocer on 5/28/17.
 */

public class DeviceListNotConnectedAdapter extends RecyclerView.Adapter<DeviceListNotConnectedAdapter.DeviceListViewHolder> {

    private List<Device> deviceList;

    public class DeviceListViewHolder extends RecyclerView.ViewHolder{
        public TextView device_public_name;
        public DeviceListViewHolder(View itemView) {
            super(itemView);
            device_public_name = (TextView)itemView.findViewById(R.id.device_public_name);
        }
    }

    public DeviceListNotConnectedAdapter(List<Device> deviceList){
        this.deviceList = deviceList;
    }

    @Override
    public DeviceListNotConnectedAdapter.DeviceListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device_list_not_conn_row,parent,false);
        return new DeviceListNotConnectedAdapter.DeviceListViewHolder(itemView);
    }

    // change values of recycler view items
    @Override
    public void onBindViewHolder(DeviceListNotConnectedAdapter.DeviceListViewHolder holder, int position) {
        Device device = deviceList.get(position);
        holder.device_public_name.setText(device.getName());
    }

    @Override
    public int getItemCount() {
        return this.deviceList.size();
    }

    public void add(Device device){
        this.deviceList.add(device);
    }

    public void clear(){
        this.deviceList.clear();
    }

}