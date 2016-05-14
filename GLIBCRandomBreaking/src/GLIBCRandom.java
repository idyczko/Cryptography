
public class GLIBCRandom {
	private static long MODULO = 4294967296L;
	private static long MODULO_2 = 2147483647L;
	
	long[] r=new long[32];

	public GLIBCRandom(long seed) {
		long[] r = new long[344];
		int i;
		
		r[0] = seed;
		for (i=1; i<31; i++) {
			r[i] = (16807L * (int) r[i-1]) % MODULO_2;
		if (r[i] < 0) {
			r[i] += 2147483647;
		}
		}	
		for (i=31; i<34; i++) {
			r[i] = r[i-31];
		}
		for (i=34; i<344; i++) {
			r[i] = (r[i-31] + r[i-3])%MODULO;
		}
		
		for(i = 0;i<32;i++){
			this.r[i]=r[344-32+i];
		}

	}
	
	public long rand(){
		 long temp = (r[r.length-31]+r[r.length-3])%MODULO;
		 for(int i=0;i<r.length-1;i++){
			 r[i]=r[i+1];
		 }
		 r[r.length-1]=temp;
		 return temp>>1;
	}
}
