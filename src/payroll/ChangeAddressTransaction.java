package payroll;
import payroll.employee.Employee;


public class ChangeAddressTransaction extends ChangeEmployeeTransaction{
	String itsAddress;
	
	public ChangeAddressTransaction(int empid, String address){
		super(empid); 
		itsAddress=address;
	}
	public void change(Employee e){
		e.setAddress(itsAddress);
	}
}
