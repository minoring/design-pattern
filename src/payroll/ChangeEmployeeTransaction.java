package payroll;

import payroll.dao.PayrollDatabase;
import payroll.employee.Employee;


public abstract class ChangeEmployeeTransaction implements Transaction {
	
	private int itsEmpId;

	public ChangeEmployeeTransaction(int empid){
		this.itsEmpId = empid;
	}
	
	@Override
	public void execute() {
		Employee e = PayrollDatabase.getEmployee(itsEmpId);
		if(!e.equals(null)){
			change(e);
		}
	}
	
	protected int getEmpId(){
		return itsEmpId;
	}
	// 하위 클래스에서 구현
	public abstract void change(Employee e);
}


