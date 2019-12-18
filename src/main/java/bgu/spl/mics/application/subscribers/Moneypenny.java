package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Callback;
import bgu.spl.mics.Message;
import bgu.spl.mics.MessageBrokerImpl;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.AgentsAvailableEvent;
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

	public Moneypenny() {
		super("Change_This_Name");
		// TODO Implement this
	}

	@Override
	protected void initialize() {
		// TODO Implement this
		MessageBrokerImpl.getInstance().register(this);

		Callback<AgentsAvailableEvent> AgentsEventCallback= c -> {
			ArrayList<String>AgentsForMission= (ArrayList<String>) c.getAgentsListForMission();
			while(!Squad.GetInstance().getAgents(AgentsForMission)){
				try{
					wait();
				}
				catch (InterruptedException e){
					Thread.currentThread().interrupt();
				}
			}
			complete(c,true);	//TODO Hypoteticly i return true, but if we need to abort i need to return false
		};	//callback

		subscribeEvent(AgentsAvailableEvent.class,AgentsEventCallback);
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
