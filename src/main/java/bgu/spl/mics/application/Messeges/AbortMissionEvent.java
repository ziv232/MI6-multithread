package bgu.spl.mics.application.Messeges;

import bgu.spl.mics.Event;
import java.util.ArrayList;
import java.util.List;

public class AbortMissionEvent implements Event<Boolean> {
    private List<String> agentsToRelease;

    public AbortMissionEvent(List<String> agents){
        agentsToRelease=new ArrayList<>();
        for(String agent: agents){
            agentsToRelease.add(agent);
        }
    }

}
