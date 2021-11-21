package payroll.method;

import payroll.Paycheck;

public class MailMethod implements PaymentMethod {
	
	private String itsAddress;
	
	public MailMethod(String address){
		this.itsAddress = address;
	}
	
	public String getAddress(){
		return itsAddress;
	}
	
	
	@Override
	public void pay(Paycheck pc) {
		//Disposition : 기질,성향,배치,배열
		pc.setField("Disposition", "Mail");
	}

}
