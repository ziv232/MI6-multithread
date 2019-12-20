package bgu.spl.mics.application.Messeges;

import bgu.spl.mics.Event;
import bgu.spl.mics.MessageBrokerImpl;

public class GadgetAvailableEvent implements Event<Boolean> {
    String gadget;
    boolean avaliable;
    private int time;

    public GadgetAvailableEvent(String gadget){ this.gadget=gadget;}

    public String getGadget(){return this.gadget;}


    public void sendGadgetAvailableEvent(){
        MessageBrokerImpl.getInstance().sendEvent(this);
    }

}
