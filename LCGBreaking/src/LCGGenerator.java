
public class LCGGenerator {
	private long a;
	private long b;
	private long m;
	private long x;
	
	public LCGGenerator(long seed, long a, long b, long m){
		this.a=a;
		this.b=b;
		this.m=m;
		this.x=seed;
	}
	
	public long rand(){
		this.x=CustomMathLib.modulo(a*x+b,m);
		return this.x;
	}
	
	
}
