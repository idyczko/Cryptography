
public class CustomMathLib {

	public static int modulo(int a, int b) {
		return a > 0 ? a % b : (b - Math.abs(a) % b) % b;
	}

	public static int xor(int a, int b) {
		if (a == b) {
			return 0;
		}
		return 1;
	}

	public static int xor(int tab[]) {
		int xorResult = tab[0];
		for (int i = 1; i < tab.length; i++) {
			xorResult = CustomMathLib.xor(xorResult, tab[i]);
		}
		return xorResult;
	}

	public static int negxor(int tab[]) {
		return CustomMathLib.xor(tab)==1?0:1;
	}

	public static int negxor(int a, int b) {
		return CustomMathLib.xor(a,b)==1?0:1;
	}
}
