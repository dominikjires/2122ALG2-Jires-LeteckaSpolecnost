package utils;

public final class TimeTools {

    public static String minutesToStringTime(int secondsInput){
        int hours = secondsInput / 60;
        int minutes = secondsInput % 60;
        return String.format("%02d:%02d", hours, minutes);
    }
    
    public static int timeToMinutes(int hours, int minutes){
        return hours*60 + minutes;
        
    }
    
    public static int stringTimeToMinutes(String time) {
        String[] arr = time.split(":");
        int hours = Integer.parseInt(arr[0]);
        int minutes = Integer.parseInt(arr[1]);
        return timeToMinutes(hours, minutes);
    }
    
    
    public static void main(String[] args) {
        System.out.println(minutesToStringTime(61));
        System.out.println(timeToMinutes(2, 2));
        System.out.println(stringTimeToMinutes("10:24"));
    }
}