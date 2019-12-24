package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Callback;
import bgu.spl.mics.Future;
import bgu.spl.mics.MessageBrokerImpl;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.Messeges.AbortMissionEvent;
import bgu.spl.mics.application.Messeges.AgentsAvailableEvent;
import bgu.spl.mics.application.Messeges.SendAgentsEvent;
import bgu.spl.mics.application.passiveObjects.Squad;

import java.util.ArrayList;
import java.util.List;

/**
 * Only this type of Subscriber can access the squad.
 * Three are several Moneypenny-instances - each of them holds a unique serial number that will later be printed on the report.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Moneypenny extends Subscriber {
	private boolean sender;//TODO check if neccessery.

	public Moneypenny(String name) {
		super(name);
//		System.out.println("MoneyPenny "+getName()+" created on MoneyPenny class");
		sender= Integer.parseInt(getName())%2==1;	//only half MoneyPennys will sendAgents
		// TODO Implement this
	}

	@Override
	protected void initialize() {
		// TODO Implement this
//		MessageBrokerImpl.getInstance().register(this);

		Callback<AgentsAvailableEvent> AgentsEventCallback= (AgentsAvailableEvent c) -> {
			System.out.println("MoneyPenny "+getName()+ " received an event  on MoneyPenny Callback");
			ArrayList<String>AgentsForMission= (ArrayList<String>) c.getAgentsListForMission();
//			while(!Squad.GetInstance().getAgents(AgentsForMission)){
//				try{
//					wait();
//				}
//				catch (InterruptedException e){
//					Thread.currentThread().interrupt();
//				}
//			}
			c.setMp(this);
			boolean answer=Squad.GetInstance().getAgents(AgentsForMission);	//
			System.out.println("MoneyPenny "+getName()+ " got a Squad answer===========");

			complete(c,answer);	//TODO Hypoteticly i return true, but if we need to abort i need to return false
			if(!answer) {
				return;
			}
			c.setAgentsNames(Squad.GetInstance().getAgentsNames(AgentsForMission));

			System.out.println("MoneyPenny "+getName()+ " TESTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");

			Future<Boolean> toSend=c.getFut();
			System.out.println("MoneyPenny "+getName()+ " test2===================================");

			boolean sendOrRelease=toSend.get();
			System.out.println("MoneyPenny "+getName()+ " send or receive");

			if(sendOrRelease){
				Squad.GetInstance().sendAgents((c.getAgentsListForMission()),c.getDuration());
				complete(c,true);
			}
			else{
				Squad.GetInstance().releaseAgents(c.getAgentsListForMission());
				complete(c,false);
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
		//TODO continue
		
	}

}

	//those should be in the run(); of subscriber callback
//
//		try{
//				Message mess =MessageBrokerImpl.getInstance().awaitMessage(this);
//				}
//				catch (InterruptedException e){
//				Thread.currentThread().interrupt();
//				}
