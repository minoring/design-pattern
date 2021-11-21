package payroll.method;

import payroll.Paycheck;

public class HoldMethod implements PaymentMethod {
	@Override
	public void pay(Paycheck pc) {
		//Disposition : 기질,성향,배치,배열
		pc.setField("Disposition", "Hold");
	}

}
