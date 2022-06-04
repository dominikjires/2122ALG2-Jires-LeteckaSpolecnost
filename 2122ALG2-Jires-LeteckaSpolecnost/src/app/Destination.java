package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Třída pro destinaci
 * @author Dominik Jireš
 */
public final class Destination {

    private final String name;
    private final ArrayList<Flight> flights;
    
    /**
     * Konstruktor pro destinaci
     * @param name String
     */
    public Destination(String name) {
        this.name = name;
        flights = new ArrayList<>();
    }
    
    /**
     * Načítá lety z jednotlivých souborů podle destinace
     * @param DestinationName File
     * @throws java.io.FileNotFoundException
     */
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
    
    /**
     * Vrací jméno
     * @return String
     */
    public String getName() {
        return name;
    }
    
    /**
     * Registrace letu
     * @param date String
     * @param flightNumber int
     * @param departureAirport String
     * @param arrivalAirport String
     * @param departureTime String
     * @param arrivalTime String
     * @param typeOfAircraft String
     * @param registrationOfAircraft String
     */
    public void registerFlight(String date, int flightNumber, String departureAirport, String arrivalAirport, String departureTime, String arrivalTime, String typeOfAircraft, String registrationOfAircraft) {
        Flight newFlight = new Flight(date, flightNumber, departureAirport, arrivalAirport, departureTime, arrivalTime, typeOfAircraft, registrationOfAircraft);
        flights.add(newFlight);
    }
    
    /**
     * Vrací String
     * @return String 
     */
    @Override
    public String toString() {
        return getName();
    }
    
    /**
     * Vrací seznam letů do destinace
     * @return String
     */
    public String getFlights() {
        StringBuilder builder = new StringBuilder();
        for (Flight flight : flights) {
            builder.append(flight.getSequence()).append(". ").append(flight.toString());
        }
        return builder.toString();
    }

    /**
     * Hladá let a pokud ho najde tak vrací flight
     * @param sequenceNumber int
     * @return Flight 
     */
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
