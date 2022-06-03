package ui;

import app.Destination;
import app.Flight;
import app.Passenger;
import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

class subMenu {

    private static final Scanner sc = new Scanner(System.in);

    public static void searchDestination() throws IOException {
        String search;
        Boolean success = false;
        do {
            System.out.println("--------------------------------------------------------");
            System.out.println("Here are the destinations we are currently serving:\n");
            System.out.println(app.Airline.getDestinations());
            System.out.println("Please enter a destination name:\nfor return 0.");
            String destinationName = sc.next();
            if ("0".equals(destinationName)) {
                return;
            }

            try {
                app.Airline.destinationsList.contains(app.Airline.findDestination(destinationName));
                success = true;
                searchFlight(destinationName);
            } catch (NoSuchElementException e) {
                System.out.println("Destination not found, please try again");
            }
        } while (success == false);
    }

    private static void searchFlight(String destinationName) throws IOException {
        Destination destination;
        Boolean success = false;
        do {
            System.out.println("--------------------------------------------------------");

            System.out.println("Here are available flights from/to: " + app.Airline.findDestination(destinationName).getName() + "\n");
            destination = app.Airline.findDestination(destinationName);
            System.out.print(destination.getFlights());
            System.out.println("");
            System.out.println("Please enter a sequence number of flight:\nfor return 0.");
            int number = sc.nextInt();
            if (number == 0) {
                return;
            }
            try {
                destination.findFlight(number);
                success = true;
                
                passengerRegistration(destinationName, number);
                
            } catch (NoSuchElementException e) {
                System.out.println("Flight not found, please try again");
            }

        } while (success == false);
    }

    private static void passengerRegistration(String destinationName, int number) throws IOException {
        System.out.println("--------------------------------------------------------");

        System.out.println("please fill in your contact details");
        System.out.println("your name:");
        String name;
        while (!(name = sc.next()).matches(".*\\w.*")) {
            System.out.println("please try again:");
        };

        String surname;
        System.out.println("your surname:");
        while (!(surname = sc.next()).matches(".*\\w.*")) {
            System.out.println("please try again:");
        };

        Destination destination = app.Airline.findDestination(destinationName);
        Flight flight = destination.findFlight(number);
        name = name.substring(0,1).toUpperCase() + surname.substring(1).toLowerCase();
        surname = surname.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
        Passenger passenger = app.Airline.findDestination(destinationName).findFlight(number).registerPassenger(name, surname);
        manageReservation(destination, flight, passenger);
    }

    private static void manageReservation(Destination destination, Flight flight, Passenger passenger) throws IOException {
        System.out.println("--------------------------------------------------------");
        System.out.println("Here is your flight!");
        System.out.println("Your flight " + flight.getFlightNumber() + " to " + flight.getDepartureAirport() + " on " + flight.getDate() + " departs at " + flight.getDepartureTime());
        System.out.println("Your booking number is " + passenger.getPassengerNumber());
        System.out.println("--------------------------------------------------------");
        if (!passenger.isPaid()) {
            System.out.println("do you want to pay? y/n");
            char answer;
            while ((answer = sc.next().charAt(0))!='y') {               
                if (answer == 'y' || answer == 'Y' || answer == 'n' || answer == 'N') {
                    break;
                }
                System.out.println("please try again:");
            };
            if (answer == 'y' || answer == 'Y') {
                System.out.println("Successfully paid!");
                passenger.setPaid(true);
            } else {
                System.out.println("--------------------------------------------------------");
                System.out.println("You can pay later, returning to the introduction");
            }

        }
        if (passenger.isPaid()) {
            int choice;
            do {
                try {
                    displaySecondMenu();
                    choice = sc.nextInt();
                    switch (choice) {
                        case 0:
                            break;
                        case 1:
                            checkIn(destination, flight, passenger);
                            break;
                        case 2:
                            choice=(cancelFlight(passenger, flight))?0:2;
                            break;
                        case 3:
                            showBoardingPass(passenger, flight);
                            break;
                        default:
                            System.out.println("Request not found");
                            break;
                    }
                } catch (IllegalArgumentException | IllegalStateException e) {
                    System.out.println(e.getMessage());
                    choice = 0;
                }
            } while (choice != 0);
        }

    }

    private static void displaySecondMenu() {
        System.out.println("--------------------------------------------------------");
        System.out.println("Your Booking:");
        System.out.println("--------------------------------------------------------");
        System.out.println("1. Check-in\n2. Cancel flight\n3. Generate boarding pass\n0. Exit");
    }

