package payroll;

import payroll.employee.Employee;
import payroll.method.PaymentMethod;

public abstract class ChangeAffiliationTransaction extends ChangeEmployeeTransaction {
	
	private String itsAddress;
	
	public ChangeAffiliationTransaction(int empid) {
		super(empid);
	}
	
	public String getAddress(){
		return itsAddress;
	}
	
	// 하위 클래스에 구현
	public abstract Affiliation getAffiliation();
	// 하위 클래스에 구현
	public abstract void recordMembership(Employee e);
	
	public void change(Employee e){
		recordMembership(e);
		e.setAffiliation(getAffiliation());
	}

}
