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

/**
 * vedlejší uživatelské rozhraní
 * @author Dominik Jireš
 */
class subMenu {

    private static final Scanner sc = new Scanner(System.in);
    
    /**
     * Vyhledávač letů
     */
    public static void searchDestination() throws IOException {
        String search;
        Boolean success = false;
        do {
            //výpis všech dostupných destinací
            System.out.println("--------------------------------------------------------");
            System.out.println("Here are the destinations we are currently serving:\n");
            System.out.println(app.Airline.getDestinations());
            //zadání požadované destinace
            System.out.println("Please enter a destination name:\nfor return 0.");
            String destinationName = sc.next();
            if ("0".equals(destinationName)) {
                return;
            }

            try {
                app.Airline.destinationsList.contains(app.Airline.findDestination(destinationName));
                success = true;
                //vyhledat let
                searchFlight(destinationName);
            } catch (NoSuchElementException e) {
                System.out.println("Destination not found, please try again");
            }
        } while (success == false);
    }
    
    /**
     * Vyhledání letu
     * @param destinationName String
     */
    private static void searchFlight(String destinationName) throws IOException {
        Destination destination;
        Boolean success = false;
        do {
            //výpis dostupných letů
            System.out.println("--------------------------------------------------------");
            System.out.println("Here are available flights from/to: " + app.Airline.findDestination(destinationName).getName() + "\n");
            destination = app.Airline.findDestination(destinationName);
            System.out.print(destination.getFlights());
            System.out.println("");
            //zadání požadovaného letu
            System.out.println("Please enter a sequence number of flight:\nfor return 0.");
            int number = sc.nextInt();
            if (number == 0) {
                return;
            }
            try {
                //zaregistrování pasažéra
                destination.findFlight(number);
                success = true;
                passengerRegistration(destinationName, number);

            } catch (NoSuchElementException e) {
                System.out.println("Flight not found, please try again");
            }

        } while (success == false);
    }

    /**
     * Registarce pasažéra
     * @param destiantionName String
     * @param number int
     */
    private static void passengerRegistration(String destinationName, int number) throws IOException {
        System.out.println("--------------------------------------------------------");
        //zadání kontaktních údajů
        System.out.println("please fill in your contact details");
        //zadání křestního jména
        System.out.println("your name:");
        String name;
        while (!(name = sc.next()).matches(".*\\w.*")) {
            System.out.println("please try again:");
        };
        //zadání příjmení
        String surname;
        System.out.println("your surname:");
        while (!(surname = sc.next()).matches(".*\\w.*")) {
            System.out.println("please try again:");
        };
        
        //zaregistrování pasažéra na požadovaný let, přeměna stringu na tvar první velké písmeno, ostatní malá
        Destination destination = app.Airline.findDestination(destinationName);
        Flight flight = destination.findFlight(number);
        name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        surname = surname.substring(0, 1).toUpperCase() + surname.substring(1).toLowerCase();
        Passenger passenger = app.Airline.findDestination(destinationName).findFlight(number).registerPassenger(name, surname);
        //přesměrování na správu rezervace
        manageReservation(destination, flight, passenger);
    }
    
