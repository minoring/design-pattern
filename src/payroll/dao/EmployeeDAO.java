package payroll.dao;

import java.sql.SQLException;
import java.util.List;

import payroll.employee.Employee;

public interface EmployeeDAO {
	// 특정 직원 반환
	public Employee getEmployee(int empid) throws SQLException;
	// 전체 직원 반환
	public List<Employee> getAllEmployees() throws SQLException; 
	// 직원 추가
	public boolean addEmployee(Employee emp) throws SQLException;
	// 직원 삭제
	public boolean deleteEmployee(int empid) throws SQLException;
	// 직원 전체 삭제
	public boolean deleteAllEmployee()throws SQLException;
	// 직원 정보 수정
	public boolean updateEmployee(Employee emp) throws SQLException;
	// 조합원으로 등록
	public boolean addUnionMember(int memberId, Employee emp) throws SQLException;
	// 특정 조합원 반환
	public Employee getUnionMember(int memberId) throws SQLException;
	// 조합원 삭제
	public boolean removeUnionMember(int memberId) throws SQLException;
}







