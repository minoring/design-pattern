package payroll;


public class NoAffiliation implements Affiliation {

	@Override
	public double calculateDeductions(Paycheck pc) {
		System.out.println("NoAffiliation");
		return 0;
	}

}
