package col106.assignment3.Heap;

import java.util.Arrays;

@SuppressWarnings("rawtypes")
public class Heap<T extends Comparable, E extends Comparable> implements HeapInterface <T, E> {
	/* 
	 * Do not touch the code inside the upcoming block 
	 * If anything tempered your marks will be directly cut to zero
	*/
	public static void main() {
		HeapDriverCode HDC = new HeapDriverCode();
		System.setOut(HDC.fileout());
	}
	/*
	 * end code
	 */
	
	// write your code here 
	public Node[] heap;
	public int pointer;
	
	@SuppressWarnings("hiding")
	public class Node<T, E extends Comparable> {
		T key;
		E value;
		public Node(T key, E value) {
			this.key = key;
			this.value = value;
		}
	}
	
	public Heap() {
		this.heap = new Node[2];
		this.pointer = 0;
	}
	
	public void insert(T key, E value) {
		// write your code here
		if (pointer == heap.length)
			heap = Arrays.copyOf(heap, 2*heap.length);
		heap[pointer] = new Node<T, E>(key, value);
		upheap(pointer);
		pointer++;
	}

	@SuppressWarnings("unchecked")
	public E extractMax() {
		//write your code here
		if (heap[0] == null)
			return null;
		E max = (E) heap[0].value;
		heap[0] = heap[pointer-1];
		pointer--;
		downheap(0);
		return max;
	}

	public void delete(T key) {
		//write your code here
		int temp = -1;
		for (int i = 0; i < pointer; i++) {
			if ((heap[i].key).equals(key)) {
				temp = i;
				break;
			}
		}
		if (temp == -1)
			return;
		heap[temp] = heap[pointer-1];
		pointer--;
		downheap(temp);
	}

	@SuppressWarnings("unchecked")
	public void increaseKey(T key, E value) {
		//write your code here
		int temp = -1;
		for (int i = 0; i < pointer; i++) {
			if ((heap[i].key).equals(key)) {
				temp = i;
				break;
			}
		}
		if (temp == -1)
			return;
		heap[temp].value = value;
		upheap(temp);
	}

	public void printHeap() {
		//write your code here
		for (int i = 0; i < pointer; i++)
			System.out.println(heap[i].key + ", " + heap[i].value);
	}
	
	@SuppressWarnings("unchecked")
	private void upheap(int n) {
		while ((n-1)/2 >= 0 && (heap[(n-1)/2].value).compareTo(heap[n].value) < 0) {
			Node<T, E> temp = heap[(n-1)/2];
			heap[(n-1)/2] = heap[n];
			heap[n] = temp;
			n = (n-1)/2;
		}
	}
	
	@SuppressWarnings("unchecked")
	private void downheap(int n) {
		while (2*n+1 < pointer) {
			int temp = 2*n + 1;
			if (2*n+2 < pointer && (heap[2*n+2].value).compareTo(heap[2*n+1].value) > 0)
				temp = 2*n + 2;
			if ((heap[n].value).compareTo(heap[temp].value) < 0) {
				Node<T, E> temp1 = heap[n];
				heap[n] = heap[temp];
				heap[temp] = temp1;
			}
			else
				break;
			n = temp;
		}
	}
	
	@SuppressWarnings("unchecked")
	public E containsKey(T key) {
		for (int i = 0; i < pointer; i++) {
			if ((heap[i].key).equals(key))
				return (E) heap[i].value;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public T getKey() {
		return (T) heap[0].key;
	}

}
