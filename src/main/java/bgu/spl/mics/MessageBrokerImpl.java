package bgu.spl.mics;
import java.util.concurrent.*;

/**
 * The {@link MessageBrokerImpl class is the implementation of the MessageBroker interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBrokerImpl implements MessageBroker {

	private ConcurrentHashMap<Subscriber,LinkedBlockingQueue<Message>> mapOfSubscribers;	//not sure about the key and the value, we need a Queue
	private ConcurrentHashMap<Class<? extends Message>,LinkedBlockingQueue<Subscriber>> mapOfTopics;	//check
	private ConcurrentHashMap<Event,Future> mapOfEvents;

	private static class MessageBrokerHolder{
		private static MessageBrokerImpl instance= new MessageBrokerImpl();
	}

	/**
	 * Retrieves the single instance of this class.
	 */
	public static MessageBroker getInstance() {
		//TODO: Implement this
		return MessageBrokerHolder.instance;
	}


	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, Subscriber m) {	//find the right type and add the sub to the right queue
		// TODO Auto-generated method stub
		mapOfTopics.get(type).add(m);

	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, Subscriber m) {
		// TODO Auto-generated method stub
		mapOfTopics.get(type).add(m);

	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		// TODO Auto-generated method stub
		mapOfEvents.get(e).resolve(result);

	}

	@Override
	public void sendBroadcast(Broadcast b) {
		// TODO Auto-generated method stub

	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		// TODO Auto-generated method stub
		Subscriber sub=mapOfTopics.get(e.getClass()).poll();
		Future future=new Future();
		try {
			mapOfSubscribers.get(sub).put(e);
			mapOfTopics.get(e.getClass()).put(sub);
			mapOfEvents.put(e,future);
		}
		catch (InterruptedException exp){
			Thread.currentThread().interrupt();
		}
		return future;
	}

	@Override
	public void register(Subscriber m) {	//assume we adding the sub name as key,and sub to the map
		// TODO Auto-generated method stub
		mapOfSubscribers.put(m,new LinkedBlockingQueue<Message>());

	}

	@Override
	public void unregister(Subscriber m) {    //similar to the register, but we need to clear his EVENT queue TODO check if its the only 1 subscribe to the topic
		// TODO Auto-generated method stub
		LinkedBlockingQueue<Message> messQ = mapOfSubscribers.get(m);    //get the sub Mess Queue
		while (!messQ.isEmpty()) {
			Message mess = messQ.poll();        //get a mess from his Queue
//			LinkedBlockingQueue<Subscriber> topicSubQ=mapOfTopics.get(mess.getClass());
			Subscriber sub = mapOfTopics.get(mess.getClass()).poll();
			try {
				if (sub.equals(m)) {    //if not the same sub- we will add a message to his Q and push him to the end of the topic Q
					 sub = mapOfTopics.get(mess.getClass()).poll();
				}
					mapOfSubscribers.get(sub).put(mess);
					mapOfTopics.get(mess.getClass()).put(sub);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		mapOfSubscribers.remove(m);
	}

	@Override
	public Message awaitMessage(Subscriber m) throws InterruptedException {
		// TODO Auto-generated method stub
		return mapOfSubscribers.get(m).poll();	//TODO we just removed the first message, but we did nothing
	}

	

}
