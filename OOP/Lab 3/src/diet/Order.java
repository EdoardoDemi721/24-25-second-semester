package diet;

/**
 * Represents and order issued by an {@link Customer} for a {@link Restaurant}.
 *
 * When an order is printed to a string is should look like:
 * <pre>
 *  RESTAURANT_NAME, USER_FIRST_NAME USER_LAST_NAME : DELIVERY(HH:MM):
 *  	MENU_NAME_1->MENU_QUANTITY_1
 *  	...
 *  	MENU_NAME_k->MENU_QUANTITY_k
 * </pre>
 */
public class Order {
	
	protected Food food;
	protected Takeaway takeaway;
	protected Customer customer;
	protected String restaurantName;
	protected String deliveryTime;
	protected java.util.Map<String, Integer> menus = new java.util.HashMap<>(); // menu name -> quantity
	protected OrderStatus status = OrderStatus.ORDERED;
	protected PaymentMethod paymentMethod = PaymentMethod.CASH;

	public Order (Food food, Takeaway takeaway, Customer customer, String restaurantName, String deliveryTime) {
		this.food = food;
		this.takeaway = takeaway;
		this.customer = customer;
		this.restaurantName = restaurantName;
		this.deliveryTime = deliveryTime;
	}


	/**
	 * Possible order statuses
	 */
	public enum OrderStatus {
		ORDERED, READY, DELIVERED
	}

	/**
	 * Accepted payment methods
	 */
	public enum PaymentMethod {
		PAID, CASH, CARD
	}

	/**
	 * Set payment method
	 * @param pm the payment method
	 */
	public void setPaymentMethod(PaymentMethod pm) {
		this.paymentMethod = pm;
	}

	/**
	 * Retrieves current payment method
	 * @return the current method
	 */
	public PaymentMethod getPaymentMethod() {
		return this.paymentMethod;
	}

	/**
	 * Set the new status for the order
	 * @param os new status
	 */
	public void setStatus(OrderStatus os) {
		this.status = os;
	}

	/**
	 * Retrieves the current status of the order
	 *
	 * @return current status
	 */
	public OrderStatus getStatus() {
		return this.status;
	}

	/**
	 * Add a new menu to the order with a given quantity
	 *
	 * @param menu	menu to be added
	 * @param quantity quantity
	 * @return the order itself (allows method chaining)
	 */
	public Order addMenus(String menu, int quantity) {
		menus.put(menu, quantity);
		return this;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(restaurantName).append(", ").append(customer.getFirstName()).append(" ").append(customer.getLastName()).append(" : (").append(deliveryTime).append("):\n");
		for (String menu : menus.keySet()) {
			sb.append("\t").append(menu).append("->").append(menus.get(menu)).append("\n");
		}
		return sb.toString().trim();		
	}	

	public String getRestaurantName() {
		return restaurantName;
	}
}
