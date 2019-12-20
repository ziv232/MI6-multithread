package bgu.spl.mics.application.Messeges;

import bgu.spl.mics.Event;
import bgu.spl.mics.MessageBrokerImpl;
import bgu.spl.mics.application.subscribers.Moneypenny;
import java.util.ArrayList;
import java.util.List;

public class AgentsAvailableEvent implements Event<Boolean> {
    ArrayList<String> agentsForMission;
    ArrayList<String> agentsNames;
    private Moneypenny mp;

    public AgentsAvailableEvent(List<String> agentsList){
        agentsForMission=new ArrayList<>(); //getting an agentsList and init the field
        for(String agent:agentsList){
            agentsForMission.add(agent);
        }
    }

    public void setMp(Moneypenny mp) {
        this.mp = mp;
    }

    public Moneypenny getMp(){return mp;}

    public synchronized List<String>getAgentsListForMission(){return agentsForMission;}
    public void sendAgentsAvailableEvent(){ //TODO CHECK
        MessageBrokerImpl.getInstance().sendEvent(this);
    }


}
