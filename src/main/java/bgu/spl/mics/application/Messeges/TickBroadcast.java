package bgu.spl.mics.application.Messeges;

import bgu.spl.mics.Broadcast;

public class TickBroadcast implements Broadcast {
    private int tick;
    private boolean terminated;

    public TickBroadcast(int tick,boolean terminated){
        this.tick =tick;
        this.terminated=terminated;
    }

    public int getTick(){
        return tick;
    }
    public boolean isTerminated(){return terminated;}

}