    /**
     * Správa rezervace, submenu
     * @param destination Destination
     * @param flight Flight
     * @param passenger Passenger
     */
    private static void manageReservation(Destination destination, Flight flight, Passenger passenger) throws IOException {
        //výpis základních informací o letu, přidělení čísla rezervace
        System.out.println("--------------------------------------------------------");
        System.out.println("Here is your flight!");
        System.out.println("Your flight " + flight.getFlightNumber() + " to " + flight.getDepartureAirport() + " on " + flight.getDate() + " departs at " + flight.getDepartureTime());
        System.out.println("Your booking number is " + passenger.getPassengerNumber());
        System.out.println("--------------------------------------------------------");
        //otázka zda chce pasažér zaplatit, bez zaplacení není vpuštěn do submenu, zaplatit lze i poté přes vyhledání rezervace
        if (!passenger.isPaid()) {
            //pokud již zaplatil otázka se nezobrazuje
            System.out.println("do you want to pay? y/n");
            char answer;
            while ((answer = sc.next().charAt(0)) != 'y') {
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
        //submenu - správa rezervace
        if (passenger.isPaid()) {
            String choice;
            do {
                try {
                    //vyúsání možností výběru
                    displaySecondMenu();
                    choice = sc.next();
                    switch (Integer.valueOf(choice)) {
                        case 0:
                            //vrácení se do hlavního menu
                            break;
                        case 1:
                            //odbavení rezervace
                            checkIn(destination, flight, passenger);
                            break;
                        case 2:
                            //zrušení rezervace
                            choice = String.valueOf((cancelFlight(passenger, flight)) ? 0 : 2);
                            break;
                        case 3:
                            //stáhnutí palubní vstupenky
                            showBoardingPass(passenger, flight);
                            break;
                        default:
                            System.out.println("Request not found");
                            break;
                    }
                } catch (IllegalArgumentException | IllegalStateException e) {
                    System.out.println("Request not found");
                    choice = "4";
                }
            } while (!"0".equals(choice));
        }

    }

    /**
     * Vypsání možností druhého menu
     */
    private static void displaySecondMenu() {
        System.out.println("--------------------------------------------------------");
        System.out.println("Your Booking:");
        System.out.println("--------------------------------------------------------");
        System.out.println("1. Check-in\n2. Cancel flight\n3. Generate boarding pass\n0. Exit");
    }
    
    /**
     * Odbavení
     * @param destination Destination
     * @param flight Flight
     * @param passenger Passenger
     */
    private static void checkIn(Destination destination, Flight flight, Passenger passenger) throws IOException {
        //kontrola přiřazené řady sedadla zda není pasažér již odbavený (pokud ano - zpět do submenu)
        if (passenger.getRow() != 0) {
            System.out.println("You are already checked");
            return;
        }
        //zadání dne narození
        String day;
        System.out.println("Please enter your day of birth:");
        while (!(day = sc.next()).matches("[1-9]|[12][0-9]|3[01]")) {
            System.out.println("please try day again:");
        };
        //zadání měsíce narození
        System.out.println("Please enter your month of birth:");
        String month;
        while (!(month = sc.next()).matches("[1-9]|1[0-2]")) {
            System.out.println("please try month again:");
        };
        //zadání roku narození
        String year;
        System.out.println("Please enter your year of birth:");
        while (!(year = sc.next()).matches("[1-9][0-9][0-9][0-9]")) {
            System.out.println("please try year again:");
        };
        System.out.println("--------------------------------------------------------");

        passenger.setBirthDay(Integer.valueOf(day));
        passenger.setBirthMonth(Integer.valueOf(month));
        passenger.setBirthYear(Integer.valueOf(year));

        //výběr pohlaví
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
        //výběr typu dokumentu
        System.out.println("Please select a document type:");
        System.out.println("1. Passport\n2. ID");
        String number;
        while (!"1".equals(number = sc.next())) {
            if ("1".equals(number) || "2".equals(number)) {
                break;
            }
            System.out.println("please try again:");

        }
        if ("1".equals(number)) {
            passenger.setID("Passport");
        } else {
            passenger.setID("ID");
        }

        //zadání čísla dokumentu
        String IDnumber;
        System.out.println("Please enter the document number:");
        while (!(IDnumber = sc.next()).matches(".*\\w.*")) {
            System.out.println("please try again:");
        };
        System.out.println("--------------------------------------------------------");
        passenger.setIDNumber(IDnumber);

        //výběr zda cestující požaduje zavazadlo
        System.out.println("do you want to add some baggage? y/n");
        char answer;
        while ((answer = sc.next().charAt(0)) != 'y') {
            if (answer == 'y' || answer == 'Y' || answer == 'n' || answer == 'N') {
                break;
            }
            System.out.println("please try again:");
        };
        if (answer == 'y' || answer == 'Y') {
            //výběr váhy zavazadla
            System.out.println("How many kg? 20/32");
            String baggage;
            while (!"20".equals(baggage = sc.next())) {
                if ("20".equals(baggage) || "32".equals(baggage)) {
                    break;
                }
                System.out.println("please try again:");

            }
            if ("20".equals(baggage)) {
                passenger.setTypeOfBaggage('S');
            } else {
                passenger.setTypeOfBaggage('l');
            }
            //výběr počtu zavazadel
            System.out.println("How much bagagge? max 2 per person");
            String numberOfBaggage;
            while (!"1".equals(numberOfBaggage = sc.next())) {
                if ("1".equals(numberOfBaggage) || "2".equals(numberOfBaggage)) {
                    break;
                }
                System.out.println("please try again:");
            }
            if ("1".equals(numberOfBaggage)) {
                passenger.setNumberOfBaggage(1);
            } else {
                passenger.setNumberOfBaggage(2);
            }
            System.out.println("Bagagge successfully added!");
        } else {
            passenger.setTypeOfBaggage('n');
        }
        System.out.println("--------------------------------------------------------");

        //výběr pokud chce cestující zadat číslo věrnostní karty
        char answerFFP;
        System.out.println("Are you a member of the frequent flyer program? y/n");
        while ((answerFFP = sc.next().charAt(0)) != 'y') {
            if (answerFFP == 'y' || answerFFP == 'Y' || answerFFP == 'n' || answerFFP == 'N') {
                break;
            }
            System.out.println("please try again:");
        };
        if (answerFFP == 'y' || answerFFP == 'Y') {
            //zadání čísla věrnostní karty
            System.out.println("Please fill in the number of FFP:");
            String FFPNumber;
            while (!(FFPNumber = sc.next()).matches(".*\\w.*")) {
                System.out.println("please try again:");
            };
            passenger.setNumberFrequentFlyerProgram(FFPNumber);
        }
        System.out.println("--------------------------------------------------------");

        //výběr zda cestující požaduje občerstvení
        char answerCatering;
        System.out.println("Do you want to order some catering? y/n");
        while ((answerCatering = sc.next().charAt(0)) != 'y') {
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
        //přiřazení sedadla, pokud je již let plný - zrušení letenky a registrace nové
        try {
            flight.seatAllocation(passenger.getPassengerNumber());
            //ukázání palubní vstupenky
            showBoardingPass(passenger, flight);
        } catch (NoSuchElementException e) {
            System.out.println("Flight is full please change your flight");
            return;
        }
    }

    /**
     * Ukázání palubní vstupenky
     * @param passenger Passenger
     * @param flight Flight
     */
    private static void showBoardingPass(Passenger passenger, Flight flight) throws IOException {
        //kontrola zda má cestující provedeno odbavení (pokud ne zpět do submenu)
        if (passenger.getRow() == 0) {
            System.out.println("Please check in first");
            return;
        }
        
        //generování boarding passu
        System.out.println("--------------------------------------------------------");
        System.out.println(flight.generateBoardingPass(passenger.getPassengerNumber()));
        System.out.println("--------------------------------------------------------");

        //výběr zda cestující chce uložit palubní vstupenku do pdf
        char answerPdf;
        System.out.println("Do you want to save the boarding pass to pdf? y/n");
        while ((answerPdf = sc.next().charAt(0)) != 'y') {
            if (answerPdf == 'y' || answerPdf == 'Y' || answerPdf == 'n' || answerPdf == 'N') {
                break;
            }
            System.out.println("please try again:");
        };
        if (answerPdf == 'y' || answerPdf == 'Y') {
            //uložení palubní vstupenky do pdf
            flight.saveToFile(new File("data/BoardingPass" + passenger.getSurname() + ".txt"), passenger);
            System.out.println("Boarding pass saved");
        }
    }

    /**
     * Vyhledání rezervace - destinace
     */
    static void searchDestinationReservation() throws IOException {
        String search;
        Boolean success = false;
        Destination destination;
        do {
            //zadání destinace do které/z je rezervace vytvořena
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
                //vyhledání letu rezervace
                searchFlightReservation(destination);
            } catch (NoSuchElementException e) {
                System.out.println("Destination not found, please try again");
            }
        } while (success == false);
    }

    /**
     * Vyhledání letu rezervace
     * @param destination Destination
     */
    private static void searchFlightReservation(Destination destination) throws IOException {
        Boolean success = false;
        Flight flight;
        do {
            //vypsání letů
            System.out.println("--------------------------------------------------------");
            System.out.print(destination.getFlights());
            System.out.println("");
            //zadání požadovaného letu
            System.out.println("Please enter a sequence number of your flight:\nfor return 0.");
            int number = sc.nextInt();
            if (number == 0) {
                return;
            }
            try {
                //vyhledání čísla registrace
                flight = destination.findFlight(number);
                success = true;
                searchPassengerNumber(destination, flight);
            } catch (NoSuchElementException e) {
                System.out.println("Flight not found, please try again");
            }

        } while (success == false);
    }

    /**
     * Vyhledání čísla rezervace
     * @param destination Destination
     * @param flight Flight
     */
    private static void searchPassengerNumber(Destination destination, Flight flight) throws IOException {
        Boolean success = false;
        Passenger passenger;
        do {
            //zadání čísla rezervace
            System.out.println("--------------------------------------------------------");
            System.out.println("Please enter your booking number:\nfor return 0.");
            int number = sc.nextInt();
            if (number == 0) {
                return;
            }
            try {
                passenger = flight.findPassenger(number);
                success = true;
                //předání indormací do správy rezervace
                manageReservation(destination, flight, passenger);

            } catch (NoSuchElementException e) {
                System.out.println("Booking number not found, please try again");
            }

        } while (success == false);
    }

    /**
     * Zrušení letenky
     * @param passenger Passenger
     * @param flight Flight
     * @return boolean
     */
    private static boolean cancelFlight(Passenger passenger, Flight flight) {
        //kontrola zda cestující neudělal odbavení (pokud ano - zpět do submenu)
        if (passenger.getRow() != 0) {
            System.out.println("You cannot cancel the flight after the check-in!");
            return false;
        }
        //pokus o zrušení letenky pokud je cestující nalezen
        try {
            flight.removePassenger(passenger.getPassengerNumber());
        } catch (NoSuchElementException e) {
            System.out.println("Passenger not found");
            return true;
        }
        System.out.println("Flight canceled successfully!");
        return true;

    }

    /**
     * Vstup do zaměstnanecké sekce
     */
    static void adminArea() {
        Boolean success = false;
        do {
            //zadání hesla
            System.out.println("--------------------------------------------------------");
            System.out.println("Please enter a password:\nfor return 0.");
            String Password = sc.next();
            if ("0".equals(Password)) {
                return;
            }
            if ("TUL".equals(Password)) {
                success = true;
                //vstup do databáze
                DestinationDatabase();
            } else {
                System.out.println("Password incorect");
            }
        } while (success == false);
    }

    /**
     * Datadáze destinací
     */
    private static void DestinationDatabase() {
        String search;
        Boolean success = false;
        do {
            //zadání destinace
            System.out.println(app.Airline.getDestinations());
            String destinationName = sc.next();
            if ("0".equals(destinationName)) {
                return;
            }
            try {
                //databáze letů
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

    /**
     * Databáze letů a uložení seznamu pasažérů
     * @param destination Destination
     * @return
     */
    private static void flightDatabase(Destination destination) throws IOException {
        Boolean success = false;
        do {
            //zadání letu
            System.out.print(destination.getFlights());
            System.out.println("");
            int number = sc.nextInt();
            if (number == 0) {
                return;
            }
            try {
                //uložení seznamu pasažérů
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
