
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
	
	public static int[] xgcd(int a, int b)
    { 
        int[] ans = new int[3];
        int q;

        if (b == 0)  {  
            ans[0] = a;
            ans[1] = 1;
            ans[2] = 0;
        }
        else
            {
               q = a/b;
               ans = xgcd (b, (int) CustomMathLib.modulo(a, b));
               int temp = ans[1] - ans[2]*q;
               ans[1] = ans[2];
               ans[2] = temp;
            }

        return ans;
    }
	
}
