
public class GLIBCBreaker {
	private static long MODULO = 4294967296L;

	public static long breakGLIBC(long[] randomNumbers) {
		long nextNumber;
		if(randomNumbers.length<32)
			return 0;
		long r_31 = randomNumbers[randomNumbers.length-31]<<1;
		long r_3 = randomNumbers[randomNumbers.length-3]<<1;
		nextNumber=(r_3+ r_31+1)%MODULO;
		return nextNumber>>1;
	}
}
