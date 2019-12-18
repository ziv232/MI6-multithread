package bgu.spl.mics.application;

import bgu.spl.mics.Event;
import bgu.spl.mics.MessageBrokerImpl;

public class GadgetAvailableEvent implements Event<String> {
    String gadget;
    boolean avaliable;

    public GadgetAvailableEvent(String gadget){ this.gadget=gadget;}

    public String getGadget(){return this.gadget;}


    public void sendGadgetAvailableEvent(){
        MessageBrokerImpl.getInstance().sendEvent(this);
    }

}
