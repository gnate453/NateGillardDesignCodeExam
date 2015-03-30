import java.text.DecimalFormat;

public class VendChangeState extends TransactionState {

	private DecimalFormat changeFormat = new DecimalFormat("$#.##");

	public VendChangeState(double p, String i) {
		super(p, i);
	}

	protected TransactionState handle(String in) {

		if (this.itemCode.equalsIgnoreCase("Z0")) {
			if (this.amountPaid > 0) {
				String out = changeFormat.format(this.amountPaid);
				System.out.println(out + " tendered returned.");
			}
			System.out.println("Transaction canceled.");
			return VendingMachine.cancel();
		}
		else if (this.itemCode.equalsIgnoreCase("Z9")) {
			if (this.amountPaid > 0) {
				String out = changeFormat.format(this.amountPaid);
				System.out.println(out + " tendered returned.");
			}
			return VendingMachine.end();
		}
		else {
			try {
				double price =  VendingMachine.getItemPrice(this.itemCode);
				VendingMachine.checkItemStock(this.itemCode);
				double change = this.amountPaid - price;
				if (change < 0) {
					System.out.println("Insufficient funds. Please make another selection.");
					return new SelectItemState(this.amountPaid, new String(""));
				}
				else if (change != 0) {
					String out = changeFormat.format(change);
					System.out.println("Please collect change: " + out);
				}
			}
			catch (NullPointerException e) {
				System.out.println("Invalid item code. Please make another selection.");
				return new SelectItemState(this.amountPaid, new String(""));
			} catch (ItemOutOfStockException e) {
				System.out.println("Item out of stock. Please make another selection.");
				return new SelectItemState(this.amountPaid, new String(""));
			}
			
			VendingMachine.vendItem(this.itemCode);
			return new AcceptPaymentState(0.0, new String(""));
		}
	}
}
