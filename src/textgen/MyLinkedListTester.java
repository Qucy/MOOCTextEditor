/**
 * 
 */
package textgen;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author UC San Diego MOOC team
 *
 */
public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH =10; 

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Feel free to use these lists, or add your own
	    shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		emptyList = new MyLinkedList<Integer>();
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++)
		{
			longerList.add(i);
		}
		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);
		
	}

	
	/** Test if the get method is working correctly.
	 */
	/*You should not need to add much to this method.
	 * We provide it as an example of a thorough test. */
	@Test
	public void testGet()
	{
		//test empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		
		// test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));
		
		try {
			shortList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			shortList.get(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		// test longer list contents
		for(int i = 0; i<LONG_LIST_LENGTH; i++ ) {
			assertEquals("Check "+i+ " element", (Integer)i, longerList.get(i));
		}
		
		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
	}
	
	
	/** Test removing an element from the list.
	 * We've included the example from the concept challenge.
	 * You will want to add more tests.  */
	@Test
	public void testRemove()
	{
		
		list1.add(99);
		list1.add(88);
		list1.add(77);
		
		// test remove first
		int a = list1.remove(0);
		assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
		assertEquals("Remove: check size is correct ", 5, list1.size());
		
		assertEquals("testAddAtIndex: check head point to correct element ", (Integer) 21, (Integer) list1.head.next.data);
		assertEquals("testAddAtIndex: check prev node point to correct element ", null, list1.head.next.prev.data);
		
		// test remove last
		a = list1.remove(list1.size - 1);
		
		assertEquals("Remove: check a is correct ", 77, a);
		assertEquals("Remove: check element size-1 is correct ", (Integer)88, list1.get(list1.size - 1));
		assertEquals("Remove: check size is correct ", 4, list1.size());
		
		assertEquals("testAddAtIndex: check tail point to correct element ", (Integer) 88, (Integer) list1.tail.prev.data);
		assertEquals("testAddAtIndex: check prev node point to tail ", null, list1.tail.prev.next.data);
		
		
		// test remove from middle
		a = list1.remove(1);
		
		assertEquals("Remove: check a is correct ", 42, a);
		assertEquals("Remove: check element 1 is correct ", (Integer)99, list1.get(1));
		assertEquals("Remove: check size is correct ", 3, list1.size());
		
		assertEquals("testAddAtIndex: check tail point to correct element ", (Integer) 99, (Integer) list1.head.next.next.data);
		assertEquals("testAddAtIndex: check prev node point to tail ", (Integer) 99, list1.tail.prev.prev.data);
		
		
		// test off the end of the array
		try {
			list1.remove(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		
		// test off the out of the array
		try {
			list1.remove(5);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		
		// test off the empty array
		try {
			emptyList.remove(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		
		
	}
	
	/** Test adding an element into the end of the list, specifically
	 *  public boolean add(E element)
	 * */
	@Test
	public void testAddEnd()
	{
		int size = list1.size;
		
		boolean isSuccess = list1.add(90);
		
		assertEquals("testAddEnd: check add return value should be true ", true, isSuccess);
		assertEquals("testAddEnd: check add to end size is correct ", size + 1, list1.size);
		assertEquals("testAddEnd: check add element is at the end of list ", (Integer)90, list1.get(list1.size - 1));
		
		
		assertEquals("testAddAtIndex: check tail point to correct element ", (Integer) 90, (Integer) list1.tail.prev.data);
		assertEquals("testAddAtIndex: check prev node point to correct element ", (Integer) 90, (Integer) list1.tail.prev.prev.next.data);
		
		// test add null
		try {
			list1.add(null);
			fail("Check null pointer exception");
		}
		catch (NullPointerException e) {
		
		}
	}

	
	/** Test the size of the list */
	@Test
	public void testSize()
	{
		// Test size after add
		int size = list1.size;
		
		list1.add(90);
		
		assertEquals("testAddEnd: check add to end size is correct ", size + 1, list1.size);
		
		// Test size after remove
		list1.remove(0);
		
		assertEquals("testAddEnd: check add to end size is correct ", size, list1.size);
	}

	
	
	/** Test adding an element into the list at a specified index,
	 * specifically:
	 * public void add(int index, E element)
	 * */
	@Test
	public void testAddAtIndex()
	{
		// test add null
		try {
			list1.add(1, null);
			fail("Check null pointer exception");
		}
		catch (NullPointerException e) {
		
		}
		
		// test off the empty array
		try {
			emptyList.add(1, 1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		
		// test add element at index 0
		emptyList.add(0, 1);
		
		assertEquals("testAddEnd: check add to end size is correct ", 1, emptyList.size);
		assertEquals("testAddEnd: check add element is at the end of list ", (Integer)1, emptyList.get(0));
		
		
		// test off out of array
		try {
			list1.add(-1, 1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		
		
		// test off out of array
		try {
			list1.add(3, 1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		
		int list1Size = list1.size;
				
		// test add after head
		list1.add(0, 888);
		assertEquals("testAddAtIndex: check add size is correct ", list1Size + 1, list1.size);
		assertEquals("testAddAtIndex: check add element at beginning of list ", (Integer) 888, list1.get(0));
		assertEquals("testAddAtIndex: check head point to correct element ", (Integer) 888, (Integer) list1.head.next.data);
		assertEquals("testAddAtIndex: check next node point to correct element ", (Integer) 888, (Integer) list1.head.next.next.prev.data);
		
		
		// test add after tail
		list1.add(list1.size - 1, 999);
		assertEquals("testAddAtIndex: check add size is correct ", list1Size + 2, list1.size);
		assertEquals("testAddAtIndex: check add element is at the end of list ", (Integer) 999, list1.get(list1.size - 1));
		assertEquals("testAddAtIndex: check tail point to correct element ", (Integer) 999, (Integer) list1.tail.prev.data);
		assertEquals("testAddAtIndex: check prev node point to correct element ", (Integer) 999, (Integer) list1.tail.prev.prev.next.data);
		
		
		// test add none head or tail
		list1.add(2, 222);
		assertEquals("testAddAtIndex: check add size is correct ", list1Size + 3, list1.size);
		assertEquals("testAddAtIndex: check add element is at right position of list ", (Integer)222, list1.get(2));
		assertEquals("testAddAtIndex: check prev node point to correct element ", (Integer) 222, (Integer) list1.head.next.next.next.data);
		assertEquals("testAddAtIndex: check prev node point to correct element ", (Integer) 222, (Integer) list1.tail.prev.prev.prev.prev.data);
		
	}
	
	/** Test setting an element in the list */
	@Test
	public void testSet()
	{
		// test set null
		try {
			list1.set(1, null);
			fail("Check null pointer exception");
		}
		catch (NullPointerException e) {
		
		}
		// test off the empty array
		try {
			emptyList.set(0, 1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		
		// test off the out of array
		try {
			list1.set(-1, 1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		
		// test off the out of array
		try {
			list1.set(5, 1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		
		// functional test
		int replaced = list1.get(0);
		int returnValue = list1.set(0, 109);
		int newValue = list1.get(0);
		
		
		assertEquals("testSet: check add return value should be replaced value ", returnValue, replaced);
		assertEquals("testSet: check add return value should be new value ", 109, newValue);
	    
	}
	
	
	// TODO: Optionally add more test methods.
	
}
