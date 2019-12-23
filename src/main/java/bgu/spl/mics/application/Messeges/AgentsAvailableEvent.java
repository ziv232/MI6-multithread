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
        agentsNames=new ArrayList<>();
        agentsForMission.addAll(agentsList);
    }

    public void setMp(Moneypenny mp) {
        this.mp = mp;
    }

    public Moneypenny getMp(){return mp;}

    public List<String>getAgentsListForMission(){return agentsForMission;}

    public List<String> getNames(){return agentsNames;}

    public void setAgentsNames(List<String>names){
        agentsNames.addAll(names);
    }

    public void sendAgentsAvailableEvent(){ //TODO CHECK
        MessageBrokerImpl.getInstance().sendEvent(this);
    }


}
