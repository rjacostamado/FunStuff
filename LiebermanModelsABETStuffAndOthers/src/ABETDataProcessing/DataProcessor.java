package ABETDataProcessing;


import edu.duke.*;

import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.csv.*;

public class DataProcessor {
	public static HashMap<String, HashMap<String, double[]>> evaluadorEvaluados 
	= new HashMap<String, HashMap<String, double[]>>();
	public static HashMap<String, ArrayList<String>> evaluadoEvaluadores 
	= new HashMap<String, ArrayList<String>>();
	public static HashMap<String, Double> evaluationResults 
	= new HashMap<String, Double>();
	public static void main(String[] args) {
		// TODO Auto-generated constructor stub
		/*
		String filePath = "C:/Users/RolandoJose/Documents/1. Mis Cursos/ICESI/2020-2/ABET/"
				+ "PE/Evaluación de pares trabajo Cadenas de Markov Grupo 001.csv/"
				+ "Evaluación de pares trabajo Cadenas de Markov Grupo 001.csv";
				*/
		String filePath = "C:/Users/RolandoJose/Documents/1. Mis Cursos/ICESI/2020-2/ABET/IO/"
				+ "Evaluación de pares trabajo conformación de equipos4.csv/"
				+ "Evaluación de pares trabajo conformación de equipos.csv";
		FileResource fr = new FileResource(filePath);
		CSVParser parser = fr.getCSVParser(true);
		double[] grades = new double[5];
		for(CSVRecord record : parser)
		{
			String evaluator = record.get("Selecciona tu nombre");
			String evaluated = record.get("Elige la persona que vas a evaluar");
			grades[0] = Double.parseDouble(record.get("Contribución al trabajo del equipo ").substring(0,1));
			grades[1] = Double.parseDouble(record.get("Interacción con los compañeros de equipo").substring(0,1));
			grades[2] = Double.parseDouble(record.get("Mantener al equipo enfocado (Seguimiento / Retroalimentación) ").substring(0,1));
			grades[3] = Double.parseDouble(record.get("Calidad Esperada ").substring(0,1));
			grades[4] = Double.parseDouble(record.get("Poseer conocimientos, destrezas y habilidades relevantes").substring(0,1));
			if(evaluadorEvaluados.containsKey(evaluator))
			{
				evaluadorEvaluados.get(evaluator).put(evaluated, new double[5]);
				for(int j = 0; j < 5; j++)
				{
					evaluadorEvaluados.get(evaluator).get(evaluated)[j] = grades[j];
				}
			}
			else
			{
				evaluadorEvaluados.put(evaluator, new HashMap<String, double[]>());
				evaluadorEvaluados.get(evaluator).put(evaluated, new double[5]);
				for(int j = 0; j < 5; j++)
				{
					evaluadorEvaluados.get(evaluator).get(evaluated)[j] = grades[j];
				}
			}
			if(evaluadoEvaluadores.containsKey(evaluated))
			{
				evaluadoEvaluadores.get(evaluated).add(evaluator);
			}
			else
			{
				evaluadoEvaluadores.put(evaluated, new ArrayList<String>());
				evaluadoEvaluadores.get(evaluated).add(evaluator);
			}
		}
		

		for(String evaluado : evaluadoEvaluadores.keySet())
		{
			double[] averagedGrades = new double[5];
			for(String evaluador : evaluadoEvaluadores.get(evaluado))
			{
				averagedGrades[0] += evaluadorEvaluados.get(evaluador).get(evaluado)[0];
				averagedGrades[1] += evaluadorEvaluados.get(evaluador).get(evaluado)[1];
				averagedGrades[2] += evaluadorEvaluados.get(evaluador).get(evaluado)[2];
				averagedGrades[3] += evaluadorEvaluados.get(evaluador).get(evaluado)[3];
				averagedGrades[4] += evaluadorEvaluados.get(evaluador).get(evaluado)[4];
			}
			int n = evaluadoEvaluadores.get(evaluado).size();
			averagedGrades[0] = averagedGrades[0] / n;
			averagedGrades[1] = averagedGrades[1] / n;
			averagedGrades[2] = averagedGrades[2] / n;
			averagedGrades[3] = averagedGrades[3] / n;
			averagedGrades[4] = averagedGrades[4] / n;
			System.out.println(evaluado + "," + averagedGrades[0] + "," + averagedGrades[1]
					+ "," + averagedGrades[2] + "," + averagedGrades[3]
							+ "," + averagedGrades[4]);
		}
	}
}