    private static void checkIn(Destination destination, Flight flight, Passenger passenger) throws IOException {
        if (passenger.getRow()!=0) {
            System.out.println("You are already checked");
            return;
        }
        //Birth day
        int day;

        System.out.println("Please enter your day of birth:");
        while (!Integer.toString(day = sc.nextInt()).matches("[1-9]|[12][0-9]|3[01]")) {
            System.out.println("please try day again:");
        };

        //Birth month
        System.out.println("Please enter your month of birth:");
        int month;
        while (!Integer.toString(month = sc.nextInt()).matches("[1-9]|1[0-2]")) {
            System.out.println("please try month again:");
        };

        //Birth year
        int year;
        System.out.println("Please enter your year of birth:");
        while (!Integer.toString(year = sc.nextInt()).matches("[1-9][0-9][0-9][0-9]")) {
            System.out.println("please try year again:");
        };
        System.out.println("--------------------------------------------------------");

        passenger.setBirthDay(day);
        passenger.setBirthMonth(month);
        passenger.setBirthYear(year);
        
        //Gender
        System.out.println("Please enter your gender:");
        System.out.println("f for female, m for male");
        char gender;
        while ((gender = sc.next().charAt(0)) != 'f') {
            if (gender == 'f' || gender == 'F' || gender == 'm' || gender == 'M') {
                break;
            }
            System.out.println("please try again:");
        }

        if (gender == 'f' || gender == 'F') {
            passenger.setGender('f');
        } else {
            passenger.setGender('m');
        }
        System.out.println("--------------------------------------------------------");

        //Document type
        System.out.println("Please select a document type:");
        System.out.println("1. Passport\n2. ID");
        int number;
        while ((number = sc.nextInt()) != 1) {
            if (number == 1 || number == 2) {
                break;
            }
            System.out.println("please try again:");

        }

        if (number == 1) {
            passenger.setID("Passport");
        } else {
            passenger.setID("ID");
        }

        //ID number
        String IDnumber;
        System.out.println("Please enter the document number:");
        while (!(IDnumber = sc.next()).matches(".*\\w.*")) {
            System.out.println("please try again:");
        };
        System.out.println("--------------------------------------------------------");
        passenger.setIDNumber(IDnumber);

        //Type and number of baggage
        System.out.println("do you want to add some baggage? y/n");
        char answer;
        while ((answer = sc.next().charAt(0))!='y') {           
            if (answer == 'y' || answer == 'Y' || answer == 'n' || answer == 'N') {
                break;
            }
            System.out.println("please try again:");
        };
        if (answer == 'y' || answer == 'Y') {

            System.out.println("How many kg? 20/32");
            int baggage;
            while ((baggage = sc.nextInt()) != 20) {
                if (baggage == 20 || baggage == 32) {
                    break;
                }
                System.out.println("please try again:");

            }
            if (baggage == 20) {
                passenger.setTypeOfBaggage('S');
            } else {
                passenger.setTypeOfBaggage('l');
            }
            System.out.println("How much bagagge? max 2 per person");
            int numberOfBaggage;
            while ((numberOfBaggage = sc.nextInt()) != 1) {
                if (numberOfBaggage == 1 || numberOfBaggage == 2) {
                    break;
                }
                System.out.println("please try again:");
            }
            if (numberOfBaggage == 1) {
                passenger.setNumberOfBaggage(1);
            } else {
                passenger.setNumberOfBaggage(2);
            }
            System.out.println("Bagagge successfully added!");
        } else {
        passenger.setTypeOfBaggage('n');
        }
        System.out.println("--------------------------------------------------------");

        //Frequent flyer program (optional)
        char answerFFP;
        System.out.println("Are you a member of the frequent flyer program? y/n");
        while ((answerFFP = sc.next().charAt(0))!='y') {            
            if (answerFFP == 'y' || answerFFP == 'Y' || answerFFP == 'n' || answerFFP == 'N') {
                break;    
            }
            System.out.println("please try again:");
        };
        if (answerFFP == 'y' || answerFFP == 'Y') {
            System.out.println("Please fill in the number of FFP:");
            int FFPNumber;
            while (!Integer.toString(FFPNumber = sc.nextInt()).matches(".*\\w.*")) {
                System.out.println("please try again:");
            };
            passenger.setNumberFrequentFlyerProgram(FFPNumber);
        }
        System.out.println("--------------------------------------------------------");

        //Catering (optional)
        char answerCatering;
        System.out.println("Do you want to order some catering? y/n");
        while ((answerCatering = sc.next().charAt(0))!='y') {            
            if (answerCatering == 'y' || answerCatering == 'Y' || answerCatering == 'n' || answerCatering == 'N') {
                break;
            }
            System.out.println("please try again:");
        };
        if (answer == 'y' || answer == 'Y') {
            passenger.setCatering("YES");
        } else {
            passenger.setCatering("NO");
        }
        try{
        flight.seatAllocation(passenger.getPassengerNumber());
        showBoardingPass(passenger, flight);
        } catch(NoSuchElementException e) {
                System.out.println("Flight is full please change your flight");
                return;
        }
    }

