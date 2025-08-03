package MarkovChainsApplicationWordWise;

/**
 * Write a description of MarkovWord here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;

public class MarkovWord implements IMarkovModel{
    private String[] myText;
    private Random myRandom;
    private int myOrder;
    
    public MarkovWord(int order) {
        myRandom = new Random();
        myOrder = order;
    }
    
    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }
    
    public void setTraining(String text){
        myText = text.split("\\s+");
    }
    
    public int indexOf(String[] words, WordGram target, int start){
        for(int k = start; k < words.length - myOrder; k++){
            WordGram wg = new WordGram(words, k, myOrder); 
            //System.out.println(wg);
            if(wg.equals(target)){
                //System.out.println(wg);
                return k;
            }
        }
        return -1;
    }
    
    private ArrayList<String> getFollows(WordGram kGram) {
        ArrayList<String> follows = new ArrayList<String>();
        
        int pos = 0;
        //System.out.println("the kgram is " + kGram);
        //System.out.println("the text length is " + myText.length);
        while(pos < myText.length){
            int start = indexOf(myText, kGram, pos);
            //System.out.println("start is " + start);
            if(start == -1){
                break;
            }
            if(start >= myText.length - myOrder){
                break;
            }
            //System.out.println(start + myOrder);
            String next = myText[start + myOrder];
            //System.out.println("Added word to follows is " + next);
            follows.add(next);
            pos = start + 1;  
            //System.out.println("pos is now " + pos);
        }       
        //System.out.println("the follows of " + kGram + " is " + follows);
        return follows;
    }
    
    public String getRandomText(int numWords){
        StringBuffer sb = new StringBuffer();
        int index = myRandom.nextInt(myText.length - myOrder);  // random word to start with
        //String key = myText[index];
        //int index = 0;
        WordGram kGram = new WordGram(myText, index, myOrder);
                        
        //System.out.println(kGram);
        sb.append(kGram);
        sb.append(" ");
        for(int k = 0; k < numWords - myOrder; k++){
            ArrayList<String> follows = getFollows(kGram);
            if (follows.size() == 0) {
                break;
            }
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            sb.append(" ");
            kGram = kGram.shiftAdd(next);
            //System.out.println(kGram);
        }       
        return sb.toString().trim();
    }

}
