public class Item {

	private String name;
	private double price;
	private int stock;

	public Item(String n, double p, int s) {

		this.name = n;
		this.price = p;
		this.stock = s;

	}

	public double getPrice() {
		return this.price;
	}

	public int getStock() {
		return this.stock;
	}
	
	public void vend() {
		this.stock--;
		System.out.println(this.name + " dispensed.");
	}

}
