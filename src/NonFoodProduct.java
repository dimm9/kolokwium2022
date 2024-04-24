import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;

public class NonFoodProduct extends Product{

    private String name;
    private Double[] prices;

    private NonFoodProduct(String name, Double[] prices) {
        this.name = name;
        this.prices = prices;
    }

    public static NonFoodProduct fromCsv(Path path) {
        String name;
        Double[] prices;

        try {
            Scanner scanner = new Scanner(path);
            name = scanner.nextLine(); // odczytuję pierwszą linię i zapisuję ją jako nazwa
            scanner.nextLine();  // pomijam drugą linię z nagłówkiem tabeli
            prices = Arrays.stream(scanner.nextLine().split(";")) // odczytuję kolejną linię i dzielę ją na tablicę
                    .map(value -> value.replace(",",".")) // zamieniam polski znak ułamka dziesiętnego - przecinek na kropkę
                    .map(Double::valueOf) // konwertuję string na double
                    .toArray(Double[]::new); // dodaję je do nowo utworzonej tablicy

            scanner.close();

            return new NonFoodProduct(name, prices);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public double getPrice(int year, int month) throws IndexOutOfBoundsException{
        int difference = year - 2010;
        int index = difference*12 + month -1;
        int maxMonths = 2022 * 12 + 3;
        int minMonths = 2010 * 12 + 1;
        int currentMonths = year * 12 + month;
        if(currentMonths < minMonths || currentMonths > maxMonths || month > 12 || month < 1){
            throw new IndexOutOfBoundsException();
        }
        return prices[index];
    }
}
