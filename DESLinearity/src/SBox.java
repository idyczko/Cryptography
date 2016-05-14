public class SBox {
	private int[][] lookupTable;

	public SBox(int[][] lookupTable) {
		this.lookupTable = lookupTable;
	}

	public Integer calculateOutput(Integer input) {
		input+=64;
		String binaryInput=  Integer.toBinaryString(input);
		int controlBits = Integer.parseInt(binaryInput.substring(binaryInput.length()-6, binaryInput.length()-5)+binaryInput.substring(binaryInput.length()-1), 2);
		int middleBits = Integer.parseInt(binaryInput.substring(binaryInput.length()-5, binaryInput.length()-1),2);
		return this.lookupTable[controlBits][middleBits];
	}
}
