package payroll.test;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import payroll.*;
import payroll.classification.*;
import payroll.dao.PayrollDatabase;
import payroll.employee.*;
import payroll.method.*;
import payroll.schedule.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;


public class PayrollTest extends TestCase {

	private PayrollDatabase db = new PayrollDatabase();
	
	public PayrollTest(String name){
		super(name);
	}
	
	public void setUp(){
		PayrollDatabase.clear();
	}
	
	public void test1(){
		Calendar date = Calendar.getInstance();
		date.set(2011, 10, 5);
		
		System.out.println(date.get(date.MONTH));
		System.out.println(date.get(date.MONTH));
		System.out.println(date.get(date.MONTH));
		System.out.println(date.get(date.MONTH));
		System.out.println(date.get(date.MONTH));
		
	}
	
	public void testAddSalariedEmployee(){
		System.out.println("TestAddSalariedEmployee");
		int empId=1;
		AddSalariedEmployee t=new AddSalariedEmployee(empId, "Bob", "Home", 1000.00);
		t.execute();
		Employee e = PayrollDatabase.getEmployee(empId);
		assertTrue(e!=null);
		
		assertTrue (e.getName().equals("Bob"));
		
		PaymentClassification pc = e.getClassification();
		SalariedClassification sc = (SalariedClassification)pc;
		
		assertTrue (sc!=null);
		assertEquals( 1000.00, sc.getSalary(), .001);
		
		PaymentSchedule ps = e.getSchedule();
		MonthlySchedule ms = (MonthlySchedule)ps;
		
		assertTrue (ms!=null);
		PaymentMethod pm = e.getMethod();
		HoldMethod hm = (HoldMethod)pm;
		assertTrue (hm!=null);
		
		
	}
	public void testAddHourlyEmployee()
	{
	  System.out.println("TestAddHourlyEmployee");
	  int empId = 2;
	  AddHourlyEmployee t=new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
	  t.execute();
	  Employee e = PayrollDatabase.getEmployee(empId);
	  assertTrue (e!=null);
	  assertTrue( e.getName().equals("Bill"));
	  PaymentClassification pc = e.getClassification();
	  HourlyClassification hc = (HourlyClassification)pc;
	  assertTrue (hc!=null);
	  	  
	  assertEquals(15.25, hc.getRate(), .001);
	  PaymentSchedule ps = e.getSchedule();
	  WeeklySchedule ws = (WeeklySchedule)ps;
	  assertTrue (ws!=null);
	  PaymentMethod pm = e.getMethod();
	  HoldMethod hm = (HoldMethod)pm;
	  assertTrue (hm!=null);
	}

	
	public void testAddCommissionedEmployee()
		{
		  System.out.println("TestAddCommissionedEmployee");
		  int empId = 3;
		  AddCommissionedEmployee t=new AddCommissionedEmployee(empId, "Lance", "Home", 2500, 3.2);
		  t.execute();
		  Employee e = PayrollDatabase.getEmployee(empId);
		  assertTrue (e!=null);
		  assertTrue (e.getName().equals("Lance"));
		  PaymentClassification pc = e.getClassification();
		  CommissionedClassification cc = (CommissionedClassification)pc;
		  assertTrue(cc!=null);
		  assertEquals(2500, cc.getSalary(), .001);
		  PaymentSchedule ps = e.getSchedule();
		  BiweeklySchedule bws = (BiweeklySchedule)ps;
		  assertTrue(bws!=null);
		  PaymentMethod pm = e.getMethod();
		  HoldMethod hm = (HoldMethod)pm;
		  assertTrue(hm!=null);
	}
	
	public void testDeleteEmployee()
		{
		  System.out.println("TestDeleteEmployee");
		  int empId = 3;
		  AddCommissionedEmployee t=new AddCommissionedEmployee(empId, "Lance", "Home", 2500, 3.2);
		  t.execute();
		  {
		    Employee e = PayrollDatabase.getEmployee(empId);
		    assertTrue(e!=null);
		  }
		  DeleteEmployeeTransaction dt=new DeleteEmployeeTransaction(empId);
		  dt.execute();
		  {
		    Employee e = PayrollDatabase.getEmployee(empId);
		    assertTrue(e == null);
		  }
	}
	
	public void testTimeCardTransaction()
	{
	  System.out.println("TestTimeCardTransaction");
	  int empId = 2;
	  AddHourlyEmployee t=new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
	  t.execute();
	  Calendar date = Calendar.getInstance();
	  date.set(2001, 10, 31);
	  TimeCardTransaction tct=new TimeCardTransaction(date, 8.0, empId);
	  tct.execute();
	  Employee e = PayrollDatabase.getEmployee(empId);
	  assertTrue(e!=null);
	  PaymentClassification pc = e.getClassification();
	  HourlyClassification hc = (HourlyClassification)pc;
	  assertTrue(hc!=null);
	  TimeCard tc = hc.getTimeCard(date);
	  assertTrue(tc!=null);
	  assertEquals(8.0, tc.getHours());

	}
	
