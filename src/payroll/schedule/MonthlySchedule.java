package payroll.schedule;

import java.util.Calendar;
import java.util.Date;

public class MonthlySchedule implements PaymentSchedule {

	@Override
	public boolean isPayDate(Calendar date) {
		// 월의 마지막날을 반환
		return isLastDayOfMonth(date);
	}
	
	public boolean isLastDayOfMonth(Calendar date){
		Date d = date.getTime();
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		int m1 = c.get(Calendar.DAY_OF_MONTH);
		c.add(Calendar.DAY_OF_MONTH, 1);
		int m2 = c.get(Calendar.DAY_OF_MONTH);
		return (m1 != m2);
		
		/*
		int lastDate = date.getActualMaximum(date.DATE);
		if(lastDate == date.get(date.DATE)){
			return true;
		}else{
			return false;
		}
		*/
	}
	
	@Override
	public Calendar getPayPeriodStartDate(Calendar payPeriodEndDate) {
		Date d = payPeriodEndDate.getTime();
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		int lastDayOfMonth = c.getTime().getDate();
		c.add(Calendar.DAY_OF_MONTH, -(lastDayOfMonth -1));
		return c;
		
		/*
        int lastDayOfMonth = payPeriodEndDate.get(Calendar.DAY_OF_MONTH); 
        
        payPeriodEndDate.add(Calendar.DAY_OF_MONTH,-(lastDayOfMonth - 1));    
        Calendar firstDayOfMonth = payPeriodEndDate;       
        return firstDayOfMonth;  
        */
	}

}



