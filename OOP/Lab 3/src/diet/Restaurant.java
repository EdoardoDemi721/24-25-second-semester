package diet;
import java.util.Map;
import java.util.TreeMap;

import diet.Order.OrderStatus;

/**
 * Represents a restaurant class with given opening times and a set of menus.
 */
public class Restaurant {
	
	protected String name;
	protected String[] openingTimes;
	protected java.util.Map<String, Menu> menus=new java.util.HashMap<>();
	protected Food food;
	protected Takeaway takeaway;

	public Restaurant(String name, Food food, Takeaway takeaway) {
		this.name = name;
		this.food = food;
		this.takeaway = takeaway;
	}

	/**
	 * retrieves the name of the restaurant.
	 *
	 * @return name of the restaurant
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Define opening times.
	 * Accepts an array of strings (even number of elements) in the format {@code "HH:MM"},
	 * so that the closing hours follow the opening hours
	 * (e.g., for a restaurant opened from 8:15 until 14:00 and from 19:00 until 00:00,
	 * arguments would be {@code "08:15", "14:00", "19:00", "00:00"}).
	 *
	 * @param hm sequence of opening and closing times
	 */
	public void setHours(String ... hm) {
		openingTimes = hm;
	}

	/**
	 * Checks whether the restaurant is open at the given time.
	 *
	 * @param time time to check
	 * @return {@code true} is the restaurant is open at that time
	 */
	public boolean isOpenAt(String time){
		if (openingTimes == null || openingTimes.length == 0) {
			return false; // No opening times defined
		}
		for (int i = 0; i < openingTimes.length; i += 2) {
			if (time.compareTo(openingTimes[i]) >= 0 && time.compareTo(openingTimes[i + 1]) <= 0) {
				return true; // Time is within the opening hours
			}
		}
		return false; // Time is outside the opening hours
	}

	/**
	 * Adds a menu to the list of menus offered by the restaurant
	 *
	 * @param menu	the menu
	 */
	public void addMenu(Menu menu) {
		menus.put(menu.getName(), menu);
	}

	/**
	 * Gets the restaurant menu with the given name
	 *
	 * @param name	name of the required menu
	 * @return menu with the given name
	 */
	public Menu getMenu(String name) {
		return menus.get(name);
	}

	/**
	 * Retrieve all order with a given status with all the relative details in text format.
	 *
	 * @param status the status to be matched
	 * @return textual representation of orders
	 */
	public String ordersWithStatus(OrderStatus status) {
		Map<String, Order> orders = new TreeMap<>();
		StringBuilder sb = new StringBuilder();
		for (Order o : takeaway.orders()) {
			if (o.getRestaurantName().equals(name) && o.getStatus() == status) {
				orders.put(o.getRestaurantName()+o.customer.getFirstName()+o.deliveryTime, o);
			}
		}
		for (Order o : orders.values()) {
			sb.append(o.toString()).append("\n");
		}
		return sb.toString().trim();
	}
}
