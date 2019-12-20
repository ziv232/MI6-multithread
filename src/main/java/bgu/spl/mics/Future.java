package bgu.spl.mics;

import java.util.concurrent.TimeUnit;

/**
 * A Future object represents a promised result - an object that will
 * eventually be resolved to hold a result of some operation. The class allows
 * Retrieving the result once it is available.
 * 
 * Only private methods may be added to this class.
 * No public constructor is allowed except for the empty constructor.
 */
public class Future<T> {
	
	private boolean isdone;
	private T answer;
	
	/**
	 * This should be the the only public constructor in this class.
	 */
	public Future() {
		//TODO: implement this
		isdone=false;
		answer=null;
	}
	
	/**
     * retrieves the result the Future object holds if it has been resolved.
     * This is a blocking method! It waits for the computation in case it has
     * not been completed.
     * <p>
     * @return return the result of type T if it is available, if not wait until it is available.
     * 	       
     */
	public synchronized T get() {
		//TODO: implement this.
		while(!isDone()) {
			try{
				wait();
			}
			catch (InterruptedException e){
				Thread.currentThread().interrupt();
				System.out.println("future interrupted");
			}
		}
		return answer;
	}
	
	/**
     * Resolves the result of this Future object.
     */
	public synchronized void resolve (T result) {//Changed to Synchronized
		isdone=true;
		answer=result;
		notifyAll();//Notify to all the threads that waiting for a result.
		
	}
	
	/**
     * @return true if this object has been resolved, false otherwise
     */
	public boolean isDone() {
		return isdone;
	}
	
	/**
     * retrieves the result the Future object holds if it has been resolved,
     * This method is non-blocking, it has a limited amount of time determined
     * by {@code timeout}
     * <p>
     * @param timeout 	the maximal amount of time units to wait for the result.
     * @param unit		the {@link TimeUnit} time units to wait.
     * @return return the result of type T if it is available, if not, 
     * 	       wait for {@code timeout} TimeUnits {@code unit}. If time has
     *         elapsed, return null.
     */
	public T get(long timeout, TimeUnit unit) {
		//TODO: implement this.
		long miliSec=unit.toMillis(timeout);
		
		if(!isDone()) {		//TODO check if instead of while
			try{
				wait(miliSec);
			}
			catch (InterruptedException e){
				Thread.currentThread().interrupt();
			}
		}
		return answer;
	}

}
