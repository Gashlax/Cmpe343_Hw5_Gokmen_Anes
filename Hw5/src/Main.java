import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		try {
			Scanner s=new Scanner(System.in);

			//String firstInput=s.nextLine();
			//firstInput=firstInput.trim();
			String firstInput="input1.txt";

			File file = new File(firstInput);

			s=new Scanner(file);


			String firstLine = s.nextLine();
			TrieST trie= new TrieST();

			String str=null;
			int counter=0;
			while(s.hasNext()) {
				String newWord=s.next();
				//System.out.println(newWord);
				trie.put(newWord, newWord);
			}
			System.out.println();
			s=new Scanner(System.in);
			int userInput=0;
			while(userInput!=-1) {
				System.out.println("Please select method");
				System.out.println("1-Search");
				System.out.println("2-AutoComplete");
				System.out.println("3-ReverseAutoComplete");
				System.out.println("4-FullComplete");
				System.out.println("5-findTopK");
				System.out.println("6-SolvePuzzle");
				System.out.println("Any other number quit");
				userInput=s.nextInt();

				if(userInput==1) {
					System.out.println("Give a input");
					String input=s.next();

					if(trie.Search(input)) {
						System.out.println("True");
					}else {
						System.out.println("False");
					}

				}else if(userInput==2) {
					System.out.println("Give a input");
					String input=s.next();
					trie.autoComplete(input);

				}else if(userInput==3) {
					System.out.println("Give a input");
					String input=s.next();
					trie.reverseAutoComplete(input);

				}else if(userInput==4) {
					System.out.println("Give seperate two inputs");
					String input1=s.next();
					String input2=s.next();
					trie.FullAutoComplete(input1, input2);

				}else if(userInput==5) {
					System.out.println("Not coded");

				}else if(userInput==6) {
					System.out.println("Not coded");

				}else {
					userInput=-1;
				}
				System.out.println();


			}



			//			System.out.println(trie.Search("wall"));
			//			
			//			//trie.autoComplete("lov");
			//			trie.reverseAutoComplete("s");
			//			
			//			trie.FullAutoComplete("th", "ee");
			//			trie.FullAutoComplete("th", "eee");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("PLease give valid inputs to the system");
		}
	}


}
