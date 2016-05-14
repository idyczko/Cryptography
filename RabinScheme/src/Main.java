import java.math.BigInteger;

public class Main {

	public static void main(String[] args) {
		RabinSystem rabinSystem = new RabinSystem(64);
		BigInteger message = BigInteger.valueOf(15);
		BigInteger ciphertext = rabinSystem.encrypt(message);
		for(BigInteger i : rabinSystem.decrypt(ciphertext))
			System.out.println(i);
	}

}
