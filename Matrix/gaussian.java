import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;
public class gaussian {
	public static void main(String[]args) {
		boolean spp = false, doublePrecision = false;
		try {
			for(int i = 0; i < args.length;i++) {
				if(args[i].equals("--spp")) {
					spp = true;
				}
				if(args[i].equals("--double")) {
					doublePrecision = true;
				}
				if(spp && doublePrecision && args[i].substring(args[i].length()-4).equals(".lin")) {
					File file = new File(args[i]);
					Scanner fReader = new Scanner(file);
					int n = fReader.nextInt();
					fReader.nextLine();
					double[][] matrix = new double[n][n];
					for(int a = 0; a < matrix.length; a++) {
						for(int b = 0; b < matrix[a].length; b++) {
							matrix[a][b] = fReader.nextDouble();
						}
						fReader.nextLine();
					}
					double[] constantVector = new double[n];
					for(int c = 0; c < constantVector.length; c++) {
						constantVector[c] = fReader.nextDouble();
					}
					fReader.close();
					int[] indices = new int[n];
					for(int a = 0;a < n;a++) {
						indices[a]=a;
					}
					double[] sol = new double[n];
					SPPFwdElimination(matrix,constantVector,indices);
					SPPBackSubst(matrix,constantVector,sol,indices);
					File wFile = new File(args[i].substring(0,args[i].length()-4) + ".sol");
					FileWriter fWriter = new FileWriter(wFile);
					for(int w = 0; w < sol.length;w++) {
						fWriter.write(Double.toString(sol[w]) + " ");
					}
					fWriter.close();
				}
				else if(spp && args[i].substring(args[i].length()-4).equals(".lin")) {
					File file = new File(args[i]);
					Scanner fReader = new Scanner(file);
					int n = fReader.nextInt();
					fReader.nextLine();
					float[][] matrix = new float[n][n];
					for(int a = 0; a < matrix.length; a++) {
						for(int b = 0; b < matrix[a].length; b++) {
							matrix[a][b] = fReader.nextFloat();
						}
						fReader.nextLine();
					}
					float[] constantVector = new float[n];
					for(int c = 0; c < constantVector.length; c++) {
						constantVector[c] = fReader.nextFloat();
					}
					fReader.close();
					int[] indices = new int[n];
					for(int a = 0;a < n;a++) {
						indices[a]=a;
					}
					float[] sol = new float[n];
					SPPFwdElimination(matrix,constantVector,indices);
					SPPBackSubst(matrix,constantVector,sol,indices);
					File wFile = new File(args[i].substring(0,args[i].length()-4) + ".sol");
					FileWriter fWriter = new FileWriter(wFile);
					for(int w = 0; w < sol.length;w++) {
						fWriter.write(Float.toString(sol[w]) + " ");
					}
					fWriter.close();
				}
				else if(doublePrecision && args[i].substring(args[i].length()-4).equals(".lin")) {
					File file = new File(args[i]);
					Scanner fReader = new Scanner(file);
					int n = fReader.nextInt();
					fReader.nextLine();
					double[][] matrix = new double[n][n];
					for(int a = 0; a < matrix.length; a++) {
						for(int b = 0; b < matrix[a].length; b++) {
							matrix[a][b] = fReader.nextDouble();
						}
						fReader.nextLine();
					}
					double[] constantVector = new double[n];
					for(int c = 0; c < constantVector.length; c++) {
						constantVector[c] = fReader.nextDouble();
					}
					fReader.close();
					FwdElimination(matrix, constantVector);
					double[] sol = new double[n];
					BackSubst(matrix,constantVector,sol);
					File wFile = new File(args[i].substring(0,args[i].length()-4) + ".sol");
					FileWriter fWriter = new FileWriter(wFile);
					for(int w = 0; w < sol.length;w++) {
						fWriter.write(Double.toString(sol[w]) + " ");
					}
					fWriter.close();
				}
				else if(args[i].substring(args[i].length()-4).equals(".lin")) {
					File file = new File(args[i]);
					Scanner fReader = new Scanner(file);
					int n = fReader.nextInt();
					fReader.nextLine();
					float[][] matrix = new float[n][n];
					for(int a = 0; a < matrix.length; a++) {
						for(int b = 0; b < matrix[a].length; b++) {
							matrix[a][b] = fReader.nextFloat();
						}
						fReader.nextLine();
					}
					float[] constantVector = new float[n];
					for(int c = 0; c < constantVector.length; c++) {
						constantVector[c] = fReader.nextFloat();
					}
					fReader.close();
					FwdElimination(matrix, constantVector);
					float[] sol = new float[n];
					BackSubst(matrix,constantVector,sol);
					File wFile = new File(args[i].substring(0,args[i].length()-4) + ".sol");
					FileWriter fWriter = new FileWriter(wFile);
					for(int w = 0; w < sol.length;w++) {
						fWriter.write(Float.toString(sol[w]) + " ");
					}
					fWriter.close();
				}
			}
		}
		catch(IOException e) {
			System.out.println("File Not Found Error");
		}
	}
	
