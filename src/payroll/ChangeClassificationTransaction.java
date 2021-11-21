package payroll;

import payroll.classification.PaymentClassification;
import payroll.employee.Employee;
import payroll.schedule.PaymentSchedule;

public abstract class ChangeClassificationTransaction extends ChangeEmployeeTransaction {

	public ChangeClassificationTransaction(int empid) {
		super(empid);
	}
	
	// 하위 클래스에서 구현
	public abstract PaymentClassification getClassification();
	// 하위 클래스에서 구현
	public abstract PaymentSchedule getSchedule();
	
	
	public void change(Employee e){
		e.setClassification(getClassification());
		e.setSchedule(getSchedule());
	}

}


