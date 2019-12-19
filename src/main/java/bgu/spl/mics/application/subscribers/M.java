package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.*;
import bgu.spl.mics.application.AgentsAvailableEvent;
import bgu.spl.mics.application.GadgetAvailableEvent;
import bgu.spl.mics.application.MissionReceivedEvent;

import java.util.ArrayList;

/**
 * M handles ReadyEvent - fills a report and sends agents to mission.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class M extends Subscriber {

	public M() {
		super("Change_This_Name");
		// TODO Implement this
	}

	@Override
	protected void initialize() {
		// TODO Implement this
		MessageBrokerImpl.getInstance().register(this);
//		MessageBrokerImpl.getInstance().subscribeEvent(MissionReceivedEvent.class,this);

		Callback<MissionReceivedEvent> missionReceivedCallback=c -> {
			//TODO add to the diary
			AgentsAvailableEvent agentsEvent=new AgentsAvailableEvent(c.getAgentsSerialNumbers());
			Future<Boolean> agentsFut= MessageBrokerImpl.getInstance().sendEvent(agentsEvent);
			boolean agentsAvailable=agentsFut.get();

			if(agentsAvailable){	//we acquired the agents
				GadgetAvailableEvent gadgetEvent=new GadgetAvailableEvent(c.getGadget());
				Future<Boolean> gadgetFut=MessageBrokerImpl.getInstance().sendEvent(gadgetEvent);
				boolean gadgetAvailable=gadgetFut.get();

				if(gadgetAvailable){	//we got the gadget- lets do the mission!
					complete(c,true);
				}
				else{
					complete(c,false);
				}
			}	//we didnt got the agents so we wont try to get the gadget
			else{	//TODO timeout means we need to abort mission
				complete(c,false);
			}

		};	//callback
		subscribeEvent(MissionReceivedEvent.class,missionReceivedCallback);
		//TODO continue
		
	}

}
