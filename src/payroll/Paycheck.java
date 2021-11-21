package payroll;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Paycheck {
	
	private Calendar itsPayPeriodStartDate;
	private Calendar itsPayPeriodEndDate;
	private double itsGrossPay;
	private double itsNetPay;
	private double itsDeductions;
	private Map<String, String> itsFields = new HashMap<String, String>();
	
	
	public Paycheck(Calendar payPeriodStartDate, Calendar payPeriodEndDate){
		this.itsPayPeriodStartDate = payPeriodStartDate;
		this.itsPayPeriodEndDate = payPeriodEndDate;
	}
	
	public void setGrossPay(double grossPay)
	{
		itsGrossPay = grossPay;
	}
	
	public void setNetPay(double netPay)
	{
		itsNetPay = netPay;
	}
	
	public void setDeductions(double deductions)
	{
		itsDeductions = deductions;
	}
	
	public void setField(String name, String value)
	{
		itsFields.put(name, value);
	}
	
	public String getField(String name)
	{
		return itsFields.get(name);
	}
	
	
	public double getGrossPay(){
		return itsGrossPay;
	}

	public double getDeductions(){
		return itsDeductions;
	}
	
	public double getNetPay(){
		return itsNetPay;
	}
	
	public Calendar getPayPeriodStartDate(){
		return itsPayPeriodStartDate;
	}
	
	
	public Calendar getPayPeriodEndDate() {
		return itsPayPeriodEndDate;
	}
	

}
