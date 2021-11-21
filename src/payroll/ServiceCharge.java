package payroll;

import java.util.Calendar;
import java.util.Date;

public class ServiceCharge {
	private Calendar itsDate;
	private double itsAmount;	
	
	
	public ServiceCharge(Calendar date, double amount){
		this.itsDate = date;
		this.itsAmount = amount;
	}
	
	public double getAmount(){
		return itsAmount;
	}
	public Calendar getDate(){
		return itsDate;
	}
}
