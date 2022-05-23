package app;

import static app.Destination.loadFlights;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Airline {

    private final String name;
    public static ArrayList<Destination> destinationsList;

    public Airline(String name) {
        this.name = name;
        destinationsList = new ArrayList<>();
    }

    public static void loadDestination(File destinations) throws FileNotFoundException, IOException {;
        try (Scanner in = new Scanner(destinations)) {
            String DestinationName;
            in.nextLine();
            destinationsList = new ArrayList<>();
            while (in.hasNext()) {
                DestinationName = in.next();
                Destination newDestination = new Destination(DestinationName);
                loadFlights(new File("data/" + DestinationName + ".txt"));
                destinationsList.add(newDestination);
            }
        }
    }

    public static String getDestinations() {
        StringBuilder builder = new StringBuilder();
        for (Destination destination : destinationsList) {
            builder.append(destinationsList.toString()).append("\n");
        }
        return builder.toString();
    }
   
    public static Destination findDestination(String name) {
        for (Destination destination : destinationsList) {
            if (destination.getName() == null ? name == null : destination.getName().equals(name)) {
                return destination;
            }
        }

        throw new NoSuchElementException("Destination not found.");
    }

    public static void main(String[] args) {
        //Airline a = new Airline("TUL air");
        //try {
        //    try {
        //        a.loadDestination(new File("data/destinations.txt"));
        //        System.out.println(getDestinations());
        //    } catch (RuntimeException e) {
        //        System.out.println(e.getMessage());
        //    }
        //} catch (IOException e) {
        //    System.out.println(e.getMessage());
        //}
    }
    
}
