package payroll;

import payroll.classification.PaymentClassification;
import payroll.dao.PayrollDatabase;
import payroll.employee.Employee;
import payroll.method.HoldMethod;
import payroll.method.PaymentMethod;
import payroll.schedule.PaymentSchedule;

public abstract class AddEmployeeTransaction implements Transaction{
	
	private int itsEmpid;
	private String itsName;
	private String itsAddress;
	
	public AddEmployeeTransaction(int empid, String name, String address){
		itsEmpid = empid;
	    itsName = name;
	    itsAddress = address;
	}
	
	// 하위 클래스에서 구현
	public abstract PaymentClassification getClassification();
	// 하위 클래스에서 구현
	public abstract PaymentSchedule getSchedule();
	
	@Override
	public void execute() {
		PaymentClassification pc = getClassification();
		PaymentSchedule ps = getSchedule();
		PaymentMethod pm = new HoldMethod();
	
		Employee e = new Employee(itsEmpid, itsName, itsAddress);
		
		e.setClassification(pc);
		e.setSchedule(ps);
		e.setMethod(pm);

		PayrollDatabase.addEmployee(e);
		
	}

}




