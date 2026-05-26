import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;
public class practice {
	public static void main(String[]args) {
		try {
			File file = new File("powers.txt");
			Scanner fReader = new Scanner(file);
			double[] basis = new double[fReader.nextInt()];
			for(int i = 0; i < basis.length;i++) {
				basis[i] = fReader.nextDouble();
			}
			double[][] matrix = new double[basis.length][basis.length];
			for(int i = 0; i <matrix.length;i++) {
				for(int j = 0;j<matrix[i].length ;j++) {
					matrix[i][j] = basis[i]+basis[j];
				}
			}
			fReader.nextLine();
			String[]array = fReader.nextLine().split(" ");
			double[] xPoints = new double[array.length];
			double[] yPoints = new double[array.length];
			for(int i = 0; i <array.length;i++) {
				xPoints[i] = Double.parseDouble(array[i]);
			}
			array = fReader.nextLine().split(" ");
			for(int i = 0; i <array.length;i++) {
				yPoints[i] = Double.parseDouble(array[i]);
			}
			leastSquaresApprox(matrix,xPoints,yPoints,basis);
		}
		catch(IOException e) {
			System.out.println("file not found");
		}
	}
	public static void leastSquaresApprox(double[][] matrix,double[] xPoints,double[]yPoints,double[]basis) {
		int sum;
		for(int i = 0; i < matrix.length;i++) {
			for(int j = 0; j < matrix[i].length;j++) {
				sum = 0;
				for(int k = 0; k <xPoints.length;k++) {
					sum = sum + (int)Math.pow(xPoints[k], matrix[i][j]);
				}
				matrix[i][j] = sum;
			}
		}
		double[] solVector = new double[basis.length];
		double sol;
		for(int i = 0; i < basis.length;i++) {
			sol = 0;
			for(int j = 0; j < yPoints.length; j++) {
				sol = sol + (yPoints[j]*Math.pow(xPoints[j], basis[i]));
			}
			solVector[i] = sol;
		}
		System.out.println(solVector.length);
		for(int i = 0; i < matrix.length;i++) {
			for(int j = 0; j < matrix[i].length;j++) {
				System.out.printf("%-10.3f",matrix[i][j]);
			}
			System.out.println("");
		}
		for (int i = 0; i < solVector.length;i++) {
			System.out.printf("%-10.3f", solVector[i]);
		}
	}
}