package payroll.schedule;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class BiweeklySchedule implements PaymentSchedule {

	@SuppressWarnings("deprecation")
	public boolean isPayDate(Calendar theDate) {
		Calendar firstPayableFriday = new GregorianCalendar(2011, 12, 03);
		long tm = theDate.getTimeInMillis() - firstPayableFriday.getTimeInMillis();            
		int daysSinceFirstPayableFriday = (int)tm/(24*60*60*1000);
		return (daysSinceFirstPayableFriday % 14) == 0; //  two weeks.
		
	}

	@Override
	public Calendar getPayPeriodStartDate(Calendar payPeriodEndDate) {
		// Saturday, tow weeks ago.
		payPeriodEndDate.add(Calendar.DAY_OF_MONTH, -13);	
		return payPeriodEndDate;
	}


}
	