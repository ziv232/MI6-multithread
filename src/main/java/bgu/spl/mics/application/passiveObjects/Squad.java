package bgu.spl.mics.application.passiveObjects;
import java.util.*;

/**
 * Passive data-object representing a information about an agent in MI6.
 * You must not alter any of the given public methods of this class. 
 * <p>
 * You may add ONLY private fields and methods to this class.
 */
public class Squad {

	private static class SquadHolder{
		private static Squad instance=new Squad();
	}

	private Map<String, Agent> agents= new HashMap<>();

	/**
	 * Retrieves the single instance of this class.
	 */
	public static Squad GetInstance() {return SquadHolder.instance;}


	/**
	 * Initializes the squad. This method adds all the agents to the squad.
	 * <p>
	 * @param agents 	Data structure containing all data necessary for initialization
	 * 						of the squad.
	 */
	public synchronized void load (Agent[] agents) {	//TODO CHECK AGENTS THAT DOESN'T START WITH 00
		for (Agent agent : agents) {
			this.agents.put(agent.getSerialNumber(), agent);    //add agent to map
		}
	}

	/**
	 * Releases agents.
	 */
	public void releaseAgents(List<String> serials){	//TODO check what happens when we release while other try to acquire and if needed to notifyall after
//		for(String serial: serials) {
//			if(!agents.containsKey(serial)) {	//for each agent, if is in the squad- release.
//				System.out.println(serial+" is not existed");
//				return;
//			}
//		}
		for(String serial: serials) {	//for each agent, if is in the squad- release.
			agents.get(serial).release();
		}
//		notifyAll();	//released all the agents, now threads can look for them
	}

	/**
	 * simulates executing a mission by calling sleep.
	 * @param time   milliseconds to sleep
	 */
	public void sendAgents(List<String> serials, int time)  {
		try {
			Thread.sleep(time*100);
//			System.out.println("Squad, func sendAgents, Thread finished sleeping");

			releaseAgents(serials);
		}
		catch (InterruptedException e){
			Thread.currentThread().interrupt();
		}

	}

	/**
	 * acquires an agent, i.e. holds the agent until the caller is done with it
	 * @param serials   the serial numbers of the agents
	 * @return ‘false’ if an agent of serialNumber ‘serial’ is missing, and ‘true’ otherwise
	 */
	public boolean getAgents(List<String> serials) {    //TODO we need to remove sync
		// TODO Implement this
		Collections.sort(serials);//Sorting the List of SerialNumbers to AVOID DEADLOCK.
		for (String num : serials) {
			if (!agents.containsKey(num)) {    //one of the agents does not exists
				return false;
			}
		}
//		synchronized (this) {
			boolean done = true;
//			while (!done) {
				for (String seri:serials) {
					agents.get(seri).acquire();


//					if (!getAgent(serials.get(i)).isAvailable()) {    //if agent is unavailable- we check the loop from the start
//						try {
//							wait();        //TODO TODO TODO WAIT!!!!!
//							i = -1;
//						} catch (InterruptedException e) {
//							Thread.currentThread().interrupt();
//						}
//					}
				}

//			while(!getAgent(serial).isAvailable()) {	//we got only 1 semaphore therefore
//				if (getAgent(serial).isAvailable()) {
//					getAgent(serial).acquire();
//				}
//
//			}
//				done = acquireAgents(serials);
//			}
			return done;
//		}
	}



    /**
     * gets the agents names
     * @param serials the serial numbers of the agents
     * @return a list of the names of the agents with the specified serials.
     */
    public List<String> getAgentsNames(List<String> serials){
		List<String> toReturn= new ArrayList<>();
		for(String serialNum: serials){
			toReturn.add(agents.get(serialNum).getName());
		}
	    return toReturn;
    }
}
