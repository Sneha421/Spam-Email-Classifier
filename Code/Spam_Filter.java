/*This is a spam filter that flags a message as spam or ham
This is done based on the number of spam words present in that message
The spam words are stored in a trie
Through string matching we can decide the number of spam words in a message*/

//Importing all the necessary packages
import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.lang.Math;

class Spam_TrieNode
{
    Spam_TrieNode[] children;
    boolean isEndOfWord;

    Spam_TrieNode()
    {
        children = new Spam_TrieNode[26];//Initializing a standard trie with 26 branches 
        isEndOfWord = false;
    }
    
  
}
class Spam_Trie{
	Spam_TrieNode trieNode = new Spam_TrieNode();
	
	//Method to search for words in the trie
	boolean Search(String w) {
	int level=0;	
	int length = w.length();
    int index;
    Spam_TrieNode pCrawl = trieNode;

    for (level = 0; level < length; level++)
    {
        index = w.charAt(level) - 'a';
        if (pCrawl.children[index] == null)
            return false;

        pCrawl = pCrawl.children[index];
    }

    return (pCrawl != null && pCrawl.isEndOfWord);}
	
	//Method to insert new words into the trie
	 void Insert(String key)
    {
        int level;
        int length = key.length();
        int index;

        Spam_TrieNode pCrawl = trieNode;

        for (level = 0; level < length; level++)
        {
            index = key.charAt(level) - 'a';
           
            if (pCrawl.children[index] == null)
                pCrawl.children[index] = new Spam_TrieNode();

            pCrawl = pCrawl.children[index];
        }

        
        pCrawl.isEndOfWord = true;
    }
}
public class Spam_Filter {
	public static void main(String args[])throws Exception {Scanner scan=new Scanner(System.in);
	Spam_Trie trie = new Spam_Trie();
	
	//Reading the file that contains all the spam words to be stored in the trie
	File f=new File("C:\\Users\\SANHAPRI\\Documents\\Sneha\\AMRITA\\Second Year\\SEM-III\\DSA-II\\Spam Filter\\Spam Words.txt");
	BufferedReader b = new BufferedReader(new FileReader(f));
	String spam[]=new String[737];//Array that stores all the words that need to be inserted into the trie
	String w[];String s;int j=0;
	while((s=b.readLine())!=null) {
		s=s.toLowerCase();
		w=s.split(" ");
		
		for(int i=0;i<w.length;i++,j++)
			spam[j]=w[i];
	}
	
	System.out.println("***This is a Spam Message Filter***\nIt loops through the given five test files and categorizes each as either spam or not spam");
	
	//Final array that stores all the words without any duplication
	String[] spam_final = new LinkedHashSet<String>(Arrays.asList(spam)).toArray(new String[0]);
	
	for(int i=0;i<spam_final.length;i++)
		 trie.Insert(spam_final[i]);//Inserting the unique words into the trie
	
	//Iterates through all the files in the folder called Test Files
	//The Test Files folder stores all the sample text files that need to classified as ham or spam
	File dir=new File("C:\\Users\\SANHAPRI\\Documents\\Sneha\\AMRITA\\Second Year\\SEM-III\\DSA-II\\Spam Filter\\Test Files");
	File[] dir_list=dir.listFiles();
	for( j=0;j<dir_list.length;j++) {
		
		if(dir_list[j].isFile()) {
			
			File file=new File(dir_list[j].getPath());//Gets the path of each file in the folder
			BufferedReader br = new BufferedReader(new FileReader(file)); 
			 String words[];
			 String st;
			 double c=0;double st_len=0;
			System.out.println("\nThe contents of the file Sample"+(j+1)+" are:");
			
			//Displays the content of each file
			  while ((st = br.readLine()) != null) 
			  {	
				  st=st.toLowerCase();
				  System.out.println(st);
				  words= st.split(" ");
				  st_len+=words.length;
				  for(int i=0;i<words.length;i++)
				  {
					if(trie.Search(words[i])) c++;//String matching is done here
												//c counts the number of spam words in the file
				  	else c+=0;  
				}
			  }
			  br.close();
			  
			  if(c>=15) {//If the number of spam words in a file is >15,it is a spam file
				  System.out.println("Count of all the words: "+st_len);
				  System.out.println("Count of spam words: "+c);
				  System.out.println("The given file is Spam");
				  
				  double perc=(double)Math.round((c/st_len)*100*100)/100;//percentage of spam upto 2 decimal places
				  System.out.println("Percentage of spam is "+perc+" %");
				  
				  if(perc>50)System.out.println("This is severe spam");
				  else System.out.println("This is mild spam");
				  
				  Path source = Paths.get(dir_list[j].getPath()); 
				  Path destFile = Paths.get("C:\\Users\\SANHAPRI\\Documents\\Sneha\\AMRITA\\Second Year\\SEM-III\\DSA-II\\Spam Filter\\Spam\\Sample"+(j+1)+".txt");
				   
				  Files.move(source, destFile);//moves the file from the Test Files folder to the Spam folder
				  }
			  else {
				  {	  System.out.println("Count of all the words: "+st_len);
					  System.out.println("The given file is Not Spam");
					  
					  Path source = Paths.get(dir_list[j].getPath()); 
					  Path destFile = Paths.get("C:\\Users\\SANHAPRI\\Documents\\Sneha\\AMRITA\\Second Year\\SEM-III\\DSA-II\\Spam Filter\\Ham\\Sample"+(j+1)+".txt");
					 
					  Files.move(source, destFile);//moves the file from the Test Files folder to the Ham folder
					  }
			  }
			
		}
			
	}
  }
}