	public void testBadTimeCardTransaction()
	{
	  System.out.println("TestBadTimeCardTransaction");
	  int empId = 3;
	  AddCommissionedEmployee t=new AddCommissionedEmployee(empId, "Lance", "Home", 2500, 3.2);
	  t.execute();
	  Calendar date = Calendar.getInstance();
	  date.set(2001, 10, 31);
	  TimeCardTransaction tct=new TimeCardTransaction(date, 8.0, empId);
	  try {
	    tct.execute();
	    //assertTrue(false);
	  }
	  catch(Exception s) {
	    System.out.println("Caught: " + s);
	  }
	}

	public void testSalesReceiptTransaction()
	{
	  System.out.println("TestSalesReceiptTransaction");
	  int empId = 3;
	  AddCommissionedEmployee t=new AddCommissionedEmployee(empId, "Lance", "Home", 2500, 3.2);
	  t.execute();
	  Calendar saleDate = Calendar.getInstance();;
	  saleDate.set(2001, 11, 12); // Monday
	  SalesReceiptTransaction srt=new SalesReceiptTransaction(saleDate, 25000, empId);
	  srt.execute();
	  Employee e = PayrollDatabase.getEmployee(empId);
	  assertTrue(e!=null);
	  PaymentClassification pc = e.getClassification();
	  CommissionedClassification cc = (CommissionedClassification)pc;
	  assertTrue(cc!=null);
	  SalesReceipt receipt = cc.getReceipt(saleDate);
	  assertTrue(receipt!=null);
	  assertEquals(25000, receipt.getAmount(), .001);
	}
		
	public void testBadSalesReceiptTransaction()
	{
	  System.out.println("TestBadSalesReceiptTransaction");
	  int empId = 2;
	  AddHourlyEmployee t=new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
	  t.execute();
	  Calendar saleDate = Calendar.getInstance();;
	  saleDate.set(2001, 11, 12);
	  
	  SalesReceiptTransaction tct=new SalesReceiptTransaction(saleDate, 25000, empId);
	  try {
	    tct.execute();
	    assertTrue(false);
	  }
	  catch(Exception e) {
	    System.out.println("Caught: " + e);
	  }
	}

	public void testAddServiceCharge()
	{
	  System.out.println("TestAddServiceCharge");
	  int empId = 2;
	  int memberId = 86; // Maxwell Smart
	  AddHourlyEmployee t=new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
	  t.execute();
	  Employee e = PayrollDatabase.getEmployee(empId);
	  assertTrue(e != null);
	  UnionAffiliation af = new UnionAffiliation(memberId, 12.5);
	  e.setAffiliation(af);
	  PayrollDatabase.addUnionMember(memberId, e);
	  Calendar serviceChargeDate = Calendar.getInstance();
	  serviceChargeDate.set(2001, 11, 01);
	  ServiceChargeTransaction sct=new ServiceChargeTransaction(memberId, serviceChargeDate, 12.95);
	  sct.execute();
	  ServiceCharge sc = af.getServiceCharge(serviceChargeDate);
	  assertTrue(sc!=null);
	  assertEquals(12.95, sc.getAmount(), .001);
	}

	public void testChangeNameTransaction()
	{
	  System.out.println("TestChangeNameTransaction");
	  int empId = 2;
	  AddHourlyEmployee t=new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
	  t.execute();
	  ChangeNameTransaction cnt=new ChangeNameTransaction(empId, "Bob");
	  cnt.execute();
	  Employee e = PayrollDatabase.getEmployee(empId);
	  assertTrue(e!=null);
	  assertTrue(e.getName().equals("Bob"));
	}

	public void testChangeAddressTransaction()
	{
	  System.out.println("TestChangeAddressTransaction");
	  int empId = 2;
	  AddHourlyEmployee t=new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
	  t.execute();
	  ChangeAddressTransaction cnt=new ChangeAddressTransaction(empId, "PO Box 7575");
	  cnt.execute();
	  Employee e = PayrollDatabase.getEmployee(empId);
	  assertTrue(e!=null);
	  assertTrue(e.getAddress().equals("PO Box 7575"));
	}

