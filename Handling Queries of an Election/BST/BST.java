package col106.assignment3.BST;

@SuppressWarnings("rawtypes")
public class BST<T extends Comparable, E extends Comparable> implements BSTInterface<T, E>  {
	/* 
	 * Do not touch the code inside the upcoming block 
	 * If anything tempered your marks will be directly cut to zero
	*/
	public static void main() {
		BSTDriverCode BDC = new BSTDriverCode();
		System.setOut(BDC.fileout());
	}
	/*
	 * end code
	 * start writing your code from here
	 */
	
	//write your code here 
	public Node root;
	
	public class Node {
		public T key;
		public E value;
		public Node left, right;	
		public Node(T key, E value) {
			this.key = key;
			this.value = value;
			left = null;
			right = null;
		}
	}

	public BST() {
		this.root = null;
	}
	
    @SuppressWarnings("unchecked")
	public void insert(T key, E value) {
		//write your code here
    	Node node = new Node(key, value);
    	if (root == null) {
    		root = node;
    		return;
    	}
    	Node current = root;
    	Node parent = null;
    	while(true) {
    		parent = current;
    		if (value.compareTo(current.value) < 0) {
    			current = current.left;
    			if (current == null) {
    				parent.left = node;
    				return;
    			}
    		}
    		else {
    			current = current.right;
    			if (current == null) {
    				parent.right = node;
    				return;
    			}
    		}
    	}
    }

    public void update(T key, E value) {
		//write your code here
    	delete(key);
    	insert(key, value);
    }

    @SuppressWarnings("unchecked")
	public void delete(T key) {
		//write your code here
    	Node parent = root;
    	Node current = root;
    	E value = value(root, key);
    	if (value == null)
    		return;
    	boolean leftChild = false;
    	while (!(current.key).equals(key)) {
    		parent = current;
    		if (value.compareTo(current.value) < 0) {
    			leftChild = true;
    			current = current.left;
    		}
    		else {
    			leftChild = false;
    			current = current.right;
    		}
    		if (current == null)
    			return;
    	}
    	
    	if (current.left == null && current.right == null) {
    		if (current.equals(root))
    			root = null;
    		if (leftChild == true)
    			parent.left = null;
    		else
    			parent.right = null;
    	}
    	
    	else if (current.left == null) {
    		if (current.equals(root))
    			root = current.right;
    		else if (leftChild == true) 
    			parent.left = current.right;
    		else
    			parent.right = current.right;
    	}
    	
    	else if (current.right == null) {
    		if (current.equals(root))
    			root = current.left;
    		else if (leftChild == true) 
    			parent.left = current.left;
    		else
    			parent.right = current.left;
    	}
    	
    	else if (current.left != null && current.right != null) {
    		Node temp = current.right;
    		Node temp1 = null;
    		Node temp2 = null;
    		while (temp != null) {
    			temp2 = temp1;
    			temp1 = temp;
    			temp = temp.left;
    		}
    		if (!temp1.equals(current.right)) {
    			temp2.left = temp1.right;
    			temp1.right = current.right;
    		}
    		if (current.equals(root))
    			root = temp1;
    		else if (leftChild == true)
    			parent.left = temp1;
    		else
    			parent.right = temp1;
    		temp1.left = current.left;
    	}
    }
    
    private E value(Node node, T key) {
    	Node current = root;
    	Node temp;
    	E value = null;
    	if (node == null)
    		return null;
    	while (current != null) {
    		if (current.left == null) { 
    			if ((current.key).equals(key))
    				value = current.value;
                current = current.right; 
            } 
            else { 
                temp = current.left; 
                while (temp.right != null && !(temp.right).equals(current)) 
                    temp = temp.right; 
                if (temp.right == null) { 
                    temp.right = current; 
                    current = current.left; 
                } 
                else { 
                    temp.right = null;
                    if ((current.key).equals(key))
        				value = current.value;
                    current = current.right; 
                }
            }
    	}
		return value;
    }

    public void printBST () {
		//write your code here
    	int height = height(root);
    	for (int i = 0; i < height; i++)
    		print(root, i);
    }
    
    public int height(Node node) {
    	if (node == null)
    		return 0;
    	else {
    		int temp1 = height(node.left); 
            int temp2 = height(node.right); 
            if (temp1 > temp2) 
                return(temp1 + 1); 
            else 
            	return(temp2 + 1);
    	}
    }
    
    private void print(Node node, int level) {
    	if (node == null) 
            return; 
        if (level == 0) 
            System.out.println(node.key + ", " + node.value); 
        else if (level > 0) 
        { 
            print(node.left, level-1); 
            print(node.right, level-1); 
        } 
    }

}
