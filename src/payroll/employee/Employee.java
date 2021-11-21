package payroll.employee;

import java.util.Calendar;

import payroll.Affiliation;
import payroll.NoAffiliation;
import payroll.Paycheck;
import payroll.classification.PaymentClassification;
import payroll.method.PaymentMethod;
import payroll.schedule.PaymentSchedule;


public class Employee {
	
	private int itsEmpid;
	private String itsName;
	private String itsAddress;
	private PaymentClassification itsClassification;
	private PaymentSchedule itsSchedule;
	private PaymentMethod itsPaymentMethod;
	private Affiliation itsAffiliation;
	
	public Employee(int empid, String name, String address){
		itsEmpid = empid;
		itsName = name;
		itsAddress = address;
		itsAffiliation = new NoAffiliation();
	}
	
	public void setName(String name){
		this.itsName = name;
	}
	
	public void setAddress(String address){
		this.itsAddress = address;
	}
	
	public void setSchedule(PaymentSchedule schedule){
		this.itsSchedule = schedule;
	}
	
	public void setClassification(PaymentClassification classification){
		this.itsClassification = classification;
	}
	
	public void setMethod(PaymentMethod paymentMethod){
		this.itsPaymentMethod = paymentMethod;
	}
	
	public void setAffiliation(Affiliation affiliation){
		this.itsAffiliation = affiliation;
	}
	
	public Calendar getPayPeriodStartDate(Calendar payPeriodEndDate){
		return itsSchedule.getPayPeriodStartDate(payPeriodEndDate);
	}
	
	public int getEmpid(){
		return itsEmpid;
	}
	
	public String getName(){
		return itsName;
	}
	
	public String getAddress(){
		return itsAddress;
	}
	
	public PaymentMethod getMethod(){
		return itsPaymentMethod;
	}
	
	public PaymentClassification getClassification(){
		return itsClassification;
	}
	
	public PaymentSchedule getSchedule(){
		return itsSchedule;
	}
	
	public Affiliation getAffiliation(){
		return itsAffiliation;
	}

	public boolean isPayDate(Calendar payDate)  {  
		return itsSchedule.isPayDate(payDate);  
	}    
	
	public void payDay(Paycheck pc){
		//총 급여
		double grossPay = itsClassification.calculatePay(pc);
		//공제항목
		double deductions = itsAffiliation.calculateDeductions(pc);
		//지불할 금액
		double netPay = grossPay - deductions;
		
		pc.setGrossPay(grossPay);
		pc.setDeductions(deductions);
		pc.setNetPay(netPay);
		itsPaymentMethod.pay(pc);
	}


}
