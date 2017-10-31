package spelling;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size = 0;
    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should convert the 
	 * string to all lower case before you insert it. 
	 * 
	 * This method adds a word by creating and linking the necessary trie nodes 
	 * into the trie, as described outlined in the videos for this week. It 
	 * should appropriately use existing nodes in the trie, only creating new 
	 * nodes when necessary. E.g. If the word "no" is already in the trie, 
	 * then adding the word "now" would add only one additional node 
	 * (for the 'w').
	 * 
	 * @return true if the word was successfully added or false if it already exists
	 * in the dictionary.
	 */
	public boolean addWord(String word)
	{
		if (isEmptyString(word) || isWord(word)) {
			return false;
		}
		char[] chars = word.toLowerCase().toCharArray();
		TrieNode node = root;
		for (char c : chars) {
			if (null == node.getChild(c)) {
				node.insert(c);
			}
			node = node.getChild(c);
		}
		// words already exist
		if (node.endsWord()) {
			return false;
		}
		// words not exist
		else {
			size++;
			node.setEndsWord(true);
		    return true;
		}
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    return size;
	}
	
	
	/** Returns whether the string is a word in the trie, using the algorithm
	 * described in the videos for this week. */
	@Override
	public boolean isWord(String s) 
	{
		if (isEmptyString(s)) {
			return false;
		}
		else {
			TrieNode node = findNodeByPrefix(s);
			return node == null ? false : node.endsWord();
		}
	}

	/** 
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions 
     * of the prefix string. All legal completions must be valid words in the 
     * dictionary. If the prefix itself is a valid word, it is included 
     * in the list of returned words. 
     * 
     * The list of completions must contain 
     * all of the shortest completions, but when there are ties, it may break 
     * them in any order. For example, if there the prefix string is "ste" and 
     * only the words "step", "stem", "stew", "steer" and "steep" are in the 
     * dictionary, when the user asks for 4 completions, the list must include 
     * "step", "stem" and "stew", but may include either the word 
     * "steer" or "steep".
     * 
     * If this string prefix is not in the trie, it returns an empty list.
     * 
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {    	
  	    // create a queue (LinkedList) and add the node that completes the stem to the back  of the list.
  		LinkedList<TrieNode> stems = new LinkedList<>();
  		// Create a list of completions to return (initially empty)
  		List<String> completions = new ArrayList<>(numCompletions);
  		// number of words in completions
  		int numberOfWord = 0;
    	 
    	 // If string is empty add all the nodes ad sub node
    	if (isEmptyString(prefix)) {
    		addSubNodesToStems(root, stems);
		}
    	else {
    	   	// Find the stem in the trie.  If the stem does not appear in the trie, return an empty list
     		TrieNode node = findNodeByPrefix(prefix);
     		if (null == node) {
     			return new ArrayList<>(0);
     		}
 			if(node.endsWord()) {
 				completions.add(node.getText());
 				numberOfWord++;
 			}
     		addSubNodesToStems(node, stems);
    	}
 		
 		while(!stems.isEmpty() && numberOfWord < numCompletions) {
 			TrieNode subNode = stems.pollFirst(); 			
 			if(subNode.endsWord()) {
 				completions.add(subNode.getText());
 				numberOfWord++;
 			}
 			addSubNodesToStems(subNode, stems);
 		} 
         return completions;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText() + ":" + curr.endsWord());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}

 	/**
 	 * Is string empty
 	 * @param s string
 	 * @return
 	 */
    private boolean isEmptyString(String s) {
    	return null == s || "".equals(s);
    }
    
    /**
     * Find a node in tries by prefix
     * @param prefix
     * @return
     */
    private TrieNode findNodeByPrefix(String prefix) {
		char[] chars = prefix.toLowerCase().toCharArray();
		TrieNode node = root;
		for (char c : chars) {
			node = node.getChild(c);
			if (null == node) {
				return null;
			}
		}
		return node;
    }
    
    /**
     * Add sub node to current stems(Queue)
     * @param node
     * @param stems
     */
    private void addSubNodesToStems(TrieNode node, LinkedList<TrieNode> stems) {
 		Iterator<Character> it = node.getValidNextCharacters().iterator();
 		while(it.hasNext()) {
 			stems.add(node.getChild(it.next()));	
 		}
    }
	
}