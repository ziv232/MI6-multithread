package bgu.spl.mics.application.publishers;
import bgu.spl.mics.Broadcast;
import bgu.spl.mics.MessageBrokerImpl;
import bgu.spl.mics.Publisher;
import bgu.spl.mics.application.subscribers.TickBroadcast;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * TimeService is the global system timer There is only one instance of this Publisher.
 * It keeps track of the amount of ticks passed since initialization and notifies
 * all other subscribers about the current time tick using {@link Tick Broadcast}.
 * This class may not hold references for objects which it is not responsible for.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class TimeService extends Publisher {
	private int step;
	private int duration;
	private AtomicInteger tick;
	//private long startTime;



	public TimeService(int duration) {
		super("clock");
		this.step = 100;
		this.duration = duration;
		this.tick.set(1);
		//this.startTime = 0;
	}

	@Override
	protected void initialize() {
		Thread thread = new Thread(this);
		
	}

	@Override
	public void run() {
		//this.startTime=System.currentTimeMillis();
		while(tick.get()<duration){
			try { Thread.sleep(step); }
			catch (InterruptedException e){ throw new IllegalStateException(e.getMessage()); }
			tick.incrementAndGet();

			MessageBrokerImpl.getInstance().sendBroadcast((Broadcast) new TickBroadcast(tick.get()));

			//int currentTick = (int)((System.currentTimeMillis()-startTime)/step);
			//tick.compareAndSet(tick,currentTick);

		}

	}

}
