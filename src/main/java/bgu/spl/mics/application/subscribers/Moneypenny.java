package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Callback;
import bgu.spl.mics.Future;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.Messeges.AgentsAvailableEvent;
import bgu.spl.mics.application.passiveObjects.Squad;

import java.util.ArrayList;

/**
 * Only this type of Subscriber can access the squad.
 * Three are several Moneypenny-instances - each of them holds a unique serial number that will later be printed on the report.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Moneypenny extends Subscriber {

	public Moneypenny(String name) {
		super(name);
	}

	@Override
	protected void initialize() {
		Callback<AgentsAvailableEvent> AgentsEventCallback= (AgentsAvailableEvent c) -> {
			ArrayList<String>AgentsForMission= (ArrayList<String>) c.getAgentsListForMission();
			c.setMp(this);
			boolean answer=Squad.GetInstance().getAgents(AgentsForMission);	//
//			System.out.println("MoneyPenny "+getName()+ " got a Squad answer===========");

			complete(c,answer);
			if(!answer) {
				return;
			}
			c.setAgentsNames(Squad.GetInstance().getAgentsNames(AgentsForMission));
			Future<Boolean> toSend=c.getFut();
			boolean sendOrRelease=toSend.get();
//			System.out.println("MoneyPenny "+getName()+ " send or receive");

			if(sendOrRelease){
				Squad.GetInstance().sendAgents((c.getAgentsListForMission()),c.getDuration());
				complete(c,true);	//TO DO check!!!!!
			}
			else{
				Squad.GetInstance().releaseAgents(c.getAgentsListForMission());
//				complete(c,false);
			}
		};	//callback

//		Callback<SendAgentsEvent> SendCallBack= (SendAgentsEvent c) -> {
//			Squad.GetInstance().sendAgents(c.getAgentsForMission(),c.getMissionTime());
//			List<String> serials=Squad.GetInstance().getAgentsNames(c.getAgentsForMission());	//moneyPenny gets the agentsSerials from Squad
////			c.setAgentsNames(serials);
//			complete(c,serials);
//
//
//		};
//
//		Callback<AbortMissionEvent> AbortCallBack=c -> {
//			Squad.GetInstance().releaseAgents(c.getAgentsToRelease());
//			complete(c,true);
//		};

//		if (!sender) {
			subscribeEvent(AgentsAvailableEvent.class, AgentsEventCallback);
//		}
//		else{
//			subscribeEvent(SendAgentsEvent.class,SendCallBack);
//			subscribeEvent(AbortMissionEvent.class,AbortCallBack);
//		}

	}

}

