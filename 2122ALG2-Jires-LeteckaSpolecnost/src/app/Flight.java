package app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import utils.TimeTools;

public final class Flight {

    private final String date;
    private final String departureAirport;
    private final String arrivalAirport;
    private final int flightNumber;
    private final String departureTime;
    private final String arrivalTime;
    private final String typeOfAircraft;
    private final String registrationOfAircraft;
    private int[][] seatMap;
    private static int AllocatedSeatsCount = 1;
    private static int FlightOrderCount = 1;
    private final int Sequence;

    private final ArrayList<Passenger> passengers;

    public Flight(String date,int flightNumber, String departureAirport, String arrivalAirport, String departureTime, String arrivalTime, String typeOfAircraft, String registrationOfAircraft) {
        this.date = date;
        this.flightNumber = flightNumber;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.typeOfAircraft = typeOfAircraft;
        this.registrationOfAircraft = registrationOfAircraft;
        passengers = new ArrayList<>();
        createSeatMap(this.typeOfAircraft);
        Sequence = FlightOrderCount;
        FlightOrderCount++;
    }

    public Passenger registerPassenger(String name, String surname) {
        Passenger newPassenger = new Passenger(name, surname);
        this.passengers.add(newPassenger);
        return newPassenger;
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

    private void createSeatMap(String typeOfAircraft) {
        switch (this.typeOfAircraft) {
            case "A320":
                this.seatMap = new int[29][5];
                break;
            default:
                throw new NoSuchElementException("Type of aircraft is unsupported.");
        }
    }

    public String getDate() {
        return date;
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

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getTypeOfAircraft() {
        return typeOfAircraft;
    }

    public String getRegistrationOfAircraft() {
        return registrationOfAircraft;
    }
    
    public static int getAllocatedSeatsCount() {
        return AllocatedSeatsCount;
    }

    public static void setAllocatedSeatsCount(int AllocatedSeatsCount) {
        Flight.AllocatedSeatsCount = AllocatedSeatsCount;
    }
    
    public ArrayList<Passenger> getPassengers() {
        ArrayList<Passenger> copy = new ArrayList<>();
        for (Passenger passenger : passengers) {
            copy.add(new Passenger(passenger));
        }
        return copy;
    }
    
    private void sortBySurname() {
        Collections.sort(passengers, COMP_BY_NAME);
    }
    
    public ArrayList<Passenger> getPassengersSortedbySurName() {
        sortBySurname();
        return getPassengers();
    }
    
    public static final Collator col = Collator.getInstance(new Locale("cs", "CZ"));
    public static final Comparator<Passenger> COMP_BY_NAME = (Passenger p1, Passenger p2) -> {
        int value = col.compare(p1.getSurname(), p2.getSurname());
        if (value == 0) {
            value = col.compare(p1.getName(), p2.getName());
        }
        return value;
    };
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.date).append(" ").append(this.flightNumber).append(" ").append(this.departureAirport).append(" ").append(this.arrivalAirport).append(" ").append(this.departureTime).append(" ").append(this.arrivalTime).append(" ").append(this.typeOfAircraft).append("\n");
        return builder.toString();
    }

    public String generateBoardingPass(int passengerNumber) {
        return getDepartureAirport() + " to " + getArrivalAirport() + " | flight: " + getFlightNumber() + " | " + getDate() + "\n" + findPassenger(passengerNumber).passengerBoardingPass() + "\nBoarding opens at: " + TimeTools.minutesToStringTime(TimeTools.stringTimeToMinutes(this.departureTime) - 30);
    }
    
    
    
    public void saveToFile(File passengerSurname,Passenger passenger) throws IOException{
        
        try (PDDocument document = new PDDocument()) {
            PDPage boardingPass = new PDPage();
            document.addPage(boardingPass);
            PDPageContentStream contentStream = new PDPageContentStream(document, boardingPass);
            contentStream.beginText();
            
            contentStream.setFont(PDType1Font.HELVETICA, 12);            
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(25, 725);
            String text1 = "TUL AIR";
            String text2 = "This is your boarding pass:";
            String text3 = getDepartureAirport() + " to " + getArrivalAirport() + " | flight: " + getFlightNumber() + " | " + getDate();
            String text4 = "Boarding opens at: " + TimeTools.minutesToStringTime(TimeTools.stringTimeToMinutes(this.departureTime) - 30);
            String text5 = "Seat: " + passenger.getRow() + passenger.getSeat() + " | " + passenger.getName() + " " + passenger.getSurname() + " " + passenger.getGender() + " | " + passenger.getId() + " " + passenger.getIDNumber() + " | " + passenger.getNumberOfBaggage() + "x " + passenger.getTypeOfBaggage() + " Baggage | FFP: " + passenger.getNumberFrequentFlyerProgram();
            contentStream.showText(text1);
            contentStream.newLine();
            contentStream.showText(text2);
            contentStream.newLine();
            contentStream.showText(text3);
            contentStream.newLine();
            contentStream.showText(text4);
            contentStream.newLine();
            contentStream.showText(text5);
            contentStream.endText();
            contentStream.close();
            document.save(new File("C:/Users/jires/OneDrive/Dokumenty/NetBeansProjects/2122ALG2-Jires-LeteckaSpolecnost/data/BoardingPass" + passenger.getSurname() + ".pdf"));
            document.close();
        } catch (NoSuchElementException e) {
                System.out.println("PDF not saved - ERROR");
            }
       
        
        }
    
    public void printFlight(File flightNumber) throws IOException  {
    try (PDDocument document = new PDDocument()) {
            PDPage flightList = new PDPage();
            document.addPage(flightList);
            PDPageContentStream contentStream = new PDPageContentStream(document, flightList);
            contentStream.beginText();            
            contentStream.setFont(PDType1Font.HELVETICA, 12);            
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(25, 725);
            String text1 = toString();
            text1 = text1.replace("\n", "").replace("\r", "");
            String text2 = "Passengers:";
            contentStream.showText(text1);
            contentStream.newLine();
            contentStream.showText(text2);
            contentStream.newLine();
            
            for (Passenger passenger : passengers) {
            if (passenger.getRow()!=0) {
            String textPassenger=passenger.toString();
            contentStream.showText(textPassenger);
            contentStream.newLine();
            }
        }
            contentStream.endText();
            contentStream.close();
            document.save(new File("C:/Users/jires/OneDrive/Dokumenty/NetBeansProjects/2122ALG2-Jires-LeteckaSpolecnost/data/flight" + getFlightNumber() + ".pdf"));
            document.close();
        } catch (NoSuchElementException e) {
                System.out.println("PDF not saved - ERROR");
            }
    }

    public int getSequence() {
        return Sequence;
    }

    
 public static void main(String[] args) {
 Flight ezy2252 = new Flight("06.05.2022",2252, "PRG", "LTN", "16:50", "17:45", "A320", "G-EZAS");
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
     ezy2252.findPassenger(1).setGender('M');
     ezy2252.sortBySurname();    
     System.out.println(ezy2252.toString());
     System.out.println(ezy2252.generateBoardingPass(1));



 
 }

    
}
