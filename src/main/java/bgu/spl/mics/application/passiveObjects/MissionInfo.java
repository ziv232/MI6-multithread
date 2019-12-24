package bgu.spl.mics.application.passiveObjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Passive data-object representing information about a mission.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add ONLY private fields and methods to this class.
 */
public class MissionInfo {
	private String missionName;
	private ArrayList<String> serialAgentsNumbers;
	private String gadget;
	private int timeIssued;
	private int timeExpired;
	private int duration;


	public MissionInfo(String missionName, ArrayList<String> agents, String gadget, int timeIssued, int timeExpired, int duration){
		this.missionName=missionName;
		this.serialAgentsNumbers=agents;
		this.gadget=gadget;
		this.timeIssued=timeIssued;
		this.timeExpired=timeExpired;
		this.duration=duration;
//		System.out.println("Mission: "+ getMissionName()+ " created on mission info class");
	}

    /**
     * Sets the name of the mission.
     */
    public void setMissionName(String missionName) {
    }

	/**
     * Retrieves the name of the mission.
     */
	public String getMissionName() {
		return missionName;
	}

    /**
     * Sets the serial agent number.
     */
    public void setSerialAgentsNumbers(List<String> serialAgentsNumbers) {
    }

	/**
     * Retrieves the serial agent number.
     */
	public List<String> getSerialAgentsNumbers() {
		return this.serialAgentsNumbers;
	}

    /**
     * Sets the gadget name.
     */
    public void setGadget(String gadget) {
    }

	/**
     * Retrieves the gadget name.
     */
	public String getGadget() {
		return this.gadget;
	}

    /**
     * Sets the time the mission was issued in milliseconds.
     */
    public void setTimeIssued(int timeIssued) {
    }

	/**
     * Retrieves the time the mission was issued in milliseconds.
     */
	public int getTimeIssued() {
		return this.timeIssued;
	}

    /**
     * Sets the time that if it that time passed the mission should be aborted.
     */
    public void setTimeExpired(int timeExpired) {
    }

	/**
     * Retrieves the time that if it that time passed the mission should be aborted.
     */
	public int getTimeExpired() {
		return this.timeExpired;
	}

    /**
     * Sets the duration of the mission in time-ticks.
     */
    public void setDuration(int duration) {
    }

	/**
	 * Retrieves the duration of the mission in time-ticks.
	 */
	public int getDuration() {
		return this.duration;
	}
}
