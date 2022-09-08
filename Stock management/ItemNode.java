package col106.assignment5;

public class ItemNode implements ItemInterface, Comparable<ItemNode> {

	int itemId;
	String itemName;
	int stock;
	LinkedList<PurchaseNode> purchaseTransactions;
	int lastDate;
	float numPurchase;

	public ItemNode(int itemId, String itemName, int stock){
		this.itemId = itemId;
		this.itemName = itemName;
		this.stock = stock;
		this.purchaseTransactions = new LinkedList<>();
	}

	public int getItemId(){
		//Enter your code here
		return this.itemId;
	}

	public  String getItemName(){
		//Enter your code here
		return this.itemName;
	}

	public int getStockLeft(){
		//Enter your code here
		return this.stock;
	}

	public void addTransaction(PurchaseNode purchaseT){
		//Enter your code here
		this.stock = stock - purchaseT.numItemsPurchased();
	}

	public Node<PurchaseNode> getPurchaseHead(){
		//Enter your code here
		return purchaseTransactions.getHead();
	}

	@Override
	public int compareTo(ItemNode o) {
		if (StockMgmt.flag == 1) {
			if (this.lastDate < o.lastDate)
				return -1;
			else if (this.lastDate > o.lastDate)
				return 1;
			else if ((this.getItemName()).compareTo(o.getItemName()) > 0)
				return -1;
			else if ((this.getItemName()).compareTo(o.getItemName()) < 0)
				return 1;
		}
		if (StockMgmt.flag == 2) {
			if (this.numPurchase < o.numPurchase)
				return -1;
			else if (this.numPurchase > o.numPurchase)
				return 1;
			else if ((this.getItemName()).compareTo(o.getItemName()) > 0)
				return -1;
			else if ((this.getItemName()).compareTo(o.getItemName()) < 0)
				return 1;
		}
		if (StockMgmt.flag == 3) {
			if (this.getStockLeft() > o.getStockLeft())
				return -1;
			else if (this.getStockLeft() < o.getStockLeft())
				return 1;
			else if ((this.getItemName()).compareTo(o.getItemName()) > 0)
				return -1;
			else if ((this.getItemName()).compareTo(o.getItemName()) < 0)
				return 1;
		}
		if (StockMgmt.flag == 4) {
			if (this.lastDate > o.lastDate)
				return -1;
			else if (this.lastDate < o.lastDate)
				return 1;
			else if ((this.getItemName()).compareTo(o.getItemName()) > 0)
				return -1;
			else if ((this.getItemName()).compareTo(o.getItemName()) < 0)
				return 1;
		}
		return 0;
	}

}
