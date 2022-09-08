package col106.assignment4.WeakAVLMap;

import java.util.Vector;

public class WeakAVLMap<K extends Comparable, V> implements WeakAVLMapInterface<K, V> {
	
	private class Node {
		K key; 
		V value;
		int rank;
		Node left, right, parent;
		private Node(K key, V value, Node parent) {
			this.key = key;
			this.value = value;
			this.rank = 1;
			this.left = this.right = null;
			this.parent = parent;
		}
	}
	
	private Node root;
	private int rotations;
	private Vector<V> searchRange;
	private Vector<K> BFS;

	public WeakAVLMap() {
		// write your code here
		this.root = null;
		this.rotations = 0;
	}
	
	private void rotateLeft(Node node) {
		Node temp = node.right;
		node.right = temp.left;
		if (temp.left != null) 
			temp.left.parent = node;
		temp.parent = node.parent;
		if (node.parent == null)
			root = temp;
		else if (node.parent.left == node) 
			node.parent.left = temp;
		else
			node.parent.right = temp;
		temp.left = node;
		node.parent = temp;
	}
	
	private void rotateRight(Node node) {
		Node temp = node.left;
		node.left = temp.right;
		if (temp.right != null) 
			temp.right.parent = node;
		temp.parent = node.parent;
		if (node.parent == null)
			root = temp;
		else if (node.parent.right == node) 
			node.parent.right = temp;
		else
			node.parent.left = temp;
		temp.right = node;
		node.parent = temp;
	}
	
	public V put(K key, V value) {
		// write your code her 
		if (root == null) {
			root = new Node(key, value, null);
			return null;
		}
		Node current = root;
		Node parent = null;
		while (true) {
			parent = current;
			if (key.compareTo(current.key) < 0) {
				current = current.left;
    			if (current == null) {
    				parent.left = new Node(key, value, parent);
    				break;
    			}
			}
			else if (key.compareTo(current.key) > 0) {
				current = current.right;
				if (current == null) {
    				parent.right = new Node(key, value, parent);
    				break;
    			}
			}
			else if (key.compareTo(current.key) == 0) {
				V temp = current.value;
				current.value = value;
				return temp;
			}
		}
		if (parent.rank == 1) {
			parent.rank++;
			fixAfterInsertion(parent);
		}
		return null;
	}
	
	private void fixAfterInsertion(Node node) {
		for (Node parent = node.parent; parent != null && node.rank + 1 != parent.rank; node.rank++) {
			if (parent.left == node) {
				boolean needToRotateRight = false;
				if (parent.right == null) {
					if (parent.rank == 2) 
						needToRotateRight = true;
				}
				else if (parent.rank >= parent.right.rank + 2)
					needToRotateRight = true;
				if (needToRotateRight) {
					if (node.left == null || node.rank >= node.left.rank + 2) {
						node.rank--;
						node.right.rank++;
						rotateLeft(node);
						rotations++;
					}
					parent.rank--;
					rotateRight(parent);
					rotations++;
					break;
				}
			}
			else {
				boolean needToRotateLeft = false;
				if (parent.left == null) {
					if (parent.rank == 2)
						needToRotateLeft = true;
				}
				else if (parent.rank >= parent.left.rank + 2)
					needToRotateLeft = true;
				if (needToRotateLeft) {
				    if (node.right == null || node.rank >= node.right.rank + 2) {
				    	node.rank--; 
				    	node.left.rank++;
				    	rotateRight(node);
				    	rotations++;
				    }
				    parent.rank--;
				    rotateLeft(parent);
				    rotations++;
				    break;
				}
			}
			node = parent;
			parent = node.parent;
		}
	}

	public V remove(K key) {
		// write your code her
		Node node = root;
		while (node != null) {
			if (key.compareTo(node.key) < 0)
				node = node.left;
			else if (key.compareTo(node.key) > 0) 
				node = node.right;
			else if (key.compareTo(node.key) == 0)
				break;
		}
		if (node == null)
			return null;
		V value = node.value;
		if (node.left != null && node.right != null) {
			Node temp = node;
			Node successor;
			if (temp.right != null) {
				successor = temp.right;
				while (successor.left != null) 
					successor = successor.left;
			}
			else {
				successor = temp.parent;
				Node temp1 = temp;
				while (successor != null && temp1 == successor.right) {
					temp1 = successor;
					successor = successor.parent;
				}
			}
			node.key = successor.key;
			node.value = successor.value;
			node = successor;
		}
		Node current;
		if (node.left != null)
			current = node.left;
		else
			current = node.right;
		if (current != null) {
			Node sibling = null;
			current.parent = node.parent;
			if (node.parent == null) {
				root = current;
				return value;
			}
			else if (node == node.parent.left) {
				node.parent.left = current;
				sibling = node.parent.right;
			}
			else {
				node.parent.right = current;
				sibling = node.parent.left;
			}
			node.left = null;
			node.right = null;
			node.parent = null;
			fixAfterDeletion(current.parent, sibling, current);
		}
		else if (node.parent == null)
			root = null;
		else {
			Node parent = node.parent;
			Node sibling = null;
			if (node == node.parent.left) {
				node.parent.left = null;
				sibling = parent.right;
			}
			else if (node == node.parent.right) {
				node.parent.right = null;
				sibling = parent.left;
			}
			node.parent = null;
			node.rank--;
			fixAfterDeletion(parent, sibling, node);
		}
		return value;
	}
	
