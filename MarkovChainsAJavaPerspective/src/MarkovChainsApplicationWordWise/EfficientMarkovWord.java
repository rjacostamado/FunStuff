package MarkovChainsApplicationWordWise;

/**
 * Write a description of EfficientMarkovWord here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.util.*;

public class EfficientMarkovWord  implements IMarkovModel {
    private String[] myText;
    private Random myRandom;
    private int myOrder;
    private HashMap<Integer, ArrayList<String>> myMap;
    private HashMap<Integer, WordGram> wgMap;
    
    public EfficientMarkovWord(int order) {
        myRandom = new Random();
        myOrder = order;
        myMap = new HashMap<Integer, ArrayList<String>>();
        wgMap = new HashMap<Integer, WordGram>();
    }
    
    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }
    
    public void setTraining(String text){
        myText = text.split("\\s+");
        for(int i = 0; i <= myText.length - myOrder; i += 1){
            WordGram wg = new WordGram(myText, i, myOrder);
            myMap = buildMap(wg);
        }
    }
    
    public int indexOf(String[] words, WordGram target, int start){
        for(int k = start; k < words.length - myOrder; k++){
            WordGram wg = new WordGram(words, k, myOrder); 
            if(wg.equals(target)){
                return k;
            }
        }
        return -1;
    }
    
    private ArrayList<String> getFollows(WordGram kGram) {
        ArrayList<String> follows = buildMap(kGram).get(kGram.hashCode(kGram));
        return follows;
    }
    
    public String getRandomText(int numWords){
        StringBuffer sb = new StringBuffer();
        int index = myRandom.nextInt(myText.length - myOrder);  // random word to start with
        //String key = myText[index];
        //int index = 0;
        WordGram kGram = new WordGram(myText, index, myOrder);
        sb.append(kGram);
        sb.append(" ");
        for(int k = 0; k < numWords - myOrder; k++){
            ArrayList<String> follows = getFollows(kGram);
            //System.out.println(follows);
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
    
    public HashMap<Integer, ArrayList<String>> buildMap(WordGram wg){
        int pos = 0;        
        if(!myMap.containsKey(wg.hashCode(wg))){
            wgMap.put(wg.hashCode(wg), wg);
            myMap.put(wg.hashCode(wg), new ArrayList<String>());
            while(pos < myText.length){
                int start = indexOf(myText, wg, pos);
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
                myMap.get(wg.hashCode(wg)).add(next);
                //follows.add(next);
                pos = start + 1;  
                //System.out.println("pos is now " + pos);
            }
        }       
        return myMap;
    }    
    
    public void printHashMapInfo(){
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        setTraining(st);        
        System.out.println(myText.length);
        //System.out.println(myMap);
        System.out.println("The number of keys in the HashMap is " + myMap.size());
        int largest = 0;
        for(int wg : myMap.keySet()){
            if(myMap.get(wg).size() > largest){
                largest = myMap.get(wg).size();
            }
        }
        System.out.println("The largest value of the HashMap is " + largest);
        System.out.println("these are the keys with the highest values");
        for(int wg : myMap.keySet()){
            if(myMap.get(wg).size() == largest){
                //System.out.println(wgMap.get(wg));
                System.out.println();
            }
            if(myMap.get(wg).size() == 0){
                System.out.println("this poor fella here does not have any followers");
                System.out.println();
                //System.out.println(wgMap.get(wg));
                System.out.println();
            }
        }
        //System.out.println("The entire map is");
        System.out.println();
        //System.out.println(myMap);
        System.out.println();
        System.out.println("and its size is: " + myMap.size());
    }

}
