package payroll.test;

import payroll.ChangeHourlyTransaction;
import payroll.classification.HourlyClassification;
import payroll.classification.PaymentClassification;
import payroll.dao.PayrollDatabase;
import payroll.employee.AddCommissionedEmployee;
import payroll.employee.Employee;
import payroll.schedule.PaymentSchedule;
import payroll.schedule.WeeklySchedule;
import junit.framework.TestCase;

public class TestChangeHourlyTransaction extends TestCase {
	
	int empid = 3;
	
	public void testAddEmployee(){	
		
		AddCommissionedEmployee t = new AddCommissionedEmployee(empid, "Lance", "Home", 2500, 3.2);
		t.execute();
	}
	
	public void testChange(){
		ChangeHourlyTransaction cht = new ChangeHourlyTransaction(empid, 27.52);
		cht.execute();
		
		Employee e = PayrollDatabase.getEmployee(empid);
		assertTrue(e!=null);
		
		PaymentClassification pc = e.getClassification();
		HourlyClassification hc = (HourlyClassification) pc;
		
		assert (hc!=null):"false";
		
		System.out.println("pass");
		
		
		assertEquals(27.52, hc.getRate(),.001 );
		
		WeeklySchedule ws = (WeeklySchedule) e.getSchedule();
		
		assert(e.getSchedule() instanceof WeeklySchedule);
	
		
	}
}
