package payroll.test;

import payroll.classification.PaymentClassification;
import payroll.dao.PayrollDatabase;
import payroll.employee.AddCommissionedEmployee;
import payroll.employee.Employee;
import junit.framework.TestCase;

public class TestDeleteEmployee extends TestCase {
	
	public void testDeleteEmployee(){
		int empid = 3;
		AddCommissionedEmployee t = new AddCommissionedEmployee(empid, "Lance", "Home", 2500, 3.2);
		t.execute();
		
		Employee e = PayrollDatabase.getEmployee(empid);
		assert(e == null);
	}

}
