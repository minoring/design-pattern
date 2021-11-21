package payroll.employee;

import payroll.AddEmployeeTransaction;
import payroll.classification.CommissionedClassification;
import payroll.classification.PaymentClassification;
import payroll.schedule.BiweeklySchedule;
import payroll.schedule.PaymentSchedule;

public class AddCommissionedEmployee extends AddEmployeeTransaction  {

	private double itsSalary;
	private double itsCommissionRate;
		  
	 public AddCommissionedEmployee(int empid, String name, String address, double salary, double commissionRate) {
		super(empid, name, address);
		itsSalary = salary;
		itsCommissionRate = commissionRate;
	}

		  
	@Override
	public PaymentClassification getClassification() {
		return new CommissionedClassification(itsSalary, itsCommissionRate);
	}

	@Override
	public PaymentSchedule getSchedule() {
		return new BiweeklySchedule();
	}

}
