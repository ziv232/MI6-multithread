package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Broadcast;

import java.util.concurrent.atomic.AtomicInteger;

public class TickBroadcast implements Broadcast {
    private int tick;

    public TickBroadcast(int tick){
        this.tick =tick;
    }

    public int getTick(){
        return tick;
    }

}
