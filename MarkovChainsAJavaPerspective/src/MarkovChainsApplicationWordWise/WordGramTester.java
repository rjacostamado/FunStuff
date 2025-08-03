package MarkovChainsApplicationWordWise;
import java.util.*;

public class WordGramTester {
    public void testWordGram(){
        String source = "this is a test this is a test this is a test of words";
        String[] words = source.split("\\s+");
        int size = 4;
        for(int index = 0; index <= words.length - size; index += 1) {
            WordGram wg = new WordGram(words,index,size);
            System.out.println(index+"\t"+wg.length()+"\t"+wg);
        }
    }
    
    public void testWordGramEquals(){
        String source = "this is a test this is a test this is a test of words";
        String[] words = source.split("\\s+");
        ArrayList<WordGram> list = new ArrayList<WordGram>();
        int size = 4;
        for(int index = 0; index <= words.length - size; index += 1) {
            WordGram wg = new WordGram(words,index,size);
            list.add(wg);
        }
        WordGram first = list.get(0);
        System.out.println("checking " + first);
        for(int k=0; k < list.size(); k++){
            //if (first == list.get(k)) {
              if (first.equals(list.get(k))) {
                System.out.println("matched at " + k + " " + list.get(k));
            }
        }
    }
    
    public void testShiftAdd(){
        String[] myWords = new String[6];
        myWords[0] = "this";
        myWords[1] = "is";
        myWords[2] = "a";
        myWords[3] = "test";
        myWords[4] = "yes";
        myWords[5] = "this";
        WordGram one = new WordGram(myWords, 0, myWords.length);
        WordGram test1 = one.shiftAdd("is");
        System.out.println(test1);
    }

    
}
