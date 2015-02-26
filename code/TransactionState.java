public abstract class TransactionState {

	protected double amountPaid;
	protected String itemCode;

	public TransactionState(double p, String i) {

		this.amountPaid = p;
		this.itemCode = new String(i);
		
	}

	protected abstract TransactionState handle(String in);
}
