import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Product, Integer> products;

    public Cart() {
        products = new HashMap<>();
    }

    public void addProduct(Product product, int amount){
        products.put(product, amount);
    }
    public double getPrice(int year, int month){
        double price = 0;
        for(Product p : products.keySet()){
            price += p.getPrice(year, month) * products.get(p);
        }
        return price;
    }
    public double getInflation(int year1, int month1, int year2, int month2){
        double price1 = getPrice(year1, month1);
        double price2 = getPrice(year2, month2);
        int yearGap = year2 - year1;
        int months = yearGap*12 - month1 + month2;
        return (price2 - price1) / price1 * 100 / months * 12;
    }

}

