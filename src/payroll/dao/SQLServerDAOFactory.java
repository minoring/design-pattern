package payroll.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLServerDAOFactory extends DAOFactory {

	public static Connection conn;
	public static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public static final String DBURL = "jdbc:sqlserver://localhost:1433;databaseName=PAYROLL";
	public static final String USERID = "sa";
	public static final String PASSWORD = "2322";
	
	public static Connection createConnection(){
		if(conn == null){
			System.out.println("DB Connection ...");
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
	public UnionMemberDAO getUnionMember() throws ClassNotFoundException, SQLException {
		return new UnionMemberDAOImpl(createConnection());
	}
}





	