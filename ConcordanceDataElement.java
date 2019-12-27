import java.util.LinkedList;

public class ConcordanceDataElement implements Comparable<ConcordanceDataElement>{

	private String word;
	private LinkedList<Integer> listPage;// list of line numbers where word occurs

	
	/**
	 * Constructor ConcordanceDataElement that takes the String word
	 * @param word for the concordance data element
	 */
	public ConcordanceDataElement(String word)
	{
		this.word = word;
		listPage = new LinkedList<Integer>();
	}
	/**
	 * Method that returns the linked list of integers that represent the line numbers
	 * @return listPage
	 */
	public LinkedList<Integer> getList()
	{
		return listPage;
	}
	/**
	 * Add a line number to the linked list if the number doesn't exist in the list
	 * @param lineNum
	 */
	public void addPage(int lineNum)
	{
		if(!listPage.contains(lineNum))
			listPage.add(lineNum);
	}

	@Override
	public int compareTo(ConcordanceDataElement arg0) {
		
		return word.compareTo(arg0.getWord());
	}
	
	
	/**
	 * Method that returns the word portion of the Concordance Data Element
	 * @return word
	 */
	public String getWord()
	{
		return word;
	}
	
	/**
	 * Method that returns the hashCode
	 */
	public int hashCode()
	{
		return word.hashCode();
	}
	
	/**
	 * toString method that returns the word followed by page numbers Returns a string in the following format: word: page num, page num Example: after: 2,8,15
	 */
	public String toString()
	{
		String str = "";
		str = word + ": ";
		String delimiter = ", ";
		for(int i=0; i<listPage.size(); i++)
		{
			if(i<listPage.size()-1)
				str += listPage.get(i) + delimiter;
			else
				str += listPage.get(i);
		}
		return str;
	}
	
}
