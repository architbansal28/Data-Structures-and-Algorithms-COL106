package col106.assignment3.Election;

import java.util.Map;
import java.util.HashMap;
import col106.assignment3.BST.BST;
import col106.assignment3.Heap.Heap;

public class Election implements ElectionInterface {
	/* 
	 * Do not touch the code inside the upcoming block 
	 * If anything tempered your marks will be directly cut to zero
	*/
	public static void main() {
		ElectionDriverCode EDC = new ElectionDriverCode();
		System.setOut(EDC.fileout());
	}
	/*
	 * end code
	 */
	
	//write your code here 
	BST<String, Integer> bst;
	Map<String, String[]> map;
	
	public Election() {
		bst = new BST<String, Integer>();
		map = new HashMap<String, String[]>();
	}
	
    public void insert(String name, String candID, String state, String district, String constituency, String party, String votes) {
		//write your code here 
    	String[] candidate = {name, candID, state, district, constituency, party, votes};
    	int votes1 = Integer.parseInt(votes);
    	bst.insert(candID, votes1);
    	map.put(candID, candidate);
	}
    
	public void updateVote(String name, String candID, String votes) {
		//write your code here
    	int votes1 = Integer.parseInt(votes);
    	bst.update(candID, votes1);
    	String[] temp = map.get(candID);
    	temp[6] = votes;
    	map.remove(candID);
    	map.put(candID, temp);
	}
	
	public void topkInConstituency(String constituency, String k) {
		//write your code here
		Heap<String, Integer> heap = new Heap<String, Integer>();
		for (@SuppressWarnings("rawtypes") Map.Entry m : map.entrySet()) {
			String[] value = (String[]) m.getValue();
			if ((value[4]).equals(constituency)) {
				String key = (String) m.getKey();
				int votes = Integer.parseInt(value[6]);
				heap.insert(key, votes);
			}
		}
		if (heap.pointer == 0)
			return;
		int k1 = Integer.parseInt(k);
		if (k1 > heap.pointer)
			k1 = heap.pointer; 
		for (int i = 0; i < k1; i++) {
			String candID = heap.getKey();
			String[] value = map.get(candID);
			System.out.println(value[0] + ", " + candID + ", " + value[5]);
			heap.extractMax();
		}
	}
	
	public void leadingPartyInState(String state) {
		//write your code here
		Heap<String, Integer> heap = new Heap<String, Integer>();
		BST<Integer, String> temp = new BST<Integer, String>();
		for (@SuppressWarnings("rawtypes") Map.Entry m : map.entrySet()) {
			String[] value = (String[]) m.getValue();
			if ((value[2]).equals(state)) {
				if (heap.containsKey(value[5]) == null)
					heap.insert(value[5], Integer.parseInt(value[6]));
				else {
					int votes = heap.containsKey(value[5]);
					votes += Integer.parseInt(value[6]);
					heap.increaseKey(value[5], votes);
				}
			}
		}
		if (heap.pointer == 0)
			return;
		String party = heap.getKey();
		int temp1 = heap.extractMax();
		temp.insert(temp1, party);
		while (heap.pointer > 0) {
			String party1 = heap.getKey();
			int temp2 = heap.extractMax();
			if (temp1 == temp2) 
				temp.insert(temp1, party1);
			else
				break;
		}
		printInOrder(temp.root);
	}
	
	private void printInOrder(@SuppressWarnings("rawtypes") BST.Node node) {
		if (node == null) 
            return; 
		printInOrder(node.left); 
        System.out.println(node.value);
        printInOrder(node.right);
	}
	
	public void cancelVoteConstituency(String constituency) {
		//write your code here
		BST<String, String> temp = new BST<String, String>();
		for (@SuppressWarnings("rawtypes") Map.Entry m : map.entrySet()) {
			String[] value = (String[]) m.getValue();
			temp.insert(value[4], (String) m.getKey());
		}
		inOrder(temp.root, constituency);
	}
	
	private void inOrder(@SuppressWarnings("rawtypes") BST.Node node, String constituency) {
		if (node == null) 
            return; 
        inOrder(node.left, constituency); 
        if ((node.key).equals(constituency)) {
        	bst.delete((String) node.value);
        	map.remove(node.value);
        }
        inOrder(node.right, constituency);
	}
	
	public void leadingPartyOverall() {
		//write your code here
		Heap<String, Integer> heap = new Heap<String, Integer>();
		BST<Integer, String> temp = new BST<Integer, String>();
		for (@SuppressWarnings("rawtypes") Map.Entry m : map.entrySet()) {
			String[] value = (String[]) m.getValue();
			if (heap.containsKey(value[5]) == null)
				heap.insert(value[5], Integer.parseInt(value[6]));
			else {
				int votes = heap.containsKey(value[5]);
				votes += Integer.parseInt(value[6]);
				heap.increaseKey(value[5], votes);
			}
		}
		if (heap.pointer == 0)
			return;
		String party = heap.getKey();
		int temp1 = heap.extractMax();
		temp.insert(temp1, party);
		while (heap.pointer > 0) {
			String party1 = heap.getKey();
			int temp2 = heap.extractMax();
			if (temp1 == temp2) 
				temp.insert(temp1, party1);
			else
				break;
		}
		printInOrder(temp.root);
	}
	
	public void voteShareInState(String party,String state) {
		//write your code here
		Heap<String, Integer> heap = new Heap<String, Integer>();
		double totalVotes = 0;
		for (@SuppressWarnings("rawtypes") Map.Entry m : map.entrySet()) {
			String[] value = (String[]) m.getValue();
			if ((value[2]).equals(state)) {
				if (heap.containsKey(value[5]) == null) {
					heap.insert(value[5], Integer.parseInt(value[6]));
					totalVotes += Integer.parseInt(value[6]);
				}
				else {
					int votes = heap.containsKey(value[5]);
					votes += Integer.parseInt(value[6]);
					totalVotes += Integer.parseInt(value[6]);
					heap.increaseKey(value[5], votes);
				}
			}
		}
		if (heap.pointer == 0)
			return;
		double partyVotes;
		if (heap.containsKey(party) == null)
			partyVotes = 0;
		else	
			partyVotes = heap.containsKey(party);
		int voteShare = (int) ((partyVotes/totalVotes) * 100);
		System.out.println(voteShare);
	}
	
	public void printElectionLevelOrder() {
		//write your code here
		int height = bst.height(bst.root);
		for (int i = 0; i < height; i++)
    		print(bst.root, i);
	}
	
	private void print(@SuppressWarnings("rawtypes") BST.Node node, int level) {
    	if (node == null) 
            return; 
        if (level == 0) {
        	String key = (String) node.key;
        	String[] value = map.get(key);
        	System.out.println(value[0] + ", " + value[1] + ", " + value[2] + ", " + value[3] + ", " + value[4] + ", " + value[5] + ", " + value[6]);
        }
        else if (level > 0) { 
            print(node.left, level-1); 
            print(node.right, level-1); 
        } 
    }
	
}

