
public class test {
	public static void main(String[] args) {
		System.out.println(toBinary(15));
	}
	static String toBinary(double n) {
		String s = "";
		int r;
		int intPart = (int)Math.floor(n);
		double decPart = n - intPart;
		while(intPart>=2) {
			r = intPart % 2;
			intPart = intPart / 2;
			Integer i = r;
			s = i.toString()+ s;
		}
		Integer i = intPart;
		s = i.toString() + s;
		if(decPart != 0) {
			s = s + ".";
			int counter = 0;
			while(decPart != 1&& counter <= 20) {
				if(decPart * 2 < 1) {
					s = s + "0";
					decPart = decPart * 2;
				}
				else {
					s = s + "1";
					decPart = decPart * 2;
					if(decPart > 1) {
						decPart--;
					}
				}
				counter++;
			}
		}
		return s;
	}
	static String toDifferentNumberSystem(int n, int base) {
		if(base > 16) {
			return "base is too big";
		}
		String s = "";
		int r;
		while(n>=base) {
			r = n % base;
			n = n / base;
			if(r == 10) {
				s = "A" + s;
			}
			else if(r==11) {
				s = "B" + s;
			}
			else if(r==12) {
				s = "C" + s;
			}
			else if(r==13) {
				s = "D" + s;
			}
			else if(r==14) {
				s = "E" + s;
			}
			else if(r==15) {
				s = "F" + s;
			}
			else {
				Integer i = r;
				s = i.toString()+ s;
			}
		}
		Integer i = n;
		s = i.toString() + s;
		return s;
	}
	
	static String toDifferentNumberSystemRecursive(int n, int base) {
		if(base > 16) {
			return "base is too big";
		}
		if(n >= base) {
			Integer i;
			i = n % base;
			if(i == 10) {
				return toDifferentNumberSystemRecursive(n/base,base) + "A";
			}
			else if(i==11) {
				return toDifferentNumberSystemRecursive(n/base,base) + "B";
			}
			else if(i==12) {
				return toDifferentNumberSystemRecursive(n/base,base) + "C";
			}
			else if(i==13) {
				return toDifferentNumberSystemRecursive(n/base,base)+"D";
			}
			else if(i==14) {
				return toDifferentNumberSystemRecursive(n/base,base)+"E";
			}
			else if(i==15) {
				return toDifferentNumberSystemRecursive(n/base,base)+"F";
			}
			else {
				return toDifferentNumberSystemRecursive(n/base, base) + i.toString();
			}
		}
		else {
			Integer i = n;
			return i.toString();
		}
	}
}
