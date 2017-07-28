package com.wbn.uom.btdevicechain;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.wbn.uom.btdevicechain.btconnection.Bluetooth;
import com.wbn.uom.btdevicechain.btconnection.BluetoothCommunicationService;
import com.wbn.uom.btdevicechain.model.Device;
import com.wbn.uom.btdevicechain.view.DeviceSelectScreenFragment;
import com.wbn.uom.btdevicechain.view.HomeScreenFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/*
 * Purpose : MainActivity class
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public Bluetooth bluetooth;
    public BluetoothCommunicationService bluetoothCommunicationService;
    private String my_post = null;          // MASTER OR SLAVE
    private String my_name = null;
    private List<Device> selectedBluetoothDevices;
    private boolean deviceAddedFlag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        this.my_post = extras.getString("STATE");
        this.my_name = extras.getString("NAME");


        selectedBluetoothDevices = new ArrayList<>();
        deviceAddedFlag = false;
        bluetooth = new Bluetooth(this);
        bluetooth.checkBluetoothAdapter();
        bluetooth.enableBluetooth();
        bluetooth.setDisplayName(my_name);

        bluetoothCommunicationService = new BluetoothCommunicationService(this,this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment deviceSearchFragment = new DeviceSelectScreenFragment();
                changeFragment(deviceSearchFragment);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(this.my_post.equals("SLAVE")){
            bluetooth.startVisible();
            bluetoothCommunicationService.startListening();
        }

        //replace home screen by home screen fragment

        if(this.my_post.equals("MASTER")){
            startHomeFragment();
        }

    }

    private void startHomeFragment(){
        Fragment homeFragment = new HomeScreenFragment();
        changeFragment(homeFragment);
    }


    // functionality on back pressed
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        FragmentManager fm = getSupportFragmentManager();
        int c = fm.getBackStackEntryCount();

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (fm.getBackStackEntryCount() > 1) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();
        }
        else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_settings) {
            // Handle the camera action
        } else if (id == R.id.nav_stop) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onFragmentInteraction(Uri uri){
        Toast toast = Toast.makeText(this, "Wheeee!",Toast.LENGTH_SHORT);
        toast.show();
    }

    public void displaySlaveHome(BluetoothDevice device){
        Device my_master = new Device(device);
        deviceListChanged(my_master);
        startHomeFragment();
    }

    public void changeFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content_main, fragment);
        ft.addToBackStack(null).commit();
    }

    public List<Device> getSelectedDeviceList(){
        return this.selectedBluetoothDevices;
    }

    public void deviceListChanged(Device device){
        this.selectedBluetoothDevices.add(device);
        this.deviceAddedFlag = true;
    }

    public boolean isDeviceAdded(){
        return this.deviceAddedFlag;
    }

    public void resetDeviceAddedFlag(){
        this.deviceAddedFlag = false;
    }

    public String getMyPost(){
        return this.my_post;
    }

    public void deviceListUpdate(Device device){

        for(int i=0;i<selectedBluetoothDevices.size();i++){
            if(selectedBluetoothDevices.get(i).getMacAddress().equals(device.getMacAddress())){
                Log.d("FIND DEVICE : ", "RELEVANT DEVICE FOUND");
                selectedBluetoothDevices.set(i,device);
                updateFragmentScreen();
            }
        }
    }

    private void updateFragmentScreen(){
        try {
            Log.d("UPDATE FRONT : ", "going to update front");
            HomeScreenFragment f = (HomeScreenFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.home_screen_fragment);
            f.dataSetChanged();
        }
        catch (Exception ex){

        }
    }

    public void showNotification() {

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_error)
                            .setContentTitle("Device is missing")
                            .setContentText("Bluetooth device chain")
                            .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);

            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(001, mBuilder.build());


    }

    public String getDisplayName(){
        return this.my_name;
    }

}
