package com.wbn.uom.btdevicechain.view;

import android.view.View;

/**
 * Created by       : chanaka on 4/19/17.
 * Interface name   : RecyclerViewClickListener
 * Purpose          : Interface for Recycler views item click listeners
 */

public interface RecyclerViewClickListener {
    void onClick(View view, int position);
    void onLongClick(View view, int position);
}
