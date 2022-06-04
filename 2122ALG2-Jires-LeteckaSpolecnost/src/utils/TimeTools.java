package utils;

/**
 * Pomocná třída na metody pro práci s časem
 *
 * @author Dominik Jireš
 */
public final class TimeTools {

    /**
     * Metoda převodu minut na String
     * @param secondsInput int
     * @return String
    */
    public static String minutesToStringTime(int secondsInput) {
        int hours = secondsInput / 60;
        int minutes = secondsInput % 60;
        return String.format("%02d:%02d", hours, minutes);
    }

    /**
     * Metoda převodu času na minuty
     * @param hours int
     * @param minutes int
     * @return int
    */
    public static int timeToMinutes(int hours, int minutes) {
        return hours * 60 + minutes;

    }
    
    /**
     * Metoda převodu Stringu na minuty
     * @param time String
     * @return int
    */
    public static int stringTimeToMinutes(String time) {
        String[] arr = time.split(":");
        int hours = Integer.parseInt(arr[0]);
        int minutes = Integer.parseInt(arr[1]);
        return timeToMinutes(hours, minutes);
    }

    public static void main(String[] args) {
        //System.out.println(minutesToStringTime(61));
        //System.out.println(timeToMinutes(2, 2));
        //System.out.println(stringTimeToMinutes("10:24"));
    }
}
