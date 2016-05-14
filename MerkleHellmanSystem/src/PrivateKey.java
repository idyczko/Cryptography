
public class PrivateKey {
	private int[] sequence;
	private int multiplier;
	private int modulus;
	
	public PrivateKey(int[] sequence, int multiplier, int modulus) {
		this.sequence = sequence;
		this.multiplier = multiplier;
		this.modulus = modulus;
	}

	public int[] getSequence() {
		return sequence;
	}

	public int getMultiplier() {
		return multiplier;
	}

	public int getModulus() {
		return modulus;
	}
	
	
}
