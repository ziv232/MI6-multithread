package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MessageBrokerImpl;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.Messeges.TickBroadcast;
import bgu.spl.mics.application.Messeges.MissionReceivedEvent;
import bgu.spl.mics.application.passiveObjects.MissionInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * A Publisher only.
 * Holds a list of Info objects and sends them
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Intelligence extends Subscriber {
	private List<MissionInfo> missions;
	private List<MissionReceivedEvent> eventList;
	int currTick;
	int nextMissionTime;

	public Intelligence(String name) {
		super(name);
		this.currTick =0;
		this.eventList = new ArrayList<MissionReceivedEvent>();
		System.out.println("Intelligence "+getName()+" created on intelligence class");
		// TODO Implement this
	}

	public Intelligence(String name ,List<MissionInfo> missions){
		super(name);
	}

	@Override
	protected void initialize() {
		MessageBrokerImpl.getInstance().register(this);
		for(int i=0;i<this.missions.size();i++){
			eventList.add(new MissionReceivedEvent(missions.get(i).getMissionName(),
					(ArrayList<String>) missions.get(i).getSerialAgentsNumbers(),missions.get(i).getGadget(),
					missions.get(i).getTimeIssued(),missions.get(i).getTimeExpired(),missions.get(i).getDuration()));
		}

		Callback<TickBroadcast> tickCallBack = (TickBroadcast c) -> {
			if(eventList.size()>0){
				MissionReceivedEvent toSend = eventList.get(0);	//take first mission without remove
				nextMissionTime=(toSend.getTimeIssued());	//get the mission time to issued
//				try {
//					TickBroadcast msg=((TickBroadcast)MessageBrokerImpl.getInstance().awaitMessage(this));
					currTick=c.getTick();
//				System.out.println("tick time "+currTick+ " of intelligence tickCallback");

				if (currTick==(nextMissionTime)) {
//						msg=(((TickBroadcast) (MessageBrokerImpl.getInstance().awaitMessage(this))));
//						currTick=msg.getTick();
//						System.out.println(currTick);
						eventList.remove(0);
						MessageBrokerImpl.getInstance().sendEvent(toSend);	//TODO WE DID NOT TAKE THE FUTURE AND PASTED IT TO 'M'
					}
//				}
//				catch (InterruptedException e){
//					Thread.currentThread().interrupt();
//				}
			}
			else {
				if (c.isTerminated()) {
					System.out.println("intelligence "+getName()+" is going to terminate");
					terminate();
				}
			}
		};

		subscribeBroadcast(TickBroadcast.class,tickCallBack);
	}
	public List<MissionInfo> getMissions(){
		return this.missions;
	}

	public void setMissions(List<MissionInfo> missions){
		this.missions=missions;
	}

	public void setEventList(List<MissionReceivedEvent> list){
		this.eventList=list;
	}

	public List<MissionReceivedEvent> getEventList(){
		return eventList;
	}

}