	public void testChangeHourlyTransaction()
	{
	  System.out.println("TestChangeHourlyTransaction");
	  int empId = 3;
	  AddCommissionedEmployee t=new AddCommissionedEmployee(empId, "Lance", "Home", 2500, 3.2);
	  t.execute();
	  ChangeHourlyTransaction cht=new ChangeHourlyTransaction(empId, 27.52);
	  cht.execute();
	  Employee e = PayrollDatabase.getEmployee(empId);
	  assertTrue(e!=null);
	  PaymentClassification pc = e.getClassification();
	  assertTrue(pc!=null);
	  HourlyClassification hc = (HourlyClassification)pc;
	  assertTrue(hc!=null);
	  assertEquals(27.52, hc.getRate(), .001);
	  PaymentSchedule ps = e.getSchedule();
	  WeeklySchedule ws = (WeeklySchedule)ps;
	  assertTrue(ws!=null);
	}

	public void testChangeSalariedTransaction()
	{
	  System.out.println("TestChangeSalariedTransaction");
	  int empId = 3;
	  AddCommissionedEmployee t=new AddCommissionedEmployee(empId, "Lance", "Home", 2500, 3.2);
	  t.execute();
	  ChangeSalariedTransaction cht=new ChangeSalariedTransaction(empId, 25000);
	  cht.execute();
	  Employee e = PayrollDatabase.getEmployee(empId);
	  assertTrue(e!=null);
	  PaymentClassification pc = e.getClassification();
	  assertTrue(pc!=null);
	  SalariedClassification sc = (SalariedClassification)pc;
	  assertTrue(sc!=null);
	  assertEquals(25000, sc.getSalary(), .001);
	  PaymentSchedule ps = e.getSchedule();
	  MonthlySchedule ms = (MonthlySchedule)ps;
	  assertTrue(ms!=null);
	}

	public void testChangeCommissionedTransaction()
	{
	  System.out.println("TestChangeCommissionedTransaction");
	  int empId = 2;
	  AddHourlyEmployee t=new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
	  t.execute();
	  ChangeCommissionedTransaction cht=new ChangeCommissionedTransaction(empId, 25000, 4.5);
	  cht.execute();
	  Employee e = PayrollDatabase.getEmployee(empId);
	  assertTrue(e!=null);
	  PaymentClassification pc = e.getClassification();
	  assertTrue(pc!=null);
	  CommissionedClassification cc = (CommissionedClassification)pc;
	  assertTrue(cc!=null);
	  assertEquals(25000, cc.getSalary(), .001);
	  assertEquals(4.5, cc.getRate(), .001);
	  PaymentSchedule ps = e.getSchedule();
	  BiweeklySchedule bws = (BiweeklySchedule)ps;
	  assertTrue(bws!=null);
	}

	public void testChangeMailTransaction()
	{
	  System.out.println("TestChangeMailTransaction");
	  int empId = 2;
	  AddHourlyEmployee t=new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
	  t.execute();
	  ChangeMailTransaction cmt=new ChangeMailTransaction(empId, "4080 El Cerrito Road");
	  cmt.execute();
	  Employee e = PayrollDatabase.getEmployee(empId);
	  assertTrue(e!=null);
	  PaymentMethod pm = e.getMethod();
	  assertTrue(pm!=null);
	  MailMethod mm = (MailMethod)pm;
	  assertTrue(mm!=null);
	  assertTrue(mm.getAddress().equals("4080 El Cerrito Road"));
	}

	public void testChangeDirectTransaction()
	{
	  System.out.println("TestChangeDirectTransaction");
	  int empId = 2;
	  AddHourlyEmployee t=new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
	  t.execute();
	  ChangeDirectTransaction cdt=new ChangeDirectTransaction(empId, "FirstNational", "1058209");
	  cdt.execute();
	  Employee e = PayrollDatabase.getEmployee(empId);
	  assertTrue(e!=null);
	  PaymentMethod pm = e.getMethod();
	  assertTrue(pm!=null);
	  DirectMethod dm = (DirectMethod)pm;
	  assertTrue(dm!=null);
	  assertTrue(dm.getBank().equals("FirstNational"));
	  assertTrue(dm.getAccount().equals("1058209"));
	}

	public void testChangeHoldTransaction()
	{
	  System.out.println("TestChangeHoldTransaction");
	  int empId = 2;
	  AddHourlyEmployee t=new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
	  t.execute();
	  ChangeDirectTransaction cdt=new ChangeDirectTransaction(empId, "FirstNational", "1058209");
	  cdt.execute();
	  ChangeHoldTransaction cht=new ChangeHoldTransaction(empId);
	  cht.execute();
	  Employee e = PayrollDatabase.getEmployee(empId);
	  assertTrue(e!=null);
	  PaymentMethod pm = e.getMethod();
	  assertTrue(pm!=null);
	  HoldMethod hm = (HoldMethod)pm;
	  assertTrue(hm!=null);
	}

