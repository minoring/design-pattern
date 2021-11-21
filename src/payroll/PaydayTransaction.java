package payroll;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import payroll.dao.PayrollDatabase;
import payroll.employee.Employee;

public class PaydayTransaction implements Transaction {

	private Calendar itsPayDate;
	private Map<Integer, Paycheck> itsPaychecks = new HashMap<Integer,Paycheck>();
		  
	public PaydayTransaction(Calendar payDate){
		itsPayDate = payDate;
	}
	
	/**
	 * 전체 직원을 가져온다.
	 */
	@Override
	public void execute() {
		System.out.println("PaydayTransaction - execute");
		List<Employee> emps = PayrollDatabase.getAllEmployees();
		Iterator<Employee> keys = emps.iterator();
		
		while(keys.hasNext()){
			Employee emp = keys.next();
		  
		  // 지불일이 맞다면..
		  if(emp.isPayDate(itsPayDate)){
			  // 트랜젝션
			  Paycheck pc = new Paycheck(emp.getPayPeriodStartDate(itsPayDate),itsPayDate);
			  // 새 지불수표를 만들어.
			  itsPaychecks.put(emp.getEmpid(), pc);
			  // 지불일
			  emp.payDay(pc);
		  }
	  }	
	}
	public Paycheck getPaycheck(int empId){
		return itsPaychecks.get(empId);		
	}
	
	public int getPaycheckCount(){
		return itsPaychecks.size();
	}
}
