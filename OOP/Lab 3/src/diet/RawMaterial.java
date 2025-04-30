package diet;

public class RawMaterial implements NutritionalElement {
    
    private String name;
    private double calories;
    private double proteins;
    private double carbs;
    private double fat;
    
    /**
     * Constructor for the raw material.
     * The nutritional values are specified for a conventional 100g quantity
     * @param name unique name of the raw material
     * @param calories calories per 100g
     * @param proteins proteins per 100g
     * @param carbs carbs per 100g
     * @param fat fats per 100g
     */
    public RawMaterial(String name, double calories, double proteins, double carbs, double fat) {
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
        return true; // Nutritional values are specified for 100g in this class
    }
    
}
