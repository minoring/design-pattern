package payroll;

import payroll.method.MailMethod;
import payroll.method.PaymentMethod;

public class ChangeMailTransaction extends ChangeMethodTransaction  {
	
	private String itsAddress;
	
	public ChangeMailTransaction(int empid, String address) {
		super(empid);
		this.itsAddress = address;
	}
	
	public PaymentMethod getMethod(){
		return new MailMethod(itsAddress);
	}
	

}
