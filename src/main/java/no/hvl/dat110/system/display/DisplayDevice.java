package no.hvl.dat110.system.display;

import no.hvl.dat110.rpc.RPCServer;
import no.hvl.dat110.system.controller.Common;


public class DisplayDevice {
    public static void main(String[] args) {
        System.out.println("Display server starting ...");

        RPCServer rpcServer = new RPCServer(Common.DISPLAYPORT);
        new DisplayImpl((byte) Common.WRITE_RPCID, rpcServer);

        rpcServer.run();
        rpcServer.stop();

        System.out.println("Display server stopping ...");
    }
}