	public static void FwdElimination(float[][] matrix, float[] constants) {
		float mult;
		int n = constants.length;
		for(int k = 0; k < n-1; k++) {
			for(int i = k+1; i < n;i++) {
				mult = matrix[i][k]/ matrix[k][k];
				for(int j = k; j < n;j++) {
					matrix[i][j]= matrix[i][j]-mult * matrix[k][j];
				}
			constants[i]= constants[i] - mult * constants[k];
			}
		}
	}
	
	public static void BackSubst(float[][] matrix, float[] constants,float[]sol) {
		int n = constants.length;
		sol[n-1] = constants[n-1]/matrix[n-1][n-1];
		float sum;
		for(int i = n-1; i > -1;i--) {
			sum = constants[i];
			for(int j = i + 1; j < n; j++) {
				sum = sum - matrix[i][j] * sol[j];
			}
			sol[i] = sum/matrix[i][i]+ 0;
		}
	}
	
	public static void FwdElimination(double[][] matrix, double[] constants) {
		double mult;
		int n = constants.length;
		for(int k = 0; k < n-1; k++) {
			for(int i = k+1; i < n;i++) {
				mult = matrix[i][k]/ matrix[k][k];
				for(int j = k; j < n;j++) {
					matrix[i][j]= matrix[i][j]-mult * matrix[k][j];
				}
			constants[i]= constants[i] - mult * constants[k];
			}
		}
	}
	
	public static void BackSubst(double[][] matrix, double[] constants,double[]sol) {
		int n = constants.length;
		sol[n-1] = constants[n-1]/matrix[n-1][n-1];
		double sum;
		for(int i = n-1; i > -1;i--) {
			sum = constants[i];
			for(int j = i + 1; j < n; j++) {
				sum = sum - matrix[i][j] * sol[j];
			}
			sol[i] = sum/matrix[i][i]+ 0;
		}
	}
	
	public static void SPPFwdElimination(float[][]matrix, float[] constants, int[] indices) {
		int n = indices.length;
		float[]scaling = new float[n];
		float smax,rmax,r,mult;
		int maxInd;
		for(int i = 0; i < n;i++) {
			smax = 0;
			for(int j =0; j < n; j++) {
				smax = Math.max(smax, Math.abs(matrix[i][j]));
			}
			scaling[i] = smax;
		}
		for(int k = 0; k < n-1;k++) {
			rmax = 0;
			maxInd = k;
			for(int i = k; i<n;i++) {
				r = Math.abs(matrix[indices[i]][k]/scaling[indices[i]]);
				if(r>rmax) {
					rmax = r;
					maxInd = i;
				}
			}
			int temp = indices[k];
			indices[k] = indices[maxInd];
			indices[maxInd] = temp;
			for(int i = k+1;i<n;i++) {
				mult = matrix[indices[i]][k]/matrix[indices[k]][k];
				for(int j = k+1;j<n;j++) {
					matrix[indices[i]][j] = matrix[indices[i]][j] - mult * matrix[indices[k]][j];
				}
				constants[indices[i]] = constants[indices[i]] - mult * constants[indices[k]];
			}
		}
	}
	
	public static void SPPBackSubst(float[][] matrix, float[]constants, float[]sol, int[] indices) {
		float sum;
		int n = constants.length;
		sol[n-1] = constants[indices[n-1]]/matrix[indices[n-1]][n-1];
		for(int i =n-2; i > -1;i--) {
			sum = constants[indices[i]];
			for(int j = i; j < n;j++) {
				sum = sum - matrix[indices[i]][j] * sol[j];
			}
			sol[i] = sum/matrix[indices[i]][i];
		}
	}
	
	public static void SPPFwdElimination(double[][]matrix, double[] constants, int[] indices) {
		int n = indices.length;
		double[]scaling = new double[n];
		double smax,rmax,r,mult;
		int maxInd;
		for(int i = 0; i < n;i++) {
			smax = 0;
			for(int j =0; j < n; j++) {
				smax = Math.max(smax, Math.abs(matrix[i][j]));
			}
			scaling[i] = smax;
		}
		for(int k = 0; k < n-1;k++) {
			rmax = 0;
			maxInd = k;
			for(int i = k; i<n;i++) {
				r = Math.abs(matrix[indices[i]][k]/scaling[indices[i]]);
				if(r>rmax) {
					rmax = r;
					maxInd = i;
				}
			}
			int temp = indices[k];
			indices[k] = indices[maxInd];
			indices[maxInd] = temp;
			for(int i = k+1;i<n;i++) {
				mult = matrix[indices[i]][k]/matrix[indices[k]][k];
				for(int j = k+1;j<n;j++) {
					matrix[indices[i]][j] = matrix[indices[i]][j] - mult * matrix[indices[k]][j];
				}
				constants[indices[i]] = constants[indices[i]] - mult * constants[indices[k]];
			}
		}
	}
	
	public static void SPPBackSubst(double[][] matrix, double[]constants, double[]sol, int[] indices) {
		double sum;
		int n = constants.length;
		sol[n-1] = constants[indices[n-1]]/matrix[indices[n-1]][n-1];
		for(int i =n-2; i > -1;i--) {
			sum = constants[indices[i]];
			for(int j = i; j < n;j++) {
				sum = sum - matrix[indices[i]][j] * sol[j];
			}
			sol[i] = sum/matrix[indices[i]][i];
		}
	}
}