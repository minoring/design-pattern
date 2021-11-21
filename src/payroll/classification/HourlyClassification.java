package payroll.classification;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import payroll.Paycheck;
import payroll.TimeCard;

// 2011.12.03 추가
public class HourlyClassification implements PaymentClassification {

	private double itsRate;
	private String itsCode;
	Map<Calendar, TimeCard> itsTimeCards = new HashMap<Calendar, TimeCard>();
	
	public HourlyClassification(double hourlyRate){
		this.itsRate = hourlyRate;
		this.itsCode = "H";
	}
	
	public double getRate(){
		return itsRate;
	}
	
	public void addTimeCard(TimeCard tc){
		itsTimeCards.put(tc.getDate(), tc);
	}
	
	public TimeCard getTimeCard(Calendar date){
		return itsTimeCards.get(date);
	}
	
    private double calculatePayForTimeCard(TimeCard tc){      
    	double hours = tc.getHours();         
    	double overtime = Math.max(0.0, hours - 8.0);          
    	double straightTime = hours - overtime;           
    	return straightTime * itsRate + overtime * itsRate * 1.5;      
    }  
    
	
	@Override
	public double calculatePay(Paycheck pc) {
		double totalPay = 0;
		Calendar payPeriodEndDate = pc.getPayPeriodEndDate();
		Set<Calendar> keys= itsTimeCards.keySet(); 
		Iterator<Calendar> iter=keys.iterator();
		
		while(iter.hasNext()){
			Calendar key = (Calendar)iter.next(); 
			TimeCard tc=itsTimeCards.get(key);
			Calendar dt1, dt2; 
			dt1 = pc.getPayPeriodStartDate();
			dt2 = pc.getPayPeriodEndDate();
			
			//지불 시작일과 종료일 사에이 있는 TimeCard로 계산해
			if ( key.compareTo(dt1)>0 && key.compareTo(dt2)<0) {
				totalPay += calculatePayForTimeCard(tc); 
			}
		}
		
		return totalPay;
	}

	@Override
	public String getCode() {
		return itsCode;
	}

}
