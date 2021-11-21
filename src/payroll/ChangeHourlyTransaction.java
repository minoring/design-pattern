package payroll;

import payroll.classification.HourlyClassification;
import payroll.classification.PaymentClassification;
import payroll.schedule.PaymentSchedule;
import payroll.schedule.WeeklySchedule;


public class ChangeHourlyTransaction extends ChangeClassificationTransaction {
	
	private double itsHourlyRate;
	
	public ChangeHourlyTransaction(int empid, double hourlyRate) {
		super(empid);
		itsHourlyRate = hourlyRate;
	}

	@Override
	public PaymentClassification getClassification() {
		return new HourlyClassification(itsHourlyRate);
	}

	@Override
	public PaymentSchedule getSchedule() {
		  return new WeeklySchedule();
	}

}

