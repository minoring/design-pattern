package payroll.method;

import payroll.Paycheck;

public class DirectMethod implements PaymentMethod{
	private String itsBank;
	private	String itsAccount;
	
	public DirectMethod(String bank, String account){
		itsBank=bank;
		itsAccount=account;
	}
	
	public String getBank(){
		return itsBank;
	}
	
	public String getAccount(){
		return itsAccount;
	}

	@Override
	public void pay(Paycheck pc) {
		pc.setField("Disposition", "Direct");  // ��� Ȯ���ϸ� pc.SetField(String name, String value)�� �Ǿ� ����.
		
	}
}
