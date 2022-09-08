package col106.assignment4.HashMap;

import java.util.Vector;

public class HashMap<V> implements HashMapInterface<V> {
	
	private class Node<V> {
		String key;
		V value;
		Node(String key, V value) {
			this.key = key;
			this.value = value;
		}
	}
	
	private Node<V>[] map;
	private final int size;
	
	public HashMap(int size) {
		// write your code here	
		this.map = new Node[size];
		this.size = size;
		for (int i = 0; i < size; i++)
			this.map[i] = null;
	}
	
	private int hashCode(String key) {
		int hash = 0;
		for (int i = key.length(); i > 0; i--)
			hash = (41 * hash + key.charAt(i-1)) % size;
		return hash;
	}
	
	public V put(String key, V value) {
		// write your code here
		int temp = hashCode(key);
		int hash = temp;
		if (map[hash] == null) {
			map[hash] = new Node<>(key, value);
			return null;
		}
		if (key.equals(map[hash].key)) {
			V temp1 = (V) map[hash].value;
			map[hash].value = value;
			return temp1;
		}
		hash = (hash + 1) % size;
		while (hash != temp) {
			if (map[hash] == null) {
				map[hash] = new Node<>(key, value);
				return null;
			}
			if (key.equals(map[hash].key)) {
				V temp1 = (V) map[hash].value;
				map[hash].value = value;
				return temp1;
			}
			hash = (hash + 1) % size;
		}
		return null;
	}

	public V get(String key) {
		// write your code here
		int hash = hashCode(key);
		int counter = 0;
		while (map[hash] != null) {
			if (counter++ > size)
				return null;
			if (key.equals(map[hash].key))
				return (V) map[hash].value;
			hash = (hash + 1) % size;
		}
		return null;
	}

	public boolean remove(String key) {
		// write your code here
		if (!contains(key))
			return false;
		int hash = hashCode(key);
		while (!key.equals(map[hash].key))
			hash = (hash + 1) % size;
		map[hash] = null;
		for (hash = (hash + 1) % size; map[hash] != null; hash = (hash + 1) % size) {
			Node<V> temp = map[hash];
			map[hash] = null;
			put(temp.key, (V) temp.value);
		}
		return true;
	}

	public boolean contains(String key) {
		// write your code here
		int hash = hashCode(key);
		int counter = 0;
		while (map[hash] != null) {
			if (counter++ > size)
				return false;
			if (key.equals(map[hash].key))
				return true;
			hash = (hash + 1) % size;
		}
		return false;
	}

	public Vector<String> getKeysInOrder() {
		// write your code here
		Vector<String> keySet = new Vector<>();
		for (int i = 0; i < size; i++) {
			if (map[i] != null)
				keySet.add(map[i].key);
		}
		return keySet;
	}
	
}
