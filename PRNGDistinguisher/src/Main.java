import java.util.Random;

public class Main {

	public static void main(String[] args) {
		Random random = new Random();
		int trials = 100000;
		long[] test = new long[50];
		int counter = 0;
		for (int j = 0; j < trials; j++) {
			Boolean usePRNG = random.nextBoolean();
			if (usePRNG == false) {
				for (int i = 0; i < test.length; i++) {
					test[i] = random.nextLong();
				}
			} else {
				GLIBCRandom gen = new GLIBCRandom(random.nextLong());
				for (int i = 0; i < test.length; i++) {
					test[i] = gen.rand();
				}
			}
			if (usePRNG == GLIBCDistinguisher.isSequencePseudorandom(test)) {
				//System.out.println("The distinguisher wins");
				counter++;
			}
			else{
				System.out.println("The distinguisher loses");

			}
		}
		System.out.println("Distinguisher prediction correctness ratio: " + 100*(counter/trials)+"%");

	}
}
