import java.util.ArrayList;
import java.util.HashMap;

public class LCGBreaker {

	public static long breakLCG(long[] rn) {

		long[] determinants=new long[rn.length-3];
		for (int i = 3; i < rn.length - 2; i++) {
			determinants[i-3]=calculateDeterminant(i, i + 1, rn);
		}
		long m = GCDComputer.gcd(determinants);

		ArrayList<Long> asList = new ArrayList<Long>();
		long a = 0;
		for(int i =0; i<rn.length-2;i++){
			a = CustomMathLib.modulo((long)Math.ceil((double) CustomMathLib.modulo(rn[i+2]-rn[i+1] ,m)/(CustomMathLib.modulo(rn[i+1]-rn[i],m))),m);			
			if(!asList.contains(a)){
				asList.add(a);
			}
		}
		HashMap<Long,Integer> asMap = new HashMap<Long, Integer>();
		long b;
		for(long a_temp : asList){
			asMap.put(a_temp, 0);
			b=CustomMathLib.modulo(rn[1]-(CustomMathLib.modulo(a_temp*rn[0],m)),m);
			for(int i = 0; i<rn.length -1;i++){
				if(rn[i+1]==calculateOutput(rn[i], a_temp, b, m)){
					asMap.put(a_temp, asMap.get(a_temp)+1);
				}
			}
			if((asMap.get(a)!=null?asMap.get(a):0)<=asMap.get(a_temp)){
				a=a_temp;
			}
		}
		
		b=CustomMathLib.modulo(rn[1]-(CustomMathLib.modulo(a*rn[0],m)),m);
		//System.out.println("a: "+a +" b: "+b+" m: "+m);
		return CustomMathLib.modulo(a*rn[rn.length-1]+b,m);
	}

	private static long calculateDeterminant(int i, int j, long[] randomNumbers) {
		long x_11 = randomNumbers[i] - randomNumbers[0];
		long x_12 = randomNumbers[i + 1] - randomNumbers[1];
		long x_21 = randomNumbers[j] - randomNumbers[0];
		long x_22 = randomNumbers[j + 1] - randomNumbers[1];

		return Math.abs(x_11 * x_22 - x_12 * x_21);

	}
	
	private static long calculateOutput(long x_last, long a, long b, long m){
		return CustomMathLib.modulo(a*x_last+b,m);
	}
}
