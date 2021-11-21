package payroll.dao;

import java.sql.SQLException;
import payroll.employee.Employee;

public interface UnionMemberDAO {
	// 조합원 추가
	public boolean addUnionMember(int memberId, Employee emp)throws SQLException;
	// 조합원 반환
	public Employee getUnionMember(int memberId);
	// 조합원 삭제
	public void removeUnionMember(int memberId);
	// 조합원 전체 삭제
	public void removeAllMember();
}


