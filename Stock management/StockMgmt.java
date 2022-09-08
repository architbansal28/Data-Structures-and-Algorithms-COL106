package col106.assignment5;

public class StockMgmt implements StockMgmtInterface {
	//!!!!!!!*********DON'T CREATE YOUR OWN OBJECTS OF LLMergeSort*********!!!!!!!
	//use these only this object to sort
	LLMergeSort mergeSortobj;

	LinkedList<CategoryNode> categories;
	
	static int flag = 0;

	//DO NOT MNODIFY THIS CONSTRUCTOR
	public StockMgmt() {

		mergeSortobj = new LLMergeSort();
		categories = new LinkedList<CategoryNode>();

		categories.add(new CategoryNode(1, "mobile"));
		categories.add(new CategoryNode(2, "utensils"));
		categories.add(new CategoryNode(3, "sanitary"));
		categories.add(new CategoryNode(4, "medicalEquipment"));
		categories.add(new CategoryNode(5, "clothes"));

	}

	public void addItem(int categoryId, int itemId, String itemName, int stock) {
		//Your code goes here
		Node<CategoryNode> head = categories.getHead();
		for (int i=1; i<categoryId; i++)
			head = head.getNext();
		LinkedList<ItemNode> itemList = head.data.getLinkedListOfCategory();
		itemList.add(new ItemNode(itemId, itemName, stock));
	}

	public void addItemTransaction(int categoryId, int itemId, String itemName, int numItemPurchased, int day, int month, int year) {
		//Your code goes here
		Node<CategoryNode> head = categories.getHead();
		for (int i=1; i<categoryId; i++)
			head = head.getNext();
		LinkedList<ItemNode> itemList = head.data.getLinkedListOfCategory();
		Node<ItemNode> head1 = itemList.getHead();
		while (head1!=null) {
			if (head1.data.getItemId() == itemId) {
				LinkedList<PurchaseNode> purchaseTransactions = head1.data.purchaseTransactions;
				PurchaseNode purchase = new PurchaseNode(numItemPurchased, day, month, year);
				purchaseTransactions.add(purchase);
				head1.data.addTransaction(purchase);
				break;
			}
			head1 = head1.getNext();
		}
	}

	//Query 1
	public LinkedList<ItemNode> sortByLastDate() {
		//Your code goes here
		flag = 1;
		LinkedList<ItemNode> list = new LinkedList<ItemNode>();
		Node<CategoryNode> head = categories.getHead();
		for (int i=0; i<5; i++) {
			LinkedList<ItemNode> itemList = head.data.getLinkedListOfCategory();
			if (itemList.getHead() == null)
				continue;
			Node<ItemNode> current = itemList.getHead();
			while (current != null) {
				LinkedList<PurchaseNode> purchaseTransactions = current.data.purchaseTransactions;
				if (purchaseTransactions.getHead() == null)
					purchaseTransactions.add(new PurchaseNode(0, 1, 8, 1970));
				Node<PurchaseNode> current1 = purchaseTransactions.getHead();
				int lastDate = 0;
				while (current1 != null) {
					if (current1.data.getIntDate() > lastDate)
						lastDate = current1.data.getIntDate();
					current1 = current1.getNext();
				}
				current.data.lastDate = lastDate;
				list.add(current.getData());
				current = current.getNext();
			}
			head = head.getNext();
		}
		LinkedList<ItemNode> sortByLastDate = mergeSortobj.MergeSort(list);
		return sortByLastDate;
	}

