package bgu.spl.mics.application;

import bgu.spl.mics.Event;
import bgu.spl.mics.MessageBrokerImpl;

import java.util.ArrayList;
import java.util.List;

public class AgentsAvailableEvent implements Event<String> {
    ArrayList<String> agentsForMission;
    public AgentsAvailableEvent(List<String> agentsList){
        agentsForMission=new ArrayList<>(); //getting an agentsList and init the field
        for(String agent:agentsList){
            agentsForMission.add(agent);
        }
    }

    public void sendAgentsAvailableEvent(){ //TODO CHECK
        MessageBrokerImpl.getInstance().sendEvent(this);
    }


}
