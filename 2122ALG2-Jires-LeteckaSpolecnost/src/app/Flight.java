package app;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;

public class Flight {

    private final String departureAirport;
    private final String arrivalAirport;
    private final int flightNumber;
    private final int departureTime;
    private final int arrivalTime;
    private final String typeOfAircraft;
    private final String registrationOfAircraft;
    private int[][] seatMap;
    private static int AllocatedSeatsCount = 1;

    private ArrayList<Passenger> passengers;

    public Flight(int flightNumber, String departureAirport, String arrivalAirport, int departureTime, int arrivalTime, String typeOfAircraft, String registrationOfAircraft) {
        this.flightNumber = flightNumber;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.typeOfAircraft = typeOfAircraft;
        this.registrationOfAircraft = registrationOfAircraft;
        passengers = new ArrayList<>();
        createSeatMap(this.typeOfAircraft);
    }

    public void registerPassenger(String name, String surname) {
        Passenger newPassenger = new Passenger(name, surname);
        this.passengers.add(newPassenger);
    }

    public void removePassenger(int passengerNumber) {
        Passenger passenger = findPassenger(passengerNumber);
        boolean passengerRemoved = passengers.remove(passenger);

        if (!passengerRemoved) {
            throw new NoSuchElementException("Passenger not found.");
        }
    }

    public Passenger findPassenger(int passengerNumber) {
        for (Passenger passenger : passengers) {
            if (passenger.getPassengerNumber() == passengerNumber) {
                return passenger;
            }
        }

        throw new NoSuchElementException("Passenger not found.");
    }

    public void createSeatMap(String typeOfAircraft) {
        switch (this.typeOfAircraft) {
            case "A320":
                this.seatMap = new int[29][5];
                break;
            default:
                throw new NoSuchElementException("Type of aircraft is unsupported.");
        }
    }

    public void seatAllocation(int passengerNumber) {
        Passenger passenger = findPassenger(passengerNumber);
        if (AllocatedSeatsCount > this.seatMap.length * this.seatMap[0].length) {
            throw new NoSuchElementException("No available seats");
        } else {
            Random rand = new Random();
            int i;
            int j;
            do {
                i = rand.nextInt(this.seatMap.length);
                j = rand.nextInt(this.seatMap[0].length);
            } while (this.seatMap[i][j] == 1);
            this.seatMap[i][j] = 1;
            passenger.row = i+1;
            switch (j) {
                case 0:
                    passenger.seat = 'A';
                    break;
                case 1:
                    passenger.seat = 'B';
                    break;
                case 2:
                    passenger.seat = 'C';
                    break;
                case 3:
                    passenger.seat = 'D';
                    break;
                case 4:
                    passenger.seat = 'E';
                    break;
                case 5:
                    passenger.seat = 'F';
                    break;
                default:
                    throw new NoSuchElementException("Number of seats exceeded.");
            }
            AllocatedSeatsCount++;
        }

    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public int getDepartureTime() {
        return departureTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public String getTypeOfAircraft() {
        return typeOfAircraft;
    }

    public String getRegistrationOfAircraft() {
        return registrationOfAircraft;
    }
    
    public String toString() {
        return flightNumber + " " + departureAirport + " " + arrivalAirport + " " + departureTime + " " + arrivalTime + " " + typeOfAircraft + " " + registrationOfAircraft; 
    }

    public static int getAllocatedSeatsCount() {
        return AllocatedSeatsCount;
    }

    public static void setAllocatedSeatsCount(int AllocatedSeatsCount) {
        Flight.AllocatedSeatsCount = AllocatedSeatsCount;
    }
    
    
    
 public static void main(String[] args) {
 Flight ezy2252 = new Flight(2252, "PRG", "LTN", 1650, 1745, "A320", "G-EZAS");
     System.out.println(ezy2252);
     ezy2252.registerPassenger("Dominik", "Jireš");  
     ezy2252.registerPassenger("Matěj", "Paclt");
     ezy2252.registerPassenger("Jan", "Mulač");
     ezy2252.registerPassenger("Jakub", "Šlambor");
     ezy2252.registerPassenger("Tomáš", "Vrba");
     System.out.println(ezy2252.getArrivalAirport());
     ezy2252.seatAllocation(1);
     ezy2252.seatAllocation(2);
     ezy2252.seatAllocation(3);
     ezy2252.seatAllocation(4);
     ezy2252.seatAllocation(5);
     System.out.println(ezy2252.passengers.get(0).getRow() + "" + ezy2252.passengers.get(0).getSeat());
     System.out.println(ezy2252.passengers.get(1).getRow() + "" + ezy2252.passengers.get(1).getSeat());
     System.out.println(ezy2252.passengers.get(2).getRow() + "" + ezy2252.passengers.get(2).getSeat());
     System.out.println(ezy2252.passengers.get(3).getRow() + "" + ezy2252.passengers.get(3).getSeat());
     System.out.println(ezy2252.passengers.get(4).getRow() + "" + ezy2252.passengers.get(4).getSeat());


 
 }
    
}
