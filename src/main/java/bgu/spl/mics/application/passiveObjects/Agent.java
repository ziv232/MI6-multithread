package bgu.spl.mics.application.passiveObjects;

/**
 * Passive data-object representing a information about an agent in MI6.
 * You must not alter any of the given public methods of this class. 
 * <p>
 * You may add ONLY private fields and methods to this class.
 */
public class Agent {
	
	private boolean available=true;
	private String name;
	private String serialNumber;

	/**
	 * Sets the serial number of an agent.
	 */
	
	public Agent(String name,String serialNumber) {	//constructor
		this.setName(name);
		this.setSerialNumber(serialNumber);
	}
	
	public void setSerialNumber(String serialNumber) {
		// TODO Implement this
		this.serialNumber=serialNumber;
	}

	/**
     * Retrieves the serial number of an agent.
     * <p>
     * @return The serial number of an agent.
     */
	public String getSerialNumber() {
		// TODO Implement this
		return this.serialNumber;
	}

	/**
	 * Sets the name of the agent.
	 */
	public void setName(String name) {
		// TODO Implement this
		this.name=name;
	}

	/**
     * Retrieves the name of the agent.
     * <p>
     * @return the name of the agent.
     */
	public String getName() {
		// TODO Implement this
		return this.name;
	}

	/**
     * Retrieves if the agent is available.
     * <p>
     * @return if the agent is available.
     */
	public boolean isAvailable() {
		// TODO Implement this
		return available;
	}

	/**
	 * Acquires an agent.
	 */
	public void acquire(){
		// TODO Implement this
		available=false;
	}

	/**
	 * Releases an agent.
	 */
	public void release(){
		// TODO Implement this
		available=true;
	}
}
