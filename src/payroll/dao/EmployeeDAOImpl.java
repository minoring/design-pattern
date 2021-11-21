package payroll.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import payroll.classification.CommissionedClassification;
import payroll.classification.HourlyClassification;
import payroll.classification.PaymentClassification;
import payroll.classification.SalariedClassification;
import payroll.employee.Employee;

public class EmployeeDAOImpl implements EmployeeDAO {
	
	private Connection conn;
	
	public EmployeeDAOImpl(Connection conn){
		this.conn = conn;
	}
	
	@Override
	public boolean addEmployee(Employee emp) {
		
		PaymentClassification cf = emp.getClassification();
		
		double rate = 0.0;
		double commissionRate = 0.0;
		double salary = 0.0;
		
		if(cf instanceof HourlyClassification){
			rate = ((HourlyClassification)cf).getRate();
		}else if(cf instanceof SalariedClassification){
			salary = ((SalariedClassification)cf).getSalary();
		}else if(cf instanceof CommissionedClassification){
			commissionRate = ((CommissionedClassification)cf).getRate();
			salary = ((CommissionedClassification)cf).getSalary();
		}
		
		String query =   " INSERT INTO dbo.EMPLOYEE "
						+" ( EMP_ID, EMP_NAME, EMP_ADDRESS, CLASSIFICATION, RATE, COMMISSION_RATE, SALARY ) "
						+" VALUES "
						+" ( ?, ?, ?, ?, ?, ?, ? )";
		
		try{
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, emp.getEmpid());
			pstmt.setString(2, emp.getName());
			pstmt.setString(3, emp.getAddress());
			pstmt.setString(4, cf.getCode());
			pstmt.setDouble(5, rate);
			pstmt.setDouble(6, commissionRate);
			pstmt.setDouble(7, salary);
			
			pstmt.executeUpdate();
			pstmt.close();
			System.out.println("Add Employee");
			return true;
		}catch(SQLException e){
			return false;
		}
	}

	@Override
	public boolean updateEmployee(Employee emp) {
		String query =   "UPDATE dbo.EMPLOYEE "
						+"SET  EMP_NAME = ? "
						+"    ,EMP_ADDRESS = ?"
						+"WHERE EMP_ID = ? ";
		try{
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, emp.getName());
			pstmt.setString(2, emp.getAddress());
			pstmt.setInt(3, emp.getEmpid());
			
			pstmt.executeUpdate();
			pstmt.close();
			System.out.println("Update Employee");
			return true;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteEmployee(int empid) {
		String query =   "DELETE dbo.EMPLOYEE WHERE EMP_ID = ? ";
		try{
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, empid);
			pstmt.executeUpdate();
			pstmt.close();
			System.out.println("Delete Employee");
			return true;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Employee getEmployee(int empid)  {
		Employee emp = null;
		String query =  "SELECT * FROM dbo.EMPLOYEE WHERE EMP_ID = ?";
		
		try{
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, empid);
			ResultSet rs = pstmt.executeQuery();
			
			String cfCode = "";
			
			while(rs.next()){
				emp =  new Employee(rs.getInt("EMP_ID"), rs.getString("EMP_NAME"), rs.getString("EMP_ADDRESS"));
				cfCode = rs.getString("CLASSIFICATION");
				
				if(cfCode.equals("H")){
					emp.setClassification(new HourlyClassification(rs.getDouble("RATE")));
				}else if(cfCode.equals("C")){
					emp.setClassification(new CommissionedClassification(rs.getDouble("SALARY"), rs.getDouble("COMMISSION_RATE")));
				}else{
					emp.setClassification(new SalariedClassification(rs.getDouble("SALARY")));
				}
				
			}
			pstmt.close();
			rs.close();
			System.out.println("Get Employee");
		}catch(SQLException e){
			e.printStackTrace();
		}
		return emp;
	}

	@Override
	public List<Employee> getAllEmployees() throws SQLException {
		List<Employee> empList = new ArrayList<Employee>();
		String query =  "SELECT * FROM dbo.EMPLOYEE ORDER BY EMP_ID";
		
		try{
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				empList.add(new Employee(rs.getInt("EMP_ID"), rs.getString("EMP_NAME"), rs.getString("EMP_ADDRESS")));
			}
			pstmt.close();
			rs.close();
			System.out.println("Get All Employee");
		}catch(SQLException e){
			e.printStackTrace();
		}
		return empList;
	}

	@Override
	public boolean deleteAllEmployee() throws SQLException {
		String query =   "DELETE dbo.EMPLOYEE ";
		try{
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.executeUpdate();
			pstmt.close();
			System.out.println("Delete Employee");
			return true;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean addUnionMember(int memberId, Employee emp)
			throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Employee getUnionMember(int memberId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeUnionMember(int memberId) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	public String toString(){
		String str = "";
		List<Employee> emps;
		try {
			emps = getAllEmployees();
			Iterator<Employee> keys = emps.iterator();
			str += "*******************************************************************\n";
			while(keys.hasNext()){
				Employee emp =  keys.next();
				str += "# Employee ID: "+ emp.getEmpid() + ",  Name : "+ emp.getName() + ", Address : "+ emp.getAddress()+"\n";
			}
			str += "*******************************************************************\n";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}

}