	//Query 2
	public LinkedList<ItemNode> sortByPurchasePeriod(int day1, int month1, int year1, int day2, int month2, int year2) {
		//Your code goes here
		flag = 2;
		String day3 = String.format("%02d", day1);
		String month3 = String.format("%02d", month1);
		String year3 = String.format("%04d", year1);
		String date3 = "";
		date3 = date3.concat(year3).concat(month3).concat(day3);
		int date1 = Integer.parseInt(date3);
		String day4 = String.format("%02d", day2);
		String month4 = String.format("%02d", month2);
		String year4 = String.format("%04d", year2);
		String date4 = "";
		date4 = date4.concat(year4).concat(month4).concat(day4);
		int date2 = Integer.parseInt(date4);
		
		LinkedList<ItemNode> list = new LinkedList<ItemNode>();
		Node<CategoryNode> head = categories.getHead();
		for (int i=0; i<5; i++) {
			LinkedList<ItemNode> itemList = head.data.getLinkedListOfCategory();
			if (itemList.getHead() == null)
				continue;
			Node<ItemNode> current = itemList.getHead();
			while (current != null) {
				LinkedList<PurchaseNode> purchaseTransactions = current.data.purchaseTransactions;
				if (purchaseTransactions.getHead() == null)
					purchaseTransactions.add(new PurchaseNode(0, 1, 8, 1970));
				Node<PurchaseNode> current1 = purchaseTransactions.getHead();
				int numPurchase = 0, lastYear = 0, firstYear = 0;
				if (current1.data.getIntDate()>=date1 && current1.data.getIntDate()<=date2) {
					numPurchase += current1.data.numItemsPurchased(); 
					firstYear = current1.data.getDate().getYear();
					lastYear = current1.data.getDate().getYear();
				}
				current1 = current1.getNext();
				while (current1 != null) {
					if (current1.data.getIntDate()>=date1 && current1.data.getIntDate()<=date2) {
						numPurchase += current1.data.numItemsPurchased(); 
						if (current1.data.getDate().getYear()<firstYear || firstYear==0)
							firstYear = current1.data.getDate().getYear();
						if (current1.data.getDate().getYear()>lastYear || lastYear==0)
							lastYear = current1.data.getDate().getYear();
					}
					current1 = current1.getNext();
				}
				float yearDiff = lastYear - firstYear + 1;
				float ans = numPurchase / yearDiff;
				current.data.numPurchase = ans;
				list.add(current.getData());
				current = current.getNext();
			}
			head = head.getNext();
		}
		LinkedList<ItemNode> sortByPurchasePeriod = mergeSortobj.MergeSort(list);
		return sortByPurchasePeriod;
	}

	//Query 3
	public LinkedList<ItemNode> sortByStockForCategory(int category) {
		//Your code goes here
		flag = 3;
		Node<CategoryNode> head = categories.getHead();
		for (int i=1; i<category; i++)
			head = head.getNext();
		LinkedList<ItemNode> itemList = head.data.getLinkedListOfCategory();
		LinkedList<ItemNode> sortByStockForCategory = mergeSortobj.MergeSort(itemList);
		return sortByStockForCategory;
	}

	//Query 4
	public LinkedList<ItemNode> filterByCategorySortByDate(int category) {
		//Your code goes here
		flag = 4;
		Node<CategoryNode> head = categories.getHead();
		for (int i=1; i<category; i++)
			head = head.getNext();
		LinkedList<ItemNode> itemList = head.data.getLinkedListOfCategory();
		Node<ItemNode> current = itemList.getHead();
		while (current != null) {
			LinkedList<PurchaseNode> purchaseTransactions = current.data.purchaseTransactions;
			if (purchaseTransactions.getHead() == null)
				purchaseTransactions.add(new PurchaseNode(0, 1, 8, 1970));
			Node<PurchaseNode> current1 = purchaseTransactions.getHead();
			int lastDate = 0;
			while (current1 != null) {
				if (current1.data.getIntDate() > lastDate)
					lastDate = current1.data.getIntDate();
				current1 = current1.getNext();
			}
			current.data.lastDate = lastDate;
			current = current.getNext();
		}
		LinkedList<ItemNode> filterByCategorySortByDate = mergeSortobj.MergeSort(itemList);
		return filterByCategorySortByDate;
	}

	//!!!!!*****DO NOT MODIFY THIS METHOD*****!!!!!//
	public LinkedList<ItemNode> checkMergeSort() {
		LinkedList<ItemNode> retLst = mergeSortobj.getGlobalList();
		mergeSortobj.clearGlobalList();
		return retLst;
	}
}
