package bgu.spl.mics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;



public class FutureTest {
	Future<Integer> future;
	
    @BeforeEach
    public void setUp(){
    	future=new Future<>();

    }

    @Test
    public void isDone(){
    	assertFalse(future.isDone(),"wrong answer recieved");
    }
    
    @Test
    public void resolve() {
    	future.resolve(5);
    	assertTrue(future.isDone(),"wrong answer recieved");
    }
    
    @Test
    public void get() {
    	future.resolve(5);
    	assertEquals(5, future.get(),"wrong result recieved");
    }
    
    @Test
    public void get2() {
    	Thread t1=new Thread(()-> {	future.resolve(5);});
    	assertEquals(null, future.get(),"wrong answer recieved");
    	t1.start();
    	assertEquals(5, future.get(),"wrong answer recieved");
    	
    }
    @Test
    public void get3() {
    	Thread t1=new Thread(()-> {	future.get(100,TimeUnit.MILLISECONDS);});
    	assertEquals(null, future.get(),"wrong answer recieved");
    	t1.start();
    	assertEquals(5, future.get(),"wrong answer recieved");
    }
    
}
