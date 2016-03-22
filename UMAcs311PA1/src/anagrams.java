import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class anagrams {
	// prime number for each alphabet
	public static int[] prime = new int[] { 5, 71, 37, 29, 2, 53, 59, 19, 11, 83, 79, 31, 43, 13, 7, 67, 97, 23, 17, 3,
			41, 73, 47, 89, 61, 101 };
	// temp array for merge sort
	public static word[] temp;

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		// start time
		long start = System.currentTimeMillis();

		String line; // each line in dictionary
		ArrayList<word> myList = new ArrayList<word>(); // store words
		word myword; // word has key and value
		word[] wordlist; // use for transform ArrayList to array
		String outputName = "anagram";
		// input file from argument
		if (0 < args.length) {
			if (args[0].equals("dict1"))
				outputName = "anagram1";
			else if (args[0].equals("dict2"))
				outputName = "anagram2";
			Scanner input = new Scanner(new File(args[0]));
			// read each line from file and store into ArrayList
			while (input.hasNextLine()) {
				line = input.nextLine();
				if (!line.equals("")) {
					myword = new word(line);
					myList.add(myword);
				}
			}
			wordlist = myList.toArray(new word[myList.size()]); // transform into array
			temp = new word[wordlist.length];
			my_merge_sort(wordlist, 0, myList.size() - 1); // use merge sort to sort array depend on the key in each word
			// write output into output file
			FileWriter fw = new FileWriter(new File(outputName));
			BufferedWriter bw = new BufferedWriter(fw);

			// for loop divide word into different group depend on key
			bw.write(wordlist[0].getValue() + "\t");
			for (int i = 1; i < wordlist.length; i++) {
				if (wordlist[i].getKey() == wordlist[i - 1].getKey())
					bw.write(wordlist[i].getValue() + "\t");
				else
					bw.write("\n" + wordlist[i].getValue() + "\t");
			}
			bw.close();
			System.out.println("Runtime: " + (double) (System.currentTimeMillis() - start) / 1000); // print out the runtime
		}
		// use default dictionary when there is no argument
		else {
			Scanner input = new Scanner(new File("dict1"));
			// read each line from file and store into ArrayList
			while (input.hasNextLine()) {
				line = input.nextLine();
				if (!line.equals("")) {
					myword = new word(line);
					myList.add(myword);
				}
			}
			wordlist = myList.toArray(new word[myList.size()]); // transform into array
			temp = new word[wordlist.length];
			my_merge_sort(wordlist, 0, myList.size() - 1); // use merge sort to sort array depend on the key in each word
			// write output into output file
			FileWriter fw = new FileWriter(new File("anagram1"));
			BufferedWriter bw = new BufferedWriter(fw);

			// for loop divide word into different group depend on key
			bw.write(wordlist[0].getValue() + "\t");
			for (int i = 1; i < wordlist.length; i++) {
				if (wordlist[i].getKey() == wordlist[i - 1].getKey())
					bw.write(wordlist[i].getValue() + "\t");
				else
					bw.write("\n" + wordlist[i].getValue() + "\t");
			}
			bw.close();
			System.out.println("Runtime: " + (double) (System.currentTimeMillis() - start) / 1000); // print out the runtime
		}

	}

	// merge sort
	public static void my_merge_sort(word[] list, int low, int high) {
		if (low < high) {
			int middle = (high + low) / 2;
			my_merge_sort(list, low, middle); // recursive call merge sort with left half array
			my_merge_sort(list, middle + 1, high); // recursive call merge sort with right half array
			my_merge(list, low, middle, high); // call merge function
		}
	}

	// merge for merge sort
	public static void my_merge(word[] list, int low, int mid, int high) {
		int i = low, j = mid + 1;

		for (int k = low; k <= high; k++) {
			temp[k] = list[k];
		}

		// restore data back to origin array
		for (int k = low; k <= high; k++) {
			if (i > mid)
				list[k] = temp[j++];
			else if (j > high)
				list[k] = temp[i++];
			else if (temp[j].getKey() < temp[i].getKey())
				list[k] = temp[j++];
			else
				list[k] = temp[i++];
		}
	}
}

// word class which contain key and value
class word {
	public long key = 1;
	public String value;

	public word(String str) {
		value = str;
		// set up key associate with value
		for (int i = 0; i < str.length(); i++) {
			key = key * anagrams.prime[str.charAt(i) - 'a'];
		}
	}

	// getter for value
	public String getValue() {
		return value;
	}

	// getter for key
	public long getKey() {
		return key;
	}
}