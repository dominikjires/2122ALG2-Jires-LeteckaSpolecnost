package ui;

import app.Airline;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;


/**
 * hlavní uživatelské rozhraní
 * @author Dominik Jireš
 */
public class Main {

    private static final Scanner sc = new Scanner(System.in);
    
    /**
     * Hlavní UI menu, zapne se se spuštěním programu
     * @param args
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        Airline a = new Airline("TUL air");
        //pokus o načtení destiancí ze souboru
        try {            
            try {
                Airline.loadDestination(new File("data/destinations.txt"));
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        //hlavní menu
        String choice;
        Boolean success = false;
        do {
            try {
                displayMenu();
                choice = sc.next();
                switch (Integer.parseInt(choice)) {
                    case 0:
                        //konec programu
                        success = true;
                        break;
                    case 1:
                        //vyhledání destinace
                        subMenu.searchDestination();
                        break;
                    case 2:
                        //vyhledání rezervace
                        subMenu.searchDestinationReservation();
                        break;
                    case 3:
                        //přístup pro zaměstance
                        subMenu.adminArea();
                        break;
                    default:
                        System.out.println("Request not found");
                        break;
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Request not found");
                choice = "0";
            }
        } while (!success);
    }
    
    /**
     * Výpis možností v menu
     */
    private static void displayMenu() {
        System.out.println("--------------------------------------------------------");
        System.out.println("TUL Air");
        System.out.println("--------------------------------------------------------");

        System.out.println("1. Search for a flight\n2. Manage reservation\n3. For administrators only\n0. Exit");
    }

}
