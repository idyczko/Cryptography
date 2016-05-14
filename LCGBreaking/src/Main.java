
public class Main {

	public static void main(String[] args) {
		int trials = 10000;
		long[] test= new long[1000];
		LCGGenerator gen = new LCGGenerator(13231, 52, 157, 1501);
		
		for(int i = 0;i<test.length;i++){
			test[i]=gen.rand();
		}
		int counter = 0;
		for(int i=0; i<trials; i++){
			long expected = LCGBreaker.breakLCG(test);
			long rand = gen.rand();
			for(int j=0;j<test.length-1;j++){
				test[j]=test[j+1];
			}
			test[test.length-1]=rand;
			if(rand==expected){
				counter++;
			}
			System.out.println("Expected: "+expected+" Was: "+rand + ((rand==expected)?" Succeed!":" Failed!"));
		}
		System.out.println(counter + " of " +trials+" trials were successful. Around "+ counter*100/trials +"% efficiency.");

	}

}
