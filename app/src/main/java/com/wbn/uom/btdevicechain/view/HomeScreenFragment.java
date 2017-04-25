package com.wbn.uom.btdevicechain.view;

import android.content.Context;
import android.graphics.Movie;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wbn.uom.btdevicechain.R;
import com.wbn.uom.btdevicechain.model.Chain;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by   : Chanaka
 * Class name   : HomeScreenFragment
 * Purpose      : Fragment class for the home screen fragment
 */

public class HomeScreenFragment extends Fragment {

    private List<Chain> chainList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NetworkListAdapter networkListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize dataset, this data would usually come from a local content provider or
        // remote server.
        prepareDummyData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_screen, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.network_list_recycler_view);

        networkListAdapter = new NetworkListAdapter(chainList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new RecyclerViewItemDecorator(this.getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(networkListAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity().getApplicationContext(),
                recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Chain chain = chainList.get(position);
                Toast.makeText(getActivity().getApplicationContext(), chain.getName() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getActivity().getApplicationContext(), "hah haa long click!", Toast.LENGTH_SHORT).show();
            }
        }));


        // Inflate the layout for this fragment
        return view;
    }

    private void prepareDummyData(){
        Chain chain = new Chain("My Tablet");
        chainList.add(chain);

        chain = new Chain("My Laptop");
        chainList.add(chain);

    }
}
