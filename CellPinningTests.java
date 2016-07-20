import static org.junit.Assert.*;

import org.junit.Test;

public class CellPinningTests {
//----------------------------------------------------------------------------------------------
// Begin: toString pinning tests
// These tests are for the Cell.toString method refactors
//----------------------------------------------------------------------------------------------
		
	//This test is to verify that
	//for an alive cell
	//the text is set as "X"
	@Test
	public void testToString_SetsXForAliveCell() {
		//Arrange:
		//Create a new cell and set as alive
		Cell cellTest = new Cell();
		cellTest.setAlive(true);
		
		//Act:
		//Call the toString method on the cell
		String cellText = cellTest.toString();
		
		//Assert:
		//check returned value is "X"
		assertEquals("X", cellText);	
	}
	
	//This test is to verify that
	//for a dead cell
	//the text is set as "."
	@Test
	public void testToString_SetsDotForDeadCell() {
		//Arrange:
		//Create a new cell and set as alive
		Cell cellTest = new Cell();
		cellTest.setAlive(false);
		
		//Act:
		//Call the toString method on the cell
		String cellText = cellTest.toString();
		
		//Assert:
		//check returned value is "."
		assertEquals(".", cellText);	
	}
	
	//This test is to verify that
	//for a default cell
	//the text is set as "."
	@Test
	public void testToString_SetsDotForDefaultCell() {
		//Arrange:
		//Create a new cell and set as alive
		Cell cellTest = new Cell();
		
		//Act:
		//Call the toString method on the cell
		String cellText = cellTest.toString();
		
		//Assert:
		//check returned value is "."
		assertEquals(".", cellText);	
	}
	
//----------------------------------------------------------------------------------------------
// End: toString pinning tests
//----------------------------------------------------------------------------------------------
}
