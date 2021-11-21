package payroll;

import payroll.classification.CommissionedClassification;
import payroll.classification.PaymentClassification;
import payroll.schedule.BiweeklySchedule;
import payroll.schedule.PaymentSchedule;

public class ChangeCommissionedTransaction extends ChangeClassificationTransaction{
	private double itsSalary;
	private double itsRate;
	
	public ChangeCommissionedTransaction(int empid, double salary, double rate) {
		super(empid);
		itsSalary = salary;
		itsRate = rate;
	}
	
	
	public PaymentSchedule getSchedule(){
		return new BiweeklySchedule();
	}

	@Override
	public PaymentClassification getClassification() {
		return new CommissionedClassification(itsSalary, itsRate);
	}

}
