import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.regex.Pattern;

public class SelectItemState extends TransactionState {

	private DecimalFormat changeFormat = new DecimalFormat("$#.##");
	
	public SelectItemState(double p, String i) {
		super(p, i);		
	}

	protected TransactionState handle(String in) {
		if (isMatch(in)) {
			if (in.equalsIgnoreCase("cancel")) {
				TransactionState st = new VendChangeState(this.amountPaid, "Z0");
				return st.handle("");
			}	
			else if (in.equalsIgnoreCase("end")) {
				TransactionState st = new VendChangeState(this.amountPaid, "Z9");
				return st.handle("");
			}
			else {
				TransactionState st = new VendChangeState(this.amountPaid, in);
				return st.handle("");
			}
		}
		else {
			if (isDouble(in))
			{
				double d = Double.parseDouble(in);
				String out = changeFormat.format(d);
				System.out.println("Accepted amount: " + out);
				this.amountPaid += d;
				return new AcceptPaymentState(this.amountPaid, this.itemCode);
			}
			else {
				System.out.println("Invalid selection. Please Try another.");
				return this;
			}
		}
	}

	private boolean isMatch(String s) {	
		if (s.matches("[a-zA-z]+\\d")) {
			return true;
		}
		else if (s.equalsIgnoreCase("cancel") || s.equalsIgnoreCase("end")) {
			return true;
		}
		else {
			return false;
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
