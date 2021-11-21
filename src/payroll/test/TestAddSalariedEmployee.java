package payroll.test;

import junit.framework.TestCase;
import payroll.classification.SalariedClassification;
import payroll.dao.PayrollDatabase;
import payroll.employee.AddSalariedEmployee;
import payroll.employee.Employee;

public class TestAddSalariedEmployee extends TestCase {
	
	public void testAddSalariedEmployee(){
		
		int empid = 1;
		AddSalariedEmployee t = new AddSalariedEmployee(empid, "Bob", "Home", 1000.00);
		t.execute();
		
		Employee e = PayrollDatabase.getEmployee(empid);
		System.out.println("Name: "+e.getName());
	
		assertTrue("Bob".equals(e.getName()));
		assertTrue("Home".equals(e.getAddress()));
		//assertTrue(e.getClassification() instanceof SalariedClassification);
		
	}
}
