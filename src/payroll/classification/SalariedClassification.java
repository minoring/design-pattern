package payroll.classification;

import payroll.Paycheck;

public class SalariedClassification implements PaymentClassification {
	
	public double itsSalary;
	public String itsCode;
	
	public SalariedClassification(double salary){
		this.itsSalary = salary;
		this.itsCode = "S";
	}

	public double getSalary(){
		return itsSalary;
	}
	
	@Override
	public double calculatePay(Paycheck pc) {
		return itsSalary;
	}

	@Override
	public String getCode() {
		return itsCode;
	}

}
