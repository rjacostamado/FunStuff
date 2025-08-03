package MarkovChainsApplicationCharacterWise;

/**
 * Write a description of class MarkovRunner here.
 * 
 * @author Duke Software
 * @version 1.0
 */

import edu.duke.*;

public class MarkovRunner {
	
	public static void main(String[] args) {
		int numberOfReplicas = 3;
		int numberOfWords = 500;
		int order = 100;
		FileResource fr = new FileResource();
		String st = fr.asString();
		st.replace('\n', ' ');
		System.out.println("Generating " + numberOfReplicas + " replications of text using an order of zero characters Markov Chain");
		runMarkovZero(numberOfReplicas, numberOfWords, st);
		System.out.println("Generating " + numberOfReplicas + " replications of text using an order of one character Markov Chain");
		runMarkovOne(numberOfReplicas, numberOfWords, st);
		System.out.println("Generating " + numberOfReplicas + " replications of text using an order of two characters Markov Chain");
		runMarkovTwo(numberOfReplicas, numberOfWords, st);
		System.out.println("Generating " + numberOfReplicas + " replications of text using an order of three characters Markov Chain");
		runMarkovThree(numberOfReplicas, numberOfWords, st);
		System.out.println("Generating " + numberOfReplicas + " replications of text using an order of four characters Markov Chain");
		runMarkovFour(numberOfReplicas, numberOfWords, st);
		System.out.println("Generating " + numberOfReplicas + " replications of text using an order of " + order + " characters Markov Chain");
		runMarkovModel(numberOfReplicas, numberOfWords, st, order);
	}
    public static void runMarkovZero(int numberOfReplicas, int numberOfWords, String trainingString) {
		MarkovZero markov = new MarkovZero();
		markov.setTraining(trainingString);
		markov.setRandom(1199);
		for(int k=0; k < 1; k++){
			String text = markov.getRandomText(5000);
			printOut(text);
		}
	}
	
	public static void runMarkovOne(int numberOfReplicas, int numberOfWords, String trainingString) {
		MarkovOne markov = new MarkovOne();
		markov.setTraining(trainingString);
		markov.setRandom(1199);
		for(int k=0; k < 3; k++){
			String text = markov.getRandomText(500);
			printOut(text);
		}
	}
	
	public static void runMarkovTwo(int numberOfReplicas, int numberOfWords, String trainingString) {
		MarkovTwo markov = new MarkovTwo();
		markov.setTraining(trainingString);
		markov.setRandom(1199);
		for(int k=0; k < 3; k++){
			String text = markov.getRandomText(500);
			printOut(text);
		}
	}
	
	public static void runMarkovThree(int numberOfReplicas, int numberOfWords, String trainingString) {
		MarkovThree markov = new MarkovThree();
		markov.setTraining(trainingString);
		markov.setRandom(1199);
		for(int k=0; k < 3; k++){
			String text = markov.getRandomText(500);
			printOut(text);
		}
	}
	
	public static void runMarkovFour(int numberOfReplicas, int numberOfWords, String trainingString) {
		MarkovFour markov = new MarkovFour();
		markov.setTraining(trainingString);
		markov.setRandom(1199);
		for(int k=0; k < 3; k++){
			String text = markov.getRandomText(715);
			printOut(text);
		}
	}
	
	public static void runMarkovModel(int numberOfReplicas, int numberOfWords, String trainingString, int order) {
		MarkovModel markov = new MarkovModel(order);
		markov.setTraining(trainingString);
		markov.setRandom(1199);
		for(int k=0; k < 3; k++){
			String text = markov.getRandomText(500);
			printOut(text);
		}
	}
	
	private static void printOut(String s){
		String[] words = s.split("\\s+");
		int psize = 0;
		System.out.println("----------------------------------");
		for(int k=0; k < words.length; k++){
			System.out.print(words[k]+ " ");
			psize += words[k].length() + 1;
			if (psize > 60) {
				System.out.println();
				psize = 0;
			}
		}
		System.out.println("\n----------------------------------");
	}
	
}
