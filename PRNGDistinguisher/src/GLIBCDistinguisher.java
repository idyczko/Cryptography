
public class GLIBCDistinguisher {
	private static long MODULO = 4294967296L;

	public static Boolean isSequencePseudorandom(long[] randomNumbers) {
		long nextNumber;
		if (randomNumbers.length < 32)
			return false;
		long r_31 = randomNumbers[randomNumbers.length - 32] << 1;
		long r_3 = randomNumbers[randomNumbers.length - 4] << 1;
		for (int i = 0; i <= 2; i++) {
			nextNumber = (r_3 + r_31 + i) % MODULO;
			nextNumber = nextNumber >> 1;
			if (nextNumber == randomNumbers[randomNumbers.length-1])
				return true;
		}
		return false;
	}
}
