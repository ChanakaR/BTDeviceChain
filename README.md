# BTDeviceChain
Android application which helps to keep your BT devices together

## Introduction 
Application behave in to modes

### 1. Master mode
In master mode user can
 1. Search for devices
 2. Search for paried devices
 3. Connect with a device
 4. Start connection (talking to each other) with a device
 
Master device should choose the devices from the list and connect to them. Then master device start the communication.

### 2. Slave mode
Once you start the app in slave mode, app will open a port and starting to wait for a connection. Once master send request to the slave, it will stop the waiting and connect with the master.
