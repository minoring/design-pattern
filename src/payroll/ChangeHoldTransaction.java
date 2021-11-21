package payroll;

import payroll.method.HoldMethod;
import payroll.method.PaymentMethod;

public class ChangeHoldTransaction extends ChangeMethodTransaction {

	public ChangeHoldTransaction(int empid) {
		super(empid);
	}
	
	public PaymentMethod getMethod(){
		return new HoldMethod();
	}

}
