public class Cipher {
	private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789,.() '\"![]/%-_;?-=:"
			+ '\n' + '\r';

	// Set this variable to the default alphabet you wish to use
	private static final String DEFAULT_ALPHABET = ALPHABET;

	private static final double[] englishFrequencyPercentages = new double[] { 8.17, 1.49, 2.78, 4.25, 12.70, 2.23,
			2.01, 6.09, 6.97, 0.15, 0.77, 4.03, 2.40, 6.75, 7.50, 1.93, 0.09, 5.99, 6.33, 9.1, 2.8, 0.98, 2.36, 0.15,
			1.98, 0.07 };

	private static double[] scores = new double[] { englishFrequencyPercentages.length };

	private static double threshold = 4.00;

	/**
	 * Returns plaintext encrypted by the rotation cipher with a shift of
	 * movement.
	 * 
	 * @param alphabet
	 *            the alphabet to be used for the encryption
	 * @param plainText
	 *            the plain text to be encrypted.
	 * @param shiftAmount
	 *            the number of characters in ALPHABET to shift by.
	 * @return returns the encrypted plainText.
	 */
	public static String rotationCipherEncrypt(String plain, int shift, String alphabet) {
		String encrypted = "";

		for (int i = 0; i < plain.length(); i++) {
			String plainLetter = plain.substring(i, i + 1);

			encrypted += alphabet.substring(ShiftIndex(alphabet.indexOf(plainLetter, 0), shift, alphabet.length()),

					ShiftIndex(alphabet.indexOf(plainLetter, 0), shift, alphabet.length()) + 1);
		}
		return encrypted;
	}

	/****
	 * Will return the correct shift amount to shift
	 * 
	 * @param index
	 * @param shiftAmount
	 * @param alphabetLength
	 * @return correct shit
	 */
	public static int ShiftIndex(int index, int shiftAmount, int alphabetLength) {
		while (shiftAmount < 0)
			shiftAmount += alphabetLength;

		index += shiftAmount;

		index %= alphabetLength;

		return index;
	}

	/**
	 * Returns a the result of decrypting cipherText by shiftAmount using the
	 * rotation cipher.
	 * 
	 * @param alphabet
	 *            the alphabet to be used for the encryption
	 * @param cipher
	 *            the encrypted text.
	 * @param shift
	 *            the key to decrypt the cipher.
	 * @return returns the decrypted cipherText.
	 */
	public static String rotationCipherDecrypt(String cipher, int shift, String alphabet) {
		String decrypted = "";

		for (int i = 0; i < cipher.length(); i++) {
			String decryptedLetter = cipher.substring(i, i + 1);
			decrypted += alphabet.substring(ShiftIndex(alphabet.indexOf(decryptedLetter, 0), -shift, alphabet.length()),

					ShiftIndex(alphabet.indexOf(decryptedLetter, 0), -shift, alphabet.length()) + 1);
		}
		return decrypted;
	}

	public static String substitutionCipher(String plain, int[] permutation, String alphabet) {
		String subsituted = "";
		for (int i = 0; i < plain.length(); i++) {
			subsituted += alphabet.substring(alphabet.indexOf(permutation[i], 0),
					alphabet.indexOf(permutation[i], 0) + 1);
		}
		return subsituted;
	}

	/****
	 * Returns whether a permutation is truly random thus valid
	 * 
	 * @param permutation
	 * @return boolean that indicates if valid or not
	 */
	public boolean isValidPermutation(int[] permutation) {
		for (int i = 0; i < permutation.length; i++) {
			for (int j = i + 1; j < permutation.length; j++) {
				if (permutation[i] == permutation[j]) {
					return false;
				}
			}
		}

		return true;
	}

	/****
	 * Returns an array of unique randomly generated ints
	 * 
	 * @param length
	 * @return an array of unique random ints
	 */
	public int[] randomPermutation(int length) {
		int[] permutation = new int[length];
		boolean isValid = false;
		while (isValid == false) {
			for (int i = 0; i < permutation.length; i++) {
				permutation[i] = -1;
				if (permutation[i] == -1) {
					permutation[i] = (int) Math.random() * length;
				}
				if (isValidPermutation(permutation))
					isValid = true;

			}

		}

		return permutation;
	}

