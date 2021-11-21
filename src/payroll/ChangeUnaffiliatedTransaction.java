package payroll;

import payroll.dao.PayrollDatabase;
import payroll.employee.Employee;


public class ChangeUnaffiliatedTransaction extends ChangeAffiliationTransaction {
	
	
	public ChangeUnaffiliatedTransaction(int empId){
		super(empId); 
	}
	
	public Affiliation getAffiliation(){
		return new NoAffiliation();
	}
	
	public void recordMembership(Employee e){
		Affiliation af= e.getAffiliation();
		UnionAffiliation uf=(UnionAffiliation) af;
		if(uf!=null){
			int memberId=uf.getMemberId();
			PayrollDatabase.removeUnionMember(memberId);
		}
	}
}
