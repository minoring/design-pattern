package payroll.dao;

import java.sql.SQLException;


//Abstract class DAO Factory
public abstract class DAOFactory {
	public static final int ORACLE = 1;
	public static final int SQLSERVER = 2;

	public static DAOFactory getDAOFactory(int whichFactory){
		switch(whichFactory){
		case ORACLE : 
			return new OracleDAOFactory();
		case SQLSERVER :
			return new SQLServerDAOFactory();
		default:
			return null;
		}
	}
	
	public abstract EmployeeDAO getEmployee() throws ClassNotFoundException, SQLException;
	
	public abstract UnionMemberDAO getUnionMember() throws ClassNotFoundException, SQLException;
}







