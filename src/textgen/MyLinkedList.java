package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		head = new LLNode<>(null);
		tail = new LLNode<>(null);
		size = 0;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element) 
	{
		
		if(null == element) {
			throw new NullPointerException();
		}
		
		LLNode<E> preNode = (size == 0) ? head : retrieveNodeByIndex(size - 1);
		
		LLNode<E> node = new LLNode<E>(element, preNode, tail);
		
		preNode.next = node;
		tail.prev = node;
		
		size++;
		
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		if(0 == size || index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		
		LLNode<E> node = retrieveNodeByIndex(index);
		
		return node.data;
	}

	private LLNode<E> retrieveNodeByIndex(int index) {
		LLNode<E> node = null;
		
		for (int i = 0; i <= index; i++) {
			if(0 == i) {
				node = head.next;
			}
			else {
				node = node.next;
			}
		}
		return node;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		if(null == element) {
			throw new NullPointerException();
		}
		if(index < 0 || (0 != size && index > size - 1)) {
			throw new IndexOutOfBoundsException();
		}
		if (0 == size && index != 0) {
			throw new IndexOutOfBoundsException();
		}
		
		LLNode<E> preNode;
		LLNode<E> nextNode;
		
		if (0 == size) {
			preNode = head;
			nextNode = tail;
		}
		else if(0 == index) {
			preNode = head;
			nextNode = retrieveNodeByIndex(index);
		}
		else if(size - 1 == index) {
			preNode = retrieveNodeByIndex(index);
			nextNode = tail;
		}
		else {
			preNode = retrieveNodeByIndex(index - 1);
			nextNode = retrieveNodeByIndex(index);
		}
		
		LLNode<E> node = new LLNode<E>(element, preNode, nextNode);
		
		preNode.next = node;
		nextNode.prev = node;
		
		size++;
		
	}


	/** Return the size of the list */
	public int size() 
	{
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		if(0 == size || index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		
		LLNode<E> node = retrieveNodeByIndex(index);
		
		if (size == 1) {
			head.next = null;
			tail.prev = null;
		}
		else if(0 == index){
			head.next = node.next;
			node.next.prev = node.prev;
		}
		else if(size - 1 == index) {
			node.prev.next = tail;
			tail.prev = node.prev;
		}
		else {
			node.prev.next = node.next;
			node.next.prev = node.prev;
		}
		
		size--;
		
		return node.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		if(null == element) {
			throw new NullPointerException();
		}
		if(0 == size || index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		
		LLNode<E> node = retrieveNodeByIndex(index);
		E oldValue = node.data;
		node.data = element;
		
		return oldValue;
	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;
	
	public LLNode(E e, LLNode<E> prev, LLNode<E> next) {
		this.data = e;
		this.prev = prev;
		this.next = next;
	}

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

}
