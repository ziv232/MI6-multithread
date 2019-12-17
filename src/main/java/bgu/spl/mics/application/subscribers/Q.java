package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Message;
import bgu.spl.mics.MessageBrokerImpl;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.GadgetAvailableEvent;

/**
 * Q is the only Subscriber\Publisher that has access to the {@link bgu.spl.mics.application.passiveObjects.Inventory}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Q extends Subscriber {

	public Q() {
		super("Change_This_Name");
		// TODO Implement this
	}

	@Override
	protected void initialize() {
		// TODO Implement this
		MessageBrokerImpl.getInstance().register(this);
		MessageBrokerImpl.getInstance().subscribeEvent(GadgetAvailableEvent.class,this);
		try{
			Message mess =MessageBrokerImpl.getInstance().awaitMessage(this);
		}
		catch (InterruptedException e){
			Thread.currentThread().interrupt();
		}
			//TODO continue



	}

}
