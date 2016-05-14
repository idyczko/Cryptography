
public class CharacterBitsLibrary {

	public static int[] binaryRepresentation(char c){
		int[] characterBits = new int[8];
		for (int i = 0; i < 8; ++i)
		{
			characterBits[i] = ((c >> i) & 1);
		}
		return characterBits;
	}
}