	public void testChangeMemberTransaction()
	{
	  System.out.println("TestChangeMemberTransaction");
	  int empId = 2;
	  int memberId = 7734;
	  AddHourlyEmployee t=new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
	  t.execute();
	  
	  ChangeMemberTransaction cmt=new ChangeMemberTransaction(empId, memberId, 99.42);
	  cmt.execute();
	  
	  Employee e = PayrollDatabase.getEmployee(empId);
	  assertTrue(e!=null);
	  
	  Affiliation af = e.getAffiliation();
	  assertTrue(af!=null);
	  
	  UnionAffiliation uf = (UnionAffiliation)af;
	  assertTrue(uf!=null);
	  assertEquals(99.42, uf.getDues(), .001);
	  
	  Employee member = PayrollDatabase.getUnionMember(memberId);
	  
	  assertTrue(member != null);
	  assertTrue(e == member);
	}
	
	public void testChangeUnaffiliatedTransaction()
	{
	  System.out.println("TestChangeUnaffiliatedTransaction");
	  int empId = 2;
	  int memberId = 7734;
	  
	  // 시간제 직원추가
	  AddHourlyEmployee t=new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
	  t.execute();
	  
	  // 조합원으로 변경
	  ChangeMemberTransaction cmt=new ChangeMemberTransaction(empId, memberId, 99.42);
	  cmt.execute();
	  
	  // 조합원에서 해제
	  // NoAffiliation 로 변경 (소속이 없는 직원)
	  ChangeUnaffiliatedTransaction cut=new ChangeUnaffiliatedTransaction(empId);
	  cut.execute();
	  
	  // 직원 가져옴
	  Employee e = PayrollDatabase.getEmployee(empId);
	  assertTrue(e!=null);
	  
	  // 분류 가져옴
	  Affiliation af = e.getAffiliation();
	  assertTrue(af!=null);
	  assertTrue(af instanceof NoAffiliation);
	  
	  // 분류 가져옴.
	  NoAffiliation nf = (NoAffiliation)af;
	  assertTrue(nf!=null);
	  
	  Employee member = PayrollDatabase.getUnionMember(memberId);
	  assertTrue(member == null);	// 조합원이 아니기 때문에 null 

	}

	public void validatePaycheck(PaydayTransaction pt, int empid, Calendar payDate, double pay)
	{
		Paycheck pc = pt.getPaycheck(empid);
		assert(pc!=null);
		//System.out.println(pc);
		
		assertEquals(pc.getPayPeriodEndDate(), payDate);
		assertEquals(pay, pc.getGrossPay(), .001);
		assertEquals("Hold", pc.getField("Disposition"));
		assertEquals(0.0, pc.getDeductions(), .001);
		assertEquals(pay, pc.getNetPay(), .001);
	}
	
	public void testPaySingleSalariedEmployee()
	{
	  System.out.println("TestPaySingleSalariedEmployee");
	  int empId = 1;
	  
	  // 봉급제 직원 추가
	  AddSalariedEmployee t=new AddSalariedEmployee(empId, "Bob", "Home", 1000.00);
	  t.execute();
	  
	  Calendar payDate = Calendar.getInstance();
	  payDate.set(2001,11-1,30);
	  
	  PaydayTransaction pt=new PaydayTransaction(payDate);
	  pt.execute();	// e.payday 에서 계산
	  
	  validatePaycheck(pt, empId, payDate, 1000.00);
	}

	public void testPayMultipleSalariedEmployees()
	{
	  System.out.println("TestPayMultipleSalariedEmployees");
	  AddSalariedEmployee t1=new AddSalariedEmployee(1, "Bob", "Home", 1000.00);
	  AddSalariedEmployee t2=new AddSalariedEmployee(2, "Bill", "Home", 2000.00);
	  AddSalariedEmployee t3=new AddSalariedEmployee(3, "Barry", "Home", 3000.00);
	  t1.execute();
	  t2.execute();
	  t3.execute();
	  
	  Calendar payDate = Calendar.getInstance();
	  payDate.set(2001,11-1,30);
	  
	  PaydayTransaction pt = new PaydayTransaction(payDate);
	  pt.execute();

	  assertEquals(3, pt.getPaycheckCount());
	  validatePaycheck(pt, 1, payDate, 1000.00);
	  validatePaycheck(pt, 2, payDate, 2000.00);
	  validatePaycheck(pt, 3, payDate, 3000.00);
	}
	
	public void testPaySingleHourlyEmployeeNoTimeCards()
	{
	  System.out.println("TestPaySingleHourlyEmployeeNoTimeCards");
	  int empId = 2;
	  AddHourlyEmployee t=new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
	  t.execute();
	  Calendar payDate = Calendar.getInstance();
	  payDate.set(2001,11-1,9); // Friday
	  PaydayTransaction pt=new PaydayTransaction(payDate);
	  pt.execute();
	  validatePaycheck(pt, empId, payDate, 0.0);
	}