    private static void showBoardingPass(Passenger passenger, Flight flight) throws IOException {
        if (passenger.getBirthDay()==0) {
            System.out.println("Please check in first");
            return;
        }
                System.out.println("--------------------------------------------------------");
        System.out.println(flight.generateBoardingPass(passenger.getPassengerNumber()));
                System.out.println("--------------------------------------------------------");

        char answerPdf;
        System.out.println("Do you want to save the boarding pass to pdf? y/n");
        while ((answerPdf = sc.next().charAt(0))!='y') {            
            if (answerPdf == 'y' || answerPdf == 'Y' || answerPdf == 'n' || answerPdf == 'N') {
                break;
            }
            System.out.println("please try again:");
        };
        if (answerPdf == 'y' || answerPdf == 'Y') {
            flight.saveToFile(new File("data/BoardingPass" + passenger.getSurname() + ".txt"),passenger);
            System.out.println("Boarding pass saved");
        }
    }

    static void searchDestinationReservation() throws IOException {
    String search;
        Boolean success = false;
        Destination destination;
        do {
            System.out.println("--------------------------------------------------------");
            System.out.println("Please enter your destination name:\nfor return 0.");
            String destinationName = sc.next();
            if ("0".equals(destinationName)) {
                return;
            }

            try {
                app.Airline.destinationsList.contains(app.Airline.findDestination(destinationName));
                success = true;
                destination = app.Airline.findDestination(destinationName);
                searchFlightReservation(destination);
            } catch (NoSuchElementException e) {
                System.out.println("Destination not found, please try again");
            }
        } while (success == false);
    }

    private static void searchFlightReservation(Destination destination) throws IOException {
        Boolean success = false;
        Flight flight;
        do {
                        System.out.println("--------------------------------------------------------");
            System.out.print(destination.getFlights());
            System.out.println("");
            System.out.println("Please enter a sequence number of your flight:\nfor return 0.");
            int number = sc.nextInt();
            if (number == 0) {
                return;
            }
            try {
                flight=destination.findFlight(number);
                success = true;
                searchPassengerNumber(destination,flight);
            } catch (NoSuchElementException e) {
                System.out.println("Flight not found, please try again");
            }

        } while (success == false);
    }

    private static void searchPassengerNumber(Destination destination, Flight flight) throws IOException {
       Boolean success = false;
        Passenger passenger;
        do {    
            System.out.println("--------------------------------------------------------");
            System.out.println("Please enter your booking number:\nfor return 0.");
            int number = sc.nextInt();
            if (number == 0) {
                return;
            }
            try {
                passenger=flight.findPassenger(number);
                success = true;
                manageReservation(destination,flight,passenger);
                
            } catch (NoSuchElementException e) {
                System.out.println("Booking number not found, please try again");
            }

        } while (success == false);
    }

    private static boolean cancelFlight(Passenger passenger, Flight flight) {
        
        if (passenger.getRow()!=0) {
            System.out.println("You cannot cancel the flight after the check-in!");
            return false;
        }
        
        
        
        try {
                flight.removePassenger(passenger.getPassengerNumber());
            } catch (NoSuchElementException e) {
                System.out.println("Passenger not found");
                return true;
            }
        System.out.println("Flight canceled successfully!");
        return true;
        
    }

    static void adminArea(){
        Boolean success = false;
    do {
            System.out.println("--------------------------------------------------------");
            System.out.println("Please enter a password:\nfor return 0.");
            String Password = sc.next();
            if ("0".equals(Password)) {
                return;
            }
            if("TUL".equals(Password)){
                success = true;
                DestinationDatabase();
            } else {
                System.out.println("Password incorect");
            }
        } while (success == false);
    }

    private static void DestinationDatabase(){
    String search;
        Boolean success = false;
        do {
            
            System.out.println(app.Airline.getDestinations());
            String destinationName = sc.next();
            if ("0".equals(destinationName)) {
                return;
            }
            try {
                app.Airline.destinationsList.contains(app.Airline.findDestination(destinationName));
                Destination destination = app.Airline.findDestination(destinationName);
                success = true;
                try {
                    flightDatabase(destination);
                } catch (IOException ex) {
                    Logger.getLogger(subMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (NoSuchElementException e) {
                System.out.println("Destination not found, please try again");
            }
        } while (success == false);
    }

    private static void flightDatabase(Destination destination) throws IOException {
        Boolean success = false;
        do {
            System.out.print(destination.getFlights());
            System.out.println("");
            int number = sc.nextInt();
            if (number == 0) {
                return;
            }
            try {
                destination.findFlight(number);
                Flight flight = destination.findFlight(number);
                success = true;
                flight.printFlight(new File("data/" + flight.getFlightNumber() + "_" + flight.getDate() + ".txt"));
            } catch (NoSuchElementException e) {
                System.out.println("Flight not found, please try again");
            }

        } while (success == false);
    }
    
 

    



}
