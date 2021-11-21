package payroll.classification;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import payroll.Paycheck;
import payroll.SalesReceipt;

public class CommissionedClassification implements PaymentClassification {

	private String itsCode;
	private double itsSalary;
	private double itsCommissionRate;
	Map<Calendar, SalesReceipt> itsReceipts = new HashMap<Calendar, SalesReceipt>();
	
	public CommissionedClassification(double salary, double commissionRate){
		this.itsSalary = salary;
		this.itsCommissionRate = commissionRate;
		this.itsCode = "C";
	}
	 
	@Override
	public double calculatePay(Paycheck pc) {
		double commission = 0.0;
		
		Set<Calendar> keys = itsReceipts.keySet();
		Iterator<Calendar> iter = keys.iterator();
		
		while(iter.hasNext()){
			Calendar key = (Calendar)iter.next();
			SalesReceipt receipt = itsReceipts.get(key);
			
			Calendar dt1, dt2;
			dt1 = pc.getPayPeriodStartDate();
			dt2 = pc.getPayPeriodEndDate();
			if(key.compareTo(dt1)>0 && key.compareTo(dt2)<0){
				commission += receipt.getAmount() * itsCommissionRate;
			}
		}

		return itsSalary + commission;
	}
	
	public double getSalary(){
		return itsSalary;
	}
	
	public double getRate(){
		return itsCommissionRate;
	}
	
	public SalesReceipt getReceipt(Calendar saleDate){
		return itsReceipts.get(saleDate);
	}
	
	public void addReceipt(SalesReceipt receipt){
		itsReceipts.put(receipt.getSaleDate(), receipt);
	}

	@Override
	public String getCode() {
		return itsCode;
	}

}
