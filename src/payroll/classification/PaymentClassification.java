package payroll.classification;

import payroll.Paycheck;


public interface PaymentClassification {
	public double calculatePay(Paycheck pc);
	public String getCode();
}


