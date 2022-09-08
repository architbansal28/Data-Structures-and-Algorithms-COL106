package col106.assignment4.HashMap;

public class WordCounter {
	
	public WordCounter() {
		// write your code here
	}

	public int count(String str, String word) {
		// write your code here
		if (word.length() > str.length())
			return 0;
		HashMap<Integer> map = new HashMap<Integer>(str.length() - word.length() + 1);
		for (int i = 0; i < str.length() - word.length() + 1; i++) {
			String substr = str.substring(i, word.length() + i);
			if (map.contains(substr))
				map.put(substr, map.get(substr) + 1);
			else
				map.put(substr, 1);
		}
		if (!map.contains(word))
			return 0;
		return map.get(word);
	}
	
}
