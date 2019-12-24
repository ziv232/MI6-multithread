package bgu.spl.mics.application.Messeges;

import bgu.spl.mics.Event;
import bgu.spl.mics.Future;
import bgu.spl.mics.application.subscribers.Moneypenny;
import java.util.ArrayList;
import java.util.List;

public class AgentsAvailableEvent implements Event<Boolean> {
    private ArrayList<String> agentsForMission;
    private ArrayList<String> agentsNames;
    private Moneypenny mp;
    private Future<Boolean> toSend;
    private int Duration;
    //Constructor
    public AgentsAvailableEvent(List<String> agentsList,int duration){
        agentsForMission=new ArrayList<>(); //getting an agentsList and init the field
        agentsNames=new ArrayList<>();
        agentsForMission.addAll(agentsList);
        toSend=new Future<>();
        Duration=duration;
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

    public Future<Boolean> getFut(){return toSend;}

    public void setToSend(boolean bool){toSend.resolve(bool);}
    public int getDuration(){return Duration;}


}
