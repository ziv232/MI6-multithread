package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Broadcast;

import java.util.concurrent.atomic.AtomicInteger;

public class TickBroadcast implements Broadcast {
    private AtomicInteger tick;

    public TickBroadcast(int tick){
        this.tick.set(tick);
    }

    public AtomicInteger getTick(){
        return tick;
    }

}
