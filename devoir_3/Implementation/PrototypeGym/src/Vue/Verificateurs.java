package Vue;

import Modele.CentreDonnees;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static Controleur.ControleurClient.*;

public class Verificateurs {

    public static LocalDate getDateFromString (String stringDate) {
        return LocalDate.parse(stringDate, CentreDonnees.localDateFormatter);
    }

    public static boolean dateValide (String s){
        try {
            getDateFromString(s);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean courrielValide (String s){
        String regexPattern = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return s.matches(regexPattern);
    }

    private static boolean nomValide (String s){
        return s.length() >= 1;
    }

    public static boolean identifiantClientValide(String s){
        return s.length() == 9;
    }

    public static boolean adresseValide (String s){
        return s.length() >= 1;
    }

    public static boolean telephoneValide (String s){
        String regexPattern = "\\d{3}-\\d{3}-\\d{4}";
        return s.matches(regexPattern);
    }
}
