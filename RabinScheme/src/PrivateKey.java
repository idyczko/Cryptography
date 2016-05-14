import java.math.BigInteger;

public class PrivateKey {
	private BigInteger p;
	private BigInteger q;
	
	public PrivateKey(BigInteger p, BigInteger q) {
		this.p = p;
		this.q = q;
	}

	public BigInteger getP() {
		return p;
	}

	public BigInteger getQ() {
		return q;
	}
}
