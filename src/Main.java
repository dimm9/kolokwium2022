import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        NonFoodProduct nonFood = NonFoodProduct.fromCsv(Path.of("C:\\Users\\Yulia\\OneDrive\\Obrazy\\javaUMCS\\kolokwium2022\\data\\nonfood\\benzyna.csv"));
        FoodProduct food = FoodProduct.fromCsv(Path.of("C:\\Users\\Yulia\\OneDrive\\Obrazy\\javaUMCS\\kolokwium2022\\data\\food\\buraki.csv"));
        System.out.println(food.getPrice(2011, 5, "PODLASKIE"));
        System.out.println(food.getPrice(2010, 1));
        System.out.println(nonFood.getPrice(2011, 4));
        Product.addProducts(FoodProduct::fromCsv, Path.of("C:\\Users\\Yulia\\OneDrive\\Obrazy\\javaUMCS\\kolokwium2022\\data\\food"));
        Product.getProductList().forEach(System.out::println);
        try{
            Product p = Product.getProducts("Bu");
            System.out.println("---------\n" + p);
            Cart cart = new Cart();
            cart.addProduct(nonFood, 2);
            System.out.println("Cena 2011 roku w kwietniu: " + cart.getPrice(2011, 4));
            cart.addProduct(p, 3);
            System.out.println("Cena 2011 roku w maju: " + cart.getPrice(2011, 5));
            System.out.println("Inflacja: " + cart.getInflation(2011, 11, 2012, 2));
        } catch (AmbiguousProductException e) {
            e.printStackTrace();
        }
    }
}