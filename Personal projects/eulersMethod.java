import java.io.File;
import java.util.Scanner;
import java.io.IOException;
public class eulersMethod {
		public static void main(String[]args) {
			try {
			Scanner scnr = new Scanner(System.in);
			double step = 0.0000001;
			double x = scnr.nextDouble();
			double y = scnr.nextDouble();
			double endX = scnr.nextDouble();
			File file = new File("function.txt");
			Scanner fReader = new Scanner(file);
			int terms = fReader.nextInt();
			double[] coeff = new double[terms];
			int[] xPowers = new int[terms];
			int[] yPowers = new int[terms];
			for(int i = 0; i < coeff.length;i++) {
				coeff[i] = fReader.nextDouble();
			}
			for(int i = 0; i < coeff.length;i++) {
				xPowers[i] = fReader.nextInt();
			}
			for(int i = 0; i < coeff.length;i++) {
				yPowers[i] = fReader.nextInt();
			}
			eulersMethod(endX,x,y, step, coeff,xPowers,yPowers, terms);
		}
		catch(IOException e) {
			System.out.println("file not found");
		}	
	}
	public static void eulersMethod(double endX, double x, double y, double step, double[] coeff, int[]xPowers, int[]yPowers,int terms) {
		while(x<endX) {
			y = y+Function(x,y,terms,coeff,xPowers,yPowers)*step;
			x = x + step;
		}
		System.out.println(y);
	}
	public static double Function(double x,double y, int degree, double[] coeff, int[] xPowers, int[] yPowers) {
		double sum = 0;
		for(int j = 0; j < coeff.length;j++) {
			sum = sum + (coeff[j]*Math.pow(x, xPowers[j])*Math.pow(y, yPowers[j]));
		}
		return sum;
	}
}
