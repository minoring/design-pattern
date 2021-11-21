package payroll;

import payroll.employee.Employee;
import payroll.method.PaymentMethod;

public abstract class ChangeMethodTransaction extends ChangeEmployeeTransaction {

	public ChangeMethodTransaction(int empid) {
		super(empid);
	}
	
	public abstract PaymentMethod getMethod();
	
	public void change(Employee e){
		e.setMethod(getMethod());
	}

}
