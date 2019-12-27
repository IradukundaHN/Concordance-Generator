import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ConcordanceDataManager implements ConcordanceDataManagerInterface {

	private static ConcordanceDataStructure hash; 
	private int size;
	
	ConcordanceDataStructure object1 = new ConcordanceDataStructure(size);
	int tableSize = object1.getTableSize();
	
	/**
	 * constructor 
	 */
	public ConcordanceDataManager()
	{
		hash = new ConcordanceDataStructure(tableSize);
	}
	
	/**
	 * Display the words in Alphabetical Order followed by a :, 
	 * followed by the line numbers in numerical order, followed by a newline
	 * here's an example:
	 * after: 129, 175
	 * agree: 185
	 * agreed: 37
	 * all: 24, 93, 112, 175, 203
	 * always: 90, 128
	 * 
	 * @param input a String (usually consist of several lines) to make a concordance of
	 * @return an ArrayList of Strings.  Each string has one word,
	 * followed by a :, followed by the line numbers in numerical order, followed by a newline.
	 */
	public ArrayList<String> createConcordanceArray(String input) {
		String line; 
		String[] word;
		
		int lineNum = 1;
		
		while (!input.isEmpty())
		{
			if (input.indexOf("\n") == -1) 
			{
				line = input;
				input = "";
			} 
			else 
			{
				line = input.substring(0, input.indexOf("\n"));
				input = input.substring(input.indexOf("\n") + 1);
			}
			while (!line.isEmpty())
			{
				line = line.toLowerCase();
				line = line.replaceAll("and |the ", "");
	
				word = line.split(" ");
				
				line = "";
				if (word.length != 0) 
				{
					for (int i = 0; i < word.length; i++)
					{
						if (word[i].length() > 2) 
						{
							String nextWord;
							nextWord = word[i];
							hash.add(nextWord, lineNum);
						}
					}

				}
			}
			lineNum++;
		}
		return hash.showAll();
	}

	 /**
	    * Creates a file that holds the concordance  
	    * 
	    * @param input the File to read from
	    * @param output the File to write to
	    *  
	    * Following is an example:
	    * 
		* about: 24, 210
		* abuse: 96
		* account: 79
		* acknowledged: 10
		* 
	    * @return true if the concordance file was created successfully.
	    * @throws FileNotFoundException if file not found
	    */
	@Override
	public boolean createConcordanceFile(File input, File output) throws FileNotFoundException  {
		Scanner inp;
		inp = new Scanner(input);
		
		int lineNumber = 0;
		String next;
		char nextChar;
		int index;
		Scanner lScanner;
	
		while(inp.hasNextLine())
		{
			lScanner = new Scanner(inp.nextLine());
			lineNumber++;
			while(lScanner.hasNext())
			{
				next = lScanner.next();
				index = next.length();
				while((next.length()>0)&& !Character.isLetter(next.charAt(index-1)))
				{
					next = next.substring(0,index-1);
					index--;
				}
				while((next.length()>0)&& !Character.isLetter(next.charAt(0)))
				{
					next = next.substring(1);
				}
				if((next.length()>2) && (!next.equalsIgnoreCase("the"))&& (!next.equalsIgnoreCase("and")))
				{
					hash.add(next.toLowerCase(), lineNumber);
				}
			}
			
			PrintWriter outputf = new PrintWriter(output);
			ArrayList<String> word = hash.showAll();
			for(String y: word)
			{
				outputf.print(y);
			}
			outputf.close();
	
		
		}
		return true;
	}
}// end class Concordance
