package payroll.dao;

import java.sql.SQLException;

import payroll.ChangeMemberTransaction;
import payroll.ChangeUnaffiliatedTransaction;
import payroll.DeleteEmployeeTransaction;
import payroll.employee.AddCommissionedEmployee;
import payroll.employee.AddSalariedEmployee;
import payroll.employee.Employee;

public class TestDAO {
	public static void main(String[] args) throws SQLException, ClassNotFoundException{
		
		DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.SQLSERVER);
		EmployeeDAO empDAO = daoFactory.getEmployee();
		
		empDAO.deleteAllEmployee();		
		
		DeleteEmployeeTransaction delEmp1 = new DeleteEmployeeTransaction(1);
		DeleteEmployeeTransaction delEmp2 = new DeleteEmployeeTransaction(2);
		
		delEmp1.execute();
		delEmp2.execute();
		
		AddSalariedEmployee salaryEmp = new AddSalariedEmployee(1, "김한주", "경기도 의왕시 포일동", 1000.00);
		salaryEmp.execute();
		
		AddCommissionedEmployee CommEmp = new AddCommissionedEmployee(2, "홍길동", "국민대학교", 800.00, 0.2);
		CommEmp.execute();
		
		System.out.print(empDAO.toString());
	}
}















