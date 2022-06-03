package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public final class Destination {

    private final String name;
    private ArrayList<Flight> flights;

    public Destination(String name) {
        this.name = name;
        flights = new ArrayList<>();
    }

    public void loadFlights(File DestinationName) throws FileNotFoundException, IOException {
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
    
    public void registerFlight(String date, int flightNumber, String departureAirport, String arrivalAirport, String departureTime, String arrivalTime, String typeOfAircraft, String registrationOfAircraft) {
        Flight newFlight = new Flight(date, flightNumber, departureAirport, arrivalAirport, departureTime, arrivalTime, typeOfAircraft, registrationOfAircraft);
        flights.add(newFlight);
    }
    
    public String toString() {
        return getName();
    }
    
    public String getFlights() {
        StringBuilder builder = new StringBuilder();
        for (Flight flight : flights) {
            builder.append(flight.getSequence()).append(". ").append(flight.toString());
        }
        return builder.toString();
    }
    
    public Flight findFlight(int sequenceNumber) {
        for (Flight flight : flights) {
            if (flight.getSequence() == sequenceNumber) {
                return flight;
            }
        }
                throw new NoSuchElementException("Flight not found.");

    }

    public static void main(String[] args) {

    }
}
