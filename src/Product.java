import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class Product {
    private static List<Product> products = new ArrayList<>();

    protected String name;
    public String getName() {
        return name;
    }
    public static List<Product> getProductList() {return products;}
    public abstract double getPrice(int year, int month);

    public static void clearProducts(){
        products.clear();
    }
    public static void addProducts(Function<Path, Product> function, Path directory){
        try{
            Files.list(directory).forEach(path ->{
                Product p = function.apply(path);
                if(p != null){
                    products.add(p);
                }

            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Product getProducts(String prefix) throws AmbiguousProductException, IndexOutOfBoundsException{
        int count = (int)products.stream().filter(p -> p.name.startsWith(prefix)).count();
        if(count == 0){
            throw new IndexOutOfBoundsException();
        }
        if(count == 1){
            return products.stream().filter(p -> p.name.startsWith(prefix)).findFirst().orElse(null);
        }else{
            List<Product> samePrefix = products.stream().filter(p -> p.name.startsWith(prefix)).toList();
            List<String> samePrefixNames = samePrefix.stream().map(Product::getName).collect(Collectors.toList());
            throw new AmbiguousProductException(samePrefixNames);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}