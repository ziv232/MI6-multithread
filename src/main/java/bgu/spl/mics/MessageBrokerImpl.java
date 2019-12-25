package bgu.spl.mics;
import bgu.spl.mics.application.Messeges.*;

import java.util.Map;
import java.util.concurrent.*;

/**
 * The {@link MessageBrokerImpl class is the implementation of the MessageBroker interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBrokerImpl implements MessageBroker {

	private ConcurrentHashMap<Subscriber,LinkedBlockingDeque<Message>> mapOfSubscribers;	//not sure about the key and the value, we need a Queue
	private ConcurrentHashMap<Class<? extends Message>,LinkedBlockingQueue<Subscriber>> mapOfTopics;	//check
	private ConcurrentHashMap<Event,Future> mapOfEvents;

	private static class MessageBrokerHolder{
		private static MessageBrokerImpl instance= new MessageBrokerImpl();
	}
	private MessageBrokerImpl(){
		mapOfSubscribers=new ConcurrentHashMap<>();
		mapOfTopics=new ConcurrentHashMap<>();
		mapOfEvents=new ConcurrentHashMap<>();
		//==========add every topic to map===========
		mapOfTopics.putIfAbsent(TickBroadcast.class, new LinkedBlockingQueue<>());

	}


	/**
	 * Retrieves the single instance of this class.
	 */
	public static MessageBroker getInstance() {
		return MessageBrokerHolder.instance;
	}


	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, Subscriber m) {	//find the right type and add the sub to the right queue
		subscribeMessage(type,m);

	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, Subscriber m) {
		subscribeMessage(type,m);

	}

	private void subscribeMessage(Class<? extends Message> type,Subscriber m){
		mapOfTopics.putIfAbsent(type, new LinkedBlockingQueue<>());
//		synchronized (mapOfTopics.get(type)){
			LinkedBlockingQueue<Subscriber> Queue=mapOfTopics.get(type);
			if(!Queue.contains(m)){
				Queue.add(m);
//			}
		}
	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		mapOfEvents.get(e).resolve(result);

	}

	@Override
	public void sendBroadcast(Broadcast b) {
		LinkedBlockingQueue<Subscriber> subsToSend=mapOfTopics.get(b.getClass());
		if (subsToSend==null)
			return;
		for(Subscriber sub: subsToSend){
			LinkedBlockingDeque<Message> subMessQueue=mapOfSubscribers.get(sub);
			if(subMessQueue!=null) {
				if(b.getClass()==TerminationBroadCast.class){
					subMessQueue.addFirst(b);
				}
				else {
					subMessQueue.add(b);
				}
			}
		}
	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
//		System.out.println(mapOfTopics.get(e.getClass()).size()+ " size of topic Q"+ e.getClass()+ "in broker sendEvent");
		Future<T> future=new Future<>();
		try {
			mapOfEvents.put(e, future);    //add future to the mapOfEvents
			LinkedBlockingQueue<Subscriber>topicSubQueue = mapOfTopics.get(e.getClass());    //we remove sub of the topic Queue
			if(topicSubQueue.size()==0){	//TO DO CHECK THIS ACTION!!!!!!
				future.resolve(null);
				return null;
			}
//			System.out.println(topicSubQueue.size()+" QQQQQQQQQQQQQQQQ SIZE");
			Subscriber sub=topicSubQueue.poll();
//			System.out.println("test"+sub.getClass()+sub.getName());
			if(sub==null){
				return null;
			}
			mapOfSubscribers.get(sub).put(e);    //add e to sub message Queue
			mapOfTopics.get(e.getClass()).put(sub);    //round-Robin
		}
		catch (InterruptedException exp){
			Thread.currentThread().interrupt();
		}
		return future;
	}

	@Override
	public void register(Subscriber m) {	//assume we adding the sub name as key,and sub to the map
		mapOfSubscribers.put(m,new LinkedBlockingDeque<>());
	}

	@Override
	public synchronized void unregister(Subscriber m) {    //similar to the register, but we need to clear his EVENT queue
		for(Map.Entry<Class<? extends Message>, LinkedBlockingQueue<Subscriber>> topicQueue: mapOfTopics.entrySet()){
			topicQueue.getValue().remove(m);
		}
		LinkedBlockingDeque<Message> toRemove=mapOfSubscribers.remove(m);
		while (toRemove.size()>0){
			Message mess=toRemove.removeFirst();
			if (mess.getClass()!=TickBroadcast.class){
				complete((Event)mess,null);
			}

		}
//		System.out.println("broker unregistered " + m.getClass()+ m.getName());
	}

	@Override
	public Message awaitMessage(Subscriber m) throws InterruptedException {
		return mapOfSubscribers.get(m).take();
	}
}
