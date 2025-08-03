package MarkovChainsApplicationCharacterWise;

/**
 * Write a description of Tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
import edu.duke.*;
public class Tester {
    public void testGetFollows(){
        MarkovOne markov = new MarkovOne();
        String st = "this is a test yes this is a test.";
        markov.setTraining(st);
        ArrayList<String> follows = markov.getFollows("est");
        System.out.println("The resulting array list is " + follows + " and its size is " + follows.size());
    }
    
    public void testGetFollowsWithFile(){
        MarkovOne markov = new MarkovOne();
        FileResource fr = new FileResource();
        String st = fr.asString();
		st = st.replace('\n', ' ');
		//System.out.println(st.length());
		markov.setTraining(st);
		ArrayList<String> follows = markov.getFollows("he");
		System.out.println("The size is " + follows.size());
    }

}
