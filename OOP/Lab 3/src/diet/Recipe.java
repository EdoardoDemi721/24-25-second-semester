package diet;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a recipe of the diet.
 * 
 * A recipe consists of a a set of ingredients that are given amounts of raw materials.
 * The overall nutritional values of a recipe can be computed
 * on the basis of the ingredients' values and are expressed per 100g
 * 
 *
 */
public class Recipe implements NutritionalElement {
	private String name;
	private double totalWeight=0.0;
	private java.util.Map<String, Double> ingredients=new java.util.HashMap<>(); // ingredient name -> weight in grams
	private Food food;
	private List<String> insertionOrder = new LinkedList<>();

	public Recipe(String name, Food food) {
		this.name = name;
		this.food=food;
	}
	
	/**
	 * Adds the given quantity of an ingredient to the recipe.
	 * The ingredient is a raw material.
	 * 
	 * @param material the name of the raw material to be used as ingredient
	 * @param quantity the amount in grams of the raw material to be used
	 * @return the same Recipe object, it allows method chaining.
	 */
	public Recipe addIngredient(String material, double quantity) {
		ingredients.put(material, quantity);
		insertionOrder.add(material);
		totalWeight += quantity;
		return this;
	}

	@Override
	public String getName() {
		return this.name;
	}

	
	@Override
	public double getCalories() {
		double total=0.0;
		for (String key : ingredients.keySet()) {
			NutritionalElement elem = food.getRawMaterial(key);
			if (elem != null) {
				total+= elem.getCalories() * ingredients.get(key)/100.0;
			}
		}
		return total/totalWeight*100.0;
	}
	

	@Override
	public double getProteins() {
		double total=0.0;
		for (String key : ingredients.keySet()) {
			NutritionalElement elem = food.getRawMaterial(key);
			if (elem != null) {
				total+= elem.getProteins() * ingredients.get(key)/100.0;
			}
		}
		return total/totalWeight*100.0;
	}

	@Override
	public double getCarbs() {
		double total=0.0;
		for (String key : ingredients.keySet()) {
			NutritionalElement elem = food.getRawMaterial(key);
			if (elem != null) {
				total+= elem.getCarbs() * ingredients.get(key)/100.0;
			}
		}
		return total/totalWeight*100.0;
	}

	@Override
	public double getFat() {
		double total=0.0;
		for (String key : ingredients.keySet()) {
			NutritionalElement elem = food.getRawMaterial(key);
			if (elem != null) {
				total+= elem.getFat() * ingredients.get(key)/100.0;
			}
		}
		return total/totalWeight*100.0;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Recipe} class it must always return {@code true}:
	 * a recipe expresses nutritional values per 100g
	 * 
	 * @return boolean indicator
	 */
	@Override
	public boolean per100g() {
		return true;
	}
	
	@Override
	public String toString(){
		String[] insertionOrderArray = insertionOrder.toArray(new String[0]);
		String s=insertionOrderArray[0]+" "+ingredients.get(insertionOrderArray[0]);
		for (int i=1; i<insertionOrderArray.length; i++){
			s+="\n"+insertionOrderArray[i]+": "+ingredients.get(insertionOrderArray[i]);
		}
		return s;
	}
}