	/*
	public void testPaySingleHourlyEmployeeOneTimeCard()
	{
	  System.out.println("TestPaySingleHourlyEmployeeOneTimeCard");
	  int empId = 2;
	  AddHourlyEmployee t=new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
	  t.execute();
	  Calendar payDate = Calendar.getInstance();
	  payDate.set(2001,11-1,9); // Friday

	  TimeCardTransaction tc=new TimeCardTransaction(payDate, 2.0, empId);
	  tc.execute();
	  PaydayTransaction pt=new PaydayTransaction(payDate);
	  pt.execute();
	  validatePaycheck(pt, empId, payDate, 30.5);
	}

	public void testPaySingleHourlyEmployeeOvertimeOneTimeCard()
	{
	  System.out.println("TestPaySingleHourlyEmployeeOvertimeOneTimeCard");
	  int empId = 2;
	  AddHourlyEmployee t=new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
	  t.execute();
	  Calendar payDate = Calendar.getInstance();
	  payDate.set(2001,11-1,9); // Friday

	  TimeCardTransaction tc=new TimeCardTransaction(payDate, 9.0, empId);
	  tc.execute();
	  PaydayTransaction pt=new PaydayTransaction(payDate);
	  pt.execute();
	  validatePaycheck(pt, empId, payDate, (8 + 1.5) * 15.25);
	}

	public void testPaySingleHourlyEmployeeOnWrongDate()
	{
	  System.out.println("TestPaySingleHourlyEmployeeOnWrongDate");
	  int empId = 2;
	  AddHourlyEmployee t=new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
	  t.execute();
	  Calendar payDate = Calendar.getInstance();
	  payDate.set(2001,11-1,8); // Thursday

	  TimeCardTransaction tc=new TimeCardTransaction(payDate, 9.0, empId);
	  tc.execute();
	  PaydayTransaction pt=new PaydayTransaction(payDate);
	  pt.execute();

	  
	  Paycheck pc = pt.getPaycheck(empId);
	  //assertTrue(pc == 0);
	}

	public void testPaySingleHourlyEmployeeTwoTimeCards()
	{
	  System.out.println("TestPaySingleHourlyEmployeeTwoTimeCards");
	  int empId = 2;
	  AddHourlyEmployee t=new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
	  t.execute();
	  Calendar payDate = Calendar.getInstance();
	  payDate.set(2001,11-1,9); // Friday

	  TimeCardTransaction tc=new TimeCardTransaction(payDate, 2.0, empId);
	  tc.execute();
	  
	  Calendar date = Calendar.getInstance();
	  date.set(2001,11-1,8);
	  TimeCardTransaction tc2= new TimeCardTransaction(date, 5.0, empId);
	  tc2.execute();
	  
	  PaydayTransaction pt= new PaydayTransaction(payDate);
	  pt.execute();
	
	  validatePaycheck(pt, empId, payDate, 7*15.25);
	}

	void TestPaySingleHourlyEmployeeWithTimeCardsSpanningTwoPayPeriods()
	{
	  System.out.println("TestPaySingleHourlyEmployeeWithTimeCardsSpanningTwoPayPeriods");
	  int empId = 2;
	  AddHourlyEmployee t=new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
	  t.execute();
	  Calendar payDate = Calendar.getInstance();
	  payDate.set(2001,11-1,9); // Friday
	  Calendar dateInPreviousPayPeriod = Calendar.getInstance();
	  dateInPreviousPayPeriod.set(2001, 11,2);

	  TimeCardTransaction tc=new TimeCardTransaction(payDate, 2.0, empId);
	  tc.execute();
	  TimeCardTransaction tc2=new TimeCardTransaction(dateInPreviousPayPeriod, 5.0, empId);
	  tc2.execute();
	  PaydayTransaction pt=new PaydayTransaction(payDate);
	  pt.execute();
	  validatePaycheck(pt, empId, payDate, 2*15.25);
	}

	void TestPaySingleCommissionedEmployeeNoSalesReceipts()
	{
	  System.out.println("TestPaySingleCommissionedEmployeeNoSalesReceipts");
	  int empId = 3;
	  AddCommissionedEmployee t=new AddCommissionedEmployee(empId, "Lance", "Home", 2500, 3.2);
	  t.execute();
	  Calendar payDate = Calendar.getInstance();
	  payDate.set(2001,11-1,9); // Friday
	  PaydayTransaction pt=new PaydayTransaction(payDate);
	  pt.execute();
	  validatePaycheck(pt, empId, payDate, 2500.00);
	}

	public void testPaySingleCommissionedEmployeeOneSalesReceipt()
	{
	  System.out.println("TestPaySingleCOmmissionedEmployeeOneSalesReciept");
	  int empId = 3;
	  AddCommissionedEmployee t=new AddCommissionedEmployee(empId, "Lance", "Home", 2500, .032);
	  t.execute();
	  Calendar payDate = Calendar.getInstance();
	  payDate.set(2001,11-1,9); // Friday
	  SalesReceiptTransaction srt=new SalesReceiptTransaction(payDate, 13000.00, empId);
	  srt.execute();
	  PaydayTransaction pt=new PaydayTransaction(payDate);
	  pt.execute();
	  
	  
	  validatePaycheck(pt, empId, payDate, 2500.00 + .032 * 13000);
	}

	public void testPaySingleCommissionedEmployeeTwoSalesReceipts()
	{
	  System.out.println("TestPaySingleCommissionedEmployeeTwoSalesReceipts");
	  int empId = 3;
	  AddCommissionedEmployee t=new AddCommissionedEmployee(empId, "Lance", "Home", 2500, .032);
	  t.execute();
	  Calendar payDate = Calendar.getInstance();
	  payDate.set(2001,11-1,9); // Biweekly Friday
	  SalesReceiptTransaction srt=new SalesReceiptTransaction(payDate, 13000.00, empId);
	  srt.execute();
	  Calendar date = Calendar.getInstance();
	  date.set(2001,11-1,8); 
	  SalesReceiptTransaction srt2=new SalesReceiptTransaction(date, 24000, empId);
	  srt2.execute();
	  PaydayTransaction pt=new PaydayTransaction(payDate);
	  pt.execute();
	  validatePaycheck(pt, empId, payDate, 2500.00 + .032 * 13000 + .032 * 24000);
	}

	public void testPaySingleCommissionedEmployeeWrongDate()
	{
	  System.out.println("TestPaySingleCommissionedEmployeeWrongDate");
	  int empId = 3;
	  AddCommissionedEmployee t=new AddCommissionedEmployee(empId, "Lance", "Home", 2500, .032);
	  t.execute();
	  Calendar payDate = Calendar.getInstance();
	  payDate.set(2001,11-1,16); // Wrong Friday
	  SalesReceiptTransaction srt = new SalesReceiptTransaction(payDate, 13000.00, empId);
	  srt.execute();
	  Calendar date = Calendar.getInstance();
	  date.set(2001,11-1,15);
	  SalesReceiptTransaction srt2=new SalesReceiptTransaction(date, 24000, empId);
	  srt2.execute();
	  PaydayTransaction pt=new PaydayTransaction(payDate);
	  pt.execute();

	  Paycheck pc = pt.getPaycheck(empId);
	  //assertTrue(pc == 0);
	  assertTrue(pc == null);
	}

	public void testPaySingleCommissionedEmployeeSpanMultiplePayPeriods()
	{
	  System.out.println("TestPaySingleCommissionedEmployeeSpanMultiplePayPeriods");
	  int empId = 3;
	  AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Lance", "Home", 2500, .032);
	  t.execute();
	  Calendar earlyDate = Calendar.getInstance();
	  earlyDate.set(2001,11-1,9); // Previous pay period
	  Calendar payDate = Calendar.getInstance();
	  payDate.set(2001,11-1,23); // Biweekly Friday
	  Calendar lateDate = Calendar.getInstance();
	  lateDate.set(2001,12,7); // Next pay period.
	  SalesReceiptTransaction srt = new SalesReceiptTransaction(payDate, 13000.00, empId);
	  srt.execute();
	  SalesReceiptTransaction srt2 = new SalesReceiptTransaction(earlyDate, 24000, empId);
	  srt2.execute();
	  SalesReceiptTransaction srt3 = new SalesReceiptTransaction(lateDate, 15000, empId);
	  srt3.execute();
	  PaydayTransaction pt=new PaydayTransaction(payDate);
	  pt.execute();
	  validatePaycheck(pt, empId, payDate, 2500.00 + .032 * 13000);
	}

	public void testSalariedUnionMemberDues()
	{
	  System.out.println("TestSalariedUnionMemberDues");
	  int empId = 1;
	  AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", 1000.00);
	  t.execute();
	  int memberId = 7734;
	  ChangeMemberTransaction cmt=new ChangeMemberTransaction(empId, memberId, 9.42);
	  cmt.execute();
	  Calendar payDate = Calendar.getInstance();
	  payDate.set(2001,11-1,30);
	  int fridays = 5; // Fridays in Nov, 2001.
	  PaydayTransaction pt=new PaydayTransaction(payDate);
	  pt.execute();
	  Paycheck pc = pt.getPaycheck(empId);
	  assert(pc != null);
	  assertTrue(pc.getPayPeriodEndDate() == payDate);
	  assertEquals(1000.00, pc.getGrossPay(), .001);
	  assertTrue(pc.getField("Disposition").equals("Hold"));
	  assertEquals(fridays*9.42, pc.getDeductions(), .001);
	  assertEquals(1000.0 - (fridays * 9.42), pc.getNetPay(), .001);
	}

	public void testHourlyUnionMemberDues()
	{
	  System.out.println("TestHourlyUnionMemberDues");
	  int empId = 1;
	  AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.24);
	  t.execute();
	  int memberId = 7734;
	  ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId, memberId, 9.42);
	  cmt.execute();
	  Calendar payDate = Calendar.getInstance();
	  payDate.set(2001,11-1,9);
	  TimeCardTransaction tct=new TimeCardTransaction(payDate, 8.0, empId);
	  tct.execute();
	  PaydayTransaction pt = new PaydayTransaction(payDate);
	  pt.execute();
	  Paycheck pc = pt.getPaycheck(empId);
	  assertTrue(pc != null);
	  assertTrue(pc.getPayPeriodEndDate() == payDate);
	  assertEquals(8*15.24, pc.getGrossPay(), .001);
	  assertTrue(pc.getField("Disposition").equals("Hold"));
	  assertEquals(9.42, pc.getDeductions(), .001);
	  assertEquals((8*15.24)-9.42, pc.getNetPay(), .001);
	}

	public void testCommissionedUnionMemberDues()
	{
	  System.out.println("TestCommissionedUnionMemberDues");
	  int empId = 3;
	  AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Lance", "Home", 2500, .032);
	  t.execute();
	  int memberId = 7734;
	  ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId, memberId, 9.42);
	  cmt.execute();
	  Calendar payDate = Calendar.getInstance();
	  payDate.set(2001, 11, 9);
	  PaydayTransaction pt = new PaydayTransaction(payDate);
	  pt.execute();
	  Paycheck pc = pt.getPaycheck(empId);
	  assertTrue(pc != null);
	  assertTrue(pc.getPayPeriodEndDate() == payDate);
	  assertEquals(2500.00, pc.getGrossPay(), .001);
	  assertTrue(pc.getField("Disposition").equals("Hold"));
	  assertEquals(2*9.42, pc.getDeductions(), .001);
	  assertEquals(2500.0 - (2 * 9.42), pc.getNetPay(), .001);
	}

	public void testHourlyUnionMemberServiceCharge()
	{
	  System.out.println("TestHourlyUnionMemberServiceCharge");
	  int empId = 1;
	  AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.24);
	  t.execute();
	  int memberId = 7734;
	  ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId, memberId, 9.42);
	  cmt.execute();
	  Calendar payDate = Calendar.getInstance();
	  payDate.set(2001,11-1,9);
	  ServiceChargeTransaction sct=new ServiceChargeTransaction(memberId, payDate, 19.42);
	  sct.execute();
	  TimeCardTransaction tct = new TimeCardTransaction(payDate, 8.0, empId);
	  tct.execute();
	  PaydayTransaction pt=new PaydayTransaction(payDate);
	  pt.execute();
	  Paycheck pc = pt.getPaycheck(empId);
	  assertTrue(pc != null);
	  assertTrue(pc.getPayPeriodEndDate() == payDate);
	  assertEquals(8*15.24, pc.getGrossPay(), .001);
	  assertTrue(pc.getField("Disposition").equals("Hold"));
	  assertEquals(9.42 + 19.42, pc.getDeductions(), .001);
	  assertEquals((8*15.24)-(9.42 + 19.42), pc.getNetPay(), .001);
	}

	public void testServiceChargesSpanningMultiplePayPeriods()
	{
	  System.out.println("TestServiceChargesSpanningMultiplePayPeriods");
	  int empId = 1;
	  AddHourlyEmployee t=new AddHourlyEmployee(empId, "Bill", "Home", 15.24);
	  t.execute();
	  
	  int memberId = 7734;
	  
	  ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId, memberId, 9.42);
	  cmt.execute();
	  
	  Calendar earlyDate = Calendar.getInstance();
	  earlyDate.set(2001,11-1,2); // previous Friday
	  
	  Calendar payDate = Calendar.getInstance();
	  payDate.set(2001, 11,9);
	  
	  Calendar lateDate = Calendar.getInstance();
	  lateDate.set(2001, 11,16); // next Friday
	  
	  ServiceChargeTransaction sct = new ServiceChargeTransaction(memberId, payDate, 19.42);
	  sct.execute();
	  
	  ServiceChargeTransaction sctEarly = new ServiceChargeTransaction(memberId, earlyDate, 100.00);
	  sctEarly.execute();
	  
	  ServiceChargeTransaction sctLate = new ServiceChargeTransaction(memberId, lateDate, 200.00);
	  sctLate.execute();
	  
	  TimeCardTransaction tct = new TimeCardTransaction(payDate, 8.0, empId);
	  tct.execute();
	  
	  PaydayTransaction pt = new PaydayTransaction(payDate);
	  pt.execute();
	  
	  Paycheck pc = pt.getPaycheck(empId);
	  
	  assertTrue(pc != null);
	  assertTrue(pc.getPayPeriodEndDate() == payDate);
	  assertEquals(8*15.24, pc.getGrossPay(), .001);
	  assertTrue(pc.getField("Disposition").equals("Hold"));
	  assertEquals(9.42 + 19.42, pc.getDeductions(), .001);
	  assertEquals((8*15.24)-(9.42 + 19.42), pc.getNetPay(), .001);
	}

	static Test suite(){
		TestSuite testSuite = new TestSuite (PayrollTest.class);
		testSuite.addTest(new PayrollTest("testAddSalariedEmployee"));
		testSuite.addTest(new PayrollTest("testAddHourlyEmployee"));
		testSuite.addTest(new PayrollTest("testAddCommissionedEmployee"));
		testSuite.addTest(new PayrollTest("testDeleteEmployee"));
		testSuite.addTest(new PayrollTest("testTimeCardTransaction"));
		testSuite.addTest(new PayrollTest("testBadTimeCardTransaction"));
		testSuite.addTest(new PayrollTest("testSalesReceiptTransaction"));
		testSuite.addTest(new PayrollTest("testBadSalesReceiptTransaction"));
		testSuite.addTest(new PayrollTest("testAddServiceCharge"));
		testSuite.addTest(new PayrollTest("testChangeNameTransaction"));
		testSuite.addTest(new PayrollTest("testChangeAddressTransaction"));
		testSuite.addTest(new PayrollTest("testChangeHourlyTransaction"));
		testSuite.addTest(new PayrollTest("testChangeSalariedTransaction"));
		testSuite.addTest(new PayrollTest("testChangeCommissionedTransaction"));
		testSuite.addTest(new PayrollTest("testChangeMailTransaction"));
		testSuite.addTest(new PayrollTest("testChangeDirectTransaction"));
		testSuite.addTest(new PayrollTest("testChangeHoldTransaction"));
		testSuite.addTest(new PayrollTest("testChangeMemberTransaction"));
		testSuite.addTest(new PayrollTest("testChangeUnaffiliatedTransaction"));
		testSuite.addTest(new PayrollTest("testPaySingleSalariedEmployee"));
		testSuite.addTest(new PayrollTest("testPaySingleSalariedEmployeeOnWrongDate"));
		testSuite.addTest(new PayrollTest("testPayMultipleSalariedEmployees"));
		testSuite.addTest(new PayrollTest("testPaySingleHourlyEmployeeNoTimeCards"));
		testSuite.addTest(new PayrollTest("testPaySingleHourlyEmployeeOneTimeCard"));
		testSuite.addTest(new PayrollTest("testPaySingleHourlyEmployeeOvertimeOneTimeCard"));
		testSuite.addTest(new PayrollTest("testPaySingleHourlyEmployeeOnWrongDate"));
		testSuite.addTest(new PayrollTest("testPaySingleHourlyEmployeeTwoTimeCards"));
		testSuite.addTest(new PayrollTest("testPaySingleHourlyEmployeeWithTimeCardsSpanningTwoPayPeriods"));
		testSuite.addTest(new PayrollTest("testPaySingleCommissionedEmployeeNoSalesReceipts"));
		testSuite.addTest(new PayrollTest("testPaySingleCommissionedEmployeeOneSalesReceipt"));
		testSuite.addTest(new PayrollTest("testPaySingleCommissionedEmployeeTwoSalesReceipts"));
		testSuite.addTest(new PayrollTest("testPaySingleCommissionedEmployeeWrongDate"));
		testSuite.addTest(new PayrollTest("testPaySingleCommissionedEmployeeSpanMultiplePayPeriods"));
		testSuite.addTest(new PayrollTest("testSalariedUnionMemberDues"));
		testSuite.addTest(new PayrollTest("testHourlyUnionMemberDues"));
		testSuite.addTest(new PayrollTest("testCommissionedUnionMemberDues"));
		testSuite.addTest(new PayrollTest("testHourlyUnionMemberServiceCharge"));
		testSuite.addTest(new PayrollTest("testServiceChargesSpanningMultiplePayPeriods"));
		return testSuite;
	}

	
	public static void main(String[] args) {
		TestRunner.run(new TestSuite(PayrollTest.class));
	}*/


}

