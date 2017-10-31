package textgen;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	// The pattern	
	private Pattern pattern = Pattern.compile("[a-z|A-Z|.,!?']+");
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator; 
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		if(null == sourceText || "".equals(sourceText)) {
			return;
		}
		
		String preWord = "";
		String curWord = "";
		ListNode node = null;
		
		List<String> texts = getTokens(sourceText);
		if(!texts.isEmpty()) {
			
			starter = texts.get(0);
			preWord = starter;
			
			for(int i = 1; i < texts.size(); i++) {
				
				curWord = texts.get(i);				
				node = retrieveListNodeByWord(preWord);
				
				// if contains preWord in the word list , add current word into node's word list
				if(null != node) {
					node.addNextWord(curWord);
				}
				// else create a new node and set current into node's list
				else {
					ListNode newNode = new ListNode(preWord);
					newNode.addNextWord(curWord);
					wordList.add(newNode);
				}
				
				preWord = curWord;
			}
			
			// set starter into last node word list
			ListNode lastNode = wordList.get(wordList.size() - 1);
			lastNode.addNextWord(starter);
		}
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
		
		if(numWords <= 0 || wordList.isEmpty()) {
			return "";
		}
		
		String currWord = starter;
		String nextWord = "";
		StringBuilder output = new StringBuilder(starter);
		
		for(int i = 1; i < numWords; i++) {
			ListNode node = retrieveListNodeByWord(currWord);
			nextWord= node.getRandomNextWord(rnGenerator);
			output.append(" " + nextWord);
			currWord = nextWord;
		}
		
		return output.toString();
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		
		train(sourceText);
	}
	
	// Add any private helper methods you need here.
	private ListNode retrieveListNodeByWord(final String word) {
		if(null == wordList || wordList.isEmpty()) {
			return null;
		}
		for(ListNode node : wordList) {
			if(node.getWord().equals(word)) {
				return node;
			}
		}
		return null;
	}
	
	// Get tokens
	private List<String> getTokens(final String text) {
		List<String> tokens = new ArrayList<>();
		if(null != text) {
			Matcher matcher = pattern.matcher(text);
			while(matcher.find()) {
				tokens.add(matcher.group());
			}
		}
		return tokens;
	}
	
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		if (nextWords.size() == 1) {
			return nextWords.get(0);
		}
		int index = generator.nextInt(nextWords.size() - 1);
	    return nextWords.get(index);
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


