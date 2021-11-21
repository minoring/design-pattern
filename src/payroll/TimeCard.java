package payroll;

import java.util.Calendar;
import java.util.Date;


public class TimeCard {
	Calendar itsDate;
	double itsHours;
	
	public TimeCard(Calendar date, double hours){
		this.itsDate = date;
		this.itsHours = hours;
	}
	
	public Calendar getDate(){
		return itsDate;
	}
	
	public double getHours(){
		return itsHours;
	}
}
