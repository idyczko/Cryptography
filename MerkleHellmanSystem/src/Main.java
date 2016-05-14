import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import org.python.antlr.PythonParser.print_stmt_return;
import org.python.util.PythonInterpreter;

public class Main {

	public static void main(String[] args) {
		int test = 100;
		int bits = 8;
		int randomRange = 100;
		PythonInterpreter python = new PythonInterpreter();

		File pythonFile = new File("..//resource//hard-knapsack-problem.py");
		String pythonPath = pythonFile.getAbsolutePath().replaceAll("\\\\", "//").replaceFirst("\\.\\.", "");
		int counter =0;
		for (int i = 0; i < test; i++) {
			MerkleHellmanSystem m = new MerkleHellmanSystem(bits, randomRange);
			int[] plaintext = generateRandomPlaintext(bits);
			int ciphertext = m.encrypt(plaintext);
			String plaintextString = generateStringFromPlaintext(plaintext);

			String s = null;
			System.out.println();
			try {
				String command = "python " + pythonPath + " " + ciphertext;
				for (int j : m.getPublicKey()) {
					command += " " + j;
				}
				Process p = Runtime.getRuntime().exec(command);
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
				BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
				
				while ((s = stdInput.readLine()) != null) {
					System.out.println(plaintextString);
					System.out.println(s);
					if(plaintextString.equals(s)){
						System.out.println("Breaking Merkle-Hellman success.");
						counter++;
					}
					else{
						System.out.println("Breaking Merkle-Hellman fail.");
					}
				}
				while ((s = stdError.readLine()) != null) {
					System.out.println(s);
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
		System.out.println("Broken system with success ratio: "+ counter*100/test+"%");
	}

	private static String generateStringFromPlaintext(int[] plaintext) {
		String plaintextString ="[";
		for(int i: plaintext){
			plaintextString+=i+", ";
		}
		plaintextString=plaintextString.substring(0, plaintextString.lastIndexOf(", "))+"]";
		return plaintextString;
	}

	private static int[] generateRandomPlaintext(int length) {
		Random random = new Random();
		int[] plaintext = new int[length];
		for (int i = 0; i < length; i++) {
			if (random.nextBoolean()) {
				plaintext[i] = 1;
			} else {
				plaintext[i] = 0;
			}
		}
		return plaintext;
	}

}
