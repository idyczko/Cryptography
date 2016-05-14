
public class GCDComputer {

	public static long gcd(long a, long b) {
		while (b > 0) {
			long tmp = b;
			b = a % b;
			a=tmp;
		}
		return a;
	}
	
	public static long gcd(long[] numbers){
		long divider=numbers[0];
		for(long number: numbers){
			divider=gcd(divider, number);
		}
		return divider;
	}
	
}
