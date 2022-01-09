//-----------------------------------------------------
//Title: Main Class
//Author: Gökmen ÇAĞLAR - Anes Memisevic
//ID: 12590403284 - 99363209922
//Section: 1
//Assignment: 5
//Description: This class Main class makes the read operations and uses
// the other methods.
//-----------------------------------------------------

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// At this point we declare the String inputs
		String input = null;
		String input1 = null;
		String input2 = null;
		try {
			Scanner s = new Scanner(System.in);
			// This indicates the address of the input text
			String firstInput = s.nextLine();
			firstInput = firstInput.trim();

			String secondInput = s.nextLine();
			secondInput = secondInput.trim();
			int userInput = Integer.parseInt(secondInput);
			// To categorize the inputs
			if (userInput == 1 || userInput == 2 || userInput == 3 || userInput == 5 || userInput == 6) {
				input = s.nextLine();
			} else if (userInput == 4) {
				input1 = s.next();
				input2 = s.next();
			}

			// String firstInput="input1.txt";

			File file = new File(firstInput);

			s = new Scanner(file);

			String firstLine = s.nextLine();
			// Creation of trieSt
			TrieST trie = new TrieST();

			String str = null;
			int counter = 0;
			while (s.hasNext()) {
				// we put our words to trie
				// before putting we convert them to lowerCase
				String newWord = s.next();
				newWord = newWord.toLowerCase();
				// System.out.println(newWord);
				trie.put(newWord, newWord);
			}

			System.out.println();

			while (userInput != -1) {

				userInput = Integer.parseInt(secondInput);
				if (userInput == 1) {
					// It indicates the Search methods

					input = input.toLowerCase();
					if (trie.Search(input)) {
						System.out.println("True");
					} else {
						System.out.println("False");
					}
					userInput = -1;

				} else if (userInput == 2) {
					// it indicates the autoComplete

					trie.autoComplete(input);
					userInput = -1;
				} else if (userInput == 3) {
					// it indicates the reverseAutoComplete

					trie.reverseAutoComplete(input);
					userInput = -1;

				} else if (userInput == 4) {
					// it indicates the FullAutoComplete

					trie.FullAutoComplete(input1, input2);
					userInput = -1;

				} else if (userInput == 5) {
					// it indicates the findTopK

					int howMany = Integer.parseInt(input);
					trie.findTopK(howMany);
					userInput = -1;

				} else if (userInput == 6) {
					// it indicates the puzzle

					// File puzzleFile = new File("puzzle1.txt");
					solvePuzzle(input, trie);

					userInput = -1;
				} else {
					userInput = -1;
				}
				System.out.println();

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("PLease give valid inputs to the system");
		}
	}

	private static void solvePuzzle(String input, TrieST trie) throws FileNotFoundException {
		Scanner s;
		File puzzleFile = new File(input);

		s = new Scanner(puzzleFile);

		int rowCounter = 0;
		int collumnCounter = 0;

		String[][] puzzleArray;
		String line = s.nextLine();
		String[] toCount = line.split("\\s+");

		collumnCounter = toCount.length;
		// System.out.println(collumnCounter+" şlensdfsdf");

		while (s.hasNextLine()) {
			String lineCounter = s.nextLine();
			rowCounter++;
		}
		puzzleArray = new String[rowCounter + 1][collumnCounter];
		s = new Scanner(puzzleFile);

		rowCounter = 0;
		oneListSplit(s, rowCounter, collumnCounter, puzzleArray);

		ArrayList<String> words = new ArrayList<String>();

		// Based on the 2D array now we can make search operations in 3 ways
		for (int curRow = 0; curRow < puzzleArray.length; curRow++) {
			// this parts make search for 1 char
			for (int curCol = 0; curCol < puzzleArray[0].length; curCol++) {

				// same time it makes search operations
				if (trie.Search(puzzleArray[curRow][curCol])) {
					words.add(puzzleArray[curRow][curCol]);
				}

				StringBuilder word = new StringBuilder(puzzleArray[curRow][curCol]);

				// this parts make search for right chars
				// right
				checkRight(trie, puzzleArray, words, curRow, curCol, word);

				word = new StringBuilder(puzzleArray[curRow][curCol]);

				// this parts make search for bottom chars
				// to Bottom
				checkBottom(trie, puzzleArray, words, curRow, curCol, word);

				word = new StringBuilder(puzzleArray[curRow][curCol]);

				// this parts make search for Diagonal chars
				// to Diagonal
				checkDiagonal(trie, puzzleArray, words, curRow, curCol, word);

			}
		}

		// for(int t=0;t<words.size();t++) {
		// System.out.println(words.get(t));
		// }

		ArrayList<String> newList = new ArrayList<String>();

		// This for loop used to remove repetitions for example "a" char
		firstTraversing(words, newList);
		words = newList;
		newList = null;
		// Then we make sort operation
		extractOutput(words);
	}

	private static void extractOutput(ArrayList<String> words) {
		Collections.sort(words);
		// And print the words
		for (int t = 0; t < words.size(); t++) {
			if (t == 0) {
				System.out.print(words.get(t));
			} else {
				System.out.print(", " + words.get(t));
			}

		}
	}

	private static void firstTraversing(ArrayList<String> words, ArrayList<String> newList) {
		for (String check : words) {

			// If this element is not present in newList
			// then add it
			if (!newList.contains(check)) {

				newList.add(check);
			}
		}
	}

	private static void checkDiagonal(TrieST trie, String[][] puzzleArray, ArrayList<String> words, int curRow,
			int curCol,
			StringBuilder word) {
		for (int toBottom = curRow + 1, toRight = curCol + 1; toBottom < puzzleArray.length
				&& toRight < puzzleArray[0].length; toBottom++, toRight++) {
			word.append(puzzleArray[toBottom][toRight]);
			String currentWord = word.toString();

			if (trie.Search(currentWord)) {
				words.add(currentWord);
			}

		}
	}

	private static void checkBottom(TrieST trie, String[][] puzzleArray, ArrayList<String> words, int curRow,
			int curCol,
			StringBuilder word) {
		for (int toBottom = curRow + 1; toBottom < puzzleArray.length; toBottom++) {
			word.append(puzzleArray[toBottom][curCol]);
			String currentWord = word.toString();

			if (trie.Search(currentWord)) {
				words.add(currentWord);
			}

		}
	}

	private static void checkRight(TrieST trie, String[][] puzzleArray, ArrayList<String> words, int curRow, int curCol,
			StringBuilder word) {
		for (int toRight = curCol + 1; toRight < puzzleArray[0].length; toRight++) {
			word.append(puzzleArray[curRow][toRight]);
			String currentWord = word.toString();

			if (trie.Search(currentWord)) {
				words.add(currentWord);
			}

		}
	}

	private static void oneListSplit(Scanner s, int rowCounter, int collumnCounter, String[][] puzzleArray) {
		while (s.hasNextLine()) {
			// This loop creates the 2D array based on the input
			String oneLine = s.nextLine();
			String[] oneLineStringArray = oneLine.split("\\s+");
			// System.out.println(oneLine);
			for (int t = 0; t < collumnCounter; t++) {
				puzzleArray[rowCounter][t] = oneLineStringArray[t];
				// System.out.println(rowCounter+" a "+ t);

			}
			rowCounter++;
		}
	}

}
