package payroll.schedule;

import java.util.Calendar;

public class WeeklySchedule implements PaymentSchedule {

	@Override
	public boolean isPayDate(Calendar date) {
		return (date.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY); 
	}

	@Override
	public Calendar getPayPeriodStartDate(Calendar payPeriodEndDate) {
		payPeriodEndDate.add(Calendar.DATE, -6);    //the previous Saturday. 
		return payPeriodEndDate;  
	}



}
