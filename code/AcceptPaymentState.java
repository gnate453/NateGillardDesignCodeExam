import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.regex.Pattern;

public class AcceptPaymentState extends TransactionState {

	private DecimalFormat changeFormat = new DecimalFormat("$#.##");
	
	public AcceptPaymentState(double p, String i) {
		super(p, i);		
	}

	protected TransactionState handle(String in) {
		if (isDouble(in)) {
			double d = Double.parseDouble(in);
			String out = changeFormat.format(d);
			System.out.println("Accepted amount: " + out);
			this.amountPaid += d;
			return this;
		}
		else
		{
			TransactionState st = new SelectItemState(this.amountPaid, in);
			return st.handle(in);
		}
	}

	private boolean isDouble(String in) {	
		try {
			Double d = Double.parseDouble(in);
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}
}
