package payroll;

import java.util.Calendar;

import payroll.dao.PayrollDatabase;
import payroll.employee.Employee;


public class ServiceChargeTransaction implements Transaction {

	private int itsMemberId;
	private Calendar itsDate;
	private double itsCharge;
	
	public ServiceChargeTransaction(int memberId, Calendar date, double charge){
		this.itsMemberId = memberId;
		this.itsDate = date;
		this.itsCharge = charge;
	}
	
	
	@Override
	public void execute() {
		// 노조 멤버
		Employee e = PayrollDatabase.getUnionMember(itsMemberId);	
		
		Affiliation af = e.getAffiliation();
		
		// 직원의 분류가 노조 멤버와 같을 경우
		if(af instanceof UnionAffiliation){
			// Employee를 꺼내어 그 객체의 Affillation 을 UnionAffillation으로 다운캐스트
			// 이 후 그것에 ServiceCharge 를 더한다.			
			((UnionAffiliation) af).addServiceCharge(itsDate, itsCharge);
		}
	}

}
