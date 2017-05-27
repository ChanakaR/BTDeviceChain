package com.wbn.uom.btdevicechain.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wbn.uom.btdevicechain.R;

/*
 * Created by   : Chanaka
 * Class name   : NetworkScreenFragment
 * Purpose      : Fragment class for the network detail view.
 */
public class DeviceSearchScreenFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_device_search_screen, container, false);
    }
}