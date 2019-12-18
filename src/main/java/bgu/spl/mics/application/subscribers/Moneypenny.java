package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Callback;
import bgu.spl.mics.Message;
import bgu.spl.mics.MessageBrokerImpl;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.AgentsAvailableEvent;

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
		MessageBrokerImpl.getInstance().subscribeEvent(AgentsAvailableEvent.class,this);
		Callback<AgentsAvailableEvent> AgentsEvent= c -> {
			
		};

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
