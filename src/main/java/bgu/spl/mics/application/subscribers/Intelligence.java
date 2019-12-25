package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Callback;
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
	private int currTick;
	private int nextMissionTime;

	public Intelligence(String name) {
		super(name);
		this.currTick =0;
		this.eventList = new ArrayList<>();
	}

	@Override
	protected void initialize() {
		for (MissionInfo mission : this.missions) {
			eventList.add(new MissionReceivedEvent(mission.getMissionName(),
					(ArrayList<String>) mission.getSerialAgentsNumbers(), mission.getGadget(),
					mission.getTimeIssued(), mission.getTimeExpired(), mission.getDuration()));
		}

		Callback<TickBroadcast> tickCallBack = (TickBroadcast c) -> {
			currTick=c.getTick();
			if (c.isTerminated()) {
				terminate();
				return;
			}
			if(eventList.size()>0){
				MissionReceivedEvent toSend = eventList.get(0);	//take first mission without remove
				nextMissionTime=(toSend.getTimeIssued());	//get the mission time to issued

				while (currTick==(nextMissionTime)) {
					eventList.remove(0);
					getSimplePublisher().sendEvent(toSend);

					if (eventList.size() == 0) {
						break;
					} else {
						toSend = eventList.get(0);
						nextMissionTime = (toSend.getTimeIssued());
					}
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

}
