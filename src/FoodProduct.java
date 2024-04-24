import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FoodProduct extends Product{

    List<String> provinces = new ArrayList<>();
    private Double[][] prices;

    public FoodProduct(List<String> provinces, Double[][] prices, String name) {
        this.provinces = provinces;
        this.prices = prices;
        this.name = name;
    }

    public static FoodProduct fromCsv(Path path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            String name = reader.readLine();
            reader.readLine(); // pass the year line
            int colsCount = (int) Arrays.stream(reader.readLine().split(";")).count();
            reader.readLine();
            reader.readLine();
            int count = 1;
            while (reader.readLine() != null) {
                count++;
            }
            reader.close();

            try (BufferedReader reopenedReader = new BufferedReader(new FileReader(path.toFile()))) {
                reopenedReader.readLine();
                reopenedReader.readLine();
                List<String> provinces = new ArrayList<>();
                Double[][] prices = new Double[count][colsCount];
                String line;
                int j = 0;
                while ((line = reopenedReader.readLine()) != null) {
                    String[] parts = line.split(";");
                    provinces.add(parts[0]);
                    for (int i = 1; i < colsCount; i++) {
                        parts[i] = parts[i].replace(",", ".");
                        prices[j][i-1] = Double.parseDouble(parts[i]); // Adjust index to start from 0
                    }
                    if(j != count-1){
                        j++;
                    }
                }
                return new FoodProduct(provinces, prices, name);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public double getPrice(int year, int month, String province) throws IndexOutOfBoundsException{
        int indexRow = provinces.indexOf(province);
        int difference = year - 2010;
        int index = difference*12 + month - 1;
        int maxMonths = 2022 * 12 + 3;
        int minMonths = 2010 * 12 + 1;
        int currentMonths = year * 12 + month;
        if(currentMonths < minMonths || currentMonths > maxMonths || month > 12 || month < 1 || indexRow == -1){
            throw new IndexOutOfBoundsException();
        }
        return prices[indexRow][index];
    }

    @Override
    public double getPrice(int year, int month) {
        int difference = year - 2010;
        int index = difference*12 + month - 1;
        double sum = 0;
        for(int i=0; i<prices.length; i++){
            sum += prices[i][index];
        }
        return sum/provinces.size();
    }
}
