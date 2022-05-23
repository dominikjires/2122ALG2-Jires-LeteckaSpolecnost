package ui;

import app.Destination;
import app.Flight;
import app.Passenger;
import java.util.NoSuchElementException;
import java.util.Scanner;

class subMenu {

    private static final Scanner sc = new Scanner(System.in);

    public static void searchDestination() {
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

    private static void searchFlight(String destinationName) {
        Boolean success = false;
        do {
            System.out.println("--------------------------------------------------------");

            System.out.println("Here are available flights to: " + app.Airline.findDestination(destinationName).getName() + "\n");
            System.out.print(Destination.getFlights());
            System.out.println("");
            System.out.println("Please enter a sequence number of flight:\nfor return 0.");
            int number = sc.nextInt();
            if (number == 0) {
                return;
            }
            try {
                app.Airline.findDestination(destinationName).findFlight(number);
                success = true;
                passengerRegistration(destinationName, number);
            } catch (NoSuchElementException e) {
                System.out.println("Flight not found, please try again");
            }

        } while (success == false);
    }

    private static void passengerRegistration(String destinationName, int number) {
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
        Passenger passenger = app.Airline.findDestination(destinationName).findFlight(number).registerPassenger(name, surname);
        manageReservation(destination, flight, passenger);
    }

    private static void manageReservation(Destination destination, Flight flight, Passenger passenger) {
        System.out.println("--------------------------------------------------------");
        System.out.println("Here is your flight!");
        System.out.println("Your flight " + flight.getFlightNumber() + " to " + destination.getName() + " on " + flight.getDate() + " departs at " + flight.getDepartureTime());
        System.out.println("Your booking number is " + passenger.getPassengerNumber());
        System.out.println("--------------------------------------------------------");
        if (!passenger.isPaid()) {
            System.out.println("do you want to pay? y/n");
            char answer;
            while (!Character.toString(answer = sc.next().charAt(0)).matches(".*\\w.*")) {
                System.out.println("please try again:");
                if (answer == 'y' || answer == 'Y' || answer == 'n' || answer == 'N') {
                    break;
                }
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
        System.out.println("1. Check-in\n2. Change flight\n3. Generate boarding pass\n0. Exit");
    }

    private static void checkIn(Destination destination, Flight flight, Passenger passenger) {
        if (passenger.getBirthDay()!=0) {
            System.out.println("You are already checked");
            return;
        }
        //Birth day
        int day;

        System.out.println("Please enter your date of birth: DD/MM/YYYY");
        while (!Integer.toString(day = sc.nextInt()).matches("[1-9]|[12][0-9]|3[01]")) {
            System.out.println("please try day again:");
        };

        //Birth month
        System.out.printf("/");
        int month;
        while (!Integer.toString(month = sc.nextInt()).matches("[1-9]|1[0-2]")) {
            System.out.println("please try month again:");
        };

        //Birth year
        int year;
        System.out.printf("/");
        while (!Integer.toString(year = sc.nextInt()).matches("[1-9][0-9][0-9][0-9]")) {
            System.out.println("please try year again:");
        };
        System.out.println("--------------------------------------------------------");

        passenger.setBirthDay(day);
        passenger.setBirthMonth(month);
        passenger.setBirthYear(year);
        
        //Gender
        System.out.println("Please enter your gender:");
        System.out.println("f for female, m for man");
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
            if (number != 1 || number != 2) {
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
        while (!Character.toString(answer = sc.next().charAt(0)).matches(".*\\w.*")) {
            System.out.println("please try again:");
            if (answer == 'y' || answer == 'Y' || answer == 'n' || answer == 'N') {
                break;
            }
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
                if (baggage == 1 || baggage == 2) {
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
        }
        System.out.println("--------------------------------------------------------");

        //Frequent flyer program (optional)
        char anwerFFP;
        System.out.println("Are you a member of the frequent flyer program? y/n");
        while (!Character.toString(anwerFFP = sc.next().charAt(0)).matches(".*\\w.*")) {
            System.out.println("please try again:");
            if (anwerFFP == 'y' || anwerFFP == 'Y' || anwerFFP == 'n' || anwerFFP == 'N') {
                break;
            }
        };
        if (anwerFFP == 'y' || anwerFFP == 'Y') {
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
        while (!Character.toString(answerCatering = sc.next().charAt(0)).matches(".*\\w.*")) {
            System.out.println("please try again:");
            if (answerCatering == 'y' || answerCatering == 'Y' || answerCatering == 'n' || answerCatering == 'N') {
                break;
            }
        };
        if (answer == 'y' || answer == 'Y') {
            passenger.setCatering("YES");
        } else {
            passenger.setCatering("NO");
        }
        flight.seatAllocation(passenger.getPassengerNumber());
        showBoardingPass(passenger, flight);
    }

    private static void showBoardingPass(Passenger passenger, Flight flight) {
        if (passenger.getBirthDay()==0) {
            System.out.println("Please check in first");
            return;
        }
                System.out.println("--------------------------------------------------------");
        System.out.println(flight.generateBoardingPass(passenger.getPassengerNumber()));
                System.out.println("--------------------------------------------------------");

        char answerPdf;
        System.out.println("Do you want to save the boarding pass to pdf? y/n");
        while (!Character.toString(answerPdf = sc.next().charAt(0)).matches(".*\\w.*")) {
            System.out.println("please try again:");
            if (answerPdf == 'y' || answerPdf == 'Y' || answerPdf == 'n' || answerPdf == 'N') {
                break;
            }
        };
        if (answerPdf == 'y' || answerPdf == 'Y') {
            System.out.println("Boarding pass saved");
        }
    }
}
