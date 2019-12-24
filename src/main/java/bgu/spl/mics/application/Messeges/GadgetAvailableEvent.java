package bgu.spl.mics.application.Messeges;

import bgu.spl.mics.Event;

public class GadgetAvailableEvent implements Event<Integer> {
    private String gadget;
    private int time;


    //Constructor
    public GadgetAvailableEvent(String gadget){ this.gadget=gadget;}


    public String getGadget(){return this.gadget;}

    public void setTime(int Q_time){time=Q_time;}

    public int getTime(){return time;}


}
