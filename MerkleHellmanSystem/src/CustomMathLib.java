
public class CustomMathLib {

	public static long modulo(long a, long b){
		return a>0?a%b:(b-Math.abs(a)%b)%b;
	}
}
