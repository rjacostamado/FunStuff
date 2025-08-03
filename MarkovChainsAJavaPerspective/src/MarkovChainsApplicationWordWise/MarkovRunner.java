package MarkovChainsApplicationWordWise;

/**
 * Write a description of class MarkovRunner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;

public class MarkovRunner {
	
	public static void main(String[] args) {
		int numberOfReplicas = 1;
        FileResource fr = new FileResource(); 
        String st = fr.asString(); 
        st = st.replace('\n', ' ');
        MarkovWord markovOne = new MarkovWord(1);
        MarkovWord markovTwo = new MarkovWord(2);
        MarkovWord markovThree = new MarkovWord(3);
        MarkovWord markovFour = new MarkovWord(4);
        MarkovWord markovFive = new MarkovWord(5);
        MarkovWord markovTen = new MarkovWord(100);
        EfficientMarkovWord ef = new EfficientMarkovWord(50);
		System.out.println("Generating " + numberOfReplicas + " replications of text using a one order Markov Chain");
        double startTime = System.nanoTime();
		runModel(markovOne, st, 100, 1199, numberOfReplicas);
		double totalTime = System.nanoTime() - startTime;
		System.out.println("It took it " + totalTime);
		System.out.println("Generating " + numberOfReplicas + " replications of text using a two order Markov Chain");
        startTime = System.nanoTime();
		runModel(markovTwo, st, 100, 1199, numberOfReplicas);
		totalTime = System.nanoTime() - startTime;
		System.out.println("It took it " + totalTime);
		System.out.println("Generating " + numberOfReplicas + " replications of text using a three order Markov Chain");
        startTime = System.nanoTime();
		runModel(markovThree, st, 100, 1199, numberOfReplicas);
		totalTime = System.nanoTime() - startTime;
		System.out.println("It took it " + totalTime);
		System.out.println("Generating " + numberOfReplicas + " replications of text using a four order Markov Chain");
        startTime = System.nanoTime();
		runModel(markovFour, st, 100, 1199, numberOfReplicas);
		totalTime = System.nanoTime() - startTime;
		System.out.println("It took it " + totalTime);
		System.out.println("Generating " + numberOfReplicas + " replications of text using a five order Markov Chain");
        startTime = System.nanoTime();
		runModel(markovFive, st, 100, 1199, numberOfReplicas);
		totalTime = System.nanoTime() - startTime;
		System.out.println("It took it " + totalTime);
		System.out.println("Generating " + numberOfReplicas + " replications of text using a ten order Markov Chain");
        startTime = System.nanoTime();
		runModel(markovTen, st, 100, 1199, numberOfReplicas);
		totalTime = System.nanoTime();
		System.out.println("It took it " + totalTime);
		System.out.println("Generating " + numberOfReplicas + " replications of text using a ten order Markov Chain efficiently implemented");
        startTime = System.nanoTime();
		runModel(ef, st, 100, 1199, numberOfReplicas);
		totalTime = System.nanoTime() - startTime;
		System.out.println("It took it " + totalTime);
    }
	
    public static void runModel(IMarkovModel markov, String text, int size, int replications){ 
        markov.setTraining(text); 
        System.out.println("running with " + markov.getClass().toString()); 
        for(int k=0; k < replications; k++){
        	String st = markov.getRandomText(size);
        	printOut(st);
        }
    }

    public static void runModel(IMarkovModel markov, String text, int size, int seed, int replications){ 
        markov.setTraining(text); 
        markov.setRandom(seed);
        System.out.println("running with " + markov.getClass().toString()); 
        for(int k=0; k < replications; k++){ 
            String st = markov.getRandomText(size); 
            printOut(st); 
        } 
    } 

    public void runMarkov(String st) { 
        MarkovWord markovword = new MarkovWord(5);
        runModel(markovword, st, 200, 844); 
    } 
    
    public void runMarkovModel(String st){
        MarkovWord mw = new MarkovWord(3);
        runModel(mw, st, 200, 643);
    }

    public void testHashMap(String st){
        EfficientMarkovWord mw = new EfficientMarkovWord(3);
        runModel(mw, st, 5000, 371);
        mw.printHashMapInfo();
    }

    private static void printOut(String s){
        String[] words = s.split("\\s+");
        int psize = 0;
        System.out.println("----------------------------------");
        for(int k=0; k < words.length - 2; k += 2){
            System.out.println(words[k]+ " " + words[k + 1]);
            psize += words[k].length() + 1;
            if (psize > 60) {
                System.out.println(); 
                psize = 0;
            }

        } 
        System.out.println("\n----------------------------------");
    } 

}
