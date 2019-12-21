package bgu.spl.mics.application.Messeges;

import bgu.spl.mics.Event;

import java.util.ArrayList;
import java.util.List;

public class SendAgentsEvent implements Event<Boolean> {
    int missionTime;
    List<String> agentsForMission;
    List<String> agentsNames;

    public SendAgentsEvent(int missionTime, List<String> agents){
        this.missionTime=missionTime;
        agentsNames=new ArrayList<>();
        agentsForMission=new ArrayList<>();
        for(String agent:   agents){
            agentsForMission.add(agent);
        }
    }

    public int getMissionTime(){return missionTime;}

    public List<String> getAgentsForMission() {
        return agentsForMission;
    }

    public List<String> getAgentsNames(){ return agentsNames;}

    public void setAgentsNames(List<String> names){
        this.agentsNames.addAll(names);
    }
}
