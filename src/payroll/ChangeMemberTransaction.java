package payroll;

import payroll.dao.PayrollDatabase;
import payroll.employee.Employee;

public class ChangeMemberTransaction extends ChangeAffiliationTransaction  {

	private int itsMemberId;
	private double itsDues;
		  
	public ChangeMemberTransaction(int empid, int memberid, double dues) {
		super(empid);
		itsMemberId = memberid;
		itsDues = dues;
	}

	@Override
	public Affiliation getAffiliation() {
		return new UnionAffiliation(itsMemberId, itsDues);
	}

	@Override
	public void recordMembership(Employee e) {
		System.out.println("ChangeMemberTransaction - PayrollDatabase.addUnionMember");
		PayrollDatabase.addUnionMember(itsMemberId, e);		
	}

}
