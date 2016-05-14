import java.math.BigInteger;
import java.security.SecureRandom;

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
	
	public BigInteger[] decrypt(BigInteger c) {
        BigInteger N = this.privateKey.getP().multiply(this.privateKey.getQ());
        BigInteger m_p1 = c.modPow(this.privateKey.getP().add(BigInteger.ONE).divide(BigInteger.valueOf(4)), this.privateKey.getP());
        BigInteger m_p2 = this.privateKey.getP().subtract(m_p1);
        BigInteger m_q1 = c.modPow(this.privateKey.getQ().add(BigInteger.ONE).divide(BigInteger.valueOf(4)), this.privateKey.getQ());
        BigInteger m_q2 = this.privateKey.getQ().subtract(m_q1);

        BigInteger[] ext = GCDComputer.calculateXGCD(this.privateKey.getP(),this.privateKey.getQ());
        BigInteger y_p = ext[1];
        BigInteger y_q = ext[2];

        BigInteger d1 = y_p.multiply(this.privateKey.getP()).multiply(m_q1).add(y_q.multiply(this.privateKey.getQ()).multiply(m_p1)).mod(N);
        BigInteger d2 = y_p.multiply(this.privateKey.getP()).multiply(m_q2).add(y_q.multiply(this.privateKey.getQ()).multiply(m_p1)).mod(N);
        BigInteger d3 = y_p.multiply(this.privateKey.getP()).multiply(m_q1).add(y_q.multiply(this.privateKey.getQ()).multiply(m_p2)).mod(N);
        BigInteger d4 = y_p.multiply(this.privateKey.getP()).multiply(m_q2).add(y_q.multiply(this.privateKey.getQ()).multiply(m_p2)).mod(N);

        return new BigInteger[]{d1,d2,d3,d4};
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
}
