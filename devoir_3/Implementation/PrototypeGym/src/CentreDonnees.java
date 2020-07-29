import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class CentreDonnees {

    public static ZoneId zoneId = ZoneId.systemDefault();
    public static DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    public static DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static DateTimeFormatter localTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");


    public CentreDonnees() {
        
    }

    public static LocalDate today() {
        return LocalDate.now(zoneId) ;
    }

    public static LocalDateTime now() {
        return LocalDateTime.now(zoneId) ;
    }

    

}
