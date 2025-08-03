package MarkovChainsApplicationCharacterWise;

/**
 * Write a description of MarkovFour here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;

public class MarkovFour {
    private String myText;
	private Random myRandom;
	
	public MarkovFour() {
		myRandom = new Random();
	}
	
	public void setRandom(int seed){
		myRandom = new Random(seed);
	}
	
	public void setTraining(String s){
		myText = s.trim();
	}
	
	public String getRandomText(int numChars){
	    StringBuffer sb = new StringBuffer();
	    int index = myRandom.nextInt(myText.length() - 4);
	    String key = myText.substring(index, index + 4);
	    sb.append(key);
		for(int k=0; k < numChars -4; k++){
		    ArrayList<String> follows = getFollows(key);
		    if(follows.size() == 0){
		        break;
		    }
		    index = myRandom.nextInt(follows.size());
		    String next = follows.get(index);
		    sb.append(next);
		    key = key.substring(1) + next;
		}		
		return sb.toString();
	}
	
	public ArrayList<String> getFollows(String key){
	    ArrayList<String> follows = new ArrayList<String>();
	    int index = 0;
	    int pos = 0;
	    while(!(myText.indexOf(key, pos) == -1) && pos < myText.length() - 2){
	        index = myText.indexOf(key, pos);
	        if(index == myText.length() - key.length()){
	            break;
	           }
	        pos = index + 1;
	        follows.add(myText.substring(index + key.length(), key.length() + pos));
	        //System.out.println(myText.substring(index + key.length(), key.length() + pos));
	    }
	    //System.out.println(follows);
	    return follows;
	}

}
