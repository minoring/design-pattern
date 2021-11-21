package payroll;

import java.util.Calendar;

import payroll.classification.CommissionedClassification;
import payroll.classification.PaymentClassification;
import payroll.dao.PayrollDatabase;
import payroll.employee.Employee;



public class SalesReceiptTransaction {
	int itsEmpid;
	Calendar itsSaleDate;
	double itsAmount;

	public SalesReceiptTransaction(Calendar saleDate, double amount, int empid){ 
		itsSaleDate=saleDate;
		itsAmount=amount;
		itsEmpid=empid;
	}
	
	public void execute(){
		Employee e=PayrollDatabase.getEmployee(itsEmpid);
		if(e!=null){
			PaymentClassification pc= e.getClassification();
			CommissionedClassification cc = (CommissionedClassification) pc;   
			if(pc!=null){
				cc.addReceipt(new SalesReceipt(itsSaleDate, itsAmount));
			}
			else{
				try{
					throw new Exception("Tried to add sales receipt to non=commissioned employee");
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		}
		else{
			try{
				throw new Exception("직원을 찾을 수 없어..");
			}catch(Exception e1){
				e1.printStackTrace();
			}
			
		}
	}
	
}
