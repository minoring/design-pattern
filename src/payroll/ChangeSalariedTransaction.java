package payroll;

import payroll.classification.PaymentClassification;
import payroll.classification.SalariedClassification;
import payroll.schedule.MonthlySchedule;
import payroll.schedule.PaymentSchedule;

public class ChangeSalariedTransaction extends ChangeClassificationTransaction {
	
	double itsSalary;

	public ChangeSalariedTransaction(int empid, double salary) {
		super(empid);
		this.itsSalary = salary;
	}

	@Override
	public PaymentClassification getClassification() {
		return new SalariedClassification(itsSalary);
	}

	@Override
	public PaymentSchedule getSchedule() {
		return new MonthlySchedule();
	}
	

}
