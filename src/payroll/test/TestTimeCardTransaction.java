package payroll.test;

import java.util.Calendar;

import payroll.TimeCardTransaction;
import payroll.dao.PayrollDatabase;
import payroll.employee.Employee;
import junit.framework.TestCase;

public class TestTimeCardTransaction extends TestCase {
	
	public void testTimeCardTransaction(){
		int empid = 2;
		
		Calendar date = Calendar.getInstance();
		date.set(2011, 12, 03);
		
		TimeCardTransaction tct = new TimeCardTransaction(date, 8.0, empid);
		tct.execute();
		
		
	}

}