	private void fixAfterDeletion(Node parent, Node sibling, Node node) {
		int rank = parent.rank - node.rank;
		while (rank == 3 || parent.rank == 2 && TwoTwoNode(parent) == true) { 
			int sibRank;
			if (sibling == null)
				sibRank = parent.rank;
			else
				sibRank = parent.rank - sibling.rank;
			if (sibRank == 2)
				parent.rank--;
			else {
				int LSibRank, RSibRank, temp1, temp2;
				if (sibling.left == null)
					temp1 = 0; 
				else
					temp1 = sibling.left.rank;
				if (sibling.right == null)
					temp2 = 0;
				else
					temp2 = sibling.right.rank;
				LSibRank = sibling.rank - temp1;
				RSibRank = sibling.rank - temp2;
				if (LSibRank == 2 && RSibRank == 2) {
					sibling.rank--;
					parent.rank--;
				}
				else if (parent.right == sibling) {
					if (RSibRank == 1) {
						parent.rank--;
						sibling.rank++;
						if (sibling.left == null)
							parent.rank--;
						rotateLeft(parent);
						rotations++;
					}
					else {
						sibling.rank--;
						sibling.left.rank = sibling.left.rank + 2;
						parent.rank = parent.rank - 2;
						rotateRight(sibling);
						rotations++;
						rotateLeft(parent);
						rotations++;
					}
					break;
				}
				else {
					if (LSibRank == 1) {
						parent.rank--;
						sibling.rank++;
						if (sibling.right == null)
							parent.rank--;
						rotateRight(parent);
						rotations++;
					}
					else {
						sibling.rank--;
						sibling.right.rank = sibling.right.rank + 2;
						parent.rank = parent.rank - 2;
						rotateLeft(sibling);
						rotations++;
						rotateRight(parent);
						rotations++;
					}
					break;
				}
			}
			if (parent.parent == null)
				return;
			node = parent;
			parent = parent.parent;
			if (parent.left == node)
				sibling = parent.right;
			else
				sibling = parent.left;
			rank = parent.rank - node.rank;
		}
	}
	
	private boolean TwoTwoNode(Node node) {
		if (node.rank == 1 || node == null)
			return false;
		if (node.rank == 2) {
			if (node.left == null && node.right == null)
				return true;
			else
				return false;
		}
		else {
			if (node.left.rank == node.right.rank && node.left.rank + 2 == node.rank)
				return true;
			else
				return false;
		}
	}
	
	public V get(K key) {
		// write your code her 
		Node node = root;
		while (node != null) {
			if (key.compareTo(node.key) < 0)
				node = node.left;
			else if (key.compareTo(node.key) > 0) 
				node = node.right;
			else
				return node.value;
		}
		return null;
	}

	public Vector<V> searchRange(K key1, K key2) {
		// write your code her 
		searchRange = new Vector<>();
		inOrderTraversal(root, key1, key2);
		return searchRange;
	}
	
	private void inOrderTraversal(Node node, K key1, K key2) {
		if (node == null)
			return;
		inOrderTraversal(node.left, key1, key2);
		if (key1.compareTo(node.key) <= 0 && key2.compareTo(node.key) >= 0) 
			searchRange.add(node.value);
		inOrderTraversal(node.right, key1, key2);
	}

	public int rotateCount() {
		// write your code her 
		return rotations;
	}

	public int getHeight() {
		// write your code her 
		return getHeight(root);
	}
	
	private int getHeight(Node node) {
		if (node == null)
		    return 0;
		return Math.max(getHeight(node.left), getHeight(node.right)) + 1;
	}

	public Vector<K> BFS() {
		// write your code her 
		BFS = new Vector<>();
		for (int i = 0; i < getHeight(); i++)
    		BFS(root, i);
		return BFS;
	}
	
	private void BFS(Node node, int level) {
    	if (node == null) 
            return; 
        if (level == 0) 
            BFS.add(node.key); 
        else if (level > 0) 
        { 
            BFS(node.left, level-1); 
            BFS(node.right, level-1); 
        } 
    }

}
