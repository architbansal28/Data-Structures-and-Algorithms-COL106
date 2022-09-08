package col106.assignment5;

public class PurchaseNode implements PurchaseInterface{

	int numItemPurchased = 0;
	DateNode dateobj = null;
	int date = 19700801;

	public PurchaseNode(int numItems, int day, int month, int year){
		this.dateobj = new DateNode(day, month, year);
		numItemPurchased = numItems;
		this.date = dateobj.getDate();
	}

	public DateNode getDate(){
		return this.dateobj;
	}

	public int numItemsPurchased(){
		return this.numItemPurchased;
	}
	
	public int getIntDate(){
		return this.date;
	}

}