	/**
	 * Returns plaintext encrypted by the vigenere cipher encoded with the
	 * String code
	 * 
	 * @param alphabet
	 *            the alphabet to be used for the encryption
	 * @param plain
	 *            the plain text to be encrypted.
	 * @param password
	 *            the code to use as the encryption key.
	 * @return returns the encrypted plainText.
	 */
	public static String vigenereCipherEncrypt(String plain, String password, String alphabet) {
		String encrypted = "";
		for (int i = 0; i < plain.length(); i++) {
			String plainLetter = plain.substring(i, i + 1);
			int shift = alphabet.indexOf(password.substring(i % password.length(), i % password.length() + 1), 0);
			int initIndex = alphabet.indexOf(plainLetter, 0);

			encrypted += alphabet.substring(ShiftIndex(initIndex, shift, alphabet.length()),
					ShiftIndex(initIndex, shift, alphabet.length()) + 1);

		}
		return encrypted;
	}

	/**
	 * Returns the result of decrypting cipherText with the key code.
	 * 
	 * @param alphabet
	 *            the alphabet to be used for the encryption
	 * @param cipher
	 *            the encrypted text.
	 * @param password
	 *            the decryption key
	 * @return returns the decrypted cipherText.
	 */
	public static String vigenereCipherDecrypt(String cipher, String password, String alphabet) {
		String decrypted = "";
		for (int i = 0; i < cipher.length(); i++) {
			String cipherLetter = cipher.substring(i, i + 1);
			int shift = alphabet.indexOf(password.substring(i % password.length(), i % password.length() + 1), 0);
			int initIndex = alphabet.indexOf(cipherLetter, 0);

			decrypted += alphabet.substring(ShiftIndex(initIndex, -shift, alphabet.length()),
					ShiftIndex(initIndex, -shift, alphabet.length()) + 1);

		}
		return decrypted;
	}

