package col106.assignment5;

public class DateNode implements DateInterface {

	int day;
	int month;
	int year;
	int date;

	public DateNode(int day, int month , int year){
		this.day = day;
		this.month= month;
		this.year = year;
		String day1 = String.format("%02d", day);
		String month1 = String.format("%02d", month);
		String year1 = String.format("%04d", year);
		String date1 = "";
		date1 = date1.concat(year1).concat(month1).concat(day1);
		this.date = Integer.parseInt(date1);
	}

	public int getDay(){
		return this.day;
	}

	public int getMonth(){
		return this.month;
	}

	public int getYear(){
		return this.year;
	}
	
	public int getDate(){
		return this.date;
	}

}
