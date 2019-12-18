package bgu.spl.mics.application;

import bgu.spl.mics.Event;

import java.util.ArrayList;

public class MissionReceivedEvent implements Event<Boolean> {
    String missionName;
    ArrayList<String> agentsSerialNumbers;
    String gadget;

    public MissionReceivedEvent(String missionName, ArrayList<String> agentsSerialNumbers, String gadget){
        this.missionName=missionName;
        this.agentsSerialNumbers=new ArrayList<>();
        for(String serNum:agentsSerialNumbers){
            this.agentsSerialNumbers.add(serNum);
        }
        this.gadget=gadget;
    }

    public String getGadget() {
        return gadget;
    }

    public ArrayList<String> getAgentsSerialNumbers() {
        return agentsSerialNumbers;
    }
}
