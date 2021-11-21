package payroll.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleDAOFactory extends DAOFactory {
	
	public static Connection conn;
	public static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	public static final String DBURL = "jdbc:oracle:thin:@localhost:1521:topaz";
	public static final String USERID = "scott";
	public static final String PASSWORD = "tiger";

	public static Connection createConnection() throws ClassNotFoundException, SQLException{
		if(conn == null){
			try{
				Class.forName(DRIVER);
				conn = DriverManager.getConnection(DBURL, USERID , PASSWORD);
				if(conn!=null){
					System.out.println("DB Connection Success!");
				}else{
					System.out.println("DB Connection Fail!");
				}
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		return conn;
	}

	@Override
	public EmployeeDAO getEmployee() throws ClassNotFoundException, SQLException {
		return new EmployeeDAOImpl(createConnection());
	}

	@Override
	public UnionMemberDAO getUnionMember() throws ClassNotFoundException,SQLException {
		return new UnionMemberDAOImpl(createConnection());
	}
}







