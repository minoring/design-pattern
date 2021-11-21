package payroll.dao;

import java.sql.SQLException;
import java.util.List;
import payroll.employee.Employee;


public class PayrollDatabase {
	
	private static DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.SQLSERVER);

	/**
	 * 직원 반환
	 * @param empid
	 * @return
	 */
	public static Employee getEmployee(int empid) {
		try {
			return daoFactory.getEmployee().getEmployee(empid);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 직원 추가
	 * @param empid
	 * @param emp
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void addEmployee(Employee emp){
		try {
			daoFactory.getEmployee().addEmployee(emp);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * 직원 삭제
	 * @param empid
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void deleteEmployee(int empid) {
		try {
			daoFactory.getEmployee().deleteEmployee(empid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 전체 직원 반환
	 * @return
	 */
	public static List<Employee> getAllEmployees(){
		List<Employee> emps = null;
		try{
			emps = daoFactory.getEmployee().getAllEmployees();	
		}catch(Exception e){
			e.printStackTrace();
		}
		return emps;
	}
	
	/**
	 * 조합원 등록
	 * @param memberId
	 * @param e
	 */
	public static void addUnionMember(int memberId, Employee emp){
		try{
			daoFactory.getUnionMember().addUnionMember(memberId, emp);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 직원/조합원 정보 삭제
	 */
	public static void clear(){
		try{
			daoFactory.getEmployee().deleteAllEmployee();
			daoFactory.getUnionMember().removeAllMember();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 조합원 반환
	 * @param memberId
	 * @return
	 */
	public static Employee getUnionMember(int memberId){
		Employee emp = null;
		try{
			emp = daoFactory.getUnionMember().getUnionMember(memberId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return emp;
	}
	
	/**
	 * 조합원 삭제
	 * @param memberId
	 */
	public static void removeUnionMember(int memberId){
		try{
			daoFactory.getUnionMember().removeUnionMember(memberId);
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
	
	
	public static void getAllEmployeeIds(){
		
	}
}
