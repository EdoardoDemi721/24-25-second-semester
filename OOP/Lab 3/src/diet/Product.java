package diet;

public class Product implements NutritionalElement {
    private String name;
    private double calories;
    private double proteins;
    private double carbs;
    private double fat;

    /**
     * Constructor for the product.
     * The nutritional values are specified for a unit of the product
     * @param name unique name of the product
     * @param calories calories for a product unit
     * @param proteins proteins for a product unit
     * @param carbs carbs for a product unit
     * @param fat fats for a product unit
     */
    public Product(String name, double calories, double proteins, double carbs, double fat) {
        this.name = name;
        this.calories = calories;
        this.proteins = proteins;
        this.carbs = carbs;
        this.fat = fat;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getCalories() {
        return calories;
    }

    @Override
    public double getProteins() {
        return proteins;
    }

    @Override
    public double getCarbs() {
        return carbs;
    }

    @Override
    public double getFat() {
        return fat;
    }

    @Override
    public boolean per100g() {
        return false; // Nutritional values are specified for a unit in this class
    }
    
}
