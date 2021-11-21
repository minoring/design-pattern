package payroll;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class UnionAffiliation implements Affiliation{
	private int itsMemberId;
	private double itsDues;	//회비
	Map<Calendar, ServiceCharge> itsServiceCharges = new HashMap<Calendar, ServiceCharge>();
	

	public UnionAffiliation(int memberId, double dues){
		System.out.println("UnionAffiliation");
		this.itsMemberId = memberId;
		this.itsDues = dues;
		
	}
	public void addServiceCharge(Calendar date, double amount){
		ServiceCharge sc = new ServiceCharge(date, amount);
		itsServiceCharges.put(date, sc);
	}
	
	public ServiceCharge getServiceCharge(Calendar date){
		return itsServiceCharges.get(date);
	}
	
	//회비
	public double getDues(){
		return itsDues;
	}
	public int getMemberId(){
		return itsMemberId;
	}
	
	// 공제 계산
	@Override
	public double calculateDeductions(Paycheck pc) {
		  double totalServiceCharge = 0;
		  double totalDues = 0;
		  
		  Set<Calendar> keys = itsServiceCharges.keySet();
		  Iterator<Calendar> iter = keys.iterator();
		  
		  while(iter.hasNext()){
			  Calendar key = (Calendar)iter.next();
			  ServiceCharge sc = itsServiceCharges.get(key);
			  Calendar dt1, dt2;
			  dt1 = pc.getPayPeriodStartDate();
			  dt2 = pc.getPayPeriodEndDate();
			  
			  if(key.compareTo(dt1) > 0 && key.compareTo(dt2) < 0){
				  totalServiceCharge += sc.getAmount();
			  }
		  }
		  
		  int fridays = numberOfFridaysInPayPeriod(pc.getPayPeriodStartDate(), pc.getPayPeriodEndDate());
		  totalDues = itsDues * fridays;
		  return totalDues + totalServiceCharge;

	}
	
	//금요일은 몇 번 있는가.
	private int numberOfFridaysInPayPeriod(Calendar payPeriodStart,
			Calendar payPeriodEnd) {
		int fridays = 0;
		
		for(Calendar date = payPeriodStart; date.equals(payPeriodEnd) || date.before(payPeriodEnd);date.add(Calendar.DAY_OF_MONTH, +1)){
			if(date.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
				fridays++;
		}
		return fridays;
	}

}
