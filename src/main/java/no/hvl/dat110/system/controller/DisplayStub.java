package no.hvl.dat110.system.controller;

import no.hvl.dat110.rpc.*;

public class DisplayStub extends RPCLocalStub {
    public DisplayStub(RPCClient rpcclient) {
        super(rpcclient);
    }

    public void write(String message) {
        // implement marshalling, call and unmarshalling for write RPC method

        byte[] marshalled = RPCUtils.marshallString(message);
        byte[] reply = rpcclient.call((byte) Common.WRITE_RPCID, marshalled);

        RPCUtils.unmarshallVoid(reply);
    }
}