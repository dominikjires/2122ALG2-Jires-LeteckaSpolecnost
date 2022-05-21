package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public final class Destination {

    private final String name;
    private static ArrayList<Flight> flights;

    public Destination(String name) {
        this.name = name;
        flights = new ArrayList<>();
    }

    public static void loadDestination(File destinations) throws FileNotFoundException, IOException {
        try (Scanner in = new Scanner(destinations)) {
            String DestinationName;
            in.nextLine();
            while (in.hasNext()) {
                DestinationName = in.next();
                Destination newDestination = new Destination(DestinationName);
                loadFlights(new File("data/" + DestinationName + ".txt"));
            }
        }
    }

    public static void loadFlights(File DestinationName) throws FileNotFoundException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(DestinationName))) {
            int lineNumber = 1;
            String date;
            int flightNumber;
            String line, departureAirport, arrivalAirport, departureTime, arrivalTime, typeOfAircraft, registrationOfAircraft;
            String[] parts;
            br.readLine();
            while ((line = br.readLine()) != null) {
                lineNumber++;
                parts = line.split("[ ]+");
                date = parts[0];
                flightNumber = Integer.parseInt(parts[1]);
                departureAirport = parts[2];
                arrivalAirport = parts[3];
                departureTime = parts[4];
                arrivalTime = parts[5];
                typeOfAircraft = parts[6];
                registrationOfAircraft = parts[7];
                registerFlight(date, flightNumber, departureAirport, arrivalAirport, departureTime, arrivalTime, typeOfAircraft, registrationOfAircraft);
            }
        }
    }

    public String getName() {
        return name;
    }
    
    public static void registerFlight(String date,int flightNumber, String departureAirport, String arrivalAirport, String departureTime, String arrivalTime, String typeOfAircraft, String registrationOfAircraft) {
        Flight newFlight = new Flight(date,flightNumber,departureAirport,arrivalAirport,departureTime, arrivalTime, typeOfAircraft, registrationOfAircraft);
        Destination.flights.add(newFlight);
    }

    public static void main(String[] args) {

        try {
            try {
                loadDestination(new File("data/destinations.txt"));
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
