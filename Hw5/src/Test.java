//-----------------------------------------------------
//Title: Test Class
//Author: Gökmen ÇAĞLAR - Anes Memisevic
//ID: 12590403284 - 99363209922
//Section: 1
//Assignment: 5
//Description: This is the test class to test our menu and our operations
//-----------------------------------------------------

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
		try {
			System.out.println("Please give a valid input address");
			Scanner s = new Scanner(System.in);
			String firstInput = s.nextLine();
			firstInput = firstInput.trim();
			// String firstInput="input1.txt";
			File file = new File(firstInput);
			s = new Scanner(file);
			String firstLine = s.nextLine();
			TrieST trie = new TrieST();
			String str = null;
			int counter = 0;
			while (s.hasNext()) {
				String newWord = s.next();
				newWord = newWord.toLowerCase();
				// System.out.println(newWord);
				trie.put(newWord, newWord);
			}
			System.out.println();
			s = new Scanner(System.in);
			int userInput = 0;
			while (userInput != -1) {
				s = new Scanner(System.in);
				System.out.println("Please select method");
				System.out.println("1-Search");
				System.out.println("2-AutoComplete");
				System.out.println("3-ReverseAutoComplete");
				System.out.println("4-FullComplete");
				System.out.println("5-findTopK");
				System.out.println("6-SolvePuzzle");
				System.out.println("Any other number quit");
				userInput = s.nextInt();
				if (userInput == 1) {
					System.out.println("Give a input");
					String input = s.next();
					input = input.toLowerCase();
					if (trie.Search(input)) {
						System.out.println("True");
					} else {
						System.out.println("False");
					}
				} else if (userInput == 2) {
					System.out.println("Give a input");
					String input = s.next();
					trie.autoComplete(input);
				} else if (userInput == 3) {
					System.out.println("Give a input");
					String input = s.next();
					trie.reverseAutoComplete(input);
				} else if (userInput == 4) {
					System.out.println("Give seperate two inputs");
					String input1 = s.next();
					String input2 = s.next();
					trie.FullAutoComplete(input1, input2);
				} else if (userInput == 5) {
					System.out.println("Give an int");
					String input = s.next();
					int intPut = Integer.parseInt(input);
					trie.findTopK(intPut);

				} else if (userInput == 6) {

					System.out.println("give a puzzle adrress");
					s = new Scanner(System.in);
					String input1 = s.nextLine();
					input1 = input1.trim();

					File puzzleFile = new File(input1);

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
					while (s.hasNextLine()) {
						String oneLine = s.nextLine();
						String[] oneLineStringArray = oneLine.split("\\s+");
						// System.out.println(oneLine);
						for (int t = 0; t < collumnCounter; t++) {
							puzzleArray[rowCounter][t] = oneLineStringArray[t];
							// System.out.println(rowCounter+" a "+ t);

						}
						rowCounter++;
					}

					ArrayList<String> words = new ArrayList<String>();

					for (int curRow = 0; curRow < puzzleArray.length; curRow++) {
						for (int curCol = 0; curCol < puzzleArray[0].length; curCol++) {

							if (trie.Search(puzzleArray[curRow][curCol])) {
								words.add(puzzleArray[curRow][curCol]);
							}

							StringBuilder word = new StringBuilder(puzzleArray[curRow][curCol]);

							// right
							for (int toRight = curCol + 1; toRight < puzzleArray[0].length; toRight++) {
								word.append(puzzleArray[curRow][toRight]);
								String currentWord = word.toString();

								if (trie.Search(currentWord)) {
									words.add(currentWord);
								}

							}

							word = new StringBuilder(puzzleArray[curRow][curCol]);
							// to Bottom
							for (int toBottom = curRow + 1; toBottom < puzzleArray.length; toBottom++) {
								word.append(puzzleArray[toBottom][curCol]);
								String currentWord = word.toString();

								if (trie.Search(currentWord)) {
									words.add(currentWord);
								}

							}

							word = new StringBuilder(puzzleArray[curRow][curCol]);
							// to Diagonall

							for (int toBottom = curRow + 1, toRight = curCol + 1; toBottom < puzzleArray.length
									&& toRight < puzzleArray[0].length; toBottom++, toRight++) {
								word.append(puzzleArray[toBottom][toRight]);
								String currentWord = word.toString();

								if (trie.Search(currentWord)) {
									words.add(currentWord);
								}

							}

						}
					}

					// for(int t=0;t<words.size();t++) {
					// System.out.println(words.get(t));
					// }

					ArrayList<String> newList = new ArrayList<String>();

					// Traverse through the first list
					for (String check : words) {

						// If this element is not present in newList
						// then add it
						if (!newList.contains(check)) {

							newList.add(check);
						}
					}
					words = newList;
					newList = null;
					Collections.sort(words);
					for (int t = 0; t < words.size(); t++) {
						if (t == 0) {
							System.out.print(words.get(t));
						} else {
							System.out.print(", " + words.get(t));
						}

					}

					//
					// for(int row=0;row<puzzleArray.length;row++) {
					// for(int col=0;col<puzzleArray[0].length;col++) {
					// System.out.print(puzzleArray[row][col]+" ");
					// }
					// System.out.println();
					// }
					//

					// to test
					userInput = -1;
				} else {
					userInput = -1;
				}
				System.out.println();
			}
			// System.out.println(trie.Search("wall"));
			//
			// //trie.autoComplete("lov");
			// trie.reverseAutoComplete("s");
			//
			// trie.FullAutoComplete("th", "ee");
			// trie.FullAutoComplete("th", "eee");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("PLease give valid inputs to the system");
			e.printStackTrace();
			// System.out.println("PLease give valid inputs to the system");
		}
	}

}