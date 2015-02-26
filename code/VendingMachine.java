import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

class VendingMachine {

	private static int initialStock = 5;
	private static int rows = 3;
	private boolean next = false;
	private static HashMap<String, Item> items = new HashMap<String, Item>();

	public VendingMachine() {
		for (int i = 0; i < rows; i++) {
			String l = new String("");
			String key;
			if (i == 0)
				l = "a";
			if (i == 1)
				l = "b";
			if (i == 2)
				l = "c";
			for (int k = 0; k < 10; k++) {
				key = l + k;
				items.put(key, new Item("Item " + key,  0.75, initialStock));
			}
		}
	}

	public static void main(String[] args) {
		new VendingMachine();
		ArrayList<String> cargs = new ArrayList<String>(Arrays.asList(args));
		TransactionState st = new AcceptPaymentState(0.0, new String(""));
		
		if (cargs.size() == 0) {
			Scanner cin = new Scanner(System.in);
			displayStock();
			System.out.println("Please insert cash first...");
			do {
				String input = cin.nextLine();
				if (input.equalsIgnoreCase("display")) {
					displayStock();
				}
				else {
					st = st.handle(input);
				}
			} while (st != null);
		}
		else {
			Scanner fin;
			try {
				fin = new Scanner(new FileInputStream(cargs.get(0)));
				do {
					String input = fin.nextLine();
					st = st.handle(input);
				} while (st != null);
			} catch (FileNotFoundException e) {
				System.out.println("File Not Found");
			}
		}
	}

	public static void vendItem(String code) {
		items.get(code).vend();
		new AcceptPaymentState(0.0, new String(""));
	}

	public static double getItemPrice(String code) throws NullPointerException {
		Item i = items.get(code);

		if (i == null) {
			throw new NullPointerException();
		}

		return i.getPrice();			
	}

	public static void checkItemStock(String code) throws ItemOutOfStockException {
		int i = items.get(code).getStock();

		if (i <= 0) {
			throw new ItemOutOfStockException();
		}
	}

	public static TransactionState cancel() {
		return new AcceptPaymentState(0.0, new String(""));
	}
	
	public static TransactionState end() {
		System.out.println("Shutting down...");
		return null;
	}
	
	public static void displayStock() {
		for (String key : items.keySet()) {
			System.out.println("Name: Item" + key + "\t Price:" +  items.get(key).getPrice() + "\t stock: " + items.get(key).getStock());
		}
	}
}
