package no.hvl.dat110.system.controller;

import no.hvl.dat110.rpc.RPCClient;
import no.hvl.dat110.rpc.RPCClientStopStub;
import no.hvl.dat110.system.display.DisplayDevice;
import no.hvl.dat110.system.sensor.SensorDevice;

public class Controller {
    private static final int N = 5;

    public static void main(String[] args) {
        DisplayStub display;
        SensorStub sensor;

        RPCClient displayClient, sensorClient;

        System.out.println("Controller starting ...");

        // create  RPC clients for the system
        displayClient = new RPCClient(Common.DISPLAYHOST, Common.DISPLAYPORT);
        sensorClient = new RPCClient(Common.SENSORHOST, Common.SENSORPORT);

        // setup stop methods in the RPC middleware
        RPCClientStopStub stopDisplay = new RPCClientStopStub(displayClient);
        RPCClientStopStub stopSensor = new RPCClientStopStub(sensorClient);

        // denne koden gjør main kjørbar, men gjør testen ukjørbar
        //new Thread(() -> DisplayDevice.main(null)).start();
        //new Thread(() -> SensorDevice.main(null)).start();

        display = new DisplayStub(displayClient);
        sensor = new SensorStub(sensorClient);

        displayClient.connect();
        sensorClient.connect();

        for (int i = 0; i < N; i++) {
            display.write("Sensor value=" + sensor.read());
        }

        stopDisplay.stop();
        stopSensor.stop();

        displayClient.disconnect();
        sensorClient.disconnect();

        System.out.println("Controller stopping ...");
    }
}