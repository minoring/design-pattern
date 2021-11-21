package payroll;

import java.util.Calendar;
import java.util.Date;


import payroll.classification.HourlyClassification;
import payroll.classification.PaymentClassification;
import payroll.dao.PayrollDatabase;
import payroll.employee.Employee;

public class TimeCardTransaction implements Transaction {
	private int itsEmpid;
	private Calendar itsDate;
	private double itsHours;
	
	
	
	
	public TimeCardTransaction(Calendar date,double hours, int empid){
		this.itsDate = date;
		this.itsHours = hours;
		this.itsEmpid = empid;
	}
	
	@Override
	public void execute() {
		// 직원을 받아온다
		Employee e = PayrollDatabase.getEmployee(itsEmpid);
		
		// 직원이 있을 경우
		if(e != null){
			// 분류를 가져온다.
			PaymentClassification pc = e.getClassification();
			
			// 시간제 직원일 경우
			if(pc instanceof HourlyClassification){
				HourlyClassification hc = (HourlyClassification)e.getClassification();
				// TimeCard를 추가한다.
				hc.addTimeCard(new TimeCard(itsDate, itsHours));
			}else{
				try{
					throw new Exception("시간제가 아닌 직원에게 Timecard를 추가하려고 함...");
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
				
		}else{
			try{
				throw new Exception("직원을 찾을 수 없어..");
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		
	}

}
