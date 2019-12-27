import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This is the test file for the ConcordanceDataManager
 * which is implemented from the ConcordanceDataManagerInterface
 * 
 */
public class ConcordanceDataStructureTest {
	ConcordanceDataStructureInterface concordanceDataStructure, testStructure, testStructureSTUDENT;
	String text;
	ArrayList<String> array;

	@Before
	public void setUp() throws Exception {
		concordanceDataStructure = new ConcordanceDataStructure(500);
		testStructure = new ConcordanceDataStructure("Testing", 20);
		testStructureSTUDENT = new ConcordanceDataStructure("TestingSTUDENT", 20);
	}

	@After
	public void tearDown() throws Exception {
		concordanceDataStructure = testStructure = null;
	}

	/**
	 * Test that words that hash to the same index are put in the same
	 * "bucket" for that index
	 */
	@Test
	public void testAddA() {
		testStructure.add("banana", 1);
		//banana should be stored at index 7
		//Math.abs("banana".hashCode()%20)
		assertEquals("banana", testStructure.getWords(7).get(0));
		assertEquals(1, testStructure.getPageNumbers(7).get(0).get(0), .001);
		testStructure.add("carrot", 2);
		//carrot should be stored at index 5
		assertEquals("carrot", testStructure.getWords(5).get(0));
		assertEquals(2, testStructure.getPageNumbers(5).get(0).get(0), .001);
		testStructure.add("apple", 3);
		//apple should be stored at index 10
		assertEquals("apple", testStructure.getWords(10).get(0));
		assertEquals(3, testStructure.getPageNumbers(10).get(0).get(0), .001);
		testStructure.add("cucumber", 2);
		//cucumber should also be stored at index 10
		assertEquals("cucumber", testStructure.getWords(10).get(1));
		assertEquals(2, testStructure.getPageNumbers(10).get(1).get(0), .001);
	}
	
	@Test
	public void testAddSTUDENT() {
		testStructureSTUDENT.add("dog", 1);
		//System.out.println(Math.abs("dog".hashCode() % 20));
		assertEquals("dog", testStructureSTUDENT.getWords(4).get(0));
		assertEquals(1, testStructureSTUDENT.getPageNumbers(4).get(0).get(0), .001);
		testStructureSTUDENT.add("cat", 2);
		//System.out.println(Math.abs("cat".hashCode() % 20));
		assertEquals("cat", testStructureSTUDENT.getWords(2).get(0));
		assertEquals(2, testStructureSTUDENT.getPageNumbers(2).get(0).get(0), .001);
		testStructureSTUDENT.add("mouse", 3);
		//System.out.println(Math.abs("mouse".hashCode() % 20));
		assertEquals("mouse", testStructureSTUDENT.getWords(13).get(0));
		assertEquals(3, testStructureSTUDENT.getPageNumbers(13).get(0).get(0), .001);
		testStructureSTUDENT.add("cow", 2);
		//System.out.println(Math.abs("cow".hashCode() % 20));
		assertEquals("cow", testStructureSTUDENT.getWords(19).get(0));
		assertEquals(2, testStructureSTUDENT.getPageNumbers(19).get(0).get(0), .001);

		// fail("This test is not yet implemented");
		
	}
	
	/**
	 * Test that the same word is only entered once in the "bucket"
	 * for that index
	 */
	@Test
	public void testAddB() {
		testStructure.add("apple",1);
		//apple should be stored at index 10
		assertEquals("apple", testStructure.getWords(10).get(0));
		testStructure.add("apple", 2);
		testStructure.add("cucumber", 2);
		//cucumber should also be stored at index 10
		assertEquals("cucumber", testStructure.getWords(10).get(1));		
	}
	
	/**
	 * Test that the same linenumber for a word is only entered once
	 * for that word
	 */
	@Test
	public void testAddC() {
		testStructure.add("apple",1);
		//apple should be stored at index 10
		assertEquals(1, testStructure.getPageNumbers(10).get(0).get(0), .001);
		testStructure.add("apple", 1);
		testStructure.add("apple", 2);
		assertEquals(2, testStructure.getPageNumbers(10).get(0).get(1), .001);
				
	}


	/**
	 * Test the tableSize for concordanceDataStructures constructed
	 * with both constructors
	 */
	@Test
	public void testGetTableSize()
	{
		assertEquals(347, concordanceDataStructure.getTableSize());
		assertEquals(20, testStructure.getTableSize());		
	}
	
	/**
	 * Test that the resulting ArrayList is in alphabetical order
	 */
	@Test
	public void testShowAll() {
		testStructure.add("banana", 1);
		testStructure.add("carrot", 2);
		testStructure.add("apple", 3);
		array = testStructure.showAll();
		assertTrue(array.get(0).contains("apple: 3"));
		assertTrue(array.get(1).contains("banana: 1"));
		assertTrue(array.get(2).contains("carrot: 2"));
	}

}
