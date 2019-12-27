import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class ConcordanceDataStructure implements ConcordanceDataStructureInterface {

	private LinkedList<ConcordanceDataElement>[] list;
	private int tableSize;
	private int prime;
	
	public ConcordanceDataStructure(int num)
	{
		double s = (num * 1.0) / 1.5;
		
		tableSize = (int)s;
		
		boolean isPrime;
		
		do 
		{
			isPrime = true;
		    if (tableSize%2==0)
		    	isPrime = false;
	
		    for(int i=3;i*i<=tableSize;i+=2) 
		    {
		        if(tableSize%i==0)
		            isPrime = false;
		    }
		
			if(isPrime == true) 
			{
				if(((tableSize - 3)) % 4 == 0)
					prime = tableSize;
				else
					tableSize++;
			} 
			else
			{
				tableSize++;
			}
		} while(!isPrime || prime == 0);
		
		list = new LinkedList[tableSize];
	}
	
	/**
	 * Constructor for testing purpose
	 * @param test
	 * @param size
	 */
	public ConcordanceDataStructure(String test, int size)
	{
		this.tableSize = size;
		list = new LinkedList[size];
	}
	
	/**
	 * Returns the size of the ConcordanceDataStructure (number of indexes in the array)
	 */
	@Override
	public int getTableSize() {
		
		return tableSize;
	}
	 /**
	    * Returns an ArrayList of the words at this index
	    * [0] of the ArrayList holds the first word in the "bucket" (index)
	    * [1] of the ArrayList holds the next word in the "bucket", etc.
	    * This is used for testing
	    * @param index location within the hash table
	    * @return an Arraylist of the words at this index
	    */
	@Override
	public ArrayList<String> getWords(int index) 
	{
		ArrayList<String> words = new ArrayList<String>();
		for(int j = 0; j < list[index].size(); j++) 
		{
			words.add(list[index].get(j).getWord());
		}
		return words;
	}
	   /**
	    * Returns an ArrayList of the Linked list of page numbers for each word at this index
	    * [0] of the ArrayList holds the LinkedList of page numbers for the first word in the "bucket" (index)
	    * [1] of the ArrayList holds the LinkedList of page numbers for next word in the "bucket", etc.
	    * This is used for testing
	    * @param index location within the hash table
	    * @return an ArrayList of the Linked list of page numbers for each word at this index
	    */
	@Override
	public ArrayList<LinkedList<Integer>> getPageNumbers(int index) {
		ArrayList<LinkedList<Integer>> pageNumbers = new ArrayList<LinkedList<Integer>>();
		for(int i = 0; i < list[index].size(); i ++) 
		{
			pageNumbers.add(list[index].get(i).getList());
		}
		return pageNumbers;
	}
	
	/** 
	    * Use the hashcode of the ConcordanceDataElement to see if it is 
	    * in the hashtable.
	    * 
	    * If the word does not exist in the hashtable - Add the ConcordanceDataElement 
	    * to the hashtable. Put the line number in the linked list
	    *  
	    * If the word already exists in the hashtable
	    * 1. add the line number to the end of the linked list in the ConcordanceDataElement 
	    * (if the line number is not currently there).  
	    * 
	    * @param word the word to be added/updated with a line number.
	    * @param lineNum the line number where the word is found
	    */
	@Override
	public void add(String word, int lineNum) {
		int index;
		boolean add = false;
		ConcordanceDataElement element = new ConcordanceDataElement(word);

		index = Math.abs( element.hashCode() % tableSize);
		
		if(list[index] != null) 
		{
			for(int i = 0; i < list[index].size(); i ++)
			{
				if(list[index].get(i).compareTo(element) == 0)
				{
					list[index].get(i).addPage(lineNum);
					add = true;
				}
			}
		}
		else 
		{
			LinkedList<ConcordanceDataElement> listAdd = new LinkedList<ConcordanceDataElement>();
			listAdd.add(element);
			list[index] = listAdd;
			list[index].getFirst().addPage(lineNum);
			add = true;
		}
		if(add == false)
		{
			list[index].add(element);
			list[index].getLast().addPage(lineNum);
	}
	}

	@Override
	public ArrayList<String> showAll() {
		ArrayList<String> listOfString = new ArrayList<String>();
		ArrayList<ConcordanceDataElement> elements = new ArrayList<ConcordanceDataElement>();
		LinkedList<ConcordanceDataElement> hList;

		for (int i = 0; i < list.length; i++) 
		{
			if (!(list[i] == null)) 
			{
				hList = list[i];
				for (ConcordanceDataElement element : hList)
				{
					elements.add(element);
				}
			}
		}
		Collections.sort(elements);
		for (ConcordanceDataElement element : elements) {
			listOfString.add(element.toString() + "\n");
		}
		return listOfString;
	}

}
