import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;
public class polRoot {
	public static void main(String[]args) {
		try {
			//iterate through command line args
			int maxIter = 10000;
			//checks if maxIter is edited, default is 10,000
			for(int i = 0; i < args.length;i++) {
				if(args[i].equals("-maxIter")) {
					maxIter = Integer.parseInt(args[i+1]);
				}
			}
			//are we using newton's method? The default algorithm is bisection
			if(args[0].equals("-newt")) {
				float x0 = Float.parseFloat(args[args.length -2]);
				if(args[args.length-1].substring(args[args.length-1].length()-4).equals(".pol")) {
					File polFile = new File(args[args.length-1]);
					Scanner fileReader = new Scanner(polFile);
					int n = fileReader.nextInt();
					fileReader.nextLine();
					float[] coeffArray = new float[n+1];
					for(int i = 0; i < coeffArray.length;i++) {
						coeffArray[i]= fileReader.nextFloat();
					}
					fileReader.close();
					File solFile = new File(args[args.length-1].substring(0,args[args.length-1].length()-4) + ".sol");
					Newton(x0,maxIter,(float)Math.pow(2, -23),(float)0.000001, solFile, coeffArray, n);
				}
			}
			//are we using secant method?
			else if(args[0].equals("-sec")) {
				float x0 = Float.parseFloat(args[args.length -3]);
				float x1 = Float.parseFloat(args[args.length -2]);
				if(args[args.length-1].substring(args[args.length-1].length()-4).equals(".pol")) {
					File polFile = new File(args[args.length-1]);
					Scanner fileReader = new Scanner(polFile);
					int n = fileReader.nextInt();
					fileReader.nextLine();
					float[] coeffArray = new float[n+1];
					for(int i = 0; i < coeffArray.length;i++) {
						coeffArray[i]= fileReader.nextFloat();
					}
					fileReader.close();
					File solFile = new File(args[args.length-1].substring(0,args[args.length-1].length()-4) + ".sol");
					Secant(x0,x1,maxIter,(float)Math.pow(2,-23),coeffArray,n,solFile);
				}
			}
			//are we using a hybrid of newton's method and bisection method?
			else if(args[0].equals("-hybrid")) {
				float x0 = Float.parseFloat(args[args.length -3]);
				float x1 = Float.parseFloat(args[args.length -2]);
				if(args[args.length-1].substring(args[args.length-1].length()-4).equals(".pol")) {
					File polFile = new File(args[args.length-1]);
					Scanner fileReader = new Scanner(polFile);
					int n = fileReader.nextInt();
					fileReader.nextLine();
					float[] coeffArray = new float[n+1];
					for(int i = 0; i < coeffArray.length;i++) {
						coeffArray[i]= fileReader.nextFloat();
					}
					fileReader.close();
					File solFile = new File(args[args.length-1].substring(0,args[args.length-1].length()-4) + ".sol");
					Hybrid(x0, x1, maxIter,(float)Math.pow(2, -23), coeffArray,n, solFile,(float)0.000001);
				}
			}
			//default bisection method
			else {
				float x0 = Float.parseFloat(args[args.length -3]);
				float x1 = Float.parseFloat(args[args.length -2]);
				if(args[args.length-1].substring(args[args.length-1].length()-4).equals(".pol")) {
					File polFile = new File(args[args.length-1]);
					Scanner fileReader = new Scanner(polFile);
					int n = fileReader.nextInt();
					fileReader.nextLine();
					float[] coeffArray = new float[n+1];
					for(int i = 0; i < coeffArray.length;i++) {
						coeffArray[i]= fileReader.nextFloat();
					}
					fileReader.close();
					File solFile = new File(args[args.length-1].substring(0,args[args.length-1].length()-4) + ".sol");
					Bisection(x0, x1, maxIter,(float)Math.pow(2, -23), coeffArray,n, solFile);
				}
			}
		}
		catch(IOException e) {
			System.out.println("File not found");
		}
	}
	//first 10 iterations use bisection, rest uses Newton's method
	public static float Hybrid(float x0, float x1, int maxIter, float eps, float[] coeffArray, int n, File solFile,float delta) {
		try {
			FileWriter fWriter = new FileWriter(solFile);
			float fx0 = Function(x0,n, coeffArray);
			float fx1 = Function(x1,n,coeffArray);
			float x2 = 0;
			float fx2;
			if(fx0 * fx1 >= 0) {
				System.out.println("Inadequate values for x0 and x1");
				fWriter.write("-1 0 fail");
				fWriter.close();
				return -1;
			}
			float error = x1-x0;
			int bisectMaxIter = 10;
			int newtonMaxIter = maxIter - 10;
			for(int i=0;i< bisectMaxIter;i++) {
				error = error/2;
				x2 = x0 + error;
				fx2 = Function(x2,n,coeffArray);
				if(Math.abs(error)< eps ||fx2 ==0) {
					System.out.println("Algorithm converged after " + i + " iterations!");
					fWriter.write(Float.toString(x2)+ " " + Integer.toString(i)+ " success");
					fWriter.close();
					return x2;
				}
				if(fx0 * fx2 <0) {
					x1 = x2;
					fx1 = fx2;
				}
				else {
					x0 = x2;
					fx0 = fx2;
				}
			}
			fx2 = Function(x2,n, coeffArray);
			float derFx2;
			for(int i = 0; i < newtonMaxIter;i++) {
				derFx2 = Derivative(x2,n,coeffArray);
				if((float)Math.abs(derFx2)<delta) {
					System.out.println("Small slope!");
					fWriter.write(Float.toString(x2)+ " 0 fail");
					fWriter.close();
					return x2;
				}
				float d = fx2/derFx2;
				x2 = x2 -d;
				fx2 = Function(x2,n,coeffArray);
				if((float)Math.abs(d) < eps) {
					int sum = bisectMaxIter + i;
					System.out.println("Algorithm has converged after " + sum + " iterations!");
					fWriter.write(Float.toString(x2)+ " " + sum + " success");
					fWriter.close();
					return x2;
				}
			}
			System.out.println("Max iteration reached without convergence...");
			fWriter.write(Float.toString(x2)+ " " + maxIter + " fail");
			fWriter.close();
			return x2;
		}
		catch(IOException e) {
			System.out.println("File Error");
			return -1;
		}
	}
	//uses the Secant method, the equation is x2 = x1 - (f(x1))(x1 -x0)/(f(x1)-f(x0)).The formula has a sequence so the next iteration would have x3 = x2 -(f(x2))(x2 -x1)/(f(x2)-f(x1)) and so on.
	public static float Secant(float x0, float x1, int maxIter, float eps, float[] coeffArray, int n, File solFile) {
		try {
			float fx0 = Function(x0,n, coeffArray);
			float fx1 = Function(x1,n, coeffArray);
			float x2;
			FileWriter fWriter = new FileWriter(solFile);
			for(int i = 0; i < maxIter;i++) {
				if(Math.abs(fx0)> Math.abs(fx1)) {
					float temp = x0;
					x0=x1;
					x1 = temp;
					temp = fx0;
					fx0 = fx1;
					fx1 = temp;
				}
				x2 = (x1-x0)/(fx1-fx0);
				x1 = x0;
				fx1 = fx0;
				x2 = x2 * fx0;
				if(Math.abs(x2) < eps) {
					System.out.println("Algorithm has converged after " + i + " iterations!");
					fWriter.write(Float.toString(x0) + " " + Integer.toString(i) + " success");
					fWriter.close();
					return x0;
				}
				x0 = x0 - x2;
				fx0 = Function(x0,n,coeffArray);
			}
			System.out.println("Maximum number of iterations reached!");
			fWriter.write(Float.toString(x0) + " " + Integer.toString(maxIter) + " fail");
			fWriter.close();
			return x0;
		}
		catch(IOException e) {
			System.out.println("File error");
			return -1;
		}
	}
	//uses Newton's method to find zero, the biggest downside is that the derivative is required, and the program can return prematurely if the slope is too small. This takes less iterations and follows the formula x = x0 + f(x0)/f'(x0).
	public static float Newton(float x0, int maxIter, float eps, float delta, File solFile, float[] coeffArray, int n) {
		try {
			FileWriter fWriter = new FileWriter(solFile);
			float fx0 = Function(x0,n, coeffArray);
			float derFx0;
			for(int i = 0; i < maxIter;i++) {
				derFx0 = Derivative(x0,n,coeffArray);
				if((float)Math.abs(derFx0)<delta) {
					System.out.println("Small slope!");
					fWriter.write(Float.toString(x0)+ " 0 fail");
					fWriter.close();
					return x0;
				}
				float d = fx0/derFx0;
				x0 = x0 -d;
				fx0 = Function(x0,n,coeffArray);
				if((float)Math.abs(d) < eps) {
					System.out.println("Algorithm has converged after " + i + " iterations!");
					fWriter.write(Float.toString(x0)+ " " + i + " success");
					fWriter.close();
					return x0;
				}
			}
			System.out.println("Max iteration reached without convergence...");
			fWriter.write(Float.toString(x0)+ " " + maxIter + " fail");
			fWriter.close();
			return x0;
		}
		catch(IOException e) {
			System.out.println("File Error");
			return -1;
		}
	}
	//bisection method finds the midpoint between boundaries x0 and x1 by using (x0+x1)/2 = x2 and replacing a bound depending if f(x2) is negative or positive. X0 is replaced if f(x2) is negative else x1 gets replaced
	public static float Bisection(float x0, float x1, int maxIter, float eps, float[] coeffArray, int n, File solFile) {
		try {
			FileWriter fWriter = new FileWriter(solFile);
			float fx0 = Function(x0,n, coeffArray);
			float fx1 = Function(x1,n,coeffArray);
			float x2 = 0;
			float fx2;
			if(fx0 * fx1 >= 0) {
				System.out.println("Inadequate values for x0 and x1");
				fWriter.write("-1 0 fail");
				fWriter.close();
				return -1;
			}
			float error = x1-x0;
			for(int i=0;i<maxIter;i++) {
				error = error/2;
				x2 = x0 + error;
				fx2 = Function(x2,n,coeffArray);
				if(Math.abs(error)< eps ||fx2 ==0) {
					System.out.println("Algorithm converged after " + i + " iterations!");
					fWriter.write(Float.toString(x2)+ " " + Integer.toString(i)+ " success");
					fWriter.close();
					return x2;
				}
				if(fx0 * fx2 <0) {
					x1 = x2;
					fx1 = fx2;
				}
				else {
					x0 = x2;
					fx0 = fx2;
				}
			}
			System.out.println("Max iterations reached without convergence...");
			fWriter.write(Float.toString(x2)+ " " + Integer.toString(maxIter)+ " fail");
			return x2;
		}
		catch(IOException e) {
			System.out.println("File Error");
			return -1;
		}
	}
	//finds the output of a given polynomial, we are given degree, and the coefficients
	public static float Function(float x, int degree, float[] coeff) {
		float y = 0;
		for(int i=degree; i >-1;i--) {
			y = y + coeff[coeff.length - i-1]*(float)Math.pow(x, i);
		}
		return y;
	}
	//finds derivative of a polynomial function, we find the derivative at a given point
	public static float Derivative(float x, int degree, float[]coeff) {
		float y =0;
		for(int i = degree; i > -1;i--) {
			y = y +coeff[coeff.length-i-1]*i;
			if(i > 0) {//clears out a bug when x = 0, and i is a negative number, which leads to 0 being raised to a negative power, thus we obtain 0^-1 or as the coomputer calls it NaN
				y = y * (float)Math.pow(x, i-1);
			}
		}
		return y;
	}
}
