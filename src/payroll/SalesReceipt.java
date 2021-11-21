package payroll;

import java.util.Calendar;

public class SalesReceipt {
	
	private Calendar itsSaleDate;
	private double itsAmount;
		  
		  
	public SalesReceipt(Calendar saleDate, double amount){
		this.itsSaleDate = saleDate;
		this.itsAmount = amount;
	}
	
	public Calendar getSaleDate(){
		return itsSaleDate;
	}
	
	public double getAmount(){
		return itsAmount;
	}
	
	
}
