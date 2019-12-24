package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Callback;
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
	}
	@Override
	protected void initialize() {
		Callback<GadgetAvailableEvent>gadgetCallback= c ->  {
			if(Inventory.getInstance().getItem(c.getGadget())){		//if gadget exist we return time, else return -1
				complete(c,currTick);
			}
			else{
				complete(c,-1);
			}
		};	//callBack

		subscribeEvent(GadgetAvailableEvent.class,gadgetCallback);	//add callback to the sub callbackMap

		Callback<TickBroadcast> tickCallback = (TickBroadcast c) -> currTick=c.getTick();	//timeCallBack

		subscribeBroadcast(TickBroadcast.class,tickCallback);
	}


}
