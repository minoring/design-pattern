package payroll;

import payroll.method.DirectMethod;
import payroll.method.PaymentMethod;


public class ChangeDirectTransaction extends ChangeMethodTransaction{

	String itsBank;
	String itsAccount;
	
	public ChangeDirectTransaction(int empid, String bank, String account){
		super(empid);  
		itsBank=bank;
		itsAccount=account;
	}
	
	public PaymentMethod getMethod(){
		return new DirectMethod(itsBank, itsAccount);  //
	}
}
