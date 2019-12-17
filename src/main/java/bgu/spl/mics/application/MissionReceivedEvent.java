package bgu.spl.mics.application;

import bgu.spl.mics.Event;

public class MissionReceivedEvent implements Event<String> {
    String missionName;
    String agentNumber;
    String gadget;

    public MissionReceivedEvent(String missionName,String agentNumber, String gadget){
        this.missionName=missionName;
        this.agentNumber=agentNumber;
        this.gadget=gadget;
    }


}
