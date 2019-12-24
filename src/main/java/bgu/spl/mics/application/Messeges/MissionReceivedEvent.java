package bgu.spl.mics.application.Messeges;

import bgu.spl.mics.Event;

import java.util.ArrayList;

public class MissionReceivedEvent implements Event<Boolean> {
    private String missionName;
    private ArrayList<String> agentsSerialNumbers;
    private String gadget;
    private int timeIssued;
    private int timeExpired;
    private int duration;

    public MissionReceivedEvent(String missionName, ArrayList<String> agentsSerialNumbers, String gadget,int timeIssued, int timeExpired, int duration){
        this.missionName=missionName;
        this.agentsSerialNumbers= new ArrayList<String>();
        for(String serNum:agentsSerialNumbers){
            this.agentsSerialNumbers.add(serNum);
        }
        this.gadget=gadget;
        this.timeIssued=timeIssued;
        this.timeExpired=timeExpired;
        this.duration=duration;
    }

    public String getMissionName(){ return missionName;}

    public String getGadget() {
        return gadget;
    }

    public ArrayList<String> getAgentsSerialNumbers() {
        return agentsSerialNumbers;
    }

    public int getTimeIssued(){
        return timeIssued;
    }

    public int getTimeExpired(){ return timeExpired;}

    public int getDuration(){return duration;}
}
