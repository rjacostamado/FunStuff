import ilog.concert.*;
import ilog.cplex.*;
import edu.duke.*;
import org.apache.commons.csv.*;

public class SimpleModels{
	public static int m;
	public static int n;
	public static String[] nombreCompleto;
	public static Double[] seeing;
	public static Double[] feeling;
	public static Double[] dreaming;
	public static Double[] making;
	public static Double[] learning;
	public static Integer[] male;
	public static Integer[] female;
	public static int lowerBound;
	public static int upperBound;
	public static Double[] teamScore;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//windorGlass();
		String filePath = "C:/Users/RolandoJose/Documents/1. Mis Cursos/ICESI/2020-2/ABET/IO"
				+ "/Registro para la obtención de camisetas.csv/RespuestasIO.csv";
		FileResource fr = new FileResource(filePath);
		//FileResource fr = new FileResource("C:/Users/RolandoJose/workspace/LiebermanModels/Data/TestingFile.csv");
		//FileResource fr = new FileResource("C:/Users/RolandoJose/workspace/LiebermanModels/Data/Neumeier_PE.csv");
		//FileResource fr = new FileResource("C:/Users/RolandoJose/workspace/LiebermanModels/Data/Neumeier_IO.csv");
		//FileResource fr = new FileResource("C:/Users/RolandoJose/workspace/LiebermanModels/Data/003.Neumeier_PE.csv");
		//FileResource fr = new FileResource("C:/Users/RolandoJose/workspace/LiebermanModels/Data/001.Neumeier_PE.csv");
		CSVParser parser = fr.getCSVParser(true);
		/*
		for(CSVRecord record : parser) {
			records += 1;
		}
		*/
		/*
		 * m is the number of students
		 * n is the number of groups to be formed
		 */
		m = 27;
		nombreCompleto = new String[m];
		seeing = new Double[m];
		feeling = new Double[m];
		dreaming = new Double[m];
		making = new Double[m];
		learning = new Double[m];
		male = new Integer[m];
		female = new Integer[m]; 
		upperBound = 5;
		lowerBound = 3;
		teamScore = new Double[m];
		for(int i = 0; i < m; i++)
			teamScore[i] = 0.0;
		
