package payroll.method;

import payroll.Paycheck;

public interface PaymentMethod {
	public void pay(Paycheck pc);
}
