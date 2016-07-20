import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

public class MainPanelPinningTests {
//----------------------------------------------------------------------------------------------
// Begin: runContinuous pinning tests
// These tests are for the MainPanel.runContinuous method refactors
//----------------------------------------------------------------------------------------------
	
	//This test is to verify that
	//the global variable _r has the same value before and
	//after refactoring the runContinuous() method
	@Test
	public void testRunContinuous_rUnchanged() {
		//Arrange:
		//Create MainPanel Object and capture initial _r
		MainPanel m = new MainPanel(15);
		int originalR = m._r;
		
		//Act:
		//call m.runContinuous() in an asynchronous call
		//so as to break the call later using m.stop()
		new Thread(new Runnable() {
		           public void run() {
		        	   m.runContinuous();		
			}
		}).start();
		
		//call m.stop to break the runContinuous method
		m.stop();
		
		//Assert:
		//Check Value of _r remain same after calling runContinuous
		assertEquals(originalR, m._r);
	}
	
	//This test is to verify that
	//the cell size remains the same
	//after refactoring the runContinuous() method
	@Test
	public void testRunContinuous_CellSizeUnchanged() {
		//Arrange:
		//Create MainPanel Object and set cell size as 15
		MainPanel m = new MainPanel(15);
		
		
		//Act:
		//call m.runContinuous() in an asynchronous call
		//so as to break the call later using m.stop()
		new Thread(new Runnable() {
		           public void run() {
		        	   m.runContinuous();		
			}
		}).start();
		
		//call m.stop to break the runContinuous method
		m.stop();
		
		//Assert:
		//Check Value of cell size remain same after calling runContinuous
		assertEquals(15, m.getCellsSize());
	}
	
	//This test is to verify that
	//the program logic of setting cells alive and dead
	//works correctly
	//after refactoring the runContinuous Method
	@Test
	public void testRunContinuous_CellLogicIntact() {
		//Arrange:
		//Create MainPanel Object and set cell size as 2 
		MainPanel m = new MainPanel(2);
		
		Cell[][] cells = new Cell[2][2];
		cells[0][0] = new Cell();
		cells[0][1] = new Cell();
		cells[1][0] = new Cell();
		cells[1][1] = new Cell();
		
		//only setting diagonal cells as alive
		//these should become dead according to logic
		cells[0][0].setAlive(true);
		cells[1][1].setAlive(true);
		
		
		m.setCells(cells);
		
		//Act:
		//call m.runContinuous() in an asynchronous call
		//so as to break the call later using m.stop()
		new Thread(new Runnable() {
		           public void run() {
		        	   m.runContinuous();		
			}
		}).start();
		
		//call m.stop to break the runContinuous method
		//placing a sleep of 100 milliseconds to allow the logic to run
		try {
			Thread.sleep(100);
			m.stop();
		    } catch (InterruptedException iex) { }
		
		//Assert:
		//Check the cells at [0][0] and [1][1] are not alive
		assertFalse(cells[0][0].getAlive());
		assertFalse(cells[1][1].getAlive());
	}
//----------------------------------------------------------------------------------------------
// End: runContinuous pinning tests
//----------------------------------------------------------------------------------------------

//----------------------------------------------------------------------------------------------
// Begin: getNumNeighbors pinning tests
// These tests are for the getNumNeighbors refactor which also involves converToInt()
//----------------------------------------------------------------------------------------------

	//This test is to verify that
	//the getNumNeighbors method returns the correct
	//number of neighbors for a cell
	@Test
	public void testgetNumNeighbors_CorrectNumberOfNeighborsReturned() {
		//Arrange:
		//Create MainPanel Object and set cell size as 2 
		MainPanel m = new MainPanel(2);
		
		Cell[][] cells = new Cell[2][2];
		cells[0][0] = new Cell();
		cells[0][1] = new Cell();
		cells[1][0] = new Cell();
		cells[1][1] = new Cell();
		
		//only setting diagonal cells as alive
		//these should become dead according to logic
		cells[0][0].setAlive(true);
		cells[1][1].setAlive(true);
		
		//setting cells to the MainPanel 
		m.setCells(cells);
		
		try {	//Using reflection in Java to access the private getNumNeighbors method
				Method method = MainPanel.class.getDeclaredMethod("getNumNeighbors", int.class, int.class);
				method.setAccessible(true);
				
				//Act:
				//calling getNumNeighbors with arguments x=1 and y=1
				Object returnValue = method.invoke(m, 1, 1);
				int foo = ((Integer) returnValue).intValue();
				
				//Assert:
				//Check that for a single alive cell the number of neighbors returned is 4
				//this may seem incorrect, but this ensures that array out of bounds don't happen
				//and the logic to run the program remains intact.
				assertEquals(4, foo);
			} 
		catch (NoSuchMethodException|IllegalAccessException|InvocationTargetException ex) {
				// The method does not exist
				fail();
			}
	}
	
	//This test is to verify that
	//the getNumNeighbors method returns the correct
	//number of neighbors for a single cell game with cell Dead
	@Test
	public void testgetNumNeighbors_CorrectNeigborsForSizeOneAndDead() {
		//Arrange:
		//Create MainPanel Object and set cell size as 1
		MainPanel m = new MainPanel(1);
		
		Cell[][] cells = new Cell[1][1];
		cells[0][0] = new Cell();
		
		//this should become dead according to logic
		cells[0][0].setAlive(false);
		
		//set cells to the MainPanel
		m.setCells(cells);
		
		try {	//Use Reflection in Java to access the getNumNeighbors private method
				Method method = MainPanel.class.getDeclaredMethod("getNumNeighbors", int.class, int.class);
				method.setAccessible(true);
				
				//Call the getNumNeighbors method with arguments x=0 and y=0
				Object returnValue = method.invoke(m, 0, 0);
				int foo = ((Integer) returnValue).intValue();
				
				//Assert:
				//Check that for a single alive cell the number of neighbors returned is 0
				assertEquals(0, foo);
			} 
		catch (NoSuchMethodException|IllegalAccessException|InvocationTargetException ex) {
				// The method does not exist
				fail();
			}
	}
	
	//This test is to verify that
	//the getNumNeighbors method returns the correct
	//number of neighbors for a single cell game with cell Alive
	@Test
	public void testgetNumNeighbors_CorrectNeigborsForSizeOneAndAlive() {
		//Arrange:
		//Create MainPanel Object and set cell size as 1
		MainPanel m = new MainPanel(1);
		
		Cell[][] cells = new Cell[1][1];
		cells[0][0] = new Cell();
		
		//this should become dead according to logic
		cells[0][0].setAlive(true);
		
		//setting cells to the MainPanel
		m.setCells(cells);
		
		try {	//Using reflection to access the private method getNumNeighbors
				Method method = MainPanel.class.getDeclaredMethod("getNumNeighbors", int.class, int.class);
				method.setAccessible(true);
				
				//Act:
				//Call the private getNumNeighbors method with arguments x=0 and y=0
				Object returnValue = method.invoke(m, 0, 0);
				int foo = ((Integer) returnValue).intValue();
				
				//Assert:
				//Check that for a single alive cell the number of neighbors returned is 8
				//this may seem incorrect, but this ensures that array out of bounds don't happen
				//and the logic to run the program remains intact.
				assertEquals(8, foo);
			} 
		catch (NoSuchMethodException|IllegalAccessException|InvocationTargetException ex) {
				// The method does not exist
				fail();
			}
	}
	
}
