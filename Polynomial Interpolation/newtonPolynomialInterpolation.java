import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;
public class newtonPolynomialInterpolation {
	public static void main(String[]args) {
		try {
			File file = new File(args[0]);
			Scanner lengthReader = new Scanner(file);
			int i = 0;
			String next = lengthReader.nextLine();
			String[] array = next.split(" ");
			lengthReader.close();
			//now we know the number of points we have, and thus the number of x's and y's
			Scanner fileReader = new Scanner(file);
			double[] xarray = new double[array.length];
			double[] yarray = new double[array.length];
			for(int j = 0; j < xarray.length;j++) {
				xarray[j] = fileReader.nextDouble();
			}
			for(int k = 0; k <yarray.length;k++) {
				yarray[k] = fileReader.nextDouble();
			}
			fileReader.close();
			String input;
			Scanner scnr = new Scanner(System.in);
			do {
				System.out.println("Enter a real number");
				input = scnr.next();
				if(input.equals("q")) {
					break;
				}
				System.out.println("The solution is " + evalNewton(xarray,newtonPolyInterp(xarray,yarray),input));
			}while(!input.equals("q"));
		}
		catch(IOException e){
			System.out.println("File not found");
		}
	}
	//this method finds the coefficients of the individual terms for our polynomial, it returns these terms in an array
	public static double[] newtonPolyInterp(double[] x, double[] y) {
		long start = System.nanoTime();
		double[] constants = new double[y.length];
		for(int i = 0; i < x.length;i++) {
			constants[i] = y[i];
		}
		for(int j = 1;j<x.length;j++) {
			for(int i = x.length-1;i>= j;i--) {
				constants[i]= (constants[i]-constants[i-1])/(x[i]-x[i-j]);
			}
		}
		System.out.print("Time to interpolate is ");
		System.out.print(System.nanoTime()-start);
		System.out.println(" nanoseconds");
		return constants;
	}
	
	public static double evalNewton(double[]x,double[]c,String input) {
		long start = System.nanoTime();
		double x0 = Double.parseDouble(input);
		double result = c[c.length-1];
		for(int i = x.length-2;i >-1;i--) {
			result = result * (x0-x[i])+c[i];
		}
		System.out.print("Time to evaluate is ");
		System.out.print(System.nanoTime()-start);
		System.out.println(" nanoseconds");
		return result;
	}
}
