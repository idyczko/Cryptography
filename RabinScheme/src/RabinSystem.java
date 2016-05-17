import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RabinSystem {

	private static SecureRandom RANDOM = new SecureRandom();

	private PrivateKey privateKey;
	private BigInteger publicKey;

	public RabinSystem(int bits) {
		this.privateKey = this.generatePrivateKey(bits);
		this.publicKey = this.privateKey.getP().multiply(this.privateKey.getQ());
	}

	public BigInteger encrypt(BigInteger message) {
		return message.modPow(BigInteger.valueOf(2), this.publicKey);
	}

	public BigInteger[] decrypt(BigInteger ciphertext) {
		final BigInteger[] m = new BigInteger[4];
		ExecutorService rootExecutor = Executors.newFixedThreadPool(2);
		rootExecutor.execute(new SquareRootThread(0, m, ciphertext, this.privateKey.getP()));
		rootExecutor.execute(new SquareRootThread(2, m, ciphertext, this.privateKey.getQ()));

		rootExecutor.shutdown();
		while (!rootExecutor.isTerminated());

		BigInteger[] ext = GCDComputer.calculateXGCD(this.privateKey.getP(), this.privateKey.getQ());
		BigInteger y_p = ext[1];
		BigInteger y_q = ext[2];

		ExecutorService chineseExecutor = Executors.newFixedThreadPool(4);
		final BigInteger[] d = new BigInteger[4];
		BigInteger[][] parameters = { { m[0], m[2] }, { m[0], m[3] }, { m[1], m[2] }, { m[1], m[3] } };
		for (int i = 0; i < 4; i++) {
			BigInteger[] actualParameters = { parameters[i][0], parameters[i][1], y_p, y_q };
			chineseExecutor
					.execute(new ChineseRemainderThread(i, d, this.publicKey, this.privateKey, actualParameters));
		}
		chineseExecutor.shutdown();
		while (!chineseExecutor.isTerminated());

		return d;
	}


	private PrivateKey generatePrivateKey(int bits) {
		BigInteger p = generatePrime(bits / 2);
		BigInteger q = generatePrime(bits / 2);
		return new PrivateKey(p, q);
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public BigInteger getPublicKey() {
		return publicKey;
	}

	public BigInteger generatePrime(int bits) {
		BigInteger p;
		do {
			p = BigInteger.probablePrime(bits, RANDOM);
		} while (!p.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3)));
		return p;
	}

	public class SquareRootThread implements Runnable {

		private int index;
		private BigInteger[] m;
		private BigInteger ciphertext;
		private BigInteger prime;

		public SquareRootThread(int index, BigInteger[] m, BigInteger ciphertext, BigInteger prime) {
			this.index = index;
			this.m = m;
			this.ciphertext = ciphertext;
			this.prime = prime;
		}

		@Override
		public void run() {
			m[index] = ciphertext.modPow(prime.add(BigInteger.ONE).divide(BigInteger.valueOf(4)), prime);
			m[index + 1] = prime.subtract(m[index]);
		}

	}

	public class ChineseRemainderThread implements Runnable {

		private int index;
		private BigInteger[] d;
		private BigInteger publicKey;
		private PrivateKey privateKey;
		private BigInteger[] parameters;

		public ChineseRemainderThread(int index, BigInteger[] d, BigInteger publicKey, PrivateKey privateKey,
				BigInteger[] parameters) {
			this.index = index;
			this.d = d;
			this.publicKey = publicKey;
			this.privateKey = privateKey;
			this.parameters = parameters;
		}

		@Override
		public void run() {
			d[index] = calculatePosiblePlaintext(parameters[0], parameters[1], parameters[2], parameters[3]);
		}

		private BigInteger calculatePosiblePlaintext(BigInteger m_p1, BigInteger m_q1, BigInteger y_p, BigInteger y_q) {
			return y_p.multiply(this.privateKey.getP()).multiply(m_q1)
					.add(y_q.multiply(this.privateKey.getQ()).multiply(m_p1)).mod(this.publicKey);
		}
	}
}
