package main.controleur;

import main.modele.Jour;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Verificateurs {

    public static ZoneId zoneId = ZoneId.systemDefault();
    public static DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    public static DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static DateTimeFormatter localTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public static LocalDate today() {
        return LocalDate.now(zoneId) ;
    }

    public static LocalDateTime now() {
        return LocalDateTime.now(zoneId) ;
    }

    public static LocalDate getDateFromString(String stringDate) {
        return LocalDate.parse(stringDate, localDateFormatter);
    }

    public static LocalTime getHeureFromString(String stringTime) {
        return LocalTime.parse(stringTime, localTimeFormatter);
    }

    public static Jour getJourFromString(String stringJour) {
        return Jour.valueOf(stringJour.toUpperCase());
    }

    public static int getIntFromString(String stringInt) {
        return Integer.parseInt(stringInt);
    }

    public static double getDoubleFromString(String stringDouble) {
        return Double.parseDouble(stringDouble);
    }

    public static boolean dateValide (String s){
        try {
            getDateFromString(s);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    public static boolean codePostalValide(String s) {
        return s != null && s.length() == 6;
    }

    public static boolean courrielValide (String s) {
        String regexPattern = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return s.matches(regexPattern);
    }

    public static boolean nomValide (String s){
        return s.length() >= 1;
    }

    public static boolean horaireValide(String s) {
        String regexPattern = "^([0-1][0-9]|[2][0-3]):([0-5][0-9])$";
        return s.matches(regexPattern);
    }

    public static boolean jourSemaineValide(String s) {
        for (Jour jour : Jour.values()) {
            if (s.toUpperCase().equals(jour.name().toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    public static boolean identifiantClientValide(String s) {
        return s != null && s.length() == 9 && intValide(s);
    }

    public static boolean identifiantServiceValide(String s) {
        return s != null && s.length() == 3 && intValide(s);
    }
    
    public static boolean identifiantSeanceValide(String s) {
        return s != null && s.length() == 7 && intValide(s);
    }

    public static boolean fraisServiceValide(String s) {
        String regexPattern = "^\\d+([.,]\\d{1,2})?$";
        return s.matches(regexPattern);
    }

    public static boolean intValide(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean adresseValide (String s){
        return s.length() >= 1;
    }
    
    public static boolean villeValide (String s){
        return s.length() >= 1;
    }
    
    public static boolean provinceValide (String s){
        return s.length() >= 1;
    }

    public static boolean telephoneValide (String s){
        String regexPattern = "\\d{3}-\\d{3}-\\d{4}";
        return s.matches(regexPattern);
    }
}
