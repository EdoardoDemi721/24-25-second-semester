package diet;

/**
 * Represents a complete menu.
 * 
 * It can be made up of both packaged products and servings of given recipes.
 *
 */
public class Menu implements NutritionalElement {

	private String name;
	private Food food;
	private java.util.Map<String, Double> recipes=new java.util.HashMap<>(); // recipe name -> weight in grams
	private java.util.List<String> products=new java.util.ArrayList<>(); // product name

	public Menu(String name, Food food) {
		this.name = name;
		this.food = food;
	}


	/**
	 * Adds a given serving size of a recipe.
	 * The recipe is a name of a recipe defined in the {@code food}
	 * argument of the constructor.
	 * 
	 * @param recipe the name of the recipe to be used as ingredient
	 * @param quantity the amount in grams of the recipe to be used
	 * @return the same Menu to allow method chaining
	 */
    public Menu addRecipe(String recipe, double quantity) {
		recipes.put(recipe, quantity);
		return this;
	}

	/**
	 * Adds a unit of a packaged product.
	 * The product is a name of a product defined in the {@code food}
	 * argument of the constructor.
	 * 
	 * @param product the name of the product to be used as ingredient
	 * @return the same Menu to allow method chaining
	 */
    public Menu addProduct(String product) {
		products.add(product);
		return this;
	}

	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * Total KCal in the menu
	 */
	@Override
	public double getCalories() {
		double total=0.0;
		for (String key : recipes.keySet()) {
			NutritionalElement elem = food.getRecipe(key);
			if (elem != null) {
				total+= elem.getCalories() * recipes.get(key)/100.0;
			}
		}
		for (String key : products) {
			NutritionalElement elem = food.getProduct(key);
			if (elem != null) {
				total+= elem.getCalories();
			}
		}
		return total;
	}

	/**
	 * Total proteins in the menu
	 */
	@Override
	public double getProteins() {
		double total=0.0;
		for (String key : recipes.keySet()) {
			NutritionalElement elem = food.getRecipe(key);
			if (elem != null) {
				total+= elem.getProteins() * recipes.get(key)/100.0;
			}
		}
		for (String key : products) {
			NutritionalElement elem = food.getProduct(key);
			if (elem != null) {
				total+= elem.getProteins();
			}
		}
		return total;
	}

	/**
	 * Total carbs in the menu
	 */
	@Override
	public double getCarbs() {
		double total=0.0;
		for (String key : recipes.keySet()) {
			NutritionalElement elem = food.getRecipe(key);
			if (elem != null) {
				total+= elem.getCarbs() * recipes.get(key)/100.0;
			}
		}
		for (String key : products) {
			NutritionalElement elem = food.getProduct(key);
			if (elem != null) {
				total+= elem.getCarbs();
			}
		}
		return total;
	}

	/**
	 * Total fats in the menu
	 */
	@Override
	public double getFat() {
		double total=0.0;
		for (String key : recipes.keySet()) {
			NutritionalElement elem = food.getRecipe(key);
			if (elem != null) {
				total+= elem.getFat() * recipes.get(key)/100.0;
			}
		}
		for (String key : products) {
			NutritionalElement elem = food.getProduct(key);
			if (elem != null) {
				total+= elem.getFat();
			}
		}
		return total;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Menu} class it must always return {@code false}:
	 * nutritional values are provided for the whole menu.
	 * 
	 * @return boolean indicator
	 */
	@Override
	public boolean per100g() {
		return false;
	}
}