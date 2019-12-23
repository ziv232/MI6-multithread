package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MessageBrokerImpl;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.Messeges.GadgetAvailableEvent;
import bgu.spl.mics.application.Messeges.TickBroadcast;
import bgu.spl.mics.application.passiveObjects.Inventory;

/**
 * Q is the only Subscriber\Publisher that has access to the {@link bgu.spl.mics.application.passiveObjects.Inventory}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Q extends Subscriber {
	private int currTick=0;

	public Q(String name) {
		super(name);
		System.out.println("Q "+getName()+" created on Q class");
		// TODO Implement this
	}

	public int getCurrTick() {
		return currTick;
	}

	@Override
	protected void initialize() {
		// TODO Implement this
//		MessageBrokerImpl.getInstance().register(this);
		Callback<GadgetAvailableEvent>gadgetCallback= c ->  {
//			c.setTime(getCurrTick());	//update when Q receive the event

			if(Inventory.getInstance().getItem(c.getGadget())){		//if gadget exist we return time, else return -1
				complete(c,currTick);		//TODO CHECK if complete of broker or this
			}
			else{
				complete(c,-1);
			}
		};	//callBack

		subscribeEvent(GadgetAvailableEvent.class,gadgetCallback);	//add callback to the sub callbackMap
		//TODO continue

		Callback<TickBroadcast> tickCallback = (TickBroadcast c) -> {
//			try {
//				TickBroadcast tick = ((TickBroadcast) MessageBrokerImpl.getInstance().awaitMessage(this));
				currTick=c.getTick();
//				System.out.println("Q tickTime "+getCurrTick()+ "on Q tickCallback ");
//			}
//			catch (InterruptedException e){
//				Thread.currentThread().interrupt();
//			}
		};	//timeCallBack
		subscribeBroadcast(TickBroadcast.class,tickCallback);
	}


}
