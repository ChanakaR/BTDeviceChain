package com.wbn.uom.btdevicechain.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wbn.uom.btdevicechain.R;
import com.wbn.uom.btdevicechain.model.Chain;

import java.util.List;

/**
 * Created by chanaka on 4/19/17.
 */

public class NetworkListAdapter extends RecyclerView.Adapter<NetworkListAdapter.NetworkListViewHolder> {

    private List<Chain> chainList;

    public class NetworkListViewHolder extends RecyclerView.ViewHolder{
        public TextView network_name, device_count;
        public NetworkListViewHolder(View itemView) {
            super(itemView);
            network_name = (TextView)itemView.findViewById(R.id.network_name_home);
            device_count = (TextView)itemView.findViewById(R.id.device_count_home);
        }
    }

    public NetworkListAdapter(List<Chain> chainList){
        this.chainList = chainList;
    }

    @Override
    public NetworkListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.network_list_row,parent,false);
        return new NetworkListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NetworkListViewHolder holder, int position) {
        Chain chain = chainList.get(position);
        holder.network_name.setText(chain.getName());

        int d_count = chain.getDeviceCount();
        String count_label = "0 devices";
        if(d_count == 1){
            count_label = "1 device";
        }
        else if(d_count > 1){
            count_label = d_count + " devices";
        }
        holder.device_count.setText(count_label);
    }

    @Override
    public int getItemCount() {
        return this.chainList.size();
    }

}
