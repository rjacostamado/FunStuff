import ilog.concert.*;
import ilog.cplex.*;
public class DecompositionAlgorithm {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		smallModel();		
		windorGlassCoModel();
		modelReddyMikks();
		toyFullModel();


	}
	public static void smallModel() {
		
		try {
			
			
			
			int n = 2;
			
			int m = 3;
			
			double[] c = new double[n];
			
			double[][] a = new double[m][n];
			
			double[] b = new double[m];
			
			c[0] = 3;
			c[1] = 5;
			
			a[0][0] = 1;
			a[0][1] = 0;
			a[1][0] = 0;
			a[1][1] = 2;
			a[2][0] = 3;
			a[2][1] = 2;
			
			b[0] = 4;
			b[1] = 12;
			b[2] = 18;
			IloCplex toy = new IloCplex();
			IloNumVar[] x = new IloNumVar[n];
			
			for(int j = 0; j < n; j++) {
				
				x[j] = toy.numVar(0, Double.MAX_VALUE);
				
			}
			
			IloLinearNumExpr[] constraint = new IloLinearNumExpr[m];
			
			
			
			for(int i = 0; i < m; i++) {
				
				constraint[i] = toy.linearNumExpr();
				
				for(int j = 0; j < n; j++) {
					
					constraint[i].addTerm(a[i][j], x[j]);
					
				}
				
				toy.addLe(constraint[i], b[i]);
				
			}	
			
			IloLinearNumExpr objective = toy.linearNumExpr();
			
			for(int j = 0; j < n; j++) {
				
				objective.addTerm(c[j], x[j]);
				
			}			
			
			toy.addMaximize(objective);
			toy.solve();
			
			if(toy.solve()) {
				
				System.out.println("the model was solved");
				
				System.out.println(toy.getValue(objective));
				
				for(int j = 0; j < n; j++) {
					
					System.out.println("x[" + j + "] = " + toy.getValue(x[j]));
					
				}
			}
			
			else {
				
				System.out.println("The model was not solved");
				
			}			
			
		}
		catch(IloException exc) {
			
			exc.printStackTrace();
			
		}
	}
	public static void windorGlassCoModel() {
		//sets the number of variables
		int n = 2;
		//sets the number of constraints
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

		try {
			IloCplex windorGlassCo = new IloCplex();
			
			
			//declares variables
			IloNumVar[] x = new IloNumVar[n];
			for(int j = 0; j < n; j++) {
				x[j] = windorGlassCo.numVar(0, Double.MAX_VALUE);
			}
			
			
			//declares constraints and objective function			
			IloLinearNumExpr[] constraint = new IloLinearNumExpr[m];
			IloLinearNumExpr objective = windorGlassCo.linearNumExpr();
			
			//defines constraints
			
			for(int i = 0; i < m; i++) {
				constraint[i] = windorGlassCo.linearNumExpr();
				for(int j = 0; j < n; j++) {
					constraint[i].addTerm(A[i][j], x[j]);
				}
				windorGlassCo.addLe(constraint[i], b[i]);				
			}
			
			//defines the objective function
			for(int j = 0; j < n; j++) {
				objective.addTerm(c[j], x[j]);
			}
			
			System.out.println(windorGlassCo.toString());
			windorGlassCo.maximize(objective);			
			if(windorGlassCo.solve()) {
				System.out.print("the objective function value is: " + windorGlassCo.getValue(objective));				
				for(int j = 0; j < n; j++) {
					System.out.println("the value of the variable x[" + j + "] is: " + windorGlassCo.getValue(x[j]));						
				}	
			}
			
			else {
				System.out.println("no solution was found");
			}			
		}
		catch(IloException exc){
			exc.printStackTrace();
		}
	}
	public static void modelReddyMikks() {
		try {
			IloCplex ReddyMikks = new IloCplex();
			//sets the number of products
			int n = 2;
			//sets the number of constraints
			int m = 4;
			
			double[] c = new double[n];
			double[][] a = new double[m][n];
			double[] b = new double[m];
			c[0] = 5;
			c[1] = 4;
			a[0][0] = 6;
			a[0][1] = 4;
			a[1][0] = 1;
			a[1][1] = 2;
			a[2][0] = -1;
			a[2][1] = 1;
			a[3][0] = 0;
			a[3][1] = 1;
			b[0] = 24;
			b[1] = 6;
			b[2] = 1;
			b[3] = 2;
			
			//declares variables
			IloNumVar[] x = new IloNumVar[n];
			for(int j = 0; j < n; j++) {
				x[j] = ReddyMikks.numVar(0, Double.MAX_VALUE);
			}
			//declares constraints
			IloLinearNumExpr[] constraints = new IloLinearNumExpr[m];
			//declares objective function
			IloLinearNumExpr objective = ReddyMikks.linearNumExpr();
			
			//defines constraints
			for(int i = 0; i < m; i++) {
				constraints[i] = ReddyMikks.linearNumExpr();
				for(int j = 0; j < n; j++) {
					constraints[i].addTerm(a[i][j], x[j]);
				}
				ReddyMikks.addLe(constraints[i], b[i]);				
			}
			System.out.println(ReddyMikks.toString());			
			
			//defines objective function
			for(int j = 0; j < n; j++) {
				objective.addTerm(c[j], x[j]);
			}
			
			ReddyMikks.addMaximize(objective);
			
			//solves the problem
			if(ReddyMikks.solve()) {
				System.out.println("the objective function value is: " + ReddyMikks.getValue(objective));
				for(int j = 0; j < n; j++) {
					System.out.println(ReddyMikks.getValue(x[j]));
				}				
			}
			
		}
		catch(IloException exc) {
			exc.printStackTrace();
		}
	}
	public static void toyFullModel() {
		try {
			IloCplex cplex = new IloCplex();
			//sets the number of variables
			int n = 4;
			//sets the number of constraints
			int m = 6;
			
			double[] c = new double[n];
			c[0] = 3;
			c[1] = 5;
			c[2] = 2;
			c[3] = 3;
			
			double[][] A = new double[m][n];
			A[0][0] = 2;
			A[0][1] = 4;
			A[0][2] = 5;
			A[0][3] = 2;					
			A[1][0] = 2;
			A[1][1] = 3;
			A[1][2] = 0;
			A[1][3] = 0;
			A[2][0] = 1;
			A[2][1] = 4;
			A[2][2] = 0;
			A[2][3] = 0;
			A[3][0] = 0;
			A[3][1] = 0;
			A[3][2] = -3;
			A[3][3] = -4;
			A[4][0] = 0;
			A[4][1] = 0;
			A[4][2] = 1;
			A[4][3] = 0;
			A[5][0] = 0;
			A[5][1] = 0;
			A[5][2] = 0;
			A[5][3] = 1;
			
			double[] b = new double[m];
			b[0] = 7;
			b[1] = 6;
			b[2] = 4;
			b[3] = -12;
			b[4] = 4;
			b[5] = 3;
			
			//declares variables
			IloNumVar[] x = cplex.numVarArray(n, 0, Double.MAX_VALUE);
			
			//declares and creates the model constraints
			IloLinearNumExpr[] constraint = new IloLinearNumExpr[m];
			
			for(int i = 0; i < m; i++) {
				constraint[i] = cplex.linearNumExpr();
				for(int j = 0; j < n; j++) {
					constraint[i].addTerm(A[i][j], x[j]);
				}
				cplex.addLe(constraint[i], b[i]);
			}
			
			//declares and creates the objective function
			IloLinearNumExpr objective = cplex.linearNumExpr();
			for(int j = 0; j < n; j++) {
				objective.addTerm(c[j], x[j]);
			}
			
			cplex.addMaximize(objective);		
			
			if(cplex.solve()) {
				System.out.print("the objective function value is: " + cplex.getValue(objective));				
				for(int j = 0; j < n; j++) {
					System.out.println("the value of the variable x[" + j + "] is: " + cplex.getValue(x[j]));						
				}	
			}
			else {
				System.out.println("no solution was found");
			}			
		}
		catch(IloException exc){
			exc.printStackTrace();
		}
	}




}
