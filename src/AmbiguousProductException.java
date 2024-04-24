import java.util.List;
import java.util.stream.Collectors;

public class AmbiguousProductException extends Exception{

    public AmbiguousProductException(List<String> names) {
        super(formatMessage(names));
    }
    private static String formatMessage(List<String> names){
        return String.join(" | ", names);
    }
}