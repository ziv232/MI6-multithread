package bgu.spl.mics;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bgu.spl.mics.application.passiveObjects.Agent;
import bgu.spl.mics.application.passiveObjects.Squad;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

public class SquadTest {
	Squad squad;
	ArrayList<String> agentList;
	Agent[] load;
	
    @BeforeEach
    public void setUp(){
    	squad= new Squad();
    	Agent a=new Agent("moshe", "001");
    	Agent b=new Agent("yossi", "002");
    	Agent c=new Agent("avi", "003");
    	agentList=new ArrayList<>();
    	agentList.add(a.getSerialNumber());
    	agentList.add(b.getSerialNumber());
    	
    	load=new Agent[] {a,b,c};
    	squad.load(load);
    	
    }
    

//    @Test
//    public void load(){
//
//    	assertEquals(load.length, squad.getMapSize(), "squad size is incorrect");
//    	for(Agent ag: load) {
//    		assertEquals(true, squad.getAgent(ag.getSerialNumber()).equals(ag), "error");
//    	}
//        //TODO: change this test and add more tests :)
//    }
//
    
//    @Test
//    public void releaseAgents(){
//    	squad.getAgents(agentList);
//    	squad.releaseAgents(agentList);		//change some agents to available
//
//    	for(Agent ag: load) {
//    		String agSerial=ag.getSerialNumber();
//    		if(agentList.contains(agSerial)) {
//    			assertEquals(true,squad.getAgentsMap().get(agSerial).isAvailable() ,"agent availablity should be false");
//    		}
//    		else {
//    			assertEquals(false, squad.getAgentsMap().get(agSerial).isAvailable(),"agent availablity should be true");
//    		}
//    	}
//
//    }
//
//    @Test
//    public void getAgents() {
//    	assertTrue(squad.getAgents(agentList), "an agent avalibalty is wrong");
//    	agentList.remove(0);
//    	assertFalse(squad.getAgents(agentList),"got an Agent tough he's acquired");
//    }
//
//
//    @Test
//    public void getAgentsNames() {
//    	ArrayList<String> recievedNames=(ArrayList<String>) squad.getAgentsNames(agentList);
//    	for(int i=0;i<agentList.size();i++) {
//    		assertTrue(squad.getAgent(agentList.get(i)).getName()==recievedNames.get(i),"wrong name recived ");
//    	}
//
//    }
//
//
    
    
}
