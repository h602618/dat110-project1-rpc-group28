package no.hvl.dat110.messaging;

import no.hvl.dat110.rpc.RPCUtils;

public class Message {
    private byte[] data;

    public Message(byte[] data) {
        if (data != null && data.length < MessageUtils.SEGMENTSIZE) {
            this.data = data;
        }
    }

    public byte[] getData() {
        return this.data;
    }

    public String toString() {
        return RPCUtils.unmarshallString(data);
    }
}