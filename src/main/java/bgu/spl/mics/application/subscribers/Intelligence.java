package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MessageBrokerImpl;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.MissionReceivedEvent;
import bgu.spl.mics.application.passiveObjects.MissionInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
	AtomicInteger currTick;
	AtomicInteger nextMission;

	public Intelligence(String name) {
		super(name);
		this.currTick.set(0);
		this.eventList = new ArrayList<MissionReceivedEvent>();
		// TODO Implement this
	}

	public Intelligence(String name ,List<MissionInfo> missions){
		super(name);

	}

	@Override
	protected void initialize() {
		MessageBrokerImpl.getInstance().register(this);
		for(int i=0;i<this.missions.size();i++){
			eventList.add(new MissionReceivedEvent(missions.get(i).getMissionName(), (ArrayList<String>) missions.get(i).getSerialAgentsNumbers(),
					missions.get(i).getGadget(),missions.get(i).getTimeIssued(), missions.get(i).getTimeExpired(),
					missions.get(i).getDuration()));
		}

		Callback<TickBroadcast> tickCallBack = c -> {
			while(eventList.size()>0){
				MissionReceivedEvent toSend = eventList.remove(0);	//dequeue first mission
				nextMission.set(toSend.getTimeIssued());	//get the mission time to issued
				try {
					while (!((TickBroadcast) (MessageBrokerImpl.getInstance().awaitMessage(this))).getTick().equals(nextMission)) {
					}
					MessageBrokerImpl.getInstance().sendEvent(toSend);	//TODO WE DID NOT TAKE THE FUTURE AND PASTED IT TO 'M'
				}
				catch (InterruptedException e){
					Thread.currentThread().interrupt();
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
