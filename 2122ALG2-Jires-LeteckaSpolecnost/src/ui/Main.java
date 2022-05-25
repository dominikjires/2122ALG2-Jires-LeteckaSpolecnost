package ui;

import app.Airline;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        Airline a = new Airline("TUL air");
        try {
            try {
                a.loadDestination(new File("data/destinations.txt"));
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        int choice;
        do {
            try {
                displayMenu();
                choice = sc.nextInt();
                switch (choice) {
                    case 0:
                        break;
                    case 1:
                        subMenu.searchDestination();
                        break;
                    case 2:
                        subMenu.searchDestinationReservation();
                        break;
                    case 3:
                        subMenu.adminArea();
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

    private static void displayMenu() {
                System.out.println("--------------------------------------------------------");
        System.out.println("TUL Air");
        System.out.println("--------------------------------------------------------");

        System.out.println("1. Search for a flight\n2. Manage reservation\n3. For administrators only\n0. Exit");
    }


}
