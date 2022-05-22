package ui;
import app.Destination;
import static app.Destination.loadDestination;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class Main {

        private static Scanner sc = new Scanner(System.in);

    
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
OUTER:
while (true) {
            try {
                displayMenu();
                int choice;
                choice = sc.nextInt();
                switch (choice) {
                    case 0:
                        break OUTER;
                    case 1:
                        //System.out.println(Destination.getDestinations());
                        break;
                    case 2:
                        break;
                    default:
                        System.out.println("Požadavek nenalezen");
                        break;
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
     private static void displayMenu() {
        System.out.println("1. Vyhledat let\n2. Spravovat rezervaci\n0. Exit");
    }
    
}
