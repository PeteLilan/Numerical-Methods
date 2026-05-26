import java.io.File;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
public class monteCarlo {
	public static void main(String[]args) {
		//simpleMonteCarlo();
		//advancedMonteCarlo();
	}
	public static void simpleMonteCarlo() {
		try {
			File file = new File("functionMC.txt");
			Scanner fReader = new Scanner(file);
			Scanner scnr = new Scanner(System.in);
			double[] coeff = new double[fReader.nextInt()];
			for(int i = 0;i<coeff.length;i++) {
				coeff[i] = fReader.nextDouble();
			}
			double[] x = new double[10];
			double[] y = new double[10];
			double[] z = new double[10];
			Random rand = new Random();
			for(int i = 0;i<x.length;i++) {
				x[i] = rand.nextDouble();
				y[i] = rand.nextDouble();
				z[i] = rand.nextDouble();
			}
			double lowerXBound,upperXBound,lowerYBound,upperYBound, lowerZBound, upperZBound;
			lowerXBound = scnr.nextDouble();
			upperXBound = scnr.nextDouble();
			lowerYBound = scnr.nextDouble();
			upperYBound = scnr.nextDouble();
			lowerZBound = scnr.nextDouble();
			upperZBound = scnr.nextDouble();
			double volume = (upperXBound-lowerXBound)*(upperYBound-lowerYBound)*(upperZBound-lowerZBound);
			for(int i = 0; i < x.length;i++) {
				x[i]= (x[i] * (upperXBound-lowerXBound))+lowerXBound;
				y[i] = (y[i] * (upperYBound-lowerYBound))+lowerYBound;
				z[i] = (z[i] * (upperZBound- lowerZBound))+lowerZBound;
			}
			int[] xPowers = new int[coeff.length];
			int[] yPowers = new int[coeff.length];
			int[] zPowers = new int[coeff.length];
			for(int i = 0; i < xPowers.length;i++) {
				xPowers[i] = fReader.nextInt();
			}
			for(int i = 0; i < yPowers.length;i++) {
				yPowers[i] = fReader.nextInt();
			}
			for(int i = 0; i < zPowers.length;i++) {
				zPowers[i] = fReader.nextInt();
			}
			double sum = 0;
			for(int i = 0; i < x.length;i++) {
				sum = sum + Function(x[i],y[i],coeff.length,coeff, xPowers, yPowers, z[i], zPowers);
			}
			sum = sum * volume;
			sum = sum/x.length;
			System.out.println(sum);
		}
		catch(IOException e) {
			System.out.println("file not found");
		}
	}
	public static void advancedMonteCarlo() {
		try {
			File file = new File("functionMC.txt");
			Scanner fReader = new Scanner(file);
			Scanner scnr = new Scanner(System.in);
			double[] coeff = new double[fReader.nextInt()];
			for(int i = 0;i<coeff.length;i++) {
				coeff[i] = fReader.nextDouble();
			}
			double[] x = new double[90000];
			double[] y = new double[90000];
			double[] z = new double[90000];
			Random rand = new Random();
			for(int i = 0;i<x.length;i++) {
				x[i] = rand.nextDouble();
				y[i] = rand.nextDouble();
				z[i] = rand.nextDouble();
			}
			double lowerXBound,upperXBound,lowerYBound,upperYBound, lowerZBound, upperZBound;
			lowerXBound = scnr.nextDouble();
			upperXBound = scnr.nextDouble();
			lowerYBound = scnr.nextDouble();
			upperYBound = scnr.nextDouble();
			lowerZBound = scnr.nextDouble();
			upperZBound = scnr.nextDouble();
			double volume = (upperXBound-lowerXBound)*(upperYBound-lowerYBound)*(upperZBound-lowerZBound);
			for(int i = 0; i < x.length;i++) {
				x[i]= (x[i] * (upperXBound-lowerXBound))+lowerXBound;
				y[i] = (y[i] * (upperYBound-lowerYBound))+lowerYBound;
				z[i] = (z[i] * (upperZBound- lowerZBound))+lowerZBound;
			}
			int[] xPowers = new int[coeff.length];
			int[] yPowers = new int[coeff.length];
			int[] zPowers = new int[coeff.length];
			for(int i = 0; i < xPowers.length;i++) {
				xPowers[i] = fReader.nextInt();
			}
			for(int i = 0; i < yPowers.length;i++) {
				yPowers[i] = fReader.nextInt();
			}
			for(int i = 0; i < zPowers.length;i++) {
				zPowers[i] = fReader.nextInt();
			}
			double in = 0;
			double out = 0;
			for(int i = 0; i < x.length;i++) {
				double f = Function(x[i],y[i],coeff.length,coeff, xPowers, yPowers, z[i], zPowers);
				if(f<=(upperXBound-lowerXBound)&&f<=(upperYBound-lowerYBound)&&f<= (upperZBound - lowerZBound)) {
					in++;
				}
				else {
					out++;
				}
			}
			volume = volume * (in/(in+out));
			System.out.println(volume);
		}
		catch(IOException e) {
			System.out.println("file not found");
		}
	}
	public static double Function(double x,double y, int degree, double[] coeff, int[] xPowers, int[] yPowers,double z, int[] zPowers) {
		double sum = 0;
		for(int j = 0; j < coeff.length;j++) {
			sum = sum + coeff[j]*Math.pow(x, xPowers[j])*Math.pow(y, yPowers[j]*Math.pow(z, zPowers[j]));
		}
		return sum;
	}
}
