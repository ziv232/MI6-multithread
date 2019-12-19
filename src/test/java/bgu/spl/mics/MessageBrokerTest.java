package bgu.spl.mics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bgu.spl.mics.application.subscribers.Intelligence;
import bgu.spl.mics.application.subscribers.M;
import bgu.spl.mics.application.subscribers.Moneypenny;
import bgu.spl.mics.application.subscribers.Q;
import bgu.spl.mics.example.messages.ExampleBroadcast;
import bgu.spl.mics.example.messages.ExampleEvent;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class MessageBrokerTest {
	private MessageBrokerImpl broker;
	private Intelligence intel;
	private Moneypenny money;
	private Q q;
	private M m;
	private ExampleEvent event;
	private ExampleBroadcast broad;
	
	
	
	
    @BeforeEach
    public void setUp(){
    	broker=new MessageBrokerImpl();
    	intel=new Intelligence("1");
    	money=new Moneypenny();
    	q=new Q();
    	m=new M();
    	event = new ExampleEvent("Moses");
    	broad = new ExampleBroadcast("1");
    	broker.register(m);
    	broker.register(q);
    }

    @Test
    public void subscribeBroadcast(){
    	broker.subscribeBroadcast(broad.getClass(), m);
    	broker.sendBroadcast(broad);
    	try {
    	assertTrue(broker.awaitMessage(m)==broad, "message is wrong");}
    	catch (InterruptedException e) {
    		fail("m is not registered");
    	}
    }
    
    @Test
    public void subscribeEvent() {
    	broker.subscribeEvent(event.getClass(), q);
    	broker.sendEvent(event);
    	try {
    		assertTrue(broker.awaitMessage(q)==event, "message is wrong");}
    	catch(InterruptedException e) {
    		fail("q is not registered");
    	}
    }
    
    @Test
    public void unregister() {
    	broker.unregister(m);
    	try {
    		broker.subscribeEvent(event.getClass(), m);
    	}
    	catch(Exception e) {
    	}
    }
    
    @Test
    public void sendEvent() {
    	assertTrue(broker.sendEvent(event)==null,"message recieved tough no there is no suitable subs ");
    	broker.subscribeEvent(event.getClass(), q);
    	broker.sendEvent(event);
    	try {
    		assertTrue(broker.awaitMessage(q)==event," message is wrong");
    	}
    	catch(InterruptedException e) {
    		fail("q is not registered");
    	}
    }
    
    @Test
    public void sendBroadcast() {
    	broker.subscribeBroadcast(broad.getClass(), m);
    	broker.sendBroadcast(broad);
    	try {
    	assertTrue(broker.awaitMessage(m)==broad, "message is wrong");}
    	catch (InterruptedException e) {
    		fail("m is not registered");
    	}	
    }
    
}
