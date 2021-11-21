package payroll.schedule;

import java.util.Calendar;

public interface PaymentSchedule {
	
	public boolean isPayDate(Calendar date);
	
	public Calendar getPayPeriodStartDate(Calendar payPeriodEndDate);
	
	
}



