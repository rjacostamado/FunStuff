package MarkovChainsApplicationWordWise;

public class WordGram {
    private String[] myWords;

    public WordGram(String[] source, int start, int size) {
        myWords = new String[size];
        System.arraycopy(source, start, myWords, 0, size);
    }

    public String wordAt(int index) {
        if (index < 0 || index >= myWords.length) {
            throw new IndexOutOfBoundsException("bad index in wordAt " + index);
        }
        return myWords[index];
    }

    public int length(){
        return myWords.length;
    }

    public String toString(){
        String ret = "";
        for(int k = 0; k < myWords.length; k++){
            ret += myWords[k] + " ";
        }
        return ret.trim();
    }

    public boolean equals(Object o) {
        WordGram other = (WordGram) o;
        //System.out.println(other);
        //System.out.println(this);
        if(this.length() != other.length()){
            return false;
        }
        //System.out.println(other.wordAt(0));
        for(int k = 0; k < myWords.length; k++){
            //System.out.println(myWords[k]);            
            if(!myWords[k].equals(other.wordAt(k))){
                return false;
            }
        }
        return true;
    }

    public WordGram shiftAdd(String word) {        
        // shift all words one towards 0 and add word at the end. 
        // you lose the first word
        // TODO: Complete this method
        String[] newWords = new String[myWords.length];
        for(int k = 0; k < myWords.length - 1; k++){
            newWords[k] = myWords[k + 1];
        }
        newWords[myWords.length - 1] = word;
        WordGram out = new WordGram(newWords, 0, newWords.length);
        return out;
    }
    
    public int hashCode(Object o){
        int hashCode = 0;
        WordGram other = (WordGram) o;
        String text = other.toString();
        hashCode = text.hashCode();  
        return hashCode;
    }
    
    
}