	/**
	 * returns a copy of the input plaintext String with invalid characters
	 * stripped out.
	 * 
	 * @param plaintext
	 *            The plaintext string you wish to remove illegal characters
	 *            from
	 * @param alphabet
	 *            A string of all legal characters.
	 * @return String A copy of plain with all characters not in alphabet
	 *         removed.
	 */
	private String stripInvalidChars(String plaintext, String alphabet) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < plaintext.length(); i++) { // loop through plaintext
			if (alphabet.indexOf(plaintext.charAt(i)) >= 0) // get index of char
				b.append(plaintext.charAt(i)); // if it exists, keep it
			else
				// otherwise skip it &
				System.out.println("Stripping letter: \"" + plaintext.charAt(i) // display
																				// a
																				// message
						+ "\"");
		}
		return b.toString();
	}

	/****
	 * Returns the number of words inside the given input string.
	 * 
	 * @param input
	 * @return the number of words inside a string even with spaces
	 */

	public static int countWords(String input) {
		int count = 0;
		for (int i = 1; i < input.length(); i++) {
			String currentLetter = input.substring(i, i + 1);
			String prevLetter = input.substring(i - 1, i);

			if (!currentLetter.equals(" ") && prevLetter.equals(" ")) {
				count++;
			}
		}
		return count;
	}

	/*****
	 * Returns a string array of phrases/words without spaces between them
	 * 
	 * @param input
	 * @return String array of words
	 */
	public static String[] getWords(String input) {
		String[] words = new String[countWords(input)];

		input += " ";
		int indexOfLetter = 0;
		int indexOfSpace = 0;
		int next = 0;

		for (int i = 0; i < input.length(); i++) {
			String currentLetter = input.substring(i, i + 1);
			if (!currentLetter.equals(" ")) {
				indexOfLetter = input.indexOf(currentLetter, i);
			}
			indexOfSpace = input.indexOf(" ", indexOfLetter);

			if (next < words.length) {
				words[next] = input.substring(indexOfLetter, indexOfSpace);
				next++;
			}
		}
		return words;

	}

	/**
	 * returns true if plaintext is valid English.
	 * 
	 * @param plaintext
	 *            the text you wish to test for whether it's valid English
	 * @return boolean returns true if plaintext is valid English.
	 */
	public static boolean isEnglish(String plaintext, String alphabet) {
		double min = Double.MAX_VALUE;

		for (int i = 0; i < plaintext.length(); i++) {
			fillInAllScores(plaintext, alphabet);
			double totalScore = addScores(scores);
			if (totalScore < min) {
				min = totalScore;
			}
		}

		if (min <= threshold) {
			return true;
		}

		return false;
	}

	/****
	 * 
	 * @return whether cipher1 is English
	 */
	public static String rotationCipherCrack(String cipher, String alphabet) {
		String decrypted = "";

		for (int i = 0; i < alphabet.length(); i++) {
			decrypted = rotationCipherDecrypt(cipher, i, alphabet);

			if (isEnglish(decrypted, alphabet) == true) {
				return decrypted;
			}
		}

		return "";

	}

	/****
	 * Returns the decrypted vigenere password encrypted text
	 * 
	 * @param cipher
	 * @param alphabet
	 * @return
	 */
	public static String vigenereCipherCrackThreeLetter(String cipher, String alphabet) {
		String password = "";
		String[] groups = splitInto3Groups(cipher);

		for (String group : groups) {
			password += findCodeLetter(group, alphabet);
		}
		return vigenereCipherDecrypt(cipher, password, alphabet);

	}

	/****
	 * Returns the decrypted any password length encrypted text
	 * 
	 * @param cipher
	 * @param passwordLength
	 * @param alphabet
	 * @return
	 */
	public static String vigenereCipherCrack(String cipher, int passwordLength, String alphabet) {
		String password = "";
		String[] groups = splitIntoNumGroups(cipher, passwordLength);

		for (String group : groups) {
			password += findCodeLetter(group, alphabet);
		}
		return vigenereCipherDecrypt(cipher, password, alphabet);

	}

	/****
	 * Splits a cipher text into every length of chars
	 * 
	 * @param cipher
	 * @param total
	 * @return
	 */
	private static String[] splitInto3Groups(String cipher) {
		String[] groups = new String[3];
		for (int times = 0; times < 3; times++) {
			for (int i = times; i < cipher.length(); i += 3) {
				groups[times] += cipher.substring(i, i + 1);
			}
		}
		return groups;
	}

	/****
	 * Create a specified number of different groups of letters.
	 * 
	 * @param cipher
	 * @param length
	 * @return
	 */
	private static String[] splitIntoNumGroups(String cipher, int length) {
		String[] groups = new String[length];
		for (int times = 0; times < length; times++) {
			for (int i = times; i < cipher.length(); i += length) {
				groups[times] += cipher.substring(i, i + 1);
			}
		}
		return groups;
	}

	/****
	 * Finds a code letter given the group of letters that was found
	 * 
	 * @param group
	 * @param alphabet
	 * @return
	 */
	private static String findCodeLetter(String group, String alphabet) {
		for (int shiftAmount = 0; shiftAmount < alphabet.length(); shiftAmount++) {
			String decoded = rotationCipherDecrypt(group, shiftAmount, alphabet);

			if (isEnglish(decoded, alphabet)) {
				return alphabet.substring(shiftAmount, shiftAmount + 1);
			}

		}
		return null;
	}

	/****
	 * Gets the frequency of a single letter in a plaintext/decrypted text.
	 * 
	 * @param letter
	 * @param plaintext
	 * @param alphabet
	 * @return
	 */
	private static double getFrequency(String letter, String plaintext, String alphabet) {
		int count = 0;
		for (int i = 0; i < plaintext.length(); i++) {
			if (alphabet.substring(i, i + 1).equals(letter)) {
				count++;
			}
		}

		return (double) count / (double) plaintext.length();
	}

	/****
	 * Gets a score from dividing frequency given and the corresponding english
	 * frequency %
	 * 
	 * @param frequency
	 * @return
	 */
	private static double getScore(double frequency) {
		double score = 0;
		for (int i = 0; i < englishFrequencyPercentages.length; i++) {
			score = (double) frequency / (double) englishFrequencyPercentages[i];
		}

		return score;
	}

	private static void fillInScore(int index, double score) {
		scores[index] = score;
	}

	/****
	 * Gets the total sum of all the scores (the overall score of frequencies)
	 * 
	 * @param scores
	 * @return
	 */
	private static double addScores(double[] scores) {
		double total = 0;
		for (int i = 0; i < scores.length; i++) {
			total += scores[i];
		}

		return total;
	}

	/****
	 * Fills in all the scores into the global score array.
	 * 
	 * @param plaintext
	 */
	private static void fillInAllScores(String plaintext, String alphabet) {
		clearAllScores(scores);
		for (int i = 0; i < plaintext.length(); i++) {
			String currentLetter = plaintext.substring(i, i + 1);
			double frequency = getFrequency(currentLetter, plaintext, alphabet);

			double score = getScore(frequency);

			fillInScore(alphabet.indexOf(currentLetter), score);
		}
	}

	/****
	 * Clears the entire array of scores
	 * 
	 * @param scores
	 */
	private static void clearAllScores(double[] scores) {
		for (int i = 0; i < scores.length; i++) {
			scores[i] = 0;
		}
	}

}