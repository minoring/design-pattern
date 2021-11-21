package payroll.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import payroll.employee.Employee;

public class UnionMemberDAOImpl implements UnionMemberDAO {
	
	private Connection conn;
	public UnionMemberDAOImpl(Connection conn){
		this.conn = conn;
	}
	
	@Override
	public boolean addUnionMember(int memberId, Employee emp) throws SQLException {
		String query =   "INSERT INTO dbo.UNION_MEMBER ( ? , ? ) ";
		try{
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberId);
			pstmt.setInt(2, emp.getEmpid());
			pstmt.executeUpdate();
			pstmt.close();
			return true;
		}catch(SQLException e){
			return false;
		}
	}

	@Override
	public Employee getUnionMember(int memberId) {
		Employee emp = null;
		String query =   " SELECT EMP.* "
						+" FROM dbo.EMPLOYEE AS EMP  " 
						+" 		INNER JOIN UNION_MEMBER AS UM"
						+" 	 	ON EMP.EMP_ID = UM.EMP_ID "
						+" WHERE EMP_ID = ?";
		
		try{
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberId);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				emp =  new Employee(rs.getInt("EMP_ID"), rs.getString("EMP_NAME"), rs.getString("EMP_ADDRESS"));
			}
			pstmt.close();
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return emp;
	}

	@Override
	public void removeUnionMember(int memberId) {
		String query =   " DELETE UNION_MEMBER "
						+" WHERE EMP_ID = ?";
		try{
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberId);
			ResultSet rs = pstmt.executeQuery();
			pstmt.close();
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}		
		
	}

	@Override
	public void removeAllMember() {
		String query = " DELETE UNION_MEMBER";
		try{
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			pstmt.close();
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

}
