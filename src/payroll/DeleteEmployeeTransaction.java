package payroll;

import payroll.dao.PayrollDatabase;



public class DeleteEmployeeTransaction implements Transaction {

	private int itsEmpid;
	
	public DeleteEmployeeTransaction(int empid){
		this.itsEmpid = empid;
	}
	
	@Override
	public void execute() {
		PayrollDatabase.deleteEmployee(itsEmpid);
	}

}
