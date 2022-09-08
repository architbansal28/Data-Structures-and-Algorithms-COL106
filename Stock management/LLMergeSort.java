package col106.assignment5;

import java.util.Comparator;

/*
Implementation of MergeSort Algorithm :
    1. you get linked list of size <=1 you just return the list as it is already sorted
    2. Find mid node using findSplit method(Don't forget to add mid element to globalList before returning it)
    3. Create two LinkedList lt (with head = head and tail = mid) and rt (with head = mid.next and tail = tail)
    4. Now recursively MergSort lt and rt Linked lists(Maintain this order)
    5. Now merge these two lists that we got from recursive calls using given crieteria for ordering
    6. Return merged Linked list
*/

public class LLMergeSort <T extends Comparable>  {

  LinkedList<T>  globalList = new LinkedList<T>();

  //CALL THIS METHOD AFTER EVERY CALL OF findSplit and DO NOT MODIFY THIS METHOD
  public void adjustGlobalPointer(T node){
      globalList.add(node);
  }

  // Utility function to get the middle of the linked list
  public Node<T> findSplit(LinkedList<T>  lst) {
    //find middle node of LL :
    Node<T> middle = lst.getHead();
    //Enter your code here
    if (middle==null)
    	return middle;
    else if (lst.getSize()%2 == 0) {
    	for (int i=1; i<lst.getSize()/2; i++)
    		middle = middle.getNext();
    }
    else {
    	for (int i=1; i<((int)Math.floor(lst.getSize()/2))+1; i++)
    		middle = middle.getNext();
    }
    
    //!!!!!*****DO NOT REMOVE THIS METHOD CALL (change the argument apprpriately)*****!!!!!
    adjustGlobalPointer(middle.getData()); //Add object of ItemNode after finding mid in each call
    return middle;
  }


  public LinkedList<T>  MergeSort(LinkedList<T>  lst) {
    //Recursively Apply MergeSort, by calling function findSplit(..) to find middle node to split
    //Enter your code here
	Node<T> head = lst.getHead();
	if (head==null || head.getNext()==null)
		return lst;
	Node<T> middle = findSplit(lst);
	Node<T> head1 = middle.getNext();
	LinkedList<T> lt = new LinkedList<T>();
	LinkedList<T> rt = new LinkedList<T>();
	while (head!=head1) {
		lt.add(head.getData());
		head = head.getNext();
	}
	while (head1!=null) {
		rt.add(head1.getData());
		head1 = head1.getNext();
	}
	lt = MergeSort(lt);
	rt = MergeSort(rt);
	lst = Merge(lt, rt);
	return lst;

  }
  
  public LinkedList<T> Merge(LinkedList<T> lt, LinkedList<T> rt) {
	  LinkedList<T> lst = new LinkedList<T>();
	  if (lt==null)
		  return rt;
	  if (rt==null)
		  return lt;
	  Node<T> head = lt.getHead();
	  Node<T> head1 = rt.getHead();
	  while (head!=null && head1!=null) {
		  if (head.getData().compareTo(head1.getData()) == -1) {
			  lst.add(head.getData());
			  head = head.getNext();
		  }
		  else if (head.getData().compareTo(head1.getData()) == 1) {
			  lst.add(head1.getData());
			  head1 = head1.getNext();
		  }
	  }
	  while (head!=null) {
		  lst.add(head.getData());
		  head = head.getNext();
	  }
	  while (head1!=null) {
		  lst.add(head1.getData());
		  head1 = head1.getNext();
	  }
	  return lst;
  }

  //DO NOT CALL OR MODIFY THESE METHODS IN YOUR CALL THIS IS FOR USE IN DRIVER CODE
  public LinkedList<T> getGlobalList() {
    return this.globalList;
  }

  public void clearGlobalList(){
    globalList  = new LinkedList<>();
  }

}
