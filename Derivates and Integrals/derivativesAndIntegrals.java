import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;
public class derivativesAndIntegrals {
	public static void main(String[]args) {
		try {
			String input,operation;
			double[][] raggedArray = new double[6][6];
			File file = new File(args[0]);
			Scanner fScnr = new Scanner(file);
			Scanner scnr = new Scanner(System.in);
			int n = fScnr.nextInt();
			fScnr.nextLine();
			double[] cArray = new double[n+1];
			for(int i = 0; i < cArray.length;i++) {
				cArray[i]= fScnr.nextDouble();
			}
			do {
				System.out.println("Press d to take a derivative, i to integrate, and q to quit");
				input = scnr.next();
				if(input.equals("d")) {
					System.out.println("Give an x-value point to compute the derivative");
					double point = scnr.nextDouble();
					richardsonExtrapolation(cArray, point, 5, 5, n, raggedArray);
					for(int i = 0;i < 6; i++) {
						for(int j = 0; j <= i;j++) {
							System.out.print("D(" + i + "," + j + ") = "+ raggedArray[i][j] + " ");
						}
						System.out.println("");
					}
				}
				else if(input.equals("i")) {
					System.out.println("Enter an x-value to represent the lower bound");
					double a = scnr.nextDouble();
					System.out.println("Enter another x-value to represent the upper bound");
					double b = scnr.nextDouble();
					rombergMethod(cArray, a, b, 5, 5, n, raggedArray);
					for(int i = 0;i < raggedArray.length; i++) {
						for(int j = 0; j <= i;j++) {
							System.out.print("R(" + i + "," + j + ") = "+ raggedArray[i][j] + " ");
						}
						System.out.println("");
					}
				}
				else if(input.equals("q")) {
					System.out.println("quitting");
				}
				else {
					System.out.println("Enter a valid lowercase input");
				}
			}while(!input.equals("q"));
		}
		catch(IOException e) {
			System.out.println("file not found");
		}
	}
	
	public static double richardsonExtrapolation(double[] cArray, double x, int i, int j, int n, double[][]matrix) {
		double h = 0.0001/Math.pow(2, i);
		if(j == 0) {
			matrixBuilder(i,j,matrix,(Function(x+h, n, cArray)-Function(x-h,n,cArray))/(2*h));
			return (Function(x+h, n, cArray)-Function(x-h,n,cArray))/(2*h);
		}
		else {
			matrixBuilder(i,j,matrix,richardsonExtrapolation(cArray,x,i,j-1,n,matrix)+ ((richardsonExtrapolation(cArray,x,i,j-1,n,matrix)-richardsonExtrapolation(cArray,x,i-1,j-1,n,matrix))/(Math.pow(4,j)-1)));
			return richardsonExtrapolation(cArray,x,i,j-1,n,matrix)+ ((richardsonExtrapolation(cArray,x,i,j-1,n,matrix)-richardsonExtrapolation(cArray,x,i-1,j-1,n,matrix))/(Math.pow(4,j)-1));
		}
	}
	
	public static double rombergMethod(double[] cArray, double a, double b, int i, int j, int n, double[][] matrix) {
		double h = (b-a)/Math.pow(2, i);
		if(i==0 && j == 0) {
			matrixBuilder(i, j, matrix, h*(Function(a,n,cArray)+Function(b,n,cArray))/2);
			return h*(Function(a,n,cArray)+Function(b,n,cArray))/2;
		}
		else if(j == 0) {
			double sum = 0;
			for(int k = 1; k <= Math.pow(2, i-1); k++) {
				sum = sum + h*Function(a + h*(2*k-1),n,cArray);
			}
			matrixBuilder(i,j, matrix, rombergMethod(cArray, a, b, i-1, j,n, matrix)/2 + sum);
			return rombergMethod(cArray, a, b, i-1, j,n, matrix)/2 + sum;
		}
		else {
			matrixBuilder(i,j,matrix,rombergMethod(cArray,a,b,i,j-1,n,matrix)+ (rombergMethod(cArray,a,b,i,j-1,n,matrix)-rombergMethod(cArray,a,b,i-1,j-1,n,matrix))/(Math.pow(4, j)-1));
			return rombergMethod(cArray,a,b,i,j-1,n,matrix)+ (rombergMethod(cArray,a,b,i,j-1,n,matrix)-rombergMethod(cArray,a,b,i-1,j-1,n,matrix))/(Math.pow(4, j)-1);
		}
	}
	
	public static void matrixBuilder(int i, int j, double[][] matrix, double value) {
		matrix[i][j] = value;
	}
	
	public static double Function(double x, int degree, double[] coeff) {
		double y = 0;
		for(int i=degree; i >-1;i--) {
			y = y + coeff[coeff.length - i-1]*Math.pow(x, i);
		}
		return y;
	}
}