		int index = 0;		
		for (CSVRecord record : parser){

			//nombreCompleto[index] = record.get("Nombres") + "," + record.get("Apellidos");
			nombreCompleto[index] = record.get("Nombre Completo");
			//nombreCompleto[index] = record.get("Código");
			
			seeing[index] = Double.parseDouble(record.get("Seeing")) * Double.parseDouble(record.get("SeeingWeight"));
			feeling[index] = Double.parseDouble(record.get("Feeling")) * Double.parseDouble(record.get("FeelingWeight"));
			dreaming[index] = Double.parseDouble(record.get("Dreaming")) * Double.parseDouble(record.get("DreamingWeight"));
			making[index] = Double.parseDouble(record.get("Making")) * Double.parseDouble(record.get("MakingWeight"));
			learning[index] = Double.parseDouble(record.get("Learning")) * Double.parseDouble(record.get("LearningWeight"));
			/*
			seeing[index] = Double.parseDouble(record.get("Seeing"));
			feeling[index] = Double.parseDouble(record.get("Feeling"));
			dreaming[index] = Double.parseDouble(record.get("Dreaming"));
			making[index] = Double.parseDouble(record.get("Making"));
			learning[index] = Double.parseDouble(record.get("Learning"));
			*/
			if(Integer.parseInt(record.get("Sexo")) == 1)
			{
				male[index] = 1;
				female[index] = 0;
			}
			else
			{
				male[index] = 0;
				female[index] = 1;
			}
			index += 1;			
		}
		for(n = 8; n < 9; n++)
		{
			System.out.println("*****************************************");
			System.out.println("Beginning of the experiment for n = " + n);
			createTeamsOfWork(seeing, feeling, dreaming, making, learning, 
							male, female, upperBound, lowerBound, n);
			System.out.println("End of the experiment for n = " + n);
			System.out.println("*****************************************");
			}
		for(int j = 0; j < 27; j++)
		createPersonalizedTeamsWork(seeing, feeling, dreaming, making, learning, 
				male, female, upperBound, lowerBound, j);
	}
	
	public static void windorGlass() {


		try {
			
			int n = 2;
			int m = 3;
			double[] c = new double[n];
			c[0] = 3;
			c[1] = 5;
			
			double[][] A = new double[m][n];
			A[0][0] = 1;
			A[0][1] = 0;
			A[1][0] = 0;
			A[1][1] = 2;
			A[2][0] = 3;
			A[2][1] = 2;
			
			double[] b = new double[m];
			
			b[0] = 4;
			b[1] = 12;
			b[2] = 18;
			
			IloCplex windorGlasModel = new IloCplex();
			IloNumVar[] x = new IloNumVar[n];
			for(int j = 0; j < n; j++) {
				//creates the variables of the model
				x[j] = windorGlasModel.numVar(0, Double.MAX_VALUE);
			}
			
			IloLinearNumExpr[] constraints = new IloLinearNumExpr[m];
			for(int i = 0; i < m; i++) {
				//declares the constraints as linear numerical expressions
				constraints[i] = windorGlasModel.linearNumExpr();
				for(int j = 0; j < n; j++) {
					//defines the left hand side of the constraints
					constraints[i].addTerm(A[i][j], x[j]);
				}		
				//defines the constraints of the model by using the left hand side and the right hand side vector
				windorGlasModel.addLe(constraints[i], b[i]);
			}
			//declares the objective function of the model as a linear numerical expression
			IloLinearNumExpr objective = windorGlasModel.linearNumExpr();
			for(int j = 0; j < n; j++) {
				//defines the objective function of the model
				objective.addTerm(c[j], x[j]);
			}
			//defines the direction of the optimization process (maximize)
			windorGlasModel.addMaximize(objective);
			int numberOfRows = windorGlasModel.getNrows();
			System.out.println("allright, the number of rows of the Windor Glass Model is: " + numberOfRows);
			//solves the problem
			windorGlasModel.solve();
			//presents the user with the results of solving the problem
			System.out.println("the objective function value is: " + windorGlasModel.getValue(objective));
			System.out.println("the optimal values of the decisoin variables are:");
			for(int j = 0; j < n; j++) {
				System.out.println(windorGlasModel.getValue(x[j]));
			}
			
		} catch (IloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void windorLPMatrix() {
		IloCplex windorGlassLPMatrix;
		try {
			windorGlassLPMatrix = new IloCplex();
			IloLPMatrix windorGlasslp = windorGlassLPMatrix.addLPMatrix();
		} catch (IloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void createTeamsOfWork(Double[] seeing, Double[] feeling, Double[] dreaming, 
			Double[] making, Double[] learning, Integer[] male,
			Integer[] female, Integer upperBound, Integer lowerBound, Integer n) {
		try {
			IloCplex teamGenerator = new IloCplex();
			IloNumVar[][] x = new IloNumVar[m][n];
			for(int i = 0; i < m; i++) {
				for(int j = 0; j < n; j++) {
					x[i][j] = teamGenerator.intVar(0, 1);
				}
			}
			
			IloLinearNumExpr[] teamSize = new IloLinearNumExpr[n];
			IloLinearNumExpr[] studentTeam = new IloLinearNumExpr[m];
			IloLinearNumExpr[] studentSexMale = new IloLinearNumExpr[m];
			IloLinearNumExpr[] studentSexFemale = new IloLinearNumExpr[m];
			
			for(int j = 0; j < n; j++) {
				teamSize[j] = teamGenerator.linearNumExpr();
			}
			
			for(int i = 0; i < m; i++) {
				studentTeam[i] = teamGenerator.linearNumExpr();
				studentSexMale[i] = teamGenerator.linearNumExpr();
				studentSexFemale[i] = teamGenerator.linearNumExpr();
			}
			
			for(int j = 0; j < n; j++) {
				for(int i = 0; i < m; i++) {
					teamSize[j].addTerm(1, x[i][j]);
					studentSexMale[j].addTerm(male[i], x[i][j]);
					studentSexFemale[j].addTerm(female[i], x[i][j]);
				}
			}
			
			for(int i = 0; i < m; i++) {
				for(int j = 0; j < n; j++) {
					studentTeam[i].addTerm(1, x[i][j]);
				}
			}
			
			for(int j = 0; j < n; j++) {
				teamGenerator.addLe(teamSize[j], upperBound);
				teamGenerator.addGe(teamSize[j], lowerBound);
				teamGenerator.addGe(studentSexMale[j], 1);
				teamGenerator.addGe(studentSexFemale[j], 1);
			}
			
			for(int i = 0; i < m; i++) {
				teamGenerator.addEq(studentTeam[i], 1);
			}
			
			IloLinearNumExpr objective = teamGenerator.linearNumExpr();
			for(int i = 0; i < m; i++) {
				for(int j = 0; j < n; j++) {
					objective.addTerm(seeing[i], x[i][j]);
					objective.addTerm(feeling[i], x[i][j]);
					objective.addTerm(dreaming[i], x[i][j]);
					objective.addTerm(making[i], x[i][j]);
					objective.addTerm(learning[i], x[i][j]);
				}
			}
			
			teamGenerator.addMaximize(objective);
			if(teamGenerator.solve())
			{
				for(int i = 0; i < m; i++)
				{
					for(int j = 0; j < n; j++)
					{
						if(teamGenerator.getValue(x[i][j]) == 1)
						{
							System.out.println(nombreCompleto[i] + "," + (j + 1));
						}
					}
				}
				for(int j = 0; j < n; j++)
				{
					for(int i = 0; i < m; i++)
					{
						teamScore[i] += ((seeing[i] + feeling[i] + dreaming[i] + making[i] + learning[i]) * teamGenerator.getValue(x[i][j]));
					}
				}
				for(int i = 0; i < n; i++)
				{
					System.out.println("teamScore[" + i + "] = " + teamScore[i]);
				}
			}
			else
			{
				System.out.println("No solution found");
			}
			
		}
		catch (IloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void createPersonalizedTeamsWork(Double[] seeing, Double[] feeling, Double[] dreaming, 
			Double[] making, Double[] learning, Integer[] male,
			Integer[] female, Integer upperBound, Integer lowerBound, int student) {
		try {
			IloCplex personalizedTeamGenerator = new IloCplex();
			IloNumVar[] x = new IloNumVar[m];
			for(int j = 0; j < m; j++) {
					x[j] = personalizedTeamGenerator.intVar(0, 1);
			}			
			IloLinearNumExpr teamSize;
			IloLinearNumExpr studentSexMale;
			IloLinearNumExpr studentSexFemale;
			IloLinearNumExpr studentIncluded;
			
			teamSize = personalizedTeamGenerator.linearNumExpr();
			studentIncluded = personalizedTeamGenerator.linearNumExpr();
			studentSexMale = personalizedTeamGenerator.linearNumExpr();
			studentSexFemale = personalizedTeamGenerator.linearNumExpr();
			
			studentIncluded.addTerm(1, x[student]);
			for(int j = 0; j < m; j++) {
				teamSize.addTerm(1, x[j]);
				studentSexMale.addTerm(male[j], x[j]);
				studentSexFemale.addTerm(female[j], x[j]);
			}
			personalizedTeamGenerator.addLe(teamSize, upperBound);
			personalizedTeamGenerator.addGe(teamSize, lowerBound);
			personalizedTeamGenerator.addGe(studentSexMale, 1);
			personalizedTeamGenerator.addGe(studentSexFemale, 1);
			personalizedTeamGenerator.addEq(studentIncluded, 1);
			
			IloLinearNumExpr objective = personalizedTeamGenerator.linearNumExpr();
			for(int j = 0; j < m; j++) {
				objective.addTerm(seeing[j], x[j]);
				objective.addTerm(feeling[j], x[j]);
				objective.addTerm(dreaming[j], x[j]);
				objective.addTerm(making[j], x[j]);
				objective.addTerm(learning[j], x[j]);
			}
			personalizedTeamGenerator.addMaximize(objective);
			if(personalizedTeamGenerator.solve())
			{
				for(int j = 0; j < m; j++)
				{
					if(personalizedTeamGenerator.getValue(x[j]) == 1)
					{
						System.out.println(nombreCompleto[j] + "," + (j + 1));
					}
				}
				for(int j = 0; j < m; j++)
				{
						teamScore[0] += ((seeing[j] + feeling[j] + dreaming[j] + making[j] + learning[j]) * 
								personalizedTeamGenerator.getValue(x[j]));
				}
				System.out.println("teamScore[" + student + "] = " + teamScore[student]);
			}
			else
			{
				System.out.println("No solution found");
			}
			
		}
		catch (IloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

	