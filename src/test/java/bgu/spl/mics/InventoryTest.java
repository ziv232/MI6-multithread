package bgu.spl.mics;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import bgu.spl.mics.application.passiveObjects.Inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.List;


public class InventoryTest {
	private Inventory gadgets;
	private String[] items = {"Sword", "Shield"};
	
    @BeforeEach
    public void setUp(){
    	gadgets = Inventory.getInstance();
    	gadgets.load(items);
    	
    }

    @Test
    public void getItem() {
    	assertTrue(gadgets.getItem("Sword"),"failure - should be true");
    	assertTrue(gadgets.getItem("Shield"),"failure - should be true");
    	assertFalse(gadgets.getItem("Smart-Phone"),"failure - should be false");

    }
    @Test
    public void load() {
    	while(!(gadgets.getGadgets().isEmpty())) {
    		assertEquals(gadgets.getGadgets().get(0),"Sword","Strings are not equal");
    		assertEquals(gadgets.getGadgets().get(1),"Shield","Strings are not equal");  
    	}
    }
    
    @Test
    public void printToFile() {
  
    }
}