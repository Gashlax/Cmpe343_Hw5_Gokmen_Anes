//-----------------------------------------------------
//Title: TrieST Class
//Author: Gökmen ÇAĞLAR - Anes Memisevic
//ID: 12590403284 - 99363209922
//Section: 1
//Assignment: 5
//Description: This class creates the TrieST and makes the desired functionalities.
//-----------------------------------------------------

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TrieST<Value> {
	// extended ASCII
	private static final int R = 256;

	// root of trie
	private Node root;
	// number of keys in trie
	private int n;
	private static ArrayList<Node> occurances = new ArrayList<Node>();

	// R-way trie node
	// This class helps to create trie
	private static class Node {
		private Object val;
		private Node[] next = new Node[R];
		private int occurance = 0;

	}

	/**
	 * Initializes an empty string symbol table.
	 */
	public TrieST() {
	}

	public Boolean Search(String arg) {
		// --------------------------------------------------------
		// Summary: With the help of the get methods it searches the string argument in
		// the trie
		// Precondition: Taking a string input
		// Postcondition: Returns a boolean output based on the get method
		// --------------------------------------------------------
		if (arg == null) {
			System.out.println("You cannot send null string");
		}
		return get(arg) != null;
	}

	public void autoComplete(String prefix) {
		// --------------------------------------------------------
		// Summary: This methods makes the autocomplete operation based on the given
		// prefix
		// to do that it uses get method. This get method indicates the node that end of
		// the prefix
		// afterwards to make search under of that prefix we use collect method. This
		// collect works
		// like prefix + new char and based on the correct words it gets a queue. At the
		// end of this method
		// I use queue methods to print hese words.
		// Precondition: Taking a string input
		// Postcondition: It is a void method but it prints the desired output in lex.
		// order
		// --------------------------------------------------------
		Queue<String> results = new Queue<String>();
		Node x = get(root, prefix, 0);
		collect(x, new StringBuilder(prefix), results);
		int counter = 0;
		while (results.peek() != null) {

			if (counter == 0) {
				System.out.print(results.dequeue());
			} else {
				System.out.print(", " + results.dequeue());
			}
			counter++;
		}
		if (counter == 0) {
			System.out.println("No word");
		}
	}

	public void reverseAutoComplete(String last) {
		// --------------------------------------------------------
		// Summary: This method basically uses auto complete idea at the beginning. The
		// main idea at the beginning
		// is getting all the words in the trie and add these words to queue. Afterwards
		// it uses reverseChecker()
		// method to make comparisons between end of the trie words and the parameter
		// last. Based on this
		// it makes print operation
		// Precondition: Takes string last
		// Postcondition: It is a void method but it prints the desired output in lex.
		// order
		// --------------------------------------------------------
		Queue<String> results = new Queue<String>();
		Node x = get(root, "", 0);
		collect(x, new StringBuilder(""), results);
		int counter = 0;
		while (results.peek() != null) {
			boolean status = false;
			String test = results.dequeue();
			status = reverseChecker(test, last);
			if (status) {
				if (counter == 0) {
					System.out.print(test);
				} else {
					System.out.print(", " + test);
				}
				counter++;
			}
		}
		if (counter == 0) {
			System.out.println("No word");
		}
	}

	private boolean reverseChecker(String test, String desired) {
		// --------------------------------------------------------
		// Summary: This method makes comparison between the test input and the desired
		// input
		// to do that it uses a reverse for loop until the end of the desired input
		// it it completely match with test returns true else return false
		// Precondition: Takes string test and string desired
		// Postcondition: It returns a boolean based on the comparison operation.
		// --------------------------------------------------------
		int desiredLength = desired.length() - 1;
		boolean status = true;
		// System.out.println("GELEN wordler :"+test);
		for (int i = (test.length() - 1); desiredLength > -1; i--) {
			if (status == false) {
				return false;
			}

			if (test.charAt(i) == desired.charAt(desiredLength)) {

				// System.out.print(test.charAt(i)+" "+desired.charAt(desiredLength));
				desiredLength--;
				status = true;
			} else {
				// System.out.println("buraya gird,m"+ test);
				status = false;
			}
		}
		if (status) {
			return true;
		}

		return false;
	}

	public void FullAutoComplete(String prefix, String suffix) {
		// --------------------------------------------------------
		// Summary: Takes prefix and suffix afterwards it uses the autoComplete()
		// and reverseAutoComplete() methods ideas to find desired output.
		// it uses get and collect idea and afterwards it uses that queue and uses
		// the reverseChecker() method the check suffix end of this words
		// Precondition: Takes string prefix and string suffix
		// Postcondition: It is a void method but it prints the desired output in lex.
		// order
		// --------------------------------------------------------
		Queue<String> results = new Queue<String>();
		Node x = get(root, prefix, 0);
		collect(x, new StringBuilder(prefix), results);
		int counter = 0;

		while (results.peek() != null) {
			boolean status = false;
			String test = results.dequeue();
			status = reverseChecker(test, suffix);
			if (status) {
				if (counter == 0) {
					System.out.print(test);
				} else {
					System.out.print(", " + test);
				}
				counter++;
			}
		}
		if (counter == 0) {
			System.out.println("No word");
		}

	}

	public void findTopK(int count) {
		// --------------------------------------------------------
		// Summary: Finds and prints the top k words that have most occurances
		// Precondition: Having the words stored already - so that they can be found
		// Postcondition: The number of words specified by user is printed in lex. order
		// --------------------------------------------------------
		Collections.sort(occurances, new Comparator<Node>() {
			@Override
			public int compare(TrieST.Node o1, TrieST.Node o2) {
				if (o1.occurance < o2.occurance) {
					return 1;
				} else if (o1.occurance > o2.occurance) {
					return -1;
				} else {
					return 0;
				}
			}
		});

		ArrayList<Node> findTopOccurances = new ArrayList<>();

		for (Node x : occurances) {
			String value = "" + x.val;
			// System.out.println(value+" TESTTT");
			if (!value.equals("null")) {
				// test.remove(x);
				findTopOccurances.add(x);
			}
		}

		int i = 0;
		for (; i < count - 1; i++) {
			System.out.print(findTopOccurances.get(i).val + ", ");
		}
		System.out.println(findTopOccurances.get(i).val);
	}

	/**
	 * Returns the value associated with the given key.
	 * 
	 * @param key the key
	 * @return the value associated with the given key if the key is in the symbol
	 *         table
	 *         and {@code null} if the key is not in the symbol table
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public Value get(String key) {
		if (key == null)
			throw new IllegalArgumentException("argument to get() is null");
		Node x = get(root, key, 0);
		if (x == null)
			return null;
		return (Value) x.val;
	}

	/**
	 * Does this symbol table contain the given key?
	 * 
	 * @param key the key
	 * @return {@code true} if this symbol table contains {@code key} and
	 *         {@code false} otherwise
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public boolean contains(String key) {
		if (key == null)
			throw new IllegalArgumentException("argument to contains() is null");
		return get(key) != null;
	}

	private Node get(Node x, String key, int d) {
		if (x == null)
			return null;
		if (d == key.length())
			return x;
		char c = key.charAt(d);
		return get(x.next[c], key, d + 1);
	}

	/**
	 * Inserts the key-value pair into the symbol table, overwriting the old value
	 * with the new value if the key is already in the symbol table.
	 * If the value is {@code null}, this effectively deletes the key from the
	 * symbol table.
	 * 
	 * @param key the key
	 * @param val the value
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public void put(String key, Value val) {
		if (key == null)
			throw new IllegalArgumentException("first argument to put() is null");
		if (val == null)
			delete(key);
		else
			root = put(root, key, val, 0);
	}

	private Node put(Node x, String key, Value val, int d) {
		if (x == null)
			x = new Node();
		if (d == key.length()) {
			if (x.val == null)
				n++;
			x.val = val;
			if (occurances.contains(x)) {
				x.occurance++;
			} else {
				occurances.add(x);
				x.occurance++;
			}
			return x;
		}
		char c = key.charAt(d);
		x.next[c] = put(x.next[c], key, val, d + 1);
		if (occurances.contains(x)) {
			x.occurance++;
		} else {
			occurances.add(x);
			x.occurance++;
		}
		return x;
	}

	/**
	 * Returns the number of key-value pairs in this symbol table.
	 * 
	 * @return the number of key-value pairs in this symbol table
	 */
	public int size() {
		return n;
	}

	/**
	 * Is this symbol table empty?
	 * 
	 * @return {@code true} if this symbol table is empty and {@code false}
	 *         otherwise
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns all keys in the symbol table as an {@code Iterable}.
	 * To iterate over all of the keys in the symbol table named {@code st},
	 * use the foreach notation: {@code for (Key key : st.keys())}.
	 * 
	 * @return all keys in the symbol table as an {@code Iterable}
	 */
	public Iterable<String> keys() {
		return keysWithPrefix("");
	}

	/**
	 * Returns all of the keys in the set that start with {@code prefix}.
	 * 
	 * @param prefix the prefix
	 * @return all of the keys in the set that start with {@code prefix},
	 *         as an iterable
	 */
	public Iterable<String> keysWithPrefix(String prefix) {
		Queue<String> results = new Queue<String>();
		Node x = get(root, prefix, 0);
		collect(x, new StringBuilder(prefix), results);
		return results;
	}

	private void collect(Node x, StringBuilder prefix, Queue<String> results) {
		if (x == null)
			return;
		if (x.val != null)
			results.enqueue(prefix.toString());
		for (char c = 0; c < R; c++) {
			prefix.append(c);
			collect(x.next[c], prefix, results);
			prefix.deleteCharAt(prefix.length() - 1);
		}
	}

	/**
	 * Removes the key from the set if the key is present.
	 * 
	 * @param key the key
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public void delete(String key) {
		if (key == null)
			throw new IllegalArgumentException("argument to delete() is null");
		root = delete(root, key, 0);
	}

	private Node delete(Node x, String key, int d) {
		if (x == null)
			return null;
		if (d == key.length()) {
			if (x.val != null)
				n--;
			x.val = null;
		} else {
			char c = key.charAt(d);
			x.next[c] = delete(x.next[c], key, d + 1);
		}

		// remove subtrie rooted at x if it is completely empty
		if (x.val != null)
			return x;
		for (int c = 0; c < R; c++)
			if (x.next[c] != null)
				return x;
		return null;
	}

}
