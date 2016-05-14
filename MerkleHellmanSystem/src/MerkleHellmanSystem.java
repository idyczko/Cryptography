import java.util.Random;

public class MerkleHellmanSystem {

	private PrivateKey privateKey;
	private int[] publicKey;
	private int messageLength;

	public MerkleHellmanSystem(PrivateKey privateKey, int[] publicKey) {
		this.privateKey = privateKey;
		this.messageLength = privateKey.getSequence().length;
		this.publicKey = publicKey;
	}

	public MerkleHellmanSystem(PrivateKey privateKey) {
		this.privateKey = privateKey;
		this.messageLength = privateKey.getSequence().length;
		computePublicKey();
	}

	public MerkleHellmanSystem(int bits, int maxRandom) {
		Random random = new Random();
		this.messageLength = bits;
		int[] superIncreasingSequence = MerkleHellmanSystem.generateSuperIncreasingSequence(bits, maxRandom);
		int sum = 0;
		for (int i : superIncreasingSequence) {
			sum += i;
		}
		int modulus = sum + random.nextInt(maxRandom);
		int multiplier = random.nextInt(modulus);
		while (GCDComputer.gcd(modulus, multiplier) != 1) {
			multiplier = random.nextInt(modulus);
		}
		this.privateKey = new PrivateKey(superIncreasingSequence, multiplier, modulus);
		computePublicKey();
	}

	private void computePublicKey() {
		int[] publicKey = new int[this.privateKey.getSequence().length];
		for (int i = 0; i < this.privateKey.getSequence().length; i++) {
			publicKey[i] = (this.privateKey.getMultiplier() * this.privateKey.getSequence()[i])
					% this.privateKey.getModulus();
		}
		this.publicKey = publicKey;
	}

	public static int[] generateSuperIncreasingSequence(int length, int maxRandom) {
		Random random = new Random();
		int[] sequence = new int[length];
		sequence[0] = random.nextInt(maxRandom);
		for (int i = 1; i < length; i++) {
			int sum = 0;
			for (int j = 0; j <= i; j++) {
				sum += sequence[j];
			}
			sequence[i] = sum + 1 + random.nextInt(maxRandom);
		}
		return sequence;
	}

	public int encrypt(int[] plaintext) {
		int ciphertext = 0;
		for (int i = 0; i < 8; i++) {
			ciphertext += publicKey[i] * plaintext[i];
		}
		return ciphertext;
	}

	public int[] decrypt(int ciphertext) {
		int inverse = (int) CustomMathLib.modulo(
				GCDComputer.xgcd(privateKey.getMultiplier(), privateKey.getModulus())[1], privateKey.getModulus());
		int sum = (int) CustomMathLib.modulo(ciphertext * inverse, privateKey.getModulus());
		int[] plaintext = new int[messageLength];
		for (int i = messageLength - 1; i >= 0; i--) {
			if (sum >= privateKey.getSequence()[i]) {
				sum -= privateKey.getSequence()[i];
				plaintext[i] = 1;
			} else {
				plaintext[i] = 0;
			}
		}
		return plaintext;
	}

	public int[] getPublicKey() {
		return publicKey;
	}
}
