package diet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;


/**
 * Represents a takeaway restaurant chain.
 * It allows managing restaurants, customers, and orders.
 */
public class Takeaway {

	private Food food;
	private java.util.Map<String, Restaurant> restaurants=new java.util.TreeMap<>();
	private java.util.Map<String, Customer> customers=new java.util.TreeMap<>(); // customer name -> customer object
	private Collection<Order> orders = new ArrayList<>();

	/**
	 * Constructor
	 * @param food the reference {@link Food} object with materials and products info.
	 */
	public Takeaway(Food food){
		this.food=food;
	}

	/**
	 * Creates a new restaurant with a given name
	 *
	 * @param restaurantName name of the restaurant
	 * @return the new restaurant
	 */
	public Restaurant addRestaurant(String restaurantName) {
		restaurants.put(restaurantName, new Restaurant(restaurantName, food, this));
		return restaurants.get(restaurantName);
	}

	/**
	 * Retrieves the names of all restaurants
	 *
	 * @return collection of restaurant names
	 */
	public Collection<String> restaurants() {
		return restaurants.keySet();
		}

	/**
	 * Creates a new customer for the takeaway
	 * @param firstName first name of the customer
	 * @param lastName	last name of the customer
	 * @param email		email of the customer
	 * @param phoneNumber mobile phone number
	 *
	 * @return the object representing the newly created customer
	 */
	public Customer registerCustomer(String firstName, String lastName, String email, String phoneNumber) {
		Customer customer = new Customer(firstName, lastName, email, phoneNumber);
		customers.put(customer.getLastName()+customer.getFirstName(), customer);
		return customer;
	}

	/**
	 * Retrieves all registered customers
	 *
	 * @return sorted collection of customers
	 */
	public Collection<Customer> customers(){
		return customers.values();
	}


	/**
	 * Creates a new order for the chain.
	 *
	 * @param customer		 customer issuing the order
	 * @param restaurantName name of the restaurant that will take the order
	 * @param time	time of desired delivery
	 * @return order object
	 */
	public Order createOrder(Customer customer, String restaurantName, String time) {
		
		if (time.length() == 4) {
			time = "0" + time;  // Aggiungo lo 0 all'inizio per farlo diventare "09:00"
		}
		
		Restaurant r=restaurants.get(restaurantName);
		if (r.isOpenAt(time)){
			Order order = new Order(food, this, customer, restaurantName, time);
			orders.add(order);
			return order;
		}
		int i, j=r.openingTimes.length;
		for (i=0;i<j; i+=2) {
			if (time.compareTo(r.openingTimes[i])<0) {
				break;
			}
		}
		Order order = new Order(food, this, customer, restaurantName, r.openingTimes[i%j]);
		orders.add(order);
		return order;
	}

	public Collection<Order> orders() {
		return orders;
	}

	/**
	 * Find all restaurants that are open at a given time.
	 *
	 * @param time the time with format {@code "HH:MM"}
	 * @return the sorted collection of restaurants
	 */
	public Collection<Restaurant> openRestaurants(String time){
		Map<String, Restaurant> openRestaurants = new TreeMap<>();
		for (Restaurant r : restaurants.values()) {
			if (r.isOpenAt(time)) {
				openRestaurants.put(r.getName(), r);
			}
		}
		return openRestaurants.values();
	}
}
