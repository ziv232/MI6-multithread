package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.passiveObjects.MissionInfo;

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

	public Intelligence(String name) {
		super(name);
		// TODO Implement this
	}

	public Intelligence(String name ,List<MissionInfo> missions){
		super(name);

	}

	@Override
	protected void initialize() {
		// TODO Implement this
	}
	public List<MissionInfo> getMissions(){
		return this.missions;
	}

	public void setMissions(List<MissionInfo> missions){
		this.missions=missions;
	}

}
