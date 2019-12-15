package bgu.spl.mics.application.passiveObjects;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Passive data-object representing a information about an agent in MI6.
 * You must not alter any of the given public methods of this class. 
 * <p>
 * You may add ONLY private fields and methods to this class.
 */
public class Squad {

	private Map<String, Agent> agents;
//	private static Squad instance=null;
	
	
//	private Squad() {
//		agents=new TreeMap<String, Agent>();
//	}

	/**
	 * Retrieves the single instance of this class.
	 */
	public static synchronized Squad getInstance() {
		//TODO: Implement this
//		if(instance==null) {
//			instance=new Squad();
//		}
//		
//		return instance;
		return null;
	}
	
	
	public int getMapSize() {
		return agents.size();
	}
	
	
	public Map<String,Agent> getAgentsMap() {
		return agents;
	}
	
	public Agent getAgent(String serialNumber) {
		return agents.get(serialNumber);
	}

	/**
	 * Initializes the squad. This method adds all the agents to the squad.
	 * <p>
	 * @param agents 	Data structure containing all data necessary for initialization
	 * 						of the squad.
	 */
	public void load (Agent[] agents) {
		for(int i=0;i<agents.length;i++) {
			this.agents.put(agents[i].getSerialNumber(), agents[i]);	//add agent to map			
		}
	}

	/**
	 * Releases agents.
	 */
	public void releaseAgents(List<String> serials){
		for(String serial: serials) {
			if(!agents.containsKey(serial)) {	//for each agent, if is in the squad- release.
				System.out.println(serial+" is not existed");
				return;
			}
		}
		for(String serial: serials) {	//for each agent, if is in the squad- release.
			agents.get(serial).release();
		}
		// TODO Implement this
	}

	/**
	 * simulates executing a mission by calling sleep.
	 * @param time   milliseconds to sleep
	 */
	public void sendAgents(List<String> serials, int time){
		// TODO Implement this
	}

	/**
	 * acquires an agent, i.e. holds the agent until the caller is done with it
	 * @param serials   the serial numbers of the agents
	 * @return ‘false’ if an agent of serialNumber ‘serial’ is missing, and ‘true’ otherwise
	 */
	public boolean getAgents(List<String> serials){
		// TODO Implement this
		for(int i=0;i<serials.size();i++) {		//check for each number if it exists
			if(getAgent(serials.get(i))==null) {
				//TODO AGENT NOT EXIST
				System.out.println("agent"+serials.get(i)+" is not exist");
				return false;
			}
			else {
				if(!getAgent(serials.get(i)).isAvailable())	//check that each agent is avaliable
					return false;
			}
		}
		for(int i=0;i<serials.size();i++) {	//acquire each agent of the list
			getAgent(serials.get(i)).acquire();
		}
		return true;
	}

    /**
     * gets the agents names
     * @param serials the serial numbers of the agents
     * @return a list of the names of the agents with the specified serials.
     */
    public List<String> getAgentsNames(List<String> serials){
        // TODO Implement this
	    return null;
    }

}
