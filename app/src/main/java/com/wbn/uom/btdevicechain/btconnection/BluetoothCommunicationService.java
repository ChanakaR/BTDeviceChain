package com.wbn.uom.btdevicechain.btconnection;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by inocer on 6/2/17.
 */

public class BluetoothCommunicationService {
    private static final String TAG = "BluetoothConnectionServ";
    private static final String appName = "BTDeviceChain";
    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
    private final BluetoothAdapter bluetoothAdapter;
    Context context;

    private AcceptThread mInsecureAcceptThread;
    private ConnectThread connectThread;
    private BluetoothDevice mmDevice;
    private UUID deviceUUID;
    ProgressDialog progressDialog;

    private ConnectedThread connectedThread;
    private List<ConnectedThread> connectedThreadPool = new ArrayList<>();

    public BluetoothCommunicationService(Context context){
        this.context = context;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        start();
    }


    /*
     * This thread runs while listening for incoming connections. It behaves like a sever-side client
     * It will run until a connection is accepted or cancelled
     */

    private class AcceptThread extends Thread{
        private final BluetoothServerSocket bluetoothServerSocket;

        public AcceptThread(){
            BluetoothServerSocket tmp = null;

            try {
                // create a new server socket
                Log.d(TAG,"AccpetThread : I am waiting for a connection with UUID" + MY_UUID_INSECURE);
                tmp = bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(appName,MY_UUID_INSECURE);
                Log.d(TAG,"AccpetThread : Setting up server using" + MY_UUID_INSECURE);
            } catch (IOException e) {
                Log.d(TAG,"AccpetThread : IO Exception" + e.getMessage());
            }
             bluetoothServerSocket = tmp;
        }

        public void run(){
            Log.d(TAG,"run : AcceptThread running");
            BluetoothSocket socket = null;
            try {
                // this is a blocking call it will only return a
                // successful connection or an exception
                Log.d(TAG,"RFCOMM server socket start...");
                socket = bluetoothServerSocket.accept();
                Log.d(TAG,"RFCOMM server socket accepted a connection...");
            } catch (IOException e) {
                Log.d(TAG,"AccpetThread : IO Exception" + e.getMessage());
            }

            if(socket != null){
                connected(socket,mmDevice);
            }

            Log.d(TAG,"End the accept thread");
        }

        public void cancel(){
            Log.d(TAG,"cancel: Cancelling accpet thread");
            try {
                bluetoothServerSocket.close();
            } catch (IOException e) {
                Log.d(TAG,"AccpetThread :Close the accept thread server socket" + e.getMessage());
            }
        }
    }

    /**
     * This thread runs while attempting to make an outgoing connection
     * with a device. It runs straight through; the connection either
     * succeeds or fails.
     */
    private class ConnectThread extends Thread{
        private BluetoothSocket mmSocket;

        public ConnectThread(BluetoothDevice device, UUID uuid){
            mmDevice = device;
            deviceUUID = uuid;
        }

        public void run(){
            Log.d(TAG,"RUN mConnectThread");
            BluetoothSocket tmp = null;

            try {
                // get a BluetoothSocket for a connection with the
                // given bluetooth device
                Log.d(TAG,"trying to create InsecureRfcommSocket using UUID"+deviceUUID);
                tmp = mmDevice.createRfcommSocketToServiceRecord(deviceUUID);
            } catch (IOException e) {
                Log.d(TAG,"ConnectThread :Could not create InsecureRfCommSocket" + e.getMessage());
            }

            mmSocket = tmp;

            // always cancel it since discovery use more resources
            bluetoothAdapter.cancelDiscovery();

            try {
                mmSocket.connect();
                Log.d(TAG,"Connected Thread connected success fully");
                connected(mmSocket,mmDevice);

            } catch (IOException e) {
                try {
                    mmSocket.close();
                    Log.d(TAG,"run : socket closed");
                } catch (IOException e1) {
                    Log.d(TAG,"run : Unable to close the socket" + e1.getMessage());
                }
                Log.d(TAG,"run : ConnectThread: could not connect to UUID :" + e.getMessage());
                try {
                    progressDialog.dismiss();
                }catch (NullPointerException ne){
                    Log.d(TAG,"no dialog box has started");
                }
            }


        }

        public void cancel(){
            Log.d(TAG,"cancel: Cancelling accpet thread");
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.d(TAG,"AccpetThread :Close the accept thread server socket" + e.getMessage());
            }
        }
    }

    /**
     * Start the chat service. Specifically start AcceptThread to begin a
     * session in listening (server) mode. Called by the Activity onResume()
     */
    public synchronized void start() {
        Log.d(TAG,"start");

        if(connectThread != null){
            connectThread.cancel();
            connectThread = null;
        }

        if(mInsecureAcceptThread == null){
            mInsecureAcceptThread = new AcceptThread();
            mInsecureAcceptThread.start();
        }
    }

    public synchronized void startListening() {
        Log.d(TAG,"start");

        if(connectThread != null){
            connectThread.cancel();
            connectThread = null;
        }

        if(mInsecureAcceptThread == null){
            mInsecureAcceptThread = new AcceptThread();
            mInsecureAcceptThread.start();
        }
    }

    /*
     * Initiate a connect thread  as a client, then listing accept thread can catch this
     */

    public void startClient(BluetoothDevice device, UUID uuid){
        Log.d(TAG,"StartClient has started");

        progressDialog = ProgressDialog.show(context,"Connecting bluetooth","Please wait...",true);

        connectThread = new ConnectThread(device,uuid);
        connectThread.start();
    }


    private class ConnectedThread extends Thread{
        private final BluetoothSocket mmSocket;
        private final InputStream inputStream;
        private final OutputStream outputStream;

        private ConnectedThread(BluetoothSocket socket){
            Log.d(TAG,"Connected thread is starting");
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                progressDialog.dismiss();
            }catch (NullPointerException ne){
                Log.d(TAG,"no dialog box has started");
            }

            try {
                tmpIn = mmSocket.getInputStream();
                tmpOut = mmSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            inputStream = tmpIn;
            outputStream = tmpOut;
        }

        public void run(){
            byte[] buffer = new byte[1024]; // buffer store for the stream
            int bytes;                      // bytes return from the read()

            // keep listening to the inputStream until an exception occurs
            while(true){
                // read from the input stream
                try {
                    bytes = inputStream.read(buffer);
                    String inputMessage = new String(buffer,0,bytes);
                    Log.d(TAG,"Input Stream : " + inputMessage);
                } catch (IOException e) {
                    Log.d(TAG,"Error while reading input"+e.getMessage());
                    break;
                }
            }
        }

        // call from mainActivity to send data
        public void write(byte[] bytes){
            String text = new String(bytes, Charset.defaultCharset());
            Log.d(TAG,"Writting to output stream : " + text);

            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG,"Error while writing to the output stream"+e.getMessage());
            }
        }

        public void cancel(){
            try {
                mmSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void connected(BluetoothSocket socket, BluetoothDevice device){
        Log.d(TAG,"Connected starting");

        // start the thread to manage the connections and perform transmission
        ConnectedThread ct = new ConnectedThread(socket);
//        connectedThread = new ConnectedThread(socket);
        connectedThreadPool.add(ct);
        ct.start();
    }

    public void write(byte[] bytes){

        // synchronize a copy of the ConnectedThread
        Log.d(TAG,"write : write is called");
        connectedThread.write(bytes);
    }
}
